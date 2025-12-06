/**
 *
 */
package com.siteone.core.cronjob.dao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;

import java.util.List;


/**
 * @author 1124932
 *
 */
public interface ImportProductStoresCronJobDao
{
	public List<StockLevelModel> findEligibleStockLevels(String productCode);

	public List<StockLevelModel> findNonEligibleStockLevels(String productCode);

	public List<StockLevelModel> findProductCodeForModifiedStockLevels();

	public List<ProductModel> findProductCodeForModifiedProduct();

}
