package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.deliverycost.data.SiteOneWsDeliveryCostAPIResponseData;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

public interface SiteOneDeliveryCostWebService {

    SiteOneWsDeliveryCostAPIResponseData getDeliveryCost(String branch,
                                                         String division,
                                                         String orderType,
                                                         boolean isHomeowner,
                                                         boolean guestUser,
                                                         boolean expediteDelivery,
                                                         boolean needsLift) throws IOException, RestClientException;
}
