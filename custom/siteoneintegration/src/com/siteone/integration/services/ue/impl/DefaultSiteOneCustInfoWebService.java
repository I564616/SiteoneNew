package com.siteone.integration.services.ue.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneCustInfoWebService;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.util.Config;

public class DefaultSiteOneCustInfoWebService implements SiteOneCustInfoWebService{
	
	private SiteOneRestClient<?, SiteOneCustInfoResponeData> siteOneCustInfoRestClient;
	
	@Override
	public SiteOneCustInfoResponeData getCustInfo(B2BUnitModel b2BUnitModel, boolean isNewBoomiEnv) {
		// TODO Auto-generated method stub
		
		
		String custInfoUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.CUSTOMER_INFO_NEW_URL_KEY, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.CUSTOMER_INFO_URL_KEY, StringUtils.EMPTY));
		
		SiteOneCustInfoResponeData siteOneCustInfoResponeData = null;
		
		if(null != b2BUnitModel && null != b2BUnitModel.getGuid()){
			String guid = b2BUnitModel.getGuid();	
			custInfoUrl = custInfoUrl.replace(SiteoneintegrationConstants.CUSTOMER_INFO_PLACEHOLDER, guid);
			
			if(isNewBoomiEnv)
			{
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
				siteOneCustInfoResponeData = getSiteOneCustInfoRestClient().execute(custInfoUrl,
					HttpMethod.GET, null, SiteOneCustInfoResponeData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.CUSTOMER_INFO_SERVICE_NAME,httpHeaders);
			}
			else
			{
				siteOneCustInfoResponeData = getSiteOneCustInfoRestClient().execute(custInfoUrl,
						HttpMethod.GET, null, SiteOneCustInfoResponeData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.CUSTOMER_INFO_SERVICE_NAME,null);
			}
		}
		
		

		return siteOneCustInfoResponeData;

	}

	@Override
	public SiteOneCustInfoResponeData getPartnerPointsInfo(B2BUnitModel b2BUnitModel, boolean isNewBoomiEnv) {

		String partnerPointsURL = (isNewBoomiEnv
				? Config.getString(SiteoneintegrationConstants.PARTNERPOINTS_INFO_NEW_URL, StringUtils.EMPTY)
				: Config.getString(SiteoneintegrationConstants.PARTNERPOINTS_INFO_URL, StringUtils.EMPTY));

		SiteOneCustInfoResponeData siteOneCustInfoResponeData = null;
		if (b2BUnitModel != null) {
			partnerPointsURL = partnerPointsURL.replace(SiteoneintegrationConstants.PARTNERPOINTS_UNITID_PLACEHOLDER, b2BUnitModel.getUid());
			if(isNewBoomiEnv)
			{
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_LOYALTY, null));
				siteOneCustInfoResponeData = getSiteOneCustInfoRestClient().execute(partnerPointsURL, HttpMethod.GET, null,SiteOneCustInfoResponeData.class,null,SiteoneintegrationConstants.PARTNERPOINTS_INFO_SERVICE_NAME, httpHeaders);
			}
			else
			{
				siteOneCustInfoResponeData = getSiteOneCustInfoRestClient().execute(partnerPointsURL, HttpMethod.GET, null,SiteOneCustInfoResponeData.class,null,SiteoneintegrationConstants.PARTNERPOINTS_INFO_SERVICE_NAME, null);
			}
		}
		return siteOneCustInfoResponeData;
		
	}
	/**
	 * @return the siteOneCustInfoRestClient
	 */
	public SiteOneRestClient<?, SiteOneCustInfoResponeData> getSiteOneCustInfoRestClient() {
		return siteOneCustInfoRestClient;
	}
	/**
	 * @param siteOneCustInfoRestClient the siteOneCustInfoRestClient to set
	 */
	public void setSiteOneCustInfoRestClient(SiteOneRestClient<?, SiteOneCustInfoResponeData> siteOneCustInfoRestClient) {
		this.siteOneCustInfoRestClient = siteOneCustInfoRestClient;
	}

}
