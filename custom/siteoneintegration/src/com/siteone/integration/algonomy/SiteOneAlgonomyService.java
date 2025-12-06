package com.siteone.integration.algonomy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteone.integration.algonomy.data.AlgonomyApiResponse;
import com.siteone.integration.algonomy.data.AlgonomyRequestData;
import com.siteone.integration.algonomy.data.AlgonomyResponseData;
import com.siteone.integration.algonomy.data.Placement;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Service("siteOneAlgonomyService")
public class SiteOneAlgonomyService {
    private static final Logger LOG = Logger.getLogger(SiteOneAlgonomyService.class);

    private static final String URL = "algonomy.URL";
    private static final String TIMEOUT = "algonomy.timeout";

    private static final String VALUE_API_KEY = "algonomy.apiKey";
    private static final String VALUE_API_CLIENT_KEY = "algonomy.apiClientKey";

    private static final String KEY_API_KEY = "apiKey=";
    private static final String KEY_API_CLIENT_KEY = "apiClientKey=";
    private static final String KEY_SESSION_ID = "sessionId=";
    private static final String KEY_USER_ID = "userId=";
    private static final String KEY_PLACEMENTS = "placements=";

    private static final String KEY_PRODUCT_ID = "productId=";
    private static final String KEY_CATEGORY_ID = "categoryId=";
    private static final String KEY_ATCID_ID = "atcid=";
    private static final String KEY_REGION_ID = "rid=";
    private static final String KEY_BI_ID = "bi=";

    private static final String KEY_ORDER_ID = "o=";
    private static final String KEY_PRODUCT_PRICES = "pp=";
    private static final String KEY_PRODUCT_PRICES_CENTS = "ppc=";
    private static final String KEY_QUANTITIES = "q=";

    private static final String KEY_SEARCH_TERM = "searchTerm=";

    private static final String KEY_TIMESTAMP = "ts=";

    private static final String PARAM_SIGN = "&";

    private static final String SAMPLE_PLACEMENTS = "algonomy.sample.placements";
    private static final String SAMPLE_USERID = "algonomy.sample.userid";
    private static final String SAMPLE_SESSION_ID = "algonomy.sample.sessionID";
    private static final String SAMPLE_REGION_ID = "algonomy.sample.regionID";

    @Resource(name = "algonomyRestTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "algonomyObjectMapper")
    private ObjectMapper objectMapper;

    @Resource(name = "siteOneAlgonomyAuditLogService")
    private SiteOneAlgonomyAuditLogService auditLogService;

    public Map<String, List<AlgonomyResponseData>> getProductRecommendations(AlgonomyRequestData req) {
        StringBuilder reqJSONsb = new StringBuilder();
        //convert the request to json
        try {
            reqJSONsb.append(objectMapper.writeValueAsString(req));
        } catch (Exception e) {
            LOG.error("error converting request data to JSON");
        }

        String correlationId = UUID.randomUUID().toString();
        String url = buildUrl(req);
        ResponseEntity<String> resp = null;

        try {
            resp = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (RestClientException e) {
            LOG.error("Algonomy call failed: " + url, e);
            throw e;
        } finally {
            LOG.info("********* Algonomy correlationId: " + correlationId);
            LOG.info("********* Algonomy url: " + url);
            auditLogService.saveAuditLog(SiteoneintegrationConstants.ALGONOMY_SERVICE_NAME, Config.getString(URL,""),
                    reqJSONsb.toString(), resp.toString(), correlationId);
        }

        if (!resp.getStatusCode().is2xxSuccessful()) {
            LOG.error("Algonomy returned " + resp.getStatusCode());
            throw new RestClientException("Bad status " + resp.getStatusCode());
        }

        try {
            AlgonomyApiResponse apiResponse = objectMapper.readValue(resp.getBody(), AlgonomyApiResponse.class);
            return mapByPlacement(apiResponse);
        } catch (IOException e) {
            LOG.error("JSON parse error", e);
            throw new RuntimeException(e);
        }
    }

    private String buildUrl(AlgonomyRequestData algonomyRequestData) {
        StringBuilder algonomyURL = new StringBuilder();
        algonomyURL.append(Config.getString(URL,""));
        algonomyURL.append(KEY_API_KEY);
        algonomyURL.append(Config.getString(VALUE_API_KEY,""));
        algonomyURL.append(PARAM_SIGN);
        algonomyURL.append(KEY_API_CLIENT_KEY);
        algonomyURL.append(Config.getString(VALUE_API_CLIENT_KEY,""));

        //set placements
        algonomyURL.append(PARAM_SIGN);
        algonomyURL.append(KEY_PLACEMENTS);
        if (StringUtils.isNotEmpty(algonomyRequestData.getPlacements())){
            algonomyURL.append(algonomyRequestData.getPlacements());
        }else{
            algonomyURL.append(Config.getString(SAMPLE_PLACEMENTS,""));
        }

        //set session id
        algonomyURL.append(PARAM_SIGN);
        algonomyURL.append(KEY_SESSION_ID);
        if (StringUtils.isNotEmpty(algonomyRequestData.getSessionId())){
            algonomyURL.append(algonomyRequestData.getSessionId());
        }else {
            algonomyURL.append(Config.getString(SAMPLE_SESSION_ID,""));
        }

        //set user id
        algonomyURL.append(PARAM_SIGN);
        algonomyURL.append(KEY_USER_ID);
        if (StringUtils.isNotEmpty(algonomyRequestData.getUserId())){
            algonomyURL.append(algonomyRequestData.getUserId());
        }else {
            algonomyURL.append(Config.getString(SAMPLE_USERID,""));
        }

        //set user id
        algonomyURL.append(PARAM_SIGN);
        algonomyURL.append(KEY_REGION_ID);
        if (StringUtils.isNotEmpty(algonomyRequestData.getRegionId())){
            algonomyURL.append(algonomyRequestData.getRegionId());
        }else {
            algonomyURL.append(Config.getString(SAMPLE_REGION_ID,""));
        }

        //set product id
        if (StringUtils.isNotEmpty(algonomyRequestData.getProductId())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_PRODUCT_ID);
            algonomyURL.append(algonomyRequestData.getProductId());
        }

        //set bi id
        if (StringUtils.isNotEmpty(algonomyRequestData.getBiId())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_BI_ID);
            algonomyURL.append(algonomyRequestData.getBiId());
        }

        //set category id
        if (StringUtils.isNotEmpty(algonomyRequestData.getCategoryId())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_CATEGORY_ID);
            algonomyURL.append(algonomyRequestData.getCategoryId());
        }

        //set atcid id
        if (StringUtils.isNotEmpty(algonomyRequestData.getAtcid())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_ATCID_ID);
            algonomyURL.append(algonomyRequestData.getAtcid());
        }

        //Set order complete data
        if (StringUtils.isNotEmpty(algonomyRequestData.getOrderId())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_ORDER_ID);
            algonomyURL.append(algonomyRequestData.getOrderId());
        }

        if (StringUtils.isNotEmpty(algonomyRequestData.getProductPrices())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_PRODUCT_PRICES);
            algonomyURL.append(algonomyRequestData.getProductPrices());
        }

        if (StringUtils.isNotEmpty(algonomyRequestData.getProductPricesCents())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_PRODUCT_PRICES_CENTS);
            algonomyURL.append(algonomyRequestData.getProductPricesCents());
        }

        if (StringUtils.isNotEmpty(algonomyRequestData.getQuantities())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_QUANTITIES);
            algonomyURL.append(algonomyRequestData.getQuantities());
        }

        if (StringUtils.isNotEmpty(algonomyRequestData.getSearchTerm())){
            algonomyURL.append(PARAM_SIGN);
            algonomyURL.append(KEY_SEARCH_TERM);
            algonomyURL.append(algonomyRequestData.getSearchTerm());
        }

        long timestamp = Instant.now().toEpochMilli();
        algonomyURL.append(PARAM_SIGN);
        algonomyURL.append(KEY_TIMESTAMP);
        algonomyURL.append(timestamp);
        return algonomyURL.toString();
    }

    private Map<String, List<AlgonomyResponseData>> mapByPlacement(final AlgonomyApiResponse apiResponse) {
        final Map<String, List<AlgonomyResponseData>> byPlacement = new LinkedHashMap<>();

        if (apiResponse == null) {
            return byPlacement;
        }

        final String sessionId = apiResponse.getRcs();
        final List<Placement> placements = apiResponse.getPlacements();
        if (placements == null || placements.isEmpty()) {
            return byPlacement;
        }

        for (final Placement p : placements) {
            if (p == null) {
                continue;
            }

            // Build a safe key: take substring after '.', else use the original; fall back to "default"
            final String placementName = p.getPlacement();
            String key = null;
            if (StringUtils.isNotBlank(placementName)) {
                final String afterDot = StringUtils.substringAfter(placementName, ".");
                key = StringUtils.isNotBlank(afterDot) ? afterDot : placementName;
            }
            if (StringUtils.isBlank(key)) {
                key = "default";
            }

            final String strategyMessage = p.getStrategyMessage();
            final List<AlgonomyResponseData> recommended = p.getRecommendedProducts();

            // Ensure the map always has a list to add to
            final List<AlgonomyResponseData> bucket = byPlacement.computeIfAbsent(key, k -> new ArrayList<>());

            if (recommended == null || recommended.isEmpty()) {
                continue; // nothing to add for this placement
            }

            for (final AlgonomyResponseData d : recommended) {
                if (d == null) {
                    continue;
                }
                // Set fields only when non-null to avoid inadvertent null overwrites (optional choice)
                if (sessionId != null) {
                    d.setSessionID(sessionId);
                }
                if (strategyMessage != null) {
                    d.setRecommendationTitle(strategyMessage);
                }
                bucket.add(d);
            }
        }

        return byPlacement;
    }

}