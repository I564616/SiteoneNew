package com.siteone.v2.controller;

import java.beans.FeatureDescriptor;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import com.siteone.facades.checkout.form.SiteOneGCContactFacadeForm;
import com.siteone.facades.checkout.form.SiteOneOrderTypeFacadeForm;
import de.hybris.platform.commercefacades.user.data.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.siteone.commerceservice.dto.checkout.OrderTypeFormWsDTO;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.event.KountDeclineEvent;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.utils.SiteOneOrderTypeForm;
import com.siteone.commerceservices.address.dto.SiteOneAddressVerificationWsDTO;
import com.siteone.commerceservices.user.dto.EmailValidationWsDTO;
import com.siteone.commerceservices.user.dto.SiteOneGuestUserWsDTO;
import com.siteone.commerceservices.user.dto.SiteoneFulfilmentWsDTO;
import com.siteone.utils.SiteOneGCContactForm;

import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;

import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.utils.SiteOneAddressDataUtil;

import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commercewebservicescommons.dto.order.CartWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.user.UserWsDTO;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Controller
@RequestMapping(value = "/{baseSiteId}/checkout/multi")
@Tag(name = "Siteone Order Type")
public class SiteOneCheckoutController extends BaseController {

	private static final Logger LOG = Logger.getLogger(SiteOneCheckoutController.class);
	private final String COUNTRY_CODE = "US";
	private static final String MIXEDCART_ENABLED_BRANCHES = "MixedCartEnabledBranches";
	private static final String KOUNT_RESPONSE_DECLINED = "N";
	private static final String RESPONSE_DECLINED = "D";
	private static final String DECLINED = "Decline";

	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;
	
	@Resource(name = "checkoutFacade")
	private de.hybris.platform.commercefacades.order.CheckoutFacade checkoutFacade;
	
	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "defaultSiteoneAddressConverter")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;

	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;

	@Resource(name = "timeService")
	private TimeService timeService;
	
	@GetMapping("/siteOne-checkout")
	@ApiBaseSiteIdParam
	@Operation(operationId = "siteOne-checkout", summary = "Fetch the contact details", description = "Fetch the contact details")
	public @ResponseBody OrderTypeFormWsDTO getCheckoutDetails(@RequestParam(value = "storeId") final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId, 
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			@RequestParam(value = "guid", required = false)	final String guid) throws CommerceCartModificationException
	{
		try
		{
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			if (null != storeId && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId)){
				sessionService.setAttribute("isMixedCartEnabled","mixedcart");
			}
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);
			checkoutFacade.prepareCartForCheckout();
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
						
			if(null != cartData && null != cartData.getTotalTax()) {
				final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
				final BigDecimal totalTax = cartData.getTotalTax().getValue();
				final BigDecimal total = cartData.getTotalPriceWithTax().getValue().subtract(totalTax);
				cartData.getTotalPriceWithTax().setValue(total);
				cartData.getTotalPriceWithTax().setFormattedValue(fmt.format(total));
				cartData.getTotalTax().setValue(BigDecimal.valueOf(0.0d));
				cartData.getTotalTax().setFormattedValue("$".concat("0.00"));
			}
			if (null != cartData && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId) 
					&& (cartData.getPointOfService() == null || (cartData.getPointOfService() != null && cartData.getPointOfService().getStoreId() != null 
					&& !cartData.getPointOfService().getStoreId().equalsIgnoreCase(pointOfServiceData.getStoreId()))))
			{
				cartData.setPointOfService(pointOfServiceData);	
			}
			

			final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
					.populateFulfilmentStatus(cartData);
			if (cartData != null && cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)
					|| BooleanUtils.isTrue(fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING)))
			{
				final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
				final String shippingRate = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);
				final BigDecimal total = cartData.getTotalPriceWithTax().getValue().add(BigDecimal.valueOf(Double.parseDouble(shippingRate)));
				cartData.getTotalPriceWithTax().setValue(total);
				cartData.getTotalPriceWithTax().setFormattedValue(fmt.format(total));
			}
			
			if(cartData != null && cartData.getDeliveryCost() != null)
			{
				final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
				final BigDecimal total = cartData.getTotalPriceWithTax().getValue().subtract(cartData.getDeliveryCost().getValue());
				cartData.getTotalPriceWithTax().setValue(total);
				cartData.getTotalPriceWithTax().setFormattedValue(fmt.format(total));
			}
			
			OrderTypeFormWsDTO orderTypeFormWsDTO = new OrderTypeFormWsDTO();
			prepareForm(cartData, orderTypeFormWsDTO);
			prepareFulfilmentOptionForm(cartData, orderTypeFormWsDTO, fields);

			boolean isCCDisabledAtDC = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getIsCCDisabledAtDC(cartData);
			if(BooleanUtils.isTrue(isCCDisabledAtDC))
			{
				orderTypeFormWsDTO.setIsCCDisabledAtDC(true);
			}
		
			final List<RegionData> regionDataList = siteOneRegionFacade.getRegionsForCountryCode(COUNTRY_CODE);
			regionDataList.sort(Comparator.comparing(RegionData::getIsocodeShort));
			orderTypeFormWsDTO.setRegionDataList(regionDataList);
			orderTypeFormWsDTO.setRecaptchaPublicKey(Config.getString("recaptcha.publickey", null));
			orderTypeFormWsDTO.setEnabledFulfillmentStatus(((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFulfilmentStatus(cartData));
			orderTypeFormWsDTO.setAfterNoonCutOffTime(Config.getString("timeLimit.afternoon.checkout", "14:30"));
			return orderTypeFormWsDTO;
		}
		catch (Exception ex)
	    {
			LOG.error("Exception occured while calling through method getCheckoutDetails ", ex);
	        throw new RuntimeException("Exception occured while calling through method getCheckoutDetails");
	    }
		
	}
	
	@PostMapping(value = "/kount/evaluateInquiry")
	@ApiBaseSiteIdParam
	@Operation(operationId = "evaluateInquiry", summary = "Evaluate Inquiry for kount", description = "Evaluate Inquiry kount for OrderCode")
	public @ResponseBody String evaluateInquiry(@RequestParam(name = "kountSessionId") final String kountSessionId,@RequestParam(value = "storeId") final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId, @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields,
			@RequestParam(value = "guid", required = false)	final String guid,	final HttpServletRequest request)
	{
		try
		{
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);
			checkoutFacade.prepareCartForCheckout();
			
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();		
			sessionService.setAttribute("kountSessionId", kountSessionId);

			String kountAuto = ((SiteOneCustomerFacade) customerFacade).getKountValue(cartData, request);

			Gson gson = new Gson();
			if ((StringUtils.isNotBlank(kountAuto)))
			{
				String[] splitResponse = kountAuto.split("_");
				if (splitResponse[0].equalsIgnoreCase(RESPONSE_DECLINED))
				{
					 if (null!=cartData.getB2bCustomerData() && null!=cartData.getB2bCustomerData().getUnit())
					{
						 final KountDeclineEvent kountDeclineEvent = new KountDeclineEvent();
					
					     eventService.publishEvent(initializeEvent(kountDeclineEvent, cartData));
					}
					 return gson.toJson(DECLINED);
				}
				return gson.toJson(kountAuto);
			}
			else
			{

				return gson.toJson(KOUNT_RESPONSE_DECLINED);
			}
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method evaluateInquiry");
	    }
		
	}

	
	@PostMapping("/saveFulfilmentOptions")
	@ApiBaseSiteIdParam
	@Operation(operationId = "saveFulfilmentOptions", summary = "Save the fulfillment details", description = "Save the fulfillment details")
	public @ResponseBody SiteoneFulfilmentWsDTO  saveFulfilmentOptions(final SiteOneOrderTypeForm siteOneOrderTypeForm,
			@RequestParam(value = "storeId", required = false)	final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@RequestParam(value = "guid", required = false)	final String guid) throws CommerceCartModificationException
	{

		try{
        	final B2BUnitData unit;
    		if (!StringUtils.isEmpty(unitId))
    		{
    			unit = b2bUnitFacade.getUnitForUid(unitId);
    		} else {
    			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
    		}
    		storeSessionFacade.setSessionShipTo(unit);
    		if (null != storeId && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(MIXEDCART_ENABLED_BRANCHES,storeId)){
    			sessionService.setAttribute("isMixedCartEnabled","mixedcart");
    		}
    		
    		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);
    		
    		
    		final SiteoneFulfilmentData siteoneFulfilmentData = new SiteoneFulfilmentData();
    		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
    		if(cartData.getOrderType() != null && (cartData.getOrderType().equalsIgnoreCase((SiteoneCoreConstants.ORDERTYPE_SHIPPING))
    				|| (!CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()) && !CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries())))){
    			siteoneFulfilmentData.setIsNationalShipping(BooleanUtils.isFalse(cartData.getIsTampaBranch()) && BooleanUtils.isFalse(cartData.getIsLABranch()));
    		}
    		
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateCartDataBasedOnOrderType(cartData, getFacadeForm(siteOneOrderTypeForm));
			final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
					.populateFulfilmentStatus(cartData);
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				sessionService.setAttribute("isSplitMixedCartEnabledBranch", true);
			}
			
			b2bCheckoutFacade.updateCheckoutCart(cartData);
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFreights(cartData, fulfilmentStatus);
			}
			siteoneFulfilmentData.setCartData(	((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populatecartData(cartData));
			
			siteoneFulfilmentData.setIsSuccess(true);
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateShippingState(cartData,siteoneFulfilmentData);
			
			SiteoneFulfilmentWsDTO siteoneFulfilmentWsDTO = getDataMapper().map(siteoneFulfilmentData , SiteoneFulfilmentWsDTO.class);
			
			if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
					.isBranchPresentUnderFeatureSwitch("BulkDeliveryBranches", storeSessionFacade.getSessionStore().getStoreId()))
			{
				siteoneFulfilmentWsDTO.setIsBulkDeliveryBranch(true);
			}
			else
			{
				siteoneFulfilmentWsDTO.setIsBulkDeliveryBranch(false);
			}
			
			return siteoneFulfilmentWsDTO;
		}
		catch(Exception ex)
		{
			LOG.error(ex);
			throw new RuntimeException("Exception occured while calling through method saveFulfilmentOptions");
		}
        
	}
	public KountDeclineEvent initializeEvent(final KountDeclineEvent event, final CartData cartData)
	{

		event.setAccountNumber(cartData.getB2bCustomerData().getUnit().getUid());
		event.setAccountName(cartData.getB2bCustomerData().getUnit().getName());
		event.setCustomerName(cartData.getB2bCustomerData().getFirstName());
		event.setEmailAddress(cartData.getB2bCustomerData().getUid());
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		event.setOrderNumber(cartData.getCode());
		return event;

	}
	protected SiteOneOrderTypeFacadeForm getFacadeForm(SiteOneOrderTypeForm siteOneOrderTypeForm) {
		SiteOneOrderTypeFacadeForm siteOneOrderTypeFormToFacade =new SiteOneOrderTypeFacadeForm();
		final BeanWrapper wrappedSource = new BeanWrapperImpl(siteOneOrderTypeForm);
		String[] nullProps= Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
				.toArray(String[]::new);
		BeanUtils.copyProperties(siteOneOrderTypeForm,siteOneOrderTypeFormToFacade,nullProps);
		return siteOneOrderTypeFormToFacade;
	}
	protected SiteOneGCContactFacadeForm getFacadeGCForm(SiteOneGCContactForm siteOneGCContactForm) {
		SiteOneGCContactFacadeForm siteOneGCContactFacadeForm =new SiteOneGCContactFacadeForm();
		final BeanWrapper wrappedSource = new BeanWrapperImpl(siteOneGCContactForm);
		String[] nullProps= Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
				.toArray(String[]::new);
		BeanUtils.copyProperties(siteOneGCContactForm,siteOneGCContactFacadeForm,nullProps);
		return siteOneGCContactFacadeForm;
	}

	protected void prepareForm(final CartData cartData, OrderTypeFormWsDTO orderTypeFormWsDTO)
	{
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (null != cartData.getB2bCustomerData())
			{
				CustomerData contactPerson = cartData.getB2bCustomerData();
				UserWsDTO userWsData = mapper.map(contactPerson,UserWsDTO.class);
				orderTypeFormWsDTO.setContactPerson(userWsData);
			}

			final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();
			if(sessionShipTo.getAddresses() != null) {
				orderTypeFormWsDTO.setAddressData(sessionShipTo.getAddresses().get(0));				
			}
			Set<CustomerData> custSetData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getCustomersForUnit(sessionShipTo.getUid());
			List<CustomerData> custListData = new ArrayList<CustomerData>(); 
			custListData.addAll(custSetData); 
			List<UserWsDTO> userListWsData = mapper.mapAsList(custListData,UserWsDTO.class);
			orderTypeFormWsDTO.setCustomers(userListWsData);
			orderTypeFormWsDTO.setIsGuestUser(false);
			orderTypeFormWsDTO.setIsCreditPaymentBlocked(false);
		}
		else
		{
			orderTypeFormWsDTO.setIsGuestUser(true);
			if (null != cartData.getB2bCustomerData() && null != cartData.getB2bCustomerData().getDisplayUid())
			{		
				String originalID = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getOriginalEmail(cartData.getB2bCustomerData().getDisplayUid());
					final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
							.getSiteoneCCAuditDetails(originalID);
				orderTypeFormWsDTO.setIsCreditPaymentBlocked(getFlagForCreditCardPayment(auditModel,originalID));				
			}	
			
		}
		if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("BulkDeliveryBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			orderTypeFormWsDTO.setIsBulkDeliveryBranch(true);
		}
		else
		{
			orderTypeFormWsDTO.setIsBulkDeliveryBranch(false);
		}
		mapperFactory.classMap(CartData.class, CartWsDTO.class);
		CartWsDTO cartWsData = mapper.map(cartData, CartWsDTO.class);
		orderTypeFormWsDTO.setCartData(cartWsData);
	}

	
	protected void prepareFulfilmentOptionForm(final CartData cartData,  OrderTypeFormWsDTO orderTypeFormWsDTO, String fields)
	{
		final PointOfServiceData posData = storeFinderFacade.getStoreForId(((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore().getStoreId());
		final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();
		String orderType = null;
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			final CustomerData currentCustomer = customerFacade.getCurrentCustomer();
			if (currentCustomer.getRoles().contains("b2badmingroup"))
			{
				orderTypeFormWsDTO.setRegions(getI18NFacade().getRegionsForCountryIso("US"));
			}
			orderTypeFormWsDTO.setDeliveryAddresses(((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sessionShipTo.getUid()));
			if(sessionShipTo.getAddresses() != null) {			
				orderTypeFormWsDTO.setDeliveryAddress(sessionShipTo.getAddresses().get(0));				
			}
			UserWsDTO userWsData = getDataMapper().map(currentCustomer, UserWsDTO.class, fields);
			orderTypeFormWsDTO.setCurrentCustomer(userWsData);
		}
		else
		{
			sessionService.setAttribute("guestUser","guest");
		}
		if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
		{
			final String shippingRate = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);
			orderTypeFormWsDTO.setFlatRateShippingFee(shippingRate);
		}
		if(((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFulfilmentStatus(cartData).get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING)) {
			final String shippingRate = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);
			orderTypeFormWsDTO.setFlatRateShippingFee(shippingRate);
		}
		if(cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)){
			orderType = SiteoneCoreConstants.ORDERTYPE_SHIPPING_NAME;
		}else{
			orderType = cartData.getOrderType();
		}
		List<String> siteoneHolidaysList = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getStoreHolidayDates();
		posData.setSiteOneHolidays(siteoneHolidaysList);
		orderTypeFormWsDTO.setStore(posData);
		orderTypeFormWsDTO.setOrderType( cartData.getOrderType());
		orderTypeFormWsDTO.setOrderTypeName(orderType);
	}
	
	@PostMapping("/saveContactDetails")
	@ApiBaseSiteIdParam
	@Operation(operationId = "saveContactDetails", summary = "Save the contact details", description = "Save the guest contact details")
	public @ResponseBody SiteOneGuestUserWsDTO saveContactDetails(final SiteOneGCContactForm siteOneGCContactForm,
			@RequestParam(value = "storeId", required = true) final String storeId,
			@RequestParam(value = "guid", required = true)	final String guid) throws CommerceCartModificationException
	{
		
		try
		{
			final B2BUnitData unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			sessionService.setAttribute("guestUser","guest");
			storeSessionFacade.setSessionShipTo(unit);
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);		
			checkoutFacade.prepareCartForCheckout();
			
			SiteOneGuestUserData siteOneGuestUserData = new SiteOneGuestUserData();
			
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
			final CustomerData customerdata = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateGuestUserData(getFacadeGCForm(siteOneGCContactForm));
			siteOneGuestUserData = ((SiteOneCustomerFacade) customerFacade).populateGuestUserData(cartData, customerdata,
					siteOneGCContactForm.getIsSameaddressforParcelShip());
			
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateUserContact(cartData, siteOneGuestUserData,
					siteOneGCContactForm.getIsSameaddressforParcelShip(),siteOneGCContactForm.getState());
			
			final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
					.getSiteoneCCAuditDetails(customerdata.getEmail());
			final Boolean flag = getFlagForCreditCardPayment(auditModel, customerdata.getEmail());
			if (flag.equals(Boolean.TRUE))
			{
				siteOneGuestUserData.setIsCreditPaymentBlocked("Decline_Limit_Over");
			}
			else
			{
				siteOneGuestUserData.setIsCreditPaymentBlocked("");
			}

			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			SiteOneGuestUserWsDTO guestUserDTO= mapper.map(siteOneGuestUserData,SiteOneGuestUserWsDTO.class);
			return guestUserDTO;
		}
		
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method saveContactDetails");
	    }
		
	}

	@PostMapping("/validate-address")
	@ApiBaseSiteIdParam
	@Operation(operationId = "validate-address", summary = "Validate the address details", description = "Validate the guest address details")
	public @ResponseBody SiteOneAddressVerificationWsDTO validateAddress(final SiteOneGCContactForm siteOneGCContactForm,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		try
		{
			final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(siteOneGCContactForm);
			SiteOneAddressVerificationData addressVerificationData = ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
			return getDataMapper().map(addressVerificationData, SiteOneAddressVerificationWsDTO.class, fields);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method validateAddress");
	    }
		
	}

	@GetMapping("/isEmailAlreadyExists")
	@ApiBaseSiteIdParam
	@Operation(operationId = "isEmailAlreadyExists", summary = "Validate the email address", description = "Validate the email address")
	public @ResponseBody EmailValidationWsDTO isEmailAlreadyExists(@RequestParam("email") final String email,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		boolean isMailAvailable = false;
		final EmailValidationData emailValidationData = new EmailValidationData();
		try
		{
			isMailAvailable = ((SiteOneCustomerFacade) customerFacade).isUserAlreadyExists(email.toLowerCase());
			final Boolean isAvailable = Boolean.valueOf(isMailAvailable);
			emailValidationData.setIsMailAvailable(isAvailable);
			emailValidationData.setIsSuccess(Boolean.TRUE);
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to create contact", resourceAccessException);
			emailValidationData.setIsMailAvailable(Boolean.FALSE);
			emailValidationData.setIsSuccess(Boolean.FALSE);
			emailValidationData.setErrorMessage("We are unable to process your request at this time.");
		}
		catch (final Exception exception)
		{
			LOG.error("Not able to establish connection with UE to create contact", exception);
			emailValidationData.setIsMailAvailable(Boolean.FALSE);
			emailValidationData.setIsSuccess(Boolean.FALSE);
			emailValidationData.setErrorMessage("We are unable to process your request at this time.");
		}

		return getDataMapper().map(emailValidationData, EmailValidationWsDTO.class, fields);
	}
	
	@PostMapping("/saveAlternateContactDetails")
	@ApiBaseSiteIdParam
	@Operation(operationId = "saveAlternateContactDetails", summary = "Save the contact details", description = "Save the guest contact details")
	public @ResponseBody SiteOneGuestUserWsDTO saveAlternateContactDetails(final SiteOneGCContactForm siteOneGCContactForm,
			@RequestParam(value = "storeId", required = true) final String storeId,
			@RequestParam(value = "guid", required = true)	final String guid) throws CommerceCartModificationException
	{
        try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(guid);
			final SiteOneGuestUserData siteOneGuestUserData = new SiteOneGuestUserData();
			
			final CustomerData customerdata = 	((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateGuestUserData(getFacadeGCForm(siteOneGCContactForm));

			((SiteOneCustomerFacade) customerFacade).saveAlternateContactDetails(customerdata, siteOneGCContactForm.getDeliveryMode());

			siteOneGuestUserData.setCustomerData(customerdata);

			siteOneGuestUserData.setAddressData(customerdata.getDefaultAddress());
			
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			SiteOneGuestUserWsDTO guestUserDTO= mapper.map(siteOneGuestUserData,SiteOneGuestUserWsDTO.class);
			return guestUserDTO;
		}
        catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method saveAlternateContactDetails");
	    }
		
	}
	
	public I18NFacade getI18NFacade() {
		return i18NFacade;
	}

	public void setI18NFacade(I18NFacade i18nFacade) {
		i18NFacade = i18nFacade;
	}
	
	public Boolean getFlagForCreditCardPayment(final SiteoneCCPaymentAuditLogModel auditModel, final String email)
	{
		if (null != auditModel)
		{
			final int declineCountLimit = Integer
					.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
			final int declineCount = auditModel.getDeclineCount().intValue();
			LOG.error("declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);
			if (declineCount < declineCountLimit)
			{
				return Boolean.FALSE;
			}
			else
			{
				LOG.error("Cayan response is declined");
				try
				{
					final int creditCardBlockedSpan = Integer
							.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("CreditCardBlockedSpan"));

					final Date lastModification = auditModel.getModifiedtime();
					final Date currentDate = timeService.getCurrentTime();
					final Calendar threshold = Calendar.getInstance();
					threshold.setTime(currentDate);
					threshold.add(Calendar.HOUR, -creditCardBlockedSpan);
					if (lastModification.before(threshold.getTime()))
					{
						siteoneLinkToPayAuditLogService.resetSiteoneCCAuditLog(email);
						return Boolean.FALSE;
					}
					else
					{
						return Boolean.TRUE;
					}
				}
				catch (final Exception e)
				{
					LOG.error("Failed to add Credit Card details", e);
					return Boolean.FALSE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
}