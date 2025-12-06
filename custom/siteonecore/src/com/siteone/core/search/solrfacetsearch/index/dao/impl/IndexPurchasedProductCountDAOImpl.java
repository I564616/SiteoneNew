/**
 *
 */
package com.siteone.core.search.solrfacetsearch.index.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.search.solrfacetsearch.index.dao.IndexPurchasedProductCountDAO;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexPurchasedProductCountContainer;
import com.siteone.core.search.solrfacetsearch.provider.util.IndexPurchasedProductCountData;



public class IndexPurchasedProductCountDAOImpl implements IndexPurchasedProductCountDAO
{

	private static final Logger LOG = Logger.getLogger(IndexPurchasedProductCountDAOImpl.class);

	private FlexibleSearchService flexibleSearchService;

	private static final String QUERY = buildQuery();

	@Override
	public IndexPurchasedProductCountContainer loadPurchasedProductCount()
	{
		final IndexPurchasedProductCountContainer cont = new IndexPurchasedProductCountContainer();
		String queryCofig = Config.getString("buyitagain.product.count.query", null);
		if (queryCofig == null)
		{
			queryCofig = QUERY;
		}
		LOG.error("IndexPurchasedProductCountDAOImpl queryCofig " + queryCofig);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryCofig);

		query.setResultClassList(Arrays.asList(String.class, String.class, Integer.class));

		final SearchResult<List<Object>> result = flexibleSearchService.search(query);

		for (final List<Object> row : result.getResult())
		{

			final String productCode = (String) row.get(0);
			final String b2bUnit = (String) row.get(1);
			final Integer count = (Integer) row.get(2);
			final IndexPurchasedProductCountData productCountData = new IndexPurchasedProductCountData();
			productCountData.setProductCode(productCode);
			productCountData.setB2bUnit(b2bUnit);
			productCountData.setCount(count);
			cont.add(productCountData);
		}
		return cont;
	}


	private static String buildQuery()
	{

		final StringBuilder buffer = new StringBuilder();
		buffer.append("select {entry.storeproductcode} as productCode, ");
		buffer.append(" {u.uid} as b2bUnit, count(*) as purchasedCount ");
		buffer.append(" from {OrderEntry as entry join Order AS o on {entry.order}={o.pk} join ");
		buffer.append(" B2BUnit as u on {o.orderingAccount} = {u.pk}} where {u.active} = 1 ");
		buffer.append(" AND DATEDIFF (DAY,CONVERT (datetime,{o.creationtime}), SYSDATETIME()) <= 180 ");
		buffer.append(" AND {o.salesApplication} IN (8796110389339,8796110422107,8797463674971,8797463707739) ");
		buffer.append(" AND {o.code} not like '%_bak' GROUP BY {entry.storeproductcode}, ");
		buffer.append(" {u.uid} ORDER BY MAX({entry.pk}) ");
		return buffer.toString();
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


}
