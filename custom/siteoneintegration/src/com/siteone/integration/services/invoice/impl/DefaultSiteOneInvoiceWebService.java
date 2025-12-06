package com.siteone.integration.services.invoice.impl;

import java.util.Arrays;
import java.util.UUID;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceDetailsResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceResponseData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.scan.product.data.SiteOneScanProductResponse;
import com.siteone.integration.scan.product.data.SiteOneWsScanProductRequestData;
import com.siteone.integration.services.invoice.SiteOneInvoiceWebService;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

import org.apache.tomcat.util.codec.binary.Base64;

public class DefaultSiteOneInvoiceWebService implements SiteOneInvoiceWebService {
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneInvoiceWebService.class);
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	private SiteOneRestClient<SiteoneInvoiceRequestData, SiteoneInvoiceResponseData> siteOneRestClient;
	private SiteOneRestClient<?, SiteoneInvoiceDetailsResponseData> siteOneInvoiceRestClient;
	@Override
	public byte[] getInvoiceByOrderShipmentActualId(String orderShipmentActualId) {
		final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,
				SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC + " "
						+ Config.getString(SiteoneintegrationConstants.UE_INVOICE_VERIFICATION_KEY, null));
        headers.setContentType(MediaType.APPLICATION_JSON);


        try {
        	
        	
        	
            HttpEntity<String> requestEntity = new HttpEntity<>("\"{\\\"orderShipmentActualID\\\":\\\""+orderShipmentActualId+"\\\"}\"", headers);

            ResponseEntity<String> response = restTemplate.exchange(Config.getString(SiteoneintegrationConstants.UE_INVOICE_KEY,null),
                    HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
            	
            	LOG.info("Got success response for Invoice OrderShipmentActualId" +orderShipmentActualId);
            	return Base64.decodeBase64(response.getBody());
            }

        } catch (ResourceAccessException resourceAccessException) {
        	LOG.error("Resorce access exception occured while connecting to invoice pdf service",resourceAccessException);
        	throw resourceAccessException;
        }catch(RestClientException restClientException) {
        	LOG.error("Rest client exception occured while connecting to invoice pdf service",restClientException);
        }

        return null;

	}
	

	@Override
	public SiteoneInvoiceResponseData getInvoicesData(SiteoneInvoiceRequestData invoiceRequest,String customerNumber, Integer divisionId, boolean isNewBoomiEnv) throws ResourceAccessException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		SiteoneInvoiceResponseData invoiceResponse = null;
		if (isNewBoomiEnv) {
			httpHeaders.set(SiteoneintegrationConstants.INVOICE_SERVICE_KEY_NAME,
					Config.getString(SiteoneintegrationConstants.INVOICE_NEW_SERVICE_KEY, null));
			invoiceResponse = executeInvoice(invoiceRequest, httpHeaders,customerNumber,divisionId, isNewBoomiEnv);
		} else {
			httpHeaders.set(SiteoneintegrationConstants.INVOICE_SERVICE_KEY_NAME,
					Config.getString(SiteoneintegrationConstants.INVOICE_SERVICE_KEY, null));
			invoiceResponse = executeInvoice(invoiceRequest, httpHeaders,customerNumber,divisionId, isNewBoomiEnv);
		}
		
		return invoiceResponse;
	}

	private SiteoneInvoiceResponseData executeInvoice(SiteoneInvoiceRequestData invoiceRequest, HttpHeaders httpHeaders, String customerNumber, Integer divisionId, boolean isNewBoomiEnv){
		SiteoneInvoiceResponseData invoiceResponse = null;

		String invoiceApiUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.INVOICE_NEW_SERVICE_URL, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.INVOICE_SERVICE_URL, StringUtils.EMPTY));
		invoiceApiUrl = invoiceApiUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_CUSTOMER_NUMBER, customerNumber);
		invoiceApiUrl = invoiceApiUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_DIVISIONID, divisionId.toString());
		try{
			invoiceResponse = getSiteOneRestClient().execute(invoiceApiUrl,
					HttpMethod.POST, invoiceRequest, SiteoneInvoiceResponseData.class, UUID.randomUUID().toString(),
					SiteoneintegrationConstants.INVOICE_SERVICE_NAME, httpHeaders);
		}catch(final ResourceAccessException resourceAccessException){
			LOG.error("Not able to establish connection with scan prodcut API for upc ",resourceAccessException );
		}
		return invoiceResponse;
	}
	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<SiteoneInvoiceRequestData, SiteoneInvoiceResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}
	/**
	 * @param siteOneRestClient the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(
			SiteOneRestClient<SiteoneInvoiceRequestData, SiteoneInvoiceResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}


	@Override
	public SiteoneInvoiceDetailsResponseData getInvoiceDetailsData(String customerNumber, String invoiceNumber,
			boolean isNewBoomiEnv) throws ResourceAccessException {
		{
			final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
			 String divisionId = "1";
			if (basesite.getUid().equalsIgnoreCase("siteone-us"))
			{
				divisionId = "1";
			}
			else
			{
				divisionId = "2";
			}
			String invoiceUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.INVOICE_DETAIL_SERVICE_URL, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.INVOICE_DETAIL_SERVICE_URL, StringUtils.EMPTY));
			invoiceUrl = invoiceUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_CUSTOMER_NUMBER, customerNumber);
			invoiceUrl = invoiceUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_INVOICEID, invoiceNumber);
			invoiceUrl = invoiceUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_DIVISIONID, divisionId);
			HttpHeaders httpHeaders = new HttpHeaders();
			if(isNewBoomiEnv)
			{
				
				httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.INVOICE_NEW_SERVICE_KEY, null));
				return getSiteOneInvoiceRestClient().execute(invoiceUrl,
			               HttpMethod.GET, null, SiteoneInvoiceDetailsResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.INVOICE_DETAIL_SERVICE_NAME,httpHeaders);
			}
			else
			{
				 httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC+" "+Config.getString(SiteoneintegrationConstants.UE_INVOICE_VERIFICATION_KEY, null));
				  return getSiteOneInvoiceRestClient().execute(invoiceUrl,
			               HttpMethod.GET, null, SiteoneInvoiceDetailsResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.INVOICE_DETAIL_SERVICE_NAME,httpHeaders);
			 }
				
			} 
	}


	public SiteOneRestClient<?, SiteoneInvoiceDetailsResponseData> getSiteOneInvoiceRestClient() {
		return siteOneInvoiceRestClient;
	}


	public void setSiteOneInvoiceRestClient(
			SiteOneRestClient<?, SiteoneInvoiceDetailsResponseData> siteOneInvoiceRestClient) {
		this.siteOneInvoiceRestClient = siteOneInvoiceRestClient;
	}


	public BaseSiteService getBaseSiteService() {
		return baseSiteService;
	}


	public void setBaseSiteService(BaseSiteService baseSiteService) {
		this.baseSiteService = baseSiteService;
	}

}
