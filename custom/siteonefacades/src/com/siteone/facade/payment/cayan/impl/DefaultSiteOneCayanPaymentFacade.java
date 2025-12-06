/**
 *
 */
package com.siteone.facade.payment.cayan.impl;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.integration.services.cayanclient.SiteOneCayanPaymentWebService;
import com.siteone.integration.webserviceclient.cayanclient.AuthorizationRequest;
import com.siteone.integration.webserviceclient.cayanclient.Authorize;
import com.siteone.integration.webserviceclient.cayanclient.AuthorizeResponse;
import com.siteone.integration.webserviceclient.cayanclient.BoardCard;
import com.siteone.integration.webserviceclient.cayanclient.BoardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.Capture;
import com.siteone.integration.webserviceclient.cayanclient.CaptureRequest;
import com.siteone.integration.webserviceclient.cayanclient.CaptureResponse;
import com.siteone.integration.webserviceclient.cayanclient.MerchantCredentials;
import com.siteone.integration.webserviceclient.cayanclient.PaymentData;
import com.siteone.integration.webserviceclient.cayanclient.Sale;
import com.siteone.integration.webserviceclient.cayanclient.SaleRequest;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;
import com.siteone.integration.webserviceclient.cayanclient.UnboardCard;
import com.siteone.integration.webserviceclient.cayanclient.UnboardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCard;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCardRequest;
import com.siteone.integration.webserviceclient.cayanclient.UpdateBoardedCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.VaultTokenRequest;


/**
 * @author pelango
 *
 */
public class DefaultSiteOneCayanPaymentFacade implements SiteOneCayanPaymentFacade
{
	private static final Logger LOGGER = Logger.getLogger(DefaultSiteOneCayanPaymentFacade.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "siteOneCayanPaymentWebService")
	private SiteOneCayanPaymentWebService siteOneCayanPaymentWebService;
	

	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	/**
	 * @param configurationService the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	/**
	 * @return the siteOneCayanPaymentWebService
	 */
	public SiteOneCayanPaymentWebService getSiteOneCayanPaymentWebService()
	{
		return siteOneCayanPaymentWebService;
	}

	/**
	 * @param siteOneCayanPaymentWebService the siteOneCayanPaymentWebService to set
	 */
	public void setSiteOneCayanPaymentWebService(SiteOneCayanPaymentWebService siteOneCayanPaymentWebService)
	{
		this.siteOneCayanPaymentWebService = siteOneCayanPaymentWebService;
	}

	@Override
	public BoardCardResponse boardCard(final String authToken)
	{
		BoardCardResponse boardCardResponse = null;
		if (StringUtils.isNotBlank(authToken))
		{
			final MerchantCredentials cred = new MerchantCredentials();
			cred.setMerchantName(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
			cred.setMerchantSiteId(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
			cred.setMerchantKey(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));

			final PaymentData pay = new PaymentData();
			pay.setSource(configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_SOURCE_BOARDCARD));
			pay.setToken(authToken);
			final BoardCard boardCard = new BoardCard();
			boardCard.setCredentials(cred);
			boardCard.setPaymentData(pay);

			boardCardResponse = siteOneCayanPaymentWebService.boardCard(boardCard);
			
			int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
			int retryCount=0;
			while(boardCardResponse==null && retryCount<cayanCallMaxRetryCount){
				retryCount++;
				LOGGER.error("Retrying cayan boardcard- "+authToken+"...retry# "+retryCount);
				boardCardResponse = siteOneCayanPaymentWebService.boardCard(boardCard);
			}
		}
		return boardCardResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.cayanpayment.SiteOneCayanPaymentFacade#Unboardcard(java.lang.String)
	 */
	@Override
	public String unboardCard(final String vaultToken)
	{
		final UnboardCard unboardcard = new UnboardCard();

		if (null != vaultToken)
		{
			final MerchantCredentials credentials = new MerchantCredentials();
			final VaultTokenRequest vaultTokenRequest = new VaultTokenRequest();
			credentials.setMerchantName(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
			credentials.setMerchantSiteId(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
			credentials.setMerchantKey(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));

			unboardcard.setCredentials(credentials);

			vaultTokenRequest.setVaultToken(vaultToken);

			unboardcard.setRequest(vaultTokenRequest);

			UnboardCardResponse resp = siteOneCayanPaymentWebService.cayanUnboardCard(unboardcard);
			
			int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
			int retryCount=0;
			while(resp==null && retryCount<cayanCallMaxRetryCount){
				retryCount++;
				LOGGER.error("Retrying cayan unboardcard- "+vaultToken+"...retry# "+retryCount);
				resp = siteOneCayanPaymentWebService.cayanUnboardCard(unboardcard);
			}

			if (null != resp && null != (resp.getUnboardCardResult().getVaultToken()))
			{

				return "SUCCESS";
			}

		}
		return "FAILURE";
	}


	@Override
	public String updateBoardedCard(final String vaultToken, final String expDate)throws ResourceAccessException
	{
		final UpdateBoardedCard updateBoardedCard = new UpdateBoardedCard();
		if (null != vaultToken && null != expDate)
		{
			final MerchantCredentials credentials = new MerchantCredentials();

			credentials.setMerchantName(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
			credentials.setMerchantSiteId(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
			credentials.setMerchantKey(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));

			updateBoardedCard.setCredentials(credentials);

			final UpdateBoardedCardRequest updateBoardedCardRequest = new UpdateBoardedCardRequest();
			updateBoardedCardRequest.setExpirationDate(expDate);
			updateBoardedCardRequest.setVaultToken(vaultToken);

			updateBoardedCard.setRequest(updateBoardedCardRequest);

			UpdateBoardedCardResponse resp = siteOneCayanPaymentWebService.cayanUpdateBoardedCard(updateBoardedCard);
			
			int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
			int retryCount=0;
			while(resp==null && retryCount<cayanCallMaxRetryCount){
				retryCount++;
				LOGGER.error("Retrying cayan update boardcard- "+vaultToken+"...retry# "+retryCount);
				resp = siteOneCayanPaymentWebService.cayanUpdateBoardedCard(updateBoardedCard);
			}

			if (null != resp && (null != resp.getUpdateBoardedCardResult().getVaultToken()))
			{
				return "SUCCESS";
			}
		}
		return "FAILURE";
	}

	@Override
	public AuthorizeResponse cayanAuthorize(final String vaultToken, final CartData cartData)
	{
		AuthorizeResponse authorizeResponse = null;
		Authorize authorize = null;

		if (null != vaultToken && null != cartData && null != cartData.getTotalPriceWithTax())
		{
			final MerchantCredentials merchCredentials = new MerchantCredentials();
			merchCredentials.setMerchantName(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
			merchCredentials.setMerchantSiteId(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
			merchCredentials.setMerchantKey(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));

			final PaymentData paymentData = new PaymentData();
			paymentData.setSource(configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_SOURCE));
			paymentData.setVaultToken(vaultToken);

			final AuthorizationRequest authorizeRequest = new AuthorizationRequest();
			authorizeRequest.setAmount(cartData.getTotalPriceWithTax().getValue().toString());
			authorizeRequest.setForceDuplicate("true");

			authorize = new Authorize();
			authorize.setCredentials(merchCredentials);
			authorize.setPaymentData(paymentData);
			authorize.setRequest(authorizeRequest);
			authorizeResponse = siteOneCayanPaymentWebService.cayanAuthorize(authorize);
			int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
			int retryCount=0;
			while(authorizeResponse==null && retryCount<cayanCallMaxRetryCount){
				retryCount++;
				LOGGER.error("Retrying cayan authorize- "+vaultToken+"...retry# "+retryCount);
				authorizeResponse = siteOneCayanPaymentWebService.cayanAuthorize(authorize);
			}
		}

		return authorizeResponse;
	}
	
	@Override
	public SaleResponse cayanSale(final String vaultToken, final CartData cartData, final String orderCode)
	{
		SaleResponse saleResponse = null;
		Sale sale = null;

		if (null != vaultToken && null != cartData && null != cartData.getTotalPriceWithTax())
		{
			final MerchantCredentials merchCredentials = new MerchantCredentials();
			merchCredentials.setMerchantName(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
			merchCredentials.setMerchantSiteId(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
			merchCredentials.setMerchantKey(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));

			final PaymentData paymentData = new PaymentData();
			paymentData.setSource(configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_SOURCE));
			paymentData.setVaultToken(vaultToken);

			final SaleRequest authorizeRequest = new SaleRequest();
			authorizeRequest.setAmount(cartData.getTotalPriceWithTax().getValue().toString());
			authorizeRequest.setForceDuplicate("true");
			authorizeRequest.setMerchantTransactionId(orderCode);
			authorizeRequest.setPurchaseOrderNumber(orderCode);

			sale = new Sale();
			sale.setCredentials(merchCredentials);
			sale.setPaymentData(paymentData);
			sale.setRequest(authorizeRequest);
			saleResponse = siteOneCayanPaymentWebService.cayanSale(sale);
			int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
			int retryCount=0;
			while(saleResponse==null && retryCount<cayanCallMaxRetryCount){
				retryCount++;
				LOGGER.error("Retrying cayan sale- "+vaultToken+"...retry# "+retryCount);
				saleResponse = siteOneCayanPaymentWebService.cayanSale(sale);
			}
		}

		return saleResponse;
	}
	
	@Override
	public CaptureResponse cayanCapture(final String token, final ConsignmentModel consignment)
	{
		CaptureResponse captureResponse = null;
		Capture capture = null;

		if (null != token && null != consignment && null != consignment.getTotal())
		{
			final MerchantCredentials merchCredentials = new MerchantCredentials();
			merchCredentials.setMerchantName(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
			merchCredentials.setMerchantSiteId(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
			merchCredentials.setMerchantKey(
					configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));

			final CaptureRequest captureRequest = new CaptureRequest();
			captureRequest.setToken(token);
			captureRequest.setAmount(consignment.getTotal());
			captureRequest.setMerchantTransactionId(consignment.getCode());
			captureRequest.setInvoiceNumber("001");

			capture = new Capture();
			capture.setCredentials(merchCredentials);
			capture.setRequest(captureRequest);
			
			captureResponse = siteOneCayanPaymentWebService.cayanCapture(capture);
		
			final int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
			int retryCount = 0;
			while (captureResponse == null && retryCount < cayanCallMaxRetryCount)
			{
				retryCount++;
				LOGGER.error("Retrying cayan capture- " + token + "...retry# " + retryCount);
				captureResponse = siteOneCayanPaymentWebService.cayanCapture(capture);
			}
		}

		return captureResponse;
	}
}
