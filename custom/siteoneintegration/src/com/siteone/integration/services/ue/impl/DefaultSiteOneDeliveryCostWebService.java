package com.siteone.integration.services.ue.impl;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.deliverycost.data.SiteOneWsDeliveryCostAPIResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneDeliveryCostWebService;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;
/**
 * @author vd02162
 * @comment SE-9630 Delivery Cost API
 */
public class DefaultSiteOneDeliveryCostWebService implements SiteOneDeliveryCostWebService {

    private static final Logger LOG = Logger.getLogger(DefaultSiteOneDeliveryCostWebService.class.getName());
    private SiteOneRestClient<?, SiteOneWsDeliveryCostAPIResponseData> siteOneRestClient;
    @Override
    public SiteOneWsDeliveryCostAPIResponseData getDeliveryCost( String storeId,
                                                                 String division,
                                                                 String orderType,
                                                                 boolean isHomeowner,
                                                                 boolean isGuestUser,
                                                                 boolean expediteDelivery,
                                                                 boolean needsLift) throws IOException, RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,SiteoneintegrationConstants.AUTHORIZATION_TYPE_BASIC+" "+Config.getString(SiteoneintegrationConstants.DELIVERY_COST_SERVICE_URL_KEY, null));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        SiteOneWsDeliveryCostAPIResponseData siteOneWsDeliveryCostAPIResponseData = getSiteOneRestClient().execute(buildDeliveryCostUrl(storeId,division,orderType,isHomeowner,isGuestUser,expediteDelivery, needsLift),
                HttpMethod.GET, null, SiteOneWsDeliveryCostAPIResponseData.class, UUID.randomUUID().toString(),SiteoneintegrationConstants.DELIVERY_COST_SERVICE_NAME,httpHeaders);

        int deliveryCostMaxRetryCount = Config.getInt(SiteoneintegrationConstants.DELIVERYCOST_API_MAXRETRY_COUNT, 1);
        int retryCount=0;
        while(siteOneWsDeliveryCostAPIResponseData==null && retryCount<deliveryCostMaxRetryCount){
            retryCount++;
            LOG.error("Retrying UE call for SiteOneWsDeliveryCostAPIResponseData = getSiteOneRestClient():" +retryCount+ "+retryCount");
            siteOneWsDeliveryCostAPIResponseData = getSiteOneRestClient().execute(buildDeliveryCostUrl(storeId,division,orderType,isHomeowner,isGuestUser,expediteDelivery,needsLift),
                    HttpMethod.GET, null, SiteOneWsDeliveryCostAPIResponseData.class, UUID.randomUUID().toString(),SiteoneintegrationConstants.DELIVERY_COST_SERVICE_NAME,httpHeaders);
        }
        return siteOneWsDeliveryCostAPIResponseData;
    }

    /**
     * @return the siteOneRestClient
     */
    public SiteOneRestClient<?, SiteOneWsDeliveryCostAPIResponseData> getSiteOneRestClient() {
        return siteOneRestClient;
    }


    /**
     * @param siteOneRestClient the siteOneRestClient to set
     */
    public void setSiteOneRestClient(
            SiteOneRestClient<?, SiteOneWsDeliveryCostAPIResponseData> siteOneRestClient) {
        this.siteOneRestClient = siteOneRestClient;
    }

    private String buildDeliveryCostUrl(String storeId,
                                        String division,
                                        String orderType,
                                        boolean isHomeowner,
                                        boolean isGuestUser,
                                        boolean expediteDelivery,
                                        boolean needsLift){
        String deliveryCostUrl = Config.getString(SiteoneintegrationConstants.DELIVERY_COST_SERVICE_URL_KEY, null);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(deliveryCostUrl)
                // Add query parameter
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_BRANCH, storeId)
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_DIVISION, division)
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_ORDER_TYPE,orderType )
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_ORDER_ORIGIN, SiteoneintegrationConstants.QUERY_VALUE_ORDER_ORIGIN)
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_LIFT, Boolean.TRUE.equals(needsLift)?SiteoneintegrationConstants.BOOLEAN_TRUE:SiteoneintegrationConstants.BOOLEAN_FALSE)
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_ISGUESTUSER, Boolean.TRUE.equals(isGuestUser)?SiteoneintegrationConstants.BOOLEAN_TRUE:SiteoneintegrationConstants.BOOLEAN_FALSE)
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_HOMEOWNERS, Boolean.TRUE.equals(isHomeowner)?SiteoneintegrationConstants.BOOLEAN_TRUE:SiteoneintegrationConstants.BOOLEAN_FALSE)
                .queryParam(SiteoneintegrationConstants.QUERY_PARAM_EXPEDITE, Boolean.TRUE.equals(expediteDelivery)?SiteoneintegrationConstants.BOOLEAN_TRUE:SiteoneintegrationConstants.BOOLEAN_FALSE);

        return builder.build().toUriString();
    }
}