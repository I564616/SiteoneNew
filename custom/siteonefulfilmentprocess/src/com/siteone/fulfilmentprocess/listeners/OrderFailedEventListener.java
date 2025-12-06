package com.siteone.fulfilmentprocess.listeners;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import org.apache.log4j.Logger;


/**
 *
 * @author 1085284
 *
 */
public class OrderFailedEventListener extends AbstractAcceleratorSiteEventListener<OrderFailedEvent>
{

	private static final Logger LOG = Logger.getLogger(OrderFailedEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	@Override
	protected void onSiteEvent(final OrderFailedEvent event)
	{
		final OrderModel orderModel = event.getProcess().getOrder();
		LOG.error("Received order failed event for order : " + orderModel.getCode());
		//orderModel.setStatus(OrderStatus.FAILED);
		//getModelService().save(orderModel);
		final OrderProcessModel orderProcessModel = (OrderProcessModel) getBusinessProcessService().createProcess(
				"orderFailedNotificationEmailProcess-" + orderModel.getCode() + "-" + System.currentTimeMillis(),
				"orderFailedNotificationEmailProcess");
		event.getProcess().getTaskLogs().forEach(log -> {
			final String actionId = log.getActionId();
			final String returnCode = log.getReturnCode();
			if (null != actionId && !actionId.isEmpty() && null != returnCode && !returnCode.isEmpty()
					&& null != log.getLogMessages() && !log.getLogMessages().isEmpty())
			{
				if ((actionId.equalsIgnoreCase("checkOrder") || actionId.equalsIgnoreCase("submitOrderToUE"))
						&& returnCode.equalsIgnoreCase("NOK"))
				{

					final String logMessage = log.getLogMessages();
					orderProcessModel.setLogMessage(logMessage);
					//					final List<ProcessTaskLogModel> taskLogModel = (List<ProcessTaskLogModel>) new ProcessTaskLogModel();
					//					((ProcessTaskLogModel) taskLogModel).setLogMessages(logMessage);
					//					orderProcessModel.setTaskLogs(taskLogModel);
				}
			}
		});
		orderProcessModel.setOrder(orderModel);
		getModelService().save(orderProcessModel);
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Starting order failure notification process.");
		}
		LOG.error("Starting order failure notification process.");
		getBusinessProcessService().startProcess(orderProcessModel);
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final OrderFailedEvent event)
	{
		final OrderModel order = event.getProcess().getOrder();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order", order);
		final BaseSiteModel site = order.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}
}
