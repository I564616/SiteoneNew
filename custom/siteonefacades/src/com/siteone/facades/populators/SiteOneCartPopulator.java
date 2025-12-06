/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.bag.data.BagInfoData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.converters.populator.CartPopulator;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.DiscountValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.math.MathContext;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.MessageSource;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.BagInfoModel;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facade.ThresholdCheckResponseData;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.customer.price.SiteOneCspResponse;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;

import com.siteone.core.store.services.SiteOneStoreFinderService;

/**
 * @author 1190626
 *
 */
public class SiteOneCartPopulator extends CartPopulator
{

	private static final String SHIPPING_DELIVERY_FEE_BRANCHES = "ShippingandDeliveryFeeBranches";
	private static final String SHIPPING_DELIVERY_LA_FEE_BRANCHES = "ShippingandDeliveryLAFeeBranches";
	private static final String DC_SHIPPING_FEE_BRANCHES = "DCShippingFeeBranches";
	private static final String GUEST_PICKUP_AVAILABLE_BRANCHES = "GuestPickUpAvailableBranches";
	private static final String GUEST_CHECKOUT_ENABLED_BRANCHES = "GuestChekoutEnabledBranches";
	private static final String GUEST_RETAIL_CHECKOUT_DISABLED_BRANCHES = "GuestOrRetailCheckoutDisabledBranches";
   private static final String FREE_SHIPPING_THRESHOLD = "FreeShippingThreshold";
   private static final String DC_SHIPPING_THRESHOLD = "DCShippingThreshold";
   private static final String DELIVERY_COST_THRESHOLD = "DeliveryCostThreshold";
	private static final String PICKUP_NEARBY = "pickupnearby";
	private static final String PICKUP_HOME = "pickuphome";
	private static final String PICKUP = "pickup";
	private static final String DELIVERY = "delivery";
	private static final String SHIPPING = "shipping";
	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";
	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.totalprice.digits", 2);
	private static final int CURRENCY_PERUNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);


	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter;

	@Resource(name = "siteOneB2BUnitBasicPopulator")
	private SiteOneB2BUnitBasicPopulator siteOneB2BUnitBasicPopulator;
	
	@Resource(name = "defaultSiteOneStoreFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteOneGuestUserConverter")
	private Converter<CustomerModel, CustomerData> siteOneGuestUserConverter;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@Resource(name = "addressConverter")
	private Converter<AddressModel, AddressData> addressConverter;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;
	
	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	private SiteOneStoreSessionFacade storeSessionFacade;

	private MessageSource messageSource;

	final int digits = 2;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "deliveryModeService")
	private DeliveryModeService deliveryModeService;
	
	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;
		
	/**
	 * @return the pointOfServiceSiteOneConverter
	 */
	public Converter<PointOfServiceModel, PointOfServiceData> getPointOfServiceSiteOneConverter()
	{
		return pointOfServiceSiteOneConverter;
	}

	/**
	 * @param pointOfServiceSiteOneConverter
	 *           the pointOfServiceSiteOneConverter to set
	 */
	public void setPointOfServiceSiteOneConverter(
			final Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter)
	{
		this.pointOfServiceSiteOneConverter = pointOfServiceSiteOneConverter;
	}

	/**
	 * @return the siteonecustomerConverter
	 */
	public Converter<B2BCustomerModel, CustomerData> getSiteonecustomerConverter()
	{
		return siteonecustomerConverter;
	}

	/**
	 * @param siteonecustomerConverter
	 *           the siteonecustomerConverter to set
	 */
	public void setSiteonecustomerConverter(final Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter)
	{
		this.siteonecustomerConverter = siteonecustomerConverter;
	}

	private Converter<B2BCustomerModel, CustomerData> siteonecustomerConverter;

	public SiteOneStockLevelService getSiteOneStockLevelService()
	{
		return siteOneStockLevelService;
	}

	public void setSiteOneStockLevelService(final SiteOneStockLevelService siteOneStockLevelService)
	{
		this.siteOneStockLevelService = siteOneStockLevelService;
	}

	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	@Override
	public void populate(final CartModel source, final CartData target)
	{
		super.populate(source, target);
		if (null != source.getPointOfService())
		{
			target.setPointOfService(pointOfServiceSiteOneConverter.convert(source.getPointOfService()));
		}
		if (null != source.getModifiedtime())
		{
			target.setOrderDate(source.getModifiedtime());
		}
		if (null != source.getContactPerson())
		{
			target.setContactPerson(siteonecustomerConverter.convert(source.getContactPerson()));
		}
		if (null != source.getDeliveryContactPerson())
		{
			target.setDeliveryContactPerson(siteonecustomerConverter.convert(source.getDeliveryContactPerson()));
		}
		if (null != source.getShippingAddress())
		{
			target.setShippingAddress(addressConverter.convert(source.getShippingAddress()));
		}
		if (null != source.getShippingContactPerson())
		{
			target.setShippingContactPerson(siteonecustomerConverter.convert(source.getShippingContactPerson()));
		}
		if (null != source.getGuestContactPerson())
		{
			target.setGuestContactPerson(siteOneGuestUserConverter.convert(source.getGuestContactPerson()));
		}
		if (null != source.getGuestDeliveryContactPerson())
		{
			target.setGuestDeliveryContactPerson(siteOneGuestUserConverter.convert(source.getGuestDeliveryContactPerson()));
		}
		if (null != source.getGuestShippingContactPerson())
		{
			target.setGuestShippingContactPerson(siteOneGuestUserConverter.convert(source.getGuestShippingContactPerson()));
		}
		if (null != source.getPickupAddress())
		{
			target.setPickupAddress(addressConverter.convert(source.getPickupAddress()));
		}
		if (null != source.getOrderType())
		{
			target.setOrderType(source.getOrderType().getCode());
		}
		if (null != source.getRequestedDate())
		{
			target.setRequestedDate(source.getRequestedDate());
		}
		if (null != source.getSpecialInstruction())
		{
			target.setSpecialInstruction(source.getSpecialInstruction());
		}
		if (null != source.getPickupInstruction())
		{
			target.setPickupInstruction(source.getPickupInstruction());
		}
		if (null != source.getDeliveryInstruction())
		{
			target.setDeliveryInstruction(source.getDeliveryInstruction());
		}
		if (null != source.getShippingInstruction())
		{
			target.setShippingInstruction(source.getShippingInstruction());
		}
		if (null != source.getIsExpedited())
		{
			target.setIsExpedited(source.getIsExpedited());
		}
		if (null != source.getChooseLift())
		{
			target.setChooseLift(source.getChooseLift());
		}
		if (null != source.getOrderingAccount())
		{
			final B2BUnitData orderingAccount = new B2BUnitData();
			siteOneB2BUnitBasicPopulator.populate(source.getOrderingAccount(), orderingAccount);
			target.setOrderingAccount(orderingAccount);
		}
		if (null != source.getRequestedMeridian())
		{
			target.setRequestedMeridian(source.getRequestedMeridian().getCode());
		}
		if (null != source.getRequestedMeridian())
		{
			target.setRequestedMeridian(source.getRequestedMeridian().getCode());
		}
		
		if (CollectionUtils.isNotEmpty(target.getEntries()))
		{
			List<ProductData> productDataList = siteOneProductFacade.updateSalesInfoBackorderForOrderEntry(target.getEntries());
			BigDecimal totalPrice = new BigDecimal(0).setScale(digits, RoundingMode.HALF_UP);
			BigDecimal subtotal = new BigDecimal(0).setScale(digits, RoundingMode.HALF_UP);
			final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
			for (final OrderEntryData orderEntryData : target.getEntries())
			{
				if(CollectionUtils.isNotEmpty(productDataList)) {
					productDataList.forEach(product -> {
						if(product.getCode().equalsIgnoreCase(orderEntryData.getProduct().getCode())) {
							orderEntryData.setProduct(product);
						}
					});
				}
				
				if (orderEntryData.getProduct().getHideUom() != null && !orderEntryData.getProduct().getHideUom())
				{
					totalPrice = totalPrice.add(orderEntryData.getTotalPrice().getValue());
					subtotal = subtotal.add(orderEntryData.getTotalPrice().getValue());
				}
				if (orderEntryData != null && orderEntryData.getProduct() != null && orderEntryData.getProduct().getHideUom() != null
						&& orderEntryData.getProduct().getHideUom())
				{
					totalPrice = totalPrice.add(orderEntryData.getTotalPrice().getValue());
					subtotal = subtotal.add(orderEntryData.getTotalPrice().getValue());
				}
			}
			if ((!target.getAppliedOrderPromotions().isEmpty()) && target.getAppliedProductPromotions().isEmpty())
			{
				target.getSubTotal().setValue(subtotal);

				target.getSubTotal().setFormattedValue(fmt.format(subtotal));
				final BigDecimal total = subtotal.subtract(target.getTotalDiscounts().getValue());
				target.getTotalPrice().setValue(total);
				target.getTotalPrice().setFormattedValue(fmt.format(total));

			}
			else
			{
				final BigDecimal subtotal1 = subtotal.add(target.getTotalDiscounts().getValue());
				target.getSubTotal().setValue(subtotal1);
				target.getSubTotal().setFormattedValue(fmt.format(subtotal1));
				target.getTotalPrice().setValue(subtotal);
				target.getTotalPrice().setFormattedValue(fmt.format(subtotal));

			}
			
			totalPrice = target.getTotalPrice().getValue();

			final BigDecimal totalPriceWithTax = totalPrice.add(BigDecimal.valueOf(source.getTotalTax()));
			BigDecimal lclTotalPriceWithTaxnFreight = new BigDecimal(0);

			if(null != source.getFreight())
			{
				final Double freightCharges = Double.valueOf(source.getFreight());
				BigDecimal roundedFreightCharges = BigDecimal.valueOf(freightCharges);
				roundedFreightCharges = roundedFreightCharges.setScale(digits, BigDecimal.ROUND_HALF_UP);
				final PriceData freightPriceData = createPrice(source, freightCharges);
				target.setFreight(roundedFreightCharges.toString());
				target.setDeliveryCost(freightPriceData);
				lclTotalPriceWithTaxnFreight = totalPriceWithTax.add(BigDecimal.valueOf(freightCharges));			
			}
   		else
   		{
   			lclTotalPriceWithTaxnFreight = totalPriceWithTax;
   		}

			target.getTotalPriceWithTax().setValue(lclTotalPriceWithTaxnFreight);
			target.getTotalPriceWithTax().setFormattedValue(fmt.format(lclTotalPriceWithTaxnFreight));
		}


		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		if (null != sessionStore && null != sessionStore.getStoreId())
		{
			final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
			final Map<String, String> hubStoreShippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);
			if (null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0))
			{
				if (null != shippingFee.get(storeSessionFacade.getSessionStore().getHubStores().get(0)) 
						|| null != hubStoreShippingFee.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
				{
					target.setIsShippingFeeBranch(Boolean.TRUE);
				}		
				else if(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SiteoneCoreConstants.ITEM_LEVEL_SHIPPING_FEE_BRANCHES,
						storeSessionFacade.getSessionStore().getHubStores().get(0))
						&& b2bCheckoutFacade.getIfItemLevelShippingFeeApplicable(target.getEntries()))
				{
					target.setIsShippingFeeBranch(Boolean.TRUE);
					target.setIsItemLevelShippingFeeBranch(Boolean.TRUE);
				}
				else
				{
					target.setIsShippingFeeBranch(Boolean.FALSE);
					target.setIsItemLevelShippingFeeBranch(Boolean.FALSE);
				}
			}
			else
			{
				target.setIsShippingFeeBranch(Boolean.FALSE);
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SHIPPING_DELIVERY_FEE_BRANCHES,
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(target.getIsShippingFeeBranch()))
			{
				target.setIsTampaBranch(Boolean.TRUE);
			}
			else
			{
				target.setIsTampaBranch(Boolean.FALSE);
			}

			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SHIPPING_DELIVERY_LA_FEE_BRANCHES,
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(target.getIsShippingFeeBranch()))
			{
				target.setIsLABranch(Boolean.TRUE);
			}
			else
			{
				target.setIsLABranch(Boolean.FALSE);
			}

			
		
			target.setIsInDeliveryFeePilot(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SiteoneDeliveryFeeFeatureSwitch", sessionStore.getStoreId()));

		}


		if ((sessionService.getAttribute("guestUser") != null
				&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest")))
		{
			if (null != sessionStore && null != sessionStore.getStoreId())
			{
				if ((siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(GUEST_CHECKOUT_ENABLED_BRANCHES,
						sessionStore.getStoreId()) && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(GUEST_RETAIL_CHECKOUT_DISABLED_BRANCHES,
								sessionStore.getStoreId()))
						|| siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SHIPPING_DELIVERY_FEE_BRANCHES,
								sessionStore.getStoreId()))
				{
					target.setIsDeliveryTampaBranch(Boolean.TRUE);
				}
				else
				{
					target.setIsDeliveryTampaBranch(Boolean.FALSE);
				}

				if ((siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(GUEST_CHECKOUT_ENABLED_BRANCHES,
						sessionStore.getStoreId()) && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(GUEST_RETAIL_CHECKOUT_DISABLED_BRANCHES,
								sessionStore.getStoreId()))
						|| siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SHIPPING_DELIVERY_LA_FEE_BRANCHES,
								sessionStore.getStoreId()))
				{
					target.setIsDeliveryLABranch(Boolean.TRUE);
				}
				else
				{
					target.setIsDeliveryLABranch(Boolean.FALSE);
				}


				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(GUEST_PICKUP_AVAILABLE_BRANCHES,
						sessionStore.getStoreId()))
				{
					target.setIsPickupaAllowedBranch(Boolean.TRUE);
				}
				else
				{
					target.setIsPickupaAllowedBranch(Boolean.FALSE);
				}
			}
		}
		boolean isGuestOrHomeowner = ((SiteOneCartFacade) cartFacade).isGuestOrHomeowner(source);
		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
		PointOfServiceData pos = null;
		if(sessionStore != null && sessionStore.getHubStores() != null && sessionStore.getHubStores().get(0) != null)
		{
		   pos = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(sessionStore.getHubStores().get(0));
		}
		if(target.getIsItemLevelShippingFeeBranch() != null && target.getIsItemLevelShippingFeeBranch())
		{
			target.setFreeShippingThreshold(null);
		}
		else if(sessionShipTo != null && sessionShipTo.getShippingThreshold() != null && !sessionShipTo.getShippingThreshold().isEmpty()
				&& (target.getIsLABranch() || target.getIsTampaBranch() || target.getIsShippingFeeBranch()))
		{
			target.setFreeShippingThreshold(sessionShipTo.getShippingThreshold());
		}
		else if (pos != null && isGuestOrHomeowner  && pos.getShippingThresholdForHomeownerOrGuest() != null && !pos.getShippingThresholdForHomeownerOrGuest().isEmpty())
      {
      	target.setFreeShippingThreshold(pos.getShippingThresholdForHomeownerOrGuest());
      }
      else if (pos != null && !isGuestOrHomeowner && pos.getShippingThresholdForContractor() != null && !pos.getShippingThresholdForContractor().isEmpty())
      {
      	target.setFreeShippingThreshold(pos.getShippingThresholdForContractor());
      }
      else if (BooleanUtils.isFalse(target.getIsShippingFeeBranch()) && null != siteOneFeatureSwitchCacheService.getValueForSwitch(FREE_SHIPPING_THRESHOLD))
		{
			target.setFreeShippingThreshold(siteOneFeatureSwitchCacheService.getValueForSwitch(FREE_SHIPPING_THRESHOLD));
		}
		else
		{
			final Map<String, String> flatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_THRESHOLD);
			final Map<String, String> hubflatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUB_SHIPPING_THRESHOLD);
			if (null != sessionStore && null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0) 
					&& null != flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
			{
      	  target.setFreeShippingThreshold(flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)));
      	}
			else if (null != sessionStore && null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0) 
         		&& (null != hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0))))
			{
         	target.setFreeShippingThreshold(hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)));
         }
		}
		boolean branchThreshold = false;
		if(isGuestOrHomeowner && null != sessionStore && sessionStore.getDeliveryThresholdForHomeownerOrGuest() != null 
				&& !sessionStore.getDeliveryThresholdForHomeownerOrGuest().isEmpty())
		{
			target.setDeliveryEligibilityThreshold(sessionStore.getDeliveryThresholdForHomeownerOrGuest());
			branchThreshold = true;
		}
		else if(!isGuestOrHomeowner && null != sessionStore && sessionStore.getDeliveryThresholdForContractor() != null 
				&& !sessionStore.getDeliveryThresholdForContractor().isEmpty())
		{
			target.setDeliveryEligibilityThreshold(sessionStore.getDeliveryThresholdForContractor());
			branchThreshold = true;
		}
		final Map<String, String> deliveryThreshold = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.DELIVERY_COST_THRESHOLD_BRANCEHES);

		if(null != sessionStore && null != deliveryThreshold.get(sessionStore.getStoreId()) && isGuestOrHomeowner && !branchThreshold)
		{
			target.setDeliveryEligibilityThreshold(deliveryThreshold.get(sessionStore.getStoreId()));
		}
		else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch(DELIVERY_COST_THRESHOLD) && isGuestOrHomeowner && !branchThreshold)
		{
			target.setDeliveryEligibilityThreshold(siteOneFeatureSwitchCacheService.getValueForSwitch(DELIVERY_COST_THRESHOLD));
		}

		Set<String> itemNumbers = new HashSet<>();
		if (CollectionUtils.isNotEmpty(target.getEntries()) && CollectionUtils.isNotEmpty(source.getEntries()))
		{
			for (final OrderEntryData orderEntryData : target.getEntries()) { 
			    for (final AbstractOrderEntryModel entry : source.getEntries()) { 
			        
			        if (entry.getProduct() == null || orderEntryData.getProduct() == null) {
			      	  continue;
			       }

			       if (!orderEntryData.getProduct().getCode().equalsIgnoreCase(entry.getProduct().getCode())){
			      	 continue; 
			       }

			        PointOfServiceData pos1 = null;
			        PointOfServiceModel posModel = null;
			        String code = null;
			        if (sessionStore != null && sessionStore.getStoreId() != null) { 
			            pos1 = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(sessionStore.getStoreId()); 
			            posModel = siteOneStoreFinderService.getStoreDetailForId(sessionStore.getStoreId());
			        }
			        
			        if (null != pos1 && siteOneFeatureSwitchCacheService
			  				.isBranchPresentUnderFeatureSwitch("BulkBigBag", pos1.getStoreId()))
			        {
				        if (Boolean.TRUE.equals(entry.getProduct().getWeighAndPayEnabled()) 
				            && Boolean.TRUE.equals(pos1.getBigBag())) { 
				      	  
					        if (posModel != null && posModel.getBigBagSize() != null && posModel.getBigBagSize().getCode() != null) {
					      	  code = posModel.getBigBagSize().getCode();
					        }

				            if (entry.getBigBagInfo() == null) {
				                entry.setBigBagInfo(modelService.create(BagInfoModel.class));
				            }

				            if (entry.getBigBagInfo().getIsChecked() == null)            
				            {
				            	if (code != null)
				            	{
				            		 ProductData productdata = siteOneProductFacade.getProductBySearchTermForSearch(code,
												Arrays.asList( ProductOption.CUSTOMER_PRICE));
											if (productdata != null && productdata.getCustomerPrice() != null && productdata.getCustomerPrice().getValue() != null)
											{
												entry.getBigBagInfo().setUnitPrice(productdata.getCustomerPrice().getValue().toPlainString());
											}
											else
											{
												productdata = siteOneProductFacade.getProductBySearchTermForSearch(code,
														Arrays.asList( ProductOption.PRICE));
												if (productdata != null && productdata.getPrice() != null && productdata.getPrice().getValue() != null)
												{
													entry.getBigBagInfo().setUnitPrice(productdata.getPrice().getValue().toPlainString());
												}
												else
							            	{
							            		entry.getBigBagInfo().setUnitPrice(null);
							            	}
											}
				            	}
				            	else
				            	{
				            		entry.getBigBagInfo().setUnitPrice(null);
				            	}
					            entry.getBigBagInfo().setNumberOfBags(null);
					            entry.getBigBagInfo().setTotalPrice(null);
					            entry.getBigBagInfo().setUOM(orderEntryData.getUomMeasure() != null ?  
					            		orderEntryData.getUomMeasure() : null);				            
					            entry.getBigBagInfo().setIsChecked(null);

					            modelService.save(entry.getBigBagInfo());
					            modelService.save(entry);
				            }
				            
				            if (entry.getBigBagInfo() != null && entry.getBigBagInfo().getIsChecked() != null && Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()))			            
				            {
				            	itemNumbers.add(entry.getProduct().getItemNumber());
				            }

				            if (entry.getBigBagInfo() != null && Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()))			            
				            {
   	      			        
   	      			        if (target.getSubTotal() != null && target.getTotalPrice() != null) {
   	      			      	    BigDecimal subtotalValue = target.getSubTotal().getValue() != null 
   	      			      	        ? target.getSubTotal().getValue() 
   	      			      	        : BigDecimal.ZERO;
   	      			      	    
   	      			      	    BigDecimal totalValue = target.getTotalPrice().getValue() != null 
       	      			      	        ? target.getTotalPrice().getValue() 
       	      			      	        : BigDecimal.ZERO;
   	      			      	    
   	      			      	    BigDecimal totalTaxPriceValue = target.getTotalPriceWithTax().getValue() != null 
     	      			      	        ? target.getTotalPriceWithTax().getValue() 
     	      			      	        : BigDecimal.ZERO;
   	      			      	    if (entry.getBigBagInfo().getTotalPrice() != null)
   	      			      	    {
      	      			      	    BigDecimal bigBagTotal = new BigDecimal(entry.getBigBagInfo().getTotalPrice());
      	      			      	    subtotalValue = subtotalValue.add(bigBagTotal).setScale(2, RoundingMode.DOWN); // Ensures 2 decimal places
      	      			      	    totalValue = totalValue.add(bigBagTotal).setScale(2, RoundingMode.DOWN);
      	      			      	    totalTaxPriceValue = totalTaxPriceValue.add(bigBagTotal).setScale(2, RoundingMode.DOWN);
 	      			      	    }

   	      			      	    target.setSubTotal(createPrice(source, subtotalValue.doubleValue()));
   	      			      	    target.setTotalPrice(createPrice(source, totalValue.doubleValue()));
 	      			      	    target.setTotalPriceWithTax(createPrice(source, totalTaxPriceValue.doubleValue()));	
   	      			      	}
				            }
				            
				            if (orderEntryData.getBigBagInfo() == null) {
				                orderEntryData.setBigBagInfo(new BagInfoData());
				            }

				            if (entry.getBigBagInfo() != null && entry.getBigBagInfo().getIsChecked() != null)
				            {
				            	if (entry.getBigBagInfo().getUnitPrice() != null)
				            	{
					            	orderEntryData.getBigBagInfo().setUnitPrice(truncatePrice(Double.valueOf(entry.getBigBagInfo().getUnitPrice()), CURRENCY_PERUNIT_PRICE_DIGITS)); 
				            	}				            				       

					            orderEntryData.getBigBagInfo().setUOM(entry.getBigBagInfo().getUOM() != null ?  
						      	        entry.getBigBagInfo().getUOM() : null);
					            
					            orderEntryData.getBigBagInfo().setIsChecked(entry.getBigBagInfo().getIsChecked());
					            
					            if (entry.getBigBagInfo().getNumberOfBags() != null)
				            	{
					            	orderEntryData.getBigBagInfo().setNumberOfBags(entry.getBigBagInfo().getNumberOfBags());
				            	}
					            else if (Boolean.TRUE.equals(entry.getBigBagInfo().getIsChecked()) && entry.getBigBagInfo().getNumberOfBags() == null)
					            {
					            	if (entry.getProduct() != null && entry.getProduct().getCode() != null && entry.getQuantity() != null && entry.getBigBagInfo().getUOM() != null)
					            	{
					            		String sku = entry.getProduct().getCode();
					            		String uom = entry.getBigBagInfo().getUOM();
					            		Integer qnty = Integer.valueOf(entry.getQuantity().intValue());
					            		Integer noOfBags = ((SiteOneCartFacade) cartFacade).getNumberOfBigBagCalculation(sku, qnty, uom);
					            		orderEntryData.getBigBagInfo().setNumberOfBags(noOfBags);
					            	}
					            	else
					            	{
					            		orderEntryData.getBigBagInfo().setNumberOfBags(1);
					            	}
					            }
					            else
					            {
					            	orderEntryData.getBigBagInfo().setNumberOfBags(null);
					            }
						            
					            if (entry.getBigBagInfo().getTotalPrice() != null)
				            	{
					            	orderEntryData.getBigBagInfo().setTotalPrice(truncatePrice(Double.valueOf(entry.getBigBagInfo().getTotalPrice()), CURRENCY_UNIT_PRICE_DIGITS)); 
				            	}
					            else if (entry.getBigBagInfo().getUnitPrice() != null && orderEntryData.getBigBagInfo().getNumberOfBags() != null)
					            {
					            	Double totalPrice = Double.valueOf(entry.getBigBagInfo().getUnitPrice()) * orderEntryData.getBigBagInfo().getNumberOfBags();
					            	orderEntryData.getBigBagInfo().setTotalPrice(truncatePrice(totalPrice, CURRENCY_UNIT_PRICE_DIGITS));
					            }
					            else
					            {
					            	orderEntryData.getBigBagInfo().setTotalPrice(null);
					            }
				            }
				            else
				            {
				            	if (entry.getBigBagInfo().getUnitPrice() != null)
				            	{
				            		orderEntryData.getBigBagInfo().setUnitPrice(truncatePrice(Double.valueOf(entry.getBigBagInfo().getUnitPrice()), CURRENCY_PERUNIT_PRICE_DIGITS)); 
				            	}
				               orderEntryData.getBigBagInfo().setNumberOfBags(null);
				               orderEntryData.getBigBagInfo().setTotalPrice(null);
				               orderEntryData.getBigBagInfo().setUOM(entry.getBigBagInfo().getUOM() != null ?  
						      	        entry.getBigBagInfo().getUOM() : null);
				               orderEntryData.getBigBagInfo().setIsChecked(null);
				            }
				        }
			        }
			       
			    }
			}
		}
		
   	   	if (!itemNumbers.isEmpty()) 
   	   	{
   	   		String itemNumbersString = String.join(",", itemNumbers);
   	   		itemNumbersString += ": Big Bag ";
   	   	   if (StringUtils.isNotEmpty(itemNumbersString))
   	   	   {
   	   	   	 target.setBigBagInstruction(itemNumbersString);
   	   	   }
   	   	}

			
		if (CollectionUtils.isNotEmpty(target.getEntries()))
		{
			final StringBuilder productList = convertproductListToCommaSeparatedProductList(target);
			final List<PointOfServiceData> nearByStores = new ArrayList<PointOfServiceData>();
			final List<PointOfServiceData> hubStores = new ArrayList<PointOfServiceData>();
			if ((storeSessionFacade.getSessionStore() != null
					&& CollectionUtils.isNotEmpty(storeSessionFacade.getSessionStore().getHubStores())))
			{
				final List<String> hubStoresList = storeSessionFacade.getSessionStore().getHubStores();
				hubStoresList.forEach(storeId -> {
					final PointOfServiceData posData = new PointOfServiceData();
					posData.setStoreId(storeId);
					hubStores.add(posData);
				});
			}

			final List<List<Object>> hubStoresInventory = getSiteOneStockLevelService()
					.getStockLevelCountForHomeAndNearByStores(productList, hubStores);

			for (final OrderEntryData orderEntryData : target.getEntries())
			{
				final ProductData productData = orderEntryData.getProduct();
				int hubStoresInventoryCount = 0;
				for (final List<Object> inventoryRecord : hubStoresInventory)
				{
					if (inventoryRecord.get(0) != null && inventoryRecord.get(0).toString().equals(productData.getCode()))
					{
						if (Integer.parseInt(inventoryRecord.get(2).toString()) < 0)
						{
							hubStoresInventoryCount = 0;
						}
						else
						{
							hubStoresInventoryCount = Integer.parseInt(inventoryRecord.get(2).toString());
						}

					}
				}
				if(orderEntryData.getUomMultiplier()!=null)
				{
				productData.setHubStoresAvailableQty((int) Math.floor(hubStoresInventoryCount / orderEntryData.getUomMultiplier()));
				}
				else
				{
					productData.setHubStoresAvailableQty(hubStoresInventoryCount);
				}

			}

			if (storeSessionFacade.getNearbyStoresFromSession() != null)
			{
				nearByStores.addAll(storeSessionFacade.getNearbyStoresFromSession());
			}


			final List<List<Object>> inventory = getSiteOneStockLevelService().getStockLevelCountForHomeAndNearByStores(productList,
					nearByStores);

			for (final OrderEntryData orderEntryData : target.getEntries())
			{
				final ProductData productData = orderEntryData.getProduct();
				int homeStoreInventoryCount = 0;
				int nearbyStoresInventoryCount = 0;
				for (final List<Object> inventoryRecord : inventory)
				{
					if (inventoryRecord.get(0) != null && inventoryRecord.get(0).toString().equals(productData.getCode()))
					{
						if (inventoryRecord.get(1) != null && storeSessionFacade.getSessionStore() != null
								&& inventoryRecord.get(1).equals(storeSessionFacade.getSessionStore().getStoreId()))
						{
							
							if (Integer.parseInt(inventoryRecord.get(2).toString()) < 0)
							{
								homeStoreInventoryCount = 0;
							}
							else
							{
								homeStoreInventoryCount = Integer.parseInt(inventoryRecord.get(2).toString());
							}

						}
						else
						{
							if (Integer.parseInt(inventoryRecord.get(2).toString()) < 0)
							{
								nearbyStoresInventoryCount += 0;
							}
							else
							{
								nearbyStoresInventoryCount += Integer.parseInt(inventoryRecord.get(2).toString());
							}
						}

					}
					
					if(orderEntryData.getUomMultiplier()!=null)
					{
					productData.setNearbyStoresAvailableQty((int) Math.floor(nearbyStoresInventoryCount / orderEntryData.getUomMultiplier()));
					productData.setHomeStoreAvailableQty((int) Math.floor(homeStoreInventoryCount / orderEntryData.getUomMultiplier()));
					}
					else
					{
						productData.setNearbyStoresAvailableQty(nearbyStoresInventoryCount);
						productData.setHomeStoreAvailableQty(homeStoreInventoryCount);
						
					}

				}
			}
		}
		
		boolean spliMixedCart = false;
		PointOfServiceData homestore= storeSessionFacade.getSessionStore();
		if(!userFacade.isAnonymousUser() && null != homestore && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", homestore.getStoreId()))
		{
			spliMixedCart=true;
		}

		if (CollectionUtils.isNotEmpty(target.getEntries()))
		{
			final List<OrderEntryData> pickupAndDeliveryEntries = new ArrayList<>();
			final List<OrderEntryData> shippingOnlyEntries = new ArrayList<>();
			target.setIsShippingHubOnly(Boolean.FALSE);
			target.getEntries().forEach(orderEntryData -> {

				final ProductData productData = orderEntryData.getProduct();
				final boolean shippable = productData != null && productData.getIsShippable() != null
						&& productData.getIsShippable().booleanValue();
				if (orderEntryData.getProduct() != null && shippable && orderEntryData.getProduct().getMaxShippableQuantity() != null
						&& orderEntryData.getQuantity() > orderEntryData.getProduct().getMaxShippableQuantity().longValue())
				{
					orderEntryData.getProduct().setValidationMessage(
							getMessageSource().getMessage("cart.message.product.maximumQuantityExceeded", new Object[]
					{ productData.getMaxShippableQuantity() }, i18nService.getCurrentLocale()));
				}

				if (orderEntryData.getProduct() != null && shippable
						&& orderEntryData.getProduct().getMaxShippableDollarAmount() != null && orderEntryData.getTotalPrice() != null
						&& orderEntryData.getTotalPrice().getValue().doubleValue() > orderEntryData.getProduct()
								.getMaxShippableDollarAmount().doubleValue())
				{
					BigDecimal formattedFinalPrice = BigDecimal.valueOf(productData.getMaxShippableDollarAmount());
					formattedFinalPrice = formattedFinalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					orderEntryData.getProduct().setValidationMessage(
							getMessageSource().getMessage("cart.message.product.maximumDollarAmountExceeded", new Object[]
					{ "$".concat(formattedFinalPrice.toString()) }, i18nService.getCurrentLocale()));
				}
				if (orderEntryData.getProduct() != null && orderEntryData.getProduct().getFullfillmentStoreId() != null)
				{
					orderEntryData.setFullfillmentStoreId(orderEntryData.getProduct().getFullfillmentStoreId());
				}
				if (orderEntryData.getProduct() != null && orderEntryData.getProduct().getFullfilledStoreType() != null)
				{
					orderEntryData.setFullfilledStoreType(orderEntryData.getProduct().getFullfilledStoreType());
					if ((orderEntryData.getProduct().getFullfilledStoreType()).equalsIgnoreCase("HubStore"))
					{
						shippingOnlyEntries.add(orderEntryData);
						target.setIsShippingHubOnly(Boolean.TRUE);
					}
					else
					{
						pickupAndDeliveryEntries.add(orderEntryData);
					}

				}
				else if (orderEntryData.getProduct() != null && BooleanUtils.isNotTrue(orderEntryData.getProduct().getOutOfStockImage()))
				{
					pickupAndDeliveryEntries.add(orderEntryData);
				}
				if (!BooleanUtils.isTrue(target.getStockAvailableOnlyHubStore()))
				{
					target.setStockAvailableOnlyHubStore(false);
					if (orderEntryData.getProduct() != null && orderEntryData.getProduct().getStockAvailableOnlyHubStore() != null)
					{
						target.setStockAvailableOnlyHubStore(orderEntryData.getProduct().getStockAvailableOnlyHubStore());
					}
				}

			});
			
			if(spliMixedCart && !CollectionUtils.isEmpty(shippingOnlyEntries) && !CollectionUtils.isEmpty(pickupAndDeliveryEntries)
					&& source.getOrderType() != null && !source.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING"))
			{
				target.setShippingOnlyEntries(shippingOnlyEntries);
				target.setPickupAndDeliveryEntries(pickupAndDeliveryEntries);
			}

		}
		
		if(spliMixedCart && !CollectionUtils.isEmpty(target.getShippingOnlyEntries()) && !CollectionUtils.isEmpty(target.getPickupAndDeliveryEntries()))
		{
			if(target.getIsItemLevelShippingFeeBranch() != null && target.getIsItemLevelShippingFeeBranch()
					&& b2bCheckoutFacade.getItemLevelShippingFee(target.getShippingOnlyEntries()) == null)
			{
				target.setIsShippingFeeBranch(Boolean.FALSE);
				target.setIsItemLevelShippingFeeBranch(Boolean.FALSE);
			}
			updateSplitMixedFulfillmentDetail(source,target,branchThreshold);
		}
		else if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			updateShippingFulfillmentDetails(target);
			updateDefaultFulfillmentdetails(source, target);			
			populateMaxShippingRelatedMessages(source, target);
			ThresholdCheckResponseData deliveryThresholdCheckData = new ThresholdCheckResponseData();
			ThresholdCheckResponseData shippingThresholdCheckData = new ThresholdCheckResponseData();
			DecimalFormat df = new DecimalFormat("#.00");  
			double shippableItemTotal = 0.0d;
			double deliveryItemTotal = 0.0d;
			boolean flag = ((SiteOneCartFacade) cartFacade).isGuestOrHomeowner(source);
			for (final AbstractOrderEntryModel entry : source.getEntries())
			{
				if(entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE))
				{
					shippableItemTotal += entry.getTotalPrice();
				}
				if(entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE))
				{
					deliveryItemTotal += entry.getTotalPrice();
				}
			}
			source.setShippableItemTotal(shippableItemTotal);
			source.setDeliverableItemTotal(deliveryItemTotal);
			modelService.save(source);
			if (null != source.getDeliverableItemTotal() && (flag || branchThreshold))
			{
				deliveryThresholdCheckData.setSelectedItemTotal(Double.valueOf(df.format(source.getDeliverableItemTotal())));
				double differenceAmount = 0.00;
				
				if(flag && branchThreshold)
				{
					differenceAmount = Double.valueOf(sessionStore.getDeliveryThresholdForHomeownerOrGuest()) - source.getDeliverableItemTotal();
				}
				else if(!flag && branchThreshold)
				{
					differenceAmount = Double.valueOf(sessionStore.getDeliveryThresholdForContractor()) - source.getDeliverableItemTotal();
				}
				
				final Map<String, String> branchDeliveryThreshold = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.DELIVERY_COST_THRESHOLD_BRANCEHES);
				
				if(null != sessionStore && null != branchDeliveryThreshold.get(sessionStore.getStoreId()) && !branchThreshold)
				{
					differenceAmount =  (Double.valueOf(branchDeliveryThreshold.get(sessionStore.getStoreId()))
							-source.getDeliverableItemTotal());
				}
				else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold") && !branchThreshold)
				{
					differenceAmount = (Double.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold"))
							- source.getDeliverableItemTotal());
				}
				
				deliveryThresholdCheckData.setDifferenceAmount(Double.valueOf(df.format(differenceAmount)));
				target.setDeliveryThresholdCheckData(deliveryThresholdCheckData);
			}
			if (null != source.getShippableItemTotal())
			{
				shippingThresholdCheckData.setSelectedItemTotalShipping(Double.valueOf(df.format(source.getShippableItemTotal())));
				final double differenceAmountShipping = ((null != siteOneFeatureSwitchCacheService
						.getValueForSwitch(FREE_SHIPPING_THRESHOLD))
								? (Double.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch(FREE_SHIPPING_THRESHOLD))
										- source.getShippableItemTotal())
								: 0.00);
				shippingThresholdCheckData.setDifferenceAmountShipping(Double.valueOf(df.format(differenceAmountShipping)));
				target.setShippingThresholdCheckData(shippingThresholdCheckData);
			}			
		}
		else
		{
			boolean isModified = false;
			double deliveryThresholdTotal = 0.0d;
			double shippableItemTotal = 0.0d;
			DecimalFormat df = new DecimalFormat("#.00");
			final ThresholdCheckResponseData shippingThresholdCheckData = new ThresholdCheckResponseData();
			for (final AbstractOrderEntryModel entry : source.getEntries())
			{
				isModified = false;
				if(entry.getDeliveryPointOfService() == null && storeSessionFacade.getSessionStore() != null)
				{
					isModified = true;
			
				}
				if(entry.getDeliveryMode() == null && null != source.getOrderType())
				{
					if(source.getOrderType().getCode().equalsIgnoreCase("PICKUP"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
					}
					if(source.getOrderType().getCode().equalsIgnoreCase("DELIVERY"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE));
					}
					if(source.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING"))
					{
						entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
					}
					isModified = true;
				}
				if(isModified) {
				modelService.save(entry);
				}
				if(((entry.getFullfilledStoreType() !=null && !entry.getFullfilledStoreType().equalsIgnoreCase("HubStore")) 
						|| entry.getFullfilledStoreType() == null) && BooleanUtils.isTrue(entry.getProduct().getIsDeliverable()))
				{
					deliveryThresholdTotal += entry.getTotalPrice();
				}
				if(entry.getFullfilledStoreType() !=null &&  entry.getFullfilledStoreType().equalsIgnoreCase("HubStore"))
				{
					shippableItemTotal += entry.getTotalPrice();
				}
			}
			target.setDeliveryEligibleTotal(Double.valueOf(df.format(deliveryThresholdTotal)));
			if (source.getOrderType() != null && source.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING")
					 && BooleanUtils.isNotTrue(target.getIsItemLevelShippingFeeBranch()))
			{
				shippingThresholdCheckData
						.setSelectedItemTotalShipping(Double.valueOf(df.format(target.getTotalPrice().getValue().doubleValue())));
				final double shippingThreshold = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getThreshold(target);
				final double differenceAmountShipping = shippingThreshold - shippableItemTotal;
				final double differenceAmountShippingForEntireCart = shippingThreshold
						- Double.valueOf(df.format(target.getTotalPrice().getValue().doubleValue()));
				if (differenceAmountShipping > 0)
				{
					shippingThresholdCheckData.setDifferenceAmountShipping(Double.valueOf(df.format(differenceAmountShipping)));
				}
				if (differenceAmountShippingForEntireCart > 0)
				{
					shippingThresholdCheckData.setDifferenceAmountShippingEntireCartTotal(Double.valueOf(df.format(differenceAmountShippingForEntireCart)));
				}
				target.setShippingThresholdCheckData(shippingThresholdCheckData);
			}
			
		}
		target.setShowDeliveryFeeMessage(siteOneCartFacade.showDeliveryFeeMessage(target, source));
		target.setCartToggleResponseData(siteOneCartFacade.setCartToggleResponseData(target, target.getOrderType()));
		if (target.getOrderType() != null && target.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
      {
          target.setIsNationalShipping(BooleanUtils.isFalse(target.getIsTampaBranch()) && BooleanUtils.isFalse(target.getIsLABranch()));
      }
	}
	
	private String truncatePrice(final Double priceToTruncate, final int toDigits)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat;
			if (toDigits == 3)
			{
				decimalFormat = new DecimalFormat("#,##0.000");
			}
			else
			{
				decimalFormat = new DecimalFormat("#,##0.00");
			}
			final String truncatedPrice = decimalFormat
					.format(BigDecimal.valueOf(priceToTruncate.doubleValue()).setScale(toDigits, BigDecimal.ROUND_HALF_UP));
			return truncatedPrice;
		}
		else
		{
			return null;
		}
	}

	private void updateSplitMixedFulfillmentDetail(CartModel source, CartData target, boolean branchThreshold)
	{
		if(source.getOrderType()!= null && source.getOrderType().getCode().equalsIgnoreCase("DELIVERY") && null != source.getFreight())
		{
			final Double freightCharges = Double.valueOf(source.getFreight());
			BigDecimal roundedFreightCharges = BigDecimal.valueOf(freightCharges);
			roundedFreightCharges = roundedFreightCharges.setScale(digits, BigDecimal.ROUND_HALF_UP);
			target.setDeliveryFreight(roundedFreightCharges.toString());		
		}
		
		final Double shippingFreightCharges = null != source.getShippingFreight() ? Double.valueOf(source.getShippingFreight())
				: Double.valueOf(0.00);
		if (shippingFreightCharges != null)
		{
			BigDecimal roundedFreightCharges = BigDecimal.valueOf(shippingFreightCharges);
			roundedFreightCharges = roundedFreightCharges.setScale(digits, BigDecimal.ROUND_HALF_UP);
			target.setShippingFreight(roundedFreightCharges.toString());
		}
		updateShippingFulfillmentDetails(target);
		updateDefaultFulfillmentDetailsForSplitCartPickup(source, target);
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		ThresholdCheckResponseData shippingThresholdCheckData = new ThresholdCheckResponseData();
		ThresholdCheckResponseData deliveryThresholdCheckData = new ThresholdCheckResponseData();
		DecimalFormat df = new DecimalFormat("#.00");  
		double shippableItemTotal = 0.0d;
		double deliveryItemTotal = 0.0d;
		double deliveryThresholdTotal = 0.0d;
		for (final AbstractOrderEntryModel entry : source.getEntries())
		{
			if(entry.getFullfilledStoreType() !=null &&  entry.getFullfilledStoreType().equalsIgnoreCase("HubStore"))
			{
				shippableItemTotal += entry.getTotalPrice();
			}
			else if(BooleanUtils.isTrue(entry.getProduct().getIsDeliverable()))
			{
				deliveryThresholdTotal += entry.getTotalPrice();
			}
			if(entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE))
			{
				deliveryItemTotal += entry.getTotalPrice();
			}
		}
		source.setShippableItemTotal(shippableItemTotal);
		source.setDeliverableItemTotal(deliveryItemTotal);	
		target.setDeliveryEligibleTotal(Double.valueOf(df.format(deliveryThresholdTotal)));
		source.setCalculated(Boolean.TRUE);
		modelService.save(source);
		boolean flag = ((SiteOneCartFacade) cartFacade).isGuestOrHomeowner(source);
		if (null != source.getShippableItemTotal() && BooleanUtils.isNotTrue(target.getIsItemLevelShippingFeeBranch()))
		{
			shippingThresholdCheckData.setSelectedItemTotalShipping(Double.valueOf(df.format(source.getShippableItemTotal())));
			final double shippingThreshold = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getThreshold(target);
			final double differenceAmountShipping = shippingThreshold - source.getShippableItemTotal();
			final double differenceAmountShippingForEntireCart = shippingThreshold
					- Double.valueOf(df.format(target.getTotalPrice().getValue().doubleValue()));
			if(differenceAmountShipping > 0)
			{
				shippingThresholdCheckData.setDifferenceAmountShipping(Double.valueOf(df.format(differenceAmountShipping)));
			}
			if (differenceAmountShippingForEntireCart > 0)
			{
				shippingThresholdCheckData.setDifferenceAmountShippingEntireCartTotal(Double.valueOf(df.format(differenceAmountShippingForEntireCart)));
			}
			target.setShippingThresholdCheckData(shippingThresholdCheckData);
		}
		if (null != target.getDeliveryEligibleTotal() && (flag || branchThreshold))
		{
			Double deliveryTotal = target.getDeliveryEligibleTotal();
			deliveryThresholdCheckData.setSelectedItemTotal(Double.valueOf(df.format(deliveryTotal)));
			double differenceAmount = 0.00;
			
			if(flag && branchThreshold)
			{
				differenceAmount = Double.valueOf(sessionStore.getDeliveryThresholdForHomeownerOrGuest()) - deliveryTotal;
			}
			else if(!flag && branchThreshold)
			{
				differenceAmount = Double.valueOf(sessionStore.getDeliveryThresholdForContractor()) - deliveryTotal;
			}
			
			final Map<String, String> branchDeliveryThreshold = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.DELIVERY_COST_THRESHOLD_BRANCEHES);
			
			if(null != sessionStore && null != branchDeliveryThreshold.get(sessionStore.getStoreId()) && !branchThreshold)
			{
				differenceAmount =  (Double.valueOf(branchDeliveryThreshold.get(sessionStore.getStoreId()))
						-deliveryTotal);
			}
			else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold") && !branchThreshold)
			{
				differenceAmount = (Double.valueOf(siteOneFeatureSwitchCacheService.getValueForSwitch("DeliveryCostThreshold"))
						- deliveryTotal);
			}
			
			deliveryThresholdCheckData.setDifferenceAmount(Double.valueOf(df.format(differenceAmount)));
			target.setDeliveryThresholdCheckData(deliveryThresholdCheckData);
		}
		
	}

	@Override
	protected double getProductsDiscountsAmount(final AbstractOrderModel source)
	{
		double discounts = 0.0d;

		final List<AbstractOrderEntryModel> entries = source.getEntries();
		if (entries != null)
		{
			for (final AbstractOrderEntryModel entry : entries)
			{
				final List<DiscountValue> discountValues = entry.getDiscountValues();
				if (entry.getBasePrice() != Double.valueOf(0.0) && discountValues != null)
				{
					for (final DiscountValue dValue : discountValues)
					{
						discounts += dValue.getAppliedValue();
					}
				}
			}
		}
		return discounts;
	}

	private StringBuilder convertproductListToCommaSeparatedProductList(final CartData cart)
	{
		final StringBuilder productList = new StringBuilder();
		final boolean[] firstTime =
		{ true };

		cart.getEntries().forEach(orderEntryData -> {
			if (orderEntryData != null && orderEntryData.getProduct() != null && orderEntryData.getProduct().getCode() != null)
			{
				if (firstTime[0])
				{
					productList.append("'");
					productList.append(orderEntryData.getProduct().getCode());
					productList.append("'");
					firstTime[0] = false;
				}
				else
				{
					productList.append(", '");
					productList.append(orderEntryData.getProduct().getCode());
					productList.append("'");
				}
			}

		});

		return productList;

	}

	private void updateDefaultFulfillmentDetailsForSplitCartPickup(final CartModel cartModel, final CartData cartData)
	{
		if (cartModel != null && CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (cartData != null && CollectionUtils.isNotEmpty(cartData.getEntries()))
				{
					for (final OrderEntryData cartDataEntry : cartData.getEntries())
					{
						if (null != entry.getProduct() && StringUtils.isNotBlank(entry.getProduct().getCode())
								&& null != cartDataEntry.getProduct() && StringUtils.isNotBlank(cartDataEntry.getProduct().getCode())
								&& entry.getProduct().getCode().equalsIgnoreCase(cartDataEntry.getProduct().getCode())
								&& (entry.getEntryNumber().equals(cartDataEntry.getEntryNumber())))
						{
							if(null != entry.getIsShippingOnlyProduct() && entry.getIsShippingOnlyProduct())
							{
								cartDataEntry.setDefaultFulfillmentType(SHIPPING);
								if(entry.getDeliveryPointOfService() != null)
								{
									siteOneCartFacade.updateShippingStore(cartDataEntry.getProduct(), ((SiteOneStoreFinderFacade) storeFinderFacade)
   										.getStoreForId(entry.getDeliveryPointOfService().getStoreId()));
								}
								entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
							}
							else
							{
								if(entry.getDeliveryMode()==null)
   							{   								
   								entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
   							}
								if(cartDataEntry.getFullfilledStoreType() != null && !cartDataEntry.getFullfilledStoreType().equalsIgnoreCase("HubStore"))
								{
									cartDataEntry.setDefaultFulfillmentType(PICKUP);
								}
							}
							modelService.save(entry);
						}
					}
				}
			}
		}
		
	}

	protected void updateDefaultFulfillmentdetails(final CartModel cartModel, final CartData cartData)
	{
		if (cartModel != null && CollectionUtils.isNotEmpty(cartModel.getEntries()))
		{
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (cartData != null && CollectionUtils.isNotEmpty(cartData.getEntries()))
				{
					for (final OrderEntryData cartDataEntry : cartData.getEntries())
					{
						if (null != entry.getProduct() && StringUtils.isNotBlank(entry.getProduct().getCode())
								&& null != cartDataEntry.getProduct() && StringUtils.isNotBlank(cartDataEntry.getProduct().getCode())
								&& entry.getProduct().getCode().equalsIgnoreCase(cartDataEntry.getProduct().getCode())
								&& (entry.getEntryNumber().equals(cartDataEntry.getEntryNumber())))
						{
							if(null != entry.getDeliveryMode() && null != entry.getDeliveryPointOfService())
							{
   							if ((null == cartDataEntry.getDefaultFulfillmentType()
   									&& null != cartDataEntry.getProduct().getPickupHomeStoreInfo()
   									&& cartDataEntry.getProduct().getPickupHomeStoreInfo().getIsEnabled()
   									&& null != entry.getDeliveryPointOfService() && entry.getDeliveryPointOfService().getStoreId()
   											.equalsIgnoreCase(cartDataEntry.getProduct().getPickupHomeStoreInfo().getStoreId()))
   									|| (null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE)
   											&& null != entry.getDeliveryPointOfService() && entry.getDeliveryPointOfService().getStoreId()
   													.equalsIgnoreCase(cartDataEntry.getProduct().getPickupHomeStoreInfo().getStoreId())))
   							{
   								cartDataEntry.setDefaultFulfillmentType(PICKUP_HOME);
   							}
								if (null == cartDataEntry.getDefaultFulfillmentType() && (BooleanUtils
										.isTrue(cartDataEntry.getProduct().getIsStockInNearbyBranch())
										|| (BooleanUtils.isTrue(cartDataEntry.getProduct().getInStockImage())
												&& null != entry.getDeliveryPointOfService()
												&& !entry.getDeliveryPointOfService().getStoreId()
														.equalsIgnoreCase(cartDataEntry.getProduct().getPickupHomeStoreInfo().getStoreId())
												|| (null != cartDataEntry.getProduct().getNearestBackorderableStore()))))
								{
   								cartDataEntry.setDefaultFulfillmentType(PICKUP_NEARBY);
   								siteOneCartFacade.updateShippingStore(cartDataEntry.getProduct(), ((SiteOneStoreFinderFacade) storeFinderFacade)
   										.getStoreForId(entry.getDeliveryPointOfService().getStoreId()));
   							}
								if ((!((null != cartDataEntry.getProduct().getPickupHomeStoreInfo()
										&& cartDataEntry.getProduct().getPickupHomeStoreInfo().getIsEnabled())
										|| (null != cartDataEntry.getProduct().getPickupNearbyStoreInfo()
												&& cartDataEntry.getProduct().getPickupNearbyStoreInfo().getIsEnabled())
										|| (null != cartDataEntry.getProduct().getDeliveryStoreInfo()
												&& cartDataEntry.getProduct().getDeliveryStoreInfo().getIsEnabled()))
										&& (null != cartDataEntry.getProduct().getShippingStoreInfo()
												&& cartDataEntry.getProduct().getShippingStoreInfo().getIsEnabled()
												|| (null != cartDataEntry.getProduct().getStockAvailableOnlyHubStore()
														&& cartDataEntry.getProduct().getStockAvailableOnlyHubStore())))
										|| (null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode()
												.equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE)))
   							{
   								cartDataEntry.setDefaultFulfillmentType(SHIPPING);
   								siteOneCartFacade.updateShippingStore(cartDataEntry.getProduct(), ((SiteOneStoreFinderFacade) storeFinderFacade)
   										.getStoreForId(entry.getDeliveryPointOfService().getStoreId()));
   							}
   							if (null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE))
   							{
   								cartDataEntry.setDefaultFulfillmentType(DELIVERY);
   								siteOneCartFacade.updateShippingStore(cartDataEntry.getProduct(), ((SiteOneStoreFinderFacade) storeFinderFacade)
   										.getStoreForId(entry.getDeliveryPointOfService().getStoreId()));
   							}
   							if (null != entry.getDeliveryPointOfService()
   									&& null != cartDataEntry.getProduct().getPickupNearbyStoreInfo()
   									&& null != cartDataEntry.getProduct().getPickupHomeStoreInfo()
   									&& !entry.getDeliveryPointOfService().getStoreId()
   											.equalsIgnoreCase(cartDataEntry.getProduct().getPickupHomeStoreInfo().getStoreId()))
   							{
   								if (null != entry.getDeliveryMode()
   										&& (!entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.DELIVERY_DELIVERYMODE_CODE)
   												&& !entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE)))
   								{
   									cartDataEntry.setDefaultFulfillmentType(PICKUP_NEARBY);
   								}
   								cartDataEntry.getProduct().getPickupNearbyStoreInfo()
   										.setStoreId(entry.getDeliveryPointOfService().getStoreId());
   								cartDataEntry.getProduct().getPickupNearbyStoreInfo()
   										.setStoreName(StringUtils.isNotBlank(entry.getDeliveryPointOfService().getDisplayName())
   												? entry.getDeliveryPointOfService().getDisplayName()
   												: entry.getDeliveryPointOfService().getName());
   								cartDataEntry.getProduct().getPickupNearbyStoreInfo().setIsEnabled(true);
   								cartDataEntry.getProduct().getDeliveryStoreInfo()
   								.setStoreId(entry.getDeliveryPointOfService().getStoreId());
   						cartDataEntry.getProduct().getDeliveryStoreInfo()
   								.setStoreName(StringUtils.isNotBlank(entry.getDeliveryPointOfService().getDisplayName())
   										? entry.getDeliveryPointOfService().getDisplayName()
   										: entry.getDeliveryPointOfService().getName());
   						cartDataEntry.getProduct().getDeliveryStoreInfo().setIsEnabled(true);
   						siteOneCartFacade.updateFullfillmentMessage(cartDataEntry.getProduct(), ((SiteOneStoreFinderFacade) storeFinderFacade)
   										.getStoreForId(entry.getDeliveryPointOfService().getStoreId()));
   						siteOneCartFacade.updateShippingStore(cartDataEntry.getProduct(), ((SiteOneStoreFinderFacade) storeFinderFacade)
									.getStoreForId(entry.getDeliveryPointOfService().getStoreId()));
   								break;
   							}
   						}
   						else
   						{
   							if(entry.getDeliveryMode()==null)
   							{   								
   								entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.PICKUP_DELIVERYMODE_CODE));
   							}
   							if(null == cartDataEntry.getDefaultFulfillmentType()
   									&& null != cartDataEntry.getProduct().getPickupHomeStoreInfo()
   									&& cartDataEntry.getProduct().getPickupHomeStoreInfo().getIsEnabled())
   							{
   								cartDataEntry.setDefaultFulfillmentType(PICKUP_HOME);
   								
   							}
   							else if(null == cartDataEntry.getDefaultFulfillmentType() && BooleanUtils
   									.isTrue(cartDataEntry.getProduct().getIsStockInNearbyBranch()) && null != cartDataEntry.getProduct().getPickupNearbyStoreInfo()
   											&& cartDataEntry.getProduct().getPickupNearbyStoreInfo().getIsEnabled())
   							{
   								cartDataEntry.setDefaultFulfillmentType(PICKUP_NEARBY);
   								
   							}
   							modelService.save(entry);
   						}	
						}
					}
				}
			}
		}

	}
	
	protected void updateShippingFulfillmentDetails(final CartData cartData)
	{
		boolean forceInStock = false;
		if (CollectionUtils.isNotEmpty(cartData.getEntries()))
		{
			for (final OrderEntryData orderEntryData : cartData.getEntries())
			{
				if (null != orderEntryData.getProduct() && BooleanUtils.isTrue(orderEntryData.getProduct().getIsShippable()))
				{
					if (BooleanUtils.isNotTrue(orderEntryData.getProduct().getInventoryCheck()))
					{
						forceInStock = true;
					}
					final StoreLevelStockInfoData shippingStoreInfo = siteOneProductFacade
							.populateHubStoresStockInfoData(orderEntryData.getProduct().getCode(), false, forceInStock);
					if (null != shippingStoreInfo)
					{
						shippingStoreInfo.setIsEnabled(Boolean.TRUE);
						orderEntryData.getProduct().setShippingStoreInfo(shippingStoreInfo);
					}
				}
			}
		}
	}
	
	protected void populateMaxShippingRelatedMessages(final CartModel cartModel, final CartData cartData)
	{
		for(final  OrderEntryData orderEntryData : cartData.getEntries())
		{
			long quantity = 0;
			double price = 0.0d;
			for(final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if(null != entry.getDeliveryMode() && entry.getDeliveryMode().getCode().equalsIgnoreCase(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE) && entry.getProduct().getCode().equalsIgnoreCase(orderEntryData.getProduct().getCode()))
				{
					quantity += entry.getQuantity();
					price += entry.getTotalPrice();
				}
			}

			if (null != orderEntryData.getDefaultFulfillmentType() && orderEntryData.getDefaultFulfillmentType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING_NAME))
			{
				if (null != orderEntryData.getProduct().getMaxShippableQuantity()
						&& quantity > orderEntryData.getProduct().getMaxShippableQuantity())
				{
					orderEntryData.setMaxShippingMessage(
							getMessageSource().getMessage("cart.message.product.maximumQuantityExceeded", new Object[]
							{ orderEntryData.getProduct().getMaxShippableQuantity() }, i18nService.getCurrentLocale()));
				}
				if (null != orderEntryData.getProduct().getMaxShippableDollarAmount()
						&& price > orderEntryData.getProduct().getMaxShippableDollarAmount())
				{
					BigDecimal formattedFinalPrice = BigDecimal.valueOf(orderEntryData.getProduct().getMaxShippableDollarAmount());
					formattedFinalPrice = formattedFinalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					orderEntryData.setMaxShippingMessage(
							getMessageSource().getMessage("cart.message.product.maximumDollarAmountExceeded", new Object[]
							{ "$".concat(formattedFinalPrice.toString()) }, i18nService.getCurrentLocale()));
				}
			}

		}
		
		
	}
	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}


	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

}
