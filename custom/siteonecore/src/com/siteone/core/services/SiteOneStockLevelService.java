/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

import java.util.List;
import java.util.Map;


/**
 * @author 1219341
 *
 */
public interface SiteOneStockLevelService
{
	List<StockLevelModel> getStockLevelsForQuantity(String productCode, Long quantity);

	boolean checkForNLAStockLevel(String productCode);

	List<List<Object>> getStockLevelsForNearByStores(String productCode, List<PointOfServiceData> nearByStores);

	List<List<Object>> getStockLevelCountForHomeAndNearByStores(final StringBuilder productList, final List<PointOfServiceData> nearByStores);

	List<List<Object>> getStockLevelsForHubStoresForProducts(final List<String> products, final String hubStoreId);

	List<List<Object>> getStockLevelsForHubStoresForProduct(final String productCode, final String hubStoreId);

	StockLevelModel getStockLevelForWarehouse(final String productCode, final WarehouseModel warehouse);

	List<Boolean> isForceInStock(String productCode, String storeId, String hubStoreId ,float multiplier);
	
	List<List<Object>> getStockLevelsForStore(String productCode, PointOfServiceData store);

	Map<Boolean, Integer>  isDcStockAvailable(String productCode, List<String> storeId, float multiplier);
}
