package com.siteone.integration.services.cayanclient.impl;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.siteone.integration.services.cayanclient.SiteOneCayanPaymentWebService;
import com.siteone.integration.webserviceclient.cayanclient.Authorize;
import com.siteone.integration.webserviceclient.cayanclient.AuthorizeResponse;
import com.siteone.integration.webserviceclient.cayanclient.BoardCard;
import com.siteone.integration.webserviceclient.cayanclient.BoardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.Capture;
import com.siteone.integration.webserviceclient.cayanclient.CaptureResponse;
import com.siteone.integration.webserviceclient.cayanclient.Sale;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;
import com.siteone.integration.webserviceclient.cayanclient.UnboardCard;
import com.siteone.integration.webserviceclient.cayanclient.UnboardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCard;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCardResponse;

import de.hybris.platform.util.Config;

public class DefaultSiteOneCayanPaymentWebService implements SiteOneCayanPaymentWebService {
	private static final Logger LOGGER = Logger.getLogger(DefaultSiteOneCayanPaymentWebService.class);
	
	@Resource(name = "siteOneCayanPaymentWebServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	@Override
	public BoardCardResponse boardCard(BoardCard boardCard) {

		try {
			setProxyIfRequired();
			BoardCardResponse boardCardResponse = (BoardCardResponse) webServiceTemplate
					.marshalSendAndReceive(boardCard);
			if (null != boardCardResponse && null != boardCardResponse.getBoardCardResult()) {
				LOGGER.info("Boardcard is success for "+boardCard.getPaymentData().getToken());
				return boardCardResponse;
			}
		} catch (Exception ex) {
			LOGGER.error("Error occured in boardCard", ex);
		} finally {
			clearProxy();
		}
		return null;
	}
	
	@Override
	public UnboardCardResponse cayanUnboardCard(UnboardCard unboardCard) {
		try {
			setProxyIfRequired();
			UnboardCardResponse unboardCardResponse = (UnboardCardResponse) webServiceTemplate
					.marshalSendAndReceive(unboardCard);
			if (null != unboardCardResponse && null != unboardCardResponse.getUnboardCardResult()) {
				String vaultToken = unboardCardResponse.getUnboardCardResult().getVaultToken();
				LOGGER.info("Vault Token after unboard>>>>>>"+vaultToken);
				return unboardCardResponse;
			}
		} catch (Exception exception) {
			LOGGER.error("Error occured in openSession", exception);
		} finally {
			clearProxy();
		}
		return null;
	}
	
	@Override
	public UpdateBoardedCardResponse cayanUpdateBoardedCard(UpdateBoardedCard updateBoardedCard) {

		try {
			setProxyIfRequired();
			UpdateBoardedCardResponse updateBoardedCardResponse = (UpdateBoardedCardResponse) webServiceTemplate
					.marshalSendAndReceive(updateBoardedCard);
			if (null != updateBoardedCardResponse && null != updateBoardedCardResponse.getUpdateBoardedCardResult()) {
				String vaultToken = updateBoardedCardResponse.getUpdateBoardedCardResult().getVaultToken();
				LOGGER.info("Vault Token after update>>>>>>"+vaultToken);
				return updateBoardedCardResponse;
			}
		} catch (Exception exception) {
			LOGGER.error("Error occured in openSession", exception);
		} finally {
			clearProxy();
		}
		return null;
	}

	@Override
	public AuthorizeResponse cayanAuthorize(Authorize authorize) {
		try {
			setProxyIfRequired();
			AuthorizeResponse authorizeResponse = (AuthorizeResponse) webServiceTemplate
					.marshalSendAndReceive(authorize);

			if (null != authorizeResponse && null != authorizeResponse.getAuthorizeResult()) {
				return authorizeResponse;
			}
		} catch (Exception exception) {
			LOGGER.error("Error occured in cayanAuthorize", exception);
		} finally {
			clearProxy();
		}
		return null;
	}
	
	@Override
	public SaleResponse cayanSale(Sale sale) {
		try {
			setProxyIfRequired();
			SaleResponse saleResponse = (SaleResponse) webServiceTemplate
					.marshalSendAndReceive(sale);

			if (null != saleResponse && null != saleResponse.getSaleResult()) {
				return saleResponse;
			}
		} catch (Exception exception) {
			LOGGER.error("Error occured in cayanSale", exception);
		} finally {
			clearProxy();
		}
		return null;
	}
	
	@Override
	public CaptureResponse cayanCapture(final Capture capture) {
		try {
			setProxyIfRequired();
			CaptureResponse captureResponse = (CaptureResponse) webServiceTemplate
					.marshalSendAndReceive(capture);

			if (null != captureResponse && null != captureResponse.getCaptureResult()) {
				return captureResponse;
			}
		} catch (Exception exception) {
			LOGGER.error("Error occured in cayanCapture", exception);
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
