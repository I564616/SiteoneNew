/**
 *
 */
package com.siteone.facades.storefinder.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.OpeningSchedulePopulator;
import de.hybris.platform.commercefacades.storelocator.data.OpeningScheduleData;
import de.hybris.platform.commercefacades.storelocator.data.SpecialOpeningDayData;
import de.hybris.platform.commercefacades.storelocator.data.WeekdayOpeningDayData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.storelocator.model.OpeningDayModel;
import de.hybris.platform.storelocator.model.OpeningScheduleModel;
import de.hybris.platform.storelocator.model.SpecialOpeningDayModel;
import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author SD02010
 *
 */
public class SiteOneOpeningSchedulePopulator extends OpeningSchedulePopulator
{

	private TimeService timeService;
	private Converter<SpecialOpeningDayModel, SpecialOpeningDayData> specialDayConverter;
	private Converter<List<WeekdayOpeningDayModel>, List<WeekdayOpeningDayData>> weekDaysConverter;

	@Override
	protected Converter<List<WeekdayOpeningDayModel>, List<WeekdayOpeningDayData>> getWeekDaysConverter()
	{
		return weekDaysConverter;
	}

	@Override
	public void setWeekDaysConverter(final Converter<List<WeekdayOpeningDayModel>, List<WeekdayOpeningDayData>> weekDaysConverter)
	{
		this.weekDaysConverter = weekDaysConverter;
	}

	@Override
	protected Converter<SpecialOpeningDayModel, SpecialOpeningDayData> getSpecialDayConverter()
	{
		return specialDayConverter;
	}

	@Override
	public void setSpecialDayConverter(final Converter<SpecialOpeningDayModel, SpecialOpeningDayData> specialDayConverter)
	{
		this.specialDayConverter = specialDayConverter;
	}

	@Override
	protected TimeService getTimeService()
	{
		return timeService;
	}

	@Override
	public void setTimeService(final TimeService timeService)
	{
		this.timeService = timeService;
	}

	@Override
	public void populate(final OpeningScheduleModel source, final OpeningScheduleData target)
	{
		target.setCode(source.getCode());
		target.setName(source.getName());

		final Date currentTime = getTimeService().getCurrentTime();
		final Calendar specialDayCutOffStart = Calendar.getInstance();
		specialDayCutOffStart.setTime(currentTime);
		specialDayCutOffStart.set(Calendar.MILLISECOND, 0);
		specialDayCutOffStart.set(Calendar.SECOND, 0);
		specialDayCutOffStart.set(Calendar.MINUTE, 0);
		specialDayCutOffStart.set(Calendar.HOUR_OF_DAY, 0);
		specialDayCutOffStart.add(Calendar.DATE, 0);
		final Date specialDayCutOffDateStart = specialDayCutOffStart.getTime();

		final Calendar specialDayCutOffEnd = Calendar.getInstance();
		specialDayCutOffEnd.setTime(currentTime);
		specialDayCutOffEnd.set(Calendar.MILLISECOND, 0);
		specialDayCutOffEnd.set(Calendar.SECOND, 0);
		specialDayCutOffEnd.set(Calendar.MINUTE, 0);
		specialDayCutOffEnd.set(Calendar.HOUR_OF_DAY, 0);
		specialDayCutOffEnd.add(Calendar.DATE, 7);
		final Date specialDayCutOffDateEnd = specialDayCutOffEnd.getTime();

		final List<WeekdayOpeningDayModel> weekDaysOpening = new ArrayList<>();
		final Map<Date, SpecialOpeningDayData> specialDaysOpening = new TreeMap<>();
		for (final OpeningDayModel singleOpeningEntry : source.getOpeningDays())
		{
			if (singleOpeningEntry instanceof WeekdayOpeningDayModel)
			{
				WeekdayOpeningDayModel weekdayOpeningDay=(WeekdayOpeningDayModel) singleOpeningEntry;				 
				if(weekdayOpeningDay.getStartDate()!=null && weekdayOpeningDay.getStartDate().compareTo(specialDayCutOffDateStart)<=0 
						&& weekdayOpeningDay.getEndDate()!=null && weekdayOpeningDay.getEndDate().compareTo(specialDayCutOffDateStart)>=0) {
					weekDaysOpening.add(weekdayOpeningDay);
				}
				
			}

			if (singleOpeningEntry instanceof SpecialOpeningDayModel)
			{
				final SpecialOpeningDayModel singleSpecialOpeningEntry = (SpecialOpeningDayModel) singleOpeningEntry;

				// Filter out any special days before the cut off date
				if (singleSpecialOpeningEntry.getDate() != null
						&& !singleSpecialOpeningEntry.getDate().before(specialDayCutOffDateStart)
						&& singleSpecialOpeningEntry.getDate().before(specialDayCutOffDateEnd))
				{
					specialDaysOpening.put(singleSpecialOpeningEntry.getDate(),
							specialDayConverter.convert(singleSpecialOpeningEntry));
				}
			}
		}
		target.setWeekDayOpeningList(weekDaysConverter.convert(weekDaysOpening));
		target.setSpecialDayOpeningList(new ArrayList<>(specialDaysOpening.values()));
	}
}
