/**
 *
 */
package com.siteone.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.order.OrderEntryModel;
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
import java.util.Date;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;



/**
 * @author snavamani
 *
 */
public class BuyItAgainLastPurchasedDateFacetValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final OrderEntryModel orderEntry = (OrderEntryModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		fieldValues.addAll(createFieldValue(orderEntry.getOrder().getCreationtime(), indexedProperty));
		return fieldValues;

	}

	/**
	 * @param date
	 * @param indexedProperty
	 * @return
	 */
	private Collection<? extends FieldValue> createFieldValue(final Date date, final IndexedProperty indexedProperty)
	{
		final LocalDate currentDate = LocalDate.now();
		final LocalDate lastPurchasedDate = new LocalDate(date);
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		final int days = Days.daysBetween(lastPurchasedDate, currentDate).getDays();
		if (days <= 30)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Past 30 Days"));
			}
		}
		if (days <= 60)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Past 60 Days"));
			}
		}
		if (days <= 90)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Past 90 Days"));
			}
		}

		if (days <= 183)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Past 6 months"));
			}
		}

		if (days <= 365)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Past Year"));
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
