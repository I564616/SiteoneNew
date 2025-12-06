package com.siteone.integration.rest.client;

import org.springframework.http.ResponseEntity;


/**
 * @param <REQUEST>  - request data
 * @param <RESPONSE> - response data
 * @author 1230514
 * <p>
 * Rest clientsiteo
 */
public class SiteOneRestClient<REQUEST, RESPONSE> extends AbstractSiteOneRestClient<REQUEST, RESPONSE> {
    protected void beforeRestCall() {
    }

    protected void afterRestCall(ResponseEntity<RESPONSE> responseEntity) {
    }
}


