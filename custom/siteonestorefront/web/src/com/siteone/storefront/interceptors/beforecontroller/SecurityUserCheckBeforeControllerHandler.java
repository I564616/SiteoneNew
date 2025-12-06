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
package com.siteone.storefront.interceptors.beforecontroller;

import com.sap.security.core.server.csi.util.URLDecoder;
import de.hybris.platform.acceleratorcms.services.CMSPageContextService;
import de.hybris.platform.acceleratorstorefrontcommons.interceptors.BeforeControllerHandler;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.WebUtils;


/**
 * Spring MVC interceptor that validates that the spring security user and the hybris session user are in sync. If the
 * spring security user and the hybris session user are not in sync then the session is invalidated and the visitor is
 * redirect to the homepage.
 */
public class SecurityUserCheckBeforeControllerHandler implements BeforeControllerHandler
{
	private static final Logger LOG = Logger.getLogger(SecurityUserCheckBeforeControllerHandler.class);

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "cmsPageContextService")
	private CMSPageContextService cmsPageContextService;


	@Override
	public boolean beforeController(final HttpServletRequest request, final HttpServletResponse response,
									final HandlerMethod handler) throws IOException
	{
		// Skip this security check when run from within the WCMS Cockpit
		if (isPreviewDataModelValid(request))
		{
			return true;
		}

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null)
		{
			//LOG.info("authentication.getCredentials()=="+authentication.getCredentials()+" authentication.getName()=="+authentication.getName()+" authentication.getDetails()=="+authentication.getDetails()+" authentication.getPrincipal()=="+authentication.getPrincipal());
			final Object principal = authentication.getPrincipal();
			if (principal instanceof String)
			{
				//final String springSecurityUserId = (String) principal;
                String springSecurityUserId = (String) principal;
				final String hybrisUserId = userService.getCurrentUser().getUid();
				final Cookie jusernameCookie = WebUtils.getCookie(request, "j_username");
				if((jusernameCookie!=null && jusernameCookie.getValue()!=null)){
					String softLoginUid = URLDecoder.decode(jusernameCookie!=null ? jusernameCookie.getValue():null,"UTF-8");
					softLoginUid = softLoginUid.trim().toLowerCase();
					final String hybrisUserIdtemp = userService.getCurrentUser().getUid();
					if(!springSecurityUserId.equals(hybrisUserId) && hybrisUserId.equalsIgnoreCase(softLoginUid)){
						//hybrisUserId=springSecurityUserId; to
                        springSecurityUserId=hybrisUserId;
					}
				}



				//LOG.info(" springSecurityUserId==" + springSecurityUserId + ", hybrisUserId==" + hybrisUserId);
				if (!springSecurityUserId.equals(hybrisUserId))
				{
					LOG.error("User miss-match springSecurityUserId [" + springSecurityUserId + "] hybris session user ["
							+ hybrisUserId + "]. Invalidating session.");

					// Invalidate session and redirect to the root page
					request.getSession().invalidate();

					final String encodedRedirectUrl = response.encodeRedirectURL(request.getContextPath() + "/");
					response.sendRedirect(encodedRedirectUrl);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks whether there is a preview data setup for the current request
	 *
	 * @param httpRequest
	 *           current request
	 * @return true whether is valid otherwise false
	 */
	protected boolean isPreviewDataModelValid(final HttpServletRequest httpRequest)
	{
		return cmsPageContextService.getCmsPageRequestContextData(httpRequest).getPreviewData() != null;
	}
}