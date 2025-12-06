/**
 *
 */
package com.siteone.core.content.search.provider.impl;



import de.hybris.platform.cms2.model.pages.ContentPageModel;
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

import org.apache.commons.lang3.StringUtils;


/**
 * @author 965504
 *
 */
public class ContentSearchUrlValueProvider extends AbstractPropertyFieldValueProvider implements Serializable, FieldValueProvider
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
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		if (model instanceof ContentPageModel)
		{
			final String url = ((ContentPageModel) model).getLabel();
			if (StringUtils.startsWith(url, "/"))
			{
				addFieldValues(fieldValues, indexedProperty, url);
			}
			else
			{
				addFieldValues(fieldValues, indexedProperty, "/" + url);
			}
		}
		return fieldValues;
	}


	/**
	 * @param fieldValues
	 * @param indexedProperty
	 * @param attributeValue
	 */
	private void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final Object attributeValue)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, attributeValue));
		}

	}


}
