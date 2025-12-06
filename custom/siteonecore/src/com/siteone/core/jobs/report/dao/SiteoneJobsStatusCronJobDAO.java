/**
 * 
 */
package com.siteone.core.jobs.report.dao;

import de.hybris.platform.cronjob.model.CronJobModel;

import java.util.List;

/**
 * @author NMangal
 *
 */
public interface SiteoneJobsStatusCronJobDAO
{
  List<CronJobModel> getSiteoneCronJobs();
}
