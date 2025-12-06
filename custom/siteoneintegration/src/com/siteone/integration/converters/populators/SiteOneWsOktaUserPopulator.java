package com.siteone.integration.converters.populators;

import org.apache.commons.lang3.StringUtils;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserRequestData;
import com.siteone.integration.services.okta.data.OktaSetPassword;
import com.siteone.integration.services.okta.data.Profile;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.PhoneContactInfoModel;



public class SiteOneWsOktaUserPopulator implements Populator<B2BCustomerModel, OktaCreateOrUpdateUserRequestData>{

	
	@Override
	public void populate(B2BCustomerModel source, OktaCreateOrUpdateUserRequestData target) {
		// TODO Auto-generated method stub
		Profile profile = new Profile();
		profile.setFirstName(source.getFirstName());
		profile.setLastName(source.getLastName());
		profile.setEmail(source.getEmail());
		profile.setLogin(source.getEmail());
		
		
		if (null != source.getPayBillOnline()) {
			profile.setPayBillOnline(source.getPayBillOnline());
		}
		
		if (null != source.getPartnerProgramPermissions() && source.getPartnerProgramPermissions().equals(Boolean.TRUE)) {
			profile.setPartnersProgram(source.getPartnerProgramPermissions());
		}
		
		if (null != source.getDefaultB2BUnit()) {
			profile.setAccountNumber(StringUtils.substringBeforeLast(source.getDefaultB2BUnit().getUid().trim(), SiteoneintegrationConstants.SEPARATOR_UNDERSCORE));
			
			for (final PrincipalGroupModel principalGroupModel : source.getAllGroups()) {
				if(principalGroupModel instanceof B2BUnitModel)
				{
					final B2BUnitModel b2bUnit = (B2BUnitModel) principalGroupModel;
					
					if(b2bUnit.getReportingOrganization() == null || b2bUnit.getReportingOrganization().getUid().equalsIgnoreCase(b2bUnit.getUid()))
					{
						String mainAccount = b2bUnit.getUid();
						mainAccount = StringUtils.substringBeforeLast(mainAccount, SiteoneintegrationConstants.SEPARATOR_UNDERSCORE);
						profile.setMainAccount(mainAccount);
						
					}
					
					if (null != b2bUnit.getDivision().getUid()) {
							
						
						   if(b2bUnit.getDivision().getUid().equalsIgnoreCase("US"))
							{
								profile.setDivision("US");	
							}
							else if(b2bUnit.getDivision().getUid().equalsIgnoreCase("CA"))
							{
								profile.setDivision("CA");	
							}
							
							
					}
				}
				
			}
			
		}

		boolean isPrimaryContact = false;
		
		for (final PrincipalGroupModel principalGroupModel : source.getGroups()) {
			
			// Setting isPrimary contact
			if (principalGroupModel.getUid().contains("b2badmingroup")) {
				isPrimaryContact =  true;
			}
		}
		
		if(isPrimaryContact){
			profile.setUserType(SiteoneintegrationConstants.USER_TYPE_ADMIN);	
		}else {
			profile.setUserType(SiteoneintegrationConstants.USER_TYPE_USER);	
		}
		
		if (null != source.getContactInfos()) {
			
			source.getContactInfos().forEach(contactInfo->{
				
				if(contactInfo instanceof PhoneContactInfoModel) {
					
					PhoneContactInfoModel phoneContactInfoModel = (PhoneContactInfoModel) contactInfo;
					
					if(null != phoneContactInfoModel.getPhoneNumber()){
						String phoneNumber = phoneContactInfoModel.getPhoneNumber();
						//Removing bracket,hyphen and space from phoneNumber
						phoneNumber=phoneNumber.replaceAll("[\\s()-]","");
						
						profile.setMobilePhone(phoneNumber);
					}
				}
				
				
				
			});
		}
		
		target.setProfile(profile);
		
	}

	
}
