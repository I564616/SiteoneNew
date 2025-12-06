/**
 *
 */
package com.siteone.core.cronjob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.service.NoVisibleUomCronJobService;
import com.siteone.core.model.NoVisibleUomCronJobModel;


/**
 * @author HR03708
 *
 */
public class NoVisibleUomCronJob extends AbstractJobPerformable<NoVisibleUomCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(NoVisibleUomCronJob.class);
	private NoVisibleUomCronJobService noVisibleUomCronJobService;

	@Override
	public PerformResult perform(final NoVisibleUomCronJobModel noVisibleUomCronJobModel)
	{
		try
		{

			getNoVisibleUomCronJobService().getNoVisibleUomProducts(noVisibleUomCronJobModel);


		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in order details cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);

	}

	/**
	 * @return the noVisibleUomCronJobService
	 */
	public NoVisibleUomCronJobService getNoVisibleUomCronJobService()
	{
		return noVisibleUomCronJobService;
	}

	/**
	 * @param noVisibleUomCronJobService
	 *           the noVisibleUomCronJobService to set
	 */
	public void setNoVisibleUomCronJobService(final NoVisibleUomCronJobService noVisibleUomCronJobService)
	{
		this.noVisibleUomCronJobService = noVisibleUomCronJobService;
	}


}
