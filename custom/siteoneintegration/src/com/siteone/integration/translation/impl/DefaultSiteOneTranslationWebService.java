package com.siteone.integration.translation.impl;

import com.siteone.integration.translation.SiteOneTranslationWebService;
import com.siteone.integration.translation.model.GoogleTranslateRequest;
import com.siteone.integration.translation.model.GoogleTranslateResponse;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;


import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import de.hybris.platform.util.Config;
import org.springframework.web.client.ResourceAccessException;

public class DefaultSiteOneTranslationWebService implements SiteOneTranslationWebService {
	 private static final Logger LOG = Logger.getLogger(DefaultSiteOneTranslationWebService.class);
	    
	    private SiteOneRestClient<GoogleTranslateRequest, GoogleTranslateResponse> siteOneRestClient;
	    
	    @Override
	    public GoogleTranslateResponse translateTextBatch(GoogleTranslateRequest translateRequest) {
	        
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.set(SiteoneintegrationConstants.HTTP_HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	        
	        GoogleTranslateResponse translateResponse = null;
	        
	        try {
	            String apiKey = Config.getString(SiteoneintegrationConstants.GOOGLE_TRANSLATE_API_KEY, null);
	            String serviceUrl = Config.getString(SiteoneintegrationConstants.GOOGLE_TRANSLATE_SERVICE_URL, 
	                "https://translation.googleapis.com/language/translate/v2") + "?key=" + apiKey;
	            
	            translateResponse = getSiteOneRestClient().execute(
	                serviceUrl,
	                HttpMethod.POST, 
	                translateRequest, 
	                GoogleTranslateResponse.class, 
	                UUID.randomUUID().toString(),
	                SiteoneintegrationConstants.GOOGLE_TRANSLATE_SERVICE_NAME, 
	                httpHeaders);
	                
	        } catch (final ResourceAccessException resourceAccessException) {
	            LOG.error("Not able to establish connection with Google Translate API for text: " + 
	                translateRequest.getQ(), resourceAccessException);
	        }
	        
	        return translateResponse;
	    }
	    
	    public SiteOneRestClient<GoogleTranslateRequest, GoogleTranslateResponse> getSiteOneRestClient() {
	        return siteOneRestClient;
	    }
	    
	    public void setSiteOneRestClient(
	            SiteOneRestClient<GoogleTranslateRequest, GoogleTranslateResponse> siteOneRestClient) {
	        this.siteOneRestClient = siteOneRestClient;
	    }
}
