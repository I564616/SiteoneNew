package com.siteone.integration.services.ue.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.order.data.QuoteApprovalHistoryResponseData;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoRequestData;
import com.siteone.integration.order.data.SiteOneQuoteShiptoResponseData;
import com.siteone.integration.order.data.SiteOneRequestQuoteRequestData;
import com.siteone.integration.order.data.SiteOneRequestQuoteResponseData;
import com.siteone.integration.quotes.order.data.SiteOneQuotesListResponseData;
import com.siteone.integration.quotes.order.data.SiteoneQuotesRequestData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneQuotesWebService;

import de.hybris.platform.util.Config;

public class DefaultSiteOneQuotesWebService implements SiteOneQuotesWebService {
	
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneQuotesWebService.class);
	
	private SiteOneRestClient<?, SiteOneQuoteDetailResponseData> siteOneRestClient;
	
	private SiteOneRestClient<SiteOneRequestQuoteRequestData, SiteOneRequestQuoteResponseData> siteOneRestClientForQuotes;
	private SiteOneRestClient<SiteOneQuoteShiptoRequestData, SiteOneQuoteShiptoResponseData> siteOneRestClientForShiptoQuotes;
	private SiteOneRestClient<SiteOneQuoteDetailResponseData, SiteOneQuoteDetailResponseData> siteOneRestClientForUpdateQuotes; 
	private SiteOneRestClient<SiteOneQuoteDetailResponseData, SiteOneQuoteDetailResponseData> siteOneRestClientForUpdateQuotesDetail;
	private RestTemplate restTemplate;

	@Override
	public List<SiteOneQuotesListResponseData> getQuotes(SiteoneQuotesRequestData siteoneQuotesRequestData) 
	{
		LOG.error("Request data for getQuotes" + siteoneQuotesRequestData);
		LOG.error("Request data for customernumber " + siteoneQuotesRequestData.getCustomerNumber());
		String quotesUrl = Config.getString(SiteoneintegrationConstants.QUOTES_NEW_URL_KEY, StringUtils.EMPTY);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));
		HttpEntity<SiteoneQuotesRequestData> requestEntity = new HttpEntity<>(siteoneQuotesRequestData, httpHeaders);
		LOG.error("RequestEntity"+requestEntity+"url"+quotesUrl);
		ResponseEntity<Object[]> response = getRestTemplate().postForEntity(quotesUrl, requestEntity, Object[].class);
		Object[] objects = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		final List<SiteOneQuotesListResponseData> responseData= Arrays.stream(objects)
		  .map(object -> mapper.convertValue(object, SiteOneQuotesListResponseData.class))
		  .collect(Collectors.toList());
		return responseData;
		 }
	
	@Override
	public SiteOneQuoteDetailResponseData getQuotesDetails(final boolean isNewBoomiEnv,final String quoteHeaderID) 
	{
		String quoteDetailsUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.QUOTES_DETAIL_NEW_URL_KEY, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.QUOTES_DETAIL_NEW_URL_KEY, StringUtils.EMPTY));
		quoteDetailsUrl = quoteDetailsUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_QUOTE_HEADER_ID, quoteHeaderID);
		HttpHeaders httpHeaders = new HttpHeaders();
		if(isNewBoomiEnv)
		{
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));
			final SiteOneQuoteDetailResponseData response = getSiteOneRestClient().execute(quoteDetailsUrl,
		               HttpMethod.GET, null, SiteOneQuoteDetailResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.QUOTES_DETAILS_SERVICE_NAME,httpHeaders);
			LOG.error("Response data for getQuotes" + response);
			return response;
		}
		else
		{
		return null;
		}
		
			
		}
	
	public SiteOneRequestQuoteResponseData updateRequestQuote(SiteOneRequestQuoteRequestData siteoneQuotesRequestData) 
	{
		LOG.error("Request Quotes " + siteoneQuotesRequestData);
		String quotesUrl = Config.getString(SiteoneintegrationConstants.QUOTES_REQUEST_NEW_URL_KEY, StringUtils.EMPTY);

		HttpHeaders httpHeaders = new HttpHeaders();
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));
			final SiteOneRequestQuoteResponseData response = getSiteOneRestClientForQuotes().execute(quotesUrl,
		               HttpMethod.POST, siteoneQuotesRequestData, SiteOneRequestQuoteResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.QUOTES_REQUEST_SERVICE_NAME,httpHeaders);
			LOG.error("Response data for getQuotes" + response);
			return response;
		}
		
	public void updateQuote(SiteOneQuoteDetailResponseData quoteDetails, String quoteHeaderID) 
	{
		LOG.error("Update Full Quote " + quoteDetails);
		String quotesUrl = Config.getString(SiteoneintegrationConstants.UPDATE_QUOTE_NEW_URL_KEY, StringUtils.EMPTY);
		quotesUrl = quotesUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_QUOTE_HEADER_ID, quoteHeaderID);
		LOG.error("Quotes url for update quote is " + quotesUrl);

		HttpHeaders httpHeaders = new HttpHeaders();
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));
			getSiteOneRestClientForUpdateQuotes().execute(quotesUrl,
		               HttpMethod.PUT, quoteDetails, SiteOneQuoteDetailResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.QUOTES_REQUEST_SERVICE_NAME,httpHeaders);
			LOG.error("Response data for getSiteOneRestClientForUpdateQuotes " + getSiteOneRestClientForUpdateQuotes());
		}
	
	public void updateQuoteDetail(SiteOneQuoteDetailResponseData quoteDetails, String quoteHeaderID) 
	{
		LOG.error("Update Partial Quote Details " + quoteDetails);
		String quotesUrl = Config.getString(SiteoneintegrationConstants.UPDATE_QUOTE_NEW_URL_KEY, StringUtils.EMPTY);
		quotesUrl = quotesUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_QUOTE_HEADER_ID, quoteHeaderID);
		LOG.error("Quotes url for update quote is " + quotesUrl);

		HttpHeaders httpHeaders = new HttpHeaders();
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));
			getSiteOneRestClientForUpdateQuotes().execute(quotesUrl,
		               HttpMethod.PUT, quoteDetails, SiteOneQuoteDetailResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.QUOTES_REQUEST_SERVICE_NAME,httpHeaders);
			LOG.error("Response data for getSiteOneRestClientForUpdateQuotes " + getSiteOneRestClientForUpdateQuotes());
		}

	public List<SiteOneQuoteShiptoResponseData> shiptoQuote(SiteOneQuoteShiptoRequestData siteOneQuoteShiptoRequestData) 
	{
		LOG.error("Shipping Quotes " + siteOneQuoteShiptoRequestData.getCustomerNumbers());
		String quotesUrl = Config.getString(SiteoneintegrationConstants.SUMMARY_QUOTE_NEW_URL_KEY, StringUtils.EMPTY);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));			
			HttpEntity<SiteOneQuoteShiptoRequestData> requestEntity = new HttpEntity<>(siteOneQuoteShiptoRequestData, httpHeaders);
			ResponseEntity<Object[]> response = getRestTemplate().postForEntity(quotesUrl, requestEntity, Object[].class);
			Object[] objects = response.getBody();
			ObjectMapper mapper = new ObjectMapper();
			final List<SiteOneQuoteShiptoResponseData> responseData= Arrays.stream(objects)
			  .map(object -> mapper.convertValue(object, SiteOneQuoteShiptoResponseData.class))
			  .collect(Collectors.toList());
			return responseData;
		}
	
	public List<QuoteApprovalHistoryResponseData> quoteApprovalHistory(final boolean isNewBoomiEnv, String quoteDetailID) 
	{
		String quoteApprovalHistoryUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.QUOTES_APPROVAL_HISTORY_NEW_URL_KEY, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.QUOTES_APPROVAL_HISTORY_NEW_URL_KEY, StringUtils.EMPTY));
		quoteApprovalHistoryUrl = quoteApprovalHistoryUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_QUOTE_DETAIL_ID, quoteDetailID);
		HttpHeaders httpHeaders = new HttpHeaders();
		if(isNewBoomiEnv)
		{
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_QUOTES, null));
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

		    ResponseEntity<QuoteApprovalHistoryResponseData[]> responseEntity = restTemplate.exchange(quoteApprovalHistoryUrl,HttpMethod.GET,entity,QuoteApprovalHistoryResponseData[].class);
		    QuoteApprovalHistoryResponseData[] responseBody = responseEntity.getBody();

		    List<QuoteApprovalHistoryResponseData> responseList = responseBody != null
		        ? Arrays.asList(responseBody)
		        : Collections.emptyList();

		    LOG.error("Response data for quoteApprovalHistory: {}" + responseList);

		    return responseList;
		}
		else
		{
		return null;
		}
	}

	public SiteOneRestClient<?, SiteOneQuoteDetailResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(SiteOneRestClient<?, SiteOneQuoteDetailResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
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

	public SiteOneRestClient<SiteOneRequestQuoteRequestData, SiteOneRequestQuoteResponseData> getSiteOneRestClientForQuotes() {
		return siteOneRestClientForQuotes;
	}

	public void setSiteOneRestClientForQuotes(SiteOneRestClient<SiteOneRequestQuoteRequestData, SiteOneRequestQuoteResponseData> siteOneRestClientForQuotes) {
		this.siteOneRestClientForQuotes = siteOneRestClientForQuotes;
	} 
	
	/**
	 * @return the siteOneRestClientForUpdateQuotes
	 */
	public SiteOneRestClient<SiteOneQuoteDetailResponseData, SiteOneQuoteDetailResponseData> getSiteOneRestClientForUpdateQuotes() {
		return siteOneRestClientForUpdateQuotes;
	}

	/**
	 * @param siteOneRestClientForUpdateQuotes the siteOneRestClientForUpdateQuotes to set
	 */
	public void setSiteOneRestClientForUpdateQuotes(
			SiteOneRestClient<SiteOneQuoteDetailResponseData, SiteOneQuoteDetailResponseData> siteOneRestClientForUpdateQuotes) {
		this.siteOneRestClientForUpdateQuotes = siteOneRestClientForUpdateQuotes;
	}
	
	/**
	 * @return the siteOneRestClientForUpdateQuotesDetail
	 */
	public SiteOneRestClient<SiteOneQuoteDetailResponseData, SiteOneQuoteDetailResponseData> getSiteOneRestClientForUpdateQuotesDetail() {
		return siteOneRestClientForUpdateQuotesDetail;
	}

	/**
	 * @param siteOneRestClientForUpdateQuotesDetail the siteOneRestClientForUpdateQuotesDetail to set
	 */
	public void setSiteOneRestClientForUpdateQuotesDetail(
			SiteOneRestClient<SiteOneQuoteDetailResponseData, SiteOneQuoteDetailResponseData> siteOneRestClientForUpdateQuotesDetail) {
		this.siteOneRestClientForUpdateQuotesDetail = siteOneRestClientForUpdateQuotesDetail;
	}

	public SiteOneRestClient<SiteOneQuoteShiptoRequestData, SiteOneQuoteShiptoResponseData> getSiteOneRestClientForShiptoQuotes() {
		return siteOneRestClientForShiptoQuotes;
	}

	public void setSiteOneRestClientForShiptoQuotes(SiteOneRestClient<SiteOneQuoteShiptoRequestData, SiteOneQuoteShiptoResponseData> siteOneRestClientForShiptoQuotes) {
		this.siteOneRestClientForShiptoQuotes = siteOneRestClientForShiptoQuotes;
	}

	

}
