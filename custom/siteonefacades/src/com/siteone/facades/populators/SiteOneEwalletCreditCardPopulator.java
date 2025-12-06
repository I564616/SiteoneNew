/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.HashSet;
import java.util.Set;

import com.siteone.core.model.SiteoneEwalletCreditCardModel;


/**
 * @author pelango
 *
 *
 */
public class SiteOneEwalletCreditCardPopulator implements Populator<SiteoneEwalletCreditCardModel, SiteOneEwalletData>
{

	@Override
	public void populate(final SiteoneEwalletCreditCardModel source, final SiteOneEwalletData target) throws ConversionException
	{
		target.setCreditCardType(source.getCreditCardType());
		target.setNickName(source.getNickName());
		target.setCardExpirationDate(source.getExpDate());
		target.setNameOnCard(source.getNameOnCard());
		target.setZipCode(source.getCreditCardZip());
		target.setLast4CreditcardDigits(source.getLast4Digits());
		target.setCreditCardType(source.getCreditCardType());
		target.setValutToken(source.getVaultToken());
		target.setActive(source.getActive());
		target.setCreditcardId(String.valueOf(source.getCusttreeNodeCreditCardId()));
		target.setStreetAddress(source.getStreetAddress());
		final Set<B2BUnitModel> b2bUnitLiost = source.getUnitUID();
		b2bUnitLiost.forEach(b2bUnit -> {
			target.setUnitName(b2bUnit.getName());
		});

		final Set<B2BCustomerModel> b2bCustomerList = source.getCustomerUID();
		final Set<String> customerUid = new HashSet<>();
		b2bCustomerList.forEach(b2bCustomer -> {
			customerUid.add(b2bCustomer.getUid());
		});
		target.setCustomerUid(customerUid);

	}

}