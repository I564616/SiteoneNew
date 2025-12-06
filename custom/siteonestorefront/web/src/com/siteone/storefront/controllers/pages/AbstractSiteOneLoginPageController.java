package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractLoginPageController;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.persistence.security.EJBPasswordEncoderNotFoundException;
import de.hybris.platform.util.Config;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;


/**
 * Created by arun on 10/20/2017.
 */
public abstract class AbstractSiteOneLoginPageController extends AbstractLoginPageController
{

	private static final String SSO_COOKIE_MAX_AGE = "sso.cookie.max.age";

	private static final String SSO_COOKIE_PATH = "sso.cookie.path";

	private static final String SSO_COOKIE_DOMAIN = "sso.cookie.domain";

	private static final String SSO_DEFAULT_COOKIE_DOMAIN = null;

	private static final String SSO_DEFAULT_COOKIE_NAME = "samlPassThroughToken";

	private static final String DEFAULT_COOKIE_PATH = "/";

	private static final int DEFAULT_COOKIE_MAX_AGE = 1800;

	private static final String SSO_COOKIE_NAME = "sso.cookie.name";

	private static final Logger LOG = Logger.getLogger(AbstractSiteOneLoginPageController.class);

	protected void storeTokenFromOkta(final HttpServletResponse response, final String uid)
	{
		try
		{

			final String cookiePath = StringUtils.defaultIfEmpty(Config.getParameter(SSO_COOKIE_PATH), DEFAULT_COOKIE_PATH);

			final String cookieMaxAgeStr = StringUtils.defaultIfEmpty(Config.getParameter(SSO_COOKIE_MAX_AGE),
					String.valueOf(DEFAULT_COOKIE_MAX_AGE));

			int cookieMaxAge;

			if (!NumberUtils.isNumber(cookieMaxAgeStr))
			{
				cookieMaxAge = DEFAULT_COOKIE_MAX_AGE;
			}
			else
			{
				cookieMaxAge = Integer.parseInt(cookieMaxAgeStr);
			}

			LOG.info("cookieMaxAge " + cookieMaxAge);

			UserManager.getInstance().storeLoginTokenCookie(
					//
					StringUtils.defaultIfEmpty(Config.getParameter(SSO_COOKIE_NAME), SSO_DEFAULT_COOKIE_NAME), // cookie name
					uid.toLowerCase(), // user id
					"en", // language iso code
					null, // credentials to check later
					cookiePath, // cookie path
					StringUtils.defaultIfEmpty(Config.getParameter(SSO_COOKIE_DOMAIN), SSO_DEFAULT_COOKIE_DOMAIN), // cookie domain
					true, // secure cookie
					cookieMaxAge, // max age in seconds. TODO fetch it from properties file
					response);
		}
		catch (final EJBPasswordEncoderNotFoundException e)
		{
			throw new RuntimeException(e); // NOSONAR
		}
	}
}
