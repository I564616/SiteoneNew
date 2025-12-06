package com.siteone.integration.services.ue.impl;

import jakarta.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.granule.json.JSONObject;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.integration.payment.data.CAGlobalPaymentDetails;
import com.siteone.integration.payment.data.CAVoidGlobalPaymentDetails;
import com.siteone.integration.payment.data.CAGlobalPaymentIndicators;
import com.siteone.integration.payment.data.CAVoidGlobalPaymentIndicators;
import com.siteone.integration.payment.data.CAGlobalPaymentTransaction;
import com.siteone.integration.payment.data.CAVoidGlobalPaymentTransaction;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.ewallet.data.SiteOneWsEwalletRequestData;
import com.siteone.integration.ewallet.data.SiteOneWsEwalletResponseData;
import com.siteone.integration.okta.response.OktaSessionResponse;
import com.siteone.integration.order.data.SiteOneQuoteDetailResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.scan.product.data.SiteOneCAGlobalPaymentRequest;
import com.siteone.integration.global.data.SiteOneCAGlobalPaymentResponse;
import com.siteone.integration.global.data.SiteOneCAGlobalPaymentVoidRequest;
import com.siteone.integration.services.ue.SiteOneEwalletWebService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.internal.model.impl.ItemModelCloneCreator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.util.Config;
import de.hybris.platform.b2b.model.B2BCustomerModel;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DefaultSiteOneEwalletWebService implements SiteOneEwalletWebService {
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneEwalletWebService.class);
	private SiteOneRestClient<SiteOneWsEwalletRequestData, SiteOneWsEwalletResponseData> siteOneRestClient;
	private Converter<SiteoneEwalletCreditCardModel, SiteOneWsEwalletRequestData> siteOneWsEwalletConverter;
	private SiteOneRestClient<SiteOneCAGlobalPaymentVoidRequest, SiteOneCAGlobalPaymentVoidRequest> siteOneRestClientForVoidPayment; 
	private ItemModelCloneCreator itemModelCloneCreator;
	@Resource
	private ModelService modelService;
	@Resource
	private I18NService i18nService;
	@Resource
	private TypeService typeService;
	@Resource
	private RestTemplate restTemplate;
	
	@Override
	public SiteOneWsEwalletResponseData createOrUpdOrDelEwallet(SiteoneEwalletCreditCardModel creditCardModel, 
			String operationType, boolean isNewBoomiEnv){
		
		SiteOneWsEwalletRequestData siteOneWsEwalletRequestData = getSiteOneWsEwalletConverter().convert(creditCardModel);
		siteOneWsEwalletRequestData.setOperationType(operationType);
		
		SiteOneWsEwalletResponseData siteOneWsEwalletResponseData = invokeUEManageEwallet(siteOneWsEwalletRequestData, isNewBoomiEnv);
		
		int cayanCallMaxRetryCount = Config.getInt("hybris.eWallet.UE.maxRetryCount", 1);
		int retryCount=0;
		while(siteOneWsEwalletResponseData==null && retryCount<cayanCallMaxRetryCount){
			retryCount++;
			LOG.error("Retrying UE call for "+operationType+"-"+siteOneWsEwalletRequestData.getVaultToken()+"...retry# "+retryCount);
			siteOneWsEwalletResponseData = invokeUEManageEwallet(siteOneWsEwalletRequestData, isNewBoomiEnv);
		}
		
		return siteOneWsEwalletResponseData;
	}
	
	@Override
	public SiteOneWsEwalletResponseData assignOrRevokeEwallet(SiteoneEwalletCreditCardModel creditCardModel, String operationType,
			List<B2BCustomerModel> customerModel, boolean isNewBoomiEnv){

		SiteOneWsEwalletRequestData siteOneWsEwalletRequestData = getSiteOneWsEwalletConverter().convert(creditCardModel);
		siteOneWsEwalletRequestData.setOperationType(operationType);
		List<String> listOfContactId = new ArrayList<>();
		customerModel.forEach(b2bCust -> {
			if(b2bCust!=null) 
			{
				listOfContactId.add(b2bCust.getGuid());
			}
		});
		siteOneWsEwalletRequestData.setListOfContactId(listOfContactId);
			
		SiteOneWsEwalletResponseData siteOneWsEwalletResponseData = invokeUEManageEwallet(siteOneWsEwalletRequestData, isNewBoomiEnv);
		
		int cayanCallMaxRetryCount = Config.getInt("hybris.eWallet.UE.maxRetryCount", 1);
		int retryCount=0;
		while(siteOneWsEwalletResponseData==null && retryCount<cayanCallMaxRetryCount){
			retryCount++;
			LOG.error("Retrying UE call for "+operationType+"-"+siteOneWsEwalletRequestData.getVaultToken()+"...retry# "+retryCount);
			siteOneWsEwalletResponseData = invokeUEManageEwallet(siteOneWsEwalletRequestData, isNewBoomiEnv);
		}
		
		return siteOneWsEwalletResponseData;
	}
	
	public SiteOneWsEwalletResponseData invokeUEManageEwallet(SiteOneWsEwalletRequestData siteOneWsEwalletRequestData, boolean isNewBoomiEnv){
		SiteOneWsEwalletResponseData siteOneWsEwalletResponseData = null;
		try {
				if(isNewBoomiEnv)
				{
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
					siteOneWsEwalletResponseData = getSiteOneRestClient().execute(Config.getString(SiteoneintegrationConstants.EWALLET_NEW_URL_KEY, null),
							HttpMethod.POST, siteOneWsEwalletRequestData, SiteOneWsEwalletResponseData.class,siteOneWsEwalletRequestData.getCorrelationId(),SiteoneintegrationConstants.EWALLET_SERVICE_NAME,httpHeaders);
				}
				else
				{
					siteOneWsEwalletResponseData = getSiteOneRestClient().execute(Config.getString(SiteoneintegrationConstants.EWALLET_URL_KEY, null),
						HttpMethod.POST, siteOneWsEwalletRequestData, SiteOneWsEwalletResponseData.class,siteOneWsEwalletRequestData.getCorrelationId(),SiteoneintegrationConstants.EWALLET_SERVICE_NAME,null);
				}
			} catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error("Not able to establish connection with cayan/UE API to ewallet - "+siteOneWsEwalletRequestData.getOperationType()+"-"+siteOneWsEwalletRequestData.getVaultToken(), resourceAccessException);
				return null;
			}
		
		return siteOneWsEwalletResponseData;
	}
	
	public Converter<SiteoneEwalletCreditCardModel, SiteOneWsEwalletRequestData> getSiteOneWsEwalletConverter() {
		return siteOneWsEwalletConverter;
	}

	public void setSiteOneWsEwalletConverter(
			Converter<SiteoneEwalletCreditCardModel, SiteOneWsEwalletRequestData> siteOneWsEwalletConverter) {
		this.siteOneWsEwalletConverter = siteOneWsEwalletConverter;
	}

	public SiteOneRestClient<SiteOneWsEwalletRequestData, SiteOneWsEwalletResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsEwalletRequestData, SiteOneWsEwalletResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}
	@Override
	public SiteOneCAGlobalPaymentResponse addGlobalPayment(String authToken,SiteOneCAGlobalPaymentRequest requestData) {
		final String baseUrl=Config.getString(SiteoneintegrationConstants.GLOBAL_BASE_URL, null);
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "AuthToken " + authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-gp-version", "2019-06-27");
        headers.set("x-gp-api-key",Config.getString(SiteoneintegrationConstants.GLOBAL_INTERNATIONL_API_KEY, null) );
        headers.set("x-gp-request-id", requestData.getPayment().getReference_id());

        HttpEntity<SiteOneCAGlobalPaymentRequest> request = new HttpEntity<>(requestData, headers);
        try {
        	 ResponseEntity<SiteOneCAGlobalPaymentResponse> response = getRestTemplate().postForEntity(baseUrl, request,
        			 SiteOneCAGlobalPaymentResponse.class);
        	 if(response.getBody()!=null)
        	 {
        	 LOG.error("Response Global Pay : "+gson.toJson(response.getBody()));
        	 }
            return response.getBody();
        } catch (RestClientException e) {
            // Assuming error response structure
            // You may need to create a proper error handling based on actual response
            // Here we simply print and return failure
            LOG.error("Capture Payment responded with an error: " + e.getMessage());
            try {
				updateVoidPayment(populateVoidPaymentData(requestData.getPayment().getAmount()), requestData.getPayment().getReference_id(), createAuthTokenV2());
			} catch (Exception ex) {
				LOG.error("Void Authotoken with an error: " + e.getMessage());
				 return null;
			}
            return null;
        }
	}
	@Override
	public void updateVoidPayment(SiteOneCAGlobalPaymentVoidRequest voidRequest, String referenceID,String authToken) 
	{
		LOG.error("Update Void Request " + voidRequest);
		String voidUrl = Config.getString(SiteoneintegrationConstants.GLOBAL_VOID_PAYMENT_URL, StringUtils.EMPTY);
		voidUrl = voidUrl.replace(SiteoneintegrationConstants.QUERY_PARAM_VOID_HEADER_ID, referenceID);
		LOG.error("Payment Void url  " + voidUrl);

		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "AuthToken " + authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-gp-version", "2019-06-27");
        headers.set("x-gp-api-key",Config.getString(SiteoneintegrationConstants.GLOBAL_INTERNATIONL_API_KEY, null) );
        headers.set("x-gp-request-id", referenceID);
        HttpEntity<SiteOneCAGlobalPaymentVoidRequest> request = new HttpEntity<>(voidRequest, headers);
        try {
        	getRestTemplate().put(voidUrl, request);
			/*
			 * getSiteOneRestClientForVoidPayment().execute(voidUrl, HttpMethod.PUT,
			 * voidRequest,
			 * SiteOneCAGlobalPaymentVoidRequest.class,UUID.randomUUID().toString(),
			 * SiteoneintegrationConstants.GLOBAL_PAYMENT_SERVICE_NAME,httpHeaders);
			 */LOG.error("Response data for Void payment ");
        }
        catch (RestClientException e) {
            LOG.error("Capture Void Payment responded with an error: " + e.getMessage());
        }
		}

	private SiteOneCAGlobalPaymentVoidRequest populateVoidPaymentData(final String amount)
	{
		final SiteOneCAGlobalPaymentVoidRequest requestData = new SiteOneCAGlobalPaymentVoidRequest();
		final CAVoidGlobalPaymentDetails paymentDetails = new CAVoidGlobalPaymentDetails();
		paymentDetails.setAmount(amount);
		requestData.setPayment(paymentDetails);
		final CAVoidGlobalPaymentTransaction transaction = new CAVoidGlobalPaymentTransaction();
		final CAVoidGlobalPaymentIndicators indicators = new CAVoidGlobalPaymentIndicators();
		indicators.setGenerate_receipt(true);
		transaction.setProcessing_indicators(indicators);
		requestData.setTransaction(transaction);
		return requestData;
	}
	
	public String createAuthTokenV2() throws Exception
	{
		// Create header

		final JSONObject jwtHeaderObj = new JSONObject();
		jwtHeaderObj.put("alg", "HS256").put("typ", "JWT");

		// Create payload
		final JSONObject jwtPayloadObj = new JSONObject();
		jwtPayloadObj.put("account_credential", Config.getString(SiteoneintegrationConstants.GLOBAL_ACCOUNT_CREDS, null));
		jwtPayloadObj.put("region", "CA");
		jwtPayloadObj.put("type", "AuthTokenV2");
		jwtPayloadObj.put("ts", System.currentTimeMillis());

		// Encode header and payload
		final String jwtPayloadATBase64 = Base64.getUrlEncoder()
				.encodeToString(jwtPayloadObj.toString().getBytes(StandardCharsets.UTF_8));
		// Base64 encoding of Header
		final String jwtHeaderATBase64 = Base64.getUrlEncoder()
				.encodeToString(jwtHeaderObj.toString().getBytes(StandardCharsets.UTF_8));
		// Concatenating encoded Header and Payload with "."
		final String jwtMessage = jwtHeaderATBase64 + "." + jwtPayloadATBase64;

		// Create Signature using HMAC-SH256 algorithm and ApiSecret as the secret
		final Mac sha256HMAC = Mac.getInstance("HmacSHA256");
		final SecretKeySpec secretKey = new SecretKeySpec(
				String.valueOf(Config.getString(SiteoneintegrationConstants.GLOBAL_API_SECRET_KEY, null)).getBytes(), "HmacSHA256");
		sha256HMAC.init(secretKey);
		final String hashSignature = Base64.getUrlEncoder()
				.encodeToString(sha256HMAC.doFinal(jwtMessage.getBytes(StandardCharsets.UTF_8)));

		/**
		 * Create the JWT AuthToken by concatenating Base64 URL encoded header, payload and signature
		 */

		final String token = jwtHeaderATBase64 + "." + jwtPayloadATBase64 + "." + hashSignature;


		return token;
	}
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public SiteOneRestClient<SiteOneCAGlobalPaymentVoidRequest, SiteOneCAGlobalPaymentVoidRequest> getSiteOneRestClientForVoidPayment() {
		return siteOneRestClientForVoidPayment;
	}

	public void setSiteOneRestClientForVoidPayment(
			SiteOneRestClient<SiteOneCAGlobalPaymentVoidRequest, SiteOneCAGlobalPaymentVoidRequest> siteOneRestClientForVoidPayment) {
		this.siteOneRestClientForVoidPayment = siteOneRestClientForVoidPayment;
	}


}
