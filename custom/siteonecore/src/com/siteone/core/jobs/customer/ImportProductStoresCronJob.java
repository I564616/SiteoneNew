/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.model.ImportProductStoresCronJobModel;
import com.siteone.core.services.ImportProductStoresCronJobService;


/**
 * @author 1197861
 *
 */
public class ImportProductStoresCronJob extends AbstractJobPerformable<ImportProductStoresCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(ImportProductStoresCronJob.class);
	private ImportProductStoresCronJobService importProductStoresCronJobService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel)
	 */
	@Override
	public PerformResult perform(final ImportProductStoresCronJobModel model)
	{
		try
		{
			getImportProductStoresCronJobService().importProductStore(model);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in Import Product Store CronJob ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the importProductStoresCronJobService
	 */
	public ImportProductStoresCronJobService getImportProductStoresCronJobService()
	{
		return importProductStoresCronJobService;
	}

	/**
	 * @param importProductStoresCronJobService
	 *           the ImportProductStoresCronJobService to set
	 */
	public void setImportProductStoresCronJobService(final ImportProductStoresCronJobService importProductStoresCronJobService)
	{
		this.importProductStoresCronJobService = importProductStoresCronJobService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel)
	 */



}
