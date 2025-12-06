package com.siteone.integration.azure.pushnotification;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.i18n.I18NService;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.log4j.Logger;
import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;

import com.siteone.core.model.CustomPushNotificationModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.windowsazure.messaging.NotificationHub;
import com.windowsazure.messaging.Notification;
import com.windowsazure.messaging.NotificationOutcome;
import de.hybris.platform.util.Config;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;

public class SendNotificationApiImpl implements SendNotificationApi
{
	private static final Logger log = Logger.getLogger(SendNotificationApiImpl.class);

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	private static final String MESSAGE_TYPE_ANDROID = "Android";
	private static final String MESSAGE_TYPE_APPLE = "Apple";
	private static final String DEFAULT_ORDER_REDIRECT = "/my-account/order/";
	
	private static final String MESSAGE_TEMPLATE_ANDROID = "{\"message\":{\"data\":{\"body\":\"<<message>>\"}}}";
	private static final String MESSAGE_TEMPLATE_APPLE = "{\"aps\":{\"alert\":\"<<message>>\"}}";
	
	
	public NotificationHub createNotificationHubClient()
	{
		final NotificationHub hub = new NotificationHub(Config.getString(SiteoneintegrationConstants.NOTIFICATIONHUB_ENDPOINT, null), Config.getString(SiteoneintegrationConstants.NOTIFICATIONHUB_NAME, null));
		return hub;
	}


	public void sendNotification(final NotificationHub hub, final String code, final String shipmentNumber, final OrderModel order)
	{
		final Set<String> tags = new HashSet<String>();
		if(null !=  order.getUser() && null != order.getUser().getUid()) {
			tags.add(order.getUser().getUid());
			final FutureCallback<NotificationOutcome> callback = new FutureCallback<NotificationOutcome>()
			{
				@Override
				public void completed(final NotificationOutcome result)
				{
					log.info("Message Sent successfully!");
				}

				@Override
				public void failed(final Exception ex)
				{
					log.error("Exception occured in Sending"+ex.getMessage());
				}

				@Override
				public void cancelled()
				{
					// Operation has been cancelled
				}
			};
			String message = composeMessage(code, MESSAGE_TYPE_ANDROID, shipmentNumber, order);
			Notification n = Notification.createFcmV1Notification(message);
			hub.sendNotificationAsync(n, tags, callback);
		
			message = composeMessage(code, MESSAGE_TYPE_APPLE, shipmentNumber, order);
			n = Notification.createAppleNotification(message);
			hub.sendNotificationAsync(n, tags, callback);
		}

	}

	public void sendTestNotification(final NotificationHub hub, String tag)
	{
		final Set<String> tags = new HashSet<String>();
		tags.add(tag);
		final FutureCallback<NotificationOutcome> callback = new FutureCallback<NotificationOutcome>()
		{
			@Override
			public void completed(final NotificationOutcome result)
			{
				log.info("Message Sent successfully!");
			}

			@Override
			public void failed(final Exception ex)
			{
				log.error("Exception occured in Sending"+ex.getMessage());

			}

			@Override
			public void cancelled()
			{
				// Operation has been cancelled
			}
		};
		String message = getMessageSource().getMessage("pushnotificaiton.readyforpickup.message", new Object[]
				{ "M12345", "Alpharetta GA #172" },
						getI18nService().getCurrentLocale());
				
		String androidMessageTemplate = MESSAGE_TEMPLATE_ANDROID;
		androidMessageTemplate = androidMessageTemplate.replaceAll("<<title>>", "Test");
		androidMessageTemplate = androidMessageTemplate.replaceAll("<<message>>", message);
		androidMessageTemplate = androidMessageTemplate.replaceAll("<<url>>", "URL");

		Notification n = Notification.createFcmV1Notification(androidMessageTemplate);
		hub.sendNotificationAsync(n, tags, callback);
		
		String appleMessageTemplate = MESSAGE_TEMPLATE_APPLE;
		appleMessageTemplate = appleMessageTemplate.replace("<<message>>", message);
		
		n = Notification.createAppleNotification(appleMessageTemplate);
		hub.sendNotificationAsync(n, tags, callback);
	}

	@Override
	public void sendCustomNotification(final NotificationHub hub,
			final CustomPushNotificationModel customPushNotificationModel) {
		final Set<String> tags = new HashSet<String>();
		tags.add("Allusers");
		final FutureCallback<NotificationOutcome> callback = new FutureCallback<NotificationOutcome>()
		{
			@Override
			public void completed(final NotificationOutcome result)
			{
				log.info("Message Sent successfully!");
			}

			@Override
			public void failed(final Exception ex)
			{
				log.error("Exception occured in Sending"+ex.getMessage());
			}

			@Override
			public void cancelled()
			{
				// Operation has been cancelled
			}
		};
		
		String message="{\"message\":{\"data\":{\"body\":\"{\\\"pageId\\\":\\\""+customPushNotificationModel.getPageId()+"\\\",\\\"identifier\\\":\\\""+customPushNotificationModel.getIdentifier()+"\\\",\\\"message\\\":\\\""+customPushNotificationModel.getMessage()+"\\\"}\"}}}";
		log.error("androidMessage:::"+message);
		Notification n = Notification.createFcmV1Notification(message);
		hub.sendNotificationAsync(n, tags, callback);
		
		message="{\"aps\":{\"alert\":\""+customPushNotificationModel.getMessage()+"\",\"data\":\"{\\\"pageId\\\":\\\""+customPushNotificationModel.getPageId()+"\\\",\\\"identifier\\\":\\\""+customPushNotificationModel.getIdentifier()+"\\\",\\\"message\\\":\\\""+customPushNotificationModel.getMessage()+"\\\"}\"}}";
		log.error("appleMessage:::"+message);
		n = Notification.createAppleNotification(message);
		hub.sendNotificationAsync(n, tags, callback);
	}

	public String composeMessage(final String code, final String type, final String shipmentNumber, final OrderModel order)
	{
		String messageTemplate = "";
		String message = "";
		String title = "";
		if (null != code)
		{
			if (code.equals(ConsignmentStatus.READY_FOR_PICKUP.getCode()))
			{
				title = getMessageSource().getMessage("pushnotificaiton.readyforpickup.title", null,
						getI18nService().getCurrentLocale());
				message = getMessageSource().getMessage("pushnotificaiton.readyforpickup.message", new Object[]
				{ order.getCode(), null != order.getPointOfService() ? order.getPointOfService().getName() : "", shipmentNumber != null ? shipmentNumber : "" , null != order.getOrderingAccount() ? order.getOrderingAccount().getUid() : ""},
						getI18nService().getCurrentLocale());
			}
			else if (code.equals(ConsignmentStatus.SCHEDULED_FOR_DELIVERY.getCode()))
			{
				title = getMessageSource().getMessage("pushnotificaiton.scheduledfordelivery.title", null,
						getI18nService().getCurrentLocale());
				message = getMessageSource().getMessage("pushnotificaiton.scheduledfordelivery.message", new Object[]
				{ order.getCode(), shipmentNumber != null ? shipmentNumber : "" , null != order.getOrderingAccount() ? order.getOrderingAccount().getUid() : ""},
						getI18nService().getCurrentLocale());
			}
		}
		if (type.equals(MESSAGE_TYPE_ANDROID))
		{
			messageTemplate = MESSAGE_TEMPLATE_ANDROID;
			messageTemplate = messageTemplate.replaceAll("<<title>>", title);
			messageTemplate = messageTemplate.replaceAll("<<message>>", message);
		}
		else if (type.equals(MESSAGE_TYPE_APPLE))
		{
			messageTemplate = MESSAGE_TEMPLATE_APPLE;
			messageTemplate = messageTemplate.replaceAll("<<message>>", message);
		}
		return messageTemplate;
	}


	public I18NService getI18nService()
	{
		return i18nService;
	}

	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}


}
