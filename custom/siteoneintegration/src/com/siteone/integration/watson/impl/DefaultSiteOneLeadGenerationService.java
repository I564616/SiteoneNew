package com.siteone.integration.watson.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
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
//import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.facade.LeadGenarationData;
import com.siteone.integration.customer.data.Contact;
import com.siteone.integration.customer.data.CustomFields;
import com.siteone.integration.customer.data.LookupKeyFields;
import com.siteone.integration.customer.data.UpdateCust;
import com.siteone.integration.watson.AbstractWatsonIntegrationService;
import com.siteone.integration.watson.SiteOneLeadGenerationService;

import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;

/**
 * @author 1190626
 *
 */
public class DefaultSiteOneLeadGenerationService extends AbstractWatsonIntegrationService implements SiteOneLeadGenerationService
{
	private static final String SAVE_API = "email.preference.saveAPI";
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneLeadGenerationService.class);
	
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
	public String saveLeadGenerationData(final LeadGenarationData leadGenarationData) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{
		final String acessToken = fetchAccessToken();
		final HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", "Bearer " + acessToken);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		final List<LookupKeyFields> lookupKeyFields = new ArrayList<LookupKeyFields>();
		final LookupKeyFields lookUpKey = new LookupKeyFields();
		lookUpKey.setName("Email");
		lookUpKey.setValue(leadGenarationData.getEmail());

		lookupKeyFields.add(lookUpKey);

		final List<CustomFields> customFields = new ArrayList<CustomFields>();
		
		for (Field field : leadGenarationData.getClass().getDeclaredFields())
		{
			CustomFields customField = new CustomFields();
			field.setAccessible(true);
			try 
			{
				customField.setName(field.getName());
				customField.setValue((String) field.get(leadGenarationData));
				customFields.add(customField);
			} 
			catch (IllegalArgumentException | IllegalAccessException e) 
			{
				LOG.error(e.getMessage(), e);
			}
		}

		final CustomFields sourceSystemField = new CustomFields();
		sourceSystemField.setName("sourceSystem");
		sourceSystemField.setValue("hybrisLeadForm");
		customFields.add(sourceSystemField);

		final UpdateCust updCust = new UpdateCust();
		final Contact contact = new Contact();
		contact.setEmail(leadGenarationData.getEmail());
		contact.setCustomFields(customFields);

		updCust.setContact(contact);
		updCust.setLookupKeyFields(lookupKeyFields);

		final HttpEntity<UpdateCust> requestEntity = new HttpEntity<UpdateCust>(updCust, requestHeaders);
		LOG.info("RequestEntityJSON -> "+ new ObjectMapper().writeValueAsString(requestEntity));
		final RestTemplate restTemplate = new RestTemplate();

		final HttpClient httpClient = HttpClients.createDefault();
		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
	
		restTemplate.setRequestFactory(requestFactory);
		final ResponseEntity<String> responseEntity;
		
		String URL  = configurationService.getConfiguration().getString(SAVE_API);
		LOG.info("URL -> "+URL);
		responseEntity = restTemplate.exchange(URL,
			HttpMethod.PATCH, requestEntity, String.class);
		final String result = responseEntity.getBody();
		LOG.info("Result -> "+result);
		
		return result;
	}
	

}
