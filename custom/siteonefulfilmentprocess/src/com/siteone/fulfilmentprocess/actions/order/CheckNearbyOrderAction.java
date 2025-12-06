/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.fulfilmentprocess.actions.order;

import com.siteone.fulfilmentprocess.service.CheckNearbyOrderService;

import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.processengine.definition.ActionDefinitionContext;
import de.hybris.platform.task.RetryLaterException;
import org.apache.log4j.Logger;
	
import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


/**
 * This example action checks the order for required data in the business process. Skipping this action may result in
 * failure in one of the subsequent steps of the process. The relation between the order and the business process is
 * defined in basecommerce extension through item OrderProcess. Therefore if your business process has to access the
 * order (a typical case), it is recommended to use the OrderProcess as a parentClass instead of the plain
 * BusinessProcess.
 */
public class CheckNearbyOrderAction extends AbstractAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CheckNearbyOrderAction.class);

	@Resource(name = "checkNearbyOrderService")
	private CheckNearbyOrderService checkNearbyOrderService;
	
	public enum Transition
	{
		OK, NOK, ERROR;

		public static Set<String> getStringValues()
		{
			final Set<String> res = new HashSet<String>();

			for (final CheckNearbyOrderAction.Transition transition : CheckNearbyOrderAction.Transition.values())
			{
				res.add(transition.toString());
			}
			return res;
		}
	}


	protected CheckNearbyOrderService getCheckNearbyOrderService()
	{
		return checkNearbyOrderService;
	}

	public void setCheckNearbyOrderService(final CheckNearbyOrderService checkNearbyOrderService)
	{
		this.checkNearbyOrderService = checkNearbyOrderService;
	}

	@Override
	public String execute(ConsignmentProcessModel process) throws RetryLaterException, Exception {
		final ConsignmentModel consignment = process.getConsignment();
		
		if (consignment == null)
		{
			LOG.error("Missing the consignment, exiting the process");
			return Transition.ERROR.toString();
		}

		if (checkNearbyOrderService.checkNearbyOrderHold(consignment))
		{
			return Transition.OK.toString();
		}
		else
		{
			return Transition.NOK.toString();
		}
	}

	@Override
	public ActionDefinitionContext getCurrentActionDefinitionContext() {
		return null;
	}

	@Override
	public Set<String> getTransitions() {
		return Transition.getStringValues();
	}

}

