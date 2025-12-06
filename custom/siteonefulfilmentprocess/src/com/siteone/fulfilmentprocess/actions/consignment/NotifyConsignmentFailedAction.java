/**
 *
 */
package com.siteone.fulfilmentprocess.actions.consignment;

import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.util.Config;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.fulfilmentprocess.listeners.OrderFailedEvent;

/**
 * @author SNavamani
 *
 */
public class NotifyConsignmentFailedAction extends AbstractProceduralAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(NotifyConsignmentFailedAction.class);

	private EventService eventService;

	@Override
	public void executeAction(final ConsignmentProcessModel process)
	{
		if (Config.getBoolean("siteone.orderfailure.notification.enable", false)
				&& StringUtils.isNotEmpty(Config.getString("siteone.orderfailure.notification.toEmail", null)))
		{

			eventService.publishEvent(new OrderFailedEvent(process.getParentProcess()));
			LOG.info("Process: " + process.getCode() + " in step " + getClass());
		}

	}

	protected EventService getEventService()
	{
		return eventService;
	}

	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}


}