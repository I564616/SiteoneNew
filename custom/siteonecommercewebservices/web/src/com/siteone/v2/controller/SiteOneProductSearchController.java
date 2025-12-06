package com.siteone.v2.controller;

import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.*;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.security.core.server.csi.XSSEncoder;
import com.siteone.commerceservice.category.data.CategoryProductSearchData;
import com.siteone.commerceservice.savedList.data.SavedListDataDTO;
import com.siteone.commerceservices.search.facetdata.ProductCategorySearchPageDTO;
import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentWsDTO;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.contentsearch.ContentSearchPageWsDTO;

import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.content.search.facade.ContentSearchFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.promotions.SiteOnePromotionsFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.utils.SiteOneSearchUtils;
import com.siteone.utils.XSSFilterUtil;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;

@Controller
@RequestMapping(value = "/{baseSiteId}/search")
@Tag(name = "Siteone Product Search")
public class SiteOneProductSearchController extends BaseController {

	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String FACET_SEPARATOR = ":";
	private static final String PRODUCT_SEARCH_TAB = "product";
	private static final int L2_CATEGORY_LENGTH = 6;
	private static final int L3_CATEGORY_LENGTH = 9;
	public static final int MAX_PAGE_LIMIT = 100; 
	private static final String EXCEPTION = "Exception occurred while calling the method";
	private static final String FULL_OPTION = "FULL";

	public enum ShowMode
	{
		// Constant names cannot be changed due to their usage in dependant extensions, thus nosonar
		Page, // NOSONAR
		All // NOSONAR
	}
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SiteOneProductSearchController.class);

	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

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

	@Resource(name = "defaultSiteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
		
	@Resource(name = "sessionService")
	private SessionService sessionService;
	

	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;


	@GetMapping
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "searchProducts", summary = "Search the products", description = "Fetch the products by search")
	public CategoryProductSearchData textSearch(@RequestParam(value = "text", defaultValue = "") final String searchText,
							 @RequestParam(value = "searchtype", defaultValue = PRODUCT_SEARCH_TAB) final String searchType,
							 @RequestParam(value = "pagesize", required = false) final String pageSize,
							 @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
							 @RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
							 @RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
							 @RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
							 @RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
							 @RequestParam(value = "storeId", required = true) final String storeId,
							 @RequestParam(value = "expressShipping", required = false) final String expressShipping,
							 @RequestParam(value = "unitId", required = false) final String unitId)
	{
		try {
		CategoryProductSearchData productSearchData = new CategoryProductSearchData();
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);		
		B2BUnitData unit = null;
		if(unitId != null){
			unit = b2bUnitFacade.getUnitForUid(unitId);
		}else{
			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		}
		if(unit != null)
		{
			((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled(unit.getTradeClass());
		}
		else
		{
			((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled("guest");
		}
		storeSessionFacade.setSessionShipTo(unit);
		
		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected, nearbyDisabled);
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on"))
		{
			sessionService.setAttribute("expressShipping", "true");
		}
		if (null != pageSize)
		{
			storeSessionFacade.setSessionPageSize(pageSize);
		}
		
		if (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			productSearchData.setIsRetailBranchPrice(true);
		}
		final String trimmedSearchText = searchText.trim();
		if (StringUtils.isNotBlank(trimmedSearchText))
		{
			final PageableData pageableData = createPageableData(0, getSearchPageSize(), null, ShowMode.Page);
			final String encodedSearchText = XSSFilterUtil.filter(trimmedSearchText);
			String failed_word = null, suggested_word = null;
			final SearchStateData searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			searchQueryData.setValue(encodedSearchText);
			searchState.setQuery(searchQueryData);
			if (searchType.equalsIgnoreCase(PRODUCT_SEARCH_TAB))
			{
				storeSessionFacade.setSessionTabId(PRODUCT_SEARCH_TAB);
				final List<ProductOption> options = Arrays.asList(ProductOption.BASIC);

				ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
				ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;

				try
				{
					searchPageData = encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
					if (searchPageData.getPagination().getTotalNumberOfResults() == 0
							&& searchPageData.getSpellingSuggestion() != null)
					{
						failed_word = trimmedSearchText;
						suggested_word = searchPageData.getSpellingSuggestion().getSuggestion();
						searchQueryData.setValue(suggested_word);
						searchState.setQuery(searchQueryData);
						searchPageData = encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
					}
					siteOnecategoryFacade.populateModelForInventory(searchPageData, productSearchData);
					cleanCategoryFacets(searchPageData);
					//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
					siteOneSearchUtils.attachImageUrls(searchPageData,productSearchData);
					siteOnecategoryFacade.filterProductsForParcelShippingSearch(searchPageData,sessionService.getAttribute("expressShipping") != null ? sessionService.getAttribute("expressShipping"): null);
					siteOnecategoryFacade.updateSearchPageData(searchPageData);
					siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
					siteOnecategoryFacade.updateStockFlagsForRegulatedProducts(searchPageData);
					productSearchData.setFailedWord(failed_word);
					productSearchData.setSuggestedWord(suggested_word);
				}
				catch (final ConversionException e) // NOSONAR
				{
					// nothing to do - the exception is logged in SearchSolrQueryPopulator
				}

				try
				{
					contentSearchPageData = encodeContentSearchPageData(contentSearchFacade.textSearch(searchState, pageableData));
				}
				catch (final ConversionException e) // NOSONAR
				{
					// nothing to do - the exception is logged in SearchSolrQueryPopulator
				}

				if((null != searchPageData && searchPageData.getPagination().getTotalNumberOfResults() >= 0) 
						|| (null != contentSearchPageData && contentSearchPageData.getPagination().getTotalNumberOfResults() >= 0))
				{
					final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
					productSearchData.setNumberPagesShown(numberPagesShown);
					productSearchData.setIsShowAllAllowed(calculateShowAll(searchPageData, showMode));
					productSearchData.setIsShowPageAllowed( calculateShowPaged(searchPageData, showMode));
					ProductCategorySearchPageDTO searchPageDTO = new ProductCategorySearchPageDTO();
					prepareProductSearchDTO(searchPageDTO,searchPageData);
					productSearchData.setSearchPageData(searchPageDTO);
					
					ContentSearchPageWsDTO contentSearchPageDTO = new ContentSearchPageWsDTO();
					prepareContentSearchDTO(contentSearchPageDTO,contentSearchPageData);
					productSearchData.setContentSearchPageData(contentSearchPageDTO);
				}

			}

			productSearchData.setUserLocation(customerLocationService.getUserLocation());
			productSearchData.setNearbyStores(storeSessionFacade.getNearbyStoresFromSession());
			productSearchData.setIsInStockFilter(storeSessionFacade.getInstockFilterSelected());
			if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
			{
				getAllSavedList(productSearchData);
			}

		}
		productSearchData.setPageType(PageType.PRODUCTSEARCH.name());
		return productSearchData;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" textSearch");
		}
	}
	
	@GetMapping(value = "/filter", params = "q")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "searchProductsFilter", summary = "Search the products and filter", description = "Fetch the products by search")
	public CategoryProductSearchData refineSearch(@RequestParam("q") final String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "searchtype", defaultValue = "product") final String searchType,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "pagesize", required = false) final String pageSize,
			@RequestParam(value = "text", required = false) final String searchText,
			@RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
			@RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
			@RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
			@RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
			@RequestParam(value = "storeId", required = true) final String storeId,
			@RequestParam(value = "expressShipping", required = false) final String expressShipping,
			@RequestParam(value = "unitId", required = false) final String unitId)
	{
		try {
		CategoryProductSearchData productSearchData = new CategoryProductSearchData();
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);		
		B2BUnitData unit = null;
		if(unitId != null){
			unit = b2bUnitFacade.getUnitForUid(unitId);
		}else{
			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		}
		storeSessionFacade.setSessionShipTo(unit);
		
		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected, nearbyDisabled);
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on") || searchQuery != null && searchQuery.contains("soisShippable:true"))
		{
			sessionService.setAttribute("expressShipping", "true");
		}
		ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;

		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			getAllSavedList(productSearchData);
		}

		if (searchQuery.contains("soallPromotions"))
		{
			final String promotionCode = searchQuery.split("soallPromotions:")[1];
			final PromotionData promotionData = siteOnePromotionsFacade.getPromotionsByCode(promotionCode);
			productSearchData.setPromotionData(promotionData);
			searchPageData = performSearch(searchQuery, page, showMode, sortCode, getSearchPageSize());
			ProductCategorySearchPageDTO searchPageDTO = new ProductCategorySearchPageDTO();
			prepareProductSearchDTO(searchPageDTO,searchPageData);
			productSearchData.setSearchPageData(searchPageDTO);
			productSearchData.setNearbyStores(storeSessionFacade.getNearbyStoresFromSession());
			productSearchData.setIsInStockFilter(storeSessionFacade.getInstockFilterSelected());
			return productSearchData;
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
				siteOnecategoryFacade.populateModelForInventory(searchPageData, productSearchData);
				cleanCategoryFacets(searchPageData);
				//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
				siteOneSearchUtils.attachImageUrls(searchPageData, productSearchData);
				siteOnecategoryFacade.filterProductsForParcelShippingSearch(searchPageData,sessionService.getAttribute("expressShipping") != null ? sessionService.getAttribute("expressShipping") : null);
				siteOnecategoryFacade.updateSearchPageData(searchPageData);
				siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
				siteOnecategoryFacade.updateStockFlagsForRegulatedProducts(searchPageData);
				contentSearchPageData = performContentSearch(searchQuery, 0, showMode, sortCode, getSearchPageSize());
			}
			else
			{
				searchPageData = performSearch(searchQuery, 0, showMode, sortCode, getSearchPageSize());
				contentSearchPageData = performContentSearch(searchQuery, page, showMode, sortCode, getSearchPageSize());
			}

			ProductCategorySearchPageDTO searchPageDTO = new ProductCategorySearchPageDTO();
			prepareProductSearchDTO(searchPageDTO,searchPageData);
			productSearchData.setSearchPageData(searchPageDTO);
			
			ContentSearchPageWsDTO contentSearchPageDTO = new ContentSearchPageWsDTO();
			prepareContentSearchDTO(contentSearchPageDTO,contentSearchPageData);
			productSearchData.setContentSearchPageData(contentSearchPageDTO);

			productSearchData.setUserLocation(customerLocationService.getUserLocation());
			productSearchData.setPageType(PageType.PRODUCTSEARCH.name());
			productSearchData.setNearbyStores(storeSessionFacade.getNearbyStoresFromSession());
			productSearchData.setIsInStockFilter(storeSessionFacade.getInstockFilterSelected());
			return productSearchData;
		}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" refineSearch");
		}

	}
	
	@GetMapping("/autocomplete/{componentUid}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "searchAutocomplete", summary = "Autocomplete suggestions", description = "Autocomplete suggestions for search")
	public AutocompleteResultDTO getAutocompleteSuggestions(@PathVariable("componentUid") final String componentUid,
			@RequestParam("term") final String term,@RequestParam("storeId") final String storeId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields) throws CMSItemNotFoundException
	{
		try {
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);

			final SearchBoxComponentModel component = (SearchBoxComponentModel) cmsComponentService.getSimpleCMSComponent(componentUid);

			final AutocompleteResultDTO result = new AutocompleteResultDTO();

			if (component.isDisplaySuggestions()) {
				result.setSuggestions (subList(productSearchFacade.getAutocompleteSuggestions(term), component.getMaxSuggestions()));
			}

			if (component.isDisplayProducts()) {
				SearchPageData<ProductData> productsPageData = productSearchFacade.textSearch(term, SearchQueryContext.SUGGESTIONS);
				int numberOfProductsToMap = Math.min(productsPageData.getResults().size(), component.getMaxProducts());
				List<ProductWsDTO> products = new ArrayList<>(numberOfProductsToMap);
				if (!productsPageData.getResults().isEmpty()) {
					products = getDataMapper()
							.mapAsList(subList(productsPageData.getResults(),numberOfProductsToMap), ProductWsDTO.class, fields);
				}
				result.setProducts(subList(products,component.getMaxProducts()));
			}

			if (component.isDisplayContents()) {
				SearchPageData<ContentData> contentsPageData = contentSearchFacade.textSearch(term, SearchQueryContext.SUGGESTIONS);
				int numberOfContentsToMap = Math.min(contentsPageData.getResults().size(), component.getMaxProducts());
				List<ContentWsDTO> contents = new ArrayList<>(numberOfContentsToMap);
				if (!contentsPageData.getResults().isEmpty()) {
					contents = getDataMapper()
							.mapAsList(subList(contentsPageData.getResults(), numberOfContentsToMap), ContentWsDTO.class, fields);
				}
				result.setContents(contents);
			}

			return result;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getAutocompleteSuggestions");
		}
	}
	
	protected void getAllSavedList(CategoryProductSearchData productSearchData)
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
			productSearchData.setCreateWishList("true");
		}

		productSearchData.setWishlistName(wishlistName);
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(SavedListData.class, SavedListDataDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    List<SavedListDataDTO> savedListDTO = mapper.mapAsList(allWishlist, SavedListDataDTO.class);
	    productSearchData.setAllWishlist(savedListDTO);
	}
	
	protected ProductSearchPageData<SearchStateData, ProductData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return encodeSearchPageData(productSearchFacade.textSearch(searchState, pageableData));
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
					List<FacetValueData> facetValueDatasToDelete = new ArrayList<>();
					for (FacetValueData facetValueData  : facetData.getValues())
					{
						//Remove all SH17 categories or any categoy that's not L2 or L3
						if (facetValueData.getCode().startsWith("SH17") ||
								(facetValueData.getCode().length() != L2_CATEGORY_LENGTH &&
										facetValueData.getCode().length() != L3_CATEGORY_LENGTH))
						{
							facetValueDatasToDelete.add(facetValueData);
						}
					}
					facetData.getValues().removeAll(facetValueDatasToDelete);
				}
			}
		}
	}

	protected ProductSearchPageData<SearchStateData, ProductData> encodeSearchPageData(
			final ProductSearchPageData<SearchStateData, ProductData> searchPageData)
	{
		final SearchStateData currentQuery = searchPageData.getCurrentQuery();

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
	
	protected void processFacetData(final List<FacetData<SearchStateData>> facets) throws UnsupportedEncodingException
	{
		for (final FacetData<SearchStateData> facetData : facets)
		{
			final List<FacetValueData<SearchStateData>> topFacetValueDatas = facetData.getTopValues();
			if (CollectionUtils.isNotEmpty(topFacetValueDatas))
			{
				processFacetDatas(topFacetValueDatas);
			}
			final List<FacetValueData<SearchStateData>> facetValueDatas = facetData.getValues();
			if (CollectionUtils.isNotEmpty(facetValueDatas))
			{
				processFacetDatas(facetValueDatas);
			}
		}
	}

	protected void processFacetDatas(final List<FacetValueData<SearchStateData>> facetValueDatas)
			throws UnsupportedEncodingException
	{
		for (final FacetValueData<SearchStateData> facetValueData : facetValueDatas)
		{
			final SearchStateData facetQuery = facetValueData.getQuery();
			final SearchQueryData queryData = facetQuery.getQuery();
			final String queryValue = queryData.getValue();
			if (StringUtils.isNotBlank(queryValue))
			{
				final String[] queryValues = queryValue.split(FACET_SEPARATOR);
				final StringBuilder queryValueBuilder = new StringBuilder();
				queryValueBuilder.append(XSSEncoder.encodeHTML(queryValues[0]));
				for (int i = 1; i < queryValues.length; i++)
				{
					queryValueBuilder.append(FACET_SEPARATOR).append(queryValues[i]);
				}
				queryData.setValue(queryValueBuilder.toString());
			}
		}
	}
		
	protected PageableData createPageableData(final int pageNumber, final int pageSize, final String sortCode,
			final ShowMode showMode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);

		if (ShowMode.All == showMode)
		{
			pageableData.setPageSize(MAX_PAGE_LIMIT);
		}
		else
		{
			pageableData.setPageSize(pageSize);
		}
		return pageableData;
	}
	
	protected int getSearchPageSize()
	{
		return siteConfigService.getInt("storefront.search.pageSize", 0);
	}
	protected boolean isShowAllAllowed(final SearchPageData<?> searchPageData)
	{
		return searchPageData.getPagination().getNumberOfPages() > 1
				&& searchPageData.getPagination().getTotalNumberOfResults() < MAX_PAGE_LIMIT;
	}

	protected Boolean calculateShowAll(final SearchPageData<?> searchPageData, final ShowMode showMode)
	{
		return Boolean.valueOf((showMode != ShowMode.All && //
				searchPageData.getPagination().getTotalNumberOfResults() > searchPageData.getPagination().getPageSize())
				&& isShowAllAllowed(searchPageData));
	}

	protected Boolean calculateShowPaged(final SearchPageData<?> searchPageData, final ShowMode showMode)
	{
		return Boolean.valueOf(showMode == ShowMode.All && (searchPageData.getPagination().getNumberOfPages() > 1
				|| searchPageData.getPagination().getPageSize() == getMaxSearchPageSize()));
	}
	
	protected int getMaxSearchPageSize()
	{
		return MAX_PAGE_LIMIT;
	}
	
	protected void prepareProductSearchDTO(ProductCategorySearchPageDTO searchPageDTO,
			ProductSearchPageData<SearchStateData, ProductData> searchPageData){
		searchPageDTO.setCategoryCode(searchPageData.getCategoryCode());
		searchPageDTO.setBreadcrumbs(searchPageData.getBreadcrumbs());
		searchPageDTO.setCurrentQuery(searchPageData.getCurrentQuery());
		searchPageDTO.setFacets(searchPageData.getFacets());
		searchPageDTO.setFreeTextSearch(searchPageData.getFreeTextSearch());
		searchPageDTO.setKeywordRedirectUrl(searchPageData.getKeywordRedirectUrl());
		searchPageDTO.setPagination(searchPageData.getPagination());
		List<ProductWsDTO> productList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
			productList = getDataMapper().mapAsList(searchPageData.getResults(), ProductWsDTO.class, FULL_OPTION);
		}
		searchPageDTO.setResults(productList);
		searchPageDTO.setSorts(searchPageData.getSorts());
		searchPageDTO.setSpellingSuggestion(searchPageData.getSpellingSuggestion());
	}
	
	protected void prepareContentSearchDTO(ContentSearchPageWsDTO searchPageDTO,
			ContentSearchPageData<SearchStateData, ContentData> searchPageData){
		searchPageDTO.setCategoryCode(searchPageData.getCategoryCode());
		searchPageDTO.setBreadcrumbs(searchPageData.getBreadcrumbs());
		searchPageDTO.setCurrentQuery(searchPageData.getCurrentQuery());
		searchPageDTO.setFacets(searchPageData.getFacets());
		searchPageDTO.setFreeTextSearch(searchPageData.getFreeTextSearch());
		searchPageDTO.setKeywordRedirectUrl(searchPageData.getKeywordRedirectUrl());
		searchPageDTO.setPagination(searchPageData.getPagination());
		List<ContentWsDTO> productList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
			productList = getDataMapper().mapAsList(searchPageData.getResults(), ContentWsDTO.class, FULL_OPTION);
		}
		searchPageDTO.setResults(productList);
		searchPageDTO.setSorts(searchPageData.getSorts());
		searchPageDTO.setSpellingSuggestion(searchPageData.getSpellingSuggestion());
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
	
}
