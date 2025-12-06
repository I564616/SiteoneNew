package com.siteone.integration.azure.pushnotification;

import de.hybris.platform.core.model.order.OrderModel;

import com.siteone.core.model.CustomPushNotificationModel;
import com.windowsazure.messaging.NotificationHub;


public interface SendNotificationApi
{
	NotificationHub createNotificationHubClient();

	void sendNotification(NotificationHub hub, String code, String shipmentNumber, OrderModel order);

	void sendTestNotification(NotificationHub hub, String tag);
	
	void sendCustomNotification(NotificationHub hub, CustomPushNotificationModel customPushNotificationModel);

}