/**
 *
 */
package com.siteone.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractCheckoutController.SelectOption;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.EmailValidationData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.SiteOneGuestUserData;
import de.hybris.platform.commercefacades.user.data.SiteoneFulfilmentData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

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
import java.util.stream.Stream;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.enums.MeridianTimeEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.recaptcha.util.VerifyRecaptchaUtils;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.form.SiteOneGCContactFacadeForm;
import com.siteone.facades.checkout.form.SiteOneOrderTypeFacadeForm;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.checkout.steps.validation.impl.SiteOneOrderTypeFormValidator;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneAddPhoneNumberForm;
import com.siteone.storefront.forms.SiteOneAddressForm;
import com.siteone.storefront.forms.SiteOneGCContactForm;
import com.siteone.storefront.forms.SiteOneOrderTypeForm;
import com.siteone.storefront.util.SiteOneAddressDataUtil;
import com.siteone.storefront.util.SiteOneCheckoutUtils;

/**
 * @author Karasan
 *
 */
@Controller
@RequestMapping(value = "/checkout/multi")
public class SiteOneCheckoutController extends AbstractPageController
{
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;


	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "storeSessionFacade")
	protected StoreSessionFacade storeSessionFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@Resource(name = "orderTypeFormValidator")
	private SiteOneOrderTypeFormValidator orderTypeFormValidator;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "verifyRecaptchaUtils")
	private VerifyRecaptchaUtils verifyRecaptchaUtils;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "siteOneCheckoutUtils")
	private SiteOneCheckoutUtils siteOneCheckoutUtils;

	@Resource
	private CommonI18NService commonI18NService;

	@Resource(name = "siteoneAddressDataUtil")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;

	@Resource(name = "timeService")
	private TimeService timeService;

	private final String COUNTRY_CODE = "US";

	private static final String ORDER_TYPE = "order-type";
	private static final String REDIRECT_CART_URL = REDIRECT_PREFIX + "/cart";

	private static final String ADDRESS_FORM_ATTR = "siteOneAddressForm";
	private static final String REGIONS_ATTR = "regions";

	private static final Logger LOG = Logger.getLogger(SiteOneCheckoutController.class);

	private static final String FULLPAGEPATH_B2B = "checkout: checkout";
	private static final String FULLPAGEPATH_GUEST = "checkout: checkout as guest";
	private static final String PATHING_CHANNEL = "checkout";

	@GetMapping("/siteOne-checkout")
	public String getContactDetails(final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();

		if (userFacade.isAnonymousUser() && (cartData.getEntries()==null || cartData.getEntries().isEmpty()))
		{
			LOG.info("Missing or empty cart");
			// No session cart or empty session cart. Bounce back to the cart page.
			return REDIRECT_CART_URL;
		}
		
		if (!userFacade.isAnonymousUser())
		{
			final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

			if (!(((SiteOneB2BUnitFacade) b2bUnitFacade).checkIsUnitForApproveOrders(parent.getUid()))
					&& null != customerModel.getNeedsOrderApproval() && BooleanUtils.isTrue(customerModel.getNeedsOrderApproval()))
			{
				customerModel.setNeedsOrderApproval(Boolean.FALSE);
				getModelService().save(customerModel);
			}
		}

      final List<String> siteoneHolidays = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getStoreHolidayDates();
		
		if (siteoneHolidays != null && !siteoneHolidays.isEmpty()) 
		{
		   model.addAttribute("siteoneHolidays", siteoneHolidays);
		}
	
		
		if (null != cartData && null != cartData.getTotalTax())
		{
			final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
			final BigDecimal totalTax = cartData.getTotalTax().getValue();
			final BigDecimal total = cartData.getTotalPriceWithTax().getValue().subtract(totalTax);
			cartData.getTotalPriceWithTax().setValue(total);
			cartData.getTotalPriceWithTax().setFormattedValue(fmt.format(total));
			cartData.getTotalTax().setValue(BigDecimal.valueOf(0.0d));
			cartData.getTotalTax().setFormattedValue("$".concat("0.00"));
		}
		
		final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
				.populateFulfilmentStatus(cartData);
		if (cartData != null && cartData.getOrderType() != null && (cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)
				|| BooleanUtils.isTrue(fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))))
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
		if(cartData.getTotalPriceWithTax()!=null) {
			model.addAttribute("guestTotalPriceWithTax", cartData.getTotalPriceWithTax().getValue());
		}else if(cartData.getTotalPrice()!=null) {
			model.addAttribute("guestTotalPriceWithTax", cartData.getTotalPrice().getValue());
		}
		prepareForm(cartData, model);
		prepareFulfilmentOptionForm(cartData, model);
		model.addAttribute("SiteOneGCContactForm", new SiteOneGCContactForm());
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		final List<RegionData> regionDataList = siteOneRegionFacade.getRegionsForCountryCode((((basesite.getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_CA))
				? SiteoneCoreConstants.CA_ISO_CODE : COUNTRY_CODE)));
		regionDataList.sort(Comparator.comparing(RegionData::getIsocodeShort));
		model.addAttribute("states", regionDataList);
		model.addAttribute("recaptchaPublicKey", Config.getString("recaptcha.publickey", null));
		storeCmsPageInModel(model, getContentPageForLabelOrId("siteOneCheckoutPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("siteOneCheckoutPage"));

		boolean isCCDisabledAtDC = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getIsCCDisabledAtDC(cartData);
		if(BooleanUtils.isTrue(isCCDisabledAtDC))
		{
			model.addAttribute("isCCDisabledAtDC", isCCDisabledAtDC);
		}

		/* SE-9752 analytics change starts */
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			model.addAttribute("fullPagePath", FULLPAGEPATH_B2B);
		}
		else
		{
			model.addAttribute("fullPagePath", FULLPAGEPATH_GUEST);
		}
		model.addAttribute("pathingChannel", PATHING_CHANNEL);
		/* SE-9752 analytics change ends */

		return ControllerConstants.Views.Pages.MultiStepCheckout.SiteOneCheckoutPage;
	}

	protected void prepareForm(final CartData cartData, final Model model)
	{
		String isCreditPaymentBlocked = "";
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{			
			if (null != cartData.getB2bCustomerData())
			{
				model.addAttribute("contactPerson", cartData.getB2bCustomerData());
			}

			final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();
			if(sessionShipTo.getAddresses()!=null) {
				model.addAttribute("addressData", sessionShipTo.getAddresses().get(0));
			}
			model.addAttribute("customers", ((SiteOneB2BUnitFacade) b2bUnitFacade).getCustomersForUnit(sessionShipTo.getUid()));
			model.addAttribute("isGuestUser", Boolean.FALSE);
		}
		else
		{
			if (null != cartData.getB2bCustomerData() && null != cartData.getB2bCustomerData().getDisplayUid())
			{
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).formGuestUserData(cartData.getB2bCustomerData(), model);
				
				String originalID = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getOriginalEmail(cartData.getB2bCustomerData().getDisplayUid());
				final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
						.getSiteoneCCAuditDetails(originalID);

				final Boolean flag = getFlagForCreditCardPayment(auditModel, originalID);
				if (flag.equals(Boolean.TRUE))
				{
					isCreditPaymentBlocked = "Decline_Limit_Over";
				}
			}	
			model.addAttribute("isGuestUser", Boolean.TRUE);
		}
		model.addAttribute("isCreditPaymentBlocked", isCreditPaymentBlocked);
		model.addAttribute("cartData", cartData);

	}

	@PostMapping("/saveContactDetails")
	public @ResponseBody SiteOneGuestUserData saveContactDetails(final SiteOneGCContactForm siteOneGCContactForm,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException, CommerceCartModificationException
	{

		SiteOneGuestUserData siteOneGuestUserData = new SiteOneGuestUserData();
		try
		{
			final String recaptchaResponse = request.getParameter("recaptcha");

			boolean verify = false;
			if (StringUtils.isNotEmpty(recaptchaResponse))
			{
				verify = verifyRecaptchaUtils.checkAnswer(recaptchaResponse);
			}

			if (!verify || bindingResult.hasErrors())
			{
				siteOneGuestUserData.setRecaptchaChallengeAnswered(Boolean.valueOf(verify));
				LOG.info("Recaptcha Failure");
				return siteOneGuestUserData;
			}

			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();

			final CustomerData customerdata = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
					.populateGuestUserData(getFacadeGCForm(siteOneGCContactForm));

			siteOneGuestUserData = ((SiteOneCustomerFacade) customerFacade).populateGuestUserData(cartData, customerdata,
					siteOneGCContactForm.getIsSameaddressforParcelShip());
			final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
					.populateFulfilmentStatus(cartData);
			if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_DELIVERY))
			{
				siteOneGuestUserData.setIsSameAsContactInfo(siteOneGCContactForm.getIsSameaddressforParcelShip());
			}
			else if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
			{
				siteOneGuestUserData.setIsSameAsContactInfo(siteOneGCContactForm.getIsSameaddressforParcelShip());
				final boolean validState = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).isShippingStateValid(
						((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore(), siteOneGCContactForm.getState());
				siteOneGuestUserData.setIsShippingStateValid(validState);
			}
			if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_DELIVERY))
			{
				siteOneGuestUserData.setIsSameAsContactInfo(siteOneGCContactForm.getIsSameaddressforParcelShip());
			}
			else if (cartData.getOrderType() != null
					&& cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
			{
				siteOneGuestUserData.setIsSameAsContactInfo(siteOneGCContactForm.getIsSameaddressforParcelShip());
				final boolean validState = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).isShippingStateValid(
						((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore(), siteOneGCContactForm.getState());
				siteOneGuestUserData.setIsShippingStateValid(validState);
			}
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
		}
		catch (final Exception e)
		{
			LOG.info("Add/Edit Guest User Failure");
			return null;
		}

		return siteOneGuestUserData;
	}

	@PostMapping("/update-contact")
	public @ResponseBody String updateContactData(@RequestParam(name = "uid")
	final String uid, @RequestParam(name = "deliveryMode")
	final String deliveryMode)
	{
		final CustomerData customer = ((SiteOneCustomerFacade) customerFacade).getCustomerForId(uid.trim().toLowerCase());
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		if (null != deliveryMode)
		{
			if (deliveryMode.equalsIgnoreCase("pickup"))
			{
				cartData.setContactPerson(customer);
			}
			else if (deliveryMode.equalsIgnoreCase("standard-net"))
			{
				cartData.setDeliveryContactPerson(customer);
			}
			else
			{
				cartData.setShippingContactPerson(customer);
			}
		}
		else
		{
			cartData.setContactPerson(customer);
		}
		try
		{
			b2bCheckoutFacade.updateCheckoutCart(cartData);

		}
		catch (final Exception ex)
		{
			return SiteoneCoreConstants.RESPONSE_FAILURE;
		}

		return SiteoneCoreConstants.RESPONSE_SUCCESS;
	}

	@GetMapping("/isEmailAlreadyExists")
	public @ResponseBody EmailValidationData isEmailAlreadyExists(@RequestParam("email")
	final String email)
	{
		final EmailValidationData emailValidationData = new EmailValidationData();
		try
		{
			if (!StringUtils.isBlank(email))
			{
				emailValidationData
						.setIsMailAvailable(((SiteOneCustomerFacade) customerFacade).isUserAlreadyExists(email.toLowerCase()));
				emailValidationData.setIsSuccess(Boolean.TRUE);
			}
			else
			{
				LOG.error("Email value is empty or null");
				emailValidationData.setIsMailAvailable(Boolean.FALSE);
				emailValidationData.setIsSuccess(Boolean.FALSE);
				emailValidationData.setErrorMessage(
						getMessageSource().getMessage("text.email.add.error", null, getI18nService().getCurrentLocale()));
			}
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to create contact", resourceAccessException);
			emailValidationData.setIsMailAvailable(Boolean.FALSE);
			emailValidationData.setIsSuccess(Boolean.FALSE);
			emailValidationData
					.setErrorMessage(getMessageSource().getMessage("text.email.add.error", null, getI18nService().getCurrentLocale()));
		}
		catch (final Exception exception)
		{
			LOG.error("Not able to establish connection with UE to create contact", exception);
			emailValidationData.setIsMailAvailable(Boolean.FALSE);
			emailValidationData.setIsSuccess(Boolean.FALSE);
			emailValidationData
					.setErrorMessage(getMessageSource().getMessage("text.email.add.error", null, getI18nService().getCurrentLocale()));
		}
		return emailValidationData;
	}

	@GetMapping("/add-phone")
	public String getAddPhoneNumber(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(new SiteOneAddPhoneNumberForm());
		return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
	}

	@PostMapping("/add-phone")
	public String addPhoneNumber(@Valid
	final SiteOneAddPhoneNumberForm form, final BindingResult bindingResult, final Model model) throws CMSItemNotFoundException
	{
		if (((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).validatePhoneNumber(form.getPhoneNumber()))
		{
			bindingResult.rejectValue("phoneNumber", "address.phone.invalid");
		}

		if (bindingResult.hasErrors())
		{
			return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
		}
		else
		{
			//call to add  phone number

			final String phoneNumber = form.getPhoneNumber();
			final String uid = form.getEmailId();

			try
			{
				((SiteOneCustomerFacade) customerFacade).addPhoneNumber(phoneNumber, uid);
				GlobalMessages.addInfoMessage(model, "checkout.phonenumber.updated");
			}
			catch (final ServiceUnavailableException serviceUnavailableException)
			{
				LOG.error("Service Unavailable Exception Occured", serviceUnavailableException);
				model.addAttribute("isPhoneNumberUpdated", false);
				return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
			}
			catch (final ContactNotCreatedOrUpdatedInUEException contactNotCreatedOrUpdatedInUEException)
			{
				LOG.error("Contact Not Created/Updated In UE", contactNotCreatedOrUpdatedInUEException);
				model.addAttribute("isPhoneNumberUpdated", false);
				return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.error("unknown Identifier Exception: " + uid, unknownIdentifierException);
				model.addAttribute("isPhoneNumberUpdated", false);
				return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberPopup;
			}

		}

		return ControllerConstants.Views.Fragments.Checkout.AddPhoneNumberSuccessPopup;
	}

	@ModelAttribute("meridianTime")
	public List<SelectOption> getMeridianTime()
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();
		selectOptions
				.add(new SelectOption(MeridianTimeEnum.AM.getCode(), enumerationService.getEnumerationName(MeridianTimeEnum.AM)));
		selectOptions
				.add(new SelectOption(MeridianTimeEnum.PM.getCode(), enumerationService.getEnumerationName(MeridianTimeEnum.PM)));
		selectOptions
		.add(new SelectOption(MeridianTimeEnum.ANYTIME.getCode(), enumerationService.getEnumerationName(MeridianTimeEnum.ANYTIME)));
		return selectOptions;
	}

	protected void prepareFulfilmentOptionForm(final CartData cartData, final Model model)
	{

		final PointOfServiceData posData = ((SiteOneStoreFinderFacade) storeFinderFacade)
				.getStoreForId(((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore().getStoreId());
		final CustomerData currentCustomer = customerFacade.getCurrentCustomer();
		final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();
		String orderType = null;
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{

			if (currentCustomer.getRoles().contains("b2badmingroup") || (currentCustomer.getEnableAddModifyDeliveryAddress() != null
					&& currentCustomer.getEnableAddModifyDeliveryAddress().booleanValue()))
			{
				final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
				model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso(((basesite.getUid().equalsIgnoreCase(SiteoneCoreConstants.BASESITE_CA))
						? SiteoneCoreConstants.CA_ISO_CODE :"US")));
				final SiteOneAddressForm addressForm = getPreparedAddressForm();
				addressForm.setUnitId(sessionShipTo.getUid());
				model.addAttribute(ADDRESS_FORM_ATTR, addressForm);
			}

			model.addAttribute("deliveryAddresses",
					((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sessionShipTo.getUid()));
			model.addAttribute("enableAddDeliveryAddress", currentCustomer.getEnableAddModifyDeliveryAddress());
			if(sessionShipTo.getAddresses()!=null) {
				model.addAttribute("deliveryAddress", sessionShipTo.getAddresses().get(0));
			}
		}
		final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
				.populateFulfilmentStatus(cartData);
		if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING)
				|| BooleanUtils.isTrue(fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING)))
		{
			final String shippingRate = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getShippingRate(cartData);
			model.addAttribute("flatRateShippingFee", shippingRate);
		}

		if (!model.containsAttribute("siteOneOrderTypeForm"))
		{
			final SiteOneOrderTypeForm siteOneOrderTypeForm = new SiteOneOrderTypeForm();

			if (null != cartData.getOrderType())
			{
				siteOneOrderTypeForm.setOrderType(cartData.getOrderType());
			}


			LOG.info("inside Store Id>" + posData.getStoreId());
			siteOneOrderTypeForm.setStoreId(posData.getStoreId());

			if (null != cartData.getDeliveryAddress())
			{
				LOG.info("inside Delivery Address>" + cartData.getDeliveryAddress());
				siteOneOrderTypeForm.setAddressId(cartData.getDeliveryAddress().getId());
			}

			if (null != cartData.getContactPerson())
			{
				LOG.info("inside contact Details>>" + cartData.getContactPerson());
				siteOneOrderTypeForm.setContactId(cartData.getContactPerson().getEmail().trim().toLowerCase());
			}
			else
			{
				LOG.info("inside contact Details>>" + currentCustomer.getDisplayUid());
				siteOneOrderTypeForm.setContactId(currentCustomer.getDisplayUid());
			}
			if (null != cartData.getRequestedMeridian())
			{
				LOG.info("inside requested Meridian>" + cartData.getRequestedMeridian());
				siteOneOrderTypeForm.setRequestedMeridian(cartData.getRequestedMeridian());
			}
			if (null != cartData.getRequestedDate())
			{
				LOG.info("inside requested Date>" + cartData.getRequestedDate());
				siteOneOrderTypeForm.setRequestedDate(cartData.getRequestedDate());
			}

			siteOneOrderTypeForm.setSpecialInstruction(cartData.getSpecialInstruction());
			siteOneOrderTypeForm.setPickupInstruction(cartData.getPickupInstruction());
			siteOneOrderTypeForm.setDeliveryInstruction(cartData.getDeliveryInstruction());
			siteOneOrderTypeForm.setShippingInstruction(cartData.getShippingInstruction());
			siteOneOrderTypeForm.setPurchaseOrderNumber(cartData.getPurchaseOrderNumber());

			model.addAttribute("siteOneOrderTypeForm", siteOneOrderTypeForm);
		}


		if (cartData.getOrderType() != null && cartData.getOrderType().equalsIgnoreCase(SiteoneCoreConstants.ORDERTYPE_SHIPPING))
		{
			orderType = SiteoneCoreConstants.ORDERTYPE_SHIPPING_NAME;
		}
		else
		{
			orderType = cartData.getOrderType();
		}

		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("pointOfService", posData);
		model.addAttribute("orderType", cartData.getOrderType());
		model.addAttribute("orderTypeName", orderType);
		model.addAttribute("currentCustomer", currentCustomer);

	}


	@PostMapping("/validate-address")
	public @ResponseBody SiteOneAddressVerificationData validateAddress(final SiteOneGCContactForm siteOneGCContactForm)
	{
		final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(siteOneGCContactForm);
		return ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
	}

	@PostMapping("/saveFulfilmentOptions")
	public @ResponseBody SiteoneFulfilmentData saveFulfilmentOptions(final SiteOneOrderTypeForm siteOneOrderTypeForm,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final SiteoneFulfilmentData siteoneFulfilmentData = new SiteoneFulfilmentData();
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		if (bindingResult.hasErrors())
		{
			model.addAttribute("siteOneOrderTypeForm", siteOneOrderTypeForm);
			prepareFulfilmentOptionForm(cartData, model);
			siteoneFulfilmentData.setTaxForCA(getSessionService().getAttribute("taxCA"));
			for (final Object object : bindingResult.getAllErrors())
			{
				if (object instanceof FieldError)
				{
					final FieldError fieldError = (FieldError) object;
					if (fieldError.getField().equalsIgnoreCase("addressId"))
					{
						siteoneFulfilmentData.setIsSuccess(false);
						siteoneFulfilmentData.setErrorMessage("Error in Address Entry");
					}
					else if (fieldError.getField().equalsIgnoreCase("contactId"))
					{
						siteoneFulfilmentData.setIsSuccess(false);
						siteoneFulfilmentData.setErrorMessage("Error in Contact Entry");
					}
				}
			}
			return siteoneFulfilmentData;
		}
		try
		{
			((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).updateCartDataBasedOnOrderType(cartData,
					getFacadeForm(siteOneOrderTypeForm));
		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to connect to UE with Delivery Cost", ex);
			siteoneFulfilmentData.setIsSuccess(false);
			return siteoneFulfilmentData;
		}
		catch (final Exception ex)
		{
			LOG.error("Unable to get delivery charge Please try again" + ex);
			siteoneFulfilmentData.setIsSuccess(false);
			return siteoneFulfilmentData;
		}
		final Map<String, Boolean> fulfilmentStatus = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
				.populateFulfilmentStatus(cartData);
		try
		{
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				getSessionService().setAttribute("isSplitMixedCartEnabledBranch", true);
			}
			else
			{
				getSessionService().setAttribute("isSplitMixedCartEnabledBranch", false);
			}
			b2bCheckoutFacade.updateCheckoutCart(cartData);
			if(!CollectionUtils.isEmpty(cartData.getPickupAndDeliveryEntries()) && !CollectionUtils.isEmpty(cartData.getShippingOnlyEntries()))
			{
				((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populateFreights(cartData, fulfilmentStatus);
			}
			siteoneFulfilmentData.setCartData(((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).populatecartData(cartData));
		}
		catch (final ResourceAccessException ex)
		{
			LOG.error("Unable to get customer specific price", ex);
			siteoneFulfilmentData.setIsSuccess(false);
			return siteoneFulfilmentData;
		}

		LOG.info("Requested Date>>" + cartData.getRequestedDate());
		siteoneFulfilmentData.setIsSuccess(true);
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
			if (fulfilmentStatus.get(SiteoneCoreConstants.DELIVERYMODE_SHIPPING))
			{
				final boolean validState = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
						.isShippingStateValid(((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore(), shippingState);
				siteoneFulfilmentData.setIsShippingStateValid(validState);
			}
		}
		siteoneFulfilmentData.setTaxForCA(getSessionService().getAttribute("taxCA"));
		return siteoneFulfilmentData;
	}


	@PostMapping("/saveAlternateContactDetails")
	public @ResponseBody SiteOneGuestUserData saveAlternateContactDetails(final SiteOneGCContactForm siteOneGCContactForm,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException, CommerceCartModificationException
	{

		final SiteOneGuestUserData siteOneGuestUserData = new SiteOneGuestUserData();
		try
		{


			final CustomerData customerdata = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade)
					.populateGuestUserData(getFacadeGCForm(siteOneGCContactForm));

			((SiteOneCustomerFacade) customerFacade).saveAlternateContactDetails(customerdata,
					siteOneGCContactForm.getDeliveryMode());

			siteOneGuestUserData.setCustomerData(customerdata);

			siteOneGuestUserData.setAddressData(customerdata.getDefaultAddress());
			getSessionService().setAttribute("guestAddressData", customerdata.getDefaultAddress());

		}
		catch (final Exception e)
		{
			LOG.info("Add/Edit Guest User Failure");
			return null;
		}

		return siteOneGuestUserData;
	}

	protected SiteOneAddressForm getPreparedAddressForm()
	{
		final CustomerData currentCustomerData = customerFacade.getCurrentCustomer();
		final SiteOneAddressForm addressForm = new SiteOneAddressForm();
		addressForm.setFirstName(currentCustomerData.getFirstName());
		addressForm.setLastName(currentCustomerData.getLastName());
		addressForm.setTitleCode(currentCustomerData.getTitleCode());
		return addressForm;
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
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	protected I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	protected SiteOneOrderTypeFacadeForm getFacadeForm(final SiteOneOrderTypeForm siteOneOrderTypeForm)
	{
		final SiteOneOrderTypeFacadeForm siteOneOrderTypeFormToFacade = new SiteOneOrderTypeFacadeForm();
		final BeanWrapper wrappedSource = new BeanWrapperImpl(siteOneOrderTypeForm);
		final String[] nullProps = Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
		BeanUtils.copyProperties(siteOneOrderTypeForm, siteOneOrderTypeFormToFacade, nullProps);
		return siteOneOrderTypeFormToFacade;
	}

	protected SiteOneGCContactFacadeForm getFacadeGCForm(final SiteOneGCContactForm siteOneGCContactForm)
	{
		final SiteOneGCContactFacadeForm siteOneGCContactFacadeForm = new SiteOneGCContactFacadeForm();
		final BeanWrapper wrappedSource = new BeanWrapperImpl(siteOneGCContactForm);
		final String[] nullProps = Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
		BeanUtils.copyProperties(siteOneGCContactForm, siteOneGCContactFacadeForm, nullProps);
		return siteOneGCContactFacadeForm;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
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
