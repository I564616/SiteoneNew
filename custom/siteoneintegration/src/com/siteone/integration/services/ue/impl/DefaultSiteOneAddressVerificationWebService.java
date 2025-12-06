package com.siteone.integration.services.ue.impl;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.siteone.integration.addressverification.data.SiteOneWsAddressVerificationResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneAddressVerificationWebService;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.util.Config;

public class DefaultSiteOneAddressVerificationWebService implements SiteOneAddressVerificationWebService{

	private SiteOneRestClient<?, SiteOneWsAddressVerificationResponseData> siteOneRestClient;
	
	public SiteOneWsAddressVerificationResponseData validateAddress(AddressData addressData) {
		
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC+" "+Config.getString(SiteoneintegrationConstants.ADDRESS_VERIFICATION_API_KEY, null));
		
		final SiteOneWsAddressVerificationResponseData siteOneAddressVerificationResponseData = getSiteOneRestClient().execute(buildAddressVerificationUrl(addressData),
				HttpMethod.GET, null, SiteOneWsAddressVerificationResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.ADDRESS_VERIFICATION_SERVICE_NAME,httpHeaders);	
		return siteOneAddressVerificationResponseData;
	}


	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<?, SiteOneWsAddressVerificationResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}


	/**
	 * @param siteOneRestClient the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(
			SiteOneRestClient<?, SiteOneWsAddressVerificationResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}
	
	private String buildAddressVerificationUrl(AddressData addressData){
		String addressVerificationUrl = Config.getString(SiteoneintegrationConstants.ADDRESS_VERIFICATION_SERVICE_URL_KEY, null);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(addressVerificationUrl)
				// Add query parameter
		        .queryParam(SiteoneintegrationConstants.QUERY_PARAM_STREET, addressData.getLine1())
		        .queryParam(SiteoneintegrationConstants.QUERY_PARAM_STREET2, addressData.getLine2())
				.queryParam(SiteoneintegrationConstants.QUERY_PARAM_CITY, addressData.getTown())
				.queryParam(SiteoneintegrationConstants.QUERY_PARAM_STATE, addressData.getRegion().getIsocodeShort())
				.queryParam(SiteoneintegrationConstants.QUERY_PARAM_ZIPCODE, addressData.getPostalCode());
		
		return builder.build().toUriString();
	}

	
}
