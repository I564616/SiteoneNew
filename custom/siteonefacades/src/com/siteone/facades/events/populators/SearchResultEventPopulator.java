/**
 *
 */
package com.siteone.facades.events.populators;

import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.siteone.facade.EventData;




/**
 * @author 1124932
 *
 */
public class SearchResultEventPopulator implements Populator<SearchResultValueData, EventData>
{

	@Override
	public void populate(final SearchResultValueData source, final EventData target) throws ConversionException
	{
		target.setName(this.<String> getValue(source, "soeventname"));
		target.setShortDescription(this.<String> getValue(source, "soeventshortdescription"));
		target.setLongDescription(this.<String> getValue(source, "soeventlongdescription"));
			target.setEventStartDate(this.<String> getValue(source, "sodate"));
		final String eventEndDate = this.<String> getValue(source, "eventEndDate");
		if(eventEndDate!=null){
			final DateFormat fmt = new SimpleDateFormat("EEEE, MMMM dd");
			target.setEventEndDate(fmt.format(this.<String> getValue(source, "eventEndDate")));
		}
		target.setType(this.<String> getValue(source, "soeventtype"));
		target.setCode(this.<String> getValue(source, "soeventcode"));
		target.setLocation(this.<String> getValue(source, "soeventlocation"));
		target.setTime(this.<String> getValue(source, "soeventtime"));
		target.setVenue(this.<String> getValue(source, "soeventvenue"));
		target.setDivision(this.<String> getValue(source, "sodivision"));
	}

	protected <T> T getValue(final SearchResultValueData source, final String propertyName)
	{
		if (source.getValues() == null)
		{
			return null;
		}

		// DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
		return (T) source.getValues().get(propertyName);
	}

}
