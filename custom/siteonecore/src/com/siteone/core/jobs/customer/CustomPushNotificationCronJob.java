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

import com.siteone.core.jobs.customer.service.CustomPushNotificationService;
import com.siteone.core.model.CustomPushNotificationCronJobModel;

/**
 * @author LR03818
 *
 */
public class CustomPushNotificationCronJob extends AbstractJobPerformable<CustomPushNotificationCronJobModel>
{
	private CustomPushNotificationService customPushNotificationService;
	
	private static final Logger LOG = Logger.getLogger(CustomPushNotificationCronJob.class);
	
	@Override
	public PerformResult perform(final CustomPushNotificationCronJobModel customPushNotificationCronJobModel)
	{
		try
		{
			getCustomPushNotificationService().sendCustomPushNotification(customPushNotificationCronJobModel);
		}
		catch (final IOException exception)
		{
			LOG.error("Exception occured in custom push notification cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the customPushNotificationService
	 */
	public CustomPushNotificationService getCustomPushNotificationService()
	{
		return customPushNotificationService;
	}

	/**
	 * @param customPushNotificationService the customPushNotificationService to set
	 */
	public void setCustomPushNotificationService(CustomPushNotificationService customPushNotificationService)
	{
		this.customPushNotificationService = customPushNotificationService;
	}

}
