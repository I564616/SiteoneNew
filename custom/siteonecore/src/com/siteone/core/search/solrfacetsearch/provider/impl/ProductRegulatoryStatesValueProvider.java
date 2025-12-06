/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author 1091124
 *
 */
public class ProductRegulatoryStatesValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
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

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;

			final Collection<FieldValue> fieldValues = new ArrayList<>();

			final Collection<VariantProductModel> variants = product.getVariants();
			if (CollectionUtils.isEmpty(variants) || (CollectionUtils.isNotEmpty(variants) && variants.size() > 1))
			{
				for (final RegionModel regionModel : product.getRegulatoryStates())
				{
					if (regionModel.getActive())
					{
						fieldValues.addAll(createFieldValue(regionModel.getIsocodeShort(), indexedProperty));
					}
				}
			}
			else
			{
				for (final VariantProductModel variant : variants)
				{
					for (final RegionModel regionModel : variant.getRegulatoryStates())
					{
						if (regionModel.getActive())
						{
							fieldValues.addAll(createFieldValue(regionModel.getIsocodeShort(), indexedProperty));
						}
					}
				}
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get regulatory states for the product");
		}
	}

	protected List<FieldValue> createFieldValue(final Object value, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
		return fieldValues;
	}

}
