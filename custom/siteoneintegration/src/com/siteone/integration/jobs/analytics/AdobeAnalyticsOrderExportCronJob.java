package com.siteone.integration.jobs.analytics;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.siteone.core.model.AdobeAnalyticsOrderExportCronJobModel;
import com.siteone.integration.jobs.analytics.service.AdobeAnalyticsOrderExportCronJobService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

/**
 * @author SMondal
 *
 */
public class AdobeAnalyticsOrderExportCronJob
		extends AbstractJobPerformable<AdobeAnalyticsOrderExportCronJobModel> {

	private AdobeAnalyticsOrderExportCronJobService adobeAnalyticsOrderExportCronJobService;

	private static final Logger LOG = Logger.getLogger(AdobeAnalyticsOrderExportCronJob.class);

	@Override
	public PerformResult perform(AdobeAnalyticsOrderExportCronJobModel cronjobModel) {
		try {

			getAdobeAnalyticsOrderExportCronJobService().exportOrderMappingReport(cronjobModel);
		} catch (IOException exception) {
			LOG.error("Exception occured in analytics report cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public AdobeAnalyticsOrderExportCronJobService getAdobeAnalyticsOrderExportCronJobService() {
		return adobeAnalyticsOrderExportCronJobService;
	}

	public void setAdobeAnalyticsOrderExportCronJobService(
			AdobeAnalyticsOrderExportCronJobService adobeAnalyticsOrderExportCronJobService) {
		this.adobeAnalyticsOrderExportCronJobService = adobeAnalyticsOrderExportCronJobService;
	}


}
