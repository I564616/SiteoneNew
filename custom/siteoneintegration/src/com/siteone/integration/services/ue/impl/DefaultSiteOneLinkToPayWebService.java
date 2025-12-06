package com.siteone.integration.services.ue.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;
import com.siteone.integration.linktopay.order.data.SiteoneLinkToPayPaymentDetailsRequestData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneLinkToPayWebService;
import de.hybris.platform.util.Config;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayPaymentInfoData;

public class DefaultSiteOneLinkToPayWebService implements SiteOneLinkToPayWebService{

	private SiteOneRestClient<?, SiteOneWsLinkToPayOrderResponseData> siteOneRestClient;
	private RestTemplate restTemplate;
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneLinkToPayWebService.class.getName());
	@Override
	public SiteOneWsLinkToPayOrderResponseData getOrderDetails(final String orderNumber,boolean isNewBoomiEnv,String correlationID) 
	{
		String linkToPayUrl = (isNewBoomiEnv ? Config.getString(SiteoneintegrationConstants.LINK_TO_PAY_ORDER_NEW_URL_KEY, StringUtils.EMPTY) : Config.getString(SiteoneintegrationConstants.LINK_TO_PAY_ORDER_URL_KEY, StringUtils.EMPTY));
		 linkToPayUrl = linkToPayUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_ORDER_NUMBER, orderNumber);
         linkToPayUrl = linkToPayUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_CORRELATION_ID, correlationID);
		HttpHeaders httpHeaders = new HttpHeaders();
		if(isNewBoomiEnv)
		{
			
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			return getSiteOneRestClient().execute(linkToPayUrl,
		               HttpMethod.GET, null, SiteOneWsLinkToPayOrderResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.LINK_TO_PAY_ORDER_SERVICE_NAME,httpHeaders);
		}
		else
		{
			 httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC+" "+Config.getString(SiteoneintegrationConstants.ADDRESS_VERIFICATION_API_KEY, null));
			  return getSiteOneRestClient().execute(linkToPayUrl,
		               HttpMethod.GET, null, SiteOneWsLinkToPayOrderResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.LINK_TO_PAY_ORDER_SERVICE_NAME,httpHeaders);
		 }
			
		} 
		
	@Override
	public String sendPaymentDetails(final LinkToPayCayanResponseModel cayanResponseModel ,
			boolean isNewBoomiEnv) throws ResourceAccessException {
		final SiteoneLinkToPayPaymentDetailsRequestData siteOneLinkToPayData=siteOneLinkToPaySendRequestData(cayanResponseModel);
		
		if(isNewBoomiEnv)
		{
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			HttpEntity<SiteoneLinkToPayPaymentDetailsRequestData> requestEntity = new HttpEntity<>(siteOneLinkToPayData, httpHeaders);
			LOG.error("Inside LinkToPay Payment web service");
			ResponseEntity<String> reponse = getRestTemplate().postForEntity(Config.getString(SiteoneintegrationConstants.LINK_TO_PAY_PAYMENT_NEW_URL_KEY,StringUtils.EMPTY) , requestEntity, String.class);
			return reponse.getStatusCode().toString();   
		}
		else
		{
			 ResponseEntity<String> reponse = getRestTemplate().postForEntity(Config.getString(SiteoneintegrationConstants.LINK_TO_PAY_PAYMENT_URL_KEY, StringUtils.EMPTY), siteOneLinkToPayData,
		                String.class);
			return reponse.getStatusCode().toString();
		}
	}
	
	public SiteoneLinkToPayPaymentDetailsRequestData siteOneLinkToPaySendRequestData(final LinkToPayCayanResponseModel cayanResponseModel) {
		final SiteoneLinkToPayPaymentDetailsRequestData paymentDetails = new SiteoneLinkToPayPaymentDetailsRequestData();
		final SiteOneWsLinkToPayPaymentInfoData cardDetails = new SiteOneWsLinkToPayPaymentInfoData();
		cardDetails.setAuthCode(cayanResponseModel.getAuthCode());
		cardDetails.setLast4Digits(cayanResponseModel.getLast4Digits());
		cardDetails.setAmountCharged(cayanResponseModel.getAmountCharged());
		cardDetails.setExpDate(cayanResponseModel.getExpDate());
		cardDetails.setCreditCardType((SiteoneintegrationConstants.cardTypeShortName.get(cayanResponseModel.getCreditCardType())));
		cardDetails.setToken(cayanResponseModel.getToken());
		cardDetails.setCreditCardZip(cayanResponseModel.getCreditCardZip());
		cardDetails.setTransactionStatus(cayanResponseModel.getTransactionStatus());
		cardDetails.setAid(cayanResponseModel.getAid());
		cardDetails.setApplicationLabel(cayanResponseModel.getCreditCardType());
		cardDetails.setPinStatement(cayanResponseModel.getPinStatement());
		cardDetails.setTransactionReferenceNumber(cayanResponseModel.getTransactionReferenceNumber());
		cardDetails.setDeclineCode(cayanResponseModel.getDeclineCode());
		paymentDetails.setCorrelationId(UUID.randomUUID().toString());
		paymentDetails.setExternalSystemId(cayanResponseModel.getExternalSystemId());
		paymentDetails.setOrderNumber(cayanResponseModel.getOrderNumber());
		paymentDetails.setPaymentInfo(cardDetails);
		return paymentDetails;

	}
	
	public SiteOneRestClient<?, SiteOneWsLinkToPayOrderResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<?, SiteOneWsLinkToPayOrderResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}
