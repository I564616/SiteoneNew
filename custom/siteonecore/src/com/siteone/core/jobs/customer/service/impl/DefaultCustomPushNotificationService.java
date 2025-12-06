/**
 *
 */
package com.siteone.core.jobs.customer.service.impl;

import java.io.IOException;

import jakarta.annotation.Resource;

import com.siteone.core.jobs.customer.dao.CustomPushNotificationDao;
import com.siteone.core.jobs.customer.service.CustomPushNotificationService;
import com.siteone.core.model.CustomPushNotificationCronJobModel;
import com.siteone.core.model.CustomPushNotificationModel;
import com.siteone.integration.azure.pushnotification.SendNotificationApi;
import com.windowsazure.messaging.NotificationHub;


/**
 * @author LR03818
 *
 */
public class DefaultCustomPushNotificationService implements CustomPushNotificationService
{
	private CustomPushNotificationDao customPushNotificationDao;

	@Resource(name = "sendNotificationApi")
	private SendNotificationApi sendNotificationApi;

	@Override
	public void sendCustomPushNotification(final CustomPushNotificationCronJobModel customPushNotificationCronJobModel)
			throws IOException
	{
		final CustomPushNotificationModel customPushNotificationModel = getCustomPushNotificationDao().getCustomPushNotification();
		final NotificationHub hub = sendNotificationApi.createNotificationHubClient();
		sendNotificationApi.sendCustomNotification(hub, customPushNotificationModel);
	}

	/**
	 * @return the customPushNotificationDao
	 */
	public CustomPushNotificationDao getCustomPushNotificationDao()
	{
		return customPushNotificationDao;
	}

	/**
	 * @param customPushNotificationDao
	 *           the customPushNotificationDao to set
	 */
	public void setCustomPushNotificationDao(final CustomPushNotificationDao customPushNotificationDao)
	{
		this.customPushNotificationDao = customPushNotificationDao;
	}

}
