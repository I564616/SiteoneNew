package com.siteone.core.cronjob.dao.impl;

import com.siteone.core.cronjob.dao.ImportOnHandProductStoresCronJobDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.util.Config;
import com.siteone.core.model.ImportOnHandProductStoresCronJobModel;

import de.hybris.platform.ordersplitting.model.StockLevelModel;

import java.util.List;

/**
 * @author nmangal
 */
public class DefaultImportOnHandProductStoresCronJobDao extends AbstractItemDao implements ImportOnHandProductStoresCronJobDao {

    @Override
    public List<StockLevelModel> findOnHandProductCodeForModifiedStockLevels()
    {
        return getFlexibleSearchService()
                .<StockLevelModel> search(Config.getString("import.product.stores.cronjob.productcode.onhand.query", null)).getResult();
    }

    @Override
    public List<StockLevelModel> findEligibleOnHandStockLevels(final String productCode)
    {
        final String queryString = Config.getString("import.product.stores.cronjob.eligible.onhand.query", null);
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.addQueryParameter("productCode", productCode);
        return getFlexibleSearchService().<StockLevelModel> search(query).getResult();
    }

    @Override
    public List<StockLevelModel> findNonEligibleOnHandStockLevels(final String productCode)
    {
        final String queryString = Config.getString("import.product.stores.cronjob.noneligible.onhand.query", null);
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.addQueryParameter("productCode", productCode);
        return getFlexibleSearchService().<StockLevelModel> search(query).getResult();

    }
}
