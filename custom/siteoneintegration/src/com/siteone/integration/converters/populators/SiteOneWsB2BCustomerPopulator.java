package com.siteone.integration.converters.populators;

import java.util.Locale;
import java.util.UUID;

import jakarta.annotation.Resource;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.data.SiteOneWsB2BCustomerRequestData;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;

/**
 * @author 1230514
 * Request Populator
 *
 */
public class SiteOneWsB2BCustomerPopulator implements Populator<B2BCustomerModel, SiteOneWsB2BCustomerRequestData> {

	@Resource
	private I18NService i18nService;
	
	@Override
	public void populate(final B2BCustomerModel source, final SiteOneWsB2BCustomerRequestData target)
			throws ConversionException {
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		
		if (source.getLanguagePreference() != null)
		{
		target.setLangPreference(source.getLanguagePreference().getName(Locale.ENGLISH));
		}
		else
		{
			target.setLangPreference("English");
		}
		
        if( source.getPayBillOnline()== null ) {
            target.setPayBillOnline(false);
        }
        else {
            target.setPayBillOnline(source.getPayBillOnline());
        }
        
         if(source.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup")) && (null != source.getIsAccountOwner() && !source.getIsAccountOwner())){
        	target.setIsOnlineAdmin(true);
        }else{
        	target.setIsOnlineAdmin(false);
        }

         if(null!=source.getTitle()){
			 	target.setContactTitleID(Integer.valueOf(source.getTitle().getCode()));
			 	}
			 	else
			 	{
			 		target.setContactTitleID(1);
			 	}
		
		
		if (null != source.getDefaultB2BUnit()) {
			target.setAccountNumber(source.getDefaultB2BUnit().getGuid());
			
			if (null != source.getDefaultB2BUnit().getDivision() && SiteoneintegrationConstants.DIVISION_US
					 				.equalsIgnoreCase(source.getDefaultB2BUnit().getDivision().getUid())) {
					 			target.setDivisionID(1);
					 		} else if (null != source.getDefaultB2BUnit().getDivision() && SiteoneintegrationConstants.DIVISION_CA
					 				.equalsIgnoreCase(source.getDefaultB2BUnit().getDivision().getUid())) {
					 			target.setDivisionID(2);
					 		}

		}

		 target.setIsPrimaryContact(false);
		
		if (null != source.getContactInfos()) {
			
			source.getContactInfos().forEach(contactInfo->{
				PhoneContactInfoModel phoneContactInfoModel = (PhoneContactInfoModel) contactInfo;
				
				if(null != phoneContactInfoModel.getPhoneNumber()){
					
					String phoneNumber = phoneContactInfoModel.getPhoneNumber();
					
					//Removing bracket,hyphen and space from phoneNumber
					phoneNumber=phoneNumber.replaceAll("[\\s()-]","");
					
					target.setPhoneNumber(phoneNumber);
					target.setPhoneId(phoneContactInfoModel.getPhoneId());
				}
				
				
			});
		}
		
		//Setting guid/contactId for update customer
		if(null != source.getGuid()) {
			target.setContactId(source.getGuid());
		}
		
		
		//Settng unique Id for each request.
		target.setCorrelationId(UUID.randomUUID().toString());
		
		target.setIsActive(source.getActive());

	}

}
