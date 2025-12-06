/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;


import de.hybris.platform.b2b.model.B2BUnitModel;
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

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author PP10513
 *
 */
public class ExcludedB2BUnitValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		{
			if (model instanceof ProductModel)
			{
				final ProductModel productModel = (ProductModel) model;
				final Collection<FieldValue> fieldValues = new ArrayList<>();
				//if(CollectionUtils.isNotEmpty(productModel.getB2bUnitGroup())) {
				//		fieldValues.addAll(null)
				//		productModel.getB2bUnitGroup().stream().map(b2bunit -> b2bunit.getUid()).collect(Collectors.joining(","));
				//	}
				if (CollectionUtils.isNotEmpty(productModel.getB2bUnitGroup()))
				{
					for (final B2BUnitModel b2bUnit : productModel.getB2bUnitGroup())
					{
						fieldValues.addAll(createFieldValue(b2bUnit.getUid(), indexedProperty));

					}
				}
				return fieldValues;
			}
			else
			{
				throw new FieldValueProviderException("Cannot get code");
			}

		}
	}

	protected Collection<FieldValue> createFieldValue(final String variantCode, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, variantCode));
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
