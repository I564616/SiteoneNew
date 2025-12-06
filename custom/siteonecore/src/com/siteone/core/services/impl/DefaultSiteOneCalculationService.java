/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.OrderManager;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.order.strategies.calculation.OrderRequiresCalculationStrategy;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.DiscountValue;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.util.TaxValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneCalculationService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.strategies.impl.SiteOneCommerceCartEntryRemovalStrategy;
import com.siteone.facades.customer.price.SiteOneCartCSPProductData;
import com.siteone.facades.customer.price.SiteOneCartCspPriceData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.services.ue.SiteOnePriceWebService;


/**
 * @author 1219341 i
 */
public class DefaultSiteOneCalculationService extends DefaultCalculationService implements SiteOneCalculationService
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCalculationService.class);
	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);
	private static final int MAX_LIMIT_ROUND = 4;

	private final boolean taxFreeEntrySupport = false;


	private ProductService productService;

	@Autowired
	private OrderRequiresCalculationStrategy orderRequiresCalculationStrategy;
	@Autowired
	private CommonI18NService commonI18NService;
	private UserFacade userFacade;
	private SiteOnePriceWebService siteOnePriceWebService;
	private PriceDataFactory priceDataFactory;
	@Autowired
	private B2BCustomerService b2bCustomerService;
	private SiteOneCommerceCartEntryRemovalStrategy siteOneCommerceCartEntryRemovalStrategy;
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
	private SiteOneProductUOMService siteOneProductUOMService;
	private ModelService modelService;
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	public static final String MIXED_CART_ENABLED = "MixedCartEnabledBranches";
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String D365_CSP_BRANCHES = "d365cspPriceBranches";
	private static final String D365_NEW_URL = "isD365NewUrlEnable";
	private static final int CURRENCY_PERUNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);

	@Override
	public void calculate(final AbstractOrderModel order) throws CalculationException
	{
		if (orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			// -----------------------------
			// first calc all entries
			calculateEntries(order, false);
			// -----------------------------
			// reset own values
			final Map taxValueMap = resetAllValues(order);
			// -----------------------------
			// now calculate all totals
			calculateTotals(order, false, taxValueMap);
			// notify manual discouns - needed?
			//notifyDiscountsAboutCalculation();
		}
	}

	@Override
	public void calculateEntries(final AbstractOrderModel order, final boolean forceRecalculate) throws CalculationException
	{
		double subtotal = 0.0;
		for (final AbstractOrderEntryModel e : order.getEntries())
		{
			recalculateOrderEntryIfNeeded(e, forceRecalculate);
			subtotal += e.getTotalPrice().doubleValue();
		}
		order.setTotalPrice(Double.valueOf(subtotal));

	}

	@Override
	protected Map resetAllValues(final AbstractOrderModel order) throws CalculationException
	{
		// -----------------------------
		// set subtotal and get tax value map
		final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap = calculateSubtotal(order, false);
		/*
		 * filter just relative tax values - payment and delivery prices might require conversion using taxes -> absolute
		 * taxes do not apply here TODO: ask someone for absolute taxes and how they apply to delivery cost etc. - this
		 * implementation might be wrong now
		 */
		final Collection<TaxValue> relativeTaxValues = new LinkedList<TaxValue>();
		for (final Map.Entry<TaxValue, ?> e : taxValueMap.entrySet())
		{
			final TaxValue taxValue = e.getKey();
			if (!taxValue.isAbsolute())
			{
				relativeTaxValues.add(taxValue);
			}
		}

		//PLA-10914
		final boolean setAdditionalCostsBeforeDiscounts = Config
				.getBoolean("ordercalculation.reset.additionalcosts.before.discounts", true);
		if (setAdditionalCostsBeforeDiscounts)
		{
			resetAdditionalCosts(order, relativeTaxValues);
		}
		// -----------------------------
		// set discount values ( not applied yet ) - dont needed in model domain (?)
		//removeAllGlobalDiscountValues();
		order.setGlobalDiscountValues(findGlobalDiscounts(order));
		// -----------------------------
		// set delivery costs - convert if net or currency is different

		if (!setAdditionalCostsBeforeDiscounts)
		{
			resetAdditionalCosts(order, relativeTaxValues);
		}

		return taxValueMap;

	}

	@Override
	protected void resetAllValues(final AbstractOrderEntryModel entry) throws CalculationException
	{
		final List<DiscountValue> entryDiscounts = findDiscountValues(entry);
		entry.setDiscountValues(entryDiscounts);
	}

	@Override
	public PriceValue convertPriceIfNecessary(final PriceValue pv, final boolean toNet, final CurrencyModel toCurrency,
			final Collection taxValues)
	{
		// net - gross - conversion
		double convertedPrice = pv.getValue();
		if (pv.isNet() != toNet)
		{
			convertedPrice = pv.getOtherPrice(taxValues).getValue();
			//commented for FF price round off issue
			//convertedPrice = commonI18NService.roundCurrency(convertedPrice, toCurrency.getDigits().intValue());
		}
		// currency conversion
		final String iso = pv.getCurrencyIso();
		if (iso != null && !iso.equals(toCurrency.getIsocode()))
		{
			try
			{
				final CurrencyModel basePriceCurrency = commonI18NService.getCurrency(iso);
				convertedPrice = commonI18NService.convertAndRoundCurrency(basePriceCurrency.getConversion().doubleValue(),
						toCurrency.getConversion().doubleValue(), CURRENCY_UNIT_PRICE_DIGITS, convertedPrice);
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error(e.getMessage(), e);
				LOG.warn("Cannot convert from currency '" + iso + "' to currency '" + toCurrency.getIsocode() + "' since '" + iso
						+ "' doesn't exist any more - ignored");
			}
		}
		return new PriceValue(toCurrency.getIsocode(), convertedPrice, toNet);
	}

	@Override
	public void calculateTotals(final AbstractOrderEntryModel entry, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(entry))
		{
			final AbstractOrderModel order = entry.getOrder();
			final CurrencyModel curr = order.getCurrency();
			final int digits = 2;
			boolean inventoryUOM = true;
			if (null != entry.getProduct() && null != entry.getProduct().getUpcData())
			{
				if (null != entry.getProduct().getInventoryUPCID())
				{
					if (null == entry.getInventoryUOM())
					{
						final InventoryUPCModel uomData = entry.getProduct().getUpcData().stream()
								.filter(upc -> upc.getBaseUPC().booleanValue()).findFirst().orElse(null);
						entry.setInventoryUOM(uomData);
						modelService.save(entry);
					}
					if(null != entry.getInventoryUOM())
					{
						final List<InventoryUPCModel> upcModels = entry.getProduct().getUpcData().stream()
   							.filter(upc -> upc.getInventoryUPCID().equalsIgnoreCase(entry.getInventoryUOM().getInventoryUPCID()))
   							.collect(Collectors.toList());
   					if (CollectionUtils.isNotEmpty(upcModels))
   					{
   						for (final InventoryUPCModel inventoryUOMData1 : upcModels)
   						{
   							if (null != inventoryUOMData1.getHideUPCOnline() && !inventoryUOMData1.getHideUPCOnline())
   							{
   								final Float inventoryMultiValue = Float.valueOf(inventoryUOMData1.getInventoryMultiplier());
   								final ProductData productDataUom = getProductByUOM(entry, inventoryMultiValue);
   								if (productDataUom != null && productDataUom.getCustomerPrice() != null)
   								{
   									entry.setBasePrice(Double.valueOf(productDataUom.getCustomerPrice().getValue().doubleValue()));
   									if (productDataUom.getPrice() != null)
   									{
   										entry.setListPrice(productDataUom.getPrice().getValue().doubleValue());
   									}
   								}
   								else if (productDataUom != null && productDataUom.getPrice() != null)
   								{
   									entry.setBasePrice(Double.valueOf(productDataUom.getPrice().getValue().doubleValue()));
   								}
   							}
   						}
   					}
					} else {
						inventoryUOM = false;
					}
				}
			}
			if(null != entry.getProduct()) {
				entry.setStoreProductCode(entry.getProduct().getCode());
				entry.setStoreProductItemNumber(entry.getProduct().getItemNumber());
				entry.setStoreProductDesc(entry.getProduct().getProductShortDesc());
			}
			if(inventoryUOM) {
			final double totalPriceWithoutDiscount = commonI18NService
					.roundCurrency(entry.getBasePrice().doubleValue() * entry.getQuantity().longValue(), digits);
			final double quantity = entry.getQuantity().doubleValue();


			//setting value for string quantity
			entry.setQuantityText(Double.toString(quantity));
			/*
			 * apply discounts (will be rounded each) convert absolute discount values in case their currency doesn't match
			 * the order currency
			 */
			//YTODO : use CalculatinService methods to apply discounts
			double totalPrice = totalPriceWithoutDiscount;

			if (totalPriceWithoutDiscount > 0.0)
			{
				final List appliedDiscounts = DiscountValue.apply(quantity, totalPriceWithoutDiscount, MAX_LIMIT_ROUND,
						convertDiscountValues(order, entry.getDiscountValues()), curr.getIsocode());
				entry.setDiscountValues(appliedDiscounts);

				for (final Iterator it = appliedDiscounts.iterator(); it.hasNext();)
				{
					totalPrice -= ((DiscountValue) it.next()).getAppliedValue();
				}
			}
			BigDecimal roundedTotalPrice = BigDecimal.valueOf(totalPrice);
			roundedTotalPrice = roundedTotalPrice.setScale(digits, BigDecimal.ROUND_HALF_UP);

			entry.setTotalPrice(roundedTotalPrice.doubleValue());
			// apply tax values too
			//YTODO : use CalculatinService methods to apply taxes
			calculateTotalTaxValues(entry);
			entry.setCalculated(Boolean.TRUE);
			getModelService().save(entry);
			}
			LOG.error("Inside calculateTotals "+entry.getBasePrice()+ "total price " +entry.getTotalPrice());
		}
	}

	@Override
	protected Map<TaxValue, Map<Set<TaxValue>, Double>> calculateSubtotal(final AbstractOrderModel order,
			final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			double subtotal = 0.0;
			// entry grouping via map { tax code -> Double }
			final List<AbstractOrderEntryModel> entries = order.getEntries();
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap = new LinkedHashMap<TaxValue, Map<Set<TaxValue>, Double>>(
					entries.size() * 2);

			for (final AbstractOrderEntryModel entry : entries)
			{
				calculateTotals(entry, recalculate);
				if(null != entry) {
					final double bigBagTotal = (entry.getBigBagInfo() != null 
					        && Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()) 
					        && entry.getBigBagInfo().getLocalTotal() != null) 
					    ? entry.getBigBagInfo().getLocalTotal().doubleValue() 
					    : 0.00;
   				final double entryTotal = entry.getTotalPrice().doubleValue();
   				subtotal += entryTotal + bigBagTotal;
   				// use un-applied version of tax values!!!
   				final Collection<TaxValue> allTaxValues = entry.getTaxValues();
   				final Set<TaxValue> relativeTaxGroupKey = this.getUnappliedRelativeTaxValues(allTaxValues);
   				for (final TaxValue taxValue : allTaxValues)
   				{
   					if (taxValue.isAbsolute())
   					{
   						addAbsoluteEntryTaxValue(entry.getQuantity().longValue(), taxValue.unapply(), taxValueMap);
   					}
   					else
   					{
   						addRelativeEntryTaxValue(entryTotal, taxValue.unapply(), relativeTaxGroupKey, taxValueMap);
   					}
   				}
				}
			}
			// store subtotal
			subtotal = commonI18NService.roundCurrency(subtotal, order.getCurrency().getDigits().intValue());
			order.setSubtotal(Double.valueOf(subtotal));
			return taxValueMap;
		}
		return Collections.emptyMap();
	}

	@Override
	protected Set<TaxValue> getUnappliedRelativeTaxValues(final Collection<TaxValue> allTaxValues)
	{
		if (CollectionUtils.isNotEmpty(allTaxValues))
		{
			final Set<TaxValue> ret = new LinkedHashSet<TaxValue>(allTaxValues.size());
			for (final TaxValue appliedTv : allTaxValues)
			{
				if (!appliedTv.isAbsolute())
				{
					ret.add(appliedTv.unapply());
				}
			}
			return ret;
		}
		else
		{
			return Collections.emptySet();
		}
	}

	/**
	 * calculates all totals. this does not trigger price, tax and discount calculation but takes all currently set
	 * price, tax and discount values as base. this method requires the correct subtotal to be set before and the correct
	 * tax value map.
	 *
	 * @param recalculate
	 *           if false calculation is done only if the calculated flag is not set
	 * @param taxValueMap
	 *           the map { tax value -> Double( sum of all entry totals for this tax ) } obtainable via
	 *           {@link #calculateSubtotal(AbstractOrderModel, boolean)}
	 * @throws CalculationException
	 */
	@Override
	protected void calculateTotals(final AbstractOrderModel order, final boolean recalculate,
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap) throws CalculationException
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			// subtotal
			final double subtotal = order.getSubtotal().doubleValue();
			// discounts

			//Freight
			if (null != order.getFreight())
			{
				final Double freight = Double.valueOf(Double.parseDouble(order.getFreight()));
				order.setDeliveryCost(freight);
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXED_CART_ENABLED,
					((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
			{
				Double freight = 0.00;
				if (null != order.getDeliveryFreight())
				{
					freight = Double.parseDouble(order.getDeliveryFreight());
					order.setDeliveryCost(freight);
				}
				if (null != order.getShippingFreight())
				{
					freight = freight + (Double.parseDouble(order.getShippingFreight()));
					order.setDeliveryCost(freight);

				}
			}
			//Total Tax

			final double totalDiscounts = calculateDiscountValues(order, recalculate);
			final double roundedTotalDiscounts = commonI18NService.roundCurrency(totalDiscounts, digits);
			order.setTotalDiscounts(Double.valueOf(roundedTotalDiscounts));
			// set total
			final double total = subtotal + order.getPaymentCost().doubleValue() + order.getDeliveryCost().doubleValue()
					+ order.getTotalTax().doubleValue() - roundedTotalDiscounts;
			final double totalRounded = commonI18NService.roundCurrency(total, digits);
			order.setTotalPrice(Double.valueOf(totalRounded));
									

			LOG.error("OrderCode=" + order.getCode()
			    + ", Subtotal=" + subtotal
			    + ", Order Tax=" + order.getTotalTax()
			    + ", Order DeliveryCost=" + order.getDeliveryCost()
			    + ", Order PaymentCost=" + order.getPaymentCost()
			    + ", Order Discounts=" + order.getTotalDiscounts()			    
			    + ", Order Total=" + order.getTotalPrice());

			if (order.getTotalPrice() != null && order.getTotalPrice() <= 0) 
			{
			  LOG.error("OrderCode=" + order.getCode()
			      + " computed incorrect total:" + order.getTotalPrice());
			}
						
			
			setCalculatedStatus(order);
		}
	}

	@Override
	protected void setCalculatedStatus(final AbstractOrderModel order)
	{
		order.setCalculated(Boolean.TRUE);
		order.setIsCartSizeExceeds(Boolean.FALSE);
		getModelService().save(order);
		final List<AbstractOrderEntryModel> entries = order.getEntries();
		if (entries != null)
		{
			for (final AbstractOrderEntryModel entry : entries)
			{
				entry.setCalculated(Boolean.TRUE);
			}
			getModelService().saveAll(entries);
		}
	}

	@Override
	protected double getTaxCorrectionFactor(final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap, final double subtotal,
			final double total, final AbstractOrderModel order) throws CalculationException
	{
		// default: adjust taxes relative to total-subtotal ratio
		double factor = Math.abs(subtotal) != 0.0f ? (total / subtotal) : 1.0;

		if (mustHandleTaxFreeEntries(taxValueMap, subtotal, order))
		{
			final double taxFreeSubTotal = getTaxFreeSubTotal(order);

			final double taxedTotal = total - taxFreeSubTotal;
			final double taxedSubTotal = subtotal - taxFreeSubTotal;

			// illegal state: taxed subtotal is <= 0 -> cannot calculate with that
			if (taxedSubTotal <= 0)
			{
				throw new CalculationException("illegal taxed subtotal " + taxedSubTotal + ", must be > 0");
			}
			// illegal state: taxed total is <= 0 -> no sense in having negative taxes (factor would become negative!)
			if (taxedTotal <= 0)
			{
				throw new CalculationException("illegal taxed total " + taxedTotal + ", must be > 0");
			}
			factor = Math.abs(taxedSubTotal) != 0.0f ? (taxedTotal / taxedSubTotal) : 1.0;
		}
		return factor;
	}

	@Override
	protected double getTaxFreeSubTotal(final AbstractOrderModel order)
	{
		double sum = 0;
		for (final AbstractOrderEntryModel e : order.getEntries())
		{
			if (CollectionUtils.isEmpty(e.getTaxValues()))
			{
				sum += e.getTotalPrice().doubleValue();
			}
		}
		return sum;
	}


	@Override
	protected boolean mustHandleTaxFreeEntries(final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap, final double subtotal,
			final AbstractOrderModel order)
	{
		return MapUtils.isNotEmpty(taxValueMap) // got taxes at all
				&& taxFreeEntrySupport // mode is enabled
				&& !isAllEntriesTaxed(taxValueMap, subtotal, order); // check sums whether some entries are contributing to tax map
	}

	@Override
	protected boolean isAllEntriesTaxed(final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap, final double subtotal,
			final AbstractOrderModel order)
	{
		double sum = 0.0;
		final Set<Set<TaxValue>> consumedTaxGroups = new HashSet<Set<TaxValue>>();
		for (final Map.Entry<TaxValue, Map<Set<TaxValue>, Double>> taxEntry : taxValueMap.entrySet())
		{
			for (final Map.Entry<Set<TaxValue>, Double> taxGroupEntry : taxEntry.getValue().entrySet())
			{
				if (consumedTaxGroups.add(taxGroupEntry.getKey())) // avoid duplicate occurrences of the same tax group
				{
					sum += taxGroupEntry.getValue().doubleValue();
				}
			}
		}
		// delta ( 2 digits ) == 10 ^-3 == 0.001
		final double allowedDelta = Math.pow(10, -1 * (order.getCurrency().getDigits().intValue() + 1));
		return Math.abs(subtotal - sum) <= allowedDelta;
	}

	@Override
	public void calculate(final AbstractOrderModel order, final CommerceCartParameter parameter)
			throws CalculationException, ResourceAccessException
	{
		if (orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			// -----------------------------
			// first calc all entries
			calculateEntries(order, false, parameter);
			// -----------------------------
			// reset own values
			final Map taxValueMap = resetAllValues(order);
			// -----------------------------
			// now calculate all totals
			calculateTotals(order, false, taxValueMap);
			// notify manual discouns - needed?
			//notifyDiscountsAboutCalculation();
		}
	}

	@Override
	public void recalculate(final AbstractOrderModel order, final CommerceCartParameter parameter)
			throws CalculationException, ResourceAccessException
	{
		// YTODO Auto-generated method stub
		// -----------------------------
		// first force calc entries
		calculateEntries(order, true, parameter);
		// -----------------------------
		// reset own values
		final Map taxValueMap = resetAllValues(order);
		// -----------------------------
		// now recalculate totals
		calculateTotals(order, true, taxValueMap);
		// notify discounts -- needed?
		//			notifyDiscountsAboutCalculation();


	}

	@Override
	protected void recalculateOrderEntryIfNeeded(final AbstractOrderEntryModel entry, final boolean forceRecalculation)
			throws CalculationException
	{
		if (forceRecalculation || orderRequiresCalculationStrategy.requiresCalculation(entry))
		{
			resetAllValues(entry);
			calculateTotals(entry, true);
		}
	}


	@Override
	public boolean requiresCalculation(final AbstractOrderModel order)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("order", order);
		return orderRequiresCalculationStrategy.requiresCalculation(order);
	}



	/**
	 * Returns the total discount for this abstract order.
	 *
	 * @param recalculate
	 *           <code>true</code> forces a recalculation
	 * @return totalDiscounts
	 */
	@Override
	protected double calculateDiscountValues(final AbstractOrderModel order, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final List<DiscountValue> discountValues = order.getGlobalDiscountValues();
			if (discountValues != null && !discountValues.isEmpty())
			{
				// clean discount value list -- do we still need it?
				//				removeAllGlobalDiscountValues();
				//
				final CurrencyModel curr = order.getCurrency();
				final String iso = curr.getIsocode();

				final int digits = curr.getDigits().intValue();
				final double discountablePrice = order.getSubtotal().doubleValue()
						+ (order.isDiscountsIncludeDeliveryCost() ? order.getDeliveryCost().doubleValue() : 0.0)
						+ (order.isDiscountsIncludePaymentCost() ? order.getPaymentCost().doubleValue() : 0.0);
				/*
				 * apply discounts to this order's total
				 */
				final List appliedDiscounts = DiscountValue.apply(1.0, discountablePrice, digits,
						convertDiscountValues(order, discountValues), iso);
				// store discount values
				order.setGlobalDiscountValues(appliedDiscounts);
				return DiscountValue.sumAppliedValues(appliedDiscounts);
			}
			return 0.0;
		}
		else
		{
			return DiscountValue.sumAppliedValues(order.getGlobalDiscountValues());
		}
	}

	@Override
	public void calculateEntries(final AbstractOrderModel order, final boolean forceRecalculate,
			final CommerceCartParameter parameter) throws CalculationException, ResourceAccessException
	{
		LOG.error("Entering DefaultSiteOneCalculationService calculateEntries");
		double subtotal = 0.0;
		PriceValue pv = null;
		Integer count=0;
		final Map<String, String> cspResponse = new HashMap<>();
		final Map<String, String> retailResponse = new HashMap<>();
		final Map<String, Map<String, String>> cspResponseForStore = new HashMap<String, Map<String, String>>();
		final Map<String, Map<String, String>> retailResponseForStore = new HashMap<String, Map<String, String>>();
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		boolean isMixedcartEnabled = false;
		String storeId = null;
		String branchRetailID=null;
		if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXED_CART_ENABLED,
				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
		{
			isMixedcartEnabled = true;
		}
		if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId())
		&& null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
		{
			branchRetailID=((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getCustomerRetailId();
		}
		final int cartPageSize = Integer.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("CartPageSize"));
		if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) && (!userFacade.isAnonymousUser() || StringUtils.isNotEmpty(branchRetailID))
				&& (order.getEntries().size() <= cartPageSize || !order.getIsCartSizeExceeds()))
		{
			Map<ProductModel, Integer> products = new HashMap<>();
			final Map<String, Map<ProductModel, Integer>> productsWithStore = new HashMap<String, Map<ProductModel, Integer>>();
        LOG.error("Inside calculate entries method "+order.getEntries().size());
			List<PointOfServiceData> sessionNearbyStores = null;
			//			for (final AbstractOrderEntryModel e : order.getEntries())
			//			{
			//				products.put(e.getProduct(), Integer.valueOf(e.getQuantity().intValue()));
			//			}
			if (null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
			{
				storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
			}
			if (CollectionUtils.isNotEmpty(getSessionService().getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES)))
			{
				sessionNearbyStores = (List<PointOfServiceData>) getSessionService()
						.getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
			}

			Map<ProductModel,String> productInventoryUOMs=new HashMap<>();
			for (final AbstractOrderEntryModel entry : order.getEntries())
			{
				if (isMixedcartEnabled && entry.getDeliveryPointOfService() != null)
				{
					storeId = entry.getDeliveryPointOfService().getStoreId();
				}
				int invMulti=1;
				if(entry.getInventoryUOM()!=null) {
					invMulti = entry.getInventoryUOM().getInventoryMultiplier().intValue();
               productInventoryUOMs.put(entry.getProduct(),entry.getInventoryUOM().getCode());
				}
				if (productsWithStore.containsKey(storeId))
				{
					products = productsWithStore.get(storeId);
					products.put(entry.getProduct(), Integer.valueOf(entry.getQuantity().intValue() * invMulti));
					productsWithStore.put(storeId, products);
				}
				else
				{
					final Map<ProductModel, Integer> newproduct = new HashMap<>();
					newproduct.put(entry.getProduct(), Integer.valueOf(entry.getQuantity().intValue() * invMulti));
					productsWithStore.put(storeId, newproduct);
				}

			}
			B2BCustomerModel b2bCustomer=null;
			if(!userFacade.isAnonymousUser())
			{
			b2bCustomer = (B2BCustomerModel) getB2bCustomerService().getCurrentB2BCustomer();
			}

			final String currencyIso = currency.getIsocode();

			try
			{
				for (final Entry<String, Map<ProductModel, Integer>> entry : productsWithStore.entrySet())
				{
					SiteOneWsPriceResponseData cspResponseData = null;
					SiteOneWsPriceResponseData retailResponseData = null;
					if (!entry.getValue().isEmpty() && !userFacade.isAnonymousUser())
					{
						cspResponseData = siteOnePriceWebService.getPrice(entry.getValue(), productInventoryUOMs, entry.getKey(), sessionNearbyStores,
								b2bCustomer, currencyIso,
								Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), 
								siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),null);
					}
					if (!entry.getValue().isEmpty() && StringUtils.isNotEmpty(branchRetailID))
					{
						retailResponseData = siteOnePriceWebService.getPrice(entry.getValue(), productInventoryUOMs, entry.getKey(), sessionNearbyStores,
								null, currencyIso,
								Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), 
								siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),branchRetailID);
					}
					if (isMixedcartEnabled)
					{
						storeId = entry.getKey();
					}
			 //  LOG.error("Inside calculateEntries customer details " +b2bCustomer.getEmail()+ " order " + order.getCode());
					if (null != cspResponseData && CollectionUtils.isNotEmpty(cspResponseData.getPrices()))
					{
						cspResponseData.getPrices().forEach(resp -> {
							cspResponse.put(resp.getSkuId(), resp.getPrice());
							cspResponseForStore.put(entry.getKey(), cspResponse);
						});
					}
					else
					{
						LOG.error("CSP response : failed for cart " + order.getCode() + " Customer "+storeId);
					}
					if (null != retailResponseData && CollectionUtils.isNotEmpty(retailResponseData.getPrices()))
					{
						retailResponseData.getPrices().forEach(resp -> {
							retailResponse.put(resp.getSkuId(), resp.getPrice());
							retailResponseForStore.put(entry.getKey(), retailResponse);
							
						});
					}
					else
					{
						LOG.error("Retail response failed for cart");
					}
				}

			}
			catch (final ResourceAccessException ex)
			{
				LOG.error("Unable to get customer specific price", ex);
				siteOneCommerceCartEntryRemovalStrategy.removeCartEntry(order);
				throw ex;
			}

		}

			
			for (final AbstractOrderEntryModel entry : order.getEntries()) {
				if (entry.getEntryNumber() != count)
				{
					entry.setEntryNumber(count);
					getModelService().save(entry);
				}
				count++;		
			final AbstractOrderEntry entryItem = getModelService().getSource(entry);
			if (isMixedcartEnabled && entry.getDeliveryPointOfService() != null)
			{
				storeId = entry.getDeliveryPointOfService().getStoreId();
			}
			final Map<String, String> cspResponseData = cspResponseForStore.get(storeId);
			final Map<String,String> retailResponseData = retailResponseForStore.get(storeId);
			if(null != retailResponseData && StringUtils.isNotEmpty(branchRetailID))
			{
				BigDecimal price = BigDecimal.ZERO;
				if (null != retailResponseData.get(entry.getProduct().getCode()))
				{
					price = new BigDecimal(retailResponseData.get(entry.getProduct().getCode()));
				}
				if (price.compareTo(BigDecimal.ZERO) != 0)
				{
					final PriceData retailPriceData = getPriceDataFactory().create(PriceDataType.BUY, price,
							getCommonI18NService().getCurrentCurrency().getIsocode());
					pv = new PriceValue(retailPriceData.getCurrencyIso(), retailPriceData.getValue().doubleValue(), false);
					entry.setListPrice(pv.getValue());
				}
			}
			if (null != cspResponseData)
			{
				entry.setIsCustomerPrice(Boolean.TRUE);
				try
				{
					if(StringUtils.isEmpty(branchRetailID))
					{
					entry.setListPrice(OrderManager.getInstance().getPriceFactory().getBasePrice(entryItem).getValue());
					}
				}
				catch (final JaloPriceFactoryException e)
				{
					throw new CalculationException(e);
				}
				BigDecimal price = BigDecimal.ZERO;
				if (null != cspResponseData.get(entry.getProduct().getCode()))
				{
					price = new BigDecimal(cspResponseData.get(entry.getProduct().getCode()));
				}
				if (price.compareTo(BigDecimal.ZERO) != 0)
				{
					final PriceData cspPriceData = getPriceDataFactory().create(PriceDataType.BUY, price,
							getCommonI18NService().getCurrentCurrency().getIsocode());
					pv = new PriceValue(cspPriceData.getCurrencyIso(), cspPriceData.getValue().doubleValue(), false);
				}
				else
				{
					if(StringUtils.isEmpty(branchRetailID))
					{
					pv = findBasePrice(entry);
					}
				}
			}
			else
			{
				if(StringUtils.isEmpty(branchRetailID))
				{
				pv = findBasePrice(entry);
				entry.setIsCustomerPrice(Boolean.FALSE);
				try
				{
					entry.setListPrice(OrderManager.getInstance().getPriceFactory().getBasePrice(entryItem).getValue());
				}
				catch (final JaloPriceFactoryException e)
				{
					throw new CalculationException(e);
				}
				}
			}

			// taxes
			if(null != pv)
			{		
			final Collection<TaxValue> entryTaxes = findTaxValues(entry);
			entry.setTaxValues(entryTaxes);
						
			final PriceValue basePrice = this.convertPriceIfNecessary(pv, order.getNet().booleanValue(), order.getCurrency(),
					entryTaxes);
		//	final int digits = currency.getDigits().intValue();

			if (null != parameter.getProduct() && entry.getProduct().getCode().equalsIgnoreCase(parameter.getProduct().getCode()))
			{
				final InventoryUPCModel inventoryUOM = parameter.getInventoryUOM();
				if (null != inventoryUOM)
				{
					entry.setInventoryUOM(inventoryUOM);

					entry.setIsBaseUom(parameter.getIsBaseUom());

					if (entry.getIsBaseUom())
					{
						entry.setBasePrice(Double.valueOf(basePrice.getValue()));
					}
					else
					{
						final double inventoryBasePrice = commonI18NService.roundCurrency(
								basePrice.getValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
								CURRENCY_UNIT_PRICE_DIGITS);
						entry.setBasePrice(Double.valueOf(inventoryBasePrice));
						final double inventoryListPrice = commonI18NService.roundCurrency(
								entry.getListPrice().doubleValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
								CURRENCY_UNIT_PRICE_DIGITS);
						entry.setUomPrice(inventoryListPrice);

					}

				}
				else
				{
					entry.setBasePrice(Double.valueOf(basePrice.getValue()));
				}
			}
			else
			{
				if (null != entry.getInventoryUOM())
				{
					if (entry.getIsBaseUom())
					{
						entry.setBasePrice(Double.valueOf(basePrice.getValue()));
					}
					else
					{
						final double inventoryBasePrice = commonI18NService.roundCurrency(
								basePrice.getValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
								CURRENCY_UNIT_PRICE_DIGITS);
						entry.setBasePrice(Double.valueOf(inventoryBasePrice));
						final double inventoryListPrice = commonI18NService.roundCurrency(
								entry.getListPrice().doubleValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
								CURRENCY_UNIT_PRICE_DIGITS);
						entry.setUomPrice(inventoryListPrice);
					}
				}
				else
				{
					entry.setBasePrice(Double.valueOf(basePrice.getValue()));
				}
			}
			recalculateOrderEntryIfNeeded(entry, forceRecalculate);
			if (null != entry.getProduct() && null != entry.getProduct().getUpcData() && null == entry.getInventoryUOM()) {
				getModelService().remove(entry);				
			}else {
   			subtotal += entry.getTotalPrice().doubleValue();
   			order.setTotalPrice(Double.valueOf(subtotal));
			}
			}
		}
		getModelService().refresh(order);
	}


	@Override
	public void recalculate(final AbstractOrderEntryModel entry) throws CalculationException
	{
		recalculateOrderEntryIfNeeded(entry, true);
	}


	/**
	 * @param recalculate
	 * @param digits
	 * @param taxAdjustmentFactor
	 * @param taxValueMap
	 * @return total taxes
	 */
	@Override
	protected double calculateTotalTaxValues(final AbstractOrderModel order, final boolean recalculate, final int digits,
			final double taxAdjustmentFactor, final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final CurrencyModel curr = order.getCurrency();
			final String iso = curr.getIsocode();
			//Do we still need it?
			//removeAllTotalTaxValues();
			final boolean net = order.getNet().booleanValue();
			double totalTaxes = 0.0;
			// compute absolute taxes if necessary
			if (MapUtils.isNotEmpty(taxValueMap))
			{
				// adjust total taxes if additional costs or discounts are present
				//	create tax values which contains applied values too
				final Collection orderTaxValues = new ArrayList<TaxValue>(taxValueMap.size());
				for (final Map.Entry<TaxValue, Map<Set<TaxValue>, Double>> taxValueEntry : taxValueMap.entrySet())
				{
					// e.g. VAT_FULL 19%
					final TaxValue unappliedTaxValue = taxValueEntry.getKey();
					// e.g. { (VAT_FULL 19%, CUSTOM 2%) -> 10EUR, (VAT_FULL) -> 20EUR }
					// or, in case of absolute taxes one single entry
					// { (ABS_1 4,50EUR) -> 2 (pieces) }
					final Map<Set<TaxValue>, Double> taxGroups = taxValueEntry.getValue();

					final TaxValue appliedTaxValue;

					if (unappliedTaxValue.isAbsolute())
					{
						// absolute tax entries ALWAYS map to a single-entry map -> we'll use a shortcut here:
						final double quantitySum = taxGroups.entrySet().iterator().next().getValue().doubleValue();
						appliedTaxValue = calculateAbsoluteTotalTaxValue(curr, iso, digits, net, unappliedTaxValue, quantitySum);
					}
					else if (net)
					{
						appliedTaxValue = applyNetMixedRate(unappliedTaxValue, taxGroups, digits, taxAdjustmentFactor);
					}
					else
					{
						appliedTaxValue = applyGrossMixedRate(unappliedTaxValue, taxGroups, digits, taxAdjustmentFactor);
					}
					totalTaxes += appliedTaxValue.getAppliedValue();
					orderTaxValues.add(appliedTaxValue);
				}
				order.setTotalTaxValues(orderTaxValues);
			}
			return totalTaxes;
		}
		else
		{
			return order.getTotalTax().doubleValue();
		}
	}

	@Override
	protected TaxValue applyNetMixedRate(final TaxValue unappliedTaxValue, final Map<Set<TaxValue>, Double> taxGroups,
			final int digits, final double taxAdjustmentFactor)
	{
		if (unappliedTaxValue.isAbsolute())
		{
			throw new IllegalStateException("cannot applyGrossMixedRate(..) cannot be called on absolute tax value!");
		}

		// In NET mode we don't care for tax groups since they're only needed to calculated *incldued* taxes!
		// Here we just sum up all group totals...
		double entriesTotalPrice = 0.0;
		for (final Map.Entry<Set<TaxValue>, Double> taxGroupEntry : taxGroups.entrySet())
		{
			entriesTotalPrice += taxGroupEntry.getValue().doubleValue();
		}
		// and apply them in one go:
		return unappliedTaxValue.apply(1.0, entriesTotalPrice * taxAdjustmentFactor, digits, true, null);
	}

	@Override
	protected TaxValue applyGrossMixedRate(final TaxValue unappliedTaxValue, final Map<Set<TaxValue>, Double> taxGroups,
			final int digits, final double taxAdjustmentFactor)
	{
		if (unappliedTaxValue.isAbsolute())
		{
			throw new IllegalStateException("AbstractOrder.applyGrossMixedRate(..) cannot be called for absolute tax value!");
		}
		final double singleTaxRate = unappliedTaxValue.getValue();
		double appliedTaxValueNotRounded = 0.0;
		for (final Map.Entry<Set<TaxValue>, Double> taxGroupEntry : taxGroups.entrySet())
		{
			final double groupTaxesRate = TaxValue.sumRelativeTaxValues(taxGroupEntry.getKey());
			final double taxGroupPrice = taxGroupEntry.getValue().doubleValue();

			appliedTaxValueNotRounded += (taxGroupPrice * singleTaxRate) / (100.0 + groupTaxesRate);
		}

		//adjust taxes according to global discounts / costs
		appliedTaxValueNotRounded = appliedTaxValueNotRounded * taxAdjustmentFactor;

		return new TaxValue(//
				unappliedTaxValue.getCode(), //
				unappliedTaxValue.getValue(), //
				false, //
				// always round (even if digits are 0) since relative taxes result in unwanted precision !!!
				commonI18NService.roundCurrency(appliedTaxValueNotRounded, Math.max(digits, 0)), //
				null //
		);
	}

	@Override
	public ProductData getProductByUOM(final AbstractOrderEntryModel entry, final float inventoryMultiplier)
	{
		final ProductModel productModel = productService.getProductForCode(entry.getProduct().getCode());
		final ProductData productData = new ProductData();
		Double listPrice = 0.0;
		String branchRetailID=null;
		if (null != getSessionService().getAttribute("sessionStore") && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId())
		&& null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getCustomerRetailId())
		{
			branchRetailID=((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getCustomerRetailId();
		}
		if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXED_CART_ENABLED,
				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
		{
			getProductConfiguredPopulator().populate(productModel, productData, Arrays.asList(ProductOption.STOCK));
			if (entry.getDeliveryPointOfService() != null)
			{
				productData.getStock().setFullfillmentStoreId(entry.getDeliveryPointOfService().getStoreId());
				try
				{
				   if(!StringUtils.isNotBlank(branchRetailID))
				   {
					listPrice = findBasePrice(entry).getValue();
				   }
				}
				catch (final CalculationException calculationException)
				{
					throw new IllegalStateException("Cart model " + entry.getOrder().getCode() + " was not calculated due to: "
							+ calculationException.getMessage(), calculationException);
				}
			}
		}
		List<ProductOption> options = new ArrayList<>();
		if(!StringUtils.isNotBlank(branchRetailID))
		{
		options.add(ProductOption.PRICE);
		}
		if (null != entry.getBasePrice())
		{
			double basePrice = 0.00;
			final DecimalFormat decimalFormat = new DecimalFormat("#.###");
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			if (null != entry.getInventoryUOM())
			{
				if (null != entry.getInventoryUOM().getInventoryMultiplier())
				{
					basePrice = entry.getBasePrice() / entry.getInventoryUOM().getInventoryMultiplier().floatValue();
				}
				else
				{
					basePrice = entry.getBasePrice();
				}
			}
			else
			{
				basePrice = entry.getBasePrice();
			}
			final PriceData customerPrice = createPrice(BigDecimal.valueOf(basePrice), entry.getOrder().getCurrency());
			final String roundedPrice = decimalFormat.format(customerPrice.getValue());
			customerPrice.setValue(new BigDecimal(roundedPrice));
			productData.setCustomerPrice(customerPrice);
			
			if(StringUtils.isNotBlank(branchRetailID) && entry.getListPrice() !=null)
			{
			listPrice=entry.getListPrice();
			}
		}
		else
		{
			if(StringUtils.isNotBlank(branchRetailID) && entry.getListPrice()!=null)
			{
				options.add(ProductOption.CUSTOMER_PRICE);
				listPrice=entry.getListPrice();
			}
			else
			{
			options = Arrays.asList(ProductOption.PRICE, ProductOption.CUSTOMER_PRICE);
			}
		}
		if(CollectionUtils.isNotEmpty(options))
		{
		getProductConfiguredPopulator().populate(productModel, productData, options);
		}
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		if (listPrice == 0 && productData.getPrice() != null)
		{
			listPrice = productData.getPrice().getValue().doubleValue();
		}
		if (productData.getCustomerPrice() != null)
		{
			final double uomPrice = productData.getCustomerPrice().getValue().floatValue() * inventoryMultiplier;
			if (listPrice != 0)
			{
				final double listuomPrice = listPrice * inventoryMultiplier;
				if (null != productData.getPrice())
				{
					productData.getPrice().setValue(BigDecimal.valueOf(listuomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP));
				}
				else
				{
					final PriceData priceData = new PriceData();
					priceData.setValue(BigDecimal.valueOf(listuomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP));
					productData.setPrice(priceData);
				}
				productData.getCustomerPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP));
			}
			else
			{
				final PriceData priceData = new PriceData();
				priceData.setValue(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP));
				productData.setPrice(priceData);
				productData.getCustomerPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP));
			}
		}
		else if (listPrice != 0)
		{
			final double uomPrice = listPrice * inventoryMultiplier;
			productData.getPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_PERUNIT_PRICE_DIGITS, RoundingMode.HALF_UP));
		}
		final List<ProductOption> promotionOptions = Arrays.asList(ProductOption.PROMOTIONS);
		getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);

		return productData;
	}

	/**
	 * @param entry
	 * @param inventoryMultiplier
	 * @return
	 */
	private PriceData getCustomerPriceOfAddedProduct(AbstractOrderEntryModel entry, float inventoryMultiplier)
	{
		String storeId = null;
		List<PointOfServiceData> sessionNearbyStores = null;
		final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getB2bCustomerService().getCurrentB2BCustomer();
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		final String currencyIso = currency.getIsocode();
		BigDecimal price = BigDecimal.ZERO;
		if (null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
		{
			storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
		}
		if (CollectionUtils.isNotEmpty(getSessionService().getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES)))
		{
			sessionNearbyStores = (List<PointOfServiceData>) getSessionService()
					.getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
		}
		Map<ProductModel,String> productInventoryUOMs=new HashMap<>();
		Map<ProductModel, Integer> products = new HashMap<>();
		products.put(entry.getProduct(), Integer.valueOf((int) (entry.getQuantity().intValue() * inventoryMultiplier)));
		productInventoryUOMs.put(entry.getProduct(),entry.getInventoryUOM().getCode());
		try
		{
			SiteOneWsPriceResponseData cspResponseData = siteOnePriceWebService.getPrice(products, productInventoryUOMs, storeId, sessionNearbyStores,
					b2bCustomer, currencyIso,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), 
					siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),null);
			if (null != cspResponseData && CollectionUtils.isNotEmpty(cspResponseData.getPrices()))
			{
				price = new BigDecimal(cspResponseData.getPrices().get(0).getPrice());
			}
		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to get customer specific price", ex);					
		}

		final PriceData cspPriceData = getPriceDataFactory().create(PriceDataType.BUY, price,
				getCommonI18NService().getCurrentCurrency().getIsocode());
		return cspPriceData;
	}

	protected PriceData createPrice(final BigDecimal val, final CurrencyModel currencyIso)
	{
		return priceDataFactory.create(PriceDataType.BUY, val, currencyIso);
	}

	protected ConfigurablePopulator<ProductModel, ProductData, ProductOption> getProductConfiguredPopulator()
	{
		return productConfiguredPopulator;
	}

	public void setProductConfiguredPopulator(
			final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

	/**
	 * @return the priceDataFactory
	 */
	public PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	/**
	 * @param priceDataFactory
	 *           the priceDataFactory to set
	 */
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	@Override
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the userFacade
	 */
	public UserFacade getUserFacade()
	{
		return userFacade;
	}

	/**
	 * @param userFacade
	 *           the userFacade to set
	 */
	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}

	/**
	 * @return the siteOneProductUOMService
	 */
	public SiteOneProductUOMService getSiteOneProductUOMService()
	{
		return siteOneProductUOMService;
	}

	/**
	 * @param siteOneProductUOMService
	 *           the siteOneProductUOMService to set
	 */
	public void setSiteOneProductUOMService(final SiteOneProductUOMService siteOneProductUOMService)
	{
		this.siteOneProductUOMService = siteOneProductUOMService;
	}

	/**
	 * @return the siteOnePriceWebService
	 */
	public SiteOnePriceWebService getSiteOnePriceWebService()
	{
		return siteOnePriceWebService;
	}

	/**
	 * @param siteOnePriceWebService
	 *           the siteOnePriceWebService to set
	 */
	public void setSiteOnePriceWebService(final SiteOnePriceWebService siteOnePriceWebService)
	{
		this.siteOnePriceWebService = siteOnePriceWebService;
	}

	public ProductService getProductService()
	{
		return productService;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}


	/**
	 * @return the orderRequiresCalculationStrategy
	 */
	public OrderRequiresCalculationStrategy getOrderRequiresCalculationStrategy()
	{
		return orderRequiresCalculationStrategy;
	}

	/**
	 * @param orderRequiresCalculationStrategy
	 *           the orderRequiresCalculationStrategy to set
	 */
	@Override
	public void setOrderRequiresCalculationStrategy(final OrderRequiresCalculationStrategy orderRequiresCalculationStrategy)
	{
		this.orderRequiresCalculationStrategy = orderRequiresCalculationStrategy;
	}

	/**
	 * @return the b2bCustomerService
	 */
	public B2BCustomerService getB2bCustomerService()
	{
		return b2bCustomerService;
	}

	/**
	 * @param b2bCustomerService
	 *           the b2bCustomerService to set
	 */
	public void setB2bCustomerService(final B2BCustomerService b2bCustomerService)
	{
		this.b2bCustomerService = b2bCustomerService;
	}

	public SiteOneCommerceCartEntryRemovalStrategy getSiteOneCommerceCartEntryRemovalStrategy()
	{
		return siteOneCommerceCartEntryRemovalStrategy;
	}

	public void setSiteOneCommerceCartEntryRemovalStrategy(
			final SiteOneCommerceCartEntryRemovalStrategy siteOneCommerceCartEntryRemovalStrategy)
	{
		this.siteOneCommerceCartEntryRemovalStrategy = siteOneCommerceCartEntryRemovalStrategy;
	}

	/**
	 * @return the modelService
	 */
	@Override
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	public SiteOneCartCspPriceData fetchCartCSPPrice(final AbstractOrderModel order, final String sku) throws CalculationException
	{
		LOG.info("Entering inside CartCSPPrice");
		final List<String> productCodes = new ArrayList<>();
		double subtotal = 0.0;
		if (StringUtils.isNotBlank(sku))
		{
			final String[] products = sku.split(",");
			final List<String> list = Arrays.asList(products);
			productCodes.addAll(list);
		}
		final SiteOneCartCspPriceData cspData = new SiteOneCartCspPriceData();
		SiteOneCartCSPProductData productData = new SiteOneCartCSPProductData();
		final List<SiteOneCartCSPProductData> productsData = new ArrayList<SiteOneCartCSPProductData>();
		PriceValue pv=null;
		final Map<String, String> cspResponse = new HashMap<>();
		final Map<String, Map<String, String>> cspResponseForStore = new HashMap<String, Map<String, String>>();
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		String storeId = null;
		B2BCustomerModel b2bCustomer=null;
		String branchRetailID=null;
		final Map<String, Map<String, String>> retailResponseForStore = new HashMap<String, Map<String, String>>();
		final Map<String, String> retailResponse = new HashMap<>();
		final Map<String, Map<ProductModel, Integer>> productsWithStore = new HashMap<String, Map<ProductModel, Integer>>();
		List<PointOfServiceData> sessionNearbyStores = null;
		if (null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
		{
			storeId = ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId();
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
					((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
			{
				branchRetailID=((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getCustomerRetailId();
			}
		}
		if (CollectionUtils.isNotEmpty(getSessionService().getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES)))
		{
			sessionNearbyStores = (List<PointOfServiceData>) getSessionService()
					.getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
		}

		Map<ProductModel,String> productInventoryUOMs=new HashMap<>();
		for (final String productCode : productCodes)
		{
			if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) && !userFacade.isAnonymousUser() || StringUtils.isNotEmpty(branchRetailID))
			{
				Map<ProductModel, Integer> products = new HashMap<>();
				
				for (final AbstractOrderEntryModel entry : order.getEntries())
				{
					if (productCode.equalsIgnoreCase(entry.getProduct().getCode()))
					{
						int invMulti=1;
						if(entry.getInventoryUOM()!=null) {
							invMulti = entry.getInventoryUOM().getInventoryMultiplier().intValue();
						}
						if (productsWithStore.containsKey(storeId))
						{
							products = productsWithStore.get(storeId);
							products.put(entry.getProduct(), Integer.valueOf(entry.getQuantity().intValue() * invMulti));
							productsWithStore.put(storeId, products);
						}
						else
						{
							final Map<ProductModel, Integer> newproduct = new HashMap<>();
							newproduct.put(entry.getProduct(), Integer.valueOf(entry.getQuantity().intValue() * invMulti));
							productsWithStore.put(storeId, newproduct);
						}
					}

					productInventoryUOMs.put(entry.getProduct(), entry.getInventoryUOM().getCode());

				}
			}
		}
		if(!userFacade.isAnonymousUser())
		{
		b2bCustomer = (B2BCustomerModel) getB2bCustomerService().getCurrentB2BCustomer();
		}
		final String currencyIso = currency.getIsocode();

		try
		{
			for (final Entry<String, Map<ProductModel, Integer>> entry : productsWithStore.entrySet())
			{
				SiteOneWsPriceResponseData cspResponseData = null;
				SiteOneWsPriceResponseData retailResponseData = null;
				if (!entry.getValue().isEmpty() && !userFacade.isAnonymousUser())
				{
					cspResponseData = siteOnePriceWebService.getPrice(entry.getValue(), productInventoryUOMs, entry.getKey(), sessionNearbyStores,
							b2bCustomer, currencyIso,
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), 
							siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),null);
				}
				if (!entry.getValue().isEmpty() && StringUtils.isNotEmpty(branchRetailID))
				{
					retailResponseData = siteOnePriceWebService.getPrice(entry.getValue(), productInventoryUOMs, entry.getKey(), sessionNearbyStores,
							null, currencyIso,
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), 
							siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),branchRetailID);
				}
				final String storeData = storeId;
				LOG.error("Inside Cart Ajax: " + order.getCode() +" Store " + storeId);
				if (null != cspResponseData && CollectionUtils.isNotEmpty(cspResponseData.getPrices()))
				{
					cspResponseData.getPrices().forEach(resp -> {
						cspResponse.put(resp.getSkuId(), resp.getPrice());
						cspResponseForStore.put(entry.getKey(), cspResponse);
					});
				}
				else
				{
					LOG.info("CSP response : failed for cart " + order.getCode() + " Store " + storeId);
				}
				if (null != retailResponseData && CollectionUtils.isNotEmpty(retailResponseData.getPrices()))
				{
					retailResponseData.getPrices().forEach(resp -> {
						retailResponse.put(resp.getSkuId(), resp.getPrice());
						retailResponseForStore.put(entry.getKey(), retailResponse);
						
					});
				}
				else
				{
					LOG.error("Retail response failed for cart");
				}
			}

		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to get customer specific price", ex);
			siteOneCommerceCartEntryRemovalStrategy.removeCartEntry(order);
			throw ex;
		}
		
		for (final String productCode : productCodes)
		{
			productData = new SiteOneCartCSPProductData();
			for (final AbstractOrderEntryModel entry : order.getEntries())
			{
				if (productCode.equalsIgnoreCase(entry.getProduct().getCode()))
				{
					final AbstractOrderEntry entryItem = getModelService().getSource(entry);
					final Map<String, String> cspResponseData = cspResponseForStore.get(storeId);
					final Map<String,String> retailResponseData = retailResponseForStore.get(storeId);
					if(null != retailResponseData && StringUtils.isNotEmpty(branchRetailID))
					{
						BigDecimal price = BigDecimal.ZERO;
						if (null != retailResponseData.get(entry.getProduct().getCode()))
						{
							price = new BigDecimal(retailResponseData.get(entry.getProduct().getCode()));
						}
						if (price.compareTo(BigDecimal.ZERO) != 0)
						{
							final PriceData retailPriceData = getPriceDataFactory().create(PriceDataType.BUY, price,
									getCommonI18NService().getCurrentCurrency().getIsocode());
							pv = new PriceValue(retailPriceData.getCurrencyIso(), retailPriceData.getValue().doubleValue(), false);
							entry.setListPrice(pv.getValue());
						}
					}
					if (null != cspResponseData)
					{
						entry.setIsCustomerPrice(Boolean.TRUE);
						try
						{
							if(StringUtils.isEmpty(branchRetailID))
							{
							entry.setListPrice(OrderManager.getInstance().getPriceFactory().getBasePrice(entryItem).getValue());
							productData.setListPrice(entry.getListPrice());
							}
						}
						catch (final JaloPriceFactoryException e)
						{
							throw new CalculationException(e);
						}
						BigDecimal price = BigDecimal.ZERO;
						if (null != cspResponseData.get(entry.getProduct().getCode()))
						{
							price = new BigDecimal(cspResponseData.get(entry.getProduct().getCode()));
						}
						if (price.compareTo(BigDecimal.ZERO) != 0)
						{
							final PriceData cspPriceData = getPriceDataFactory().create(PriceDataType.BUY, price,
									getCommonI18NService().getCurrentCurrency().getIsocode());
							pv = new PriceValue(cspPriceData.getCurrencyIso(), cspPriceData.getValue().doubleValue(), false);
						}
						else
						{
							if(StringUtils.isEmpty(branchRetailID))
							{
							pv = findBasePrice(entry);
							}
						}
					}
					else
					{
						if(StringUtils.isEmpty(branchRetailID))
						{
						pv = findBasePrice(entry);
						entry.setIsCustomerPrice(Boolean.FALSE);
						try
						{
							entry.setListPrice(OrderManager.getInstance().getPriceFactory().getBasePrice(entryItem).getValue());
							productData.setListPrice(entry.getListPrice());
						}
						catch (final JaloPriceFactoryException e)
						{
							throw new CalculationException(e);
						}
						}
					}

					// taxes
					final Collection<TaxValue> entryTaxes = findTaxValues(entry);
					entry.setTaxValues(entryTaxes);
					final PriceValue basePrice = this.convertPriceIfNecessary(pv, order.getNet().booleanValue(), order.getCurrency(),
							entryTaxes);
					final int digits = currency.getDigits().intValue();
					final CommerceCartParameter parameter = new CommerceCartParameter();
					parameter.setEnableHooks(true);
					parameter.setCart((CartModel) order);
					parameter.setRecalculate(true);
					if (null != parameter.getProduct()
							&& entry.getProduct().getCode().equalsIgnoreCase(parameter.getProduct().getCode()))
					{
						final InventoryUPCModel inventoryUOM = parameter.getInventoryUOM();
						if (null != inventoryUOM)
						{
							entry.setInventoryUOM(inventoryUOM);

							entry.setIsBaseUom(parameter.getIsBaseUom());

							if (entry.getIsBaseUom())
							{
								entry.setBasePrice(Double.valueOf(basePrice.getValue()));
								productData.setItemPrice(entry.getBasePrice());
							}
							else
							{
								final double inventoryBasePrice = commonI18NService.roundCurrency(
										basePrice.getValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
										CURRENCY_UNIT_PRICE_DIGITS);
								entry.setBasePrice(Double.valueOf(inventoryBasePrice));
								productData.setItemPrice(entry.getBasePrice());
								final double inventoryListPrice = commonI18NService.roundCurrency(
										entry.getListPrice().doubleValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
										CURRENCY_UNIT_PRICE_DIGITS);
								entry.setUomPrice(inventoryListPrice);

							}

						}
						else
						{
							entry.setBasePrice(Double.valueOf(basePrice.getValue()));
							productData.setItemPrice(entry.getBasePrice());
						}
					}
					else
					{
						if (null != entry.getInventoryUOM())
						{
							if (entry.getIsBaseUom())
							{
								entry.setBasePrice(Double.valueOf(basePrice.getValue()));
								productData.setItemPrice(entry.getBasePrice());
							}
							else
							{
								final double inventoryBasePrice = commonI18NService.roundCurrency(
										basePrice.getValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
										CURRENCY_UNIT_PRICE_DIGITS);
								entry.setBasePrice(Double.valueOf(inventoryBasePrice));
								productData.setItemPrice(entry.getBasePrice());
								final double inventoryListPrice = commonI18NService.roundCurrency(
										entry.getListPrice().doubleValue() * entry.getInventoryUOM().getInventoryMultiplier().floatValue(),
										CURRENCY_UNIT_PRICE_DIGITS);
								entry.setUomPrice(inventoryListPrice);
							}
						}
						else
						{
							entry.setBasePrice(Double.valueOf(basePrice.getValue()));
							productData.setItemPrice(entry.getBasePrice());
						}
					}

					recalculateOrderEntryIfNeeded(entry, true);
					subtotal += entry.getTotalPrice().doubleValue();
					order.setTotalPrice(Double.valueOf(subtotal));
					productData.setItemTotal(entry.getTotalPrice().doubleValue());
					productData.setProductCode(entry.getProduct().getCode());
					productsData.add(productData);

				}
			}

		}
		final Map taxValueMap = resetAllValues(order);
		// -----------------------------
		// now recalculate totals
		calculateTotals(order, true, taxValueMap);
		cspData.setProducts(productsData);
		cspData.setSubTotal(order.getSubtotal());
		cspData.setTotalPrice(order.getTotalPrice());
		return cspData;
	}

}
