/**
 *
 */
package com.siteone.fulfilmentprocess.approval.actions;

import de.hybris.platform.b2b.process.approval.actions.AbstractProceduralB2BOrderAproveAction;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2bacceleratorservices.event.OrderApprovalRejectionEvent;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.task.RetryLaterException;

import org.apache.log4j.Logger;


/**
 * @author BS
 *
 */
public class InformOfOrderApproval extends AbstractProceduralB2BOrderAproveAction
{
	private static final Logger LOG = Logger.getLogger(InformOfOrderApproval.class);

	private EventService eventService;

	@Override
	public void executeAction(final B2BApprovalProcessModel process) throws RetryLaterException
	{
		getEventService().publishEvent(new OrderApprovalRejectionEvent(process));
		Logger.getLogger(getClass()).info("Process: " + process.getCode() + " in step " + getClass());
	}


	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}


	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}
}