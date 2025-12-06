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
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.ordergridform.OrderGridFormFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdatePasswordForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateProfileForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.AddressValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.EmailValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.PasswordValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ProfileValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.verification.AddressVerificationResultHandler;
import de.hybris.platform.acceleratorstorefrontcommons.util.AddressDataUtil;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.address.AddressVerificationFacade;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.util.ResponsiveUtils;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.user.exceptions.PasswordPolicyViolationException;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.oauth2.token.dao.OAuthTokenDao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.granule.json.JSONObject;
import com.sap.security.core.server.csi.XSSEncoder;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.exceptions.EwalletNotFoundException;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.facade.InvoiceData;
import com.siteone.facade.customer.info.MyAccountUserInfo;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.invoice.InvoiceFacade;
import com.siteone.facade.order.SiteOneOrderFacade;
import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;
import com.siteone.facade.payment.cayan.SiteOneCayanTransportFacade;
import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.ewallet.SiteOneEwalletFacade;
import com.siteone.facades.exceptions.AddressNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.AddressNotRemovedInUEException;
import com.siteone.facades.exceptions.CardAlreadyPresentInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInCayanException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.NickNameAlreadyPresentInUEException;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.RecentlyUsedPasswordException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.CustomFields;
import com.siteone.integration.customer.data.UserEmailData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import com.siteone.storefront.breadcrumbs.impl.SiteOneAccountBreadcrumbBuilder;
import com.siteone.storefront.breadcrumbs.impl.SiteOneChangePwdBreadcrumbBuilder;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.CayanBoarcardResponseForm;
import com.siteone.storefront.forms.SiteOneAddressForm;
import com.siteone.storefront.forms.SiteOneRequestAccountForm;
import com.siteone.storefront.forms.SiteOneUpdateProfileForm;
import com.siteone.storefront.forms.SiteoneEwalletForm;
import com.siteone.storefront.title.SiteOnePageTitleResolver;
import com.siteone.storefront.util.SiteOneAddressDataUtil;
import com.siteone.storefront.util.SiteOneEwalletDataUtil;
import com.siteone.storefront.util.SiteOneInvoicePDFUtils;
import com.siteone.storefront.util.SiteoneXSSFilterUtil;
import com.siteone.storefront.validator.SiteOneAddressValidator;
import com.siteone.storefront.validator.SiteOneChangePwdValidator;
import com.siteone.storefront.validator.SiteOneRequestAccountValidator;


@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping("/**/my-account")
public class AccountPageController extends AbstractSearchPageController
{
	private static final String TEXT_ACCOUNT_ADDRESS_BOOK = "text.account.addressBook";
	private static final String TEXT_ACCOUNT_DASHBOARD = "text.account.addressBook.dashboard";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	private static final String IS_DEFAULT_ADDRESS_ATTR = "isDefaultAddress";
	private static final String COUNTRY_DATA_ATTR = "countryData";
	private static final String ADDRESS_BOOK_EMPTY_ATTR = "addressBookEmpty";
	private static final String TITLE_DATA_ATTR = "titleData";
	private static final String FORM_GLOBAL_ERROR = "form.global.error";
	private static final String PROFILE_CURRENT_PASSWORD_INVALID = "profile.currentPassword.invalid";
	private static final String TEXT_ACCOUNT_PROFILE = "text.account.profile";
	private static final String ADDRESS_DATA_ATTR = "addressData";
	private static final String POINT_OF_SERVICE_DATA_ATTR = "pointOfServiceData";
	private static final String ADDRESS_FORM_ATTR = "siteOneAddressForm";
	private static final String COUNTRY_ATTR = "country";
	private static final String REGIONS_ATTR = "regions";
	private static final String MY_ACCOUNT_ADDRESS_BOOK_URL = "/my-account/address-book";
	private static final String MY_ACCOUNT_DASHBOARD_URL = "/my-account/account-dashboard";
	private static final String MY_ACCOUNT_SHIPTO_URL = "/my-account/ship-to/";
	private static final String STORE_ID_PATH_VARIABLE_PATTERN = "{storeId:.*}";
	// Internal Redirects
	private static final String REDIRECT_TO_ADDRESS_BOOK_PAGE = REDIRECT_PREFIX + MY_ACCOUNT_ADDRESS_BOOK_URL;
	private static final String REDIRECT_TO_PAYMENT_INFO_PAGE = REDIRECT_PREFIX + "/my-account/payment-details";
	private static final String REDIRECT_TO_UPDATE_EMAIL_PAGE = REDIRECT_PREFIX + "/my-account/update-email";
	private static final String REDIRECT_TO_UPDATE_PROFILE = REDIRECT_PREFIX + "/my-account/update-profile";
	private static final String REDIRECT_TO_UPDATE_LANGUAGE = REDIRECT_PREFIX + "/my-account/update-siteoneprofile/";
	private static final String REDIRECT_TO_PASSWORD_UPDATE_PAGE = REDIRECT_PREFIX + "/my-account/update-password";
	private static final String REDIRECT_TO_ORDER_HISTORY_PAGE = REDIRECT_PREFIX + "/my-account/orders";
	private static final String REDIRECT_TO_MYSTORES_PAGE = REDIRECT_PREFIX + "/my-account/my-stores";
	private static final String REDIRECT_TO_MAIN_VIEW = REDIRECT_PREFIX + "/my-account/account-dashboard";
	private static final String REDIRECT_TO_SHIPTO_VIEW = REDIRECT_PREFIX + "/my-account/ship-to/";
	private static final String REDIRECT_TO_EWALLET_PAGE = REDIRECT_PREFIX + "/my-account/ewallet";

	private static final String GEO_LOCATED_STORE_COOKIE = "gls";
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";
	private static final String ADDRESS_CODE_PATH_VARIABLE_PATTERN = "{addressCode:.*}";
	private static final String UNIT_ID_PATH_VARIABLE_PATTERN = "{unitId:.*}";


	// CMS Pages
	private static final String ACCOUNT_CMS_PAGE = "account";
	private static final String PROFILE_CMS_PAGE = "profile";
	private static final String UPDATE_PASSWORD_CMS_PAGE = "updatePassword";
	private static final String UPDATE_PROFILE_CMS_PAGE = "update-profile";
	private static final String UPDATE_EMAIL_CMS_PAGE = "update-email";
	private static final String ADDRESS_BOOK_CMS_PAGE = "address-book";
	private static final String MY_STORES_CMS_PAGE = "my-stores";
	private static final String ADD_EDIT_ADDRESS_CMS_PAGE = "add-edit-address";
	private static final String PAYMENT_DETAILS_CMS_PAGE = "payment-details";
	private static final String ORDER_HISTORY_CMS_PAGE = "orders";
	private static final String ORDER_DETAIL_CMS_PAGE = "order";
	private static final String OPEN_ORDER_CMS_PAGE = "openorderspage";
	private static final String ACCOUNT_ORDERS_CMS_PAGE = "accountOrdersPage";
	private static final String ORDERS_HISTORY_CMS_PAGE = "accountOrdersHistoryPage";
	private static final String ACCOUNT_PARTNER_PROGRAM_PAGE = "accountPartnerProgramPage";
	private static final String CACHE_CONTROL = "Cache-Control";
	private static final String REVALIDATE = "must-revalidate, post-check=0, pre-check=0";
	private static final String PRAGMA = "Pragma";
	private static final String PUBLIC = "public";
	private static final String DESCENDING_ORDER = "desc";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
	private static final String EXCEPTION_PDF = "Exception while creating PDF : ";

	private static final String INVOICE_NUMBER_PATH_VARIABLE_PATTERN = "{invoiceNumber:.*}";
	private static final String ORDER_ID_PATH_VARIABLE_PATTERN = "{orderShipmentActualId:.*}";
	private static final String REDIRECT_TO_INVOICE_LIST_PAGE = REDIRECT_PREFIX + "/my-account/invoices";
	private static final Logger LOG = Logger.getLogger(AccountPageController.class);

	private static final String TEXT_INVOICE_LISTING = "text.invoices";

	private static final String MY_ACCOUNT_INVOICE_DETAILS_URL = "/my-account/invoices/";

	private static final String FORM_GLOBAL_ERROR_PASSWORD = "form.global.error.password";

	private static final String FORM_SYSTEM_DOWN_ERROR = "form.system.down.customerinfo.error";

	private static final String PARTNER_POINTS_SERVICE_DOWN_ERROR = "partner.points.service.down.error";

	private static final String TEXT_STORE_DATEFORMAT_KEY = "text.store.dateformat";
	private static final String DEFAULT_DATEFORMAT = "MM/dd/yyyy";

	private static final String NOINVLOICEFLAG = "noInvoiceFlag";

	private static final String CAYAN_USER_CALCEL = "User_Cancelled";

	private static final String DELIVERY_ADDRESS_FULLPAGEPATH = "analytics.fullpath.delivery.addresses";
	private static final String BILLING_ADDRESS_FULLPAGEPATH = "analytics.fullpath.billing.addresses";
	private static final String EWALLET_NOT_FOUND = "Unable to fetch the Ewallet details in hybris";

	private static final String PAYMENTTYPE_ONE = "1";

	private static final String IS_PARTNERPROGRAM_ENROLLED = "isPartnerProgramEnrolled";

	private static final String IS_PARTNERSPROGRAM_RETAIL = "isPartnersProgramRetail";
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "invoiceFacade")
	private InvoiceFacade invoiceFacade;

	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "passwordValidator")
	private PasswordValidator passwordValidator;

	@Resource(name = "addressValidator")
	private AddressValidator addressValidator;

	@Resource(name = "siteoneAddressValidator")
	private SiteOneAddressValidator siteOneAddressValidator;

	@Resource(name = "profileValidator")
	private ProfileValidator profileValidator;

	@Resource(name = "emailValidator")
	private EmailValidator emailValidator;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Resource(name = "addressVerificationFacade")
	private AddressVerificationFacade addressVerificationFacade;

	@Resource(name = "addressVerificationResultHandler")
	private AddressVerificationResultHandler addressVerificationResultHandler;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@Resource(name = "orderGridFormFacade")
	private OrderGridFormFacade orderGridFormFacade;

	@Resource(name = "addressDataUtil")
	private AddressDataUtil addressDataUtil;

	@Resource(name = "siteoneAddressDataUtil")
	private SiteOneAddressDataUtil siteoneAddressDataUtil;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "b2bUnitFacade")
	protected B2BUnitFacade b2bUnitFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "siteOneChangePwdValidator")
	private SiteOneChangePwdValidator siteOneChangePwdValidator;

	@Resource(name = "siteOneAccountBreadcrumbBuilder")
	private SiteOneAccountBreadcrumbBuilder siteOneAccountBreadcrumbBuilder;

	@Resource(name = "siteOneChangePwdBreadcrumbBuilder")
	private SiteOneChangePwdBreadcrumbBuilder siteOneChangePwdBreadcrumbBuilder;

	@Resource(name = "siteOneInvoicePDFUtils")
	private SiteOneInvoicePDFUtils siteOneInvoicePDFUtils;

	@Resource(name = "siteOneInvoiceCSVUtils")
	private SiteOneInvoiceCSVUtils siteOneInvoiceCSVUtils;

	private static final String UPDATE_PERSONAL_CMS_PAGE = "siteoneupdate-profile";

	private static final String UPDATE_PREFERENCE_CMS_PAGE = "siteoneupdate-preference";

	@Resource(name = "pageTitleResolver")
	private SiteOnePageTitleResolver siteOnePageTitleResolver;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneRequestAccountValidator")
	private SiteOneRequestAccountValidator siteOneRequestAccountValidator;

	@Resource(name = "siteOneCayanTransportFacade")
	private SiteOneCayanTransportFacade siteOneCayanTransportFacade;


	@Resource(name = "siteOneCayanPaymentFacade")
	private SiteOneCayanPaymentFacade siteOneCayanPaymentFacade;

	@Resource(name = "siteOneEwalletFacade")
	private SiteOneEwalletFacade siteOneEwalletFacade;

	@Resource(name = "b2bUserFacade")
	private B2BUserFacade b2bUserFacade;

	@Resource(name = "siteOneEwalletDataUtil")
	private SiteOneEwalletDataUtil siteOneEwalletDataUtil;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "blobDataImportService")
	private SiteOneBlobDataImportService blobDataImportService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "oauthTokenStore")
	private TokenStore oauthTokenStore;


	/**
	 * @return the siteOnePageTitleResolver
	 */
	public SiteOnePageTitleResolver getSiteOnePageTitleResolver()
	{
		return siteOnePageTitleResolver;
	}

	/**
	 * @param siteOnePageTitleResolver
	 *           the siteOnePageTitleResolver to set
	 */
	public void setSiteOnePageTitleResolver(final SiteOnePageTitleResolver siteOnePageTitleResolver)
	{
		this.siteOnePageTitleResolver = siteOnePageTitleResolver;
	}

	/**
	 * @return the invoiceFacade
	 */
	public InvoiceFacade getInvoiceFacade()
	{
		return invoiceFacade;
	}

	/**
	 * @param invoiceFacade
	 *           the invoiceFacade to set
	 */
	public void setInvoiceFacade(final InvoiceFacade invoiceFacade)
	{
		this.invoiceFacade = invoiceFacade;
	}

	protected PasswordValidator getPasswordValidator()
	{
		return passwordValidator;
	}

	protected SiteOneAddressValidator getSiteOneAddressValidator()
	{
		return siteOneAddressValidator;
	}

	protected AddressValidator getAddressValidator()
	{
		return addressValidator;
	}


	protected ProfileValidator getProfileValidator()
	{
		return profileValidator;
	}

	protected EmailValidator getEmailValidator()
	{
		return emailValidator;
	}

	protected I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	protected AddressVerificationFacade getAddressVerificationFacade()
	{
		return addressVerificationFacade;
	}

	protected AddressVerificationResultHandler getAddressVerificationResultHandler()
	{
		return addressVerificationResultHandler;
	}

	@ModelAttribute("countries")
	public Collection<CountryData> getCountries()
	{
		return checkoutFacade.getDeliveryCountries();
	}

	@ModelAttribute("titles")
	public Collection<TitleData> getTitles()
	{
		return userFacade.getTitles();
	}

	@ModelAttribute("countryDataMap")
	public Map<String, CountryData> getCountryDataMap()
	{
		final Map<String, CountryData> countryDataMap = new HashMap<>();
		for (final CountryData countryData : getCountries())
		{
			countryDataMap.put(countryData.getIsocode(), countryData);
		}
		return countryDataMap;
	}

	@ModelAttribute("childUnits")
	public List<SelectOption> getB2BUnits()
	{
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();

		final List<SelectOption> childUnits = populateSelectBoxForB2BUnit(
				((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(parent.getUid()));

		return childUnits;
	}

	protected List<SelectOption> populateSelectBoxForB2BUnit(final List<B2BUnitData> unitData)
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();

		for (final B2BUnitData unit : unitData)
		{
			selectOptions.add(new SelectOption(unit.getUid(), unit.getName()));
		}

		return selectOptions;
	}

	@GetMapping("/addressform")
	public String getCountryAddressForm(@RequestParam("addressCode")
	final String addressCode, @RequestParam("countryIsoCode")
	final String countryIsoCode, final Model model)
	{
		model.addAttribute("supportedCountries", getCountries());
		populateModelRegionAndCountry(model, countryIsoCode);

		final SiteOneAddressForm addressForm = new SiteOneAddressForm();
		model.addAttribute(ADDRESS_FORM_ATTR, addressForm);

		for (final AddressData addressData : userFacade.getAddressBook())
		{
			if (addressData.getId() != null && addressData.getId().equals(addressCode)
					&& countryIsoCode.equals(addressData.getCountry().getIsocode()))
			{
				model.addAttribute(ADDRESS_DATA_ATTR, addressData);
				siteoneAddressDataUtil.convert(addressData, addressForm);
				break;
			}
		}
		return ControllerConstants.Views.Fragments.Account.CountryAddressForm;
	}

	protected void populateModelRegionAndCountry(final Model model, final String countryIsoCode)
	{
		model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso(countryIsoCode));
		model.addAttribute(COUNTRY_ATTR, countryIsoCode);
	}

	@GetMapping
	@RequireHardLogIn
	public String account(final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (ResponsiveUtils.isResponsive())
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page.not.found", null);
			return REDIRECT_PREFIX + "/";
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ACCOUNT_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@GetMapping("/profile")
	@RequireHardLogIn
	public String profile(final Model model) throws CMSItemNotFoundException
	{
		final List<TitleData> titles = userFacade.getTitles();

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getTitleCode() != null)
		{
			model.addAttribute("title", findTitleForCode(titles, customerData.getTitleCode()));
		}

		model.addAttribute("customerData", customerData);

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	protected TitleData findTitleForCode(final List<TitleData> titles, final String code)
	{
		if (code != null && !code.isEmpty() && titles != null && !titles.isEmpty())
		{
			for (final TitleData title : titles)
			{
				if (code.equals(title.getCode()))
				{
					return title;
				}
			}
		}
		return null;
	}

	protected String setErrorMessagesAndCMSPage(final Model model, final String cmsPageLabelOrId) throws CMSItemNotFoundException
	{
		GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
		storeCmsPageInModel(model, getContentPageForLabelOrId(cmsPageLabelOrId));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(cmsPageLabelOrId));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		return getViewForPage(model);
	}


	@GetMapping("/update-profile")
	@RequireHardLogIn
	public String editProfile(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		final UpdateProfileForm updateProfileForm = new UpdateProfileForm();

		updateProfileForm.setTitleCode(customerData.getTitleCode());
		updateProfileForm.setFirstName(customerData.getFirstName());
		updateProfileForm.setLastName(customerData.getLastName());

		model.addAttribute("updateProfileForm", updateProfileForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PROFILE_CMS_PAGE));

		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@PostMapping("/update-profile")
	@RequireHardLogIn
	public String updateProfile(final UpdateProfileForm updateProfileForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		getProfileValidator().validate(updateProfileForm, bindingResult);

		String returnAction = REDIRECT_TO_UPDATE_PROFILE;
		final CustomerData currentCustomerData = customerFacade.getCurrentCustomer();
		final CustomerData customerData = new CustomerData();
		customerData.setTitleCode(updateProfileForm.getTitleCode());
		customerData.setFirstName(updateProfileForm.getFirstName());
		customerData.setLastName(updateProfileForm.getLastName());
		customerData.setUid(currentCustomerData.getUid());
		customerData.setDisplayUid(currentCustomerData.getDisplayUid());

		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());

		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PROFILE_CMS_PAGE));

		if (bindingResult.hasErrors())
		{
			returnAction = setErrorMessagesAndCMSPage(model, UPDATE_PROFILE_CMS_PAGE);
		}
		else
		{
			try
			{
				customerFacade.updateProfile(customerData);
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
						"text.account.profile.confirmationUpdated", null);

			}
			catch (final DuplicateUidException e)
			{
				LOG.error(e.getMessage(), e);
				bindingResult.rejectValue("email", "registration.error.account.exists.title");
				returnAction = setErrorMessagesAndCMSPage(model, UPDATE_PROFILE_CMS_PAGE);
			}
		}


		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		return returnAction;
	}

	@GetMapping("/update-password")
	@RequireHardLogIn
	public String updatePassword(final Model model) throws CMSItemNotFoundException
	{
		final UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();

		model.addAttribute("updatePasswordForm", updatePasswordForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));

		//model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.updatePasswordForm"));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, siteOneChangePwdBreadcrumbBuilder.getBreadcrumbsForChangePwd());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@PostMapping("/update-password")
	@RequireHardLogIn
	public String updatePassword(final UpdatePasswordForm updatePasswordForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		getSiteOneChangePwdValidator().validate(updatePasswordForm, bindingResult);
		if (!bindingResult.hasErrors())
		{
			if (updatePasswordForm.getNewPassword().equals(updatePasswordForm.getCheckNewPassword()))
			{
				try
				{
					((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
					((SiteOneCustomerFacade) customerFacade).changePassword(updatePasswordForm.getCurrentPassword(),
							updatePasswordForm.getNewPassword());
				}
				catch (final PasswordMismatchException localException)
				{
					LOG.error(localException.getMessage(), localException);
					bindingResult.rejectValue("currentPassword", PROFILE_CURRENT_PASSWORD_INVALID, new Object[] {},
							PROFILE_CURRENT_PASSWORD_INVALID);
				}
				catch (final PasswordPolicyViolationException passwordPolicyViolationException)
				{
					LOG.error(passwordPolicyViolationException);
					storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
					GlobalMessages.addErrorMessage(model, "form.global.error.password");
					model.addAttribute(WebConstants.BREADCRUMBS_KEY, siteOneChangePwdBreadcrumbBuilder.getBreadcrumbsForChangePwd());
					return getViewForPage(model);
				}
				catch (final RecentlyUsedPasswordException oktaRecentlyUsedPasswordException)
				{
					LOG.error(oktaRecentlyUsedPasswordException);
					storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
					GlobalMessages.addErrorMessage(model, "form.global.error.recent.password");
					model.addAttribute(WebConstants.BREADCRUMBS_KEY, siteOneChangePwdBreadcrumbBuilder.getBreadcrumbsForChangePwd());
					return getViewForPage(model);
				}

			}
			else
			{
				bindingResult.rejectValue("checkNewPassword", "validation.checkPwd.equals", new Object[] {},
						"validation.checkPwd.equals");
			}
		}

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));

			model.addAttribute(WebConstants.BREADCRUMBS_KEY, siteOneChangePwdBreadcrumbBuilder.getBreadcrumbsForChangePwd());
			return getViewForPage(model);
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
					"text.account.confirmation.password.updated", null);
			return REDIRECT_TO_PASSWORD_UPDATE_PAGE;
		}
	}

	@GetMapping("/address-book/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getAddressBook(@PathVariable("unitId")

	final String unitId, final Model model) throws CMSItemNotFoundException
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		model.addAttribute(ADDRESS_DATA_ATTR,
				((SiteOneCustomerFacade) customerFacade).getShippingAddresssesForUnit(sanitizedunitId));
		getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE).setFullPagePath(
				getMessageSource().getMessage(DELIVERY_ADDRESS_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("unitId", sanitizedunitId);
		model.addAttribute("enableAddModifyDeliveryAddress",
				((B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer()).getEnableAddModifyDeliveryAddress());
		return getViewForPage(model);
	}

	@GetMapping("/billing-address/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getBillingAddress(@PathVariable("unitId")
	final String unitId, final Model model) throws CMSItemNotFoundException
	{
		/** Start of Modification for Defect SE-6461 : Show only 1 billingAddress and a default one for the Unit */
		model.addAttribute(ADDRESS_DATA_ATTR, ((SiteOneCustomerFacade) customerFacade).getDefaultBillingAddressesForUnit(unitId));
		/** End of Modification for Defect SE-6461 : Show only 1 billingAddress and a default one for the Unit */
		getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE).setFullPagePath(
				getMessageSource().getMessage(BILLING_ADDRESS_FULLPAGEPATH, null, getI18nService().getCurrentLocale()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		updateAdddressBookPageTitle(model);
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("isBilling", true);
		return getViewForPage(model);
	}


	/**
	 * This method is responsible for handle validate address request.
	 *
	 * @param addressForm
	 *           - holds address entered by user.
	 * @return addressVerificationData - Address Verification result json.
	 */
	@ResponseBody
	@PostMapping("/validate-address")
	public SiteOneAddressVerificationData validateAddress(final SiteOneAddressForm addressForm)
	{
		final AddressData addressData = siteoneAddressDataUtil.convertToAddressData(addressForm);
		return ((SiteOneCustomerFacade) customerFacade).validateAddress(addressData);
	}

	@GetMapping("/add-address/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String addAddress(@PathVariable("unitId")

	final String unitId, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getRoles().contains("b2badmingroup") || (customerData.getEnableAddModifyDeliveryAddress()))
		{
			model.addAttribute(COUNTRY_DATA_ATTR, checkoutFacade.getDeliveryCountries());
			model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
			final SiteOneAddressForm addressForm = getPreparedAddressForm();
			model.addAttribute(ADDRESS_FORM_ATTR, addressForm);
			model.addAttribute(ADDRESS_BOOK_EMPTY_ATTR,
					Boolean.valueOf(((SiteOneCustomerFacade) customerFacade).isAddressBookEmpty(sanitizedunitId)));
			model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.FALSE);
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));

			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder
					.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ADDRESS_BOOK_URL + "/" + sanitizedunitId,
					getMessageSource().getMessage(TEXT_ACCOUNT_ADDRESS_BOOK, null, getI18nService().getCurrentLocale()), null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
			model.addAttribute("unitId", sanitizedunitId);
			model.addAttribute("edit", Boolean.FALSE);
			return getViewForPage(model);
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.incorrect.customer", null);
			return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + sanitizedunitId;
		}
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

	@PostMapping("/add-address/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String addAddress(@PathVariable("unitId")
	final String unitId, final SiteOneAddressForm addressForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		getSiteOneAddressValidator().validate(addressForm, bindingResult);
		model.addAttribute("unitId", unitId);

		model.addAttribute("edit", Boolean.FALSE);
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ADDRESS_BOOK_URL + "/" + unitId,
				getMessageSource().getMessage(TEXT_ACCOUNT_ADDRESS_BOOK, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}

		final AddressData newAddress = siteoneAddressDataUtil.convertToVisibleAddressData(addressForm);

		if (((SiteOneCustomerFacade) customerFacade).isAddressBookEmpty(addressForm.getUnitId()))
		{
			newAddress.setDefaultAddress(true);
		}

		populateModelRegionAndCountry(model, addressForm.getCountryIso());
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR,
				Boolean.valueOf(isDefaultAddress(addressForm.getAddressId(), addressForm.getUnitId())));
		try
		{
			((SiteOneCustomerFacade) customerFacade).addAddress(newAddress, addressForm.getUnitId());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.added",
					null);
			if (BooleanUtils.isTrue(newAddress.isDefaultAddress()))
			{
				((SiteOneCustomerFacade) customerFacade).setDefaultAddress(newAddress.getId(), unitId);
			}
			return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + SiteoneXSSFilterUtil.filter(addressForm.getUnitId());
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addErrorMessage(model, "form.sytem.down.address.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}
		catch (final AddressNotCreatedOrUpdatedInUEException addressNotCreatedOrUpdatedInUEException)
		{
			LOG.error(addressNotCreatedOrUpdatedInUEException);
			GlobalMessages.addErrorMessage(model, "form.sytem.down.address.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}

	}

	protected void setUpAddressFormAfterError(final SiteOneAddressForm addressForm, final Model model)
	{
		model.addAttribute(COUNTRY_DATA_ATTR, checkoutFacade.getDeliveryCountries());
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
		model.addAttribute(ADDRESS_BOOK_EMPTY_ATTR,
				Boolean.valueOf(((SiteOneCustomerFacade) customerFacade).isAddressBookEmpty(addressForm.getUnitId())));
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR,
				Boolean.valueOf(isDefaultAddress(addressForm.getAddressId(), addressForm.getUnitId())));
		if (addressForm.getCountryIso() != null)
		{
			populateModelRegionAndCountry(model, addressForm.getCountryIso());
		}
	}

	@GetMapping("/edit-address/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ ADDRESS_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String editAddress(@PathVariable("addressCode")
	final String addressCode, @PathVariable("unitId")
	final String unitId, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getRoles().contains("b2badmingroup") || (customerData.getEnableAddModifyDeliveryAddress()))
		{
			final SiteOneAddressForm addressForm = new SiteOneAddressForm();
			model.addAttribute(COUNTRY_DATA_ATTR, checkoutFacade.getDeliveryCountries());
			model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
			model.addAttribute(ADDRESS_FORM_ATTR, addressForm);
			model.addAttribute("unitId", unitId);
			final Collection<AddressData> addressBook = ((SiteOneCustomerFacade) customerFacade)
					.getShippingAddresssesForUnit(unitId);
			model.addAttribute(ADDRESS_BOOK_EMPTY_ATTR, Boolean.valueOf(CollectionUtils.isEmpty(addressBook)));

			for (final AddressData addressData : addressBook)
			{
				if (addressData.getId() != null && addressData.getId().equals(addressCode))
				{
					model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso(addressData.getCountry().getIsocode()));
					model.addAttribute(COUNTRY_ATTR, addressData.getCountry().getIsocode());
					model.addAttribute(ADDRESS_DATA_ATTR, addressData);
					siteoneAddressDataUtil.convert(addressData, addressForm);
					addressForm.setUnitId(addressForm.getUnitId());
					if (Boolean.TRUE.equals(addressData.getDefaultUserAddress()))
					{
						addressForm.setDefaultAddress(Boolean.TRUE);
						model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.TRUE);
					}
					else
					{
						addressForm.setDefaultAddress(Boolean.FALSE);
						model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.FALSE);
					}
					break;
				}
			}

			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));

			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ADDRESS_BOOK_URL + "/" + unitId,
					getMessageSource().getMessage(TEXT_ACCOUNT_ADDRESS_BOOK, null, getI18nService().getCurrentLocale()), null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
			model.addAttribute("edit", Boolean.TRUE);
			return getViewForPage(model);
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.incorrect.customer", null);
			return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + SiteoneXSSFilterUtil.filter(unitId);
		}
	}

	/**
	 * Method checks if address is set as default
	 *
	 * @param addressId
	 *           - identifier for address to check
	 * @return true if address is default, false if address is not default
	 */
	protected boolean isDefaultAddress(final String addressId, final String unitId)
	{
		final AddressData defaultAddress = ((SiteOneCustomerFacade) customerFacade).getDefaultShippingAddressForUnit(unitId);
		return defaultAddress != null && defaultAddress.getId() != null && defaultAddress.getId().equals(addressId);
	}

	@PostMapping("/edit-address/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ ADDRESS_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String editAddress(@PathVariable("addressCode")
	final String addressCode, @PathVariable("unitId")
	final String unitId, final SiteOneAddressForm addressForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		getSiteOneAddressValidator().validate(addressForm, bindingResult);

		model.addAttribute("edit", Boolean.TRUE);
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ADDRESS_BOOK_URL + "/" + unitId,
				getMessageSource().getMessage(TEXT_ACCOUNT_ADDRESS_BOOK, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}

		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);

		final AddressData newAddress = siteoneAddressDataUtil.convertToVisibleAddressData(addressForm);

		final Collection<AddressData> unitAddresses = ((SiteOneCustomerFacade) customerFacade)
				.getShippingAddresssesForUnit(addressForm.getUnitId());

		if (CollectionUtils.isEmpty(unitAddresses))
		{
			newAddress.setDefaultAddress(true);
		}

		model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso(addressForm.getCountryIso()));
		model.addAttribute(COUNTRY_ATTR, addressForm.getCountryIso());
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR,
				Boolean.valueOf(isDefaultAddress(addressForm.getAddressId(), addressForm.getUnitId())));

		try
		{
			((SiteOneCustomerFacade) customerFacade).editAddress(newAddress, unitId);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"account.confirmation.address.updated", null);
			if (Boolean.TRUE.equals(addressForm.getDefaultAddress()))
			{
				((SiteOneCustomerFacade) customerFacade).setDefaultAddress(addressCode, unitId);
			}
			return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + SiteoneXSSFilterUtil.filter(addressForm.getUnitId());
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addErrorMessage(model, "form.sytem.down.address.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}
		catch (final AddressNotCreatedOrUpdatedInUEException addressNotCreatedOrUpdatedInUEException)
		{
			LOG.error(addressNotCreatedOrUpdatedInUEException);
			GlobalMessages.addErrorMessage(model, "form.sytem.down.address.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}
		catch (final AddressNotRemovedInUEException addressNotRemovedInUEException)
		{
			LOG.error(addressNotRemovedInUEException);
			GlobalMessages.addErrorMessage(model, "form.sytem.down.address.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}
	}

	@PostMapping("/select-suggested-address")
	public String doSelectSuggestedAddress(final SiteOneAddressForm addressForm, final RedirectAttributes redirectModel)
	{
		final Set<String> resolveCountryRegions = org.springframework.util.StringUtils
				.commaDelimitedListToSet(Config.getParameter("resolve.country.regions"));

		final AddressData selectedAddress = addressDataUtil.convertToVisibleAddressData(addressForm);

		final CountryData countryData = selectedAddress.getCountry();

		if (!resolveCountryRegions.contains(countryData.getIsocode()))
		{
			selectedAddress.setRegion(null);
		}

		if (Boolean.TRUE.equals(addressForm.getEditAddress()))
		{
			userFacade.editAddress(selectedAddress);
		}
		else
		{
			userFacade.addAddress(selectedAddress);
		}

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.added");

		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/remove-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN + "/" + UNIT_ID_PATH_VARIABLE_PATTERN, method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeAddress(@PathVariable("addressCode")
	final String addressCode, @PathVariable("unitId")
	final String unitId, final RedirectAttributes redirectModel)
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		if (customerData.getRoles().contains("b2badmingroup") || (customerData.getEnableAddModifyDeliveryAddress()))
		{

			final AddressData addressData = new AddressData();
			addressData.setId(addressCode);
			try
			{
				((SiteOneCustomerFacade) customerFacade).removeAddress(addressData, unitId);
			}
			catch (final ServiceUnavailableException serviceUnavailableException)
			{
				LOG.error(serviceUnavailableException);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
						"form.sytem.down.removeaddress.error");
				return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + sanitizedunitId;
			}
			catch (final AddressNotRemovedInUEException addressNotRemovedInUEException)
			{
				LOG.error(addressNotRemovedInUEException);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
						"form.sytem.down.removeaddress.error");
				return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + sanitizedunitId;
			}

			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"account.confirmation.address.removed");
			return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + sanitizedunitId;
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.incorrect.customer", null);
			return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + sanitizedunitId;
		}
	}


	@GetMapping(value = "/set-default-address/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String setDefaultAddress(@PathVariable("addressCode")
	final String addressCode, @PathVariable("unitId")
	final String unitId, final RedirectAttributes redirectModel)
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		((SiteOneCustomerFacade) customerFacade).setDefaultAddress(addressCode, unitId);
		return REDIRECT_TO_ADDRESS_BOOK_PAGE + "/" + sanitizedunitId;
	}

	@GetMapping("/payment-details")
	@RequireHardLogIn
	public String paymentDetails(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("customerData", customerFacade.getCurrentCustomer());
		model.addAttribute("paymentInfoData", userFacade.getCCPaymentInfos(true));
		storeCmsPageInModel(model, getContentPageForLabelOrId(PAYMENT_DETAILS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs("text.account.paymentDetails"));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@PostMapping("/set-default-payment-details")
	@RequireHardLogIn
	public String setDefaultPaymentDetails(@RequestParam
	final String paymentInfoId)
	{
		CCPaymentInfoData paymentInfoData = null;
		if (StringUtils.isNotBlank(paymentInfoId))
		{
			paymentInfoData = userFacade.getCCPaymentInfoForCode(paymentInfoId);
		}
		userFacade.setDefaultPaymentInfo(paymentInfoData);
		return REDIRECT_TO_PAYMENT_INFO_PAGE;
	}

	@PostMapping("/remove-payment-method")
	@RequireHardLogIn
	public String removePaymentMethod(@RequestParam(value = "paymentInfoId")
	final String paymentMethodId, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		userFacade.unlinkCCPaymentInfo(paymentMethodId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.profile.paymentCart.removed");
		return REDIRECT_TO_PAYMENT_INFO_PAGE;
	}

	@GetMapping("/my-stores")
	@RequireHardLogIn
	public String getMyStores(final Model model) throws CMSItemNotFoundException
	{
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(POINT_OF_SERVICE_DATA_ATTR, ((SiteOneCustomerFacade) customerFacade).getMyStores());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_STORES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_STORES_CMS_PAGE));
		return getViewForPage(model);
	}

	@GetMapping("/make-my-store/" + STORE_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String makeMyStore(@PathVariable("storeId")
	final String storeId, final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		((SiteOneCustomerFacade) customerFacade).makeMyStore(storeId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.preferredstore.added", null);
		return REDIRECT_TO_MYSTORES_PAGE;

	}

	@GetMapping("/remove-my-store/" + STORE_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String removeMyStore(@PathVariable("storeId")
	final String storeId, final Model model, final HttpServletRequest request, final HttpServletResponse response,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		final Cookie geoLocatedCookie = WebUtils.getCookie(request, GEO_LOCATED_STORE_COOKIE);
		String geoLocatedStoreId = null;
		if (null != geoLocatedCookie)
		{
			geoLocatedStoreId = geoLocatedCookie.getValue();
		}
		((SiteOneCustomerFacade) customerFacade).removeFromMyStore(storeId, geoLocatedStoreId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.storeFromList.removed", null);
		return REDIRECT_TO_MYSTORES_PAGE;
	}

	@GetMapping("/account-dashboard")
	@RequireHardLogIn
	public String getAccountDashboardPage(final Model model, final HttpServletRequest request) throws CMSItemNotFoundException // NOSONAR
	{
		boolean quoteFeature = false;
		((SiteOneB2BUnitFacade) b2bUnitFacade).setApproverGroupsForAdmin();
		final HttpSession session = request.getSession();
		storeCmsPageInModel(model, getContentPageForLabelOrId("accountDashboardPage"));
		/** Start of Modification for Defect SE-6461 : Show only 1 billingAddress and just the last created */
		final B2BUnitData b2BUnitData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		final AddressData defaultBillingAddress = ((SiteOneCustomerFacade) customerFacade)
				.getDefaultBillingAddressForUnit(b2BUnitData.getUid());
		model.addAttribute("billingAddress", defaultBillingAddress);
		B2BUnitData bUnit = storeSessionFacade.getSessionShipTo();
		model.addAttribute("unit", bUnit);
		/** End of Modification for Defect SE-6461 */
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		if (null == session.getAttribute("shipToCompanyName"))
		{
			session.setAttribute("shipToCompanyName",
					((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer().getName());
		}
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		
		if(BooleanUtils.isTrue(getSessionService().getAttribute("isEnabledForAgroAI")) || BooleanUtils.isTrue(((SiteOneB2BUnitFacade) b2bUnitFacade).checkIsParentUnitEnabledForAgroAI(parent.getUid())))
		{
			model.addAttribute("isEnabledForAgroAI", true);
		try
		{
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			Collection<OAuth2AccessToken> accessTokenList=new ArrayList<>();
			if(null !=customer)
			{
			accessTokenList=oauthTokenStore.findTokensByClientIdAndUserName(Config.getString("agro.ai.client.id", StringUtils.EMPTY),customer.getUid());
			if(CollectionUtils.isNotEmpty(accessTokenList))
			{
				for(final OAuth2AccessToken token:accessTokenList)
				{
					getSessionService().setAttribute("refreshToken",token.getRefreshToken().getValue());
					break;
				}
				
			}
			}
		final JSONObject json = new JSONObject();
		json.put("sessionToken", (String)getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN));
		json.put("refreshToken", (String)getSessionService().getAttribute("refreshToken"));		
		final PointOfServiceData pos = storeSessionFacade.getSessionStore();
		if (null != pos)
		{
			json.put("branchNumber", pos.getStoreId());
		}
		model.addAttribute("agroToken",XSSEncoder.encodeURL(getEncryptionData(json.toString(),Config.getString("agro.encryption.aes.secret", null))));
		}
		catch(Exception e)
		{
			LOG.error("Error while converting json object: " + e.toString());
		}
		}

		model.addAttribute("featureSwitch", ((SiteOneB2BUnitFacade) b2bUnitFacade).checkIsUnitForApproveOrders(parent.getUid()));
		model.addAttribute("isPunchOutAccount",
				((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer().getIsPunchOutAccount());
		model.addAttribute("isMainAccount", true);
		//model.addAttribute("isAdmin", ((SiteOneB2BUnitService) b2bUnitService).isAdminUser());
		if (bUnit != null)
		{
			model.addAttribute("orderData", ((SiteOneOrderFacade) orderFacade).fetchRecentOrdersFromAPI(bUnit.getUid()));
		}
		else
		{
			model.addAttribute("orderData", ((SiteOneOrderFacade) orderFacade).getRecentOrderForAccount(null));
		}		
		model.addAttribute("ordersInTransit", ((SiteOneOrderFacade) orderFacade).ordersInTransit());
		siteOneEwalletFacade.populatePaymentconfig(ACCOUNT_CMS_PAGE, model);
		model.addAttribute("pendingOrderCount",
				((SiteOneOrderFacade) orderFacade).getPendingApprovalCount(createPageableData(0, 25, null, null)));
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();


		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}


		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);

		SiteOneCustInfoData siteOneCustInfoData = null;
		SiteOneCustInfoData siteOneLoyaltyInfoData = null;
		try
		{
			siteOneCustInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(bUnit.getUid());
			
			if (null != siteOneCustInfoData)
			{
				model.addAttribute("rewardPoints", siteOneCustInfoData.getCustomerRewardsPointsInfo());
				if (siteOneCustInfoData.getCustomerSalesInfo() != null)
				{
					model.addAttribute("ytdSales", siteOneCustInfoData.getCustomerSalesInfo().getYtdSales());
					model.addAttribute("lastYtdSales", siteOneCustInfoData.getCustomerSalesInfo().getLastYtdSales());
					model.addAttribute("twelveMonthsSales", siteOneCustInfoData.getCustomerSalesInfo().getTwelveMonthSales());
				}
				if (siteOneCustInfoData.getCustomerCreditInfo() != null)
				{
					model.addAttribute("creditLimit", siteOneCustInfoData.getCustomerCreditInfo().getCreditLimit());
					model.addAttribute("creditBalance", siteOneCustInfoData.getCustomerCreditInfo().getBalance());
					model.addAttribute("creditOTB", siteOneCustInfoData.getCustomerCreditInfo().getOpenToBuy());
				}				
			}
			siteOneLoyaltyInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getLoyaltyInformation(b2BUnitData.getUid());

			if (null != siteOneLoyaltyInfoData)
			{
				model.addAttribute("loyaltyPartnerPoints", siteOneLoyaltyInfoData.getLoyaltyProgramInfo());
			}
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addErrorMessage(model, PARTNER_POINTS_SERVICE_DOWN_ERROR);
			model.addAttribute("rewardPoints", null);
			model.addAttribute("loyaltyPartnerPoints", null);
		}

		final List<TitleData> titles = userFacade.getTitles();

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getTitleCode() != null)
		{
			model.addAttribute("title", findTitleForCode(titles, customerData.getTitleCode()));
		}
//		final Boolean projectServices = ((SiteOneCustomerFacade) customerFacade).isHavingProjectServices();
//		String psUrl = null;
//		if (projectServices != null && Boolean.TRUE.equals(projectServices))
//		{
//			String baseUrl = configurationService.getConfiguration().getString(("project.services.url.base"));
//			String sessionToken = (String)getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN);
//			psUrl = baseUrl + "?sessionToken=" + sessionToken;
//
//			LOG.error("The url is " + psUrl);
//			LOG.error("The account page session token is " + sessionToken);
//		}
//		model.addAttribute("projectServicesUrl", psUrl);

		boolean isPartnerProgramAdminEmail = false;
		if (null != siteOneLoyaltyInfoData && null != siteOneLoyaltyInfoData.getProfile()
				&& siteOneLoyaltyInfoData.getProfile().getAttributes().getPartnerProgramAdminEmail() != null && siteOneLoyaltyInfoData
						.getProfile().getAttributes().getPartnerProgramAdminEmail().equalsIgnoreCase(customerData.getUid()))
		{
			isPartnerProgramAdminEmail = true;
		}

		final MyAccountUserInfo myAccountUserInfo = ((SiteOneB2BUnitFacade) b2bUnitFacade).getUserAccountInfo();
		model.addAttribute(IS_PARTNERPROGRAM_ENROLLED, myAccountUserInfo.getIsPartnersProgramEnrolled());
		model.addAttribute(IS_PARTNERSPROGRAM_RETAIL, myAccountUserInfo.getIsPartnersProgramRetail());
		model.addAttribute("isAdminUser", myAccountUserInfo.getIsAdminUser());
		model.addAttribute("isPartnerProgramAdminEmail", isPartnerProgramAdminEmail);
		model.addAttribute("isInPartnersProgramPilot", myAccountUserInfo.getIsInPartnersProgramPilot());		
		
		this.populatePermissions(model);		
		return ControllerConstants.Views.Pages.Account.AccountDashboardPage;

	}

	@GetMapping("/pay-account-online")
	public ModelAndView getOktaRedirectLink(final HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		final Boolean havingContactPayBillOnline = ((SiteOneCustomerFacade) customerFacade).isHavingPayBillOnline();
		final Boolean havingAccountPayBillOnline = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer()
				.getPayBillOnline();
		if (null != havingAccountPayBillOnline && null != havingContactPayBillOnline && havingAccountPayBillOnline.booleanValue()
				&& havingContactPayBillOnline.booleanValue())
		{
			return new ModelAndView(
					REDIRECT_PREFIX + Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART1, StringUtils.EMPTY)
							+ getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN)
							+ Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART2, StringUtils.EMPTY));
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"account.error.paybillonline.access", null);
			return new ModelAndView(REDIRECT_TO_MAIN_VIEW);
		}

	}

	@GetMapping("/listofshipto")
	public @ResponseBody List<B2BUnitData> getListOfShipTo(@RequestParam(value = "uid")
	final String uid)
	{
		final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnitBasedOnCustomer(uid);
		if (CollectionUtils.isNotEmpty(childs))
		{
			return childs;
		}
		return null;
	}

	//getting selected ship-to details

	@GetMapping("/ship-to/{id}")
	public String getShipToDetails(@PathVariable("id")
	final String id, final Model model) throws CMSItemNotFoundException
	{
		boolean quoteFeature = false;
		storeCmsPageInModel(model, getContentPageForLabelOrId("accountDashboardPage"));
		model.addAttribute("unit", b2bUnitFacade.getUnitForUid(id));
		sessionService.setAttribute("selectedShipTo", b2bUnitFacade.getUnitForUid(id));
		model.addAttribute("isShipTo", true);
		//model.addAttribute("isAdmin", ((SiteOneB2BUnitService) b2bUnitService).isAdminUser());
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		model.addAttribute("featureSwitch", ((SiteOneB2BUnitFacade) b2bUnitFacade).checkIsUnitForApproveOrders(parent.getUid()));
		model.addAttribute("orderData", ((SiteOneOrderFacade) orderFacade).getRecentOrderForAccount(id));
		model.addAttribute("pendingOrderCount",
				((SiteOneOrderFacade) orderFacade).getPendingApprovalCount(createPageableData(0, 25, null, null)));
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();


		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}


		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);
		this.populatePermissions(model);
		return ControllerConstants.Views.Pages.Account.AccountDashboardPage;
	}

	@GetMapping("/all-ship-to/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getShipToPage(@PathVariable("unitId")
	final String unitId, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", defaultValue = B2BUnitModel.NAME)
	final String sortCode, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer) throws CMSItemNotFoundException
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);

		String trimmedSearchParam = null;
		if (null != searchParam)
		{
			trimmedSearchParam = searchParam.trim();
		}
		final PageableData pageableData = createPageableData(page, this.getSearchPageSize(), sortCode, showMode);
		final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade())
				.getPagedB2BUnits(pageableData, sanitizedunitId, trimmedSearchParam);
		populateModel(model, searchPageData, showMode);

		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("action", "manageUnit");
		model.addAttribute("sortShhipTo", sortCode);
		model.addAttribute("searchParam", trimmedSearchParam);
		storeCmsPageInModel(model, getContentPageForLabelOrId("shipToPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("shipToPage"));
		return ControllerConstants.Views.Pages.Account.ShipToPage;
	}





	@Override
	protected int getSearchPageSize()
	{
		return getSiteConfigService().getInt("siteoneorgaddon.search.pageSize", 2);
	}

	protected int getSearchPopUpSize()
	{
		return getSiteConfigService().getInt("siteoneorgaddon.searchPopup.pageSize", 10);
	}

	protected int getPurchasedProductPageSize()
	{
		return getSiteConfigService().getInt("siteone.purchasedProduct.pageSize", 24);
	}


	@SuppressWarnings("boxing")
	public void populatePermissions(final Model model) throws CMSItemNotFoundException
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		
		if (null != customer)
		{
			model.addAttribute("InvoicePermission", customer.getInvoicePermissions());
			model.addAttribute("partnerProgramPermission", customer.getPartnerProgramPermissions());
			model.addAttribute("AccountOverviewForParent", customer.getAccountOverviewForParent());
			model.addAttribute("AccountOverviewForShipTos", customer.getAccountOverviewForShipTos());
			LOG.error("partnerProgramPermission flag value is"+customer.getPartnerProgramPermissions());
		}
	}

	public static class SelectOption
	{
		private final String code;
		private final String name;

		public SelectOption(final String code, final String name)
		{
			this.code = code;
			this.name = name;
		}

		public String getCode()
		{
			return code;
		}

		public String getName()
		{
			return name;
		}
	}

	@GetMapping("/update-siteoneprofile")
	@RequireHardLogIn
	public String editPersonalDetails(@ModelAttribute("siteOneRequestAccountForm")
	final SiteOneRequestAccountForm siteOneRequestAccountForm, final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
		final CustomerData customerData = ((SiteOneCustomerFacade) customerFacade).fetchPersonalDetails();
		final SiteOneUpdateProfileForm updateProfileForm = new SiteOneUpdateProfileForm();


		updateProfileForm.setName(customerData.getName());
		updateProfileForm.setContactNumber(customerData.getContactNumber());

		updateProfileForm.setEmail(customerData.getEmail());
		updateProfileForm.setPassword(customerData.getEncodedPass());


		updateProfileForm.setIsEmailOpt(customerData.getEmailOptIn());

		updateProfileForm.setIsSMSOpt(customerData.getSmsOptIn());

		updateProfileForm.setIsAdmin(customerData.getIsAdmin());


		model.addAttribute("updateProfileForm", updateProfileForm);

		final UserEmailData userData = ((SiteOneCustomerFacade) customerFacade).fetchPreferences();

		if (null != userData && null != userData.getCustomFields())
		{
			for (final CustomFields custField : userData.getCustomFields())
			{
				if (custField.getName().equalsIgnoreCase("emailPromo"))
				{
					model.addAttribute("emailSubscribe", custField.getValue());
				}
			}
		}


		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PERSONAL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PERSONAL_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));

		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);


		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);

		model.addAttribute("isPunchOutAccount",
				((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer().getIsPunchOutAccount());
		if (customerData.getLangPreference() != null)
		{
			siteOneRequestAccountForm.setLanguagePreference(customerData.getLangPreference().getName(Locale.ENGLISH));
		}
		else
		{
			siteOneRequestAccountForm.setLanguagePreference(null);
		}
		model.addAttribute("siteOneRequestAccountForm", siteOneRequestAccountForm);

		return getViewForPage(model);
	}

	@PostMapping("/update-language")
	@RequireHardLogIn
	public String updatePreferredLanguage(@ModelAttribute("siteOneRequestAccountForm")
	final SiteOneRequestAccountForm siteOneRequestAccountForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException // NOSONAR
	{
		siteOneRequestAccountValidator.validatePreferredLanguage(siteOneRequestAccountForm.getLanguagePreference(), bindingResult);
		if (bindingResult.hasErrors())
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "account.error.preferredLanguage",
					null);
			return REDIRECT_TO_UPDATE_LANGUAGE;
		}
		model.addAttribute("siteOneRequestAccountForm", siteOneRequestAccountForm);
		try
		{
			((SiteOneCustomerFacade) customerFacade).updateLanguagePreference(siteOneRequestAccountForm.getLanguagePreference());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"account.confirmation.preferredLanguage", null);

		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Service Unavailable Exception Occured", serviceUnavailableException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "ue.error.preferredLanguage", null);
			return REDIRECT_TO_UPDATE_LANGUAGE;
		}
		catch (final ContactNotCreatedOrUpdatedInUEException contactNotCreatedOrUpdatedInUEException)
		{
			LOG.error("Contact Not Created/Updated In UE", contactNotCreatedOrUpdatedInUEException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "ue.error.preferredLanguage", null);
			return REDIRECT_TO_UPDATE_LANGUAGE;
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			LOG.error("unknown Identifier Exception: " + siteOneRequestAccountForm.getEmailAddress(), unknownIdentifierException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "ue.error.preferredLanguage", null);
			return REDIRECT_TO_UPDATE_LANGUAGE;
		}


		return REDIRECT_TO_UPDATE_LANGUAGE;

	}

	@GetMapping("/update-siteonepreference")
	@RequireHardLogIn
	public String editPreferenceDetails(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
		final ObjectMapper mapper = new ObjectMapper();
		final UserEmailData userData = ((SiteOneCustomerFacade) customerFacade).fetchPreferences();
		String json = "";

		if (null != userData && CollectionUtils.isNotEmpty(userData.getCustomFields()))
		{
			for (final CustomFields custField : userData.getCustomFields())
			{
				if (custField.getName().equalsIgnoreCase("EmailTopic"))
				{
					final List<String> items = new ArrayList<String>(Arrays.asList(custField.getValue().split("\\|")));
					model.addAttribute("emailTopic", items);
				}

				if (custField.getName().equalsIgnoreCase("EmailTopicPreference"))
				{
					final List<String> preference = new ArrayList<String>(Arrays.asList(custField.getValue().split("\\|")));
					try
					{
						json = mapper.writeValueAsString(preference);
					}
					catch (final Exception e)
					{
						LOG.error(e.getMessage(), e);
					}
					model.addAttribute("emailTopicPreference", json);
				}

				if (custField.getName().equalsIgnoreCase("orderInfo"))
				{
					model.addAttribute("orderInfo", custField.getValue());
				}

				if (custField.getName().equalsIgnoreCase("emailPromo"))
				{
					model.addAttribute("emailPromo", custField.getValue());
				}


				if (custField.getName().equalsIgnoreCase("orderPromo"))
				{
					model.addAttribute("orderPromo", custField.getValue());
				}

				if (custField.getName().equalsIgnoreCase("isAdmin"))
				{
					model.addAttribute("isAdmin", custField.getValue());
				}
			}
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PREFERENCE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PREFERENCE_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));

		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@PostMapping("/updatePrefernce")
	@ResponseBody
	public String savePreference(@RequestParam(value = "emailType")
	final String emailType, @RequestParam(value = "emailTopicPreference")
	final String emailTopicPreference, @RequestParam(value = "emailPromo")
	final String emailPromo, final Model model, final RedirectAttributes redirectModel)
	{
		LOG.debug("Method to update");
		final String pNameParameter = emailType;
		final String lNameParameter = emailTopicPreference;

		return ((SiteOneCustomerFacade) customerFacade).saveUserPreference(pNameParameter, lNameParameter, emailPromo);

	}



	@GetMapping("/account-information/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getAccountInformationPage(@PathVariable("unitId")
	final String unitId, final Model model) throws CMSItemNotFoundException // NOSONAR
	{
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		//To Get Breadcrumb
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		//To get Company information and admin
		//model.addAttribute("isAdmin", ((SiteOneB2BUnitService) b2bUnitService).isAdminUser());
		model.addAttribute("companyInfo", b2bUnitFacade.getUnitForUid(sanitizedunitId));
		model.addAttribute("admin", ((SiteOneB2BUnitFacade) b2bUnitFacade).getAdminUserForUnit(sanitizedunitId));

		storeCmsPageInModel(model, getContentPageForLabelOrId("accountinfo"));
		return ControllerConstants.Views.Pages.Account.AccountInformationPage;
	}


	@GetMapping("/account-overview")
	@RequireHardLogIn
	public String getAccountOverviewPage(final Model model, final RedirectAttributes redirectModel,
			@RequestParam(value = "accountId", required = false)
			final String accountId) throws CMSItemNotFoundException // NOSONAR
	{
		if (((SiteOneCustomerFacade) customerFacade).isHavingAccountOverviewForParent())
		{
			populateAccountOverview(accountId, model);
			return ControllerConstants.Views.Pages.Account.AccountOverviewPage;
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"text.company.user.no.accountOverviewForParent", null);

			return REDIRECT_TO_MAIN_VIEW;

		}

	}



	@SuppressWarnings("boxing")
	protected void populateAccountOverview(final String uid, final Model model) throws CMSItemNotFoundException
	{


		storeCmsPageInModel(model, getContentPageForLabelOrId("accountOverviewPage"));

		String unitId = null;
		if (null != uid)
		{
			model.addAttribute("unit", b2bUnitFacade.getUnitForUid(uid));
			unitId = uid;
		}
		else
		{
			final B2BUnitData b2bUnitData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			unitId = b2bUnitData.getUid();
			model.addAttribute("unit", b2bUnitData);

		}

		try
		{
			final SiteOneCustInfoData custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(unitId);
			model.addAttribute("custInfoData", custInfoData);
			model.addAttribute("currency", storeSessionFacade.getCurrentCurrency().getSymbol());
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addErrorMessage(model, FORM_SYSTEM_DOWN_ERROR);
			model.addAttribute("custInfoData", new SiteOneCustInfoData());
			model.addAttribute("currency", "");
		}

		model.addAttribute("currentYear", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

	}

	@GetMapping("/getshiptoinfo/{id}")
	public String getShipToDetail(@PathVariable("id")
	final String id, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (((SiteOneCustomerFacade) customerFacade).isHavingAccountOverviewForShipTos())
		{
			populateAccountOverview(id, model);
			return ControllerConstants.Views.Pages.Account.AccountOverviewPage;
		}
		else
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"text.company.user.no.accountOverviewForShipTos", null);
			return REDIRECT_TO_SHIPTO_VIEW + SiteoneXSSFilterUtil.filter(id);
		}
	}

	@URL
	@GetMapping("/invoices/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn

	public String getInvoiceListingPage(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", defaultValue = "InvoiceDate")
	String sortCode, @PathVariable("unitId")
	final String unitId, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "oSearchParam", required = false)
	final String oSearchParam, @RequestParam(value = "iSearchParam", required = false)
	final String iSearchParam, @RequestParam(value = "pnSearchParam", required = false)
	final String pnSearchParam, @RequestParam(value = "dateFrom", required = false)
	final String dateFrom, @RequestParam(value = "pagesize", required = false)
	final String pageSize, @RequestParam(value = "dateTo", required = false)
	final String dateTo, final Model model, @RequestParam(value = "datarange", required = false)
	final String datarange, @RequestHeader(value = "referer", required = false)
	final String referer, @RequestParam(value = "invoiceShiptos", required = false)
	final String invoiceShiptos, @RequestParam(value = "sortDirection", defaultValue = DESCENDING_ORDER, required = false)
	final String sortDirection, final RedirectAttributes redirectModel) throws CMSItemNotFoundException // NOSONAR
	{
		final SiteoneInvoiceRequestData requestData = new SiteoneInvoiceRequestData();
		final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
		int viewByPageSize = getSiteConfigService().getInt("siteone.invoicedefault.pageSize", 25);
		if (sortCode.equalsIgnoreCase("byDate"))
		{
			sortCode = "InvoiceDate";
		}
		final Calendar f = Calendar.getInstance();
		f.setTime(new Date());
		f.add(Calendar.MONTH, -3);


		String oneTwentyStrDate = null;
		String nowStrDate = null;
		Date datenow = null;
		Date dateoneTwenty = null;

		final LocalDate now = LocalDate.now();
		if (((SiteOneCustomerFacade) customerFacade).isHavingInvoicePermissions())
		{
			final Locale currentLocale = getI18nService().getCurrentLocale();
			final String formatString = getMessageSource().getMessage(TEXT_STORE_DATEFORMAT_KEY, null, DEFAULT_DATEFORMAT,
					currentLocale);
			final DateFormat dateFormat = new SimpleDateFormat(formatString, currentLocale);
			Date dateFromFinal = null, dateToFinal = null;
			String dateFromInvoice = null, dateToInvoice = null;

			try
			{
				datenow = new Date();
				dateoneTwenty = new Date();

				final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				datenow = java.sql.Date.valueOf(now);
				nowStrDate = formatter.format(datenow);
				dateoneTwenty = java.sql.Date.valueOf(now.minusDays(90));
				oneTwentyStrDate = formatter.format(dateoneTwenty);
				if (dateFrom != null && !dateFrom.isEmpty())
				{
					dateFromFinal = dateFormat.parse(dateFrom.trim());
					dateFromInvoice = formatter.format(dateFromFinal);
				}
				else
				{
					dateFromInvoice = oneTwentyStrDate.trim();
				}
				if (dateTo != null && !dateTo.isEmpty())
				{
					f.setTime(formatter.parse(dateTo));
					f.add(Calendar.DATE, 1);
					dateToFinal = dateFormat.parse(formatter.format(f.getTime()).trim());
					dateToInvoice = formatter.format(f.getTime()).trim();

				}
				else
				{
					dateToInvoice = nowStrDate.trim();
				}

			}
			catch (final ParseException e)
			{
				LOG.error(e);
			}
			String orderSearchParam = null;
			String invoiceSearchParam = null;
			String poNumberSearchParam = null;
			
			if (null != oSearchParam)
			{
				orderSearchParam = oSearchParam.trim();
			}
			if (null != iSearchParam)
			{
				invoiceSearchParam = iSearchParam.trim();
			}
			if (null != pnSearchParam)
			{
				poNumberSearchParam = pnSearchParam.trim();
			}
			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder
					.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
			model.addAttribute("unitId", sanitizedunitId);
			if (null != pageSize)
			{
				viewByPageSize = Integer.parseInt(pageSize);
			}
			if (null != dateFromInvoice)
			{
				requestData.setStartDate(dateFromInvoice);
			}
			else
			{
				requestData.setStartDate(oneTwentyStrDate.trim());
			}
			if (null != dateToInvoice)
			{
				requestData.setEndDate(dateToInvoice);
			}
			else
			{
				requestData.setEndDate(nowStrDate.trim());
			}
			requestData.setPage(page + 1);
			requestData.setSort(sortCode);
			requestData.setRows(Integer.valueOf(25));
			if(StringUtils.isNotBlank(sortDirection))
			{
				requestData.setSortDirection((sortDirection.equalsIgnoreCase("asc")? "1": "0"));
			}
			else
			{
				requestData.setSortDirection("0");
			}
			requestData.setInvoiceNumber(invoiceSearchParam);
			requestData.setOrderNumber(orderSearchParam);
			requestData.setPoNumber(poNumberSearchParam);
			final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
			SearchPageData<InvoiceData> searchPageData = null;
			if (invoiceShiptos != null)
			{
				if ("shipToopenPopupNew".equals(invoiceShiptos))
				{

					requestData.setIncludeShipTos(false);
					searchPageData = invoiceFacade.getListOfInvoiceData(requestData, sanitizedunitId);
				}
				else
				{
					final String trimmedInvoiceShiptos = invoiceShiptos.trim();
					if ("All".equalsIgnoreCase(trimmedInvoiceShiptos))
					{
						requestData.setIncludeShipTos(true);
						searchPageData = invoiceFacade.getListOfInvoiceData(requestData, sanitizedunitId);

					}
					else
					{
						final String[] shipToNumber = trimmedInvoiceShiptos.split("\\s+");
						final String shipToUid = shipToNumber[0];
						requestData.setIncludeShipTos(false);
						searchPageData = invoiceFacade.getListOfInvoiceData(requestData, setCustomerNoWithDivision(shipToUid));
					}
				}
			}

			else
			{
				requestData.setIncludeShipTos(true);
				searchPageData = invoiceFacade.getListOfInvoiceData(requestData, sanitizedunitId);
			}
			if (searchPageData != null && searchPageData.getPagination() != null)
			{
				searchPageData.getPagination().setSort(sortCode);
			}
			final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
			final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(parentUnit.getUid());
			//final Set<String> listOfShipTos = new TreeSet<>();
			final Map<String, String> shipTosList = new LinkedHashMap<>();
			final Map<String, String> shipToListUpdated = new LinkedHashMap<>();
			if (invoiceShiptos != null && !invoiceShiptos.isEmpty() && !invoiceShiptos.equalsIgnoreCase("All"))
			{
				final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sanitizedunitId);
				final String shipToNameUnitID = b2BUnitModel.getUid().split("_US")[0] + ' ' + b2BUnitModel.getName();
				shipTosList.put(b2BUnitModel.getUid(), shipToNameUnitID);
			}
			final Collection<String> assignedShipTos = ((SiteOneB2BUnitFacade) b2bUnitFacade).getModifiedAssignedShipTos();
			List<B2BUnitData> currentShipTos;
			final Map<String, String> shipToListData = new LinkedHashMap<>();

			for (final B2BUnitData child : childs)
			{
				String shipToID = null;
				String shipToNameID = null;
				if (child.getUid().contains(SiteoneCoreConstants.INDEX_OF_CA))
				{
					shipToID = child.getUid().substring(0, child.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_CA));
					shipToNameID = child.getUid().substring(0, child.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_CA)) + " "
							+ child.getName();
				}
				else
				{
					shipToID = child.getUid().substring(0, child.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_US));
					shipToNameID = child.getUid().substring(0, child.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_US)) + " "
							+ child.getName();
				}
				shipToListUpdated.put(shipToID, shipToNameID);

				currentShipTos = ((SiteOneB2BUnitFacade) b2bUnitFacade).getModifiedShipTos(child, assignedShipTos);

				if (CollectionUtils.isNotEmpty(currentShipTos))
				{
					for (final B2BUnitData shipToData : currentShipTos)
					{
						String shipToIDNew = null;
						String shipToNameIDNew = null;
						if (shipToData.getUid().contains(SiteoneCoreConstants.INDEX_OF_CA))
						{
							shipToIDNew = shipToData.getUid().substring(0, shipToData.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_CA));
							shipToNameIDNew = shipToData.getUid().substring(0,
									shipToData.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_CA)) + " " + shipToData.getName();

						}
						else
						{
							shipToIDNew = shipToData.getUid().substring(0, shipToData.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_US));
							shipToNameIDNew = shipToData.getUid().substring(0,
									shipToData.getUid().indexOf(SiteoneCoreConstants.INDEX_OF_US)) + " " + shipToData.getName();
						}
						shipToListData.put(shipToIDNew, shipToNameIDNew);
					}
				}

			}
			if (shipToListData.isEmpty())
			{
				shipTosList.putAll(shipToListUpdated);
			}
			else
			{
				shipTosList.putAll(shipToListData);
			}

			final Set<String> listOfShipTos = new LinkedHashSet<String>(shipTosList.values());

			model.addAttribute("oSearchParam", orderSearchParam);
			model.addAttribute("iSearchParam", invoiceSearchParam);
			model.addAttribute("pnSearchParam", poNumberSearchParam);
			model.addAttribute("dateFrom", dateFrom);
			model.addAttribute("dateTo", dateTo);
			model.addAttribute("sort", sortCode);
			model.addAttribute("viewByPageSize", viewByPageSize);
			model.addAttribute("listOfShipTos", listOfShipTos);

			model.addAttribute("invoiceShiptos", invoiceShiptos);

			if (dateFromFinal == null && dateToFinal == null)
			{
				model.addAttribute(NOINVLOICEFLAG, Boolean.FALSE);
			}
			else
			{
				model.addAttribute(NOINVLOICEFLAG, Boolean.TRUE);
			}

			if (searchPageData == null)
			{
				searchPageData = new SearchPageData<>();
				final PaginationData paginationData = createEmptyPagination();
				searchPageData.setResults(Collections.emptyList());
				searchPageData.setPagination(paginationData);
				if (StringUtils.isNotBlank(sortCode) && searchPageData.getPagination() != null)
				{
					searchPageData.getPagination().setSort(sortCode);
				}
				else
				{
					searchPageData.setSorts(Collections.emptyList());
				}
			}

			populateModel(model, searchPageData, showMode);
			storeCmsPageInModel(model, getContentPageForLabelOrId("invoicelistingpage"));
			return ControllerConstants.Views.Pages.Account.InvoiceListingPage;
		}
		else
		{
			final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"text.company.user.no.invoicepermissions", null);
			if (defaultUnit.getUid().equalsIgnoreCase(unitId))
			{
				return REDIRECT_TO_MAIN_VIEW;
			}
			else
			{
				return REDIRECT_TO_SHIPTO_VIEW + unitId;
			}

		}
	}


	@GetMapping("/invoicesCSV/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public void createInvoiceCSV(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", defaultValue = "InvoiceDate")
	final String sortCode, @PathVariable("unitId")
	final String unitId, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "oSearchParam", required = false)
	final String oSearchParam, @RequestParam(value = "iSearchParam", required = false)
	final String iSearchParam, @RequestParam(value = "pnSearchParam", required = false)
	final String pnSearchParam, @RequestParam(value = "dateFrom", required = false)
	final String dateFrom, @RequestParam(value = "dateTo", required = false)
	final String dateTo, @RequestParam(value = "totalresults", required = false)
	final String totalresults, @RequestParam(value = "invoiceShiptos", required = false)
	final String invoiceShiptos, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer, @RequestParam(value = "createcsv", required = false)
	final String createcsv, final RedirectAttributes redirectModel, final HttpServletResponse response,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final SiteoneInvoiceRequestData requestData = new SiteoneInvoiceRequestData();
		final LocalDate now = LocalDate.now();
		String oneTwentyStrDate = null;
		String nowStrDate = null;
		Date datenow = new Date();
		Date dateoneTwenty = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		datenow = java.sql.Date.valueOf(now);
		nowStrDate = formatter.format(datenow);
		dateoneTwenty = java.sql.Date.valueOf(now.minusDays(90));
		oneTwentyStrDate = formatter.format(dateoneTwenty);
		final Locale currentLocale = getI18nService().getCurrentLocale();
		final String formatString = getMessageSource().getMessage(TEXT_STORE_DATEFORMAT_KEY, null, DEFAULT_DATEFORMAT,
				currentLocale);
		final DateFormat dateFormat = new SimpleDateFormat(formatString, currentLocale);
		Date dateFromFinal = null, dateToFinal = null;
		String dateFromInvoice = null, dateToInvoice = null;
		final Calendar f = Calendar.getInstance();
		f.setTime(new Date());
		f.add(Calendar.MONTH, -3);
		try
		{
			if (dateFrom != null && !dateFrom.isEmpty())
			{
				dateFromFinal = dateFormat.parse(dateFrom.trim());
				dateFromInvoice = formatter.format(dateFromFinal);
			}
			else
			{
				dateFromInvoice = oneTwentyStrDate.trim();
			}
			if (dateTo != null && !dateTo.isEmpty())
			{
				dateToFinal = dateFormat.parse(dateTo.trim());
				f.setTime(dateToFinal);
				f.add(Calendar.DATE, 1);
				dateToInvoice = formatter.format(f.getTime()).trim();
			}
			else
			{
				dateToInvoice = nowStrDate.trim();
			}
		}
		catch (final ParseException e)
		{
			LOG.error(e);
		}

		String orderSearchParam = null;
		String invoiceSearchParam = null;
		String poNumberSearchParam = null;

		if (StringUtils.isNotBlank(oSearchParam))
		{
			orderSearchParam = oSearchParam.trim();
		}
		if (StringUtils.isNotBlank(iSearchParam))
		{
			invoiceSearchParam = iSearchParam.trim();
		}
		if (StringUtils.isNotBlank(pnSearchParam))
		{
			poNumberSearchParam = pnSearchParam.trim();
		}

		if (null != dateFromInvoice)
		{
			requestData.setStartDate(dateFromInvoice);
		}
		else
		{
			requestData.setStartDate(oneTwentyStrDate.trim());
		}
		if (null != dateToInvoice)
		{
			requestData.setEndDate(dateToInvoice);
		}
		else
		{
			requestData.setEndDate(nowStrDate.trim());
		}
		requestData.setPage(page + 1);
		requestData.setSort("InvoiceDate");
		requestData.setRows(Integer.parseInt(totalresults));
		requestData.setSortDirection("Descending");
		requestData.setInvoiceNumber(invoiceSearchParam);
		requestData.setOrderNumber(orderSearchParam);
		requestData.setPoNumber(poNumberSearchParam);
		List<InvoiceData> searchPageData = new ArrayList<InvoiceData>();
		SearchPageData<InvoiceData> searchResults = null;
		if (createcsv.equalsIgnoreCase("summary"))
		{
			if (invoiceShiptos != null)
			{
				if (StringUtils.isNotEmpty(invoiceShiptos))
				{
					if ("shipToopenPopupNew".equals(invoiceShiptos))
					{

						requestData.setIncludeShipTos(false);
						searchResults = invoiceFacade.getListOfInvoiceData(requestData, unitId);
						searchPageData = searchResults.getResults();
					}
					else
					{
						final String trimmedInvoiceShiptos = invoiceShiptos.trim();
						if ("All".equalsIgnoreCase(trimmedInvoiceShiptos))
						{
							requestData.setIncludeShipTos(true);
							searchResults = invoiceFacade.getListOfInvoiceData(requestData, unitId);
							searchPageData = searchResults.getResults();

						}
						else
						{
							final String[] shipToNumber = trimmedInvoiceShiptos.split("\\s+");
							final String shipToUid = shipToNumber[0];
							requestData.setIncludeShipTos(false);
							searchResults = invoiceFacade.getListOfInvoiceData(requestData, setCustomerNoWithDivision(shipToUid));
							searchPageData = searchResults.getResults();
						}
					}
				}

				else
				{
					requestData.setIncludeShipTos(true);
					searchResults = invoiceFacade.getListOfInvoiceData(requestData, unitId);
					searchPageData = searchResults.getResults();
				}
			}

			else
			{

				requestData.setIncludeShipTos(true);
				searchResults = invoiceFacade.getListOfInvoiceData(requestData, unitId);
				searchPageData = searchResults.getResults();
			}
		}
		else
		{
			requestData.setIncludeShipTos(true);
			searchPageData = invoiceFacade.getDownloadListOfInvoiceData(requestData, unitId);
		}

		try
		{
			final byte[] csvContent = SiteOneInvoiceCSVUtils.createCSV(searchPageData, createcsv);
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");

			response.setHeader("Content-Disposition", "attachment; filename=invoice_" + unitId + ".csv");
			response.setContentType("application/octet-stream");
			response.setContentLength(csvContent.length);
			response.getOutputStream().write(csvContent);
			response.getOutputStream().flush();

			LOG.info(csvContent);
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access csv", serviceUnavailableException);
		}
		catch (final CsvNotAvailableException csvNotAvailableException)
		{
			LOG.error("Csv is currently not available", csvNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error("Exception while creating CSV : " + e);
			LOG.error(e.getMessage(), e);
		}

	}

	@GetMapping("/invoice/" + UNIT_ID_PATH_VARIABLE_PATTERN + "/"
			+ INVOICE_NUMBER_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getInvoiceForCode(@PathVariable("invoiceNumber")
	final String code, @PathVariable("unitId")
	final String unitId, @RequestParam(value = "orderShipmentActualId", required = false)
	final String orderShipmentActualId, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		try
		{
			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

			final InvoiceData invoiceDetails = invoiceFacade.getInvoiceDetailsForCode(code, unitId, orderShipmentActualId, Boolean.FALSE);

			String paymentType = "";
			String cardNumber = "";
			String cardType = "";
			String termsCode = "";
			if (invoiceDetails.getSiteOnePaymentInfoData() != null)
			{
				paymentType = invoiceDetails.getSiteOnePaymentInfoData().getPaymentType();
				cardNumber = "XX" + invoiceDetails.getSiteOnePaymentInfoData().getCardNumber();
				cardType = invoiceDetails.getSiteOnePaymentInfoData().getApplicationLabel();

				LOG.info("card details>>>>>>>>>>>>>>>>>>>>>>>" + cardNumber + paymentType);
			}
			else if (invoiceDetails.getSiteOnePOAPaymentInfoData() != null)
			{
				paymentType = invoiceDetails.getSiteOnePOAPaymentInfoData().getPaymentType();
				final SiteOneCustInfoData custInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(unitId);
				termsCode = custInfoData.getCustomerCreditInfo().getCreditTermCode();

				LOG.info("POA details>>>>>>>>>>>>>>>>>>>>>>>" + termsCode + paymentType);
			}
			else
			{
				paymentType = "2";
			}



			model.addAttribute("cardNumber", cardNumber);
			model.addAttribute("paymentType", paymentType);
			model.addAttribute("cardType", cardType);
			model.addAttribute("termsCode", termsCode);
			model.addAttribute("invoiceData", invoiceDetails);
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a invoice that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page.not.found", null);
			return REDIRECT_TO_INVOICE_LIST_PAGE;
		}
		catch (final PdfNotAvailableException e)
		{
			LOG.warn("Invoice PDF not available for UID: " + unitId + " and Code: " + code, e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "invoice.error.pdf.not.available",
					null);
			model.addAttribute("cardNumber", "");
			model.addAttribute("paymentType", "");
			model.addAttribute("cardType", "");
			model.addAttribute("termsCode", "");
			model.addAttribute("invoiceData", new InvoiceData());
		}
		catch (final Exception e)
		{
			LOG.error("Unexpected error loading invoice details", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page", null);
			return REDIRECT_TO_INVOICE_LIST_PAGE;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId("invoicedetailspage"));
		return ControllerConstants.Views.Pages.Account.InvoiceDetailsPage;
	}

	@GetMapping("/partner-program")	
	public ModelAndView partnerProgram(final HttpServletRequest request, final RedirectAttributes redirectModel)
	{				
		String partnerPointURL = StringUtils.EMPTY;
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		if (customer != null && customer.getContactEmail() != null && customer.getPartnerProgramPermissions().equals(Boolean.TRUE))
		{
				String oktaEndPoint = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
						+ Config.getString(SiteoneintegrationConstants.NEXT_LEVEL_ENDPOINT, StringUtils.EMPTY)
						+ Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_APPNAME, StringUtils.EMPTY)
						+ Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_GROUPID, StringUtils.EMPTY)+"/"
						+ Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_CLIENTID, StringUtils.EMPTY);
				LOG.error(" Next level API url is " + oktaEndPoint);

				partnerPointURL = Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART1, StringUtils.EMPTY)
						+ getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN) + "&redirectUrl=" + oktaEndPoint ;

				LOG.error(" Next level Redirect url is " + partnerPointURL);
				return new ModelAndView(
						REDIRECT_PREFIX + Config.getString(SiteoneintegrationConstants.OKTA_REDIRECT_URL_PART1, StringUtils.EMPTY)
								+ getSessionService().getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN)
								+ "&redirectUrl=" + oktaEndPoint) ;				
		}
		else
		{		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
				"account.error.partner.point.redeem", null);
		return new ModelAndView(REDIRECT_PREFIX + "/my-account/accountPartnerProgram");
		}
	}

	@GetMapping("/invoicePDF/" + INVOICE_NUMBER_PATH_VARIABLE_PATTERN + "/"
			+ ORDER_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public void createInvoicePDF(@PathVariable("invoiceNumber")
	final String invoiceNumber, @PathVariable("orderShipmentActualId")
	final String orderShipmentActualId, final Model model, final RedirectAttributes redirectModel,
			final HttpServletResponse response, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		try
		{
			final byte[] pdfContent = invoiceFacade.getInvoiceByOrderShipmentActualId(orderShipmentActualId);

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");

			response.setHeader("Content-Disposition", "attachment; filename=invoice_" + invoiceNumber + ".pdf");
			response.setContentType("application/octet-stream");
			response.setContentLength(pdfContent.length);
			response.getOutputStream().write(pdfContent);
			response.getOutputStream().flush();
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access pdf", serviceUnavailableException);
		}
		catch (final PdfNotAvailableException pdfNotAvailableException)
		{
			LOG.error("Pdf is currently not available", pdfNotAvailableException);
		}
		catch (final IOException e)
		{
			LOG.error("Exception while creating PDF : " + e);
			LOG.error(e.getMessage(), e);
		}

	}

	@GetMapping(value = "/downloadNurseryInventoryCSV")
	@RequireHardLogIn
	public void inventoryFeedPDF(@RequestParam(value = "storeId", required = false)
	final String storeId, final HttpServletResponse response)
	{
		try
		{
			String currentsession = storeId;
			if (currentsession == null)
			{
				currentsession = storeSessionFacade.getSessionStore().getStoreId();
			}
			final String inventoryFeedContainer = getConfigurationService().getConfiguration()
					.getString(SiteoneintegrationConstants.NURSERY_INVENTORY_FEED_TARGET_LOCATION);
			final String containerName = configurationService.getConfiguration().getString("azure.outbound.storage.container.name");

			final File file = getBlobDataImportService().readBlobToFile("Nursery_Item_Inventory_" + currentsession + ".xlsx",
					inventoryFeedContainer, containerName);
			response.setHeader("Expires", "0");
			response.setHeader(CACHE_CONTROL, REVALIDATE);
			response.setHeader(PRAGMA, PUBLIC);
			response.setHeader(CONTENT_DISPOSITION, "attachment;filename=Nursery_Item_Inventory_" + currentsession + ".xlsx");
			response.setContentType(APPLICATION_OCTET_STREAM);
			response.setContentLength((int) file.length());
			try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file)))
			{
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
			catch (final FileNotFoundException fileNotFoundException)
			{
				LOG.error("Could not find file", fileNotFoundException);
			}
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error("Not able to access file", serviceUnavailableException);
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage(), e);
		}

	}

	/**
	 * @param blobDataImportService
	 *           the blobDataImportService to set
	 */
	public void setBlobDataImportService(final SiteOneBlobDataImportService blobDataImportService)
	{
		this.blobDataImportService = blobDataImportService;
	}

	/**
	 * @return
	 */
	private SiteOneBlobDataImportService getBlobDataImportService()
	{
		// YTODO Auto-generated method stub
		return blobDataImportService;
	}

	/**
	 * @return the siteOneChangePwdValidator
	 */
	public SiteOneChangePwdValidator getSiteOneChangePwdValidator()
	{
		return siteOneChangePwdValidator;
	}

	/**
	 * @param siteOneChangePwdValidator
	 *           the siteOneChangePwdValidator to set
	 */
	public void setSiteOneChangePwdValidator(final SiteOneChangePwdValidator siteOneChangePwdValidator)
	{
		this.siteOneChangePwdValidator = siteOneChangePwdValidator;
	}

	protected void updateAdddressBookPageTitle(final Model model)
	{
		storeContentPageTitleInModel(model, getSiteOnePageTitleResolver().resolveAddressBookPageTitle());
	}

	@GetMapping("/accountPartnerProgram")
	public String getAccountPartnerProgram(final Model model) throws CMSItemNotFoundException
	{
		SiteOneCustInfoData siteOneCustInfoData = null;
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		try
		{
			final B2BUnitModel defaultUnit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

			final PointOfServiceData homeBranch = ((SiteOneCustomerFacade) customerFacade).getHomeBranch();

			
			if (basesite.getUid().equalsIgnoreCase("siteone-us"))
			{
			siteOneCustInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getLoyaltyInformation(defaultUnit.getUid());
			}
			else
			{
			  siteOneCustInfoData = ((SiteOneB2BUnitFacade) b2bUnitFacade).getAccountInformation(defaultUnit.getUid());
			}

			if (null != siteOneCustInfoData)
			{
				model.addAttribute("rewardPoints", siteOneCustInfoData.getCustomerRewardsPointsInfo());
				double totalValuePoints =0.0;
				if(null!=siteOneCustInfoData.getCustomerRewardsPointsInfo() && null!= siteOneCustInfoData.getCustomerRewardsPointsInfo().getTotalAvailablePoints() && StringUtils.isNotEmpty(siteOneCustInfoData.getCustomerRewardsPointsInfo().getTotalAvailablePoints()))
				{
					double decimalTVA = Double.parseDouble(siteOneCustInfoData.getCustomerRewardsPointsInfo().getTotalAvailablePoints());
					totalValuePoints = decimalTVA * 0.0139;
				}	
				model.addAttribute("totalValuePoints", Double.toString(totalValuePoints));
				if(null!=siteOneCustInfoData.getLoyaltyProgramInfo())
				{
				model.addAttribute("loyaltyPartnerPoints", siteOneCustInfoData.getLoyaltyProgramInfo());
				}
			}
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			LOG.error(serviceUnavailableException);
			GlobalMessages.addErrorMessage(model, PARTNER_POINTS_SERVICE_DOWN_ERROR);
			model.addAttribute("rewardPoints", null);
			model.addAttribute("loyaltyPartnerPoints", null);
		}

		final List<TitleData> titles = userFacade.getTitles();

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getTitleCode() != null)
		{
			model.addAttribute("title", findTitleForCode(titles, customerData.getTitleCode()));
		}

		boolean isPartnerProgramAdminEmail = false;
		if (null != siteOneCustInfoData && null != siteOneCustInfoData.getProfile()
				&& siteOneCustInfoData.getProfile().getAttributes().getPartnerProgramAdminEmail() != null && siteOneCustInfoData
						.getProfile().getAttributes().getPartnerProgramAdminEmail().equalsIgnoreCase(customerData.getUid()))
		{
			isPartnerProgramAdminEmail = true;
		}

		final MyAccountUserInfo myAccountUserInfo = ((SiteOneB2BUnitFacade) b2bUnitFacade).getUserAccountInfo();
		if (basesite.getUid().equalsIgnoreCase("siteone-ca"))
		{
		if(null != siteOneCustInfoData && null != siteOneCustInfoData.getCustomerRewardsPointsInfo()
					&& null != siteOneCustInfoData.getCustomerRewardsPointsInfo().getEnrolledInCurrentYearProgram())
		{
			model.addAttribute(IS_PARTNERPROGRAM_ENROLLED, siteOneCustInfoData.getCustomerRewardsPointsInfo().getEnrolledInCurrentYearProgram());
		}
		}
		else
		{
		model.addAttribute(IS_PARTNERPROGRAM_ENROLLED, myAccountUserInfo.getIsPartnersProgramEnrolled());
		}
		model.addAttribute(IS_PARTNERSPROGRAM_RETAIL, myAccountUserInfo.getIsPartnersProgramRetail());
		model.addAttribute("isAdminUser", myAccountUserInfo.getIsAdminUser());
		model.addAttribute("isPartnerProgramAdminEmail", isPartnerProgramAdminEmail);
		model.addAttribute("isInPartnersProgramPilot", myAccountUserInfo.getIsInPartnersProgramPilot());
		boolean isPartnerProgramUrl = false;
		final Boolean nxtLevelApp = ((SiteOneCustomerFacade) customerFacade).isHavingNxtLevel();
		if(isPartnerProgramAdminEmail)
		{
			if ((nxtLevelApp != null && Boolean.TRUE.equals(nxtLevelApp)))
			{
				isPartnerProgramUrl = true;
			}
			else
			{
				isPartnerProgramUrl = ((SiteOneCustomerFacade) customerFacade).updateOktaCustomerProfile();
			}
		}		
		model.addAttribute("isPartnerProgramUrl",isPartnerProgramUrl);
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
	   getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ACCOUNT_PARTNER_PROGRAM_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ACCOUNT_PARTNER_PROGRAM_PAGE));
		return ControllerConstants.Views.Pages.Account.AccountPartnerProgramPage;
	}

	@GetMapping(value = "/getTalonOneLoyaltyStatus")
	@RequireHardLogIn
	@ResponseBody
	public boolean getCustomerLoyaltyProgramEnrollmentStatus()
	{
		return ((SiteOneB2BUnitFacade) b2bUnitFacade).getLoyaltyProgramInfoStatus();

	}


	@GetMapping("/all-ship-to-popup/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getShipToPagePopup(@PathVariable("unitId")
	final String unitId, @RequestParam(value = "searchParam")
	final String searchParam, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", defaultValue = B2BUnitModel.NAME)
	final String sortCode, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer) throws CMSItemNotFoundException
	{
		final String trimmedSearchParam = searchParam.trim();
		final PageableData pageableData = createPageableData(page, this.getSearchPopUpSize(), sortCode, showMode);
		final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade())
				.getPagedB2BUnits(pageableData, unitId, trimmedSearchParam);
		populateModel(model, searchPageData, showMode);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("action", "manageUnit");
		model.addAttribute("searchParam", trimmedSearchParam);
		model.addAttribute("sortShipToPopup", sortCode);
		storeCmsPageInModel(model, getContentPageForLabelOrId("shipToPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("shipToPage"));
		return ControllerConstants.Views.Pages.Account.ShipToPagePopup;
	}

	@GetMapping("/all-ship-to-popup-invoice/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getShipToPagePopupInvoices(@PathVariable("unitId")
	final String unitId, @RequestParam(value = "searchParam")
	final String searchParam, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "dateFromPopup", required = false)
	final String dateFrom, @RequestParam(value = "dateToPopup", required = false)
	final String dateTo, @RequestParam(value = "sort", defaultValue = B2BUnitModel.NAME)
	final String sortCode, final Model model, @RequestHeader(value = "referer", required = false)
	final String referer) throws CMSItemNotFoundException
	{
		final String trimmedSearchParam = searchParam.trim();
		final PageableData pageableData = createPageableData(page, this.getSearchPopUpSize(), sortCode, showMode);
		final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade())
				.getPagedB2BUnits(pageableData, unitId, trimmedSearchParam);
		populateModel(model, searchPageData, showMode);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("action", "manageUnit");
		model.addAttribute("searchParam", trimmedSearchParam);
		model.addAttribute("sortShipToPopupInvoice", sortCode);
		model.addAttribute("dateFromPopup", dateFrom);
		model.addAttribute("dateToPopup", dateTo);
		storeCmsPageInModel(model, getContentPageForLabelOrId("invoiceShipToPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("invoiceShipToPage"));
		return ControllerConstants.Views.Pages.Account.InvoiceShipToPagePopup;
	}

	protected int getInvoicePageSize(final String pageSize)
	{
		int invoicePage = getSiteConfigService().getInt("siteone.invoicedefault.pageSize", 25);
		if (null != pageSize)
		{
			invoicePage = Integer.parseInt(pageSize);
			if (invoicePage >= 100)
			{
				invoicePage = getSiteConfigService().getInt("siteone.invoicehundred.pageSize", 25);
			}
			else if (invoicePage >= 50)
			{
				invoicePage = getSiteConfigService().getInt("siteone.invoicefifty.pageSize", 25);
			}
			else
			{
				invoicePage = getSiteConfigService().getInt("siteone.invoicedefault.pageSize", 25);
			}
		}
		return invoicePage;
	}

	@GetMapping("/getBoardCardTransportKey")
	public @ResponseBody String getBoardCardTransportKey(final Model model)
	{
		String transportKey = null;

		transportKey = siteOneCayanTransportFacade.getBoardCardTransportKey(false);

		return transportKey;
	}

	@PostMapping("/editEwallet")
	@RequireHardLogIn
	public @ResponseBody String editEwallet1(@RequestParam(value = "vaultToken")
	final String vaultToken, @RequestParam(value = "nickName")
	final String nickName, @RequestParam(value = "expDate")
	final String expDate, @Valid
	final SiteoneEwalletForm ewalletForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException, ResourceAccessException, EwalletNotCreatedOrUpdatedInCayanException,
			EwalletNotCreatedOrUpdatedInUEException, ModelSavingException, ServiceUnavailableException
	{

		final DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-yy");
		final YearMonth yearMonth = YearMonth.parse(expDate, format);

		if (yearMonth.compareTo(YearMonth.now()) < 0)
		{
			return "EXPFAILURE";
		}

		try
		{

			siteOneEwalletFacade.updateEwalletDetails(vaultToken, nickName, expDate); //service  Call

		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			return "FAILURE";
		}
		catch (final EwalletNotFoundException ewalletException)
		{
			LOG.error(EWALLET_NOT_FOUND, ewalletException);
			return "FAILURE";

		}
		catch (final EwalletNotCreatedOrUpdatedInCayanException cayanExp)
		{
			LOG.error("Unable to Update Ewallet details in Cayan", cayanExp);
			return "FAILURE";

		}
		catch (final EwalletNotCreatedOrUpdatedInUEException UEExp)
		{
			LOG.error("Unable to update the Ewallet details in UE", UEExp);
			return "FAILURE";

		}
		catch (final ModelSavingException modelExp)
		{
			LOG.error("Unable to update the Ewallet details in hybris", modelExp);
			return "FAILURE";

		}
		catch (final Exception ex)
		{
			LOG.error("Failed to update Credit Card details", ex);
			if (ex.getMessage().equalsIgnoreCase("Values are same"))
			{
				return "SAME";
			}
			else
			{
				return "FAILURE";
			}
		}


		return "SUCCESS";
	}

	@GetMapping("/add-ewallet")
	@RequireHardLogIn
	public String addEwallet(final CayanBoarcardResponseForm boardCardRespForm, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{

		String unitId = null;
		String userEmail = null;
		if (null != boardCardRespForm
				&& boardCardRespForm.getStatus().equalsIgnoreCase(SiteoneFacadesConstants.CAYAN_STATUS_APPROVED))
		{

			final String nickName = getSessionService().getAttribute(SiteoneFacadesConstants.NICK_NAME);
			final SiteOneEwalletData ewalletData = siteOneEwalletDataUtil.convert(boardCardRespForm, nickName);
			final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();
			final B2BUnitModel b2bUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sessionShipTo.getUid());
			unitId = b2bUnitModel.getUid().toString();
			final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			userEmail = customerModel.getEmail();
			try
			{
				siteOneEwalletFacade.addEwalletDetails(ewalletData);
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with UE to create contact", resourceAccessException);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
			catch (final CardAlreadyPresentInUEException cardAlreadyPresentInUEException)
			{
				LOG.error("Card already present for customer in UE", cardAlreadyPresentInUEException);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.Card.exist", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
			catch (final NickNameAlreadyPresentInUEException nickNameAlreadyPresentInUEException)
			{
				LOG.error("Nick Name already present for customer in UE", nickNameAlreadyPresentInUEException);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.Name.exist", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
			catch (final Exception e)
			{
				LOG.error("Failed to add Credit Card details", e);
				getSessionService().setAttribute("ewalletAddError",
						getMessageSource().getMessage("text.ewallet.add.error", null, getI18nService().getCurrentLocale()));
				return ControllerConstants.Views.Pages.Account.AddEwalletPopup;
			}
		}
		else if (StringUtils.isNotEmpty(boardCardRespForm.getStatus()) && boardCardRespForm.getStatus().equals(CAYAN_USER_CALCEL))
		{
			final B2BUnitData b2bunit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			return REDIRECT_TO_EWALLET_PAGE + "/" + SiteoneXSSFilterUtil.filter(b2bunit.getUid());
		}
		else
		{
			LOG.error("Invalid Boardcard response : " + boardCardRespForm.getStatus());
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
		getSessionService().setAttribute("ewalletAddSuccess", "New Card Added to Ewallet");
		getSessionService().setAttribute("userEmail", userEmail);
		return ControllerConstants.Views.Pages.Account.AddEwalletPopup;

	}

	@PostMapping("/delete-card")
	@RequireHardLogIn
	public @ResponseBody String deleteCard(@RequestParam(value = "vaultToken")
	final String vaultToken, final SiteoneEwalletForm ewalletForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (null != bindingResult && bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			model.addAttribute(ewalletForm);
			return ControllerConstants.Views.Pages.Account.EWalletDetailsPage;
		}
		try
		{
			siteOneEwalletFacade.deleteCardFromEwallet(vaultToken); //service  Call

		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection with UE to create contact", resourceAccessException);
			return "FAILURE";
		}
		catch (final EwalletNotFoundException ewalletException)
		{

			LOG.error(EWALLET_NOT_FOUND, ewalletException);
			return "FAILURE";
		}
		catch (final ModelSavingException modelExp)
		{

			LOG.error("Unable to delete the Ewallet details in hybris", modelExp);
			return "FAILURE";
		}
		catch (final Exception ex)
		{
			LOG.error("Failed to delete Credit Card details", ex);
			return "FAILURE";
		}

		return "SUCCESS";

	}

	@GetMapping("/ewallet/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getEwalletDetails(@PathVariable("unitId")
	final String unitId, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "shiptounit", required = false)
	String shipToUnit, @RequestParam(value = "pagesize", defaultValue = "10")
	final String pageSize, @RequestParam(value = "sort", defaultValue = SiteoneEwalletCreditCardModel.NAMEONCARD)
	final String sortCode, final Model model) throws CMSItemNotFoundException
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final boolean isAdminUser = customerModel.getGroups().stream()
				.anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup"));
		if (isAdminUser)
		{
			final String sanitizedunitId = SiteoneXSSFilterUtil.filter(unitId);
			final String sanitizedsearchParam = SiteoneXSSFilterUtil.filter(searchParam);
			final Map<String, String> listOfShipTos = new LinkedHashMap<>();
			String userEmail = null;
			String[] userUnitSplit = null;
			String userUnitId = null;
			if (shipToUnit != null && !shipToUnit.isEmpty() && !shipToUnit.equalsIgnoreCase("All"))
			{
				final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(shipToUnit);
				listOfShipTos.put(b2BUnitModel.getUid(), b2BUnitModel.getUid().split("_US")[0] + ' ' + b2BUnitModel.getName());
			}
			listOfShipTos.putAll(((SiteOneB2BUserFacade) b2bUserFacade).getListOfShipTos());

			if (null != shipToUnit && shipToUnit.equalsIgnoreCase("shipToopenPopupNew"))
			{
				shipToUnit = sanitizedunitId;
			}
			final String userUnit = (null != shipToUnit) ? shipToUnit : sanitizedunitId;
			final Boolean shipToUnitFlag = (null != shipToUnit && !shipToUnit.equalsIgnoreCase("All")) ? true : false;

			final int viewByPageSize = (null != pageSize) ? Integer.parseInt(pageSize)
					: getSiteConfigService().getInt("siteoneorgaddon.search.pageSize", 10);
			final String trimmedSearchParam = (null != sanitizedsearchParam) ? sanitizedsearchParam.trim() : null;

			// Handle paged search results
			final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
			final SearchPageData<SiteOneEwalletData> searchPageData = siteOneEwalletFacade.getPagedEWalletDataForUnit(pageableData,
					userUnit, trimmedSearchParam, sortCode, shipToUnitFlag);

			userEmail = customerModel.getEmail();

			populateModel(model, searchPageData, showMode);

			storeCmsPageInModel(model, getContentPageForLabelOrId("ewalletdetailspage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("ewalletdetailspage"));
			if (!shipToUnitFlag)
			{
				userUnitSplit = sanitizedunitId.split("_");
				userUnitId = userUnitSplit[0];
			}
			else
			{
				userUnitSplit = userUnit.split("_");
				userUnitId = userUnitSplit[0];
			}
			final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder
					.getBreadcrumbsForAccountDashboardPage(sanitizedunitId);
			if (getSessionService().getAttribute("ewalletAddSuccess") != null)
			{
				model.addAttribute("ewalletAddSuccess", getSessionService().getAttribute("ewalletAddSuccess").toString());
				model.addAttribute("userEmail", getSessionService().getAttribute("userEmail").toString());
				getSessionService().removeAttribute("ewalletAddSuccess");
				getSessionService().removeAttribute("userEmail");
			}
			else if (getSessionService().getAttribute("ewalletAddError") != null)
			{
				model.addAttribute("ewalletAddError", getSessionService().getAttribute("ewalletAddError").toString());
				getSessionService().removeAttribute("ewalletAddError");
			}
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
			model.addAttribute("unitId", sanitizedunitId);
			model.addAttribute("shiptounit", userUnit);
			model.addAttribute("searchParam", trimmedSearchParam);
			model.addAttribute("sort", sortCode);
			model.addAttribute("pagesize", viewByPageSize);
			model.addAttribute("listOfShipTos", listOfShipTos);
			model.addAttribute("userUnitId", userUnitId);
			model.addAttribute("userEmail", userEmail);
			model.addAttribute("totalCount", searchPageData.getPagination().getTotalNumberOfResults());
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
			return ControllerConstants.Views.Pages.Account.EWalletDetailsPage;
		}
		else
		{
			return REDIRECT_PREFIX + "/";
		}
	}


	@GetMapping("/all-ship-to-eWallet-popup/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public String getShipToPagePopupEwallet(@PathVariable("unitId")
	final String unitId, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "shiptounit", required = false)
	final String shipToUnit, @RequestParam(value = "pagesize", defaultValue = "3")
	final String pageSize, @RequestParam(value = "sort", defaultValue = B2BCustomerModel.NAME)
	final String sortCode, final Model model) throws CMSItemNotFoundException
	{
		int viewByPageSize = getSiteConfigService().getInt("siteoneorgaddon.searchPopup.pageSize", 3);
		final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);
		String trimmedSearchParam = null;
		if (null != searchParam)
		{
			trimmedSearchParam = searchParam.trim();
		}
		if (null != pageSize)
		{
			viewByPageSize = Integer.parseInt(pageSize);
		}
		final String userUnit = (null != shipToUnit) ? shipToUnit : unitId;
		final Map<String, String> listOfShipTos = ((SiteOneB2BUserFacade) b2bUserFacade).getListOfShipTos();

		final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade())
				.getPagedB2BDefaultUnits(pageableData, trimmedSearchParam);
		populateModel(model, searchPageData, showMode);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("searchParam", trimmedSearchParam);
		model.addAttribute("sortShipToPopupEwallet", sortCode);
		model.addAttribute("unit", unitId);
		model.addAttribute("shiptounit", userUnit);
		model.addAttribute("sort", sortCode);
		model.addAttribute("pagesize", viewByPageSize);
		model.addAttribute("listOfShipTos", listOfShipTos);
		model.addAttribute("totalCount", searchPageData.getPagination().getTotalNumberOfResults());
		model.addAttribute("searchPageData", searchPageData);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		storeCmsPageInModel(model, getContentPageForLabelOrId("userShipToPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("userShipToPage"));
		return ControllerConstants.Views.Pages.Account.EWalletShipToPage;
	}

	//handle ajax call for assign/revoke credit card
	@PostMapping("/assign-revoke-ewallet")
	@RequireHardLogIn
	public @ResponseBody String updateEwalletDetails(final Model model, final RedirectAttributes redirectModel,
			@RequestParam(value = "listOfCustIds")
			final String custUids, @RequestParam(value = "vaultToken")
			final String vaultToken, @RequestParam(value = "operationType")
			final String operationType) throws CMSItemNotFoundException, Exception
	{
		try
		{
			final List<String> listOfCustIds = Arrays.asList(custUids.split(","));
			siteOneEwalletFacade.updateEwalletToUser(vaultToken, listOfCustIds, operationType);
		}
		catch (final ModelSavingException modelExp)
		{
			LOG.error("Unable to " + operationType + " the Ewallet details in hybris", modelExp);
			GlobalMessages.addErrorMessage(model, "Unable to " + operationType + " Ewallet Card");
			return "FAILURE";
		}
		catch (final ServiceUnavailableException serviceUnavailableException)
		{
			return "FAILURE";
		}
		catch (final EwalletNotCreatedOrUpdatedInUEException UEExp)
		{
			LOG.error("Unable to update the Ewallet details in UE", UEExp);
			return "FAILURE";

		}
		catch (final EwalletNotFoundException ewalletException)
		{
			LOG.error(EWALLET_NOT_FOUND, ewalletException);
			return "FAILURE";

		}
		catch (final Exception ex)
		{
			LOG.error("Failed to update Credit Card details", ex);
			return "FAILURE";
		}
		return "SUCCESS";
	}

	@RequestMapping(value = "/add-ewalletStatus=User_Cancelled")
	@RequireHardLogIn
	public String cancelPayment(final Model model, final RedirectAttributes redirectModel, final HttpServletRequest request)
			throws CMSItemNotFoundException, InvalidCartException, CommerceCartModificationException
	{
		final B2BUnitData b2bunit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		return REDIRECT_TO_EWALLET_PAGE + "/" + SiteoneXSSFilterUtil.filter(b2bunit.getUid());
	}

	@GetMapping("/manageEwallet")
	@RequireHardLogIn
	public String getAssignRevokePopupEwallet(@RequestParam(value = "unitId")
	final String unitId, @RequestParam(value = "vaultToken")
	final String vaultToken, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchParam", required = false)
	final String searchParam, @RequestParam(value = "shiptounit", required = false)
	final String shipToUnit, @RequestParam(value = "pagesize", defaultValue = "3")
	final String pageSize, @RequestParam(value = "sort", defaultValue = B2BCustomerModel.NAME)
	final String sortCode, final Model model) throws CMSItemNotFoundException
	{
		try
		{

			model.addAttribute("userEmail", customerFacade.getCurrentCustomer().getUid());

		}
		catch (final Exception e)
		{
			LOG.error("Failed to get manage ewallet details", e);
			return ControllerConstants.Views.Pages.Account.AssignRevokeEwalletPopup;
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId("assignRevokeEwalletPopup"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("assignRevokeEwalletPopup"));
		return ControllerConstants.Views.Pages.Account.AssignRevokeEwalletPopup;
	}


	@GetMapping("/assignRevoke-users")
	@RequireHardLogIn
	public String getAssignRevokeUsers(@RequestParam("unitId")
	final String unitId, @RequestParam("action")
	final String action, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "pagesize", defaultValue = "5")
	final String pageSize, @RequestParam(value = "vaultToken", required = true)
	final String vaultToken, @RequestParam(value = "sort", defaultValue = SiteoneEwalletCreditCardModel.NAMEONCARD)
	final String sortCode, final Model model) throws CMSItemNotFoundException
	{

		final int viewByPageSize = 5;

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);

		SearchPageData<CustomerData> searchPageData = null;

		searchPageData = siteOneEwalletFacade.getPagedAssignRevokeUsers(pageableData, unitId, sortCode, action, vaultToken);

		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		final String userEmail = customerModel.getEmail();
		final List<Breadcrumb> breadcrumbs = siteOneAccountBreadcrumbBuilder.getBreadcrumbsForAccountDashboardPage(unitId);
		populateModel(model, searchPageData, showMode);

		storeCmsPageInModel(model, getContentPageForLabelOrId("assignRevokeEwalletPopup"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("assignRevokeEwalletPopup"));

		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute("searchParam", "");
		model.addAttribute("sort", sortCode);
		model.addAttribute("pagesize", viewByPageSize);
		model.addAttribute("userUnitId", unitId);
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("totalCount", searchPageData.getPagination().getTotalNumberOfResults());
		model.addAttribute("searchPageData", searchPageData);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.Account.AssignRevokeEwalletPopup;
	}

	@RequireHardLogIn
	@ResponseBody
	@PostMapping("/enrollCustomerInTalonOne")
	public boolean enrollCustomerInTalonOne()
	{
		return ((SiteOneCustomerFacade) customerFacade).enrollCustomerInTalonOne();
	}

	private String getEncryptionData(final String stringToEncrypt, final String secret)
	{
		final byte[] decodedKey = Base64.getDecoder().decode(secret);
		try
		{
			final Cipher cipher = Cipher.getInstance("AES");
			final SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);
			final byte[] cipherText = cipher.doFinal(stringToEncrypt.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(cipherText);
		}
		catch (final Exception e)
		{
			LOG.error("Error while encrypting: " + e.toString());
		}
		return null;

	}

	public String setCustomerNoWithDivision(final String customerNumber)
	{
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
			return customerNumber.concat("_US");
		}
		else
		{
			return customerNumber.concat("_CA");
		}
	}

	@GetMapping("/all-shipto-popup-more/" + UNIT_ID_PATH_VARIABLE_PATTERN)
	@RequireHardLogIn
	public @ResponseBody List<B2BUnitData> getShipToPopupMore(@PathVariable("unitId") final String unitId,
	  @RequestParam(value = "page", defaultValue = "0") final int page,
	  @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
	  @RequestParam(value = "searchParam", required = false) final String searchParam,
	  @RequestParam(value = "sort", defaultValue = B2BCustomerModel.NAME) final String sortCode)
			throws CMSItemNotFoundException
	{
		final String trimmedSearchParam = searchParam.trim();
		final PageableData pageableData = createPageableData(page, this.getSearchPopUpSize(), sortCode, showMode);
		final SearchPageData<B2BUnitData> searchPageData = ((SiteOneCustomerFacade) getCustomerFacade())
				.getPagedB2BUnits(pageableData, unitId, trimmedSearchParam);

		List<B2BUnitData> shipToMoreData = searchPageData.getResults();
		return shipToMoreData;
	}

}
