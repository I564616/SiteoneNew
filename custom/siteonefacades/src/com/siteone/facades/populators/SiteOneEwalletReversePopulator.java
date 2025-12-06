/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.core.model.SiteoneEwalletCreditCardModel;


/**
 * @author RSUBATHR
 *
 */
public class SiteOneEwalletReversePopulator implements Populator<SiteOneEwalletData, SiteoneEwalletCreditCardModel>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final SiteOneEwalletData source, final SiteoneEwalletCreditCardModel target) throws ConversionException
	{

		target.setCreditCardType(source.getCreditCardType());
		target.setNickName(source.getNickName());
		target.setExpDate(source.getCardExpirationDate());
		target.setNameOnCard(source.getNameOnCard());
		target.setCreditCardZip(source.getZipCode());
		target.setLast4Digits(source.getLast4CreditcardDigits());
		target.setVaultToken(source.getValutToken());
		target.setCusttreeNodeCreditCardId(source.getCreditcardId());
		target.setStreetAddress(source.getStreetAddress());
		target.setActive(source.getActive());
	}

}
