/**
 *
 */
package com.siteone.facade.payment.cayan.impl;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.facade.payment.cayan.SiteOneCayanTransportFacade;
import com.siteone.facades.constants.SiteoneFacadesConstants;
import com.siteone.integration.services.cayanclient.SiteOneCayanTransportWebService;
import com.siteone.integration.webserviceclient.cayantransportclient.CreateTransaction;
import com.siteone.integration.webserviceclient.cayantransportclient.EntryMode;
import com.siteone.integration.webserviceclient.cayantransportclient.TransportRequest;


/**
 * @author pelango
 *
 */
public class DefaultSiteOneCayanTransportFacade implements SiteOneCayanTransportFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCayanTransportFacade.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "siteOneCayanTransportWebService")
	private SiteOneCayanTransportWebService siteoneCayanTransportWebservice;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Override
	public String getBoardCardTransportKey(boolean isCheckout)
	{
		String transportkey = null;
		final CreateTransaction createTransaction = new CreateTransaction();
		final TransportRequest transportRequest = new TransportRequest();
		createTransaction.setMerchantKey(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));
		createTransaction.setMerchantName(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
		createTransaction.setMerchantSiteId(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
		transportRequest.setTransactionType(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_BOARDCARD);
		transportRequest.setEntryMode(EntryMode.KEYED);
		transportRequest.setDba(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_DBA);
		transportRequest.setClerkId(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_CLERKID);
		transportRequest.setOrderNumber("0");
		String redirectMethod = "";
		if(isCheckout) {
			redirectMethod = configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_AUTHORIZE_REDIRECT_URL);
		}else {
			redirectMethod = configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_REDIRECT_URL);
		}
		final String redirectUrl = configurationService.getConfiguration().getString(SiteoneFacadesConstants.WEBSITE_SITEONE_SERVER)
				+ "/" + getCommonI18NService().getCurrentLanguage().getIsocode()
				+ redirectMethod;
		transportRequest.setRedirectLocation(redirectUrl);
		transportRequest.setSoftwareName(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_SOFTWARE_NAME);
		transportRequest.setSoftwareVersion(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_SOFTWARE_VER);
		transportRequest.setLogoLocation(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_REDIRECT_LOGO));

		createTransaction.setRequest(transportRequest);

		transportkey = siteoneCayanTransportWebservice.getTransportKey(createTransaction);

		final int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
		int retryCount = 0;
		while (transportkey == null && retryCount < cayanCallMaxRetryCount)
		{
			retryCount++;
			LOG.error("Retrying cayan get transport key for boardcard...retry# " + retryCount);
			transportkey = siteoneCayanTransportWebservice.getTransportKey(createTransaction);
		}

		return transportkey;
	}

	@Override
	public String getBoardCardTransportKeyForMobile()
	{
		String transportkey = null;
		final CreateTransaction createTransaction = new CreateTransaction();
		final TransportRequest transportRequest = new TransportRequest();
		createTransaction.setMerchantKey(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));
		createTransaction.setMerchantName(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
		createTransaction.setMerchantSiteId(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
		transportRequest.setTransactionType(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_BOARDCARD);
		transportRequest.setEntryMode(EntryMode.KEYED);
		transportRequest.setDba(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_DBA);
		transportRequest.setClerkId(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_CLERKID);
		transportRequest.setOrderNumber("0");

		final String redirectUrl = configurationService.getConfiguration()
				.getString(SiteoneFacadesConstants.WEBSITE_SITEONE_AKAMAI_SERVER)
				+ configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_MOBILE_REDIRECT_URL);

		LOG.info("MA Transport key redirection " + redirectUrl);
		transportRequest.setRedirectLocation(redirectUrl);
		transportRequest.setSoftwareName(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_SOFTWARE_NAME);
		transportRequest.setSoftwareVersion(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_SOFTWARE_VER);
		transportRequest.setLogoLocation(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_REDIRECT_LOGO));

		createTransaction.setRequest(transportRequest);

		transportkey = siteoneCayanTransportWebservice.getTransportKey(createTransaction);

		final int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
		int retryCount = 0;
		while (transportkey == null && retryCount < cayanCallMaxRetryCount)
		{
			retryCount++;
			LOG.error("Retrying cayan get transport key for boardcard...retry# " + retryCount);
			transportkey = siteoneCayanTransportWebservice.getTransportKey(createTransaction);
		}

		return transportkey;
	}

	@Override
	public String getAuthorizeTransportKey(final CartData cartData)
	{
		String transportkey = null;
		final CreateTransaction createTransaction = new CreateTransaction();
		final TransportRequest transportRequest = new TransportRequest();
		createTransaction.setMerchantKey(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_KEY));
		createTransaction.setMerchantName(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_NAME));
		createTransaction.setMerchantSiteId(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_PAYMENT_MERCHANT_SITE_ID));
		transportRequest.setTransactionType(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_PREAUTH);
		transportRequest.setEntryMode(EntryMode.KEYED);
		transportRequest.setDba(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_DBA);
		transportRequest.setClerkId(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_CLERKID);
		transportRequest.setOrderNumber("0");
		transportRequest.setTransactionId(cartData.getCode());	
		transportRequest.setAmount(cartData.getTotalPriceWithTax().getValue());
		final String redirectUrl = configurationService.getConfiguration().getString(SiteoneFacadesConstants.WEBSITE_SITEONE_SERVER)
				+ "/" + getCommonI18NService().getCurrentLanguage().getIsocode()
				+ configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_AUTHORIZE_LINK_TO_PAY_REDIRECT_URL);
		transportRequest.setRedirectLocation(redirectUrl);
		transportRequest.setSoftwareName(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_SOFTWARE_NAME);
		transportRequest.setSoftwareVersion(SiteoneFacadesConstants.CAYAN_TRANSPORT_KEY_SOFTWARE_VER);
		transportRequest.setLogoLocation(
				configurationService.getConfiguration().getString(SiteoneFacadesConstants.CAYAN_TRANSPORT_REDIRECT_LOGO));
		transportRequest.setForceDuplicate(true);
		createTransaction.setRequest(transportRequest);

		transportkey = siteoneCayanTransportWebservice.getTransportKey(createTransaction);

		final int cayanCallMaxRetryCount = Config.getInt("hybris.cayan.maxRetryCount", 1);
		int retryCount = 0;
		while (transportkey == null && retryCount < cayanCallMaxRetryCount)
		{
			retryCount++;
			LOG.error("Retrying cayan get transport key for authorize...retry# " + retryCount);
			transportkey = siteoneCayanTransportWebservice.getTransportKey(createTransaction);
		}

		return transportkey;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}
}
