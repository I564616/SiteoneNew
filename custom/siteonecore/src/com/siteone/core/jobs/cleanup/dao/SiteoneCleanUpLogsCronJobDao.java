/**RegulatoryStatesModel
 *
 */
package com.siteone.core.jobs.cleanup.dao;

import de.hybris.platform.cronjob.model.JobLogModel;
import de.hybris.platform.cronjob.model.LogFileModel;

import java.util.List;


/**
 * @author snavamani
 *
 */
public interface SiteoneCleanUpLogsCronJobDao
{

	public List<JobLogModel> getOldCronJobLogs(String deleteTillDate);

	public List<LogFileModel> getOldCronLogFiles(String deleteTillDate);

}
