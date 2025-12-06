/**
 *
 */
package com.siteone.core.cronjob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.siteone.core.cronjob.dao.SiteOneFeedFileInfoCronJobDao;
import com.siteone.core.event.SiteOneFeedFileNotificationEvent;
import com.siteone.core.model.SiteOneFeedFileInfoCronJobModel;


/**
 * @author RPalanisamy
 *
 */
public class SiteOneFeedFileInfoCronJob extends AbstractJobPerformable<SiteOneFeedFileInfoCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneFeedFileInfoCronJob.class);

	private FlexibleSearchService flexibleSearchService;
	private BaseStoreService baseStoreService;
	private BaseSiteService baseSiteService;
	private EventService eventService;
	private SiteOneFeedFileInfoCronJobDao siteOneFeedFileInfoCronJobDao;
	private CommonI18NService commonI18NService;

	final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	final DateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd");

	@Override
	public PerformResult perform(final SiteOneFeedFileInfoCronJobModel model)
	{

		final Map<String, String> feedFiles = getfeedFileConfig();
		LOG.info("FeedFile configuration" + feedFiles.toString());

		final List<String> feedFilesInfo = siteOneFeedFileInfoCronJobDao.getFeedFilesInfo();
		LOG.info("FeedFile arrived files list" + feedFilesInfo.toString());

		final List<Map<String, String>> missingFeedFiles = new ArrayList<Map<String, String>>();
		for (final Map.Entry<String, String> feedType : feedFiles.entrySet())
		{
			try
			{
				final List<String> listHours = new ArrayList<String>(Arrays.asList(feedType.getValue().split(",")));
				for (final String hour : listHours)
				{
					final String fileName = getFileNameWithDateTimeFormat(feedType.getKey(), hour);
					if (!feedFilesInfo.contains(fileName))
					{
						final Map<String, String> missingFileInfo = new HashMap<String, String>();
						final Date date = fileFormatter.parse(getDate(hour));
						missingFileInfo.put("fileName", feedType.getKey());
						missingFileInfo.put("fileDate", formatter.format(date));
						missingFileInfo.put("fileTime", hour);
						missingFeedFiles.add(missingFileInfo);
						LOG.info("FeedFile is missing in Hot folder " + fileName + "-" + formatter.format(date) + " " + hour);
					}
				}

			}
			catch (final Exception exception)
			{
				LOG.error("Exception occured in SiteOneFeedFileInfoCronJob" + feedType.getKey(), exception);
			}

		}

		if (!missingFeedFiles.isEmpty())
		{
			generateErrorEmailNotification(missingFeedFiles);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * Generate Error Email notification if Import feed not yet reached fromU E.
	 *
	 */
	private void generateErrorEmailNotification(final List<Map<String, String>> missingFeedFiles)
	{

		if (Config.getBoolean("siteone.batch.notification.enable", false)
				&& StringUtils.isNotEmpty(Config.getString("siteone.batch.notification.toEmail", null)))
		{
			getEventService().publishEvent(initializeEvent(new SiteOneFeedFileNotificationEvent(), missingFeedFiles));
		}
	}

	/**
	 * Initialize SiteOneFeedFileNotificationEvent
	 *
	 * @return the SiteOneFeedFileNotificationEvent
	 */
	private SiteOneFeedFileNotificationEvent initializeEvent(final SiteOneFeedFileNotificationEvent event,
			final List<Map<String, String>> missingFeedFiles)
	{
		event.setFeedFiles(missingFeedFiles);
		event.setBaseStore(getBaseStoreService().getBaseStoreForUid("siteone"));
		event.setSite(getBaseSiteService().getBaseSiteForUID("siteone"));
		event.setEmailReceiver(Config.getString("siteone.batch.notification.toEmail", null));
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		return event;

	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	private String getDate(final String hour) throws ParseException
	{

		String fileDate = null;

		final SimpleDateFormat hourMinutesFormat = new SimpleDateFormat("HH");

		final Date currentDate = new Date();
		final Date hourMinutes = hourMinutesFormat.parse(hour);

		if (hourMinutesFormat.parse(hourMinutesFormat.format(currentDate)).before(hourMinutes))
		{
			final Date yersterDay = DateUtils.addDays(currentDate, -1);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(yersterDay);
			DateUtils.addHours(yersterDay, cal.get(Calendar.HOUR));
			DateUtils.addMinutes(yersterDay, cal.get(Calendar.MINUTE));
			fileDate = fileFormatter.format(yersterDay);
		}
		else
		{
			final Calendar cal = Calendar.getInstance();
			cal.setTime(hourMinutes);
			DateUtils.addHours(currentDate, cal.get(Calendar.HOUR));
			DateUtils.addMinutes(currentDate, cal.get(Calendar.MINUTE));
			fileDate = fileFormatter.format(currentDate);
		}

		return fileDate;
	}

	private String getFileNameWithDateTimeFormat(final String fileName, final String hour) throws ParseException
	{
		return fileName + "_" + hour;
	}

	private Map getfeedFileConfig()
	{
		Map<String, String> feedFiles = new HashMap<String, String>();
		final String feedfileProperty = Config.getString("siteone.cronjob.feedFiles", null);
		if (StringUtils.isNotEmpty(feedfileProperty))
		{
			feedFiles = Splitter.on(";").withKeyValueSeparator(":").split(feedfileProperty);
		}
		return feedFiles;
	}


	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	@Override
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}


	/**
	 * @return the siteOneFeedFileInfoCronJobDao
	 */
	public SiteOneFeedFileInfoCronJobDao getSiteOneFeedFileInfoCronJobDao()
	{
		return siteOneFeedFileInfoCronJobDao;
	}

	/**
	 * @param siteOneFeedFileInfoCronJobDao
	 *           the siteOneFeedFileInfoCronJobDao to set
	 */
	public void setSiteOneFeedFileInfoCronJobDao(final SiteOneFeedFileInfoCronJobDao siteOneFeedFileInfoCronJobDao)
	{
		this.siteOneFeedFileInfoCronJobDao = siteOneFeedFileInfoCronJobDao;
	}

}
