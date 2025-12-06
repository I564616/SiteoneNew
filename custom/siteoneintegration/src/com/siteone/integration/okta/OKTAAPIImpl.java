package com.siteone.integration.okta;

import com.google.gson.Gson;
import com.siteone.core.enums.UpdateEmailStatusEnum;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.exception.okta.OktaInvalidPasswordException;
import com.siteone.integration.exception.okta.OktaInvalidTokenException;
import com.siteone.integration.exception.okta.OktaPasswordMismatchException;
import com.siteone.integration.exception.okta.OktaRecentlyUsedPasswordException;
import com.siteone.integration.exception.okta.OktaUnknownUserException;
import com.siteone.integration.okta.data.SiteOneOktaSessionData;
import com.siteone.integration.okta.request.LoginRequest;
import com.siteone.integration.okta.request.OktaSessionIdRequest;
import com.siteone.integration.okta.request.OktaSessionRequest;
import com.siteone.integration.okta.response.LoginResponse;
import com.siteone.integration.okta.response.OktaSessionCloseResponse;
import com.siteone.integration.okta.response.OktaSessionResponse;
import com.siteone.integration.rest.client.SiteOneOktaRestClient;
import com.siteone.integration.services.okta.data.*;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.site.BaseSiteService;


import java.util.*;

import jakarta.annotation.Resource;


/**
 * Created by arun on 24/9/17.
 */
public class OKTAAPIImpl implements OKTAAPI {

    private static final Logger log = Logger.getLogger(OKTAAPIImpl.class);
    private ClientDetailsService clientDetailsService;
    private OAuth2RequestFactory smarteditOAuth2RequestFactory;
    private static final String AUTH_ENDPOINT = "/api/v1/authn";
    private static final String SESSION_ENDPOINT = "/api/v1/sessions?additionalFields=cookieToken";
    private static final String USER_ENDPOINT = "/api/v1/users/";
    private static final String GROUP_ENDPOINT = "/api/v1/groups/";
    private static final String ACTIVE_STATUS = "ACTIVE";
    private static final String SEARCH_URL = "?q=";

    private SiteOneOktaRestClient<SiteoneOktaRequestData, SiteoneOktaResponseData> siteOneOktaRestClient;

    private SiteOneOktaRestClient<OktaCreateOrUpdateUserRequestData, OktaCreateOrUpdateUserResponseData> siteOneCreateOrUpdateRestClient;

    private SiteOneOktaRestClient<?, OktaCreateOrUpdateUserResponseData> siteOneGetUserRestClient;

	private SiteOneOktaRestClient<OktaChangePasswordRequestData, OktaChangePasswordResponseData> siteOneChangePasswordRestClient;

	private SiteOneOktaRestClient<OktaSetPasswordRequestData, OktaSetPasswordResponseData> siteOneSetPasswordRestClient;

	private SiteOneOktaRestClient<OktaSessionIdRequest, OktaUserResponseData> siteOneResfreshSessionRestClient;

	private SiteOneOktaRestClient<?, OktaUserActivationResponseData> siteOneActivateUserRestClient;
	
	private SiteOneOktaRestClient<?, OktaUnlockUserResponseData> siteOneUnlockUserRestClient;
	
	private SiteOneOktaRestClient<OktaForgotPwdRequestData, OktaForgotPwdResponseData> siteOneForgotPasswordRestClient;

	private SiteOneOktaRestClient<OktaVerifyRecoveryTokenRequestData, OktaVerifyRecoveryTokenResponseData> siteOneRecoveryTokenRestClient;

	private SiteOneOktaRestClient<OktaResetPasswordRequestData, OktaResetPasswordResponseData> siteOneResetPasswordRestClient;

	private Converter<B2BCustomerModel, OktaCreateOrUpdateUserRequestData> siteOneWsOktaUserConverter;

	private SiteOneOktaRestClient<?, OktaConfigAppDetailsResponseData[]> siteOneOktaConfigAppDetailsRestClient;
	
	private SiteOneOktaRestClient<?, OktaConfigUserAppDetailResponseData[]> siteOneOktaConfigUserAppDetailsRestClient;

    private SessionService sessionService;
    private RestTemplate restTemplate;
    
    @Resource(name = "baseSiteService")
 	private BaseSiteService baseSiteService;

    public SessionService getSessionService() {
        return sessionService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public SiteOneOktaSessionData doAuth(String username, String password) {
        setProxyIfRequired();
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        SiteOneOktaSessionData data = new SiteOneOktaSessionData();
        String resJson = "";
        try {
            HttpHeaders httpHeaders = createHttpHeaders(true);
            HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request, httpHeaders);
            resJson = getRestTemplate().postForObject(getFullUrl(AUTH_ENDPOINT), requestEntity, String.class);
            // TODO will be removed
            log.info("Response json from OKTA " + resJson);
            debugMsg("Response json from OKTA " + resJson);
        } catch (RestClientException e) {
            log.error("Exception while posting to OKTA for user - " + username, e);
            if (e instanceof HttpClientErrorException) {
                HttpClientErrorException ex = (HttpClientErrorException) e;
                data.setHttpStatusCode(ex.getStatusCode().toString());
                data.setMessage(ex.getStatusText());
            }

            if (e instanceof ResourceAccessException) {
                ResourceAccessException ex = (ResourceAccessException) e;
                data.setHttpStatusCode("404");
                data.setMessage(ex.getMessage());
            }
            // TODO chk for other status codes (403)
            data.setIsSuccess(Boolean.FALSE);
            return data;
        } finally {

            // Clear proxy after okta authentication if it's enabled
            clearProxy();
        }
        if (!"".equals(resJson)) {
            Gson gson = new Gson();
            String groupID = Config.getString(SiteoneintegrationConstants.OKTA_BILLTRUST_GROUPID, StringUtils.EMPTY);
            LoginResponse loginRes = gson.fromJson(resJson, LoginResponse.class);
            debugMsg("In doAuth " + loginRes.getStatus());
            if ("SUCCESS".equalsIgnoreCase(loginRes.getStatus())) {
                debugMsg("AUTH SUCCESS");
                data.setIsSuccess(Boolean.TRUE);
                data.setHttpStatusCode("200");
                String sesToken = loginRes.getSessionToken();
                String loginId = loginRes.get_embedded().getUser().getProfile().getLogin();
                final String oktaId = loginRes.get_embedded().getUser().getId();
                data.setExpiresAt(loginRes.getExpiresAt());
                data.setMessage("SUCCESS");
                data.setUid(loginId);
                OktaSessionResponse sessionRes = establishSession(sesToken);
                data.setSessionToken(sessionRes.getCookieToken());
                log.error("Cookie token is " + sessionRes.getCookieToken());
                log.error("OktaAPI session token is " + data.getSessionToken());
                data.setOktaId(oktaId);

                if (StringUtils.isNotEmpty(oktaId)) {
                    data = getOktaConfigAppDetails(loginId, data);
                    data = getOktaConfigUserAppDetails(loginId, data);
                    

                    log.info(data);
                }

                //establishSession(sesToken);
            } else {
                debugMsg("Request succeeded, but auth failed ");
                data.setHttpStatusCode("403");
                data.setMessage(loginRes.getStatus());
                data.setIsSuccess(Boolean.FALSE);
            }
        }
        debugObj(data);
        return data;

    }


    @SuppressWarnings("finally")
    public SiteOneOktaSessionData getOktaConfigAppDetails(String userId, SiteOneOktaSessionData data) throws OktaUnknownUserException {
        setProxyIfRequired();
        boolean isSiteoneAppAssigned = false;
        boolean isSiteoneCAAppAssigned = false;
        boolean btAppEnabled = false;
        HttpHeaders httpHeaders = createHttpHeaders(true);
        String endPoint = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY) + Config.getString(SiteoneintegrationConstants.OKTA_AUTH_ENDPOINT_USERS, StringUtils.EMPTY) + userId + Config.getString(SiteoneintegrationConstants.OKTA_APP_LINKS, StringUtils.EMPTY);
        log.info(endPoint);
        try {
            final OktaConfigAppDetailsResponseData[] oktaConfigAppDetailsResponseData = getSiteOneOktaConfigAppDetailsRestClient().execute(endPoint,
                    HttpMethod.GET, null, OktaConfigAppDetailsResponseData[].class, UUID.randomUUID().toString(),
                    SiteoneintegrationConstants.OKTA_CONFIG_APP_DETAILS_SERVICE_NAME, httpHeaders);
            if (oktaConfigAppDetailsResponseData != null && oktaConfigAppDetailsResponseData.length > 0) {
                ArrayList<OktaConfigAppDetailsResponseData> oktaConfigAppDetailsList = new ArrayList<OktaConfigAppDetailsResponseData>(Arrays.asList(oktaConfigAppDetailsResponseData));
                log.info("********** OktaConfigAppDetailsResponseData[]: " + oktaConfigAppDetailsResponseData);
                log.info("********** OktaConfigAppDetailsResponseData[]: " + oktaConfigAppDetailsResponseData.length);

                final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
                if (basesite.getUid().equalsIgnoreCase("siteone-us")) {
                    isSiteoneAppAssigned = oktaConfigAppDetailsList.stream().anyMatch(siteoneApp -> siteoneApp.getAppName().equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.OKTA_SITEONE_APPNAME, StringUtils.EMPTY)));
                }
                else {
                    isSiteoneCAAppAssigned = oktaConfigAppDetailsList.stream().anyMatch(siteoneApp -> siteoneApp.getAppName().equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.OKTA_SITEONE_CA_APPNAME, StringUtils.EMPTY)));

                }
                
                if (isSiteoneAppAssigned || isSiteoneCAAppAssigned) {
                    btAppEnabled = oktaConfigAppDetailsList.stream().anyMatch(btApp -> btApp.getAppName().equalsIgnoreCase(Config.getString(SiteoneintegrationConstants.OKTA_BILLTRUST_APPNAME, StringUtils.EMPTY)));
                    log.info(btAppEnabled);
                } else {
                    data.setIsSuccess(false);
                }
                log.info(isSiteoneAppAssigned);
                log.info(isSiteoneCAAppAssigned);
            } else {
                data.setIsSuccess(false);
            }
            data.setIsBTApp(btAppEnabled);
        } catch (OktaUnknownUserException e) {
            data.setIsSuccess(false);
        } finally {
            clearProxy();
            return data;
        }
    }
    
    @SuppressWarnings("finally")
    public SiteOneOktaSessionData getOktaConfigUserAppDetails(String userId, SiteOneOktaSessionData data) throws OktaUnknownUserException {
        setProxyIfRequired();
        boolean nxtLevelAppEnabled = false;
        boolean projectServicesAppEnabled = false;
        HttpHeaders httpHeaders = createHttpHeaders(true);
        try {
        String endPoint = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY) + Config.getString(SiteoneintegrationConstants.OKTA_AUTH_ENDPOINT_APPS, StringUtils.EMPTY) + Config.getString(SiteoneintegrationConstants.OKTA_PROJECT_SERVICES_CLIENTID, StringUtils.EMPTY) + Config.getString(SiteoneintegrationConstants.OKTA_APP_USERS, StringUtils.EMPTY) + SEARCH_URL + userId;
        log.error("Calling Okta App User API: " + endPoint);
            final OktaConfigUserAppDetailResponseData[] response = getSiteOneOktaConfigUserAppDetailsRestClient()
                .execute(endPoint, HttpMethod.GET, null, OktaConfigUserAppDetailResponseData[].class,
                    UUID.randomUUID().toString(), SiteoneintegrationConstants.OKTA_CONFIG_USER_APP_DETAILS_SERVICE_NAME, httpHeaders);

            if (response != null && response.length > 0) {
                List<OktaConfigUserAppDetailResponseData> appDetailsList = Arrays.asList(response);

                log.error("********** OktaConfigUserAppDetailsResponseData[]: " + response);
                log.error("********** OktaConfigUserAppDetailsResponseData[] length: {}" + response.length);

                projectServicesAppEnabled = appDetailsList.stream().anyMatch(
                    psApp -> psApp.getProfile().getEmail().equalsIgnoreCase(userId) &&
                             psApp.getStatus().equalsIgnoreCase(ACTIVE_STATUS)
                );
                data.setIsProjectServicesApp(projectServicesAppEnabled);
            } else {
                data.setIsProjectServicesApp(false);
            }
            
            
            String endPointNextLevel = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY) +
            		Config.getString(SiteoneintegrationConstants.OKTA_AUTH_ENDPOINT_APPS, StringUtils.EMPTY) +
            		Config.getString(SiteoneintegrationConstants.OKTA_NXTLEVEL_GROUPID, StringUtils.EMPTY) + 
            		Config.getString(SiteoneintegrationConstants.OKTA_APP_USERS, StringUtils.EMPTY) + SEARCH_URL + userId;
            log.error("Calling Okta App User API for next level: " + endPointNextLevel);
                final OktaConfigUserAppDetailResponseData[] responseNextLevel = getSiteOneOktaConfigUserAppDetailsRestClient()
                    .execute(endPointNextLevel, HttpMethod.GET, null, OktaConfigUserAppDetailResponseData[].class,
                        UUID.randomUUID().toString(), SiteoneintegrationConstants.OKTA_CONFIG_USER_APP_DETAILS_SERVICE_NAME, httpHeaders);

                if (responseNextLevel != null && responseNextLevel.length > 0) {
                    List<OktaConfigUserAppDetailResponseData> appDetailList = Arrays.asList(responseNextLevel);

                    log.error("**********Next level OktaConfigUserAppDetailsResponseData[]: " + responseNextLevel);
                    log.error("********** Next level OktaConfigUserAppDetailsResponseData[] length: {}" + responseNextLevel.length);

                    nxtLevelAppEnabled = appDetailList.stream().anyMatch(
                        nlApp -> nlApp.getCredentials().getUserName().equalsIgnoreCase(userId) &&
                        nlApp.getStatus().equalsIgnoreCase(ACTIVE_STATUS)
                    );
                    data.setIsNextLevelApp(nxtLevelAppEnabled);
                } else {
                    data.setIsNextLevelApp(false);
                }
                log.error("nxtLevelAppEnabled flag value is "+ nxtLevelAppEnabled);

        } catch (OktaUnknownUserException e) {
        	data.setIsProjectServicesApp(false);
        	data.setIsNextLevelApp(false);
        }
        catch (Exception e) {
        	log.error("The PS status is" + data.getIsProjectServicesApp() + " and the Main exception is " + e);
        }finally {
            clearProxy();
            return data;
        }
    }

    @Override
    public OktaSessionResponse establishSession(String sessionToken) {
        log.info("establishSession(),sessionToken==" + sessionToken);
        setProxyIfRequired();
        OktaSessionRequest sessionObj = new OktaSessionRequest();
        sessionObj.setSessionToken(sessionToken);
        debugMsg("In establishSession " + sessionToken);
        HttpEntity<OktaSessionRequest> request = new HttpEntity<>(sessionObj, createHttpHeaders(true));
        ResponseEntity<OktaSessionResponse> res = getRestTemplate().postForEntity(getFullUrl(SESSION_ENDPOINT), request,
                OktaSessionResponse.class);
        debugMsg("Res obj in establish session " + res);
        clearProxy();
        // TODO createa data obj and populate it .
        return res.getBody();// .getLogin();
    }

    @Override
    public OktaSessionResponse getSessionDetails(String sessionId) {
        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("sessionId", sessionId);

        ResponseEntity<OktaSessionResponse> sessionRes = getRestTemplate().getForEntity(getFullUrl(SESSION_ENDPOINT), OktaSessionResponse.class, urlVariables);

        return sessionRes.getBody();
    }

    @Override
    public OktaSessionResponse refreshSession(String sessionId) {
        log.info("refreshSession(),sessionId==" + sessionId);

        SiteoneOktaRequestData siteoneOktaRequestData = new SiteoneOktaRequestData();
        //TODO Venkat SiteoneOktaRequestData.setId(sessionId);
        final SiteoneOktaResponseData siteoneOktaResponseData = getSiteOneOktaRestClient().execute(getFullUrl(SESSION_ENDPOINT + "/" + sessionId + "/lifecycle/refresh"),
                HttpMethod.POST, siteoneOktaRequestData, SiteoneOktaResponseData.class, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_GET_USER_SERVICE_NAME, createHttpHeaders(true));

        //TODO venkat deletelog.info("refreshSession(), oktaUserResponseData=="+siteoneOktaResponseData.getId()+"--"+siteoneOktaResponseData.getStatus()+"--"+siteoneOktaResponseData.getErrorCode());

        return null;
    }

    @Override
    public void closeSession(final String userOktaId) {
        log.info("closeSession(),oktaId==" + userOktaId);

        HttpEntity<String> requestEntity = new HttpEntity<String>(SiteoneintegrationConstants.HTTP_ENTITY_KEY, createHttpHeaders(true));
        ResponseEntity<OktaSessionCloseResponse> responseEntity = null;
        responseEntity = getRestTemplate().exchange(getFullUrl(USER_ENDPOINT + userOktaId + "/sessions"), HttpMethod.DELETE, requestEntity, OktaSessionCloseResponse.class);
        log.info("responseEntity==" + responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().value() == 204) {
            log.info(" Okta user Id " + userOktaId + " has been successfully logged out");
        }
    }


    @Override
    public void removeUserFromGroup(final String groupId, final String userId) {
        log.info("removeUserFromGroup(),groupId==" + groupId + " ,userId==" + userId);
        HttpEntity<String> requestEntity = new HttpEntity<String>(SiteoneintegrationConstants.HTTP_ENTITY_KEY, createHttpHeaders(true));
        ResponseEntity<OktaSessionCloseResponse> responseEntity = null;
        responseEntity = getRestTemplate().exchange(getFullUrl(GROUP_ENDPOINT + groupId + "/users/" + userId), HttpMethod.DELETE, requestEntity, OktaSessionCloseResponse.class);
        log.info("removeUserFromGroup(), responseEntity==" + responseEntity.getStatusCode());
    }

    /*
     * @Override public SiteOneOktaSessionData
     * establishSessionWithCredentials(String username, String password) {
     * String sessionToken = doAuth(username, password); OktaSessionResponse res
     * = establishSession(sessionToken); SiteOneOktaSessionData data = new
     * SiteOneOktaSessionData(); data.setUid(res.getLogin());
     * data.setExpiresAt(res.getExpiresAt());
     * data.setSessionToken(sessionToken); return data;
     *
     * }
     */

    private String getFullUrl(String endpoint) {
        String baseUrl = Config.getString("okta.domain.url", "");
        return baseUrl + endpoint;
    }

    private RestTemplate getRestTemplate() {
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(Config.getInt("rest.connection.timeout", 5000));
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(Config.getInt("rest.read.timeout", 5000));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
          return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private void setProxyIfRequired() {
        if (Config.getBoolean("proxy.enable", false)) {
            System.getProperties().put("http.proxyHost", Config.getParameter("proxy.host"));
            System.getProperties().put("http.proxyPort", Config.getString("proxy.http.port", "80"));
            System.getProperties().put("https.proxyHost", Config.getParameter("proxy.host"));
            System.getProperties().put("https.proxyPort", Config.getString("proxy.https.port", "80"));
            System.getProperties().put("http.proxyUser", Config.getParameter("proxy.user"));
            System.getProperties().put("http.proxyPassword", Config.getParameter("proxy.password"));
        }
    }

    private void debugObj(Object obj) {
        if (log.isDebugEnabled()) {
            log.debug(ToStringBuilder.reflectionToString(obj));
        }
    }

    private void debugMsg(String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    @Override
    public OktaCreateOrUpdateUserResponseData createUser(B2BCustomerModel b2bCustomerModel) throws ResourceAccessException {

        setProxyIfRequired();

        HttpHeaders httpHeaders = createHttpHeaders(true);
        OktaCreateOrUpdateUserRequestData oktaCreateOrUpdateUserRequestData = siteOneWsOktaUserConverter
                .convert(b2bCustomerModel);

        List<String> groupIds = new ArrayList<String>();
        
       
        if (b2bCustomerModel.getDefaultB2BUnit().getUid().contains("_US"))
        {
            groupIds.add(Config.getString(SiteoneintegrationConstants.OKTA_HYBRIS_GROUPID, StringUtils.EMPTY));

        }
        else
        {
            groupIds.add(Config.getString(SiteoneintegrationConstants.OKTA_HYBRIS_CA_GROUPID, StringUtils.EMPTY));

        }
        
        if (null != b2bCustomerModel.getPayBillOnline() && b2bCustomerModel.getPayBillOnline()) {
            groupIds.add(Config.getString(SiteoneintegrationConstants.OKTA_BILLTRUST_GROUPID, StringUtils.EMPTY));
        }

        oktaCreateOrUpdateUserRequestData.setGroupIds(groupIds);
        final OktaCreateOrUpdateUserResponseData oktaCreateOrUpdateUserResponseData = getSiteOneCreateOrUpdateRestClient().execute(
                Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                        + Config.getString(SiteoneintegrationConstants.OKTA_CREATE_USER_URL_KEY, StringUtils.EMPTY),
                HttpMethod.POST, oktaCreateOrUpdateUserRequestData, OktaCreateOrUpdateUserResponseData.class,
                UUID.randomUUID().toString(), SiteoneintegrationConstants.OKTA_CREATE_USER_SERVICE_NAME, httpHeaders);
        log.info(oktaCreateOrUpdateUserResponseData);
        clearProxy();
        return oktaCreateOrUpdateUserResponseData;

    }

	@Override
	public OktaChangePasswordResponseData changePasswordForUser(String oldPassword, String newPassword, String userId)
			throws OktaPasswordMismatchException, OktaInvalidPasswordException {

		setProxyIfRequired();
		HttpHeaders httpHeaders = createHttpHeaders(true);

		OktaChangePasswordResponseData oktaChangePasswordResponseData = new OktaChangePasswordResponseData();

		OktaChangePasswordRequestData oktaChangePasswordRequestData = new OktaChangePasswordRequestData();

		Password oldPasswordReq = new Password();
		oldPasswordReq.setValue(oldPassword);

		Password newPasswordReq = new Password();
		newPasswordReq.setValue(newPassword);

		oktaChangePasswordRequestData.setOldPassword(oldPasswordReq);
		oktaChangePasswordRequestData.setNewPassword(newPasswordReq);
		String changePasswordUrl = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
				+ Config.getString(SiteoneintegrationConstants.OKTA_CHANGE_PASSWORD_URL_KEY, StringUtils.EMPTY);
		changePasswordUrl = changePasswordUrl.replace(SiteoneintegrationConstants.OKTA_USER_ID_PLACEHOLDER, userId);
		OktaChangePasswordResponseData siteoneOktaResponseData = getSiteOneChangePasswordRestClient().execute(
				changePasswordUrl, HttpMethod.POST, oktaChangePasswordRequestData, OktaChangePasswordResponseData.class,
				UUID.randomUUID().toString(), SiteoneintegrationConstants.OKTA_CHANGE_PASSWORD_SERVICE_NAME,
				httpHeaders);

		if (null != siteoneOktaResponseData && SiteoneintegrationConstants.OKTA_CHANGE_PASSWORD_ERROR_CODE
				.equalsIgnoreCase(siteoneOktaResponseData.getErrorCode())) {
			if (null != siteoneOktaResponseData.getErrorCode()
					&& null != siteoneOktaResponseData.getErrorCauses()
					&& !siteoneOktaResponseData.getErrorCauses().isEmpty() && siteoneOktaResponseData
							.getErrorCauses().get(0).getErrorSummary().contains("Old Password is not correct")) {

				throw new OktaPasswordMismatchException(siteoneOktaResponseData.getErrorCode());
			}
			if (null != siteoneOktaResponseData.getErrorCode()
					&& null != siteoneOktaResponseData.getErrorCauses()
					&& !siteoneOktaResponseData.getErrorCauses().isEmpty()
					&& (siteoneOktaResponseData.getErrorCauses().get(0).getErrorSummary()
							.contains("Password has been used too recently") || siteoneOktaResponseData.getErrorCauses().get(0).getErrorSummary()
							.contains("Password cannot be your current password"))) {
				
				throw new OktaRecentlyUsedPasswordException(siteoneOktaResponseData.getErrorCode());
			} else {
				throw new OktaInvalidPasswordException(siteoneOktaResponseData.getErrorCode());
			}

		}
	
		clearProxy();
		return siteoneOktaResponseData;
	}

	@Override
	public OktaSetPasswordResponseData setPasswordForUser(B2BCustomerModel b2bCustomerModel, String password)
			throws OktaInvalidPasswordException {
		// TODO Auto-generated method stub
		setProxyIfRequired();
		HttpHeaders httpHeaders = createHttpHeaders(true);
		OktaSetPasswordRequestData oktaSetPasswordRequestData = new OktaSetPasswordRequestData();

		OktaSetPasswordCredentials credentials = new OktaSetPasswordCredentials();

		OktaSetPassword value = new OktaSetPassword();
		value.setValue(password);

		credentials.setPassword(value);

		oktaSetPasswordRequestData.setCredentials(credentials);
		final OktaSetPasswordResponseData siteoneOktaResponseData = getSiteOneSetPasswordRestClient().execute(
				Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
						+ Config.getString(SiteoneintegrationConstants.OKTA_SET_PASSWORD_URL_KEY, StringUtils.EMPTY)
						+ b2bCustomerModel.getUid(),
				HttpMethod.PUT, oktaSetPasswordRequestData, OktaSetPasswordResponseData.class,
				UUID.randomUUID().toString(), SiteoneintegrationConstants.OKTA_SET_PASSWORD_SERVICE_NAME, httpHeaders);

		if (null != siteoneOktaResponseData && SiteoneintegrationConstants.OKTA_SET_PASSWORD_ERROR_CODE
				.equalsIgnoreCase(siteoneOktaResponseData.getErrorCode())) {
			throw new OktaInvalidPasswordException(siteoneOktaResponseData.getErrorCode());
		}
		clearProxy();
		return siteoneOktaResponseData;

	}


    @Override
    public OktaCreateOrUpdateUserResponseData updateUser(B2BCustomerModel b2bCustomerModel) throws ResourceAccessException {

        setProxyIfRequired();
        HttpHeaders httpHeaders = createHttpHeaders(true);
        OktaCreateOrUpdateUserRequestData oktaCreateOrUpdateUserRequestData = siteOneWsOktaUserConverter
                .convert(b2bCustomerModel);

        String uid = b2bCustomerModel.getUid();

        if ((b2bCustomerModel.getUpdateEmailFlag() != null &&
                b2bCustomerModel.getUpdateEmailFlag().getCode().equalsIgnoreCase(UpdateEmailStatusEnum.UPDATE_OKTA.getCode()))) {
            uid = b2bCustomerModel.getOldContactEmail();
        }
        final OktaCreateOrUpdateUserResponseData oktaCreateOrUpdateUserResponseData = getSiteOneCreateOrUpdateRestClient().execute(
                Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                        + Config.getString(SiteoneintegrationConstants.OKTA_UPDATE_USER_URL_KEY, StringUtils.EMPTY)
                        + uid,
                HttpMethod.POST, oktaCreateOrUpdateUserRequestData, OktaCreateOrUpdateUserResponseData.class,
                UUID.randomUUID().toString(), SiteoneintegrationConstants.OKTA_UPDATE_USER_SERVICE_NAME, httpHeaders);
        clearProxy();
        return oktaCreateOrUpdateUserResponseData;
    }

	@Override
	public void activateUser(String userId) {
		// TODO Auto-generated method stub
		setProxyIfRequired();
		HttpHeaders httpHeaders = createHttpHeaders(true);

		String activateUserUrl = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
				+ Config.getString(SiteoneintegrationConstants.OKTA_ACTIVATE_USER_URL_KEY, StringUtils.EMPTY);
		activateUserUrl = activateUserUrl.replace(SiteoneintegrationConstants.OKTA_USER_ID_PLACEHOLDER, userId);
		SiteoneOktaResponseData siteoneOktaResponseData = getSiteOneOktaRestClient().execute(
				activateUserUrl, HttpMethod.POST, null, null, UUID.randomUUID().toString(),
				SiteoneintegrationConstants.OKTA_ACTIVATE_USER_SERVICE_NAME, httpHeaders);
		
		clearProxy();
	}

	@Override
	public OktaUnlockUserResponseData unlockUser(String userId) {
		// TODO Auto-generated method stub
		setProxyIfRequired();
		HttpHeaders httpHeaders = createHttpHeaders(true);

		String unlockUserUrl = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
				+ Config.getString(SiteoneintegrationConstants.OKTA_UNLOCK_USER_URL_KEY, StringUtils.EMPTY);
		unlockUserUrl = unlockUserUrl.replace(SiteoneintegrationConstants.OKTA_USER_ID_PLACEHOLDER, userId);
		OktaUnlockUserResponseData siteoneOktaResponseData = getSiteOneUnlockUserRestClient().execute(unlockUserUrl, HttpMethod.POST, null, OktaUnlockUserResponseData.class, UUID.randomUUID().toString(),
				SiteoneintegrationConstants.OKTA_UNLOCK_USER_SERVICE_NAME, httpHeaders);
		
		if(null != siteoneOktaResponseData && null == siteoneOktaResponseData.getErrorCode()){

			siteoneOktaResponseData.setStatus("SUCCESS");
		}
		
		clearProxy();
		
		return siteoneOktaResponseData;
		 
	}

    @Override
    public OktaCreateOrUpdateUserResponseData getUser(String userId) throws OktaUnknownUserException {
        // TODO Auto-generated method stub
         setProxyIfRequired();
        HttpHeaders httpHeaders = createHttpHeaders(true);

        String endPoint = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                + Config.getString(SiteoneintegrationConstants.OKTA_GET_USER_URL_KEY, StringUtils.EMPTY) + userId;
        final OktaCreateOrUpdateUserResponseData oktaCreateOrUpdateUserResponseData = getSiteOneGetUserRestClient().execute(endPoint,
                HttpMethod.GET, null, OktaCreateOrUpdateUserResponseData.class, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_GET_USER_SERVICE_NAME, httpHeaders);

        if (null != oktaCreateOrUpdateUserResponseData && SiteoneintegrationConstants.OKTA_GET_USER_UNKNOWN_ERROR_CODE.equalsIgnoreCase(oktaCreateOrUpdateUserResponseData.getErrorCode())) {
            throw new OktaUnknownUserException(oktaCreateOrUpdateUserResponseData.getErrorCode());
        }


        clearProxy();
        return oktaCreateOrUpdateUserResponseData;

    }

    private HttpHeaders createHttpHeaders(boolean isAuthorizationRequired) {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(SiteoneintegrationConstants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(SiteoneintegrationConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if (getSessionService().getAttribute("clientIp") != null) {

            final String clientIpAddr = getSessionService().getAttribute("clientIp");
            log.error("clientIpAddr: " + clientIpAddr);
            httpHeaders.set(SiteoneintegrationConstants.CLIENT_IP_ADDR, clientIpAddr);
        }
        if (getSessionService().getAttribute("userAgent") != null) {
            final String userAgent = getSessionService().getAttribute("userAgent");
            httpHeaders.set(SiteoneintegrationConstants.USER_AGENT, userAgent);
        }
        if (isAuthorizationRequired) {
            httpHeaders.set(SiteoneintegrationConstants.AUTHORIZATION_HEADER,
                    SiteoneintegrationConstants.AUTHORIZATION_TYPE_SSWS + " "
                            + Config.getString(SiteoneintegrationConstants.OKTA_API_KEY, null));
        }

        return httpHeaders;
    }
	
	@Override
	 public OktaForgotPwdResponseData forgotPasswordForUser(String userId) throws OktaUnknownUserException{
	 	// TODO Auto-generated method stub
		
		 setProxyIfRequired();
		 HttpHeaders httpHeaders= createHttpHeaders(true);
		 OktaForgotPwdRequestData oktaForgotPwdRequestData = new OktaForgotPwdRequestData();
		 
		 oktaForgotPwdRequestData.setUsername(userId);
		 oktaForgotPwdRequestData.setRelayState("/");
		 		 
		 
		 final OktaForgotPwdResponseData siteoneOktaResponseData = getSiteOneForgotPasswordRestClient().execute(Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)+Config.getString(SiteoneintegrationConstants.OKTA_FORGOT_PASSWORD_URL_KEY, StringUtils.EMPTY),
					HttpMethod.POST, oktaForgotPwdRequestData , OktaForgotPwdResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.OKTA_FORGOT_PASSWORD_SERVICE_NAME,httpHeaders);
		   
		 if (null != siteoneOktaResponseData && SiteoneintegrationConstants.OKTA_FORGOT_PASSWORD_ERROR_CODE.equalsIgnoreCase(siteoneOktaResponseData.getErrorCode()))
			{
				throw new OktaUnknownUserException(siteoneOktaResponseData.getErrorCode());
		    }
		   
		 clearProxy();
		 return siteoneOktaResponseData;
		 
	 	}

    private void clearProxy() {
        if (Config.getBoolean("proxy.enable", false)) {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
            System.clearProperty("http.proxyUser");
            System.clearProperty("http.proxyPassword");
        }
    }

    /**
     * @return the siteOneRestClient
     */
	@Override
		public OktaVerifyRecoveryTokenResponseData recoverytokenVerification(String recoveryToken) throws OktaInvalidTokenException{
			// TODO Auto-generated method stub
		 setProxyIfRequired();
		 HttpHeaders httpHeaders= createHttpHeaders(false);


		 OktaVerifyRecoveryTokenRequestData OktaVerifyRecoveryTokenRequestData = new OktaVerifyRecoveryTokenRequestData();
		 
		 OktaVerifyRecoveryTokenRequestData.setRecoveryToken(recoveryToken);
		 final OktaVerifyRecoveryTokenResponseData siteoneOktaResponseData = getSiteOneRecoveryTokenRestClient().execute(Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)+Config.getString(SiteoneintegrationConstants.OKTA_VERIFY_RECOVERY_TOKEN_URL_KEY, StringUtils.EMPTY),
					HttpMethod.POST, OktaVerifyRecoveryTokenRequestData , OktaVerifyRecoveryTokenResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.OKTA_VERIFY_RECOVERY_TOKEN_SERVICE_NAME,httpHeaders);

		 if (null != siteoneOktaResponseData && SiteoneintegrationConstants.OKTA_VERIFY_RECOVERY_TOKEN_ERROR_CODE.equalsIgnoreCase(siteoneOktaResponseData.getErrorCode()))
			{
				throw new OktaInvalidTokenException(siteoneOktaResponseData.getErrorCode());
		    }
		 clearProxy();
		 return siteoneOktaResponseData;

		}


		@Override
		public OktaResetPasswordResponseData resetPasswordForUser(String stateToken, String newPassword) throws OktaInvalidTokenException,OktaInvalidPasswordException,OktaRecentlyUsedPasswordException
		{
			// TODO Auto-generated method stub
			 setProxyIfRequired();
			 HttpHeaders httpHeaders= createHttpHeaders(false);
             OktaResetPasswordRequestData oktaResetPasswordRequestData = new OktaResetPasswordRequestData();
			 
			 oktaResetPasswordRequestData.setStateToken(stateToken);
			 oktaResetPasswordRequestData.setNewPassword(newPassword);
			 final OktaResetPasswordResponseData siteoneOktaResponseData = getSiteOneResetPasswordRestClient().execute(Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)+Config.getString(SiteoneintegrationConstants.OKTA_RESET_PASSWORD_URL_KEY, StringUtils.EMPTY),
						HttpMethod.POST, oktaResetPasswordRequestData , OktaResetPasswordResponseData.class,UUID.randomUUID().toString(),SiteoneintegrationConstants.OKTA_RESET_PASSWORD_SERVICE_NAME,httpHeaders);

		if (null != siteoneOktaResponseData && null != siteoneOktaResponseData.getErrorCode()) {
			if (SiteoneintegrationConstants.OKTA_RESET_PASSWORD_ERROR_CODE1.equalsIgnoreCase(siteoneOktaResponseData.getErrorCode())) {
				throw new OktaInvalidTokenException(siteoneOktaResponseData.getErrorCode());
			} else if (SiteoneintegrationConstants.OKTA_RESET_PASSWORD_ERROR_CODE2.equalsIgnoreCase(siteoneOktaResponseData.getErrorCode())) {

				if (null != siteoneOktaResponseData.getErrorCode()
						&& null != siteoneOktaResponseData.getErrorCauses()
						&& !siteoneOktaResponseData.getErrorCauses().isEmpty()
						&& ( siteoneOktaResponseData.getErrorCauses().get(0).getErrorSummary()
								.contains("Password has been used too recently") || siteoneOktaResponseData.getErrorCauses().get(0).getErrorSummary()
						.contains("Password cannot be your current password")))  {

					throw new OktaRecentlyUsedPasswordException(siteoneOktaResponseData.getErrorCode());
				}else {
					throw new OktaInvalidPasswordException(siteoneOktaResponseData.getErrorCode());
				}

			}
		}
		clearProxy();
			 return siteoneOktaResponseData;

		}
    public void addUserToGroup(String groupId, String userID) {
        // TODO Auto-generated method stub

        log.info("addUserToGroup(),groupId==" + groupId + " ,userId==" + userID);
        HttpEntity<String> requestEntity = new HttpEntity<String>(SiteoneintegrationConstants.HTTP_ENTITY_KEY, createHttpHeaders(true));
        ResponseEntity<SiteoneOktaResponseData> responseEntity = null;
        responseEntity = getRestTemplate().exchange(getFullUrl(GROUP_ENDPOINT + groupId + "/users/" + userID), HttpMethod.PUT, requestEntity, SiteoneOktaResponseData.class);
        log.info("addUserToGroup(), responseEntity==" + responseEntity.getStatusCode());


    }

    @Override
    public Boolean deleteUser(String userId)
    {
        setProxyIfRequired();
        HttpHeaders httpHeaders = createHttpHeaders(true);

        String deleteUserUrl = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                + Config.getString(SiteoneintegrationConstants.OKTA_UPDATE_USER_URL_KEY, StringUtils.EMPTY) + userId;

        SiteoneOktaResponseData siteoneOktaDeactivateResponseData = getSiteOneOktaRestClient().execute(
                deleteUserUrl, HttpMethod.DELETE, null, null, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_REMOVE_USER_SERVICE_NAME, httpHeaders);

        if (siteoneOktaDeactivateResponseData != null && StringUtils.isNotEmpty(siteoneOktaDeactivateResponseData.getErrorCode()))
        {
            log.error("User can't be deactivated " + userId +
                    " - Error: " + siteoneOktaDeactivateResponseData.getErrorCode() + " - " +
                    siteoneOktaDeactivateResponseData.getErrorSummary());
            clearProxy();
            return false;
        }

        SiteoneOktaResponseData siteoneOktaDeleteResponseData = getSiteOneOktaRestClient().execute(
                deleteUserUrl, HttpMethod.DELETE, null, null, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_REMOVE_USER_SERVICE_NAME, httpHeaders);

        if (siteoneOktaDeleteResponseData != null && StringUtils.isNotEmpty(siteoneOktaDeleteResponseData.getErrorCode()))
        {
            log.error("User can't be deleted " + userId +
                    " - Error: " + siteoneOktaDeleteResponseData.getErrorCode() + " - " +
                    siteoneOktaDeleteResponseData.getErrorSummary());
            clearProxy();
            return false;
        }

        clearProxy();
        return true;
    }

    @Override
    public Boolean isUserInOkta (String uid)
    {
        Boolean userExistInOkta = false;
        setProxyIfRequired();
        HttpHeaders httpHeaders = createHttpHeaders(true);

        String endPoint = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                + Config.getString(SiteoneintegrationConstants.OKTA_GET_USER_URL_KEY, StringUtils.EMPTY) + uid;
        final OktaCreateOrUpdateUserResponseData oktaCreateOrUpdateUserResponseData = getSiteOneGetUserRestClient().execute(endPoint,
                HttpMethod.GET, null, OktaCreateOrUpdateUserResponseData.class, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_GET_USER_SERVICE_NAME, httpHeaders);

        if (null != oktaCreateOrUpdateUserResponseData && null == oktaCreateOrUpdateUserResponseData.getErrorCode()) {
            userExistInOkta = true;
        }
        clearProxy();
        return userExistInOkta;
    }

    public SiteOneOktaRestClient<OktaCreateOrUpdateUserRequestData, OktaCreateOrUpdateUserResponseData> getSiteOneCreateOrUpdateRestClient() {
        return siteOneCreateOrUpdateRestClient;
    }

    public void setSiteOneCreateOrUpdateRestClient(SiteOneOktaRestClient<OktaCreateOrUpdateUserRequestData, OktaCreateOrUpdateUserResponseData> siteOneCreateOrUpdateRestClient) {
        this.siteOneCreateOrUpdateRestClient = siteOneCreateOrUpdateRestClient;
    }

    public SiteOneOktaRestClient<SiteoneOktaRequestData, SiteoneOktaResponseData> getSiteOneOktaRestClient() {
        return siteOneOktaRestClient;
    }

    public void setSiteOneOktaRestClient(SiteOneOktaRestClient<SiteoneOktaRequestData, SiteoneOktaResponseData> siteOneOktaRestClient) {
        this.siteOneOktaRestClient = siteOneOktaRestClient;
    }

    public Converter<B2BCustomerModel, OktaCreateOrUpdateUserRequestData> getSiteOneWsOktaUserConverter() {
        return siteOneWsOktaUserConverter;
    }

    public void setSiteOneWsOktaUserConverter(Converter<B2BCustomerModel, OktaCreateOrUpdateUserRequestData> siteOneWsOktaUserConverter) {
        this.siteOneWsOktaUserConverter = siteOneWsOktaUserConverter;
    }

    public SiteOneOktaRestClient<?, OktaConfigAppDetailsResponseData[]> getSiteOneOktaConfigAppDetailsRestClient() {
        return siteOneOktaConfigAppDetailsRestClient;
    }

    public void setSiteOneOktaConfigAppDetailsRestClient(
            SiteOneOktaRestClient<?, OktaConfigAppDetailsResponseData[]> siteOneOktaConfigAppDetailsRestClient) {
        this.siteOneOktaConfigAppDetailsRestClient = siteOneOktaConfigAppDetailsRestClient;
    }

    public SiteOneOktaRestClient<?, OktaCreateOrUpdateUserResponseData> getSiteOneGetUserRestClient() {
        return siteOneGetUserRestClient;
    }

    public void setSiteOneGetUserRestClient(SiteOneOktaRestClient<?, OktaCreateOrUpdateUserResponseData> siteOneGetUserRestClient) {
        this.siteOneGetUserRestClient = siteOneGetUserRestClient;
    }

	public SiteOneOktaRestClient<OktaChangePasswordRequestData, OktaChangePasswordResponseData> getSiteOneChangePasswordRestClient() {
		return siteOneChangePasswordRestClient;
	}

	public void setSiteOneChangePasswordRestClient(
			SiteOneOktaRestClient<OktaChangePasswordRequestData, OktaChangePasswordResponseData> siteOneChangePasswordRestClient) {
		this.siteOneChangePasswordRestClient = siteOneChangePasswordRestClient;
	}

	public SiteOneOktaRestClient<OktaSetPasswordRequestData, OktaSetPasswordResponseData> getSiteOneSetPasswordRestClient() {
		return siteOneSetPasswordRestClient;
	}

	public void setSiteOneSetPasswordRestClient(
			SiteOneOktaRestClient<OktaSetPasswordRequestData, OktaSetPasswordResponseData> siteOneSetPasswordRestClient) {
		this.siteOneSetPasswordRestClient = siteOneSetPasswordRestClient;
	}

	public SiteOneOktaRestClient<OktaSessionIdRequest, OktaUserResponseData> getSiteOneResfreshSessionRestClient() {
		return siteOneResfreshSessionRestClient;
	}

	public void setSiteOneResfreshSessionRestClient(
			SiteOneOktaRestClient<OktaSessionIdRequest, OktaUserResponseData> siteOneResfreshSessionRestClient) {
		this.siteOneResfreshSessionRestClient = siteOneResfreshSessionRestClient;
	}

	public SiteOneOktaRestClient<?, OktaUserActivationResponseData> getSiteOneActivateUserRestClient() {
		return siteOneActivateUserRestClient;
	}

	public void setSiteOneActivateUserRestClient(
			SiteOneOktaRestClient<?, OktaUserActivationResponseData> siteOneActivateUserRestClient) {
		this.siteOneActivateUserRestClient = siteOneActivateUserRestClient;
	}

	public SiteOneOktaRestClient<?, OktaUnlockUserResponseData> getSiteOneUnlockUserRestClient() {
		return siteOneUnlockUserRestClient;
	}

	public void setSiteOneUnlockUserRestClient(
			SiteOneOktaRestClient<?, OktaUnlockUserResponseData> siteOneUnlockUserRestClient) {
		this.siteOneUnlockUserRestClient = siteOneUnlockUserRestClient;
	}

	public SiteOneOktaRestClient<OktaForgotPwdRequestData, OktaForgotPwdResponseData> getSiteOneForgotPasswordRestClient() {
		return siteOneForgotPasswordRestClient;
	}

	public void setSiteOneForgotPasswordRestClient(
			SiteOneOktaRestClient<OktaForgotPwdRequestData, OktaForgotPwdResponseData> siteOneForgotPasswordRestClient) {
		this.siteOneForgotPasswordRestClient = siteOneForgotPasswordRestClient;
	}

	public SiteOneOktaRestClient<OktaVerifyRecoveryTokenRequestData, OktaVerifyRecoveryTokenResponseData> getSiteOneRecoveryTokenRestClient() {
		return siteOneRecoveryTokenRestClient;
	}

	public void setSiteOneRecoveryTokenRestClient(
			SiteOneOktaRestClient<OktaVerifyRecoveryTokenRequestData, OktaVerifyRecoveryTokenResponseData> siteOneRecoveryTokenRestClient) {
		this.siteOneRecoveryTokenRestClient = siteOneRecoveryTokenRestClient;
	}

	public SiteOneOktaRestClient<OktaResetPasswordRequestData, OktaResetPasswordResponseData> getSiteOneResetPasswordRestClient() {
		return siteOneResetPasswordRestClient;
	}

	public void setSiteOneResetPasswordRestClient(
			SiteOneOktaRestClient<OktaResetPasswordRequestData, OktaResetPasswordResponseData> siteOneResetPasswordRestClient) {
		this.siteOneResetPasswordRestClient = siteOneResetPasswordRestClient;
	}
	
    /**
	 * @return the siteOneOktaConfigUserAppDetailsRestClient
	 */
	public SiteOneOktaRestClient<?, OktaConfigUserAppDetailResponseData[]> getSiteOneOktaConfigUserAppDetailsRestClient() {
		return siteOneOktaConfigUserAppDetailsRestClient;
	}

	/**
	 * @param siteOneOktaConfigUserAppDetailsRestClient the siteOneOktaConfigUserAppDetailsRestClient to set
	 */
	public void setSiteOneOktaConfigUserAppDetailsRestClient(
			SiteOneOktaRestClient<?, OktaConfigUserAppDetailResponseData[]> siteOneOktaConfigUserAppDetailsRestClient) {
		this.siteOneOktaConfigUserAppDetailsRestClient = siteOneOktaConfigUserAppDetailsRestClient;
	}

	@Override
	public void getOAuth(String uid, String plainPassword) {
		sessionService.setAttribute("agroWebEnabled",false);
		String url = Config.getString(SiteoneintegrationConstants.AGRO_AI_API,StringUtils.EMPTY);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(SiteoneintegrationConstants.CLIENT_ID, Config.getString("agro.ai.client.id", StringUtils.EMPTY));
		map.add(SiteoneintegrationConstants.CLIENT_SECRET,Config.getString("agro.ai.client.secret", StringUtils.EMPTY));

		map.add(SiteoneintegrationConstants.GRANT_TYPE, Config.getString("agro.ai.grant.type", StringUtils.EMPTY));
		map.add(SiteoneintegrationConstants.USER_NAME,uid);
		map.add(SiteoneintegrationConstants.PASSWORD,plainPassword);
	
		
		HttpEntity<MultiValueMap<String, String>> requestEntity= 
		new HttpEntity<>(map, headers);
		try{
		 OAuth2AccessToken res = getRestTemplate().postForObject(url, requestEntity, OAuth2AccessToken.class);		 
		 sessionService.setAttribute("refreshToken",res.getRefreshToken().getValue());
		}
		catch(Exception e){
			log.error("exception on Oauth "+ e);
		}
	}


	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	public OAuth2RequestFactory getSmarteditOAuth2RequestFactory() {
		return smarteditOAuth2RequestFactory;
	}

	public void setSmarteditOAuth2RequestFactory(OAuth2RequestFactory smarteditOAuth2RequestFactory) {
		this.smarteditOAuth2RequestFactory = smarteditOAuth2RequestFactory;
	}

    @Override
    public Boolean suspendUser(String userId) {
        setProxyIfRequired();
        HttpHeaders httpHeaders = createHttpHeaders(true);

        String suspendUserUrl = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                + Config.getString(SiteoneintegrationConstants.OKTA_UPDATE_USER_URL_KEY, StringUtils.EMPTY) + userId
                + Config.getString(SiteoneintegrationConstants.OKTA_SUSPEND_USER_URL_KEY, StringUtils.EMPTY);

        SiteoneOktaResponseData siteoneOktaSuspendResponseData = getSiteOneOktaRestClient().execute(
                suspendUserUrl, HttpMethod.POST, null, null, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_SUSPEND_USER_SERVICE_NAME, httpHeaders);

        if (siteoneOktaSuspendResponseData != null && StringUtils.isNotEmpty(siteoneOktaSuspendResponseData.getErrorCode()))
        {
            log.error("User can't be suspended " + userId +
                    " - Error: " + siteoneOktaSuspendResponseData.getErrorCode() + " - " +
                    siteoneOktaSuspendResponseData.getErrorSummary());
            clearProxy();
            return false;
        }
        clearProxy();
        return true;
    }

    @Override
    public Boolean unSuspendUser(String userId) {
        setProxyIfRequired();
        HttpHeaders httpHeaders = createHttpHeaders(true);

        String unSuspendUserUrl = Config.getString(SiteoneintegrationConstants.OKTA_API_BASE_URL, StringUtils.EMPTY)
                + Config.getString(SiteoneintegrationConstants.OKTA_UPDATE_USER_URL_KEY, StringUtils.EMPTY) + userId
                + Config.getString(SiteoneintegrationConstants.OKTA_UNSUSPEND_USER_URL_KEY, StringUtils.EMPTY);

        SiteoneOktaResponseData siteoneOktaUnSuspendResponseData = getSiteOneOktaRestClient().execute(
                unSuspendUserUrl, HttpMethod.POST, null, null, UUID.randomUUID().toString(),
                SiteoneintegrationConstants.OKTA_UNSUSPEND_USER_SERVICE_NAME, httpHeaders);

        if (siteoneOktaUnSuspendResponseData != null && StringUtils.isNotEmpty(siteoneOktaUnSuspendResponseData.getErrorCode()))
        {
            log.error("User can't be unsuspended " + userId +
                    " - Error: " + siteoneOktaUnSuspendResponseData.getErrorCode() + " - " +
                    siteoneOktaUnSuspendResponseData.getErrorSummary());
            clearProxy();
            return false;
        }
        clearProxy();
        return true;
    }
}
