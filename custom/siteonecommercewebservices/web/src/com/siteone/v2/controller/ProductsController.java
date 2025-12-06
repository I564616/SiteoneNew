/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.v2.controller;

import com.siteone.variants.VariantSortStrategy;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.enums.ProductReferenceTypeEnum;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.catalog.CatalogFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ProductReferenceData;
import de.hybris.platform.commercefacades.product.data.ProductReferencesData;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.product.data.SuggestionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderStockFacade;
import de.hybris.platform.commercefacades.storefinder.data.StoreFinderStockSearchPageData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commercewebservicescommons.dto.product.ImageWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductReferenceListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ProductWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ReviewListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.ReviewWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.StockWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.SuggestionListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.search.facetdata.ProductSearchPageWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.store.StoreFinderStockSearchPageWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.RecommendedProductsWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.product.RecommendedProductsListWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.RequestParameterException;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.StockSystemException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.errors.exceptions.WebserviceValidationException;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import de.hybris.platform.commercefacades.order.CartFacade;

import com.siteone.core.category.service.SiteOneCategoryService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.commerceservices.dto.product.DataSheetListWsDTO;
import com.siteone.commerceservices.dto.product.DataSheetWsDTO;
import com.siteone.commerceservice.customer.dto.SiteOnePdpPriceDataUomWsDTO;
import com.siteone.facades.customer.price.SiteOnePdpPriceDataUom;
import com.siteone.constants.YcommercewebservicesConstants;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;
import com.siteone.facades.customer.price.SiteOneCspResponse;
import com.siteone.facade.location.SiteOneRegionFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.data.DataSheetData;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.facades.savedList.SiteoneSavedListFacade;
import com.siteone.formatters.WsDateFormatter;
import com.siteone.product.data.ReviewDataList;
import com.siteone.product.data.SuggestionDataList;
import com.siteone.stock.CommerceStockFacade;
import com.siteone.variants.VariantMatrixSortStrategy;
import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.scan.product.data.SingleProductResponse;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.commerceservices.store.dto.StoreLevelStockInfoDataWsDTO;

import com.siteone.facades.sds.SdsPdfFacade;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.v2.helper.ProductsHelper;
import com.siteone.validator.PointOfServiceValidator;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.ui.ExtendedModelMap;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Web Services Controller to expose the functionality of the
 * {@link de.hybris.platform.commercefacades.product.ProductFacade} and SearchFacade.
 */

@Controller
@Tag(name = "Products")
@RequestMapping(value = "/{baseSiteId}/products")
public class ProductsController extends BaseController
{
	private static final String BASIC_OPTION = "BASIC";
	private static final Set<ProductOption> OPTIONS;
	private static final String MAX_INTEGER = "2147483647";
	private static final int CATALOG_ID_POS = 0;
	private static final int CATALOG_VERSION_POS = 1;
	private static final String COMMA_SEPARATOR = ",";
	private static final Logger LOG = Logger.getLogger(ProductsController.class);
	private static final String PRODUCT_OPTIONS;
	private static final String CURRENCY_ISO="USD";
	private static final String VARIANT_PRODUCT="VARIANT";
	private static final String REGULATORY_STATES="regulatoryStates";
	private static final String CATALOG_ID = "siteoneProductCatalog";
	private static final String CATALOG_VERSION_ONLINE = "Online";
	private static final String FULL_OPTION = "FULL";

	static
	{
		PRODUCT_OPTIONS = Arrays.stream(ProductOption.values()).map(ProductOption::toString)
				.collect(Collectors.joining(YcommercewebservicesConstants.OPTIONS_SEPARATOR));
		OPTIONS = extractOptions(PRODUCT_OPTIONS);
	}

	@Resource(name = "storeFinderStockFacade")
	private StoreFinderStockFacade storeFinderStockFacade;
	@Resource(name = "cwsProductFacade")
	private ProductFacade productFacade;
	@Resource(name = "wsDateFormatter")
	private WsDateFormatter wsDateFormatter;
	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;
	@Resource(name = "httpRequestReviewDataPopulator")
	private Populator<HttpServletRequest, ReviewData> httpRequestReviewDataPopulator;
	@Resource(name = "reviewValidator")
	private Validator reviewValidator;
	@Resource(name = "reviewDTOValidator")
	private Validator reviewDTOValidator;
	@Resource(name = "commerceStockFacade")
	private CommerceStockFacade commerceStockFacade;
	@Resource(name = "pointOfServiceValidator")
	private PointOfServiceValidator pointOfServiceValidator;
	@Resource(name = "catalogFacade")
	private CatalogFacade catalogFacade;
	@Resource(name = "productsHelper")
	private ProductsHelper productsHelper;
	@Resource(name = "productService")
	private ProductService productService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	@Resource(name = "siteOneRegionFacade")
	private SiteOneRegionFacade siteOneRegionFacade;
	@Resource(name = "variantSortStrategy")
	private VariantSortStrategy variantSortStrategy;
	@Resource(name = "variantMatrixSortStrategy")
	private VariantMatrixSortStrategy variantMatrixSortStrategy;
	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;
	@Resource(name = "sdsPdfFacade")
	private SdsPdfFacade sdsPdfFacade;
	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	@Resource(name = "categoryService")
	private SiteOneCategoryService siteOneCategoryService;	
	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;
	@Resource
	CatalogVersionService catalogVersionService;


	protected static Set<ProductOption> extractOptions(final String options)
	{
		final String[] optionsStrings = options.split(YcommercewebservicesConstants.OPTIONS_SEPARATOR);
		final Set<ProductOption> opts = EnumSet.noneOf(ProductOption.class);
		for (final String option : optionsStrings)
		{
			opts.add(ProductOption.valueOf(option));
		}
		return opts;
	}


	@GetMapping("/search")
	@ResponseBody
	@Operation(operationId = "getProducts", summary = "Get a list of products and additional data", description =
			"Returns a list of products and additional data, such as available facets, "
					+ "available sorting, and pagination options. It can also include spelling suggestions. To make spelling suggestions work, you need to make sure "
					+ "that \"enableSpellCheck\" on the SearchQuery is set to \"true\" (by default, it should already be set to \"true\"). You also need to have indexed "
					+ "properties configured to be used for spellchecking.")
	@ApiBaseSiteIdParam
	@Hidden
	public ProductSearchPageWsDTO getProducts(
			@Parameter(description = "Serialized query, free text search, facets. The format of a serialized query: freeTextSearch:sort:facetKey1:facetValue1:facetKey2:facetValue2") @RequestParam(required = false) final String query,
			@Parameter(description = "The current result page requested.") @RequestParam(defaultValue = DEFAULT_CURRENT_PAGE) final int currentPage,
			@Parameter(description = "The number of results returned per page.") @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pageSize,
			@Parameter(description = "Sorting method applied to the return results.") @RequestParam(required = false) final String sort,
			@Parameter(description = "The context to be used in the search query.") @RequestParam(required = false) final String searchQueryContext,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields, final HttpServletResponse response)
	{
		final ProductSearchPageWsDTO result = productsHelper
				.searchProducts(query, currentPage, pageSize, sort, addPaginationField(fields), searchQueryContext);
		// X-Total-Count header
		setTotalCountHeader(response, result.getPagination());
		return result;
	}


	@RequestMapping(value = "/search", method = RequestMethod.HEAD)
	@Operation(operationId = "countProducts", summary = "Get a header with total number of products.", description = "In the response header, the \"x-total-count\" indicates the total number of products satisfying a query.")
	@ApiBaseSiteIdParam
	@Hidden
	public void countProducts(
			@Parameter(description = "Serialized query, free text search, facets. The format of a serialized query: freeTextSearch:sort:facetKey1:facetValue1:facetKey2:facetValue2") @RequestParam(required = false) final String query,
			final HttpServletResponse response)
	{
		final ProductSearchPageData<SearchStateData, ProductData> result = productsHelper.searchProducts(query, 0, 1, null);
		setTotalCountHeader(response, result.getPagination());
	}


	@GetMapping("/{productCode}")
	@CacheControl(directive = CacheControlDirective.PRIVATE, maxAge = 120)
	@Cacheable(value = "productCache", key = "T(de.hybris.platform.commercewebservicescommons.cache.CommerceCacheKeyGenerator).generateKey(true,true,#productCode,#fields)")
	@ResponseBody
	@Operation(operationId = "getProduct", summary = "Get product details.", description = "Returns details of a single product according to a product code.")
	@ApiBaseSiteIdParam
	@Hidden
	public ProductWsDTO getProduct(@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("getProduct: code=" + sanitize(productCode) + " | options=" + PRODUCT_OPTIONS);
		}

		final ProductData product = productFacade.getProductForCodeAndOptions(productCode, OPTIONS);
		return getDataMapper().map(product, ProductWsDTO.class, fields);
	}

	@GetMapping("/details/{productCode}")
	@ResponseBody
	@Operation(operationId = "getProductDetails", summary = "Get product details information.", description = "Returns more details of a single product according to a product code.")
	@ApiBaseSiteIdParam
	public ProductWsDTO getProductDetails(final HttpServletRequest request, @Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@RequestParam(value = "storeId", required = true) final String storeId, @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		String pCode=productCode;
		ExtendedModelMap extModel = new ExtendedModelMap();
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
		
		ProductData productDataDisplay;
		final List<ProductOption> extraOptions = Arrays.asList(ProductOption.DESCRIPTION, ProductOption.KEYWORDS,
				ProductOption.CATEGORIES);
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, extraOptions);
		
		if(CollectionUtils.isEmpty(productData.getVariantOptions()) && BooleanUtils.isTrue(productData.getOutOfStockImage()))
		{
			List<ProductData> productList = new ArrayList<>();
			productList.add(productData);
			siteOneProductFacade.updateSalesInfoBackorderForProduct(productList);			
		}
		
		if (null != productData.getIsProductOffline() && productData.getIsProductOffline()) {
			ProductWsDTO productDTO = new ProductWsDTO();
			productDTO.setIsProductOffline(true);
			return productDTO;
		} else {
			final String categoryName = productData.getCategories().iterator().next().getName();
			
			final List<ProductOption> varOptions = Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.CATEGORIES,
					ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL, ProductOption.VARIANT_MATRIX_BASE,
					ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.KEYWORDS);
			final ProductData productVarData = productFacade.getProductForCodeAndOptions(productCode, varOptions);
			if (productVarData.getMultidimensional()
					&& (productVarData.getVariantOptions() == null || productVarData.getVariantOptions().isEmpty())
					&& productVarData.getBaseProduct() != null)
			{
				pCode=productData.getBaseProduct();
			}
			if (productData.getVariantCount().intValue() == 1)
			{
				final List<ProductOption> firstVariantOptions = Arrays.asList(ProductOption.VARIANT_FIRST_VARIANT,
						ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA,
						ProductOption.KEYWORDS);
				productDataDisplay = populateProductDetailForDisplay(pCode, request, firstVariantOptions, extModel);
			}
			else
			{
				final List<ProductOption> otherOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE,
						ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.KEYWORDS);
				productDataDisplay = populateProductDetailForDisplay(pCode, request, otherOptions, extModel);
			}
		}
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		Map<String, List<DataSheetData>> dataSheet = productDataDisplay.getDataSheetList();
		Map<String, DataSheetListWsDTO> dataSheetDTO = new HashMap();
		dataSheet.entrySet().forEach(map ->{
			
			mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
			List<DataSheetWsDTO> sheetListWsDTO = mapper.mapAsList(map.getValue(), DataSheetWsDTO.class);
			DataSheetListWsDTO list = new DataSheetListWsDTO();
			list.setDataSheetList(sheetListWsDTO);
			dataSheetDTO.put(map.getKey(), list);
		});
		
		if (null != storeSessionFacade.getSessionStore() && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
				storeSessionFacade.getSessionStore().getStoreId()) && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && null == storeSessionFacade.getSessionStore().getHubStores())
		{
			productData.setIsShippable(false);
			productDataDisplay.setIsShippable(false);
		}
		mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
		ProductWsDTO productDTO = mapper.map(productDataDisplay, ProductWsDTO.class);
		productDTO.setDataSheetListDTO(dataSheetDTO);
		productDTO.setVariantIsPickupEnabled((Boolean)extModel.get("isPickupEnabled"));
		productDTO.setVariantIsDeliveryEnabled((Boolean)extModel.get("isDeliveryEnabled"));
		productDTO.setVariantStoreName((String)extModel.get("storeName"));
		productDTO.setVariantStoreId((String)extModel.get("storeId"));
		productDTO.setRegulatoryNotAvailbleStates((List<String>)extModel.get(REGULATORY_STATES));
		return productDTO;
	}

	
	@GetMapping(value = "/showNearbyOverlay")
	@ResponseBody
	@Operation(operationId = "showNearbyOverlay", summary = "Show nearby OverLay for product", description = "Returns list of nearby stores for a product")
	@ApiBaseSiteIdParam
	public List<StoreLevelStockInfoDataWsDTO> showNearbyOverlay(@RequestParam(value = "code") final String code, @RequestParam(value = "storeId", required = true) final String storeId)
	{
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);
		try {
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade.populateStoreLevelStockInfoData(code,false);
		List<StoreLevelStockInfoDataWsDTO> storeLevelStockInfoDataListDTO = new ArrayList<>();
					
		if(CollectionUtils.isNotEmpty(storeLevelStockInfoDataList)) {
			MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
			MapperFacade mapper = mapperFactory.getMapperFacade();
			mapperFactory.classMap(StoreLevelStockInfoData.class, StoreLevelStockInfoDataWsDTO.class);
			storeLevelStockInfoDataListDTO = mapper.mapAsList(storeLevelStockInfoDataList, StoreLevelStockInfoDataWsDTO.class);
		}
		return storeLevelStockInfoDataListDTO;
		}
		catch(final Exception ex) {
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method populateStoreLevelStockInfoData");
		}
	}
	
	@GetMapping(value = "/customerpriceforuom")
	@ResponseBody
	@Operation(operationId = "customerpriceforuom" , summary = "Customer price for UOM products", description = "Returns the UOM price")
	@ApiBaseSiteIdParam
	public SiteOnePdpPriceDataUomWsDTO getCustomerPriceForUom(@RequestParam(value = "productCode", required = true)final String productCode ,
	@RequestParam(value = "quantity", required = true)final Integer quantity,
	@RequestParam(value = "inventoryUomId", required = false)final String inventoryUomId,
	@RequestParam(value = "storeId", required = true) final String storeId, 
	@Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
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
		
		List<ProductOption> productOptions = Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.SUMMARY,
				ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE,
				ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL,
				ProductOption.DATA_SHEET, ProductOption.AVAILABILITY_MESSAGE);
		try {
		SiteOnePdpPriceDataUom siteOnePdpPriceDataUom = siteOneProductFacade.getCustomerPriceforUom(productCode, quantity, inventoryUomId, productOptions);
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		mapperFactory.classMap(SiteOnePdpPriceDataUom.class, SiteOnePdpPriceDataUomWsDTO.class);
		SiteOnePdpPriceDataUomWsDTO siteOnePdpPriceDataUomWsDTO = mapper.map(siteOnePdpPriceDataUom, SiteOnePdpPriceDataUomWsDTO.class);
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, productOptions);
		if(BooleanUtils.isTrue(productData.getInventoryCheck()) && BooleanUtils.isTrue(productData.getIsEligibleForBackorder())
				&& siteoneSavedListFacade.categoryMatch(productData.getLevel2Category()))
		{
			siteOnePdpPriceDataUomWsDTO.getTotalPrice().setValue(BigDecimal.valueOf(0.0d));
			siteOnePdpPriceDataUomWsDTO.getTotalPrice().setFormattedValue("$0.00");
		}
		return siteOnePdpPriceDataUomWsDTO;	
		}
		catch(final Exception ex)
		{
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method getCustomerPriceForUom");
		}
	
	}
	
	protected ProductData populateProductDetailForDisplay(final String productCode, final HttpServletRequest request,
			final List<ProductOption> extraOptions, ExtendedModelMap  extModel)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final List<ProductOption> options = new ArrayList<>(
				Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.PRICE, ProductOption.SUMMARY,
						ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE,
						ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL,
						ProductOption.DATA_SHEET, ProductOption.AVAILABILITY_MESSAGE));

		options.addAll(extraOptions);
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, options);
		
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
			siteOneProductFacade.populatVariantProductsForDisplay(productData,  extModel);
			productData.getVariantOptions().sort(Comparator.comparing(VariantOptionData::getStockLevel,Comparator.nullsLast(Comparator.naturalOrder())).reversed());
		}

		if (productData.getIsRegulateditem())
		{
			final List<RegionData> regionDataList = siteOneRegionFacade.getRegionsForCountryCode("US");

			if (CollectionUtils.isNotEmpty(productData.getRegulatoryStates()))
			{
				final List<RegionData> regulatoryStatesList = regionDataList.stream().filter(e -> (productData.getRegulatoryStates()
						.stream().filter(d -> d.equalsIgnoreCase(e.getIsocodeShort())).count()) < 1).collect(Collectors.toList());
				final List<String> regulatoryStates = regulatoryStatesList.stream().map(e -> e.getIsocodeShort())
						.collect(Collectors.toList());
				Collections.sort(regulatoryStates);
				extModel.addAttribute(REGULATORY_STATES, regulatoryStates);
			}
			else
			{
				final List<String> regulatoryStates = regionDataList.stream().map(e -> e.getIsocodeShort())
						.collect(Collectors.toList());
				Collections.sort(regulatoryStates);
				extModel.addAttribute(REGULATORY_STATES, regulatoryStates);
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
								Float.valueOf(inventoryUOMData1.getInventoryMultiplier()), null);
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
						}
	
					}
					inventoryUOMListnew.add(inventoryUOMData1);
				}
				if (productData.getHideUom() != null && productData.getHideUom())
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
		// parcel shipping
				if (null != storeSessionFacade.getSessionStore() && !siteOneFeatureSwitchCacheService
						.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches", storeSessionFacade.getSessionStore().getStoreId()))
				{
					siteOneProductFacade.updateParcelShippingForProduct(productData, storeSessionFacade.getSessionStore() != null ? storeSessionFacade.getSessionStore().getStoreId() : null);
				}
		return productData;
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

	@GetMapping("/{productCode}/stock/{storeName}")
	@ResponseBody
	@Operation(operationId = "getStoreProductStock", summary = "Get a product's stock level for a store", description = "Returns a product's stock level for a particular store (in other words, for a particular point of sale).")
	@Hidden
	public StockWsDTO getStoreProductStock(
			@Parameter(description = "Base site identifier", required = true) @PathVariable final String baseSiteId,
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@Parameter(description = "Store identifier", required = true) @PathVariable final String storeName,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
			throws WebserviceValidationException, StockSystemException //NOSONAR
	{
		validate(storeName, "storeName", pointOfServiceValidator);
		if (!commerceStockFacade.isStockSystemEnabled(baseSiteId))
		{
			throw new StockSystemException("Stock system is not enabled on this site", StockSystemException.NOT_ENABLED, baseSiteId);
		}
		final StockData stockData = commerceStockFacade.getStockDataForProductAndPointOfService(productCode, storeName);
		return getDataMapper().map(stockData, StockWsDTO.class, fields);
	}


	@GetMapping("/{productCode}/stock")
	@ResponseBody
	@Operation(operationId = "getLocationProductStock", summary = "Get a product's stock level.", description =
			"Returns a product's stock levels sorted by distance from the specified location, which is provided "
					+ "using the free-text \"location\" parameter, or by using the longitude and latitude parameters. The following two sets of parameters are available: location "
					+ "(required), currentPage (optional), pageSize (optional); or longitude (required), latitude (required), currentPage (optional), pageSize(optional).")
	@ApiBaseSiteIdParam
	@Hidden
	public StoreFinderStockSearchPageWsDTO getLocationProductStock(
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode, //NOSONAR
			@Parameter(description = "Free-text location") @RequestParam(required = false) final String location,
			@Parameter(description = "Latitude location parameter.") @RequestParam(required = false) final Double latitude,
			@Parameter(description = "Longitude location parameter.") @RequestParam(required = false) final Double longitude,
			@Parameter(description = "The current result page requested.") @RequestParam(required = false, defaultValue = DEFAULT_CURRENT_PAGE) final int currentPage,
			@Parameter(description = "The number of results returned per page.") @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) final int pageSize,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields, final HttpServletResponse response)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("getLocationProductStock: code=" + sanitize(productCode) + " | location=" + sanitize(location) + " | latitude="
					+ latitude + " | longitude=" + longitude);
		}

		final StoreFinderStockSearchPageData result = doSearchProductStockByLocation(productCode, location, latitude, longitude,
				currentPage, pageSize);

		// X-Total-Count header
		setTotalCountHeader(response, result.getPagination());

		return getDataMapper().map(result, StoreFinderStockSearchPageWsDTO.class, addPaginationField(fields));
	}


	@RequestMapping(value = "/{productCode}/stock", method = RequestMethod.HEAD)
	@Operation(operationId = "countProductStockByLocation", summary = "Get header with a total number of product's stock levels.", description =
			"In the response header, the \"x-total-count\" indicates the total number of a "
					+ "product's stock levels. The following two sets of parameters are available: location (required); or longitude (required), and latitude (required).")
	@ApiBaseSiteIdParam
	@Hidden
	public void countProductStockByLocation(
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@Parameter(description = "Free-text location") @RequestParam(required = false) final String location,
			@Parameter(description = "Latitude location parameter.") @RequestParam(required = false) final Double latitude,
			@Parameter(description = "Longitude location parameter.") @RequestParam(required = false) final Double longitude,
			final HttpServletResponse response)
	{
		final StoreFinderStockSearchPageData result = doSearchProductStockByLocation(productCode, location, latitude, longitude, 0,
				1);

		setTotalCountHeader(response, result.getPagination());
	}

	protected StoreFinderStockSearchPageData doSearchProductStockByLocation(final String productCode, final String location,
			final Double latitude, final Double longitude, final int currentPage, final int pageSize)
	{
		final Set<ProductOption> opts = extractOptions(BASIC_OPTION);
		final StoreFinderStockSearchPageData result;
		if (latitude != null && longitude != null)
		{
			result = storeFinderStockFacade
					.productSearch(createGeoPoint(latitude, longitude), productFacade.getProductForCodeAndOptions(productCode, opts),
							createPageableData(currentPage, pageSize, null));
		}
		else if (location != null)
		{
			result = storeFinderStockFacade.productSearch(location, productFacade.getProductForCodeAndOptions(productCode, opts),
					createPageableData(currentPage, pageSize, null));
		}
		else
		{
			throw new RequestParameterException("You need to provide location or longitute and latitute parameters",
					RequestParameterException.MISSING, "location or longitute and latitute");
		}
		return result;
	}


	@GetMapping("/{productCode}/reviews")
	@ResponseBody
	@Operation(operationId = "getProductReviews", summary = "Get reviews for a product", description = "Returns the reviews for a product with a given product code.")
	@ApiBaseSiteIdParam
	@Hidden
	public ReviewListWsDTO getProductReviews(
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@Parameter(description = "Maximum count of reviews") @RequestParam(required = false) final Integer maxCount,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final ReviewDataList reviewDataList = new ReviewDataList();
		reviewDataList.setReviews(productFacade.getReviews(productCode, maxCount));
		return getDataMapper().map(reviewDataList, ReviewListWsDTO.class, fields);
	}


	@PostMapping("/{productCode}/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@Operation(hidden = true, summary = "Creates a new customer review as an anonymous user", description = "Creates a new customer review as an anonymous user.")
	@Parameter(name = "headline", description = "Headline of customer review", required = true, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "comment", description = "Comment of customer review", required = true, schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@Parameter(name = "rating", description = "Rating of customer review", required = true, schema = @Schema(type = "double"), in = ParameterIn.QUERY)
	@Parameter(name = "alias", description = "Alias of customer review", schema = @Schema(type = "string"), in = ParameterIn.QUERY)
	@ApiBaseSiteIdParam
	@Hidden
	public ReviewWsDTO createProductReview(
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields, final HttpServletRequest request)
			throws WebserviceValidationException //NOSONAR
	{
		final ReviewData reviewData = new ReviewData();
		httpRequestReviewDataPopulator.populate(request, reviewData);
		validate(reviewData, "reviewData", reviewValidator);
		final ReviewData reviewDataRet = productFacade.postReview(productCode, reviewData);
		return getDataMapper().map(reviewDataRet, ReviewWsDTO.class, fields);
	}


	@PostMapping(value = "/{productCode}/reviews", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@Operation(operationId = "createProductReview", summary = "Creates a new customer review as an anonymous user.", description = "Creates a new customer review as an anonymous user.")
	@ApiBaseSiteIdParam
	@Hidden
	public ReviewWsDTO createProductReview(
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@Parameter(description = "Object contains review details like : rating, alias, headline, comment", required = true) @RequestBody final ReviewWsDTO review,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
			throws WebserviceValidationException //NOSONAR
	{
		validate(review, "review", reviewDTOValidator);
		final ReviewData reviewData = getDataMapper().map(review, ReviewData.class, "alias,rating,headline,comment");
		final ReviewData reviewDataRet = productFacade.postReview(productCode, reviewData);
		return getDataMapper().map(reviewDataRet, ReviewWsDTO.class, fields);
	}


	@GetMapping("/{productCode}/references")
	@ResponseBody
	@Operation(operationId = "getProductReferences", summary = "Get a product reference", description = "Returns references for a product with a given product code. Reference type specifies which references to return.")
	@ApiBaseSiteIdParam
	@Hidden
	public ProductReferenceListWsDTO getProductReferences(
			@Parameter(description = "Product identifier", required = true) @PathVariable final String productCode,
			@Parameter(description = "Maximum size of returned results.") @RequestParam(required = false, defaultValue = MAX_INTEGER) final int pageSize,
			@Parameter(description = "Comma-separated list of reference types according to enum ProductReferenceTypeEnum. If not specified, all types of product references will be used.") @RequestParam(required = false) final String referenceType,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final List<ProductOption> opts = Lists.newArrayList(OPTIONS);

		final List<ProductReferenceTypeEnum> productReferenceTypeList = StringUtils.isNotEmpty(referenceType) ?
				getProductReferenceTypeEnums(referenceType) :
				List.of(ProductReferenceTypeEnum.values());

		final List<ProductReferenceData> productReferences = productFacade
				.getProductReferencesForCode(productCode, productReferenceTypeList, opts, Integer.valueOf(pageSize));
		final ProductReferencesData productReferencesData = new ProductReferencesData();
		productReferencesData.setReferences(productReferences);

		return getDataMapper().map(productReferencesData, ProductReferenceListWsDTO.class, fields);
	}

	protected List<ProductReferenceTypeEnum> getProductReferenceTypeEnums(final String referenceType)
	{
		final String[] referenceTypes = referenceType.split(COMMA_SEPARATOR);
		return Arrays.stream(referenceTypes).map(ProductReferenceTypeEnum::valueOf).collect(Collectors.toList());
	}

	protected PageableData createPageableData(final int currentPage, final int pageSize, final String sort)
	{
		final PageableData pageable = new PageableData();

		pageable.setCurrentPage(currentPage);
		pageable.setPageSize(pageSize);
		pageable.setSort(sort);
		return pageable;
	}

	protected GeoPoint createGeoPoint(final Double latitude, final Double longitude)
	{
		final GeoPoint point = new GeoPoint();
		point.setLatitude(latitude.doubleValue());
		point.setLongitude(longitude.doubleValue());

		return point;
	}



	@GetMapping("/suggestions")
	@ResponseBody
	@Operation(operationId = "getSuggestions", summary = "Get a list of available suggestions", description = "Returns a list of all available suggestions related to a given term and limits the results to a specific value of the max parameter.")
	@ApiBaseSiteIdParam
	@Hidden
	public SuggestionListWsDTO getSuggestions(@Parameter(description = "Specified term", required = true) @RequestParam final String term,
			@Parameter(description = "Specifies the limit of results.") @RequestParam(defaultValue = "10") final int max,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final List<SuggestionData> suggestions = new ArrayList<>();
		final SuggestionDataList suggestionDataList = new SuggestionDataList();

		List<AutocompleteSuggestionData> autoSuggestions = productSearchFacade.getAutocompleteSuggestions(term);
		if (max < autoSuggestions.size())
		{
			autoSuggestions = autoSuggestions.subList(0, max);
		}

		for (final AutocompleteSuggestionData autoSuggestion : autoSuggestions)
		{
			final SuggestionData suggestionData = new SuggestionData();
			suggestionData.setValue(autoSuggestion.getTerm());
			suggestions.add(suggestionData);
		}
		suggestionDataList.setSuggestions(suggestions);

		return getDataMapper().map(suggestionDataList, SuggestionListWsDTO.class, fields);
	}


	protected List<String> validateAndSplitCatalog(final String catalog) throws RequestParameterException //NOSONAR
	{
		final List<String> catalogInfo = new ArrayList<>();
		if (StringUtils.isNotEmpty(catalog))
		{
			catalogInfo.addAll(Lists.newArrayList(Splitter.on(':').trimResults().omitEmptyStrings().split(catalog)));
			if (catalogInfo.size() == 2)
			{
				catalogFacade.getProductCatalogVersionForTheCurrentSite(catalogInfo.get(CATALOG_ID_POS),
						catalogInfo.get(CATALOG_VERSION_POS), Collections.emptySet());
			}
			else if (!catalogInfo.isEmpty())
			{
				throw new RequestParameterException("Invalid format. You have to provide catalog as 'catalogId:catalogVersion'",
						RequestParameterException.INVALID, "catalog");
			}
		}
		return catalogInfo;
	}
	
	@GetMapping("/pdf/sdsPDF")
	@Operation(operationId = "sdsPDF", summary = "Information and Guideline PDF", description = "Displaying Information and Guideline as PDF.")
	@ApiBaseSiteIdParam
	@ResponseBody
	public void getSDSPDF(@RequestParam("resourceId") final String resourceId, @RequestParam("skuId") final Optional<String>  skuId,
			  final HttpServletResponse response) 
	{
		try
		{
			String SkuNumber = skuId.isPresent()? skuId.get():null;
			final byte[] pdfContent = sdsPdfFacade.getPDFByResourceId(SkuNumber, resourceId);

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			final String encodedResourceId = StringUtils.normalizeSpace(resourceId);
			response.setHeader("Content-Disposition", "attachment; filename=SDS_OR_Label_" + encodedResourceId + ".pdf");
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
		}
		catch(final Exception ex)
		{
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method getSDSPDF");
		}
	}
	
	@PostMapping("/p/customerprice")
	@Operation(operationId = "customerPrice", summary = "Click to See your Customer Price", description = "Displaying Customer Price range .")
	@ApiBaseSiteIdParam
	public @ResponseBody SiteOneCspResponse getPriceForCustomer(@RequestParam(value = "productCode") final String productCode,
			@RequestParam(value = "quantity") final Integer quantity,
			@RequestParam(value = "storeId") final String storeId,
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId)
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
		try {
		final SiteOneCspResponse siteOneCspResponse = siteOneProductFacade.getPriceForCustomer(productCode, quantity,storeId,null);
		if (null != siteOneCspResponse)
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
			LOG.info("ProductPageController : productCode - " + productCode + " CSP Response - " + siteOneCspResponse.isIsSuccess()
					+ " for Store - " + storeSessionFacade.getSessionStore().getStoreId() + " and customer - "
					+ b2bCustomer.getEmail());
		}
		return siteOneCspResponse;
		}
		catch(final Exception ex)
		{
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method getPriceForCustomer");
		}
	}
	

	@PostMapping("/p/branchRetailPrice")
	@Operation(operationId = "customerPrice", summary = "Click to See your Branch Price", description = "Displaying Branch Price range .")
	@ApiBaseSiteIdParam
	public @ResponseBody SiteOneCspResponse getRetailPriceForBranch(@RequestParam(value = "productCode") final String productCode,
			@RequestParam(value = "quantity") final Integer quantity,
			@RequestParam(value = "storeId") final String storeId)
	{
		
	final PointOfServiceData pointOfServiceData = storeFinderFacade.getStoreForId(storeId);
		storeSessionFacade.setSessionStore(pointOfServiceData);
		
		String retailBranchId=null;
		if (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			retailBranchId=storeSessionFacade.getSessionStore().getCustomerRetailId();
		}
	
		try {
		final SiteOneCspResponse siteOneCspResponse = siteOneProductFacade.getPriceForCustomer(productCode, quantity,storeId,retailBranchId);

		return siteOneCspResponse;
		}
		catch(final Exception ex)
		{
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method getPriceForCustomer");
		}
	}


	@GetMapping("/scanProduct")
	@Operation(operationId = "scanProduct", summary = "Fetch the scanned product details", description = "Fetch the scanned product details")
	@ApiBaseSiteIdParam
	public @ResponseBody ProductWsDTO getScanProductDetails( @RequestParam(value = "upc", required = true) final String upc,
			@RequestParam(value = "storeId", required = true) final String storeId, @Parameter(description = "unitId") @RequestParam(required = false) final String unitId,
			final HttpServletRequest request)
	{
		try {
		ExtendedModelMap extModel = new ExtendedModelMap();
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
		
		Map<String, String> variantProduct = new HashMap<>();
		ProductData productData = siteOneProductFacade.getScanProductDetails(upc, pointOfServiceData.getSupplyChainNodeId(),
				variantProduct, storeId);
		ProductData productDataDisplay = null;
		
		if(productData == null){
			throw new ServiceUnavailableException("404");
		}
		else if (null == productData.getIsProductOffline() || !productData.getIsProductOffline()) {
			String productCode = productData.getCode();
			final String categoryName = productData.getCategories().iterator().next().getName();
			
			final List<ProductOption> varOptions = Arrays.asList(ProductOption.BASIC, ProductOption.URL, ProductOption.CATEGORIES,
					ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL, ProductOption.VARIANT_MATRIX_BASE,
					ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.KEYWORDS);
			final ProductData productVarData = productFacade.getProductForCodeAndOptions(productCode, varOptions);
			if (productVarData.getMultidimensional()
					&& (productVarData.getVariantOptions() == null || productVarData.getVariantOptions().isEmpty())
					&& productVarData.getBaseProduct() != null)
			{
				productCode=productData.getBaseProduct();
			}
			if (productData.getVariantCount().intValue() == 1)
			{
				final List<ProductOption> firstVariantOptions = Arrays.asList(ProductOption.VARIANT_FIRST_VARIANT,
						ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA,
						ProductOption.KEYWORDS);
				productDataDisplay = populateProductDetailForDisplay(productCode, request, firstVariantOptions, extModel);
			}
			else
			{
				final List<ProductOption> otherOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE,
						ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.KEYWORDS);
				productDataDisplay = populateProductDetailForDisplay(productCode, request, otherOptions, extModel);
			}
			if (!StringUtils.isEmpty(unitId))
			{
				siteOneProductFacade.createRecentScanProducts(unitId, productCode);
			}
		}
		if (null != storeSessionFacade.getSessionStore() && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
				storeSessionFacade.getSessionStore().getStoreId()) && !siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && null == storeSessionFacade.getSessionStore().getHubStores())
		{
			productData.setIsShippable(false);
			productDataDisplay.setIsShippable(false);
		}
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		ProductWsDTO productDTO = null;
		if(productDataDisplay != null){
			Map<String, List<DataSheetData>> dataSheet = productDataDisplay.getDataSheetList();
			Map<String, DataSheetListWsDTO> dataSheetDTO = new HashMap();
			dataSheet.entrySet().forEach(map ->{
				
				mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
				List<DataSheetWsDTO> sheetListWsDTO = mapper.mapAsList(map.getValue(), DataSheetWsDTO.class);
				DataSheetListWsDTO list = new DataSheetListWsDTO();
				list.setDataSheetList(sheetListWsDTO);
				dataSheetDTO.put(map.getKey(), list);
			});
			
			mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
			productDTO = mapper.map(productDataDisplay, ProductWsDTO.class);
			productDTO.setDataSheetListDTO(dataSheetDTO);
			productDTO.setVariantIsPickupEnabled((Boolean)extModel.get("isPickupEnabled"));
			productDTO.setVariantIsDeliveryEnabled((Boolean)extModel.get("isDeliveryEnabled"));
			productDTO.setVariantStoreName((String)extModel.get("storeName"));
			productDTO.setVariantStoreId((String)extModel.get("storeId"));
		}else{
			mapperFactory.classMap(ProductData.class, ProductWsDTO.class);
			productDTO = mapper.map(productData, ProductWsDTO.class);
		}
		if(variantProduct.get(VARIANT_PRODUCT)!=null)
		{
		productDTO.setSelectedVariantCode(variantProduct.get(VARIANT_PRODUCT));
		}
		else
		{
			productDTO.setSelectedVariantCode(productData.getCode());	
		}
		return productDTO;
		}
		catch(final Exception ex)
		{
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method getScanProductDetails");
		}
		
	}
	
	@GetMapping("/restricted-use-pesticide")
	@Operation(operationId = "restricted use pesticide", summary = "Check restricted-use-pesticide", description = "Check restricted-use-pesticide status for product in store")
	@ApiBaseSiteIdParam
	@ResponseBody
	public Boolean isRestrictedUsePesticide(@RequestParam("productCode") final String productCode,@RequestParam(value = "storeId", required = true) final String storeId)
	{
		try {
		return siteOneProductFacade.isRestrictedUsePesticide(productCode, storeId);
		}
		catch(final Exception ex)
		{
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method isRestrictedUsePesticide");
		}
		
	}

	@GetMapping("/recommendedProducts")
	@ResponseBody
	@Operation(operationId = "Recommended Products", summary = "Recommended Products", description = "Recommended Products")
	@ApiBaseSiteIdParam
	public RecommendedProductsWsDTO recommendedProducts(final HttpServletRequest request, @RequestParam(value = "storeId", required = true) final String storeId, 
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId, @Parameter(description = "placementPage") @RequestParam(required = true) final String placementPage, 
			@Parameter(description = "categoryId") @RequestParam(required = false) final String categoryId, @Parameter(description = "productId") @RequestParam(required = false) final String productId,
			@Parameter(description = "orderId") @RequestParam(required = false) final String orderId, @Parameter(description = "searchTerm") @RequestParam(required = false) final String searchTerm)
	{
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
		final String sessionId = request.getSession().getId();
		final String pagePosition = "mobileApp";
		
		String pimCategoryId="";
		String productCode=productId;
		StringBuilder productCodes = new StringBuilder();
		StringBuilder productPrices = new StringBuilder();
		StringBuilder productQtys = new StringBuilder();
		if(!StringUtils.isEmpty(categoryId))
		{
			try {
				final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(CATALOG_ID, CATALOG_VERSION_ONLINE);
				final CategoryModel category = siteOneCategoryService.getCategoryForCode(catalogVersionModel, categoryId.toUpperCase());
				pimCategoryId = category.getPimCategoryId();
			} catch(Exception ex) {
				LOG.error(ex.getMessage());
			}
		}		
		if(!StringUtils.isEmpty(orderId))
		{	
			final OrderData orderDetails;
			orderDetails = orderFacade.getOrderDetailsForCode(orderId);
			List<OrderEntryData> orderEntries = orderDetails.getEntries();
			boolean firstEntry = true;

			for (OrderEntryData orderEntryData : orderEntries) {
				if (firstEntry) {
					firstEntry = false;
				} else {
					productCodes.append("|");
					productPrices.append("|");
					productQtys.append("|");
				}
				ProductData productData = orderEntryData.getProduct();
				productCodes.append(productData.getCode());
				Optional.ofNullable(orderEntryData.getBasePrice())
						.map(PriceData::getValue)
						.or(() -> Optional.ofNullable(orderEntryData.getListPrice()))
						.ifPresent(productPrices::append);
				productQtys.append(orderEntryData.getQuantity());
			}
			productCode=productCodes.toString();
		}	
		
		Object[] recommProduct=siteOneProductFacade.getRecommendedProductsToDisplay(placementPage, pimCategoryId, productCode, sessionId, pagePosition
				, orderId, productPrices.toString(), null, productQtys.toString(), searchTerm);

		List<ProductData> productList=new ArrayList<>();
		
		if(recommProduct[1]!=null) {
			productList=(List<ProductData>)recommProduct[1];
		}
		List<ProductWsDTO> productWsList = getDataMapper().mapAsList(productList, ProductWsDTO.class, FULL_OPTION);
		RecommendedProductsWsDTO recommendedProductsWsDTO=new RecommendedProductsWsDTO();
		recommendedProductsWsDTO.setRecommendedProducts(productWsList);
		recommendedProductsWsDTO.setRecommendationTitle((String)recommProduct[2]);
		return recommendedProductsWsDTO;
		}
		catch(final Exception ex) {
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method recommendedProducts");
		}
		

	}

	@GetMapping(value = "/recommendedProductsPDP")
	@ResponseBody
	@Operation(operationId = "Recommended Products PDP", summary = "Recommended Products PDP", description = "Recommended Products PDP")
	@ApiBaseSiteIdParam
	public RecommendedProductsListWsDTO recommendedProductsPDP(final HttpServletRequest request, @RequestParam(value = "storeId", required = true) final String storeId, 
			@Parameter(description = "unitId") @RequestParam(required = false) final String unitId, 
			@Parameter(description = "productId") @RequestParam(required = true) final String productId)
	{
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
		final String sessionId = request.getSession().getId();
		final String placementPage = "itemPage";
		final String pagePosition = "mobileApp";
		
		Object[] recommProduct=siteOneProductFacade.getRecommendedProductsToDisplay(placementPage, null, productId, sessionId, pagePosition
				, null, null, null, null, null);

		List<RecommendedProductsWsDTO> recommendedProductsWsDTOList=new ArrayList<>();
		
		List<ProductData> productList1=new ArrayList<>();		
		if(recommProduct[1]!=null) {
			productList1=(List<ProductData>)recommProduct[1];
		}
		List<ProductWsDTO> productWsList1= new ArrayList<>();
		productWsList1 = getDataMapper().mapAsList(productList1, ProductWsDTO.class, FULL_OPTION);		
		RecommendedProductsWsDTO recommendedProductsWsDTO1=new RecommendedProductsWsDTO();
		recommendedProductsWsDTO1.setRecommendedProducts(productWsList1);
		recommendedProductsWsDTO1.setRecommendationTitle((String)recommProduct[2]);
		recommendedProductsWsDTOList.add(recommendedProductsWsDTO1);
		
		List<ProductData> productList2=new ArrayList<>();		
		if(recommProduct[3]!=null) {
			productList2=(List<ProductData>)recommProduct[3];
		}
		List<ProductWsDTO> productWsList2=new ArrayList<>();	
		productWsList2 = getDataMapper().mapAsList(productList2, ProductWsDTO.class, FULL_OPTION);
		RecommendedProductsWsDTO recommendedProductsWsDTO2=new RecommendedProductsWsDTO();
		recommendedProductsWsDTO2.setRecommendedProducts(productWsList2);
		recommendedProductsWsDTO2.setRecommendationTitle((String)recommProduct[4]);
		recommendedProductsWsDTOList.add(recommendedProductsWsDTO2);
		
		RecommendedProductsListWsDTO recommendedProductsListWsDTO = new RecommendedProductsListWsDTO();
		recommendedProductsListWsDTO.setPlacements(recommendedProductsWsDTOList);
		return recommendedProductsListWsDTO;
		}
		catch(final Exception ex) {
			LOG.error(ex.getMessage(),ex);
			throw new RuntimeException("Exception occurred while calling the method recommendedProductsPDP");
		}

	}
	
}
