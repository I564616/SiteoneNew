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

import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.SearchBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.util.MetaSanitizerUtil;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.AutocompleteResultData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetRefinement;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.security.core.server.csi.XSSEncoder;
import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.content.search.facade.ContentSearchFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.promotions.SiteOnePromotionsFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.util.SiteOneSearchUtils;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.core.constants.SiteoneCoreConstants;


@Controller
@RequestMapping("/search")
public class SearchPageController extends AbstractSearchPageController
{
	private static final String SEARCH_META_DESCRIPTION_ON = "search.meta.description.on";
	private static final String SEARCH_META_DESCRIPTION_RESULTS = "search.meta.description.results";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String COMPONENT_UID_PATH_VARIABLE_PATTERN = "{componentUid:.*}";
	private static final String FACET_SEPARATOR = ":";
	private static final String SEARCH_CMS_PAGE_ID = "search";
	private static final String PROMOTION_SEARCH_CMS_PAGE_ID = "siteOnePromotionSearchPage";
	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	private static final String PRODUCT_SEARCH_TAB = "product";
	private static final String CONTENT_SEARCH_TAB = "content";
	private static final String LAST_LINK_CLASS = "active";
	private static final int L2_CATEGORY_LENGTH = 6;
	private static final int L3_CATEGORY_LENGTH = 9;
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	private static final String QUOTESFEATURESWITCH = "QuotesFeatureSwitch";
	private static final String SKUID_URL = "/search?q=";
	private static final String SKUID = "skuId=";
	private static final String PRODUCT_VIEW_TYPE_KEY = "productViewType";
	private static final String MOBILE_WEB_VIEW = "mobileWebView";
	private static final String DEFAULT_PLP_VIEW_THRESHOLD = "default.plp.view.threshold";
	private static final String PLP_CARD_VARIANT_COUNT = "plp.card.variant.count";
	private static final String PLP_LIST_VARIANT_COUNT = "plp.list.variant.count";
	private static final String FACET_LIMIT = "facet.api.limit";


	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SearchPageController.class);

	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "productDataUrlResolver")
	private UrlResolver<ProductData> productDataUrlResolver;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "contentSearchFacade")
	private ContentSearchFacade<ContentData> contentSearchFacade;

	@Resource(name = "siteOnePromotionsFacade")
	private SiteOnePromotionsFacade siteOnePromotionsFacade;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "i18nService")
	private I18NService i18nService;
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "siteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;

	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	/**
	 * @return the i18nService
	 */
	@Override
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
	@Override
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

	@GetMapping( params = "!q")
	public String textSearch(@RequestParam(value = "text", defaultValue = "")
	String searchText, @RequestParam(value = "searchtype", defaultValue = PRODUCT_SEARCH_TAB)
	final String searchType, final HttpServletRequest request, final Model model,
			@RequestParam(value = "pagesize", required = false)
			final String pageSize, @RequestParam(value = "nearby", required = false)
			final String nearbyCheckOn, @RequestParam(value = "selectedNearbyStores", required = false)
			final String selectedNearbyStores, @RequestParam(value = "inStock", required = false)
			final String inStockFilterSelected, @RequestParam(value = "nearbyDisabled", required = false)
			final String nearbyDisabled, @RequestParam(value = "expressShipping", required = false)
			final String expressShipping, @RequestParam(value = "displayall", required = false)
			final String displayall, @RequestParam(value = "skuId", defaultValue = "")
			final String skuId, final RedirectAttributes redirectAttr) throws CMSItemNotFoundException
	{
		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected,
				nearbyDisabled);
		getSessionService().removeAttribute("expressShipping");
		boolean quoteFeature = false;
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on"))
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
		redirectAttr.addFlashAttribute("searchText", searchText);
		if (StringUtils.isNotBlank(skuId))
		{
			redirectAttr.addFlashAttribute("searchText", skuId);
		}

		if (null != pageSize)
		{
			storeSessionFacade.setSessionPageSize(pageSize);
		}
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
		if (StringUtils.isNotBlank(skuId))
		{
			searchText = SKUID + skuId;
		}
		final String trimmedSearchText = searchText.trim();

		if (StringUtils.isNotBlank(trimmedSearchText))
		{
			final PageableData pageableData = createPageableData(0, getSearchPageSize(), null, ShowMode.Page);

			final SearchStateData searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			String failed_word = null, suggested_word = null;

			searchQueryData.setValue(trimmedSearchText);
			searchState.setQuery(searchQueryData);
			if (searchType.equalsIgnoreCase(PRODUCT_SEARCH_TAB))
			{
				storeSessionFacade.setSessionTabId(PRODUCT_SEARCH_TAB);
				final List<ProductOption> options = Arrays.asList(ProductOption.BASIC);

				ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
				ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;

				try
				{
					if (trimmedSearchText.startsWith(SKUID))
					{
						final ProductSearchPageData<SearchStateData, ProductData> productSearchPageData = productSearchFacade
								.textSearch(searchState, pageableData);
						productSearchPageData.setFreeTextSearch(trimmedSearchText);
						searchPageData = encodeSearchPageData(productSearchPageData);
					}
					else
					{
						searchPageData = encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
					}

					if (null != searchPageData && searchPageData.getKeywordRedirectUrl() != null)
					{
						// if the search engine returns a redirect, just
						return "redirect:" + searchPageData.getKeywordRedirectUrl();
					}

					if (searchPageData.getPagination().getTotalNumberOfResults() == 0 && searchPageData.getSpellingSuggestion() != null
							&& !trimmedSearchText.startsWith(SKUID))
					{
						failed_word = trimmedSearchText;
						suggested_word = searchPageData.getSpellingSuggestion().getSuggestion();
						searchQueryData.setValue(suggested_word);
						searchState.setQuery(searchQueryData);
						searchPageData = encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
					}

					siteOneSearchUtils.populateModelForInventory(searchPageData, model);
					cleanCategoryFacets(searchPageData);
					
					int totalFacetsSize=0;
					if(searchPageData.getFacets()!=null) {
						totalFacetsSize = searchPageData.getFacets().size();
					}
					int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
					model.addAttribute("facetLimit", facetLimit);
					model.addAttribute("totalFacetsSize", totalFacetsSize);
					//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
					siteOneSearchUtils.attachImageUrls(searchPageData, model);
					siteOnecategoryFacade.filterProductsForParcelShippingSearch(searchPageData,
							getSessionService().getAttribute("expressShipping") != null
									? getSessionService().getAttribute("expressShipping")
									: null);
					siteOnecategoryFacade.updateSearchPageData(searchPageData);
					siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
					siteOnecategoryFacade.updateStockFlagsForRegulatedProducts(searchPageData);
					limitFacets(searchPageData);
					model.addAttribute("failed_word", failed_word);
					model.addAttribute("suggested_word", suggested_word);
					model.addAttribute("expressShipping",
							expressShipping != null && expressShipping.equalsIgnoreCase("true") ? "on" : null);

					getBaseVariantMap(searchPageData, model);
				}
				catch (final ConversionException e) // NOSONAR
				{
					// nothing to do - the exception is logged in SearchSolrQueryPopulator
				}

				try
				{
					contentSearchPageData = encodeContentSearchPageData(contentSearchFacade.textSearch(searchState, pageableData));
					limitFacets(contentSearchPageData);
				}
				catch (final ConversionException e) // NOSONAR
				{
					// nothing to do - the exception is logged in SearchSolrQueryPopulator
				}

				if (searchPageData == null && null == contentSearchPageData)
				{
					storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
				}
				else if (null != searchPageData && null != contentSearchPageData
						&& searchPageData.getPagination().getTotalNumberOfResults() == 0
						&& contentSearchPageData.getPagination().getTotalNumberOfResults() == 0)
				{
					model.addAttribute("searchPageData", searchPageData);
					model.addAttribute("contentSearchPageData", contentSearchPageData);
					storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
					updatePageTitle(trimmedSearchText, model);
				}
				else
				{
					storeContinueUrl(request);
					populateModel(model, searchPageData, ShowMode.Page);
					//populateModelForInventory(searchPageData, model);
					populateModelForContent(model, contentSearchPageData, ShowMode.Page);
					storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
					updatePageTitle(trimmedSearchText, model);

				}

				getRequestContextData(request).setSearch(searchPageData);

				if (searchPageData != null)
				{
					if (null != searchPageData.getPagination() && searchPageData.getPagination().getTotalNumberOfResults() == 1)
					{
						final List<Breadcrumb> breadcrumbs = new ArrayList<>();
						final Breadcrumb breadcrumb = new Breadcrumb("#",
								getMessageSource().getMessage("breadcrumb.search", null, getI18nService().getCurrentLocale()),
								CollectionUtils.isEmpty(searchPageData.getBreadcrumbs()) ? LAST_LINK_CLASS : "");
						breadcrumbs.add(breadcrumb);

						final Breadcrumb breadcrumbForSearchText = new Breadcrumb("/search?text=" + getEncodedUrl(searchText),
								"Result for " + '"' + searchText + '"',
								CollectionUtils.isEmpty(searchPageData.getBreadcrumbs()) ? LAST_LINK_CLASS : "");
						breadcrumbs.add(breadcrumbForSearchText);
						model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
					}
					else
					{
						model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(null, trimmedSearchText,
								CollectionUtils.isEmpty(searchPageData.getBreadcrumbs())));
					}

				}

			}
			else
			{
				storeSessionFacade.setSessionTabId(CONTENT_SEARCH_TAB);
				ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
				ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;

				try
				{
					searchPageData = encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
					siteOnecategoryFacade.updateSearchPageData(searchPageData);
					siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
					int totalFacetsSize=0;
					if(searchPageData.getFacets()!=null) {
						totalFacetsSize = searchPageData.getFacets().size();
					}
					int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
					model.addAttribute("facetLimit", facetLimit);
					model.addAttribute("totalFacetsSize", totalFacetsSize);
					limitFacets(searchPageData);
				}
				catch (final ConversionException e) // NOSONAR
				{
					// nothing to do - the exception is logged in SearchSolrQueryPopulator
				}
				getBaseVariantMap(searchPageData, model);
				try
				{
					contentSearchPageData = encodeContentSearchPageData(contentSearchFacade.textSearch(searchState, pageableData));
				}
				catch (final ConversionException e) // NOSONAR
				{
					// nothing to do - the exception is logged in SearchSolrQueryPopulator
				}

				if (searchPageData == null && null == contentSearchPageData)
				{
					storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
				}
				else if (null != searchPageData && searchPageData.getKeywordRedirectUrl() != null)
				{
					// if the search engine returns a redirect, just
					return "redirect:" + searchPageData.getKeywordRedirectUrl();
				}
				else if (null != searchPageData && null != contentSearchPageData
						&& searchPageData.getPagination().getTotalNumberOfResults() == 0
						&& contentSearchPageData.getPagination().getTotalNumberOfResults() == 0)
				{
					model.addAttribute("searchPageData", searchPageData);
					model.addAttribute("contentSearchPageData", contentSearchPageData);
					storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
					updatePageTitle(trimmedSearchText, model);
				}
				else
				{
					storeContinueUrl(request);
					populateModel(model, searchPageData, ShowMode.Page);
					populateModelForContent(model, contentSearchPageData, ShowMode.Page);
					storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
					updatePageTitle(trimmedSearchText, model);

				}

				getRequestContextData(request).setSearch(searchPageData);

				if (searchPageData != null)
				{
					if (null != searchPageData.getPagination() && searchPageData.getPagination().getTotalNumberOfResults() == 1)
					{
						final List<Breadcrumb> breadcrumbs = new ArrayList<>();
						final Breadcrumb breadcrumb = new Breadcrumb("#",
								getMessageSource().getMessage("breadcrumb.search", null, getI18nService().getCurrentLocale()),
								CollectionUtils.isEmpty(searchPageData.getBreadcrumbs()) ? LAST_LINK_CLASS : "");
						breadcrumbs.add(breadcrumb);

						final Breadcrumb breadcrumbForSearchText = new Breadcrumb("/search?text=" + getEncodedUrl(searchText),
								"Result for " + '"' + searchText + '"',
								CollectionUtils.isEmpty(searchPageData.getBreadcrumbs()) ? LAST_LINK_CLASS : "");
						breadcrumbs.add(breadcrumbForSearchText);
						model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
					}
					else
					{
						model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(null, trimmedSearchText,
								CollectionUtils.isEmpty(searchPageData.getBreadcrumbs())));
					}

				}
			}

			model.addAttribute("userLocation", customerLocationService.getUserLocation());


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
			}

			final LoginForm loginForm = new LoginForm();
			model.addAttribute(loginForm);


		}

		else
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		}

		model.addAttribute("pageType", PageType.PRODUCTSEARCH.name());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_FOLLOW);

		final String metaDescription = MetaSanitizerUtil
				.sanitizeDescription(getMessageSource().getMessage(SEARCH_META_DESCRIPTION_RESULTS, null,
						SEARCH_META_DESCRIPTION_RESULTS, getI18nService().getCurrentLocale()) + " " + trimmedSearchText + " "
						+ getMessageSource().getMessage(SEARCH_META_DESCRIPTION_ON, null, SEARCH_META_DESCRIPTION_ON,
								getI18nService().getCurrentLocale())
						+ " " + getSiteName());

		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(trimmedSearchText);
		setUpMetaData(model, metaKeywords, metaDescription);
		getSessionService().setAttribute("assignedProducts", "");
		return getViewForPage(model);

	}

	/**
	 * @param contentSearchPageData
	 */
	private void limitFacets(ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData)
	{
		LOG.error("Inside Content");		
		List<FacetData<SearchStateData>> allFacets  =contentSearchPageData.getFacets();
		if(CollectionUtils.isNotEmpty(allFacets)) {
			int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
			int toIndex = Math.min(facetLimit, allFacets.size());
			contentSearchPageData.setFacets(allFacets.subList(0, toIndex));
			
		}
	}

	/**
	 * @param searchPageData
	 */
	private void limitFacets(ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		List<FacetData<SearchStateData>> allFacets  =searchPageData.getFacets();
		if(CollectionUtils.isNotEmpty(allFacets)) {
			int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
			int toIndex = Math.min(facetLimit, allFacets.size());
			searchPageData.setFacets(allFacets.subList(0, toIndex));
			
		}
		
	}

	protected String getEncodedUrl(final String url)
	{
		try
		{
			return URLEncoder.encode(url, "utf-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(e);
			}
			return url;
		}
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

	@GetMapping( params = "q")
	public String refineSearch(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "searchtype", defaultValue = "product")
	final String searchType, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "pagesize", required = false)
	final String pageSize, @RequestParam(value = "text", required = false)
	final String searchText, @RequestParam(value = "nearby", required = false)
	final String nearbyCheckOn, @RequestParam(value = "selectedNearbyStores", required = false)
	final String selectedNearbyStores, @RequestParam(value = "inStock", required = false)
	final String inStockFilterSelected, @RequestParam(value = "nearbyDisabled", required = false)
	final String nearbyDisabled, final HttpServletRequest request, @RequestParam(value = "expressShipping", required = false)
	final String expressShipping, final Model model) throws CMSItemNotFoundException
	{

		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected,
				nearbyDisabled);

		getSessionService().removeAttribute("expressShipping");
		boolean quoteFeature = false;
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on")
				|| searchQuery != null && searchQuery.contains("soisShippable:true"))
		{
			getSessionService().setAttribute("expressShipping", "true");
		}
		else
		{
			getSessionService().removeAttribute("expressShipping");
		}
		getSessionService().setAttribute("paginationValue", page);
		ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;

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
		}
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
		if (searchQuery.contains("soallPromotions"))
		{
			final String promotionCode = searchQuery.split("soallPromotions:")[1];
			final PromotionData promotionData = siteOnePromotionsFacade.getPromotionsByCode(promotionCode);
			model.addAttribute("promotionData", promotionData);
			final List<Breadcrumb> breadcrumbs = new ArrayList<>();
			final Breadcrumb breadcrumb = new Breadcrumb("#", "Monthly Specials", null);
			breadcrumbs.add(breadcrumb);
			model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
			searchPageData = performSearch(searchQuery, page, showMode, sortCode, getSearchPageSize());
			populateModelForpromotions(model, searchPageData, showMode);
			storeCmsPageInModel(model, getContentPageForLabelOrId(PROMOTION_SEARCH_CMS_PAGE_ID));
			final LoginForm loginForm = new LoginForm();
			model.addAttribute(loginForm);

			return getViewForPage(model);
		}
		else
		{
			if (null != pageSize)
			{
				storeSessionFacade.setSessionPageSize(pageSize);
			}
			if (searchType.equalsIgnoreCase("product"))
			{
				searchPageData = performSearch(searchQuery, page, showMode, sortCode, getSearchPageSize());
				siteOneSearchUtils.populateModelForInventory(searchPageData, model);
				cleanCategoryFacets(searchPageData);
				//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
				siteOneSearchUtils.attachImageUrls(searchPageData, model);
				siteOnecategoryFacade.filterProductsForParcelShippingSearch(searchPageData,
						getSessionService().getAttribute("expressShipping") != null
								? getSessionService().getAttribute("expressShipping")
								: null);
				siteOnecategoryFacade.updateSearchPageData(searchPageData);
				siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
				int totalFacetsSize=0;
				if(searchPageData.getFacets()!=null) {
					totalFacetsSize = searchPageData.getFacets().size();
				}
				int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
				model.addAttribute("facetLimit", facetLimit);
				model.addAttribute("totalFacetsSize", totalFacetsSize);
				limitFacets(searchPageData);
				contentSearchPageData = performContentSearch(searchQuery, 0, showMode, sortCode, getSearchPageSize());
				limitFacets(contentSearchPageData);
			}
			else
			{
				searchPageData = performSearch(searchQuery, 0, showMode, sortCode, getSearchPageSize());
				int totalFacetsSize=0;
				if(searchPageData.getFacets()!=null) {
					totalFacetsSize = searchPageData.getFacets().size();
				}
				int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
				model.addAttribute("facetLimit", facetLimit);
				model.addAttribute("totalFacetsSize", totalFacetsSize);
				limitFacets(searchPageData);
				contentSearchPageData = performContentSearch(searchQuery, page, showMode, sortCode, getSearchPageSize());
				limitFacets(contentSearchPageData);
			}


			populateModel(model, searchPageData, showMode);
			populateModelForContent(model, contentSearchPageData, showMode);
			getBaseVariantMap(searchPageData, model);
			model.addAttribute("userLocation", customerLocationService.getUserLocation());
			model.addAttribute("expressShipping", expressShipping != null && expressShipping.equalsIgnoreCase("true") ? "on" : null);

			final LoginForm loginForm = new LoginForm();
			model.addAttribute(loginForm);
			if (searchPageData.getPagination().getTotalNumberOfResults() == 0
					&& contentSearchPageData.getPagination().getTotalNumberOfResults() == 0)
			{
				updatePageTitle(searchPageData.getFreeTextSearch(), model);
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
			}
			else
			{
				storeContinueUrl(request);
				updatePageTitle(searchPageData.getFreeTextSearch(), model);
				storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
			}

			if (StringUtils.isNotBlank(searchQuery) && searchQuery.startsWith(SKUID))
			{
				searchPageData.setFreeTextSearch(searchQuery.split(":relevance")[0]);
				if (StringUtils.isNotBlank(searchPageData.getFreeTextSearch()))
				{
					for (final BreadcrumbData<SearchStateData> breadCrumb : searchPageData.getBreadcrumbs())
					{
						if (null != breadCrumb && breadCrumb.getRemoveQuery() != null)
						{
							breadCrumb.getRemoveQuery()
									.setUrl(breadCrumb.getRemoveQuery().getUrl().replace("q=", "q=" + searchPageData.getFreeTextSearch()));
						}
					}

				}
			}

			if (null != searchPageData.getPagination() && StringUtils.isNotEmpty(searchPageData.getFreeTextSearch())
					&& searchPageData.getPagination().getTotalNumberOfResults() == 1)
			{
				final List<Breadcrumb> breadcrumbs = new ArrayList<>();
				final Breadcrumb breadcrumb = new Breadcrumb("#",
						getMessageSource().getMessage("breadcrumb.search", null, getI18nService().getCurrentLocale()),
						CollectionUtils.isEmpty(searchPageData.getBreadcrumbs()) ? LAST_LINK_CLASS : "");
				breadcrumbs.add(breadcrumb);

				final Breadcrumb breadcrumbForSearchText = new Breadcrumb(
						"/search?text=" + getEncodedUrl(searchPageData.getFreeTextSearch()),
						"Result for " + '"' + searchPageData.getFreeTextSearch() + '"',
						CollectionUtils.isEmpty(searchPageData.getBreadcrumbs()) ? LAST_LINK_CLASS : "");
				breadcrumbs.add(breadcrumbForSearchText);
				model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);
			}
			else
			{
				model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(null, searchPageData));
			}
			model.addAttribute("pageType", PageType.PRODUCTSEARCH.name());

			final String metaDescription = MetaSanitizerUtil
					.sanitizeDescription(getMessageSource().getMessage(SEARCH_META_DESCRIPTION_RESULTS, null,
							SEARCH_META_DESCRIPTION_RESULTS, getI18nService().getCurrentLocale()) + " " + searchText + " "
							+ getMessageSource().getMessage(SEARCH_META_DESCRIPTION_ON, null, SEARCH_META_DESCRIPTION_ON,
									getI18nService().getCurrentLocale())
							+ " " + getSiteName());

			final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(searchText);
			setUpMetaData(model, metaKeywords, metaDescription);
			getSessionService().setAttribute("assignedProducts", "");
			return getViewForPage(model);

		}

	}




	/**
	 * @param model
	 * @param searchPageData
	 * @param showMode
	 */
	private void populateModelForpromotions(final Model model,
			final ProductSearchPageData<SearchStateData, ProductData> searchPageData, final ShowMode showMode)
	{
		final int numberPagesShown = getSiteConfigService().getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);

		model.addAttribute("promotions_numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("promotions_searchPageData", searchPageData);
		model.addAttribute("promotions_isShowAllAllowed", calculateShowAll(searchPageData, showMode));
		model.addAttribute("promotions_isShowPageAllowed", calculateShowPaged(searchPageData, showMode));

	}

	protected ProductSearchPageData<SearchStateData, ProductData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);
		if (StringUtils.isNotBlank(searchQuery) && searchQuery.startsWith(SKUID))
		{
			final ProductSearchPageData<SearchStateData, ProductData> productSearchPageData = productSearchFacade
					.textSearch(searchState, pageableData);
			productSearchPageData.setFreeTextSearch(searchQuery.split(":relevance")[0]);
			return encodeSearchPageData(productSearchPageData);
		}
		else
		{
			return encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
		}
	}

	protected ContentSearchPageData<SearchStateData, ContentData> performContentSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return encodeContentSearchPageData(contentSearchFacade.textSearch(searchState, pageableData));
	}

	@ResponseBody
	@GetMapping("/results")
	public SearchResultsData<ProductData> jsonSearchResults(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode) throws CMSItemNotFoundException
	{
		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performSearch(searchQuery, page, showMode,
				sortCode, getSearchPageSize());
		final SearchResultsData<ProductData> searchResultsData = new SearchResultsData<>();
		searchResultsData.setResults(searchPageData.getResults());
		searchResultsData.setPagination(searchPageData.getPagination());
		return searchResultsData;
	}

	@ResponseBody
	@GetMapping("/contentresults")
	public SearchResultsData<ContentData> jsonContentSearchResults(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode) throws CMSItemNotFoundException
	{
		final ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = performContentSearch(searchQuery, page,
				showMode, sortCode, getSearchPageSize());
		final SearchResultsData<ContentData> contentsearchResultsData = new SearchResultsData<>();
		contentsearchResultsData.setResults(contentSearchPageData.getResults());
		contentsearchResultsData.setPagination(contentSearchPageData.getPagination());
		return contentsearchResultsData;
	}

	
	@GetMapping("/facets")
	public String getFacets(
	    @RequestParam(value="q",required=false) final String facetQuery,
	    @RequestParam(value="text",required=false) final String searchText,
	    @RequestParam(value = "page", defaultValue = "0") final int page,
	    @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
	    @RequestParam(value = "sort", required = false) final String sortCode,
	    @RequestParam(value = "searchtype", defaultValue = PRODUCT_SEARCH_TAB) final String searchType,
	    @RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
	    @RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
	    @RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
	    @RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
	    @RequestParam(value = "expressShipping", required = false) final String expressShipping,
	    @RequestParam(value = "displayall", required = false) final String displayall,
	    @RequestParam(value = "viewtype", required = false) final String viewType,
	    @RequestParam(value = "facetOffset" , defaultValue = "0") final int facetOffset,
	    HttpServletRequest request, final Model model) throws CMSItemNotFoundException {
	    
	
	    siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, 
	                                                           inStockFilterSelected, nearbyDisabled);
	    
	    if (expressShipping != null && expressShipping.equalsIgnoreCase("on")) {
	        getSessionService().setAttribute("expressShipping", "true");
	    }
	    
	    if (displayall != null) {
	        getSessionService().setAttribute("displayAll", displayall);
	    }
	    
	    // Process search
	    final SearchStateData searchState = new SearchStateData();
	    final SearchQueryData searchQueryData = new SearchQueryData();
	    if(StringUtils.isNotBlank(facetQuery)) {
	   	 searchQueryData.setValue(facetQuery);
	    }
	    else {
	   	 searchQueryData.setValue(searchText !=null ? searchText : "");
	    }
	    searchState.setQuery(searchQueryData);
	    
	    // Apply correct search based on searchType
	    ProductSearchPageData<SearchStateData, ProductData> searchPageData;
	    if (searchType.equalsIgnoreCase(PRODUCT_SEARCH_TAB)) {
	        searchPageData = productSearchFacade.textSearch(searchState, 
	                createPageableData(page, getSearchPageSize(), sortCode, showMode));
	                
	        cleanCategoryFacets(searchPageData);
	        siteOnecategoryFacade.filterProductsForParcelShippingSearch(searchPageData,
	                getSessionService().getAttribute("expressShipping") != null
	                        ? getSessionService().getAttribute("expressShipping")
	                        : null);
	        siteOnecategoryFacade.updateSearchPageData(searchPageData);
	    } else {
	        // Handle content search if needed
	        searchPageData = productSearchFacade.textSearch(searchState, 
	                createPageableData(page, getSearchPageSize(), sortCode, showMode));
	    }
	    
	    // Process facets
	    final List<FacetData<SearchStateData>> allFacets = refineFacets(searchPageData.getFacets(),
	            convertBreadcrumbsToFacets(searchPageData.getBreadcrumbs()));
	    
	    int facetLimit = getSiteConfigService().getInt(FACET_LIMIT, 10);
	    int toIndex = Math.min(facetOffset+facetLimit, allFacets.size());
	    final List<FacetData<SearchStateData>> paginatedFacets = allFacets.subList(facetOffset, toIndex);
	    
	    final FacetRefinement<SearchStateData> refinement = new FacetRefinement<>();
	    refinement.setFacets(allFacets);
	    refinement.setCount(searchPageData.getPagination().getTotalNumberOfResults());
	    refinement.setBreadcrumbs(searchPageData.getBreadcrumbs());
	    
	    model.addAttribute("facets", paginatedFacets);
	    

	    return ControllerConstants.Views.Pages.Category.FacetFragment;
	}

	@PostMapping("/setView")
	@ResponseBody
	public ResponseEntity<String> setProducView(@RequestParam(value="viewType",required = false)
	final String viewType,@RequestParam(value = "mobileView", required = false)
	final String mobileView)
	{
		if(StringUtils.isNotBlank(viewType))
		{
		sessionService.setAttribute(PRODUCT_VIEW_TYPE_KEY, viewType);
		}		
		sessionService.setAttribute(MOBILE_WEB_VIEW, mobileView);		
		return ResponseEntity.ok("View updated successfully");
	}

	@ResponseBody
	@GetMapping("/contentfacets")
	public FacetRefinement<SearchStateData> getContentFacets(@RequestParam("q")
	final String searchQuery, @RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode) throws CMSItemNotFoundException
	{
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		final ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = contentSearchFacade
				.textSearch(searchState, createPageableData(page, getSearchPageSize(), sortCode, showMode));
		final List<FacetData<SearchStateData>> facets = refineFacets(contentSearchPageData.getFacets(),
				convertBreadcrumbsToFacets(contentSearchPageData.getBreadcrumbs()));
		final FacetRefinement<SearchStateData> refinement = new FacetRefinement<>();
		refinement.setFacets(facets);
		refinement.setCount(contentSearchPageData.getPagination().getTotalNumberOfResults());
		refinement.setBreadcrumbs(contentSearchPageData.getBreadcrumbs());
		return refinement;
	}

	@ResponseBody
	@GetMapping("/autocomplete/" + COMPONENT_UID_PATH_VARIABLE_PATTERN)
	public AutocompleteResultData getAutocompleteSuggestions(@PathVariable
	final String componentUid, @RequestParam("term")
	final String term) throws CMSItemNotFoundException
	{
		final AutocompleteResultData resultData = new AutocompleteResultData();

		final SearchBoxComponentModel component = (SearchBoxComponentModel) cmsComponentService.getSimpleCMSComponent(componentUid);

		if (component.isDisplaySuggestions())
		{
			resultData.setSuggestions(subList(productSearchFacade.getAutocompleteSuggestions(term), component.getMaxSuggestions()));
		}

		if (component.isDisplayProducts())
		{
			resultData.setProducts(subList(productSearchFacade.textSearch(term, SearchQueryContext.SUGGESTIONS).getResults(),
					component.getMaxProducts()));
		}

		if (component.isDisplayContents())
		{
			resultData.setContents(subList(contentSearchFacade.textSearch(term, SearchQueryContext.SUGGESTIONS).getResults(),
					component.getMaxContents()));
		}

		return resultData;
	}

	protected <E> List<E> subList(final List<E> list, final int maxElements)
	{
		if (CollectionUtils.isEmpty(list))
		{
			return Collections.emptyList();
		}

		if (list.size() > maxElements)
		{
			return list.subList(0, maxElements);
		}

		return list;
	}

	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(
				getMessageSource().getMessage("search.meta.title", null, "search.meta.title", getI18nService().getCurrentLocale())
						+ " " + searchText));
	}


	@Override
	protected ProductSearchPageData<SearchStateData, ProductData> encodeSearchPageData(
			final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		final SearchStateData currentQuery = searchPageData.getCurrentQuery();
		final StringBuilder trimmedtext = new StringBuilder();
		boolean skuId = false;
		if (StringUtils.isNotEmpty(searchPageData.getFreeTextSearch()) && searchPageData.getFreeTextSearch().startsWith(SKUID))
		{
			skuId = true;
			trimmedtext.append(searchPageData.getFreeTextSearch());
			searchPageData.getCurrentQuery().setUrl(SKUID_URL + URLEncoder.encode(trimmedtext.toString(), StandardCharsets.UTF_8));
			searchPageData.getCurrentQuery().getQuery().setValue(trimmedtext.toString());
		}

		if (currentQuery != null)
		{
			try
			{
				final SearchQueryData query = currentQuery.getQuery();
				final String encodedQueryValue = XSSEncoder.encodeHTML(query.getValue());
				query.setValue(encodedQueryValue);
				currentQuery.setQuery(query);
				searchPageData.setCurrentQuery(currentQuery);
				searchPageData.setFreeTextSearch(XSSEncoder.encodeHTML(searchPageData.getFreeTextSearch()));

				final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
				if (CollectionUtils.isNotEmpty(facets) && !skuId)
				{
					processFacetData(facets);
				}
				if (CollectionUtils.isNotEmpty(facets) && skuId)
				{
					processFacetDataForSku(facets, trimmedtext);
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
		return searchPageData;
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

	protected void cleanCategoryFacets(final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			//Clean up the category facets
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if ("socategory".equalsIgnoreCase(facetData.getCode()))
				{
					final List<FacetValueData> facetValueDatasToDelete = new ArrayList<>();
					for (final FacetValueData facetValueData : facetData.getValues())
					{
						//Remove all SH17 categories or any categoy that's not L2 or L3
						if (facetValueData.getCode().startsWith("SH17") || (facetValueData.getCode().length() != L2_CATEGORY_LENGTH
								&& facetValueData.getCode().length() != L3_CATEGORY_LENGTH))
						{
							facetValueDatasToDelete.add(facetValueData);
						}
					}
					facetData.getValues().removeAll(facetValueDatasToDelete);
				}
			}
		}
	}

	@Override
	protected void processFacetData(final List<FacetData<SearchStateData>> facets) throws UnsupportedEncodingException
	{
		facetDataProcess(facets, null);
	}

	protected void processFacetDataForSku(final List<FacetData<SearchStateData>> facets, final StringBuilder trimmedtext)
			throws UnsupportedEncodingException
	{
		facetDataProcess(facets, trimmedtext);
	}

	public void facetDataProcess(final List<FacetData<SearchStateData>> facets, final StringBuilder trimmedtext)
			throws UnsupportedEncodingException
	{
		final boolean skuId = (null != trimmedtext && StringUtils.isNotBlank(trimmedtext.toString()));
		for (final FacetData<SearchStateData> facetData : facets)
		{
			final List<FacetValueData<SearchStateData>> topFacetValueDatas = facetData.getTopValues();
			if (CollectionUtils.isNotEmpty(topFacetValueDatas))
			{
				if (skuId)
				{
					processFacetDatasForSku(topFacetValueDatas, trimmedtext);
				}
				else
				{
					processFacetDatas(topFacetValueDatas);
				}
			}
			final List<FacetValueData<SearchStateData>> facetValueDatas = facetData.getValues();
			if (CollectionUtils.isNotEmpty(facetValueDatas))
			{
				if (skuId)
				{
					processFacetDatasForSku(facetValueDatas, trimmedtext);
				}
				else
				{
					processFacetDatas(facetValueDatas);
				}
			}
		}
	}


	@Override
	protected void processFacetDatas(final List<FacetValueData<SearchStateData>> facetValueDatas)
			throws UnsupportedEncodingException
	{
		facetDatasUpdate(facetValueDatas, null);
	}

	protected void processFacetDatasForSku(final List<FacetValueData<SearchStateData>> facetValueDatas,
			final StringBuilder trimmedtext) throws UnsupportedEncodingException
	{
		facetDatasUpdate(facetValueDatas, trimmedtext);

	}

	public void facetDatasUpdate(final List<FacetValueData<SearchStateData>> facetValueDatas, final StringBuilder trimmedtext)
			throws UnsupportedEncodingException
	{
		final boolean skuId = (null != trimmedtext && StringUtils.isNotBlank(trimmedtext.toString()));

		for (final FacetValueData<SearchStateData> facetValueData : facetValueDatas)
		{
			final SearchStateData facetQuery = facetValueData.getQuery();
			if (skuId)
			{
				facetQuery.setUrl(facetQuery.getUrl().replace(SKUID_URL, SKUID_URL + URLEncoder.encode(trimmedtext.toString(), StandardCharsets.UTF_8)));

			}
			final SearchQueryData queryData = facetQuery.getQuery();
			final String queryValue = queryData.getValue();
			if (StringUtils.isNotBlank(queryValue))
			{
				final String[] queryValues = queryValue.split(FACET_SEPARATOR);
				final StringBuilder queryValueBuilder = new StringBuilder();
				if (skuId)
				{
					queryValueBuilder.append(XSSEncoder.encodeHTML(trimmedtext));
				}
				else
				{
					queryValueBuilder.append(XSSEncoder.encodeHTML(queryValues[0]));
				}
				for (int i = 1; i < queryValues.length; i++)
				{
					queryValueBuilder.append(FACET_SEPARATOR).append(queryValues[i]);
				}
				queryData.setValue(queryValueBuilder.toString());
			}
		}
	}


	@RequestMapping(value = "/changePageSize")
	public String changePageSize(@RequestParam(value = "pageSize")
	final String pageSize, @RequestHeader(value = "referer", required = false)
	final String referer)
	{
		storeSessionFacade.setSessionPageSize(pageSize);
		return REDIRECT_PREFIX + referer;
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
	public void getBaseVariantMap(final ProductSearchPageData<SearchStateData, ProductData> searchPageData, final Model model)
	{

		int baseCount = 0;
		Map<String, List<String>> baseVariantSku = new HashMap<>();
		List<ProductData> baseSku = new ArrayList<>();
		final int paginationValue = null != sessionService.getAttribute("paginationValue")
				? sessionService.getAttribute("paginationValue")
				: 0;
		final String viewMode = sessionService.getAttribute(PRODUCT_VIEW_TYPE_KEY);
		final String mobileView = sessionService.getAttribute(MOBILE_WEB_VIEW);


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

			if (model.getAttribute("viewType").equals("card"))
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
}