package com.siteone.facades.product.impl;

import de.hybris.platform.acceleratorfacades.order.data.PriceRangeData;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ProductHighlights;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.product.data.VariantMatrixElementData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.impl.DefaultProductFacade;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.impl.GeometryUtils;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.model.RecentScanProductsModel;
import com.siteone.core.model.SiteonePricingTrackModel;
import com.siteone.core.product.dao.SiteOneProductUOMDao;
import com.siteone.core.recentscan.service.SiteOneRecentScanService;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.services.SiteOneCalculationService;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facades.customer.price.SiteOneCspResponse;
import com.siteone.facades.customer.price.SiteOnePdpPriceDataUom;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.populators.ProductSellableAndMessagePopulator;
import com.siteone.facades.populators.SiteOneProductBasicPopulator;
import com.siteone.facades.populators.SiteOneProductGalleryImagesPopulator;
import com.siteone.facades.populators.SiteOneProductPriceRangePopulator;
import com.siteone.facades.populators.SiteOneProductPriceRangePopulator.CSPPriceRangeComparator;
import com.siteone.facades.populators.SiteOneProductPrimaryImagePopulator;
import com.siteone.facades.populators.SiteOneProductPromotionsPopulator;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.savedList.data.CustomerPriceData;
import com.siteone.facades.sds.SiteOneSDSProductSearchFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.algonomy.data.AlgonomyRequestData;
import com.siteone.integration.algonomy.data.AlgonomyResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.price.data.Prices;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.product.data.SiteOneSalesRequestData;
import com.siteone.integration.product.data.SiteOneSalesResponseData;
import com.siteone.integration.product.data.SiteOneWsCategoryResponseData;
import com.siteone.integration.product.data.SiteOneWsProductResponseData;
import com.siteone.integration.scan.product.data.SingleProductResponse;
import com.siteone.integration.scan.product.data.SiteOneScanProductPriceList;
import com.siteone.integration.scan.product.data.SiteOneScanProductResponse;
import com.siteone.integration.scan.product.data.SiteOneWsScanProductRequestData;
import com.siteone.integration.services.mpos.SiteOneScanProductWebService;
import com.siteone.integration.services.ue.SiteOnePriceWebService;
import com.siteone.integration.services.ue.impl.DefaultSiteOneSalesDataWebService;
import com.siteone.facades.savedList.SiteoneSavedListFacade;

/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductFacade extends DefaultProductFacade implements SiteOneProductFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneProductFacade.class);

	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.totalprice.digits", 2);

	private static final String CURRENCY_ISO = "USD";
	private static final String VARIANT_PRODUCT = "VARIANT";
	private static final String RECOMMENDED_PRODUCT = "RecommendedProduct";
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	private static final String D365_CSP_BRANCHES = "d365cspPriceBranches";
	private static final String D365_NEW_URL = "isD365NewUrlEnable";

	public static final double IMPERIAL_DISTANCE_RATIO = 0.62137;

	private SiteOneProductService siteOneProductService;

	private SiteOnePriceWebService siteOnePriceWebService;

	private PriceDataFactory priceDataFactory;

	private SiteOneStoreSessionFacade storeSessionFacade;

	private Converter<ProductModel, ProductData> promotionsConverter;

	private UserFacade userFacade;

	private SessionService sessionService;

	private ProductSellableAndMessagePopulator productSellableAndMessagePopulator;

	private SiteOneProductBasicPopulator<ProductModel, ProductData> productBasicPopulator;

	private SiteOneProductPrimaryImagePopulator<ProductModel, ProductData> productPrimaryImagePopulator;

	private SiteOneProductGalleryImagesPopulator<ProductModel, ProductData> productGalleryImagesPopulator;
	
	public SiteOneProductGalleryImagesPopulator<ProductModel, ProductData> getProductGalleryImagesPopulator()
	{
		return productGalleryImagesPopulator;
	}

	public void setProductGalleryImagesPopulator(
			SiteOneProductGalleryImagesPopulator<ProductModel, ProductData> productGalleryImagesPopulator)
	{
		this.productGalleryImagesPopulator = productGalleryImagesPopulator;
	}

	public SiteOneProductPrimaryImagePopulator<ProductModel, ProductData> getProductPrimaryImagePopulator()
	{
		return productPrimaryImagePopulator;
	}

	public void setProductPrimaryImagePopulator(
			SiteOneProductPrimaryImagePopulator<ProductModel, ProductData> productPrimaryImagePopulator)
	{
		this.productPrimaryImagePopulator = productPrimaryImagePopulator;
	}

	/**
	 * @return the productBasicPopulator
	 */
	public SiteOneProductBasicPopulator<ProductModel, ProductData> getProductBasicPopulator()
	{
		return productBasicPopulator;
	}

	/**
	 * @param productBasicPopulator the productBasicPopulator to set
	 */
	public void setProductBasicPopulator(final SiteOneProductBasicPopulator<ProductModel, ProductData> productBasicPopulator)
	{
		this.productBasicPopulator = productBasicPopulator;
	}

	
	private SiteOneProductPromotionsPopulator productPromotionsPopulator;

	private CommercePriceService commercePriceService;

	private SiteOneStockLevelService siteOneStockLevelService;
	
	private SiteOneProductPriceRangePopulator<ProductModel, ProductData> productPriceRangePopulator;

	@Resource(name = "b2bUnitConverter")
	private Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter;
	private static final String SESSION_SHIPTO = "shipTo";

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private MessageSource messageSource;

	@Resource(name = "siteOneScanProductWebService")
	private SiteOneScanProductWebService siteOneScanProductWebService;

	@Resource(name = "siteOneProductUOMDao")
	private SiteOneProductUOMDao siteOneProductUOMDao;

	@Resource(name = "cwsProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "regulatoryStatesCronJobService")
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService storeFinderService;
	
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;


	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;

	@Resource(name = "calculationService")
	private SiteOneCalculationService calculationService;

	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;

	@Resource(name = "siteOneSDSProductSearchFacade")
	private SiteOneSDSProductSearchFacade siteOneSDSProductSearchFacade;
	
	@Resource(name = "siteOneRecentScanService")
	private SiteOneRecentScanService siteOneRecentScanService;
	
	@Resource(name = "stockService")
	private StockService stockService;

	@Resource
	private CatalogVersionService catalogVersionService;
	
	@Resource(name = "siteOneProductUOMService")
	private SiteOneProductUOMService siteOneProductUOMService;
	
	@Resource(name = "siteOneSalesDataWebService")
	private DefaultSiteOneSalesDataWebService siteOneSalesDataWebService;
	
	@Resource(name = "siteoneSavedListFacade")
	private SiteoneSavedListFacade siteoneSavedListFacade;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	
	private static final String HOME_PLACEMENTS = "algonomy.home_page.placements";
	private static final String ITEM_PLACEMENTS = "algonomy.item_page.placements";
	private static final String CATEGORY_PLACEMENTS = "algonomy.category_page.placements";
	private static final String ATC_PLACEMENTS = "algonomy.atc_page.placements";
	private static final String PURCHASE_COMPLETE_PLACEMENTS = "algonomy.purchase_complete_page.placements";
	private static final String SEARCH_PLACEMENTS = "algonomy.search_page.placements";
	private static final String CART_PLACEMENTS = "algonomy.cart_page.placements";
	private static final String BUYAGAIN_PAGE_PLACEMENT = "algonomy.buyAgainProductsrr1.placements";
	private static final String PERSONAL_PAGE_PLACEMENT = "algonomy.recentProductsrr2.placements";		
	private static final String RECOMPROD_PAGE_PLACEMENTS = "algonomy.recomProductsrr3.placements";
	
	private static final String ALGONOMYRECOMMFLAG = "AlgonomyProductRecommendationEnable";
	private static final int CURRENCY_PERUNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);
	private static final String OUTOFSTOCK = "outOfStock";
	private static final String BACKORDER = "backorder";

	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @return the productPromotionsPopulator
	 */
	public SiteOneProductPromotionsPopulator getProductPromotionsPopulator()
	{
		return productPromotionsPopulator;
	}

	/**
	 * @param productPromotionsPopulator
	 *           the productPromotionsPopulator to set
	 */
	public void setProductPromotionsPopulator(final SiteOneProductPromotionsPopulator productPromotionsPopulator)
	{
		this.productPromotionsPopulator = productPromotionsPopulator;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public Converter<B2BUnitModel, B2BUnitData> getB2bUnitConverter()
	{
		return b2bUnitConverter;
	}


	public void setB2bUnitConverter(final Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter)
	{
		this.b2bUnitConverter = b2bUnitConverter;
	}

	/**
	 * @return the i18nService
	 */
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



	/**
	 * @return the productSellableAndMessagePopulator
	 */
	public ProductSellableAndMessagePopulator getProductSellableAndMessagePopulator()
	{
		return productSellableAndMessagePopulator;
	}

	/**
	 * @param productSellableAndMessagePopulator
	 *           the productSellableAndMessagePopulator to set
	 */
	public void setProductSellableAndMessagePopulator(final ProductSellableAndMessagePopulator productSellableAndMessagePopulator)
	{
		this.productSellableAndMessagePopulator = productSellableAndMessagePopulator;
	}




	/**
	 * @return the commercePriceService
	 */
	public CommercePriceService getCommercePriceService()
	{
		return commercePriceService;
	}

	/**
	 * @param commercePriceService
	 *           the commercePriceService to set
	 */
	public void setCommercePriceService(final CommercePriceService commercePriceService)
	{
		this.commercePriceService = commercePriceService;
	}




	/**
	 * @return the siteOneStockLevelService
	 */
	public SiteOneStockLevelService getSiteOneStockLevelService()
	{
		return siteOneStockLevelService;
	}

	/**
	 * @param siteOneStockLevelService
	 *           the siteOneStockLevelService to set
	 */
	public void setSiteOneStockLevelService(final SiteOneStockLevelService siteOneStockLevelService)
	{
		this.siteOneStockLevelService = siteOneStockLevelService;
	}

	/**
	 * @return the productPriceRangePopulator
	 */
	public SiteOneProductPriceRangePopulator<ProductModel, ProductData> getProductPriceRangePopulator()
	{
		return productPriceRangePopulator;
	}

	/**
	 * @param productPriceRangePopulator
	 *           the productPriceRangePopulator to set
	 */
	public void setProductPriceRangePopulator(final SiteOneProductPriceRangePopulator<ProductModel, ProductData> productPriceRangePopulator)
	{
		this.productPriceRangePopulator = productPriceRangePopulator;
	}


	@Autowired
	private CommonI18NService commonI18NService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.product.SiteOneProductFacade#getProductByCodeForSearch(java.lang.String,
	 * java.util.Collection)
	 */
	@Override
	public ProductData getProductBySearchTermForSearch(final String searchTerm, final Collection<ProductOption> options)
	{
		final ProductModel productModel = getSiteOneProductService().getProductBySearchTermForSearch(searchTerm);
		if (productModel != null)
		{
			return getProductForOptions(productModel, options);
		}
		else
		{
			return null;
		}
	}

	@Override
	public ProductData getProductForList(final String searchTerm, final Collection<ProductOption> options)
	{
		final ProductModel productModel = getSiteOneProductService().getProductForList(searchTerm);
		if (productModel != null)
		{
			return getProductForOptions(productModel, options);
		}
		else
		{
			return null;
		}
	}


	@Override
	public SiteOneCspResponse getPriceForCustomer(final String productCode,String inventoryUOMDataCode, Integer quantity) {

		final SiteOneCspResponse cspResponse = new SiteOneCspResponse();
		final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();


		if ((getSessionService().getAttribute("softLogin")) != null)
		{

			final boolean isSoftLogin = ((boolean) getSessionService().getAttribute("softLogin"));

			if (isSoftLogin)
			{
				final B2BUnitModel b2BUnitModel = b2bCustomer.getDefaultB2BUnit();
				final B2BUnitData b2BUnitData = getB2bUnitConverter().convert(b2BUnitModel);
				getSessionService().setAttribute(SESSION_SHIPTO, b2BUnitData);
			}
		}
		if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true))
		{
			final ProductModel productModel = getProductService().getProductForCode(productCode);
			final Map<ProductModel, Integer> products = new HashMap<>();
			final Map<ProductModel, String> productsUoms = new HashMap<>();

			products.put(productModel, quantity);
			if(inventoryUOMDataCode != null) {
				productsUoms.put(productModel, inventoryUOMDataCode);
			}
			boolean isHideCSPEnabled = false;
			for (final VariantProductModel variant : productModel.getVariants())
			{
				if (variant.getHideCSP() != null && variant.getHideCSP().booleanValue())
				{
					isHideCSPEnabled = true;
				}
			}
			for (final ProductModel variant : productModel.getVariants())
			{
				if(BooleanUtils.isNotTrue(variant.getIsProductDiscontinued())) {
					products.put(variant, quantity);
					InventoryUPCModel inventoryUPC = variant.getUpcData().stream().filter(upc->!upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
					if(inventoryUPC != null) {
						productsUoms.put(variant, inventoryUPC.getCode());
					}
				}
			}
			String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

			try
			{

				final SiteOneWsPriceResponseData siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products, productsUoms,
						storeId, nearbyStoresList, b2bCustomer, currencyIso,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
						siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), null);

				if (null != siteOneWsPriceResponseData)
				{
					if (!isHideCSPEnabled)
					{
						final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
						if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isEmpty(productModel.getVariants()))
						{
							final String price = allPrices.get(0).getPrice();

							if (null != price && productCode.equals(allPrices.get(0).getSkuId()))
							{
								final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(price),
										currencyIso);
								cspResponse.setPrice(priceData);
								cspResponse.setIsSuccess(true);
							}
							else
							{
								cspResponse.setIsSuccess(false);
								cspResponse.setErrorMessageToDisplay("SKU id not matched");
							}
						}
						else if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isNotEmpty(productModel.getVariants()))
						{

							//final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();

							allPrices.removeIf(priceInfo -> (Double.compare(Double.parseDouble(priceInfo.getPrice()), 0.0d)) == 0);

							// sort the list
							Collections.sort(allPrices, CSPPriceRangeComparator.INSTANCE);
							final PriceRangeData priceRange = new PriceRangeData();
							final BigDecimal minPrice = new BigDecimal(allPrices.get(0).getPrice());
							final BigDecimal maxPrice = new BigDecimal(allPrices.get(allPrices.size() - 1).getPrice());
							priceRange.setMinPrice(getPriceDataFactory().create(PriceDataType.FROM, minPrice,
									getCommonI18NService().getCurrentCurrency().getIsocode()));
							priceRange.setMaxPrice(getPriceDataFactory().create(PriceDataType.FROM, maxPrice,
									getCommonI18NService().getCurrentCurrency().getIsocode()));
							cspResponse.setPriceRange(priceRange);
							cspResponse.setIsSuccess(true);
							//productData.setPriceRange(priceRange);
						}
						else
						{
							cspResponse.setIsSuccess(false);
						}
					}
					else
					{
						cspResponse.setIsSuccess(false);
						cspResponse.setErrorMessageToDisplay("true");
					}
				}
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				cspResponse.setIsSuccess(false);
				cspResponse.setErrorMessageToDisplay(
						getMessageSource().getMessage("ue.price.unavailable", null, getI18nService().getCurrentLocale()));
			}
		}

		return cspResponse;

	
	}

	public PriceData getRetailPriceBranch(final String productCode, final String inventoryUOMDataCode, final Integer quantity)
	{
		final SiteOneCspResponse cspResponse = new SiteOneCspResponse();
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		final Map<ProductModel, Integer> products = new HashMap<>();
		final Map<ProductModel, String> productsUoms = new HashMap<>();

		products.put(productModel, quantity);
		if (inventoryUOMDataCode != null)
		{
			productsUoms.put(productModel, inventoryUOMDataCode);
		}

		boolean isHideListEnabled = false;
		for (final VariantProductModel variant : productModel.getVariants())
		{
			if (variant.getHideList() != null && variant.getHideList().booleanValue())
			{
				isHideListEnabled = true;
			}
		}

		String storeId = null;
		String branchRetailID = null;
		List<PointOfServiceData> nearbyStoresList = null;
		if (null != storeSessionFacade.getSessionStore())
		{
			storeId = storeSessionFacade.getSessionStore().getStoreId();
			nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			branchRetailID = storeSessionFacade.getSessionStore().getCustomerRetailId();
		}
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

		try
		{

			final SiteOneWsPriceResponseData siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products, productsUoms,
					storeId, nearbyStoresList, null, currencyIso,
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
					siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), branchRetailID);

			if (null != siteOneWsPriceResponseData)
			{
				if (!isHideListEnabled)
				{
					final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
					if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isEmpty(productModel.getVariants()))
					{
						final String price = allPrices.get(0).getPrice();

						if (null != price && productCode.equals(allPrices.get(0).getSkuId()))
						{
							return getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(price), currencyIso);

						}
					}
				}
			}
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			cspResponse.setIsSuccess(false);
			cspResponse.setErrorMessageToDisplay(
					getMessageSource().getMessage("ue.price.unavailable", null, getI18nService().getCurrentLocale()));
		}


		return null;

	}

	@Override
	public SiteOneCspResponse getPriceForCustomer(final String productCode, final Integer quantity, final String storeId,
			final String branchRetailId)
	{
		final SiteOneCspResponse cspResponse = new SiteOneCspResponse();

		B2BCustomerModel b2bCustomer=null;
		if(StringUtils.isEmpty(branchRetailId))
		{
		b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
		if ((getSessionService().getAttribute("softLogin")) != null)
		{

			final boolean isSoftLogin = ((boolean) getSessionService().getAttribute("softLogin"));

			if (isSoftLogin)
			{
				final B2BUnitModel b2BUnitModel = b2bCustomer.getDefaultB2BUnit();
				final B2BUnitData b2BUnitData = getB2bUnitConverter().convert(b2BUnitModel);
				getSessionService().setAttribute(SESSION_SHIPTO, b2BUnitData);
			}
		}
		}
		if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) || StringUtils.isNotEmpty(branchRetailId))
		{
			final ProductModel productModel = getProductService().getProductForCode(productCode);
			final Map<ProductModel, Integer> products = new HashMap<>();
			final Map<ProductModel, String> productsUoms = new HashMap<>();
			products.put(productModel, quantity);
			final InventoryUPCModel inventoryUOM = productModel.getUpcData().stream()
					.filter(upc -> !upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
			if (inventoryUOM != null)
			{
				productsUoms.put(productModel, inventoryUOM.getCode());
			}

			boolean isHideCSPEnabled = false;
			for (final VariantProductModel variant : productModel.getVariants())
			{
				if(StringUtils.isNotEmpty(branchRetailId))
				{
				if (!variant.getIsProductDiscontinued().booleanValue() && variant.getHideList() != null
						&& variant.getHideList().booleanValue())
				{
					isHideCSPEnabled = true;
				}
				}
				else
				{
					if (!variant.getIsProductDiscontinued().booleanValue() && variant.getHideCSP() != null
							&& variant.getHideCSP().booleanValue())
					{
						isHideCSPEnabled = true;
					}
				}
			}
			//			for (final ProductModel variant : productModel.getVariants())
			//			{
			//
			//				//products.put(variant, quantity);
			//			}
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

			try
			{
				final SiteOneWsPriceResponseData siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products, productsUoms,
						storeId, nearbyStoresList, b2bCustomer, currencyIso,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
						siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), branchRetailId);

				if (null != siteOneWsPriceResponseData)
				{
					if(StringUtils.isNotEmpty(branchRetailId) && !isHideCSPEnabled)
					{
						final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
						if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isEmpty(productModel.getVariants()))
						{
							final String price = allPrices.get(0).getPrice();

							if (null != price && productCode.equals(allPrices.get(0).getSkuId()))
							{
								final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(price),
										currencyIso);
								cspResponse.setPrice(priceData);
								cspResponse.setIsSuccess(true);
							}
							else
							{
								cspResponse.setIsSuccess(false);
								cspResponse.setErrorMessageToDisplay("SKU id not matched");
							}
						}
						else if (CollectionUtils.isNotEmpty(productModel.getVariants()))
						{
							final ProductData productData = new ProductData();
							getProductConfiguredPopulator().populate(productModel, productData,
									Arrays.asList(ProductOption.VARIANT_FULL));
							setRetailPriceForVariants(productData,branchRetailId);
							final List<Double> allVariantPrices = new ArrayList<Double>();
							for (final VariantOptionData data : productData.getVariantOptions())
							{
								if (data.getPriceData() != null)
								{
									final StockData stockData = data.getStock();
									boolean inventoryFlag = false;
									if (!((null != stockData && null != stockData.getStockLevel()
											&& stockData.getStockLevel() > Long.valueOf(0))
											|| (null != stockData && null != stockData.getInventoryHit() && stockData.getInventoryHit() > 0))
											&& (null != data.getInventoryCheck() && data.getInventoryCheck()))
									{
										inventoryFlag = true;
									}
									if (!inventoryFlag)
									{
										allVariantPrices.add(data.getPriceData().getValue().doubleValue());
									}
								}
							}
							if (CollectionUtils.isNotEmpty(allVariantPrices))
							{
								allVariantPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);
								// sort the list
								Collections.sort(allVariantPrices);
								final PriceRangeData priceRange = new PriceRangeData();
								final BigDecimal minPrice = BigDecimal.valueOf(allVariantPrices.get(0));
								final BigDecimal maxPrice = BigDecimal.valueOf(allVariantPrices.get(allVariantPrices.size() - 1));
								priceRange.setMinPrice(getPriceDataFactory().create(PriceDataType.FROM, minPrice,
										getCommonI18NService().getCurrentCurrency().getIsocode()));
								priceRange.setMaxPrice(getPriceDataFactory().create(PriceDataType.FROM, maxPrice,
										getCommonI18NService().getCurrentCurrency().getIsocode()));
								cspResponse.setPriceRange(priceRange);
								cspResponse.setIsSuccess(true);
							}
							//productData.setPriceRange(priceRange);
						}
						else
						{
							cspResponse.setIsSuccess(false);
							cspResponse.setErrorMessageToDisplay("true");
						}
					}
					else
					{
					if (!isHideCSPEnabled)
					{
						final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
						if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isEmpty(productModel.getVariants()))
						{
							final String price = allPrices.get(0).getPrice();

							if (null != price && productCode.equals(allPrices.get(0).getSkuId()))
							{
								final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(price),
										currencyIso);
								cspResponse.setPrice(priceData);
								cspResponse.setIsSuccess(true);
							}
							else
							{
								cspResponse.setIsSuccess(false);
								cspResponse.setErrorMessageToDisplay("SKU id not matched");
							}
						}
						else if (CollectionUtils.isNotEmpty(productModel.getVariants()))
						{
							final ProductData productData = new ProductData();
							getProductConfiguredPopulator().populate(productModel, productData,
									Arrays.asList(ProductOption.VARIANT_FULL));
							setCustomerPriceForVariants(productData,null);
							final List<Double> allVariantPrices = new ArrayList<Double>();
							for (final VariantOptionData data : productData.getVariantOptions())
							{
								if (data.getCustomerPrice() != null)
								{
									final StockData stockData = data.getStock();
									boolean inventoryFlag = false;
									if (!((null != stockData && null != stockData.getStockLevel()
											&& stockData.getStockLevel() > Long.valueOf(0))
											|| (null != stockData && null != stockData.getInventoryHit() && stockData.getInventoryHit() > 0))
											&& (null != data.getInventoryCheck() && data.getInventoryCheck()))
									{
										inventoryFlag = true;
									}
									if (!inventoryFlag)
									{
										allVariantPrices.add(Double.parseDouble(data.getCustomerPrice().getPrice()));
									}
								}
							}
							if (CollectionUtils.isNotEmpty(allVariantPrices))
							{
								allVariantPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);
								// sort the list
								Collections.sort(allVariantPrices);
								final PriceRangeData priceRange = new PriceRangeData();
								final BigDecimal minPrice = BigDecimal.valueOf(allVariantPrices.get(0));
								final BigDecimal maxPrice = BigDecimal.valueOf(allVariantPrices.get(allVariantPrices.size() - 1));
								priceRange.setMinPrice(getPriceDataFactory().create(PriceDataType.FROM, minPrice,
										getCommonI18NService().getCurrentCurrency().getIsocode()));
								priceRange.setMaxPrice(getPriceDataFactory().create(PriceDataType.FROM, maxPrice,
										getCommonI18NService().getCurrentCurrency().getIsocode()));
								cspResponse.setPriceRange(priceRange);
								cspResponse.setIsSuccess(true);
							}
							//productData.setPriceRange(priceRange);
						}
						else
						{
							cspResponse.setIsSuccess(false);
						}
					}
					else
					{
						cspResponse.setIsSuccess(false);
						cspResponse.setErrorMessageToDisplay("true");
					}
					}
				}
				}
			catch (final ResourceAccessException resourceAccessException)
			{
				cspResponse.setIsSuccess(false);
				cspResponse.setErrorMessageToDisplay(
						getMessageSource().getMessage("ue.price.unavailable", null, getI18nService().getCurrentLocale()));
			}
		}

	return cspResponse;

	}

	@Override
	public SiteOnePdpPriceDataUom getCustomerPriceforUom(final String productCode, final Integer quantity, final String inventoryUomId, Collection<ProductOption> productOptions)
	{
		float uomMultiplier=1;
		float totalQuantity=1;
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, productOptions);
		SiteOnePdpPriceDataUom siteOnePdpPriceDataUom = new SiteOnePdpPriceDataUom();
		boolean isSuccess = false;
		final ProductModel product = getProductService().getProductForCode(productCode);
		InventoryUPCModel inventoryUOM = null;
		String productUomCode = null;
		if(StringUtils.isEmpty(inventoryUomId)) {
			inventoryUOM=product.getUpcData().stream().filter(upc -> upc.getBaseUPC().booleanValue() && !upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
		} else {
			inventoryUOM=product.getUpcData().stream().filter(upc -> upc.getInventoryUPCID().equals(inventoryUomId)).findFirst().orElse(null);
		}
		if(inventoryUOM != null) {
			uomMultiplier = (inventoryUOM.getInventoryMultiplier()).intValue();
			totalQuantity = quantity.floatValue() * uomMultiplier;
			productUomCode = inventoryUOM.getCode();
		}
		PriceData unitPrice = null;
		PriceData retailPrice = null;
		if (null != productData.getPrice())
		{
			retailPrice = productData.getPrice();
		}
		if(getUserService().isAnonymousUser(getUserService().getCurrentUser())) 
		{
			unitPrice = productData.getPrice();
			isSuccess = true;
		}
		else
		{
			final SiteOneCspResponse siteOneCspResponse = getPriceForCustomer(productCode, productUomCode, (int) totalQuantity);	
			if (null != siteOneCspResponse)
			{
				unitPrice = siteOneCspResponse.getPrice();
				isSuccess = true;
			}
		}
		if(unitPrice != null && totalQuantity > 0)
		{
			if (retailPrice != null)
			{
				final double retailPrices = commonI18NService.roundCurrency(retailPrice.getValue().floatValue() * uomMultiplier,
						CURRENCY_PERUNIT_PRICE_DIGITS);
				siteOnePdpPriceDataUom
						.setRetailPrice(createPriceData(BigDecimal.valueOf(retailPrices).setScale(2, RoundingMode.HALF_UP)));
			}
			final double totalPrice = commonI18NService.roundCurrency(
					unitPrice.getValue().floatValue() * totalQuantity, CURRENCY_UNIT_PRICE_DIGITS);
			final double uomPrice = commonI18NService.roundCurrency(
					unitPrice.getValue().floatValue() * uomMultiplier, CURRENCY_PERUNIT_PRICE_DIGITS);
			siteOnePdpPriceDataUom.setUnitPrice(unitPrice);
			siteOnePdpPriceDataUom.setUomPrice(createPriceData(BigDecimal.valueOf(uomPrice).setScale(2,RoundingMode.HALF_UP)));
			siteOnePdpPriceDataUom.setTotalPrice(createPriceData(BigDecimal.valueOf(totalPrice).setScale(2,RoundingMode.HALF_UP)));
			siteOnePdpPriceDataUom.setNoOfUnits((int) totalQuantity);
		}
		siteOnePdpPriceDataUom.setIsSuccess(isSuccess);
		return siteOnePdpPriceDataUom;

	}

	@Override
	public ProductData getProductByUOM(final String productCode, final float inventoryMultiplier, String inventoryUOMDataCode)
	{
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		final ProductData productData = new ProductData();
		final List<ProductOption> options = Arrays.asList(ProductOption.PRICE, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE);
		getProductConfiguredPopulator().populate(productModel, productData, options);
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		final int digits = currency.getDigits().intValue();
		PriceData branchRetailPrice=null;
		//final int currencyRoundoThreeDigits = 3;
		String inventoryUOMCode = null;
		if (inventoryUOMDataCode != null)
		{
			final InventoryUPCModel inventory = siteOneProductUOMService.getInventoryUOMById(inventoryUOMDataCode);
			if (inventory != null)
			{
				inventoryUOMCode = inventory.getCode();
			}
		}
		if (null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId()))
		{
			branchRetailPrice = getRetailPriceBranch(productCode, inventoryUOMCode, (int) inventoryMultiplier);
			productData.setPrice(branchRetailPrice);
		}

		if (productData.getCustomerPrice() != null)
		{

			final PriceData customerPrice = getPriceForCustomer(productCode, inventoryUOMCode, (int) inventoryMultiplier).getPrice();
			final double uomPrice = commonI18NService.roundCurrency(customerPrice.getValue().floatValue() * inventoryMultiplier,
					CURRENCY_PERUNIT_PRICE_DIGITS);
			if (productData.getPrice() != null)
			{
				final double listuomPrice = commonI18NService.roundCurrency(
						productData.getPrice().getValue().floatValue() * inventoryMultiplier, CURRENCY_PERUNIT_PRICE_DIGITS);
				productData.setUnitPrice(customerPrice);
				productData.setPrice(
						createPriceData(BigDecimal.valueOf(listuomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP)));
				productData.setCustomerPrice(
						createPriceData(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP)));
			}
			else
			{
				productData.setUnitPrice(customerPrice);
				productData.setPrice(
						createPriceData(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP)));
				productData.setCustomerPrice(
						createPriceData(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP)));
			}
		}
		else if (productData.getPrice() != null)
		{
			final double uomPrice = commonI18NService.roundCurrency(
					productData.getPrice().getValue().floatValue() * inventoryMultiplier, CURRENCY_PERUNIT_PRICE_DIGITS);
			productData.setUnitPrice(productData.getPrice());
			productData.setPrice(
					createPriceData(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS, RoundingMode.HALF_UP)));

		}
		final List<ProductOption> promotionOptions = Arrays.asList(ProductOption.PROMOTIONS);
		getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);

		return productData;
	}





	@Override
	public ProductData getProductByUOMPLP(final String productCode, final float inventoryMultiplier)
	{
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		final ProductData productData = new ProductData();
		final List<ProductOption> options = Arrays.asList(ProductOption.PRICE, ProductOption.STOCK, ProductOption.CUSTOMER_PRICE);
		getProductConfiguredPopulator().populate(productModel, productData, options);

		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		final int digits = currency.getDigits().intValue();
		if (productData.getCustomerPrice() != null)
		{
			final double uomPrice = commonI18NService.roundCurrency(
					productData.getCustomerPrice().getValue().floatValue() * inventoryMultiplier, CURRENCY_UNIT_PRICE_DIGITS);
			productData.getCustomerPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(2));


		}
		if (productData.getPrice() != null)
		{
			final double uomPrice = commonI18NService
					.roundCurrency(productData.getPrice().getValue().floatValue() * inventoryMultiplier, CURRENCY_UNIT_PRICE_DIGITS);
			productData.getPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(2));
		}

		final List<ProductOption> promotionOptions = Arrays.asList(ProductOption.PROMOTIONS);
		getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);

		return productData;
	}

	public ProductData updateUOMPriceForSingleUOM(final ProductData productData)
	{
		final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();
		if (inventoryUOMListData != null && inventoryUOMListData.size() == 1)
		{
			LOG.info("sellable uom there");
			final Float multiplierVal = inventoryUOMListData.get(0).getInventoryMultiplier();

			if (multiplierVal != null)
			{
				final ProductModel productModel = getProductService().getProductForCode(productData.getCode());
				if (productData.getPrice() != null)
				{
						final double uomPrice = commonI18NService
								.roundCurrency(productData.getPrice().getValue().floatValue() * multiplierVal, CURRENCY_PERUNIT_PRICE_DIGITS);
						productData.getPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS,RoundingMode.HALF_UP));
						productData.getPrice().setFormattedValue("$".concat((productData.getPrice().getValue().setScale(2,RoundingMode.HALF_UP)).toString()));
				}
				if (productData.getCustomerPrice() != null)
				{
						final double uomPrice = commonI18NService.roundCurrency(
								productData.getCustomerPrice().getValue().floatValue() * multiplierVal, CURRENCY_PERUNIT_PRICE_DIGITS);
						productData.getCustomerPrice().setValue(BigDecimal.valueOf(uomPrice).setScale(CURRENCY_UNIT_PRICE_DIGITS,RoundingMode.HALF_UP));
						productData.getCustomerPrice()
						.setFormattedValue("$".concat((productData.getCustomerPrice().getValue().setScale(2,RoundingMode.HALF_UP)).toString()));
				}
				final List<ProductOption> promotionOptions = Arrays.asList(ProductOption.PROMOTIONS);
				getProductConfiguredPopulator().populate(productModel, productData, promotionOptions);
			}
		}
		return productData;
	}

	public ProductData getProductByUOMPriceRange(final ProductData productData)
	{
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		final int digits = currency.getDigits().intValue();
		final ArrayList<Float> multipliers = new ArrayList();

		if (productData.getSellableUoms() != null)
		{
			final List<InventoryUOMData> uoms = productData.getSellableUoms();
			for (final InventoryUOMData uom : uoms)
			{
				multipliers.add(uom.getInventoryMultiplier());
			}
			Collections.sort(multipliers);
		}
		if (!CollectionUtils.isEmpty(multipliers) && multipliers.size() > 1)
		{
			final Float minMultiplier = multipliers.get(0);
			final Float maxMultiplier = multipliers.get(multipliers.size() - 1);

			if (productData.getCustomerPrice() != null && minMultiplier.floatValue() != maxMultiplier.floatValue())
			{
				final double minUomPrice = roundCurrency(productData.getCustomerPrice().getValue().floatValue(),
						minMultiplier.floatValue());
				final double maxUomPrice = roundCurrency(productData.getCustomerPrice().getValue().floatValue(),
						maxMultiplier.floatValue());
				if (productData.getPrice() != null)
				{
					final double minListuomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							minMultiplier.floatValue());
					final double maxListuomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							maxMultiplier.floatValue());
					final PriceRangeData priceRange = new PriceRangeData();
					final BigDecimal minPrice = new BigDecimal(minUomPrice);
					final BigDecimal maxPrice = new BigDecimal(maxUomPrice);
					final BigDecimal minLPrice = new BigDecimal(minListuomPrice);
					final BigDecimal maxLPrice = new BigDecimal(maxListuomPrice);

					priceRange.setMinPrice(createPriceData(minPrice));
					priceRange.setMaxPrice(createPriceData(maxPrice));
					priceRange.setMinLSPPrice(createPriceData(minLPrice));
					priceRange.setMaxLSPPrice(createPriceData(maxLPrice));

					productData.setPriceRange(priceRange);
				}
				else
				{
					final PriceRangeData priceRange = new PriceRangeData();
					final BigDecimal minPrice = new BigDecimal(minUomPrice);
					final BigDecimal maxPrice = new BigDecimal(maxUomPrice);
					priceRange.setMinPrice(createPriceData(minPrice));
					priceRange.setMaxPrice(createPriceData(maxPrice));

					productData.setPriceRange(priceRange);
				}
			}
			else
			{
				if (productData.getPrice() != null && minMultiplier.floatValue() != maxMultiplier.floatValue())
				{
					final double minUomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							minMultiplier.floatValue());
					final double maxUomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							maxMultiplier.floatValue());
					final PriceRangeData priceRange = new PriceRangeData();
					final BigDecimal minPrice = new BigDecimal(minUomPrice);
					final BigDecimal maxPrice = new BigDecimal(maxUomPrice);
					priceRange.setMinPrice(createPriceData(minPrice));
					priceRange.setMaxPrice(createPriceData(maxPrice));

					productData.setPriceRange(priceRange);
				}
			}
		}

		return productData;
	}

	public ProductData getProductByUOMPLPPriceRange(final ProductData productData)
	{
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		final int digits = currency.getDigits().intValue();
		final ArrayList<Float> multipliers = new ArrayList();

		if (productData.getSellableUoms() != null)
		{
			final List<InventoryUOMData> uoms = productData.getSellableUoms();
			for (final InventoryUOMData uom : uoms)
			{
				multipliers.add(uom.getInventoryMultiplier());
			}
			Collections.sort(multipliers);
		}

		if (!CollectionUtils.isEmpty(multipliers))
		{
			if (multipliers.size() > 1)
			{
				final Float minMultiplier = multipliers.get(0);
				final Float maxMultiplier = multipliers.get(multipliers.size() - 1);
				final PriceRangeData priceRange = new PriceRangeData();

				if (productData.getPrice() != null && minMultiplier.floatValue() != maxMultiplier.floatValue())
				{
					final double minListuomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							minMultiplier.floatValue());
					final double maxListuomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							maxMultiplier.floatValue());
					final BigDecimal minLPrice = new BigDecimal(minListuomPrice);
					final BigDecimal maxLPrice = new BigDecimal(maxListuomPrice);

					priceRange.setMinLSPPrice(createPriceData(minLPrice));
					priceRange.setMaxLSPPrice(createPriceData(maxLPrice));

					productData.setPriceRange(priceRange);
				}
				if (productData.getCustomerPrice() != null && minMultiplier.floatValue() != maxMultiplier.floatValue())
				{
					PriceData minCustomerPrice = productData.getCustomerPrice();
					final double minUomPrice = roundCurrency(minCustomerPrice.getValue().floatValue(),
							minMultiplier.floatValue());
					
					PriceData maxCustomerPrice = productData.getCustomerPrice();
					final double maxUomPrice = roundCurrency(maxCustomerPrice.getValue().floatValue(),
							maxMultiplier.floatValue());
					final BigDecimal minPrice = new BigDecimal(minUomPrice);
					final BigDecimal maxPrice = new BigDecimal(maxUomPrice);

					priceRange.setMinPrice(createPriceData(minPrice));
					priceRange.setMaxPrice(createPriceData(maxPrice));
					productData.setPriceRange(priceRange);
				}


			}
			else
			{
				final float multiplierVal = multipliers.get(0).floatValue();
				
				if (productData.getPrice() != null && productData.getCustomerPrice() == null 
						&& multiplierVal > 0 && productData.getPrice().getValue().floatValue() > 0 )
				{
					final double price=roundCurrency(productData.getPrice().getValue().floatValue(), multiplierVal);
					final BigDecimal bdPrice=BigDecimal.valueOf(price);
					productData.setPrice(createPriceData(bdPrice));
				}

				if (productData.getCustomerPrice() != null)
				{
					PriceData customerPrice = productData.getCustomerPrice();
					final double price=roundCurrency(customerPrice.getValue().floatValue(), multiplierVal);
					final BigDecimal bdPrice=BigDecimal.valueOf(price);	
					productData.setCustomerPrice(createPriceData(bdPrice));
				}
			}
		}

		return productData;
	}

	public ProductData getRetailAPIPriceByUOMPLPPriceRange(final ProductData productData)
	{
		final CurrencyModel currency = getCommonI18NService().getCurrentCurrency();
		final int digits = currency.getDigits().intValue();
		final ArrayList<Float> multipliers = new ArrayList();

		if (productData.getSellableUoms() != null)
		{
			final List<InventoryUOMData> uoms = productData.getSellableUoms();
			for (final InventoryUOMData uom : uoms)
			{
				multipliers.add(uom.getInventoryMultiplier());
			}
			Collections.sort(multipliers);
		}

		if (!CollectionUtils.isEmpty(multipliers))
		{
			if (multipliers.size() > 1)
			{
				final Float minMultiplier = multipliers.get(0);
				final Float maxMultiplier = multipliers.get(multipliers.size() - 1);
				final PriceRangeData priceRange = new PriceRangeData();

				if (productData.getPrice() != null && minMultiplier.floatValue() != maxMultiplier.floatValue())
				{
					final double minListuomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							minMultiplier.floatValue());
					final double maxListuomPrice = roundCurrency(productData.getPrice().getValue().floatValue(),
							maxMultiplier.floatValue());
					final BigDecimal minLPrice = new BigDecimal(minListuomPrice);
					final BigDecimal maxLPrice = new BigDecimal(maxListuomPrice);

					priceRange.setMinLSPPrice(createPriceData(minLPrice));
					priceRange.setMaxLSPPrice(createPriceData(maxLPrice));

					productData.setPriceRange(priceRange);
				}
			}
			else
			{
				final float multiplierVal = multipliers.get(0).floatValue();

				if (productData.getPrice() != null && multiplierVal > 0 && productData.getPrice().getValue().floatValue() > 0)
				{
					final double price = roundCurrency(productData.getPrice().getValue().floatValue(), multiplierVal);
					final BigDecimal bdPrice = BigDecimal.valueOf(price);
					productData.setPrice(createPriceData(bdPrice));
				}
			}
		}

		return productData;
	}


	protected PriceData createPriceData(final BigDecimal price)
	{
		return getPriceDataFactory().create(PriceDataType.FROM, price, getCommonI18NService().getCurrentCurrency().getIsocode());
	}

	double roundCurrency(final float price, final float multiplier)
	{
		return commonI18NService.roundCurrency(price * multiplier, CURRENCY_UNIT_PRICE_DIGITS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.product.SiteOneProductFacade#getProductByCodeForQuickOrder(java.lang.String,
	 * java.util.Collection)
	 */
	@Override
	public List<ProductData> getProductsForQuickOrder(final String code, final Collection<ProductOption> options)
	{
		final List<ProductData> productDataList = new ArrayList<>();
		List<ProductData> productList = new ArrayList<>();
		final List<ProductModel> productModelList = getSiteOneProductService().getProductsForQuickOrder(code);
		if (CollectionUtils.isNotEmpty(productModelList))
		{
			for (final ProductModel productModel : productModelList)
			{
				final ProductData productData = getProductForOptions(productModel, options);
				if(BooleanUtils.isTrue(productData.getOutOfStockImage()))
				{
					productList.add(productData);
				}
				updateInventoryUOMDataForHideUomEntry(productModel.getCode(), productData);
				productDataList.add(productData);
			}
		}
		if(CollectionUtils.isNotEmpty(productList))
		{
			updateSalesInfoBackorderForProduct(productList);			
		}
		return productDataList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.product.SiteOneProductFacade#getProductWithCustomerServicePrice(java.lang.String,
	 * java.lang.Integer, java.util.List)
	 */
	@Override
	public String getCustomerSpecificPrice(final String code, final Integer quantity)
	{

		final List<ProductModel> productModelList = getSiteOneProductService().getProductsForQuickOrder(code);
		if (CollectionUtils.isNotEmpty(productModelList))
		{
			final ProductData productData = (ProductData) getProductConverter().convert(productModelList.get(0));
			if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) && !userFacade.isAnonymousUser()
					&& (productData.getVariantCount() == null
							|| (productData.getVariantCount() != null && productData.getVariantCount().intValue() == 0)))
			{
				final SiteOneCspResponse cspResponse = getPriceForCustomer(productModelList.get(0).getCode(), null, quantity);

				if (cspResponse.isIsSuccess() && null != cspResponse.getPrice())
				{
					return String.valueOf(cspResponse.getPrice().getFormattedValue());
				}
			}

		}
		return "";
	}



	/**
	 * @return the siteOneProductService
	 */
	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	/**
	 * @param siteOneProductService
	 *           the siteOneProductService to set
	 */
	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}


	/**
	 * @return the siteOnePriceWebService
	 */
	public SiteOnePriceWebService getSiteOnePriceWebService()
	{
		return siteOnePriceWebService;
	}

	/**
	 * @param siteOnePriceWebService
	 *           the siteOnePriceWebService to set
	 */
	public void setSiteOnePriceWebService(final SiteOnePriceWebService siteOnePriceWebService)
	{
		this.siteOnePriceWebService = siteOnePriceWebService;
	}



	/**
	 * @return the priceDataFactory
	 */
	public PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	/**
	 * @param priceDataFactory
	 *           the priceDataFactory to set
	 */
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

	/**
	 * @return the storeSessionFacade
	 */
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

	/**
	 * @return the userFacade
	 */
	public UserFacade getUserFacade()
	{
		return userFacade;
	}

	/**
	 * @param userFacade
	 *           the userFacade to set
	 */
	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}


	/**
	 * @return the promotionsConverter
	 */
	public Converter<ProductModel, ProductData> getPromotionsConverter()
	{
		return promotionsConverter;
	}

	/**
	 * @param promotionsConverter
	 *           the promotionsConverter to set
	 */
	public void setPromotionsConverter(final Converter<ProductModel, ProductData> promotionsConverter)
	{
		this.promotionsConverter = promotionsConverter;
	}



	/**
	 * @return the productConverter
	 */


	@Override
	public SiteOneWsPriceResponseData getProductPriceForCustomer(final Map<ProductModel, Integer> products)
			throws ResourceAccessException
	{
		SiteOneWsPriceResponseData siteOneWsPriceResponseData = null;

		if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true)
				&& (null != products && !products.isEmpty()))
		{
			//Call to price service
			final String storeId = storeSessionFacade.getSessionStore().getStoreId();
			final List<PointOfServiceData> nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
			siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products, null, storeId, nearbyStoresList, b2bCustomer,
					currencyIso, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
					siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), null);
		}

		return siteOneWsPriceResponseData;

	}

	@Override
	public OrderData getPriceUpdateForHideUomEntry(final OrderData orderDetails, final boolean isTotalPrice)
	{
		final List<OrderEntryData> unconsignedEntriesUpdated = new ArrayList();
		boolean isHideUom = false;
		if (CollectionUtils.isNotEmpty(orderDetails.getUnconsignedEntries()))
		{
			for (final OrderEntryData orderEntryData : orderDetails.getUnconsignedEntries())
			{

				if (orderEntryData != null && orderEntryData.getProduct() != null && orderEntryData.getProduct().getHideUom() != null
						&& orderEntryData.getProduct().getHideUom())
				{
					isHideUom = true;
					final List<InventoryUOMData> inventoryUOMListData = orderEntryData.getProduct().getSellableUoms();
					final List<InventoryUOMData> inventoryUOMListnew = new ArrayList();

					if (CollectionUtils.isNotEmpty(inventoryUOMListData))
					{
						for (final InventoryUOMData inventoryUOMData1 : inventoryUOMListData)
						{

							if (!inventoryUOMData1.getHideUOMOnline() && orderEntryData.getProduct().getHideUom())
							{
								ProductData productDataUom;
								if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
										((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
								{
									final AbstractOrderModel orderModel = b2bOrderService.getAbstractOrderForCode(orderDetails.getCode());
									AbstractOrderEntryModel orderEntry = null;
									if(BooleanUtils.isTrue(orderDetails.getIsHybrisOrder()))
									{
										orderEntry = orderModel.getEntries().get(orderEntryData.getEntryNumber());
									}
									else
									{
										orderEntry = orderModel.getEntries().get(orderEntryData.getEntryNumber() - 1);
									}
									productDataUom = calculationService.getProductByUOM(orderEntry, inventoryUOMData1.getInventoryMultiplier());
									
								}
								else
								{
									productDataUom = getProductByUOM(orderEntryData.getProduct().getCode(),
											Float.valueOf(inventoryUOMData1.getInventoryMultiplier()), null);
								}
								inventoryUOMData1.setNewUomPrice(productDataUom.getPrice().getValue().floatValue());
								BigDecimal totalPricelocal = new BigDecimal(0);
								if (productDataUom.getCustomerPrice() != null)
								{
									orderEntryData.getBasePrice().setValue(productDataUom.getCustomerPrice().getValue());
									orderEntryData.getBasePrice()
											.setFormattedValue("$".concat(productDataUom.getCustomerPrice().getValue().toString()));
									totalPricelocal = (productDataUom.getCustomerPrice().getValue()
											.multiply(new BigDecimal(orderEntryData.getQuantity())));

								}
								else
								{
									orderEntryData.getBasePrice().setValue(productDataUom.getPrice().getValue());
									orderEntryData.getBasePrice()
											.setFormattedValue("$".concat(productDataUom.getPrice().getValue().toString()));
									totalPricelocal = (productDataUom.getPrice().getValue()
											.multiply(new BigDecimal(orderEntryData.getQuantity())));

								}
								orderEntryData.setUomMeasure(inventoryUOMData1.getMeasure());
								orderEntryData.setUomPrice(productDataUom.getPrice().getValue());
								if (isTotalPrice)
								{
									orderEntryData.getTotalPrice().setValue(totalPricelocal);
									orderEntryData.getTotalPrice().setFormattedValue("$".concat(totalPricelocal.toString()));
								}

							}
							inventoryUOMListnew.add(inventoryUOMData1);

						}
						if (orderEntryData.getProduct().getHideUom() != null && orderEntryData.getProduct().getHideUom())
						{
							orderEntryData.getProduct().getSellableUoms().clear();
							orderEntryData.getProduct().setSellableUoms(inventoryUOMListnew);
						}

					}
				}

				unconsignedEntriesUpdated.add(orderEntryData);
			}
			if (isHideUom)
			{
				orderDetails.getUnconsignedEntries().clear();
				orderDetails.setUnconsignedEntries(unconsignedEntriesUpdated);
			}
			List<ProductData> productDataList = updateSalesInfoBackorderForOrderEntry(orderDetails.getUnconsignedEntries());
			if(CollectionUtils.isNotEmpty(productDataList)) {
				for (final OrderEntryData orderConsinmentEntryData : orderDetails.getUnconsignedEntries())
				{
					productDataList.forEach(product -> {
						if(product.getCode().equalsIgnoreCase(orderConsinmentEntryData.getProduct().getCode())) {
							orderConsinmentEntryData.setProduct(product);
						}
					});
				}
			}
			
		}
		return orderDetails;
	}

	@Override
	public void updateInventoryUOMDataForHideUomEntry(final String code, final ProductData productData)
	{
		final List<InventoryUOMData> inventoryUOMListData = productData.getSellableUoms();
		final List<InventoryUOMData> inventoryUOMListnew = new ArrayList();

		if (CollectionUtils.isNotEmpty(inventoryUOMListData))
		{
			for (final InventoryUOMData inventoryUOMData1 : inventoryUOMListData)
			{

				if (!inventoryUOMData1.getHideUOMOnline() && productData.getHideUom())
				{
					final ProductData productDataUom = getProductByUOM(code,
							Float.valueOf(inventoryUOMData1.getInventoryMultiplier()), null);
					inventoryUOMData1.setNewUomPrice(productDataUom.getPrice().getValue().floatValue());
					if (productDataUom.getCustomerPrice() != null)
					{
						//productData.getCustomerPrice().setValue(BigDecimal.valueOf(productDataUom.getCustomerPrice().getValue().floatValue() * inventoryUOMData1.getInventoryMultiplier()));
						productData.getCustomerPrice()
								.setFormattedValue("$".concat(productDataUom.getCustomerPrice().getValue().toString()));

					}
					productData.getPrice().setValue(productDataUom.getPrice().getValue());
					productData.getPrice().setFormattedValue("$".concat(productDataUom.getPrice().getValue().toString()));
					productData.setSingleUomMeasure(inventoryUOMData1.getMeasure());
				}
				inventoryUOMListnew.add(inventoryUOMData1);
			}
			if (productData.getHideUom() != null && productData.getHideUom())
			{
				productData.getSellableUoms().clear();
				productData.setSellableUoms(inventoryUOMListnew);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.product.SiteOneProductFacade#getCSPResponse(java.util.List)
	 */
	@Override
	public SiteOneWsPriceResponseData getCSPResponse(final List<String> productcodeList)
	{
		SiteOneWsPriceResponseData cspResponseData = null;
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		//SE-7712, search related to 'fertilizer' brings a SKU# 571612, which is missing socode_string attribute in solr.
		//when we make a db call to fetch product details below, it gives an error for null SKU ID.
		//Need to filter items with null socode_string(skuNumber) attribute.

		final boolean productCodeNull = productcodeList.removeIf(product -> product == null || StringUtils.isEmpty(product));
		if (productCodeNull)
		{
			LOG.error("Null product code encountered");
		}
		//end SE-7712

		productModels = siteOneProductService.getAllProductsByCodes(productcodeList);

		if (CollectionUtils.isNotEmpty(productModels))
		{
			final Map<ProductModel, Integer> products = new HashMap<>();
			final Map<ProductModel, String> productsUoms = new HashMap<>();
			
			productModels.forEach(prod -> {
				if (null == prod.getVariantType())
				{
					products.put(prod, Integer.valueOf(1));
					final InventoryUPCModel inventoryUOM=prod.getUpcData().stream().filter(upc -> !upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
					if(inventoryUOM != null) {
						productsUoms.put(prod, inventoryUOM.getCode());
					}
					
				}

			});

			String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();

			if ((getSessionService().getAttribute("softLogin")) != null)
			{

				final boolean isSoftLogin = ((boolean) getSessionService().getAttribute("softLogin"));

				if (isSoftLogin)
				{
					final B2BUnitModel b2BUnitModel = b2bCustomer.getDefaultB2BUnit();
					final B2BUnitData b2BUnitData = getB2bUnitConverter().convert(b2BUnitModel);
					getSessionService().setAttribute(SESSION_SHIPTO, b2BUnitData);
				}
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();


			if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true))
			{
				try
				{

					cspResponseData = siteOnePriceWebService.getPrice(products, productsUoms, storeId, nearbyStoresList, b2bCustomer,
							currencyIso, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
							siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), null);
				}
				catch (final Exception e)
				{
					// If any exception , we will display Call for pricing message to the customer.
					LOG.error("Unable to populate CSP for the customer : " + b2bCustomer.getEmail());
				}
			}
		}
		return cspResponseData;
	}

	
	@Override
	public SiteOneWsPriceResponseData getCSPResponseData(final List<String> productCodeList,
			final Map<String, Integer> productCodeWithQunatity, final Map<String, String> productUomCodeId,
			final String branchRetailID)
	{
		SiteOneWsPriceResponseData cspResponseData = null;
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		B2BCustomerModel b2bCustomer = null;
		//SE-7712, search related to 'fertilizer' brings a SKU# 571612, which is missing socode_string attribute in solr.
		//when we make a db call to fetch product details below, it gives an error for null SKU ID.
		//Need to filter items with null socode_string(skuNumber) attribute.

		final boolean productCodeNull = productCodeList.removeIf(product -> product == null || StringUtils.isEmpty(product));
		if (productCodeNull)
		{
			LOG.error("Null product code encountered");
		}
		//end SE-7712

		productModels = siteOneProductService.getAllProductsByCodes(productCodeList);

		if (CollectionUtils.isNotEmpty(productModels))
		{
			final Map<ProductModel, Integer> products = new HashMap<>();
			final Map<ProductModel, String> productUomCode = new HashMap<>();
			productModels.forEach(prod -> {
				if (null == prod.getVariantType())
				{
					//products.put(prod, Integer.valueOf(1));
					products.put(prod, productCodeWithQunatity.get(prod.getCode()).compareTo(Integer.valueOf(1))>0?productCodeWithQunatity.get(prod.getCode()):Integer.valueOf(1));
					InventoryUPCModel inventoryUPC = prod.getUpcData().stream().filter(upc->upc.getInventoryUPCID().equalsIgnoreCase(productUomCodeId.get(prod.getCode()))).findFirst().orElse(null);
					if(inventoryUPC!=null) {
						productUomCode.put(prod,inventoryUPC.getCode());
					}
				}

			});

			String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			
			if (StringUtils.isEmpty(branchRetailID))
			{
				b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();

				if ((getSessionService().getAttribute("softLogin")) != null)
				{
					final boolean isSoftLogin = ((boolean) getSessionService().getAttribute("softLogin"));

					if (isSoftLogin)
					{
						final B2BUnitModel b2BUnitModel = b2bCustomer.getDefaultB2BUnit();
						final B2BUnitData b2BUnitData = getB2bUnitConverter().convert(b2BUnitModel);
						getSessionService().setAttribute(SESSION_SHIPTO, b2BUnitData);
					}
				}
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();


			if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true))
			{
				try
				{

					cspResponseData = siteOnePriceWebService.getPrice(products, productUomCode, storeId, nearbyStoresList, b2bCustomer,
							currencyIso, Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
							siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
							Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), branchRetailID);
				}
				catch (final Exception e)
				{
					// If any exception , we will display Call for pricing message to the customer.
					LOG.error("Unable to populate CSP for the customer : " + b2bCustomer.getEmail());
				}
			}
		}
		return cspResponseData;
	}

	@Override
	public Map<String, SiteOneWsPriceResponseData> getCSPResponseForUoms(final List<String> productcodeList,
			final Map<String, String> productCodeWithStore, final Map<ProductModel, String> productsUoms,
			final String branchRetailId)
	{
		SiteOneWsPriceResponseData cspResponseData = null;
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		final Map<String, SiteOneWsPriceResponseData> cspResponseForStore = new HashMap<>();
		String storeId = null;
		final Map<String, Map<ProductModel, Integer>> productsWithStore = new HashMap<String, Map<ProductModel, Integer>>();
		//SE-7712, search related to 'fertilizer' brings a SKU# 571612, which is missing socode_string attribute in solr.
		//when we make a db call to fetch product details below, it gives an error for null SKU ID.
		//Need to filter items with null socode_string(skuNumber) attribute.

		final boolean productCodeNull = productcodeList.removeIf(product -> product == null || StringUtils.isEmpty(product));
		if (productCodeNull)
		{
			LOG.error("Null product code encountered");
		}
		//end SE-7712

		productModels = siteOneProductService.getAllProductsByCodes(productcodeList);

		if (CollectionUtils.isNotEmpty(productModels))
		{
			Map<ProductModel, Integer> products = new HashMap<>();
			for (final ProductModel prod : productModels)
			{
				if (null == prod.getVariantType())
				{
					storeId = productCodeWithStore.get(prod.getCode());
					if (productsWithStore.containsKey(storeId))
					{
						products = productsWithStore.get(storeId);
						products.put(prod, Integer.valueOf(1));
						productsWithStore.put(storeId, products);
					}
					else
					{
						final Map<ProductModel, Integer> newproduct = new HashMap<>();
						newproduct.put(prod, Integer.valueOf(1));
						productsWithStore.put(storeId, newproduct);
					}
				}
			}

			//String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				//storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			B2BCustomerModel b2bCustomer = null;
			if (StringUtils.isEmpty(branchRetailId))
			{
				b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
			}
			if (null != b2bCustomer && (getSessionService().getAttribute("softLogin")) != null)
			{

				final boolean isSoftLogin = ((boolean) getSessionService().getAttribute("softLogin"));

				if (isSoftLogin)
				{
					final B2BUnitModel b2BUnitModel = b2bCustomer.getDefaultB2BUnit();
					final B2BUnitData b2BUnitData = getB2bUnitConverter().convert(b2BUnitModel);
					getSessionService().setAttribute(SESSION_SHIPTO, b2BUnitData);
				}
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();


			if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true)
					|| StringUtils.isNotEmpty(branchRetailId))
			{
				try
				{
					for (final Entry<String, Map<ProductModel, Integer>> product : productsWithStore.entrySet())
					{
						if (!product.getValue().isEmpty())
						{
							cspResponseData = siteOnePriceWebService.getPrice(product.getValue(), productsUoms, product.getKey(),
									nearbyStoresList, b2bCustomer, currencyIso,
									Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
									siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
									Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)),
									branchRetailId);
							cspResponseForStore.put(product.getKey(), cspResponseData);
						}
					}
				}
				catch (final Exception e)
				{
					// If any exception , we will display Call for pricing message to the customer.
					LOG.error("Unable to populate CSP for the customer : " + b2bCustomer.getEmail());
				}
			}
		}
		return cspResponseForStore;
	}

	@Override
	public Map<String, SiteOneWsPriceResponseData> getCSPResponse(final List<String> productcodeList,
			final Map<String, String> productCodeWithStore, final Map<String, Integer> productCodeWithQunatity, final Map<String, String> productCodeWithUom,final String branchRetailID)
	{
		SiteOneWsPriceResponseData cspResponseData = null;
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		final Map<String, SiteOneWsPriceResponseData> cspResponseForStore = new HashMap<>();
		String storeId = null;
		final Map<String, Map<ProductModel, Integer>> productsWithStore = new HashMap<String, Map<ProductModel, Integer>>();
		final Map<ProductModel, String> productsWithUom = new HashMap<ProductModel, String>();
 		//SE-7712, search related to 'fertilizer' brings a SKU# 571612, which is missing socode_string attribute in solr.
		//when we make a db call to fetch product details below, it gives an error for null SKU ID.
		//Need to filter items with null socode_string(skuNumber) attribute.

		final boolean productCodeNull = productcodeList.removeIf(product -> product == null || StringUtils.isEmpty(product));
		if (productCodeNull)
		{
			LOG.error("Null product code encountered");
		}
		//end SE-7712

		productModels = siteOneProductService.getAllProductsByCodes(productcodeList);

		if (CollectionUtils.isNotEmpty(productModels))
		{
			Map<ProductModel, Integer> products = new HashMap<>();
			for (final ProductModel prod : productModels)
			{
				if (null == prod.getVariantType())
				{
					storeId = productCodeWithStore.get(prod.getCode());
					if (productsWithStore.containsKey(storeId))
					{
						products = productsWithStore.get(storeId);
						products.put(prod, productCodeWithQunatity.get(prod.getCode()).compareTo(Integer.valueOf(1))>0?productCodeWithQunatity.get(prod.getCode()):Integer.valueOf(1));
						productsWithStore.put(storeId, products);
					}
					else
					{
						final Map<ProductModel, Integer> newproduct = new HashMap<>();
						newproduct.put(prod, productCodeWithQunatity.get(prod.getCode()).compareTo(Integer.valueOf(1))>0?productCodeWithQunatity.get(prod.getCode()):Integer.valueOf(1));
						productsWithStore.put(storeId, newproduct);
					}
					InventoryUPCModel inventoryUPC = prod.getUpcData().stream().filter(upc->upc.getInventoryUPCID().equalsIgnoreCase(productCodeWithUom.get(prod.getCode()))).findFirst().orElse(null);
               if(inventoryUPC!=null) 
               {
               	productsWithUom.put(prod,inventoryUPC.getCode());
               }
				}
			}

			//String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				//storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			B2BCustomerModel b2bCustomer=null;
			if(StringUtils.isEmpty(branchRetailID))
			{
			b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
			

			if ((getSessionService().getAttribute("softLogin")) != null)
			{

				final boolean isSoftLogin = ((boolean) getSessionService().getAttribute("softLogin"));

				if (isSoftLogin)
				{
					final B2BUnitModel b2BUnitModel = b2bCustomer.getDefaultB2BUnit();
					final B2BUnitData b2BUnitData = getB2bUnitConverter().convert(b2BUnitModel);
					getSessionService().setAttribute(SESSION_SHIPTO, b2BUnitData);
				}
			}
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();


			if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true) || StringUtils.isNotEmpty(branchRetailID))
			{
				try
				{
					for (final Entry<String, Map<ProductModel, Integer>> product : productsWithStore.entrySet())
					{
						if (!product.getValue().isEmpty())
						{
							cspResponseData = siteOnePriceWebService.getPrice(product.getValue(), productsWithUom, product.getKey(),
									nearbyStoresList, b2bCustomer, currencyIso,
									Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
									siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
									Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), branchRetailID);
							cspResponseForStore.put(product.getKey(), cspResponseData);
						}
					}
				}
				catch (final Exception e)
				{
					// If any exception , we will display Call for pricing message to the customer.
					LOG.error("Unable to populate CSP for the customer : ");
				}
			}
		}
		return cspResponseForStore;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.product.SiteOneProductFacade#getVariantCSPForCustomer(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public SiteOneCspResponse getVariantCSPForCustomer(final String productCode)
	{

		final SiteOneCspResponse cspResponse = new SiteOneCspResponse();
		boolean isHideCSPEnabled = false;
		if (Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_CSP_ENABLE_KEY, true))
		{
			final ProductModel productModel = getProductService().getProductForCode(productCode);
			final Map<ProductModel, Integer> products = new HashMap<>();

			for (final VariantProductModel variant : productModel.getVariants())
			{
				if (variant.getHideCSP().booleanValue())
				{
					isHideCSPEnabled = true;
				}
			}
			for (final VariantProductModel variant : productModel.getVariants())
			{
				products.put(variant, Integer.valueOf(1));
			}
			String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

			try
			{
				final SiteOneWsPriceResponseData siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products, null, storeId,
						nearbyStoresList, b2bCustomer, currencyIso,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
						siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), null);

				if (null != siteOneWsPriceResponseData)
				{
					if (!isHideCSPEnabled)
					{
						setVariantProductPriceRange(cspResponse, siteOneWsPriceResponseData.getPrices(), productModel);
					}
					else
					{
						cspResponse.setIsSuccess(false);
						cspResponse.setErrorMessageToDisplay("true");
					}

				}
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				cspResponse.setIsSuccess(false);
				cspResponse.setErrorMessageToDisplay(Config.getString("ue.price.unavailable", null));
			}
		}

		return cspResponse;


	}

	/**
	 * @param cspResponse
	 * @param prices
	 * @param productModel
	 */
	private void setVariantProductPriceRange(final SiteOneCspResponse cspResponse, final List<Prices> prices,
			final ProductModel productModel)
	{
		if (CollectionUtils.isNotEmpty(prices) && CollectionUtils.isNotEmpty(productModel.getVariants()))
		{
			//final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
			prices.removeIf(priceInfo -> (Double.compare(Double.parseDouble(priceInfo.getPrice()), 0.0d)) == 0);
			// sort the list
			Collections.sort(prices, CSPPriceRangeComparator.INSTANCE);
			final PriceRangeData priceRange = new PriceRangeData();
			final BigDecimal minPrice = new BigDecimal(prices.get(0).getPrice());
			final BigDecimal maxPrice = new BigDecimal(prices.get(prices.size() - 1).getPrice());
			priceRange.setMinPrice(getPriceDataFactory().create(PriceDataType.FROM, minPrice,
					getCommonI18NService().getCurrentCurrency().getIsocode()));
			priceRange.setMaxPrice(getPriceDataFactory().create(PriceDataType.FROM, maxPrice,
					getCommonI18NService().getCurrentCurrency().getIsocode()));
			cspResponse.setPriceRange(priceRange);
			cspResponse.setIsSuccess(true);
			//productData.setPriceRange(priceRange);
		}
		else
		{
			cspResponse.setIsSuccess(false);
		}

	}

	@Override
	public void populatVariantProductsForDisplay(final ProductData productData, final Model model)
	{
		final ProductModel productModel = getProductService().getProductForCode(productData.getCode());
		model.addAttribute("productType", productModel.getProductType());
		model.addAttribute("isShippable", productModel.getIsShippable());
		final Map<String, Map<String, String>> variantValueCategoryDataMap = new LinkedHashMap<String, Map<String, String>>();
		final List<String> parentVariantCategoryNameList = new ArrayList<String>();
		int count = 0;
		for (final VariantMatrixElementData parentMatrix : productData.getVariantMatrix())
		{
			LOG.info("parentMatrix.getParentVariantCategory().getName()" + parentMatrix.getParentVariantCategory().getName());
			LOG.info("parentMatrix.getVariantValueCategory().getName()" + parentMatrix.getVariantValueCategory().getName());
			LOG.info("parentMatrix.getVariantOption().getCode()" + parentMatrix.getVariantOption().getCode());
			if (count == 0)
			{
				parentVariantCategoryNameList.add(parentMatrix.getParentVariantCategory().getName());
				count++;
			}
			final Map<String, String> tempSet1 = new LinkedHashMap<String, String>();
			tempSet1.put(parentMatrix.getParentVariantCategory().getName(), parentMatrix.getVariantValueCategory().getName());
			variantValueCategoryDataMap.put(parentMatrix.getVariantOption().getCode(), tempSet1);
			for (final VariantMatrixElementData childMatrix : parentMatrix.getElements())
			{
				if (count == 1)
				{
					parentVariantCategoryNameList.add(childMatrix.getParentVariantCategory().getName());
					count++;
				}
				LOG.info("childMatrix.getParentVariantCategory().getName()" + childMatrix.getParentVariantCategory().getName());
				LOG.info("childMatrix.getVariantValueCategory().getName()" + childMatrix.getVariantValueCategory().getName());
				LOG.info("childMatrix.getVariantOption().getCode()" + childMatrix.getVariantOption().getCode());
				if (parentMatrix.getVariantOption().getCode().equals(childMatrix.getVariantOption().getCode()))
				{
					final Map<String, String> tempSet2 = new LinkedHashMap<String, String>();
					tempSet2.putAll(variantValueCategoryDataMap.get(parentMatrix.getVariantOption().getCode()));
					tempSet2.put(childMatrix.getParentVariantCategory().getName(), childMatrix.getVariantValueCategory().getName());
					variantValueCategoryDataMap.put(parentMatrix.getVariantOption().getCode(), tempSet2);

					for (final VariantMatrixElementData grandChildMatrix : childMatrix.getElements())
					{
						if (count == 2)
						{
							parentVariantCategoryNameList.add(grandChildMatrix.getParentVariantCategory().getName());
							count++;
						}
						LOG.info("grandChildMatrix.getParentVariantCategory().getName()"
								+ grandChildMatrix.getParentVariantCategory().getName());
						LOG.info("grandChildMatrix.getVariantValueCategory().getName()"
								+ grandChildMatrix.getVariantValueCategory().getName());
						LOG.info("grandChildMatrix.getVariantOption().getCode()" + grandChildMatrix.getVariantOption().getCode());
						if (childMatrix.getVariantOption().getCode().equals(grandChildMatrix.getVariantOption().getCode()))
						{

							final Map<String, String> tempSet3 = new LinkedHashMap<String, String>();
							tempSet3.putAll(variantValueCategoryDataMap.get(childMatrix.getVariantOption().getCode()));
							tempSet3.put(grandChildMatrix.getParentVariantCategory().getName(),
									grandChildMatrix.getVariantValueCategory().getName());
							variantValueCategoryDataMap.put(childMatrix.getVariantOption().getCode(), tempSet3);
						}
						else
						{
							final Map<String, String> tempSet3 = new LinkedHashMap<String, String>();
							tempSet3.put(parentMatrix.getParentVariantCategory().getName(),
									parentMatrix.getVariantValueCategory().getName());
							tempSet3.put(childMatrix.getParentVariantCategory().getName(),
									childMatrix.getVariantValueCategory().getName());
							tempSet3.put(grandChildMatrix.getParentVariantCategory().getName(),
									grandChildMatrix.getVariantValueCategory().getName());
							variantValueCategoryDataMap.put(grandChildMatrix.getVariantOption().getCode(), tempSet3);

						}
					}
				}
				else
				{
					final Map<String, String> tempSet4 = new LinkedHashMap<String, String>();
					tempSet4.put(parentMatrix.getParentVariantCategory().getName(), parentMatrix.getVariantValueCategory().getName());
					tempSet4.put(childMatrix.getParentVariantCategory().getName(), childMatrix.getVariantValueCategory().getName());
					variantValueCategoryDataMap.put(childMatrix.getVariantOption().getCode(), tempSet4);
					for (final VariantMatrixElementData grandChildMatrix : childMatrix.getElements())
					{
						if (count == 2)
						{
							parentVariantCategoryNameList.add(grandChildMatrix.getParentVariantCategory().getName());
							count++;
						}
						LOG.info("grandChildMatrix.getParentVariantCategory().getName()"
								+ grandChildMatrix.getParentVariantCategory().getName());
						LOG.info("grandChildMatrix.getVariantValueCategory().getName()"
								+ grandChildMatrix.getVariantValueCategory().getName());
						LOG.info("grandChildMatrix.getVariantOption().getCode()" + grandChildMatrix.getVariantOption().getCode());
						if (childMatrix.getVariantOption().getCode().equals(grandChildMatrix.getVariantOption().getCode()))
						{
							final Map<String, String> tempSet5 = new LinkedHashMap<String, String>();
							tempSet5.putAll(variantValueCategoryDataMap.get(childMatrix.getVariantOption().getCode()));
							tempSet5.put(grandChildMatrix.getParentVariantCategory().getName(),
									grandChildMatrix.getVariantValueCategory().getName());
							variantValueCategoryDataMap.put(childMatrix.getVariantOption().getCode(), tempSet5);

						}
						else
						{
							final Map<String, String> tempSet5 = new LinkedHashMap<String, String>();
							tempSet5.put(parentMatrix.getParentVariantCategory().getName(),
									parentMatrix.getVariantValueCategory().getName());
							tempSet5.put(childMatrix.getParentVariantCategory().getName(),
									childMatrix.getVariantValueCategory().getName());
							tempSet5.put(grandChildMatrix.getParentVariantCategory().getName(),
									grandChildMatrix.getVariantValueCategory().getName());
							variantValueCategoryDataMap.put(grandChildMatrix.getVariantOption().getCode(), tempSet5);
						}
					}
				}
			}
		}

		model.addAttribute("parentVariantCategoryNameList", parentVariantCategoryNameList);
		getSessionService().setAttribute("parentVariantCategoryNameList", parentVariantCategoryNameList);
		
		List<ProductData> productList = new ArrayList<>();
		List<ProductData> productSubList = new ArrayList<>();
		List<ProductData> inStockList = new ArrayList<>();
		final List<Double> allCspPrices = new ArrayList<Double>();
		final List<Double> allRetailPrices = new ArrayList<Double>();
		for (final VariantOptionData data : productData.getVariantOptions())
		{
			final ProductModel productModelForVariant = getProductService().getProductForCode(data.getCode());
			final ProductData productDataForVariant = new ProductData();
			getProductPrimaryImagePopulator().populate(productModelForVariant, productDataForVariant);
			getProductGalleryImagesPopulator().populate(productModelForVariant, productDataForVariant);
			LOG.info("Stock in variantoptiondata : " + data.getStockLevel());
			productDataForVariant.setStock(data.getStock());
			LOG.info("Stock in productdata : " + productDataForVariant.getStock().getStockLevel());
			productDataForVariant.setIsRegulateditem(data.getIsRegulatedItem());
			productDataForVariant.setInventoryCheck(data.getInventoryCheck());
			Double cspPrice = 0.0d;
			Double retailPrice = 0.0d;
			if (!getUserService().isAnonymousUser(getUserService().getCurrentUser()))
			{
				if (data.getCustomerPrice() != null)
				{
					final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY,
							new BigDecimal(data.getCustomerPrice().getPrice()), storeSessionFacade.getCurrentCurrency().getIsocode());
					productDataForVariant.setCustomerPrice(priceData);
					cspPrice = priceData.getValue().doubleValue();
				}
				else
				{
					data.setHideCSP(true);
				}

			}

			if (null != storeSessionFacade.getSessionStore()
					&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
							storeSessionFacade.getSessionStore().getStoreId())
					&& StringUtils.isNotEmpty(storeSessionFacade.getSessionStore().getCustomerRetailId()))
			{
				if (data.getPriceData() != null)
				{
					final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, data.getPriceData().getValue(),
							storeSessionFacade.getCurrentCurrency().getIsocode());
					productDataForVariant.setPrice(priceData);
					data.setPriceData(priceData);
					retailPrice = data.getPriceData().getValue().doubleValue();
				}
			}
			else
			{
				final PriceDataType priceType = PriceDataType.BUY;
				final PriceInformation info = getCommercePriceService().getWebPriceForProduct(productModelForVariant);
				if (info != null && info.getPrice() != null && info.getPrice().getValue() > 0.0)
				{
					final PriceData listPriceData = getPriceDataFactory().create(priceType,
							BigDecimal.valueOf(info.getPriceValue().getValue()), info.getPriceValue().getCurrencyIso());
					productDataForVariant.setPrice(listPriceData);
					data.setPriceData(listPriceData);
					retailPrice = listPriceData.getValue().doubleValue();
				}
			}

			data.setParentVariantCategoryList(parentVariantCategoryNameList);
			for (final Map.Entry<String, Map<String, String>> entry : variantValueCategoryDataMap.entrySet())
			{
				if (entry.getKey().equalsIgnoreCase(data.getCode()))
				{
					final Map<String, String> tempMap = new LinkedHashMap<String, String>();
					for (final Map.Entry<String, String> subEntry : entry.getValue().entrySet())
					{
						tempMap.put(subEntry.getKey(), subEntry.getValue());
					}
					data.setVariantValueCategory(tempMap);
				}
			}
			
			productDataForVariant.setStores(data.getStores());
			productDataForVariant.setRegulatoryStates(data.getRegulatoryStates());
			productDataForVariant.setCode(productModelForVariant.getCode());
			
			getProductSellableAndMessagePopulator().populate(productModelForVariant, productDataForVariant);
			if((data.getHideList() == null || !data.getHideList()) && !productDataForVariant.getInventoryFlag()) {
				allCspPrices.add(cspPrice);
				allRetailPrices.add(retailPrice);
			}
			if(BooleanUtils.isTrue(productDataForVariant.getOutOfStockImage()))
			{
				productSubList.add(productDataForVariant);			
			}
			else
			{
				inStockList.add(productDataForVariant);
			}
			
			if(CollectionUtils.isNotEmpty(productSubList) && productSubList.size()==20) {
				updateSalesInfoBackorderForProduct(productSubList);
				productList.addAll(productSubList);
				productSubList = new ArrayList<>();
			}
		}
		allCspPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);
		allRetailPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);
		if(!allCspPrices.isEmpty()) {
			getPriceRangeForVariant(productData, allCspPrices);
		} else if(!allRetailPrices.isEmpty()) {
			getPriceRangeForVariant(productData, allRetailPrices);
		}
		if(CollectionUtils.isNotEmpty(productSubList) && productSubList.size()<20) {
			updateSalesInfoBackorderForProduct(productSubList);
			productList.addAll(productSubList);
			productSubList = new ArrayList<>();
		}
		if(CollectionUtils.isNotEmpty(productList))
		{
			populateVariantOptionData(productData, model, productList);
		}
		if(CollectionUtils.isNotEmpty(inStockList))
		{
			populateVariantOptionData(productData, model, inStockList);
		}
		
	}
	
	protected void populateVariantOptionData(final ProductData productData, final Model model, final List<ProductData> productList) {
		
		final ProductModel productModel = getProductService().getProductForCode(productData.getCode());

		boolean isDeliveryEnabled = false;
		boolean isPickupEnabled = false;

		for (final VariantOptionData data : productData.getVariantOptions())
		{
			final ProductModel productModelForVariant = getProductService().getProductForCode(data.getCode());
			for (final ProductData productDataForVariant : productList)
			{
				if(data.getCode().equalsIgnoreCase(productDataForVariant.getCode())) 
				{
					data.setStockAvailabilityMessage(productDataForVariant.getStoreStockAvailabilityMsg());
					data.setStockAvailExtendedMessage(productDataForVariant.getStockAvailExtendedMessage());
					data.setIsAddToCartDisabled(!productDataForVariant.getIsSellable());
					if(BooleanUtils.isTrue(productData.getIsNurseryProduct()) && productDataForVariant.getStoreStockAvailabilityMsg() != null 
							&& (productDataForVariant.getStoreStockAvailabilityMsg().contains("Backorder")
									|| productDataForVariant.getStoreStockAvailabilityMsg().contains("pendiente")))
					{
						data.setIsAddToCartDisabled(Boolean.FALSE);
						data.setInventoryCheck(Boolean.FALSE);
						data.setNotInStockImage(true);
					}
					data.setInStockImage(productDataForVariant.getInStockImage());
					data.setOutOfStockImage(productDataForVariant.getOutOfStockImage());
					data.setInventoryFlag(productDataForVariant.getInventoryFlag());
					data.setIsStockInNearbyBranch(productDataForVariant.getIsStockInNearbyBranch());
					data.setIsEligibleForDelivery(productDataForVariant.getIsEligibleForDelivery());
					data.setBulkUOMCode(productDataForVariant.getBulkUOMCode());
					data.setBulkUOMPrice(productDataForVariant.getBulkUOMPrice());
					data.setBulkUOMCustomerPrice(productDataForVariant.getBulkUOMCustomerPrice());
					data.setIsForceInStock(productDataForVariant.getIsForceInStock());
					
					getProductPromotionsPopulator().populate(productModelForVariant, productDataForVariant);
					data.setCouponCode(productDataForVariant.getCouponCode());
					data.setPromoDetails(productDataForVariant.getPromoDetails());
					data.setPotentialPromotions(productDataForVariant.getPotentialPromotions());
					data.setIsPromoDescriptionEnabled(productDataForVariant.getIsPromoDescriptionEnabled());			
				   
					getProductBasicPopulator().populate(productModelForVariant, productDataForVariant);			
					data.setHideUom(productDataForVariant.getHideUom());			
					data.setSellableUoms(productDataForVariant.getSellableUoms());
					data.setImages(productDataForVariant.getImages());
					data.setOrderQuantityInterval(productDataForVariant.getOrderQuantityInterval());
					
					final InventoryUPCModel parentInventory = productModelForVariant.getUpcData().stream()
							.filter(upc -> upc.getBaseUPC().booleanValue() && !upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
					if (null != parentInventory)
					{
						data.setSingleUomMeasure(parentInventory.getInventoryUPCDesc());
					}
					else
					{
						final InventoryUPCModel childInventory = productModelForVariant.getUpcData().stream()
								.filter(upc -> !upc.getBaseUPC().booleanValue() && !upc.getHideUPCOnline().booleanValue()).findFirst()
								.orElse(null);
						if (null != childInventory)
						{
							data.setSingleUomMeasure(childInventory.getInventoryUPCDesc());
						}
					}			
					
					if (null != productDataForVariant.getStockAvailableOnlyHubStore()
							&& productDataForVariant.getStockAvailableOnlyHubStore())
					{
						data.setStockAvailableOnlyHubStore(productDataForVariant.getStockAvailableOnlyHubStore());
						if (null != data.getStockAvailableOnlyHubStore() && data.getStockAvailableOnlyHubStore())
						{
							if (CollectionUtils.isNotEmpty(getSiteOneStockLevelService().getStockLevelsForHubStoresForProduct(data.getCode(),
									storeSessionFacade.getSessionStore().getHubStores().get(0))))
							{
								for (final List<Object> stockLevelData : getSiteOneStockLevelService().getStockLevelsForHubStoresForProduct(
										data.getCode(), storeSessionFacade.getSessionStore().getHubStores().get(0)))
								{
									if (null != stockLevelData.get(2))
									{
										data.setHubStoreStockLevel(Long.valueOf(stockLevelData.get(2).toString()));
									}
								}
							}
						}
					}
					if (!(productModel.getProductType().equalsIgnoreCase("Nursery")))
					{
						data.setNotInStockImage(productDataForVariant.getNotInStockImage());
					}
					if (!isPickupEnabled)
					{
						if ((null != data.getInStockImage() && data.getInStockImage())
								|| (null != data.getNotInStockImage() && data.getNotInStockImage()))
						{
							isPickupEnabled = true;
							if (null != data.getInStockImage() && data.getInStockImage())
							{
								if (null != data.getStock())
								{
									if (null != data.getStock().getStoreName())
									{
										model.addAttribute("storeName", data.getStock().getStoreName());
									}
									if (null != data.getStock().getStoreId())
									{
										model.addAttribute("storeId", data.getStock().getStoreId());
									}
								}
							}
							if (null != data.getNotInStockImage() && data.getNotInStockImage())
							{
								if (null != storeSessionFacade.getSessionStore())
								{
									if (null != storeSessionFacade.getSessionStore().getName())
									{
										model.addAttribute("storeName", storeSessionFacade.getSessionStore().getName());
									}
									if (null != storeSessionFacade.getSessionStore().getStoreId())
									{
										model.addAttribute("storeId", storeSessionFacade.getSessionStore().getStoreId());
									}
								}
							}
						}
					}
					if (!isDeliveryEnabled)
					{
						if (null != data.getIsEligibleForDelivery() && data.getIsEligibleForDelivery())
						{
							isDeliveryEnabled = true;
						}
					}
					if (null == data.getVariantValueCategory())
					{
						final Map<String, String> tempMap1 = new LinkedHashMap<String, String>();
						for (final CategoryModel category : productModelForVariant.getSupercategories())
						{
							if (!(category.getCode().contains("PH") || category.getCode().contains("SH")))
							{

								tempMap1.put(category.getSupercategories().get(0).getName(), category.getName());
							}

						}
						data.setVariantValueCategory(tempMap1);
					}
				
				}
			}
			
		}
		model.addAttribute("isDeliveryEnabled", isDeliveryEnabled);
		model.addAttribute("isPickupEnabled", isPickupEnabled);
	}
	
	public void setRetailPriceForVariants(final ProductData productData,final String branchRetailId)
	{
		SiteOneWsPriceResponseData siteOneRetailResponse = null;
		final List<String> productCodeList = new ArrayList<String>();
		final Map<String, String> productCodeWithStore = new HashMap<>();
		final Map<ProductModel, String> productUoms = new HashMap<>();
		Map<String, SiteOneWsPriceResponseData> retailResponse = new HashMap<>();
		String storeId = null;
		if (null != productData.getVariantOptions() && CollectionUtils.isNotEmpty(productData.getVariantOptions()))
		{
			productData.getVariantOptions().forEach(variantOptionData -> {
				productCodeList.add(variantOptionData.getCode());
				ProductModel prod = getProductService().getProductForCode(variantOptionData.getCode());
				InventoryUPCModel inventoryUPC = prod.getUpcData().stream().filter(upc->!upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
				if(inventoryUPC != null) {
					productUoms.put(prod, inventoryUPC.getCode());
				}
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && variantOptionData.getStock() != null)
				{
					if (StringUtils.isNotEmpty(variantOptionData.getStock().getFullfillmentStoreId()))
					{
						productCodeWithStore.put(variantOptionData.getCode(), variantOptionData.getStock().getFullfillmentStoreId());
					}
					else if (variantOptionData.getStock().getNearestBackorderableStore() != null)
					{
						if (StringUtils.isNotEmpty(variantOptionData.getStock().getNearestBackorderableStore().getStoreId()))
						{
							productCodeWithStore.put(variantOptionData.getCode(),
									variantOptionData.getStock().getNearestBackorderableStore().getStoreId());
						}
					}
					else
					{
						productCodeWithStore.put(variantOptionData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
					}
				}
				else
				{
					productCodeWithStore.put(variantOptionData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
				}
			});
			if (CollectionUtils.isNotEmpty(productCodeList) && !productCodeWithStore.isEmpty())
			{
				retailResponse = getCSPResponseForUoms(productCodeList, productCodeWithStore, productUoms, branchRetailId);
				if (!retailResponse.isEmpty())
				{
					for (final VariantOptionData data : productData.getVariantOptions())
					{
						storeId = productCodeWithStore.get(data.getCode());
						siteOneRetailResponse = retailResponse.get(storeId);
						if (null != siteOneRetailResponse && null != siteOneRetailResponse.getPrices())
						{
							siteOneRetailResponse.getPrices().forEach(csp -> {
								if (csp.getSkuId().equalsIgnoreCase(data.getCode()))
								{
									final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
									final PriceData priceData = priceDataFactory.create(PriceDataType.BUY, new BigDecimal(csp.getPrice()), currencyIso);
									data.setPriceData(priceData);
								}
							});
						}
					}
				}
			}
		}	
	}

	@Override
	public void setCustomerPriceForVariants(final ProductData productData,final String branchRetailId)
	{
		SiteOneWsPriceResponseData siteOneCspResponse = null;
		final List<String> productCodeList = new ArrayList<String>();
		final Map<String, String> productCodeWithStore = new HashMap<>();
		final Map<ProductModel, String> productUoms = new HashMap<>();
		Map<String, SiteOneWsPriceResponseData> cspResponse = new HashMap<>();
		String storeId = null;
		if (null != productData.getVariantOptions() && CollectionUtils.isNotEmpty(productData.getVariantOptions()))
		{
			productData.getVariantOptions().forEach(variantOptionData -> {
				productCodeList.add(variantOptionData.getCode());
				ProductModel prod = getProductService().getProductForCode(variantOptionData.getCode());
				InventoryUPCModel inventoryUPC = prod.getUpcData().stream().filter(upc->!upc.getHideUPCOnline().booleanValue()).findFirst().orElse(null);
				if(inventoryUPC != null) {
					productUoms.put(prod, inventoryUPC.getCode());
				}
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && variantOptionData.getStock() != null)
				{
					if (StringUtils.isNotEmpty(variantOptionData.getStock().getFullfillmentStoreId()))
					{
						productCodeWithStore.put(variantOptionData.getCode(), variantOptionData.getStock().getFullfillmentStoreId());
					}
					else if (variantOptionData.getStock().getNearestBackorderableStore() != null)
					{
						if (StringUtils.isNotEmpty(variantOptionData.getStock().getNearestBackorderableStore().getStoreId()))
						{
							productCodeWithStore.put(variantOptionData.getCode(),
									variantOptionData.getStock().getNearestBackorderableStore().getStoreId());
						}
					}
					else
					{
						productCodeWithStore.put(variantOptionData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
					}
				}
				else
				{
					productCodeWithStore.put(variantOptionData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
				}
			});
			if (CollectionUtils.isNotEmpty(productCodeList) && !productCodeWithStore.isEmpty())
			{
				cspResponse = getCSPResponseForUoms(productCodeList, productCodeWithStore, productUoms,branchRetailId);
				if (!cspResponse.isEmpty())
				{
					for (final VariantOptionData data : productData.getVariantOptions())
					{
						storeId = productCodeWithStore.get(data.getCode());
						siteOneCspResponse = cspResponse.get(storeId);
						if (null != siteOneCspResponse && null != siteOneCspResponse.getPrices())
						{
							siteOneCspResponse.getPrices().forEach(csp -> {
								if (csp.getSkuId().equalsIgnoreCase(data.getCode()))
								{
									final CustomerPriceData customerPriceData = new CustomerPriceData();
									customerPriceData.setCode(data.getCode());
									customerPriceData.setPrice(csp.getPrice());
									data.setCustomerPrice(customerPriceData);
								}
							});
						}
					}
				}
			}
		}
	}

	@Override
	public void updateParcelShippingForProducts(final List<ProductData> productcodeList, final String homeStoreId)
	{
		siteOneProductService.updateParcelShippingForProducts(productcodeList, homeStoreId);
	}

	@Override
	public void updateParcelShippingForProduct(final ProductData productData, final String homeStoreId)
	{
		siteOneProductService.updateParcelShippingForProduct(productData, homeStoreId);
	}

	@Override
	public Map<String, List<ProductData>> parcelShippingMessageForProducts(final List<ProductData> productcodeList,
			final String homeStoreId)
	{
		return siteOneProductService.parcelShippingMessageForProducts(productcodeList, homeStoreId);
	}

	@Override
	public ProductData getScanProductDetails(final String productCode, final String supplyChainNodeId,
			final Map<String, String> variantProduct, final String storeId)
	{
		final SiteOneWsScanProductRequestData scanProductRequest = new SiteOneWsScanProductRequestData();
		scanProductRequest.setDivisionID(Integer.valueOf(1));
		scanProductRequest.setSearchText(productCode);
		scanProductRequest.setIncludePricing("false");
		scanProductRequest.setBranchNumber(storeId);
		scanProductRequest.setSupplyChainNodeID(Integer.valueOf(supplyChainNodeId));
		final SiteOneScanProductResponse scanProductResponse = siteOneScanProductWebService.getScanProduct(scanProductRequest,
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
		return getScanProduct(scanProductResponse, variantProduct);
	}

	@Override
	public SiteOneCspResponse getStoreProductPriceForCustomer(final String productCode, final Integer quantity,
			final String inventoryUOMId)
	{
		final SiteOneCspResponse cspResponse = new SiteOneCspResponse();
		if (!userFacade.isAnonymousUser())
		{
			final B2BCustomerModel b2bCustomer = (B2BCustomerModel) getUserService().getCurrentUser();
			final ProductModel productModel = new ProductModel();
			productModel.setCode(productCode);
			final Map<ProductModel, Integer> products = new HashMap<>();
			products.put(productModel, quantity);

			String storeId = null;
			List<PointOfServiceData> nearbyStoresList = null;
			if (null != storeSessionFacade.getSessionStore())
			{
				storeId = storeSessionFacade.getSessionStore().getStoreId();
				nearbyStoresList = storeSessionFacade.getNearbyStoresFromSession();
			}
			final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();

			try
			{
				final SiteOneWsPriceResponseData siteOneWsPriceResponseData = siteOnePriceWebService.getPrice(products, null, storeId,
						nearbyStoresList, b2bCustomer, currencyIso,
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)),
						siteOneFeatureSwitchCacheService.getListOfBranchesUnderFeatureSwitch(D365_CSP_BRANCHES),
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(D365_NEW_URL)), null);

				if (null != siteOneWsPriceResponseData)
				{
					final List<Prices> allPrices = siteOneWsPriceResponseData.getPrices();
					if (CollectionUtils.isNotEmpty(allPrices) && CollectionUtils.isEmpty(productModel.getVariants()))
					{
						final String price = allPrices.get(0).getPrice();

						if (null != price && productCode.equals(allPrices.get(0).getSkuId()))
						{
							final PriceData priceData = priceDataFactory.create(PriceDataType.BUY, new BigDecimal(price), currencyIso);
							cspResponse.setPrice(priceData);
							cspResponse.setIsSuccess(true);
						}
						else
						{
							cspResponse.setIsSuccess(false);
							cspResponse.setErrorMessageToDisplay("SKU id not matched");
						}
					}
					else
					{
						cspResponse.setIsSuccess(false);
					}
				}
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				cspResponse.setIsSuccess(false);
			}
		}

		return cspResponse;
	}

	@Override
	public void populateAvailablityForBuyAgainPageProducts(final List<ProductData> productDataList)
	{
		for (final ProductData productData : productDataList)
		{
			final ProductModel productModel = getProductService().getProductForCode(productData.getCode());
			getProductSellableAndMessagePopulator().populate(productModel, productData);
		}

	}


	public ProductData getScanProduct(final SiteOneScanProductResponse scanProductResponse,
			final Map<String, String> variantProduct)
	{
		String productCode = null;
		ProductData productData = new ProductData();
		final SingleProductResponse response = new SingleProductResponse();
		if (scanProductResponse != null && scanProductResponse.getScanResult() != null)
		{
			response.setSkuID(scanProductResponse.getScanResult().getSkuID());
			response.setId(scanProductResponse.getScanResult().getSkuID().toString());
			response.setItemNumber(scanProductResponse.getScanResult().getProductNumber());
			if(scanProductResponse.getPricing()!=null) {
			final SiteOneScanProductPriceList price = scanProductResponse.getPricing().getPriceList().get(0);
			response.setInventoryUOMID(scanProductResponse.getPricing().getInventoryUOMID());
			response.setPrice(price.getUnitPrice());
			}
			response.setProductShortDescription(scanProductResponse.getScanResult().getProductDescription());
			response.setProductType(scanProductResponse.getScanResult().getTaxonomy());
			//Fetch Product Model for scanned sku
			ProductModel productModel = getProductService()
					.getProductForCode(scanProductResponse.getScanResult().getSkuID().toString());
			// if the scanned sku is variant, get its base
			if (productModel instanceof VariantProductModel)
			{

				final ProductModel baseProduct = ((VariantProductModel) productModel).getBaseProduct();
				productModel = baseProduct;
			}
			//assign product code based on variant/base comparison
			if (Integer.parseInt(response.getId()) == Integer.parseInt(productModel.getCode()))
			{
				productCode = response.getId();// Base or simple
			}
			else
			{
				productCode = productModel.getCode();// Base product for variant
				variantProduct.put(VARIANT_PRODUCT, response.getId());// store variant sku
			}
		}
		if (productCode != null)
		{
			try
			{
				final List<ProductOption> extraOptions = Arrays.asList(ProductOption.DESCRIPTION, ProductOption.KEYWORDS,
						ProductOption.CATEGORIES);
				productData = productFacade.getProductForCodeAndOptions(productCode, extraOptions);
			}
			catch (final Exception e)
			{
				LOG.error(productCode + " is store product");
				productData.setIsProductOffline(true);
				final SiteOneCspResponse siteOneCspResponse = getStoreProductPriceForCustomer(productCode, Integer.valueOf(1),
						null);
				if (null != siteOneCspResponse && siteOneCspResponse.isIsSuccess())
				{
					productData.setCustomerPrice(siteOneCspResponse.getPrice());
				}
				final PriceData price = priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(response.getPrice()),
						CURRENCY_ISO);
				productData.setPrice(price);
				productData.setItemNumber(response.getItemNumber());
				productData.setProductLongDesc(response.getProductLongDescription() != null ? 
						(response.getProductLongDescription()).replaceAll("&nbsp;"," ") : response.getProductLongDescription());
				productData.setProductShortDesc(response.getProductShortDescription());
				productData.setCode(productCode);
			}
			return productData;
		}
		return null;
	}

	@Override
	public Boolean isRestrictedUsePesticide(final String productCode, final String storeId)
	{
		final PointOfServiceModel pos = storeFinderService.getStoreForId(storeId);
		return regulatoryStatesCronJobService.getRupBySkuAndState(productCode, pos.getAddress().getRegion());

	}

	@Override
	public void setCategoriesForProduct(final ProductData productData) {
		
		 ProductModel productModel = getProductService().getProductForCode(productData.getCode());
		
		if(productModel instanceof VariantProductModel) {
			
			final ProductModel baseProduct = ((VariantProductModel) productModel).getBaseProduct();
			productModel=baseProduct;
		}
		
		final Collection<CategoryModel> leafLevelCategories = productModel.getSupercategories();
		final CategoryModel category1 = getPrimaryCategory(leafLevelCategories);

		if (null != category1)
		{
			if (!isLevel1Category(category1.getSupercategories()))
			{
				final CategoryModel category2 = getPrimaryCategory(category1.getSupercategories());

				if (!isLevel1Category(category2.getSupercategories()))
				{
					final CategoryModel category3 = getPrimaryCategory(category2.getSupercategories());

					if (!isLevel1Category(category3.getSupercategories()))
					{
						final CategoryModel category4 = getPrimaryCategory(category3.getSupercategories());

						if (!isLevel1Category(category4.getSupercategories()))
						{	
							productData.setLevel2Category(category3.getName());
						}
						else
						{	
							productData.setLevel2Category(category3.getName());
						}
					}
					else
					{
						productData.setLevel2Category(category2.getName());
					}
				}
				else
				{	
					productData.setLevel2Category(category1.getName());
				}
			}
			else
			{
				productData.setLevel2Category(category1.getName());
			}
		}
		
	}


	@Override
	public void getCSPPriceListForProducts(final List<BuyItAgainData> buyItAgainDataList, final List<ProductData> productDataList)
	{
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
		SiteOneWsPriceResponseData siteOneCspResponse = null;
		final Map<String, String> productCodeWithStore = new HashMap<>();
		Map<String, SiteOneWsPriceResponseData> cspResponse = new HashMap<>();
		String storeId = null;
		final List<String> productCodeList = new ArrayList<String>();
		productDataList.forEach(listData -> {
			if (StringUtils.isNotEmpty(listData.getCode()))
			{
				productCodeList.add(listData.getCode());
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && listData.getStock() != null)
				{
					if (StringUtils.isNotEmpty(listData.getStock().getFullfillmentStoreId()))
					{
						productCodeWithStore.put(listData.getCode(), listData.getStock().getFullfillmentStoreId());
					}
					else
					{
						productCodeWithStore.put(listData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
					}
				}
				else
				{
					productCodeWithStore.put(listData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
				}
			}
		});
		if (CollectionUtils.isNotEmpty(productCodeList) && !productCodeWithStore.isEmpty())
		{
			cspResponse = getCSPResponseForUoms(productCodeList, productCodeWithStore, null,null);
			if (!cspResponse.isEmpty())
			{
				for (final ProductData productData : productDataList)
				{
					storeId = productCodeWithStore.get(productData.getCode());
					siteOneCspResponse = cspResponse.get(storeId);
					if (null != siteOneCspResponse && CollectionUtils.isNotEmpty(siteOneCspResponse.getPrices()))
					{
						siteOneCspResponse.getPrices().forEach(csp -> {
							if (csp.getSkuId().equalsIgnoreCase(productData.getCode()))
							{
								double uomMultiplier=1.0;
								BuyItAgainData buyItAgainData=getMatchingBuyItAgain(buyItAgainDataList,productData);
								if (!productData.getSellableUoms().isEmpty())
								{
									updatePriceIfUOMProduct(productData);
									if(buyItAgainData!=null){
										for (final InventoryUOMData inventoryUOMData1 : productData.getSellableUoms()){
											// get the matching upc ids
											if(inventoryUOMData1.getInventoryUOMID().equalsIgnoreCase(buyItAgainData.getProductUOMUPCID())){
												uomMultiplier= (double)inventoryUOMData1.getInventoryMultiplier();
												break;
											}
										}
									}
								}else{
									ProductModel productModel = getProductService().getProductForCode(
											getSessionService().getAttribute("currentCatalogVersion"), productData.getCode());
									if(buyItAgainData!=null) {
										for (final InventoryUPCModel inventoryUOM : productModel.getUpcData()) {
											// get the matching upc ids
											if (inventoryUOM.getInventoryUPCID().equalsIgnoreCase(buyItAgainData.getProductUOMUPCID())) {
												uomMultiplier = (double) inventoryUOM.getInventoryMultiplier();
												break;
											}
										}

									}
								}

								BigDecimal cspDisplayPrice=new BigDecimal(csp.getPrice());
								BigDecimal productDisplayPrice=BigDecimal.valueOf(0.0);
								if (productData.getPrice()!=null){
									productDisplayPrice=productData.getPrice().getValue();
								}else{
									LOG.info("buy again product price is null :"+productData.getCode()+", order number: "+buyItAgainData!=null?buyItAgainData.getOrderNumber():"null");
								}

								cspDisplayPrice=new BigDecimal(csp.getPrice()).multiply(new BigDecimal(uomMultiplier));

								final PriceData cspPriceData = priceDataFactory.create(PriceDataType.BUY, cspDisplayPrice,
										currencyIso);
								productData.setCustomerPrice(cspPriceData);

								final PriceData productPriceDisplay = priceDataFactory.create(PriceDataType.BUY, productDisplayPrice,
										currencyIso);
								productData.setPrice(productPriceDisplay);
						}
					});

					}
				}
			}
		}

	}
	
	
	@Override
	public List<ProductData> getCSPPriceListForRecommendedProducts(final List<ProductData> productDataList, String inventoryUomId)
	{
		final String currencyIso = storeSessionFacade.getCurrentCurrency().getIsocode();
		SiteOneWsPriceResponseData siteOneCspResponse = null;
		final Map<String, String> productCodeWithStore = new HashMap<>();
		Map<String, SiteOneWsPriceResponseData> cspResponse = new HashMap<>();
		String storeId = null;
		List<ProductData> prdtData= new ArrayList<ProductData>();
		final List<String> productCodeList = new ArrayList<String>();
		productDataList.forEach(listData -> {
			if (StringUtils.isNotEmpty(listData.getCode()))
			{
				productCodeList.add(listData.getCode());
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()) && listData.getStock() != null)
				{
					if (StringUtils.isNotEmpty(listData.getStock().getFullfillmentStoreId()))
					{
						productCodeWithStore.put(listData.getCode(), listData.getStock().getFullfillmentStoreId());
					}
					else
					{
						productCodeWithStore.put(listData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
					}
				}
				else
				{
					productCodeWithStore.put(listData.getCode(), storeSessionFacade.getSessionStore().getStoreId());
				}
			}
		});
		if (CollectionUtils.isNotEmpty(productCodeList) && !productCodeWithStore.isEmpty())
		{
			cspResponse = getCSPResponseForUoms(productCodeList, productCodeWithStore, null,null);
			if (!cspResponse.isEmpty())
			{ 
				for (final ProductData productData : productDataList)
				{
					storeId = productCodeWithStore.get(productData.getCode());
					siteOneCspResponse = cspResponse.get(storeId);
					if (null != siteOneCspResponse && CollectionUtils.isNotEmpty(siteOneCspResponse.getPrices()))
					{
						siteOneCspResponse.getPrices().forEach(csp -> {
							if (csp.getSkuId().equalsIgnoreCase(productData.getCode()))
							{
								double uomMultiplier=1.0;
								if (!productData.getSellableUoms().isEmpty())
								{
									updatePriceIfUOMProduct(productData);							
										for (final InventoryUOMData inventoryUOMData1 : productData.getSellableUoms()){
											// get the matching upc ids
											if(inventoryUOMData1.getInventoryUOMID().equalsIgnoreCase(inventoryUomId)){
												uomMultiplier= (double)inventoryUOMData1.getInventoryMultiplier();
												break;
											}
										}
									
								}else{
									ProductModel productModel = getProductService().getProductForCode(
											getSessionService().getAttribute("currentCatalogVersion"), productData.getCode());
										for (final InventoryUPCModel inventoryUOM : productModel.getUpcData()) {
											// get the matching upc ids
											if (inventoryUOM.getInventoryUPCID().equalsIgnoreCase(inventoryUomId)) {
												uomMultiplier = (double) inventoryUOM.getInventoryMultiplier();
												break;
											}
										}

									
								}

								BigDecimal cspDisplayPrice=new BigDecimal(csp.getPrice());
								BigDecimal productDisplayPrice=BigDecimal.valueOf(0.0);
								if (productData.getPrice()!=null){
									productDisplayPrice=productData.getPrice().getValue();
								}else{
									LOG.info("buy again product price is null :"+productData.getCode());
								}

								cspDisplayPrice=new BigDecimal(csp.getPrice()).multiply(new BigDecimal(uomMultiplier));

								final PriceData cspPriceData = priceDataFactory.create(PriceDataType.BUY, cspDisplayPrice,
										currencyIso);
								productData.setCustomerPrice(cspPriceData);

								final PriceData productPriceDisplay = priceDataFactory.create(PriceDataType.BUY, productDisplayPrice,
										currencyIso);
								productData.setPrice(productPriceDisplay);
								prdtData.add(productData);
						}
					});

					}
				} 
			}
		}
		return prdtData;	
	}

	private BuyItAgainData getMatchingBuyItAgain(final List<BuyItAgainData> buyItAgainDataList, final ProductData productData){
		for (final BuyItAgainData buyItAgainData1 : buyItAgainDataList)
		{
			//get the buy it again entry for the current product
			if(buyItAgainData1.getProductCode()!=null){
				if(buyItAgainData1.getProductCode().equalsIgnoreCase(productData.getCode())){
					return buyItAgainData1;
				}
			}
		}

		return null;
	}

	public void updatePriceIfUOMProduct(final ProductData productData)
	{
		List<InventoryUOMData> inventoryUOMListData = null;
		if (productData.getSellableUoms() != null)
		{
			inventoryUOMListData = productData.getSellableUoms();
		}
		if (CollectionUtils.isNotEmpty(inventoryUOMListData))
		{

			for (final InventoryUOMData inventoryUOMData1 : inventoryUOMListData)
			{
			
				if (!inventoryUOMData1.getHideUOMOnline() && productData.getHideUom() != null && productData.getHideUom())
				{
					final ProductData productDataUom = getProductByUOM(productData.getCode(),
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
					if (productData.getCustomerPrice()!=null && productDataUom.getCustomerPrice() != null)
					{
						productData.getCustomerPrice()
								.setFormattedValue("$".concat(productDataUom.getCustomerPrice().getValue().toString()));

					}
				}
			}

		}
	}

	@Override
	public void trackRetailCSPPrice(final String itemNumber, final String customerUnit, final String emailAddress,
			final String retailPrice, final String cspPrice, final String branchId)
	{
		final SiteonePricingTrackModel pricingTrack = new SiteonePricingTrackModel();
		pricingTrack.setProductCode(itemNumber);
		pricingTrack.setBranchId(branchId);
		pricingTrack.setCustomerUnit(customerUnit);
		pricingTrack.setEmailAddress(emailAddress);
		pricingTrack.setCspPrice(cspPrice);
		pricingTrack.setRetailPrice(retailPrice);
		getModelService().save(pricingTrack);

	}
	
	@Override
	public List<StoreLevelStockInfoData> populateStoreLevelStockInfoData(final String productCode, final boolean isPDP)
	{
		PointOfServiceData homeBranch = null;
		PointOfServiceData nearbyBranch = null;
		List<PointOfServiceData> nearbyStores = null;
		StoreLevelStockInfoData homeBranchStoreLevelStockInfoData = null;
		List<StoreLevelStockInfoData> storeLevelStockInfoDataList = new ArrayList<>();		
		List<StoreLevelStockInfoData> inStockStoreLevelStockInfoDataList = new ArrayList<>();
		List<StoreLevelStockInfoData> backorderStoreLevelStockInfoDataList = new ArrayList<>();
		
		if (null != storeSessionFacade.getSessionStore())
		{
			homeBranch = storeSessionFacade.getSessionStore();
			nearbyStores = (isPDP ? storeSessionFacade.getNearbyStoresFromSession() : storeSessionFacade.getExtendedNearbyStoresFromSession());
		}
		if (CollectionUtils.isNotEmpty(nearbyStores))
		{
			for (final List<Object> stockLevelRowData : siteOneStockLevelService.getStockLevelsForNearByStores(productCode,
					nearbyStores))
			{
				StoreLevelStockInfoData storeLevelStockInfoData = new StoreLevelStockInfoData();
				if ((stockLevelRowData.get(2) != null && (int) stockLevelRowData.get(2) > 0)
						|| (stockLevelRowData.get(1) != null && (int) stockLevelRowData.get(1) > 4))
				{					
					if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0)))
					{
						storeLevelStockInfoData.setStoreId((String) stockLevelRowData.get(0));
						nearbyBranch = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId((String) stockLevelRowData.get(0));
					}
					if (stockLevelRowData.get(3) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(3)))
					{
						storeLevelStockInfoData.setStoreName((String) stockLevelRowData.get(3));
					}
					if (stockLevelRowData.get(2) != null)
					{
						storeLevelStockInfoData.setOnHandQuantity((int) stockLevelRowData.get(2));
					}
					if (stockLevelRowData.get(1) == null)
					{
						storeLevelStockInfoData.setInventoryHit(0);
					}
					else
					{
						storeLevelStockInfoData.setInventoryHit((int) stockLevelRowData.get(1));
					}
					if (stockLevelRowData.get(2) != null && (int) stockLevelRowData.get(2) > 0)
					{
						storeLevelStockInfoData.setIsEligibleForBackorder(false);
						if (homeBranch.getStoreId().equalsIgnoreCase(storeLevelStockInfoData.getStoreId()))
						{
							storeLevelStockInfoData.setStockAvailablityMessage(getMessageSource().getMessage("product.instock.homestore.latest", new Object[]
											{ storeLevelStockInfoData.getOnHandQuantity(), storeLevelStockInfoData.getStoreName() }, getI18nService().getCurrentLocale()));
						}
						else
						{
							storeLevelStockInfoData.setStockAvailablityMessage(getMessageSource().getMessage("product.instock.nearbystore.latest", new Object[]
											{ storeLevelStockInfoData.getOnHandQuantity(), storeLevelStockInfoData.getStoreName() }, getI18nService().getCurrentLocale()));
						}
					}
					else
					{
						if (storeLevelStockInfoData.getInventoryHit() > 4)
						{
							storeLevelStockInfoData.setIsEligibleForBackorder(true);
							if (homeBranch.getStoreId().equalsIgnoreCase(storeLevelStockInfoData.getStoreId()))
							{
   							storeLevelStockInfoData.setStockAvailablityMessage(getMessageSource().getMessage("product.backorder.latest", new Object[]
										{ storeLevelStockInfoData.getStoreName() }, getI18nService().getCurrentLocale()));
							}
							else
							{
								storeLevelStockInfoData.setStockAvailablityMessage(getMessageSource().getMessage("product.backorder.nearby.latest", new Object[]
										{ storeLevelStockInfoData.getStoreName() }, getI18nService().getCurrentLocale()));
							}
						}
					}
					final GPS homeStoreGPS = new DefaultGPS(homeBranch.getGeoPoint().getLatitude(), homeBranch.getGeoPoint().getLongitude());
					if(null != nearbyBranch)
					{
                  final GPS currentStoreGPS = new DefaultGPS(nearbyBranch.getGeoPoint().getLatitude(), nearbyBranch.getGeoPoint().getLongitude());
                  final double distanceInKM = Double.valueOf(GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS));
                  final double distanceInMiles = (Double.valueOf(GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS)))* IMPERIAL_DISTANCE_RATIO;
                  if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
                  	storeLevelStockInfoData.setDistanceFromHomebranch((BigDecimal.valueOf(distanceInMiles).setScale(2, RoundingMode.HALF_UP).doubleValue()));
                  }else {
                  	storeLevelStockInfoData.setDistanceFromHomebranch((BigDecimal.valueOf(distanceInKM).setScale(2, RoundingMode.HALF_UP).doubleValue()));
                  }
					}
				}
				if(StringUtils.isNotBlank(storeLevelStockInfoData.getStockAvailablityMessage()))
				{
   				if (homeBranch.getStoreId().equalsIgnoreCase(storeLevelStockInfoData.getStoreId()))
   				{
   					homeBranchStoreLevelStockInfoData = storeLevelStockInfoData;
   				}
   				else
   				{   					
   					if(storeLevelStockInfoData.getStockAvailablityMessage().equalsIgnoreCase(getMessageSource().getMessage("product.instock.nearbystore.latest", new Object[]
   											{ storeLevelStockInfoData.getOnHandQuantity(), storeLevelStockInfoData.getStoreName() }, getI18nService().getCurrentLocale())))
   					{
   						inStockStoreLevelStockInfoDataList.add(storeLevelStockInfoData);
   					}
   					else
   					{
   						backorderStoreLevelStockInfoDataList.add(storeLevelStockInfoData);
   					}
   				}
				}
			}			
		}
		inStockStoreLevelStockInfoDataList.sort(Comparator.comparingDouble(StoreLevelStockInfoData::getDistanceFromHomebranch));
		backorderStoreLevelStockInfoDataList.sort(Comparator.comparingDouble(StoreLevelStockInfoData::getDistanceFromHomebranch));
		if(null != homeBranchStoreLevelStockInfoData)
		{
			storeLevelStockInfoDataList.add(homeBranchStoreLevelStockInfoData);
		}
		storeLevelStockInfoDataList.addAll(inStockStoreLevelStockInfoDataList);
		storeLevelStockInfoDataList.addAll(backorderStoreLevelStockInfoDataList);
		return storeLevelStockInfoDataList;
	}
	
	@Override
	public String populateStoreLevelStockInfoDataForPopup(final String productCode, final PointOfServiceData store)
	{
		String stockDetail = null;
		Boolean backorder = Boolean.FALSE;
		Boolean inventoryCheck = Boolean.FALSE;

		List<ProductOption> productOption = Arrays.asList(ProductOption.STORE_POPUP);
		final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, productOption);
		float inventoryMultiplier = productData.getInventoryMultiplier() != null ? productData.getInventoryMultiplier() : 1f;

		final List<List<Object>> stockLevelRow = siteOneStockLevelService.getStockLevelsForStore(productCode, store);
		if (stockLevelRow != null && !stockLevelRow.isEmpty())
		{
			final List<Object> stockLevelRowData = stockLevelRow.get(0);
			if(stockLevelRowData.get(4) != null && BooleanUtils.isTrue((boolean) stockLevelRowData.get(4)))
			{
				inventoryCheck = Boolean.TRUE;
			}

			if (stockLevelRowData.get(1) != null && (int) stockLevelRowData.get(1) >= inventoryMultiplier)
			{
				stockDetail = "inStock";
			}
			else if ( stockLevelRowData.get(2) != null && BooleanUtils.isTrue((boolean) stockLevelRowData.get(2)) &&
					((stockLevelRowData.get(3) != null && BooleanUtils.isNotTrue((boolean) stockLevelRowData.get(3))) || stockLevelRowData.get(3) == null))
			{
				stockDetail = "forceInStock";
			}
			else if (stockLevelRowData.get(5) != null && BooleanUtils.isTrue((boolean) stockLevelRowData.get(5)) && 
					checkHubStoreAvailability(productCode, store, stockLevelRowData.get(4)))
			{
				stockDetail = "inStockForShipping";
			}
			else if ((stockLevelRowData.get(0) != null && (int) stockLevelRowData.get(0) >= inventoryMultiplier) &&
					((stockLevelRowData.get(4) != null && BooleanUtils.isNotTrue((boolean) stockLevelRowData.get(4))) || stockLevelRowData.get(4) == null))
			{
				stockDetail = BACKORDER;
			}
			else
			{
				stockDetail = OUTOFSTOCK;
				if(stockLevelRowData.get(0) != null && (int) stockLevelRowData.get(0) > 0)
				{
					backorder = Boolean.TRUE;
				}
			}
		}
		else
		{
			if(productData.getIsShippable() != null && BooleanUtils.isTrue(productData.getIsShippable()) 
					&& checkHubStoreAvailability(productCode, store, (Object) productData.getInventoryCheck()))
			{
				stockDetail = "inStockForShipping";
			}
			else
			{
				stockDetail = OUTOFSTOCK;
			}
		}
		if(stockDetail.equalsIgnoreCase(OUTOFSTOCK) && !userFacade.isAnonymousUser())
		{
			List<ProductOption> productOptions = Arrays.asList(ProductOption.CATEGORIES);

			final ProductData prodData = productFacade.getProductForCodeAndOptions(productCode, productOptions);
			if(!BooleanUtils.isNotTrue(inventoryCheck)
					&& BooleanUtils.isTrue(siteoneSavedListFacade.categoryMatch(prodData.getLevel2Category()))
					&& BooleanUtils.isTrue(backorder))
			{
				stockDetail = "forceInStock";
			}
		}
		if(stockDetail.equalsIgnoreCase(OUTOFSTOCK) && siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P2) != null
				&& siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P2).equalsIgnoreCase("on")
				&& CollectionUtils.isNotEmpty(store.getDistributedBranches())) {
			Map<Boolean, Integer> stock = siteOneStockLevelService.isDcStockAvailable(productCode,store.getDistributedBranches(),0.0f);
			if(stock.containsKey(true)) {
				stockDetail = BACKORDER;
			}else {
				stockDetail = OUTOFSTOCK;
			}
		}
		return stockDetail;
	}
	
	
	private boolean checkHubStoreAvailability(String productCode, PointOfServiceData store, Object inventoryCheckObj) {
		boolean hubStoreAvailable = false;
		
		final List<String> hubStoreList = store.getHubStores();
		if (CollectionUtils.isNotEmpty(hubStoreList))
		{
			final String hubstore = hubStoreList.get(0);
			final List<String> products = new ArrayList<>();
			products.add(productCode);
			final List<List<Object>> stockLevelDataList = siteOneStockLevelService.getStockLevelsForHubStoresForProducts(products, hubstore);
			if (CollectionUtils.isNotEmpty(stockLevelDataList))
			{
				for (final List<Object> stockLevelData1 : stockLevelDataList)
				{
					if (checkHubStoreInHand(stockLevelData1) || checkHubStoreForceInStock(stockLevelData1, inventoryCheckObj))
					{
						hubStoreAvailable = true;
					}
				}
			}
		}			
		return hubStoreAvailable;
	}
	
	private boolean checkHubStoreInHand(List<Object> stockLevelData1) {
		boolean hubStoreInHand = false;
		if(stockLevelData1 != null && stockLevelData1.get(2) != null && Long.valueOf(stockLevelData1.get(2).toString()) > 0) {
			hubStoreInHand = true;
		}
		return hubStoreInHand;
	}
	
	private boolean checkHubStoreForceInStock(List<Object> stockLevelData1, Object inventoryCheckObj) {
		boolean hubStoreForceInStock = false;
		if (stockLevelData1 != null && BooleanUtils.isTrue((Boolean) stockLevelData1.get(6)) &&
				(BooleanUtils.isNotTrue((Boolean) inventoryCheckObj) || BooleanUtils.isNotTrue((Boolean) stockLevelData1.get(7))))
		{
			hubStoreForceInStock = true;
		}
		return hubStoreForceInStock;
	}

	@Override
	public StoreLevelStockInfoData populateHubStoresStockInfoData(final String productCode, final boolean isPDP,
			final boolean isForcedInStock)
	{
		List<PointOfServiceData> nearbyStores = new ArrayList<>();
		if (null != storeSessionFacade.sortNearbyStoresBasedOnDistanceFromHomeBranch(isPDP))
		{
			nearbyStores = storeSessionFacade.sortNearbyStoresBasedOnDistanceFromHomeBranch(isPDP);
		}
		final List<PointOfServiceData> hubStores = storeSessionFacade.sortHubStoresBasedOnDistanceFromHomeBranch();
		final List<StoreLevelStockInfoData> inStockList = new ArrayList<>();
		final List<StoreLevelStockInfoData> backorderList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(nearbyStores))
		{

			/*
			 * for (PointOfServiceData pos : nearbyStores) { if (null != ((SiteOneStoreFinderFacade)
			 * storeFinderFacade).getHubStoreBranch(pos.getStoreId())) { hubStores.add(((SiteOneStoreFinderFacade)
			 * storeFinderFacade).getHubStoreBranch(pos.getStoreId())); } }
			 */
			if (CollectionUtils.isNotEmpty(hubStores))
			{
				final List<List<Object>> sortedStockLevelsForNearByStores = new ArrayList<List<Object>>();
				final List<List<Object>> stockLevelsForNearByStores = siteOneStockLevelService
						.getStockLevelsForNearByStores(productCode, hubStores);
				if (CollectionUtils.isNotEmpty(stockLevelsForNearByStores))
				{
					for (final PointOfServiceData store : hubStores)
					{
						for (final List<Object> stockLevelRowData : stockLevelsForNearByStores)
						{
							if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0))
									&& store.getStoreId().equalsIgnoreCase((String) stockLevelRowData.get(0)))
							{
								sortedStockLevelsForNearByStores.add(stockLevelRowData);
							}
						}

					}
				}
				if (CollectionUtils.isNotEmpty(sortedStockLevelsForNearByStores))
				{
					for (final List<Object> stockLevelRowData : sortedStockLevelsForNearByStores)
					{
						final ProductModel product = siteOneProductService.getProductByProductCode(productCode);
						final Collection<PointOfServiceModel> pointOfServiceList = product.getStores();
						final Collection<StockLevelModel> stockLevelList = new ArrayList<>();
						if(CollectionUtils.isNotEmpty(pointOfServiceList))
						{
							for (final PointOfServiceModel pointOfService : pointOfServiceList)
							{
								if (pointOfService.getStoreId().equalsIgnoreCase((String) stockLevelRowData.get(0)))
								{
									stockLevelList.addAll(getStockService().getStockLevels(product, pointOfService.getWarehouses()));
								}
							}
						}
						final Integer obj = Integer.valueOf(0);
						int inventoryHitCount = obj.intValue();
						if (CollectionUtils.isNotEmpty(stockLevelList))
						{
							for (final StockLevelModel stockLevel : stockLevelList)
							{
								inventoryHitCount = stockLevel.getAvailable();
								if (inventoryHitCount > 0)
								{
									break;
								}
							}
						}
						final StoreLevelStockInfoData storeLevelStockInfoData = new StoreLevelStockInfoData();
						if (inventoryHitCount > 0
								|| (BooleanUtils.isTrue((Boolean) stockLevelRowData.get(5))
										&& (isForcedInStock || (BooleanUtils.isNotTrue((Boolean) stockLevelRowData.get(6))))))
						{
							if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0)))
							{
								storeLevelStockInfoData.setStoreId((String) stockLevelRowData.get(0));
							}
							if (stockLevelRowData.get(2) != null)
							{
								storeLevelStockInfoData.setOnHandQuantity((int) stockLevelRowData.get(2));
							}
							if (stockLevelRowData.get(1) == null)
							{
								storeLevelStockInfoData.setInventoryHit(0);
							}
							else
							{
								storeLevelStockInfoData.setInventoryHit((int) stockLevelRowData.get(1));
							}
							if (storeLevelStockInfoData.getOnHandQuantity() > 0
									|| (BooleanUtils.isTrue((Boolean) stockLevelRowData.get(5))
											&& (isForcedInStock || (BooleanUtils.isNotTrue((Boolean) stockLevelRowData.get(6))))))
							{
								inStockList.add(storeLevelStockInfoData);
								break;
							}
							else
							{
								if (storeLevelStockInfoData.getInventoryHit() > 4)
								{
									backorderList.add(storeLevelStockInfoData);
								}
							}
						}
					}
					if (CollectionUtils.isNotEmpty(inStockList))
					{
						return inStockList.get(0);
					}
					else
					{
						if (CollectionUtils.isNotEmpty(backorderList))
						{
							return backorderList.get(0);
						}
					}
				}
			}
		}
		return null;
	}

  
  /**
	 * @return List<SiteOneWsProductResponseData>
	 */
	@Override
	public List<SiteOneWsProductResponseData> getAllProducts()
	{
		return populateProductData(siteOneProductService.getAllProducts());
	}

	/**
	 * @param productCodes
	 * @return List<SiteOneWsProductResponseData>
	 */
	@Override
	public List<SiteOneWsProductResponseData> getAllRecommProducts(final String productCodes)
	{
		return populateProductData(siteOneProductService.getAllRecommProducts(productCodes));
	}

	private List<SiteOneWsProductResponseData> populateProductData(List<ProductModel> productModels){
		final List<SiteOneWsProductResponseData> productResponseDataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(productModels))
		{
			for (final ProductModel productModel : productModels)
			{
				final SiteOneWsProductResponseData productResponseData = new SiteOneWsProductResponseData();
				productResponseData.setProductId(productModel.getCode());
				productResponseData.setLinkURL("/p/" + productModel.getCode());
				if (productModel.getPicture() != null)
				{
					productResponseData.setImageURL(productModel.getPicture().getURL());
				}
				if(productModel.getCreationtime() != null) {
					final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					productResponseData.setCreationTime(formatter.format(productModel.getCreationtime().getTime()));
					productResponseData.setPrice(siteOneProductService.getProductPrice(productModel.getCode()));
					} 
					productResponseDataList.add(productResponseData);
				}
			}
			return productResponseDataList;
		}

		/**
		 * @param categoriesCode
		 * @return List<SiteOneWsCategoryResponseData>
		 */
		@Override
		public List<SiteOneWsCategoryResponseData> getAllRecommCategories(final String categoriesCode)
		{
			final List<SiteOneWsCategoryResponseData> categoryResponseDataList = new ArrayList<>();
			List<CategoryModel> categoryModels = siteOneProductService.getAllRecommCategories(categoriesCode);
			if (CollectionUtils.isNotEmpty(categoryModels))
			{
				for (final CategoryModel categoryModel : categoryModels)
				{
					final SiteOneWsCategoryResponseData categoryResponseData = new SiteOneWsCategoryResponseData();
					categoryResponseData.setCategoryId(categoryModel.getPimCategoryId());
					categoryResponseData.setLinkURL("/c/" + categoryModel.getCode());
					if (categoryModel.getThumbnail() != null)
					{
						categoryResponseData.setImageURL(categoryModel.getThumbnail().getURL());
					}
					categoryResponseDataList.add(categoryResponseData);
				}
			}
			return categoryResponseDataList;
		}

		@Override
		public Object[] getRecommendedProductsToDisplay(final String placementPage, final String categoryId, final String productId,
														final String sessionId, final String pagePosition) {

			return getRecommendedProductsToDisplay(placementPage, categoryId, productId, sessionId, pagePosition,
					null, null, null, null, null);
		}

		@Override
		public Object[] getRecommendedProductsToDisplay(final String placementPage, final String categoryId,
														final String productId, final String sessionId, final String pagePosition,
														final String orderId, final String productPrices, final String productPricesCents,
														final String quantities, final String searchTerm) {
			String responseSessionId = sessionId;
			String recommendationTitle = "";
			String recommendationTitlerr2 = "";
			Map<String, List<ProductData>> responseProductListMap = new HashMap<>();
			final BaseSiteModel basesite = getBaseSiteService().getCurrentBaseSite();
			Boolean algonomyRecommendation = ((basesite.getUid().equalsIgnoreCase(SiteoneintegrationConstants.BASESITE_CA)) ?
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ALGONOMYRECOMMFLAG_CA)) : 
						Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(ALGONOMYRECOMMFLAG)));
			if (algonomyRecommendation) {
				String userId = "";
				final AlgonomyRequestData algonomyRequestData = new AlgonomyRequestData();
				PointOfServiceData homeBranch = null;
				if (null != storeSessionFacade.getSessionStore())
				{
					 homeBranch = storeSessionFacade.getSessionStore();
					 algonomyRequestData.setRegionId(homeBranch.getStoreId());
				}
				algonomyRequestData.setSessionId(sessionId);
				if (!userFacade.isAnonymousUser()) {
					userId = ((B2BCustomerModel) getUserService().getCurrentUser()).getGuid();
					algonomyRequestData.setUserId(userId);
				}

				String placement = "";
				String itemPage = "itemPage";
				String mobileApp = "mobileApp";
				String homePage = "homePage";

				if (homePage.equalsIgnoreCase(placementPage)) {
					placement = Config.getString(HOME_PLACEMENTS, "");
				} else if ("categoryPage".equalsIgnoreCase(placementPage)) {
					placement = Config.getString(CATEGORY_PLACEMENTS, "");
					algonomyRequestData.setCategoryId(categoryId);
					algonomyRequestData.setProductId(productId);
				} else if (itemPage.equalsIgnoreCase(placementPage)) {
					placement = Config.getString(ITEM_PLACEMENTS, "");
					algonomyRequestData.setProductId(productId);
					algonomyRequestData.setBiId(productId);
				} else if ("atcPage".equalsIgnoreCase(placementPage)) {
					placement = Config.getString(ATC_PLACEMENTS, "");
					algonomyRequestData.setAtcid(productId);
				} else if ("purchaseCompletePage".equalsIgnoreCase(placementPage)) {
					placement = Config.getString(PURCHASE_COMPLETE_PLACEMENTS, "");
					algonomyRequestData.setOrderId(orderId);
					algonomyRequestData.setProductId(productId);
					algonomyRequestData.setProductPrices(productPrices);
					algonomyRequestData.setProductPricesCents(productPricesCents);
					algonomyRequestData.setQuantities(quantities);
				} else if ("searchPage".equalsIgnoreCase(placementPage)) {
					placement = Config.getString(SEARCH_PLACEMENTS, "");
					algonomyRequestData.setSearchTerm(searchTerm);
					algonomyRequestData.setProductId(productId);
				} else if ("cartPage".equalsIgnoreCase(placementPage)) {
					placement = Config.getString(CART_PLACEMENTS, "");
					algonomyRequestData.setProductId(productId);
				} else if("PersonalPage".equalsIgnoreCase(placementPage)) {
					placement =Config.getString(PERSONAL_PAGE_PLACEMENT, "");
				} else if("BuyAgainPage".equalsIgnoreCase(placementPage)) {
					placement =Config.getString(BUYAGAIN_PAGE_PLACEMENT, "");
				} else if("RecommProductPage".equalsIgnoreCase(placementPage)) {
					placement =Config.getString(RECOMPROD_PAGE_PLACEMENTS, "");
				}
				algonomyRequestData.setPlacements(placement);
				
				int noOfPlacements=1;
				if((!mobileApp.equalsIgnoreCase(pagePosition) && homePage.equalsIgnoreCase(placementPage)) || itemPage.equalsIgnoreCase(placementPage)) {
					noOfPlacements=2;
				}
				
				int reqListSize = 0;
				Map<String, List<AlgonomyResponseData>> algonomyResponsePlacementMap = siteOneProductService.getRecommendedProducts(algonomyRequestData);
				int index=1;
				if(algonomyResponsePlacementMap!=null) {
   				for(Map.Entry<String, List<AlgonomyResponseData>> algonomyResponsePlacementEntry:algonomyResponsePlacementMap.entrySet()) {					
   					if(index<=noOfPlacements) {
   						List<String> productCodeList = new ArrayList<>();
   						List<ProductData> responseProductList = new ArrayList<>();
   						String placementArr=algonomyResponsePlacementEntry.getKey();
   						if (!mobileApp.equalsIgnoreCase(pagePosition) && homePage.equalsIgnoreCase(placementPage) && "rr1".equalsIgnoreCase(placementArr)) {
   							reqListSize = 4;
   						}
   						List<AlgonomyResponseData> algonomyResponseList=algonomyResponsePlacementEntry.getValue();
   						if (algonomyResponseList != null && !algonomyResponseList.isEmpty()) {
   							if("rr1".equalsIgnoreCase(placementArr)){
      	   					responseSessionId = algonomyResponseList.get(0).getSessionID();
      	   					recommendationTitle = algonomyResponseList.get(0).getRecommendationTitle();
   							}
   							if("rr2".equalsIgnoreCase(placementArr)){
   								recommendationTitlerr2 = algonomyResponseList.get(0).getRecommendationTitle();
   							}
   	   					productCodeList.addAll(algonomyResponseList.stream().map(algonomyResponse -> algonomyResponse.getProductID()).collect(Collectors.toList()));
   	   			      List<ProductData> productList = collectSearchProducts(productCodeList);
   	   					for (final AlgonomyResponseData algonomyResponse : algonomyResponseList) {
   	   					final ProductData productData = productList.stream()
								.filter(pData -> pData.getVariantSkus().contains(algonomyResponse.getProductID())).findFirst()
								.orElse(null);
   	   						if (productData!=null && (reqListSize == 0 || responseProductList.size() < reqListSize)){
   	   							productData.setClickTrackingURL(algonomyResponse.getClickTrackingURL());
   	   							responseProductList.add(productData);
   	   						}
   	   					}
   	   				}
   						responseProductListMap.put(placementArr, responseProductList);
   						reqListSize=0;
   					}  
   					index++;
   				}
				}
			}
			return new Object[]{ responseSessionId, responseProductListMap.get("rr1"), recommendationTitle, responseProductListMap.get("rr2"), recommendationTitlerr2, responseProductListMap.get("rr3") };
		}

		protected List<ProductData> collectSearchProducts(final List<String> productCodes)
		{
			List<ProductData> products = new ArrayList<>();
			final SearchQueryData searchQueryData = new SearchQueryData();
			final String productCode = String.join(",", productCodes);
			searchQueryData.setValue(productCode);
			if (searchQueryData.getValue() != null)
			{
				final SearchStateData searchState = new SearchStateData();
				searchState.setQuery(searchQueryData);
				final PageableData pageableData = new PageableData();
				pageableData.setPageSize(24);
				products = siteOneSDSProductSearchFacade.curatedPLPSearch(searchState, pageableData, RECOMMENDED_PRODUCT).getResults();
			}
			return products;

		}	
			
		
		@Override
		public void getVariantProductMultiplier(final ProductData productData)
		{
			final List<Double> allCspPrices = new ArrayList<Double>();
			final List<Double> allRetailPrices = new ArrayList<Double>();
			for (final VariantOptionData variant : productData.getVariantOptions())
			{
				final List<InventoryUOMData> inventoryUOMListnew = new ArrayList<>();
				if (CollectionUtils.isNotEmpty(variant.getSellableUoms()))
				{
					for (final InventoryUOMData inventoryUOMData1 : variant.getSellableUoms())
					{

						if (Boolean.FALSE.equals(inventoryUOMData1.getHideUOMOnline()) && Boolean.TRUE.equals(variant.getHideUom()))
						{
							final ProductData productDataUom = getProductByUOM(variant.getCode(), 
									inventoryUOMData1.getInventoryMultiplier(), inventoryUOMData1.getInventoryUOMID());
							if (productDataUom.getPrice() != null)
							{
								inventoryUOMData1.setNewUomPrice(productDataUom.getPrice().getValue().floatValue());
								if (variant.getPriceData() != null)
								{
									variant.getPriceData().setValue(productDataUom.getPrice().getValue());
								}
								else
								{
									final PriceData priceData = new PriceData();
									priceData.setValue(productDataUom.getPrice().getValue());
									variant.setPriceData(priceData);
								}
								variant.getPriceData().setFormattedValue("$".concat(productDataUom.getPrice().getValue().toString()));
								allRetailPrices.add(productDataUom.getPrice().getValue().doubleValue());
							}

							if (productDataUom.getCustomerPrice() != null)
							{
								final CustomerPriceData price = new CustomerPriceData();
								price.setPrice(productDataUom.getCustomerPrice().getValue().toString());
								variant.setCustomerPrice(price);
								allCspPrices.add(Double.parseDouble(price.getPrice()));

							}


						}
						inventoryUOMListnew.add(inventoryUOMData1);
					}
					if (variant.getHideUom() != null && variant.getHideUom())
					{
						variant.getSellableUoms().clear();
						variant.setSellableUoms(inventoryUOMListnew);
					}
				}
			}
			allCspPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);
			allRetailPrices.removeIf(priceInfo -> (Double.compare(priceInfo, 0.0d)) == 0);
			if(!allCspPrices.isEmpty()) {
				getPriceRangeForVariant(productData, allCspPrices);
			} else if(!allRetailPrices.isEmpty()) {
				getPriceRangeForVariant(productData, allRetailPrices);
			}
		}
		
		@Override
		public void getPriceRangeForVariant(ProductData productData, final List<Double> prices) {
			Collections.sort(prices);
			final PriceRangeData priceRange = new PriceRangeData();
			final BigDecimal minPrice = BigDecimal.valueOf(prices.get(0));
			final BigDecimal maxPrice = BigDecimal.valueOf(prices.get(prices.size() - 1));
			priceRange.setMinPrice(getPriceDataFactory().create(PriceDataType.FROM, minPrice,
					getCommonI18NService().getCurrentCurrency().getIsocode()));
			priceRange.setMaxPrice(getPriceDataFactory().create(PriceDataType.FROM, maxPrice,
					getCommonI18NService().getCurrentCurrency().getIsocode()));
			productData.setPriceRange(priceRange);
		}
		
		@Override
		public void createRecentScanProducts(String accountNumber, String productCode) {
			RecentScanProductsModel recentScanProductsModel=new RecentScanProductsModel();
			recentScanProductsModel.setAccountNumber(accountNumber);
			recentScanProductsModel.setProductCode(productCode);
			siteOneRecentScanService.createRecentScanProducts(recentScanProductsModel);
		}

		@Override
		public List<String> getRecentScanProductsByUser(final String accountNumber)
		{
			return siteOneRecentScanService.getRecentScanProductsByUser(accountNumber);
		}
		
		
		@Override
		public List<ProductHighlights> getHighLights(ProductModel productModel){
		List<ProductHighlights> highlights = productModel.getFeatures().stream()
	    .filter(feature -> {
	        ClassAttributeAssignmentModel assignment = feature.getClassificationAttributeAssignment();
	        return assignment != null && Boolean.TRUE.equals(assignment.getHighlight());
	    })
	    .map(feature -> {
	        ClassAttributeAssignmentModel assignment = feature.getClassificationAttributeAssignment();
	        ProductHighlights hl = new ProductHighlights();
	        hl.setName(assignment.getClassificationAttribute().getName());
	        hl.setValue(feature.getValue() != null ? feature.getValue().toString() : "");
	        LOG.error("Highlight"+ hl.getName()+ hl.getValue());
	        return hl;
	    })
	    .limit(6)
	    .collect(Collectors.toList());
		
		return highlights;
		}
		
		@Override
		public List<ProductData> updateSalesInfoBackorderForOrderEntry(List<OrderEntryData> orderEntryList) {
			List<ProductData> productDataList = new ArrayList<>();
			if(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P3) != null && 
					siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P3).equalsIgnoreCase("on"))
			{
				List<ProductData> productDataSubList = new ArrayList<>();
				for (final OrderEntryData orderEntryData : orderEntryList)
				{
					if(orderEntryData.getProduct().getOutOfStockImage() !=null 
							&& orderEntryData.getProduct().getOutOfStockImage()) {
						productDataSubList.add(orderEntryData.getProduct());
					}
					if(CollectionUtils.isNotEmpty(productDataSubList) && productDataSubList.size()==20) {
						updateSalesInfoBackorderForProduct(productDataSubList);
						productDataList.addAll(productDataSubList);
						productDataSubList = new ArrayList<>();
					}
				}

				if(CollectionUtils.isNotEmpty(productDataSubList) && productDataSubList.size()<20) {
					updateSalesInfoBackorderForProduct(productDataSubList);
					productDataList.addAll(productDataSubList);
					productDataSubList = new ArrayList<>();
				}
			}
			return productDataList;
		}
		
		@Override
		public void updateSalesInfoBackorderForProduct(List<ProductData> productList) {
			if(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P3) != null &&
					siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P3).equalsIgnoreCase("on"))
			{
				String productCodes = productList.stream().map(productData->productData.getCode()).collect(Collectors.joining(","));
				SiteOneSalesRequestData siteOneSalesRequestData  = new SiteOneSalesRequestData();
				siteOneSalesRequestData.setStoreNumber(Integer.valueOf(storeSessionFacade.getSessionStore().getStoreId()));
				siteOneSalesRequestData.setCorrelationID(UUID.randomUUID().toString());
				siteOneSalesRequestData.setItems(productCodes);
				try
				{  
					SiteOneSalesResponseData salesResponseData = siteOneSalesDataWebService.getSalesData(siteOneSalesRequestData);
					if(salesResponseData != null && CollectionUtils.isNotEmpty(salesResponseData.getItems())) 
					{
						 salesResponseData.getItems().forEach(responseItem ->{
							
							productList.forEach(productData ->{
								if(responseItem.getSkuID().equalsIgnoreCase(productData.getCode()) && BooleanUtils.isTrue(responseItem.getIsBackOrderEligible())) 
								{
									StockData stockData = productData.getStock();
									stockData.setIsHomeStoreInventoryHit(true);
									stockData.setInventoryHit(Integer.valueOf(1));
									stockData.setDcBranchStock(Boolean.TRUE);
									stockData.setFullfilledStoreType("InventoryHit");
									stockData.setFullfillmentStoreId(storeSessionFacade.getSessionStore().getStoreId());
									productData.setOutOfStockImage(null);
									final ProductModel productModel = getProductService().getProductForCode(productData.getCode());
									getProductSellableAndMessagePopulator().populate(productModel, productData);
								}
							});
							
						});
					}
				}
				catch (Exception ex)
				{
					LOG.error("Exception occured while calling the sales data API "+siteOneSalesRequestData.getCorrelationID()+" : "+ex);
				}
			}						
		}

		/**
		 * @return the stockService
		 */
		public StockService getStockService()
		{
			return stockService;
		}

		/**
		 * @param stockService the stockService to set
		 */
		public void setStockService(StockService stockService)
		{
			this.stockService = stockService;
		}
		
		
		protected boolean isLevel1Category(final Collection<CategoryModel> supercategories)
		{
			boolean isPartOfPrimaryHierarchy = false;
			for (final CategoryModel superCategory : supercategories)
			{
				if (Config.getString("primary.hierarchy.root.category", null).equalsIgnoreCase(superCategory.getCode()))
				{
					isPartOfPrimaryHierarchy = true;
					break;
				}
			}
			return isPartOfPrimaryHierarchy;
		}
		
		protected CategoryModel getPrimaryCategory(final Collection<CategoryModel> supercategories)
		{
			for (final CategoryModel category : supercategories)
			{
				if (!(category instanceof ClassificationClassModel) && !(category instanceof VariantCategoryModel)
						&& !(category instanceof VariantValueCategoryModel) && category.getCode().startsWith("SH"))
				{
					return category;
				}
			}
			return null;
		}

		public BaseSiteService getBaseSiteService()
		{
			return baseSiteService;
		}

		public void setBaseSiteService(final BaseSiteService baseSiteService)
		{
			this.baseSiteService = baseSiteService;
		}
}
