/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.util.Config;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.GenericVariantProductModel;
import de.hybris.platform.variants.model.VariantCategoryModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.variants.model.VariantValueCategoryModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.RegulatoryStatesCronJobService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;



/**
 * @author 1197861
 *
 */
public class ProductSellableAndMessagePopulator implements Populator<ProductModel, ProductData>
{
	private SiteOneStoreSessionFacade storeSessionFacade;
	private ProductService productService;
	private static final String SHIPPING_DELIVERY_FEE_BRANCHES = "ShippingandDeliveryFeeBranches";
	private static final String SHIPPING_DELIVERY_LA_FEE_BRANCHES = "ShippingandDeliveryLAFeeBranches";
	private static final String DC_SHIPPING_FEE_BRANCHES = "DCShippingFeeBranches";
 	private static final String DC_SHIPPING_THRESHOLD = "DCShippingThreshold";
	private static final String FORCE_STOCK_MESSAGE= "product.outofstock.forcestock";
	private static final String FORCE_STOCK_EXTENDED_MESSAGE= "product.outofstock.forcestock.extended.message";
	private static final String FORCE_STOCK_EXTENDED_MESSAGE_CA = "product.outofstock.forcestock.extended.message.ca";
	private static final String RETAIL_TRADECLASS = "409033";
	private static final String Bulk_Category = "Bulkcategory";
	private static final String HOLIDAY_LIGHTING = "HolidayLightingCategory"; 

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "regulatoryStatesCronJobService")
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;

	@Resource(name = "storeFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	private MessageSource messageSource;


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


	public ProductService getProductService()
	{
		return productService;
	}


	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	private final Logger LOG = Logger.getLogger(ProductSellableAndMessagePopulator.class);
	private static final String DELIVERY_INFO = "delivery.info";

	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException
	{

		final Collection<VariantProductModel> variants = source.getVariants();
		target.setMultidimensional(Boolean.valueOf(CollectionUtils.isNotEmpty(variants)));
		target.setIsNurseryProduct(Boolean.FALSE);
		target.setIsHardscapeProduct(false);
		if (CollectionUtils.isNotEmpty(target.getCategories()))
		{
			target.getCategories().forEach(category -> {
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("HardscapeCategories", category.getCode()))
				{
					target.setAskAnExpertEnable(Boolean.TRUE);
				}
				if(category.getCode().startsWith("SH16")) {
					target.setIsNurseryProduct(Boolean.TRUE);
				}
				else if(category.getCode().startsWith("SH15"))
		      {
					target.setIsHardscapeProduct(Boolean.TRUE);
		      }

			});
		}


		boolean UOMCheck = false;
		float multiplier = 0.0f;
		target.setIsRUPTrainingSku(Boolean.FALSE);
		if (siteOneFeatureSwitchCacheService.isProductPresentUnderFeatureSwitch("RUPTrainingSku", target.getCode()))
		{
			target.setIsRUPTrainingSku(Boolean.TRUE);
		}
		if (CollectionUtils.isNotEmpty(source.getUpcData()))
		{
			Boolean baseUPC = Boolean.FALSE;
			Boolean bulkUOMPriceEnable = Boolean.TRUE;
			for (final InventoryUPCModel upcData : source.getUpcData()) {
				if (BooleanUtils.isTrue(upcData.getBaseUPC()) && BooleanUtils.isNotTrue(upcData.getHideUPCOnline())) {
					bulkUOMPriceEnable = Boolean.FALSE;
				}
			}
			if (BooleanUtils.isTrue(bulkUOMPriceEnable))
			{
   			for (final InventoryUPCModel upcData : source.getUpcData())
   			{
   				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("BulkUOM", upcData.getCode()))
   				{
   					if (source.getBaseUPCCode() != null)
   					{
   						target.setBulkUOMCode(source.getBaseUPCCode().toLowerCase());
   					}
   					else
   					{
   						baseUPC = Boolean.TRUE;
   					}
   					if (BooleanUtils.isNotTrue(upcData.getBaseUPC()) && BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
   					{
   						if (target.getCustomerPrice() != null)
   						{
   							target.setBulkUOMCustomerPrice(
   									"$" + (target.getCustomerPrice().getValue().setScale(3, RoundingMode.HALF_UP)).toString());
   						}
   						if (target.getPrice() != null)
   						{
   							target.setBulkUOMPrice("$" + (target.getPrice().getValue().setScale(3, RoundingMode.HALF_UP)).toString());
   						}
   					}
   				}
   			}
			}
			if (BooleanUtils.isTrue(baseUPC))
			{
				source.getUpcData().forEach(upcData -> {
					if (BooleanUtils.isTrue(upcData.getBaseUPC()))
					{
						target.setBulkUOMCode(upcData.getCode().toLowerCase());
					}
				});
			}
			boolean forceInStock = false;
			if (target.getStock() != null && (BooleanUtils.isNotTrue(target.getStock().getIsHomeStoreStockAvailable()) || target.getIsHardscapeProduct()) && BooleanUtils.isTrue(target.getStock().getIsForceInStock()))
			{
				forceInStock = true;
			}
			target.setIsForceInStock(forceInStock);
			
			if (BooleanUtils.isTrue(target.getIsForceInStock()))
			{
				target.setInventoryCheck(Boolean.FALSE);
			}
			
			if (target.getStock() != null && CollectionUtils.isNotEmpty(target.getSellableUoms()) && target.getSellableUoms().size() <= 1
					&& BooleanUtils.isNotTrue(target.getStock().getIsHomeStoreInventoryHit()) && BooleanUtils.isNotTrue(target.getStock().getIsHomeStoreStockAvailable()))
			{
				for (final InventoryUPCModel upcData : source.getUpcData())
				{

					if ((BooleanUtils.isNotTrue(upcData.getBaseUPC()) && BooleanUtils.isNotTrue(upcData.getHideUPCOnline())))
					{
						if (null != target.getStock().getStockLevel()
								&& target.getStock().getStockLevel() < upcData.getInventoryMultiplier())
						{
							UOMCheck = true;
							multiplier = upcData.getInventoryMultiplier();
						}
					}
				}
			}
		}

		final PointOfServiceData sessionStore = storeSessionFacade.getSessionStore();
		final B2BUnitData sessionShipTo = storeSessionFacade.getSessionShipTo();

		final String stockAvailabilityMessage = null;
		boolean isGuestOrHomeowner = false;
		boolean disableAddToCart = false;
		boolean isNotBuyable = false;
		boolean isMyStoreProduct = false;
		boolean isProductSellable = false;
		boolean inStockFlag = false;
		boolean orderingAccount = true;
		boolean inventoryHit = false;
		Boolean isRup = Boolean.FALSE;
		boolean inventoryFlag = false;
		boolean isMixedCart = false;
		boolean isSplitMixedCart = false;
		Boolean isTampaBranch = Boolean.FALSE;
		Boolean isLABranch = Boolean.FALSE;
		final Map<String, String> shippingFeeBranch = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
		final Map<String, String> hubShippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);
		Boolean dcBranch = Boolean.FALSE;
	
		
		final Map<String, String> bulkCalciMapping = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(Bulk_Category);

		if (bulkCalciMapping != null)
		{
			final List<String> productCategoryList = getCategoriesForProduct(source);
			if (CollectionUtils.isNotEmpty(productCategoryList)
					&& productCategoryList.stream().anyMatch(bulkCalciMapping::containsKey))
			{
				for (final String categoryCode : productCategoryList)
				{
					if (bulkCalciMapping.containsKey(categoryCode))
					{
						target.setBulkCalculatorType(bulkCalciMapping.get(categoryCode));
					}
				}
			}
		}
		
		final Map<String, String> holidayLightingCalciMapping = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(HOLIDAY_LIGHTING);
		if (holidayLightingCalciMapping != null)
		{
			LOG.info("Inside Product Sellable and Message Populator");
			final List<String> productCategoryList = getCategoriesForProduct(source);
			if (CollectionUtils.isNotEmpty(productCategoryList)
					&& productCategoryList.stream().anyMatch(holidayLightingCalciMapping::containsKey))
			{
				for (final String categoryCode : productCategoryList)
				{
					if (holidayLightingCalciMapping.containsKey(categoryCode))
					{
						LOG.info("holidat lighting calci Mapping : " + holidayLightingCalciMapping.get(categoryCode));
						target.setHolidayLightingCalcType(holidayLightingCalciMapping.get(categoryCode));
					}
				}
			}
		}
		
		if (null != sessionStore && null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0) 
			&& shippingFeeBranch.containsKey(sessionStore.getHubStores().get(0)))
		{
			dcBranch=Boolean.TRUE;
		}
		else if (null != sessionStore && null != sessionStore.getHubStores() && null != sessionStore.getHubStores().get(0) 
				&& hubShippingFee.containsKey(sessionStore.getHubStores().get(0)))
		{
			dcBranch=Boolean.TRUE;
		}
		if (null != sessionStore && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
				sessionStore.getStoreId()))
		{
			isMixedCart = true;
		}

		if (!userFacade.isAnonymousUser() && null != sessionStore && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",
				sessionStore.getStoreId()))
		{
			isSplitMixedCart = true;
		}

		if (null != sessionStore && null != sessionStore.getStoreId())
		{
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuantityMinimumBranches",
					sessionStore.getStoreId()) && null != source.getMinOrderQuantity() && (null == target.getOrderQuantityInterval() || target.getOrderQuantityInterval() == 0))
			{
				target.setMinOrderQuantity(source.getMinOrderQuantity());
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SHIPPING_DELIVERY_FEE_BRANCHES,
					sessionStore.getStoreId()))
			{
				isTampaBranch = Boolean.TRUE;
			}
			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch(SHIPPING_DELIVERY_LA_FEE_BRANCHES,
					sessionStore.getStoreId()))
			{
				isLABranch = Boolean.TRUE;
			}
		}
		if (null != sessionShipTo)
		{
			orderingAccount = ((SiteOneB2BUnitService) b2bUnitService).isOrderingAccount(sessionShipTo.getUid());
			LOG.info(
					"ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " orderingAccountFlag " + orderingAccount);
			if(sessionShipTo.getTradeClass() != null && sessionShipTo.getTradeClass().equalsIgnoreCase(RETAIL_TRADECLASS))
			{
				isGuestOrHomeowner = true;
			}
		}
		else
		{
			isGuestOrHomeowner = true;
		}
		final Map<String, String> shippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_FEE_BRANCHES);
		final Map<String, String> hubStoreShippingFee = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUBSTORE_SHIPPING_FEE_BRANCHES);
		PointOfServiceData posHub = null;
		if(sessionStore  != null && sessionStore.getHubStores() != null && sessionStore.getHubStores().get(0) != null)
		{
		   posHub = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(sessionStore.getHubStores().get(0));
		}
		if(sessionShipTo != null && sessionShipTo.getShippingThreshold() != null && !sessionShipTo.getShippingThreshold().isEmpty()
				&& (BooleanUtils.isTrue(isLABranch) || BooleanUtils.isTrue(isTampaBranch) || BooleanUtils.isTrue(dcBranch)))
		{
			target.setFreeShippingThreshold(sessionShipTo.getShippingThreshold());
		}
		else if (posHub != null && isGuestOrHomeowner  && posHub.getShippingThresholdForHomeownerOrGuest() != null && !posHub.getShippingThresholdForHomeownerOrGuest().isEmpty())
      {
      	target.setFreeShippingThreshold(posHub.getShippingThresholdForHomeownerOrGuest());
      }
      else if (posHub != null && !isGuestOrHomeowner && posHub.getShippingThresholdForContractor() != null && !posHub.getShippingThresholdForContractor().isEmpty())
      {
      	target.setFreeShippingThreshold(posHub.getShippingThresholdForContractor());
      }
      else if (BooleanUtils.isTrue(dcBranch))
		{
			final Map<String, String> flatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(DC_SHIPPING_THRESHOLD);
			final Map<String, String> hubflatrate = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(SiteoneCoreConstants.HUB_SHIPPING_THRESHOLD);
			if (null != flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
      	{
				target.setFreeShippingThreshold(flatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)));
      	}
			else if (null != hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)))
			{
				target.setFreeShippingThreshold(hubflatrate.get(storeSessionFacade.getSessionStore().getHubStores().get(0)));
			}
		}
		else if (null != siteOneFeatureSwitchCacheService.getValueForSwitch("FreeShippingThreshold"))
		{
			target.setFreeShippingThreshold(siteOneFeatureSwitchCacheService.getValueForSwitch("FreeShippingThreshold"));
		}

		final StockData stockData = target.getStock();

		if (null != stockData && null != stockData.getStockLevel() && stockData.getStockLevel() > Long.valueOf(0) && (!UOMCheck || stockData.getStockLevel() >= multiplier))
		{
			inStockFlag = true;
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " inStockFlag " + inStockFlag
					+ "inventoryHit " + inventoryHit);
		}

		if ((null != stockData && null != stockData.getStockLevel() && stockData.getStockLevel() < multiplier
				&& (null != stockData.getInventoryHit() && stockData.getInventoryHit() < multiplier)))
		{
			inStockFlag = false;
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " inStockFlag " + inStockFlag
					+ "inventoryHit " + inventoryHit);
		}

		if ((null != stockData && null != stockData.getStockLevel() && stockData.getStockLevel() < multiplier)
				&& (null != stockData.getInventoryHit() && stockData.getInventoryHit() > 0 && (!UOMCheck || stockData.getInventoryHit() >= multiplier)))
		{
			inStockFlag = false;
			inventoryHit = true;
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " inStockFlag " + inStockFlag
					+ "inventoryHit " + inventoryHit);

		}

		if (!(inStockFlag || stockData != null && ((null != stockData.getInventoryHit() && stockData.getInventoryHit() > 0 && (!UOMCheck || stockData.getInventoryHit() >= multiplier))))
				&& BooleanUtils.isNotTrue(target.getIsForceInStock())
				&& (null != target.getInventoryCheck() && target.getInventoryCheck()))
		{
			inventoryFlag = true;
		}

		if (!inStockFlag && !inventoryHit)

		{
			disableAddToCart = true;
			isNotBuyable = true;
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " disableAddToCart !inStockFlag");

		}

		if (null != sessionStore && CollectionUtils.isNotEmpty(target.getStores()))
		{
			if (!UOMCheck)
			{
				isMyStoreProduct = target.getStores().stream().anyMatch(store -> store.equalsIgnoreCase(sessionStore.getStoreId()));
			}
			if (!isMyStoreProduct)
			{
				if (((stockData.getIsNearbyStoreStockAvailable() != null && stockData.getIsNearbyStoreStockAvailable())
						|| (stockData != null && stockData.getIsHomeStoreInventoryHit() != null && stockData.getIsHomeStoreInventoryHit())) && !UOMCheck)
				{
					isMyStoreProduct = true;
				}
			}
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " isMyStoreProductFlag "
					+ isMyStoreProduct);
		}


		if (null != sessionStore && target.getIsRegulateditem() && CollectionUtils.isNotEmpty(target.getRegulatoryStates()))
		{
			final PointOfServiceModel pos = siteOneStoreFinderService.getStoreForId(sessionStore.getStoreId());
			isRup = regulatoryStatesCronJobService.getRupBySkuAndState(source.getCode(), pos.getAddress().getRegion());


			for (final String state : target.getRegulatoryStates())
			{
				if (null != sessionStore.getAddress()
						&& state.equalsIgnoreCase(sessionStore.getAddress().getRegion().getIsocodeShort()))
				{
					isProductSellable = true;
					LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " isProductSellableFlag "
							+ isProductSellable);
				}
			}
		}


		if (((null != sessionStore && target.getIsRegulateditem() && isMyStoreProduct
				&& (null != sessionStore && !isProductSellable || (isProductSellable && (isRup && !sessionStore.getIsLicensed()))))
				|| (!isMyStoreProduct)) || !orderingAccount)
		{
			disableAddToCart = true;
			isNotBuyable = true;
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " disableAddToCart" + disableAddToCart
					+ " beacause of (!isMyStoreProduct) or (null Price) or !orderingAccount or !isProductSellable or  !sessionStore.getIsLicensed()");
		}

		if (!target.getMultidimensional())
		{
			if (target.getIsRegulateditem() == null || BooleanUtils.isNotTrue(target.getIsRegulateditem()))
			{
				if (!UOMCheck)
				{
					if (stockData != null && stockData.getStoreName() != null && StringUtils.isNotBlank(stockData.getStoreName()))
					{
						if (stockData.getIsHomeStoreStockAvailable() != null && stockData.getIsHomeStoreStockAvailable())
						{
							setAvailabilityMsg(target, stockData, isMixedCart, true);
							disableAddToCart = false;
						}
						else if (null != stockData.getIsBackorderAndShippable() && stockData.getIsBackorderAndShippable())
						{
							target.setStoreStockAvailabilityMsg(
									getMessageSource().getMessage("product.backorder.and.shippable", new Object[]
									{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
							target.setNotInStockImage(true);
							target.setIsEligibleForBackorder(true);
							target.setIsBackorderAndShippable(true);
							disableAddToCart = false;
						}
						else
						{
							setAvailabilityMsg(target, stockData, isMixedCart, false);
							disableAddToCart = false;
						}
					}
					else
					{

						if (null != sessionStore && null != sessionStore.getAddress()
								&& ((stockData != null && stockData.getIsHomeStoreInventoryHit() != null && stockData.getIsHomeStoreInventoryHit())
										|| (stockData != null && stockData.getIsForceInStock() != null && stockData.getIsForceInStock())))
						{
							if(stockData.getIsForceInStock() != null && stockData.getIsForceInStock())
							{
								if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca")) {
									target.setStoreStockAvailabilityMsg(getMessageSource().getMessage(FORCE_STOCK_MESSAGE, new Object[]
											{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
									target.setStockAvailExtendedMessage(getMessageSource().getMessage(FORCE_STOCK_EXTENDED_MESSAGE_CA,
											new Object[]{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
								}
								else {
									target.setStoreStockAvailabilityMsg(getMessageSource().getMessage(FORCE_STOCK_MESSAGE, new Object[]
											{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
									target.setStockAvailExtendedMessage(getMessageSource().getMessage(FORCE_STOCK_EXTENDED_MESSAGE,
											new Object[]{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
								}
							}
							else
							{
							target.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.outofstock.new", new Object[]
							{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
							target.setStockAvailExtendedMessage(getMessageSource()
									.getMessage("product.outofstock.new.extended.message", null, getI18nService().getCurrentLocale()));
							}
							target.setNotInStockImage(true);
							target.setIsEligibleForBackorder(true);
							disableAddToCart = false;
							if(BooleanUtils.isTrue(target.getIsNurseryProduct()))
							{
								target.setInventoryCheck(Boolean.FALSE);	
							}
							if (null != source.getInventoryCheck() && source.getInventoryCheck()
									&& BooleanUtils.isNotTrue(stockData.getIsForceInStock()) && BooleanUtils.isNotTrue(target.getIsNurseryProduct()))
							{
								disableAddToCart = true;
							}
						}
						else if (stockData != null && null != stockData.getIsBackorderAndShippable() && stockData.getIsBackorderAndShippable())
						{
							target.setStoreStockAvailabilityMsg(
									getMessageSource().getMessage("product.backorder.and.shippable", new Object[]
									{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
							target.setNotInStockImage(true);
							target.setIsEligibleForBackorder(true);
							target.setIsBackorderAndShippable(true);
							disableAddToCart = false;
						}
						else
						{
							if (stockData != null && stockData.getNearestBackorderableStore() != null)
							{
								target.setNearestBackorderableStore(stockData.getNearestBackorderableStore());
								target.setStoreStockAvailabilityMsg(
										getMessageSource().getMessage("product.outofstock.nearbystore.backorder", new Object[]
										{ target.getNearestBackorderableStore().getName() }, getI18nService().getCurrentLocale()));
								disableAddToCart = false;
								target.setIsEligibleForBackorder(true);
								if (null != source.getInventoryCheck() && source.getInventoryCheck()
										&& BooleanUtils.isNotTrue(stockData.getIsForceInStock()))
								{
									disableAddToCart = true;
								}
							}

							else
							{
								if (null != sessionStore && null != sessionStore.getAddress())
								{
									target.setStoreStockAvailabilityMsg(
											getMessageSource().getMessage("product.outofstock.noinventoryhit.new", new Object[]
											{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
									target.setStockAvailExtendedMessage(getMessageSource()
											.getMessage("product.outofstock.noinventoryhit.new.extended.message", new Object[]
											{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
									target.setOutOfStockImage(true);
								}

							}

						}
					}
				}
			}
			else if (target.getIsRegulateditem())
			{

				if (((isMyStoreProduct && (null != sessionStore && !isProductSellable
						|| (isProductSellable && (isRup && !sessionStore.getIsLicensed())))) || (!isMyStoreProduct))
						|| !orderingAccount)
				{
					disableAddToCart = true;
					target.setIsRegulatedAndNotSellable(true);
					target.setIsRegulatoryLicenseCheckFailed(true);
					isNotBuyable = true;
					LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode() + " disableAddToCart"
							+ disableAddToCart
							+ " beacause of (!isMyStoreProduct) or (null Price) or !orderingAccount or !isProductSellable or  !sessionStore.getIsLicensed()");
				}
				if (isMyStoreProduct && isProductSellable && isRup && null != sessionStore && !sessionStore.getIsLicensed()
						&& null != sessionStore.getAddress())
				{
					disableAddToCart = true;
					target.setIsRegulatedAndNotSellable(true);
					target.setIsRegulatoryLicenseCheckFailed(true);
					target.setStoreStockAvailabilityMsg(
							getMessageSource().getMessage("product.regulatedItem.licenseExpired", new Object[]
							{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
					target.setNotInStockImage(true);
					LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode()
							+ " is regulatedItem and notApproved in state and not licensed");
				}
				if (isMyStoreProduct && !isProductSellable && null != sessionStore && null != sessionStore.getAddress())
				{
					disableAddToCart = true;
					target.setIsRegulatedAndNotSellable(true);
					target.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.regulatedItem.notApproved", new Object[]
					{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
					target.setNotInStockImage(true);
					LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode()
							+ " is regulatedItem and notApproved in state");
				}
				if (isMyStoreProduct && isProductSellable
						&& ((isRup && null != sessionStore && sessionStore.getIsLicensed()) || !isRup))
				{
					if (inStockFlag || (stockData != null && stockData.getIsHomeStoreInventoryHit() != null && stockData.getIsHomeStoreInventoryHit()))
					{

						if (stockData.getStoreName() != null && StringUtils.isNotBlank(stockData.getStoreName()))
						{
							if (stockData.getIsHomeStoreStockAvailable() != null && stockData.getIsHomeStoreStockAvailable())
							{
								setAvailabilityMsg(target, stockData, isMixedCart, true);

							}
							else
							{
								setAvailabilityMsg(target, stockData, isMixedCart, false);
							}
						}
						else
						{
							if (null != sessionStore && null != sessionStore.getAddress()
									&& ((stockData != null && stockData.getIsHomeStoreInventoryHit() != null && stockData.getIsHomeStoreInventoryHit())
											|| (stockData != null && stockData.getIsForceInStock() != null && stockData.getIsForceInStock())))
							{
								if(stockData.getIsForceInStock() != null && stockData.getIsForceInStock())
								{
									if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca")) {
										target.setStoreStockAvailabilityMsg(getMessageSource().getMessage(FORCE_STOCK_MESSAGE, new Object[]
												{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
										target.setStockAvailExtendedMessage(getMessageSource().getMessage(FORCE_STOCK_EXTENDED_MESSAGE_CA,
												new Object[]{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
									}
									else {
										target.setStoreStockAvailabilityMsg(getMessageSource().getMessage(FORCE_STOCK_MESSAGE, new Object[]
												{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
										target.setStockAvailExtendedMessage(getMessageSource().getMessage(FORCE_STOCK_EXTENDED_MESSAGE,
												new Object[]{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
									}
								}
								else
								{
								target.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.outofstock.new", new Object[]
								{ sessionStore.getName() }, getI18nService().getCurrentLocale()));
								target.setStockAvailExtendedMessage(getMessageSource()
										.getMessage("product.outofstock.new.extended.message", null, getI18nService().getCurrentLocale()));
								}
								target.setNotInStockImage(true);
								target.setIsEligibleForBackorder(true);
								disableAddToCart = false;
								if(BooleanUtils.isTrue(target.getIsNurseryProduct()))
								{
									target.setInventoryCheck(Boolean.FALSE);	
								}
								if (null != source.getInventoryCheck() && source.getInventoryCheck()
										&& BooleanUtils.isNotTrue(stockData.getIsForceInStock()) && BooleanUtils.isNotTrue(target.getIsNurseryProduct()))
								{
									disableAddToCart = true;
								}
							}
							else if (stockData.getNearestBackorderableStore() != null)
							{
								target.setNearestBackorderableStore(stockData.getNearestBackorderableStore());
								target.setStoreStockAvailabilityMsg(
										getMessageSource().getMessage("product.outofstock.nearbystore.backorder", new Object[]
										{ target.getNearestBackorderableStore().getName() }, getI18nService().getCurrentLocale()));
								disableAddToCart = false;
								target.setIsEligibleForBackorder(true);
								if (null != source.getInventoryCheck() && source.getInventoryCheck()
										&& BooleanUtils.isNotTrue(stockData.getIsForceInStock()))
								{
									disableAddToCart = true;
								}
							}
						}
					}
					else
					{
						if (null != sessionStore && null != sessionStore.getAddress())
						{
							target.setStoreStockAvailabilityMsg(
									getMessageSource().getMessage("product.outofstock.noinventoryhit.new", new Object[]
									{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
							target.setStockAvailExtendedMessage(
									getMessageSource().getMessage("product.outofstock.noinventoryhit.new.extended.message", new Object[]
									{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
							target.setOutOfStockImage(true);
							LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode()
									+ " is regulatedItem and OOS in store");
						}
					}
				}
				else if (!isMyStoreProduct && null != sessionStore && null != sessionStore.getAddress())
				{
					target.setStoreStockAvailabilityMsg(
							getMessageSource().getMessage("product.outofstock.noinventoryhit.new", new Object[]
							{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
					target.setStockAvailExtendedMessage(
							getMessageSource().getMessage("product.outofstock.noinventoryhit.new.extended.message", new Object[]
							{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
					target.setOutOfStockImage(true);
				}
				if (isRup)
				{
					target.setIsRegulatoryLicenseCheckFailed(false);
				}

			}
		}
		if (UOMCheck && sessionStore != null && sessionStore.getAddress() != null)
		{
			target.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.outofstock.noinventoryhit.new", new Object[]
			{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
			target.setStockAvailExtendedMessage(
					getMessageSource().getMessage("product.outofstock.noinventoryhit.new.extended.message", new Object[]
					{ sessionStore.getAddress().getPhone() }, getI18nService().getCurrentLocale()));
			target.setOutOfStockImage(true);
		}
		if (stockData != null && stockData.getFullfillmentStoreId() != null)
		{
			target.setFullfillmentStoreId(stockData.getFullfillmentStoreId());
		}
		if (stockData != null && stockData.getFullfilledStoreType() != null)
		{
			target.setFullfilledStoreType(stockData.getFullfilledStoreType());
		}
		if (stockData != null && stockData.getStockAvailableOnlyHubStore() != null && stockData.getStockAvailableOnlyHubStore())
		{
			target.setStockAvailableOnlyHubStore(stockData.getStockAvailableOnlyHubStore());
		}
		else
		{
			target.setStockAvailableOnlyHubStore(false);
		}
		//Check for Delivery Eligibility
		if (target.getOutOfStockImage() != Boolean.TRUE)
		{
			if ((sessionService.getAttribute("guestUser") != null
					&& sessionService.getAttribute("guestUser").toString().equalsIgnoreCase("guest")))
			{
				LOG.info("Inside Guest User Check");
				if (null != sessionStore && null != sessionStore.getStoreId())
				{
					if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("GuestChekoutEnabledBranches",
							sessionStore.getStoreId()))
					{
						target.setIsEligibleForDelivery(Boolean.TRUE);
					}
					else
					{
						if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryFeeBranches",
								sessionStore.getStoreId()))
						{
							target.setIsEligibleForDelivery(Boolean.TRUE);
						}
						if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("ShippingandDeliveryLAFeeBranches",
								sessionStore.getStoreId()))
						{
							target.setIsEligibleForDelivery(Boolean.TRUE);
						}
					}
				}
			}
			else
			{
				target.setIsEligibleForDelivery(Boolean.TRUE);
			}

		}
		else
		{
			target.setIsEligibleForDelivery(Boolean.FALSE);
		}
		if ((target.getVariantOptions() == null || target.getVariantOptions().isEmpty()) && target.getBaseProduct() != null)
		{
			final ProductModel baseProduct = ((GenericVariantProductModel) source).getBaseProduct();
			target.setIsDeliverable(baseProduct.getIsDeliverable());
		}

		//CheckForZeroPrice
		if ((target.getPrice() == null && target.getCustomerPrice() == null)
				|| ((null != target.getPrice() && target.getPrice().getValue().compareTo(BigDecimal.ZERO) == 0)
						&& (null != target.getCustomerPrice() && target.getCustomerPrice().getValue().compareTo(BigDecimal.ZERO) == 0)))
		{
			disableAddToCart = true;
			LOG.info("ProductSellableAndMessagePopulator ProductCode " + source.getCode()
					+ " is having Zero Price for CSP and retail price");

		}
		target.setStockAvailabilityMessage(stockAvailabilityMessage);
		target.setIsSellable(!disableAddToCart);
		target.setIsBuyable(!isNotBuyable);
		target.setInventoryFlag(inventoryFlag);

		if (!target.getMultidimensional() && isMixedCart)
		{
			populateFulfillmentInfo(target, sessionStore);
		}
		if (!target.getMultidimensional() && isSplitMixedCart)
		{
			populateFulfillmentInfoForSplitMixedCart(target, sessionStore);
		}
		if ((BooleanUtils.isFalse(isTampaBranch) && BooleanUtils.isFalse(isLABranch) || BooleanUtils.isTrue(dcBranch)) && null != source.getIsShippable()
				&& BooleanUtils.isTrue(source.getIsShippable()))
		{
			target.setIsNationalShippingPDP(Boolean.TRUE);
		}
		else
		{
			target.setIsNationalShippingPDP(Boolean.FALSE);
		}		
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

	protected List<String> getCategoriesForProduct(final ProductModel productModel)
	{
		final List<String> categoryCodeList = new ArrayList<>();
		final Collection<CategoryModel> leafLevelCategories = productModel.getSupercategories();
		final CategoryModel l1Category = getPrimaryCategory(leafLevelCategories);

		if (null != l1Category)
		{
			categoryCodeList.add(l1Category.getCode());
			if (!isLevel1Category(l1Category.getSupercategories()))
			{
				final CategoryModel l2Category = getPrimaryCategory(l1Category.getSupercategories());
				categoryCodeList.add(l2Category.getCode());
				if (!isLevel1Category(l2Category.getSupercategories()))
				{
					final CategoryModel l3Category = getPrimaryCategory(l2Category.getSupercategories());
					categoryCodeList.add(l3Category.getCode());
					if (!isLevel1Category(l3Category.getSupercategories()))
					{

						final CategoryModel l4Category = getPrimaryCategory(l3Category.getSupercategories());
						categoryCodeList.add(l4Category.getCode());
					}
				}
			}
		}

		return categoryCodeList;
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
		if(supercategories != null)
			
		{
			for (final CategoryModel category : supercategories)
			{
				if (!(category instanceof ClassificationClassModel) && !(category instanceof VariantCategoryModel)
						&& !(category instanceof VariantValueCategoryModel) && category.getCode().startsWith("SH"))
				{
					return category;
				}
			}
		}
		return null;
	}

	private void populateFulfillmentInfoForSplitMixedCart(final ProductData productData, final PointOfServiceData sessionStore)
	{
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade
				.populateStoreLevelStockInfoData(productData.getCode(), true);
		final StoreLevelStockInfoData pickupHomeStoreInfo = new StoreLevelStockInfoData();
		boolean outOfStock = false;

		if (!CollectionUtils.isEmpty(storeLevelStockInfoDataList))
		{
			if (StringUtils.isNotBlank(storeLevelStockInfoDataList.get(0).getStoreId()) && null != sessionStore
					&& StringUtils.isNotBlank(sessionStore.getStoreId())
					&& storeLevelStockInfoDataList.get(0).getStoreId().equalsIgnoreCase(sessionStore.getStoreId()))
			{
				pickupHomeStoreInfo.setStoreName(storeLevelStockInfoDataList.get(0).getStoreName());
				pickupHomeStoreInfo.setStoreId(storeLevelStockInfoDataList.get(0).getStoreId());
				pickupHomeStoreInfo.setIsEnabled(Boolean.TRUE);
				pickupHomeStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(0).getOnHandQuantity());
			}
			else
			{
				if (null != sessionStore)
				{
					pickupHomeStoreInfo
							.setStoreName(!StringUtils.isBlank(sessionStore.getDisplayName()) ? sessionStore.getDisplayName()
									: sessionStore.getName());
					pickupHomeStoreInfo.setStoreId(sessionStore.getStoreId());
				}
				pickupHomeStoreInfo.setIsEnabled(Boolean.TRUE);
				pickupHomeStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(0).getOnHandQuantity());
			}
		}
		else
		{
			outOfStock = true;
			pickupHomeStoreInfo.setStoreName(
					StringUtils.isNotBlank(sessionStore.getDisplayName()) ? sessionStore.getDisplayName() : sessionStore.getName());
			pickupHomeStoreInfo.setStoreId(sessionStore.getStoreId());
			pickupHomeStoreInfo.setIsEnabled(Boolean.FALSE);
			pickupHomeStoreInfo.setOnHandQuantity(0);
		}
		productData.setPickupHomeStoreInfo(pickupHomeStoreInfo);
		if ((!outOfStock || BooleanUtils.isTrue(productData.getStockAvailableOnlyHubStore()))
				&& BooleanUtils.isTrue(productData.getIsShippable()))
		{
			final StoreLevelStockInfoData shippingStoreInfo = siteOneProductFacade
					.populateHubStoresStockInfoData(productData.getCode(), true, productData.getIsForceInStock());
			if (null != shippingStoreInfo)
			{
				shippingStoreInfo.setIsEnabled(Boolean.TRUE);
				productData.setShippingStoreInfo(shippingStoreInfo);
			}
		}
	}
	
	protected void populateFulfillmentInfo(final ProductData productData, final PointOfServiceData sessionStore)
	{
		final List<StoreLevelStockInfoData> storeLevelStockInfoDataList = siteOneProductFacade
				.populateStoreLevelStockInfoData(productData.getCode(), true);
		final StoreLevelStockInfoData pickupHomeStoreInfo = new StoreLevelStockInfoData();
		final StoreLevelStockInfoData pickupNearbyStoreInfo = new StoreLevelStockInfoData();
		final StoreLevelStockInfoData deliveryStoreInfo = new StoreLevelStockInfoData();
		boolean inStockInHB = false;
		boolean inStockInNB = false;
		boolean inStockInNBOnly = false;
		boolean isBackorderHome = false;
		boolean isBackorderNearby = false;
		boolean outOfStock = false;		
		final ProductModel productModel = getProductService().getProductForCode(productData.getCode());

		if (!CollectionUtils.isEmpty(storeLevelStockInfoDataList))
		{
			if (StringUtils.isNotBlank(storeLevelStockInfoDataList.get(0).getStoreId()) && null != sessionStore
					&& StringUtils.isNotBlank(sessionStore.getStoreId())
					&& storeLevelStockInfoDataList.get(0).getStoreId().equalsIgnoreCase(sessionStore.getStoreId()))
			{
				pickupHomeStoreInfo.setStoreName(storeLevelStockInfoDataList.get(0).getStoreName());
				pickupHomeStoreInfo.setStoreId(storeLevelStockInfoDataList.get(0).getStoreId());
				pickupHomeStoreInfo.setIsEnabled(Boolean.TRUE);
				pickupHomeStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(0).getOnHandQuantity());
				if (storeLevelStockInfoDataList.get(0).getStockAvailablityMessage()
						.equalsIgnoreCase(getMessageSource().getMessage("product.instock.homestore.latest", new Object[]
						{ storeLevelStockInfoDataList.get(0).getOnHandQuantity(), storeLevelStockInfoDataList.get(0).getStoreName() },
								getI18nService().getCurrentLocale())))
				{
					pickupHomeStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("pickup.homestore.info1", new Object[]
					{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
							getI18nService().getCurrentLocale()));
					inStockInHB = true;
				}
				else
				{
					isBackorderHome = true;
					pickupHomeStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("pickup.homestore.info2", new Object[]
					{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
							getI18nService().getCurrentLocale()));
				}
				if (storeLevelStockInfoDataList.size() > 1 && null != storeLevelStockInfoDataList.get(1))
				{
					pickupNearbyStoreInfo.setStoreName(storeLevelStockInfoDataList.get(1).getStoreName());
					pickupNearbyStoreInfo.setStoreId(storeLevelStockInfoDataList.get(1).getStoreId());
					pickupNearbyStoreInfo.setIsEnabled(Boolean.TRUE);
					pickupNearbyStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(1).getOnHandQuantity());
					if (storeLevelStockInfoDataList.get(1).getStockAvailablityMessage()
							.equalsIgnoreCase(getMessageSource().getMessage("product.instock.nearbystore.latest", new Object[]
							{ storeLevelStockInfoDataList.get(1).getOnHandQuantity(),
									storeLevelStockInfoDataList.get(1).getStoreName() }, getI18nService().getCurrentLocale())))
					{
						pickupNearbyStoreInfo
								.setStockAvailablityMessage(getMessageSource().getMessage("pickup.nearby.info1", new Object[]
								{ storeLevelStockInfoDataList.get(1).getStoreName(), storeLevelStockInfoDataList.get(1).getStoreId() },
										getI18nService().getCurrentLocale()));
						inStockInNB = true;
					}
					else
					{
						pickupNearbyStoreInfo
								.setStockAvailablityMessage(getMessageSource().getMessage("pickup.nearby.info2", new Object[]
								{ storeLevelStockInfoDataList.get(1).getStoreName(), storeLevelStockInfoDataList.get(1).getStoreId() },
										getI18nService().getCurrentLocale()));
					}
				}
				else
				{
					pickupNearbyStoreInfo.setIsEnabled(Boolean.FALSE);
					pickupNearbyStoreInfo.setOnHandQuantity(0);
					pickupNearbyStoreInfo.setStockAvailablityMessage(
							getMessageSource().getMessage("pickup.nearby.unavailable.info", null, getI18nService().getCurrentLocale()));
				}
			}
			else
			{
				if (null != sessionStore)
				{
					pickupHomeStoreInfo
							.setStoreName(!StringUtils.isBlank(sessionStore.getDisplayName()) ? sessionStore.getDisplayName()
									: sessionStore.getName());
					pickupHomeStoreInfo.setStoreId(sessionStore.getStoreId());
				}
				pickupHomeStoreInfo.setIsEnabled(Boolean.FALSE);
				pickupHomeStoreInfo.setOnHandQuantity(0);
				pickupHomeStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("pickup.unavailable.info", new Object[]
				{ pickupHomeStoreInfo.getStoreName() }, getI18nService().getCurrentLocale()));

				pickupNearbyStoreInfo.setStoreName(storeLevelStockInfoDataList.get(0).getStoreName());
				pickupNearbyStoreInfo.setStoreId(storeLevelStockInfoDataList.get(0).getStoreId());
				pickupNearbyStoreInfo.setIsEnabled(Boolean.TRUE);
				pickupNearbyStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(0).getOnHandQuantity());
				if (storeLevelStockInfoDataList.get(0).getStockAvailablityMessage()
						.equalsIgnoreCase(getMessageSource().getMessage("product.instock.nearbystore.latest", new Object[]
						{ storeLevelStockInfoDataList.get(0).getOnHandQuantity(), storeLevelStockInfoDataList.get(0).getStoreName() },
								getI18nService().getCurrentLocale())))
				{
					pickupNearbyStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("pickup.nearby.info1", new Object[]
					{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
							getI18nService().getCurrentLocale()));
					inStockInNBOnly = true;
				}
				else
				{
					pickupNearbyStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("pickup.nearby.info2", new Object[]
					{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
							getI18nService().getCurrentLocale()));
					isBackorderNearby = true;
				}
			}

			deliveryStoreInfo.setStoreName(storeLevelStockInfoDataList.get(0).getStoreName());
			deliveryStoreInfo.setStoreId(storeLevelStockInfoDataList.get(0).getStoreId());
			deliveryStoreInfo.setIsEnabled(Boolean.TRUE);
			deliveryStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(0).getOnHandQuantity());
			if (storeLevelStockInfoDataList.get(0).getStockAvailablityMessage()
					.equalsIgnoreCase(getMessageSource().getMessage("product.instock.homestore.latest", new Object[]
					{ storeLevelStockInfoDataList.get(0).getOnHandQuantity(), storeLevelStockInfoDataList.get(0).getStoreName() },
							getI18nService().getCurrentLocale())))
			{
				deliveryStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage(DELIVERY_INFO, new Object[]
				{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
						getI18nService().getCurrentLocale()));
			}
			else if (storeLevelStockInfoDataList.get(0).getStockAvailablityMessage()
					.equalsIgnoreCase(getMessageSource().getMessage("product.instock.nearbystore.latest", new Object[]
					{ storeLevelStockInfoDataList.get(0).getOnHandQuantity(), storeLevelStockInfoDataList.get(0).getStoreName() },
							getI18nService().getCurrentLocale())))
			{
				deliveryStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage(DELIVERY_INFO, new Object[]
				{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
						getI18nService().getCurrentLocale()));
			}
			else
			{
				deliveryStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("delivery.info2", new Object[]
				{ storeLevelStockInfoDataList.get(0).getStoreName(), storeLevelStockInfoDataList.get(0).getStoreId() },
						getI18nService().getCurrentLocale()));
			}
			if (!inStockInHB && inStockInNB)
			{
				if (storeLevelStockInfoDataList.size() > 1 && null != storeLevelStockInfoDataList.get(1))
				{
					deliveryStoreInfo.setStoreName(storeLevelStockInfoDataList.get(1).getStoreName());
					deliveryStoreInfo.setStoreId(storeLevelStockInfoDataList.get(1).getStoreId());
					deliveryStoreInfo.setIsEnabled(Boolean.TRUE);
					deliveryStoreInfo.setOnHandQuantity(storeLevelStockInfoDataList.get(1).getOnHandQuantity());
					if (storeLevelStockInfoDataList.get(1).getStockAvailablityMessage()
							.equalsIgnoreCase(getMessageSource().getMessage("product.instock.nearbystore.latest", new Object[]
							{ storeLevelStockInfoDataList.get(1).getOnHandQuantity(),
									storeLevelStockInfoDataList.get(1).getStoreName() }, getI18nService().getCurrentLocale())))
					{
						deliveryStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage(DELIVERY_INFO, new Object[]
						{ storeLevelStockInfoDataList.get(1).getStoreName(), storeLevelStockInfoDataList.get(1).getStoreId() },
								getI18nService().getCurrentLocale()));
					}
					else
					{
						deliveryStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("delivery.info2", new Object[]
						{ storeLevelStockInfoDataList.get(1).getStoreName(), storeLevelStockInfoDataList.get(1).getStoreId() },
								getI18nService().getCurrentLocale()));
					}
				}
			}
		}
		else
		{
			outOfStock = true;
			pickupHomeStoreInfo.setStoreName(
					StringUtils.isNotBlank(sessionStore.getDisplayName()) ? sessionStore.getDisplayName() : sessionStore.getName());
			pickupHomeStoreInfo.setStoreId(sessionStore.getStoreId());
			pickupHomeStoreInfo.setIsEnabled(Boolean.FALSE);
			pickupHomeStoreInfo.setOnHandQuantity(0);
			pickupHomeStoreInfo.setStockAvailablityMessage(getMessageSource().getMessage("pickup.unavailable.info", new Object[]
			{ pickupHomeStoreInfo.getStoreName() }, getI18nService().getCurrentLocale()));
			pickupNearbyStoreInfo.setIsEnabled(Boolean.FALSE);
			pickupNearbyStoreInfo.setOnHandQuantity(0);
			pickupNearbyStoreInfo.setStockAvailablityMessage(
					getMessageSource().getMessage("pickup.nearby.unavailable.info", null, getI18nService().getCurrentLocale()));
			deliveryStoreInfo.setIsEnabled(Boolean.FALSE);
			deliveryStoreInfo.setOnHandQuantity(0);
			deliveryStoreInfo.setStockAvailablityMessage(
					getMessageSource().getMessage("delivery.unavailable", null, getI18nService().getCurrentLocale()));
		}
		if (productData.getIsDeliverable() == Boolean.FALSE)
		{
			deliveryStoreInfo.setIsEnabled(Boolean.FALSE);
			deliveryStoreInfo.setOnHandQuantity(0);
			deliveryStoreInfo.setStockAvailablityMessage(
					getMessageSource().getMessage("delivery.unavailable", null, getI18nService().getCurrentLocale()));
		}
		if (inStockInHB || (isBackorderHome && !inStockInNB)
				|| (!inStockInHB && !inStockInNB && !isBackorderNearby && !inStockInNBOnly))
		{
			pickupHomeStoreInfo.setShowInPDP(true);
			pickupNearbyStoreInfo.setShowInPDP(false);
		}
		else
		{
			pickupHomeStoreInfo.setShowInPDP(false);
			pickupNearbyStoreInfo.setShowInPDP(true);
		}

		productData.setPickupHomeStoreInfo(pickupHomeStoreInfo);
		productData.setPickupNearbyStoreInfo(pickupNearbyStoreInfo);
		productData.setDeliveryStoreInfo(deliveryStoreInfo);
		if ((!outOfStock || BooleanUtils.isTrue(productData.getStockAvailableOnlyHubStore()))
				&& BooleanUtils.isTrue(productData.getIsShippable()))
		{
			final StoreLevelStockInfoData shippingStoreInfo = siteOneProductFacade
					.populateHubStoresStockInfoData(productData.getCode(), true, productData.getIsForceInStock());
			if (null != shippingStoreInfo)
			{
				shippingStoreInfo.setIsEnabled(Boolean.TRUE);
				productData.setShippingStoreInfo(shippingStoreInfo);
			}
		}
	}

	protected void setAvailabilityMsg(final ProductData productData, final StockData stockData, final boolean isMixedCart,
			final boolean isHomeStore)
	{
		if (isMixedCart)
		{
			if (isHomeStore)
			{
				productData
						.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.instock.homestore.new.mixed", new Object[]
						{ stockData.getStockLevel(), stockData.getStoreName() }, getI18nService().getCurrentLocale()));
			}
			else
			{
				productData.setStoreStockAvailabilityMsg(
						getMessageSource().getMessage("product.instock.nearbystore.new.mixed", new Object[]
						{ stockData.getStockLevel(), stockData.getStoreName() }, getI18nService().getCurrentLocale()));
			}

		}
		else
		{
			if (isHomeStore)
			{
				productData.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.instock.homestore.new", new Object[]
				{ stockData.getStoreName() }, getI18nService().getCurrentLocale()));
			}
			else
			{
				productData.setStoreStockAvailabilityMsg(getMessageSource().getMessage("product.instock.nearbystore.new", new Object[]
				{ stockData.getStoreName() }, getI18nService().getCurrentLocale()));
			}
		}

		productData.setInStockImage(true);
		if (!isHomeStore)
		{
			if (!isMixedCart)
			{
				if (BooleanUtils.isFalse(productData.getIsTransferrable()))
				{
					productData.setStockAvailExtendedMessage(
							getMessageSource().getMessage("product.instock.nearbystore.nontransfer.extended.message",null, getI18nService().getCurrentLocale()));
				}
				else
				{
					productData.setStockAvailExtendedMessage(getMessageSource()
							.getMessage("product.instock.nearbystore.new.extended.message", null, getI18nService().getCurrentLocale()));
				}
			}
			productData.setIsStockInNearbyBranch(true);
		}
	}
}
