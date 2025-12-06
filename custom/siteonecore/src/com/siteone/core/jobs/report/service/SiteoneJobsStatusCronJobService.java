/**
 * 
 */
package com.siteone.core.jobs.report.service;

import java.util.List;

import com.siteone.facade.reports.SiteOneJobsStatusData;
import de.hybris.platform.cronjob.model.CronJobModel;

/**
 * @author NMangal
 * 
 */
public interface SiteoneJobsStatusCronJobService
{
	 List<CronJobModel> sendSiteoneCronJobStatusReport();
}
