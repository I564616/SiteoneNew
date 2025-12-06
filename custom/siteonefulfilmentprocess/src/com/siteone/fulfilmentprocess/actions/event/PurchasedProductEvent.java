/**
 *
 */
package com.siteone.fulfilmentprocess.actions.event;

import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;


/**
 * @author PBurnwal
 *
 */
public class PurchasedProductEvent extends AbstractEvent
{
	private OrderProcessModel process;

	/**
	 * @return the process
	 */
	public OrderProcessModel getProcess()
	{
		return process;
	}

	/**
	 * @param process
	 *           the process to set
	 */
	public void setProcess(final OrderProcessModel process)
	{
		this.process = process;
	}

	/**
	 * @param process
	 */
	public PurchasedProductEvent(final OrderProcessModel process)
	{
		super();
		this.process = process;
	}
}
