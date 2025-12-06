/**
 *
 */
package com.siteone.core.batch.adapter.impl;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.storelocator.model.OpeningDayModel;
import de.hybris.platform.storelocator.model.OpeningScheduleModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.storelocator.model.SpecialOpeningDayModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.batch.adapter.SpecialOpeningOrClosingAdapter;


/**
 * @author 1085284
 *
 */
public class DefaultSpecialOpeningOrClosingAdapter implements SpecialOpeningOrClosingAdapter
{
	private static final Logger LOGGER = Logger.getLogger(DefaultSpecialOpeningOrClosingAdapter.class);

	private ModelService modelService;

	@Resource(name = "timeService")
	private TimeService timeService;

	private static final String DATE_DELIMITER = "_";
	private static final String TIME_DELIMITER = ",";
	private static final String RANGE_DELIMITER = "~";
	private static final String DATE_FORMAT = "MM/dd/yyyy";

	private static final String TIME_FORMAT = "HHmm";
	private static final String DATE_TIME = "@";


	@Override
	public void performImport(final String cellValue, final Item pointOfService)
	{
		try
		{
			final PointOfServiceModel pointOfServiceModel = modelService.get(pointOfService);
			final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
			final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			final SimpleDateFormat oldDateFormat = new SimpleDateFormat(DATE_FORMAT);
			final Map<String, String[]> specialOpenOrCloseMap = new HashMap<>();
			final Map<String, String[]> daynTimeMap = new HashMap<>();
			final List<String> newSpecialOpeningDays = new ArrayList<>();
			final SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			final Date currentTime = timeService.getCurrentTime();
			final Calendar specialDayCutOffStart = Calendar.getInstance();
			specialDayCutOffStart.setTime(currentTime);
			specialDayCutOffStart.set(Calendar.MILLISECOND, 0);
			specialDayCutOffStart.set(Calendar.SECOND, 0);
			specialDayCutOffStart.set(Calendar.MINUTE, 0);
			specialDayCutOffStart.set(Calendar.HOUR_OF_DAY, 0);
			specialDayCutOffStart.add(Calendar.DATE, 0);

			final Date specialDayCutOffDateStart = specialDayCutOffStart.getTime();

			if (!cellValue.contains("<ignore>") && !cellValue.isEmpty())
			{
				final String[] specialOpenOrClose = cellValue.split(RANGE_DELIMITER);
				for (final String specialOpenOrClosing : specialOpenOrClose)
				{
					final String[] dateList = specialOpenOrClosing.split(DATE_TIME, 2)[0].split(DATE_DELIMITER);
					final String[] scheduleList = specialOpenOrClosing.split(DATE_TIME, 2)[1].split(DATE_DELIMITER);
					if (!dateFormat.parse(dateList[1]).before(specialDayCutOffDateStart))
					{
						
					final List<String[]> timeLine = new ArrayList<>();
					for (final String scheduleData : scheduleList)
					{
						final String value = scheduleData.trim().substring(4, scheduleData.trim().length());
						final String[] timings = value.split(TIME_DELIMITER);
						timeLine.add(timings);
					}


					final LocalDate start = LocalDate.parse(outputFormat.format(dateFormat.parse(dateList[0])));
					final LocalDate end = LocalDate.parse(
							outputFormat.format(dateFormat.parse(StringUtils.isNotEmpty(dateList[1]) ? dateList[1] : dateList[0])));

					LOGGER.info("Special Opening #8 " + start);
					LOGGER.info("Special Opening #9 " + end);

					final List<LocalDate> dateRange = Stream.iterate(start, d -> d.plusDays(1))
							.limit(ChronoUnit.DAYS.between(start, end.plusDays(1))).collect(Collectors.toList());
					LOGGER.info("Special Opening #10 " + dateRange);
					int count = 0;
					for (final LocalDate date : dateRange)
					{
						if (CollectionUtils.isNotEmpty(timeLine) && ArrayUtils.isNotEmpty(timeLine.get(count)))
						{
							specialOpenOrCloseMap.put(
									dateFormat.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())),
									timeLine.get(count));
							count = count + 1;
						}
					}

					specialOpenOrCloseMap.forEach((specialOpenCloseDays, timings) -> {
						try
						{
							if (StringUtils.isNotBlank(specialOpenCloseDays)
									&& !dateFormat.parse(specialOpenCloseDays).before(specialDayCutOffDateStart))
							{
								SpecialOpeningDayModel specialOpeningDayModel = null;

								for (final OpeningDayModel openingDayModel : pointOfServiceModel.getOpeningSchedule().getOpeningDays())
								{

									if (openingDayModel instanceof SpecialOpeningDayModel)
									{
										final SpecialOpeningDayModel oldSpecialOpeningDay = (SpecialOpeningDayModel) openingDayModel;
										final String oldSpecialOpening = oldDateFormat.format(oldSpecialOpeningDay.getDate());

										if (oldSpecialOpening.equalsIgnoreCase(specialOpenCloseDays.trim()))
										{
											specialOpeningDayModel = oldSpecialOpeningDay;
										}
									}
								}


								if (null == specialOpeningDayModel)
								{
									specialOpeningDayModel = getModelService().create(SpecialOpeningDayModel.class);
								}

								final OpeningScheduleModel openingScheduleModel = pointOfServiceModel.getOpeningSchedule();

								specialOpeningDayModel.setOpeningSchedule(openingScheduleModel);
								try
								{
									specialOpeningDayModel.setDate(dateFormat.parse(specialOpenCloseDays.trim()));

									if (timings[0].trim().equalsIgnoreCase("closed"))
									{
										specialOpeningDayModel.setClosed(true);
										specialOpeningDayModel.setOpeningTime(null);
										specialOpeningDayModel.setClosingTime(null);
									}
									else
									{
										specialOpeningDayModel.setOpeningTime(timeFormat.parse(timings[0].trim()));
										specialOpeningDayModel.setClosingTime(timeFormat.parse(timings[1].trim()));
										specialOpeningDayModel.setClosed(false);
									}
								}
								catch (final ParseException parseException)
								{
									// YTODO Auto-generated catch block
									LOGGER.error(parseException);
								}
								getModelService().save(specialOpeningDayModel);
								newSpecialOpeningDays.add(oldDateFormat.format(specialOpeningDayModel.getDate()));
							}
						}
						catch (final ParseException parseException)
						{
							// YTODO Auto-generated catch block
							LOGGER.error(parseException);
						}
					});
					daynTimeMap.clear();
					specialOpenOrCloseMap.clear();
					}
				}
			}




			//To Remove existing week which is not present in new feed
			for (final OpeningDayModel openingDayModel : pointOfServiceModel.getOpeningSchedule().getOpeningDays())
			{

				if (openingDayModel instanceof SpecialOpeningDayModel)
				{
					final SpecialOpeningDayModel oldSpecialOpenDay = (SpecialOpeningDayModel) openingDayModel;

					if (!newSpecialOpeningDays.contains(oldDateFormat.format(oldSpecialOpenDay.getDate())))
					{
						getModelService().remove(oldSpecialOpenDay);
					}
				}

			}


		}
		catch (final Exception exception)
		{
			LOGGER.error(exception);
		}
	}


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}


	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}