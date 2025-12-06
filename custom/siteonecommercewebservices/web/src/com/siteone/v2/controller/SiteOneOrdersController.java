package com.siteone.v2.controller;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.order.OrderHistoryWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.order.OrderWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import com.siteone.facade.MasterHybrisOrderData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.security.core.server.csi.XSSEncoder;
import com.sap.security.core.server.csi.util.URLDecoder;
import com.siteone.commerceservices.buyagain.data.BuyAgainProductSearchDTO;
import com.siteone.commerceservices.buyagain.data.BuyAgainSearchResultDTO;
import com.siteone.commerceservices.search.facetdata.ProductCategorySearchPageDTO;
import com.siteone.commerceservices.dto.order.MasterHybrisOrderWsDTO;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.order.SiteOneOrderFacade;
import com.siteone.facades.buyitagain.search.facade.BuyItAgainSearchFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.utils.XSSFilterUtil;
import com.siteone.v2.controller.AbstractSearchPageController;
import com.siteone.commerceservices.order.data.OrdersResponseWsDTO;
import com.siteone.commerceservices.order.data.SearchPageOrdersWsDTO;
import com.siteone.facade.email.SiteOneShareEmailFacade;
import com.siteone.commerceservices.dto.order.OrderHistoryListData;
import com.siteone.commerceservices.dto.order.OrderListData;
import com.siteone.commerceservices.dto.order.RecentOrdersWsDTO;
import com.siteone.commerceservices.dto.order.ConsignmentEntriesWsDTO;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercewebservicescommons.dto.order.ConsignmentWsDTO;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercewebservicescommons.dto.order.ConsignmentEntryWsDTO;
import com.siteone.commerceservices.dto.order.ConsignmentFulfillmentEntriesWsDTO;


/**
 * @author pelango
 *
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/my-account")
@Tag(name = "Siteone Order History")
public class SiteOneOrdersController extends AbstractSearchPageController 
{

	private static final String PAGINATION_NUMBER_OF_RESULTS_COUNT = "pagination.number.results.count";
	private static final String FACET_SEPARATOR = ":";
	

	private static final Logger LOG = Logger.getLogger(SiteOneOrdersController.class);

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
	@Resource(name = "buyItAgainSearchFacade")
	private BuyItAgainSearchFacade buyItAgainSearchFacade;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	
	@Resource(name = "siteOneShareEmailFacade")
	private SiteOneShareEmailFacade siteOneShareEmailFacade;
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/buy-again/{accountUnitId}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "buyAgain", summary = "Get the purchased products for loggedin user", description = "Get the purchased products for loggedin user")
	public BuyAgainSearchResultDTO refineSearch(@RequestParam(value = "q", required = false) final String searchQuery, 
			@RequestParam(value = "page", defaultValue = "0") final int page, 
			@PathVariable("accountUnitId")	final String accountUnitId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode, 
			@RequestParam(value = "searchParam", required = false) final String searchParam, 
			@RequestParam(value = "sort", required = false)	final String sortCode, 
			@RequestParam(value = "pagesize", required = false)	final String pageSize, 
			@RequestParam(value = "storeId") final String storeId)
	{
		try 
		{
			final String sanitizedquery = XSSFilterUtil.filter(searchQuery);
			return performBuyItAgainSearch(pageSize, true, null, sanitizedquery, page, showMode, sortCode, unitId, storeId);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method refineSearch");
	    }
		
	}

	public BuyAgainSearchResultDTO performBuyItAgainSearch(final String pageSize, final boolean qParam,
			final String searchParam, final String searchQuery, final int page, final ShowMode showMode, final String sortCode,
			final String unitId, final String storeId)
	{
		BuyAgainSearchResultDTO searchResult= new BuyAgainSearchResultDTO();
		Boolean quoteFeature = false;
		final B2BUnitData unit;
		if (!StringUtils.isEmpty(unitId))
		{
			unit = b2bUnitFacade.getUnitForUid(unitId);
		} else {
			unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		}
		final B2BUnitModel unit_Model = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
		if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
				|| (null != unit_Model && null != unit_Model.getUid()
						&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit_Model.getUid())))
		{
			quoteFeature = true;
		}
		storeSessionFacade.setSessionShipTo(unit);
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);
		
		int viewByPageSize = siteConfigService.getInt("siteoneorgaddon.search.pageSize", 10);
		if (null != pageSize)
		{
			viewByPageSize = Integer.parseInt(pageSize);
		}
		BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData = null;
		try
		{
			String trimmedSearchParam = null;
			if (null != searchParam)
			{
				trimmedSearchParam = searchParam.trim();
			}
			if (qParam)
			{
				searchPageData = performSearch(searchQuery, page, showMode, sortCode, viewByPageSize);

			}
			else
			{
				searchPageData = performSearch(trimmedSearchParam, page, showMode, sortCode, viewByPageSize);
			}

		}
		catch (final ConversionException e) // NOSONAR
		{
			// nothing to do - the exception is logged in SearchSolrQueryPopulator
		}
		
		final List<ProductData> productList = getBuyAgainProductList(searchPageData);
		final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);
		BuyAgainProductSearchDTO searchPageDTO =new BuyAgainProductSearchDTO();
		if(searchPageData!=null){
			prepareProductSearchDTO(searchPageDTO,searchPageData);
		}
		searchResult.setNumberPagesShown(numberPagesShown);
		searchResult.setSearchPageData(searchPageDTO);
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    List<ProductWsDTO> productListDTO = mapper.mapAsList(productList, ProductWsDTO.class);
		searchResult.setProductList(productListDTO);		
		searchResult.setViewByPageSize(viewByPageSize);
		searchResult.setSearchParam(searchParam);
		searchResult.setIsQuotesEnabled(quoteFeature);
		return searchResult;
	}

	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/ordersHistory/{unitId}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "orderHistory", summary = "Get the list of orders", description = "Get the list of orders")
	public OrdersResponseWsDTO myOrders(@RequestParam(value = "page", defaultValue = "0") final int page, 
			@PathVariable("unitId") final String unitId, 
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode, 
			@RequestParam(value = "searchParam", required = false) final String searchParam, 
			@RequestParam(value = "oSearchParam", required = false)final String oSearchParam, 
			@RequestParam(value = "iSearchParam", required = false)final String iSearchParam, 
			@RequestParam(value = "pnSearchParam", required = false)final String pnSearchParam,
			@RequestParam(value = "sort", required = false) final String sortCode, 
			@RequestParam(value = "pagesize", required = false) final String pageSize, 
			@RequestParam(value = "dateSort", required = false) final String dateSort, 
			@RequestParam(value = "datarange", required = false) final String datarange, 
			@RequestParam(value = "accountShiptos", required = false) final String accountShiptos,
			@RequestParam(value = "paymentType", required = false) final String paymentType,
			@RequestParam(value = "storeId") final String storeId)
	{
		try
		{
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			
			final String sanitizedunitId = XSSFilterUtil.filter(unitId);
			int viewByPageSize = siteConfigService.getInt("siteoneorgaddon.search.pageSize", 10);
			
			final Calendar f = Calendar.getInstance();
			f.setTime(new Date());
			f.add(Calendar.MONTH, -3);

			String daysArgs = null;
			daysArgs = datarange;

			String trimmedSearchParam = null;
			String orderSearchParam = null;
			String invoiceSearchParam = null;
			String poNumberSearchParam = null;
			if (null != searchParam)
			{
				trimmedSearchParam = searchParam.trim();
			}
			
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
			
			if (null != pageSize)
			{
				viewByPageSize = Integer.parseInt(pageSize);
			}		
			else
			{
				viewByPageSize = Integer.parseInt("10");
			}
			final PageableData pageableData = createPageableData(page, viewByPageSize, sortCode, showMode);

			SearchPageData<OrderHistoryData> searchPageData = null;
			if (StringUtils.isNotBlank(accountShiptos))
			{
				final String trimmedInvoiceShiptos = accountShiptos.trim();
				final String[] shipToNumber = trimmedInvoiceShiptos.split("\\s+");
				final String shipToUid = shipToNumber[0];

				searchPageData = ((SiteOneOrderFacade) orderFacade).getPagedOrderHistoryForStatuses(pageableData, page, 
						shipToUid.concat("_US"), trimmedSearchParam, orderSearchParam, invoiceSearchParam, poNumberSearchParam, dateSort, paymentType, null ,sortCode);
			}	
			else
			{
				searchPageData = ((SiteOneOrderFacade) orderFacade).getPagedOrderHistoryForStatuses(pageableData, page, 
						sanitizedunitId, trimmedSearchParam, orderSearchParam, invoiceSearchParam, poNumberSearchParam, dateSort, paymentType, null, sortCode);
			}
			
			final B2BUnitModel parentUnit = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
			final List<B2BUnitData> childs = ((SiteOneB2BUnitFacade) b2bUnitFacade).getShipTosForUnit(parentUnit.getUid());
			final Map<String, String> shipTosList = new LinkedHashMap<>();
			final Map<String, String> shipToListUpdated = new LinkedHashMap<>();
			if (accountShiptos != null && !accountShiptos.isEmpty() && !accountShiptos.equalsIgnoreCase("All"))
			{
				final B2BUnitModel b2BUnitModel = (B2BUnitModel) b2bUnitService.getUnitForUid(sanitizedunitId);
				final String shipToNameUnitID = b2BUnitModel.getUid().split("_US")[0] + ' ' + b2BUnitModel.getName();
				shipTosList.put(b2BUnitModel.getUid(), shipToNameUnitID);
			}
			for (final B2BUnitData child : childs)
			{
				final String shipToID = child.getUid().substring(0, child.getUid().indexOf("_US"));
				final String shipToNameID = child.getUid().substring(0, child.getUid().indexOf("_US")) + " " + child.getName();

				shipToListUpdated.put(shipToID, shipToNameID);
			}
			shipTosList.putAll(shipToListUpdated);
			final Set<String> listOfShipTos = new LinkedHashSet<String>(shipTosList.values());
			final int numberPagesShown = siteConfigService.getInt(PAGINATION_NUMBER_OF_RESULTS_COUNT, 5);

			SearchPageOrdersWsDTO searchPageDTO = new SearchPageOrdersWsDTO();
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			mapperFactory.classMap(OrderHistoryData.class, OrderHistoryWsDTO.class);
		    MapperFacade mapper = mapperFactory.getMapperFacade();
		    List<OrderHistoryWsDTO> dto = mapper.mapAsList(searchPageData.getResults(), OrderHistoryWsDTO.class);
			searchPageDTO.setPagination(searchPageData.getPagination());
			searchPageDTO.setResults(dto);
			searchPageDTO.setSorts(searchPageData.getSorts());
			Boolean quoteFeature = false;
			final B2BUnitModel unit = ((SiteOneB2BUnitService) b2bUnitService).getDefaultUnitForCurrentCustomer();
			if (siteOneFeatureSwitchCacheService.isAllPresentUnderFeatureSwitch("QuotesFeatureSwitch")
					|| (null != unit && null != unit.getUid()
							&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuotesFeatureSwitch", unit.getUid())))
			{
				quoteFeature = true;
			}
			OrdersResponseWsDTO result = new OrdersResponseWsDTO();
			result.setDaysArgs(daysArgs);
			result.setDateSort(dateSort);
			result.setListOfShipTos(listOfShipTos);
			result.setViewByPageSize(viewByPageSize);
			result.setSortCode(sortCode);
			result.setSearchParam(trimmedSearchParam);
			result.setOSearchParam(orderSearchParam);
			result.setISearchParam(invoiceSearchParam);
			result.setPnSearchParam(poNumberSearchParam);
			result.setSearchPageData(searchPageDTO);
			result.setUnitId(sanitizedunitId);
			result.setNumberPagesShown(numberPagesShown);
			result.setAccountShipTos(accountShiptos);
			result.setIsQuotesEnabled(quoteFeature);
			return result;
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method myOrders");
	    }
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/masterOrder/{unitId}/{orderCode}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "masterOrder", summary = "Get the Master Order", description = "Get the master order for the consignment")
	public MasterHybrisOrderWsDTO getMasterOrder(@PathVariable("orderCode")
	final String orderCode,	@PathVariable("unitId")
	final String unitId,@RequestParam(value = "storeId") final String storeId)	throws CMSItemNotFoundException {
		
		try {
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			final String sanitizedorderCode = XSSFilterUtil.filter(orderCode);
			MasterHybrisOrderData masterHybrisOrderData = new MasterHybrisOrderData();
			MasterHybrisOrderWsDTO masterHybrisOrderWsDTO = new MasterHybrisOrderWsDTO();
			
			masterHybrisOrderData = ((SiteOneOrderFacade) orderFacade).getOrdersWithSameHybrisOrderNumber(sanitizedorderCode);
			
			ConsignmentFulfillmentEntriesWsDTO consignmentFulfillmentEntriesWsDTO = new ConsignmentFulfillmentEntriesWsDTO();
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			
			if (masterHybrisOrderData != null) {
				if( masterHybrisOrderData.getDeliveryEntries() != null ) {
					consignmentFulfillmentEntriesWsDTO.setDeliveryEntriesList(populateMasterOrderEntries(masterHybrisOrderData.getDeliveryEntries()));
				}
				if( masterHybrisOrderData.getPickupEntries() != null) {
					consignmentFulfillmentEntriesWsDTO.setPickupEntriesList(populateMasterOrderEntries(masterHybrisOrderData.getPickupEntries()));
				}
				
				mapperFactory.classMap(MasterHybrisOrderData.class, MasterHybrisOrderWsDTO.class);
				masterHybrisOrderWsDTO = mapper.map(masterHybrisOrderData, MasterHybrisOrderWsDTO.class);
				if(consignmentFulfillmentEntriesWsDTO != null) {
					masterHybrisOrderWsDTO.setFulfillmentEntries(consignmentFulfillmentEntriesWsDTO);
				}
				
			}

			return masterHybrisOrderWsDTO;
		}
		catch(final UnknownIdentifierException e) {
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			LOG.error(e);
			return null;
        }

		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getMasterOrder");
	    }

	}
	
	protected Map<String, ConsignmentEntriesWsDTO> populateMasterOrderEntries( Map<String, List<ConsignmentEntryData>> entryData) {
		Map<String, ConsignmentEntriesWsDTO> entryDTO = new HashMap();
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	    MapperFacade mapper = mapperFactory.getMapperFacade();
		if (entryData != null) {

			entryData.entrySet().forEach(map -> {
				mapperFactory.classMap(ConsignmentEntryData.class, ConsignmentEntryWsDTO.class);
				List<ConsignmentEntryWsDTO> consignmentEntryWsDTOList = mapper.mapAsList(map.getValue(),
						ConsignmentEntryWsDTO.class);
				ConsignmentEntriesWsDTO consignmentEntriesWsDTO = new ConsignmentEntriesWsDTO();
				consignmentEntriesWsDTO.setEntriesList(consignmentEntryWsDTOList);
				entryDTO.put(map.getKey(), consignmentEntriesWsDTO);
			});

		}
	   
		
		return entryDTO;
	}
	
		
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/orderDetails/{orderCode}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "orderDetails", summary = "Get the product list", description = "Get the list of products for given order")
	public String getOrderDetails(@PathVariable("orderCode") final String orderCode,
			@RequestParam(value = "storeId") final String storeId, @Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
	{
		try
		{
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			final ConsignmentData consignmentDetails;
		
			consignmentDetails = ((SiteOneOrderFacade) orderFacade).getMultipleShipmentPage(orderCode, unitId);
			ConsignmentWsDTO consignmentWsDTO = getDataMapper().map(consignmentDetails, ConsignmentWsDTO.class);
			if (consignmentWsDTO != null && consignmentWsDTO.getTrackingUrl() != null)
			{
				return "\"" + consignmentWsDTO.getTrackingUrl().toString() + "\"";
			}
			else
			{
				return null;
			}
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method getOrderDetails");
	    }
		
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/order/{accountUnitId}/{orderCode}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "orderDetails", summary = "Get the order details", description = "Get the order details")
	public OrderWsDTO order(@PathVariable("orderCode") final String orderCode,
			@PathVariable("accountUnitId") final String accountUnitId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@Parameter(description = "branchNo") @RequestParam(required = false) final String branchNo,
			@RequestParam(value = "storeId")  final String storeId)
	{
		try
		{
			final String sanitizedunitId = XSSFilterUtil.filter(accountUnitId);
			final String sanitizedOrderCode = XSSFilterUtil.filter(orderCode);
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			
			OrderData orderDetails;
			if(sanitizedOrderCode != null && sanitizedOrderCode.startsWith("MH"))
			{
				orderDetails = ((SiteOneOrderFacade) orderFacade).getOrderDetailsPage(sanitizedOrderCode.substring(2,sanitizedOrderCode.length()).split("-")[0], sanitizedunitId);
			}
			else if (branchNo != null)
			{
				orderDetails = ((SiteOneOrderFacade) orderFacade).getOrderDetailsPage(sanitizedOrderCode, sanitizedunitId, branchNo, null, Boolean.FALSE);
			}
			else
			{
				orderDetails = ((SiteOneOrderFacade) orderFacade).getOrderDetailsPage(sanitizedOrderCode, sanitizedunitId, null, null, Boolean.FALSE);
			}
			//uom enhancements changes
			if (CollectionUtils.isNotEmpty(orderDetails.getUnconsignedEntries()))
			{
				siteOneProductFacade.getPriceUpdateForHideUomEntry(orderDetails,false);
			}
			
			return getDataMapper().map(orderDetails, OrderWsDTO.class);
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method order");
	    }
		
	}
		
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/emailOrderDetails/{orderCode}")
	@ApiBaseSiteIdParam
	@Operation(operationId = "emailOrderDetails", summary = "Email the purchased products", description = "Share the purchased products through email")
	public @ResponseBody String shareOrderDetailsEmail(@PathVariable("orderCode") final String orderCode,
			@RequestParam(value = "email", required = true) final String emails)
			
	{
		try
		{
			siteOneShareEmailFacade.shareOrderDetailEmailForCode(orderCode, null, emails, null);
			Gson gson = new Gson();
			return gson.toJson("Success");
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method shareOrderDetailsEmail");
	    }
		
	}
	
	protected BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);

		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return encodeSearchPageData(buyItAgainSearchFacade.buyItAgainSearch(searchState, pageableData));
	}

	protected BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> encodeSearchPageData(
			final BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData)
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

	@Override
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

	@Override
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
							Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
											ProductOption.PRICE_RANGE, ProductOption.CATEGORIES, ProductOption.PROMOTIONS,
											ProductOption.STOCK, ProductOption.IMAGES, ProductOption.AVAILABILITY_MESSAGE));
			
					if(product.getLevel1Category() != null && (product.getLevel1Category().equalsIgnoreCase("Nursery")
							|| product.getLevel1Category().equalsIgnoreCase("vivero")) && product.getIsEligibleForBackorder() != null
							&& product.getIsEligibleForBackorder())
					{
						product.setInventoryCheck(Boolean.FALSE);
						product.setIsSellable(Boolean.TRUE);
					}
						if (!product.getIsProductDiscontinued() && product.getPrice()!=null)
					{
						productList.add(product);
					}
					if (product.getPrice()==null)
					{
						LOG.info("buy again product price is null :"+product.getCode()+", order number: "+buyItAgainData.getOrderNumber());
					}
				}
			}
			siteOneProductFacade.getCSPPriceListForProducts(searchPageData.getResults(), productList);
			siteOneProductFacade.populateAvailablityForBuyAgainPageProducts(productList);
			for(final ProductData prod : productList)
			{
				if(prod.getLevel1Category() != null && (prod.getLevel1Category().equalsIgnoreCase("Nursery")
						|| prod.getLevel1Category().equalsIgnoreCase("vivero")))
					{
						prod.setInventoryCheck(Boolean.FALSE);
						prod.setIsSellable(Boolean.TRUE);
					}
			}
		}
		return productList;
	}

	protected void prepareProductSearchDTO(BuyAgainProductSearchDTO searchPageDTO,
			BuyItAgainSearchPageData<SearchStateData, BuyItAgainData> searchPageData){
		searchPageDTO.setCategoryCode(searchPageData.getCategoryCode());
		searchPageDTO.setBreadcrumbs(searchPageData.getBreadcrumbs());
		searchPageDTO.setCurrentQuery(searchPageData.getCurrentQuery());
		searchPageDTO.setFacets(searchPageData.getFacets());
		searchPageDTO.setFreeTextSearch(searchPageData.getFreeTextSearch());
		searchPageDTO.setKeywordRedirectUrl(searchPageData.getKeywordRedirectUrl());
		searchPageDTO.setPagination(searchPageData.getPagination());
		searchPageDTO.setResults(searchPageData.getResults());
		searchPageDTO.setSorts(searchPageData.getSorts());
		searchPageDTO.setSpellingSuggestion(searchPageData.getSpellingSuggestion());
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@GetMapping("/recentOrders/{unitId}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "recentOrders", summary = "Get the list of recent orders", description = "Get the list of recent orders")
	public RecentOrdersWsDTO recentOrders(@PathVariable("unitId") final String unitId,
			@RequestParam(value = "numberOfOrders", required = true) final Integer numberOfOrders,
			@RequestParam(value = "storeId") final String storeId,
			@Parameter(description = "Response configuration. This is the list of fields that should be returned in the response body.", schema = @Schema(allowableValues = "BASIC, DEFAULT, FULL")) @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		try
		{
			final B2BUnitData unit;
			if (!StringUtils.isEmpty(unitId))
			{
				unit = b2bUnitFacade.getUnitForUid(unitId);
			} else {
				unit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
			}
			storeSessionFacade.setSessionShipTo(unit);
			
			final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
			storeSessionFacade.setSessionStore(pointOfServiceData);
			if (numberOfOrders == 0)
			{
				final List<OrderData> recentOrders = ((SiteOneOrderFacade) orderFacade).fetchRecentOrdersFromAPI(unitId);
				OrderListData recentOrdersList = new OrderListData();
				recentOrdersList.setRecentApiOrders(recentOrders);
				return getDataMapper().map(recentOrdersList, RecentOrdersWsDTO.class);
			}
			else
			{
				final List<OrderHistoryData> recentOrders = ((SiteOneOrderFacade) orderFacade)
						.getRecentOrders(unitId,numberOfOrders);
				OrderHistoryListData recentOrdersList = new OrderHistoryListData();
				recentOrdersList.setRecentOrders(recentOrders);
				return getDataMapper().map(recentOrdersList, RecentOrdersWsDTO.class);
			}
		}
		catch (Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occured while calling through method recentOrders");
	    }
		
	}
}
