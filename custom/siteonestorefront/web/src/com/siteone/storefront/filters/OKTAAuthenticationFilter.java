package com.siteone.storefront.filters;

import de.hybris.platform.jalo.user.CookieBasedLoginToken;
import de.hybris.platform.jalo.user.LoginToken;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.util.Config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.util.WebUtils;


/**
 * Created by arun on 9/29/2017.
 */
public class OKTAAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter
{

	private static final String SSO_COOKIE_NAME = "sso.cookie.name";
	private AuthenticationSuccessHandler successHandler;

	public void setSuccessHandler(final AuthenticationSuccessHandler successHandler)
	{
		this.successHandler = successHandler;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request)
	{
		return getUIDFromCookie(request);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(final HttpServletRequest request)
	{
		return "N/A";
	}

	private String getUIDFromCookie(final HttpServletRequest request)
	{
		String uid = "";
		final Cookie samlCookie = getSamlCookie(request);
		if (null != samlCookie)
		{
			final LoginToken token = new CookieBasedLoginToken(samlCookie);
			final User user = token.getUser();
			if (user != null)
			{
				uid = user.getUid();
			}
		}
		return uid;
	}

	private Cookie getSamlCookie(final HttpServletRequest request)
	{
		final String cookieName = Config.getParameter(SSO_COOKIE_NAME);
		return cookieName != null ? WebUtils.getCookie(request, cookieName) : null;
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authResult) throws IOException, ServletException
	{
		//		LOG.info("Calling successfulAuthentication of filter ");
		super.successfulAuthentication(request, response, authResult);
		this.successHandler.onAuthenticationSuccess(request, response, authResult);
		//		LOG.info("successful authentication for - " + authResult.getPrincipal().toString().toLowerCase());


	}
}
