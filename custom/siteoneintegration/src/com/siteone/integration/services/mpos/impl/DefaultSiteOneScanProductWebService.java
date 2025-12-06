package com.siteone.integration.services.mpos.impl;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.scan.product.data.SiteOneScanProductResponse;
import com.siteone.integration.scan.product.data.SiteOneWsScanProductRequestData;
import com.siteone.integration.services.mpos.SiteOneScanProductWebService;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import de.hybris.platform.util.Config;
import org.springframework.web.client.ResourceAccessException;

public class DefaultSiteOneScanProductWebService implements SiteOneScanProductWebService {

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneScanProductWebService.class.getName());

	private SiteOneRestClient<SiteOneWsScanProductRequestData, SiteOneScanProductResponse> siteOneRestClient;

	@Override
	public SiteOneScanProductResponse getScanProduct(SiteOneWsScanProductRequestData scanProductRequest, boolean isNewBoomiEnv) throws ResourceAccessException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		SiteOneScanProductResponse scanProductResponse = null;
		if (isNewBoomiEnv) {
			httpHeaders.set(SiteoneintegrationConstants.SCAN_PRODUCT_SERVICE_KEY_NAME,
					Config.getString(SiteoneintegrationConstants.SCAN_PRODUCT_NEW_SERVICE_KEY, null));
			scanProductResponse = executeMPOSCustomerBlanketCall(scanProductRequest, httpHeaders, isNewBoomiEnv);
		} else {
			httpHeaders.set(SiteoneintegrationConstants.SCAN_PRODUCT_SERVICE_KEY_NAME,
					Config.getString(SiteoneintegrationConstants.SCAN_PRODUCT_SERVICE_KEY, null));
			scanProductResponse = executeMPOSCustomerBlanketCall(scanProductRequest, httpHeaders, isNewBoomiEnv);
		}
		
		int scanProductMaxRetryCount = Config.getInt(SiteoneintegrationConstants.SCAN_PRODUCT_SERVICE_RETRY, 1);
		int retryCount=0;
		while(scanProductResponse==null && retryCount<scanProductMaxRetryCount ){
			retryCount++;
			LOG.error("Retrying scanProductRequest for - "+scanProductRequest.getSearchText()+"...retry# "+retryCount);
			scanProductResponse = executeMPOSCustomerBlanketCall(scanProductRequest,httpHeaders, isNewBoomiEnv);
		}

		return scanProductResponse;
	}

	private SiteOneScanProductResponse executeMPOSCustomerBlanketCall(SiteOneWsScanProductRequestData scanProductRequest, HttpHeaders httpHeaders, boolean isNewBoomiEnv){
		SiteOneScanProductResponse scanProductResponse = null;

		try{
			scanProductResponse = getSiteOneRestClient().execute(
					(isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.SCAN_PRODUCT_NEW_SERVICE_URL, null)
							: Config.getString(SiteoneintegrationConstants.SCAN_PRODUCT_SERVICE_URL, null)),
					HttpMethod.POST, scanProductRequest, SiteOneScanProductResponse.class, UUID.randomUUID().toString(),
					SiteoneintegrationConstants.SCAN_PRODUCT_SERVICE_NAME, httpHeaders);
		}catch(final ResourceAccessException resourceAccessException){
			LOG.error("Not able to establish connection with scan prodcut API for upc "+scanProductRequest.getSearchText(),resourceAccessException );
		}
		return scanProductResponse;
	}

	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<SiteOneWsScanProductRequestData, SiteOneScanProductResponse> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	/**
	 * @param siteOneRestClient the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsScanProductRequestData, SiteOneScanProductResponse> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}
}
