package com.siteone.integration.rest.client;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.errorhandler.okta.OktaErrorHandler;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

/**
 * @param <REQUEST>  - request data
 * @param <RESPONSE> - response data
 * @author 1230514
 * <p>
 * Rest client
 */
public class SiteOneOktaRestClient<REQUEST, RESPONSE> extends AbstractSiteOneRestClient<REQUEST, RESPONSE> {
    private static final Logger LOG = Logger.getLogger(SiteOneOktaRestClient.class);
    private final String X_RATE_LIMIT_REMAINING = "X-Rate-Limit-Remaining";

    protected void beforeRestCall() {
        getRestTemplate().setErrorHandler(new OktaErrorHandler());
    }

    protected void afterRestCall(ResponseEntity<RESPONSE> responseEntity) {
        if (responseEntity != null && responseEntity.getHeaders() != null &&
                responseEntity.getHeaders().get(X_RATE_LIMIT_REMAINING) != null && responseEntity.getHeaders().get(X_RATE_LIMIT_REMAINING).size() > 0) {
            if (Integer.valueOf(responseEntity.getHeaders().get(X_RATE_LIMIT_REMAINING).get(0)) < Config
                    .getInt(SiteoneintegrationConstants.OKTA_RATE_LIMIT_REMAINDER_THRESOLD_VALUE, 200)) {
                try {
                    LOG.error("The current thread will be delayed by "
                            + Config.getLong(SiteoneintegrationConstants.OKTA_SERVICE_CALL_DELAY_MS, 100)
                            + " ms, X-Rate-Limit-Remaining=" + responseEntity.getHeaders().get(X_RATE_LIMIT_REMAINING).get(0));
                    Thread.sleep(Config.getLong(SiteoneintegrationConstants.OKTA_SERVICE_CALL_DELAY_MS, 100));
                } catch (final InterruptedException ie) {
                    LOG.error("Delayed thread interrupted",ie);
                }

            }
        }
    }
}


