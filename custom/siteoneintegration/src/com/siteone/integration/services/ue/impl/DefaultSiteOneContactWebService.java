package com.siteone.integration.services.ue.impl;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.customer.event.PendoEventRequest;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.SiteOneWsB2BCustomerRequestData;
import com.siteone.integration.customer.data.SiteOneWsB2BCustomerResponseData;
import com.siteone.integration.linktopay.order.data.SiteoneLinkToPayPaymentDetailsRequestData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneContactWebService;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.GenericSearchConstants.LOG;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;


/**
 * @author 1230514
 *siteone web contact service class
 */
public class DefaultSiteOneContactWebService implements SiteOneContactWebService
{
private SiteOneRestClient<SiteOneWsB2BCustomerRequestData, SiteOneWsB2BCustomerResponseData> siteOneRestClient;
	
	private Converter<B2BCustomerModel, SiteOneWsB2BCustomerRequestData> siteOneWsB2BCustomerConverter;
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneContactWebService.class.getName());
	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;
	public SiteOneWsB2BCustomerResponseData createB2BCustomer(B2BCustomerModel b2bCustomerModel, boolean isNewBoomiEnv) throws ResourceAccessException
	{
		
		SiteOneWsB2BCustomerRequestData siteOneWsCustomerRequestData = siteOneWsB2BCustomerConverter.convert(b2bCustomerModel);
		siteOneWsCustomerRequestData.setOperationType(SiteoneintegrationConstants.OPERATION_TYPE_CREATE);		
		return getResponse(siteOneWsCustomerRequestData, isNewBoomiEnv);
	}
	
	
	public SiteOneWsB2BCustomerResponseData updateB2BCustomer(B2BCustomerModel b2bCustomerModel, boolean isNewBoomiEnv) throws ResourceAccessException
	{
		return updateOperation(b2bCustomerModel, isNewBoomiEnv);
	}
	
	public SiteOneWsB2BCustomerResponseData disableOrEnableB2BCustomer(B2BCustomerModel b2bCustomerModel, boolean isNewBoomiEnv) throws ResourceAccessException
	{
		return updateOperation(b2bCustomerModel, isNewBoomiEnv);
	}

	protected SiteOneWsB2BCustomerResponseData updateOperation(B2BCustomerModel b2bCustomerModel,
			boolean isNewBoomiEnv) {
		SiteOneWsB2BCustomerRequestData siteOneWsCustomerRequestData = siteOneWsB2BCustomerConverter.convert(b2bCustomerModel);
		siteOneWsCustomerRequestData.setOperationType(SiteoneintegrationConstants.OPERATION_TYPE_UPDATE);
		return getResponse(siteOneWsCustomerRequestData, isNewBoomiEnv);
	}
	
	protected SiteOneWsB2BCustomerResponseData getResponse(SiteOneWsB2BCustomerRequestData siteOneWsCustomerRequestData, boolean isNewBoomiEnv)
	{
		if(isNewBoomiEnv)
		{
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			return getSiteOneRestClient().execute(Config.getString(SiteoneintegrationConstants.CONTACT_SERVICE_NEW_URL_KEY, null),
				HttpMethod.POST, siteOneWsCustomerRequestData, SiteOneWsB2BCustomerResponseData.class,siteOneWsCustomerRequestData.getCorrelationId(),SiteoneintegrationConstants.CONTACT_SERVICE_NAME,httpHeaders);
		}
		else
		{
			return getSiteOneRestClient().execute(Config.getString(SiteoneintegrationConstants.CONTACT_SERVICE_URL_KEY, null),
					HttpMethod.POST, siteOneWsCustomerRequestData, SiteOneWsB2BCustomerResponseData.class,siteOneWsCustomerRequestData.getCorrelationId(),SiteoneintegrationConstants.CONTACT_SERVICE_NAME,null);
		}
	}

	public SiteOneRestClient<SiteOneWsB2BCustomerRequestData, SiteOneWsB2BCustomerResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsB2BCustomerRequestData, SiteOneWsB2BCustomerResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

	public Converter<B2BCustomerModel, SiteOneWsB2BCustomerRequestData> getSiteOneWsB2BCustomerConverter() {
		return siteOneWsB2BCustomerConverter;
	}

	public void setSiteOneWsB2BCustomerConverter(
			Converter<B2BCustomerModel, SiteOneWsB2BCustomerRequestData> siteOneWsB2BCustomerConverter) {
		this.siteOneWsB2BCustomerConverter = siteOneWsB2BCustomerConverter;
	}


	@Override
	public String generatePendoEvent(PendoEventRequest request)  throws ResourceAccessException {
			
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				httpHeaders.set(SiteoneintegrationConstants.X_API_PENDO_HEADER, Config.getString(SiteoneintegrationConstants.X_API_PENDO_KEY, null));
				HttpEntity<PendoEventRequest> requestEntity = new HttpEntity<>(request, httpHeaders);
				LOG.error("Inside Pendo Event web service");
				Gson gson = new Gson();
		LOG.error("SERVICE URL :  Request : " + gson.toJson(request));
				ResponseEntity<String> reponse = getRestTemplate().postForEntity(Config.getString(SiteoneintegrationConstants.PENDO_EVENT_URL,StringUtils.EMPTY) , requestEntity, String.class);
				return reponse.getStatusCode().toString();   
			}
			/**
	 * @return the restTemplate
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}


	/**
	 * @param restTemplate the restTemplate to set
	 */
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
