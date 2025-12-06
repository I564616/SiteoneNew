package com.siteone.integration.jobs.analytics;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.siteone.core.model.AdobeAnalyticsCustomerExportCronJobModel;
import com.siteone.core.model.AdobeAnalyticsRealtimeCustomerExportCronJobModel;
import com.siteone.integration.jobs.analytics.service.AdobeAnalyticsCustomerExportCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class AdobeAnalyticsRealtimeCustomerExportCronJob
		extends AbstractJobPerformable<AdobeAnalyticsRealtimeCustomerExportCronJobModel> {

	private AdobeAnalyticsCustomerExportCronJobService adobeAnalyticsCustomerExportCronJobService;

	private static final Logger LOG = Logger.getLogger(AdobeAnalyticsRealtimeCustomerExportCronJob.class);

	@Override
	public PerformResult perform(AdobeAnalyticsRealtimeCustomerExportCronJobModel cronjobModel) {
		try {

			getAdobeAnalyticsCustomerExportCronJobService().exportRealtimeCustomerMappingReport(cronjobModel);
		} catch (IOException exception) {
			LOG.error("Exception occured in analytics report cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public AdobeAnalyticsCustomerExportCronJobService getAdobeAnalyticsCustomerExportCronJobService() {
		return adobeAnalyticsCustomerExportCronJobService;
	}

	public void setAdobeAnalyticsCustomerExportCronJobService(
			AdobeAnalyticsCustomerExportCronJobService adobeAnalyticsCustomerExportCronJobService) {
		this.adobeAnalyticsCustomerExportCronJobService = adobeAnalyticsCustomerExportCronJobService;
	}


}
