/**
 *
 */
package com.siteone.core.jobs.cleanup.service.impl;

import de.hybris.platform.cronjob.model.JobLogModel;
import de.hybris.platform.cronjob.model.LogFileModel;

import java.util.List;

import com.siteone.core.jobs.cleanup.dao.SiteoneCleanUpLogsCronJobDao;
import com.siteone.core.jobs.cleanup.service.SiteoneCleanUpLogsCronJobService;


/**
 * @author snavamani
 *
 */
public class DefaultSiteoneCleanUpLogsCronJobService implements SiteoneCleanUpLogsCronJobService
{
	private SiteoneCleanUpLogsCronJobDao siteoneCleanUpLogsCronJobDao;

	/**
	 * @return the siteoneCleanUpLogsCronJobDao
	 */
	public SiteoneCleanUpLogsCronJobDao getSiteoneCleanUpLogsCronJobDao()
	{
		return siteoneCleanUpLogsCronJobDao;
	}

	/**
	 * @param siteoneCleanUpLogsCronJobDao
	 *           the siteoneCleanUpLogsCronJobDao to set
	 */
	public void setSiteoneCleanUpLogsCronJobDao(final SiteoneCleanUpLogsCronJobDao siteoneCleanUpLogsCronJobDao)
	{
		this.siteoneCleanUpLogsCronJobDao = siteoneCleanUpLogsCronJobDao;
	}

	public List<JobLogModel> getOldCronJobLogs(final String deleteTillDate)
	{
		final List<JobLogModel> oldCronJobLogs = getSiteoneCleanUpLogsCronJobDao().getOldCronJobLogs(deleteTillDate);
		return oldCronJobLogs;
	}

	public List<LogFileModel> getOldCronLogFiles(final String deleteTillDate)
	{
		final List<LogFileModel> oldCronLogFiles = getSiteoneCleanUpLogsCronJobDao().getOldCronLogFiles(deleteTillDate);
		return oldCronLogFiles;
	}
}
