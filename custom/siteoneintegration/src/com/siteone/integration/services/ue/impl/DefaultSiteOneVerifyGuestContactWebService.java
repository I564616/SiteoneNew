package com.siteone.integration.services.ue.impl;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneVerifyGuestContactWebService;
import com.siteone.integration.verifyguestcontact.data.SiteOneWsVerifyGuestContactResponseData;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.UUID;

public class DefaultSiteOneVerifyGuestContactWebService implements SiteOneVerifyGuestContactWebService {
    private SiteOneRestClient<?, SiteOneWsVerifyGuestContactResponseData> siteOneRestClient;

    @Override
    public SiteOneWsVerifyGuestContactResponseData verifyGuestInUE(String uid, boolean isNewBoomiEnv) {
        String buildVerifyUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.VERIFYGUESTCONTACT_NEW_SERVICE_URL, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.VERIFYGUESTCONTACT_SERVICE_URL, StringUtils.EMPTY));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        buildVerifyUrl = buildVerifyUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_GUEST_EMAIL, uid);
        if(isNewBoomiEnv)
		{
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
		}
		return getSiteOneRestClient().execute(buildVerifyUrl,
               HttpMethod.GET, null, SiteOneWsVerifyGuestContactResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.VERIFYGUESTCONTACT_SERVICE_NAME,httpHeaders);
    }
    /**
     * @return the siteOneRestClient
     */
    public SiteOneRestClient<?, SiteOneWsVerifyGuestContactResponseData> getSiteOneRestClient() {
        return siteOneRestClient;
    }
    /**
     * @param siteOneRestClient the siteOneRestClient to set
     */
    public void setSiteOneRestClient(
            SiteOneRestClient<?, SiteOneWsVerifyGuestContactResponseData> siteOneRestClient) {
        this.siteOneRestClient = siteOneRestClient;
    }
}
