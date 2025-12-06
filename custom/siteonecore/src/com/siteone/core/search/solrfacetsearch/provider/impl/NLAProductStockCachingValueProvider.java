/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPosInventoryData;


/**
 * @author i849388
 *
 */
public class NLAProductStockCachingValueProvider extends AbstractProductInventoryCachingValueProvider
		implements FieldValueProvider, Serializable
{

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty, final Object model)
			throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final List<FieldValue> fieldValues = new ArrayList<>();

			loadProductCache(product);


			final Collection<String> allStores = this.threadLocalCache.get().getAllStores();

			if (!CollectionUtils.isEmpty(allStores))
			{
				for (final String storeID : allStores)
				{
					final boolean isSellable = isSellableInStore(storeID);
					addFieldValues(fieldValues, indexedProperty, isSellable, storeID);
				}

			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get stock of non-product item");
		}
	}

	private boolean isSellableInStore(final String storeID)
	{

		boolean isSellable = false;

		final Collection<IndexProductPosInventoryData> inventoryCol = this.threadLocalCache.get().getAllInventoryForStore(storeID);

		if (!CollectionUtils.isEmpty(inventoryCol))
		{

			for (final IndexProductPosInventoryData inventoryData : inventoryCol)
			{
				isSellable |= isSellableInventoryHit(inventoryData);

				//stop at first one found
				if (isSellable)
				{
					break;
				}
			}
		}

		return isSellable;
	}


	private boolean isSellableInventoryHit(final IndexProductPosInventoryData inventoryData)
	{
		return !(inventoryData.getStockLevelFlag()
				&& (inventoryData.getInventoryHitCountFlag() || (null == inventoryData.getInventoryHitCount())));

	}

	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value,
			final String storeId)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, storeId);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
	}

}
