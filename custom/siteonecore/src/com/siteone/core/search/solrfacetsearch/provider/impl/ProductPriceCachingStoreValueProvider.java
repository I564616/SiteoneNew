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

import com.siteone.core.search.solrfacetsearch.provider.util.IndexProductPriceData;


/**
 * @author i849388
 *
 */
public class ProductPriceCachingStoreValueProvider extends AbstractProductPriceCachingValueProvider
		implements FieldValueProvider, Serializable
{

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		if (model instanceof ProductModel)
		{

			final ProductModel product = (ProductModel) model;

			final Collection<FieldValue> fieldValues = new ArrayList<>();

			loadProductCache(product);

			for (final IndexProductPriceData priceData : this.threadLocalCache.get().getProductPrices(product.getCode()))
			{
				fieldValues.addAll(createFieldValue(priceData, indexedProperty));
			}

			return fieldValues;

		}
		else
		{
			throw new FieldValueProviderException("Cannot get sku of non-product item");
		}

	}

	protected List<FieldValue> createFieldValue(final IndexProductPriceData priceData, final IndexedProperty indexedProperty)
			throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<>();

		final List<String> rangeNameList = getRangeNameList(indexedProperty, priceData.getPrice(), priceData.getCurrencyIsoCode());

		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty,
				priceData.getStoreId() + "_" + priceData.getCurrencyIsoCode());
		addFieldValues(fieldValues, rangeNameList, priceData.getPrice(), fieldNames);

		return fieldValues;
	}

	protected void addFieldValues(final Collection<FieldValue> fieldValues, final List<String> rangeNameList, final Double value,
			final Collection<String> fieldNames)
	{
		for (final String fieldName : fieldNames)
		{
			if (rangeNameList.isEmpty())
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}
			else
			{
				for (final String rangeName : rangeNameList)
				{
					fieldValues.add(new FieldValue(fieldName, rangeName == null ? value : rangeName));
				}
			}
		}
	}

}
