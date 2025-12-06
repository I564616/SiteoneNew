/**
 *
 */
package com.siteone.storefront.util;

import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;

import jakarta.annotation.Resource;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.ewallet.dao.impl.DefaultSiteOneEwalletDao;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;
import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.integration.webserviceclient.cayanclient.AuthorizeResponse;
import com.siteone.integration.webserviceclient.cayanclient.BoardCardResponse;
import com.siteone.integration.webserviceclient.cayanclient.SaleResponse;
import com.siteone.integration.webserviceclient.cayanclient.TransactionResponse45;
import com.siteone.storefront.forms.SiteOnePaymentForm;


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

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneEwalletReverseConverter")
	private Converter<SiteOneEwalletData, SiteoneEwalletCreditCardModel> siteOneEwalletReverseConverter;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	public SiteOnePaymentInfoData processPaymentInfoData(final SiteOnePaymentForm siteOnePaymentForm, final String nickName,
			final String saveCard) throws Exception
	{
		final Gson gson = new Gson();
		BoardCardResponse boardCardResponse = null;
		String vaultToken = null;
		final String transportToken = siteOnePaymentForm.getToken();
		boardCardResponse = siteOneCayanPaymentFacade.boardCard(transportToken);
		if (null != boardCardResponse && null != boardCardResponse.getBoardCardResult())
		{
			vaultToken = boardCardResponse.getBoardCardResult().getVaultToken();
		}
		else
		{
			LOGGER.error("Cayan boradcard API : " + gson.toJson(boardCardResponse));
			throw new Exception("Cayan boradcard failed");
		}
		LOGGER.info("Board Card Vault Token : " + vaultToken);
		return formSiteOnePaymentInfoData(siteOnePaymentForm, vaultToken, nickName, saveCard);
	}

	public SiteOnePaymentInfoData formSiteOnePaymentInfoData(final SiteOnePaymentForm siteOnePaymentForm, final String vaultToken,
			String nickName, final String saveCard)
	{
		final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
		siteOnePaymentData.setAuthCode(siteOnePaymentForm.getAuthCode());
		final String cardNumber = siteOnePaymentForm.getCardNumber();
		siteOnePaymentData.setCardNumber(cardNumber.substring(cardNumber.length() - 4));
		siteOnePaymentData.setCardType(creditCardNameMapping.getCardTypeName().get(siteOnePaymentForm.getCardType()));
		siteOnePaymentData.setAvs(siteOnePaymentForm.getAVS());
		siteOnePaymentData.setValidationKey(siteOnePaymentForm.getValidationKey());
		siteOnePaymentData.setZipCode(siteOnePaymentForm.getZipCode());
		siteOnePaymentData.setStatus(siteOnePaymentForm.getStatus());
		siteOnePaymentData.setToken(siteOnePaymentForm.getToken());
		siteOnePaymentData.setExpDate(siteOnePaymentForm.getExpDate());
		siteOnePaymentData.setRefID(siteOnePaymentForm.getRefID());
		siteOnePaymentData.setCvv(siteOnePaymentForm.getCVV());
		siteOnePaymentData.setEntryMode(siteOnePaymentForm.getEntryMode());
		siteOnePaymentData.setAddress(siteOnePaymentForm.getAddress());
		siteOnePaymentData.setVaultToken(vaultToken);
		siteOnePaymentData.setTransRefNumber(getAlphaNumericString());
		siteOnePaymentData.setTransportKey(siteOnePaymentForm.getRefID());
		siteOnePaymentData.setApplicationLabel(creditCardNameMapping.getCardTypeName().get(siteOnePaymentForm.getCardType()));
		nickName = StringUtils.isNotEmpty(nickName) ? nickName
				: siteOnePaymentData.getApplicationLabel() + "-" + siteOnePaymentData.getCardNumber();
		siteOnePaymentData.setNickName(nickName);
		siteOnePaymentData.setSaveCard(StringUtils.isNotEmpty(saveCard) ? Boolean.valueOf(saveCard) : Boolean.valueOf(false));
		siteOnePaymentData.setNameOnCard(siteOnePaymentForm.getCardholder());
		siteOnePaymentData.setIseWalletCard(siteOnePaymentData.getSaveCard());
		return siteOnePaymentData;
	}

	public SiteOnePaymentInfoData processPaymentInfoData(final String vaultToken, final CartData cartData)
	{
		final Gson gson = new Gson();
		final AuthorizeResponse authResponse = siteOneCayanPaymentFacade.cayanAuthorize(vaultToken, cartData);
		SiteOnePaymentInfoData siteOnePaymentData=null;
		if (null != authResponse && null != authResponse.getAuthorizeResult()
				&& authResponse.getAuthorizeResult().getApprovalStatus().equals("APPROVED"))
		{
			LOGGER.info("Cayan authorize API : " + authResponse.getAuthorizeResult().getApprovalStatus());
			final TransactionResponse45 authorizeResult = authResponse.getAuthorizeResult();
			siteOnePaymentData = formSiteOnePaymentDataFromAuthResult(authorizeResult, vaultToken);
			siteOnePaymentData.setAmountCharged(cartData.getTotalPriceWithTax().getValue().doubleValue());
		}
		else
		{
			LOGGER.error("Cayan authorize API : " + gson.toJson(authResponse));
			return null;
		}

		return siteOnePaymentData;
	}

	public SiteOnePaymentInfoData formSiteOnePaymentDataFromAuthResult(final TransactionResponse45 authResult,
			final String vaultToken)
	{
		final SiteOnePaymentInfoData siteOnePaymentData = new SiteOnePaymentInfoData();
		final SiteoneEwalletCreditCardModel ewalletCardData = new SiteoneEwalletCreditCardModel();
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
