package com.siteone.integration.rest.client;

import com.google.gson.Gson;
import com.siteone.core.model.WebserviceAuditLogModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


/**
 * @author 1230514
 *
 * Rest client 
 * @param <REQUEST> - request data
 * @param <RESPONSE> - response data
 */
public abstract class AbstractSiteOneRestClient<REQUEST, RESPONSE> {
	private static final Logger LOG = Logger.getLogger(AbstractSiteOneRestClient.class);

	protected RestTemplate restTemplate;
	
	protected ModelService modelService;

	abstract void beforeRestCall();
	abstract void afterRestCall(ResponseEntity<RESPONSE> responseEntity);


	public RESPONSE execute(final String url, final HttpMethod httpMethod, final REQUEST requestData,
			final Class<RESPONSE> responseClass,String correlationId,String serviceName,HttpHeaders httpHeaders) throws ResourceAccessException, RestClientException {

		RESPONSE response = null;
		if(httpHeaders!=null && httpHeaders.getContentType()==null) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}
		
		//Gson for converting POJO into JSON
		Gson gson = new Gson();
		
		boolean isLogEnabled = true;
		
		String[] logDisabledServices = Config.getString("log.disabled.services", StringUtils.EMPTY).split(",");
		
		if(null != logDisabledServices && Arrays.asList(logDisabledServices).contains(serviceName)){
			isLogEnabled = false;
		}
		String requestForAudit = StringUtils.EMPTY;
		
		String responseForAudit = StringUtils.EMPTY;

		if (null != requestData) {
			requestForAudit = gson.toJson(requestData);
		}
		if (isLogEnabled) {
			LOG.error("SERVICE URL : " + url + " Request : " + requestForAudit);
		}
		
		if(Config.getBoolean(SiteoneintegrationConstants.INTEGRATION_ENABLE_KEY, true))
		{
		try {
			
			if(SiteoneintegrationConstants.PRICE_SERVICE_NAME.equalsIgnoreCase(serviceName)) {
				((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(Config.getInt("rest.price.connection.timeout", 20000));
				((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(Config.getInt("rest.price.read.timeout", 20000));
			}else {
				((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(Config.getInt("rest.connection.timeout", 20000));
				((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(Config.getInt("rest.read.timeout", 20000));
			}
			beforeRestCall();
			if(null == httpHeaders){
				if (httpMethod.equals(HttpMethod.POST)) {
					response = restTemplate.postForObject(url, requestData, responseClass);

				} else if (httpMethod.equals(HttpMethod.GET)) {
					response = restTemplate.getForObject(url, responseClass);
				}
			}else{
					ResponseEntity<RESPONSE> responseEntity = null;
					if (null != requestData) {
						HttpEntity<REQUEST> requestEntity = new HttpEntity<REQUEST>(requestData, httpHeaders);
						responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, responseClass);

					} else {
						HttpEntity<String> requestEntity = new HttpEntity<String>(SiteoneintegrationConstants.HTTP_ENTITY_KEY, httpHeaders);
						responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, responseClass);
					}

				response = responseEntity.getBody();
				afterRestCall(responseEntity);
			}

		} catch (ResourceAccessException resourceAccessException) {
			responseForAudit = resourceAccessException.getMessage();
			logError(resourceAccessException, serviceName, url, requestForAudit, responseForAudit);
			saveAudit(serviceName, url, requestForAudit, responseForAudit, correlationId);
			if(SiteoneintegrationConstants.ORDER_SUBMIT_SERVICE_NAME.equals(serviceName) 
					   || SiteoneintegrationConstants.CONTACT_SERVICE_NAME.equals(serviceName)
					   || SiteoneintegrationConstants.ADDRESS_SERVICE_NAME.equals(serviceName)
					   || SiteoneintegrationConstants.PRICE_SERVICE_NAME.equals(serviceName))  {
				
						throw resourceAccessException ;
		}
			
		} catch (RestClientException restClientException) {
			responseForAudit = restClientException.getMessage();
			logError(restClientException, serviceName, url, requestForAudit, responseForAudit);
			if(SiteoneintegrationConstants.ORDER_SUBMIT_SERVICE_NAME.equals(serviceName)) {
				throw restClientException ;
			}
		}
		
		}

		if(null != response) {
			responseForAudit = gson.toJson(response);
		}
		if (isLogEnabled) {
				LOG.error("Response : "+responseForAudit);
		}
		saveAudit(serviceName, url, requestForAudit, responseForAudit, correlationId);
		return response;
	}

	

	/**
	 * This method is used to save the request and response parameters in DB
	 * @param serviceName - Name of the service
	 * @param endPointUrl - End point url
	 * @param request - request JSON
	 * @param response - response JSON
	 * @param corrId - unique ID for each request
	 */
	private void saveAudit(final String serviceName, final String endPointUrl, String request, final String response, final String corrId)
	{
        final String auditableServicesString = Config.getString("audit.log.services.list", null);

		//logging only certain request/responses to WebServiceAuditLog table.
		if(StringUtils.contains(auditableServicesString, serviceName)) {
			String[] requestDisabledServices = Config.getString("request.disabled.services", StringUtils.EMPTY).split(",");

			if (null != requestDisabledServices && Arrays.asList(requestDisabledServices).contains(serviceName)) {
				request = StringUtils.EMPTY;
			}

			final WebserviceAuditLogModel webserviceAuditLog = getModelService().create(WebserviceAuditLogModel.class);

			webserviceAuditLog.setServiceName(serviceName);
			webserviceAuditLog.setEndPointUrl(endPointUrl);
			webserviceAuditLog.setRequestLog(request);
			webserviceAuditLog.setResponseLog(response);
			webserviceAuditLog.setCorrelationId(corrId);

			try {
				getModelService().save(webserviceAuditLog);
			} catch (ModelSavingException modelSavingException) {
				LOG.error("Not able to save in webserviceAuditLog for " + serviceName + " and correlationId is " + corrId, modelSavingException);
			}
		}

	}

	private void logError(RestClientException ex, String serviceName, String url, String requestForAudit, String responseForAudit){
		StringBuilder sb = new StringBuilder();
		sb.append("Service name : ").append(serviceName).append("\n")
				.append("Endpoint : ").append(url).append("\n")
				.append("Request : ").append(requestForAudit).append("\n")
				.append("Response : ").append(responseForAudit);
		LOG.error(sb.toString(), ex);
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
}


