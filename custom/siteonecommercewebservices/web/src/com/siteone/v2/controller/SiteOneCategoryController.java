package com.siteone.v2.controller;

import com.google.gson.Gson;
import com.sap.security.core.server.csi.util.URLDecoder;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.data.RequestContextData;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.facetdata.*;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.search.facetdata.BreadcrumbWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.search.facetdata.ProductCategorySearchPageWsDTO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import com.siteone.v2.controller.AbstractSearchPageController.ShowMode;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.commerceservice.category.data.CategoryProductSearchData;
import com.siteone.commerceservice.savedList.data.SavedListDataDTO;
import com.siteone.commerceservices.search.facetdata.ProductCategorySearchPageDTO;
import com.siteone.core.cms.service.SiteOneCMSPageService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.category.SiteOneCategoryFacade;
import com.siteone.facades.company.SiteOneB2BUserFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.commerceservices.store.dto.StoreLevelStockInfoDataWsDTO;

import org.springframework.web.util.WebUtils;

import com.siteone.utils.SiteOneCategoryPageUtils;
import com.siteone.utils.SiteOneSearchUtils;
import com.siteone.utils.XSSFilterUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for a category page
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/c")
@Tag(name = "Siteone Category")
public class SiteOneCategoryController extends AbstractCategoryPageController
{
	private static final Logger LOG = Logger.getLogger(SiteOneCategoryController.class);
	
	@Resource(name = "defaultSiteOneCategoryPageUtils")
	private SiteOneCategoryPageUtils siteOneCategoryPageUtils;

	@Resource(name = "defaultSiteOneSearchUtils")
	private SiteOneSearchUtils siteOneSearchUtils;

	@Resource(name = "siteOneCategoryLandingConverter")
	private Converter<CategoryModel, CategoryData> siteOneCategoryLandingConverter;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "siteOnecategoryFacade")
	private SiteOneCategoryFacade siteOnecategoryFacade;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";

	protected static final String New_Category_PAGE = "pages/category/newCategoryPage";
	private static final String EXCEPTION = "Exception occurred while calling the method";


	@GetMapping("/{categoryCode}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "getCatageryProducts", summary = "Fetch the catagory or products", description = "Fetch the catagory or products")
	public CategoryProductSearchData category(@PathVariable("categoryCode") String categoryCode, // NOSONAR
						   @RequestParam(value = "q", required = false) final String searchQuery,
						   @RequestParam(value = "page", defaultValue = "0") final int page,
						   @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
						   @RequestParam(value = "sort", required = false) final String sortCode,
						   @RequestParam(value = "pagesize", required = false) final String pageSize,
						   @RequestParam(value = "viewtype", defaultValue = "Page") final ShowMode viewType,
						   @RequestParam(value = "nearby", required = false) final String nearbyCheckOn,
						   @RequestParam(value = "selectedNearbyStores", required = false) final String selectedNearbyStores,
						   @RequestParam(value = "inStock", required = false) final String inStockFilterSelected,
						   @RequestParam(value = "nearbyDisabled", required = false) final String nearbyDisabled,
						   @RequestParam(value = "storeId", required = true) final String storeId,
						   @RequestParam(value = "expressShipping", required = false) final String expressShipping,
						   @RequestParam(value = "unitId", required = false) final String unitId,
						   final HttpServletRequest request, final HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		try {
		CategoryProductSearchData categorySearchData= new CategoryProductSearchData();
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
		else
		{
			((SiteOneB2BUnitFacade) b2bUnitFacade).setIsSegmentLevelShippingEnabled("guest");
		}
		storeSessionFacade.setSessionShipTo(unit);
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);
		
		siteOneSearchUtils.setInStockAndNearbyStoresFlagAndData(nearbyCheckOn, selectedNearbyStores, inStockFilterSelected, nearbyDisabled);
		if (expressShipping != null && expressShipping.equalsIgnoreCase("on")
				|| searchQuery != null && searchQuery.contains("soisShippable:true"))
		{
			sessionService.setAttribute("expressShipping", "true");
		}
		else
		{
			sessionService.removeAttribute("expressShipping");
		}
		
		CategoryModel category = null;
		CategoryModel l1category = null;
		//SE-9371 ends
		if (null != categoryCode)
		{
			categoryCode = categoryCode.toUpperCase();
			category = getCommerceCategoryService().getCategoryForCode(categoryCode);
			final CategoryData categoryData = siteOneCategoryLandingConverter.convert(category);
			if (CollectionUtils.isNotEmpty(categoryData.getSubCategories()))
			{
				categoryData.getSubCategories().sort(Comparator.comparing(CategoryData::getSequence));
			}

			//This is only needed for L1 category, L1 category code is of the format of SH11, SH12 etc.
			if (StringUtils.isNotEmpty(category.getCode()) && category.getCode().length()==4) {
				final String l1code = categoryCode.substring(0, 4);
				l1category = getCommerceCategoryService().getCategoryForCode(l1code);
				final CategoryData l1categoryData = siteOneCategoryLandingConverter.convert(l1category);
				//model.addAttribute("l1categoryData", l1categoryData);
				categorySearchData.setL1categoryData(l1categoryData);
			}
			categorySearchData.setCategoryData(categoryData);
		}

		if (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			categorySearchData.setIsRetailBranchPrice(true);
		}
		if (null != pageSize)
		{
			storeSessionFacade.setSessionPageSize(pageSize);
		}

		if (viewType == ShowMode.All)
		{
			return performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, 
					sortCode, request, response, categorySearchData);
		}
		else if (viewType == ShowMode.Page && null != category && siteOneCategoryPageUtils.isCMSCategory(category))//Check the requested category is L1 / L2
		{
			getAllSavedList(categorySearchData);
			
		}
		else
		{
			return performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, sortCode, 
					request, response, categorySearchData);
		}

		return categorySearchData;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" category");
		}
		
	}

	protected CategoryProductSearchData performSearchAndGetResultsPage(final String categoryCode, final String searchQuery, final int page,
						final ShowMode showMode, final String sortCode, final HttpServletRequest request,
						final HttpServletResponse response, CategoryProductSearchData categorySearchData) throws UnsupportedEncodingException
	{
		final CategoryModel category = getCommerceCategoryService().getCategoryForCode(categoryCode);
		final CategoryPageModel categoryPage = getCategoryPage(category);
		
		final CategorySearchEvaluator categorySearch = new CategorySearchEvaluator(categoryCode,
				XSSFilterUtil.filter(searchQuery), page, showMode, sortCode, categoryPage);

		ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = null;
		try
		{
			categorySearch.doSearch();
			searchPageData = categorySearch.getSearchPageData();
		}
		catch (final ConversionException e) // NOSONAR
		{
			searchPageData = createEmptySearchResult(categoryCode);
		}
		
		ProductCategorySearchPageDTO searchPageDTO = new ProductCategorySearchPageDTO();
		if(searchPageData!=null){
			prepareProductSearchDTO(searchPageDTO,searchPageData);
		}
		categorySearchData.setSearchPageData(searchPageDTO);
		
		final boolean showCategoriesOnly = categorySearch.isShowCategoriesOnly();
		final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
		categorySearchData.setShowCategoriesOnly(showCategoriesOnly);
		categorySearchData.setNumberPagesShown(numberPagesShown);
		categorySearchData.setIsShowAllAllowed(calculateShowAll(searchPageData, showMode));
		categorySearchData.setIsShowPageAllowed(calculateShowAll(searchPageData, showMode));
		
		siteOnecategoryFacade.populateModelForInventory(searchPageData, categorySearchData);
		siteOnecategoryFacade.updateSearchPageData(searchPageData);
		siteOnecategoryFacade.updateSalesInfoBackorderForProduct(searchPageData);
		siteOnecategoryFacade.updateStockFlagsForRegulatedProducts(searchPageData);
		cleanCategoryFacet(searchPageData, categoryCode);
		//siteOnecategoryFacade.updatePriorityBrandFacet(searchPageData);
		siteOneSearchUtils.attachImageUrls(searchPageData,categorySearchData);
		categorySearchData.setShowCategoriesOnly(showCategoriesOnly);
		categorySearchData.setCategoryName(category.getName());
		categorySearchData.setPageType(PageType.CATEGORY.name());
		categorySearchData.setUserLocation(getCustomerLocationService().getUserLocation());
		categorySearchData.setNearbyStores(storeSessionFacade.getNearbyStoresFromSession());
		categorySearchData.setIsInStockFilter(storeSessionFacade.getInstockFilterSelected());
		getAllSavedList(categorySearchData);

		return categorySearchData;
	}
	
	@GetMapping("/showNearbyOverlay")
	@ResponseBody
	@Operation(operationId = "showNearbyOverlay", summary = "Show nearby OverLay for product", description = "Returns list of nearby stores for a product")
	@ApiBaseSiteIdParam
	public List<StoreLevelStockInfoDataWsDTO> showNearbyOverlay(@RequestParam(value = "code") final String code, @RequestParam(value = "storeId", required = true) final String storeId)
	{
		try {
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);
		
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade.populateStoreLevelStockInfoData(code,false);
		List<StoreLevelStockInfoDataWsDTO> storeLevelStockInfoDataListDTO = new ArrayList<StoreLevelStockInfoDataWsDTO>();
					
		if(CollectionUtils.isNotEmpty(storeLevelStockInfoDataList) && null != storeLevelStockInfoDataList ) {
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(StoreLevelStockInfoData.class, StoreLevelStockInfoDataWsDTO.class);
			storeLevelStockInfoDataListDTO = mapper.mapAsList(storeLevelStockInfoDataList, StoreLevelStockInfoDataWsDTO.class);
		}
				
		return storeLevelStockInfoDataListDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" showNearbyOverlay");
		}
	}
	
	private void cleanCategoryFacet(final ProductSearchPageData<SearchStateData, ProductData> searchPageData, final String categoryCode) {
		int categoryLevelLength = categoryCode.length() + 3;
		List<FacetValueData> facetValueDataToDelete = new ArrayList<>();
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			for (final FacetData<SearchStateData> facetData : facets) {
				if ("socategory".equalsIgnoreCase(facetData.getCode()))
				{
					for (final FacetValueData<SearchStateData> facetValueData : facetData.getValues())
					{
						if (facetValueData.getCode().length() != categoryLevelLength ||
								((categoryCode.startsWith("SH11") && !facetValueData.getCode().startsWith("SH11")) ||
								(categoryCode.startsWith("SH12") && !facetValueData.getCode().startsWith("SH12")) ||
								(categoryCode.startsWith("SH13") && !facetValueData.getCode().startsWith("SH13")) ||
								(categoryCode.startsWith("SH14") && !facetValueData.getCode().startsWith("SH14")) ||
								(categoryCode.startsWith("SH15") && !facetValueData.getCode().startsWith("SH15")) ||
								(categoryCode.startsWith("SH16") && !facetValueData.getCode().startsWith("SH16")) ||
								(categoryCode.startsWith("SH17") && !facetValueData.getCode().startsWith("SH17")))) {
							facetValueDataToDelete.add(facetValueData);
						}
					}
					facetData.getValues().removeAll(facetValueDataToDelete);
				}
			}
		}
	}

	private void getAllSavedList( CategoryProductSearchData categorySearchData)
	{
		List<SavedListDataDTO> allWishlistDTO = new ArrayList<>();
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
				categorySearchData.setCreateWishList("true");
			}
			
			categorySearchData.setWishlistName(wishlistName);
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(SavedListData.class, SavedListDataDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    List<SavedListDataDTO> savedListDTO = mapper.mapAsList(allWishlist, SavedListDataDTO.class);
		    categorySearchData.setAllWishlist(savedListDTO);
		}

	}
	
	protected void prepareProductSearchDTO(ProductCategorySearchPageDTO searchPageDTO,
			ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData){
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    Gson gson = new Gson();
		searchPageDTO.setCategoryCode(searchPageData.getCategoryCode());
		searchPageDTO.setBreadcrumbs(searchPageData.getBreadcrumbs());
		searchPageDTO.setCurrentQuery(searchPageData.getCurrentQuery());
		searchPageDTO.setFacets(searchPageData.getFacets());
		searchPageDTO.setFreeTextSearch(searchPageData.getFreeTextSearch());
		searchPageDTO.setKeywordRedirectUrl(searchPageData.getKeywordRedirectUrl());
		searchPageDTO.setPagination(searchPageData.getPagination());
		List<ProductWsDTO> productList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
			searchPageData.getResults().forEach(product -> {
				ProductWsDTO productDTO = mapper.map(product, ProductWsDTO.class);
				productList.add(productDTO);
			});
		}
		searchPageDTO.setResults(productList);
		searchPageDTO.setSorts(searchPageData.getSorts());
		searchPageDTO.setSpellingSuggestion(searchPageData.getSpellingSuggestion());
		searchPageDTO.setSubCategories(searchPageData.getSubCategories());
	}
	
}
