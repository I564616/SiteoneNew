package com.siteone.integration.services.ue.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.product.data.PurchasedProduct;
import com.siteone.integration.product.data.SiteOneWsPurchasedProductData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOnePurchasedProductWebService;

import de.hybris.platform.util.Config;


public class DefaultSiteOnePurchasedProductWebService implements
		SiteOnePurchasedProductWebService {
	

	private SiteOneRestClient<?, PurchasedProduct[] > siteOneRestClient;
	
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePurchasedProductWebService.class);
	
	public List<PurchasedProduct> getPurchasedProducts(String accountGuid, boolean isBillingAccount){
		
		 HttpHeaders httpHeaders = new HttpHeaders();
		 httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			
		 List<PurchasedProduct> products = new ArrayList<PurchasedProduct>();
	
		 try{
		 final PurchasedProduct[] purchasedProductList = getSiteOneRestClient().execute(buildPurchProdServiceUrl(accountGuid, isBillingAccount),
				HttpMethod.GET, null, PurchasedProduct[].class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PURCHASED_PRODUCT_SERVICE_NAME,httpHeaders);	
		
       if(purchasedProductList!=null && purchasedProductList.length>0)	{	
		products = new ArrayList<PurchasedProduct>(Arrays.asList(purchasedProductList));
       }
		 }catch(Exception exp){
			 LOG.error("Error while fetching purchased products for customer - "+accountGuid);
		 }
		return products;

	}
	
	
	private String buildPurchProdServiceUrl(String accountGuid, boolean isBillingAccount){
		String purchasedProductUrl = Config.getString(SiteoneintegrationConstants.PURCHASED_PRODUCT_SERVICE_URL_KEY, null);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(purchasedProductUrl)
				// Add query parameter
		        .queryParam(SiteoneintegrationConstants.QUERY_PARAM_CUSTTREENODEID, accountGuid)
		        .queryParam(SiteoneintegrationConstants.QUERY_PARAM_ISBILLINGNODE , isBillingAccount);
		
		return builder.build().toUriString();
	}


	public SiteOneRestClient<?, PurchasedProduct[]> getSiteOneRestClient() {
		return siteOneRestClient;
	}


	public void setSiteOneRestClient(
			SiteOneRestClient<?, PurchasedProduct[]> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}


	


	
	
	
}
