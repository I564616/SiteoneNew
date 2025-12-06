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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.siteone.core.model.SiteOneEventModel;



/**
 * @author 1124932
 *
 */
public class EventDateFacetValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final SiteOneEventModel event = (SiteOneEventModel) model;
		final Collection<FieldValue> fieldValues = new ArrayList<>();
		fieldValues.addAll(createFieldValue(event.getEventStartDate(), indexedProperty));
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
		final LocalDate eventDate = new LocalDate(date);
		final List<FieldValue> fieldValues = new ArrayList<>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		final int days = Days.daysBetween(currentDate, eventDate).getDays();
		if (days <= 30)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Next 30 days"));
			}
		}
		if (days <= 60)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Next 60 days"));
			}
		}
		if (days <= 90)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Next 90 days"));
			}
		}

		final Calendar nowCalendar = Calendar.getInstance();
		final int year = nowCalendar.get(Calendar.YEAR);

		final Calendar thisYearCal = new GregorianCalendar(year, 11, 31);
		final long remainingDaysInYear = TimeUnit.DAYS.convert(thisYearCal.getTime().getTime() - nowCalendar.getTime().getTime(),
				TimeUnit.MILLISECONDS);

		final Calendar nextYearCalendar = new GregorianCalendar(year + 1, 11, 31);
		final long remainingDaysTillNextYear = TimeUnit.DAYS
				.convert(nextYearCalendar.getTime().getTime() - nowCalendar.getTime().getTime(), TimeUnit.MILLISECONDS);



		if (days >= remainingDaysInYear && days <= remainingDaysTillNextYear)
		{
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, "Next year"));
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
