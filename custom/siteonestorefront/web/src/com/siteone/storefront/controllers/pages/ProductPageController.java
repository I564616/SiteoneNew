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

import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.FutureStockForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.ReviewForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ReviewValidator;
import de.hybris.platform.acceleratorstorefrontcommons.util.MetaSanitizerUtil;
import de.hybris.platform.acceleratorstorefrontcommons.util.XSSFilterUtil;
import de.hybris.platform.acceleratorstorefrontcommons.variants.VariantSortStrategy;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commercefacades.futurestock.FutureStockFacade;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.data.ConfigurationInfoData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ClassificationData;
import de.hybris.platform.commercefacades.product.data.FeatureData;
import de.hybris.platform.commercefacades.product.data.FeatureValueData;
import de.hybris.platform.commercefacades.product.data.FutureStockData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.personalizationservices.service.CxService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.commercefacades.product.data.ProductHighlights;


import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Maps;
import com.sap.security.core.server.csi.util.URLDecoder;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facades.customer.price.SiteOneCspResponse;
import com.siteone.facades.customer.price.SiteOnePdpPriceDataUom;
import com.siteone.facades.customer.price.SiteOneCspWithSaleResponse;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.breadcrumbs.impl.SiteOneProductBreadCrumbBuilder;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.variants.VariantMatrixSortStrategy;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.translation.SiteOneTranslationService;

/**
 * Controller for product details page
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/p")
public class ProductPageController extends AbstractPageController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ProductPageController.class);

	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains one or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String PRODUCT_CODE_PATH_VARIABLE_PATTERN = "/{productCode:.*}";
	private static final String REVIEWS_PATH_VARIABLE_PATTERN = "{numberOfReviews:.*}";

	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";
	private static final String STOCK_SERVICE_UNAVAILABLE = "basket.page.viewFuture.unavailable";
	private static final String NOT_MULTISKU_ITEM_ERROR = "basket.page.viewFuture.not.multisku";
	private static final String OFFLINE_PRODUCT_PAGE_ID = "offlineProductPage";
	private static final String ALL_PRODUCTS = "analytics.fullpath.category.page.all.products";
	private static final String PATHING_CHANNEL = "analytics.pathing.channel.category.page";
	private static final String MEDIA_ALTTEXT_ATTRIBUTE = "https://www.youtube.com/embed/";
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	private static final List<String> KNOWN_BOTS = Arrays.asList(
		    "googlebot", "bingbot", "gptbot", "facebookexternalhit",
		    "twitterbot", "linkedinbot", "ahrefsbot", "semrushbot", "applebot"
		);


	@Resource(name = "productDataUrlResolver")
	private UrlResolver<ProductData> productDataUrlResolver;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "siteOneProductBreadcrumbBuilder")
	private SiteOneProductBreadCrumbBuilder siteOneProductBreadcrumbBuilder;

	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;

	@Resource(name = "variantSortStrategy")
	private VariantSortStrategy variantSortStrategy;

	@Resource(name = "variantMatrixSortStrategy")
	private VariantMatrixSortStrategy variantMatrixSortStrategy;

	@Resource(name = "reviewValidator")
	private ReviewValidator reviewValidator;

	@Resource(name = "futureStockFacade")
	private FutureStockFacade futureStockFacade;


	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "cxService")
	private CxService cxService;
	
	@Resource(name = "defaultSiteOneStoreFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;
	
	@Resource(name = "b2bUnitService")
	private SiteOneB2BUnitService b2bUnitService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	@Resource(name = "defaultSiteOneTranslationService")
	private SiteOneTranslationService translationService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	
	private static final String SEO_INDEX_ENV = "storefront.seo.index.env";

	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN)
	public String productDetail(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException, UnsupportedEncodingException
	{
		if (model.asMap() != null)
		{
			model.addAttribute("searchText", model.asMap().get("searchText"));
		}
		//SE-9371 code for getting external and internal natural referrers
		final String referrer = request.getHeader("referer");
		if (null != referrer)
		{
			if (!referrer.contains(Config.getString("website.siteone.https", StringUtils.EMPTY)))
			{
				model.addAttribute("method", "naturalReferrer");
				model.addAttribute("methodMetaData", referrer);
			}
		}
		String analyticsParam = null;
		if (request.getParameterMap().containsKey("isrc"))
		{
			analyticsParam = "isrc=" + request.getParameter("isrc");
		}
		if (request.getParameterMap().containsKey("icis"))
		{
			analyticsParam = "icid=" + request.getParameter("icid");
		}
		if (request.getParameterMap().containsKey("cid"))
		{
			analyticsParam = "cid=" + request.getParameter("cid");
		}
		//SE-9371 ends
		//SE-20010
		if (request.getParameter("store") != null)
		{
			final String storeId = request.getParameter("store");
			final PointOfServiceModel pointOfServiceModel = siteOneStoreFinderService.getStoreForId(storeId);
			if(pointOfServiceModel!=null) {
				Cookie cookie = WebUtils.getCookie(request, "csc");
				if(getUserFacade().isAnonymousUser())
		   	{
		   		if (null != cookie)
		   		{
		   			cookie.setMaxAge(0);
		   			cookie.setSecure(true);
		   			response.addCookie(cookie);
		    					
		   			final Cookie newCookie = new Cookie("csc", storeId);
		   			newCookie.setMaxAge(10 * 24 * 60 * 60);
		   			newCookie.setPath("/");
		   			newCookie.setSecure(true);
		   			response.addCookie(newCookie);
		   		}
		   		else
		   		{
		   			cookie = new Cookie("csc", storeId);
		   			cookie.setMaxAge(10 * 24 * 60 * 60);
		   			cookie.setPath("/");
		   			cookie.setSecure(true);
		   			response.addCookie(cookie);
		   		}
		   	}
		   	((SiteOneCustomerFacade) customerFacade).makeMyStore(storeId);
		   	if (!getUserFacade().isAnonymousUser() && null != cookie)
		   	{
		   		cookie.setValue(null);
		   		cookie.setMaxAge(0);
		   		cookie.setSecure(true);
		   		response.addCookie(cookie);
		   	}
		   	cxService.calculateAndLoadPersonalizationInSession(userService.getCurrentUser());
			}
		}
		//
		
		final List<ProductOption> extraOptions = Arrays.asList(ProductOption.DESCRIPTION, ProductOption.KEYWORDS,
				ProductOption.CATEGORIES);

		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, extraOptions);
		
		//SE-1814 starts
		if (CollectionUtils.isNotEmpty(productData.getImages()))
		{
			for (final ImageData image : productData.getImages())
			{
				if (null != image.getAltText() && image.getAltText().contains(MEDIA_ALTTEXT_ATTRIBUTE))
				{
					model.addAttribute("videoId", image.getAltText().replace(MEDIA_ALTTEXT_ATTRIBUTE, ""));
				}
			}
		}
		//SE-1814 ends
		if (null != productData.getIsProductOffline() && productData.getIsProductOffline())
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(OFFLINE_PRODUCT_PAGE_ID));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(OFFLINE_PRODUCT_PAGE_ID));
			return ControllerConstants.Views.Pages.Account.OfflineProductPage;
		}
		else
		{
			final String categoryName = productData.getCategories().iterator().next().getName();
			final String redirection = checkRequestUrl(request, response, productDataUrlResolver.resolve(productData));
			if (StringUtils.isNotEmpty(redirection))
			{
				model.asMap().clear();
				return redirection;
			}

			final List<ProductOption> varOptions = Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.CATEGORIES,
					ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL, ProductOption.VARIANT_MATRIX_BASE,
					ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.KEYWORDS);

			final ProductData productVarData = productFacade.getProductForCodeAndOptions(productCode, varOptions);

			if (productVarData.getMultidimensional()
					&& (productVarData.getVariantOptions() == null || productVarData.getVariantOptions().isEmpty())
					&& productVarData.getBaseProduct() != null)
			{
				final ProductData baseProductData = productFacade.getProductForCodeAndOptions(productData.getBaseProduct(),
						extraOptions);
				return handleAccessVariantProduct(request, baseProductData, analyticsParam);
			}

			updatePageTitle(productCode, model);

			final Cookie usernameCookie = WebUtils.getCookie(request, "j_username");
			String uid = null;
			if ((usernameCookie != null && usernameCookie.getValue() != null))
			{
				uid = URLDecoder.decode(usernameCookie != null ? usernameCookie.getValue() : null, "UTF-8");
				uid = uid.trim().toLowerCase();
				userService.setCurrentUser(userService.getUserForUID(uid));
				getSessionService().setAttribute("softLogin", true);
			}

			if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
			{

				final boolean isAssembly = false;
				final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);

				//final List<Wishlist2Model> allWishlist = siteoneSavedListFacade.getAllwishlist(userService.getCurrentUser());
				String wishlistName = null;
				if (CollectionUtils.isNotEmpty(allWishlist) && allWishlist.size() == 1)
				{
					wishlistName = allWishlist.get(0).getCode();

				}

				if (CollectionUtils.isEmpty(allWishlist))
				{
					model.addAttribute("createWishList", "true");
				}


				model.addAttribute("wishlistName", wishlistName);
				model.addAttribute("allWishlist", allWishlist);
				model.addAttribute("loggedUser", "true");
			}
		   boolean pdpEnabled=true;

			if (productData.getVariantCount().intValue() == 1)
			{
				final List<ProductOption> firstVariantOptions = Arrays.asList(ProductOption.VARIANT_FIRST_VARIANT,
						ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA,
						ProductOption.KEYWORDS);
				populateProductDetailForDisplay(productCode, model, request, firstVariantOptions,pdpEnabled);

			}
			else
			{
				final List<ProductOption> otherOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE,
						ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.KEYWORDS);
				populateProductDetailForDisplay(productCode, model, request, otherOptions,pdpEnabled);
			}
			

			final LoginForm loginForm = new LoginForm();
			model.addAttribute(loginForm);
			final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
			if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
			{
				model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
			}
			else
			{
				model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
			}

			model.addAttribute(new ReviewForm());
			model.addAttribute("pageType", PageType.PRODUCT.name());
			model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));
			model.addAttribute("categoryData", categoryName);
			final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
			Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
			model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);
			
			boolean quoteFeature = false;
			final B2BUnitModel unit = b2bUnitService.getDefaultUnitForCurrentCustomer();
			if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
					|| (null != unit && null != unit.getUid()
							&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
			{
				quoteFeature = true;
			}
			model.addAttribute("quotesFeatureSwitch", quoteFeature);
			
			final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productData.getKeywords());
			final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productData.getDescription());
			setUpMetaData(model, metaKeywords, metaDescription);
		}
		return getViewForPage(model);
	}


	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/orderForm")
	public String productOrderForm(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		final List<ProductOption> extraOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE,
				ProductOption.VARIANT_MATRIX_PRICE, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.VARIANT_MATRIX_STOCK,
				ProductOption.URL);

		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, extraOptions);
		updatePageTitle(productCode, model);

		populateProductDetailForDisplay(productCode, model, request, extraOptions,false);

		if (!model.containsAttribute(WebConstants.MULTI_DIMENSIONAL_PRODUCT))
		{
			return REDIRECT_PREFIX + productDataUrlResolver.resolve(productData);
		}

		return ControllerConstants.Views.Pages.Product.OrderForm;
	}

	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/zoomImages")
	public String showZoomImages(@PathVariable("productCode")
	final String productCode, @RequestParam(value = "galleryPosition", required = false)
	final String galleryPosition, final Model model)
	{
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode,
				Collections.singleton(ProductOption.GALLERY));
		final List<Map<String, ImageData>> images = getGalleryImages(productData);
		populateProductData(productData, model);
		if (galleryPosition != null)
		{
			try
			{
				model.addAttribute("zoomImageUrl", images.get(Integer.parseInt(galleryPosition)).get("zoom").getUrl());
			}
			catch (final IndexOutOfBoundsException | NumberFormatException ex)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug(ex);
				}
				model.addAttribute("zoomImageUrl", "");
			}
		}
		return ControllerConstants.Views.Fragments.Product.ZoomImagesPopup;
	}

	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/quickView")
	public String showQuickView(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode,
				Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION,
						ProductOption.CATEGORIES, ProductOption.PROMOTIONS, ProductOption.STOCK, ProductOption.REVIEW,
						ProductOption.VARIANT_FULL, ProductOption.DELIVERY_MODE_AVAILABILITY, ProductOption.URL, ProductOption.IMAGES,
						ProductOption.CUSTOMER_PRICE, ProductOption.AVAILABILITY_MESSAGE, ProductOption.PROMOTIONS));

		sortVariantOptionData(productData);
		populateProductData(productData, model);
		getRequestContextData(request).setProduct(productModel);

		return ControllerConstants.Views.Fragments.Product.QuickViewPopup;
	}

	@GetMapping("/getUOMPrice")
	public @ResponseBody ProductData productUOM(@RequestParam(value = "productCode", required = false)
	final String productCode, @RequestParam(value = "inventoryMultiplier", required = false)
	final String inventoryMultiplier)
	{
		final ProductData productData = siteOneProductFacade.getProductByUOM(productCode,
				Float.valueOf(inventoryMultiplier).floatValue(), null);
		return productData;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/review", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String postReview(@PathVariable
	final String productCode, final ReviewForm form, final BindingResult result, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectAttrs) throws CMSItemNotFoundException
	{
		getReviewValidator().validate(form, result);

		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, null);
		if (result.hasErrors())
		{
			updatePageTitle(productCode, model);
			GlobalMessages.addErrorMessage(model, "review.general.error");
			model.addAttribute("showReviewForm", Boolean.TRUE);
			populateProductDetailForDisplay(productCode, model, request, Collections.emptyList(),false);
			storeCmsPageInModel(model, getPageForProduct(productCode));
			return getViewForPage(model);
		}

		final ReviewData review = new ReviewData();
		review.setHeadline(XSSFilterUtil.filter(form.getHeadline()));
		review.setComment(XSSFilterUtil.filter(form.getComment()));
		review.setRating(form.getRating());
		review.setAlias(XSSFilterUtil.filter(form.getAlias()));
		productFacade.postReview(productCode, review);
		GlobalMessages.addFlashMessage(redirectAttrs, GlobalMessages.CONF_MESSAGES_HOLDER, "review.confirmation.thank.you.title");

		return REDIRECT_PREFIX + productDataUrlResolver.resolve(productData);
	}

	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/reviewhtml/"
			+ REVIEWS_PATH_VARIABLE_PATTERN)
	public String reviewHtml(@PathVariable("productCode")
	final String productCode, @PathVariable("numberOfReviews")
	final String numberOfReviews, final Model model, final HttpServletRequest request)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final List<ReviewData> reviews;
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode,
				Arrays.asList(ProductOption.BASIC, ProductOption.REVIEW));

		if ("all".equals(numberOfReviews))
		{
			reviews = productFacade.getReviews(productCode);
		}
		else
		{
			final int reviewCount = Math.min(Integer.parseInt(numberOfReviews),
					productData.getNumberOfReviews() == null ? 0 : productData.getNumberOfReviews().intValue());
			reviews = productFacade.getReviews(productCode, Integer.valueOf(reviewCount));
		}

		getRequestContextData(request).setProduct(productModel);
		model.addAttribute("reviews", reviews);
		model.addAttribute("reviewsTotal", productData.getNumberOfReviews());
		model.addAttribute(new ReviewForm());

		return ControllerConstants.Views.Fragments.Product.ReviewsTab;
	}

	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/writeReview")
	public String writeReview(@PathVariable
	final String productCode, final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(new ReviewForm());
		setUpReviewPage(model, productCode);
		return ControllerConstants.Views.Pages.Product.WriteReview;
	}

	protected void setUpReviewPage(final Model model, final String productCode) throws CMSItemNotFoundException
	{
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, null);
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productData.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productData.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		storeCmsPageInModel(model, getPageForProduct(productCode));
		model.addAttribute("product", productFacade.getProductForCodeAndOptions(productCode, Arrays.asList(ProductOption.BASIC)));
		updatePageTitle(productCode, model);
	}

	@PostMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/writeReview")
	public String writeReview(@PathVariable
	final String productCode, final ReviewForm form, final BindingResult result, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectAttrs) throws CMSItemNotFoundException
	{
		getReviewValidator().validate(form, result);

		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, null);

		if (result.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "review.general.error");
			populateProductDetailForDisplay(productCode, model, request, Collections.emptyList(),false);
			setUpReviewPage(model, productCode);
			return ControllerConstants.Views.Pages.Product.WriteReview;
		}

		final ReviewData review = new ReviewData();
		review.setHeadline(XSSFilterUtil.filter(form.getHeadline()));
		review.setComment(XSSFilterUtil.filter(form.getComment()));
		review.setRating(form.getRating());
		review.setAlias(XSSFilterUtil.filter(form.getAlias()));
		productFacade.postReview(productCode, review);
		GlobalMessages.addFlashMessage(redirectAttrs, GlobalMessages.CONF_MESSAGES_HOLDER, "review.confirmation.thank.you.title");

		return REDIRECT_PREFIX + productDataUrlResolver.resolve(productData);
	}

	@GetMapping(PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/futureStock")
	public String productFutureStock(@PathVariable("productCode")
	final String productCode, final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		final boolean futureStockEnabled = Config.getBoolean(FUTURE_STOCK_ENABLED, false);
		if (futureStockEnabled)
		{
			final List<FutureStockData> futureStockList = futureStockFacade.getFutureAvailability(productCode);
			if (futureStockList == null)
			{
				GlobalMessages.addErrorMessage(model, STOCK_SERVICE_UNAVAILABLE);
			}
			else if (futureStockList.isEmpty())
			{
				GlobalMessages.addInfoMessage(model, "product.product.details.future.nostock");
			}

			populateProductDetailForDisplay(productCode, model, request, Collections.emptyList(),false);
			model.addAttribute("futureStocks", futureStockList);

			return ControllerConstants.Views.Fragments.Product.FutureStockPopup;
		}
		else
		{
			return ControllerConstants.Views.Pages.Error.ErrorNotFoundPage;
		}
	}

	@ResponseBody
	@PostMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/grid/skusFutureStock", produces = MediaType.APPLICATION_JSON_VALUE)
	public final Map<String, Object> productSkusFutureStock(final FutureStockForm form, final Model model)
	{
		final String productCode = form.getProductCode();
		final List<String> skus = form.getSkus();
		final boolean futureStockEnabled = Config.getBoolean(FUTURE_STOCK_ENABLED, false);

		Map<String, Object> result = new HashMap<>();
		if (futureStockEnabled && CollectionUtils.isNotEmpty(skus) && StringUtils.isNotBlank(productCode))
		{
			final Map<String, List<FutureStockData>> futureStockData = futureStockFacade
					.getFutureAvailabilityForSelectedVariants(productCode, skus);

			if (futureStockData == null)
			{
				// future availability service is down, we show this to the user
				result = Maps.newHashMap();
				final String errorMessage = getMessageSource().getMessage(NOT_MULTISKU_ITEM_ERROR, null,
						getI18nService().getCurrentLocale());
				result.put(NOT_MULTISKU_ITEM_ERROR, errorMessage);
			}
			else
			{
				for (final Map.Entry<String, List<FutureStockData>> entry : futureStockData.entrySet())
				{
					result.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return result;
	}

	@ExceptionHandler(UnknownIdentifierException.class)
	public String handleUnknownIdentifierException(final UnknownIdentifierException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
	}

	protected void updatePageTitle(final String productCode, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveProductPageTitle(productCode));
	}

	protected void populateProductDetailForDisplay(final String productCode, final Model model, final HttpServletRequest request,
			final List<ProductOption> extraOptions, final boolean pdpEnabled) throws CMSItemNotFoundException
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		
		List<ProductHighlights> highlights =siteOneProductFacade.getHighLights(productModel);
		model.addAttribute("highlights", highlights);
		
		getRequestContextData(request).setProduct(productModel);

		final List<ProductOption> options = new ArrayList<>(Arrays.asList(ProductOption.BASIC, ProductOption.URL,
				ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.GALLERY,
				ProductOption.CATEGORIES, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE, ProductOption.PROMOTIONS,
				ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL, ProductOption.DATA_SHEET,
				ProductOption.AVAILABILITY_MESSAGE));

		options.addAll(extraOptions);

		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, options);
		if (pdpEnabled && shouldTranslateContent() 
				&& translationService.isTranslationEnabled() && !isCrawlBot(request)) 
		{
			final Collection<ClassificationData> classifications = productData.getClassifications();
		    translationService.translateProductFieldsAndFeatures(productData, classifications, "es");
		}
		
		if(CollectionUtils.isEmpty(productData.getVariantOptions()) && BooleanUtils.isTrue(productData.getOutOfStockImage()))
		{
			List<ProductData> productList = new ArrayList<>();
			productList.add(productData);
			siteOneProductFacade.updateSalesInfoBackorderForProduct(productList);			
		}

		sortVariantOptionData(productData);
		if (productData.getMultidimensional())
		{
			sortVariantMatrixData(productData);
		}

		if (productData.getVariantOptions() != null && !productData.getVariantOptions().isEmpty())
		{
			if (!userService.isAnonymousUser(userService.getCurrentUser()))
			{
				siteOneProductFacade.setCustomerPriceForVariants(productData,null);
			}
			if (null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				if(StringUtils.isNotEmpty(storeSessionFacade.getSessionStore().getCustomerRetailId()))
				{
				siteOneProductFacade.setRetailPriceForVariants(productData,storeSessionFacade.getSessionStore().getCustomerRetailId());
				}
			}
			siteOneProductFacade.populatVariantProductsForDisplay(productData, model);
			productData.getVariantOptions().sort(Comparator.comparing(VariantOptionData::getStockLevel,Comparator.nullsLast(Comparator.naturalOrder())).reversed());
		}

		if (productData.getIsRegulateditem())

		{
			
			 List<RegionData> regionDataList = new ArrayList<RegionData>();
			if (baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
			{
			 regionDataList = siteOneRegionFacade.getRegionsForCountryCode("US");
			}
			else {
				 regionDataList = siteOneRegionFacade.getRegionsForCountryCode("CA");
			}

			if (CollectionUtils.isNotEmpty(productData.getRegulatoryStates()))
			{
				final List<RegionData> regulatoryStatesList = regionDataList.stream().filter(e -> (productData.getRegulatoryStates()
						.stream().filter(d -> d.equalsIgnoreCase(e.getIsocodeShort())).count()) < 1).collect(Collectors.toList());
				final List<String> regulatoryStates = regulatoryStatesList.stream().map(e -> e.getIsocodeShort())
						.collect(Collectors.toList());
				Collections.sort(regulatoryStates);
				model.addAttribute("regulatoryStates", regulatoryStates);
			}
			else
			{
				final List<String> regulatoryStates = regionDataList.stream().map(e -> e.getIsocodeShort())
						.collect(Collectors.toList());
				Collections.sort(regulatoryStates);
				model.addAttribute("regulatoryStates", regulatoryStates);
			}
		}

		if (CollectionUtils.isEmpty(productData.getVariantOptions()))
		{
			List<InventoryUOMData> inventoryUOMListData = null;
			if (productData.getSellableUoms() != null)
			{
				inventoryUOMListData = productData.getSellableUoms();
			}
			final List<InventoryUOMData> inventoryUOMListnew = new ArrayList();

			if (CollectionUtils.isNotEmpty(inventoryUOMListData))
			{

				for (final InventoryUOMData inventoryUOMData1 : inventoryUOMListData)
				{

					if (!inventoryUOMData1.getHideUOMOnline() && productData.getHideUom() != null && productData.getHideUom())
					{
						final ProductData productDataUom = siteOneProductFacade.getProductByUOM(productCode,
								Float.valueOf(inventoryUOMData1.getInventoryMultiplier()), inventoryUOMData1.getInventoryUOMID());
						if (productDataUom.getPrice() != null)
						{
							inventoryUOMData1.setNewUomPrice(productDataUom.getPrice().getValue().floatValue());
							if (productData.getPrice() != null)
							{
								productData.getPrice().setValue(productDataUom.getPrice().getValue());
							}
							else
							{
								final PriceData priceData = new PriceData();
								priceData.setValue(productDataUom.getPrice().getValue());
								productData.setPrice(priceData);
							}
							productData.getPrice().setFormattedValue("$".concat(productDataUom.getPrice().getValue().toString()));
						}
						if (productDataUom.getCustomerPrice() != null)
						{
							productData.getCustomerPrice()
									.setFormattedValue("$".concat(productDataUom.getCustomerPrice().getValue().toString()));
							productData.getCustomerPrice().setValue(productDataUom.getCustomerPrice().getValue());

						}
						if (productDataUom.getUnitPrice() != null)
						{
							inventoryUOMData1.setUnitPrice(productDataUom.getUnitPrice());
						}
					}

					if(inventoryUOMListData.size()>1)
					{
   					final ProductData productDataUom = siteOneProductFacade.getProductByUOM(productCode,
   							inventoryUOMData1.getInventoryMultiplier().floatValue(), inventoryUOMData1.getInventoryUOMID());
   					if (productDataUom.getPrice() != null)
                  {
                      inventoryUOMData1.setPrice(productDataUom.getPrice());
                  }
                  if (productDataUom.getCustomerPrice() != null)
                  {
                      inventoryUOMData1.setCustomerPrice(productDataUom.getCustomerPrice());
                  }
   					if (productDataUom.getUnitPrice() != null)
   					{
   						inventoryUOMData1.setUnitPrice(productDataUom.getUnitPrice());
   					}
					}
					if(productData.getStock()!=null)
					{
					inventoryUOMData1.setStockQuantity(productData.getStock().getStockLevel());
					}
					inventoryUOMListnew.add(inventoryUOMData1);
				}
				if ((productData.getHideUom() != null && productData.getHideUom())||inventoryUOMListData.size()>1)
				{
					productData.getSellableUoms().clear();
					productData.setSellableUoms(inventoryUOMListnew);
				}
				if (CollectionUtils.isNotEmpty(inventoryUOMListnew))
				{
					siteOneProductFacade.getProductByUOMPriceRange(productData);
				}
			}
		}
		else
		{
			siteOneProductFacade.getVariantProductMultiplier(productData);
		}

		if (productData.getVariantOptions() != null && !productData.getVariantOptions().isEmpty())
		{
			getSessionService().setAttribute("sessionVariantOptions", productData.getVariantOptions());
		}


		// parcel shipping
		if (null != storeSessionFacade.getSessionStore() && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
				storeSessionFacade.getSessionStore().getStoreId()) && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && null == storeSessionFacade.getSessionStore().getHubStores())
		{
			productData.setIsShippable(Boolean.FALSE);
		}
		if (null != storeSessionFacade.getSessionStore() && !siteOneFeatureSwitchCacheService
				.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
		{
			siteOneProductFacade.updateParcelShippingForProduct(productData,
					storeSessionFacade.getSessionStore() != null ? storeSessionFacade.getSessionStore().getStoreId() : null);
		}
		storeCmsPageInModel(model, getPageForProduct(productCode));
		populateProductData(productData, model);
		displayConfigurableAttribute(productData, model);
		final List<Breadcrumb> breadcrumbs = siteOneProductBreadcrumbBuilder.getBreadcrumbs(productCode);
		getFullPagePath(productData, model);

		if (null != (getSessionService().getAttribute("allowedCategories")))
		{
			final String cats[] = StringUtils.split((String) getSessionService().getAttribute("allowedCategories"), ',');
			//String partQuery= StringUtils.EMPTY;
			boolean isAllowedCategory = false;
			for (int index = 0; index < cats.length; index++)
			{
				if (breadcrumbs.get(1).getCategoryCode().toUpperCase().startsWith(cats[index].toUpperCase()))
				{
					isAllowedCategory = true;
				}
			}

			if (!isAllowedCategory)
			{
				LOG.error("The category " + breadcrumbs.get(1).getCategoryCode() + " is not allowed");
				throw new RuntimeException("The category " + breadcrumbs.get(1).getCategoryCode() + " not allowed");
			}

		}

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

		if (CollectionUtils.isNotEmpty(productData.getVariantMatrix()))
		{
			model.addAttribute(WebConstants.MULTI_DIMENSIONAL_PRODUCT,
					Boolean.valueOf(CollectionUtils.isNotEmpty(productData.getVariantMatrix())));
		}
		
		if (productData.getVariantOptions() != null && !productData.getVariantOptions().isEmpty())
		{
			getSessionService().setAttribute("sessionProductData", productData);
		}
	}
	
	

	protected void getFullPagePath(final ProductData productData, final Model model)
	{
		final StringBuilder fullPagePath = new StringBuilder();

		fullPagePath.append(getMessageSource().getMessage(PATHING_CHANNEL, null, getI18nService().getCurrentLocale()) + ": ");
		if (CollectionUtils.isNotEmpty(siteOneProductBreadcrumbBuilder.getFullPagePath(productData.getCode(), null)))
		{
			for (final String categoryName : siteOneProductBreadcrumbBuilder.getFullPagePath(productData.getCode(), null))
			{
				if (null != categoryName)
				{
					fullPagePath.append(categoryName + ": ");
				}
			}
		}
		if (null != productData.getName())
		{
			fullPagePath.append(StringUtils.substring(productData.getName(), 0, 60).toLowerCase());
		}
		model.addAttribute("fullPagePath", fullPagePath.toString());
		model.addAttribute("pathingChannel",
				getMessageSource().getMessage(PATHING_CHANNEL, null, getI18nService().getCurrentLocale()));
	}

	protected void populateProductData(final ProductData productData, final Model model)
	{
		model.addAttribute("galleryImages", getGalleryImages(productData));
		model.addAttribute("product", productData);
		model.addAttribute("sessionVariantOpts", getSessionService().getAttribute("sessionVariantOptions"));
		if (productData.getConfigurable())
		{
			final List<ConfigurationInfoData> configurations = productFacade.getConfiguratorSettingsForCode(productData.getCode());
			if (CollectionUtils.isNotEmpty(configurations))
			{
				model.addAttribute("configuratorType", configurations.get(0).getConfiguratorType());
			}
		}
	}

	protected void sortVariantOptionData(final ProductData productData)
	{
		if (CollectionUtils.isNotEmpty(productData.getBaseOptions()))
		{
			for (final BaseOptionData baseOptionData : productData.getBaseOptions())
			{
				if (CollectionUtils.isNotEmpty(baseOptionData.getOptions()))
				{
					Collections.sort(baseOptionData.getOptions(), variantSortStrategy);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(productData.getVariantOptions()))
		{
			Collections.sort(productData.getVariantOptions(), variantSortStrategy);
		}
	}

	protected void sortVariantMatrixData(final ProductData productData)
	{
		final int index = 0;
		int categoryCount = 0;
		List<VariantMatrixElementData> theMatrix = null;
		VariantMatrixElementData theParentMatrix = null;
		String variantCategoryName = null;
		boolean unitsAttribute = false;

		categoryCount = getProductCategoryCount(productData);

		for (int i = 1; i <= categoryCount; i++)
		{
			if (i == 1)
			{
				theMatrix = productData.getVariantMatrix();
			}
			else
			{
				if (CollectionUtils.isNotEmpty(theMatrix) && CollectionUtils.isNotEmpty(theMatrix.get(index).getElements()))
				{
					theParentMatrix = theMatrix.get(index);
					theMatrix = theMatrix.get(index).getElements();
				}
				else
				{
					break;
				}

			}
			if (CollectionUtils.isNotEmpty(theMatrix))
			{
				variantCategoryName = theMatrix.get(index).getParentVariantCategory().getName();
				unitsAttribute = isUnitsAttribute(variantCategoryName);
				if (unitsAttribute)
				{

					final List<VariantMatrixElementData> orderedList = groupAndSortUnitsAttribute(theMatrix);
					if (i == 1)
					{
						productData.setVariantMatrix(orderedList);
					}
					else
					{
						theParentMatrix.setElements(orderedList);
					}
				}
			}
		}

	}

	protected List<Map<String, ImageData>> getGalleryImages(final ProductData productData)
	{
		final List<Map<String, ImageData>> galleryImages = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(productData.getImages()))
		{
			final List<ImageData> images = new ArrayList<>();
			for (final ImageData image : productData.getImages())
			{
				if (ImageDataType.GALLERY.equals(image.getImageType()))
				{
					images.add(image);
				}
			}
			Collections.sort(images, new Comparator<ImageData>()
			{
				@Override
				public int compare(final ImageData image1, final ImageData image2)
				{
					return image1.getGalleryIndex().compareTo(image2.getGalleryIndex());
				}
			});

			if (CollectionUtils.isNotEmpty(images))
			{
				addFormatsToGalleryImages(galleryImages, images);
			}
		}
		return galleryImages;
	}

	protected void addFormatsToGalleryImages(final List<Map<String, ImageData>> galleryImages, final List<ImageData> images)
	{
		int currentIndex = images.get(0).getGalleryIndex().intValue();
		Map<String, ImageData> formats = new HashMap<String, ImageData>();
		for (final ImageData image : images)
		{
			if (currentIndex != image.getGalleryIndex().intValue())
			{
				galleryImages.add(formats);
				formats = new HashMap<>();
				currentIndex = image.getGalleryIndex().intValue();
			}
			formats.put(image.getFormat(), image);
		}
		if (!formats.isEmpty())
		{
			galleryImages.add(formats);
		}
	}

	protected ReviewValidator getReviewValidator()
	{
		return reviewValidator;
	}

	protected AbstractPageModel getPageForProduct(final String productCode) throws CMSItemNotFoundException
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		return cmsPageService.getPageForProduct(productModel);
	}

	/*
	 * SE-17731
	 *
	 * @ResponseBody
	 *
	 * @RequestMapping(value = "/trackRetailCSPPrice", method = RequestMethod.POST) public void
	 * trackRetailCSPPrice(@RequestParam("itemNumber") final String itemNumber, @RequestParam("customerUnit") final
	 * String customerUnit, @RequestParam("emailAddress") final String emailAddress, @RequestParam("retailPrice") final
	 * String retailPrice, @RequestParam("cspPrice") final String cspPrice, @RequestParam("branchId") final String
	 * branchId) { siteOneProductFacade.trackRetailCSPPrice(itemNumber, customerUnit, emailAddress, retailPrice,
	 * cspPrice, branchId); }
	 */

	@PostMapping("/customerprice")
	public @ResponseBody SiteOneCspResponse getPriceForCustomer(@RequestParam(value = "productCode")
	final String productCode, @RequestParam(value = "quantity")
	final Integer quantity)
	{
		final SiteOneCspResponse siteOneCspResponse = siteOneProductFacade.getPriceForCustomer(productCode, quantity,
				storeSessionFacade.getSessionStore().getStoreId(),null);
		if (null != siteOneCspResponse)
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			LOG.info("ProductPageController : productCode - " + productCode + " CSP Response - " + siteOneCspResponse.isIsSuccess()
					+ " for Store - " + storeSessionFacade.getSessionStore().getStoreId() + " and customer - "
					+ b2bCustomer.getEmail());
		}
		return siteOneCspResponse;
	}
	
	@PostMapping("/branchRetailPrice")
	public @ResponseBody SiteOneCspResponse getRetailPriceForBranch(@RequestParam(value = "productCode")
	final String productCode, @RequestParam(value = "quantity")
	final Integer quantity)
	{
		String retailBranchId=null;
		if (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			retailBranchId=storeSessionFacade.getSessionStore().getCustomerRetailId();
		}
		final SiteOneCspResponse siteOneCspResponse = siteOneProductFacade.getPriceForCustomer(productCode, quantity,
				storeSessionFacade.getSessionStore().getStoreId(),retailBranchId);
		return siteOneCspResponse;
	}
	
	@GetMapping(value = "/customerpriceforuom")
	public @ResponseBody SiteOnePdpPriceDataUom getCustomerPriceForUom(@RequestParam(value = "productCode", required = true)
	final String productCode , @RequestParam(value = "quantity", required = true)
	final Integer quantity, @RequestParam(value = "inventoryUomId", required = false)
	final String inventoryUomId)
	{
		List<ProductOption> productOptions = Arrays.asList(ProductOption.BASIC,ProductOption.PRICE);		
		return siteOneProductFacade.getCustomerPriceforUom(productCode, quantity, inventoryUomId, productOptions);
	}
	@GetMapping("/variantCustomerprice")
	public @ResponseBody SiteOneCspResponse getVariantPriceForCustomer(@RequestParam(value = "productCode")
	final String productCode)
	{
		final SiteOneCspResponse siteOneCspResponse = siteOneProductFacade.getVariantCSPForCustomer(productCode);
		if (null != siteOneCspResponse)
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			LOG.info("ProductPageController : productCode - " + productCode + " CSP Response - " + siteOneCspResponse.isIsSuccess()
					+ " for Store - " + storeSessionFacade.getSessionStore().getStoreId() + " and customer - "
					+ b2bCustomer.getEmail());
		}
		return siteOneCspResponse;
	}

	@PostMapping("/salecustomerprice")
	public @ResponseBody SiteOneCspWithSaleResponse getSalePriceForCustomer(@RequestParam(value = "productCode")
	final String productCode, @RequestParam(value = "quantity")
	final Integer quantity)
	{
		final SiteOneCspWithSaleResponse siteOneCspWithSaleResponse = new SiteOneCspWithSaleResponse();
		final SiteOneCspResponse siteOneCspResponse = siteOneProductFacade.getPriceForCustomer(productCode, null, quantity);
		if (null != siteOneCspResponse)
		{
			if (null != siteOneCspResponse.getPrice())
			{
				final ProductModel productModel = productService.getProductForCode(productCode);
				final ProductData productData = new ProductData();
				productData.setCustomerPrice(siteOneCspResponse.getPrice());
				final List<ProductOption> options = Arrays.asList(ProductOption.PROMOTIONS);
				productConfiguredPopulator.populate(productModel, productData, options);
				siteOneCspWithSaleResponse.setCspResponse(siteOneCspResponse);
				siteOneCspWithSaleResponse.setSalePrice(productData.getSalePrice());
			}
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			LOG.info("ProductPageController : productCode - " + productCode + " CSP Response - " + siteOneCspResponse.isIsSuccess()
					+ " for Store - " + storeSessionFacade.getSessionStore().getStoreId() + " and customer - "
					+ b2bCustomer.getEmail());
		}
		return siteOneCspWithSaleResponse;
	}

	@GetMapping("/showNearbyOverlay")
	public @ResponseBody List<StoreLevelStockInfoData> showNearbyOverlay(@RequestParam(value = "code")
	final String code, final Model model)
	{
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade.populateStoreLevelStockInfoData(code,
				false);
		return (CollectionUtils.isEmpty(storeLevelStockInfoDataList) ? null : storeLevelStockInfoDataList);
	}
	
	@GetMapping("/getPDPVariantProducts")
	public String getVariantResults(final Model model) throws UnsupportedEncodingException
			{
      		model.addAttribute("sessionVariantOpts", getSessionService().getAttribute("sessionVariantOptions"));
      		model.addAttribute("productData", getSessionService().getAttribute("sessionProductData"));
      		model.addAttribute("parentVariantCategoryNameList",getSessionService().getAttribute("parentVariantCategoryNameList"));
      		return ControllerConstants.Views.Pages.Product.PDPVariantLoadPage;
			}

	

protected void displayConfigurableAttribute(final ProductData productData, final Model model)
{
    final Map<String, List<FeatureData>> mapConfigurableAttributes = new HashMap<>();


    if (CollectionUtils.isNotEmpty(productData.getClassifications()))
    {
        final List<ClassificationData> configurableAttributeList =
            (List<ClassificationData>) productData.getClassifications();


        Set<String> attributeNames = new HashSet<>();


        for (final ClassificationData classification : configurableAttributeList)
        {
            final List<FeatureData> originalFeatureList = new ArrayList<>(classification.getFeatures());
            final List<FeatureData> existingFeatureList = mapConfigurableAttributes.get(classification.getName());


            if (CollectionUtils.isEmpty(existingFeatureList))
            {
                if (CollectionUtils.isNotEmpty(attributeNames))
                {
                    originalFeatureList.removeIf(feature -> attributeNames.contains(feature.getName()));
                }


                mapConfigurableAttributes.put(classification.getName(), originalFeatureList);
                attributeNames.addAll(originalFeatureList.stream()
                        .map(FeatureData::getName)
                        .collect(Collectors.toSet()));
            }
            else
            {
                if (CollectionUtils.isNotEmpty(attributeNames))
                {
                    originalFeatureList.removeIf(feature -> attributeNames.contains(feature.getName()));
                }


                originalFeatureList.addAll(existingFeatureList);
                attributeNames.addAll(originalFeatureList.stream()
                        .map(FeatureData::getName)
                        .collect(Collectors.toSet()));


                mapConfigurableAttributes.put(classification.getName(), originalFeatureList);
            }
        }
    }


    model.addAttribute("mapConfigurableAttributes", mapConfigurableAttributes);
}


	private boolean shouldTranslateContent() {
	    try {
	        String currentLanguage = storeSessionFacade.getCurrentLanguage().getIsocode();
	        return "es".equals(currentLanguage);
	    } catch (Exception e) {
	        LOG.warn("Error determining translation preference", e);
	        return false;
	    }
	}

	private boolean isCrawlBot(HttpServletRequest request) {
	    String userAgent = request.getHeader("User-Agent");
	    if (userAgent != null) {
	        String ua = userAgent.toLowerCase();
	        return KNOWN_BOTS.stream().anyMatch(ua::contains);
	    }
	    return false;
	}


	protected int getProductCategoryCount(final ProductData productData)
	{
		int categoryCount = 0;
		for (final CategoryData category : productData.getCategories())
		{
			if (!category.getCode().startsWith("SH1") && !category.getCode().startsWith("PH1"))
			{
				categoryCount += 1;
			}
		}
		return categoryCount;
	}

	protected boolean isUnitsAttribute(final String variantCategoryName)
	{
		for (final String variantCategoryNameListEntry : SiteoneCoreConstants.PRODUCT_VARIANT_WITH_UNITS_CATEGORY_NAME)
		{
			if (variantCategoryNameListEntry.equalsIgnoreCase(variantCategoryName))
			{
				return true;
			}
		}
		return false;
	}

	protected List<VariantMatrixElementData> groupAndSortUnitsAttribute(final List<VariantMatrixElementData> theMatrix)
	{
		final Map<String, List<VariantMatrixElementData>> keyValMap = new HashMap<String, List<VariantMatrixElementData>>();
		final List<VariantMatrixElementData> unknownUnitsList = new ArrayList<VariantMatrixElementData>();
		final List<VariantMatrixElementData> finalList = new ArrayList<VariantMatrixElementData>();
		final Pattern unitPattern = Pattern.compile("([[a-zA-Z]?[^0-9./,\\t\\n\\x0b\\r\\f][a-zA-Z]?]+)");
		String unitKey = null;

		for (final VariantMatrixElementData matrixElementData : theMatrix)
		{
			final Matcher unitMatcher = unitPattern.matcher(matrixElementData.getVariantValueCategory().getName());

			unitKey = StringUtils.EMPTY;
			while (unitMatcher.find())
			{
				unitKey = unitKey + unitMatcher.group();
			}
			unitKey = unitKey.trim();

			if (StringUtils.isEmpty(unitKey))
			{
				unknownUnitsList.add(matrixElementData);
			}
			else
			{
				if (keyValMap.get(unitKey) != null)
				{
					keyValMap.get(unitKey).add(matrixElementData);
				}
				else
				{
					final List<VariantMatrixElementData> emptyList = new ArrayList<VariantMatrixElementData>();
					emptyList.add(matrixElementData);
					keyValMap.put(unitKey, emptyList);
				}
			}
		}
		final Iterator<String> keysIterator = keyValMap.keySet().iterator();

		while (keysIterator.hasNext())
		{
			final String localUnitKey = keysIterator.next();
			Collections.sort(keyValMap.get(localUnitKey), variantMatrixSortStrategy);
			finalList.addAll(keyValMap.get(localUnitKey));
		}
		finalList.addAll(unknownUnitsList);
		return finalList;
	}



	/**
	 * @return the storeSessionFacade
	 */
	@Override
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	protected String handleAccessVariantProduct(final HttpServletRequest request, final ProductData productData,
			final String analyticsParam)
	{
		request.setAttribute("org.springframework.web.servlet.View.responseStatus", HttpStatus.MOVED_PERMANENTLY);
		if (null != analyticsParam)
		{
			return REDIRECT_PREFIX + productDataUrlResolver.resolve(productData) + "?" + analyticsParam;
		}
		else
		{
			return REDIRECT_PREFIX + productDataUrlResolver.resolve(productData);
		}
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
