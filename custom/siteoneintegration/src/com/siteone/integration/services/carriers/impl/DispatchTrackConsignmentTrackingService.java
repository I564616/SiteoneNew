package com.siteone.integration.services.carriers.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.Clock;
import java.time.Instant;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
//import org.codehaus.jackson.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
//import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siteone.integration.carrier.data.OneOf;
import com.siteone.integration.carrier.data.SiteOneCarrierTrackingRequestData;
import com.siteone.integration.carrier.data.SiteOneCarrierTrackingResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.carriers.CarrierTrackingService;
import com.siteone.integration.carrier.data.CarrierAuthResponse;

import de.hybris.platform.consignmenttrackingservices.daos.impl.DefaultConsignmentDao;
import de.hybris.platform.consignmenttrackingservices.delivery.data.ConsignmentEventData;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
/**
 * @author 1190626
 *
 */
public class DispatchTrackConsignmentTrackingService implements CarrierTrackingService
{
	private static final Logger LOG = Logger.getLogger(DispatchTrackConsignmentTrackingService.class);
	private static final String EXPORT_API = "dispatchTrack.exportAPI.url";
	private static final String CLIENT_ID = "dispatchTrack.oauth2.clientId";
	private static final String CLIENT_SECRET = "dispatchTrack.oauth2.clientSecret";
	private static final String CARRIER_AUTH_TOKEN_API = "dispatchTrack.authTokenAPI.url";

	private static String ACCESS_TOKEN;
	private static String TOKEN_EXPIRY;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;	
	
	private DefaultConsignmentDao defaultConsignmentDao;
	
	
	
	private SiteOneCarrierTrackingResponseData exportTrackingDetails(final String orderCode, final Date requestedDate, final Date scheduledDate) throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{
		
		final HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", "Bearer " + getAccessToken());
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		SiteOneCarrierTrackingRequestData requestData=prepareRequest(orderCode, requestedDate, scheduledDate);
				
		SiteOneRestClient<SiteOneCarrierTrackingRequestData, SiteOneCarrierTrackingResponseData> siteOneTrackingClient = new SiteOneRestClient<>();		
		siteOneTrackingClient.setRestTemplate(new RestTemplate());
		String URL  = configurationService.getConfiguration().getString(EXPORT_API);
		 
		SiteOneCarrierTrackingResponseData response=siteOneTrackingClient.execute(URL,
			HttpMethod.POST, requestData, SiteOneCarrierTrackingResponseData.class,UUID.randomUUID().toString(),"DispatchTrackConsignmentTrackingService",requestHeaders);
		
		return response;
	}

	private SiteOneCarrierTrackingRequestData prepareRequest(final String orderCode, final Date requestedDate, final Date scheduledDate) {
		SiteOneCarrierTrackingRequestData requestData=new SiteOneCarrierTrackingRequestData();
		requestData.setOrder_number(orderCode);
		OneOf oneOf= new OneOf();
		if(requestedDate!=null) {
			oneOf.setRequest_date(new SimpleDateFormat("MM/dd/yyyy").format(requestedDate));
		}else {
			oneOf.setSchedule_date(new SimpleDateFormat("MM/dd/yyyy").format(scheduledDate));
		}
		requestData.setOneOf(oneOf);
		requestData.setStart_time("");
		requestData.setEnd_time("");
		requestData.setStatus("");
		return requestData;	
	}
	
	/**
	 * Retrieves the consignment events from Dispatch Track API
	 * @param trackingId the tracking id of the consignment
	 * 
	 * @return list of consignment events data
	 */
	public List<ConsignmentEventData> getConsignmentEvents(final String trackingId) {
		List<ConsignmentEventData> consignmentEventDataList=new ArrayList<>();
		Map<String, String> params=new HashMap<>();
		params.put("trackingID", trackingId);
		
		ConsignmentModel consignmentModel=getDefaultConsignmentDao().find(params).stream().findFirst().orElse(null);
		
		try {
			SiteOneCarrierTrackingResponseData carrierTrackingResponseData=null;
			
			if(consignmentModel.getOrder()!=null) {
				carrierTrackingResponseData=exportTrackingDetails(consignmentModel.getOrder().getCode(), consignmentModel.getRequestedDate(), consignmentModel.getScheduledDate());
			}
			
			if(carrierTrackingResponseData!=null) {				
				carrierTrackingResponseData.getService_orders().forEach(serviceOrder->{
					try {
						ConsignmentEventData scheduledConsignmentEventData=new ConsignmentEventData();
						scheduledConsignmentEventData.setEventDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(serviceOrder.getScheduled_at()));
						scheduledConsignmentEventData.setDetail(serviceOrder.getStatus());
						consignmentEventDataList.add(scheduledConsignmentEventData);
						
						ConsignmentEventData finishedConsignmentEventData=new ConsignmentEventData();
						finishedConsignmentEventData.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(serviceOrder.getDelivery_date()));
						finishedConsignmentEventData.setDetail(serviceOrder.getService_type());
						consignmentEventDataList.add(finishedConsignmentEventData);	
					} catch (final ParseException e)
					{
						LOG.error(e);
					}
				});
			}
			
		} catch (ResourceAccessException e) {
			LOG.error("Error in Resource Access",e);
		} catch (JsonParseException e) {
			LOG.error("Error in Json parsing",e);
		} catch (JsonMappingException e) {
			LOG.error("Error in Json mapping",e);
		} catch (RestClientException e) {
			LOG.error("Error in Rest client",e);
		} catch (IOException e) {
			LOG.error("Error in Input Output",e);
		} 
		return consignmentEventDataList;
	}
	
	
	
	/**
	 * Retrieves the authentication token from dispatch track api
	 * @return the authentication token
	 * @throws ResourceAccessException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 */
	private String getAccessToken() throws ResourceAccessException, JsonParseException, JsonMappingException, IOException, RestClientException
	{
		Instant currentTime = Instant.now(Clock.systemUTC());	
		if(ACCESS_TOKEN!=null  && currentTime.compareTo(Instant.parse(TOKEN_EXPIRY)) < 0)
		{
			return ACCESS_TOKEN;
		}
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		final String clientId = configurationService.getConfiguration().getString(CLIENT_ID);
		final String clientSecret = configurationService.getConfiguration().getString(CLIENT_SECRET);

		map.add(SiteoneintegrationConstants.GRANT_TYPE, SiteoneintegrationConstants.CLIENT_CREDENTIALS);
		map.add(SiteoneintegrationConstants.CLIENT_ID, clientId);
		map.add(SiteoneintegrationConstants.CLIENT_SECRET, clientSecret);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		final RestTemplate restTemplate = new RestTemplate();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());    
		messageConverters.add(new FormHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);

		final String URL = configurationService.getConfiguration().getString(CARRIER_AUTH_TOKEN_API);
		CarrierAuthResponse response = (CarrierAuthResponse) restTemplate.postForObject(URL, request, CarrierAuthResponse.class);
		ACCESS_TOKEN=response.getAccess_token();
		TOKEN_EXPIRY=response.getAccess_token_expires_at();			
	
		return ACCESS_TOKEN;
     }
	
	
	protected DefaultConsignmentDao getDefaultConsignmentDao() {
		return defaultConsignmentDao;
	}

	public void setDefaultConsignmentDao(DefaultConsignmentDao defaultConsignmentDao) {
		this.defaultConsignmentDao = defaultConsignmentDao;
	}
	
}
