/**
 *
 */
package com.siteone.fulfilmentprocess.approval.actions;

import de.hybris.platform.b2b.process.approval.actions.AbstractSimpleB2BApproveOrderDecisionAction;
import de.hybris.platform.b2b.process.approval.event.ApprovalProcessStartEvent;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.task.RetryLaterException;


/**
 * @author BS
 *
 */
public class ApprovalProcessStartAction extends AbstractSimpleB2BApproveOrderDecisionAction
{
	private EventService eventService;

	protected EventService getEventService()
	{
		return eventService;
	}

	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	@Override
	public Transition executeAction(final B2BApprovalProcessModel process) throws RetryLaterException
	{
		getEventService().publishEvent(new ApprovalProcessStartEvent(process));
		return Transition.OK;
	}
}
