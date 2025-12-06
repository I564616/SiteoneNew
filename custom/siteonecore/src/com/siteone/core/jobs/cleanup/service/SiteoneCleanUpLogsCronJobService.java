/**
 *
 */
package com.siteone.core.jobs.cleanup.service;

import de.hybris.platform.cronjob.model.JobLogModel;
import de.hybris.platform.cronjob.model.LogFileModel;

import java.util.List;


/**
 * @author snavamani
 *
 */
public interface SiteoneCleanUpLogsCronJobService
{
	public List<JobLogModel> getOldCronJobLogs(String deleteTillDate);

	public List<LogFileModel> getOldCronLogFiles(String deleteTillDate);
}
