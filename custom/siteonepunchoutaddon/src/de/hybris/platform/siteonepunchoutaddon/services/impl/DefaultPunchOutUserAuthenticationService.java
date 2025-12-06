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
package de.hybris.platform.siteonepunchoutaddon.services.impl;

import de.hybris.platform.b2b.punchout.PunchOutSession;
import de.hybris.platform.b2b.punchout.constants.PunchOutSetupOperation;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.siteonepunchoutaddon.constants.SiteonepunchoutaddonConstants;
import de.hybris.platform.siteonepunchoutaddon.security.PunchOutUserAuthenticationStrategy;
import de.hybris.platform.siteonepunchoutaddon.security.PunchOutUserGroupSelectionStrategy;
import de.hybris.platform.siteonepunchoutaddon.security.PunchOutUserSelectionStrategy;
import de.hybris.platform.siteonepunchoutaddon.services.PunchOutUserAuthenticationService;

import java.util.Collection;
import java.util.Iterator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;

import org.apache.log4j.Logger;

import org.apache.log4j.Logger;



/**
 *
 * Default implementation of {@link PunchOutUserAuthenticationService}.
 */
public class DefaultPunchOutUserAuthenticationService implements PunchOutUserAuthenticationService
{
	private static final Logger LOG = Logger.getLogger(DefaultPunchOutUserAuthenticationService.class);

	private PunchOutUserGroupSelectionStrategy punchOutUserGroupSelectionStrategy;
	private PunchOutUserSelectionStrategy punchOutUserSelectionStrategy;
	private PunchOutUserAuthenticationStrategy punchOutUserAuthenticationStrategy;

	private ConfigurationService configurationService;
	private SessionService sessionService;

	@Override
	public void authenticate(final String userId, final boolean isSeamlessLogin, final PunchOutSession punchOutSession,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		LOG.info("DefaultPunchOutUserAuthenticationService.authenticate()");
		final Collection<UserGroupModel> groups = getPunchOutUserGroupSelectionStrategy().select(userId);
		final UserModel user = getPunchOutUserSelectionStrategy().select(userId, groups, punchOutSession);

		if(user!= null){
			B2BCustomerModel b2bCustomerModel = null;

			if(user instanceof B2BCustomerModel){
				b2bCustomerModel = (B2BCustomerModel) user;
				
				if(b2bCustomerModel.getDefaultB2BUnit() != null && b2bCustomerModel.getDefaultB2BUnit().getVisibleCategories() != null  && 
						b2bCustomerModel.getDefaultB2BUnit().getIsPunchOutAccount() != null && 
						b2bCustomerModel.getDefaultB2BUnit().getIsPunchOutAccount().booleanValue()){
					LOG.info("VisibleCategories()=="+b2bCustomerModel.getDefaultB2BUnit().getVisibleCategories());


					final HttpSession session = request.getSession();
					session.setAttribute("allowedCategories",b2bCustomerModel.getDefaultB2BUnit().getVisibleCategories());
					session.setAttribute("isPunchOutAccount","true");

					if(getSessionService() != null){
						Iterator<CategoryModel> iter = b2bCustomerModel.getDefaultB2BUnit().getVisibleCategories().iterator();
						String visibleCategories = null;
						while(iter.hasNext()){
							if(visibleCategories == null){
								visibleCategories = iter.next().getCode();
							}else{
								visibleCategories=visibleCategories+","+iter.next().getCode();
							}
						}
						getSessionService().setAttribute("allowedCategories",visibleCategories);

						LOG.info("allowedCategories in SessionService==" + getSessionService().getAttribute("allowedCategories"));
					}
				}
			}
		}
		
		if (isSeamlessLogin)
		{
			selectRedirectPage(punchOutSession, request);
		}
		getPunchOutUserAuthenticationStrategy().authenticate(user, request, response);
	}

	@Override
	public void logout()
	{
		getPunchOutUserAuthenticationStrategy().logout();
	}

	/**
	 * Select page that user will be redirected in seamless login, depending on the selected operation.
	 *
	 * @param punchOutSession
	 * @param request
	 */
	protected void selectRedirectPage(final PunchOutSession punchOutSession, final HttpServletRequest request)
	{
		final Configuration configuration = configurationService.getConfiguration();
		final String operation = punchOutSession.getOperation();

		if (StringUtils.equalsIgnoreCase(operation, PunchOutSetupOperation.CREATE.toString()))
		{
			request.setAttribute(SiteonepunchoutaddonConstants.SEAMLESS_PAGE,
					configuration.getString("siteonepunchoutaddon.redirect.create"));
		}
		else if (StringUtils.equalsIgnoreCase(operation, PunchOutSetupOperation.EDIT.toString()))
		{
			request.setAttribute(SiteonepunchoutaddonConstants.SEAMLESS_PAGE,
					configuration.getString("siteonepunchoutaddon.redirect.edit"));
		}
		else if (StringUtils.equalsIgnoreCase(operation, PunchOutSetupOperation.INSPECT.toString()))
		{
			request.setAttribute(SiteonepunchoutaddonConstants.SEAMLESS_PAGE,
					configuration.getString("siteonepunchoutaddon.redirect.inspect"));
		}
	}

	public PunchOutUserGroupSelectionStrategy getPunchOutUserGroupSelectionStrategy()
	{
		return punchOutUserGroupSelectionStrategy;
	}

	public void setPunchOutUserGroupSelectionStrategy(final PunchOutUserGroupSelectionStrategy punchOutUserGroupSelectionStrategy)
	{
		this.punchOutUserGroupSelectionStrategy = punchOutUserGroupSelectionStrategy;
	}

	public PunchOutUserSelectionStrategy getPunchOutUserSelectionStrategy()
	{
		return punchOutUserSelectionStrategy;
	}

	public void setPunchOutUserSelectionStrategy(final PunchOutUserSelectionStrategy punchOutUserSelectionStrategy)
	{
		this.punchOutUserSelectionStrategy = punchOutUserSelectionStrategy;
	}

	public PunchOutUserAuthenticationStrategy getPunchOutUserAuthenticationStrategy()
	{
		return punchOutUserAuthenticationStrategy;
	}

	public void setPunchOutUserAuthenticationStrategy(final PunchOutUserAuthenticationStrategy punchOutUserAuthenticationStrategy)
	{
		this.punchOutUserAuthenticationStrategy = punchOutUserAuthenticationStrategy;
	}

	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}
	
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
