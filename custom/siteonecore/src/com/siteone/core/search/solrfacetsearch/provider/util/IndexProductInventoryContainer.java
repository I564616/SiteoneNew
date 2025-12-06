/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author i849388
 *
 */
public class IndexProductInventoryContainer
{

	private String baseProductId;

	private final Map<String, Collection<IndexProductPosInventoryData>> storeInventoryMap = new HashMap<String, Collection<IndexProductPosInventoryData>>();


	public Collection<String> getAllStores()
	{
		return this.storeInventoryMap.keySet();
	}

	public Collection<IndexProductPosInventoryData> getAllInventoryForStore(final String storeID)
	{
		return this.storeInventoryMap.get(storeID);
	}

	public Collection<IndexProductPosInventoryData> getAllInventoryForProductAndStore()
	{

		final Collection<IndexProductPosInventoryData> allInventoryData = new ArrayList<IndexProductPosInventoryData>();

		for (final Collection<IndexProductPosInventoryData> storeInventory : storeInventoryMap.values())
		{
			allInventoryData.addAll(storeInventory);
		}
		return allInventoryData;
	}

	public void add(final IndexProductPosInventoryData inventoryData)
	{
		Collection<IndexProductPosInventoryData> posInventory = this.storeInventoryMap.get(inventoryData.getStoreId());

		if (posInventory == null)
		{
			posInventory = new ArrayList<IndexProductPosInventoryData>();
			this.storeInventoryMap.put(inventoryData.getStoreId(), posInventory);
		}

		posInventory.add(inventoryData);
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
