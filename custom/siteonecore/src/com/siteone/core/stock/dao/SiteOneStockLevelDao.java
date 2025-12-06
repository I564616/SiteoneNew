/**
 *
 */
package com.siteone.core.stock.dao;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.ordersplitting.model.StockLevelModel;

import java.util.List;


/**
 * @author 1219341
 *
 */
public interface SiteOneStockLevelDao
{
	List<StockLevelModel> getStockLevelsForQuantity(String productCode, Long quantity);

	boolean checkForNLAStockLevel(String productCode);

	List<List<Object>> getStockLevelsForNearByStores(String productCode, List<PointOfServiceData> nearByStores);

	List<List<Object>>	getStockLevelCountForHomeAndNearByStores(StringBuilder productList, final List<PointOfServiceData> nearByStores);

	List<List<Object>> getStockLevelsForHubStoresForProducts(List<String> products, String hubStoreId);

	List<List<Object>> getStockLevelsForHubStoresForProduct(String productCode, String hubStoreId);

	List<List<Object>> isForceInStock(String productCode, String storeId, String hubStoreId);

	List<List<Object>> getStockLevelsForStore(String productCode, PointOfServiceData store);

	List<StockLevelModel> isDcStockAvailable(String productCode, List<String> storeId);
}
