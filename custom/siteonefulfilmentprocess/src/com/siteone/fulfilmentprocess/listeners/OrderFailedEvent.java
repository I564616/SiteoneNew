package com.siteone.fulfilmentprocess.listeners;

import de.hybris.platform.orderprocessing.events.OrderProcessingEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;


/**
 *
 * @author 1085284
 *
 */
public class OrderFailedEvent extends OrderProcessingEvent
{

	public OrderFailedEvent(final OrderProcessModel process)
	{
		super(process);
	}
}
