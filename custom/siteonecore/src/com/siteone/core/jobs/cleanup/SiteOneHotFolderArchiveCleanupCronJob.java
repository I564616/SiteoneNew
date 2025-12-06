/**
 *
 */
package com.siteone.core.jobs.cleanup;

import de.hybris.platform.core.Registry;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;


/**
 * @author SNavamani
 *
 */
public class SiteOneHotFolderArchiveCleanupCronJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(SiteOneHotFolderArchiveCleanupCronJob.class);
	private final String feedFiles = Config.getString("hotfolder.inbound.feedfiles", "siteonecustomer");
	private final String archiveDir = "archive";

	private final String tenantId = Registry.getCurrentTenant().getTenantID();

	private final int defaultRetentionDays = 30;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	/* The configuration service*/
	private ConfigurationService configurationService;

	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		try
		{
			final String hotFolderContainer= getConfigurationService().getConfiguration().getString("azure.hotfolder.storage.container.name");
			final int keepArchiveDays = Config.getInt("hotfolder.archive.retain.days", defaultRetentionDays);
			int deleteCount;
			LOG.info("Started Hot folder archive clean up job");
			final List<String> feedList = Arrays.asList(feedFiles.split("\\s*,\\s*"));
			final StringBuffer hotfolderPath = new StringBuffer(tenantId).append("/").append("hotfolder").append("/");
			for (final String feedType : feedList) {
				final String directory = new StringBuffer(hotfolderPath).append(feedType).append("/").append(archiveDir).toString();
				//Migration | Find and remove files beyond the retention days from the container.
				deleteCount= blobDataImportService.deleteBlob(hotFolderContainer,directory,keepArchiveDays);
				LOG.info("Hotfolder Archive cleanup Completed for " + directory + " and  deleted " + deleteCount + " files");
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception occurred while deleting archive files", e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}
		LOG.info("Hotfolder Archive cleanup Completed Successfully ");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * Getter method for configurationService
	 *
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	/**
	 * Setter method for  configurationService
	 *
	 * @param configurationService
	 *            the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
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

