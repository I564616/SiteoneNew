/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */

package com.siteone.storefront.controllers.pages.checkout.steps;


import de.hybris.platform.acceleratorservices.enums.CheckoutPciOptionEnum;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.PaymentDetailsForm;
import de.hybris.platform.assistedservicefacades.constants.AssistedservicefacadesConstants;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneAddEwalletData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOnePaymentUserData;
import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.voucher.VoucherFacade;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.enums.SiteOnePaymentMethodEnum;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.payment.cayan.SiteOneCayanTransportFacade;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.ewallet.impl.DefaultSiteOneEwalletFacadeImpl;
import com.siteone.facades.exceptions.CardAlreadyPresentInUEException;
import com.siteone.facades.exceptions.NickNameAlreadyPresentInUEException;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.CayanBoarcardResponseForm;
import com.siteone.storefront.forms.SiteOnePaymentForm;
import com.siteone.storefront.util.SiteOneCheckoutUtils;
import com.siteone.storefront.util.SiteOneEwalletDataUtil;
import com.siteone.storefront.util.SiteOnePaymentInfoUtil;


@Controller
@RequestMapping(value = "/checkout/multi/siteone-payment")
public class PaymentMethodCheckoutStepController extends AbstractCheckoutStepController
{
	protected static final Map<String, String> SITEONE_CARD_TYPES = new HashMap<>();
	private static final String SITEONE_PAYMENT = "siteone-payment";
	private static final String CART_DATA_ATTR = "cartData";
	private static final String CAYAN_PAYMENT_WEB_API = "siteone.cayan.payment.webApi";

	private static final String PAY_AT_BRANCH = "PAY_AT_BRANCH";
	private static final String PAY_ONLINE_WITH_CREDIT_CARD = "PAY_ONLINE_WITH_CREDIT_CARD";
	private static final String REDIRECT_TO_CART_PAGE = REDIRECT_PREFIX + "/cart";
	private static final String REDIRECT_TO_ORDERTYPE_PAGE = REDIRECT_PREFIX + "/checkout/multi/order-type/choose";
	private static final String REDIRECT_TO_PAYMENT_PAGE = "/checkout/multi/siteone-payment/add";
	private static final String REDIRECT_URL_ORDER_CONFIRMATION_PAGE = "/checkout/orderConfirmation/";
	private static final String REDIRECT_TO_ORDER_REVIEW_PAGE = REDIRECT_PREFIX + "/checkout/multi/order-summary/view";
	private static final String REDIRECT_TO_ORDER_REVIEW_SCREEN = "/checkout/multi/order-summary/view";
	private static final String CAYAN_USER_CALCEL = "User_Cancelled";
	private static final String CHECKED = "checked";
	private static final String DECLINE_LIMIT_OVER = "Decline_Limit_Over";
	private static final Logger LOGGER = Logger.getLogger(PaymentMethodCheckoutStepController.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Resource(name = "voucherFacade")
	private VoucherFacade voucherFacade;

	@Resource(name = "siteOneCheckoutUtils")
	private SiteOneCheckoutUtils siteOneCheckoutUtils;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "cartFacade")
	private SiteOneCartFacade siteOneCartFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneCayanTransportFacade")
	private SiteOneCayanTransportFacade siteOneCayanTransportFacade;

	@Resource(name = "siteOnePaymentInfoUtil")
	private SiteOnePaymentInfoUtil siteOnePaymentInfoUtil;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "siteOneEwalletDataUtil")
	private SiteOneEwalletDataUtil siteOneEwalletDataUtil;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "siteOneEwalletCreditCardConverter")
	private Converter<SiteoneEwalletCreditCardModel, SiteOneEwalletData> siteOneEwalletCreditCardConverter;

	@Resource(name = "storeSessionFacade")
	protected StoreSessionFacade storeSessionFacade;

	@Resource(name = "siteoneLinkToPayAuditLogService")
	private SiteoneLinkToPayAuditLogService siteoneLinkToPayAuditLogService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "timeService")
	private TimeService timeService;

	public List<SelectOptionforPayment> getSiteOnePaymentMethod(final B2BUnitModel b2bUnit, final Model model)
	{
		final List<SelectOptionforPayment> siteOnePaymentMethod2 = new ArrayList<>();
		final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		if (isPOAEnabled(b2bUnit))
		{
			siteOnePaymentMethod2.add(new SelectOptionforPayment(SiteOnePaymentMethodEnum.SITEONE_ONLINE_ACCOUNT.getCode(),
					getMessageSource().getMessage("checkout.multi.option.account", null, getI18nService().getCurrentLocale()),
					getMessageSource().getMessage("checkout.multi.option.description.account", null,
							getI18nService().getCurrentLocale())));
			model.addAttribute(CHECKED, SiteOnePaymentMethodEnum.SITEONE_ONLINE_ACCOUNT.getCode());
		}
		if (getSessionService().getAttribute("showCCOption").equals(Boolean.TRUE))
		{
			siteOnePaymentMethod2.add(new SelectOptionforPayment(SiteOnePaymentMethodEnum.PAY_ONLINE_WITH_CREDIT_CARD.getCode(),
					getMessageSource().getMessage("checkout.multi.option.branch", null, getI18nService().getCurrentLocale()),
					getMessageSource().getMessage("checkout.multi.option.description", null, getI18nService().getCurrentLocale())));
			if (!model.containsAttribute(CHECKED))
			{
				model.addAttribute(CHECKED, SiteOnePaymentMethodEnum.PAY_ONLINE_WITH_CREDIT_CARD.getCode());
			}
		}

		siteOnePaymentMethod2.add(new SelectOptionforPayment(SiteOnePaymentMethodEnum.PAY_AT_BRANCH.getCode(),
				getMessageSource().getMessage("checkout.multi.option.credit", null, getI18nService().getCurrentLocale()),
				getMessageSource().getMessage("checkout.multi.option.description.card", null, getI18nService().getCurrentLocale())));
		if (!model.containsAttribute(CHECKED))
		{
			model.addAttribute(CHECKED, SiteOnePaymentMethodEnum.PAY_AT_BRANCH.getCode());
		}

		return siteOnePaymentMethod2;
	}

	@ModelAttribute("billingCountries")
	public Collection<CountryData> getBillingCountries()
	{
		return getCheckoutFacade().getBillingCountries();
	}

	@ModelAttribute("cardTypes")
	public Collection<CardTypeData> getCardTypes()
	{
		return getCheckoutFacade().getSupportedCardTypes();
	}

	@ModelAttribute("months")
	public List<SelectOption> getMonths()
	{
		final List<SelectOption> months = new ArrayList<>();

		months.add(new SelectOption("1", "01"));
		months.add(new SelectOption("2", "02"));
		months.add(new SelectOption("3", "03"));
		months.add(new SelectOption("4", "04"));
		months.add(new SelectOption("5", "05"));
		months.add(new SelectOption("6", "06"));
		months.add(new SelectOption("7", "07"));
		months.add(new SelectOption("8", "08"));
		months.add(new SelectOption("9", "09"));
		months.add(new SelectOption("10", "10"));
		months.add(new SelectOption("11", "11"));
		months.add(new SelectOption("12", "12"));

		return months;
	}

	@ModelAttribute("startYears")
	public List<SelectOption> getStartYears()
	{
		final List<SelectOption> startYears = new ArrayList<>();
		final Calendar calender = new GregorianCalendar();

		for (int i = calender.get(Calendar.YEAR); i > calender.get(Calendar.YEAR) - 6; i--)
		{
			startYears.add(new SelectOption(String.valueOf(i), String.valueOf(i)));
		}

		return startYears;
	}

	@ModelAttribute("expiryYears")
	public List<SelectOption> getExpiryYears()
	{
		final List<SelectOption> expiryYears = new ArrayList<>();
		final Calendar calender = new GregorianCalendar();

		for (int i = calender.get(Calendar.YEAR); i < calender.get(Calendar.YEAR) + 11; i++)
		{
			expiryYears.add(new SelectOption(String.valueOf(i), String.valueOf(i)));
		}

		return expiryYears;
	}

	@Override
	@GetMapping("/add")
	@RequireHardLogIn

	public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{

		final String vaultToken = getSessionService().getAttribute("vaultToken");
		final Boolean redirectToOrderSummary = null != getSessionService().getAttribute("redirectToOrderSummary")
				? getSessionService().getAttribute("redirectToOrderSummary")
				: Boolean.FALSE;
		if (redirectToOrderSummary.equals(Boolean.TRUE) && !StringUtils.isEmpty(vaultToken))
		{
			getSessionService().removeAttribute("redirectToOrderSummary");
			return REDIRECT_TO_ORDER_REVIEW_PAGE;
		}
		else
		{
			getSessionService().removeAttribute("vaultToken");
			getSessionService().removeAttribute("ewalletData");
		}

		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();

		final String redirectUrl = validateCheckoutCart(cartData, redirectAttributes);

		if (null != redirectUrl)
		{
			return redirectUrl;
		}
		model.addAttribute("totalPrice", cartData.getTotalPriceWithTax());

		final String webApi = configurationService.getConfiguration().getString(CAYAN_PAYMENT_WEB_API);
		model.addAttribute("WEB_API", webApi);
		b2bCheckoutFacade.setDeliveryAddressIfAvailable();

		setupAddPaymentPage(model);

		final SiteOnePaymentForm siteOnePaymentForm = new SiteOnePaymentForm();
		// Use the checkout PCI strategy for getting the URL for creating new subscriptions.
		final CheckoutPciOptionEnum subscriptionPciOption = getCheckoutFlowFacade().getSubscriptionPciOption();
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		model.addAttribute("ewalletAddError", getSessionService().getAttribute("ewalletAddError"));
		getSessionService().removeAttribute("ewalletAddError");
		getSessionService().removeAttribute("saveCard");
		getSessionService().removeAttribute("nickName");
		final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (CheckoutPciOptionEnum.SOP.equals(subscriptionPciOption))
		{
			// Build up the SOP form data and render page containing form
			try
			{
				siteOneEwalletFacade.populatePaymentconfig(SITEONE_PAYMENT, model);
				model.addAttribute("siteOnePaymentMethod", getSiteOnePaymentMethod(b2bUnit, model));
				getSessionService().removeAttribute("showCCOption");
				model.addAttribute(CART_DATA_ATTR, cartData);
				if (null != getSessionService().getAttribute(AssistedservicefacadesConstants.ASM_SESSION_PARAMETER))
				{
					model.addAttribute("asm", Boolean.TRUE);
				}
				else
				{
					model.addAttribute("asm", Boolean.FALSE);
				}

				final CustomerData customerData = ((DefaultSiteOneCustomerFacade) customerFacade).getCurrentCustomer();
				model.addAttribute("eWallet",
						((DefaultSiteOneEwalletFacadeImpl) siteOneEwalletFacade).filterValidEwalletData(customerData));
				setupSilentOrderPostPage(siteOnePaymentForm, model);
				if (StringUtils.isNotEmpty(b2bUnit.getCreditCode()))
				{
					model.addAttribute("creditCode", b2bUnit.getCreditCode());
				}
				return ControllerConstants.Views.Pages.MultiStepCheckout.SiteOnePaymentMethodPage;
			}
			catch (final Exception e)
			{
				LOGGER.error("Failed to build beginCreateSubscription request", e);
				GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.addPaymentDetails.generalError");
				model.addAttribute("siteonePaymentForm", siteOnePaymentForm);
			}
		}
		model.addAttribute(CART_DATA_ATTR, cartData);
		setupSilentOrderPostPage(siteOnePaymentForm, model);
		return ControllerConstants.Views.Pages.MultiStepCheckout.SiteOnePaymentMethodPage;


	}

	@GetMapping("/back")
	@RequireHardLogIn
	@Override
	public String back(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().previousStep();
	}

	@GetMapping("/next")
	@RequireHardLogIn
	@Override
	public String next(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().nextStep();
	}

	protected CardTypeData createCardTypeData(final String code, final String name)
	{
		final CardTypeData cardTypeData = new CardTypeData();
		cardTypeData.setCode(code);
		cardTypeData.setName(name);
		return cardTypeData;
	}

	protected void setupAddPaymentPage(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("metaRobots", "noindex,nofollow");
		model.addAttribute("hasNoPaymentInfo", Boolean.valueOf(getCheckoutFlowFacade().hasNoPaymentInfo()));
		prepareDataForPage(model);
		final ContentPageModel contentPage = getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL);
		storeCmsPageInModel(model, contentPage);
		setUpMetaDataForContentPage(model, contentPage);
		setCheckoutStepLinksForModel(model, getCheckoutStep());
	}

	protected void setupSilentOrderPostPage(final SiteOnePaymentForm siteonePaymentForm, final Model model)
	{


		final CartData cartData = getCheckoutFacade().getCheckoutCart();
		model.addAttribute("silentOrderPostForm", new PaymentDetailsForm());
		model.addAttribute("siteOneCardTypes", getSiteOneCardTypes());
		model.addAttribute(CART_DATA_ATTR, cartData);
		model.addAttribute("deliveryAddress", cartData.getDeliveryAddress());
		model.addAttribute("siteonePaymentForm", siteonePaymentForm);
		model.addAttribute("paymentInfos", getUserFacade().getCCPaymentInfos(true));

	}

	protected Collection<CardTypeData> getSiteOneCardTypes()
	{
		final Collection<CardTypeData> sopCardTypes = new ArrayList<>();

		final List<CardTypeData> supportedCardTypes = getCheckoutFacade().getSupportedCardTypes();
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

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(SITEONE_PAYMENT);
	}

	static
	{
		SITEONE_CARD_TYPES.put("visa", "001");
		SITEONE_CARD_TYPES.put("master", "002");
		SITEONE_CARD_TYPES.put("amex", "003");
		SITEONE_CARD_TYPES.put("diners", "005");
		SITEONE_CARD_TYPES.put("maestro", "024");
	}

	protected String validateCheckoutCart(final CartData checkoutCart, final RedirectAttributes redirectAttributes)
	{
		LOGGER.info(checkoutCart.getContactPerson());

		if (!b2bCheckoutFacade.isCartValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"cart.checkout.disable.message");
			LOGGER.info("User is not able to checkout beacause of recently changed store");
			return REDIRECT_TO_CART_PAGE;
		}

		if (!b2bCheckoutFacade.isPosValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER, "checkout.multi.pos.invalid");
			LOGGER.info("User is not able to checkout beacause of recently changed Store");
			return REDIRECT_TO_CART_PAGE;
		}

		if (!b2bCheckoutFacade.isAccountValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER, "checkout.error.invalid.shipto");
			LOGGER.info("User is not able to checkout beacause of recently changed Account");
			return REDIRECT_TO_CART_PAGE;
		}

		if (checkoutCart.getOrderType() == null)
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
					"checkout.multi.orderType.notprovided");
			return REDIRECT_TO_ORDERTYPE_PAGE;
		}

		if (checkoutCart.getContactPerson() == null)
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
					"checkout.multi.contactPerson.notprovided");
			return REDIRECT_TO_ORDERTYPE_PAGE;
		}

		if (checkoutCart.getDeliveryAddress() == null)
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
					"checkout.multi.deliveryAddress.notprovided");
			return REDIRECT_TO_ORDERTYPE_PAGE;
		}

		if (checkoutCart.getRequestedDate() == null)
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
					"checkout.multi.requestedDate.notprovided");
			return REDIRECT_TO_ORDERTYPE_PAGE;
		}

		return null;
	}

	@GetMapping("/getAuthorizeTransportKey")
	public @ResponseBody String getAuthorizeTransportKey(final Model model)
	{
		String transportKey = null;

		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();

		transportKey = siteOneCayanTransportFacade.getAuthorizeTransportKey(cartData);

		return transportKey;
	}

	@PostMapping("/getBoardCardTransportKey")
	public @ResponseBody String getBoardCardTransportKey(@RequestParam(name = "isCheckout")
	final boolean isCheckout)
	{
		String transportKey = null;
		try
		{
			final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();

			if (getUserFacade().isAnonymousUser())
			{
				if (null != cartData && null != cartData.getB2bCustomerData()
						&& null != cartData.getB2bCustomerData().getDisplayUid())
				{
					final String originalID = b2bCheckoutFacade.getOriginalEmail(cartData.getB2bCustomerData().getDisplayUid());
					final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
							.getSiteoneCCAuditDetails(originalID);
					if (null != auditModel)
					{
						final int declineCountLimit = Integer
								.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
						final int declineCount = auditModel.getDeclineCount().intValue();
						LOGGER.error("declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);
						if (declineCount < declineCountLimit)
						{
							transportKey = siteOneCayanTransportFacade.getBoardCardTransportKey(isCheckout);
							return transportKey;
						}
						else
						{
							LOGGER.error("Cayan response is declined");
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
									siteoneLinkToPayAuditLogService.resetSiteoneCCAuditLog(originalID);
									transportKey = siteOneCayanTransportFacade.getBoardCardTransportKey(isCheckout);
									return transportKey;
								}
								else
								{
									return DECLINE_LIMIT_OVER;
								}
							}
							catch (final Exception e)
							{
								LOGGER.error("Failed to add Credit Card details", e);
								transportKey = siteOneCayanTransportFacade.getBoardCardTransportKey(isCheckout);
								return transportKey;
							}
						}
					}
					else
					{
						transportKey = siteOneCayanTransportFacade.getBoardCardTransportKey(isCheckout);
						return transportKey;
					}
				}
			}
			else
			{
				transportKey = siteOneCayanTransportFacade.getBoardCardTransportKey(isCheckout);
				return transportKey;
			}
			return transportKey;
		}
		catch (final Exception ex)
		{
			LOGGER.error("The exception occured on transport key is ", ex);
			throw new RuntimeException("Exception occured while calling through method getBoardCardTransportKey ");
		}
	}

	@RequestMapping(value = "/add-ewalletStatus=User_Cancelled")
	@RequireHardLogIn
	public String cancelPayment() throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		return REDIRECT_PREFIX + REDIRECT_TO_PAYMENT_PAGE;
	}

	@GetMapping("/add-globalpayment")
	public @ResponseBody String addGlobalPayment(final String token, final String address, final String postalCode,
			final String cardType, final String cardNumber)
	{

		if (null != token)
		{

			getSessionService().setAttribute("redirectToOrderSummary", Boolean.TRUE);
			getSessionService().setAttribute("globalTemporaryToken", token);
			getSessionService().setAttribute("globalPaymentAddress", address);
			getSessionService().setAttribute("globalPostalCode", postalCode);
			getSessionService().setAttribute("globalCardType", cardType);
			getSessionService().setAttribute("globalCardNumber", cardNumber);
			return "Success";
		}
		else
		{
			return "Failure";
		}

	}


	@GetMapping("/add-ewallet")
	@RequireHardLogIn
	public String addEwallet(final CayanBoarcardResponseForm boardCardRespForm) throws CMSItemNotFoundException
	{

		if (null != boardCardRespForm
				&& boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_APPROVED))
		{

			final String nickName = getSessionService().getAttribute(SiteoneFacadesConstants.NICK_NAME);
			final String saveCard = getSessionService().getAttribute(SiteoneFacadesConstants.SAVECARD);
			final SiteOneEwalletData ewalletData = siteOneEwalletDataUtil.convert(boardCardRespForm, nickName);
			try
			{
				if (StringUtils.isNotEmpty(saveCard) && Boolean.valueOf(saveCard))
				{
					siteOneEwalletFacade.addEwalletDetails(ewalletData);
					getSessionService().setAttribute("ewalletData", ewalletData);
				}
				else
				{

					getSessionService().setAttribute("ewalletData", ewalletData);
				}
				getSessionService().setAttribute("vaultToken", boardCardRespForm.getVaultToken());
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOGGER.error("Not able to establish connection with UE to create contact", resourceAccessException);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
			catch (final CardAlreadyPresentInUEException cardAlreadyPresentInUEException)
			{
				LOGGER.error("Card already present for customer in UE", cardAlreadyPresentInUEException);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.Card.exist", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
			catch (final NickNameAlreadyPresentInUEException nickNameAlreadyPresentInUEException)
			{
				LOGGER.error("Nick Name already present for customer in UE", nickNameAlreadyPresentInUEException);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.Name.exist", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
			catch (final Exception e)
			{
				LOGGER.error("Failed to add Credit Card details", e);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
		}
		else if (null != boardCardRespForm
				&& boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_CANCELLED))
		{
			return REDIRECT_PREFIX + REDIRECT_TO_PAYMENT_PAGE;
		}
		else
		{
			LOGGER.error("Invalid Boardcard response : " + boardCardRespForm.getStatus());
			if (boardCardRespForm.getErrorMessage() == null)
			{
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
			}
			else
			{
				getSessionService().setAttribute("ewalletAddError", boardCardRespForm.getErrorMessage());
			}
			return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
		}
		getSessionService().setAttribute("redirectToOrderSummary", Boolean.TRUE);
		return REDIRECT_TO_ORDER_REVIEW_PAGE;

	}


	public List<SiteOneEwalletData> getEWallet(final CustomerData customer)
	{
		final String userUnitId = customer.getUnit().getUid();
		return siteOneEwalletFacade.getEWalletDataForCheckout(userUnitId);

	}

	@PostMapping("/setKountSessionId")
	public @ResponseBody void setKountSessionId(@RequestParam(name = "kountSessionId")
	final String kountSessionId, final HttpServletRequest request)
	{
		try
		{
			getSessionService().setAttribute("kountSessionId", kountSessionId);
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
		}
		catch (final Exception ex)
		{
			LOGGER.error(ex);
		}

	}




	@RequestMapping(value = "/placeOrderWithPOA")
	@RequireHardLogIn
	public @ResponseBody String placeOrderWithPOA(final Model model)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

		if (StringUtils.isNotEmpty(b2bUnit.getCreditTermCode()))
		{
			getSessionService().setAttribute("termsCode", b2bUnit.getCreditTermCode());
		}
		model.addAttribute("isReturning", "returning");
		return REDIRECT_TO_ORDER_REVIEW_SCREEN;
	}

	/* Set saveCard,nickName in session storage */
	@PostMapping("/setSaveCardInSession")
	public @ResponseBody String setSaveCardInSession(@RequestParam(name = "saveCard")
	final String saveCard, @RequestParam(name = "nickName")
	final String nickName)
	{
		try
		{
			getSessionService().setAttribute("saveCard", saveCard);
			getSessionService().setAttribute("nickName", nickName);
		}
		catch (final Exception ex)
		{
			return "Failure";
		}
		return "Success";
	}

	/**
	 * Data class used to hold a drop down select option value. Holds the code identifier as well as the display name.
	 */
	public static class SelectOptionforPayment
	{
		private final String code;
		private final String name;
		private final String description;

		public SelectOptionforPayment(final String code, final String name, final String description)
		{
			this.code = code;
			this.name = name;
			this.description = description;
		}

		public String getCode()
		{
			return code;
		}

		public String getName()
		{
			return name;
		}

		public String getDescription()
		{
			return description;
		}
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

	@GetMapping("/getAccountInformation")
	@RequireHardLogIn
	public @ResponseBody SiteOneCustInfoData getAccountInformation(final Model model)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{

		SiteOneCustInfoData custInfoData = null;
		final B2BUnitData sessionShipTo = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionShipTo();
		if (sessionShipTo != null)
		{
			custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(sessionShipTo.getUid());
		}
		else if (sessionShipTo == null || !(custInfoData != null && custInfoData.getCustomerCreditInfo() != null
				&& custInfoData.getCustomerCreditInfo().getCreditTermDescription() != null
				&& !custInfoData.getCustomerCreditInfo().getCreditTermDescription().trim().isEmpty()))
		{

			final B2BUnitModel defaultB2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			final String defaultUnitId = defaultB2bUnit.getUid();

			custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(defaultUnitId);
		}
		model.addAttribute("custInfoData", custInfoData);

		return custInfoData;
	}

	@GetMapping("/payment")
	public @ResponseBody SiteOnePaymentUserData getPaymentDetails(final Model model) throws CMSItemNotFoundException
	{
		getSessionService().removeAttribute("vaultToken");
		getSessionService().removeAttribute("ewalletData");
		getSessionService().removeAttribute("termsCode");
		getSessionService().removeAttribute("nickName");
		getSessionService().removeAttribute("saveCard");
		getSessionService().removeAttribute("globalTemporaryToken");


		final SiteOnePaymentUserData siteOnePaymentUserData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getPaymentOptions();
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();
		siteOnePaymentUserData.setCheckoutValidationError(validateCheckoutCartData(cartData));

		if (null != cartData.getCode())
		{
			final SiteoneCCPaymentAuditLogModel auditModel = ((SiteOneCustomerFacade) customerFacade)
					.getSiteoneCCAuditDetails(cartData.getCode());
			if (null != auditModel)
			{
				final int declineCountLimit = Integer
						.parseInt(siteOneFeatureSwitchCacheService.getValueForSwitch("PaymentDeclineCount"));
				if (null != auditModel.getDeclineCount() && auditModel.getDeclineCount().intValue() > 0)
				{
					final int declineCount = (auditModel.getDeclineCount().intValue()) / 2;
					LOGGER.error("declineCountLimit :" + declineCountLimit + "declineCount : " + declineCount);

					if (declineCount < declineCountLimit)
					{
						siteOnePaymentUserData.setIsCreditPaymentBlocked(Boolean.FALSE);
					}
					else
					{
						LOGGER.error("Limit crossed for credit card attempts ");

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

								siteoneLinkToPayAuditLogService.resetSiteoneCCAuditLog(cartData.getCode());
								siteOnePaymentUserData.setIsCreditPaymentBlocked(Boolean.FALSE);
							}
							else
							{
								siteOnePaymentUserData.setIsCreditPaymentBlocked(Boolean.TRUE);
							}
						}
						catch (final Exception e)
						{
							LOGGER.error("Failed to add Credit Card details", e);
							siteOnePaymentUserData.setIsCreditPaymentBlocked(Boolean.TRUE);
						}
					}
				}
			}
			else
			{
				siteOnePaymentUserData.setIsCreditPaymentBlocked(Boolean.FALSE);
			}
		}
		else
		{
			siteOnePaymentUserData.setIsCreditPaymentBlocked(Boolean.FALSE);
		}
		return siteOnePaymentUserData;
	}


	@GetMapping("/add-ewalletDataStatus=User_Cancelled")
	public @ResponseBody SiteOneAddEwalletData cancelUserPayment()
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		getSessionService().removeAttribute("nickName");
		getSessionService().removeAttribute("saveCard");
		final SiteOneAddEwalletData siteOneAddEwalletData = new SiteOneAddEwalletData();
		siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
		siteOneAddEwalletData.setErrorMessage(
				getMessageSource().getMessage("text.ewallet.user.cancel", null, getI18nService().getCurrentLocale()));
		return siteOneAddEwalletData;
	}

	@GetMapping("/add-ewalletData")
	public @ResponseBody SiteOneAddEwalletData addEwalletData(final CayanBoarcardResponseForm boardCardRespForm)
			throws CMSItemNotFoundException
	{
		final SiteOneAddEwalletData siteOneAddEwalletData = new SiteOneAddEwalletData();
		final CartData cartData = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).getCheckoutCart();

		if (null != boardCardRespForm
				&& boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_APPROVED))
		{

			final String nickName = getSessionService().getAttribute(SiteoneFacadesConstants.NICK_NAME);
			final String saveCard = getSessionService().getAttribute(SiteoneFacadesConstants.SAVECARD);
			final SiteOneEwalletData ewalletData = siteOneEwalletDataUtil.convert(boardCardRespForm, nickName);
			try
			{
				if (null != getSessionService().getAttribute("vaultToken")
						&& getSessionService().getAttribute("vaultToken").equals(boardCardRespForm.getVaultToken()))
				{
					siteOneAddEwalletData.setIsSuccessfull(Boolean.TRUE);
					siteOneAddEwalletData.setIsRedirectToOrderSummary(Boolean.TRUE);
					return siteOneAddEwalletData;
				}
				if (StringUtils.isNotEmpty(saveCard) && Boolean.valueOf(saveCard))
				{
					siteOneEwalletFacade.addEwalletDetails(ewalletData);
					getSessionService().setAttribute("isEwalletCard", Boolean.TRUE);
					siteOneAddEwalletData.setEwalletData(ewalletData);
					getSessionService().setAttribute("ewalletData", ewalletData);
				}
				else
				{

					siteOneAddEwalletData.setEwalletData(ewalletData);
					getSessionService().setAttribute("ewalletData", ewalletData);
				}
				siteOneAddEwalletData.setVaultToken(boardCardRespForm.getVaultToken());
				getSessionService().setAttribute("vaultToken", boardCardRespForm.getVaultToken());
				siteOneAddEwalletData.setIsSuccessfull(Boolean.TRUE);
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				getSessionService().removeAttribute("nickName");
				getSessionService().removeAttribute("saveCard");
				LOGGER.error("Not able to establish connection with UE to create contact", resourceAccessException);

				siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
				siteOneAddEwalletData.setErrorMessage(
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return siteOneAddEwalletData;
			}
			catch (final CardAlreadyPresentInUEException cardAlreadyPresentInUEException)
			{
				getSessionService().removeAttribute("nickName");
				getSessionService().removeAttribute("saveCard");
				LOGGER.error("Card already present for customer in UE", cardAlreadyPresentInUEException);
				siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
				siteOneAddEwalletData.setErrorMessage(
						getMessageSource().getMessage("text.ewallet.add.Card.exist", null, getI18nService().getCurrentLocale()));
				return siteOneAddEwalletData;
			}
			catch (final NickNameAlreadyPresentInUEException nickNameAlreadyPresentInUEException)
			{
				getSessionService().removeAttribute("nickName");
				getSessionService().removeAttribute("saveCard");
				LOGGER.error("Nick Name already present for customer in UE", nickNameAlreadyPresentInUEException);

				siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
				siteOneAddEwalletData.setErrorMessage(
						getMessageSource().getMessage("text.ewallet.add.Name.exist", null, getI18nService().getCurrentLocale()));
				return siteOneAddEwalletData;
			}
			catch (final Exception e)
			{
				getSessionService().removeAttribute("nickName");
				getSessionService().removeAttribute("saveCard");
				LOGGER.error("Failed to add Credit Card details", e);

				siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
				siteOneAddEwalletData.setErrorMessage(
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return siteOneAddEwalletData;
			}
		}
		else if (null != boardCardRespForm
				&& boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_CANCELLED))
		{
			siteOneAddEwalletData.setIsUserCancelled(Boolean.TRUE);
			siteOneAddEwalletData.setErrorMessage(
					getMessageSource().getMessage("text.ewallet.user.cancel", null, getI18nService().getCurrentLocale()));

			return siteOneAddEwalletData;
		}
		else if (null != boardCardRespForm && boardCardRespForm.getStatus().equalsIgnoreCase("FAILED")
				|| boardCardRespForm.getStatus().equalsIgnoreCase("DECLINED"))
		{
			if (null != cartData)
			{
				String cardNum =String.valueOf(boardCardRespForm.getCardNumber().substring(boardCardRespForm.getCardNumber().length() - 4));
				String zipCode = boardCardRespForm.getZipCode();
				
				b2bCheckoutFacade.saveSiteoneCCAuditLog(cartData, cardNum,zipCode);
				getSessionService().removeAttribute("nickName");
				getSessionService().removeAttribute("saveCard");
				LOGGER.error("Invalid Boardcard response : " + boardCardRespForm.getStatus());

				if (!getUserFacade().isAnonymousUser())
				{
					boolean isCardBlockFlag = b2bCheckoutFacade.getCardBlockFlag(cartData);

					if (isCardBlockFlag)
					{
						siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
						siteOneAddEwalletData.setIsRedirectToOrderSummary(Boolean.FALSE);
						if (null != cartData.getOrderType())
						{
							siteOneAddEwalletData.setErrorMessage(cartData.getOrderType());
						}
						return siteOneAddEwalletData;
					}
					else
					{
						if (boardCardRespForm.getErrorMessage() == null)
						{
							siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
							siteOneAddEwalletData.setErrorMessage(
									getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
							return siteOneAddEwalletData;
						}
						else
						{
							siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
							siteOneAddEwalletData.setErrorMessage(boardCardRespForm.getErrorMessage());
							return siteOneAddEwalletData;
						}
					}
				}
				else
				{

					if (boardCardRespForm.getErrorMessage() == null)
					{
						siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
						siteOneAddEwalletData.setErrorMessage(
								getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
						return siteOneAddEwalletData;
					}
					else
					{
						siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
						siteOneAddEwalletData.setErrorMessage(boardCardRespForm.getErrorMessage());
						return siteOneAddEwalletData;
					}
				}
			}
		}
		else
		{
			getSessionService().removeAttribute("nickName");
			getSessionService().removeAttribute("saveCard");
			LOGGER.error("Invalid Boardcard response : " + boardCardRespForm.getStatus());
			if (boardCardRespForm.getErrorMessage() == null)
			{
				siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
				siteOneAddEwalletData.setErrorMessage(
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return siteOneAddEwalletData;
			}
			else
			{
				siteOneAddEwalletData.setIsSuccessfull(Boolean.FALSE);
				siteOneAddEwalletData.setErrorMessage(boardCardRespForm.getErrorMessage());
				return siteOneAddEwalletData;
			}
		}
		siteOneAddEwalletData.setIsRedirectToOrderSummary(Boolean.TRUE);
		return siteOneAddEwalletData;
	}

	protected String validateCheckoutCartData(final CartData checkoutCart)
	{
		String message = null;
		if (!b2bCheckoutFacade.isCartValidForCheckout(checkoutCart))
		{
			message = getMessageSource().getMessage("cart.checkout.disable.message", null, getI18nService().getCurrentLocale());

		}

		else if (!b2bCheckoutFacade.isPosValidForCheckout(checkoutCart))
		{
			message = getMessageSource().getMessage("checkout.multi.pos.invalid", null, getI18nService().getCurrentLocale());
		}

		/*
		 * if (!siteOneCheckoutUtils.isAccountValidForCheckout(checkoutCart)) {
		 * GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
		 * "checkout.error.invalid.shipto");
		 * LOG.info("User is not able to checkout beacause of recently changed Account");
		 *
		 * }
		 */

		else if (checkoutCart.getOrderType() == null)
		{
			message = getMessageSource().getMessage("checkout.multi.orderType.notprovided", null,
					getI18nService().getCurrentLocale());
		}

		else if (checkoutCart.getContactPerson() == null)
		{
			message = getMessageSource().getMessage("checkout.multi.contactPerson.notprovided", null,
					getI18nService().getCurrentLocale());
		}

		else if (checkoutCart.getDeliveryAddress() == null)
		{
			message = getMessageSource().getMessage("checkout.multi.deliveryAddress.notprovided", null,
					getI18nService().getCurrentLocale());
		}

		else if (checkoutCart.getRequestedDate() == null)
		{
			message = getMessageSource().getMessage("checkout.multi.requestedDate.notprovided", null,
					getI18nService().getCurrentLocale());
		}

		return message;
	}



	@RequestMapping(value = "/placeCustomerOrderWithPOA")
	@RequireHardLogIn
	public @ResponseBody String placeCustomerOrderWithPOA(final Model model)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		try
		{
			final B2BUnitModel b2bUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

			if (StringUtils.isNotEmpty(b2bUnit.getCreditTermCode()))
			{
				getSessionService().setAttribute("termsCode", b2bUnit.getCreditTermCode());
			}
		}
		catch (final Exception ex)
		{
			return "Failure";
		}
		return "Success";
	}

	@RequestMapping(value = "/placeCustomerOrderWithEWallet")
	@RequireHardLogIn
	public @ResponseBody String placeCustomerOrderWithEWallet(final Model model, final String vaultToken)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		final String result = ((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).placeEwalletOrder(vaultToken);
		return result;
	}


}
