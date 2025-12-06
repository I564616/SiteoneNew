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

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.regioncache.region.impl.DefaultCacheRegion;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.emailsubscription.EmailSubscriptionFacade;
import com.siteone.facades.emailsubscriptions.data.EmailSubscriptionsData;
import com.siteone.facades.navigation.cache.SiteOneNavigationCacheKey;
import com.siteone.facades.navigation.cache.SiteOneNavigationCacheValueLoader;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneEmailSignUpForm;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * Controller for home page
 */
@Controller
@RequestMapping("/")
public class HomePageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(HomePageController.class);

	@Resource
	private EmailSubscriptionFacade emailSubscriptionFacade;

	public static final String REDIRECT_PREFIX = "redirect:";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	private static final String TEXT_EMAIL_SIGNUP = "text.email.signup";
	private static final String SEO_INDEX_ENV = "storefront.seo.index.env";
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	private static final String MEDIA_CODE = "{code:.*}";
	public static final String ANONYMOUS = "Anonymous";
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	private static final String CATALOG_ID = "siteoneProductCatalog";
	private static final String CATALOG_VERSION_ONLINE = "Online";
	private static final String LOGIN_PARTNER_PROGRAM_PAGEID = "pilotPartnersProgramPage";
	private static final String PARTNER_PERKS_PAGEID = "partnerPerksPage";
	private static final String PILOT_PARTNER_PROGRAM_TERMS_PAGEID = "pilotPartnersProgramTermsPage";
	private static final String ANONYMOUS_PARTNER_PROGRAM_PAGEID = "partnerProgram";
	private static final String ANONYMOUS_FLAG = "isAnonymous";
	private static final String IS_PARTNERPROGRAM_ENROLLED = "isPartnerProgramEnrolled";
	private static final String IS_PARTNERSPROGRAM_RETAIL = "isPartnersProgramRetail";
	private static final String REDIRECT_PARTNER_PERKS = "/PartnerPerks";

	@Resource(name = "siteOneNavigationCacheRegion")
	private DefaultCacheRegion siteOneNavigationCacheRegion;

	@Resource(name = "siteOneNavigationCacheValueLoader")
	private SiteOneNavigationCacheValueLoader siteOneNavigationCacheValueLoader;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "customerFacade")
	private SiteOneCustomerFacade siteOneCustomerFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "categoryService")
	private SiteOneCategoryService siteOneCategoryService;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource
	CatalogVersionService catalogVersionService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@SuppressWarnings("boxing")
	@GetMapping
	public String home(@RequestParam(value = "logout", defaultValue = "false")
	final boolean logout, final Model model, final RedirectAttributes redirectModel, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		boolean quoteFeature = false;
		siteOneCustomerFacade.getClientInformation(request);
		if (logout)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER, "account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}

		final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
		if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		}
		else
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		}
		model.addAttribute("isReturning", "returning");
		final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
		model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);
		request.getSession().setAttribute("user_okta_id", sessionService.getAttribute("user_okta_id"));

		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));

		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();


		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
		{
			quoteFeature = true;
		}

		//quotes toggle feature switch
		model.addAttribute("quotesFeatureSwitch", quoteFeature);

		return getViewForPage(model);
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	@GetMapping("/emailsignup")
	public String getEmailSignUpPage(final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute("siteOneEmailSignUpForm"))
		{
			final SiteOneEmailSignUpForm siteOneEmailSignUpForm = new SiteOneEmailSignUpForm();
			model.addAttribute("siteOneEmailSignUpForm", siteOneEmailSignUpForm);
		}
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("emailSignUpPage"));
		storeCmsPageInModel(model, getContentPageForLabelOrId("emailSignUpPage"));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_EMAIL_SIGNUP));
		return ControllerConstants.Views.Pages.Promotion.EmailSignUpPage;
	}

	@PostMapping("/emailsignup")
	public String emailSignUpSuccess(final SiteOneEmailSignUpForm siteOneEmailSignUpForm, final Model model)
			throws CMSItemNotFoundException
	{
		final EmailSubscriptionsData emailSubscriptionsData = new EmailSubscriptionsData();
		emailSubscriptionsData.setEmail(siteOneEmailSignUpForm.getEmail());
		emailSubscriptionsData.setPostalcode(siteOneEmailSignUpForm.getPostalcode());
		emailSubscriptionsData.setUserType(siteOneEmailSignUpForm.getUserType());
		try
		{
			emailSubscriptionFacade.subscribeEmail(emailSubscriptionsData);
		}
		catch (final ResourceAccessException | IOException e)
		{
			LOG.error(e);
			GlobalMessages.addErrorMessage(model, "Please try after sometime.");
			storeCmsPageInModel(model, getContentPageForLabelOrId("emailSignUpPage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("emailSignUpPage"));
			model.addAttribute("siteOneEmailSignUpForm", siteOneEmailSignUpForm);
			return ControllerConstants.Views.Pages.Promotion.EmailSignUpPage;
		}
		catch (final RestClientException e)
		{
			LOG.error(e);
			GlobalMessages.addErrorMessage(model, "Please try after sometime.");
			storeCmsPageInModel(model, getContentPageForLabelOrId("emailSignUpPage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("emailSignUpPage"));
			model.addAttribute("siteOneEmailSignUpForm", siteOneEmailSignUpForm);
			return ControllerConstants.Views.Pages.Promotion.EmailSignUpPage;
		}
		catch (final ModelSavingException mse)
		{
			LOG.error(mse);
			GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, "form.contact.already.exist.ue", new Object[]
			{ emailSubscriptionsData.getEmail() });
			storeCmsPageInModel(model, getContentPageForLabelOrId("emailSignUpPage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("emailSignUpPage"));
			model.addAttribute("siteOneEmailSignUpForm", siteOneEmailSignUpForm);
			return ControllerConstants.Views.Pages.Promotion.EmailSignUpPage;
		}

		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("emailSignUpSuccessPage"));
		storeCmsPageInModel(model, getContentPageForLabelOrId("emailSignUpSuccessPage"));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_EMAIL_SIGNUP));
		return ControllerConstants.Views.Pages.Promotion.EmailSignUpSuccessPage;
	}

	@GetMapping(value = "emailsubscription", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String subscribeEmail(@RequestHeader(value = "referer", required = false)
	final String referer, @RequestParam(value = "email", required = false)
	final String email, @RequestParam(value = "zipCode", required = false)
	final String zipCode, @RequestParam(value = "role", required = false)
	final String role) throws CMSItemNotFoundException
	{
		final EmailSubscriptionsData emailSubscriptionsData = new EmailSubscriptionsData();
		emailSubscriptionsData.setEmail(email);
		emailSubscriptionsData.setPostalcode(zipCode);
		emailSubscriptionsData.setUserType(role);
		try
		{
			emailSubscriptionFacade.subscribeEmail(emailSubscriptionsData);
			return SUCCESS;
		}
		catch (final ResourceAccessException | IOException e)
		{
			LOG.error(e);
			return ERROR;
		}
		catch (final RestClientException e)
		{
			LOG.error(e);
			return ERROR;
		}
		catch (final ModelSavingException e)
		{
			LOG.error(e);
			return email;
		}

	}


	@GetMapping("/navigation")
	public String navigation(final Model model)
	{

		final SiteOneNavigationCacheKey cacheKey = new SiteOneNavigationCacheKey(
				Config.getString("navigation.categories.root", null));
		final List<CategoryData> navigationCategories = (List<CategoryData>) getSiteOneNavigationCacheRegion()
				.getWithLoader(cacheKey, getSiteOneNavigationCacheValueLoader());

		model.addAttribute("categories", navigationCategories);

		return ControllerConstants.Views.Fragments.Navigation.NavigationTab;
	}

	@GetMapping("/change-ship-to")
	public @ResponseBody String changeShipTO(@RequestParam(value = "unitId")
	final String unitId)
	{
		final B2BUnitData unitData = b2bUnitFacade.getUnitForUid(unitId);
		sessionService.setAttribute("changeShiptoUnit", unitId);
		storeSessionFacade.setSessionShipTo(unitData);
		return unitData.getUid();
	}

	@GetMapping("/consentForm")
	public @ResponseBody String updateConsent(@RequestParam(value = "consent")
	final String consent)
	{
		sessionService.setAttribute("consentForm", consent);
		return "success";
	}


	@GetMapping("/mailmedia/" + MEDIA_CODE)
	public String getMailMediaForCode(@PathVariable("code")
	final String code)
	{
		try
		{
			final MediaModel media = mediaService.getMedia(code);
			if (null != media)
			{
				return REDIRECT_PREFIX + media.getURL();
			}
			else
			{
				return REDIRECT_PREFIX + "/";
			}
		}
		catch (final UnknownIdentifierException exception)
		{
			LOG.error(exception);
			return REDIRECT_PREFIX + "/";
		}
	}

	@GetMapping(value = "/recommendedProducts", produces = "application/json")
	public String recommendedProducts(final HttpServletRequest request, @RequestParam(value = "placementPage", required = true)
	final String placementPage, @RequestParam(value = "categoryId", required = false)
	final String categoryId, @RequestParam(value = "productId", required = false)
	final String productId, @RequestParam(value = "orderId", required = false)
	final String orderId, @RequestParam(value = "searchTerm", required = false)
	final String searchTerm, final Model model)
	{
		final String sessionId = request.getSession().getId();
		final String pagePosition = "";
		String pimCategoryId = "";
		String productCode = productId;
		final StringBuilder productCodes = new StringBuilder();
		final StringBuilder productPrices = new StringBuilder();
		final StringBuilder productQtys = new StringBuilder();
		if (!StringUtils.isEmpty(categoryId))
		{
			try
			{
				final CatalogVersionModel catalogVersionModel = getSessionService().getAttribute("currentCatalogVersion");
				final CategoryModel category = siteOneCategoryService.getCategoryForCode(catalogVersionModel,
						categoryId.toUpperCase());
				pimCategoryId = category.getPimCategoryId();
			}
			catch (final Exception ex)
			{
				LOG.error(ex.getMessage());
			}
		}
		if (!StringUtils.isEmpty(orderId))
		{
			final OrderData orderDetails;
			orderDetails = orderFacade.getOrderDetailsForCode(orderId);
			final List<OrderEntryData> orderEntries = orderDetails.getEntries();
			boolean firstEntry = true;

			for (final OrderEntryData orderEntryData : orderEntries)
			{
				if (firstEntry)
				{
					firstEntry = false;
				}
				else
				{
					productCodes.append("|");
					productPrices.append("|");
					productQtys.append("|");
				}
				final ProductData productData = orderEntryData.getProduct();
				productCodes.append(productData.getCode());
				Optional.ofNullable(orderEntryData.getBasePrice())
						.map(PriceData::getValue)
						.or(() -> Optional.ofNullable(orderEntryData.getListPrice()))
						.ifPresent(productPrices::append);
				productQtys.append(orderEntryData.getQuantity());
			}
			productCode = productCodes.toString();
		}

		final Object[] recommProduct = siteOneProductFacade.getRecommendedProductsToDisplay(placementPage, pimCategoryId,
				productCode, sessionId, pagePosition, orderId, productPrices.toString(), null, productQtys.toString(), searchTerm);

		List<ProductData> productList = new ArrayList<>();

		if (recommProduct[1] != null)
		{
			productList = (List<ProductData>) recommProduct[1];
		}
		final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
		model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);
		model.addAttribute("recommendedProducts", productList);
		model.addAttribute("recommendationTitle", recommProduct[2]);
		model.addAttribute("recommendationTitle", recommProduct[2]);
		return returnSpecificPage(placementPage, recommProduct, model);
	}

	private String returnSpecificPage(final String placementPage, final Object[] recommProduct, final Model model)
	{
		if ("itemPage".equalsIgnoreCase(placementPage) || "homePage".equalsIgnoreCase(placementPage))
		{
			List<ProductData> productListrr2 = new ArrayList<>();
			if (recommProduct[3] != null)
			{
				productListrr2 = (List<ProductData>) recommProduct[3];
			}
			model.addAttribute("recommendedProductsrr2", productListrr2);
			model.addAttribute("recommendationTitlerr2", recommProduct[4]);
			if ("itemPage".equalsIgnoreCase(placementPage))
			{
				return ControllerConstants.Views.Fragments.Product.ProductRecommendationSectionPDP;
			}
			if ("homePage".equalsIgnoreCase(placementPage))
			{
				return ControllerConstants.Views.Fragments.Product.ProductRecommendationSectionHomePage;
			}
		}
		if("PersonalPage".equalsIgnoreCase(placementPage))
		{
			List<ProductData> recentProdPagerr2 = new ArrayList<>();
			if (recommProduct[3] != null)
			{
				recentProdPagerr2 = (List<ProductData>) recommProduct[3];
			}
			model.addAttribute("recentProductsrr2", recentProdPagerr2);			
			return ControllerConstants.Views.Fragments.Account.AccDashRecentProductSection;
		}
		if("BuyAgainPage".equalsIgnoreCase(placementPage))
		{
			List<ProductData> buyAgainPagerr1 = new ArrayList<>();
			if (recommProduct[1] != null)
			{
				buyAgainPagerr1 = (List<ProductData>) recommProduct[1];
			}
			model.addAttribute("buyAgainProductsrr1", buyAgainPagerr1);			
			return ControllerConstants.Views.Fragments.Account.AccDashBuyItAgainProductSection;
		}
		if("RecommProductPage".equalsIgnoreCase(placementPage))
		{
			List<ProductData> recomProdPagerr3 = new ArrayList<>();
			if (recommProduct[5] != null)
			{
				recomProdPagerr3 = (List<ProductData>) recommProduct[5];
			}
			model.addAttribute("recomProductsrr3", recomProdPagerr3);			
			return ControllerConstants.Views.Fragments.Account.AccDashRecommendProductSection;
		}
		return ControllerConstants.Views.Fragments.Product.ProductRecommendationSectionPLP;
	}

	@GetMapping(value = "/partnersprogram")
	public String getPartnersProgramPage(final Model model) throws CMSItemNotFoundException
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();


		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser())
				&& customerData != null)
		{
			model.addAttribute(ANONYMOUS_FLAG, false);
			if (customerData.getPartnerProgramPermissions() != null)
			{
				model.addAttribute(IS_PARTNERPROGRAM_ENROLLED, customerData.getPartnerProgramPermissions());
			}
			return getPartnersProgramCMSPage(model);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ANONYMOUS_PARTNER_PROGRAM_PAGEID));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ANONYMOUS_PARTNER_PROGRAM_PAGEID));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@GetMapping(value = "/partnerprogramterms")
	public String getPartnersProgramPageTerms(final Model model) throws CMSItemNotFoundException
	{

		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbHead = new Breadcrumb("/",
				getMessageSource().getMessage("breadcrumb.contractorServices", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbParnter = new Breadcrumb("/partnerprogramterms", getMessageSource()
				.getMessage("breadcrumb.partnersProgramTermsAndConditions", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumbHead);
		breadcrumbs.add(breadcrumbParnter);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(PILOT_PARTNER_PROGRAM_TERMS_PAGEID));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PILOT_PARTNER_PROGRAM_TERMS_PAGEID));
		return ControllerConstants.Views.Pages.PartnersProgram.PARTNERSPROGRAMTERMSPAGE;
	}



	protected String getPartnersProgramCMSPage(final Model model) throws CMSItemNotFoundException
	{

		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final Breadcrumb breadcrumbHead = new Breadcrumb("/",
				getMessageSource().getMessage("breadcrumb.contractorServices", null, getI18nService().getCurrentLocale()), null);
		final Breadcrumb breadcrumbParnter = new Breadcrumb("/partnersprogram",
				getMessageSource().getMessage("breadcrumb.partnersProgram", null, getI18nService().getCurrentLocale()), null);

		breadcrumbs.add(breadcrumbHead);
		breadcrumbs.add(breadcrumbParnter);
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(LOGIN_PARTNER_PROGRAM_PAGEID));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LOGIN_PARTNER_PROGRAM_PAGEID));
		return ControllerConstants.Views.Pages.PartnersProgram.PARTNERSPROGRAMPAGE;
	}

	/*
	 * @GetMapping(value = "/PartnerPerks") public String getPartnerPerksPage(final Model model) throws
	 * CMSItemNotFoundException { final List<Breadcrumb> breadcrumbs = new ArrayList<>();
	 *
	 * final Breadcrumb breadcrumbHead = new Breadcrumb("/",
	 * getMessageSource().getMessage("breadcrumb.contractorServices", null, getI18nService().getCurrentLocale()), null);
	 * final Breadcrumb breadcrumbParnter = new Breadcrumb("/PartnerPerks",
	 * getMessageSource().getMessage("breadcrumb.partnerPerks", null, getI18nService().getCurrentLocale()), null);
	 *
	 * breadcrumbs.add(breadcrumbHead); breadcrumbs.add(breadcrumbParnter); final boolean isAnonymousUser =
	 * userService.getCurrentUser() != null && userService.isAnonymousUser(userService.getCurrentUser());
	 * model.addAttribute(ANONYMOUS_FLAG, isAnonymousUser); final MyAccountUserInfo myAccountUserInfo =
	 * ((SiteOneB2BUnitFacade) b2bUnitFacade).getUserAccountInfo(); if (!isAnonymousUser) {
	 * model.addAttribute(IS_PARTNERPROGRAM_ENROLLED, (null != customerFacade.getCurrentCustomer() &&
	 * customerFacade.getCurrentCustomer().getPartnerProgramPermissions()));
	 * model.addAttribute(IS_PARTNERSPROGRAM_RETAIL, myAccountUserInfo.getIsPartnersProgramRetail());
	 *
	 * } model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs); storeCmsPageInModel(model,
	 * getContentPageForLabelOrId(PARTNER_PERKS_PAGEID)); setUpMetaDataForContentPage(model,
	 * getContentPageForLabelOrId(PARTNER_PERKS_PAGEID)); return
	 * ControllerConstants.Views.Pages.PartnersProgram.PARTNERPERKSPAGE; }
	 */
	@GetMapping(value = "/redeempoints")
	public void getReedemPage(final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		final String newUrl = request.getContextPath() + REDIRECT_PARTNER_PERKS;
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", newUrl);

	}

	@GetMapping(value = "/earnpoints")
	public void getEarnPage(final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		final String newUrl = request.getContextPath() + REDIRECT_PARTNER_PERKS;
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", newUrl);

	}

	@GetMapping(value = "/businesssolutions")
	public void getBusinessPage(final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		final String newUrl = request.getContextPath() + REDIRECT_PARTNER_PERKS;
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", newUrl);

	}

	@ResponseBody
	@PostMapping("/getPendoEvent")
	public String getPendoEvent(@RequestParam(value = "type", required = false)
	final String type, @RequestParam(value = "event", required = false)
	final String event, @RequestParam(value = "userAgent", required = false)
	final String userAgent, @RequestParam(value = "url", required = false)
	final String url, @RequestParam(value = "emailId", required = false)
	final String emailId, @RequestParam(value = "timestamp", required = false)
	final String timestamp, @RequestParam(value = "ip", required = false)
	final String ip, @RequestParam(value = "title", required = false)
	final String title, @RequestParam(value = "phone", required = false)
	final String phone, @RequestParam(value = "kountSessionId", required = false)
	final String kountSessionId, @RequestParam(value = "orderNumber", required = false)
	final String orderNumber, @RequestParam(value = "orderAmount", required = false)
	final String orderAmount,@RequestParam(value = "listCode", required = false)
	final String listCode,@RequestParam(value = "quoteNumber", required = false)
	final String quoteNumber, final HttpServletRequest request, final HttpServletResponse response) throws Exception

	{
		final String eventResponse = siteOneCustomerFacade.generatePendoEvent(type, event, userAgent, url,
				emailId, timestamp, ip, title, phone, kountSessionId, orderNumber, orderAmount,quoteNumber,listCode);
		return eventResponse;
	}

	/**
	 * @return the emailSubscriptionFacade
	 */
	public EmailSubscriptionFacade getEmailSubscriptionFacade()
	{
		return emailSubscriptionFacade;
	}

	/**
	 * @param emailSubscriptionFacade
	 *           the emailSubscriptionFacade to set
	 */
	public void setEmailSubscriptionFacade(final EmailSubscriptionFacade emailSubscriptionFacade)
	{
		this.emailSubscriptionFacade = emailSubscriptionFacade;
	}

	/**
	 * @return the siteOneNavigationCacheRegion
	 */
	public DefaultCacheRegion getSiteOneNavigationCacheRegion()
	{
		return siteOneNavigationCacheRegion;
	}

	/**
	 * @param siteOneNavigationCacheRegion
	 *           the siteOneNavigationCacheRegion to set
	 */
	public void setSiteOneNavigationCacheRegion(final DefaultCacheRegion siteOneNavigationCacheRegion)
	{
		this.siteOneNavigationCacheRegion = siteOneNavigationCacheRegion;
	}

	/**
	 * @return the siteOneNavigationCacheValueLoader
	 */
	public SiteOneNavigationCacheValueLoader getSiteOneNavigationCacheValueLoader()
	{
		return siteOneNavigationCacheValueLoader;
	}

	/**
	 * @param siteOneNavigationCacheValueLoader
	 *           the siteOneNavigationCacheValueLoader to set
	 */
	public void setSiteOneNavigationCacheValueLoader(final SiteOneNavigationCacheValueLoader siteOneNavigationCacheValueLoader)
	{
		this.siteOneNavigationCacheValueLoader = siteOneNavigationCacheValueLoader;
	}


	@Override
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}
}

