/**
 *
 */
package com.siteone.core.jobs.customer.service;

import java.io.IOException;

import com.siteone.core.model.CustomPushNotificationCronJobModel;


/**
 * @author LR03818
 *
 */
public interface CustomPushNotificationService
{
	void sendCustomPushNotification(CustomPushNotificationCronJobModel customPushNotificationCronJobModel) throws IOException;
}
