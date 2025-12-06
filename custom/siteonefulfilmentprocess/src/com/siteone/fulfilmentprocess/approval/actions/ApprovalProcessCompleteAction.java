/**
 *
 */
package com.siteone.fulfilmentprocess.approval.actions;

import de.hybris.platform.b2b.process.approval.actions.AbstractSimpleB2BApproveOrderDecisionAction;
import de.hybris.platform.b2b.process.approval.event.ApprovalProcessCompleteEvent;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.task.RetryLaterException;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;



/**
 * @author BS
 *
 */
public class ApprovalProcessCompleteAction extends AbstractSimpleB2BApproveOrderDecisionAction
{

	private static final Logger LOG = Logger.getLogger(ApprovalProcessCompleteAction.class);
	private BusinessProcessService businessProcessService;
	private BaseStoreService baseStoreService;
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	public EventService getEventService()
	{
		return eventService;
	}

	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	private EventService eventService;

	/**
	 * This method allows to hook in into the completion of order approval process
	 */
	@Override
	public Transition executeAction(final B2BApprovalProcessModel process) throws RetryLaterException
	{
		eventService.publishEvent(new ApprovalProcessCompleteEvent(process));
		final OrderModel order = process.getOrder();
		BaseStoreModel store = order.getStore();
		if (store == null)
		{
			store = getBaseStoreService().getCurrentBaseStore();
		}

		if (store == null)
		{
			LOG.warn("Unable to start fulfilment process for order [" + order.getCode()
					+ "]. Store not set on Order and no current base store defined in session.");
		}
		else
		{
			final String fulfilmentProcessDefinitionName = store.getSubmitOrderProcessCode();
			if (fulfilmentProcessDefinitionName == null || fulfilmentProcessDefinitionName.isEmpty())
			{
				LOG.warn("Unable to start fulfilment process for order [" + order.getCode() + "]. Store [" + store.getUid()
						+ "] has missing SubmitOrderProcessCode");
			}
			else
			{
				final String processCode = fulfilmentProcessDefinitionName + "-" + order.getCode() + "-" + System.currentTimeMillis();
				final OrderProcessModel businessProcessModel = getBusinessProcessService().createProcess(processCode,
						fulfilmentProcessDefinitionName);
				businessProcessModel.setOrder(order);
				getModelService().save(businessProcessModel);
				getBusinessProcessService().startProcess(businessProcessModel);
			}

		}
		return Transition.OK;

	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	/**
	 * @return the businessProcessService
	 */
	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService
	 *           the businessProcessService to set
	 */
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	/**
	 * @return the baseStoreService
	 */
	protected BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}


}