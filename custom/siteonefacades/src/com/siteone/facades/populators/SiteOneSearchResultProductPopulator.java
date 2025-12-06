/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.acceleratorfacades.order.data.PriceRangeData;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.entity.SolrPriceRange;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.session.SessionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.customer.impl.DefaultSiteOneCustomerAccountService;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;



/**
 * @author 1229803
 *
 */
public class SiteOneSearchResultProductPopulator extends SearchResultVariantProductPopulator
{

	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "customerAccountService")
	private DefaultSiteOneCustomerAccountService customerAccountService;

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "siteOneProductUOMService")
	private SiteOneProductUOMService siteOneProductUOMService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	private static final String IS_SELLABLE = "isSellable";

	private static final String HARDSCAPE_PRODUCT = "CH01_15";

	private static final String NURSERY_PRODUCT = "CH01_16";
	
	private static final Logger LOG = Logger.getLogger(SiteOneSearchResultProductPopulator.class);

	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		super.populate(source, target);
		//storeSessionFacade.setGeolocatedSessionStore();
		
		Boolean isDeliverable = this.<Boolean> getValue(source, "soisDeliverable");
		String deliveryAlwaysEnabledBranches = this.<String> getValue(source, "sodeliveryAlwaysEnabledBranches");
		if(BooleanUtils.isNotTrue(isDeliverable) && deliveryAlwaysEnabledBranches != null &&
				ArrayUtils.contains(deliveryAlwaysEnabledBranches.trim().split(","),storeSessionFacade.getSessionStore().getStoreId()))
		{
			target.setIsDeliverable(true);
		}
		else
		{
         target.setIsDeliverable(isDeliverable);
		}

		target.setCode(this.<String> getValue(source, "socode"));
		target.setName(this.<String> getValue(source, "soname"));
		target.setManufacturer(this.<String> getValue(source, "somanufacturerName"));
		target.setProductShortDesc(this.<String> getValue(source, "soproductShortDesc"));
		target.setSummary(this.<String> getValue(source, "sosummary"));
		target.setVariantSkus(this.<List> getValue(source, "soproductsSku"));
		target.setVariantCount(this.<Integer> getValue(source, "sovariantCount"));
		target.setProductBrandName(this.<String> getValue(source, "soproductBrandName"));
		target.setStores(this.<List> getValue(source, "soavailableInStores"));
		target.setIsRegulateditem(this.<Boolean> getValue(source, "soisRegulateditem"));
		target.setIsProductDiscontinued(this.<Boolean> getValue(source, "soisProductDiscontinued"));
		target.setRegulatoryStates(this.<List> getValue(source, "soregulatoryStates"));
		target.setProductPromotion(this.<String> getValue(source, "sopromotion"));
		target.setItemNumber(this.<String> getValue(source, "soitemNumber"));
		target.setSellableUomsCount(this.<Integer> getValue(source, "sosellableUom"));
		target.setSellableUomDesc(this.<String> getValue(source, "soinventoryUom"));
		target.setHideList(this.<Boolean> getValue(source, "sohideList"));
		target.setHideCSP(this.<Boolean> getValue(source, "sohideCSP"));
		target.setInventoryCheck(this.<Boolean> getValue(source, "soinventoryCheck"));
		target.setCouponEnabled(this.<Boolean> getValue(source, "socouponEnabled"));
		target.setOrderQuantityInterval(this.<Integer> getValue(source, "soOrderQuantityInterval"));
		target.setEeee(this.<Boolean> getValue(source, "soeeee"));
		target.setBaseProduct(this.<String> getValue(source, "sobaseProductCode"));
		target.setVariantCodes(this.<List> getValue(source, "sovariantsku"));
		target.setVariantProductOptions(this.<String> getValue(source,"sovariantOption"));	

		final boolean soisTransferrable = this.<Boolean>getValue(source, "soisTransferrable") != null ? this.<Boolean>getValue(source, "soisTransferrable") : true;
		final boolean isTransferrableCategory = this.<Boolean>getValue(source, "soisTransferrableCategory") != null ? this.<Boolean>getValue(source, "soisTransferrableCategory") : true;
		target.setIsTransferrable(soisTransferrable && isTransferrableCategory); 

		final List<String> categoryList = this.<List> getValue(source, "socategory");
		final List<String> categoryNameList = this.<List> getValue(source, "socategoryName");
		if (StringUtils.isNotEmpty(sessionService.getAttribute("assignedProducts")))
		{
			final String productCodes = sessionService.getAttribute("assignedProducts");
			final List<String> productList = Arrays.asList(productCodes.split(","));
			final boolean isAvailable = productList.stream().anyMatch(productCode -> productCode.equalsIgnoreCase(target.getCode()));
			if (isAvailable)
			{
				target.setIsSellableForB2BUnit(Boolean.FALSE);
			}
			else
			{
				target.setIsSellableForB2BUnit(Boolean.TRUE);
			}

		}
		else
		{
			target.setIsSellableForB2BUnit(Boolean.TRUE);
		}

		if (CollectionUtils.isNotEmpty(categoryNameList))
		{
			categoryNameList.remove("Primary");
		}

		if (CollectionUtils.isNotEmpty(categoryList))
		{
			final List<CategoryData> categoryDataList = new ArrayList<>();
			categoryList.forEach(category -> {
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("HardscapeCategories", category))
				{
					target.setAskAnExpertEnable(Boolean.TRUE);
				}
				final CategoryData categoryData = new CategoryData();
				categoryData.setCode(category);
				if (CollectionUtils.isNotEmpty(categoryNameList))
				{
					categoryData.setName(categoryNameList.get(categoryList.indexOf(category)));
				}
				categoryDataList.add(categoryData);
			});
			Collections.sort(categoryDataList, (o1, o2) -> o1.getCode().length() - o2.getCode().length());
			target.setCategories(categoryDataList);

			target.setIsNurseryProduct(Boolean.FALSE);
			for (final CategoryData categoryData : categoryDataList)
			{
				if (categoryData.getCode().contains("SH16"))
				{
					target.setIsNurseryProduct(Boolean.TRUE);
					break;
				}
			}
			target.setIsHardscapeProduct(Boolean.FALSE);
			final CategoryData hardscape = categoryDataList.stream().filter(cData ->cData.getCode().contains("SH15")).findFirst().orElse(null);
			if(hardscape != null) {
				target.setIsHardscapeProduct(Boolean.TRUE);
			}
		}

		boolean UOMCheck = false;
		float multiplier = 1.0f;
		final List<InventoryUPCModel> inventoryUOM = siteOneProductUOMService.getSellableUOMsById(target.getCode());
		if (CollectionUtils.isNotEmpty(inventoryUOM))
		{
			final Collection<InventoryUPCModel> count = inventoryUOM.stream()
					.filter(upcData -> BooleanUtils.isNotTrue(upcData.getHideUPCOnline())).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(count) && count.size() <= 1)
			{
				for (final InventoryUPCModel upcData : inventoryUOM)
				{
					if (BooleanUtils.isNotTrue(upcData.getBaseUPC()) && BooleanUtils.isNotTrue(upcData.getHideUPCOnline()))
					{
						UOMCheck = true;
						multiplier = upcData.getInventoryMultiplier();
					}
				}
			}
		}
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		List<Boolean> forceStockFlags = new ArrayList();
		boolean hubStoreForceInStock = false;
		if(homeStore != null && homeStore.getHubStores() != null)
		{
			forceStockFlags = siteOneStockLevelService.isForceInStock(target.getCode(), homeStore.getStoreId(), homeStore.getHubStores().get(0), multiplier);
			hubStoreForceInStock = forceStockFlags.get(4);
		}
		else
		{
			forceStockFlags = siteOneStockLevelService.isForceInStock(target.getCode(), homeStore.getStoreId(), null, multiplier);
		}
		final boolean homeStoreForceInStock = forceStockFlags.get(0);
		final boolean forceStockRespectNearby = forceStockFlags.get(1);
		final boolean isNurseryBOLogicEnabled = siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.NURSERY_BACKORDER_LOGIC) != null
				&& siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.NURSERY_BACKORDER_LOGIC).equalsIgnoreCase("on") && forceStockFlags.get(3);
		final boolean isHomeStoreBackOrder = BooleanUtils.isNotTrue(target.getIsNurseryProduct()) ? 
				forceStockFlags.get(2) : (forceStockFlags.get(2) && isNurseryBOLogicEnabled);
		target.setIsStockAvailable(populateAvailabilityFlags(source, target, "soIsStockAvailable", homeStoreForceInStock, hubStoreForceInStock));
		target.setSostockForNLA(populateAvailabilityFlags(source, target, "sostockForNLA", homeStoreForceInStock, hubStoreForceInStock));
		target.setIsShippable(Boolean.FALSE);
		target.setIsSellable(Boolean.FALSE);
		
		if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("QuantityMinimumBranches",
				homeStore.getStoreId()) && null == target.getOrderQuantityInterval())
		{
			target.setMinOrderQuantity(this.<Integer> getValue(source, "soMinOrderQuantity"));
		}		
		Boolean sellableInHomeBranchForForceInStock = Boolean.FALSE;
		if (!UOMCheck || BooleanUtils.isTrue(target.getIsStockAvailable()) || homeStoreForceInStock || isHomeStoreBackOrder)
		{			
			final Boolean homeStockAvailable = (Boolean) source.getValues()
					.get("soIsStockAvailable" + "_" + homeStore.getStoreId().toLowerCase() + "_boolean");
			final Boolean sellableInHomeBranch = this.<Boolean> getValue(source, IS_SELLABLE) != null?
					  this.<Boolean> getValue(source, IS_SELLABLE) && (isHomeStoreBackOrder || homeStoreForceInStock) : this.<Boolean> getValue(source, IS_SELLABLE);
			if (homeStockAvailable != null && !homeStockAvailable && sellableInHomeBranch != null && sellableInHomeBranch &&
					target.getIsTransferrable() != null && (!target.getIsTransferrable()
						|| (homeStoreForceInStock && !forceStockRespectNearby)))
			{
				if(homeStoreForceInStock && !forceStockRespectNearby)
				{
					target.setIsForceInStock(Boolean.TRUE);
				}
					target.setNearestStore(null);
					target.setIsStockAvailable(null);
			}
			else
			{
				target.setNearestStore(getNearestStore(source, "soIsStockAvailable",target));
			}

			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				if (sellableInHomeBranch != null && sellableInHomeBranch)
				{
					target.setNearestBackorderableStore(storeSessionFacade.getSessionStore());
					target.setIsSellableInventoryHit(sellableInHomeBranch);
				}
				else
				{
					target.setNearestBackorderableStore(getNearestStore(source, IS_SELLABLE,target));
					target.setIsSellableInventoryHit(populateSellableFlags(source, target, IS_SELLABLE));
				}

			}
			else if(homeStoreForceInStock && forceStockRespectNearby)
			{
				sellableInHomeBranchForForceInStock = sellableInHomeBranch;
				target.setIsSellableInventoryHit(Boolean.FALSE);
			}
			else
			{
				target.setIsSellableInventoryHit(sellableInHomeBranch);
			}

			if (target.getNearestStore() != null)
			{
				target.setNearestStoreStockLevel(getNearestStoreStockLevel(source, target.getNearestStore(), "soStockAvailable"));
			}


			if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
					storeSessionFacade.getSessionStore().getStoreId()))
			{
				target.setIsShippable(populateIsShippableAvailabilityFlag(source, target, "soIsStockAvailable", IS_SELLABLE, homeStoreForceInStock));
			}
			else
			{
				target.setIsShippable(populateIsShippableAvailabilityFlag(source, target, "soIsStockAvailable", homeStoreForceInStock, forceStockRespectNearby, isNurseryBOLogicEnabled, hubStoreForceInStock));
			}
			if(BooleanUtils.isTrue(target.getIsShippable()) && target.getNearestStore()==null)
			{
				target.setNearestStoreStockLevel(getNearestStoreStockLevel(source, homeStore, "soStockAvailable"));
			}
			
		}

		if (source.getValues() != null)
		{
			target.setMultidimensional((Boolean) source.getValues().get("so" + MULTIDIMENSIONAL));
		}

		if (target.getSellableUomsCount() != null)
		{
			final int sellableUOMCount = target.getSellableUomsCount().intValue();
			if (sellableUOMCount > 0)
			{
				final String inventoryUOMID = this.<String> getValue(source, "soinventoryUomId");
				if (inventoryUOMID != null)
				{
					final List<InventoryUOMData> inventoryUOMList = populateSellableUoms(inventoryUOMID, target.getCode(), target);
					if (CollectionUtils.isNotEmpty(inventoryUOMList))
					{
						target.setSellableUoms(inventoryUOMList);
						siteOneProductFacade.getProductByUOMPLPPriceRange(target);
					}

				}
			}
		}
		if (target.getNearestStore() == null && homeStoreForceInStock && forceStockRespectNearby && BooleanUtils.isNotTrue(target.getIsShippable()))
		{
			target.setIsForceInStock(Boolean.TRUE);
			target.setIsStockAvailable(null);
			target.setIsEligibleForBackorder(Boolean.TRUE);
			target.setIsSellableInventoryHit(sellableInHomeBranchForForceInStock);
		}
		if (target.getNearestStore() != null && homeStoreForceInStock && forceStockRespectNearby)
		{
			target.setIsSellableInventoryHit(sellableInHomeBranchForForceInStock);
		}
		if (BooleanUtils.isNotTrue(target.getIsStockAvailable()) && BooleanUtils.isNotTrue(target.getIsEligibleForBackorder()) && BooleanUtils.isNotTrue(target.getIsSellableInventoryHit())
				&& target.getNearestStore() == null && siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P2) != null
				&& siteOneFeatureSwitchCacheService.getValueForSwitch(SiteoneCoreConstants.ENABLE_BO_LOGIC_P2).equalsIgnoreCase("on"))
		{
			Boolean distributedBranchAvailability = populateDistributedBranchesAvailable(source, "soIsStockAvailable");
			target.setIsEligibleForBackorder(distributedBranchAvailability);
			target.setIsSellableInventoryHit(distributedBranchAvailability);
		}
		target.setInventoryFlag(populateInventoryFlags(source, target, "soInventoryFlag"));
		if (BooleanUtils.isTrue(target.getIsEligibleForBackorder()) && BooleanUtils.isTrue(target.getInventoryCheck())
				&& (BooleanUtils.isTrue(target.getIsForceInStock()) || (BooleanUtils.isTrue(target.getIsNurseryProduct()) && isNurseryBOLogicEnabled)))
		{
			target.setInventoryCheck(Boolean.FALSE);
		}

	}
	
	private Boolean populateDistributedBranchesAvailable(final SearchResultValueData source,
			final String availabilityAttributeName)
	{
	    final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
	    List<String> distributedBranches = new ArrayList<>();
	    if(homeStore != null)
	    {
	   	 distributedBranches = homeStore.getDistributedBranches();
	    }
	    if (CollectionUtils.isNotEmpty(distributedBranches))
	    {
	   	Boolean available = false;

	 		for (final String store : distributedBranches)
	 		{
	 			available = (Boolean) source.getValues()
	 						.get(availabilityAttributeName + "_" + store + "_boolean");

	 			if (BooleanUtils.isTrue(available))
	 			{
               break;
	 			}
	 		}
	 		
         return available;
	    }
	    
	    return false;
	}

	private Boolean populateInventoryFlags(final SearchResultValueData source, final ProductData target,
			final String inventoryFlagAttributeName)
	{
		final List<PointOfServiceData> stores = new ArrayList<>();
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		if(BooleanUtils.isTrue(target.getIsNurseryProduct()) && homeStore != null && homeStore.getNurseryBuyingGroup() != null)
		{
			stores.addAll(storeSessionFacade.getNurseryNearbyBranchesFromSession());
		}
		else
		{
			stores.addAll(storeSessionFacade.getNearbyStoresFromSession());		
		}
		Boolean inventoryFlag = Boolean.FALSE;
		if (CollectionUtils.isNotEmpty(stores))
		{
			for (final PointOfServiceData store : stores)
			{
				if (store.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId())
						|| (store.getIsNearbyStoreSelected() != null && store.getIsNearbyStoreSelected()))
				{
					inventoryFlag = (Boolean) source.getValues()
							.get(inventoryFlagAttributeName + "_" + store.getStoreId().toLowerCase() + "_boolean");

					break;
				}
			}
			if (inventoryFlag == null)
			{
				boolean instock = true;
				for (final PointOfServiceData store : stores)
				{

					if (!BooleanUtils.isTrue((Boolean) source.getValues()
							.get("soIsStockAvailable" + "_" + store.getStoreId().toLowerCase() + "_boolean")))
					{
						instock = false;
					}
					else if (null != homeStore && homeStore.getStoreId().equalsIgnoreCase(store.getStoreId())
							&& this.<Boolean> getValue(source, IS_SELLABLE) != null)
					{
						instock = this.<Boolean> getValue(source, IS_SELLABLE);
					}
					else if (!BooleanUtils
							.isTrue((Boolean) source.getValues().get(IS_SELLABLE + "_" + store.getStoreId().toLowerCase() + "_boolean")))
					{
						instock = false;
					}
					else
					{
						instock = true;
						break;
					}
				}
				if (!instock && BooleanUtils.isTrue(target.getInventoryCheck()))
				{
					inventoryFlag = Boolean.TRUE;
				}
				else if (instock)
				{
					inventoryFlag = Boolean.FALSE;
				}
				if (null != target.getIsEligibleForBackorder() && target.getIsEligibleForBackorder().booleanValue())
				{
					inventoryFlag = Boolean.FALSE;
				}
			}
		}
		return inventoryFlag;
	}

	private Boolean populateSellableFlags(final SearchResultValueData source, final ProductData target,
			final String sellableFlagAttributeName)
	{
		final List<PointOfServiceData> stores = new ArrayList<>();
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		if(BooleanUtils.isTrue(target.getIsNurseryProduct()) && homeStore != null && homeStore.getNurseryBuyingGroup() != null)
		{
			stores.addAll(storeSessionFacade.getNurseryNearbyBranchesFromSession());
		}
		else
		{
			stores.addAll(storeSessionFacade.getNearbyStoresFromSession());		
		}
		Boolean sellableFlag = Boolean.FALSE;
		if (CollectionUtils.isNotEmpty(stores))
		{
			for (final PointOfServiceData store : stores)
			{
				if (store.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId())
						|| (store.getIsNearbyStoreSelected() != null && store.getIsNearbyStoreSelected()))
				{
					sellableFlag = (Boolean) source.getValues()
							.get(sellableFlagAttributeName + "_" + store.getStoreId().toLowerCase() + "_boolean");

					if (sellableFlag != null && sellableFlag)
					{

						break;
					}
				}
			}
		}
		return sellableFlag;
	}


	private Boolean populateAvailabilityFlags(final SearchResultValueData source, final ProductData target,
			final String availabilityAttributeName, final boolean isHomeStoreForceInStock, final boolean isHubStoreForceInStock)
	{
		final List<PointOfServiceData> stores = new ArrayList<>();
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		if(BooleanUtils.isTrue(target.getIsNurseryProduct()) && homeStore != null && homeStore.getNurseryBuyingGroup() != null)
		{
			stores.addAll(storeSessionFacade.getNurseryNearbyBranchesFromSession());
		}
		else
		{
			stores.addAll(storeSessionFacade.getNearbyStoresFromSession());		
		}
		List<String> hubStoreList = new ArrayList<String>();
		if (null != storeSessionFacade.getSessionStore())
		{
			hubStoreList = storeSessionFacade.getSessionStore().getHubStores();
		}
		boolean segmentLevelShippingEnabled = false;
		if(sessionService.getAttribute("segmentLevelShippingEnabled") != null)
		{
			segmentLevelShippingEnabled = (boolean) sessionService.getAttribute("segmentLevelShippingEnabled");
		}
		else
		{
			segmentLevelShippingEnabled = true;
		}
		Boolean isShippable = false;
		if(this.<Boolean> getValue(source, "soisShippable") != null)
		{
			isShippable = this.<Boolean> getValue(source, "soisShippable") && segmentLevelShippingEnabled;
		}
		if (CollectionUtils.isNotEmpty(hubStoreList) && (null != isShippable && isShippable))
		{
			final String hubstoreId = hubStoreList.get(0);
			final PointOfServiceData hubStore = new PointOfServiceData();
			hubStore.setStoreId(hubstoreId);
			hubStore.setIsNearbyStoreSelected(true);
			stores.add(hubStore);
		}

		Boolean available = false;

		for (final PointOfServiceData store : stores)
		{
			if ((null != storeSessionFacade.getSessionStore()
					&& store.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId()))
					|| (store.getIsNearbyStoreSelected() != null && store.getIsNearbyStoreSelected()))
			{

				available = (Boolean) source.getValues()
						.get(availabilityAttributeName + "_" + store.getStoreId().toLowerCase() + "_boolean");
		
				if (isHomeStoreForceInStock && BooleanUtils.isTrue(available) && BooleanUtils.isTrue(target.getIsHardscapeProduct()))
				{
					target.setIsForceInStock(Boolean.TRUE);
				}					

				if ((isHubStoreForceInStock || isHomeStoreForceInStock) && BooleanUtils.isTrue(isShippable) && BooleanUtils.isNotTrue(available))

				{
					available = Boolean.TRUE;
					if(isHomeStoreForceInStock && store.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId()))
					{
						target.setIsForceInStock(Boolean.TRUE);						
					}
				}

				if (BooleanUtils.isNotTrue(isShippable) && BooleanUtils.isNotTrue(available))

				{
					available = Boolean.FALSE;
					if (isHomeStoreForceInStock && store.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId()))
					{
						target.setIsForceInStock(Boolean.TRUE);
					}
				}

				if (BooleanUtils.isTrue(available))
				{

					break;
				}
			}

		}

		return available;

	}

	private Boolean populateIsShippableAvailabilityFlag(final SearchResultValueData source, final ProductData target,
			final String availabilityAttributeName, final boolean isHomeStoreForceInStock, final boolean isForceStockRespectNearby, final boolean isNurseryBOLogicEnabled,
			final boolean isHubStoreForceInStock)
	{
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		boolean segmentLevelShippingEnabled = false;
		if(sessionService.getAttribute("segmentLevelShippingEnabled") != null)
		{
			segmentLevelShippingEnabled = (boolean) sessionService.getAttribute("segmentLevelShippingEnabled");
		}
		else
		{
			segmentLevelShippingEnabled = true;
		}
		Boolean isShippable = false;
		if(this.<Boolean> getValue(source, "soisShippable") != null)
		{
			isShippable = this.<Boolean> getValue(source, "soisShippable") && segmentLevelShippingEnabled;
		}
		Boolean available = false;
		Boolean homeStockAvailable = false;
		Boolean backOrderEligible = false;
		Boolean availableInHubStore = null;
		if (homeStore != null && homeStore.getStoreId() != null)
		{

			homeStockAvailable = (Boolean) source.getValues()
					.get(availabilityAttributeName + "_" + homeStore.getStoreId().toLowerCase() + "_boolean");

			if (homeStockAvailable == null)
			{
				target.setIsSellable(false);
			}
			else
			{
				target.setIsSellable(homeStockAvailable);
			}
			if (target.getIsSellable() != null && !target.getIsSellable())
			{
				backOrderEligible = (target.getIsSellableInventoryHit() != null && target.getIsSellableInventoryHit()
						|| (isHomeStoreForceInStock && !isForceStockRespectNearby));
				if(BooleanUtils.isNotTrue(target.getIsNurseryProduct()) || (BooleanUtils.isTrue(target.getIsNurseryProduct()) && isNurseryBOLogicEnabled))
				{
					target.setIsEligibleForBackorder(backOrderEligible);
				}
				else
				{
					target.setIsEligibleForBackorder(false);
				}
				if (isHomeStoreForceInStock && !isForceStockRespectNearby)
				{
					target.setIsEligibleForBackorder(backOrderEligible);
					target.setIsForceInStock(Boolean.TRUE);
				}
			}
			if (CollectionUtils.isNotEmpty(homeStore.getHubStores()))
			{
				availableInHubStore = (Boolean) source.getValues()
						.get(availabilityAttributeName + "_" + homeStore.getHubStores().get(0).toLowerCase() + "_boolean");

				if (BooleanUtils.isTrue(isHubStoreForceInStock))
				{
					availableInHubStore = Boolean.TRUE;
				}
			}
			if (availableInHubStore == null && target.getIsSellable() != null && !target.getIsSellable())
			{
				target.setIsSellable(false);
			}
			else
			{
				target.setIsSellable(availableInHubStore);
			}
		}

		if (homeStore != null && CollectionUtils.isNotEmpty(homeStore.getHubStores()) && isShippable != null && isShippable)
		{

			final Boolean forceInStock = isHubStoreForceInStock;
			available = homeStore.getHubStores().stream().anyMatch(storeId -> {
				Boolean shippable = false;
				Boolean inStockHubFlag = false;

				if (storeId != null)
				{			
					inStockHubFlag = (Boolean) source.getValues()
							.get(availabilityAttributeName + "_" + storeId.toLowerCase() + "_boolean");
					if(BooleanUtils.isTrue(forceInStock))
					{
						inStockHubFlag = Boolean.TRUE;
					}
					if (target.getIsSellable() != null && !target.getIsSellable())
					{
						if (target.getNearestStore() != null && inStockHubFlag != null && inStockHubFlag)
						{
							shippable = true;
						}
						else
						{
							if ((target.getIsEligibleForBackorder() != null && target.getIsEligibleForBackorder())
									&& inStockHubFlag != null && inStockHubFlag)
							{
								shippable = true;
							}
							else
							{
								shippable = false;
							}
						}

					}
					else if (inStockHubFlag != null && inStockHubFlag)
					{
						shippable = true;
					}

				}
				if(BooleanUtils.isTrue(shippable) && BooleanUtils.isNotTrue(isHomeStoreForceInStock))
				{
					target.setIsEligibleForBackorder(false);
					target.setIsSellableInventoryHit(false);
				}
				return shippable;
			});

		}

		return available;
	}

	private Boolean populateIsShippableAvailabilityFlag(final SearchResultValueData source, final ProductData target,
			final String availabilityAttributeName, final String inventoryHitAttributeName, final boolean isHomeStoreForceInStock)
	{
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		boolean segmentLevelShippingEnabled = false;
		if(sessionService.getAttribute("segmentLevelShippingEnabled") != null)
		{
			segmentLevelShippingEnabled = (boolean) sessionService.getAttribute("segmentLevelShippingEnabled");
		}
		else
		{
			segmentLevelShippingEnabled = true;
		}
		Boolean isShippable = false;
		if(this.<Boolean> getValue(source, "soisShippable") != null)
		{
			isShippable = this.<Boolean> getValue(source, "soisShippable") && segmentLevelShippingEnabled;
		}
		final Boolean available = false;
		Boolean homeStockAvailable = false;
		Boolean backOrderEligible = false;
		Boolean shippable = false;

		if (homeStore != null && homeStore.getStoreId() != null)
		{

			homeStockAvailable = (Boolean) source.getValues()
					.get(availabilityAttributeName + "_" + homeStore.getStoreId().toLowerCase() + "_boolean");


			if (homeStockAvailable == null)
			{
				target.setIsSellable(false);
			}
			else
			{
				target.setIsSellable(homeStockAvailable);
			}
			if (target.getIsSellable() != null && !target.getIsSellable())
			{
				backOrderEligible = (target.getIsSellableInventoryHit() != null && target.getIsSellableInventoryHit()
						|| isHomeStoreForceInStock);
				target.setIsEligibleForBackorder(backOrderEligible);
			}
			if (BooleanUtils.isTrue(isShippable) && (target.getIsSellable() || target.getIsEligibleForBackorder()))
			{
				final List<PointOfServiceData> stores = new ArrayList<>();
				if(BooleanUtils.isTrue(target.getIsNurseryProduct()) && homeStore.getNurseryBuyingGroup() != null)
				{
					stores.addAll(storeSessionFacade.getNurseryNearbyBranchesFromSession());
				}
				else
				{
					stores.addAll(storeSessionFacade.getNearbyStoresFromSession());		
				}
				final List<PointOfServiceData> hubStoreList = new ArrayList<>();
				for (final PointOfServiceData pos : stores)
				{
					final PointOfServiceData hub = storeFinderFacade.getHubStoreBranch(pos.getStoreId());
					if (null != hub)
					{
						if (BooleanUtils.isTrue((Boolean) source.getValues()
								.get(availabilityAttributeName + "_" + hub.getStoreId().toLowerCase() + "_boolean")))
						{
							shippable = true;
						}
						else if (null != homeStore && homeStore.getStoreId().equalsIgnoreCase(hub.getStoreId()))
						{
							shippable = this.<Boolean> getValue(source, inventoryHitAttributeName);
						}
						else if (BooleanUtils.isTrue((Boolean) source.getValues()
								.get(inventoryHitAttributeName + "_" + hub.getStoreId().toLowerCase() + "_boolean")))
						{
							shippable = true;
						}
						if (BooleanUtils.isTrue(shippable))
						{
							break;
						}
					}
				}
			}
		}
		return shippable;
	}

	private PointOfServiceData getNearestStore(final SearchResultValueData source, final String availabilityAttributeName, final ProductData target)
	{
		final List<PointOfServiceData> stores = new ArrayList<>();
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		if(BooleanUtils.isTrue(target.getIsNurseryProduct()) && homeStore != null && homeStore.getNurseryBuyingGroup() != null)
		{
			stores.addAll(storeSessionFacade.getNurseryNearbyBranchesFromSession());
		}
		else
		{
			stores.addAll(storeSessionFacade.getNearbyStoresFromSession());		
		}

		PointOfServiceData nearestStore = null;
		Boolean available = false;
		for (final PointOfServiceData store : stores)
		{
			if (store.getStoreId().equals(storeSessionFacade.getSessionStore().getStoreId())
					|| (store.getIsNearbyStoreSelected() != null && store.getIsNearbyStoreSelected()))
			{
				available = (Boolean) source.getValues()
						.get(availabilityAttributeName + "_" + store.getStoreId().toLowerCase() + "_boolean");
				if (available != null && available)
				{
					nearestStore = store;
					break;
				}
			}
		}

		return nearestStore;
	}

	private Long getNearestStoreStockLevel(final SearchResultValueData source, final PointOfServiceData nearestStore,
			final String stockLevelAttributeName)
	{
		 Long stockLevel = (Long) source.getValues()
				.get(stockLevelAttributeName + "_" + nearestStore.getStoreId().toLowerCase() + "_long");
		 
		 if(stockLevel==null) {
			 List<String> hubStores = new ArrayList<>();
			 if (storeSessionFacade.getSessionStore()!=null)
				{
					hubStores = storeSessionFacade.getSessionStore().getHubStores();
				}
			 if (CollectionUtils.isNotEmpty(hubStores))
				{
					final String hubstoreId = hubStores.get(0);
					 stockLevel = (Long) source.getValues().get(stockLevelAttributeName+ "_"+ hubstoreId.toLowerCase()+ "_long");
				 }
			 
		 }
		return stockLevel;
	}

	private PointOfServiceData getNearestStoreBasedOnStock(final SearchResultValueData source,
			final String availabilityAttributeName, final String sellableFlagAttributeName, final ProductData target)
	{
		final List<PointOfServiceData> stores = new ArrayList<>();
		final PointOfServiceData homeStore = storeSessionFacade.getSessionStore();
		if(BooleanUtils.isTrue(target.getIsNurseryProduct()) && homeStore != null && homeStore.getNurseryBuyingGroup() != null)
		{
			stores.addAll(storeSessionFacade.getNurseryNearbyBranchesFromSession());
		}
		else
		{
			stores.addAll(storeSessionFacade.getNearbyStoresFromSession());		
		}

		PointOfServiceData nearestStore = null;
		Boolean available = false;
		Boolean sellableFlag = false;
		final Boolean sellableInHomeBranch = this.<Boolean> getValue(source, sellableFlagAttributeName);
		for (final PointOfServiceData store : stores)
		{
			if (store.getStoreId().equals(homeStore.getStoreId())
					|| (store.getIsNearbyStoreSelected() != null && store.getIsNearbyStoreSelected()))
			{
				available = (Boolean) source.getValues()
						.get(availabilityAttributeName + "_" + store.getStoreId().toLowerCase() + "_boolean");
				if (available != null && available)
				{
					nearestStore = store;
					break;
				}
			}

		}
		if (nearestStore == null)
		{
			if (Boolean.TRUE.equals(sellableInHomeBranch))
			{
				nearestStore = homeStore;
			}
			else
			{
				for (final PointOfServiceData store : stores)
				{
					sellableFlag = (Boolean) source.getValues()
							.get(sellableFlagAttributeName + "_" + store.getStoreId().toLowerCase() + "_boolean");

					if (sellableFlag != null && sellableFlag)
					{
						nearestStore = store;
						break;
					}
				}
			}
		}
		return nearestStore;
	}

	@Override
	protected void setPriceRange(final SearchResultValueData source, final ProductData target)
	{
		if (!(null != storeSessionFacade.getSessionStore() && siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
				storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId())))
		{
			final PriceRangeData priceRange = new PriceRangeData();
			if (null != storeSessionFacade.getSessionStore())
			{
				String priceRangeValue = null;
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()))
				{
					PointOfServiceData nearestStore = getNearestStoreBasedOnStock(source, "soIsStockAvailable", IS_SELLABLE, target);
					if (nearestStore == null)
					{
						nearestStore = storeSessionFacade.getSessionStore();
					}
					priceRangeValue = (String) source.getValues()
							.get(PRICE_RANGE + "_" + nearestStore.getStoreId().toLowerCase() + "_string");
				}
				else
				{
					final String productCode = this.<String> getValue(source, "socode");
					final ProductModel productModel = productService.getProductForCode(productCode);
					if (CollectionUtils.isNotEmpty(productModel.getClassificationClasses()))
					{
						if (productModel.getClassificationClasses().get(0).getCode().startsWith(HARDSCAPE_PRODUCT)
								|| productModel.getClassificationClasses().get(0).getCode().startsWith(NURSERY_PRODUCT))
						{
							PointOfServiceData nearestStore = getNearestStoreBasedOnStock(source, "soIsStockAvailable", IS_SELLABLE, target);
							if (nearestStore == null)
							{
								nearestStore = storeSessionFacade.getSessionStore();
							}
							priceRangeValue = (String) source.getValues()
									.get(PRICE_RANGE + "_" + nearestStore.getStoreId().toLowerCase() + "_string");
						}
						else
						{
							priceRangeValue = (String) source.getValues()
									.get(PRICE_RANGE + "_" + storeSessionFacade.getSessionStore().getStoreId().toLowerCase() + "_string");
						}
					}
					else if (CollectionUtils.isNotEmpty(productModel.getFeatures()))
					{
						if (productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass().getCode()
								.startsWith(HARDSCAPE_PRODUCT)
								|| productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass()
										.getCode().startsWith(NURSERY_PRODUCT))
						{
							PointOfServiceData nearestStore = getNearestStoreBasedOnStock(source, "soIsStockAvailable", IS_SELLABLE, target);
							if (nearestStore == null)
							{
								nearestStore = storeSessionFacade.getSessionStore();
							}
							priceRangeValue = (String) source.getValues()
									.get(PRICE_RANGE + "_" + nearestStore.getStoreId().toLowerCase() + "_string");
						}
						else
						{
							priceRangeValue = (String) source.getValues()
									.get(PRICE_RANGE + "_" + storeSessionFacade.getSessionStore().getStoreId().toLowerCase() + "_string");
						}
					}
					else
					{
						priceRangeValue = (String) source.getValues()
								.get(PRICE_RANGE + "_" + storeSessionFacade.getSessionStore().getStoreId().toLowerCase() + "_string");
					}
				}
				if (StringUtils.isNotEmpty(priceRangeValue))
				{
					final SolrPriceRange solrPriceRange = SolrPriceRange.buildSolrPriceRangePairFromProperty(priceRangeValue);
					if (solrPriceRange != null)
					{
						priceRange.setMinPrice(createPriceData(solrPriceRange.getLower()));
						priceRange.setMaxPrice(createPriceData(solrPriceRange.getHigher()));
					}
				}
			}
			target.setPriceRange(priceRange);
		}
	}

	@Override
	protected void populatePrices(final SearchResultValueData source, final ProductData target)
	{
		// Pull the volume prices flag
		if (!(null != storeSessionFacade.getSessionStore()
				&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("RetailBranches",
						storeSessionFacade.getSessionStore().getStoreId())
				&& StringUtils.isNotBlank(storeSessionFacade.getSessionStore().getCustomerRetailId())))
		{
			final Boolean volumePrices = this.<Boolean> getValue(source, "volumePrices");
			target.setVolumePricesFlag(volumePrices == null ? Boolean.FALSE : volumePrices);
			// Pull the price value for the current currency
			if (null != storeSessionFacade.getSessionStore())
			{
				Double priceValue = null;
				if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
						storeSessionFacade.getSessionStore().getStoreId()))
				{
					PointOfServiceData nearestStore = getNearestStoreBasedOnStock(source, "soIsStockAvailable", IS_SELLABLE, target);
					if (nearestStore == null)
					{
						nearestStore = storeSessionFacade.getSessionStore();
					}
					priceValue = this.<Double> getValue(source, "priceValue" + "_" + nearestStore.getStoreId().toLowerCase() + "_"
							+ getCommonI18NService().getCurrentCurrency().getIsocode().toLowerCase() + "_double");
				}
				else
				{
					final String productCode = this.<String> getValue(source, "socode");
					final ProductModel productModel = productService.getProductForCode(productCode);
					if (CollectionUtils.isNotEmpty(productModel.getClassificationClasses()))
					{
						if (productModel.getClassificationClasses().get(0).getCode().startsWith(HARDSCAPE_PRODUCT)
								|| productModel.getClassificationClasses().get(0).getCode().startsWith(NURSERY_PRODUCT))
						{
							PointOfServiceData nearestStore = getNearestStoreBasedOnStock(source, "soIsStockAvailable", IS_SELLABLE, target);
							if (nearestStore == null)
							{
								nearestStore = storeSessionFacade.getSessionStore();
							}
							priceValue = this.<Double> getValue(source, "priceValue" + "_" + nearestStore.getStoreId().toLowerCase()
									+ "_" + getCommonI18NService().getCurrentCurrency().getIsocode().toLowerCase() + "_double");
						}
						else
						{
							priceValue = this.<Double> getValue(source,
									"priceValue" + "_" + storeSessionFacade.getSessionStore().getStoreId().toLowerCase() + "_"
											+ getCommonI18NService().getCurrentCurrency().getIsocode().toLowerCase() + "_double");
						}
					}
					else if (CollectionUtils.isNotEmpty(productModel.getFeatures()))
					{
						if (productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass().getCode()
								.startsWith(HARDSCAPE_PRODUCT)
								|| productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass()
										.getCode().startsWith(NURSERY_PRODUCT))
						{
							PointOfServiceData nearestStore = getNearestStoreBasedOnStock(source, "soIsStockAvailable", IS_SELLABLE, target);
							if (nearestStore == null)
							{
								nearestStore = storeSessionFacade.getSessionStore();
							}
							priceValue = this.<Double> getValue(source, "priceValue" + "_" + nearestStore.getStoreId().toLowerCase()
									+ "_" + getCommonI18NService().getCurrentCurrency().getIsocode().toLowerCase() + "_double");
						}
						else
						{
							priceValue = this.<Double> getValue(source,
									"priceValue" + "_" + storeSessionFacade.getSessionStore().getStoreId().toLowerCase() + "_"
											+ getCommonI18NService().getCurrentCurrency().getIsocode().toLowerCase() + "_double");
						}
					}
					else
					{
						priceValue = this.<Double> getValue(source,
								"priceValue" + "_" + storeSessionFacade.getSessionStore().getStoreId().toLowerCase() + "_"
										+ getCommonI18NService().getCurrentCurrency().getIsocode().toLowerCase() + "_double");
					}
				}
				if (priceValue != null)
				{
					final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY,
							BigDecimal.valueOf(priceValue.doubleValue()), getCommonI18NService().getCurrentCurrency());
					target.setPrice(priceData);

				}
			}
		}
	}

	@Override
	protected void populateUrl(final SearchResultValueData source, final ProductData target)
	{
		final String url = this.<String> getValue(source, "sourl");
		if (StringUtils.isEmpty(url))
		{
			// Resolve the URL and set it on the product data
			target.setUrl(getProductDataUrlResolver().resolve(target));
		}
		else
		{
			target.setUrl(url);
		}
	}

	@Override
	protected void addImageData(final SearchResultValueData source, final String imageFormat, final String mediaFormatQualifier,
			final ImageDataType type, final List<ImageData> images)
	{
		final String imgValue = getValue(source, "soimg-" + mediaFormatQualifier);
		if (imgValue != null && !imgValue.isEmpty())
		{
			final ImageData imageData = createImageData();
			imageData.setImageType(type);
			imageData.setFormat(imageFormat);
			imageData.setUrl(imgValue);

			images.add(imageData);
		}
	}

	protected List<InventoryUOMData> populateSellableUoms(final String inventoryUOMID, final String code,final ProductData target) {
	    final List<InventoryUPCModel> inventoryUPC = siteOneProductUOMService.getSellableUOMsById(code);
	    final InventoryUPCModel inventoryUPCParent = inventoryUPC.stream()
	            .filter(InventoryUPCModel::getBaseUPC)
	            .findFirst()
	            .orElse(null);

	    final List<InventoryUPCModel> inventoryUPCChildList = inventoryUPC.stream()
	            .filter(upc -> !upc.getBaseUPC())
	            .collect(Collectors.toList());

	    final List<InventoryUOMData> inventoryUOMList = new CopyOnWriteArrayList<>();

	    if (CollectionUtils.isNotEmpty(inventoryUPCChildList)) {
	        for (final InventoryUPCModel inventoryUOMModel : inventoryUPCChildList) {
	            if (inventoryUOMModel.getHideUPCOnline() != null && !inventoryUOMModel.getHideUPCOnline()) {
	                final InventoryUOMData inventoryUOMData = new InventoryUOMData();
	                inventoryUOMData.setInventoryUOMID(inventoryUOMModel.getInventoryUPCID());
	                inventoryUOMData.setDescription(inventoryUOMModel.getInventoryUPCDesc());
	                inventoryUOMData.setParentInventoryUOMID(
	                        inventoryUPCParent != null ? inventoryUPCParent.getInventoryUPCID() : null);
	                inventoryUOMData.setInventoryMultiplier(inventoryUOMModel.getInventoryMultiplier());
	                inventoryUOMData.setHideUOMOnline(false);
	                inventoryUOMData.setInventoryUOMDesc(inventoryUOMModel.getInventoryUPCDesc());
	                inventoryUOMData.setMeasure(inventoryUOMModel.getInventoryUPCDesc());
	                inventoryUOMData.setCode(inventoryUOMModel.getCode());
	                inventoryUOMList.add(inventoryUOMData);
	            }
	        }

	        if (inventoryUPCParent != null && inventoryUPCParent.getHideUPCOnline() != null
	                && !inventoryUPCParent.getHideUPCOnline()) {
	            final InventoryUOMData parentUOMData = new InventoryUOMData();
	            parentUOMData.setInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
	            parentUOMData.setDescription(inventoryUPCParent.getInventoryUPCDesc());
	            parentUOMData.setParentInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
	            parentUOMData.setInventoryMultiplier(inventoryUPCParent.getInventoryMultiplier());
	            parentUOMData.setInventoryUOMDesc(inventoryUPCParent.getInventoryUPCDesc());
	            parentUOMData.setMeasure(inventoryUPCParent.getInventoryUPCDesc());
	            parentUOMData.setCode(inventoryUPCParent.getCode());
	            inventoryUOMList.add(parentUOMData);
	        }
	    } else {
	        if (inventoryUPCParent != null) {
	            final InventoryUOMData parentUOMData = new InventoryUOMData();
	            parentUOMData.setInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
	            parentUOMData.setDescription(inventoryUPCParent.getInventoryUPCDesc());
	            parentUOMData.setParentInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
	            parentUOMData.setInventoryMultiplier(inventoryUPCParent.getInventoryMultiplier());
	            parentUOMData.setInventoryUOMDesc(inventoryUPCParent.getInventoryUPCDesc());
	            parentUOMData.setMeasure(inventoryUPCParent.getInventoryUPCDesc());
	            parentUOMData.setCode(inventoryUPCParent.getCode());
	            inventoryUOMList.add(parentUOMData);

	        }
	    }

	    inventoryUOMList.sort(Comparator.comparing(InventoryUOMData::getInventoryMultiplier));
	    return inventoryUOMList;
	}

}

