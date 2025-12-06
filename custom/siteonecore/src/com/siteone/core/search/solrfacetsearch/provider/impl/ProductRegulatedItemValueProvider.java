/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
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
 * @author 1091124
 *
 */
public class ProductRegulatedItemValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;

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

		{
			if (model instanceof ProductModel)
			{
				final ProductModel product = (ProductModel) model;

				final Collection<FieldValue> fieldValues = new ArrayList<>();

				fieldValues.addAll(createFieldValue(product.getIsRegulatedItem(), indexedProperty));

				return fieldValues;
			}
			else
			{
				throw new FieldValueProviderException("Cannot get regulated item");
			}

		}
	}

	protected Collection<FieldValue> createFieldValue(final Boolean isitaregulateditem, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			if (isitaregulateditem != null)
			{
				fieldValues.add(new FieldValue(fieldName, Boolean.TRUE));
			}
			else
			{
				fieldValues.add(new FieldValue(fieldName, Boolean.FALSE));
			}
		}
		return fieldValues;
	}

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

}
