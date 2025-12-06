/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.TimeDataPopulator;
import de.hybris.platform.commercefacades.storelocator.data.TimeData;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author 1091124
 *
 */
public class SiteOneTimeDataPopulator extends TimeDataPopulator
{

	@Override
	public void populate(final Date source, final TimeData target)
	{
		if (source != null)
		{
			final Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(source);
			target.setHour((byte) calendar.get(Calendar.HOUR));
			target.setMinute((byte) calendar.get(Calendar.MINUTE));
			final String meridian = this.getDateFormat().format(source);
			String formattedMeridian = null;
			if (meridian.contains("AM"))
			{
				formattedMeridian = meridian.replace("AM", "a.m.");
			}
			if (meridian.contains("PM"))
			{
				formattedMeridian = meridian.replace("PM", "p.m.");
			}
			target.setFormattedHour(formattedMeridian);
		}
	}

	@Override
	protected DateFormat getDateFormat()
	{
		return DateFormat.getTimeInstance(DateFormat.SHORT);
	}

}