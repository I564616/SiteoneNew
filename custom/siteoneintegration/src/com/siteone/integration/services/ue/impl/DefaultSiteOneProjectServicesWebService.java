package com.siteone.integration.services.ue.impl;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.project.services.data.PsBiddingRequestData;
import com.siteone.integration.project.services.data.PsBiddingResponseData;
import com.siteone.integration.project.services.data.PsDashboardResponseData;
import com.siteone.integration.project.services.data.PsHiddenResponseData;
import com.siteone.integration.project.services.request.PSDashboardRequest;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneProjectServicesWebService;

import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

public class DefaultSiteOneProjectServicesWebService implements SiteOneProjectServicesWebService
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneProjectServicesWebService.class);
	
	private static final String SEARCH_URL = "?sessionToken=";
	
	private SiteOneRestClient<?, String> siteOneRestClient;
	private SiteOneRestClient<?, PsDashboardResponseData> siteOneRestClientForDashboard;
	private SiteOneRestClient<PsBiddingRequestData, PsBiddingResponseData> siteOneRestClientForBidding;
	private SiteOneRestClient<?, PsHiddenResponseData> siteOneRestClientForHiding;
	private SessionService sessionService;
	
	@Override
	public String getBearerToken(final boolean isNewBoomiEnv, final String sessionToken) 
	{
		HttpHeaders httpHeaders = createHttpHeaders(false, null);
		
	    String psTokenUrl = (isNewBoomiEnv)
	            ? Config.getString(SiteoneintegrationConstants.PS_BEARER_TOKEN_URL, StringUtils.EMPTY)
	            : Config.getString(SiteoneintegrationConstants.PS_BEARER_TOKEN_URL, StringUtils.EMPTY);

	    String url = psTokenUrl + SEARCH_URL + sessionToken;

	    try {
	    	String response = getSiteOneRestClient().execute(url,HttpMethod.GET,null,String.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PS_BEARER_TOKEN_SERVICE_NAME,httpHeaders);

	        if (response != null) {
	            LOG.error("Bearer token received successfully");
	            return response;
	        } else {
	            LOG.error("Failed to get bearer token. Status: {}" + response);
	        }
	    } catch (Exception e) {
	        LOG.error("Exception while fetching bearer token from PS API", e);
	    }

	    return null;
	}
	
	@Override
	public PsDashboardResponseData getDashboardInfo(final boolean isNewBoomiEnv, final String bToken, final PSDashboardRequest request) 
	{
		HttpHeaders httpHeaders = createHttpHeaders(true, bToken);
		
		String dashBoardUrl = (isNewBoomiEnv)
	            ? Config.getString(SiteoneintegrationConstants.PS_DASHBOARD_URL, StringUtils.EMPTY)
	            : Config.getString(SiteoneintegrationConstants.PS_DASHBOARD_URL, StringUtils.EMPTY);
		
		StringBuilder urlBuilder = new StringBuilder(dashBoardUrl);
	    boolean firstParam = true;
		
		try {
			
			for (Field field : PSDashboardRequest.class.getDeclaredFields()) {
	            field.setAccessible(true);
	            Object value = field.get(request);
	            if (value != null && !(value instanceof String && ((String)value).isEmpty())) {
	                urlBuilder.append(firstParam ? "?" : "&")
	                          .append(field.getName())
	                          .append("=")
	                          .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
	                firstParam = false;
	            }
	        }
			String url = urlBuilder.toString();
			
			PsDashboardResponseData response = getSiteOneRestClientForDashboard().execute(url,HttpMethod.GET,null,PsDashboardResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PS_DASHBOARD_SERVICE_NAME,httpHeaders);

	        if (response != null) {
	            LOG.error("Dashboard response received successfully" + response);
	            return response;
	        } else {
	            LOG.error("Failed to get dashboard response. Status: {}" + response);
	        }
	    }
		catch (IllegalAccessException e) {
	        throw new RuntimeException("Error accessing form fields", e);
	    } 
		catch (Exception e) {
	        LOG.error("Exception while calling dasboard api", e);
	    }

	    return null;
	}
	
	@Override
	public PsBiddingResponseData addBiddingInfo(PsBiddingRequestData request, String bToken) 
	{
		String addBidUrl = Config.getString(SiteoneintegrationConstants.PS_ADD_BID_URL, StringUtils.EMPTY);
		HttpHeaders httpHeaders = createHttpHeaders(true, bToken);
		
		final PsBiddingResponseData response = getSiteOneRestClientForBidding().execute(addBidUrl,
	               HttpMethod.POST, request, PsBiddingResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PS_ADD_SERVICE_NAME,httpHeaders);
		
		return response;
	}
	
	@Override
	public PsBiddingResponseData removeBiddingInfo(PsBiddingRequestData request, String bToken) 
	{
		String removeBidUrl = Config.getString(SiteoneintegrationConstants.PS_REMOVE_BID_URL, StringUtils.EMPTY);
		HttpHeaders httpHeaders = createHttpHeaders(true, bToken);
		
		final PsBiddingResponseData response = getSiteOneRestClientForBidding().execute(removeBidUrl,
	               HttpMethod.POST, request, PsBiddingResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PS_REMOVE_SERVICE_NAME,httpHeaders);
		
		return response;
	}
	
	@Override
	public PsHiddenResponseData updateHiddenInfo(String bToken, String projectId, Boolean isHidden) 
	{
		HttpHeaders httpHeaders = createHttpHeaders(true, bToken);
		String hiddenUrl = Config.getString(SiteoneintegrationConstants.PS_TOGGLE_HIDE_URL, StringUtils.EMPTY);
		hiddenUrl = hiddenUrl.replace(SiteoneintegrationConstants.PS_PROJECT_ID, projectId);
		hiddenUrl = hiddenUrl.replace(SiteoneintegrationConstants.PS_IS_HIDDEN, String.valueOf(isHidden));
		
		final PsHiddenResponseData response = getSiteOneRestClientForHiding().execute(hiddenUrl,
	               HttpMethod.POST, null, PsHiddenResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PS_HIDE_SERVICE_NAME,httpHeaders);
		
		return response;
	}
	
	@Override
	public PsHiddenResponseData updateFavoriteInfo(String bToken, String projectId, Boolean isFav) 
	{
		HttpHeaders httpHeaders = createHttpHeaders(true, bToken);
		String favUrl = Config.getString(SiteoneintegrationConstants.PS_TOGGLE_FAV_URL, StringUtils.EMPTY);
		favUrl = favUrl.replace(SiteoneintegrationConstants.PS_PROJECT_ID, projectId);
		favUrl = favUrl.replace(SiteoneintegrationConstants.PS_IS_FAVORITE, String.valueOf(isFav));
		favUrl = favUrl.replace(SiteoneintegrationConstants.PS_PREF_ID, "0");
		
		final PsHiddenResponseData response = getSiteOneRestClientForHiding().execute(favUrl,
	               HttpMethod.POST, null, PsHiddenResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.PS_FAV_SERVICE_NAME,httpHeaders);
		
		return response;
	}
	
	private HttpHeaders createHttpHeaders(boolean isAuthorizationRequired, String bToken) {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(SiteoneintegrationConstants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(SiteoneintegrationConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if (getSessionService().getAttribute("clientIp") != null) {

            final String clientIpAddr = getSessionService().getAttribute("clientIp");
            LOG.error("clientIpAddr: " + clientIpAddr);
            httpHeaders.set(SiteoneintegrationConstants.CLIENT_IP_ADDR, clientIpAddr);
        }
        if (getSessionService().getAttribute("userAgent") != null) {
            final String userAgent = getSessionService().getAttribute("userAgent");
            httpHeaders.set(SiteoneintegrationConstants.USER_AGENT, userAgent);
        }
        if (isAuthorizationRequired) {
            httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,
                    SiteoneintegrationConstants.AUTHORIZATION_TYPE_BEARER + " "
                            + bToken);
        }
        
        httpHeaders.set(SiteoneintegrationConstants.PROJECT_SERVICES_SERVICE_KEY_NAME, Config.getString(SiteoneintegrationConstants.PROJECT_SERVICES_SERVICE_KEY, null));
        
        return httpHeaders;
    }

	public SiteOneRestClient<?, String> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(SiteOneRestClient<?, String> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	/**
	 * @return the siteOneRestClientForDashboard
	 */
	public SiteOneRestClient<?, PsDashboardResponseData> getSiteOneRestClientForDashboard() {
		return siteOneRestClientForDashboard;
	}

	/**
	 * @param siteOneRestClientForDashboard the siteOneRestClientForDashboard to set
	 */
	public void setSiteOneRestClientForDashboard(
			SiteOneRestClient<?, PsDashboardResponseData> siteOneRestClientForDashboard) {
		this.siteOneRestClientForDashboard = siteOneRestClientForDashboard;
	}

	public SiteOneRestClient<PsBiddingRequestData, PsBiddingResponseData> getSiteOneRestClientForBidding() {
		return siteOneRestClientForBidding;
	}

	public void setSiteOneRestClientForBidding(SiteOneRestClient<PsBiddingRequestData, PsBiddingResponseData> siteOneRestClientForBidding) {
		this.siteOneRestClientForBidding = siteOneRestClientForBidding;
	}

	public SiteOneRestClient<?, PsHiddenResponseData> getSiteOneRestClientForHiding() {
		return siteOneRestClientForHiding;
	}

	public void setSiteOneRestClientForHiding(SiteOneRestClient<?, PsHiddenResponseData> siteOneRestClientForHiding) {
		this.siteOneRestClientForHiding = siteOneRestClientForHiding;
	}
	
}
