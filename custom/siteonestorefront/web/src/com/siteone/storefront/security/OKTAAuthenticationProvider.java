package com.siteone.storefront.security;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;


/**
 * Created by arun on 10/2/2017.
 */
public class OKTAAuthenticationProvider extends PreAuthenticatedAuthenticationProvider
{
	private static final Logger LOG = Logger.getLogger(OKTAAuthenticationProvider.class);

	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "userService")
	private UserService userService;

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{

		this.userService = userService;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException
	{
		//final Map<String, Object> allAttributes = sessionService.getAllAttributes();
		//LOG.info("Attributes from Session Service " + allAttributes);
		Authentication result = super.authenticate(authentication);
		if (result == null)
		{
			return null;
		}
		//        LOG.info(" authentication.getPrincipal() "
		//                + authentication.getPrincipal().toString().toLowerCase());
		SecurityContextHolder.getContext().setAuthentication(result);
		final User user = UserManager.getInstance().getUserByLogin(authentication.getPrincipal().toString().toLowerCase());
		boolean isValidUser;
		if (null != user)
		{
			final UserModel userModel = userService.getUserForUID(user.getUid());

			if (userModel == null)
			{
				LOG.error("The user in not available in system, not authenticating");
				isValidUser = false;
			}
			else
			{
				isValidUser = true;
			}

			if (isValidUser)
			{
				final UserModel cuser = userService.getUserForUID(user.getUid());
				userService.setCurrentUser(cuser);
				JaloSession.getCurrentSession().setUser(user);
				//                LOG.info("User validated @ OKTA is : " + user);
				//                LOG.info("jalo session id " + JaloSession.getCurrentSession().getSessionID());
				//                LOG.info("http session id " + JaloSession.getCurrentSession().getHttpSessionId());
			}
			else
			{
				LOG.error("Invalid user. Not yet available in system.");
				result = null;
			}
		}
		return result;
	}


}
