/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

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

import com.siteone.core.model.SiteOneEventModel;
import com.siteone.core.model.SiteOneEventTypeGroupModel;
import com.siteone.core.model.SiteOneEventTypeModel;


/**
 * @author 1124932
 *
 */
public class EventTypeGroupValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final SiteOneEventModel event = (SiteOneEventModel) model;
		final SiteOneEventTypeModel type = event.getType();
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		fieldValues.addAll(createFieldValue(type.getGroup(), indexedProperty));
		return fieldValues;
	}

	/**
	 * @param siteOneEventTypeGroupModel
	 * @param indexedProperty
	 * @return
	 */
	private Collection<? extends FieldValue> createFieldValue(final SiteOneEventTypeGroupModel siteOneEventTypeGroupModel,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		if (null != siteOneEventTypeGroupModel)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, siteOneEventTypeGroupModel.getName()));
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
