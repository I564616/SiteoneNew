package com.siteone.integration.services.ue.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessRequestData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsSelfServeOnlineAccessResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneSelfServeWebService;

import de.hybris.platform.util.Config;

public class DefaultSiteOneSelfServeWebService implements SiteOneSelfServeWebService{

	private SiteOneRestClient<SiteOneWsSelfServeOnlineAccessRequestData, SiteOneWsSelfServeOnlineAccessResponseData> siteOneRestClient;
	
	@Override
	public SiteOneWsSelfServeOnlineAccessResponseData selfServe(SiteOneWsSelfServeOnlineAccessRequestData siteOneWsSelfServeOnlineAccessRequestData, boolean isNewBoomiEnv)
			throws ResourceAccessException {

		
		if(isNewBoomiEnv)
		{
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.SELF_SERVE_NEW_URL_KEY , StringUtils.EMPTY), HttpMethod.POST,
					siteOneWsSelfServeOnlineAccessRequestData, SiteOneWsSelfServeOnlineAccessResponseData.class,
					siteOneWsSelfServeOnlineAccessRequestData.getCorrelationID(),
					SiteoneintegrationConstants.SELF_SERVE_SERVICE_NAME, httpHeaders);
		}
		else
		{		  
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.SELF_SERVE_URL_KEY, StringUtils.EMPTY), HttpMethod.POST,
					siteOneWsSelfServeOnlineAccessRequestData, SiteOneWsSelfServeOnlineAccessResponseData.class,
					siteOneWsSelfServeOnlineAccessRequestData.getCorrelationID(),
					SiteoneintegrationConstants.SELF_SERVE_SERVICE_NAME, null);
		}


	}

	public SiteOneRestClient<SiteOneWsSelfServeOnlineAccessRequestData, SiteOneWsSelfServeOnlineAccessResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsSelfServeOnlineAccessRequestData, SiteOneWsSelfServeOnlineAccessResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

}