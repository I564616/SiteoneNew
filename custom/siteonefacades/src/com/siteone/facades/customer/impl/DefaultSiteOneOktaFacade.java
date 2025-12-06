package com.siteone.facades.customer.impl;

import jakarta.annotation.Resource;

import com.siteone.facades.customer.SiteOneOktaFacade;
import com.siteone.integration.okta.OKTAAPI;
import com.siteone.integration.okta.data.SiteOneOktaSessionData;
import com.siteone.integration.okta.response.OktaSessionResponse;


/**
 * Created by arun on 24/9/17.
 */
public class DefaultSiteOneOktaFacade implements SiteOneOktaFacade
{

	@Resource(name = "oktaAPI")
	private OKTAAPI oktaAPI;

	@Override
	public SiteOneOktaSessionData doAuth(final String username, final String password)
	{
		return oktaAPI.doAuth(username, password);
	}

	@Override
	public SiteOneOktaSessionData establishSessionWithCredentials(final String username, final String password)
	{
		//Do check whether the user is already available in hybris
		return null;

	}

	@Override
	public void closeSession(final String sessionId)
	{
		oktaAPI.closeSession(sessionId);
	}

	@Override
	public OktaSessionResponse refreshSession(final String sessionId)
	{
		return oktaAPI.refreshSession(sessionId);
	}

	@Override
	public OktaSessionResponse establishSession(final String sessionToken)
	{
		return oktaAPI.establishSession(sessionToken);
	}
}
