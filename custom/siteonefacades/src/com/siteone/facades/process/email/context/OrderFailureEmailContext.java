package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.util.Config;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class OrderFailureEmailContext extends AbstractEmailContext<OrderProcessModel>
{

	private static final Logger LOG = Logger.getLogger(OrderFailureEmailContext.class);

	public static final String EMAIL_SUBJECT = "subject";
	public static final String EMAIL_BODY = "body";
	public static final String CUSTOMER_NUMBER = "customerNumber";
	public static final String ORDER_NUMBER = "orderNumber";
	public static final String ORDER = "order";
	public static final String CREATION_TIME = "creationTime";
	public static final String LOG_MESSAGE = "logMessage";
	public static final String ENVIRONMENT = "environment";

	@Override
	public void init(final OrderProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		//super.init(businessProcessModel, emailPageModel);
		final OrderModel order = businessProcessModel.getOrder();
		Objects.requireNonNull(order, "Order cannot be null");
		//		businessProcessModel.getTaskLogs().forEach(log -> {
		//			final String logMessage = log.getLogMessages();
		//			put(LOG_MESSAGE, null != logMessage ? logMessage : "");
		//		});
		put(LOG_MESSAGE, null != businessProcessModel.getLogMessage() ? businessProcessModel.getLogMessage() : "");
		put(FROM_EMAIL, emailPageModel.getFromEmail());
		put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		put(CREATION_TIME, order.getCreationtime());
		final String customer = order.getUser().getUid();
		if (null == customer)
		{
			put(CUSTOMER_NUMBER, "No customer number found/generated.");
		}
		else
		{
			put(CUSTOMER_NUMBER, customer);
		}
		put(ORDER_NUMBER, order.getCode());
		LOG.error("FAILED TO PLACE THE ORDER == " + order.getCode());
		put(ORDER, order);
		put(ENVIRONMENT, Config.getString("website.siteone.https", Config.getString("website.siteone.http", "NA")));

		put(EMAIL, Config.getString("siteone.orderfailure.notification.toEmail", null));
		put(DISPLAY_NAME, Config.getString("siteone.orderfailure.notification.displayname", StringUtils.EMPTY));
	}

	@Override
	protected BaseSiteModel getSite(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderProcessModel orderProcessModel)
	{
		return (CustomerModel) orderProcessModel.getOrder().getUser();
	}

	@Override
	protected LanguageModel getEmailLanguage(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getLanguage();
	}
}
