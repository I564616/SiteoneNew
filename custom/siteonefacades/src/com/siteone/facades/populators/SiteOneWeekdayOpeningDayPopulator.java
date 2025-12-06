/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.WeekdayOpeningDayPopulator;
import de.hybris.platform.commercefacades.storelocator.data.WeekdayOpeningDayData;
import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.text.SimpleDateFormat;


/**
 * @author 1124932
 *
 */
public class SiteOneWeekdayOpeningDayPopulator extends WeekdayOpeningDayPopulator
{
	@Override
	public void populate(final WeekdayOpeningDayModel source, final WeekdayOpeningDayData target)
	{
		super.populate(source, target);
		final SimpleDateFormat storeDateFormat = new SimpleDateFormat("HH:mm:ss");
		final String weekDay = source.getDayOfWeek().toString();
		target.setWeekDay(weekDay.substring(0, 1).toUpperCase() + weekDay.substring(1).toLowerCase());
		target.setFormattedClosingTime(storeDateFormat.format(source.getClosingTime()));
		target.setFormattedOpeningTime(storeDateFormat.format(source.getOpeningTime()));
		target.setFormattedWeekDay(source.getDayOfWeek().toString());
	}
}
