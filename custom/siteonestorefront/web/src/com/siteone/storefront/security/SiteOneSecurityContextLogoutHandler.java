/**
 *
 */
package com.siteone.storefront.security;

import de.hybris.platform.servicelayer.session.SessionService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.Assert;

import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.SiteOneOktaFacade;



/**
 * @author ASaha
 *
 */
public class SiteOneSecurityContextLogoutHandler extends SecurityContextLogoutHandler
{
	private static final Logger LOG = Logger.getLogger(SiteOneSecurityContextLogoutHandler.class);
	
	private final boolean invalidateHttpSession = true;
	private final boolean clearAuthentication = true;
	
	@Resource(name = "siteOneOktaFacade")
	SiteOneOktaFacade siteOneOktaFacade;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "customerFacade")
	private SiteOneCustomerFacade siteOneCustomerFacade;
	
	@Override
	public void logout(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
	{
		LOG.info(" Entering logout()");
		
		try
		{
			
			final String oktaId = (String) request.getSession().getAttribute("user_okta_id");
			LOG.info("user_okta_id=="+oktaId);
			siteOneCustomerFacade.getClientInformation(request);
			siteOneOktaFacade.closeSession(oktaId);
		}catch(Exception ex)
		{
			LOG.info(ex.getMessage());
		}
		LOG.info("SiteOneSecurityContextLogoutHandler.logout()");
		Assert.notNull(request, "HttpServletRequest required");
		if (invalidateHttpSession)
		{
			final HttpSession session = request.getSession(false);
			if (session != null)
			{
				LOG.info("Invalidating session: " + session.getId());
				session.invalidate();
			}
		}

		if (clearAuthentication)
		{
			final SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(null);
		}

		SecurityContextHolder.clearContext();
	}

	/**
	 * @return the siteOneOktaFacade
	 */
	public SiteOneOktaFacade getSiteOneOktaFacade()
	{
		return siteOneOktaFacade;
	}

	/**
	 * @param siteOneOktaFacade the siteOneOktaFacade to set
	 */
	public void setSiteOneOktaFacade(SiteOneOktaFacade siteOneOktaFacade)
	{
		this.siteOneOktaFacade = siteOneOktaFacade;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
	
	
}