package com.siteone.integration.watson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.integration.address.data.SiteOneWsAddressRequestData;
import com.siteone.integration.address.data.SiteOneWsAddressResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import org.apache.log4j.Logger;
//import org.codehaus.jackson.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.AuthResponse;

import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;
/**
 * @author 1190626
 *
 */
public abstract class AbstractWatsonIntegrationService 
{
	
	private static final String CLIENT_ID = "email.preference.clientId";
	private static final String CLIENT_SECRET = "email.preference.clientSecret";
	private static final String REFRESH_TOKEN = "email.preference.refreshToken";
	private static final String AUTH_TOKEN_API= "email.preference.authTokenAPI";
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private static final Logger LOG = Logger.getLogger(AbstractWatsonIntegrationService.class);
	
	public String fetchAccessToken() throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		final String clientId = configurationService.getConfiguration().getString(CLIENT_ID);
		final String clientSecret = configurationService.getConfiguration().getString(CLIENT_SECRET);
		final String refreshToken = configurationService.getConfiguration().getString(REFRESH_TOKEN);

		map.add(SiteoneintegrationConstants.GRANT_TYPE, SiteoneintegrationConstants.REFRESH_TOKEN);
		map.add(SiteoneintegrationConstants.CLIENT_ID, clientId);
		map.add(SiteoneintegrationConstants.CLIENT_SECRET, clientSecret);
		map.add(SiteoneintegrationConstants.REFRESH_TOKEN, refreshToken);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		final RestTemplate restTemplate = new RestTemplate();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());    
		messageConverters.add(new FormHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		
		final String URL = configurationService.getConfiguration().getString(AUTH_TOKEN_API);
		LOG.info("requestEntity for fetch"+request);
		LOG.info("url"+URL);
		AuthResponse response = (AuthResponse) restTemplate.postForObject(URL, request, AuthResponse.class);

		String accessToken=response.getAccess_token();
		return accessToken;
     }
	
}
