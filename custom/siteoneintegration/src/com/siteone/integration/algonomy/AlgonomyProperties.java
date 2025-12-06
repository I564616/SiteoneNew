package com.siteone.integration.algonomy;

/**
 * Holds Algonomy config, loaded via Hybris ConfigurationService.
 */
public class AlgonomyProperties {
    public static final String URL_KEY = "algonomy.URL";
    public static final String API_KEY_KEY = "algonomy.apiKey";
    public static final String API_CLIENT_KEY_KEY = "algonomy.apiClientKey";
    public static final String CONNECT_TIMEOUT_KEY = "rest.algonomy.connection.timeout";
    public static final String READ_TIMEOUT_KEY = "rest.algonomy.read.timeout";
    public static final String MAX_RETRY_KEY = "hybris.algonomy.connection.maxRetryCount";
    public static final String MAX_TOTAL_CONN_KEY = "algonomy.maxTotalConnections";
    public static final String MAX_PER_ROUTE_KEY = "algonomy.maxPerRoute";
    public static final String SAMPLE_PLACEMENTS_KEY = "algonomy.sample.placements";
    public static final String SAMPLE_USERID_KEY = "algonomy.sample.userid";
    public static final String SAMPLE_SESSION_KEY = "algonomy.sample.sessionID";
    public static final String SAMPLE_REGION_KEY = "algonomy.sample.regionID";

    private String url;
    private String apiKey;
    private String apiClientKey;
    private int connectTimeout;
    private int readTimeout;
    private int maxRetry;
    private int maxTotalConnections;
    private int maxPerRoute;
    private String samplePlacements;
    private String sampleUserId;
    private String sampleSessionId;
    private String sampleRegionId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiClientKey() {
        return apiClientKey;
    }

    public void setApiClientKey(String apiClientKey) {
        this.apiClientKey = apiClientKey;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public String getSamplePlacements() {
        return samplePlacements;
    }

    public void setSamplePlacements(String samplePlacements) {
        this.samplePlacements = samplePlacements;
    }

    public String getSampleUserId() {
        return sampleUserId;
    }

    public void setSampleUserId(String sampleUserId) {
        this.sampleUserId = sampleUserId;
    }

    public String getSampleSessionId() {
        return sampleSessionId;
    }

    public void setSampleSessionId(String sampleSessionId) {
        this.sampleSessionId = sampleSessionId;
    }

    public String getSampleRegionId() {
        return sampleRegionId;
    }

    public void setSampleRegionId(String sampleRegionId) {
        this.sampleRegionId = sampleRegionId;
    }
}
