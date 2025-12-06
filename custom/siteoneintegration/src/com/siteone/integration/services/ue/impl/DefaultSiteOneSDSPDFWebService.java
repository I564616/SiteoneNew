package com.siteone.integration.services.ue.impl;


import java.util.*;

import com.google.gson.Gson;
import com.siteone.integration.rest.client.SiteOneRestClient;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.services.ue.SiteOneSDSPDFWebService;
import org.apache.commons.lang3.StringUtils;

import de.hybris.platform.util.Config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author VenkatB
 *
 */
public class DefaultSiteOneSDSPDFWebService implements SiteOneSDSPDFWebService {
    private static final Logger LOG = Logger.getLogger(DefaultSiteOneSDSPDFWebService.class);

    private SiteOneRestClient<ResponseEntity<String>, String> siteOneRestClient;

    @Override
    public byte[] getPDFByResourceId(String skuId, String resourceId) {
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,
                SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC + " "
                        + Config.getString(SiteoneintegrationConstants.UE_PDF_VERIFICATION_KEY, null));
        headers.setContentType(MediaType.APPLICATION_JSON);


        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);

            LOG.info("@@skuId-"+skuId);
            if(StringUtils.isNotEmpty(skuId)){

                        UriComponentsBuilder productDocumentUrl = UriComponentsBuilder.fromUriString(Config.getString(SiteoneintegrationConstants.UE_SDSPDF_URL,StringUtils.EMPTY))
                        .queryParam("skuID", skuId)
                        .queryParam("prodResourceID", resourceId);

                ResponseEntity<String> response = restTemplate.exchange(productDocumentUrl.buildAndExpand().toUri(), HttpMethod.GET, entity, String.class);

                if (response!=null && response.getBody()!= null) {
                    Gson gson = new Gson();
                    ProductDocumentResponse pdr = gson.fromJson(response.getBody(), ProductDocumentResponse.class);
                    if(pdr.getBinaryData()!=null && StringUtils.isNotEmpty(pdr.getBinaryData()))
                    {
                        return Base64.decodeBase64(pdr.getBinaryData());
                    }
                    else {
                        LOG.error("Unexpected response while connecting to sds/label/cutsheet pdf service, skuId -" +skuId+ "resourceId-"+resourceId);
                        return "Document not found.".getBytes();
                    }
                }
            }else{
                String tempSdsPdfUrl = Config.getString(SiteoneintegrationConstants.UE_SDSPDF_URL_OLD, StringUtils.EMPTY);
                String sdsPdfUrl = tempSdsPdfUrl.concat(resourceId);
                ResponseEntity<String> response = restTemplate.exchange(sdsPdfUrl, HttpMethod.GET, entity, String.class);

                if (response.getBody()!=null) {
                    return Base64.decodeBase64(response.getBody());
                }
                else {
                    return "Document not found.".getBytes();
                }
            }

        } catch (ResourceAccessException resourceAccessException) {
            LOG.error("Resource access exception occurred while connecting to sds/label/cutsheet pdf service, skuId - "+skuId+"resourceId-"+resourceId,resourceAccessException);
            throw resourceAccessException;
        }catch(RestClientException restClientException) {
            LOG.error("Rest client exception occurred while connecting to sds/label/cutsheet pdf service, skuId - "+skuId+"resourceId-"+resourceId,restClientException);
        }
        return null;
    }

}

