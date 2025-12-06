/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import com.siteone.core.enums.SavingsCenterEnum;
import com.siteone.pimintegration.model.SiteOneProductSavingsCenterAttributeModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.*;


/**
 * @author i849388
 *
 */
public class SavingsCenterValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable

{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
												 final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;
			final List<FieldValue> fieldValues = new ArrayList<>();

			for (final SiteOneProductSavingsCenterAttributeModel savingsCenterAttrib : product.getSavingsCenterAttribute())
			{
				fieldValues.addAll(createFieldValue(savingsCenterAttrib.getText(), indexedProperty));
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get Savings Center for non-product item");
		}
	}

	protected List<FieldValue> createFieldValue(final Object value, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
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