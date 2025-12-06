/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.jobs.customer.dao.RegulatoryStatesCronJobDao;
import com.siteone.core.model.PurgeRegulatoryStatesCronJobModel;
import com.siteone.core.model.RegulatoryStatesModel;


/**
 * @author 1219341
 *
 */
public class PurgeRegulatoryStatesCronJob extends AbstractJobPerformable<PurgeRegulatoryStatesCronJobModel>
{
	private static final int DIVISOR_SIZE = 5;

	private static final Logger LOG = Logger.getLogger(PurgeRegulatoryStatesCronJob.class);

	private RegulatoryStatesCronJobDao regulatoryStatesCronJobDao;

	private ModelService modelService;

	@Override
	public PerformResult perform(final PurgeRegulatoryStatesCronJobModel arg0)
	{
		LOG.info("Inside Purge RegulatoryStates cron job ");
		try
		{
			final int totalRecords = getRegulatoryStatesCronJobDao().getAllRegulatoryStatesInBatches();
			final int batchSize = totalRecords / DIVISOR_SIZE;
			deleteRecordsInBatches(batchSize);

		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in Purge RegulatoryStates cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected void deleteRecordsInBatches(final int batchSize)
	{
		final int offset = 0;
		boolean moreResults = true;
		while (moreResults)
		{
			final List<RegulatoryStatesModel> regulatoryStates = getRegulatoryStatesCronJobDao()
					.getAllRegulatoryStatesInBatchesForDeletion(batchSize, offset);
			if (regulatoryStates.size() > 0)
			{
				getModelService().removeAll(regulatoryStates);

			}

			else
			{
				moreResults = false;
			}
		}

	}




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
	 * @return the regulatoryStatesCronJobDao
	 */
	public RegulatoryStatesCronJobDao getRegulatoryStatesCronJobDao()
	{
		return regulatoryStatesCronJobDao;
	}


	/**
	 * @param regulatoryStatesCronJobDao
	 *           the regulatoryStatesCronJobDao to set
	 */
	public void setRegulatoryStatesCronJobDao(final RegulatoryStatesCronJobDao regulatoryStatesCronJobDao)
	{
		this.regulatoryStatesCronJobDao = regulatoryStatesCronJobDao;
	}

}
