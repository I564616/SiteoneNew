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

import org.apache.commons.lang3.BooleanUtils;


/**
 * @author SD02010
 *
 */
public class VariantItemNumberValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private transient FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final ProductModel product = (ProductModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		if (!BooleanUtils.isTrue(product.getIsProductDiscontinued()))
		{
			fieldValues.addAll(createField(modelService.getAttributeValue(product, indexedProperty.getValueProviderParameter()),
					indexedProperty));
		}

		return fieldValues;
	}

	/**
	 * @param attributeValue
	 * @param indexedProperty
	 * @return
	 */
	private Collection<? extends FieldValue> createField(final Object attributeValue, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValue = new ArrayList<>();
		final Collection<String> fieldName = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String name : fieldName)
		{
			fieldValue.add(new FieldValue(name, attributeValue.toString().toLowerCase()));
		}
		return fieldValue;
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
