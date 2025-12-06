package com.siteone.facades.storefinder.populators;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storelocator.data.SpecialOpeningDayData;
import de.hybris.platform.commercefacades.storelocator.data.WeekdayOpeningDayData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.MessageSource;


public class SiteOnePointOfServiceStoreStatusPopulator implements Populator<PointOfServiceModel, PointOfServiceData>
{

	private static final Logger LOG = Logger.getLogger(SiteOnePointOfServiceStoreStatusPopulator.class);
	private static final String HOUR_FORMAT = "HH:mm";
	private static final String TWELVE_HOUR_FORMAT = "hh:mm a";
	private static final String CLOSED_STATUS = "store.status.closed";
	private static final String CLOSING_SOON = "store.status.closed.soon";
	private static final String OPEN = "store.status.open";
	private static final String OPEN_SOON = "store.status.open.now";


	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private static final DateTimeFormatter formatCurrentTime = DateTimeFormat.forPattern(HOUR_FORMAT);

	final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

	@Override
	public void populate(final PointOfServiceModel source, final PointOfServiceData target) throws ConversionException
	{
		getStoreStatus(source, target);
	}

	private void getStoreStatus(final PointOfServiceModel source, final PointOfServiceData target)
	{
		final String timeZone = source.getTimezoneId();

		Boolean special = Boolean.FALSE;
		final DateTime dt = new DateTime();
		final Date date = new Date();

		if (timeZone != null && !timeZone.isEmpty())
		{
			final DateTime regionDate = dt.withZone(DateTimeZone.forID(source.getTimezoneId()));
			final DateTime.Property pDoW = regionDate.dayOfWeek();
			final String dayOfWeek = pDoW.getAsText();
			LOG.debug("Day Of Week" + dayOfWeek);

			if (null != target.getOpeningHours())
			{

				for (final SpecialOpeningDayData specialDayOpening : target.getOpeningHours().getSpecialDayOpeningList())
				{
					if (dateFormat.format(specialDayOpening.getDate()).equalsIgnoreCase(dateFormat.format(date)))
					{
						special = Boolean.TRUE;
						if (specialDayOpening.isClosed())
						{
							target.setStoreStatus(
									getMessageSource().getMessage(CLOSED_STATUS, null, getI18nService().getCurrentLocale()));
						}
						else
						{
							getStoreTimings(target, specialDayOpening.getOpeningTime().getFormattedHour(),
									specialDayOpening.getClosingTime().getFormattedHour(), regionDate,date);
						}

					}
				}

				if (!BooleanUtils.isTrue(special))
				{
					for (final WeekdayOpeningDayData weekDayOpening : target.getOpeningHours().getWeekDayOpeningList())
					{
						if (weekDayOpening.getWeekDay().equalsIgnoreCase(dayOfWeek))
						{
							if (weekDayOpening.isClosed())
							{
								target.setStoreStatus(
										getMessageSource().getMessage(CLOSED_STATUS, null, getI18nService().getCurrentLocale()));
							}
							else
							{
								getStoreTimings(target, weekDayOpening.getOpeningTime().getFormattedHour(),
										weekDayOpening.getClosingTime().getFormattedHour(), regionDate,date);
							}
						}
					}
				}
			}
		}

	}


	private void getStoreTimings(final PointOfServiceData target, final String weekDayOpening, final String weekDayClosing,
			final DateTime regionDate,final Date date)
	{
		String openingDispTime;
		String closingDispTime;
		try
		{
			if (StringUtils.isNotBlank(weekDayOpening) && StringUtils.isNotBlank(weekDayClosing)) {
				openingDispTime = weekDayOpening.replace("a.m.", "AM").replace("p.m.", "PM");
				final Calendar calendarOpenTime = Calendar.getInstance();
				Date openingDate = null;
				final SimpleDateFormat openDate12Format1 = new SimpleDateFormat(TWELVE_HOUR_FORMAT);
				try {
					openingDate = openDate12Format1.parse(openingDispTime);
					openingDate.setMonth(date.getMonth());
					openingDate.setYear(date.getYear());
					openingDate.setDate(date.getDate());
					calendarOpenTime.setTime(openingDate);
				} catch (final Exception ex) {
					LOG.error("Open Time exception : "+target.getStoreId()+" "+ openingDispTime);
					LOG.error(ex);
				}
				final SimpleDateFormat closeDate12Format1 = new SimpleDateFormat(TWELVE_HOUR_FORMAT);
				closingDispTime = weekDayClosing.replace("a.m.", "AM").replace("p.m.", "PM");
				final Calendar calendarClosingTime = Calendar.getInstance();
				Date closingDate = null;
				try {
					closingDate = closeDate12Format1.parse(closingDispTime);
					closingDate.setMonth(date.getMonth());
					closingDate.setYear(date.getYear());
					closingDate.setDate(date.getDate());
					calendarClosingTime.setTime(closingDate);
				} catch (final Exception ex) {
					LOG.error("Close Time exception : "+target.getStoreId()+" "+closingDispTime);
					LOG.error(ex);
				}

				final String currentTime = formatCurrentTime.print(regionDate);
				LOG.debug("Current Time:::" + currentTime);

				final Date latestTime = new SimpleDateFormat(HOUR_FORMAT).parse(currentTime);
				latestTime.setYear(date.getYear());
				latestTime.setMonth(date.getMonth());
				latestTime.setDate(date.getDate());

				final Calendar calendarCurrentTime = Calendar.getInstance();
				calendarCurrentTime.setTime(latestTime);

				final Date currentDate = calendarCurrentTime.getTime();


				if (currentDate.after(calendarOpenTime.getTime()) && currentDate.before(calendarClosingTime.getTime())) {
					final long diffMs = (closingDate != null ? closingDate.getTime() : 0) - latestTime.getTime();
					final int minutes = (int) (diffMs / (1000 * 60));

					if (minutes <= 30) {
						target.setStoreStatus(getMessageSource().getMessage(CLOSING_SOON, null, getI18nService().getCurrentLocale()));
					} else {
						target.setStoreStatus(getMessageSource().getMessage(OPEN, null, getI18nService().getCurrentLocale())
								+ weekDayOpening + " - " + weekDayClosing);
					}
				} else {
					long diffMs = 0;
					if (latestTime.getTime() > (openingDate != null ? openingDate.getTime() : 0)) {
						diffMs = latestTime.getTime() - (openingDate != null ? openingDate.getTime() : 0);
					} else {
						diffMs = (openingDate != null ? openingDate.getTime() : 0) - latestTime.getTime();
					}
					final int minutes = (int) ((diffMs / (1000 * 60)));

					if (minutes <= 30) {
						target.setStoreStatus(getMessageSource().getMessage(OPEN_SOON, null, getI18nService().getCurrentLocale()));
					} else {
						target.setStoreStatus(getMessageSource().getMessage(CLOSED_STATUS, null, getI18nService().getCurrentLocale()));
					}

					if (latestTime.getTime() > (closingDate != null ? closingDate.getTime() : 0)) {
						target.setStoreStatus(fetchClosedTime(target, regionDate));
					}
				}
			}
		}
		catch (final ParseException ex)
		{
			LOG.error(ex);
		}

	}


	private String fetchClosedTime(final PointOfServiceData data, final DateTime regionDate)
	{

		String openTime = null;
		String msg = null;
		for (int i = 1; i <= 7; i++)
		{
			final DateTime nextDay = regionDate.plusDays(i);
			final DateTime.Property nextDayProperty = nextDay.dayOfWeek();
			final String nextDayOfWeek = nextDayProperty.getAsText();
			if (msg == null)
			{

				Boolean special = Boolean.FALSE;				
				for (final SpecialOpeningDayData specialDayOpening : data.getOpeningHours().getSpecialDayOpeningList())
				{
					Date nextDate=nextDay.toDate();
					try
					{
						if (dateFormat.format(specialDayOpening.getDate()).equalsIgnoreCase(dateFormat.format(nextDate)))
						{
							special = Boolean.TRUE;
							if(specialDayOpening.isClosed()) {
								continue;
							} else {
								openTime = specialDayOpening.getOpeningTime().getFormattedHour();
								if (i == 1)
								{
									msg = getMessageSource().getMessage("store.status.closed.tomorrow", null,
											getI18nService().getCurrentLocale()) + openTime;
								}
								else
								{
									msg = getMessageSource().getMessage("store.status.closed.open", null, getI18nService().getCurrentLocale())
											+ nextDayOfWeek
											+ getMessageSource().getMessage("store.status.at", null, getI18nService().getCurrentLocale())
											+ openTime;
								}
							}
						}
					} catch(Exception Ex) {
						LOG.error(Ex);
					}
				}
				if (!BooleanUtils.isTrue(special))
				{
   				for (final WeekdayOpeningDayData weekDayOpening : data.getOpeningHours().getWeekDayOpeningList())
   				{
   					if (weekDayOpening.getWeekDay().equalsIgnoreCase(nextDayOfWeek) && weekDayOpening.isClosed())
   					{
   						continue;
   					}
   					else if (weekDayOpening.getWeekDay().equalsIgnoreCase(nextDayOfWeek) && !weekDayOpening.isClosed())
   					{
   						openTime = weekDayOpening.getOpeningTime().getFormattedHour();
   						if (i == 1)
   						{
   							msg = getMessageSource().getMessage("store.status.closed.tomorrow", null,
   									getI18nService().getCurrentLocale()) + openTime;
   						}
   						else
   						{
   							msg = getMessageSource().getMessage("store.status.closed.open", null, getI18nService().getCurrentLocale())
   									+ nextDayOfWeek
   									+ getMessageSource().getMessage("store.status.at", null, getI18nService().getCurrentLocale())
   									+ openTime;
   						}
   
   					}
   				}
				}
			}
		}
		return msg;
	}

	public I18NService getI18nService()
	{
		return i18nService;
	}

	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}


	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

}
