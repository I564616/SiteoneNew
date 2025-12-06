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

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.events.OrderPlacedEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.internal.model.impl.ItemModelCloneCreator;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.type.TypeService;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;


public class SendOrderPlacedNotificationAction extends AbstractProceduralAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(SendOrderPlacedNotificationAction.class);

	private ItemModelCloneCreator itemModelCloneCreator;
	@Resource
	private I18NService i18nService;
	@Resource
	private TypeService typeService;

	private EventService eventService;


	@Override
	public void executeAction(final OrderProcessModel process)
	{
		B2BCustomerModel b2bCustomerModel = null;
		if (!(CustomerType.GUEST.equals(((CustomerModel) process.getOrder().getUser()).getType())))
		{
			b2bCustomerModel = (B2BCustomerModel) process.getOrder().getUser();
		}
		if (!(b2bCustomerModel != null && b2bCustomerModel.getNeedsOrderApproval() != null
				&& Boolean.TRUE.equals(b2bCustomerModel.getNeedsOrderApproval())))
		{
			getEventService().publishEvent(new OrderPlacedEvent(process));
			LOG.info("Process: " + process.getCode() + " in step " + getClass());
		}
		createOrderBackUp(process.getOrder());
	}

	protected EventService getEventService()
	{
		return eventService;
	}

	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	private void createOrderBackUp(final OrderModel orderModel)
	{
		itemModelCloneCreator = new ItemModelCloneCreator(modelService, i18nService, typeService);
		final OrderModel orderClone = (OrderModel) itemModelCloneCreator.copy(orderModel);
		orderClone.setCode(orderClone.getCode() + "_bak");
		orderClone.setIsDuplicate(true);
		modelService.save(orderClone);

	}
}
