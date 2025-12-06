/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;
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
import org.apache.commons.lang3.BooleanUtils;

import com.google.common.collect.Lists;


/**
 * @author 1124932
 *
 */
public class GenericSingleVariantValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;
	private ModelService modelService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final ProductModel product = (ProductModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		final Collection<VariantProductModel> variants = Lists.newArrayList(product.getVariants());
		if(CollectionUtils.isNotEmpty(variants))
		{
			variants.removeIf(v -> BooleanUtils.isTrue(v.getIsProductDiscontinued()));
		}
		if (CollectionUtils.isEmpty(variants) || (CollectionUtils.isNotEmpty(variants) && variants.size() > 1))
		{
			if (!product.getIsProductDiscontinued()) {
				fieldValues.addAll(createFieldValue(
						getModelService().getAttributeValue(product, indexedProperty.getValueProviderParameter()), indexedProperty));
			}
		}
		else
		{
			for (final VariantProductModel variant : variants)
			{
				if (!variant.getIsProductDiscontinued()) {
					fieldValues.addAll(createFieldValue(
							getModelService().getAttributeValue(variant, indexedProperty.getValueProviderParameter()), indexedProperty));
				}
			}

		}
		return fieldValues;
	}

	/**
	 * @param attributeValue
	 * @param indexedProperty
	 * @return
	 */
	private Collection<? extends FieldValue> createFieldValue(final Object attributeValue, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, attributeValue));
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

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
