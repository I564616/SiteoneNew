package com.siteone.fulfilmentprocess.actions.order;

import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.siteone.fulfilmentprocess.actions.event.PurchasedProductEvent;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class PurchasedProductsMappingAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(PurchasedProductsMappingAction.class);

	private ModelService modelService;
	private EventService eventService;

	@Override
	public ModelService getModelService()
	{
		return modelService;
	}

	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
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

	@Override
	public Transition executeAction(final OrderProcessModel process)
	{
		LOG.error("Process: " + process.getCode() + " in step " + getClass());
		try
		{

			getEventService().publishEvent(new PurchasedProductEvent(process));
			LOG.error("Purchased product event triggered");
			return Transition.OK;
		}
		catch (final Exception e)
		{
			LOG.error("Exception while executing PurchasedProductsMappingAction");
			return Transition.NOK;

		}

	}
}
