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
package com.siteone.fulfilmentprocess.service;

import de.hybris.platform.ordersplitting.model.ConsignmentModel;

/**
 * Used by CheckNearbyOrderAction, this service is designed to validate the order prior to running the fulfilment process.
 */
public interface CheckNearbyOrderService
{
	/**
	 * Performs various order checks
	 *
	 * @param order
	 *           the order
	 * @return whether the order is ready for fulfillment or not
	 */
	boolean checkNearbyOrderHold(final ConsignmentModel consignment);
}
