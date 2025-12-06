/**
 *
 */
package com.siteone.utils;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;

import java.util.concurrent.ThreadLocalRandom;
import de.hybris.platform.servicelayer.session.SessionService;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;
import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.integration.webserviceclient.cayanclient.AuthorizeResponse;
import com.siteone.integration.webserviceclient.cayanclient.BoardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.TransactionResponse45;

/**
 * @author pelango
 *
 */
public class SiteOnePaymentInfoUtil
{
	private static final Logger LOGGER = Logger.getLogger(SiteOnePaymentInfoUtil.class);

	@Resource(name = "siteOneCayanPaymentFacade")
	private SiteOneCayanPaymentFacade siteOneCayanPaymentFacade;

	@Resource(name = "creditCardNameMapping")
	private CreditCardNameMapping creditCardNameMapping;

	@Resource(name = "defaultSiteOneEwalletDao")
	private DefaultSiteOneEwalletDao defaultSiteOneEwalletDao;
	
	@Resource(name = "siteOneEwalletReverseConverter")
	private Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "sessionService")
	private SessionService sessionService;


	public SiteOnePaymentInfoData processPaymentInfoData(final String vaultToken, final CartData cartData,
			final SiteOneEwalletData ewalletData)
	{
		final Gson gson = new Gson();
		final AuthorizeResponse authResponse = siteOneCayanPaymentFacade.cayanAuthorize(vaultToken, cartData);
		SiteOnePaymentInfoData siteOnePaymentData = null;
		if (null != authResponse && null != authResponse.getAuthorizeResult()
				&& authResponse.getAuthorizeResult().getApprovalStatus().equals("APPROVED"))
		{
			LOGGER.info("Cayan authorize API : " + authResponse.getAuthorizeResult().getApprovalStatus());
			final TransactionResponse45 authorizeResult = authResponse.getAuthorizeResult();
			siteOnePaymentData = formSiteOnePaymentDataFromAuthResult(authorizeResult, vaultToken, ewalletData);
			siteOnePaymentData.setAmountCharged(cartData.getTotalPriceWithTax().getValue().doubleValue());
		}
		else
		{
			LOGGER.error("Cayan authorize API : " + gson.toJson(authResponse));
			return null;
		}

		return siteOnePaymentData;
	}

	public SiteOnePaymentInfoData processCayanSalePayment(final String vaultToken, final CartData cartData,
			final SiteOneEwalletData ewalletData)
	{
		final Gson gson = new Gson();
		final SaleResponse saleResponse = siteOneCayanPaymentFacade.cayanSale(vaultToken, cartData, null);
		SiteOnePaymentInfoData siteOnePaymentData;
		if (null != saleResponse && null != saleResponse.getSaleResult()
				&& saleResponse.getSaleResult().getApprovalStatus().equals("APPROVED"))
		{
			LOGGER.info("Cayan sale API : " + saleResponse.getSaleResult().getApprovalStatus());
			final TransactionResponse45 saleResult = saleResponse.getSaleResult();
			saleResult.setApprovalStatus("SALE");
			siteOnePaymentData = formSiteOnePaymentDataFromAuthResult(saleResult, vaultToken, ewalletData);
			siteOnePaymentData.setAmountCharged(cartData.getTotalPriceWithTax().getValue().doubleValue());
		}
		else
		{
			LOGGER.error("Cayan sale API : " + gson.toJson(saleResponse));
			return null;
		}

		return siteOnePaymentData;
	}
	
	public SiteOnePaymentInfoData formSiteOnePaymentDataFromAuthResult(final TransactionResponse45 authResult,
			final String vaultToken, final SiteOneEwalletData ewalletData)
	{
		final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
		SiteoneEwalletCreditCardModel ewalletCardData = new SiteoneEwalletCreditCardModel();
		if(ewalletData!=null) {
			siteOneEwalletReverseConverter.convert(ewalletData, ewalletCardData);
			siteOnePaymentData.setIseWalletCard(false);
		}else {
			ewalletCardData = defaultSiteOneEwalletDao.getCreditCardDetails(vaultToken);
			siteOnePaymentData.setIseWalletCard(true);
		}
		siteOnePaymentData.setPaymentType("3");
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
	public SiteOneCayanPaymentFacade getSiteOneCayanPaymentFacade() {
		return siteOneCayanPaymentFacade;
	}

	/**
	 * @param siteOneCayanPaymentFacade the siteOneCayanPaymentFacade to set
	 */
	public void setSiteOneCayanPaymentFacade(SiteOneCayanPaymentFacade siteOneCayanPaymentFacade) {
		this.siteOneCayanPaymentFacade = siteOneCayanPaymentFacade;
	}

	/**
	 * @return the creditCardNameMapping
	 */
	public CreditCardNameMapping getCreditCardNameMapping() {
		return creditCardNameMapping;
	}

	/**
	 * @param creditCardNameMapping the creditCardNameMapping to set
	 */
	public void setCreditCardNameMapping(CreditCardNameMapping creditCardNameMapping) {
		this.creditCardNameMapping = creditCardNameMapping;
	}

	/**
	 * @return the defaultSiteOneEwalletDao
	 */
	public DefaultSiteOneEwalletDao getDefaultSiteOneEwalletDao() {
		return defaultSiteOneEwalletDao;
	}

	/**
	 * @param defaultSiteOneEwalletDao the defaultSiteOneEwalletDao to set
	 */
	public void setDefaultSiteOneEwalletDao(DefaultSiteOneEwalletDao defaultSiteOneEwalletDao) {
		this.defaultSiteOneEwalletDao = defaultSiteOneEwalletDao;
	}

	/**
	 * @return the siteOneEwalletReverseConverter
	 */
	public Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> getSiteOneEwalletReverseConverter() {
		return siteOneEwalletReverseConverter;
	}

	/**
	 * @param siteOneEwalletReverseConverter the siteOneEwalletReverseConverter to set
	 */
	public void setSiteOneEwalletReverseConverter(
			Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter) {
		this.siteOneEwalletReverseConverter = siteOneEwalletReverseConverter;
	}

	/**
	 * @return the siteOneFeatureSwitchCacheService
	 */
	public SiteOneFeatureSwitchCacheService getSiteOneFeatureSwitchCacheService() {
		return siteOneFeatureSwitchCacheService;
	}

	/**
	 * @param siteOneFeatureSwitchCacheService the siteOneFeatureSwitchCacheService to set
	 */
	public void setSiteOneFeatureSwitchCacheService(SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService) {
		this.siteOneFeatureSwitchCacheService = siteOneFeatureSwitchCacheService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService() {
		return sessionService;
	}

	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	
}
