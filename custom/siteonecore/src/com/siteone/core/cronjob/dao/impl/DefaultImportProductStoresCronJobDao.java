/**
 *
 */
package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.util.Config;

import java.util.List;

import com.siteone.core.cronjob.dao.ImportProductStoresCronJobDao;


/**
 * @author 1124932
 *
 */
public class DefaultImportProductStoresCronJobDao extends AbstractItemDao implements ImportProductStoresCronJobDao
{
	@Override
	public List<ProductModel> findProductCodeForModifiedProduct()
	{
		return getFlexibleSearchService().<ProductModel> search(Config.getString("import.product.qr.cronjob.query", null))
				.getResult();
	}

	@Override
	public List<StockLevelModel> findProductCodeForModifiedStockLevels()
	{
		return getFlexibleSearchService()
				.<StockLevelModel> search(Config.getString("import.product.stores.cronjob.productcode.query", null)).getResult();
	}

	@Override
	public List<StockLevelModel> findEligibleStockLevels(final String productCode)
	{
		final String queryString = Config.getString("import.product.stores.cronjob.eligible.query", null);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("productCode", productCode);
		return getFlexibleSearchService().<StockLevelModel> search(query).getResult();
	}

	@Override
	public List<StockLevelModel> findNonEligibleStockLevels(final String productCode)
	{
		final String queryString = Config.getString("import.product.stores.cronjob.noneligible.query", null);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("productCode", productCode);
		return getFlexibleSearchService().<StockLevelModel> search(query).getResult();

	}


}
