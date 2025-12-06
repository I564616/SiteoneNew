package com.siteone.integration.converters.populators;

import java.util.UUID;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.ewallet.data.SiteOneWsEwalletRequestData;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

public class SiteOneWsEwalletPopulator implements Populator<SiteoneEwalletCreditCardModel, SiteOneWsEwalletRequestData>{
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Override
	public void populate(SiteoneEwalletCreditCardModel source,
			SiteOneWsEwalletRequestData target) throws ConversionException {
				
		target.setCorrelationId(UUID.randomUUID().toString());
		
		target.setCardExpirationDate(source.getExpDate());
		
		if(source.getCusttreeNodeCreditCardId()!=null)
		{
			target.setCustTreeNodeCreditCardId(source.getCusttreeNodeCreditCardId());
		}
		
		if(SiteoneintegrationConstants.cardTypeShortName.get(source.getCreditCardType()) !=null) {
			target.setCreditCardType(SiteoneintegrationConstants.cardTypeShortName.get(source.getCreditCardType()));
		}else {
			target.setCreditCardType(source.getCreditCardType());
		}
		
		final B2BCustomerModel b2bCustomer = (B2BCustomerModel) userService.getCurrentUser();
		
		target.setCustTreeNodeId(b2bCustomer.getDefaultB2BUnit().getGuid());
		
		target.setStreetAddress(source.getStreetAddress());
		
		target.setExternalSystemId("2");
		
		target.setLast4CreditCardDigits(source.getLast4Digits());
		
		target.setNameOnCard(source.getNameOnCard());
		
		target.setNickName(source.getNickName());
		
		target.setVaultToken(source.getVaultToken());
		
		if (null != source.getCreditCardZip()) {
			target.setZipCode(source.getCreditCardZip());
		} else {
			target.setZipCode("");
		}
	}
	
	public UserService getUserService() {
		return userService;
		}

		public void setUserService(UserService userService) {
		this.userService = userService;
		}

}