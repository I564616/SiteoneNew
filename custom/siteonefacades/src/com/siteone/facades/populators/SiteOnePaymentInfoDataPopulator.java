/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author pelango
 *
 */
public class SiteOnePaymentInfoDataPopulator implements Populator<SiteoneCreditCardPaymentInfoModel, SiteOnePaymentInfoData>
{

	@Override
	public void populate(final SiteoneCreditCardPaymentInfoModel source, final SiteOnePaymentInfoData target)
			throws ConversionException
	{
		target.setAuthCode(source.getAuthCode());
		target.setCardNumber(source.getLast4Digits());
		target.setCardType(source.getCreditCardType());
		target.setZipCode(source.getCreditCardZip());
		target.setExpDate(source.getExpDate());
		target.setSaveCard(source.getSaveCard());
		target.setStatus(source.getTransactionStatus());
		target.setTransportKey(source.getTransportKey());
		target.setVaultToken(source.getVaultToken());
		target.setTransRefNumber(source.getTransactionReferenceNumber());
		target.setApplicationLabel(source.getCreditCardType());
		target.setToken(source.getAuthToken());
		target.setNickName(source.getNickName());
		target.setAvs(source.getAvs());
		target.setCvv(source.getCvv());
		target.setCusttreeNodeCreditId(source.getCusttreeNodeCreditId());
		target.setPaymentType(source.getPaymentType());
	}

}
