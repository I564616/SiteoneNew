package com.siteone.core.batch.adapter.impl;

import de.hybris.platform.basecommerce.enums.WeekDay;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.storelocator.model.OpeningDayModel;
import de.hybris.platform.storelocator.model.OpeningScheduleModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.batch.adapter.WeekDayOpeningDayImportAdapter;


/**
 * Default implementation of {@link WeekDayOpeningDayImportAdapter}.
 */
public class DefaultWeekDayOpeningDayImportAdapter implements WeekDayOpeningDayImportAdapter
{
	private static final Logger LOGGER = Logger.getLogger(DefaultWeekDayOpeningDayImportAdapter.class);


	private ModelService modelService;


	private static final String WEEKDAYS_DELIMITER = "_";
	private static final String TIMING_SPAN_DELIMITER = ",";
	private static final String RANGE_DELIMITER = "~";
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final String DATE_TIME = "@";
	private static final String TIME_FORMAT = "HHmm";
	private static final String SEMI_COLON = ";";


	@Override
	public void performImport(final String cellValue, final Item pointOfService)
	{
		try
		{
			final PointOfServiceModel pointOfServiceModel = modelService.get(pointOfService);

			final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
			final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			final List<String> newWeekDayOpeningDays = new ArrayList<String>();

			final Map<String, String[]> weekDayOpeningMap = new HashMap<String, String[]>();

			if (!cellValue.contains("<ignore>") && !cellValue.isEmpty())
			{
				final String value = cellValue.replace(SEMI_COLON, "");

				final String[] dateRangeArray = value.split(RANGE_DELIMITER);
				for (final String dateRange : dateRangeArray)
				{
					final String[] dateList = dateRange.split(DATE_TIME, 2)[0].split(WEEKDAYS_DELIMITER);
					final String[] scheduleList = dateRange.split(DATE_TIME, 2)[1].split(WEEKDAYS_DELIMITER);

					final Date startDate = dateFormat.parse(dateList[0]);
					final Date endDate = dateFormat.parse(dateList[1]);

					for (final String weekDayOpeningDay : scheduleList)
					{
						final String[] weekDayOpeningDaysSplit = weekDayOpeningDay.split(TIMING_SPAN_DELIMITER, 2);
						final String[] timings = weekDayOpeningDaysSplit[1].split(TIMING_SPAN_DELIMITER);
						final String weekDayOpeningDaysKey = new String(weekDayOpeningDaysSplit[0]);
						weekDayOpeningMap.put(weekDayOpeningDaysKey.trim(), timings);
					}

					weekDayOpeningMap.forEach((dayOfTheWeek, timings) -> {
						if (StringUtils.isNotBlank(dayOfTheWeek))
						{
							final WeekDay weekDay = getDayOfWeekEnum(dayOfTheWeek);

							if (!timings[0].equalsIgnoreCase("closed"))
							{
								WeekdayOpeningDayModel weekdayOpeningDay = null;

								for (final OpeningDayModel openingDayModel : pointOfServiceModel.getOpeningSchedule().getOpeningDays())
								{
									if (openingDayModel instanceof WeekdayOpeningDayModel)
									{
										final WeekdayOpeningDayModel oldWeekdayOpeningDay = (WeekdayOpeningDayModel) openingDayModel;

										if (oldWeekdayOpeningDay.getDayOfWeek().getCode().equalsIgnoreCase(weekDay.getCode())
												&& startDate.compareTo(oldWeekdayOpeningDay.getStartDate()) == 0
												&& endDate.compareTo(oldWeekdayOpeningDay.getEndDate()) == 0)
										{
											weekdayOpeningDay = oldWeekdayOpeningDay;
										}
									}
								}
								if (null == weekdayOpeningDay)
								{
									weekdayOpeningDay = getModelService().create(WeekdayOpeningDayModel.class);
								}
								final OpeningScheduleModel openingScheduleModel = pointOfServiceModel.getOpeningSchedule();
								weekdayOpeningDay.setOpeningSchedule(openingScheduleModel);
								weekdayOpeningDay.setDayOfWeek(WeekDay.valueOf(weekDay.getCode()));
								weekdayOpeningDay.setStartDate(startDate);
								weekdayOpeningDay.setEndDate(endDate);
								try
								{
									weekdayOpeningDay.setOpeningTime(timeFormat.parse(timings[0].trim()));
									weekdayOpeningDay.setClosingTime(timeFormat.parse(timings[1].trim()));
								}
								catch (final ParseException parseException)
								{
									LOGGER.error(parseException);
								}
								getModelService().save(weekdayOpeningDay);
								final StringBuilder dayWithStartEnd = new StringBuilder(dateRange.split(DATE_TIME, 2)[0]);
								dayWithStartEnd.append(DATE_TIME);
								dayWithStartEnd.append(weekDay.getCode());
								newWeekDayOpeningDays.add(dayWithStartEnd.toString());
							}
						}
					});
				}
			}

			//To Remove existing week which is not present in new feed
			for (final OpeningDayModel openingDayModel : pointOfServiceModel.getOpeningSchedule().getOpeningDays())
			{

				if (openingDayModel instanceof WeekdayOpeningDayModel)
				{
					final WeekdayOpeningDayModel oldWeekdayOpeningDay = (WeekdayOpeningDayModel) openingDayModel;
					final StringBuilder dayWithStartEnd = new StringBuilder(dateFormat.format(oldWeekdayOpeningDay.getStartDate()));
					dayWithStartEnd.append(WEEKDAYS_DELIMITER);
					dayWithStartEnd.append(dateFormat.format(oldWeekdayOpeningDay.getEndDate()));
					dayWithStartEnd.append(DATE_TIME);
					dayWithStartEnd.append(oldWeekdayOpeningDay.getDayOfWeek().getCode());
					if (!newWeekDayOpeningDays.contains(dayWithStartEnd.toString()))
					{
						getModelService().remove(oldWeekdayOpeningDay);
					}
				}

			}
		}
		catch (final Exception exception)
		{
			LOGGER.error(exception);
		}
	}

	private WeekDay getDayOfWeekEnum(final String daysOfTheWeek)
	{

		WeekDay dayOfweek = null;

		switch (daysOfTheWeek.trim().toLowerCase())
		{
			case "sun":
				dayOfweek = WeekDay.SUNDAY;
				break;
			case "mon":
				dayOfweek = WeekDay.MONDAY;
				break;
			case "tue":
				dayOfweek = WeekDay.TUESDAY;
				break;
			case "wed":
				dayOfweek = WeekDay.WEDNESDAY;
				break;
			case "thu":
				dayOfweek = WeekDay.THURSDAY;
				break;
			case "fri":
				dayOfweek = WeekDay.FRIDAY;
				break;
			case "sat":
				dayOfweek = WeekDay.SATURDAY;
				break;

		}

		return dayOfweek;

	}



	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	/**
	 * @return the modelService
	 */
	protected ModelService getModelService()
	{
		return modelService;
	}


}
