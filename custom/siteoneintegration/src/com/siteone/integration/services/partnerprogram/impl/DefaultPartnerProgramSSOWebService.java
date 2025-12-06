package com.siteone.integration.services.partnerprogram.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.services.partnerprogram.PartnerProgramSSOWebService;
import com.siteone.integration.webserviceclient.partnerssowsclient.ObjectFactory;
import com.siteone.integration.webserviceclient.partnerssowsclient.OpenSession;
import com.siteone.integration.webserviceclient.partnerssowsclient.OpenSessionResponse;

import de.hybris.platform.util.Config;

public class DefaultPartnerProgramSSOWebService  implements PartnerProgramSSOWebService{
	
	private WebServiceTemplate webServiceTemplate;
	private static final Logger log = Logger.getLogger(DefaultPartnerProgramSSOWebService.class);
	

	@Override
	public String openSession(String userId) {
		ObjectFactory factory = new ObjectFactory();
		OpenSession openSession = factory.createOpenSession();
		openSession.setUsername(userId);
		openSession.setToken(Config.getString(SiteoneintegrationConstants.PARTNERPROGRAM_PILOT_KEY, StringUtils.EMPTY));
		
		try {
			setProxyIfRequired();
			OpenSessionResponse openSessionResponse = (OpenSessionResponse) webServiceTemplate
					.marshalSendAndReceive(openSession);
			if (null != openSessionResponse && null != openSessionResponse.getOpenSessionResult()) {
				return openSessionResponse.getOpenSessionResult().getAccessToken();
			}
		} catch (Exception exception) {
			log.error("Error occured in openSession",exception);
		} finally {
			clearProxy();
		}
		
		return null;
	}

	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
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

}
