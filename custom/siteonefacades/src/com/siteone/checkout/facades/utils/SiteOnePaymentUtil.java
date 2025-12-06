/**
 *
 */
package com.siteone.checkout.facades.utils;

import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;

import jakarta.annotation.Resource;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;
import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.integration.webserviceclient.cayanclient.CaptureResponse;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;
import com.siteone.integration.webserviceclient.cayanclient.TransactionResponse45;


/**
 * @author pelango
 *
 */
public class SiteOnePaymentUtil
{
	private static final Logger LOGGER = Logger.getLogger(SiteOnePaymentUtil.class);

	@Resource(name = "siteOneCayanPaymentFacade")
	private SiteOneCayanPaymentFacade siteOneCayanPaymentFacade;

	@Resource(name = "creditCardNameMapping")
	private CreditCardNameMapping creditCardNameMapping;

	@Resource(name = "defaultSiteOneEwalletDao")
	private DefaultSiteOneEwalletDao defaultSiteOneEwalletDao;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneEwalletReverseConverter")
	private Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;


	public SiteOnePaymentInfoData processCayanSalePayment(final CartData cartData, final String orderCode)
	{
		final String vaultToken = getSessionService().getAttribute("vaultToken");
		final Gson gson = new Gson();
		final SaleResponse saleResponse = siteOneCayanPaymentFacade.cayanSale(vaultToken, cartData, orderCode);
		SiteOnePaymentInfoData siteOnePaymentData;
		if (null != saleResponse && null != saleResponse.getSaleResult()
				&& saleResponse.getSaleResult().getApprovalStatus().equals("APPROVED"))
		{
			LOGGER.info("Cayan sale API : " + saleResponse.getSaleResult().getApprovalStatus());
			final TransactionResponse45 saleResult = saleResponse.getSaleResult();
			saleResult.setApprovalStatus("SALE");
			siteOnePaymentData = formSiteOnePaymentDataFromAuthResult(saleResult, vaultToken);
			siteOnePaymentData.setAmountCharged(cartData.getTotalPriceWithTax().getValue().doubleValue());
		}
		else
		{
			LOGGER.error("Cayan sale API : " + gson.toJson(saleResponse));
			return null;
		}

		return siteOnePaymentData;
	}

	public SiteoneCreditCardPaymentInfoModel processCayanCapturePayment(final ConsignmentModel consignment,
			final SiteoneCreditCardPaymentInfoModel paymentInfo)
	{
		final Gson gson = new Gson();
		final CaptureResponse captureResponse = siteOneCayanPaymentFacade.cayanCapture(paymentInfo.getAuthToken(), consignment);
		SiteoneCreditCardPaymentInfoModel siteOnePaymentData;
		if (null != captureResponse && null != captureResponse.getCaptureResult()
				&& captureResponse.getCaptureResult().getApprovalStatus().equals("APPROVED"))
		{
			LOGGER.info("Cayan capture API : " + captureResponse.getCaptureResult().getApprovalStatus());
			final TransactionResponse45 captureResult = captureResponse.getCaptureResult();
			captureResult.setApprovalStatus("SALE");
			siteOnePaymentData = formSiteOnePaymentDataFromCaptureResult(captureResult, paymentInfo);
			siteOnePaymentData.setAmountCharged(Double.valueOf(consignment.getTotal()));
		}
		else
		{
			LOGGER.error("Cayan capture API : " + gson.toJson(captureResponse));
			return null;
		}

		return siteOnePaymentData;
	}

	public SiteoneCreditCardPaymentInfoModel formSiteOnePaymentDataFromCaptureResult(final TransactionResponse45 captureResult,
			final SiteoneCreditCardPaymentInfoModel paymentInfo)
	{
		final SiteoneCreditCardPaymentInfoModel capturePaymentInfo = new SiteoneCreditCardPaymentInfoModel();
		capturePaymentInfo.setAuthCode(captureResult.getAuthorizationCode());
		capturePaymentInfo.setLast4Digits(paymentInfo.getLast4Digits());
		capturePaymentInfo.setCreditCardType(paymentInfo.getCreditCardType());
		capturePaymentInfo.setCreditCardZip(paymentInfo.getCreditCardZip());
		capturePaymentInfo.setExpDate(paymentInfo.getExpDate());
		capturePaymentInfo.setSaveCard(paymentInfo.getSaveCard());
		capturePaymentInfo.setTransactionStatus("SALE");
		capturePaymentInfo.setTransportKey(captureResult.getRfmiq());
		capturePaymentInfo.setVaultToken(paymentInfo.getVaultToken());
		capturePaymentInfo.setTransactionReferenceNumber(getAlphaNumericString());
		capturePaymentInfo.setApplicationLabel(paymentInfo.getApplicationLabel());
		capturePaymentInfo.setPaymentType("3");
		capturePaymentInfo.setAuthToken(captureResult.getToken());
		capturePaymentInfo.setNickName(paymentInfo.getNickName());
		capturePaymentInfo.setAvs(captureResult.getAvsResponse());
		capturePaymentInfo.setCvv(captureResult.getCvResponse());
		capturePaymentInfo.setCusttreeNodeCreditId(paymentInfo.getCusttreeNodeCreditId());
		capturePaymentInfo.setAmountCharged(Double.valueOf(captureResult.getAmount()));
		capturePaymentInfo.setIseWalletCard(paymentInfo.getIseWalletCard());
		capturePaymentInfo.setPaymentTransaction("Capture");
		return capturePaymentInfo;
	}

	public SiteOnePaymentInfoData formSiteOnePaymentDataFromAuthResult(final TransactionResponse45 authResult,
			final String vaultToken)
	{
		final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
		SiteoneEwalletCreditCardModel ewalletCardData = new SiteoneEwalletCreditCardModel();
		final SiteOneEwalletData ewalletData = sessionService.getAttribute("ewalletData");
		if (ewalletData != null)
		{
			siteOneEwalletReverseConverter.convert(ewalletData, ewalletCardData);
			final Boolean isEwalletCard = sessionService.getAttribute("isEwalletCard");
			if (isEwalletCard != null)
			{
				if (isEwalletCard.booleanValue())
				{
					siteOnePaymentData.setIseWalletCard(isEwalletCard);
				}
				else
				{
					siteOnePaymentData.setIseWalletCard(Boolean.FALSE);
				}
			}
		}
		else
		{
			ewalletCardData = defaultSiteOneEwalletDao.getCreditCardDetails(vaultToken);
			siteOnePaymentData.setIseWalletCard(true);
		}
		siteOnePaymentData.setAuthCode(authResult.getAuthorizationCode());
		final String cardNumber = authResult.getCardNumber();
		siteOnePaymentData.setCardNumber(cardNumber.substring(cardNumber.length() - 4));
		siteOnePaymentData.setCardType(creditCardNameMapping.getCardTypeName().get(authResult.getCardType()));
		siteOnePaymentData.setAvs(authResult.getAvsResponse());
		siteOnePaymentData.setValidationKey(authResult.getExtraData());
		siteOnePaymentData.setZipCode(ewalletCardData.getCreditCardZip());
		siteOnePaymentData.setStatus(authResult.getApprovalStatus());
		siteOnePaymentData.setToken(authResult.getToken());
		siteOnePaymentData.setExpDate(ewalletCardData.getExpDate());
		siteOnePaymentData.setCvv(authResult.getCvResponse());
		siteOnePaymentData.setEntryMode(authResult.getReaderEntryMode());
		siteOnePaymentData.setAddress(ewalletCardData.getStreetAddress());
		siteOnePaymentData.setVaultToken(vaultToken);
		siteOnePaymentData.setTransRefNumber(getAlphaNumericString());
		siteOnePaymentData.setTransportKey(authResult.getRfmiq());
		siteOnePaymentData.setApplicationLabel(creditCardNameMapping.getCardTypeName().get(authResult.getCardType()));
		siteOnePaymentData.setNickName(ewalletCardData.getNickName());
		siteOnePaymentData.setSaveCard(false);
		siteOnePaymentData.setNameOnCard(authResult.getCardholder());
		siteOnePaymentData.setCusttreeNodeCreditId(ewalletCardData.getCusttreeNodeCreditCardId());
		return siteOnePaymentData;
	}

	public String getAlphaNumericString()
	{
		// chose a Character random from this String
		final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

		// create StringBuffer size of AlphaNumericString
		final StringBuilder sb = new StringBuilder(8);

		for (int i = 0; i < 8; i++)
		{

			// generate a random number between
			// 0 to AlphaNumericString variable length
			final int index = (int) (AlphaNumericString.length() * ThreadLocalRandom.current().nextDouble());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	/**
	 * @return the siteOneCayanPaymentFacade
	 */
	public SiteOneCayanPaymentFacade getSiteOneCayanPaymentFacade()
	{
		return siteOneCayanPaymentFacade;
	}

	/**
	 * @param siteOneCayanPaymentFacade
	 *           the siteOneCayanPaymentFacade to set
	 */
	public void setSiteOneCayanPaymentFacade(final SiteOneCayanPaymentFacade siteOneCayanPaymentFacade)
	{
		this.siteOneCayanPaymentFacade = siteOneCayanPaymentFacade;
	}

	/**
	 * @return the creditCardNameMapping
	 */
	public CreditCardNameMapping getCreditCardNameMapping()
	{
		return creditCardNameMapping;
	}

	/**
	 * @param creditCardNameMapping
	 *           the creditCardNameMapping to set
	 */
	public void setCreditCardNameMapping(final CreditCardNameMapping creditCardNameMapping)
	{
		this.creditCardNameMapping = creditCardNameMapping;
	}

	/**
	 * @return the defaultSiteOneEwalletDao
	 */
	public DefaultSiteOneEwalletDao getDefaultSiteOneEwalletDao()
	{
		return defaultSiteOneEwalletDao;
	}

	/**
	 * @param defaultSiteOneEwalletDao
	 *           the defaultSiteOneEwalletDao to set
	 */
	public void setDefaultSiteOneEwalletDao(final DefaultSiteOneEwalletDao defaultSiteOneEwalletDao)
	{
		this.defaultSiteOneEwalletDao = defaultSiteOneEwalletDao;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	/**
	 * @return the siteOneEwalletReverseConverter
	 */
	public Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> getSiteOneEwalletReverseConverter()
	{
		return siteOneEwalletReverseConverter;
	}

	/**
	 * @param siteOneEwalletReverseConverter
	 *           the siteOneEwalletReverseConverter to set
	 */
	public void setSiteOneEwalletReverseConverter(
			final Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter)
	{
		this.siteOneEwalletReverseConverter = siteOneEwalletReverseConverter;
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService()
	{
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService
	 *           the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(final SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService)
	{
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

}
