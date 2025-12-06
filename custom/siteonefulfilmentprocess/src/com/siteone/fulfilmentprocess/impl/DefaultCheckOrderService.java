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
package com.siteone.fulfilmentprocess.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.order.services.SiteOneOrderService;
import com.siteone.fulfilmentprocess.CheckOrderService;
import com.siteone.fulfilmentprocess.constants.SiteoneFulfilmentProcessConstants;


/**
 * Default implementation of {@link CheckOrderService}
 */
public class DefaultCheckOrderService implements CheckOrderService
{
	private static final Logger LOG = Logger.getLogger(DefaultCheckOrderService.class);

	private ModelService modelService;



	@Resource(name = "siteOneOrderService")
	private SiteOneOrderService siteOneOrderService;


	//private Converter<OrderModel, OrderData> orderConverter;

	@Override
	public boolean check(final OrderModel order)
	{

		order.setIsHybrisOrder(true);
		modelService.save(order);

		if ((order.getPunchOutOrder() == null || BooleanUtils.isFalse(order.getPunchOutOrder()))
				&& !order.getCalculated().booleanValue())
		{
			// Order must be calculated
			return false;
		}
		if (order.getEntries().isEmpty())
		{
			// Order must have some lines
			return false;
		}

		final Map<String, Boolean> fulfilmentStatus = siteOneOrderService.populateFulfilmentStatus(order);
		final OrderTypeEnum orderType = order.getOrderType();
		if (null == order.getGuestContactPerson())

		{
			if (null == ((B2BCustomerModel) order.getUser()).getGuid() || ((B2BCustomerModel) order.getUser()).getGuid().isEmpty())
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for user " + ((B2BCustomerModel) order.getUser()).getEmail());
				return false;
			}


			if (null == order.getOrderingAccount().getGuid() || order.getOrderingAccount().getGuid().isEmpty())
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for ordering account " + order.getOrderingAccount().getUid());
				return false;
			}

			if (null == order.getUnit().getGuid() || order.getUnit().getGuid().isEmpty())
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for Billing Account " + order.getUnit().getUid());
				return false;
			}
			if ((orderType != null || fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_PICKUP))
					&& (null != order.getContactPerson()
							&& (null == order.getContactPerson().getGuid() || order.getContactPerson().getGuid().isEmpty())))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for pickup contact person " + order.getContactPerson().getEmail());
				return false;
			}
			if ((orderType == null && fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_DELIVERY))
					&& (null != order.getDeliveryContactPerson() && (null == order.getDeliveryContactPerson().getGuid()
							|| order.getDeliveryContactPerson().getGuid().isEmpty())))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for delivery contact person " + order.getDeliveryContactPerson().getEmail());
				return false;
			}
			if ((orderType == null && fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_SHIPPING))
					&& (null != order.getShippingContactPerson() && (null == order.getShippingContactPerson().getGuid()
							|| order.getShippingContactPerson().getGuid().isEmpty())))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for shipping contact person " + order.getShippingContactPerson().getEmail());
				return false;
			}
			if (((orderType == null && fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_DELIVERY))
					|| (orderType != null && !OrderTypeEnum.PICKUP.getCode().equalsIgnoreCase(order.getOrderType().getCode())))
					&& (null == order.getDeliveryAddress() || null == order.getDeliveryAddress().getGuid()
							|| order.getDeliveryAddress().getGuid().isEmpty()))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for delivery address");
				return false;
			}
			if ((orderType == null && fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_SHIPPING))
					&& (null == order.getShippingAddress() || null == order.getShippingAddress().getGuid()
							|| order.getShippingAddress().getGuid().isEmpty()))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for shipping address");
				return false;
			}
		}
		else
		{
			if ((order.getOrderType() != null || fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_PICKUP))
					&& (null == order.getGuestContactPerson().getUid() || order.getGuestContactPerson().getUid().isEmpty()))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for contact person " + order.getGuestContactPerson().getUid());
				return false;
			}
			if ((order.getOrderType() == null && fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_DELIVERY))
					&& (null == order.getGuestDeliveryContactPerson().getUid() || order.getGuestDeliveryContactPerson().getUid().isEmpty()))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for delivery contact person " + order.getGuestDeliveryContactPerson().getUid());
				return false;
			}
			if ((order.getOrderType() == null && fulfilmentStatus.get(SiteoneFulfilmentProcessConstants.DELIVERYMODE_SHIPPING))
					&& (null == order.getGuestShippingContactPerson().getUid() || order.getGuestShippingContactPerson().getUid().isEmpty()))
			{
				// Order must have mandatory fields
				LOG.error("GUID not present for shipping contact person " + order.getGuestShippingContactPerson().getUid());
				return false;
			}
		}
		if (order.getSubtotal() <= 0.0)
		{
			LOG.error("Order subtotal cannot be 0.0");
			return false;
		}

		if (order.getTotalPrice() <= 0.0)
		{
			LOG.error("Order total cannot be 0.0");
			return false;
		}
		if (null != order.getIsDuplicate() && order.getIsDuplicate())
		{
			LOG.error("This is hybris back up order model. Failed to place Order.");
			return false;
		}

		return true;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the siteOneOrderService
	 */
	public SiteOneOrderService getSiteOneOrderService()
	{
		return siteOneOrderService;
	}

	/**
	 * @param siteOneOrderService the siteOneOrderService to set
	 */
	public void setSiteOneOrderService(SiteOneOrderService siteOneOrderService)
	{
		this.siteOneOrderService = siteOneOrderService;
	}
	
}