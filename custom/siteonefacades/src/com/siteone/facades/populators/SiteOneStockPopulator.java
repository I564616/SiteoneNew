/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.basecommerce.enums.StockLevelStatus;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.converters.populator.StockPopulator;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.impl.GeometryUtils;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.GenericVariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facade.StoreLevelStockInfoData;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.integration.constants.SiteoneintegrationConstants;


/**
 * @author 1091124
 *
 */
public class SiteOneStockPopulator extends StockPopulator<ProductModel, StockData>
{

	public static final double IMPERIAL_DISTANCE_RATIO = 0.62137;

	private StockService stockService;
	private CommerceStockService commerceStockService;
	private SessionService sessionService;
	private SiteOneStoreFinderService siteOneStoreFinderService;
	private SiteOneStoreSessionFacade storeSessionFacade;
	private SiteOneStockLevelService siteOneStockLevelService;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;
	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Override
	protected CommerceStockService getCommerceStockService()
	{
		return commerceStockService;
	}

	@Override
	public void setCommerceStockService(final CommerceStockService commerceStockService)
	{
		this.commerceStockService = commerceStockService;
	}

	@Override
	public void populate(final ProductModel productModel, final StockData stockData) throws ConversionException
	{
		final PointOfServiceData sessionPOS = ((PointOfServiceData) getSessionService().getAttribute("sessionStore"));
		boolean stockAvailable = false;
		boolean nearByStockAvailable = false;
		boolean shippingStockAvailable = false;
        boolean isHomeStoreBackOrder = false;
		boolean isHomeStoreStockAvailable = false;
		boolean isNearByStoreSelected = false;
		boolean isForceInStockBackOrder = false;
		boolean isForceStockRespectNearby = false;
		boolean isNurseryCoreSKU = false;
		boolean isNurseryBackOrderLogicOn = false;
		
		boolean segmentLevelShippingEnabled = false;
		if((sessionService.getAttribute("segmentLevelShippingEnabled") != null))
		{
			segmentLevelShippingEnabled = (boolean) getSessionService().getAttribute("segmentLevelShippingEnabled");
		}
		else
		{
			segmentLevelShippingEnabled = true;
		}
		
		if(siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.NURSERY_BACKORDER_LOGIC) != null
				&& siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.NURSERY_BACKORDER_LOGIC).equalsIgnoreCase("on"))
		{
			isNurseryBackOrderLogicOn = true;
		}

		int homeStoreInventoryHit = 0;
		boolean UOMCheck = false;
		float multiplier = 0.0f;
		boolean forceInStock = false;

		if (CollectionUtils.isNotEmpty(productModel.getUpcData()))
		{
			final Collection<InventoryUPCModel> count = productModel.getUpcData().stream()
					.filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline())).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(count) && count.size() <= 1)
			{
				for (final InventoryUPCModel upcData : productModel.getUpcData())
				{

					if (BooleanUtils.isNotTrue(upcData.getBaseUPC()) && BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
					{

						UOMCheck = true;
						multiplier = upcData.getInventoryMultiplier();

					}
				}
			}
		}

		if (BooleanUtils.isNotTrue(productModel.getInventoryCheck()))
		{
			forceInStock = true;
		}
		
		final ProductModel product;
      if (productModel instanceof GenericVariantProductModel) {
  	    product = ((GenericVariantProductModel) productModel).getBaseProduct();
      }
      else 
      {
     	 product = productModel;
      }
      
      List<CategoryModel> categoryModels = product.getSupercategories().stream().filter(cat ->
         cat.getCode().startsWith("S") && cat.getCode().length() == SiteoneintegrationConstants.LENGTH_LEVEL3_CATEGORY).collect(Collectors.toList());
      CategoryModel categoryModel=null;
      List<CategoryModel> categoryModelList = product.getSupercategories().stream().filter(cat ->
         cat.getCode().startsWith("S")).collect(Collectors.toList());
      if(categoryModelList != null && !CollectionUtils.isEmpty(categoryModelList) 
      		&& (categoryModels == null || CollectionUtils.isEmpty(categoryModels)))
      {
      	Collection<CategoryModel> categories = categoryModelList.get(0).getAllSupercategories();
      	List<CategoryModel> superCategoryModels = categories.stream().filter(cat ->
      	  cat.getCode().startsWith("S") && cat.getCode().length() == SiteoneintegrationConstants.LENGTH_LEVEL3_CATEGORY).collect(Collectors.toList());
      	if(CollectionUtils.isNotEmpty(superCategoryModels)) 
      	{
      		categoryModel = superCategoryModels.get(0);
      	}
      	else
      	{
      		categoryModel = categoryModelList.get(0);
      	}
      }
      else if(categoryModels != null && !CollectionUtils.isEmpty(categoryModels))
      {
      	categoryModel = categoryModels.get(0);
      }
      boolean isTransferrable = true;
      if ((categoryModel != null && categoryModel.getIsTransferrableCategory() != null && 
      		BooleanUtils.isNotTrue(categoryModel.getIsTransferrableCategory())) || 
      		(productModel.getIsTransferrable() != null && BooleanUtils.isNotTrue(productModel.getIsTransferrable()))) {
        isTransferrable = false;
      }
      
      boolean isNurseryProduct = false;
      boolean isHardscapeProduct = false;
      if(categoryModel != null && categoryModel.getCode().startsWith("SH16"))
      {
      	isNurseryProduct = true;
      }
      
      else if(categoryModel != null && categoryModel.getCode().startsWith("SH15"))
      {
      	isHardscapeProduct = true;
      }
    	
		List<PointOfServiceData> nearByStores;
		if(isNurseryProduct && sessionPOS != null && sessionPOS.getNurseryBuyingGroup() != null)
		{
			nearByStores = storeSessionFacade.getNurseryNearbyBranchesFromSession();
		}
		else
		{
			nearByStores = storeSessionFacade.getNearbyStoresFromSession();
		}

		List<List<Object>> stockLevelData = null;
		if (CollectionUtils.isNotEmpty(nearByStores))
		{

			if (CollectionUtils.isNotEmpty(nearByStores))
			{
				stockLevelData = siteOneStockLevelService.getStockLevelsForNearByStores(productModel.getCode(), nearByStores);
			}
			
			for(final List<Object> stockLevelRowData : stockLevelData)
			{
				if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0))
						&& sessionPOS.getStoreId() != null
						&& ((String) stockLevelRowData.get(0)).equalsIgnoreCase(sessionPOS.getStoreId()) && stockLevelRowData.get(7) != null)
				{
					isForceStockRespectNearby = (boolean) stockLevelRowData.get(7);
				}
			}
			
			for (final PointOfServiceData nearByStorerecord : nearByStores)
			{
				
				if ((stockAvailable && !isForceInStockBackOrder) || nearByStockAvailable || (!isForceStockRespectNearby && isForceInStockBackOrder)
						|| isHomeStoreBackOrder)
				{
					break;
				}
				for (final List<Object> stockLevelRowData : stockLevelData)
				{
					if (stockLevelRowData.get(5) != null && BooleanUtils.isTrue((Boolean) stockLevelRowData.get(5))
							&& (forceInStock || (BooleanUtils.isNotTrue((Boolean) stockLevelRowData.get(6)))))
					{
						forceInStock = true;
					}
					else
					{
						forceInStock = false;
					}

					isNearByStoreSelected = false;
					if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0))
							&& sessionPOS.getStoreId() != null
							&& ((String) stockLevelRowData.get(0)).equalsIgnoreCase(sessionPOS.getStoreId()))
					{
						isNearByStoreSelected = true;
					}
					else if (nearByStorerecord.getStoreId().equals(stockLevelRowData.get(0))
							&& nearByStorerecord.getIsNearbyStoreSelected() != null && nearByStorerecord.getIsNearbyStoreSelected())
					{
						isNearByStoreSelected = true;
					}
					if (isNearByStoreSelected)
					{
						stockData.setStoreId((String) stockLevelRowData.get(0));
					}

					if (nearByStorerecord.getStoreId().equals(stockLevelRowData.get(0)) && isNearByStoreSelected)
					{
						if (stockLevelRowData.get(4) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(4)))
						{
							stockData.setStoreName((String) stockLevelRowData.get(4));
						}
						else
						{
							stockData.setStoreName((String) stockLevelRowData.get(3));
						}
						if (stockLevelRowData.get(1) == null)
						{
							stockData.setInventoryHit(0);
						}
						else
						{
							stockData.setInventoryHit((int) stockLevelRowData.get(1));
						}
						if ((stockLevelRowData.get(2) != null && (int) stockLevelRowData.get(2) > 0
								&& (!UOMCheck || (int) stockLevelRowData.get(2) >= multiplier)))
						{
							stockData.setStockLevel(Long.valueOf((int) stockLevelRowData.get(2)));
							stockAvailable = true;
							stockData.setStockLevelStatus(StockLevelStatus.INSTOCK);
							if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0))
									&& sessionPOS.getStoreId() != null
									&& ((String) stockLevelRowData.get(0)).equalsIgnoreCase(sessionPOS.getStoreId()))
							{
								isHomeStoreStockAvailable = true;
								stockData.setFullfillmentStoreId((String) stockLevelRowData.get(0));
								stockData.setFullfilledStoreType("HomeStore");
								
								if (isHardscapeProduct && forceInStock) 
								{
								  stockData.setIsForceInStock(true);
								}
							}
							
							
							else
							{
								stockData.setIsNearbyStoreStockAvailable(true);
								stockData.setFullfillmentStoreId((String) stockLevelRowData.get(0));
								stockData.setFullfilledStoreType("NearByStore");
								nearByStockAvailable = true;
							}
							stockData.setIsHomeStoreStockAvailable(isHomeStoreStockAvailable);
							if (isHomeStoreStockAvailable && stockData.getInventoryHit() != null
									&& stockData.getInventoryHit() > 0 && (!UOMCheck || stockData.getInventoryHit() >= multiplier))
							{
								stockData.setIsHomeStoreInventoryHit(true);
							}
							else
							{
								stockData.setIsHomeStoreInventoryHit(false);
							}
							break;
						}
						else if (forceInStock)
						{
							if(sessionPOS.getStoreId() != null
										&& ((String) stockLevelRowData.get(0)).equalsIgnoreCase(sessionPOS.getStoreId()))
							{
   							stockData.setIsForceInStock(forceInStock);
   							stockData.setStoreName(null);
   							stockData.setIsHomeStoreInventoryHit(true);
   							stockAvailable = true;
   							isForceInStockBackOrder = true;
							}
						}
						else
						{
							stockData.setStockLevel(Long.valueOf(0));
							if ((String) stockLevelRowData.get(0) != null && StringUtils.isNotBlank((String) stockLevelRowData.get(0))
									&& sessionPOS.getStoreId() != null
									&& ((String) stockLevelRowData.get(0)).equalsIgnoreCase(sessionPOS.getStoreId()))
							{
								if (stockData.getInventoryHit() != null && stockData.getInventoryHit() > 0 && (!UOMCheck || stockData.getInventoryHit() >= multiplier))
								{
									homeStoreInventoryHit = stockData.getInventoryHit();
									stockData.setIsHomeStoreInventoryHit(true);
									if(stockLevelRowData.get(8) != null && BooleanUtils.isTrue((Boolean) stockLevelRowData.get(8)))
									{
										isNurseryCoreSKU = true;							
									}
									if((!isTransferrable && !isNurseryProduct) || (isNurseryProduct && isNurseryCoreSKU && isNurseryBackOrderLogicOn && !isTransferrable))
									{
										isHomeStoreBackOrder = true;
									}
								}
								else
								{
									stockData.setIsHomeStoreInventoryHit(false);
								}
								stockData.setIsHomeStoreStockAvailable(false);
							}
						}
					}
				}

			}
		}
		else if (null != sessionPOS)
		{
			final PointOfServiceModel sessionStore = getSiteOneStoreFinderService().getStoreForId(sessionPOS.getStoreId());
			stockData.setStockLevel(getCommerceStockService().getStockLevelForProductAndPointOfService(productModel, sessionStore));
			stockData.setStoreId(sessionPOS.getStoreId());
			if (stockData.getStockLevel() > Long.valueOf(0) && (!UOMCheck || stockData.getStockLevel() >= multiplier))
			{
				stockAvailable = true;
				stockData.setIsHomeStoreStockAvailable(true);
				stockData.setFullfillmentStoreId(sessionPOS.getStoreId());
				stockData.setFullfilledStoreType("HomeStore");
				if (sessionPOS.getDisplayName() != null && StringUtils.isNotBlank(sessionPOS.getDisplayName()))
				{
					stockData.setStoreName(sessionPOS.getDisplayName());
				}
				else
				{
					stockData.setStoreName(sessionPOS.getName());
				}
			}
			else
			{
				stockData.setIsHomeStoreStockAvailable(false);
			}
			stockData.setStockLevelStatus(
					getCommerceStockService().getStockLevelStatusForProductAndPointOfService(productModel, sessionStore));
			final Collection<StockLevelModel> stockLevelList = getStockService().getStockLevels(productModel,
					sessionStore.getWarehouses());
			if (CollectionUtils.isNotEmpty(stockLevelList))
			{
				stockData.setInventoryHit(((List<StockLevelModel>) stockLevelList).get(0).getInventoryHit());
			}
			if (stockData.getInventoryHit() != null && stockData.getInventoryHit() > 0 && (!UOMCheck || stockData.getInventoryHit() >= multiplier))
			{
				stockData.setIsHomeStoreInventoryHit(true);
			}
			else
			{
				stockData.setIsHomeStoreInventoryHit(false);
			}
		}
		if (productModel.getIsShippable() != null && productModel.getIsShippable() && BooleanUtils.isTrue(segmentLevelShippingEnabled) && !isHomeStoreStockAvailable
				&& sessionPOS != null && !nearByStockAvailable && (!isForceInStockBackOrder || (isForceInStockBackOrder && isForceStockRespectNearby)))
		{
			final List<String> hubStoreList = sessionPOS.getHubStores();
			if (CollectionUtils.isNotEmpty(hubStoreList))
			{
				final StoreLevelStockInfoData storeLevelStockInfoData = siteOneProductFacade
						.populateHubStoresStockInfoData(productModel.getCode(), true, forceInStock);
				if (storeLevelStockInfoData != null && storeLevelStockInfoData.getStoreId() != null)
				{
					final String hubstore = storeLevelStockInfoData.getStoreId();
					if (!(sessionPOS.getStoreId()).equalsIgnoreCase(hubstore))
					{
						final List<String> products = new ArrayList<>();
						products.add(productModel.getCode());
						final List<List<Object>> stockLevelDataList = siteOneStockLevelService
								.getStockLevelsForHubStoresForProducts(products, hubstore);
						if (CollectionUtils.isNotEmpty(stockLevelDataList))
						{
							for (final List<Object> stockLevelData1 : stockLevelDataList)
							{
								if ((stockLevelData1 != null && stockLevelData1.get(5) != null)
										&& ((stockLevelData1.get(2) != null && Long.valueOf(stockLevelData1.get(2).toString()) > 0 &&
										      (!UOMCheck || Long.valueOf(stockLevelData1.get(2).toString()) >= multiplier))
												|| (stockLevelData1.get(6) != null && BooleanUtils.isTrue((Boolean) stockLevelData1.get(6))
														&& (forceInStock || (BooleanUtils.isNotTrue((Boolean) stockLevelData1.get(7)))))))
								{
									if ((null == stockData.getFullfilledStoreType()) || (null != stockData.getFullfilledStoreType())
											&& (!stockData.getFullfilledStoreType().equalsIgnoreCase("NearByStore")))
									{
										stockAvailable = true;
										shippingStockAvailable = true;
										stockData.setStockLevel(Long.valueOf(stockLevelData1.get(2).toString()));
										stockData.setStockAvailableOnlyHubStore(true);
										stockData.setIsHomeStoreStockAvailable(true);
										stockData.setFullfillmentStoreId(hubstore);
										stockData.setFullfilledStoreType("HubStore");
										final PointOfServiceData hubStorePointOfServiceData = ((SiteOneStoreFinderFacade) storeFinderFacade)
												.getStoreForId(hubstore);
										if (hubStorePointOfServiceData.getDisplayName() != null
												&& StringUtils.isNotBlank(hubStorePointOfServiceData.getDisplayName()))
										{
											stockData.setStoreName(hubStorePointOfServiceData.getDisplayName());
										}
										else
										{
											stockData.setStoreName(hubStorePointOfServiceData.getName());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (null != sessionPOS && !stockAvailable)
		{
			stockData.setStockLevel(Long.valueOf(0));
			stockData.setStockLevelStatus(StockLevelStatus.OUTOFSTOCK);
			stockData.setInventoryHit(homeStoreInventoryHit);
			stockData.setStoreName("");
			stockData.setIsHomeStoreStockAvailable(false);
			if (homeStoreInventoryHit > 0 && (!UOMCheck || homeStoreInventoryHit >= multiplier) &&
					(!isNurseryProduct || (isNurseryProduct && isNurseryCoreSKU && isNurseryBackOrderLogicOn)))
			{
				stockData.setIsHomeStoreInventoryHit(true);
				stockData.setFullfillmentStoreId(sessionPOS.getStoreId());
				stockData.setFullfilledStoreType("InventoryHit");
			}
			else if(null != sessionPOS && CollectionUtils.isNotEmpty(sessionPOS.getDistributedBranches())
					   && siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P2) != null
					   && siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P2).equalsIgnoreCase("on"))
			{			
				Map<Boolean, Integer> stockAvailability = siteOneStockLevelService.isDcStockAvailable(productModel.getCode(),sessionPOS.getDistributedBranches(),multiplier);
					if(BooleanUtils.isTrue(stockAvailability.containsKey(true)))
					{
						stockData.setIsHomeStoreInventoryHit(true);
						stockData.setInventoryHit((int) stockAvailability.get(true));
						stockData.setDcBranchStock(Boolean.TRUE);
					}
			}
			else
			{
				stockData.setIsHomeStoreInventoryHit(false);
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						sessionPOS.getStoreId()))
				{
					stockData.setNearestBackorderableStore(populateBackorderNearByStores(stockLevelData, sessionPOS));
					if (null != stockData.getNearestBackorderableStore())
					{
						stockData.setFullfillmentStoreId(stockData.getNearestBackorderableStore().getStoreId());
						stockData.setFullfilledStoreType("InventoryHit");
					}
				}
			}
		}
		if(isForceInStockBackOrder && !nearByStockAvailable && !shippingStockAvailable)
		{
			stockData.setStoreName(null);
			stockData.setIsHomeStoreInventoryHit(true);
		}
		if(shippingStockAvailable && isForceInStockBackOrder)
		{
			stockData.setIsForceInStock(false);
		}
	}

	public PointOfServiceData populateBackorderNearByStores(final List<List<Object>> stockLevelData,
			final PointOfServiceData sessionPOS)
	{

		final List<PointOfServiceData> nearbyBranchList = new ArrayList<>();
		List<PointOfServiceData> nearByStores = null;

		PointOfServiceData nearbyBranch = null;

		if (null != storeSessionFacade.getSessionStore())
		{

			nearByStores = storeSessionFacade.getNearbyStoresFromSession();
		}


		if (CollectionUtils.isNotEmpty(nearByStores))
		{
			for (final List<Object> stockLevelDataList : stockLevelData)
			{

				if ((String) stockLevelDataList.get(0) != null && StringUtils.isNotBlank((String) stockLevelDataList.get(0))
						&& sessionPOS.getStoreId() != null && !((String) stockLevelDataList.get(0)).equals(sessionPOS.getStoreId()))
				{
					if (stockLevelDataList.get(2) != null && (int) stockLevelDataList.get(2) <= 0 && stockLevelDataList.get(1) != null
							&& (int) stockLevelDataList.get(1) > 4)
					{
						nearbyBranch = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId((String) stockLevelDataList.get(0));

						final GPS homeStoreGPS = new DefaultGPS(sessionPOS.getGeoPoint().getLatitude(),
								sessionPOS.getGeoPoint().getLongitude());
						final GPS currentStoreGPS = new DefaultGPS(nearbyBranch.getGeoPoint().getLatitude(),
								nearbyBranch.getGeoPoint().getLongitude());
						final double distanceInKM = Double.valueOf(GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS));
						final double distanceInMiles = (Double
								.valueOf(GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS))) * IMPERIAL_DISTANCE_RATIO;
						if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
							nearbyBranch.setDistance(distanceInMiles);
						}
						else {
							nearbyBranch.setDistance(distanceInKM);
						}

					}
					if (nearbyBranch != null)
					{
						nearbyBranchList.add(nearbyBranch);
					}

				}

			}

			if (CollectionUtils.isNotEmpty(nearbyBranchList))
			{
				nearbyBranchList.sort(Comparator.comparingDouble(PointOfServiceData::getDistance));
				nearbyBranch = nearbyBranchList.get(0);
			}
		}
		return nearbyBranch;
	}

	@Override
	protected boolean isStockSystemEnabled()
	{
		return getCommerceStockService().isStockSystemEnabled(getBaseStoreService().getCurrentBaseStore());
	}

	@Override
	protected boolean isStockSystemEnabled(final BaseStoreModel baseStore)
	{
		return getCommerceStockService().isStockSystemEnabled(baseStore);
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}

	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}

	/**
	 * @return the stockService
	 */
	public StockService getStockService()
	{
		return stockService;
	}

	/**
	 * @param stockService
	 *           the stockService to set
	 */
	public void setStockService(final StockService stockService)
	{
		this.stockService = stockService;
	}

	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	public SiteOneStockLevelService getSiteOneStockLevelService()
	{
		return siteOneStockLevelService;
	}

	public void setSiteOneStockLevelService(final SiteOneStockLevelService siteOneStockLevelService)
	{
		this.siteOneStockLevelService = siteOneStockLevelService;
	}
}
