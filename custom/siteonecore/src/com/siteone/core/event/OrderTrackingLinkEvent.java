/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;


/**
 * @author 1099417
 *
 */
public class OrderTrackingLinkEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private OrderModel order;
	private ConsignmentModel consignment;

	/**
	 * @return the order
	 */
	public OrderModel getOrder()
	{
		return order;
	}

	/**
	 * @param order
	 *           the order to set
	 */
	public void setOrder(final OrderModel order)
	{
		this.order = order;
	}

	/**
	 * Parameterized Constructor
	 *
	 * @param order
	 */
	public OrderTrackingLinkEvent(final OrderModel order, final ConsignmentModel consignment)
	{
		super();
		this.order = order;
		this.consignment = consignment;
	}



	/**
	 * @return the consignment
	 */
	public ConsignmentModel getConsignment()
	{
		return consignment;
	}

	/**
	 * @param consignment
	 *           the consignment to set
	 */
	public void setConsignment(final ConsignmentModel consignment)
	{
		this.consignment = consignment;
	}
}
