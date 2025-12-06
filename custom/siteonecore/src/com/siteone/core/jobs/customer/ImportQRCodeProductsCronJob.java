
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.model.ImportQRCodeProductsCronJobModel;
import com.siteone.core.services.ImportQRCodeProductsCronJobService;


/**
 *
 */
public class ImportQRCodeProductsCronJob extends AbstractJobPerformable<ImportQRCodeProductsCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(ImportQRCodeProductsCronJob.class);
	private ImportQRCodeProductsCronJobService importQRCodeProductsCronJobService;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.
	 * CronJobModel)
	 */
	@Override
	public PerformResult perform(final ImportQRCodeProductsCronJobModel model)
	{
		try
		{
			getImportQRCodeProductsCronJobService().importQRCodeForProducts(model);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in Import OnHand Product Store CronJob ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the importQRCodeProductsCronJobService
	 */
	public ImportQRCodeProductsCronJobService getImportQRCodeProductsCronJobService()
	{
		return importQRCodeProductsCronJobService;
	}

	/**
	 * @param importQRCodeProductsCronJobService
	 *           the importQRCodeProductsCronJobService to set
	 */
	public void setImportQRCodeProductsCronJobService(final ImportQRCodeProductsCronJobService importQRCodeProductsCronJobService)
	{
		this.importQRCodeProductsCronJobService = importQRCodeProductsCronJobService;
	}
}