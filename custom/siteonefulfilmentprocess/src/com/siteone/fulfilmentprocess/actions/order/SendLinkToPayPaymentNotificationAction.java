/**
 *
 */
package com.siteone.fulfilmentprocess.actions.order;
import org.apache.log4j.Logger;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.event.EventService;

import com.siteone.core.event.LinkToPayEmailPaymentEvent;
import com.siteone.core.model.LinkToPayPaymentProcessModel;


/**
 * @author SJ08640
 *
 */
public class SendLinkToPayPaymentNotificationAction extends AbstractProceduralAction<LinkToPayPaymentProcessModel>
{
	private EventService eventService;

	@Override
	public void executeAction(final LinkToPayPaymentProcessModel process)
	{	
		Logger.getLogger(getClass()).info("sendLinkToPayPaymentNotificationAction"); 
		getEventService().publishEvent(new LinkToPayEmailPaymentEvent(process));
		Logger.getLogger(getClass()).info("Process: " + process.getCode() + " in step " + getClass());
	}

	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	protected EventService getEventService()
	{
		return eventService;
	}
}

