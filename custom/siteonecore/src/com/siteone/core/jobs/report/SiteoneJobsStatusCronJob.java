/**
 *
 */
package com.siteone.core.jobs.report;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.event.SiteoneJobsStatusEvent;
import com.siteone.core.jobs.report.service.SiteoneJobsStatusCronJobService;
import com.siteone.core.model.SiteoneJobsStatusCronJobModel;


/**
 * @author NMangal
 *
 */
public class SiteoneJobsStatusCronJob extends AbstractJobPerformable<SiteoneJobsStatusCronJobModel>
{

	private static final Logger LOG = Logger.getLogger(SiteoneJobsStatusCronJob.class);

	private SiteoneJobsStatusCronJobService siteoneJobsStatusCronJobService;

	private EventService eventService;

	private BaseStoreService baseStoreService;
	private BaseSiteService baseSiteService;
	private CommonI18NService commonI18NService;

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


	@Override
	public PerformResult perform(final SiteoneJobsStatusCronJobModel arg0)

	{
		List<CronJobModel> jobsStatusList = null;
		try
		{
			jobsStatusList = getSiteoneJobsStatusCronJobService().sendSiteoneCronJobStatusReport();
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in SiteoneJobsStatusCronJob ", exception);
		}

		getEventService().publishEvent(initializeEvent(new SiteoneJobsStatusEvent(), jobsStatusList));

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected SiteoneJobsStatusEvent initializeEvent(final SiteoneJobsStatusEvent event, final List<CronJobModel> jobsStatusList)
	{
		event.setEmailReceiver(Config.getString("siteone.batch.notification.toEmail", null));
		event.setBaseStore(getBaseStoreService().getBaseStoreForUid("siteone"));
		event.setSite(getBaseSiteService().getBaseSiteForUID("siteone"));
		event.setCronJobList(jobsStatusList);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		return event;

	}

	public SiteoneJobsStatusCronJobService getSiteoneJobsStatusCronJobService()
	{
		return siteoneJobsStatusCronJobService;
	}


	public void setSiteoneJobsStatusCronJobService(final SiteoneJobsStatusCronJobService siteoneJobsStatusCronJobService)
	{
		this.siteoneJobsStatusCronJobService = siteoneJobsStatusCronJobService;
	}


	public EventService getEventService()
	{
		return eventService;
	}


	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}


}
