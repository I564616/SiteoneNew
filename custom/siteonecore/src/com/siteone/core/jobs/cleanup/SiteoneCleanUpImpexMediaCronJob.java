/**
 *
 */
package com.siteone.core.jobs.cleanup;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.impex.model.ImpExMediaModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.siteone.core.jobs.cleanup.service.SiteoneCleanUpImpexMediaCronJobService;
import com.siteone.core.util.SiteoneSecurityUtils;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;



/**
 * @author SMondal
 *
 */
public class SiteoneCleanUpImpexMediaCronJob extends AbstractJobPerformable<CronJobModel>
{

	private static final Logger LOG = Logger.getLogger(SiteoneCleanUpImpexMediaCronJob.class);
	private final int defaultRetainDays = 14;
	private ModelService modelService;
	private SiteoneCleanUpImpexMediaCronJobService siteoneCleanUpImpexMediaCronJobService;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	@Override
	public PerformResult perform(final CronJobModel cronJobModel)
	{

		try
		{
			final int keepLogDays = Config.getInt("cronjob.log.retain.days", defaultRetainDays);
			final Date deleteTillDate = DateUtils.addDays(new Date(), keepLogDays * -1);
			LOG.info("Date" + deleteTillDate);
			final String pattern = "yyyy-MM-dd";
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			final String strDeleteTillDate = simpleDateFormat.format(deleteTillDate);
			if (!CollectionUtils.isEmpty(getSiteoneCleanUpImpexMediaCronJobService().getImpexMedia(strDeleteTillDate)))
			{
				final List<ImpExMediaModel> impExMedia = getSiteoneCleanUpImpexMediaCronJobService().getImpexMedia(strDeleteTillDate);
				for (final ImpExMediaModel impexMediaModel : impExMedia)
				{
					final String mediaPath = impexMediaModel.getLocation();
					String container = blobDataImportService.getMediaContainer(impexMediaModel.getFolder().getQualifier());
					final String folderPath = mediaPath.substring(0,mediaPath.lastIndexOf("/"));
					final String fileName = mediaPath.substring(mediaPath.lastIndexOf("/")+1);
					blobDataImportService.deleteMediaBlob(container,fileName,folderPath);
				}
				getModelService().removeAll(impExMedia);
				LOG.info("Deleted impexMedia count: " + impExMedia.size());
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception occurred while deleting log files: ", e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
		}
		LOG.info("Impex Media cleanup Completed Successfully ");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

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
	 * @return the siteoneCleanUpImpexMediaCronJobService
	 */
	public SiteoneCleanUpImpexMediaCronJobService getSiteoneCleanUpImpexMediaCronJobService()
	{
		return siteoneCleanUpImpexMediaCronJobService;
	}

	/**
	 * @param siteoneCleanUpImpexMediaCronJobService
	 *           the siteoneCleanUpImpexMediaCronJobService to set
	 */
	public void setSiteoneCleanUpImpexMediaCronJobService(
			final SiteoneCleanUpImpexMediaCronJobService siteoneCleanUpImpexMediaCronJobService)
	{
		this.siteoneCleanUpImpexMediaCronJobService = siteoneCleanUpImpexMediaCronJobService;
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
