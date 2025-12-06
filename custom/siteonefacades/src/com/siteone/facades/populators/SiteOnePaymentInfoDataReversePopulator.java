/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.apache.log4j.Logger;


/**
 * @author pelango
 *
 */
public class SiteOnePaymentInfoDataReversePopulator implements Populator<SiteOnePaymentInfoData, SiteoneCreditCardPaymentInfoModel>
{

	private static final Logger LOG = Logger.getLogger(SiteOnePaymentInfoDataReversePopulator.class);

	@Override
	public void populate(final SiteOnePaymentInfoData source, final SiteoneCreditCardPaymentInfoModel target)
			throws ConversionException
	{
		target.setAuthCode(source.getAuthCode());
		target.setLast4Digits(source.getCardNumber());
		target.setCreditCardType(source.getCardType());
		target.setCreditCardZip(source.getZipCode());
		target.setExpDate(source.getExpDate());
		target.setSaveCard(source.getSaveCard());
		target.setTransactionStatus(source.getStatus());
		target.setTransportKey(source.getTransportKey());
		target.setVaultToken(source.getVaultToken());
		target.setTransactionReferenceNumber(source.getTransRefNumber());
		target.setApplicationLabel(source.getApplicationLabel());
		target.setPaymentType("3");
		target.setAuthToken(source.getToken());
		target.setNickName(source.getNickName());
		target.setAvs(source.getAvs());
		target.setCvv(source.getCvv());
		target.setCusttreeNodeCreditId(source.getCusttreeNodeCreditId());
		target.setAmountCharged(source.getAmountCharged());
		target.setIseWalletCard(source.getIseWalletCard());
	}

}
