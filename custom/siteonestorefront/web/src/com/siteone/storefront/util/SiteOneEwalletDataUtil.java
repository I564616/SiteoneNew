/**
 *
 */
package com.siteone.storefront.util;

import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.siteone.facades.constants.CreditCardNameMapping;
import com.siteone.storefront.forms.CayanBoarcardResponseForm;
import com.siteone.storefront.forms.SiteoneEwalletForm;


/**
 * @author pelango
 *
 */
public class SiteOneEwalletDataUtil
{

	@Resource(name = "creditCardNameMapping")
	private CreditCardNameMapping creditCardNameMapping;

	public SiteOneEwalletData convert(final SiteoneEwalletForm ewalletForm)
	{
		final SiteOneEwalletData ewalletData = new SiteOneEwalletData();
		ewalletData.setCardExpirationDate(ewalletForm.getExpDate());
		ewalletData.setNickName(ewalletForm.getNickName());
		ewalletData.setCreditCardType(ewalletForm.getCreditCardType());
		ewalletData.setOperationType(ewalletForm.getOperationType());
		ewalletData.setLast4CreditcardDigits(ewalletForm.getLast4CreditcardDigits());
		ewalletData.setNameOnCard(ewalletForm.getCardName());
		ewalletData.setCreditcardId(ewalletForm.getCreditCardId());
		return ewalletData;
	}

	public SiteOneEwalletData convert(final CayanBoarcardResponseForm ewalletForm, String nickName)
	{
		final SiteOneEwalletData ewalletData = new SiteOneEwalletData();
		ewalletData.setCardExpirationDate(ewalletForm.getExpDate());
		ewalletData.setCreditCardType(creditCardNameMapping.getCardTypeName().get(ewalletForm.getCardType()));
		final String cardNumber = ewalletForm.getCardNumber();
		ewalletData.setLast4CreditcardDigits(cardNumber.substring(cardNumber.length() - 4));
		ewalletData.setNameOnCard(ewalletForm.getCardholder());
		ewalletData.setValutToken(ewalletForm.getVaultToken());
		ewalletData.setStreetAddress(ewalletForm.getAddress());
		ewalletData.setZipCode(ewalletForm.getZipCode());
		ewalletData.setActive(true);
		nickName = StringUtils.isNotEmpty(nickName) ? nickName + "-" + ewalletData.getLast4CreditcardDigits()
				: ewalletData.getCreditCardType() + "-" + ewalletData.getLast4CreditcardDigits();
		ewalletData.setNickName(nickName);
		return ewalletData;
	}
}
