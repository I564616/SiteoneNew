/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @author i849388
 *
 */
public class IndexProductPriceContainer
{

	private String baseProductId;

	private final Comparator<IndexProductPriceData> priceStoreIDComparator = Comparator
			.nullsLast(Comparator.comparingDouble(IndexProductPriceData::getPrice))
			.thenComparing(Comparator.nullsLast(Comparator.comparing(IndexProductPriceData::getStoreId)));

	private final Comparator<IndexProductPriceData> priceProductIDComparator = Comparator
			.nullsLast(Comparator.comparingDouble(IndexProductPriceData::getPrice))
			.thenComparing(Comparator.nullsLast(Comparator.comparing(IndexProductPriceData::getProductId)));


	private final Map<String, SortedSet<IndexProductPriceData>> pricesByProductId = new HashMap<String, SortedSet<IndexProductPriceData>>();

	private final Map<String, SortedSet<IndexProductPriceData>> pricesByStoreId = new HashMap<String, SortedSet<IndexProductPriceData>>();

	public void addProductPriceData(final IndexProductPriceData priceData)
	{
		SortedSet<IndexProductPriceData> productPrices = pricesByProductId.get(priceData.getProductId());

		if (productPrices == null)
		{
			productPrices = new TreeSet<IndexProductPriceData>(priceStoreIDComparator);
			pricesByProductId.put(priceData.getProductId(), productPrices);
		}

		productPrices.add(priceData);

		SortedSet<IndexProductPriceData> storePrices = pricesByStoreId.get(priceData.getStoreId());

		if (storePrices == null)
		{
			storePrices = new TreeSet<IndexProductPriceData>(priceProductIDComparator);
			pricesByStoreId.put(priceData.getStoreId(), storePrices);
		}

		storePrices.add(priceData);

	}

	public SortedSet<IndexProductPriceData> getProductPrices(final String code)
	{
		return pricesByProductId.get(code);
	}

	public Collection<String> getAllStores()
	{
		return pricesByStoreId.keySet();
	}

	public SortedSet<IndexProductPriceData> getStorePrices(final String storeId)
	{
		return pricesByStoreId.get(storeId);
	}

	/**
	 * @return the baseProductId
	 */
	public String getBaseProductId()
	{
		return baseProductId;
	}

	/**
	 * @param baseProductId
	 *           the baseProductId to set
	 */
	public void setBaseProductId(final String baseProductId)
	{
		this.baseProductId = baseProductId;
	}


}
