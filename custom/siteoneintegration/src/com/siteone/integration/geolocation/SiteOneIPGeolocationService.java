package com.siteone.integration.geolocation;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.granule.json.JSONObject;

import com.granule.json.JSONException;
//import org.codehaus.jackson.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.servicelayer.config.ConfigurationService;

/**
 * @author 1190626
 *
 */
public class SiteOneIPGeolocationService
{
	private static final Logger LOG = Logger.getLogger(SiteOneIPGeolocationService.class);
	private static final String IP_GEOLOCATION_URL = "ip.geolocation.url";
	private static final String IP_GEOLOCATION_KEY = "ip.geolocation.key.server";
	private static final String IP_GEOLOCATION_KEY_HEADER = "key";

		
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;	
	
	@Resource(name = "geolocationRestTemplate")
	private RestTemplate restTemplate;
	
	public GeoPoint getGeoPoint()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set(IP_GEOLOCATION_KEY_HEADER,
				configurationService.getConfiguration().getString(IP_GEOLOCATION_KEY));
		headers.setContentType(MediaType.APPLICATION_JSON);
		String ip = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Forwarded-For");
	if(StringUtils.isNotEmpty(ip))
	{	
			ip= ip.split(",")[0];
			LOG.debug("ClientIP address: "+ip);
		
		try
		{
			HttpEntity<String> entity = new HttpEntity<>(headers); 
			StringBuilder ipGeolocationUrl=new StringBuilder();
			ipGeolocationUrl.append(configurationService.getConfiguration().getString(IP_GEOLOCATION_URL)).append(ip)
					.append("?key=").append(configurationService.getConfiguration().getString(IP_GEOLOCATION_KEY));
			ResponseEntity<String> response = restTemplate.exchange(ipGeolocationUrl.toString(), HttpMethod.GET, entity, String.class);
			if (response != null && response.getStatusCode().equals(HttpStatus.OK))
			{
				String body = response.getBody();
				GeoPoint geoPoint = new GeoPoint();
				try
				{
					JSONObject jsonObj = new JSONObject(body);
					if(!"fail".equalsIgnoreCase(jsonObj.getString("status")))
					{						
						geoPoint.setLatitude(Double.valueOf(jsonObj.getString("lat")));
						geoPoint.setLongitude(Double.valueOf(jsonObj.getString("lon")));
						return geoPoint;
					}
				}
				catch (final JSONException e)
				{
					LOG.error(e.getMessage(), e);
				}
				
			}
			return new GeoPoint();
		}
		catch (ResourceAccessException resourceAccessException)
		{
			LOG.error("Resorce access exception occured while connecting to ip geolocation service", resourceAccessException);
			throw resourceAccessException;
		}
		catch (RestClientException restClientException)
		{
			LOG.error("Rest client exception occured while connecting to ip geolocation service", restClientException);
			throw restClientException;
		}
	}
	else
	{
		return new GeoPoint();
	}
}
	
		
}
