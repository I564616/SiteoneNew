/* [y] hybris Platform
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
import de.hybris.platform.acceleratorservices.data.RequestContextData;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.SearchBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractCategoryPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.util.MetaSanitizerUtil;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetRefinement;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import com.siteone.storefront.controllers.ControllerConstants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.sap.security.core.server.csi.XSSEncoder;
import com.sap.security.core.server.csi.util.URLDecoder;
import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.cms.service.SiteOneCMSPageService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.content.search.facade.ContentSearchFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.breadcrumbs.impl.SiteOneProductBreadCrumbBuilder;
import com.siteone.storefront.breadcrumbs.impl.SiteOneSearchBreadcrumbBuilder;
import com.siteone.storefront.util.SiteOneCategoryPageUtils;
import com.siteone.storefront.util.SiteOneSearchUtils;
import com.siteone.storefront.util.SiteoneXSSFilterUtil;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.core.constants.SiteoneCoreConstants;


/**
 * Controller for a category page
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/c")
public class CategoryPageController extends AbstractCategoryPageController
{
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	private static final String QUOTESFEATURESWITCH = "QuotesFeatureSwitch";
	private static final String DEFAULT_PLP_VIEW_THRESHOLD = "default.plp.view.threshold";
	private static final String PLP_CARD_VARIANT_COUNT = "plp.card.variant.count";
	private static final String PLP_LIST_VARIANT_COUNT = "plp.list.variant.count";
	private static final String FACET_LIMIT = "facet.api.limit";

	private static final Logger LOG = Logger.getLogger(CategoryPageController.class);
	@Resource(name = "siteOneCategoryPageUtils")
	private SiteOneCategoryPageUtils siteOneCategoryPageUtils;

	@Resource(name = "categoryService")
	private CategoryService categoryService;
	
	@Resource(name = "siteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;

	@Resource(name = "siteOneCategoryLandingConverter")
	private Converter<CategoryModel, CategoryData> siteOneCategoryLandingConverter;

	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "b2bUserFacade")
	private SiteOneB2BUserFacade siteOneB2BUserFacade;

	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;

	@Resource(name = "contentSearchFacade")
	private ContentSearchFacade<ContentData> contentSearchFacade;

	@Resource(name = "siteOneProductBreadcrumbBuilder")
	private SiteOneProductBreadCrumbBuilder siteOneProductBreadcrumbBuilder;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	private static final String SEO_INDEX_ENV = "storefront.seo.index.env";
	private static final String ALL_PRODUCTS = "analytics.fullpath.category.page.all.products";
	private static final String PATHING_CHANNEL = "analytics.pathing.channel.category.page";
	private static final String PRODUCT_VIEW_TYPE_KEY = "productViewType";
	private static final String MOBILE_WEB_VIEW = "mobileWebView";
	

	/**
	 * @return the searchBreadcrumbBuilder
	 */
	@Override
	public SearchBreadcrumbBuilder getSearchBreadcrumbBuilder()
	{
		return searchBreadcrumbBuilder;
	}

	/**
	 * @param searchBreadcrumbBuilder
	 *           the searchBreadcrumbBuilder to set
	 */
	public void setSearchBreadcrumbBuilder(final SearchBreadcrumbBuilder searchBreadcrumbBuilder)
	{
		this.searchBreadcrumbBuilder = searchBreadcrumbBuilder;
	}

	protected static final String New_Category_PAGE = "pages/category/newCategoryPage";

	@RequestMapping(value = CATEGORY_CODE_PATH_VARIABLE_PATTERN, method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String category(@PathVariable("categoryCode")
	String categoryCode, // NOSONAR

			@RequestParam(value = "q", required = false)
			final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
			final int page, @RequestParam(value = "show", defaultValue = "Page")
			final ShowMode showMode, @RequestParam(value = "sort", required = false)
			final String sortCode, @RequestParam(value = "pagesize", required = false)
			final String pageSize, @RequestParam(value = "viewtype", defaultValue = "Page")
			final ShowMode viewType, final Model model, @RequestParam(value = "nearby", required = false)
			final String nearbyCheckOn, @RequestParam(value = "selectedNearbyStores", required = false)
			final String selectedNearbyStores, @RequestParam(value = "inStock", required = false)
			final String inStockFilterSelected, @RequestParam(value = "nearbyDisabled", required = false)
			final String nearbyDisabled, @RequestParam(value = "expressShipping", required = false)
			final String expressShipping, @RequestParam(value = "displayall", required = false)
			final String displayall, final HttpServletRequest request, final HttpServletResponse response,
			final RedirectAttributes redirectAttr) throws UnsupportedEncodingException, IOException
	{


		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected,
				nearbyDisabled);

		if (StringUtils.isNotEmpty(request.getQueryString())
				&& !(request.getQueryString()).equals(SiteoneXSSFilterUtil.filter(request.getQueryString())))
		{
			final String uri = request.getRequestURI() + "?" + SiteoneXSSFilterUtil.filter(request.getQueryString());
			return REDIRECT_PREFIX + uri;
		}
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on")
				|| searchQuery != null && searchQuery.contains("soisShippable:true"))
		{
			getSessionService().setAttribute("expressShipping", "true");
		}
		else
		{
			getSessionService().removeAttribute("expressShipping");
		}

		if (displayall != null && "on".equalsIgnoreCase(displayall))
		{
			getSessionService().setAttribute("displayAll", "on");
		}
		if (displayall != null && "off".equalsIgnoreCase(displayall))
		{
			getSessionService().setAttribute("displayAll", "off");
		}
		getSessionService().setAttribute("paginationValue", page);

		if (model.asMap() != null)
		{
			model.addAttribute("searchText", model.asMap().get("searchText"));
			redirectAttr.addFlashAttribute("searchText", model.asMap().get("searchText"));
		}
		CategoryModel category = null;
		CategoryModel l1category = null;
		ContentPageModel categoryPage = null;
		final LoginForm loginForm = new LoginForm();
		boolean quoteFeature = false;
		model.addAttribute(loginForm);
		LOG.info("categoryCode==" + categoryCode);
		//SE-9371 code for getting external and internal natural referrers
		final String referrer = request.getHeader("referer");
			if (null != referrer)
			{
				if (!referrer.contains(Config.getString("website.siteone.https", StringUtils.EMPTY))
						|| !referrer.contains(Config.getString("website.siteone-ca.https", StringUtils.EMPTY)))
				{
					model.addAttribute("method", "naturalReferrer");
					model.addAttribute("methodMetaData", referrer);
				}
				else
				{
					if (null != request.getServletPath() && request.getServletPath().contains("c/sh") && null == searchQuery)
					{
						model.addAttribute("method", "browse");
						model.addAttribute("methodMetaData", "navigation");
					}
				}
				}
		//SE-9371 ends
		if (null != categoryCode)
		{
			categoryCode = categoryCode.toUpperCase();
			category = getCategoryService().getCategoryForCode(getSessionService().getAttribute("currentCatalogVersion"),categoryCode);
			final String redirection = checkRequestUrl(request, response, getCategoryModelUrlResolver().resolve(category));
			if (StringUtils.isNotEmpty(redirection))
			{
				return redirection;
			}
			final CategoryData categoryData = siteOneCategoryLandingConverter.convert(category);
			if (CollectionUtils.isNotEmpty(categoryData.getSubCategories()))
			{
				categoryData.getSubCategories().sort(Comparator.comparing(CategoryData::getSequence));
			}

			//This is only needed for L1 category, L1 category code is of the format of SH11, SH12 etc.
			if (StringUtils.isNotEmpty(category.getCode()) && category.getCode().length() == 4)
			{
				final String l1code = categoryCode.substring(0, 4);
				l1category = getCategoryService().getCategoryForCode(getSessionService().getAttribute("currentCatalogVersion"),l1code);
				final CategoryData l1categoryData = siteOneCategoryLandingConverter.convert(l1category);
				model.addAttribute("l1categoryData", l1categoryData);
			}
			model.addAttribute("categoryData", categoryData);
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

		if (null != pageSize)
		{
			storeSessionFacade.setSessionPageSize(pageSize);
		}

		LOG.info("viewType==" + viewType);

		getFullPagePath(model, category);
		final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
		Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
		model.addAttribute("algonomyRecommendationEnabled", algonomyRecommendation);
		final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();

		if (null != unit)
		{
			final B2BUnitModel parentB2BUnit = ((SiteOneB2BUnitService) b2bUnitService)
					.getParentForUnit(storeSessionFacade.getSessionShipTo().getUid());
			if (CollectionUtils.isNotEmpty(parentB2BUnit.getAssignedProducts()))
			{
				final String assignedProduct = parentB2BUnit.getAssignedProducts().stream().map(product -> product.getCode())
						.collect(Collectors.joining(","));
				getSessionService().setAttribute("assignedProducts", assignedProduct);
			}
		}

		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch(QUOTESFEATURESWITCH)
				|| (null != unit && null != unit.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(QUOTESFEATURESWITCH, unit.getUid())))
		{
			quoteFeature = true;
		}
		model.addAttribute("quotesFeatureSwitch", quoteFeature);
		if (viewType == ShowMode.All)
		{
			return performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, sortCode, model, request, response);
		}
		else if (viewType == ShowMode.Page && null != category && siteOneCategoryPageUtils.isCMSCategory(category))//Check the requested category is L1 / L2
		{
			model.addAttribute(WebConstants.BREADCRUMBS_KEY,
					((SiteOneSearchBreadcrumbBuilder) searchBreadcrumbBuilder).getBreadcrumbs(categoryCode, null, false));
			try
			{
				LOG.info("category==" + category);
				if (isAllowedCategory(model, categoryCode, request))
				{
					categoryPage = getCategoryLandingPage(category);
				}
				LOG.info("categoryPage==" + categoryPage);

				if (null != categoryPage)
				{
					getAllSavedList(model);
					storeCategoryPageInModel(model, categoryPage);
					setUpMetaDataForCategoryPage(model, categoryPage);
					updatePageTitle(category, model);
					LOG.info("After updatePageTitle()" + model);
					return getViewForPage(model);
				}
				else
				{
					LOG.info("searchQuery==" + searchQuery);
					return performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, sortCode, model, request,
							response);
				}

			}
			catch (final CMSItemNotFoundException e)
			{
				LOG.error(e.getMessage(), e);
				LOG.info("Within catch block CatPageContr");
				return performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, sortCode, model, request, response);
			}
		}
					
			else
			{
				LOG.info("Within the else block CatPageContr.category()");
				return performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, sortCode, model, request, response);
			}
		


	}

	protected void getFullPagePath(final Model model, final CategoryModel category)
	{
		final StringBuilder fullPagePath = new StringBuilder();
		fullPagePath.append(getMessageSource().getMessage(PATHING_CHANNEL, null, getI18nService().getCurrentLocale()));
		fullPagePath.append(": ");
		if (CollectionUtils.isNotEmpty(siteOneProductBreadcrumbBuilder.getFullPagePath(null, category)))
		{
			for (final String categoryName : siteOneProductBreadcrumbBuilder.getFullPagePath(null, category))
			{
				if (null != categoryName)
				{
					fullPagePath.append(categoryName + ": ");
				}
			}
		}
		if (StringUtils.isNotEmpty(category.getName(Locale.ENGLISH)))
		{
			fullPagePath.append(category.getName(Locale.ENGLISH).toLowerCase());
		}

		model.addAttribute("fullPagePath", fullPagePath.toString());
		model.addAttribute("pathingChannel",
				getMessageSource().getMessage(PATHING_CHANNEL, null, getI18nService().getCurrentLocale()));

	}

	@Override
	protected String performSearchAndGetResultsPage(final String categoryCode, final String searchQuery, final int page, // NOSONAR
			final ShowMode showMode, final String sortCode, final Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws UnsupportedEncodingException
	{
		LOG.info("Start of performSearchAndGetResultsPage()");

		final CategoryModel category = getCommerceCategoryService().getCategoryForCode(categoryCode);

		final String redirection = checkRequestUrl(request, response, getCategoryModelUrlResolver().resolve(category));
		if (StringUtils.isNotEmpty(redirection))
		{
			return redirection;
		}

		final CategoryPageModel categoryPage = getCategoryPage(category);

		final CategorySearchEvaluator categorySearch = new CategorySearchEvaluator(categoryCode,
				SiteoneXSSFilterUtil.filter(searchQuery), page, showMode, sortCode, categoryPage);

		ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = null;
		ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;

		try
		{
			if (isAllowedCategory(model, categoryCode, request))
			{
				categorySearch.doSearch();
				searchPageData = categorySearch.getSearchPageData();
			}
			else
			{
				searchPageData = createEmptySearchResult(categoryCode);
			}
		}
		catch (final ConversionException e) // NOSONAR
		{
			searchPageData = createEmptySearchResult(categoryCode);
		}


		final PageableData pageableData = createPageableData(0, getSearchPageSize(), null, ShowMode.Page);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();

		searchQueryData.setValue(category.getName());
		searchState.setQuery(searchQueryData);

		try
		{
			contentSearchPageData = encodeContentSearchPageData(contentSearchFacade.textSearch(searchState, pageableData));
		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}

		final boolean showCategoriesOnly = categorySearch.isShowCategoriesOnly();
		storeCmsPageInModel(model, categorySearch.getCategoryPage());
		storeContinueUrl(request);
		populateModel(model, searchPageData, showMode);
		populateModelForContent(model, contentSearchPageData, showMode);
		siteOneSearchUtils.populateModelForInventory(searchPageData, model);
		siteOnecategoryFacade.updateSearchPageData(searchPageData);
		siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
		siteOnecategoryFacade.updateStockFlagsForRegulatedProducts(searchPageData);
		cleanCategoryFacet(searchPageData, categoryCode);
		int totalFacetsSize=0;
		if(searchPageData.getFacets()!=null) {
			totalFacetsSize = searchPageData.getFacets().size();
		}
		int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
		model.addAttribute("facetLimit", facetLimit);
		model.addAttribute("totalFacetsSize", totalFacetsSize);
		limitFacets(searchPageData);
		//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
		siteOneSearchUtils.attachImageUrls(searchPageData, model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, getSearchBreadcrumbBuilder().getBreadcrumbs(categoryCode, searchPageData));
		model.addAttribute("showCategoriesOnly", Boolean.valueOf(showCategoriesOnly));
		model.addAttribute("categoryName", category.getName());
		model.addAttribute("pageType", PageType.CATEGORY.name());
		model.addAttribute("userLocation", getCustomerLocationService().getUserLocation());
		model.addAttribute("isReturning", "returning");

		Map<String, List<String>> baseVariantSku = new HashMap<>();
		List<ProductData> baseSku = new ArrayList<>();
		final int paginationValue = sessionService.getAttribute("paginationValue");
		final String viewMode = sessionService.getAttribute(PRODUCT_VIEW_TYPE_KEY);
		final String mobileView = sessionService.getAttribute(MOBILE_WEB_VIEW);

		int baseCount = 0;


		if (CollectionUtils.isNotEmpty(searchPageData.getResults()))
		{
			baseCount = searchPageData.getResults().stream()
					.filter(product -> null != product.getVariantCount() && product.getVariantCount() > 1).collect(Collectors.toList())
					.size();

			if (searchPageData.getResults().size() >= 4)
			{
				baseSku = searchPageData.getResults().subList(0, 4);
			}
			else
			{
				baseSku = searchPageData.getResults().subList(0, searchPageData.getResults().size());
			}
		}

		if (0 == paginationValue && viewMode == null && 0 != baseCount && mobileView==null)
		{
			if (baseCount > getSiteConfigService().getInt(DEFAULT_PLP_VIEW_THRESHOLD, 12))
			{
				model.addAttribute("viewType", "list");
			}
			else
			{
				model.addAttribute("viewType", "card");
			}
		}
		else
		{
			if (0 == paginationValue && null == viewMode)
			{
				model.addAttribute("viewType", "card");
			}
			if(StringUtils.isNotBlank(mobileView) && mobileView.equalsIgnoreCase("mobileView"))
			{
				model.addAttribute("viewType", "card");
			}
		}
		if (model.getAttribute("viewType") == null && viewMode != null)
		{
			model.addAttribute("viewType", viewMode);
		}

		if (model.getAttribute("viewType") != null)
		{
			sessionService.setAttribute("currentView", model.getAttribute("viewType"));
		}
		else
		{
			if (null != sessionService.getAttribute("currentView"))
			{
				model.addAttribute("viewType", sessionService.getAttribute("currentView"));
			}
			else
			{
				model.addAttribute("viewType", "card");
				sessionService.setAttribute("currentView", model.getAttribute("viewType"));
			}
		}


		if (CollectionUtils.isNotEmpty(baseSku) && 0 != baseCount)
		{
			baseVariantSku = baseSku.stream().filter(product -> null != product.getVariantCount() && product.getVariantCount() > 1)
					.collect(Collectors.toMap(ProductData::getCode, ProductData::getVariantCodes));
			final PageableData variantPageableData = createPageableDataForVariants(0, 150, null, ShowMode.All);

			if (null != model.getAttribute("viewType") && model.getAttribute("viewType").equals("card"))
			{
				siteOnecategoryFacade.getVariantProducts(baseVariantSku, getSiteConfigService().getInt(PLP_CARD_VARIANT_COUNT, 4),
						variantPageableData, model);
			}
			else
			{
				siteOnecategoryFacade.getVariantProducts(baseVariantSku, getSiteConfigService().getInt(PLP_LIST_VARIANT_COUNT, 2),
						variantPageableData, model);
			}
		}


		getAllSavedList(model);


		updatePageTitle(category, model);

		getSessionService().setAttribute("assignedProducts", "");
		final RequestContextData requestContextData = getRequestContextData(request);
		requestContextData.setCategory(category);
		requestContextData.setSearch(searchPageData);

		if (searchQuery != null)
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_FOLLOW);
		}
		final List<String> keyWords = (category.getKeywords()).stream().map(e -> e.getKeyword()).collect(Collectors.toList());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(keyWords);
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);

		LOG.info("End of performSearchAndGetResultsPage()");

		return getViewPage(categorySearch.getCategoryPage());

	}

	/**
	 * @param searchPageData
	 */
	private void limitFacets(ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData)
	{
		List<FacetData<SearchStateData>> allFacets  =searchPageData.getFacets();
		if(CollectionUtils.isNotEmpty(allFacets)) {
			int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
			int toIndex = Math.min(facetLimit, allFacets.size());
			searchPageData.setFacets(allFacets.subList(0, toIndex));
			
		}
		
	}

	@GetMapping(CATEGORY_CODE_PATH_VARIABLE_PATTERN + "/facets")
	public String getFacetsHtml(@PathVariable("categoryCode") final String categoryCode,
	                            @RequestParam(value = "q", required = false) final String searchQuery,
	                            @RequestParam(value = "page", defaultValue = "0") final int page,
	                            @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
	                            @RequestParam(value = "sort", required = false) final String sortCode,
	                            @RequestParam(value = "facetOffset" , defaultValue = "0") final int facetOffset,
	                            final Model model) throws UnsupportedEncodingException {

	    final CategoryModel category = getCommerceCategoryService().getCategoryForCode(categoryCode.toUpperCase());
	    final CategoryPageModel categoryPage = getCategoryPage(category);
	    final CategorySearchEvaluator categorySearch = new CategorySearchEvaluator(categoryCode.toUpperCase(), searchQuery, page, showMode, sortCode, categoryPage);
	    categorySearch.doSearch();
	    ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = categorySearch.getSearchPageData();
	    siteOnecategoryFacade.updateSearchPageData(searchPageData);
	    cleanCategoryFacet(searchPageData, categoryCode);
	    
	    List<FacetData<SearchStateData>> allFacets  =searchPageData.getFacets();
	    int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
	    
	    int toIndex = Math.min(facetOffset+facetLimit, allFacets.size());
	    final List<FacetData<SearchStateData>> paginatedFacets = allFacets.subList(facetOffset, toIndex);

	    model.addAttribute("facets", paginatedFacets);

	    return ControllerConstants.Views.Pages.Category.FacetFragment;
	}

	@ResponseBody
	@GetMapping(CATEGORY_CODE_PATH_VARIABLE_PATTERN + "/results")
	public SearchResultsData<ProductData> getResults(@PathVariable("categoryCode")
	final String categoryCode, @RequestParam(value = "q", required = false)
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, final Model model, @RequestParam(value = "sort", required = false)
	final String sortCode) throws UnsupportedEncodingException
	{
		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);
		return performSearchAndGetResultsData(categoryCode.toUpperCase(), searchQuery, page, showMode, sortCode);
	}

	protected ContentPageModel getCategoryLandingPage(final CategoryModel category) throws CMSItemNotFoundException
	{
		return ((SiteOneCMSPageService) getCmsPageService()).getCategoryLandingPage(category);
	}

	protected void storeCategoryPageInModel(final Model model, final AbstractPageModel page)
	{
		if (model != null && page != null)
		{
			model.addAttribute(CMS_PAGE_MODEL, page);
			if (page instanceof CategoryPageModel)
			{
				storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(page.getTitle()));
			}
			final LoginForm loginForm = new LoginForm();
			model.addAttribute(loginForm);
		}
	}

	protected void setUpMetaDataForCategoryPage(final Model model, final ContentPageModel page)
	{
		setUpMetaData(model, page.getKeywords(), page.getDescription());
	}

	private void getAllSavedList(final Model model)
	{
		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{

			final boolean isAssembly = false;
			final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
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
		}

		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);



	}

	protected boolean isAllowedCategory(final Model model, final String categoryCode, final HttpServletRequest request)
	{
		final Cookie usernameCookie = WebUtils.getCookie(request, "j_username");
		//If the customer is logged in
		if (model.containsAttribute("user"))
		{
			String uid = null;

			if ((MapUtils.isNotEmpty(model.asMap()) && model.asMap().get("user") != null
					&& model.asMap().get("user") instanceof CustomerData
					&& StringUtils.isNotEmpty(((CustomerData) model.asMap().get("user")).getDisplayUid()))
					|| ((usernameCookie != null && usernameCookie.getValue() != null)))
			{
				uid = ((CustomerData) model.asMap().get("user")).getDisplayUid();
				if (uid == null)
				{
					uid = URLDecoder.decode(usernameCookie != null ? usernameCookie.getValue() : null, "UTF-8");
					uid = uid.trim().toLowerCase();
					userService.setCurrentUser(userService.getUserForUID(uid));
					getSessionService().setAttribute("softLogin", true);
				}
			}
			else
			{
				return true;
			}
			LOG.info("uid==" + uid);
			LOG.info(" PunchOutCustomer==" + siteOneB2BUserFacade.isPunchOutCustomer(uid));

			final String punchOutSpecificCat = ((String) getSessionService().getAttribute("allowedCategories"));

			final boolean isPunchOutCustomer = siteOneB2BUserFacade.isPunchOutCustomer(uid);

			if (isPunchOutCustomer && StringUtils.isNotEmpty(categoryCode) && StringUtils.isNotEmpty(punchOutSpecificCat)
					&& categoryCode.trim().toUpperCase().startsWith(punchOutSpecificCat.trim().toUpperCase()))
			{
				return true;
			}
			else if (isPunchOutCustomer && StringUtils.isNotEmpty(categoryCode) && StringUtils.isNotEmpty(punchOutSpecificCat)
					&& !categoryCode.trim().toUpperCase().startsWith(punchOutSpecificCat.trim().toUpperCase()))
			{
				return false;
			}
		}

		return true;
	}


	private void cleanCategoryFacet(final ProductSearchPageData<SearchStateData, ProductData> searchPageData,
			final String categoryCode)
	{
		final int categoryLevelLength = categoryCode.length() + 3;
		final List<FacetValueData> facetValueDataToDelete = new ArrayList<>();
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if ("socategory".equalsIgnoreCase(facetData.getCode()))
				{
					for (final FacetValueData<SearchStateData> facetValueData : facetData.getValues())
					{
						if (facetValueData.getCode().length() != categoryLevelLength
								|| ((categoryCode.startsWith("SH11") && !facetValueData.getCode().startsWith("SH11"))
										|| (categoryCode.startsWith("SH12") && !facetValueData.getCode().startsWith("SH12"))
										|| (categoryCode.startsWith("SH13") && !facetValueData.getCode().startsWith("SH13"))
										|| (categoryCode.startsWith("SH14") && !facetValueData.getCode().startsWith("SH14"))
										|| (categoryCode.startsWith("SH15") && !facetValueData.getCode().startsWith("SH15"))
										|| (categoryCode.startsWith("SH16") && !facetValueData.getCode().startsWith("SH16"))
										|| (categoryCode.startsWith("SH17") && !facetValueData.getCode().startsWith("SH17"))))
						{
							facetValueDataToDelete.add(facetValueData);
						}
					}
					facetData.getValues().removeAll(facetValueDataToDelete);
				}
			}
		}
	}

	protected ContentSearchPageData<SearchStateData, ContentData> encodeContentSearchPageData(
			final ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData)
	{
		final SearchStateData currentQuery = contentSearchPageData.getCurrentQuery();

		if (currentQuery != null)
		{
			try
			{
				final SearchQueryData query = currentQuery.getQuery();
				final String encodedQueryValue = XSSEncoder.encodeHTML(query.getValue());
				query.setValue(encodedQueryValue);
				currentQuery.setQuery(query);
				contentSearchPageData.setCurrentQuery(currentQuery);
				contentSearchPageData.setFreeTextSearch(XSSEncoder.encodeHTML(contentSearchPageData.getFreeTextSearch()));

				final List<FacetData<SearchStateData>> facets = contentSearchPageData.getFacets();
				if (CollectionUtils.isNotEmpty(facets))
				{
					processFacetData(facets);
				}
			}
			catch (final UnsupportedEncodingException e)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug("Error occured during Encoding the Search Page data values", e);
				}
			}
		}
		return contentSearchPageData;
	}

	protected void populateModelForContent(final Model model, final ContentSearchPageData contentSearchPageData,
			final ShowMode showMode)
	{
		final int numberPagesShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
		model.addAttribute("content_numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("contentSearchPageData", contentSearchPageData);
		model.addAttribute("content_isShowAllAllowed", calculateShowAll(contentSearchPageData, showMode));
		model.addAttribute("content_isShowPageAllowed", calculateShowPaged(contentSearchPageData, showMode));
	}

	@GetMapping("/showNearbyOverlay")
	public @ResponseBody List<StoreLevelStockInfoData> showNearbyOverlay(@RequestParam(value = "code")
	final String code, final Model model)
	{
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade.populateStoreLevelStockInfoData(code,
				false);
		return (CollectionUtils.isEmpty(storeLevelStockInfoDataList) ? null : storeLevelStockInfoDataList);
	}


	@PostMapping("/getVariantProducts")
	public String getVariantResults(@RequestBody
	final Map<String, Object> data, final Model model) throws UnsupportedEncodingException
	{
		final int count = (int) data.get("count");
		final String pageRedirection = (String) data.get("viewType");
		final Map<String, List<String>> baseMap = (Map<String, List<String>>) data.get("variantSkus");
		final PageableData pageableData = createPageableDataForVariants(0, 150, null, ShowMode.All);
		siteOnecategoryFacade.getVariantProducts(baseMap, count, pageableData, model);
		if (StringUtils.isNotEmpty(pageRedirection) && pageRedirection.equalsIgnoreCase("list"))
		{
			return ControllerConstants.Views.Pages.Category.PLPVariantLoadPage;
		}
		else
		{
			return ControllerConstants.Views.Pages.Category.PLPCardLoadPage;
		}

	}

	@GetMapping("/getCategoryData" + CATEGORY_CODE_PATH_VARIABLE_PATTERN)
	public @ResponseBody CategoryData getCategoryData(@PathVariable("categoryCode")
	String categoryCode)
	{
		CategoryModel category = null;
		categoryCode = categoryCode.toUpperCase();
		category = getCommerceCategoryService().getCategoryForCode(categoryCode);
		final CategoryData categoryData = siteOneCategoryLandingConverter.convert(category);
		if (CollectionUtils.isNotEmpty(categoryData.getSubCategories()))
		{
			categoryData.getSubCategories().sort(Comparator.comparing(CategoryData::getSequence));
		}
		return categoryData;
	}

	@GetMapping("/getCategoryDataAll")
	public @ResponseBody CategoryData getCategoryDataAll()
	{
		CategoryModel category = null;
		final String categoryCode = "SH1";
		final List<CategoryData> subcategoriesList = new ArrayList<>();
		category = getCommerceCategoryService().getCategoryForCode(categoryCode);
		final CategoryData categoryData = siteOneCategoryLandingConverter.convert(category);
		if (CollectionUtils.isNotEmpty(categoryData.getSubCategories()))
		{
			for (final CategoryData subcategory : categoryData.getSubCategories())
			{
				if (subcategory != null)
				{
					final List<CategoryData> subcategoriesL3List = new ArrayList<>();
					final CategoryData subcategoryData = siteOneCategoryLandingConverter
							.convert(getCommerceCategoryService().getCategoryForCode(subcategory.getCode()));
					if (CollectionUtils.isNotEmpty(subcategoryData.getSubCategories()))
					{

						for (final CategoryData subcategoryL3 : subcategoryData.getSubCategories())
						{
							if (subcategoryL3 != null)
							{
								subcategoriesL3List.add(siteOneCategoryLandingConverter
										.convert(getCommerceCategoryService().getCategoryForCode(subcategoryL3.getCode())));
							}
						}
					}
					subcategoryData.setSubCategories(subcategoriesL3List);
					subcategoriesList.add(subcategoryData);
				}
			}
			categoryData.setSubCategories(subcategoriesList);
			categoryData.getSubCategories().sort(Comparator.comparing(CategoryData::getSequence));
		}
		return categoryData;
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
	protected PageableData createPageableDataForVariants(final int pageNumber, final int pageSize, final String sortCode,
			final ShowMode showMode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);
		pageableData.setPageSize(pageSize);

		return pageableData;
	}

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	/**
	 * @param categoryService the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

}
