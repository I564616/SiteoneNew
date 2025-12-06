package com.siteone.core.stock.dao;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.stock.impl.DefaultStockLevelDao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;


public class DefaultSiteOneStockLevelDao extends DefaultStockLevelDao implements SiteOneStockLevelDao
{
	private CatalogVersionService catalogVersionService;
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneStockLevelDao.class);

	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	//NLA Stock level check for cart and checkout
	@Override
	public List<StockLevelModel> getStockLevelsForQuantity(final String productCode, final Long quantity)
	{
		FlexibleSearchQuery fQuery = null;
		fQuery = new FlexibleSearchQuery(
				"SELECT TOP 1 {pk} FROM {StockLevel} WHERE {productCode} = ?productCode and ({available}-{reserved}+{overSelling} >= ?quantity or {inventoryHit} > '4')");
		fQuery.addQueryParameter("productCode", productCode);
		fQuery.addQueryParameter("quantity", quantity);
		final SearchResult result = this.getFlexibleSearchService().search(fQuery);
		return result.getResult();
	}

	//NLA Stock level check for PDP and PLP
	@Override
	public boolean checkForNLAStockLevel(final String productCode)
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(
				"SELECT TOP 1 {pk} FROM {StockLevel} WHERE {productCode} = ?productCode and ({available}-{reserved}+{overSelling} > '0' or {inventoryHit} > '4')");
		fQuery.addQueryParameter("productCode", productCode);
		final SearchResult result = this.getFlexibleSearchService().search(fQuery);
		if (null != result.getResult() && CollectionUtils.isNotEmpty(result.getResult()))
		{
			return true;
		}
		return false;
	}


	@Override
	public List<List<Object>> getStockLevelsForNearByStores(final String productCode, final List<PointOfServiceData> nearByStores)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT {pos.storeId},{sl.quantityLevel},({sl.available} - {sl.reserved} + {sl.overSelling}) as OnHandQty,{pos.name},{pos.displayName},{sl.forceInStock},{sl.respectInventory},{sl.forceStockRespectNearby},{sl.isNurseryCoreSku} FROM {stockLevel as sl} ,{warehouse as wh}, {pointofservice as pos}");
		sb.append(" WHERE {pos.storeId} = {wh.code} AND {wh.pk} = {sl.warehouse} AND {productCode} = ?productCode AND ");
		sb.append(" {pos.storeid} IN (");
		sb.append(convertStoreListToString(nearByStores));
		sb.append(")");

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb.toString());
		fQuery.setResultClassList(Arrays.asList(String.class, Integer.class, Integer.class, String.class, String.class,
				Boolean.class, Boolean.class, Boolean.class, Boolean.class));
		fQuery.addQueryParameter("productCode", productCode);
		final SearchResult<List<Object>> result = this.getFlexibleSearchService().search(fQuery);
		return result.getResult();
	}

	@Override
	public List<List<Object>> getStockLevelsForStore(final String productCode, final PointOfServiceData store)
	{
		final String strQuery = "SELECT TOP 1 {sl.quantityLevel},({sl.available} - {sl.reserved} + {sl.overSelling}) as OnHandQty,{sl.forceInStock},{sl.respectInventory},{p.inventoryCheck},{p.isShippable} FROM {Product as p}, {stockLevel as sl} ,{warehouse as wh} "
				+ " where {sl.productCode}={p.code} and {sl.warehouse} = {wh.pk}"
				+ " and {sl.productCode} = ?productCode AND {wh.code}=?storeId and {p.catalogversion}=?catalogversion";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(strQuery);
		fQuery.setResultClassList(
				Arrays.asList(Integer.class, Integer.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class));
		fQuery.addQueryParameter("productCode", productCode);
		fQuery.addQueryParameter("storeId", store.getStoreId());
		fQuery.addQueryParameter("catalogVersion", getSessionService().getAttribute("currentCatalogVersion"));
		final SearchResult<List<Object>> result = this.getFlexibleSearchService().search(fQuery);
		return result.getResult();
	}

	private StringBuilder convertStoreListToString(final List<PointOfServiceData> nearByStores)
	{
		final StringBuilder storeList = new StringBuilder();
		boolean firstTime = true;
		for (final PointOfServiceData storeData : nearByStores)
		{
			if (storeData != null && storeData.getStoreId() != null)
			{
				if (firstTime)
				{
					storeList.append("'");
					storeList.append(storeData.getStoreId());
					storeList.append("'");
					firstTime = false;
				}
				else
				{
					storeList.append(", '");
					storeList.append(storeData.getStoreId());
					storeList.append("'");
				}
			}

		}

		return storeList;
	}

	public List<List<Object>> getStockLevelCountForHomeAndNearByStores(final StringBuilder productList,
			final List<PointOfServiceData> nearByStores)
	{

		final StringBuilder flexQueryString = new StringBuilder();
		flexQueryString.append(
				"SELECT {sl.productCode}, {wh.code}, ({sl.available} - {sl.reserved} + {sl.overSelling}) as availableQty FROM {stockLevel as sl} ,{warehouse as wh}, {pointofservice as pos}");
		flexQueryString.append(" WHERE {pos.storeId} = {wh.code} AND {wh.pk} = {sl.warehouse} AND {sl.productCode} IN (");
		flexQueryString.append(productList);
		flexQueryString.append(")");
		if (CollectionUtils.isNotEmpty(nearByStores))
		{
			flexQueryString.append(" AND {pos.storeid} IN (");
			flexQueryString.append(convertStoreListToString(nearByStores));
			flexQueryString.append(")");
		}

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(flexQueryString.toString());
		fQuery.setResultClassList(Arrays.asList(String.class, String.class, Integer.class));
		final SearchResult<List<Object>> result = this.getFlexibleSearchService().search(fQuery);
		return result.getResult();
	}

	@Override
	public List<List<Object>> getStockLevelsForHubStoresForProducts(final List<String> products, final String hubStore)
	{
		final StringBuilder productList = new StringBuilder();
		boolean firstTime = true;
		for (final String product : products)
		{
			if (product != null)
			{
				if (firstTime)
				{
					productList.append("'");
					productList.append(product);
					productList.append("'");
					firstTime = false;
				}
				else
				{
					productList.append(", '");
					productList.append(product);
					productList.append("'");
				}
			}

		}
		final StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT {pos.storeId},{sl.inventoryHit},({sl.available} - {sl.reserved} + {sl.overSelling}) as OnHandQty,{pos.name},{pos.displayName}, {sl.productCode},{sl.forceInStock},{sl.respectInventory} FROM {stockLevel as sl} ,{warehouse as wh}, {pointofservice as pos}");
		sb.append(" WHERE {pos.storeId} = {wh.code} AND {wh.pk} = {sl.warehouse} AND {pos.storeid} = ?hubStore AND ");
		sb.append(" {sl.productCode} IN (");
		sb.append(productList);
		sb.append(")");

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb.toString());
		fQuery.setResultClassList(Arrays.asList(String.class, Integer.class, Integer.class, String.class, String.class,
				String.class, Boolean.class, Boolean.class));
		fQuery.addQueryParameter("hubStore", hubStore);
		final SearchResult<List<Object>> result = this.getFlexibleSearchService().search(fQuery);
		return result.getResult();
	}

	@Override
	public List<List<Object>> getStockLevelsForHubStoresForProduct(final String productCode, final String hubStore)
	{

		final StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT {pos.storeId},{sl.inventoryHit},({sl.available} - {sl.reserved} + {sl.overSelling}) as OnHandQty,{pos.name},{pos.displayName}, {sl.productCode},{sl.forceInStock} FROM {stockLevel as sl} ,{warehouse as wh}, {pointofservice as pos}");
		sb.append(" WHERE {pos.storeId} = {wh.code} AND {wh.pk} = {sl.warehouse} AND {pos.storeid} = ?hubStore AND ");
		sb.append(" {sl.productCode} =?productCode");

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb.toString());
		fQuery.setResultClassList(
				Arrays.asList(String.class, Integer.class, Integer.class, String.class, String.class, String.class, Boolean.class));
		fQuery.addQueryParameter("hubStore", hubStore);
		fQuery.addQueryParameter("productCode", productCode);
		final SearchResult<List<Object>> result = this.getFlexibleSearchService().search(fQuery);
		return result.getResult();
	}

	@Override
	public List<List<Object>> isForceInStock(final String productCode, final String storeId, final String hubStoreId)
	{
		final StringBuilder storeList = new StringBuilder();
		storeList.append("'");
		storeList.append(storeId);
		storeList.append("'");
		if (hubStoreId != null)
		{
			storeList.append(", '");
			storeList.append(hubStoreId);
			storeList.append("'");
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {sl.forceInStock},{sl.respectInventory},{p.inventoryCheck},{sl.forceStockRespectNearby},{sl.quantityLevel},{sl.isNurseryCoreSku},{wh.code} from {Product as p},{stockLevel as sl},{warehouse as wh} where {sl.productCode}={p.code} "
				+ "AND {sl.warehouse} = {wh.pk} AND {sl.productCode} = ?productCode AND {wh.code} IN (");
		sb.append(storeList);
		sb.append(")");
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb.toString());
		fQuery.addQueryParameter("productCode", productCode);
		fQuery.setResultClassList(Arrays.asList(Boolean.class, Boolean.class, Boolean.class, Boolean.class, float.class, Boolean.class, String.class));
		final SearchResult<List<Object>> result = this.getFlexibleSearchService().search(fQuery);
    return result.getResult();
	}

	@Override
	public List<StockLevelModel> isDcStockAvailable(final String productCode, final List<String> storeId)
	{

		final StringBuilder storeList = new StringBuilder();
		boolean firstTime = true;

		for (final String store : storeId)
		{
			if (firstTime)
			{
				storeList.append("'");
				storeList.append(store);
				storeList.append("'");
				firstTime = false;
			}
			else
			{
				storeList.append(", '");
				storeList.append(store);
				storeList.append("'");
			}
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT {sl.pk} FROM {Product as p}, {stockLevel as sl} ,{warehouse as wh}");
		sb.append(
				" WHERE {sl.productCode}={p.code} and {sl.warehouse} = {wh.pk} AND {sl.productCode} = ?productCode AND {p.catalogversion}=?catalogVersion AND {sl.available} > 0 AND ");
		sb.append("{wh.code} IN (");
		sb.append(storeList);
		sb.append(")");


		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb);
		fQuery.addQueryParameter("productCode", productCode);
		fQuery.addQueryParameter("catalogVersion", getSessionService().getAttribute("currentCatalogVersion"));

		final List<StockLevelModel> results = getFlexibleSearchService().<StockLevelModel> search(fQuery).getResult();

		if (CollectionUtils.isNotEmpty(results))
		{
			return results;
		}
		else
		{
			return null;
		}


	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}


}