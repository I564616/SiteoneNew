package com.siteone.integration.watson.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteone.integration.customer.data.*;
import com.siteone.integration.rest.client.SiteOneRestClient;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.log4j.Logger;
//import org.codehaus.jackson.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.facades.emailsubscriptions.data.EmailSubscriptionsData;
import com.siteone.integration.watson.AbstractWatsonIntegrationService;
import com.siteone.integration.watson.EmailSignUpService;

import de.hybris.platform.servicelayer.config.ConfigurationService;

/**
 * @author 1190626
 *
 */
public class DefaultEmailSignUpService extends AbstractWatsonIntegrationService implements EmailSignUpService 
{
	private static final String SAVE_API = "email.preference.saveAPI";
	private static final Logger LOG = Logger.getLogger(DefaultEmailSignUpService.class);
	private static final String SOURCE_SYSTEM ="emailSignUp";

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "siteOneRestClient")
	private SiteOneRestClient<HttpEntity<UpdateCust>, ResponseEntity<String>> restClient;

	public SiteOneRestClient<HttpEntity<UpdateCust>, ResponseEntity<String>> getRestClient()
	{
		return restClient;
	}

	public void setRestClient(SiteOneRestClient<HttpEntity<UpdateCust>, ResponseEntity<String>> restClient)
	{
		this.restClient = restClient;
	}



	@Override
	public String emailSignUp(final EmailSubscriptionsData emailSubscriptionsData) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{
		final String acessToken = fetchAccessToken();
		final HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", "Bearer " + acessToken);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		final List<LookupKeyFields> lookupKeyFields = new ArrayList<LookupKeyFields>();
		final UpdateCust updCust = new UpdateCust();

		final LookupKeyFields lookUpKey = new LookupKeyFields();
		lookUpKey.setName("email");
		lookUpKey.setValue(emailSubscriptionsData.getEmail());
		lookupKeyFields.add(lookUpKey);

		final Contact contact = new Contact();
		contact.setEmail(emailSubscriptionsData.getEmail());

		final List<CustomFields> custFields = new ArrayList<CustomFields>();

		if(null != emailSubscriptionsData.getPostalcode())
        {
            final CustomFields postalCodeField = new CustomFields();
            postalCodeField.setName("postalcode");
            postalCodeField.setValue(emailSubscriptionsData.getPostalcode());
            custFields.add(postalCodeField);
        }

        if(null != emailSubscriptionsData.getUserType())
        {
            final CustomFields userTypeField = new CustomFields();
            userTypeField.setName("userType");
            userTypeField.setValue(emailSubscriptionsData.getUserType());
            custFields.add(userTypeField);
        }

		final CustomFields sourceSystemField = new CustomFields();
		sourceSystemField.setName("sourceSystem");
		sourceSystemField.setValue(SOURCE_SYSTEM);
		custFields.add(sourceSystemField);

        contact.setCustomFields(custFields);
		updCust.setContact(contact);
		updCust.setLookupKeyFields(lookupKeyFields);

		final HttpEntity<UpdateCust> requestEntity = new HttpEntity<UpdateCust>(updCust, requestHeaders);

		LOG.info("requestEntityJSON"+ new ObjectMapper().writeValueAsString(requestEntity));

		final RestTemplate restTemplate = new RestTemplate();

		final HttpClient httpClient = HttpClients.createDefault();
		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		restTemplate.setRequestFactory(requestFactory);
		final ResponseEntity<String> responseEntity;

		String URL  = configurationService.getConfiguration().getString(SAVE_API);
		LOG.info("url"+URL);
		responseEntity = restTemplate.exchange(URL,	HttpMethod.PATCH, requestEntity, String.class);
		final String result = responseEntity.getBody();
        LOG.info("result"+result);
		
		return result;
	}
	
	
}
