package com.siteone.integration.services.ue.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneTalonOneLoyaltyEnrollmentStatusWebService;

import de.hybris.platform.util.Config;

import com.siteone.integration.TalonOne.info.LoyaltyProgramStatusInfo;

public class DefaultSiteOneTalonOneLoyaltyEnrollmentStatusWebService implements SiteOneTalonOneLoyaltyEnrollmentStatusWebService {
	
	private SiteOneRestClient<?, LoyaltyProgramStatusInfo> siteOneRestClient;
		
	@Override
	public LoyaltyProgramStatusInfo getLoyaltyProgramInfoStatus(String unitId, boolean isNewBoomiEnv) {
		
		String loyaltyProgramStatusUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.LOYALTY_PROGRAM_STATUS_NEW_SERVICE_URL, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.LOYALTY_PROGRAM_STATUS_SERVICE_URL, StringUtils.EMPTY));
		
		LoyaltyProgramStatusInfo loyaltyProgramStatusInfo = null;
		
		 if(unitId != null) {
			 loyaltyProgramStatusUrl = loyaltyProgramStatusUrl.replace(SiteoneintegrationConstants.LOYALTY_PROGRAM_STATUS_CUSTOMER_PLACEHOLDER,StringUtils.split(unitId, "_")[0]);
			 if(isNewBoomiEnv)
			 {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_LOYALTY, null));
				loyaltyProgramStatusInfo = getSiteOneRestClient().execute(loyaltyProgramStatusUrl,HttpMethod.GET, null, LoyaltyProgramStatusInfo.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.LOYALTY_PROGRAM_STATUS_SERVICE_NAME,httpHeaders);
			 }
			 else
			 {
				 loyaltyProgramStatusInfo = getSiteOneRestClient().execute(loyaltyProgramStatusUrl,HttpMethod.GET, null, LoyaltyProgramStatusInfo.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.LOYALTY_PROGRAM_STATUS_SERVICE_NAME,null);
			 }
		 }
		 
		 return loyaltyProgramStatusInfo;
		 
		}

	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<?, LoyaltyProgramStatusInfo> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	/**
	 * @param siteOneRestClient the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(SiteOneRestClient<?, LoyaltyProgramStatusInfo> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}
}
