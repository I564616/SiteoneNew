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
package com.siteone.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.events.SubmitOrderEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.strategies.impl.DefaultSiteOneB2BApprovalBusinessProcessStrategy;

import java.util.Objects;


/**
 * Listener for order submits.
 */
public class SubmitOrderEventListener extends AbstractAcceleratorSiteEventListener<SubmitOrderEvent>
{
	private static final Logger LOG = Logger.getLogger(SubmitOrderEventListener.class);
	private static final String GUEST_USER = "guestUser";
	private BusinessProcessService businessProcessService;
	private BaseStoreService baseStoreService;
	private ModelService modelService;
	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "b2bSiteOneApprovalBusinessProcessStrategy")
	private DefaultSiteOneB2BApprovalBusinessProcessStrategy b2bApprovalBusinessProcessStrategy;


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

	/**
	 * @return the modelService
	 */
	protected ModelService getModelService()
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

	@Override
	protected void onSiteEvent(final SubmitOrderEvent event)
	{
		final OrderModel order = event.getOrder();
		
		ServicesUtil.validateParameterNotNullStandardMessage("event.order", order);
if (CollectionUtils.isNotEmpty(order.getEntries()))
		{
			LOG.error("Inside Order Event " + order.getEntries().get(0).getProduct().getCode()
					+ order.getEntries().get(0).getBasePrice());
		}
		// Try the store set on the Order first, then fallback to the session
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
				B2BCustomerModel b2bCustomerModel = null;
				if (null == sessionService.getAttribute(GUEST_USER)
						|| !sessionService.getAttribute(GUEST_USER).toString().equalsIgnoreCase("guest"))
				{
					b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
				}
				if (b2bCustomerModel != null && b2bCustomerModel.getNeedsOrderApproval() != null
						&& Boolean.TRUE.equals(b2bCustomerModel.getNeedsOrderApproval()))
				{
					b2bApprovalBusinessProcessStrategy.createB2BBusinessProcess(order);
				}
				else
				{
					final String processCode = fulfilmentProcessDefinitionName + "-" + order.getCode() + "-"
							+ System.currentTimeMillis();
					final OrderProcessModel businessProcessModel = getBusinessProcessService().createProcess(processCode,
							fulfilmentProcessDefinitionName);
					businessProcessModel.setOrder(order);
					if(Objects.nonNull(order.getSalesApplication())){
						businessProcessModel.setSalesApplication(order.getSalesApplication());
					}
					getModelService().save(businessProcessModel);
					getBusinessProcessService().startProcess(businessProcessModel);
					if (b2bCustomerModel != null && null == sessionService.getAttribute(GUEST_USER)
							|| !sessionService.getAttribute(GUEST_USER).toString().equalsIgnoreCase("guest"))
					{
						b2bCustomerModel.setRecentCartIds(CollectionUtils.EMPTY_COLLECTION);
						getModelService().save(b2bCustomerModel);
					}
					if (LOG.isInfoEnabled())
					{
						LOG.info(String.format("Started the process %s", processCode));
					}
				}
			}
		}
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final SubmitOrderEvent event)
	{
		final OrderModel order = event.getOrder();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order", order);
		final BaseSiteModel site = order.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}
}
