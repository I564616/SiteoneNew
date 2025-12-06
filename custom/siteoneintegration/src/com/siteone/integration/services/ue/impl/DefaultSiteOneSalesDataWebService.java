package com.siteone.integration.services.ue.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.siteone.integration.address.data.SiteOneWsAddressResponseData;
import com.siteone.integration.addressverification.data.SiteOneWsAddressVerificationResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.product.data.SiteOneSalesRequestData;
import com.siteone.integration.product.data.SiteOneSalesResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneSalesDataWebService;
import de.hybris.platform.util.Config;

public class DefaultSiteOneSalesDataWebService implements SiteOneSalesDataWebService{
	private SiteOneRestClient<SiteOneSalesRequestData, SiteOneSalesResponseData> siteOneRestClient;

	@Override
	public SiteOneSalesResponseData getSalesData(SiteOneSalesRequestData siteOneSalesRequestData) {
		siteOneSalesRequestData.setCorrelationID(UUID.randomUUID().toString());		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
		return getSiteOneRestClient().execute(Config.getString(SiteoneintegrationConstants.
		  SALESDATA_SERVICE_URL, StringUtils.EMPTY), HttpMethod.POST,
		siteOneSalesRequestData,
		  SiteOneSalesResponseData.class,siteOneSalesRequestData.getCorrelationID(),
		  SiteoneintegrationConstants.SALESDATA_SERVICE_NAME,httpHeaders);

		}

	public SiteOneRestClient<SiteOneSalesRequestData, SiteOneSalesResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneSalesRequestData, SiteOneSalesResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

	
}
