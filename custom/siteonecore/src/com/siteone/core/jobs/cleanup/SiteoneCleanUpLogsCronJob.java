/**
 *
 */
package com.siteone.core.jobs.cleanup;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.cronjob.model.JobLogModel;
import de.hybris.platform.cronjob.model.LogFileModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.siteone.core.jobs.cleanup.service.SiteoneCleanUpLogsCronJobService;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import com.siteone.core.util.SiteoneSecurityUtils;


/**
 * @author SNavamani
 *
 */
public class SiteoneCleanUpLogsCronJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(SiteoneCleanUpLogsCronJob.class);
	private final int defaultLogRetainDays = 14;
	private final int defaultLogFileRetainCount = 5;
	private SiteoneCleanUpLogsCronJobService siteoneCleanUpLogsCronJobService;
	private ModelService modelService;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the siteoneCleanUpLogsCronJobService
	 */
	public SiteoneCleanUpLogsCronJobService getSiteoneCleanUpLogsCronJobService()
	{
		return siteoneCleanUpLogsCronJobService;
	}

	/**
	 * @param siteoneCleanUpLogsCronJobService
	 *           the siteoneCleanUpLogsCronJobService to set
	 */
	public void setSiteoneCleanUpLogsCronJobService(final SiteoneCleanUpLogsCronJobService siteoneCleanUpLogsCronJobService)
	{
		this.siteoneCleanUpLogsCronJobService = siteoneCleanUpLogsCronJobService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.
	 * CronJobModel)
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		try
		{
			LOG.info("started cronjob log clean up job");
			final int keepLogDays = Config.getInt("cronjob.log.retain.days", defaultLogRetainDays);
			final int keepLogFileCount = Config.getInt("cronjob.log.retain.count", defaultLogFileRetainCount);
			final Date deleteTillDate = DateUtils.addDays(new Date(), keepLogDays * -1);
			LOG.info("Date" + deleteTillDate);
			final String pattern = "yyyy-MM-dd";
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			final String strDeleteTillDate = simpleDateFormat.format(deleteTillDate);
			final List<JobLogModel> oldCronJobLogs = getSiteoneCleanUpLogsCronJobService().getOldCronJobLogs(strDeleteTillDate);
			final List<JobLogModel> tobeDeletedOldCronJobLogs = findTobeRemovedOldCronJobLogs(oldCronJobLogs, keepLogFileCount);

			if (null != tobeDeletedOldCronJobLogs)
			{
				getModelService().removeAll(tobeDeletedOldCronJobLogs);
				LOG.info("Deleted CronJob Log count: " + tobeDeletedOldCronJobLogs.size());
			}
			final List<LogFileModel> oldCronLogFiles = getSiteoneCleanUpLogsCronJobService().getOldCronLogFiles(strDeleteTillDate);

			final List<LogFileModel> tobeDeletedOldCronLogFiles = findTobeRemovedOldCronLogFiles(oldCronLogFiles, keepLogFileCount);
			for (final LogFileModel logFileEntry : tobeDeletedOldCronLogFiles)
			{
				final String mediaPath = logFileEntry.getLocation();
				String container = blobDataImportService.getMediaContainer(logFileEntry.getFolder().getQualifier());
				final String folderPath = mediaPath.substring(0,mediaPath.lastIndexOf("/"));
				final String fileName = mediaPath.substring(mediaPath.lastIndexOf("/")+1);
				blobDataImportService.deleteMediaBlob(container,fileName,folderPath);

			}
			if (null != tobeDeletedOldCronLogFiles)
			{
				getModelService().removeAll(tobeDeletedOldCronLogFiles);
				LOG.info("Deleted CronJob Log and Log file count: " + tobeDeletedOldCronLogFiles.size());
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception occurred while deleting log files: ", e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}
		LOG.info("Cronjob Log cleanup Completed Successfully ");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	private List<JobLogModel> findTobeRemovedOldCronJobLogs(final List<JobLogModel> oldCronJobLogs, final Integer keepLogFileCount)
	{
		String prevCronJob = null;
		String currCronJob = null;
		int count = 0;
		final List<JobLogModel> tobeDeletedOldCronJobLogs = new ArrayList();
		for (final JobLogModel logModelEntry : oldCronJobLogs)
		{
			currCronJob = logModelEntry.getCronJob().PK;
			if (prevCronJob == null)
			{
				prevCronJob = currCronJob;
			}
			if (prevCronJob != currCronJob)
			{
				prevCronJob = currCronJob;
				count = 0;
			}
			if (count >= keepLogFileCount)
			{
				tobeDeletedOldCronJobLogs.add(logModelEntry);
			}
			else
			{
				count++;
			}
		}
		return tobeDeletedOldCronJobLogs;
	}

	private List<LogFileModel> findTobeRemovedOldCronLogFiles(final List<LogFileModel> oldCronLogFiles,
			final Integer keepLogFileCount)
	{
		int count = 0;
		String prevCronJob = null;
		String currCronJob = null;
		final List<LogFileModel> tobeDeletedOldCronLogFiles = new ArrayList();
		for (final LogFileModel logModelEntry : oldCronLogFiles)
		{
			currCronJob = logModelEntry.getOwner().PK;
			if (prevCronJob == null)
			{
				prevCronJob = currCronJob;
			}
			if (prevCronJob != currCronJob)
			{
				prevCronJob = currCronJob;
				count = 0;
			}
			if (count >= keepLogFileCount)
			{
				tobeDeletedOldCronLogFiles.add(logModelEntry);
			}
			else
			{
				count++;
			}
		}
		return tobeDeletedOldCronLogFiles;
	}

	/**
	 * Getter method for blobDataImportService
	 *
	 * @return the blobDataImportService
	 */
	public SiteOneBlobDataImportService getBlobDataImportService() {
		return blobDataImportService;
	}

	/**
	 * Setter method for  blobDataImportService
	 *
	 * @param blobDataImportService
	 *            the blobDataImportService to set
	 */
	public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
		this.blobDataImportService = blobDataImportService;
	}

}

