/**
 *
 */
package com.siteone.facades.checkout.impl;

import de.hybris.platform.b2b.enums.CheckoutPaymentType;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.impl.DefaultB2BCheckoutFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.ewallet.data.PaymentconfigData;
import de.hybris.platform.commercefacades.ewallet.data.SelectOptionforPaymentData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOnePaymentUserData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeAndBranchOrderEntryGroupData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.SiteOneGuestUserData;
import de.hybris.platform.commercefacades.user.data.SiteOnePOAPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commercefacades.user.data.SiteoneFulfilmentData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.externaltax.ExternalTaxesService;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.SiteonePOAPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.siteone.checkout.facades.utils.SiteOneCheckoutRequestedDateUtils;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.core.deliveryfee.service.SiteoneDeliveryFeeService;
import com.siteone.core.deliveryfee.service.SiteoneCADeliveryFeeService;
import com.siteone.core.enums.MeridianTimeEnum;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.enums.SiteOnePaymentMethodEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.ewallet.service.SiteOneEwalletService;
import com.siteone.core.exceptions.EwalletNotFoundException;
import com.siteone.core.kount.service.impl.DefaultSiteOneKountService;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.form.SiteOneGCContactFacadeForm;
import com.siteone.facades.checkout.form.SiteOneOrderTypeFacadeForm;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.address.data.SiteOneWsAddressResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.services.ue.SiteOneAddressWebService;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOneB2BCheckoutFacade extends DefaultB2BCheckoutFacade implements SiteOneB2BCheckoutFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneB2BCheckoutFacade.class);
	protected static final Map<String, String> SITEONE_CARD_TYPES = new HashMap<>();
	private static final String DELIVERY = "DELIVERY";
	private static final String PICKUP = "PICKUP";
	private static final String AM = "AM";
	private static final String PM = "PM";
	private static final String ANYTIME = "ANYTIME";
	private static final String SESSION_STORE = "sessionStore";
	private static final String CAYAN_PAYMENT_WEB_API = "siteone.cayan.payment.webApi";
	private static final String HOMEOWNER_CODE = "homeOwner.trade.class.code";
	private final static Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{3}?[-]\\d{3}?[-]\\d{4}");
	private static final String AGRONOMIC_MAINTENANCE = "Agronomic Maintenance";
	private static final String MAINTENANCE = "Maintenance";
	private static final String AGRONOMICS = "Agronomics";
	private static final String IRRIGATION = "Irrigation";
	private static final String NURSERY = "Nursery";
	private static final String LANDSCAPE_SUPPLY = "Landscape Supply";
	private static final String LANDSCAPE_SUPPLIES = "Landscape Supplies";
	private static final String HARDCSAPES_OUTDOOR_LIVING = "Hardscapes & Outdoor Living";
	private static final String HARDSCAPE = "Hardscape";
	private static final String LIGHTING = "Lighting";
	private static final String OUTDOOR_LIGHTING = "Outdoor Lighting";
	private static final String TOOLS_EQUIPMENT_AND_SAFETY = "Tools, Equipment & Safety";
	private static final String TOOLS_AND_SAFETY = "Tools and Safety";
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String DC_SHIPPING_FEE_BRANCHES = "DCShippingFeeBranches";
	private static final String DC_SHIPPING_THRESHOLD = "DCShippingThreshold";
	final int digits = 2;
	private final String COUNTRY_CODE_US = "US";
	private final String COUNTRY_CODE_CA = "CA";

	private static final String HUB_STORE = "HubStore";

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "regulatoryStatesCronJobService")
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;
	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	//end webserv

	private UserFacade userFacade;
	private CartService cartService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private MessageSource messageSource;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "ewalletService")
	private SiteOneEwalletService ewalletService;

	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;

	@Resource(name = "commerceCartCalculationStrategy")
	private CommerceCartCalculationStrategy commerceCartCalculationStrategy;

	private SiteOneCheckoutRequestedDateUtils siteOneCheckoutRequestedDateUtils;

	@Resource(name = "externalTaxesService")
	private ExternalTaxesService externalTaxesService;

	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "siteOnePaymentInfoDataReverseConverter")
	private Converter<SiteOnePaymentInfoData, SiteoneCreditCardPaymentInfoModel> siteOnePaymentInfoDataReverseConverter;

	@Resource(name = "siteOnePOAPaymentInfoDataReverseConverter")
	private Converter<SiteOnePOAPaymentInfoData, SiteonePOAPaymentInfoModel> siteOnePOAPaymentInfoDataReverseConverter;

	@Resource(name = "siteOneEwalletReverseConverter")
	private Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter;

	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;

	@Resource(name = "siteOneKountService")
	private DefaultSiteOneKountService defaultSiteOneKountService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "customerFacade")
	private SiteOneCustomerFacade customerFacade;

	@Resource(name = "deliveryModeService")
	private DeliveryModeService deliveryModeService;

	@Resource(name = "siteoneDeliveryFeeService")
	SiteoneDeliveryFeeService siteoneDeliveryFeeService;
	
	@Resource(name = "siteoneCADeliveryFeeService")
	SiteoneCADeliveryFeeService siteoneCADeliveryFeeService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "siteOneAddressWebService")
	private SiteOneAddressWebService siteOneAddressWebService;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;
	
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}
	
	public void setSiteOneFeatureSwitchCacheService(SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}
	
	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
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

	@Override
	public CartData updateCheckoutCart(final CartData cartData) throws ResourceAccessException
	{
		try {
		final CartModel cartModel = getCart();
		if (cartModel == null)
		{
			LOG.error("updateCheckoutCart - Cart Model is null");
			return null;
		}
		final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
		Map<String, Boolean> fulfilmentStatus = new HashMap<>();
		if (CollectionUtils.isNotEmpty(cartData.getDeliveryModeAndBranchOrderGroups()))
		{
			fulfilmentStatus = populateFulfilmentStatus(cartData);
		}
		else
		{
			cartModel.setOrderType(OrderTypeEnum.PICKUP);
			fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_PICKUP, true);
			fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_DELIVERY, false);
			fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_SHIPPING, false);
		}
		final boolean isPunchoutDelivery = preparePunchoutDeliveryInfo(cartModel, cartData, parentUnit);
		AddressModel punchoutDeliveryAddress = null;
		if (isPunchoutDelivery)
		{
			fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_DELIVERY, true);
			fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_PICKUP, false);
			punchoutDeliveryAddress = createDeliveryAddressForPunchout(cartModel);
		}

		// set purchase order number
		if (cartData.getPurchaseOrderNumber() != null)
		{
			cartModel.setPurchaseOrderNumber(cartData.getPurchaseOrderNumber());
		}

		//Set Billing Address
		if (null != cartModel.getOrderingAccount())
		{
			cartModel.setPaymentAddress(cartModel.getOrderingAccount().getReportingOrganization().getBillingAddress());
		}
		if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_PICKUP))
		{
			if (cartData.getPickupInstruction() != null)
			{
				cartModel.setPickupInstruction(cartData.getPickupInstruction());
			}
			if (cartData.getContactPerson() != null)
			{
				cartModel.setContactPerson((B2BCustomerModel) b2bCustomerService
						.getUserForUID(cartData.getContactPerson().getEmail().trim().toLowerCase()));
			}

			if (cartData.getPickupAddress() != null && cartData.getPointOfService() != null)
			{
				cartModel.setPickupAddress(storeFinderService.getStoreForId(cartData.getPointOfService().getStoreId()).getAddress());
			}
		}

		// set delivery address
		if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
		{
			if (cartData.getDeliveryAddress() != null)
			{
				setDeliveryAddress(cartData.getDeliveryAddress());
			}
			if (cartData.getDeliveryContactPerson() != null)
			{
				cartModel.setDeliveryContactPerson(
						(B2BCustomerModel) b2bCustomerService.getUserForUID(cartData.getDeliveryContactPerson().getEmail()));
			}
			if (cartData.getDeliveryInstruction() != null)
			{
				cartModel.setDeliveryInstruction(cartData.getDeliveryInstruction());
			}
			if (cartData.getIsSameaddressforDelivery() != null && cartData.getIsSameaddressforDelivery().booleanValue())
			{
				if (cartModel.getGuestContactPerson() != null)
				{
					cartModel.setGuestDeliveryContactPerson(cartModel.getGuestContactPerson());
				}

			}
			if(cartModel.getPickupAddress() != null)
			{
				cartModel.setPickupAddress(null);
			}

		}

		// set shipping address
		if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
		{
			if (cartData.getShippingAddress() != null)
			{
				setShippingAddress(cartData.getShippingAddress());
			}
			if (cartData.getShippingContactPerson() != null)
			{
				cartModel.setShippingContactPerson(
						(B2BCustomerModel) b2bCustomerService.getUserForUID(cartData.getShippingContactPerson().getEmail()));
			}
			if (cartData.getShippingInstruction() != null)
			{
				cartModel.setShippingInstruction(cartData.getShippingInstruction());
			}
			if (cartData.getIsSameaddressforParcelShip() != null && cartData.getIsSameaddressforParcelShip().booleanValue())
			{
				if (cartModel.getGuestContactPerson() != null)
				{
					cartModel.setGuestShippingContactPerson(cartModel.getGuestContactPerson());

				}
			}

		}


		if (cartData.getPointOfService() != null)
		{
			cartModel.setPointOfService(storeFinderService.getStoreForId(cartData.getPointOfService().getStoreId()));
		}

		if (cartData.getContactPerson() != null)
		{
			cartModel.setContactPerson(
					(B2BCustomerModel) b2bCustomerService.getUserForUID(cartData.getContactPerson().getEmail().trim().toLowerCase()));
		}
		if (cartData.getIsShippingFeeBranch() != null)
		{
			cartModel.setIsShippingFeeBranch(cartData.getIsShippingFeeBranch());
		}
		if (cartData.getRequestedDate() != null)
		{
			cartModel.setRequestedDate(cartData.getRequestedDate());
		}
		if (cartData.getIsExpedited() != null && cartData.getIsExpedited().booleanValue())
		{
			cartModel.setIsExpedited(cartData.getIsExpedited());
		}
		if (cartData.getFreight() != null)
		{
			cartModel.setFreight(cartData.getFreight());
		}
		if (cartData.getDeliveryFreight() != null)
		{
			cartModel.setDeliveryFreight(cartData.getDeliveryFreight().toString());

		}
		if (cartData.getShippingFreight() != null)
		{
			cartModel.setShippingFreight(cartData.getShippingFreight().toString());
		}

		if (cartData.getChooseLift() != null && cartData.getChooseLift().booleanValue())
		{
			cartModel.setChooseLift(cartData.getChooseLift());
		}


		if (AM.equalsIgnoreCase(cartData.getRequestedMeridian()))
		{
			cartModel.setRequestedMeridian(enumerationService.getEnumerationValue(MeridianTimeEnum.class, AM));
		}
		else if (PM.equalsIgnoreCase(cartData.getRequestedMeridian()))
		{
			cartModel.setRequestedMeridian(enumerationService.getEnumerationValue(MeridianTimeEnum.class, PM));
		}
		else if (ANYTIME.equalsIgnoreCase(cartData.getRequestedMeridian()))
		{
			cartModel.setRequestedMeridian(enumerationService.getEnumerationValue(MeridianTimeEnum.class, ANYTIME));
		}
		if (cartData.getSpecialInstruction() != null)
		{
			cartModel.setSpecialInstruction(cartData.getSpecialInstruction());
		}
		if (cartData.getPurchaseOrderNumber() != null)
		{
			cartModel.setPurchaseOrderNumber(cartData.getPurchaseOrderNumber());
		}

		if (parentUnit != null && parentUnit.getIsPunchOutAccount() != null && parentUnit.getIsPunchOutAccount().booleanValue())
		{
			cartModel.setPaymentType(CheckoutPaymentType.ACCOUNT);
		}
		if (cartData.getIsSameaddressforParcelShip() != null)
		{
			cartModel.setIsSameaddressforParcelShip(cartData.getIsSameaddressforParcelShip());
		}

		if(!(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
				((PointOfServiceData) getSessionService().getAttribute(SESSION_STORE)).getStoreId())))
		{
			if (cartData.getDeliveryAddress() != null)
			{
				setDeliveryAddress(cartData.getDeliveryAddress());
			}
		}
		if(!cartModel.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING") &&
				(CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) || CollectionUtils.isEmpty(cartData.getShippingOnlyEntries())))
		{
			cartModel.setShippingFreight("0.00");
			cartModel.setShippingAddress(null);
			cartModel.setShippingContactPerson(null);
		}

		if (isPunchoutDelivery && punchoutDeliveryAddress != null)
		{
			cartModel.setDeliveryAddress(punchoutDeliveryAddress);
		}

		getModelService().save(cartModel);
		if (cartData.getOrderType() != null)
		{
			commerceCartCalculationStrategy.calculateCart(cartModel);
		}
		getModelService().refresh(cartModel);
		if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
		{
			cartModel.setCalculated(Boolean.TRUE);
		}
		getModelService().save(cartModel);
		final CartData updatedCartData = this.getCheckoutCart();

		if (updatedCartData.getTotalTax() != null)
		{

			cartData.setTotalTax(updatedCartData.getTotalTax());
		}
		if (updatedCartData.getTotalPriceWithTax() != null)
		{

			cartData.setTotalPriceWithTax(updatedCartData.getTotalPriceWithTax());
		}
		return updatedCartData;
	
	} catch (Exception e) {
		LOG.error("Exception occurred in updateCheckoutCart: " + e.getMessage(), e);
		return null;
	   }
	}
	@Override
	public CartData getCheckoutCart()
	{
		final CartData cartData = getCartFacade().getSessionCart();
		if (cartData != null)
		{
			cartData.setDeliveryAddress(getDeliveryAddress());
			final CartModel cartModel = getCart();

			if (null != cartModel && !(CollectionUtils.isEmpty(cartModel.getEntries())))
			{
				for (final AbstractOrderEntryModel entry : cartModel.getEntries())
				{
					entry.setDeliveryPointOfService(
							storeFinderService.getStoreForId(storeSessionFacade.getSessionStore().getStoreId()));
					getModelService().save(entry);
					getModelService().refresh(entry);
				}
			}

			if (null != cartModel && null != cartModel.getPaymentAddress())
			{
				cartData.setBillingAddress(getAddressConverter().convert((cartModel.getPaymentAddress())));
			}
			if (cartData.getRequestedDate() != null)
			{
				cartData.setRequestedDate(siteOneCheckoutRequestedDateUtils.convertDateToUTC(cartData.getRequestedDate()));
			}
			boolean splitMixedCart = CollectionUtils.isNotEmpty(cartData.getPickupAndDeliveryEntries()) 
					&& CollectionUtils.isNotEmpty(cartData.getShippingOnlyEntries());
			final Map<String, Boolean> fulfilmentStatus = populateFulfilmentStatus(cartData);
			if (cartData.getOrderType() != null && (cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)
					|| splitMixedCart))
			{
				final String shippingRate = getShippingRate(cartData);
				if(fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY)
						|| cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_DELIVERY))
				{
					cartModel.setShippingFreight(shippingRate);
				}
				else
				{
					cartModel.setFreight(shippingRate);
				}

				if (!CollectionUtils.isEmpty(cartModel.getEntries()))
				{
					for (final AbstractOrderEntryModel entry : cartModel.getEntries())
					{

						if((!splitMixedCart || !BooleanUtils.isNotTrue(entry.getIsShippingOnlyProduct()) 
								|| cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)) && null != storeSessionFacade.getSessionStore().getHubStores()
								&& null != storeSessionFacade.getSessionStore().getHubStores().get(0))
						{
							final PointOfServiceModel pos = storeFinderService
									.getStoreForId(storeSessionFacade.getSessionStore().getHubStores().get(0));

							if (null != pos)
							{
								entry.setDeliveryPointOfService(pos);
								entry.setFullfilledStoreType(HUB_STORE);
								entry.setDeliveryMode(
										deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.SHIPPING_DELIVERYMODE_CODE));
								getModelService().save(entry);
							}
						}
					}
				}
			}
			else if (cartData.getOrderType() != null
					&& cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_PICKUP))
			{
				cartModel.setFreight(null);
				getModelService().save(cartModel);
			}

		}
		return cartData;
	}

	@Override
	public CartData populateFreights(final CartData cartData, final Map<String, Boolean> fulfilmentStatus)
	{
		final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
		if (cartData != null && BooleanUtils.isTrue(fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
				&& cartData.getShippingFreight() != null)
		{
			final String shippingRate = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);
			final BigDecimal total = cartData.getTotalPriceWithTax().getValue().add(BigDecimal.valueOf(Double.parseDouble(shippingRate)));
			cartData.getTotalPriceWithTax().setValue(total);
			cartData.getTotalPriceWithTax().setFormattedValue(fmt.format(total));
		}
		if (cartData != null && BooleanUtils.isNotTrue(fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
				&& cartData.getFreight() != null)
		{
			final String freight = cartData.getFreight();
			final BigDecimal total = cartData.getTotalPriceWithTax().getValue().subtract(BigDecimal.valueOf(Double.parseDouble(freight)));
			cartData.getTotalPriceWithTax().setValue(total);
			cartData.getTotalPriceWithTax().setFormattedValue(fmt.format(total));
		}
		return cartData;
	}
	
	@Override
	public List<String> getStoreHolidayDates()
	{
		final PointOfServiceModel pos = storeFinderService
				.getStoreForId(storeSessionFacade.getSessionStore().getStoreId());
	    if (pos != null && pos.getSiteoneHolidays() != null) 
	    {
	        return pos.getSiteoneHolidays();
	    } 
	    else {
	        return null;
	    }
	}
	
	@Override
	public boolean getIsCCDisabledAtDC(CartData cartData)
	{
		if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase("PARCEL_SHIPPING") && storeSessionFacade.getSessionStore() != null
				&& storeSessionFacade.getSessionStore().getHubStores() != null
				&& storeSessionFacade.getSessionStore().getHubStores().get(0) != null
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("CCDisabledDCShippingBranches",
						storeSessionFacade.getSessionStore().getHubStores().get(0)))
		{
			return true;
		}
		return false;
	}

	@Override
	public void updateDeliveryFee(final CartData cartData)
	{
		if (StringUtils.isNotBlank(cartData.getOrderType())
				&& cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_DELIVERY))
		{
			boolean isHomeOwner = false;
			boolean isGuest = false;
			final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
			final String homeownerCode = configurationService.getConfiguration().getString(HOMEOWNER_CODE);
			if (null != sessionShipTo && sessionShipTo.getTradeClass() != null)
			{
				isHomeOwner = sessionShipTo.getTradeClass().equalsIgnoreCase(homeownerCode) ? true : false;
			}
			if (userService.getCurrentUser() != null && userService.isAnonymousUser(userService.getCurrentUser()))
			{
				isGuest = true;
			}
			try
			{
				final String deliveryFreightCharge = getFreightCharge(cartData, isHomeOwner, isGuest);
				if (StringUtils.isNotBlank(deliveryFreightCharge))
				{
					populateFreightToCart(deliveryFreightCharge, SiteoneCoreConstants.DELIVERYMODE_DELIVERY, cartData);
				}
				else
				{
					cartData.setDeliveryCost(null);
					cartData.setFreight(null);
				}
			}
			catch (final IOException exception)
			{
				LOG.error("Unable to fetch the delivery fee details", exception);
				cartData.setDeliveryCost(null);
				cartData.setFreight(null);
			}
		}
	}

	@Override
	public boolean setDeliveryAddress(final AddressData addressData) throws ResourceAccessException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
			{
				AddressModel addressModel = null;
				if (addressData != null)
				{
					addressModel = addressData.getId() == null ? createDeliveryAddressModel(addressData, cartModel)
							: getDeliveryAddressModelForCode(addressData.getId());
				}

				cartModel.setDeliveryAddress(addressModel);
				return true;
			}
			else
			{
				if (cartModel.getDeliveryAddress() != null)
				{ // For Guest the Delivery address will be populated to
				  // cart model during contact save
					return true;
				}
			}

		}

		return false;
	}

	@Override
	public boolean setShippingAddress(final AddressData addressData) throws ResourceAccessException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
			{
				AddressModel addressModel = null;
				if (addressData != null)
				{
					addressModel = addressData.getId() == null ? createDeliveryAddressModel(addressData, cartModel)
							: getDeliveryAddressModelForCode(addressData.getId());
				}

				cartModel.setShippingAddress(addressModel);
				return true;
			}
			else
			{
				if (cartModel.getShippingAddress() != null)
				{ // For Guest the Shipping address will be populated to
				  // cart model during contact save
					return true;
				}
			}


		}

		return false;
	}

	@Override
	protected AddressModel getDeliveryAddressModelForCode(final String code)
	{
		Assert.notNull(code, "Parameter code cannot be null.");
		final CartData cartData = getCartFacade().getSessionCart();
		final Map<String, Boolean> fulfilmentStatus = populateFulfilmentStatus(cartData);
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			if (OrderTypeEnum.DELIVERY.equals(cartModel.getOrderType())
					|| OrderTypeEnum.PARCEL_SHIPPING.equals(cartModel.getOrderType())
					|| fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING)
					|| fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
			{
				if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
				{
					final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();

					final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sessionShipTo.getUid());

					final Collection<AddressModel> shippingAddresses = ((SiteOneCustomerAccountService) customerAccountService)
							.getShippingAddressesForUnit(b2bUnitModel);

					if (CollectionUtils.isNotEmpty(shippingAddresses))
					{
						for (final AddressModel address : shippingAddresses)
						{
							if (code.equals(address.getPk().toString()))
							{
								return address;
							}
						}
					}
				}
			}
			else if (OrderTypeEnum.PICKUP.equals(cartModel.getOrderType()))
			{
				final PointOfServiceModel sessionPos = storeFinderService
						.getStoreForId(storeSessionFacade.getSessionStore().getStoreId());

				if (code.equals(sessionPos.getAddress().getPk().toString()))
				{
					return sessionPos.getAddress();
				}
			}


		}
		return null;
	}

	@Override
	public OrderData placeOrder() throws InvalidCartException
	{
		return placeOrder(SalesApplication.WEB);
	}

	@Override
	public OrderData placeOrder(final SalesApplication salesApplication) throws InvalidCartException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null
				&& (cartModel.getUser().equals(getCurrentUserForCheckout()) || getCheckoutCustomerStrategy().isAnonymousCheckout()))
		{
			beforePlaceOrder(cartModel);
			updateCartForPunchoutSession(cartModel);
			setMixedCartOrderType(cartModel);
			siteOneOrderService.updateHubStorePosForParcelShippingOrder(cartModel);


			final CommerceCheckoutParameter parameter = createCommerceCheckoutParameter(cartModel, true);
			parameter.setSalesApplication(salesApplication);
			final OrderModel orderModel = getCommerceCheckoutService().placeOrder(parameter).getOrder();

			afterPlaceOrder(cartModel, orderModel);
			if (orderModel != null)
			{
				return getOrderConverter().convert(orderModel);
			}
		}
		return null;
	}

	@Override
	public OrderData placeOrderWithOnlinePayment(final SiteOnePaymentInfoData siteOnePaymentData) throws Exception
	{
		return placeOrderWithOnlinePayment(siteOnePaymentData, SalesApplication.WEB);
	}

	@Override
	public OrderData placeOrderWithOnlinePayment(final SiteOnePaymentInfoData siteOnePaymentData,
			final SalesApplication salesApplication) throws Exception
	{
		final CartModel cartModel = getCart();

		if (cartModel != null
				&& (cartModel.getUser().equals(getCurrentUserForCheckout()) || getCheckoutCustomerStrategy().isAnonymousCheckout()))
		{
			if (null != siteOnePaymentData && null != siteOnePaymentData.getSaveCard() && siteOnePaymentData.getSaveCard().booleanValue())
			{
				saveEwalletCardData(siteOnePaymentData);
			}

			if (null != siteOnePaymentData)
			{
				final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = setCreditCardPaymentInfo(siteOnePaymentData, cartModel);
				final List<SiteoneCreditCardPaymentInfoModel> paymentInfoList = new ArrayList();
				paymentInfoList.add(creditCardPaymentInfo);
				cartModel.setPaymentInfoList(paymentInfoList);
			}

			beforePlaceOrder(cartModel);
			setMixedCartOrderType(cartModel);
			siteOneOrderService.updateHubStorePosForParcelShippingOrder(cartModel);

			final CommerceCheckoutParameter parameter = createCommerceCheckoutParameter(cartModel, true);
			parameter.setSalesApplication(salesApplication);
			final OrderModel orderModel = getCommerceCheckoutService().placeOrder(parameter).getOrder();

			afterPlaceOrder(cartModel, orderModel);
			if (orderModel != null)
			{
				return getOrderConverter().convert(orderModel);
			}
		}
		return null;
	}


	@Override
	public OrderData placeOrderWithPOAPayment(final SiteOnePOAPaymentInfoData siteOnePaymentData) throws Exception
	{
		return placeOrderWithPOAPayment(siteOnePaymentData, SalesApplication.WEB);
	}

	@Override
	public OrderData placeOrderWithPOAPayment(final SiteOnePOAPaymentInfoData siteOnePaymentData,
			final SalesApplication salesApplication) throws Exception
	{
		final CartModel cartModel = getCart();

		if (cartModel != null
				&& (cartModel.getUser().equals(getCurrentUserForCheckout()) || getCheckoutCustomerStrategy().isAnonymousCheckout()))
		{

			final SiteonePOAPaymentInfoModel poaPaymentInfo = setPOAPaymentInfo(siteOnePaymentData, cartModel);
			final List<SiteonePOAPaymentInfoModel> paymentInfoList = new ArrayList();
			paymentInfoList.add(poaPaymentInfo);
			cartModel.setPoaPaymentInfoList(paymentInfoList);
			beforePlaceOrder(cartModel);
			setMixedCartOrderType(cartModel);
			siteOneOrderService.updateHubStorePosForParcelShippingOrder(cartModel);

			final CommerceCheckoutParameter parameter = createCommerceCheckoutParameter(cartModel, true);
			parameter.setSalesApplication(salesApplication);
			final OrderModel orderModel = getCommerceCheckoutService().placeOrder(parameter).getOrder();

			afterPlaceOrder(cartModel, orderModel);
			if (orderModel != null)
			{
				return getOrderConverter().convert(orderModel);
			}
		}
		return null;
	}

	public SiteonePOAPaymentInfoModel setPOAPaymentInfo(final SiteOnePOAPaymentInfoData siteOnePaymentData,
			final CartModel cartModel)
	{

		final SiteonePOAPaymentInfoModel poaPaymentInfo = siteOnePOAPaymentInfoDataReverseConverter.convert(siteOnePaymentData);
		poaPaymentInfo.setUser(cartModel.getUser());
		poaPaymentInfo.setCode(cartModel.getCode());
		poaPaymentInfo.setAmountCharged(cartModel.getTotalPrice());
		poaPaymentInfo.setPaymentType(siteOnePaymentData.getPaymentType());


		getModelService().save(poaPaymentInfo);
		return poaPaymentInfo;
	}

	public SiteoneCreditCardPaymentInfoModel setCreditCardPaymentInfo(final SiteOnePaymentInfoData siteOnePaymentData,
			final CartModel cartModel)
	{

		final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = siteOnePaymentInfoDataReverseConverter
				.convert(siteOnePaymentData);
		creditCardPaymentInfo.setUser(cartModel.getUser());
		creditCardPaymentInfo.setCode(cartModel.getCode());


		getModelService().save(creditCardPaymentInfo);
		return creditCardPaymentInfo;
	}

	public void saveEwalletCardData(final SiteOnePaymentInfoData siteOnePaymentData) throws Exception
	{
		final SiteOneEwalletData ewalletData = new SiteOneEwalletData();
		ewalletData.setCardExpirationDate(siteOnePaymentData.getExpDate());
		ewalletData.setCreditCardType(siteOnePaymentData.getApplicationLabel());
		ewalletData.setLast4CreditcardDigits(siteOnePaymentData.getCardNumber());
		ewalletData.setNameOnCard(siteOnePaymentData.getNameOnCard());
		ewalletData.setValutToken(siteOnePaymentData.getVaultToken());
		ewalletData.setStreetAddress(siteOnePaymentData.getAddress());
		ewalletData.setZipCode(siteOnePaymentData.getZipCode());
		ewalletData.setActive(Boolean.valueOf(true));
		ewalletData.setNickName(siteOnePaymentData.getNickName() + "-" + siteOnePaymentData.getCardNumber());
		final String creditId = siteOneEwalletFacade.addEwalletDetails(ewalletData);
		siteOnePaymentData.setCusttreeNodeCreditId(creditId);
	}

	@Override
	public SiteOnePaymentUserData getPaymentOptions()
	{
		final SiteOnePaymentUserData siteOnePaymentUserData = new SiteOnePaymentUserData();
		final CartData cartData = getCheckoutCart();
		siteOnePaymentUserData.setTotalPrice(cartData.getTotalPriceWithTax());
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{

			siteOnePaymentUserData.setIsGuestUser(Boolean.TRUE);
			final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			siteOnePaymentUserData.setCreditCode(b2bUnit.getCreditCode());
			siteOnePaymentUserData.setSiteOneCardTypes(getSiteOneCardTypes());
			setDeliveryAddressIfAvailable();
			siteOnePaymentUserData.setEwalletAddError(getSessionService().getAttribute("ewalletAddError"));
			getSessionService().removeAttribute("ewalletAddError");
			getSessionService().removeAttribute("saveCard");
			getSessionService().removeAttribute("nickName");
			final CustomerData customerData = ((DefaultSiteOneCustomerFacade) customerFacade).getCurrentCustomer();
			final PaymentconfigData paymentconfigData = siteOneEwalletFacade.populatePaymentconfig();
			siteOnePaymentUserData.setPaymentconfigData(paymentconfigData);
			final List<SelectOptionforPaymentData> siteOnePaymentMethod2 = getSiteOnePaymentMethods(b2bUnit);
			getSessionService().removeAttribute("showCCOption");
			siteOnePaymentUserData.setPaymentOption(siteOnePaymentMethod2);
			siteOnePaymentUserData.setIsPOAEnabled(Boolean.valueOf(isPOAEnabled(b2bUnit)));
			siteOnePaymentUserData.setDeliveryAddress(cartData.getDeliveryAddress());
			siteOnePaymentUserData.setPaymentInfos(userFacade.getCCPaymentInfos(true));
			final String webApi = configurationService.getConfiguration().getString(CAYAN_PAYMENT_WEB_API);
			siteOnePaymentUserData.setWebApi(webApi);
			if (null != getSessionService().getAttribute(SiteoneFacadesConstants.ASM_SESSION_PARAMETER))
			{
				siteOnePaymentUserData.setAsm(Boolean.TRUE);
			}
			else
			{
				siteOnePaymentUserData.setAsm(Boolean.FALSE);

			}

			siteOnePaymentUserData.setEWallet(siteOneEwalletFacade.filterValidEwalletData(customerData));
		}
		else
		{
			siteOnePaymentUserData.setIsGuestUser(Boolean.TRUE);
		}

		return siteOnePaymentUserData;
	}

	public List<SelectOptionforPaymentData> getSiteOnePaymentMethods(final B2BUnitModel b2bUnit)
	{
		final List<SelectOptionforPaymentData> siteOnePaymentMethod2 = new ArrayList<>();
               final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		if (isPOAEnabled(b2bUnit))
		{
			final SelectOptionforPaymentData selectOptionforPaymentData = new SelectOptionforPaymentData();
			selectOptionforPaymentData.setCode(SiteOnePaymentMethodEnum.SITEONE_ONLINE_ACCOUNT.getCode());
			selectOptionforPaymentData.setName(
					getMessageSource().getMessage("checkout.multi.option.account", null, getI18nService().getCurrentLocale()));
			selectOptionforPaymentData.setDescription(getMessageSource().getMessage("checkout.multi.option.description.account",
					null, getI18nService().getCurrentLocale()));
			selectOptionforPaymentData.setChecked(SiteOnePaymentMethodEnum.SITEONE_ONLINE_ACCOUNT.getCode());
			siteOnePaymentMethod2.add(selectOptionforPaymentData);
		}
		if (getSessionService().getAttribute("showCCOption").equals(Boolean.TRUE))
		{
			final SelectOptionforPaymentData selectOptionforPaymentData = new SelectOptionforPaymentData();
			selectOptionforPaymentData.setCode(SiteOnePaymentMethodEnum.PAY_ONLINE_WITH_CREDIT_CARD.getCode());
			selectOptionforPaymentData
					.setName(getMessageSource().getMessage("checkout.multi.option.branch", null, getI18nService().getCurrentLocale()));
			selectOptionforPaymentData.setDescription(
					getMessageSource().getMessage("checkout.multi.option.description", null, getI18nService().getCurrentLocale()));
			selectOptionforPaymentData.setChecked(SiteOnePaymentMethodEnum.PAY_ONLINE_WITH_CREDIT_CARD.getCode());
			siteOnePaymentMethod2.add(selectOptionforPaymentData);
		}
		
		final SelectOptionforPaymentData selectOptionforPaymentData = new SelectOptionforPaymentData();
		selectOptionforPaymentData.setCode(SiteOnePaymentMethodEnum.PAY_AT_BRANCH.getCode());
		selectOptionforPaymentData
				.setName(getMessageSource().getMessage("checkout.multi.option.credit", null, getI18nService().getCurrentLocale()));
		selectOptionforPaymentData.setDescription(
				getMessageSource().getMessage("checkout.multi.option.description.card", null, getI18nService().getCurrentLocale()));
		selectOptionforPaymentData.setChecked(SiteOnePaymentMethodEnum.PAY_AT_BRANCH.getCode());
		siteOnePaymentMethod2.add(selectOptionforPaymentData);
		
		return siteOnePaymentMethod2;
	}

	private boolean isPOAEnabled(final B2BUnitModel b2bUnit)
	{

		if (getSessionService().getAttribute("isPOAEnabled").equals(Boolean.TRUE))
		{

			if ((StringUtils.isNotEmpty(b2bUnit.getCreditCode())
					&& !SiteoneFacadesConstants.POA_CREDIT_CODE.contains(b2bUnit.getCreditCode()))
					&& (null != b2bUnit.getIsPayOnAccount() && b2bUnit.getIsPayOnAccount())
					|| (StringUtils.isNotEmpty(b2bUnit.getAccountGroupCode())
							&& b2bUnit.getAccountGroupCode().equalsIgnoreCase("JDC")))
			{
				if (StringUtils.isNotEmpty(b2bUnit.getCreditTermCode()) && !b2bUnit.getCreditTermCode().equalsIgnoreCase("CASH"))
				{

					return true;
				}
			}
		}

		return false;


	}


	static
	{
		SITEONE_CARD_TYPES.put("visa", "001");
		SITEONE_CARD_TYPES.put("master", "002");
		SITEONE_CARD_TYPES.put("amex", "003");
		SITEONE_CARD_TYPES.put("diners", "005");
		SITEONE_CARD_TYPES.put("maestro", "024");
	}

	protected CardTypeData createCardTypeData(final String code, final String name)
	{
		final CardTypeData cardTypeData = new CardTypeData();
		cardTypeData.setCode(code);
		cardTypeData.setName(name);
		return cardTypeData;
	}

	protected Collection<CardTypeData> getSiteOneCardTypes()
	{
		final Collection<CardTypeData> sopCardTypes = new ArrayList<>();

		final List<CardTypeData> supportedCardTypes = getSupportedCardTypes();
		for (final CardTypeData supportedCardType : supportedCardTypes)
		{

			// Add credit cards for all supported cards that have mappings for cybersource SOP
			if (SITEONE_CARD_TYPES.containsKey(supportedCardType.getCode()))
			{
				sopCardTypes
						.add(createCardTypeData(SITEONE_CARD_TYPES.get(supportedCardType.getCode()), supportedCardType.getName()));
			}
		}
		return sopCardTypes;
	}

	@Override
	public String placeEwalletOrder(final String vaultToken)
	{
		try
		{
			final SiteoneEwalletCreditCardModel creditCardModel = ewalletService.getCreditCardDetails(vaultToken);
			final SiteOneEwalletData ewalletData = new SiteOneEwalletData();
			ewalletData.setLast4CreditcardDigits(creditCardModel.getLast4Digits());
			ewalletData.setCreditCardType(creditCardModel.getCreditCardType());
			ewalletData.setValutToken(creditCardModel.getVaultToken());
			ewalletData.setCardExpirationDate(creditCardModel.getExpDate());
			getSessionService().setAttribute("ewalletData", ewalletData);
			getSessionService().setAttribute("vaultToken", creditCardModel.getVaultToken());
			getSessionService().setAttribute("isEwalletCard", Boolean.TRUE);
		}
		catch (final EwalletNotFoundException ewalletException)
		{
			LOG.error("Unable to fetch the ewallet details", ewalletException);
			return "Failure";
		}
		catch (final Exception ex)
		{
			return "Failure";
		}
		return "Success";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.checkout.SiteOneB2BCheckoutFacade#calculateTax()
	 */
	@Override
	public void calculateTax()
	{
		final CartModel cartModel = getCart();
		externalTaxesService.calculateExternalTaxes(cartModel);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.facades.checkout.SiteOneB2BCheckoutFacade#getPageTitle(de.hybris.platform.commercefacades.order.data.
	 * CartData)
	 */
	@Override
	public String getPageTitle(final CartData cartData)
	{
		final CartModel cartModel = getCartService().getSessionCart();
		cartData.setOrderType(cartModel.getOrderType().getCode());
		if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(PICKUP))
		{
			return SiteoneCoreConstants.PICKUP_PAGE;
		}
		else if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(DELIVERY))
		{
			return SiteoneCoreConstants.DELIVERY_PAGE;
		}
		else
		{
			return SiteoneCoreConstants.SHIPPING_PAGE;
		}

	}


	@Override
	public String getShippingRate(final CartData cartData)
	{
		final String defaultFreightValue = "0.0";
		String shippingFreight = "0.0";
		double subTotalValue = cartData.getSubTotal().getValue().doubleValue();
		double convertedFlateRate =0.0d;
		boolean isSplitCart = false;
		if(CollectionUtils.isNotEmpty(cartData.getPickupAndDeliveryEntries()) && CollectionUtils.isNotEmpty(cartData.getShippingOnlyEntries()))
		{
			isSplitCart = true;			
		}
		final Map<String, Boolean> fulfilmentStatus = populateFulfilmentStatus(cartData);	
		if (((null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
				|| isSplitCart)
				&& cartData.getShippingThresholdCheckData() != null)
		{
			subTotalValue = cartData.getShippingThresholdCheckData().getSelectedItemTotalShipping();
		}
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		convertedFlateRate = getThreshold(cartData);
		shippingFreight = getShippingFee(cartData);
		
		if(cartData.getIsItemLevelShippingFeeBranch() != null && cartData.getIsItemLevelShippingFeeBranch())
		{
			String newShippingFreight = isSplitCart ? getItemLevelShippingFee(cartData.getShippingOnlyEntries()) : getItemLevelShippingFee(cartData.getEntries());
			if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
			{
				cartData.setShippingFreight(newShippingFreight);
			}
			else
			{
				cartData.setFreight(newShippingFreight);
			}
			return newShippingFreight;
		}
		else if (convertedFlateRate != 0 && shippingFreight != null)
		{
			if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
			{
				if (subTotalValue < convertedFlateRate)
				{
					cartData.setShippingFreight(shippingFreight);
				}
				else
				{
					cartData.setShippingFreight(defaultFreightValue);
				}
				return cartData.getShippingFreight();
			}
			else
			{
				if (subTotalValue < convertedFlateRate)
				{
					cartData.setFreight(shippingFreight);
				}
				else
				{
					cartData.setFreight(defaultFreightValue);
				}
				return cartData.getFreight();
			}
		}
		return defaultFreightValue;

	}

	private String getShippingFee(final CartData cartData)
	{
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		PointOfServiceData pos = null;
		if (sessionStore != null && sessionStore.getHubStores() != null && sessionStore.getHubStores().get(0) != null)
		{
			pos = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(sessionStore.getHubStores().get(0));
		}
		final CartModel cartModel = getCart();
		final boolean isGuestOrHomeowner = ((SiteOneCartFacade) cartFacade).isGuestOrHomeowner(cartModel);
		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
		if(cartData.getIsItemLevelShippingFeeBranch() != null && cartData.getIsItemLevelShippingFeeBranch())
		{
			return null;
		}
		else if (sessionShipTo != null && sessionShipTo.getShippingFee() != null && !sessionShipTo.getShippingFee().isEmpty()
				&& (cartData.getIsLABranch() || cartData.getIsTampaBranch() || cartData.getIsShippingFeeBranch()))
		{
			return sessionShipTo.getShippingFee();
		}
		else if (isGuestOrHomeowner && pos != null && pos.getShippingFeeForHomeownerOrGuest() != null
				&& !pos.getShippingFeeForHomeownerOrGuest().isEmpty())
		{
			return pos.getShippingFeeForHomeownerOrGuest();
		}
		else if (!isGuestOrHomeowner && pos != null && pos.getShippingFeeForContractor() != null
				&& !pos.getShippingFeeForContractor().isEmpty())
		{
			return pos.getShippingFeeForContractor();
		}
		else if (BooleanUtils.isTrue(cartData.getIsShippingFeeBranch()))
		{
			final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
			final Map<String, String> hubStoreShippingFee = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);

			return null != shippingFee.get(storeSessionFacade.getSessionStore().getHubStores().get(0))
					? shippingFee.get(storeSessionFacade.getSessionStore().getHubStores().get(0))
					: hubStoreShippingFee.get(storeSessionFacade.getSessionStore().getHubStores().get(0));
		}
		else if ((BooleanUtils.isTrue(cartData.getIsTampaBranch()) || BooleanUtils.isTrue(cartData.getIsLABranch())))
		{
			return siteOneFeatureSwitchCacheService.getValueForSwitch("FlatRateShippingCharges");
		}
		return null;
	}

	@Override
	public double getThreshold(final CartData cartData)
	{
		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		PointOfServiceData pos = null;
		if (sessionStore != null && sessionStore.getHubStores() != null && sessionStore.getHubStores().get(0) != null)
		{
			pos = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(sessionStore.getHubStores().get(0));
		}
		final CartModel cartModel = getCart();
		final boolean isGuestOrHomeowner = ((SiteOneCartFacade) cartFacade).isGuestOrHomeowner(cartModel);
		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
		if(cartData.getIsItemLevelShippingFeeBranch() != null && cartData.getIsItemLevelShippingFeeBranch())
		{
			return 0;
		}
		else if (sessionShipTo != null && sessionShipTo.getShippingThreshold() != null && !sessionShipTo.getShippingThreshold().isEmpty()
				&& (cartData.getIsLABranch() || cartData.getIsTampaBranch() || cartData.getIsShippingFeeBranch()))
		{
			return Double.parseDouble(sessionShipTo.getShippingThreshold());
		}
		else if (isGuestOrHomeowner && pos != null && pos.getShippingThresholdForHomeownerOrGuest() != null
				&& !pos.getShippingThresholdForHomeownerOrGuest().isEmpty())
		{
			return Double.parseDouble(pos.getShippingThresholdForHomeownerOrGuest());
		}
		else if (!isGuestOrHomeowner && pos != null && pos.getShippingThresholdForContractor() != null
				&& !pos.getShippingThresholdForContractor().isEmpty())
		{
			return Double.parseDouble(pos.getShippingThresholdForContractor());
		}
		else if (BooleanUtils.isTrue(cartData.getIsShippingFeeBranch()))
		{
			final Map<String, String> flatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_THRESHOLD);
			final Map<String, String> hubflatrate = siteOneFeatureSwitchCacheService
					.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUB_SHIPPING_THRESHOLD);

			if (null != flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
			{
				return Double.parseDouble(flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)));
			}
			else if (null != hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
			{
				return Double.parseDouble(hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)));
			}
		}
		else if ((BooleanUtils.isTrue(cartData.getIsTampaBranch()) || BooleanUtils.isTrue(cartData.getIsLABranch())))
		{
			final String flatrate = siteOneFeatureSwitchCacheService.getValueForSwitch("FreeShippingThreshold");
			return Double.parseDouble(flatrate);
		}
		return 0;
	}

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
	 * @return the cartService
	 */
	@Override
	public CartService getCartService()
	{
		return cartService;
	}

	/**
	 * @param cartService
	 *           the cartService to set
	 */
	@Override
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	/**
	 * Get shippingavailable branches
	 */
	public List<String> getShippingAvailableBranches(final String featureSwitchName)
	{
		final String branches = siteOneFeatureSwitchCacheService.getValueForSwitch(featureSwitchName);
		List<String> arrayOfBranches = new ArrayList<>();
		if (null != branches)
		{
			arrayOfBranches = Arrays.asList(branches.split(","));
		}
		return arrayOfBranches;
	}

	/**
	 * validate shipping address state
	 */
	public boolean isShippingStateValid(final PointOfServiceData sessionPos, final String shippingState)
	{
		boolean validState = false;
		boolean isBranchValid = false;
		final List<String> validShippingAvailBranches = getShippingAvailableBranches("ShippingandDeliveryFeeBranches");
		final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService
				.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
		if (null != sessionPos && null != sessionPos.getHubStores() && null != sessionPos.getHubStores().get(0)
				&& shippingFee.containsKey(sessionPos.getHubStores().get(0)))
		{
			return true;
		}
		if (!CollectionUtils.isEmpty(validShippingAvailBranches))
		{
			isBranchValid = validShippingAvailBranches.stream().anyMatch(t -> t.equalsIgnoreCase(sessionPos.getStoreId()));
			if (!isBranchValid)
			{
				final List<String> validShippingAvailBranches1 = getShippingAvailableBranches("ShippingandDeliveryLAFeeBranches");
				if (!CollectionUtils.isEmpty(validShippingAvailBranches1))
				{
					isBranchValid = validShippingAvailBranches1.stream().anyMatch(t -> t.equalsIgnoreCase(sessionPos.getStoreId()));
				}
			}
			if (isBranchValid)
			{
				if (shippingState.equalsIgnoreCase(sessionPos.getAddress().getRegion().getIsocodeShort()))
				{
					validState = true;
				}
			}
		}
		return validState;
	}



	@Override
	public CartData updateCartDataBasedOnOrderType(final CartData cartData, final SiteOneOrderTypeFacadeForm siteOneOrderTypeForm)
			throws ResourceAccessException, Exception
	{
		boolean splitMixedCart = false;
		PointOfServiceData homestore= storeSessionFacade.getSessionStore();
		if(!userFacade.isAnonymousUser() && null != homestore && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", homestore.getStoreId()))
		{
			splitMixedCart=true;
		}
		cartData.setPointOfService(((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(siteOneOrderTypeForm.getStoreId()));
		CustomerData customer = null;
		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (null != siteOneOrderTypeForm.getContactId())
			{
				customer = customerFacade.getCustomerForId(siteOneOrderTypeForm.getContactId().trim().toLowerCase());
			}
		}
		final Map<String, Boolean> fulfilmentStatus = populateFulfilmentStatus(cartData);
		if(splitMixedCart && !CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) 
	   		&& !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
         {
   		if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_PICKUP))
   		{
   			cartData.setPickupInstruction(siteOneOrderTypeForm.getPickupInstruction());
   			cartData.setContactPerson(customer);
   			AddressData pickupAddress = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(siteOneOrderTypeForm.getStoreId()).getAddress();
   			cartData.setPickupAddress(pickupAddress);
   		}
   		if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
   		{
   			cartData.setIsExpedited(siteOneOrderTypeForm.isExpediteDelivery());
   			cartData.setContactPerson(customer);
   			cartData.setDeliveryInstruction(siteOneOrderTypeForm.getDeliveryInstruction());
   			String deliveryFreightCharge = null;

				if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
				{
					if (null != siteOneOrderTypeForm.getDeliveryContactId())
					{
						cartData.setDeliveryContactPerson(
								customerFacade.getCustomerForId(siteOneOrderTypeForm.getDeliveryContactId().trim().toLowerCase()));
					}
					else
					{
						cartData.setDeliveryContactPerson(customer);
					}

					boolean isHomeOwner = false;
					final String homeownerCode = configurationService.getConfiguration().getString(HOMEOWNER_CODE);
					if (sessionShipTo.getTradeClass() != null)
					{
						isHomeOwner = sessionShipTo.getTradeClass().equalsIgnoreCase(homeownerCode) ? true : false;
					}
					deliveryFreightCharge = getFreightCharge(cartData, isHomeOwner, false);


					final Collection<AddressData> addresses = customerFacade.getAllShippingAddresssesForUnit(sessionShipTo.getUid());

					for (final AddressData address : addresses)
					{
						if (StringUtils.equalsIgnoreCase(address.getId(), siteOneOrderTypeForm.getDeliveryAddressId()))
						{
							cartData.setDeliveryAddress(address);
							break;
						}
					}

				}
				else
				{
					deliveryFreightCharge = getFreightCharge(cartData, false, true);

					cartData.setDeliveryAddress(sessionService.getAttribute("guestAddressData"));

					if (siteOneOrderTypeForm.getIsSameAsContactInfoDelivery() != null
							&& siteOneOrderTypeForm.getIsSameAsContactInfoDelivery().equalsIgnoreCase("on"))
					{
						cartData.setIsSameaddressforDelivery(true);
					}
					else
					{

						cartData.setIsSameaddressforDelivery(false);
					}


				}

				if (StringUtils.isNotEmpty(deliveryFreightCharge))
				{
					populateFreightToCart(deliveryFreightCharge, SiteoneCoreConstants.DELIVERYMODE_DELIVERY, cartData);
				}

			}
			else
			{
				cartData.setDeliveryFreight("0.00");
			}
			if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
			{
				cartData.setShippingInstruction(siteOneOrderTypeForm.getShippingInstruction());
				cartData.setContactPerson(customer);
				final String freightCharge = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);
				if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
				{
					if (siteOneOrderTypeForm.getShippingContactId() != null)
					{
						cartData.setShippingContactPerson(
								customerFacade.getCustomerForId(siteOneOrderTypeForm.getShippingContactId().trim().toLowerCase()));
					}
					else
					{
						cartData.setShippingContactPerson(customer);
					}
					final Collection<AddressData> addresses = customerFacade.getAllShippingAddresssesForUnit(sessionShipTo.getUid());

					for (final AddressData address : addresses)
					{
						if (StringUtils.equalsIgnoreCase(address.getId(), siteOneOrderTypeForm.getShippingAddressId()))
						{
							cartData.setShippingAddress(address);
							cartData.setDeliveryAddress(address);
							break;
						}
					}
				}
				else
				{
					if (siteOneOrderTypeForm.getIsSameAsContactInfoShipping() != null
							&& siteOneOrderTypeForm.getIsSameAsContactInfoShipping().equalsIgnoreCase("on"))
					{
						cartData.setIsSameaddressforParcelShip(true);
					}
					else
					{
						cartData.setIsSameaddressforParcelShip(false);
					}
					cartData.setShippingAddress(sessionService.getAttribute("guestAddressData"));
				}
				if (StringUtils.isNotEmpty(freightCharge))
				{
					populateFreightToCart(freightCharge, SiteoneCoreConstants.DELIVERYMODE_SHIPPING, cartData);
				}

   		}
   		else
   		{
   			cartData.setShippingFreight("0.00");
   		}
      }
		if (siteOneOrderTypeForm.getOrderType() != null && (!splitMixedCart || CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) 
	   		|| CollectionUtils.isEmpty(cartData.getShippingOnlyEntries())))
		{

			if (siteOneOrderTypeForm.getOrderType().equalsIgnoreCase(PICKUP))
			{
				cartData.setDeliveryAddress(cartData.getPointOfService().getAddress());
				cartData.setPickupAddress(
						((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(siteOneOrderTypeForm.getStoreId()).getAddress());

			}
			else if (siteOneOrderTypeForm.getOrderType().equalsIgnoreCase(DELIVERY))
			{

				cartData.setIsExpedited(siteOneOrderTypeForm.isExpediteDelivery());
				cartData.setChooseLift(siteOneOrderTypeForm.isChooseLift());

				String deliveryFreightCharge = null;

				if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
				{

					boolean isHomeOwner = false;
					final String homeownerCode = configurationService.getConfiguration().getString(HOMEOWNER_CODE);
					if (sessionShipTo.getTradeClass() != null)
					{
						isHomeOwner = sessionShipTo.getTradeClass().equalsIgnoreCase(homeownerCode) ? true : false;
					}

					deliveryFreightCharge = getFreightCharge(cartData, isHomeOwner, false);


					final Collection<AddressData> addresses = customerFacade.getAllShippingAddresssesForUnit(sessionShipTo.getUid());

					for (final AddressData address : addresses)
					{
						if (StringUtils.equalsIgnoreCase(address.getId(), siteOneOrderTypeForm.getAddressId()))
						{
							cartData.setDeliveryAddress(address);
							break;
						}
					}

				}
				else
				{
					deliveryFreightCharge = getFreightCharge(cartData, false, true);

					cartData.setDeliveryAddress(sessionService.getAttribute("guestAddressData"));
					if (siteOneOrderTypeForm.getIsSameAsContactInfo() != null
							&& siteOneOrderTypeForm.getIsSameAsContactInfo().equalsIgnoreCase("on"))
					{
						cartData.setIsSameaddressforParcelShip(true);
					}
				}

				if (StringUtils.isNotEmpty(deliveryFreightCharge))
				{
					populateFreightToCart(deliveryFreightCharge, null, cartData);
				}

			}
			else
			{
				final String freightCharge = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);

				if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
				{
					final Collection<AddressData> addresses = customerFacade.getAllShippingAddresssesForUnit(sessionShipTo.getUid());

					for (final AddressData address : addresses)
					{
						if (StringUtils.equalsIgnoreCase(address.getId(), siteOneOrderTypeForm.getAddressId()))
						{
							cartData.setDeliveryAddress(address);
							break;
						}
					}

				}
				else
				{
					cartData.setDeliveryAddress(sessionService.getAttribute("guestAddressData"));
					if (siteOneOrderTypeForm.getIsSameAsContactInfo() != null
							&& siteOneOrderTypeForm.getIsSameAsContactInfo().equalsIgnoreCase("on"))
					{
						cartData.setIsSameaddressforParcelShip(true);
					}
				}
				if (StringUtils.isNotEmpty(freightCharge))
				{
					populateFreightToCart(freightCharge, null, cartData);
				}
			}
			cartData.setContactPerson(customer);
		}

		cartData.setPurchaseOrderNumber(siteOneOrderTypeForm.getPurchaseOrderNumber());
		if (siteOneOrderTypeForm.getOrderType() != null && (siteOneOrderTypeForm.getOrderType().equalsIgnoreCase(PICKUP)
				|| (siteOneOrderTypeForm.getOrderType().equalsIgnoreCase(DELIVERY))))
		{
			cartData.setRequestedDate(siteOneOrderTypeForm.getRequestedDate());
			cartData.setRequestedMeridian(siteOneOrderTypeForm.getRequestedMeridian());
			cartData.setSpecialInstruction(siteOneOrderTypeForm.getSpecialInstruction());
		}

		return cartData;
	}

	@Override
	public void populateFreightToCart(final String deliveryFreightCharge, final String mode, final CartData cartData)
	{
		final Double freightCharges = Double.valueOf(deliveryFreightCharge);
		BigDecimal roundedFreightCharges = BigDecimal.valueOf(freightCharges);
		roundedFreightCharges = roundedFreightCharges.setScale(digits, BigDecimal.ROUND_HALF_UP);
		final PriceData deliveryCost = new PriceData();
		deliveryCost.setValue(roundedFreightCharges);
		deliveryCost.setFormattedValue("$".concat(roundedFreightCharges.toString()));
		if (mode != null)
		{
			if (mode.equalsIgnoreCase(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
			{
				cartData.setDeliveryCost(deliveryCost);
				cartData.setDeliveryFreight(roundedFreightCharges.toString());
			}
			else
			{
				cartData.setShippingCost(deliveryCost);
				cartData.setShippingFreight(roundedFreightCharges.toString());
			}

		}
		else
		{
			cartData.setDeliveryCost(deliveryCost);
			cartData.setDeliveryFreight(roundedFreightCharges.toString());
			cartData.setFreight(roundedFreightCharges.toString());
		}
	}

	@Override
	public boolean validateAddressForDelivery(final SiteOneOrderTypeFacadeForm siteOneOrderTypeForm)
	{
		boolean status = false;

		if (StringUtils.isNotBlank(siteOneOrderTypeForm.getAddressId())
				&& StringUtils.isNotBlank(siteOneOrderTypeForm.getOrderType()))
		{
			final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();

			if (siteOneOrderTypeForm.getOrderType().equalsIgnoreCase(DELIVERY))
			{
				final Collection<AddressData> addresses = customerFacade.getAllShippingAddresssesForUnit(sessionShipTo.getUid());
				for (final AddressData address : addresses)
				{
					if (StringUtils.equalsIgnoreCase(address.getId(), siteOneOrderTypeForm.getAddressId()))
					{
						status = true;
					}
				}
			}
		}

		return status;
	}

	@Override
	public boolean isCartValidForCheckout(final CartData cartData)
	{

		boolean isMyStoreProduct = false;
		boolean isPriceAvailable = false;
		boolean isSellable = false;
		Boolean isRup = Boolean.FALSE;

		final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();

		final List<OrderEntryData> cartEntries = cartData.getEntries();
		if (null != sessionPos && null != cartData.getPointOfService())
		{
			LOG.info("Cart validation(isCartValidForCheckout): Current Store code: " + sessionPos.getStoreId() + "Cart Store Code: "
					+ cartData.getPointOfService().getStoreId());

			for (final OrderEntryData cartEntry : cartEntries)
			{
				LOG.info("ProductCode: " + cartEntry.getProduct().getCode());

				if (cartEntry.getProduct().getStores() != null)
				{
					for (final String storeId : cartEntry.getProduct().getStores())
					{
						if (storeId.equalsIgnoreCase(sessionPos.getStoreId()))
						{
							isMyStoreProduct = true;
						}
					}
				}

				//checking if there is a stock availability in nearbyStores.
				if (!isMyStoreProduct)
				{
					//this confirms that item is available in a nearby store.
					if (null != cartEntry.getProduct().getIsStockInNearbyBranch())
					{
						isMyStoreProduct = cartEntry.getProduct().getIsStockInNearbyBranch();
					}

					else if (BooleanUtils.isTrue(cartEntry.getProduct().getStockAvailableOnlyHubStore()))
					{
						isMyStoreProduct = cartEntry.getProduct().getStockAvailableOnlyHubStore();
					}
					else if (null != cartEntry.getProduct().getStock()
							&& BooleanUtils.isTrue(cartEntry.getProduct().getStock().getDcBranchStock()))
					{
						isMyStoreProduct = true;
					}
				}

				if (!isMyStoreProduct)
				{
					LOG.error("isMyStoreProduct flag : " + false);
					return false;

				}

				if (cartEntry.getBasePrice().getValue().compareTo(BigDecimal.ZERO) != 0)
				{
					isPriceAvailable = true;
				}

				if (!isPriceAvailable)
				{
					LOG.error("isPriceAvailable flag : " + false);
					return false;
				}

				if (null != cartEntry.getBasePrice().getValue()
						&& cartEntry.getBasePrice().getValue().compareTo(BigDecimal.ZERO) == 0)
				{
					LOG.error("Cart entry is having Zero Retail Price");
					return false;
				}

				if (cartEntry.getProduct().getIsRegulateditem() && !cartEntry.getProduct().getIsProductDiscontinued())
				{
					if (CollectionUtils.isNotEmpty(cartEntry.getProduct().getRegulatoryStates()))
					{
						final PointOfServiceModel pos = storeFinderService.getStoreForId(sessionPos.getStoreId());
						isRup = regulatoryStatesCronJobService.getRupBySkuAndState(cartEntry.getProduct().getCode(),
								pos.getAddress().getRegion());

						for (final String state : cartEntry.getProduct().getRegulatoryStates())
						{
							if (state.equalsIgnoreCase(sessionPos.getAddress().getRegion().getIsocodeShort()))
							{
								isSellable = true;
							}
						}

						if (!isSellable)
						{
							LOG.error("Cart entry is Regular Product with isSellable flag : " + false);
							return false;
						}
						else if (isRup && !sessionPos.getIsLicensed())
						{
							LOG.error("Cart entry is Regular Product with isSellable flag : " + true + "isRup flag : " + true
									+ " No Store license for the session store " + sessionPos.getStoreId());
							return false;
						}
					}
				}
			}
		}
		else
		{
			LOG.error("Session POS / Cart POS found null.");
			return false;
		}
		return true;
	}

	@Override
	public boolean checkStockLevelNLA(final String productCode, final long quantity)
	{
		final List<StockLevelModel> stockLevelList = siteOneStockLevelService.getStockLevelsForQuantity(productCode, quantity);
		if (CollectionUtils.isEmpty(stockLevelList))
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isAccountValidForCheckout(final CartData cartData)
	{
		if (cartData.getOrderingAccount() == null)
		{
			LOG.error("Selected shipto is not an ordering account");
			return false;
		}
		else if (!cartData.getOrderingAccount().getIsOrderingAccount())
		{
			LOG.error("Selected shipto is not an ordering account");
			return false;
		}
		return true;
	}

	@Override
	public boolean isPosValidForCheckout(final CartData cartData)
	{
		if (cartData.getPointOfService() == null)
		{
			return false;
		}
		else
		{
			final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();

			if (!sessionPos.getStoreId().equalsIgnoreCase(cartData.getPointOfService().getStoreId()))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isPONumberRequired()
	{
		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
		if (sessionShipTo.getIsPONumberRequired() != null && sessionShipTo.getIsPONumberRequired())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getFreightCharge(final CartData cartData, final boolean isHomeOwner, final boolean isGuest)
			throws IOException, RestClientException
	{
		final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();
		String freightCharge = null;
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if(basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) {
			double deliveryFee = 0.0d;
			final CartModel cartModel = getCart();
			for (final AbstractOrderEntryModel entry : cartModel.getEntries()) {
				if(null != entry.getProduct()) {
					deliveryFee = deliveryFee + siteoneCADeliveryFeeService.getDeliveryFee(sessionPos.getStoreId(), entry.getInventoryUOM().getCode(), entry.getQuantity());
				}
			}
			if(deliveryFee > 0.0d){
				freightCharge = String.valueOf(deliveryFee);
			}
		}
		else {
			if (((null != storeSessionFacade.getSessionShipTo()
					&& (!BooleanUtils.isTrue(storeSessionFacade.getSessionShipTo().isExemptDeliveryFee()))) || isGuest)
					&& (!CollectionUtils.isEmpty(cartData.getEntries())))
				{
   				final HashSet<String> lobSet = populateLOBList(cartData.getEntries());
   				final boolean proUser = !(isHomeOwner || isGuest);
   				freightCharge = siteoneDeliveryFeeService.getDeliveryFee(sessionPos.getStoreId(), proUser, lobSet);
				}
		}
		
		return freightCharge;
	}

	protected HashSet<String> populateLOBList(final List<OrderEntryData> entries)
	{
		final HashSet<String> lobSet = new HashSet<>();
		for (final OrderEntryData entry : entries)
		{
			if (null != entry.getProduct())
			{
				String category = null;
				if (StringUtils.isNotBlank(entry.getProduct().getLevel1Category()))
				{
					category = entry.getProduct().getLevel1Category();
				}
				else if (StringUtils.isNotBlank(entry.getProduct().getBaseProduct())
						&& StringUtils.isNotBlank(entry.getProduct().getParentCategory()))
				{
					category = entry.getProduct().getParentCategory();
				}
				if (StringUtils.isNotBlank(category))
				{
					if (category.equalsIgnoreCase(AGRONOMIC_MAINTENANCE) || category.equalsIgnoreCase(MAINTENANCE))
					{
						lobSet.add(AGRONOMICS);
					}
					if (category.equalsIgnoreCase(IRRIGATION))
					{
						lobSet.add(IRRIGATION);
					}
					if (category.equalsIgnoreCase(NURSERY))
					{
						lobSet.add(NURSERY);
					}
					if (category.equalsIgnoreCase(LANDSCAPE_SUPPLY) || category.equalsIgnoreCase(LANDSCAPE_SUPPLIES))
					{
						lobSet.add(LANDSCAPE_SUPPLY);
					}
					if (category.equalsIgnoreCase(HARDCSAPES_OUTDOOR_LIVING))
					{
						lobSet.add(HARDSCAPE);
					}
					if (category.equalsIgnoreCase(LIGHTING) || category.equalsIgnoreCase(OUTDOOR_LIGHTING))
					{
						lobSet.add(OUTDOOR_LIGHTING);
					}
					if (category.equalsIgnoreCase(TOOLS_EQUIPMENT_AND_SAFETY))
					{
						lobSet.add(TOOLS_AND_SAFETY);
					}
				}

			}
		}
		return lobSet;
	}
	
	@Override
	public String getItemLevelShippingFee(final List<OrderEntryData> entries)
	{
		final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();
		String shippingFee = null;
		String hubStore = sessionPos != null && sessionPos.getHubStores() != null
				&& sessionPos.getHubStores().get(0) != null ? sessionPos.getHubStores().get(0) : null;
		
		if(hubStore != null && !CollectionUtils.isEmpty(entries))
		{
			final HashSet<String> productSkus = populateProductSkus(entries);
			shippingFee = siteoneDeliveryFeeService.getShippingFee(hubStore, productSkus);
		}
		
		return shippingFee;
	}
	
	@Override
	public boolean getIfItemLevelShippingFeeApplicable(final List<OrderEntryData> entries)
	{
		final PointOfServiceData sessionPos = storeSessionFacade.getSessionStore();
		boolean shippingFee = false;
		String hubStore = sessionPos != null && sessionPos.getHubStores() != null
				&& sessionPos.getHubStores().get(0) != null ? sessionPos.getHubStores().get(0) : null;
		
		if(hubStore != null && !CollectionUtils.isEmpty(entries))
		{
			final HashSet<String> productSkus = populateProductSkus(entries);
			shippingFee = siteoneDeliveryFeeService.getifItemLevelShippingFeeApplicable(hubStore, productSkus);
		}
		return shippingFee;
	}

	private HashSet<String> populateProductSkus(List<OrderEntryData> entries)
	{
		final HashSet<String> productSkus = new HashSet<>();
		if(!CollectionUtils.isEmpty(entries))
		{
			for(OrderEntryData entry : entries)
			{
				if(entry.getProduct() != null)
				{
					productSkus.add(entry.getProduct().getCode());
				}
			}
		}
		return productSkus;
	}

	@Override
	public boolean validatePhoneNumber(final String phoneNumber)
	{
		boolean status = false;

		final Matcher matcher = PHONE_NUMBER_REGEX.matcher(phoneNumber);
		final String number = phoneNumber.replaceAll("[()\\s-]+", "");
		if (!(matcher.matches()) || number.length() != 10)
		{
			status = true;
		}
		return status;
	}

	/**
	 * @param b2bCustomerData
	 */
	@Override
	public void formGuestUserData(final CustomerData b2bCustomerData, final Model model)
	{
		final String originalEmailID = getOriginalEmail(b2bCustomerData.getDisplayUid());
		b2bCustomerData.setDisplayUid(originalEmailID);
		final AddressData addressData = sessionService.getAttribute("guestAddressData");
		b2bCustomerData.setContactNumber(addressData.getPhone());
		model.addAttribute("contactPerson", b2bCustomerData);
		model.addAttribute("addressData", addressData);
	}

	/**
	 * @param displayUid
	 * @return
	 */
	@Override
	public String getOriginalEmail(final String displayUid)
	{
		if (displayUid != null)
		{
			if(displayUid.contains("|"))
			{
			final String[] SplitUID = displayUid.split("\\|");
			final String originalEmailID = SplitUID[1];
			return originalEmailID;
			}
			else
			{
				return displayUid;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param cartData
	 */
	@Override
	public CartData populatecartData(final CartData cartData)
	{
		final CartData completeCartData = new CartData();
		completeCartData.setRequestedDate(cartData.getRequestedDate());
		completeCartData.setRequestedMeridian(cartData.getRequestedMeridian());
		completeCartData.setDeliveryAddress(cartData.getDeliveryAddress());
		completeCartData.setShippingAddress(cartData.getShippingAddress());
		completeCartData.setFreight(cartData.getFreight());
		completeCartData.setTotalPriceWithTax(cartData.getTotalPriceWithTax());
		completeCartData.setTotalTax(cartData.getTotalTax());
		completeCartData.setTotalDiscountAmount(cartData.getTotalDiscountAmount());
		completeCartData.setSubTotal(cartData.getSubTotal());
		completeCartData.setTotalDiscounts(cartData.getTotalDiscounts());
		completeCartData.setDeliveryFreight(cartData.getDeliveryFreight());
		completeCartData.setShippingFreight(cartData.getShippingFreight());
		return completeCartData;

	}

	@Override
	public CustomerData populateGuestUserData(final SiteOneGCContactFacadeForm siteOneGCContactForm)
	{
		final CustomerData customerData = new CustomerData();
		customerData.setFirstName(siteOneGCContactForm.getFirstName());
		customerData.setLastName(siteOneGCContactForm.getLastName());
		customerData.setEmail(siteOneGCContactForm.getEmail());
		customerData.setDisplayUid(siteOneGCContactForm.getEmail());
		customerData.setContactNumber(siteOneGCContactForm.getPhone());
		final AddressData addressData = new AddressData();
		addressData.setFirstName(siteOneGCContactForm.getFirstName());
		addressData.setLastName(siteOneGCContactForm.getLastName());
		addressData.setEmail(siteOneGCContactForm.getEmail());
		addressData.setLine1(siteOneGCContactForm.getAddressLine1());
		addressData.setLine2(siteOneGCContactForm.getAddressLine2());
		addressData.setCompanyName(siteOneGCContactForm.getCompanyName());
		addressData.setDistrict(siteOneGCContactForm.getState());
		addressData.setPhone(siteOneGCContactForm.getPhone());
		 String regionCode = null;
		 final RegionData regionData = new RegionData();
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_US))
		{
		 regionCode = COUNTRY_CODE_US + "-" + siteOneGCContactForm.getState();
		final CountryData countryData = new CountryData();
		countryData.setIsocode(COUNTRY_CODE_US);
		addressData.setCountry(countryData);
		regionData.setCountryIso(COUNTRY_CODE_US);
		}
		else
		{
			regionCode = COUNTRY_CODE_CA + "-" + siteOneGCContactForm.getState();
			final CountryData countryData = new CountryData();
			countryData.setIsocode(COUNTRY_CODE_CA);
			addressData.setCountry(countryData);
			regionData.setCountryIso(COUNTRY_CODE_CA);
		}
		
		regionData.setIsocodeShort(siteOneGCContactForm.getState());
		regionData.setIsocode(regionCode);
		addressData.setRegion(regionData);
		addressData.setTown(siteOneGCContactForm.getCity());
		addressData.setPostalCode(siteOneGCContactForm.getZip());
		customerData.setDefaultAddress(addressData);
		return customerData;
	}


	/*
	 * @see
	 * com.siteone.facades.checkout.SiteOneB2BCheckoutFacade#updateShippingState(de.hybris.platform.commercefacades.order
	 * .data.CartData, de.hybris.platform.commercefacades.user.data.SiteoneFulfilmentData)
	 */
	@Override
	public void updateShippingState(final CartData cartData, final SiteoneFulfilmentData siteoneFulfilmentData)
	{
		String shippingState = "";
		if (cartData.getGuestContactPerson() != null && cartData.getGuestContactPerson().getDefaultAddress() != null)
		{
			shippingState = cartData.getGuestContactPerson().getDefaultAddress().getDistrict();
		}
		else
		{
			if (cartData.getDeliveryAddress() != null)
			{
				shippingState = cartData.getDeliveryAddress().getRegion().getIsocodeShort();
			}

		}
		if (cartData.getOrderType() != null && (cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)
				|| (CollectionUtils.isNotEmpty(cartData.getPickupAndDeliveryEntries()) && CollectionUtils.isNotEmpty(cartData.getShippingOnlyEntries()))))
		{
			final boolean validState = isShippingStateValid(storeSessionFacade.getSessionStore(), shippingState);
			siteoneFulfilmentData.setIsShippingStateValid(validState);
		}

	}

	/*
	 * @see
	 * com.siteone.facades.checkout.SiteOneB2BCheckoutFacade#populateUserContact(de.hybris.platform.commercefacades.order
	 * .data.CartData, de.hybris.platform.commercefacades.user.data.SiteOneGuestUserData,
	 * com.siteone.utils.SiteOneGCContactForm)
	 */
	@Override
	public void populateUserContact(final CartData cartData, final SiteOneGuestUserData siteOneGuestUserData,
			final Boolean isSameaddressforParcelShip, final String state)
	{

		final Map<String, Boolean> fulfilmentStatus = populateFulfilmentStatus(cartData);
		if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
		{
			siteOneGuestUserData.setIsSameAsContactInfo(isSameaddressforParcelShip);
		}
		else if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
		{
			siteOneGuestUserData.setIsSameAsContactInfo(isSameaddressforParcelShip);
			final boolean validState = isShippingStateValid(storeSessionFacade.getSessionStore(), state);
			siteOneGuestUserData.setIsShippingStateValid(validState);
		}
		if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_DELIVERY))
		{
			siteOneGuestUserData.setIsSameAsContactInfo(isSameaddressforParcelShip);
		}
		else if (cartData.getOrderType() != null
				&& cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
		{
			siteOneGuestUserData.setIsSameAsContactInfo(isSameaddressforParcelShip);
			final boolean validState = isShippingStateValid(storeSessionFacade.getSessionStore(), state);
			siteOneGuestUserData.setIsShippingStateValid(validState);
		}
	}

	public Map<String, Boolean> populateFulfilmentStatus(final CartData cartData)
	{
		final Map<String, Boolean> fulfilmentStatus = new HashMap<>();
		fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_PICKUP, false);
		fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_DELIVERY, false);
		fulfilmentStatus.put(SiteoneCoreConstants.DELIVERYMODE_SHIPPING, false);

		if (null != cartData.getDeliveryModeAndBranchOrderGroups()
				&& CollectionUtils.isNotEmpty(cartData.getDeliveryModeAndBranchOrderGroups()))
		{
			for (final DeliveryModeAndBranchOrderEntryGroupData listGroupData : cartData.getDeliveryModeAndBranchOrderGroups())
			{
				if (fulfilmentStatus.keySet().contains(listGroupData.getDeliveryMode().getCode()))
				{
					fulfilmentStatus.put(listGroupData.getDeliveryMode().getCode(), true);

				}
			}
		}
		return fulfilmentStatus;
	}

	public void setMixedCartOrderType(final CartModel cartModel) {

		boolean splitMixedCart = false;
		PointOfServiceData homestore= storeSessionFacade.getSessionStore();
		B2BCustomerModel userModel = null;
		if(!userFacade.isAnonymousUser())
		{
			userModel = (B2BCustomerModel) cartModel.getUser();
		}
		boolean isShippingOnlyProdPresent = false;
		boolean isNonShippingOnlyProdPresent = false;
		if(!CollectionUtils.isEmpty(cartModel.getEntries()))
		{
			for(AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if(entry != null && entry.getIsShippingOnlyProduct()!=null && BooleanUtils.isTrue(entry.getIsShippingOnlyProduct()))
				{
					isShippingOnlyProdPresent = true;
				}
				else
				{
					isNonShippingOnlyProdPresent = true;
				}
				if(isShippingOnlyProdPresent && isNonShippingOnlyProdPresent)
				{
					break;
				}
			}
		}
		if(!userFacade.isAnonymousUser() && isShippingOnlyProdPresent && isNonShippingOnlyProdPresent && ((cartModel.getOrderType() != null
				&& !cartModel.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING")) || cartModel.getOrderType() == null) 
				&& null != homestore && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches", homestore.getStoreId()))
		{
			splitMixedCart=true;
		}
		
		final Map<String, String> isDcBranch = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping("DCShippingFeeBranches");
		if((splitMixedCart || (cartModel.getOrderType().getCode().equalsIgnoreCase("PARCEL_SHIPPING")))
				&& storeSessionFacade.getSessionStore() != null)
		{

			if(homestore != null && homestore.getHubStores() != null && homestore.getHubStores().get(0) != null 
					&& isDcBranch.containsKey(homestore.getHubStores().get(0)))
			{
				if(userFacade.isAnonymousUser() && sessionService.getAttribute("geoPointStore") != null)
				{
					cartModel.setHomeStoreNumber(sessionService.getAttribute("geoPointStore"));
				}	
				else if(!userFacade.isAnonymousUser() && userModel != null && userModel.getHomeBranch() != null)
				{
					cartModel.setHomeStoreNumber(userModel.getHomeBranch());
				}
				else
				{
					cartModel.setHomeStoreNumber(storeSessionFacade.getSessionStore().getStoreId());
				}
				getModelService().save(cartModel);
			}
		}
		
		if (splitMixedCart || (null != homestore && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", homestore.getStoreId())))
		{
			cartModel.setOrderType(null);
		}
	
	}

	public void updateCartForPunchoutSession(final CartModel cartModel)
	{
		final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();

		if (parentUnit != null && parentUnit.getIsPunchOutAccount() != null && parentUnit.getIsPunchOutAccount().booleanValue())
		{
			final B2BCustomerModel b2bcustomer = (B2BCustomerModel) cartModel.getUser();
			final PointOfServiceModel pos = b2bcustomer.getPreferredStore();
			if (cartModel.getPointOfService() == null)
			{
				cartModel.setPointOfService(pos);
			}
			final String deliveryMode = cartModel.getOrderType().getCode();
			for (final AbstractOrderEntryModel entry : cartModel.getEntries())
			{
				if (entry.getInventoryUOM() == null)
				{
					entry.setInventoryUOM(entry.getProduct().getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue())
							.findFirst().orElse(null));
				}
				entry.setDeliveryPointOfService(pos);
				if (SiteoneCoreConstants.ORDERTYPE_PICKUP.equalsIgnoreCase(deliveryMode))
				{
					entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERYMODE_PICKUP));
				}
				else if (SiteoneCoreConstants.ORDERTYPE_DELIVERY.equalsIgnoreCase(deliveryMode))
				{
					entry.setDeliveryMode(deliveryModeService.getDeliveryModeForCode(SiteoneCoreConstants.DELIVERYMODE_DELIVERY));
				}
				getModelService().save(entry);
			}
		}
	}

	@Override
	public void updateASMAgentForOrder(final EmployeeModel emp)
	{
		final CartModel cartModel = getCart();
		cartModel.setAsmAgent(emp);
		getModelService().save(cartModel);
	}

	public boolean preparePunchoutDeliveryInfo(final CartModel cartModel, final CartData cartData, final B2BUnitModel parentUnit)
	{
		LOG.error("Punchout getPunchOutOrder " + cartModel.getPunchOutOrder());
		if (cartModel.getPunchOutOrder() != null && cartModel.getPunchOutOrder())
		{
			final List<String> b2bUnitAccount = siteOneFeatureSwitchCacheService
					.getDeliveryEnabledPunchoutAccount("DefaultDeliveryEnabledPunchoutAccount");
			if (parentUnit != null && parentUnit.getIsPunchOutAccount() != null && parentUnit.getIsPunchOutAccount().booleanValue()
					&& CollectionUtils.isNotEmpty(b2bUnitAccount) && b2bUnitAccount.contains(parentUnit.getUid()))
			{
				LOG.error("Punchout getPunchOutOrder " + cartModel.getPunchOutOrder());
				cartModel.setOrderType(OrderTypeEnum.DELIVERY);
				storeSessionFacade.setSessionShipTo(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer());
				storeSessionFacade.setSessionStore(((DefaultSiteOneCustomerFacade) customerFacade).getPreferredStore());
				cartData.setPointOfService(storeSessionFacade.getSessionStore());
				boolean isHomeOwner = false;
				final String homeownerCode = configurationService.getConfiguration().getString(HOMEOWNER_CODE);
				if (storeSessionFacade.getSessionShipTo() != null && storeSessionFacade.getSessionShipTo().getTradeClass() != null)
				{
					isHomeOwner = storeSessionFacade.getSessionShipTo().getTradeClass().equalsIgnoreCase(homeownerCode) ? true : false;
				}
				try
				{
					final String deliveryFreightCharge = getFreightCharge(cartData, isHomeOwner, false);
					if (StringUtils.isNotEmpty(deliveryFreightCharge))
					{
						populateFreightToCart(deliveryFreightCharge, null, cartData);
					}
					else
					{
						cartData.setDeliveryCost(null);
						cartData.setFreight(null);
					}
				}
				catch (final IOException exception)
				{
					LOG.error("Unable to fetch the delivery fee details", exception);
					cartData.setDeliveryCost(null);
					cartData.setFreight(null);
				}
				return true;
			}
		}
		return false;
	}

	public AddressModel createDeliveryAddressForPunchout(final CartModel cartModel)
	{
		final AddressModel newAddress = cartModel.getDeliveryAddress();
		SiteOneWsAddressResponseData addressResponseData = null;
		try
		{
			addressResponseData = siteOneAddressWebService.createOrUpdateAddress(newAddress,
					((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer(),
					SiteoneintegrationConstants.OPERATION_TYPE_CREATE,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to create address", resourceAccessException);
		}

		if (null == addressResponseData || null == addressResponseData.getAddressId())
		{
			LOG.error("Not able to create address in UE");
		}
		else
		{
			newAddress.setGuid(addressResponseData.getAddressId().toUpperCase());
		}
		newAddress.setOwner(cartModel);
		getModelService().save(newAddress);
		cartModel.setDeliveryAddress(newAddress);
		return newAddress;
	}
	@Override
	public void saveSiteoneCCAuditLog(final CartData cartData, final String cardNum, final String zipCode)
	{
		final LinkToPayCayanResponseModel cayanResponse = new LinkToPayCayanResponseModel();
		
		if (getUserFacade().isAnonymousUser())
		{
			if (null != cartData.getB2bCustomerData() && null != cartData.getB2bCustomerData().getDisplayUid())
			{
				final String originalID = getOriginalEmail(cartData.getB2bCustomerData().getDisplayUid());
				cayanResponse.setCustomerName("Guest User");
				cayanResponse.setEmail(originalID);
				cayanResponse.setToEmails(originalID);
				cayanResponse.setOrderNumber(originalID);
				cayanResponse.setCartID(originalID);
			}
		}
		else
		{
			if (null != cartData.getContactPerson())
			{
				cayanResponse.setCustomerName(cartData.getContactPerson().getName());
				cayanResponse.setEmail(cartData.getContactPerson().getEmail());
				cayanResponse.setToEmails(cartData.getContactPerson().getEmail());
			}
			cayanResponse.setOrderNumber(cartData.getCode());
			cayanResponse.setCartID(cartData.getCode());
		}

		cayanResponse.setLast4Digits(cardNum);
		cayanResponse.setCreditCardZip(zipCode);
		siteoneLinkToPayAuditLogService.saveSiteoneCCAuditLog(cayanResponse);		
	}
	
	@Override
	public boolean getCardBlockFlag(final CartData cartData)
	{		
		boolean isCardBlockFlag =	false;
		final SiteoneCCPaymentAuditLogModel auditModel = customerFacade.getSiteoneCCAuditDetails(cartData.getCode());
		if (null != auditModel)
		{
			final int declineCountLimit = Integer
					.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
			if (null != auditModel.getDeclineCount() && auditModel.getDeclineCount().intValue() > 0)
			{
				final int declineCount = (auditModel.getDeclineCount().intValue()) / 2;
				LOG.error(" getCardBlockFlag declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);
				if (declineCount >= declineCountLimit)
				{
					isCardBlockFlag = true;
					return isCardBlockFlag;
				}
			}
		}			
		return isCardBlockFlag;
	}
	
}
