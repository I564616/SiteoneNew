/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.media.MediaModel;
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
import org.apache.commons.lang3.StringUtils;


/**
 * @author 1229803
 *
 */
public class LabelValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final ProductModel product = (ProductModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		final Collection<MediaModel> dataSheets = product.getData_sheet();
		if (CollectionUtils.isNotEmpty(dataSheets))
		{
			for (final MediaModel media : dataSheets)
			{
				if (StringUtils.isNotBlank(media.getLabel()))
				{
					fieldValues.addAll(createFieldValue(media, indexedProperty));
				}
			}
		}
		return fieldValues;
	}

	/**
	 * @param sdsMedia
	 * @param indexedProperty
	 * @return
	 */
	private List<FieldValue> createFieldValue(final MediaModel sdsMedia, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, sdsMedia.getLabel() + "||" + sdsMedia.getURL()));
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
