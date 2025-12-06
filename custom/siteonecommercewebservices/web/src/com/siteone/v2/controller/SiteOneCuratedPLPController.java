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
import de.hybris.platform.commercefacades.search.data.AutocompleteResultDTO;
import de.hybris.platform.commercefacades.search.data.AutocompleteResultData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetRefinement;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.security.core.server.csi.XSSEncoder;
import com.siteone.commerceservice.category.data.CategoryProductSearchData;
import com.siteone.commerceservice.savedList.data.SavedListDataDTO;
import com.siteone.commerceservices.search.facetdata.ProductCategorySearchPageDTO;
import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentWsDTO;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.SiteOneCuratedPLPComponentModel;
import com.siteone.core.model.SiteOneCuratedPLPHSProductComponentModel;
import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.contentsearch.ContentSearchPageWsDTO;

import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.content.search.facade.ContentSearchFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.promotions.SiteOnePromotionsFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.utils.SiteOneCategoryPageUtils;
import com.siteone.utils.SiteOneSearchUtils;
import com.siteone.utils.XSSFilterUtil;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;

import com.siteone.v2.controller.BaseController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping(value = "/{baseSiteId}")
@Tag(name = "Siteone Curated PLP and Recent Scan Products")
public class SiteOneCuratedPLPController extends BaseController {

	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String FACET_SEPARATOR = ":";
	private static final String PRODUCT_SEARCH_TAB = "product";
	private static final int L2_CATEGORY_LENGTH = 6;
	private static final int L3_CATEGORY_LENGTH = 9;
	public static final int MAX_PAGE_LIMIT = 100; 
	private static final String CURATED_PLP = "CuratedPLP";
	private static final String RECOMMENDED_PRODUCT = "RecommendedProduct";
	private static final String EXCEPTION = "Exception occurred while calling the method";

	public enum ShowMode
	{
		// Constant names cannot be changed due to their usage in dependant extensions, thus nosonar
		Page, // NOSONAR
		All // NOSONAR
	}
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SiteOneCuratedPLPController.class);

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

	@Resource(name = "siteOneSDSProductSearchFacade")
	private SiteOneSDSProductSearchFacade siteOneSDSProductSearchFacade;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@GetMapping(value="/curatedPLP/{componentUid}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "productList", summary = "List of products", description = "Fetch the products by componentid")
	public CategoryProductSearchData productList(@PathVariable("componentUid") final String componentUid,
							 @RequestParam(value = "page", defaultValue = "0") final int page,
							 @RequestParam(value = "searchtype", defaultValue = PRODUCT_SEARCH_TAB) final String searchType,
							 @RequestParam(value = "pagesize", required = false) final String pageSize,
							 @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
							 @RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
							 @RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
							 @RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
							 @RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
							 @RequestParam(value = "storeId", required = true) final String storeId,
							 @RequestParam(value = "expressShipping", required = false) final String expressShipping,
							 @RequestParam(value = "unitId", required = false) final String unitId,
							 @RequestParam(value = "q", required = false) final String searchQuery,
							 @RequestParam(value = "sort", required = false) final String sortCode) throws CMSItemNotFoundException
	{	
		try {
		String productCodes="";
		CategoryProductSearchData categoryProductSearchData=new CategoryProductSearchData();
        if("24".equalsIgnoreCase(pageSize)) {
            final SiteOneCuratedPLPHSProductComponentModel component=(SiteOneCuratedPLPHSProductComponentModel)cmsComponentService.getSimpleCMSComponent(componentUid);
            productCodes=component.getProductCodes();
        } else {
            final SiteOneCuratedPLPComponentModel component=(SiteOneCuratedPLPComponentModel)cmsComponentService.getSimpleCMSComponent(componentUid);
            productCodes=component.getProductCodes();
        }
        categoryProductSearchData=getSearchProductList(productCodes, page, searchType, pageSize, showMode, nearbyCheckOn, 
				selectedNearbyStores, inStockFilterSelected, nearbyDisabled, storeId, expressShipping, unitId, CURATED_PLP, 0, 0, searchQuery, sortCode);
		
    	final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
    	storeSessionFacade.setSessionStore(pointOfServiceData);
		if (null != pointOfServiceData
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						pointOfServiceData.getStoreId())
				&& StringUtils.isNotEmpty(pointOfServiceData.getCustomerRetailId()))
		{
			categoryProductSearchData.setIsRetailBranchPrice(true);
		}
		return categoryProductSearchData;
				}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" productList");
		}
	}

	@GetMapping(value="/recentScanProducts")
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "recentScanProducts", summary = "List of Recent Scan products", description = "Fetch the products by unitid")
	public CategoryProductSearchData recentScanProducts(@RequestParam(value = "page", defaultValue = "0") final int page,
							 @RequestParam(value = "searchtype", defaultValue = PRODUCT_SEARCH_TAB) final String searchType,
							 @RequestParam(value = "pagesize", required = false) final String pageSize,
							 @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
							 @RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
							 @RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
							 @RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
							 @RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
							 @RequestParam(value = "storeId", required = true) final String storeId,
							 @RequestParam(value = "expressShipping", required = false) final String expressShipping,
							 @RequestParam(value = "unitId", required = true) final String unitId)
	{
		try {
		final B2BUnitData unit;
		if (!StringUtils.isEmpty(unitId))
		{
			unit = b2bUnitFacade.getUnitForUid(unitId);
		} else {
			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		}
		if(unit != null)
		{
			((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled(unit.getTradeClass());
		}
		final List<String> productList=siteOneProductFacade.getRecentScanProductsByUser(unitId);
		if(productList.isEmpty()) {
			return null;
		} else {
			Object[] productCodesAndNoOfPages = getProductCodesAndNoOfPages(productList, pageSize, page);
			return getSearchProductList((String)productCodesAndNoOfPages[0], 0, searchType, pageSize, showMode, nearbyCheckOn, 
						selectedNearbyStores, inStockFilterSelected, nearbyDisabled, storeId, expressShipping, unitId, RECOMMENDED_PRODUCT, (int)productCodesAndNoOfPages[1], page, null, null);			
		}
		} 
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" recentScanProducts");
		}
	}

	@GetMapping(value="/recentScanProductsGuest")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "recentScanProductsGuest", summary = "List of Recent Scan products for Guest", description = "Fetch the products by productCodes")
	public CategoryProductSearchData recentScanProductsGuest(@RequestParam(value = "productCodes", required = true) final String productCodes,
							 @RequestParam(value = "page", defaultValue = "0") final int page,
							 @RequestParam(value = "searchtype", defaultValue = PRODUCT_SEARCH_TAB) final String searchType,
							 @RequestParam(value = "pagesize", required = false) final String pageSize,
							 @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
							 @RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
							 @RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
							 @RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
							 @RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
							 @RequestParam(value = "storeId", required = true) final String storeId,
							 @RequestParam(value = "expressShipping", required = false) final String expressShipping)
	{
		try {
        ((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled("guest");	
		final List<String> productList=new ArrayList<>(Arrays.asList(productCodes.split(",")));		
		Object[] productCodesAndNoOfPages = getProductCodesAndNoOfPages(productList, pageSize, page);
		return getSearchProductList((String)productCodesAndNoOfPages[0], 0, searchType, pageSize, showMode, nearbyCheckOn, 
					selectedNearbyStores, inStockFilterSelected, nearbyDisabled, storeId, expressShipping, null, RECOMMENDED_PRODUCT, (int)productCodesAndNoOfPages[1], page, null, null);
	    }
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" recentScanProductsGuest");
		}
	}
	
	protected Object[] getProductCodesAndNoOfPages(final List<String> productList, final String pageSize, final int page) {
		if (null != pageSize)
		{
			storeSessionFacade.setSessionPageSize(pageSize);
		}		
		int searchPageSize=getSearchPageSize();
		if(pageSize!=null) {
			searchPageSize=Integer.parseInt(pageSize);
		}
		
		int noOfProducts=productList.size();
		int fromIndex=0;
		if(page!=0 && page*searchPageSize<noOfProducts) {
			fromIndex=page*searchPageSize;
		}
		int toIndex=noOfProducts;
		if((page+1)*searchPageSize<noOfProducts) {
			toIndex=(page+1)*searchPageSize;
		}
		
		int noOfPages=0;
		if(noOfProducts%searchPageSize==0) {
			noOfPages=noOfProducts/searchPageSize;
		} else {
			noOfPages=(noOfProducts/searchPageSize)+1;
		}
		
		final String productCodes=String.join(",", productList.subList(fromIndex, toIndex));
		return new Object[]{productCodes, noOfPages};
	}
	
	protected CategoryProductSearchData getSearchProductList(final String productCodes, final int page, final String searchType, final String pageSize, final ShowMode showMode, 
			final String nearbyCheckOn, final String selectedNearbyStores, final String inStockFilterSelected, final String nearbyDisabled, final String storeId, final String expressShipping, 
			final String unitId, final String responseType, final int noOfPages, final int currPage,final String searchQuery, final String sortCode) {
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
		final String expressShippingAttr="expressShipping";
		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected, nearbyDisabled);
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on"))
		{
			sessionService.setAttribute(expressShippingAttr, "true");
		}
		if (null != pageSize)
		{
			storeSessionFacade.setSessionPageSize(pageSize);
		}
		int searchPageSize=getSearchPageSize();
		if(pageSize!=null) {
			searchPageSize=Integer.parseInt(pageSize);
		}
		final PageableData pageableData = createPageableData(page, searchPageSize, sortCode, ShowMode.Page);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		if(searchQuery != null) {
			searchQueryData.setValue(productCodes.toLowerCase() + "," + searchQuery);
		}else {
			searchQueryData.setValue(productCodes.toLowerCase());
		}
		searchState.setQuery(searchQueryData);
		if (searchType.equalsIgnoreCase(PRODUCT_SEARCH_TAB))
		{
			storeSessionFacade.setSessionTabId(PRODUCT_SEARCH_TAB);

			ProductSearchPageData<SearchStateData, ProductData> searchPageData = null;
			ContentSearchPageData<SearchStateData, ContentData> contentSearchPageData = null;

			try
			{
				searchPageData = encodeSearchPageData(siteOneSDSProductSearchFacade.curatedPLPSearch(searchState, pageableData, responseType));
				if(RECOMMENDED_PRODUCT.equalsIgnoreCase(responseType)) {
					List<String> listWithOrder = new ArrayList<>(Arrays.asList(productCodes.split(",")));
					List<ProductData> responseProductList = new ArrayList<>();
					for(String pCode:listWithOrder) {
						final ProductData productData = searchPageData.getResults().stream()
								.filter(pData -> pData.getVariantSkus().contains(pCode)).findFirst().orElse(null);
   	   					if (productData!=null){
   	   							responseProductList.add(productData);
   	   					}
   	   				}
					searchPageData.setResults(responseProductList);
				}
				siteOnecategoryFacade.populateModelForInventory(searchPageData, productSearchData);
				cleanCategoryFacets(searchPageData);
				siteOneSearchUtils.attachImageUrls(searchPageData,productSearchData);
				siteOnecategoryFacade.filterProductsForParcelShippingSearch(searchPageData,sessionService.getAttribute(expressShippingAttr) != null ? sessionService.getAttribute(expressShippingAttr): null);
				siteOnecategoryFacade.updateSearchPageData(searchPageData);
				siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
				siteOnecategoryFacade.updateStockFlagsForRegulatedProducts(searchPageData);
				//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
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
				if(noOfPages!=0) {
					searchPageData.getPagination().setNumberOfPages(noOfPages);
				}		
				if(currPage!=0) {
					searchPageData.getPagination().setCurrentPage(currPage);
				}			
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

		productSearchData.setPageType(PageType.PRODUCTSEARCH.name());
		return productSearchData;
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
					List<FacetValueData<SearchStateData>> facetValueDatasToDelete = new ArrayList<>();
					for (FacetValueData<SearchStateData> facetValueData  : facetData.getValues())
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
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
		searchPageDTO.setCategoryCode(searchPageData.getCategoryCode());
		searchPageDTO.setBreadcrumbs(searchPageData.getBreadcrumbs());
		searchPageDTO.setCurrentQuery(searchPageData.getCurrentQuery());
		searchPageDTO.setFacets(searchPageData.getFacets());
		searchPageDTO.setFreeTextSearch(searchPageData.getFreeTextSearch());
		searchPageDTO.setKeywordRedirectUrl(searchPageData.getKeywordRedirectUrl());
		searchPageDTO.setPagination(searchPageData.getPagination());
		List<ProductWsDTO> productList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
			productList = mapper.mapAsList(searchPageData.getResults(), ProductWsDTO.class);
		}
		searchPageDTO.setResults(productList);
		searchPageDTO.setSorts(searchPageData.getSorts());
		searchPageDTO.setSpellingSuggestion(searchPageData.getSpellingSuggestion());
	}
	
	protected void prepareContentSearchDTO(ContentSearchPageWsDTO searchPageDTO,
			ContentSearchPageData<SearchStateData, ContentData> searchPageData){
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(ContentData.class, ContentWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
		searchPageDTO.setCategoryCode(searchPageData.getCategoryCode());
		searchPageDTO.setBreadcrumbs(searchPageData.getBreadcrumbs());
		searchPageDTO.setCurrentQuery(searchPageData.getCurrentQuery());
		searchPageDTO.setFacets(searchPageData.getFacets());
		searchPageDTO.setFreeTextSearch(searchPageData.getFreeTextSearch());
		searchPageDTO.setKeywordRedirectUrl(searchPageData.getKeywordRedirectUrl());
		searchPageDTO.setPagination(searchPageData.getPagination());
		List<ContentWsDTO> productList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
			productList = mapper.mapAsList(searchPageData.getResults(), ContentWsDTO.class);
		}
		searchPageDTO.setResults(productList);
		searchPageDTO.setSorts(searchPageData.getSorts());
		searchPageDTO.setSpellingSuggestion(searchPageData.getSpellingSuggestion());
	}
	
}
