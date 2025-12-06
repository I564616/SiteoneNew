/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class IndexPurchasedProductCountContainer
{

	private final Map<String, Map<String, Integer>> productCountMap = new HashMap<String, Map<String, Integer>>();


	public Collection<String> getAllB2Bunit()
	{
		return this.productCountMap.keySet();
	}

	public Map<String, Integer> getAllProductCountForB2BUnit(final String b2bUnit)
	{
		return this.productCountMap.get(b2bUnit);
	}

	public void add(final IndexPurchasedProductCountData indexData)
	{

		productCountMap.computeIfAbsent(indexData.getB2bUnit(), productCount -> new HashMap<String, Integer>())
				.put(indexData.getProductCode(), indexData.getCount());
	}


}
