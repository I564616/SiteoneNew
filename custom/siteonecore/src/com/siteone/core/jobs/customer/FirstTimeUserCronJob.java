/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.siteone.core.jobs.customer.service.FirstTimeUserCronJobService;
import com.siteone.core.model.FirstTimeUserCronJobModel;



/**
 * @author 1099417
 *
 */
public class FirstTimeUserCronJob extends AbstractJobPerformable<FirstTimeUserCronJobModel>
{

	private FirstTimeUserCronJobService firstTimeUserCronJobService;


	private static final Logger LOG = Logger.getLogger(FirstTimeUserCronJob.class);


	@Override
	public PerformResult perform(final FirstTimeUserCronJobModel firstTimeUserCronJobModel)
	{
		LOG.error("Inside first time user cron job " + firstTimeUserCronJobModel.getCode());
		try
		{
			getFirstTimeUserCronJobService().exportFirstTimeUsers(firstTimeUserCronJobModel);
		}
		catch (final IOException exception)
		{
			LOG.error("Exception occured in first time user cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}


	/**
	 * @return the firstTimeUserCronJobService
	 */
	public FirstTimeUserCronJobService getFirstTimeUserCronJobService()
	{
		return firstTimeUserCronJobService;
	}


	/**
	 * @param firstTimeUserCronJobService
	 *           the firstTimeUserCronJobService to set
	 */
	public void setFirstTimeUserCronJobService(final FirstTimeUserCronJobService firstTimeUserCronJobService)
	{
		this.firstTimeUserCronJobService = firstTimeUserCronJobService;
	}







}