package com.siteone.v2.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.siteone.core.event.ListEditEvent;
import com.siteone.core.event.ShareListEvent;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.SiteOneCustomerFacade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.annotation.Secured;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.savedList.data.CustomerPriceData;
import com.siteone.facades.savedList.data.RecommendedListData;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.facades.savedList.data.SavedListEntryUpdatedUOMData;
import com.siteone.facades.savedList.data.ShareCustomerData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.facades.wishlist.data.CreateWishlistAndResponseAllData;
import com.siteone.facades.wishlist.data.WishlistAddData;
import com.siteone.forms.SiteOneEditSavedListForm;
import com.siteone.forms.SiteoneSavedListCreateForm;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Resource;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;

import com.siteone.commerceservice.savedList.data.SavedListDataDTO;
import com.siteone.commerceservice.savedList.data.RecommendedListDataDTO;
import com.siteone.commerceservices.dto.savedlist.SearchPageListProductWsDTO;
import com.siteone.commerceservices.dto.savedlist.SearchPageListWsDTO;
import com.siteone.commerceservice.dto.savedList.SavedListDetailsDTO;
import com.siteone.commerceservice.dto.savedList.RecommendedListDetailsDTO;
import com.siteone.commerceservice.dto.savedList.SavedListEntryDTO;
import com.siteone.utils.XSSFilterUtil;
import com.siteone.facades.buyitagain.search.facade.BuyItAgainSearchFacade;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import com.siteone.commerceservice.savedList.data.SavedListEntryUpdatedUOMDataDTO;

@Controller
@RequestMapping(value = "/{baseSiteId}/savedlist")
@Tag(name = "Siteone Saved List")
public class SiteOneSavedListController extends BaseController{

	private static final Logger LOG = Logger.getLogger(SiteOneSavedListController.class);
	private static final String DUPLICATE_LIST_ERROR = "List name already exists. Please enter a new name.";
	private static final String LIST_INPUT_INVALID = "Length of List name must be between 1 and 200 characters";
	private static final String DESCENDING_ORDER = "desc";
	public static final int MAX_PAGE_LIMIT = 100;
	public static final String SUCCESS = "Success";
	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String ADD_SELECTED_EXSITING_LIST= "Item has been added to list";

	public enum ShowMode
	{
		// Constant names cannot be changed due to their usage in dependant extensions, thus nosonar
		Page, // NOSONAR
		All // NOSONAR
	}

	
	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "eventService")
	private EventService eventService;

	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "buyItAgainSearchFacade")
	private BuyItAgainSearchFacade buyItAgainSearchFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;


	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;
	
	
	protected SiteConfigService getSiteConfigService()
	{
		return siteConfigService;
	}
		
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "getSavedList", summary = "Get the saved list for loggedin user", description = "Get the saved list for loggedin user")
	public SearchPageListWsDTO getAllSavedList(@RequestParam(value = "page", defaultValue = "0", required = false) final int page, 
			@RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false) final String sortOrder, 
			@RequestParam(value = "listDeleted", required = false) final String listDeleted,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		try
		{
			final PageableData pageableData = this.createPageableData(page, this.getSearchPageSize(), null, ShowMode.Page);
			final boolean isAssembly = false;
			final SearchPageData searchPageData = siteoneSavedListFacade.getAllSavedList(pageableData, isAssembly, sortOrder);
			List<SavedListDataDTO> savedListDTO = new ArrayList();
		    if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
		    	savedListDTO = getDataMapper().mapAsList(searchPageData.getResults(), SavedListDataDTO.class,fields);
		    }
			SearchPageListWsDTO savedList= new SearchPageListWsDTO();
			savedList.setPagination(searchPageData.getPagination());
			savedList.setResults(savedListDTO);
			savedList.setSorts(searchPageData.getSorts());
			return savedList;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getAllSavedList");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/recommendedList")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "getRecommendedList", summary = "Get the recommended list for loggedin user", description = "Get the recommended list for loggedin user")
	public SearchPageListWsDTO getAllRecommendedList(@RequestParam(value = "page", defaultValue = "0", required = false) final int page, 
			@RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false) final String sortOrder, 
			@RequestParam(value = "listDeleted", required = false) final String listDeleted,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		try
		{
			sessionService.setAttribute("RecommendedList", "LandingPage");
			final List<RecommendedListData> recommendedListData = performSearch(null, 0, ShowMode.Page, null, 50);
			sessionService.setAttribute("RecommendedList", "");
			SearchPageData<RecommendedListData> rsearchPageData =new SearchPageData<>();
			rsearchPageData.setResults(recommendedListData);
			
			List<RecommendedListDataDTO> recommendedListDataDTO = new ArrayList();
		    if(CollectionUtils.isNotEmpty(rsearchPageData.getResults())){
		    	recommendedListDataDTO = getDataMapper().mapAsList(rsearchPageData.getResults(), RecommendedListDataDTO.class,fields);
		    }
		    
			SearchPageListWsDTO RecommendedList= new SearchPageListWsDTO();
			RecommendedList.setRecommendedListResults(recommendedListDataDTO);
			
			return RecommendedList;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getAllRecommendedList");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/recommendedListDetails")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "getRecommendedListDetails", summary = "Get the recommended list details for loggedin user", description = "Get the recommended list details for loggedin user")
	public String reccomendedListDetails(final String categoryName,@RequestParam(value = "storeId", required = true)final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId, @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields) 
	{	try {
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);		
		final B2BUnitData unit;

		if (!StringUtils.isEmpty(unitId))
		{
			unit = b2bUnitFacade.getUnitForUid(unitId);
		} else {
			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		}
		storeSessionFacade.setSessionShipTo(unit);
		final int viewByPageSize = getSiteConfigService().getInt("siteone.reccomendedlistentrydefault.pageSize.Mobile", 50);
		sessionService.setAttribute("RecommendedDetailsPage", categoryName);
		BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData = null;
		searchPageData = performDetailSearch(":soPurchasedCount-desc", 0, ShowMode.Page, null, viewByPageSize);
		sessionService.setAttribute("RecommendedDetailsPage", "");
		final List<ProductData> productList = getBuyAgainProductList(searchPageData);
		
		
		RecommendedListDetailsDTO recommendedListDetailsDTO=new RecommendedListDetailsDTO();
		recommendedListDetailsDTO.setCategoryNameData(categoryName);
		recommendedListDetailsDTO.setProductListData(productList);
		recommendedListDetailsDTO.setRecommnededListDetailsData(searchPageData.getResults());
		
		Gson gson = new Gson();
		return gson.toJson(recommendedListDetailsDTO);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getAllRecommendedListDetails");
	    }
		
	}
	
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/createlist")
	@ApiBaseSiteIdParam
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Operation(operationId = "createlist", summary = "create list for the loggedin user", description = "create list for the loggedin user provided the listname and the product list")
	public String createList(@Parameter(description = "SavedList form", required = true)  @RequestBody SiteoneSavedListCreateForm siteoneSavedListForm)
	{
		try
		{
			String code;
			final SavedListData savedListData = new SavedListData();
			String wishListName = XSSFilterUtil.filter(siteoneSavedListForm.getName());
			if(null != wishListName && (wishListName.length() >= 1 &&  wishListName.length() <= 200)) {
				savedListData.setName(wishListName);
				savedListData.setDescription(siteoneSavedListForm.getDescription());
				code = siteoneSavedListFacade.createSavedList(savedListData, siteoneSavedListForm.getProduct(), false);
				if (null == code)
				{
					code = DUPLICATE_LIST_ERROR;
				}
			} else {
				code = LIST_INPUT_INVALID;
			}
			Gson gson = new Gson();
			return gson.toJson(code);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method createList");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/updateUOMforEntries")
	@ApiBaseSiteIdParam
	@Operation(operationId = "updateUOMEntries", summary = "UpdateUOM", description = "Update UOM for the product")
	public @ResponseBody boolean updateUOMforEntries(@RequestParam(value = "wishListCode", required = true)
	final String wishListCode, @RequestParam(value = "inventoryUomId", required = true)
	final String inventoryUomId, @RequestParam(value = "productCode", required = true)
	final String productCode, @RequestParam(value = "storeId", required = true)	final String storeId,
	@Parameter(description = "unitId") @RequestParam(required = false) final String unitId )
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			
			
			return siteoneSavedListFacade.updateUOMforEntries(wishListCode,inventoryUomId,productCode);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method updateUOMforEntries");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/updateUOMforRecommendedListEntries")
	@ApiBaseSiteIdParam
	@Operation(operationId = "updateUOMforRecommendedListEntries", summary = "UpdateUOM", description = "Update UOM for the product")
	public @ResponseBody SavedListEntryUpdatedUOMDataDTO updateUOMforEntriesForRecommendedList( @RequestParam(value = "inventoryUomId", required = true)
	final String inventoryUomId, @RequestParam(value = "productCode", required = true) final String productCode, @RequestParam(value = "storeId", required = true)	final String storeId,
	@Parameter(description = "unitId") @RequestParam(required = false) final String unitId )
	
	{	
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			
			final SavedListEntryUpdatedUOMData savedListEntryUpdatedUOMData = new SavedListEntryUpdatedUOMData();
			final List<ProductData> productList = new ArrayList<ProductData>();

			final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
							Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
									ProductOption.STOCK, ProductOption.PRICE_RANGE, ProductOption.PROMOTIONS));
				
			if (!product.getIsProductDiscontinued()  && product.getPrice()!=null)
			{
				productList.add(product);
			}

			if (product.getPrice()==null)
			{
				LOG.info("buy again product price is null :"+product.getCode());
			}
				
			List<ProductData> p =  siteOneProductFacade.getCSPPriceListForRecommendedProducts(productList,inventoryUomId);
			if(p.get(0).getCustomerPrice()!=null)
			{			
			  savedListEntryUpdatedUOMData.setCustomerPrice(p.get(0).getCustomerPrice());
			}
			else 
			{
			  savedListEntryUpdatedUOMData.setCustomerPrice(p.get(0).getPrice());
			}
			
			LOG.info("Customer Price :"+savedListEntryUpdatedUOMData.getCustomerPrice());
			
			SavedListEntryUpdatedUOMDataDTO savedListEntryUpdatedUOMDataDTO=new SavedListEntryUpdatedUOMDataDTO();
			savedListEntryUpdatedUOMDataDTO.setCustomerPrice(savedListEntryUpdatedUOMData.getCustomerPrice());
			
			
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(SavedListEntryUpdatedUOMData.class, SavedListEntryUpdatedUOMDataDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();

			savedListEntryUpdatedUOMDataDTO = mapper.map(savedListEntryUpdatedUOMData, SavedListEntryUpdatedUOMDataDTO.class);
			
			return savedListEntryUpdatedUOMDataDTO;
	}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method updateUOMforEntries");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@DeleteMapping("/deleteList")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "deleteList", summary = "Delete the selecetd list", description = "Delete the selecetd list for the loggedin user")
	public String deleteSavedList(@RequestParam(value = "code", required = true) final String code)
	{
		try
		{
			siteoneSavedListFacade.deleteSavedList(code);
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method deleteSavedList");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/listExists")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "listExists", summary = "Validate list name exists", description = "Validate list name exists")
	public boolean checkListExists(@RequestParam("wishListName") final String wishListName)
	{
		try
		{
			return siteoneSavedListFacade.isListWithNameExist(wishListName, false);
        }
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method checkListExists");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/displayProduct")
	@ApiBaseSiteIdParam
	@ResponseBody
	public ProductWsDTO getProductToDisplay(@RequestParam(value = "productCodes", required = true)	final String productCodes,
			@RequestParam(value = "storeId", required = true)	final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
	{
		
			try
			{
			    ProductWsDTO productDTO = null;
				if (productCodes != null)
				{
				final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
				storeSessionFacade.setSessionStore(pointOfServiceData);		
				final B2BUnitData unit;
				if (!StringUtils.isEmpty(unitId))
				{
					unit = b2bUnitFacade.getUnitForUid(unitId);
				} else {
					unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
				}
				storeSessionFacade.setSessionShipTo(unit);
				
				ProductData productData = new ProductData();
				MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
				mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
			    MapperFacade mapper = mapperFactory.getMapperFacade();
			    
				productData = siteoneSavedListFacade.getProductDatatoDisplay(productCodes.trim());
				productDTO = mapper.map(productData, ProductWsDTO.class);
				}
				return productDTO;
			}		
			catch (final UnknownIdentifierException e)
			{
				LOG.error(e);
		        return null;
            }
			catch (final Exception ex)
			{
				LOG.error(ex);
				throw new RuntimeException("Exception occurred while calling through method getProductToDisplay");
			}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/listDetails")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "listDetails", summary = "Fetch the saved list Details", description = "Fetch the saved list Details")
	public SavedListDetailsDTO getDetailsPage(@RequestParam(value = "code", required = true) final String code,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode, 
			@RequestParam(value = "page", defaultValue = "0", required = false) final int page, 
			@RequestParam(value = "pagesize", required = false)	final String pageSize, 
			@RequestParam(value = "sortOrder", defaultValue = DESCENDING_ORDER, required = false) final String sortOrder,
			@RequestParam(value = "storeId", required = true)	final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
	{
		try
		{
			SavedListDetailsDTO listDetailDTO = new SavedListDetailsDTO();
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);		
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			
			
			int viewByPageSize = siteConfigService.getInt("siteone.savedlistentrydefault.pageSize", 50);
			if (null != pageSize)
			{
				viewByPageSize = Integer.parseInt(pageSize);
			}
			
			final SavedListData savedListData = siteoneSavedListFacade.getDetailsPage(code);

			if (null != savedListData && CollectionUtils.isNotEmpty(savedListData.getRemovedProductNameList()))
			{
				listDetailDTO.setRemovedProducts(savedListData.getRemovedProductNameList());
			}
			final PageableData pageableData = createPageableData(page, viewByPageSize, null, ShowMode.Page);
			final SearchPageData searchPageData = siteoneSavedListFacade.getPagedDetailsPage(pageableData, code);
			final List<SavedListEntryData> savedListEntryData = searchPageData.getResults();
			if (null != savedListEntryData && !savedListEntryData.isEmpty())
			{
				siteoneSavedListFacade.setPriceForEntryData(savedListData.getCustomerPriceData(),savedListEntryData,savedListData.getRetailPriceData());
				final int savedListLen = savedListEntryData.size();
				for (int i = 0; i < savedListLen; i++)
				{
					final ProductData productData = savedListEntryData.get(i).getProduct();
					final ProductData productDataUpdated = siteOneProductFacade.updateUOMPriceForSingleUOM(productData);

					final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();
					if (inventoryUOMListData != null && inventoryUOMListData.size() > 1) {
						siteoneSavedListFacade.updatePriceBasedOnUOM(savedListEntryData.get(i));
					}
						
					savedListEntryData.get(i).setProduct(productDataUpdated);
					savedListEntryData.get(i).setTotalPrice(siteoneSavedListFacade.setItemTotal(savedListEntryData.get(i)));
				}
			}
			
			listDetailDTO.setSortOrder(sortOrder);
			List<CustomerPriceData> siteOneCSPList = null;
			if (null != savedListData)
			{
				savedListData.setListTotalPrice(siteoneSavedListFacade.setListTotal(savedListEntryData));
				siteOneCSPList = siteoneSavedListFacade.updatePriceListBasedOnUOM(savedListData, savedListEntryData);
			}
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(SavedListEntryData.class,SavedListEntryDTO.class);
			List<SavedListEntryDTO> savedListEntryDTO = mapper.mapAsList(savedListEntryData, SavedListEntryDTO.class);
			listDetailDTO.setSavedListEntryData(savedListEntryDTO);
			mapperFactory.classMap(SavedListData.class,SavedListDataDTO.class);
		    SavedListDataDTO savedListDTO = mapper.map(savedListData, SavedListDataDTO.class);
			listDetailDTO.setSavedListData(savedListDTO);
			listDetailDTO.setSiteOneCSPList(siteOneCSPList);
			final boolean isAssembly = false;
			final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
			int i = 0;
			for (final SavedListData data : allWishlist)
			{
				if (data.getCode().equals(code))
				{
					allWishlist.remove(i);
					break;
				}
				i++;
			}
			mapperFactory.classMap(SavedListData.class,SavedListDataDTO.class);
			List<SavedListDataDTO> savedListDTOAll = mapper.mapAsList(allWishlist, SavedListDataDTO.class);
			listDetailDTO.setSavedLists(savedListDTOAll);
			final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
			listDetailDTO.setNumberPagesShown(numberPagesShown);
			SearchPageListProductWsDTO searchPageDTO = new SearchPageListProductWsDTO();
			searchPageDTO.setPagination(searchPageData.getPagination());
			searchPageDTO.setSorts(searchPageData.getSorts());
			searchPageDTO.setResults(savedListEntryDTO);
			listDetailDTO.setSearchPageData(searchPageDTO);
			return listDetailDTO;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getDetailsPage");
	    }
		
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/addSelectedItemsToCart")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "addSelectedItemsToCart", summary = "Add the selected products in cart", description = "Add the selected products in cart")
	public boolean addSelectedProductsFromListToCart(@RequestParam("wishListCode") final String wishListCode, 
			@RequestParam("productCodes") final String productCodes,
			@RequestParam(value = "storeId") final String storeId) throws CommerceCartModificationException
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(null);
			final List<String> products = new ArrayList<String>(Arrays.asList(productCodes.split(" ")));
			final boolean quantityFlag = siteoneSavedListFacade.addSelectedProductToCart(wishListCode, products);
			return quantityFlag;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method addSelectedProductsFromListToCart");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/addListToCart")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "addListToCart", summary = "Add the the products in cart from seelcted list", description = "Add the the products in cart from seelcted list")
	public boolean addProductsFromListToCart(@RequestParam("wishListCode") final String wishListCode,
			@RequestParam(value = "storeId") final String storeId) throws CommerceCartModificationException
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			((SiteOneCartFacade) cartFacade).restoreSessionCart(null);
			final boolean quantityFlag = siteoneSavedListFacade.addListToCart(wishListCode);
			return quantityFlag;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method addProductsFromListToCart");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/moveSavedList")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "moveSavedList", summary = "Remove the selected products from list", description = "Remove the selected products from list")
	protected String moveToSaveList(@RequestParam("productCode") final String productCode, 
			@RequestParam("wishListCode") final String wishListCode)
	{
		try
		{
			final boolean isAssembly = false;
			siteoneSavedListFacade.moveToSaveList(wishListCode, productCode, isAssembly);
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method moveToSaveList");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/add")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "addProduct", summary = "Add the the products in the list", description = "Add the the products in the list")
	protected String addtoWishlist(@RequestParam("productCode") final String productCode, 
			@RequestParam(value = "quantity", required = false) final String quantity, 
			@RequestParam("wishListCode") final String wishListCode,
			@RequestParam(value = "prodQtyFlag", defaultValue = "false", required = false) final boolean prodQtyFlag, 
			@RequestParam(value = "inventoryUOMId", required = false) final String inventoryUOMId)
	{
		WishlistAddData wishlistAddResponseData = new WishlistAddData();
		try
		{
			wishlistAddResponseData = siteoneSavedListFacade.addtoWishlist(productCode, quantity, wishListCode, prodQtyFlag,
					inventoryUOMId);
			Gson gson = new Gson();
			return gson.toJson(wishlistAddResponseData.getMessage());
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.debug("Product Code not present: " + productCode);
			LOG.error(e.getMessage(), e);
	        throw new RuntimeException("Exception occurred while calling through method addtoWishlist");
        }
		catch (final Exception ex)
		{
			LOG.error(ex);
			throw new RuntimeException("Exception occurred while calling through method addtoWishlist");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/clearQuantities")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "clearQuantities", summary = "Clear the product qty in list", description = "Clear the product qty in list")
	public String clearQuantitiesFromList(@RequestParam("wishListCode")	final String wishListCode) throws CommerceCartModificationException
	{
		try
		{
			siteoneSavedListFacade.clearQuantities(wishListCode);
			Gson gson = new Gson();
			return gson.toJson("Success");
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method clearQuantitiesFromList");
	    }
		
	}

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/updateProductQuantity")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "updateProductQuantity", summary = "Update the product qty in list", description = "Update the product qty in list")
	protected boolean updateProductQuantity(@RequestParam("productCode") final String productCode, 
			@RequestParam(value = "quantity", required = false)	final String quantity, 
			@RequestParam("wishListCode") final String wishListCode) throws CommerceCartModificationException
	{
		try
		{
			return siteoneSavedListFacade.updateProductQuantity(productCode, quantity, wishListCode);

		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method updateProductQuantity");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/getShareListUsers")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "getShareListUsers", summary = "Get the users for share a list", description = "Get the users for share a list")
	public List<ShareCustomerData> getShareListUsers(@RequestParam("searchTerm") final String searchTerm, 
			@RequestParam(value = "searchType", defaultValue = "Name", required = false) final String searchType) throws JsonProcessingException
	{
		try
		{
			final List<CustomerData> customerList = siteoneSavedListFacade.getAllCustomersForSharedList(searchTerm, searchType);
			final CustomerData customerData = customerFacade.getCurrentCustomer();

			final List<ShareCustomerData> userList = new ArrayList<ShareCustomerData>();

			for (final CustomerData customer : customerList)
			{
				if (!(customer.getUid().equalsIgnoreCase(customerData.getUid())) && (customer.isActive() == true))
				{
					final ShareCustomerData user = new ShareCustomerData();
					user.setText(customer.getName());
					user.setValue(customer.getUid());
					userList.add(user);
				}
			}

			return userList;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method getShareListUsers");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/shareListEmail/{savedlistcode}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "shareListEmail", summary = "Mail the list to users ", description = "Mail the list to users ")
	public String sharedListEmail(@PathVariable("savedlistcode") final String code,
			@RequestParam(value = "email", required = true) final String emails,
			@RequestParam(value = "customerPrice", required = false) final boolean customerPrice,
			@RequestParam(value = "retailPrice", required = false) final boolean retailPrice,
			@RequestParam(value = "unitId", required = false) final String unitId,
			@RequestParam(value = "storeId", required = true) final String storeId)
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			B2BUnitData unit = null;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			final String[] emailList = emails.split(",");

			siteoneSavedListFacade.shareListEmail(code, customerPrice, retailPrice, emailList);
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method sharedListEmail");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/shareList")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "shareList", summary = "Share the list  with ither users", description = "Share the list  with ither users")
	public String saveShareListData(@RequestParam(value = "users", required = true) final String users,
			@RequestParam(value = "code", required = true) final String code,
			@RequestParam(value = "note", required = false) final String note,@RequestParam(value = "isViewEdit", required = false) final boolean isViewEdit)
	{
		try
		{
			final boolean isAssembly = false;
			siteoneSavedListFacade.saveShareListUser(note, users, code, isAssembly,isViewEdit);
			LOG.error("Inside viewEdit "+isViewEdit );
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method saveShareListData");
	    }
	}

	@GetMapping("/editList")
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "editList", summary = "Edit the list details", description = "Edit the list details")
	public SiteOneEditSavedListForm editList(@RequestParam(value = "code") final String code,
			@RequestParam(value = "storeId") final String storeId)
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			final SavedListData savedListData = siteoneSavedListFacade.getDetailsPage(code);
			final SiteOneEditSavedListForm siteOneEditSavedListForm = new SiteOneEditSavedListForm();
			siteOneEditSavedListForm.setName(savedListData.getName());
			siteOneEditSavedListForm.setDescription(savedListData.getDescription());
			siteOneEditSavedListForm.setListName(savedListData.getName());
			siteOneEditSavedListForm.setOwner(savedListData.getUser());
			siteOneEditSavedListForm.setCode(savedListData.getCode());
			return siteOneEditSavedListForm;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method editList");
	    }
	}

	@PostMapping("/editList")
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "editList", summary = "Edit the list details", description = "Edit the list details")
	public String editSavedList(@Parameter(description = "Edit List object.", required = true) @RequestBody SiteOneEditSavedListForm siteOneEditSavedListForm)
	{
		try
		{
			PointOfServiceData sessionPos = ((SiteOneCustomerFacade) customerFacade).getPreferredStore();

			final SavedListData savedListData = new SavedListData();
			savedListData.setName(siteOneEditSavedListForm.getName());

			savedListData.setDescription(siteOneEditSavedListForm.getDescription());
			savedListData.setCode(siteOneEditSavedListForm.getCode().replaceAll(",", ""));
			final boolean isAssembly = false;
			final boolean flag = siteoneSavedListFacade.updateSavedList(savedListData, siteOneEditSavedListForm.getListName(),
					isAssembly);
			Gson gson = new Gson();
			if (!flag)
			{
				return gson.toJson(DUPLICATE_LIST_ERROR);
			}
			eventService.publishEvent(initializeEvent(new ListEditEvent(), siteOneEditSavedListForm, sessionPos));
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method editSavedList");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/removeProducts")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "removeProducts", summary = "remove the selecetd products from list", description = "remove the selecetd products from list for the loggedin user")
	public String removeSelectedProductsFromSavedList(@RequestParam(value = "wishListCode", required = true) final String wishListCode, 
			@RequestParam(value = "productCodeList", required = true) final String productCodeList)
	{
		try
		{
			final List<String> products = new ArrayList<String>(Arrays.asList(productCodeList.split(" ")));
			for (final String product : products)
			{
				if (StringUtils.isNotEmpty(product))
				{
					siteoneSavedListFacade.moveToSaveList(wishListCode, product, false);
				}
			}
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method removeSelectedProductsFromSavedList");
	    }
		
	}
	
	@ResponseBody
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ApiBaseSiteIdParam
	@PostMapping(value = "/addSelectedToNewWishlist")
	@Operation(operationId = "addSelectedProductNewList", summary = "Add the selected product in new list", description = "Add the selected product in new create list")
	public String addSelectedToNewWishlist(@RequestParam("wishListName")
	final String wishListName, @RequestParam("productCodes") final String productCodes, @RequestParam("currentWishlist") final String currentWishlist)
	{
		try
		{
			String response =  siteoneSavedListFacade.addSelectedToNewWishlist(wishListName, productCodes, currentWishlist, false);
			Gson gson = new Gson();
			return gson.toJson(response);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method addSelectedToNewWishlist");
	    }
		
	}
	
	@ResponseBody
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ApiBaseSiteIdParam
	@PostMapping(value = "/createWishlistAndFetch")
	@Operation(operationId = "createWishlistAndFetch", summary = "Add the recommended products in new list", description = "Add the recommended products in new create list")
	public String createWishlistAndFetch(@RequestParam("wishListName")
	final String wishListName) 
	{
		try
		{
			final CreateWishlistAndResponseAllData wishlistResponseData = new CreateWishlistAndResponseAllData();
			final boolean flag = siteoneSavedListFacade.checkDuplicate(wishListName, false);
			wishlistResponseData.setIsDuplicate(Boolean.valueOf(flag));
			final boolean isAssembly = false;
			if (!flag)
			{
				final SavedListData savedListData = new SavedListData();
				savedListData.setName(wishListName);
				String wishListCode = "";
				wishListCode = siteoneSavedListFacade.createSavedList(savedListData, null, isAssembly);
			}
			final List<SavedListData> allWishlist = siteoneSavedListFacade.getAllSavedListForEdit(isAssembly);
			List<SavedListData> newList =new ArrayList<>();
			if(allWishlist!=null) {
				newList.add(allWishlist.get(0));
			}
			wishlistResponseData.setAllWishlist(newList);
			Gson gson = new Gson();
			return gson.toJson(wishlistResponseData);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method createWishlistAndFetch");
	    }
		
	}

	@ResponseBody
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@ApiBaseSiteIdParam
	@PostMapping(value = "/addSelectedToSavedWishlist")
	@Operation(operationId = "addSelectedToSavedWishlist", summary = "Add the selected product in saved list", description = "Add the selected product in existing saved list")
	public String addSelectedToSavedWishlist(@RequestParam("wishListCode")
	final String wishListCode, @RequestParam(value="productItemNumbers",required = false) final String productItemNumbers, @RequestParam(value="currentWishlist",required = false) final String currentWishlist,@RequestParam(value="categoryName",required = false)final String categoryName)
	{	
		List<String> products = new ArrayList<>();
		if (StringUtils.isEmpty(categoryName))
		{
			products = Arrays.asList(productItemNumbers.split(" "));
		}
		else
		{
			sessionService.setAttribute("RecommendedDetailsPage", categoryName);
			final int viewByPageSize = getSiteConfigService().getInt("siteone.reccomendedlistentrydefault.pageSize.Mobile", 50);
			final String productList = performSearchForCategory(null, 0, ShowMode.Page, null, viewByPageSize);
			sessionService.setAttribute("RecommendedDetailsPage", "");
			products = Arrays.asList(productList.split(","));
		}

		final List<WishlistAddData> wishlistAddResponseDataList = new ArrayList<>();

		for (final String product : products)
		{
			try
			{
				if (StringUtils.isNotEmpty(product))
				{
					final String[] arr = product.split(SiteoneFacadesConstants.PIPE);
					if (arr.length > 2)
					{
						wishlistAddResponseDataList.add(siteoneSavedListFacade.addtoWishlist(arr[SiteoneFacadesConstants.ZERO],
								arr[SiteoneFacadesConstants.ONE], wishListCode, false, arr[SiteoneFacadesConstants.TWO]));
					}
					else
					{
						wishlistAddResponseDataList.add(siteoneSavedListFacade.addtoWishlist(arr[SiteoneFacadesConstants.ZERO],
								arr[SiteoneFacadesConstants.ONE], wishListCode, false, null));
					}
					if (StringUtils.isNotEmpty(currentWishlist))
					{
					siteoneSavedListFacade.moveToSaveList(currentWishlist, arr[SiteoneFacadesConstants.ZERO], false);
					}
				}
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error(e.getMessage(), e);
		        throw new RuntimeException("Exception occurred while calling through method addSelectedToSavedWishlist");
            }
			catch (final Exception ex)
			{
				LOG.error(ex);
				throw new RuntimeException("Exception occurred while calling through method addSelectedToSavedWishlist");
			}
		}
		Gson gson = new Gson();
		return gson.toJson(ADD_SELECTED_EXSITING_LIST);
	}

	private ListEditEvent initializeEvent(final ListEditEvent listEditEvent,
			final SiteOneEditSavedListForm siteOneEditSavedListForm, final PointOfServiceData store)
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		listEditEvent.setListCode(siteOneEditSavedListForm.getCode());
		listEditEvent.setListName(siteOneEditSavedListForm.getListName());
		listEditEvent.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		listEditEvent.setUpdateListName(siteOneEditSavedListForm.getName());
		listEditEvent.setEmailAddress(customerData.getUid());
		listEditEvent.setStoreAddress(store.getAddress().getTown());
		listEditEvent.setStoreId(store.getStoreId());
		listEditEvent.setContactNumber(store.getAddress().getPhone());
		listEditEvent.setCustomerName(customerData.getFirstName());
		listEditEvent.setLanguage(commonI18NService.getCurrentLanguage());
		return listEditEvent;
	}
	
	public ShareListEvent initializeEvent(final ShareListEvent event, final String code, final String email, final String userName,
			final String listName)
	{
		event.setBaseStore(baseStoreService.getBaseStoreForUid("siteone"));
		event.setSite(baseSiteService.getBaseSiteForUID("siteone"));
		event.setCurrency(commonI18NService.getCurrentCurrency());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		event.setListCode(code);
		event.setListName(listName);
		event.setUserName(userName);
		event.setEmail(email);

		return event;
	}
	
	
	protected int getSearchPageSize()
	{
		return siteConfigService.getInt("storefront.savedList.search.pageSize", 0);
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
	
	protected List<RecommendedListData> performSearch(final String searchQuery, final int page, final ShowMode showMode,
			final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> buyAgainData = buyItAgainSearchFacade
				.buyItAgainSearch(searchState, pageableData);
		final Map<String, Long> productCountCategoryMap = getProductCountForCategory(buyAgainData);
		return populateRecommendedList(buyAgainData, productCountCategoryMap);
	}
	protected List<RecommendedListData> populateRecommendedList(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData,
			final Map<String, Long> productCountCategoryMap)
	{
		final List<RecommendedListData> list = new ArrayList<>();
		if (!searchPageData.getResults().isEmpty())
		{
			for (final BuyItAgainData buyAgainDate : searchPageData.getResults())
			{
				if (buyAgainDate.getCategoryName() != null && !buyAgainDate.getCategoryName().isEmpty())
				{
					final RecommendedListData recommendedList = new RecommendedListData();
					recommendedList.setCategoryName(buyAgainDate.getCategoryName());
					recommendedList.setModifiedTime(buyAgainDate.getLastPurchasedDate());
					recommendedList.setProductCount(productCountCategoryMap.get(buyAgainDate.getCategoryName()));
					list.add(recommendedList);
				}
			}
		}

		return list;
	}
	
	protected Map<String, Long> getProductCountForCategory(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{
		final Map<String, Long> productCountCategoryMap = new HashMap<>();
		final List<FacetData<SearchStateData>> facets = searchPageData.getFacets();
		if (CollectionUtils.isNotEmpty(facets))
		{
			for (final FacetData<SearchStateData> facetData : facets)
			{
				if (facetData.getCode().equalsIgnoreCase("soCategoryName"))
				{
					final List<FacetValueData<SearchStateData>> topFacetValueDatas = facetData.getTopValues();
					if (CollectionUtils.isNotEmpty(topFacetValueDatas))
					{
						processFacetDataForCategory(topFacetValueDatas, productCountCategoryMap);
					}
					final List<FacetValueData<SearchStateData>> facetValueDatas = facetData.getValues();
					if (CollectionUtils.isNotEmpty(facetValueDatas))
					{
						processFacetDataForCategory(facetValueDatas, productCountCategoryMap);
					}
				}
			}
		}
		return productCountCategoryMap;
	}
	protected void processFacetDataForCategory(final List<FacetValueData<SearchStateData>> facetValueDatas,
			final Map<String, Long> productCountCategoryMap)
	{
		final int viewByPageSize = getSiteConfigService().getInt("siteone.reccomendedlistentrydefault.pageSize.Mobile", 50);
		for (final FacetValueData<SearchStateData> facetValueData : facetValueDatas)
		{
			if (facetValueData.getCount() <= viewByPageSize)
			{
				productCountCategoryMap.put(facetValueData.getCode(), facetValueData.getCount());
			}
			else
			{
				productCountCategoryMap.put(facetValueData.getCode(), Long.valueOf(viewByPageSize));
			}
		}
	}
	
	protected String performSearchForCategory(final String searchQuery, final int page, final ShowMode showMode,
			final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> buyAgainData = buyItAgainSearchFacade
				.buyItAgainSearch(searchState, pageableData);

		return populateProductCodes(buyAgainData);
	}

	protected String populateProductCodes(final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{
		final StringBuilder sb = new StringBuilder();
		if (!searchPageData.getResults().isEmpty())
		{
			int count = 0;
			for (final BuyItAgainData buyAgainData : searchPageData.getResults())
			{
				if (buyAgainData.getProductItemNumber() != null && !buyAgainData.getProductItemNumber().isEmpty())
				{
					if (count >= 1)
					{
						sb.append(",").append(buyAgainData.getProductItemNumber()).append("|").append("1");
					}
					else
					{
						sb.append(buyAgainData.getProductItemNumber()).append("|").append("1");
					}
					count++;
				}
			}
		}
		return sb.toString();
	}
	
	protected List<ProductData> getBuyAgainProductList(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
	{   
		
		final List<ProductData> productList = new ArrayList<ProductData>();
		
		if (null != searchPageData.getResults() && !searchPageData.getResults().isEmpty())
		{
			for (final BuyItAgainData buyItAgainData : searchPageData.getResults())
			{
				if (null != buyItAgainData.getProductCode())
				{
					final ProductData product = productFacade.getProductForCodeAndOptions(buyItAgainData.getProductCode(),
							Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
									ProductOption.STOCK, ProductOption.PRICE_RANGE, ProductOption.PROMOTIONS));
					product.setPurchasedCount(Integer.valueOf(buyItAgainData.getPurchasedCount()));
					product.setName(buyItAgainData.getProductDescription());
					product.setOrderCount(Integer.valueOf(buyItAgainData.getOrderCount()));
					product.setPurchasedQuantity(Integer.valueOf(buyItAgainData.getPurchasedQuantity()));
					siteOneProductFacade.setCategoriesForProduct(product);
					
					LOG.info("Product data is :" + product.getCode());
					
					if (!product.getIsProductDiscontinued()  && product.getPrice()!=null)
					{
						productList.add(product);
					}

					if (product.getPrice() == null)
					{ 
						LOG.info("buy again product price is null :" + product.getCode() + ", order number: "
								+ buyItAgainData.getOrderNumber());
					}
				}
			}
			siteOneProductFacade.getCSPPriceListForProducts(searchPageData.getResults(), productList);
			siteOneProductFacade.populateAvailablityForBuyAgainPageProducts(productList);
		}
		return productList;
	}	
	protected BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> performDetailSearch(final String searchQuery,
			final int page, final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return buyItAgainSearchFacade.buyItAgainSearch(searchState, pageableData);
	}

}
