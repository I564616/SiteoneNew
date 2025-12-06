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
package com.siteone.storefront.security;

import de.hybris.platform.acceleratorstorefrontcommons.security.GUIDCookieStrategy;
import de.hybris.platform.jalo.user.UserManager;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;


public class StorefrontLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{
	private static final Logger LOG = Logger.getLogger(StorefrontLogoutSuccessHandler.class);
	private GUIDCookieStrategy guidCookieStrategy;
	private List<String> restrictedPages;


	protected GUIDCookieStrategy getGuidCookieStrategy()
	{
		return guidCookieStrategy;
	}

	public void setGuidCookieStrategy(final GUIDCookieStrategy guidCookieStrategy)
	{
		this.guidCookieStrategy = guidCookieStrategy;
	}

	protected List<String> getRestrictedPages()
	{
		return restrictedPages;
	}

	public void setRestrictedPages(final List<String> restrictedPages)
	{
		this.restrictedPages = restrictedPages;
	}

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException
	{
		LOG.info("In on LogoutSuccess");

		getGuidCookieStrategy().deleteCookie(request, response);
		deleteTokenCookie(request, response);

		// Delegate to default redirect behaviour
		super.onLogoutSuccess(request, response, authentication);
	}

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response)
	{
		String targetUrl = super.determineTargetUrl(request, response);

		for (final String restrictedPage : getRestrictedPages())
		{
			// When logging out from a restricted page, return user to homepage.
			if (targetUrl.contains(restrictedPage))
			{
				targetUrl = super.getDefaultTargetUrl();
			}
		}

		return targetUrl;
	}

	public void deleteTokenCookie(final HttpServletRequest request, final HttpServletResponse response)
	{
		LOG.info("Deleting token cookie ...");
		UserManager.getInstance().deleteLoginTokenCookie(request, response);
		final Cookie cookie = new Cookie("samlPassThroughToken", "");

		cookie.setValue(null);
		// By setting the cookie maxAge to 0 it will deleted immediately
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		final Cookie cookieFirstname = new Cookie("newfirstname", "");

		cookieFirstname.setValue(null);
		cookieFirstname.setMaxAge(0);
		cookieFirstname.setPath("/");
		cookieFirstname.setSecure(true);
		response.addCookie(cookieFirstname);
		final Cookie cookieJusername = new Cookie("j_username", "");

		cookieJusername.setValue(null);
		cookieJusername.setMaxAge(0);
		cookieJusername.setPath("/");
		cookieJusername.setSecure(true);
		response.addCookie(cookieJusername);

		final Cookie cookieJusername2 = new Cookie("j_username", "");

		cookieJusername2.setValue(null);
		cookieJusername2.setMaxAge(0);
		cookieJusername2.setPath("/en");
		cookieJusername2.setSecure(true);
		response.addCookie(cookieJusername2);

		final Cookie cookieJusername3 = new Cookie("j_username", "");

		cookieJusername3.setValue(null);
		cookieJusername3.setMaxAge(0);
		cookieJusername3.setPath("/es");
		cookieJusername3.setSecure(true);
		response.addCookie(cookieJusername3);

		final HttpSession session = request.getSession(false);
		if (session != null)
		{
			session.invalidate();
		}

	}

}
