package com.siteone.integration.services.cayanclient.impl;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.siteone.integration.services.cayanclient.SiteOneCayanTransportWebService;
import com.siteone.integration.webserviceclient.cayantransportclient.CreateTransaction;
import com.siteone.integration.webserviceclient.cayantransportclient.CreateTransactionResponse;
import com.siteone.integration.webserviceclient.cayantransportclient.Message;

import de.hybris.platform.util.Config;

public class DefaultSiteOneCayanTransportWebService implements SiteOneCayanTransportWebService {

	private static final Logger LOGGER = Logger.getLogger(DefaultSiteOneCayanTransportWebService.class);

	@Resource(name = "siteOneCayanTransportWebServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	@Override
	public String getTransportKey(CreateTransaction createTransaction) {
		try {
			setProxyIfRequired();
			CreateTransactionResponse createTransactionResp = (CreateTransactionResponse) webServiceTemplate
					.marshalSendAndReceive(createTransaction);
			if (null != createTransactionResp
					&& !StringUtils.isEmpty(createTransactionResp.getCreateTransactionResult().getTransportKey())) {

				LOGGER.info("Transport Key is generated successfully : "
						+ createTransactionResp.getCreateTransactionResult().getTransportKey());
				return createTransactionResp.getCreateTransactionResult().getTransportKey();

			} else {
				LOGGER.info("Transport Key is generated failed");
				if (null != createTransactionResp.getCreateTransactionResult().getMessages() && !CollectionUtils
						.isEmpty(createTransactionResp.getCreateTransactionResult().getMessages().getMessage())) {
					for (Message msg : createTransactionResp.getCreateTransactionResult().getMessages().getMessage()) {
						LOGGER.info("Cayan Error Message [Field]: " + msg.getField());
						LOGGER.info("Cayan Error Message [Information]: " + msg.getInformation());
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Error occured in getTransportKey", ex);
		} finally {
			clearProxy();
		}
		return null;
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

	/**
	 * @return the webServiceTemplate
	 */
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	/**
	 * @param webServiceTemplate the webServiceTemplate to set
	 */
	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

}
