package com.siteone.core.cronjob.dao;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import java.util.List;

/**
 * @author nmangal
 */
public interface ImportOnHandProductStoresCronJobDao {

    public List<StockLevelModel> findOnHandProductCodeForModifiedStockLevels();

    List<StockLevelModel> findEligibleOnHandStockLevels(String productCode);

    List<StockLevelModel> findNonEligibleOnHandStockLevels(String productCode);
}
