package com.siteone.integration.services.briteVerify.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.siteone.integration.services.briteVerify.SiteOneBriteVerifyWebService;
import com.siteone.integration.services.invoice.SiteOneInvoiceWebService;
import com.siteone.integration.services.ue.SiteOneSDSPDFWebService;
import com.siteone.integration.validation.email.data.SiteOneBriteVerifyEmailRequestData;

import org.apache.commons.lang3.StringUtils;

import de.hybris.platform.util.Config;

import org.apache.tomcat.util.codec.binary.Base64;

import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.granule.json.JSONObject;
import com.granule.json.JSONException;
/**
 * @author VenkatB
 *
 */
public class DefaultSiteOneBriteVerifyWebService implements SiteOneBriteVerifyWebService {
    private static final Logger LOG = Logger.getLogger(DefaultSiteOneBriteVerifyWebService.class);
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,20}$", Pattern.CASE_INSENSITIVE);


    @Override
    public String validateEmailId(String email) {
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,
                SiteoneintegrationConstants.AUTHORIZATION_VALUE + ": "
                        + Config.getString(SiteoneintegrationConstants.BRITEVERIFY_API_VERIFICATION_KEY, null));
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
        	SiteOneBriteVerifyEmailRequestData request = new SiteOneBriteVerifyEmailRequestData();
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (!(matcher.matches()))

		      {
		        throw new IllegalArgumentException("Invalid email Id");
		      } 
            request.setEmail(email);
            HttpEntity<SiteOneBriteVerifyEmailRequestData> entity = new HttpEntity<>(request,headers);
            String briteVerifyurl = Config.getString(SiteoneintegrationConstants.BRITEVERIFY_API_VERIFICATION_URL,null);
            ResponseEntity<String> response = restTemplate.exchange(briteVerifyurl, HttpMethod.POST, entity, String.class,headers);

            if (response!=null && response.getStatusCode().equals(HttpStatus.OK)) {

                String bodystr = response.getBody();
                String briteVerifyResponse="";
                try {
                    JSONObject jsonObj = new JSONObject(bodystr);
                    briteVerifyResponse = jsonObj.getJSONObject("email").getString("status");
                }catch (final JSONException e)
                {
                    LOG.error(e.getMessage(), e);
                }
               return briteVerifyResponse;
            }
            else {
                return "Invalid response from BriteVerify";
            }
        } catch (ResourceAccessException resourceAccessException) {
            LOG.error("Resorce access exception occured while connecting to BriteVerify API service",resourceAccessException);
            throw resourceAccessException;
        }catch(RestClientException restClientException) {
            LOG.error("Rest client exception occured while connecting to   BriteVerify API service",restClientException);
            throw restClientException;
        }

    }
}
