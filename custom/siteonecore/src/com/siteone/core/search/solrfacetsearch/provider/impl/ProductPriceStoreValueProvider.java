/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author 1219341
 *
 */
public class ProductPriceStoreValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.
	 * config.IndexConfig, de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;

			final Collection<FieldValue> fieldValues = new ArrayList<>();

			for (final PriceRowModel priceRow : product.getEurope1Prices())
			{
				if (priceRow.getMinqtd() == 1 && null != priceRow.getPointOfService() && priceRow.getPointOfService().getIsActive())
				{
					fieldValues.addAll(createFieldValue(priceRow, indexedProperty));
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get sku of non-product item");
		}
	}

	protected List<FieldValue> createFieldValue(final PriceRowModel value, final IndexedProperty indexedProperty)
			throws FieldValueProviderException
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		if (null != value.getPointOfService() && value.getPointOfService().getIsActive() == true)
		{
			final List<String> rangeNameList = getRangeNameList(indexedProperty, value.getPrice(), value.getCurrency().getIsocode());
			final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty,
					value.getPointOfService().getStoreId() + "_" + value.getCurrency().getIsocode());
			addFieldValues(fieldValues, rangeNameList, value.getPrice(), fieldNames);
		}
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