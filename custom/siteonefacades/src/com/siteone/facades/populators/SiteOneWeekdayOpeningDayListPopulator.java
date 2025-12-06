/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.WeekdayOpeningDayListPopulator;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * @author 1091124
 *
 */
public class SiteOneWeekdayOpeningDayListPopulator extends WeekdayOpeningDayListPopulator
{

	@Override
	protected List<String> getWeekDaySymbols()
	{
		final List<String> notEmptyWeekDay = new ArrayList<String>();
		final DateFormatSymbols weekDaySymbols = new DateFormatSymbols(getCurrentLocale());
		for (final String anyWeekDay : weekDaySymbols.getWeekdays())
		{
			if (StringUtils.isNotBlank(anyWeekDay))
			{
				notEmptyWeekDay.add(anyWeekDay);
			}
		}

		return notEmptyWeekDay;
	}

}
