package com.siteone.integration.watson.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.integration.rest.client.SiteOneRestClient;

import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.apache.hc.client5.http.impl.classic.HttpClients;
//import org.codehaus.jackson.JsonParseException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.granule.json.JSONArray;
import com.granule.json.JSONException;
import com.granule.json.JSONObject;
import com.siteone.integration.customer.data.Contact;
import com.siteone.integration.customer.data.CustomFields;
import com.siteone.integration.customer.data.LookupKeyFields;
import com.siteone.integration.customer.data.UpdateCust;
import com.siteone.integration.customer.data.UserEmailData;
import com.siteone.integration.watson.AbstractWatsonIntegrationService;
import com.siteone.integration.watson.SiteOneUserPreferenceService;

import org.apache.log4j.Logger;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.enums.PhoneContactInfoType;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;


public class DefaultSiteOneUserPreferenceService extends AbstractWatsonIntegrationService implements SiteOneUserPreferenceService{
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService; 
	
	private static final String FETCH_API = "email.preference.fetchAPI";
	private static final String SAVE_API = "email.preference.saveAPI";
	private static final String CREATE_API = "email.preference.createAPI";
	private static final String SOURCE_SYSTEM ="preferenceCenter";

	private static final String PREFERENCE_SERVICE_READ_CALL ="preferenceServiceReadCall";
	private static final String PREFERENCE_SERVICE_WRITE_CALL ="preferenceServiceWriteCall";

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneUserPreferenceService.class);

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
	public UserEmailData fetchPreferenceData(final B2BCustomerModel customer) throws ResourceAccessException,JsonParseException, JsonMappingException, IOException, RestClientException
	{
		final String email = customer.getContactEmail();
		final String url = configurationService.getConfiguration().getString(FETCH_API);
		String acessToken=fetchAccessToken();
		final HttpHeaders requestHeaders = new HttpHeaders();
		final ObjectMapper mapper = new ObjectMapper();

		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.add("Authorization", "Bearer "+acessToken);

		ResponseEntity<String> responseEntity;

		final List<LookupKeyFields> lookupKeyFields = new ArrayList<LookupKeyFields>();
		final UpdateCust updCust = new UpdateCust();
		final LookupKeyFields lookUpKey = new LookupKeyFields();
		lookUpKey.setName("email");
		lookUpKey.setValue(customer.getContactEmail());
		lookupKeyFields.add(lookUpKey);

		updCust.setLookupKeyFields(lookupKeyFields);

		final HttpEntity<?> requestEntity = new HttpEntity<UpdateCust>(updCust, requestHeaders);
		LOG.info("RequestEntityJSON -> "+ new ObjectMapper().writeValueAsString(requestEntity));
		final RestTemplate restTemplate = new RestTemplate();
		String requestUrl=url.concat("email="+email);
		LOG.info("URL -> "+requestUrl);

		try
		{
			responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
		}
		catch(ResourceAccessException | HttpClientErrorException rae)
		{
			LOG.error("Exception -> " , rae);
			createPreference(customer);
			responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
		}

		String result = null;
		if (null!=responseEntity )
		{
	    	result	= responseEntity.getBody();
			LOG.info("Result -> "+result);
		}

		String jsonString = null;

		try
		{
			final JSONObject jsonObject = new JSONObject(result);
			final JSONArray array = jsonObject.getJSONArray("data");
			final JSONObject jsonObj = array.getJSONObject(0);
			jsonString = mapper.writeValueAsString(jsonObj);
		}
		catch (final JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return mapper.readValue(jsonString, UserEmailData.class);
	}
	
	@Override
	public String saveEmailPreference(String emailType,String email,String emailTopicPreference, String orderPromo) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{
		final String acessToken = fetchAccessToken();
		final HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", "Bearer " + acessToken);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		final List<LookupKeyFields> lookupKeyFields = new ArrayList<LookupKeyFields>();
		final UpdateCust updCust = new UpdateCust();
		final LookupKeyFields lookUpKey = new LookupKeyFields();

		lookUpKey.setName("email");
		lookUpKey.setValue(email);

		lookupKeyFields.add(lookUpKey);

		final Contact contact = new Contact();
		contact.setEmail(email);

		final List<CustomFields> custFields = new ArrayList<CustomFields>();
		final CustomFields customField = new CustomFields();
		customField.setName("emailPromo");
		customField.setValue(emailType);
		
		if (null != orderPromo)
		{
		final CustomFields customFieldOrderEmail = new CustomFields();
		customFieldOrderEmail.setName("orderPromo");
		customFieldOrderEmail.setValue(orderPromo);
		custFields.add(customFieldOrderEmail);
		}

		final CustomFields customFieldEmailTopic = new CustomFields();
		customFieldEmailTopic.setName("EmailTopicPreference");
		customFieldEmailTopic.setValue(emailTopicPreference);
		custFields.add(customField);
		custFields.add(customFieldEmailTopic);

		final CustomFields sourceSystemField = new CustomFields();
		sourceSystemField.setName("sourceSystem");
		sourceSystemField.setValue(SOURCE_SYSTEM);
		custFields.add(sourceSystemField);

		contact.setCustomFields(custFields);
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
		responseEntity = restTemplate.exchange(URL, HttpMethod.PATCH, requestEntity, String.class);
		final String result = responseEntity.getBody();
        LOG.info("Result -> "+result);
		String contactId = null;

		try
		{
			final JSONObject jsonObject = new JSONObject(result);
			final JSONObject jsonObj = jsonObject.getJSONObject("data");
			contactId = jsonObj.getString("id");
		}
		catch (final JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return contactId;
	}


	private String createPreference(final B2BCustomerModel customer) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{		
		String company = null;		
		String postalCode = null;

		Collection <AddressModel> addresses = customer.getAddresses();
		
		if (null !=addresses && !addresses.isEmpty())
		{
			for (AddressModel address : addresses)
			{
				company = address.getCompany();				
				postalCode = address.getPostalcode();
			}
		}

		  	final String acessToken = fetchAccessToken();
			final HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Authorization", "Bearer " + acessToken);
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			final List<CustomFields> custFields = new ArrayList<CustomFields>();
		
			final List<LookupKeyFields> lookupKeyFields = new ArrayList<LookupKeyFields>();
			final UpdateCust updCust = new UpdateCust();

			final LookupKeyFields lookupKey = new LookupKeyFields();
			lookupKey.setName("email");
			lookupKey.setValue(customer.getContactEmail());
			lookupKeyFields.add(lookupKey);
			
			final Contact contact = new Contact();
			contact.setEmail(customer.getContactEmail());
			
			final CustomFields customEmail = new CustomFields();
			customEmail.setName("Email");
			customEmail.setValue(customer.getContactEmail());
			custFields.add(customEmail);

			final CustomFields customEmailPromo = new CustomFields();
			customEmailPromo.setName("emailPromo");
			customEmailPromo.setValue("New");
			custFields.add(customEmailPromo);

			final CustomFields customFieldOrderEmail = new CustomFields();
		    customFieldOrderEmail.setName("orderPromo");
			customFieldOrderEmail.setValue("No");
			custFields.add(customFieldOrderEmail);

			if (null != customer.getFirstName())
			{
			final CustomFields customFirstName = new CustomFields();
			customFirstName.setName("firstname");
			customFirstName.setValue(customer.getFirstName());
			custFields.add(customFirstName);
			}

			if (null != customer.getLastName())
			{
			final CustomFields customLastName = new CustomFields();
			customLastName.setName("lastname");
			customLastName.setValue(customer.getLastName());
			custFields.add(customLastName);
			}
			
			if (null != company)
			{
			final CustomFields customCompany = new CustomFields();
			customCompany.setName("company");
			customCompany.setValue(company);
			custFields.add(customCompany);
			}
			
			if (null != customer.getContactInfos())
			{
				customer.getContactInfos().forEach(info -> {
					if (info instanceof PhoneContactInfoModel)
					{
						final PhoneContactInfoModel phoneInfo = (PhoneContactInfoModel) info;
						if (PhoneContactInfoType.MOBILE.equals(phoneInfo.getType()))
						{
							final String contactNumber = phoneInfo.getPhoneNumber();
							final CustomFields customPhone = new CustomFields();
							customPhone.setName("phone");
							customPhone.setValue(contactNumber);
							custFields.add(customPhone);
						}
					}
				});
			}

			if (null != postalCode)
			{
			final CustomFields customPostalCode = new CustomFields();
			customPostalCode.setName("postalcode");
			customPostalCode.setValue(postalCode);
			custFields.add(customPostalCode);
			}
			
			final CustomFields customSourceSystem = new CustomFields();
			customSourceSystem.setName("sourceSystem");
			customSourceSystem.setValue(SOURCE_SYSTEM);
			custFields.add(customSourceSystem);
							
			contact.setCustomFields(custFields);
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
			responseEntity = restTemplate.exchange(URL, HttpMethod.PATCH, requestEntity, String.class);
			final String result = responseEntity.getBody();
			LOG.info("Result -> "+result);
			String contactId = null;

			try
			{
				final JSONObject jsonObject = new JSONObject(result);
				final JSONObject jsonObj = jsonObject.getJSONObject("data");
				contactId = jsonObj.getString("id");
			}
			catch (final JSONException e)
			{
				LOG.error(e.getMessage(), e);
			}
			return contactId;

	}
}
