/**
 *
 */
package com.siteone.core.jobs.cleanup.dao.impl;

import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.cronjob.model.JobLogModel;
import de.hybris.platform.cronjob.model.LogFileModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.jobs.cleanup.dao.SiteoneCleanUpLogsCronJobDao;


/**
 * @author snavamani
 *
 */
public class DefaultSiteoneCleanUpLogsCronJobDao extends AbstractItemDao implements SiteoneCleanUpLogsCronJobDao
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteoneCleanUpLogsCronJobDao.class);

	public static final String CRON_JOBS_QUERY = "SELECT {" + CronJobModel.PK + "} FROM {" + CronJobModel._TYPECODE + "}";
	public static final String FILTER_CRON_JOBS_QUERY = "SELECT {" + JobLogModel.CRONJOB + "} FROM {" + JobLogModel._TYPECODE
			+ "} WHERE {" + JobLogModel.CRONJOB + "} in ({{" + CRON_JOBS_QUERY + "}})  AND {" + JobLogModel.CREATIONTIME
			+ "} < CONVERT(DATETIME,?creationTime) group by {" + JobLogModel.CRONJOB + "} having count({" + JobLogModel.PK
			+ "}) > 5";
	public static final String FILTER_CRON_JOBS_FILE_QUERY = "SELECT {" + LogFileModel.OWNER + "} FROM {" + LogFileModel._TYPECODE
			+ "} WHERE {" + LogFileModel.OWNER + "} in ({{" + CRON_JOBS_QUERY + "}})  AND {" + LogFileModel.CREATIONTIME
			+ "} < CONVERT(DATETIME,?creationTime) group by {" + LogFileModel.OWNER + "} having count({" + LogFileModel.PK
			+ "}) > 5";


	public static final String JOB_LOGS_QUERY = "SELECT {" + JobLogModel.PK + "} FROM {" + JobLogModel._TYPECODE + "} WHERE {"
			+ JobLogModel.CRONJOB + "} in ({{" + FILTER_CRON_JOBS_QUERY + "}}) " + " AND {" + JobLogModel.CREATIONTIME
			+ "} < CONVERT(DATETIME,?creationTime) ORDER BY {" + JobLogModel.CRONJOB + "}, {" + JobLogModel.CREATIONTIME
			+ "} DESC";

	public static final String LOG_FILES_QUERY = "SELECT {" + LogFileModel.PK + "} FROM {" + LogFileModel._TYPECODE + "} WHERE {"
			+ LogFileModel.OWNER + "} IN ({{" + FILTER_CRON_JOBS_FILE_QUERY + "}}) " + " AND {" + LogFileModel.CREATIONTIME
			+ "} < CONVERT(DATETIME,?creationTime)  ORDER BY {" + LogFileModel.OWNER + "}, {" + LogFileModel.CREATIONTIME
			+ "} DESC";


	@Override
	public List<JobLogModel> getOldCronJobLogs(final String deleteTillDate)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(JOB_LOGS_QUERY);
		query.addQueryParameter("creationTime", deleteTillDate);
		final SearchResult<JobLogModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<LogFileModel> getOldCronLogFiles(final String deleteTillDate)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(LOG_FILES_QUERY);
		query.addQueryParameter("creationTime", deleteTillDate);
		final SearchResult<LogFileModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

}
