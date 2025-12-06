/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.checkout.facades.utils.SiteOneCheckoutRequestedDateUtils;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1219341
 *
 */
public class SiteOneOrderPopulator extends OrderPopulator
{

	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";
	private static final String DC_SHIPPING_FEE_BRANCHES = "DCShippingFeeBranches";

	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter;
	private static final Logger LOG = Logger.getLogger(SiteOneOrderPopulator.class);
	final int digits = 2;
	@Resource(name = "siteOneB2BUnitBasicPopulator")
	private SiteOneB2BUnitBasicPopulator siteOneB2BUnitBasicPopulator;

	@Resource(name = "addressConverter")
	private Converter<AddressModel, AddressData> addressConverter;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "siteOnePaymentInfoDataConverter")
	private Converter<SiteoneCreditCardPaymentInfoModel, SiteOnePaymentInfoData> siteOnePaymentInfoDataConverter;

	@Resource(name = "siteOnePOAPaymentInfoDataConverter")
	private Converter<SiteonePOAPaymentInfoModel, SiteOnePOAPaymentInfoData> siteOnePOAPaymentInfoDataConverter;

	@Resource(name = "siteOneGuestUserConverter")
	private Converter<CustomerModel, CustomerData> siteOneGuestUserConverter;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	private SiteOneStoreSessionFacade storeSessionFacade;

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

	private Converter<CustomerModel, CustomerData> b2BCustomerConverter;

	private SiteOneCheckoutRequestedDateUtils siteOneCheckoutRequestedDateUtils;

	@Override
	public void populate(final OrderModel source, final OrderData target)
	{
		super.populate(source, target);
		if (null != source.getPointOfService())
		{
			target.setPointOfService(pointOfServiceSiteOneConverter.convert(source.getPointOfService()));
		}
		if (null != source.getContactPerson())
		{
			target.setContactPerson(siteonecustomerConverter.convert(source.getContactPerson()));
		}
		if (null != source.getDeliveryContactPerson())
		{
			target.setDeliveryContactPerson(siteonecustomerConverter.convert(source.getDeliveryContactPerson()));
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
		if (null != source.getOrderType())
		{
			target.setOrderType(source.getOrderType().getCode());
		}
		if (null != source.getRequestedDate())
		{
			target.setRequestedDate(siteOneCheckoutRequestedDateUtils.convertDateToUTC(source.getRequestedDate()));
		}
		if (null != source.getIsRemainderEmailSent())
		{
			target.setIsReminderEmailSent(source.getIsRemainderEmailSent());
		}
		if (CollectionUtils.isNotEmpty(source.getConsignments()))
		{
			String specialInstructions = source.getSpecialInstruction();
			for (final ConsignmentModel consignment : source.getConsignments())
			{
				if (null != consignment.getSpecialInstructions())
				{
					specialInstructions = consignment.getSpecialInstructions();
				}
				break;
			}
			target.setSpecialInstruction(specialInstructions);
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
		if (source.getConsignments().size() == 1)
		{
			String invoiceNumber = "";
			Date date = null;
			String requestMeridien = "";
			for (final ConsignmentModel consignment : source.getConsignments())
			{
				invoiceNumber = consignment.getInvoiceNumber();
				date = consignment.getRequestedDate();
				if (null != consignment.getRequestedMeridian())
				{
					requestMeridien = consignment.getRequestedMeridian().getCode();
				}

				break;
			}
			if (null != date)
			{
				target.setRequestedDate(siteOneCheckoutRequestedDateUtils.convertDateToUTC(date));
			}
			target.setRequestedMeridian(requestMeridien);
			target.setInvoiceNumber(invoiceNumber);

		}

		//Email id of the contact person for store orders
		if (null != source.getStoreContact())
		{
			target.setStoreContact(source.getStoreContact());
		}

		//Name of the contact person for store orders
		if (null != source.getStoreUserName())
		{
			target.setStoreUserName(source.getStoreUserName());
		}

		if (null != source.getPaymentAddress())
		{
			target.setBillingAddress(addressConverter.convert(source.getPaymentAddress()));
		}
		if (null != source.getShippingAddress())
		{
			target.setShippingAddress(addressConverter.convert(source.getShippingAddress()));
		}

		if (null != source.getHybrisOrderNumber())
		{
			target.setHybrisOrderNumber(source.getHybrisOrderNumber());
			final List<OrderModel> orderModelList = ((SiteOneCustomerAccountService) customerAccountService)
					.getOrdersWithSameHybrisOrderNumber(source.getHybrisOrderNumber());
			if (!CollectionUtils.isEmpty(orderModelList) && orderModelList.size() > 1)
			{
				target.setIsPartOfMasterHybrisOrder(true);
			}
			else
			{
				target.setIsPartOfMasterHybrisOrder(false);
			}
		}
		if(source.getQuoteNumber() != null)
		{
			target.setQuoteNumber(source.getQuoteNumber());
		}
		if (null != source.getIsExpedited())
		{
			target.setIsExpedited(source.getIsExpedited());
		}
		if (null != source.getChooseLift())
		{
			target.setChooseLift(source.getChooseLift());
		}
		if (null != source.getIsHybrisOrder())
		{
			target.setIsHybrisOrder(source.getIsHybrisOrder());
		}
		if (null != source.getTotalDiscountAmount())
		{
			target.setTotalDiscountAmount(createPrice(source, source.getTotalDiscountAmount()));
		}
		if (null != source.getShippingFreight())
		{
			target.setShippingFreight(source.getShippingFreight());
		}
		if (null != source.getDeliveryFreight())
		{
			target.setDeliveryFreight(source.getDeliveryFreight());
		}

		final PointOfServiceData sessionStore = ((null != storeSessionFacade.getSessionStore())
				? storeSessionFacade.getSessionStore()
				: target.getPointOfService());
		if (null != sessionStore && null != sessionStore.getStoreId())
		{
			final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
			final Map<String, String> hubStoreShippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);
			if (null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0))
			{
				if (null != shippingFee.get(sessionStore.getHubStores().get(0)) || null != hubStoreShippingFee.get(sessionStore.getHubStores().get(0)))
				{
					target.setIsShippingFeeBranch(Boolean.TRUE);
				}				
				else if(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SiteoneCoreConstants.ITEM_LEVEL_SHIPPING_FEE_BRANCHES,
						storeSessionFacade.getSessionStore().getHubStores().get(0))
						&& ((target.getOrderType() != null && target.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)
						|| (target.getOrderType() == null)) && target.getShippingFreight() != null))
				{
					target.setIsShippingFeeBranch(Boolean.TRUE);
					target.setIsItemLevelShippingFeeBranch(Boolean.TRUE);
				}
				else
				{
					target.setIsShippingFeeBranch(Boolean.FALSE);
				}
			}
			else
			{
				target.setIsShippingFeeBranch(Boolean.FALSE);
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(target.getIsShippingFeeBranch()))
			{
				target.setIsTampaBranch(Boolean.TRUE);
			}
			else
			{
				target.setIsTampaBranch(Boolean.FALSE);
			}

			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
					sessionStore.getStoreId()) && BooleanUtils.isNotTrue(target.getIsShippingFeeBranch()))
			{
				target.setIsLABranch(Boolean.TRUE);
			}
			else
			{
				target.setIsLABranch(Boolean.FALSE);
			}
			
			target.setIsInDeliveryFeePilot(siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("SiteoneDeliveryFeeFeatureSwitch", sessionStore.getStoreId()));

		}
		
		boolean splitMixedCart = false;
		PointOfServiceData homestore= storeSessionFacade.getSessionStore();
		if(!userFacade.isAnonymousUser() && null != homestore && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", homestore.getStoreId()))
		{
			splitMixedCart=true;
		}

		if (CollectionUtils.isNotEmpty(target.getEntries()))
		{
			final List<OrderEntryData> pickupAndDeliveryEntries = new ArrayList<>();
			final List<OrderEntryData> shippingOnlyEntries = new ArrayList<>();
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
				
				if (orderEntryData.getProduct() != null && orderEntryData.getProduct().getFullfilledStoreType() != null 
						&& (orderEntryData.getProduct().getFullfilledStoreType()).equalsIgnoreCase("HubStore"))
				{
					shippingOnlyEntries.add(orderEntryData);
				}
				else if (orderEntryData.getProduct() != null && BooleanUtils.isNotTrue(orderEntryData.getProduct().getOutOfStockImage()))
				{
					pickupAndDeliveryEntries.add(orderEntryData);
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
			
			if(splitMixedCart && !CollectionUtils.isEmpty(shippingOnlyEntries) && !CollectionUtils.isEmpty(pickupAndDeliveryEntries)
					&& ((target.getOrderType() != null && !target.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)) || target.getOrderType() == null))
			{
				target.setShippingOnlyEntries(shippingOnlyEntries);
				target.setPickupAndDeliveryEntries(pickupAndDeliveryEntries);
				if(source.getPickupAddress() != null)
					target.setOrderType(OrderTypeEnum.PICKUP.getCode());
				else
				   target.setOrderType(OrderTypeEnum.DELIVERY.getCode());
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


			if (null != source.getFreight())
			{
				final Double freightCharges = Double.valueOf(source.getFreight());
				final BigDecimal roundedValue = getRoundedFreightCharge(freightCharges);
				final PriceData freightPriceData = createPrice(source, freightCharges);
				target.setFreight(roundedValue.toString());
				target.setDeliveryCost(freightPriceData);
				lclTotalPriceWithTaxnFreight = totalPriceWithTax.add(BigDecimal.valueOf(freightCharges));
			}
			else
			{
				lclTotalPriceWithTaxnFreight = totalPriceWithTax;
			}
			final Double deliveryFreightCharges = null != source.getDeliveryFreight() ? Double.valueOf(source.getDeliveryFreight())
					: Double.valueOf(0.00);
			if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES, storeSessionFacade.getSessionStore().getStoreId())
					&& deliveryFreightCharges != null)
			{
				final BigDecimal roundedValue = getRoundedFreightCharge(deliveryFreightCharges);
				target.setDeliveryFreight(roundedValue.toString());
				lclTotalPriceWithTaxnFreight = totalPriceWithTax.add(BigDecimal.valueOf(deliveryFreightCharges));
			}
			final Double shippingFreightCharges = null != source.getShippingFreight() ? Double.valueOf(source.getShippingFreight())
					: Double.valueOf(0.00);

			if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES, storeSessionFacade.getSessionStore().getStoreId())
					&& shippingFreightCharges != null)
			{
				final BigDecimal roundedValue = getRoundedFreightCharge(shippingFreightCharges);
				target.setShippingFreight(roundedValue.toString());
				if (deliveryFreightCharges != null)
				{
					final BigDecimal totalPriceShipDelivery = roundedValue.add(BigDecimal.valueOf(deliveryFreightCharges));
					lclTotalPriceWithTaxnFreight = totalPriceWithTax.add(totalPriceShipDelivery);
				}
				else
				{
					lclTotalPriceWithTaxnFreight = totalPriceWithTax.add(BigDecimal.valueOf(shippingFreightCharges));
				}
			}
			
			if(splitMixedCart && !CollectionUtils.isEmpty(shippingOnlyEntries) && !CollectionUtils.isEmpty(pickupAndDeliveryEntries) && shippingFreightCharges != null
					&& target.getDeliveryAddress() !=null)
			{
				final BigDecimal roundedValue = getRoundedFreightCharge(shippingFreightCharges);
				target.setShippingFreight(roundedValue.toString());
				lclTotalPriceWithTaxnFreight = totalPriceWithTax.add(BigDecimal.valueOf(shippingFreightCharges));
			}
			
			if(splitMixedCart && !CollectionUtils.isEmpty(shippingOnlyEntries) && !CollectionUtils.isEmpty(pickupAndDeliveryEntries) && deliveryFreightCharges != null
					&& target.getDeliveryAddress() !=null)
			{
				final BigDecimal roundedValue = getRoundedFreightCharge(deliveryFreightCharges);
				target.setDeliveryFreight(roundedValue.toString());
				lclTotalPriceWithTaxnFreight = lclTotalPriceWithTaxnFreight.add(BigDecimal.valueOf(deliveryFreightCharges));
			}

			target.getTotalPriceWithTax().setValue(lclTotalPriceWithTaxnFreight);
			target.getTotalPriceWithTax().setFormattedValue(fmt.format(lclTotalPriceWithTaxnFreight));

		}


		//Payment info
		if (CollectionUtils.isNotEmpty(source.getPaymentInfoList()))
		{
			final SiteoneCreditCardPaymentInfoModel paymentInfo = source.getPaymentInfoList().get(0);
			target.setSiteOnePaymentInfoData(siteOnePaymentInfoDataConverter.convert(paymentInfo));

		}
		else if (CollectionUtils.isNotEmpty(source.getPoaPaymentInfoList()))
		{
			final SiteonePOAPaymentInfoModel poaPaymentInfo = source.getPoaPaymentInfoList().get(0);
			target.setSiteOnePOAPaymentInfoData(siteOnePOAPaymentInfoDataConverter.convert(poaPaymentInfo));
		}
		else
		{
			final SiteOnePaymentInfoData paymentData = new SiteOnePaymentInfoData();
			paymentData.setPaymentType("2");
			target.setSiteOnePaymentInfoData(paymentData);
		}
		if (target.getOrderType() != null && target.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
		{
			target.setIsNationalShipping(
					BooleanUtils.isFalse(target.getIsTampaBranch()) && BooleanUtils.isFalse(target.getIsLABranch()));
		}
	}

	public BigDecimal getRoundedFreightCharge(final Double freight)
	{
		final BigDecimal roundedDeliveryFreightCharges = BigDecimal.valueOf(freight);
		return roundedDeliveryFreightCharges.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * @return the siteOneCheckoutRequestedDateUtils
	 */
	public SiteOneCheckoutRequestedDateUtils getSiteOneCheckoutRequestedDateUtils()
	{
		return siteOneCheckoutRequestedDateUtils;
	}

	/**
	 * @param siteOneCheckoutRequestedDateUtils
	 *           the siteOneCheckoutRequestedDateUtils to set
	 */
	public void setSiteOneCheckoutRequestedDateUtils(final SiteOneCheckoutRequestedDateUtils siteOneCheckoutRequestedDateUtils)
	{
		this.siteOneCheckoutRequestedDateUtils = siteOneCheckoutRequestedDateUtils;
	}

	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}
}
