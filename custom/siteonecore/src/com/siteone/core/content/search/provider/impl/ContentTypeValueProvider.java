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

import com.siteone.core.enums.SiteOneContentTypeEnum;




/**
 * @author 965504
 *
 */
public class ContentTypeValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{

	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		if (model instanceof ContentPageModel)
		{

			final SiteOneContentTypeEnum contenType = ((ContentPageModel) model).getContentType();


			if (contenType != null)
			{
				final String contentName = contenType.toString();
				addFieldValues(fieldValues, indexedProperty, contentName);
			}
			else
			{
				addFieldValues(fieldValues, indexedProperty, "CONTENT_PAGE");
			}

			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Not a page instance");
		}
	}


	private void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final Object attributeValue)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, attributeValue));
		}

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