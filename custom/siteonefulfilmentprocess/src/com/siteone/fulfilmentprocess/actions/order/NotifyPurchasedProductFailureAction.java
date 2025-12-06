/**
 *
 */
package com.siteone.fulfilmentprocess.actions.order;

import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;

import org.apache.log4j.Logger;


/**
 * @author PBurnwal
 *
 */
public class NotifyPurchasedProductFailureAction extends AbstractProceduralAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(NotifyPurchasedProductFailureAction.class);

	@Override
	public void executeAction(final OrderProcessModel process)
	{
		LOG.error("Purchased product mapping action failure: " + process.getCode() + " in step " + getClass());

	}
}
