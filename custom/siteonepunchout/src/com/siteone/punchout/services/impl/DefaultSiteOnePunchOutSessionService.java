/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2019 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.punchout.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.punchout.PunchOutSession;
import de.hybris.platform.b2b.punchout.PunchOutSessionExpired;
import de.hybris.platform.b2b.punchout.PunchOutSessionNotFoundException;
import de.hybris.platform.b2b.punchout.model.StoredPunchOutSessionModel;
import de.hybris.platform.b2b.punchout.services.impl.DefaultPunchOutSessionService;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.impl.DefaultCartService;
import de.hybris.platform.servicelayer.session.Session;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.Date;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;


/**
 *
 */
public class DefaultSiteOnePunchOutSessionService extends DefaultPunchOutSessionService
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePunchOutSessionService.class);

	@Resource(name = "userService")
	private UserService userService;

	@Override
	public void saveCurrentPunchoutSession()
	{
		final String punchoutSessionId = generatePunchoutSessionId();
		LOG.info(" Within saveCurrentPunchoutSession(). punchoutSessionId==" + punchoutSessionId);
		getSessionService().getCurrentSession().setAttribute(PUNCHOUT_SESSION_ID, punchoutSessionId);
		final PunchOutSession punchOutSession = getCurrentPunchOutSession();
		final CartModel cart = getCartService().getSessionCart();

		final StoredPunchOutSessionModel storedSession = getModelService().create(StoredPunchOutSessionModel.class);
		storedSession.setSid(punchoutSessionId);
		storedSession.setCart(cart);
		storedSession.setPunchOutSession(punchOutSession);

		LOG.info(" Key==" + punchOutSession.getKey() + " ,Salt==" + punchOutSession.getSalt());

		if (punchOutSession.getKey() != null && punchOutSession.getSalt() != null)
		{
			storedSession.setKey(punchOutSession.getKey());
			storedSession.setSalt(punchOutSession.getSalt());
		}

		getModelService().save(storedSession);
		LOG.info(" Saved the StoredPunchOutSession, storedSession==" + storedSession + " ,Sid==" + storedSession.getSid()
				+ " ,TenantId==" + storedSession.getTenantId() + " ,PunchOutSession==" + storedSession.getPunchOutSession());
	}

	@Override
	public PunchOutSession loadPunchOutSession(final String punchoutSessionId)
			throws PunchOutSessionNotFoundException, PunchOutSessionExpired
	{
		LOG.info(" Within loadPunchOutSession(),punchoutSessionId==" + punchoutSessionId);
		final StoredPunchOutSessionModel storedSession = loadStoredPunchOutSessionModel(punchoutSessionId);
		if (storedSession == null)
		{
			throw new PunchOutSessionNotFoundException("Session not found");
		}

		final PunchOutSession punchoutSession = (PunchOutSession) storedSession.getPunchOutSession();

		if (punchoutSession == null)
		{
			throw new PunchOutSessionNotFoundException("PunchOut session not found");
		}

		if (new Date().after(calculateCutOutTime(punchoutSession.getTime())))
		{
			throw new PunchOutSessionExpired("PunchOut session has expired");
		}

		LOG.info(" Key==" + punchoutSession.getKey() + " ,Salt==" + punchoutSession.getSalt());

		if (storedSession.getKey() != null && storedSession.getSalt() != null)
		{
			punchoutSession.setKey(storedSession.getKey());
			punchoutSession.setSalt(storedSession.getSalt());
		}

		getSessionService().getCurrentSession().setAttribute(PUNCHOUT_SESSION_KEY, punchoutSession);

		return punchoutSession;
	}
	@Override
	public void setCurrentCartFromPunchOutSetup(final String punchoutSessionId)
	{
		try
		{
		        LOG.info(" Within currentcart user==" + punchoutSessionId);
			final Session currentSession = this.getSessionService().getCurrentSession();
			final B2BCustomerModel customer = (B2BCustomerModel) getUserService().getUserForUID(punchoutSessionId);
			if(null!= customer)
			{
			if (CollectionUtils.isNotEmpty(customer.getCarts()))
			{
			final CartModel cart = customer.getCarts().iterator().next();
			// add old cart to current session
						currentSession.setAttribute(DefaultCartService.SESSION_CART_PARAMETER_NAME, cart);
			}
			}
			
		}
		catch (final NullPointerException e) // NOSONAR
		{
			throw new PunchOutSessionNotFoundException("Session could not be retrieved.", e);
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
