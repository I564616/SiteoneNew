/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.ordersplitting.model.StockLevelModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.core.stock.dao.SiteOneStockLevelDao;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.stock.impl.StockLevelDao;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;


/**
 * @author 1219341
 *
 */
public class DefaultSiteOneStockLevelService implements SiteOneStockLevelService
{
	private SiteOneStockLevelDao siteOneStockLevelDao;
	
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneStockLevelService.class);

	@Resource(name = "stockLevelDao")
	private StockLevelDao stockLevelDao;

	@Override
	public List<StockLevelModel> getStockLevelsForQuantity(final String productCode, final Long quantity)
	{
		return siteOneStockLevelDao.getStockLevelsForQuantity(productCode, quantity);
	}

	@Override
	public List<List<Object>> getStockLevelsForNearByStores(final String productCode, final List<PointOfServiceData> nearByStores)
	{
		return siteOneStockLevelDao.getStockLevelsForNearByStores(productCode, nearByStores);
	}
	
	@Override
	public List<List<Object>> getStockLevelsForStore(final String productCode, final PointOfServiceData store)
	{
		return siteOneStockLevelDao.getStockLevelsForStore(productCode, store);
	}

	@Override
	public List<List<Object>> getStockLevelsForHubStoresForProducts(final List<String> products, final String hubStoreId)
	{
		return siteOneStockLevelDao.getStockLevelsForHubStoresForProducts(products, hubStoreId);
	}

	@Override
	public List<List<Object>> getStockLevelCountForHomeAndNearByStores(final StringBuilder productList, final List<PointOfServiceData> nearByStores)
	{
		return siteOneStockLevelDao.getStockLevelCountForHomeAndNearByStores(productList, nearByStores);
	}

	@Override
	public List<List<Object>> getStockLevelsForHubStoresForProduct(final String productCode, final String hubStoreId)
	{
		return siteOneStockLevelDao.getStockLevelsForHubStoresForProduct(productCode, hubStoreId);
	}

	@Override
	public StockLevelModel getStockLevelForWarehouse(final String productCode, final WarehouseModel warehouse)
	{
		return stockLevelDao.findStockLevel(productCode, warehouse);
	}

	@Override
	public List<Boolean> isForceInStock(final String productCode, final String storeId, final String hubStoreId, final float multiplier)
	{
		List<Object> forceInStock = new ArrayList();
		List<Object> hubStoreForceInStock = new ArrayList();
		if(hubStoreId != null)
		{
			List<List<Object>> forceStocks = siteOneStockLevelDao.isForceInStock(productCode, storeId, hubStoreId);
			if(forceStocks.size() > 0 && forceStocks.get(0) != null && forceStocks.get(0).get(6) != null && ((String)forceStocks.get(0).get(6)).equalsIgnoreCase(storeId))
			{
				if(forceStocks.size() > 0)
				{
					forceInStock = forceStocks.get(0);
				}
				if(forceStocks.size() > 1)
				{
					hubStoreForceInStock = forceStocks.get(1); 
				}
			}
			else
			{
				if(forceStocks.size() > 1)
				{
					forceInStock = forceStocks.get(1);
				}
				if(forceStocks.size() > 0)
				{
					hubStoreForceInStock = forceStocks.get(0);
				}
			}	
		}
		else
		{
			List<List<Object>> forceStocks = siteOneStockLevelDao.isForceInStock(productCode, storeId, null);
			if(forceStocks.size() > 0)
			{
				forceInStock = forceStocks.get(0);
			}
		}		
		List<Boolean> forceStockFlags = new ArrayList<>();
		forceStockFlags.add(CollectionUtils.isNotEmpty(forceInStock) && BooleanUtils.isTrue((Boolean) forceInStock.get(0))
				&& (BooleanUtils.isNotTrue((Boolean) forceInStock.get(2)) || BooleanUtils.isNotTrue((Boolean) forceInStock.get(1))));
		forceStockFlags.add(CollectionUtils.isNotEmpty(forceInStock) && BooleanUtils.isTrue((Boolean) forceInStock.get(3)));
		forceStockFlags.add(CollectionUtils.isNotEmpty(forceInStock) && forceInStock.get(4) !=  null && ((float) forceInStock.get(4)) >= multiplier);
		forceStockFlags.add(CollectionUtils.isNotEmpty(forceInStock) && BooleanUtils.isTrue((Boolean) forceInStock.get(5)));
		if(hubStoreId != null)
		{
			forceStockFlags.add(CollectionUtils.isNotEmpty(hubStoreForceInStock) && BooleanUtils.isTrue((Boolean) hubStoreForceInStock.get(0))
					&& (BooleanUtils.isNotTrue((Boolean) hubStoreForceInStock.get(2)) || BooleanUtils.isNotTrue((Boolean) hubStoreForceInStock.get(1))));
		}
		else
		{
			forceStockFlags.add(Boolean.FALSE);
		}
		return forceStockFlags;
	}
	
	@Override
	public Map<Boolean, Integer> isDcStockAvailable(final String productCode, final List<String> storeId, final float multiplier)
	{
		List<StockLevelModel> stockModel = siteOneStockLevelDao.isDcStockAvailable(productCode, storeId);
		Map<Boolean, Integer> stockMap = new HashMap<>();
      if(CollectionUtils.isNotEmpty(stockModel))
      {
      	for(StockLevelModel stock : stockModel)
      	{
      		if(stock != null && stock.getAvailable() >= multiplier)
      		{
      			stockMap.put(true, stock.getAvailable());
      			return stockMap;
      		}
      	}
   	}
      return stockMap;
	}


	/**
	 * @return the siteOneStockLevelDao
	 */
	public SiteOneStockLevelDao getSiteOneStockLevelDao()
	{
		return siteOneStockLevelDao;
	}

	/**
	 * @param siteOneStockLevelDao
	 *           the siteOneStockLevelDao to set
	 */
	public void setSiteOneStockLevelDao(final SiteOneStockLevelDao siteOneStockLevelDao)
	{
		this.siteOneStockLevelDao = siteOneStockLevelDao;
	}


	@Override
	public boolean checkForNLAStockLevel(final String productCode)
	{
		return siteOneStockLevelDao.checkForNLAStockLevel(productCode);

	}

}

