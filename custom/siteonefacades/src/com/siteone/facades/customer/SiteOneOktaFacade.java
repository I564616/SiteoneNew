package com.siteone.facades.customer;

import com.siteone.integration.okta.data.SiteOneOktaSessionData;
import com.siteone.integration.okta.response.OktaSessionResponse;


/**
 * Created by arun on 24/9/17.
 */
public interface SiteOneOktaFacade
{

	SiteOneOktaSessionData doAuth(String username, String password);

	SiteOneOktaSessionData establishSessionWithCredentials(String username, String password);

	OktaSessionResponse establishSession(String sessionToken);

	OktaSessionResponse refreshSession(String sessionId);

	void closeSession(String userOktaId);
}
