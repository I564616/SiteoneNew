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
package com.siteone.core.externaltax.impl;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.externaltax.DecideExternalTaxesStrategy;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.session.SessionService;

import jakarta.annotation.Resource;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;


/**
 * Accelerator
 *
 */
public class AcceleratorDetermineExternalTaxStrategy implements DecideExternalTaxesStrategy
{
	/**
	 * Initially just to test if the delivery mode and address are set, than calculate the external taxes.
	 *
	 * Products in cart, delivery mode, delivery address and payment information to determine whether or not to calculate
	 * taxes as tracked in https://jira.hybris.com/browse/ECP-845.
	 */
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Override
	public boolean shouldCalculateExternalTaxes(final AbstractOrderModel abstractOrder)
	{
		if (abstractOrder == null)
		{
			throw new IllegalStateException("Order is null. Cannot apply external tax to it.");
		}

		if(abstractOrder.getDeliveryAddress() != null)
		{
			return Boolean.TRUE.equals(abstractOrder.getNet());
		}
		else if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()) || 
				(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",
		 				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId())))
		{
			getSessionService().setAttribute("isSplitMixedCartEnabledBranch", true);
			return Boolean.TRUE.equals(abstractOrder.getNet());
		}
		else
		{
			return Boolean.FALSE;
		}
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService
	 *           the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(final SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

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
}
