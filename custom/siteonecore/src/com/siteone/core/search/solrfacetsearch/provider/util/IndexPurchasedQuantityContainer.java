/**
 * 
 */
package com.siteone.core.search.solrfacetsearch.provider.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AA04994
 *
 */
public class IndexPurchasedQuantityContainer
{

	private final Map<String, Map<String, Integer>> purchasedQuantityMap = new HashMap<String, Map<String, Integer>>();


	public Collection<String> getAllB2Bunit()
	{
		return this.purchasedQuantityMap.keySet();
	}

	public Map<String, Integer> getAllPurchasedQuantityForB2BUnit(final String b2bUnit)
	{
		return this.purchasedQuantityMap.get(b2bUnit);
	}

	public void add(IndexPurchasedProductQuantityData indexData)
	{
		purchasedQuantityMap.computeIfAbsent(indexData.getB2bUnit(), purchasedQuantity -> new HashMap<String, Integer>())
		.put(indexData.getProductCode(), indexData.getCount());
		
	}


}