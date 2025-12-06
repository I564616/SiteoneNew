package com.siteone.integration.converters.populators;

import java.util.UUID;

import com.siteone.core.model.TalonOneEnrollmentModel;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteoneWsCreateCustomerInfoData;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteoneWsCreateLoyaltyProgramInfoData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteOneWsTalonOneLoyaltyEnrollmentRequestData;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteoneWsCreateContactInfoData;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class SiteOneWsTalonOneLoyaltyEnrollmentPopulator
		implements Populator<TalonOneEnrollmentModel, SiteOneWsTalonOneLoyaltyEnrollmentRequestData> {

	@Override
	public void populate(TalonOneEnrollmentModel source,
			SiteOneWsTalonOneLoyaltyEnrollmentRequestData target) throws ConversionException {
		target.setCorrelationID(UUID.randomUUID().toString());
		target.setExternalSystemID("2");
		
		SiteoneWsCreateCustomerInfoData siteoneWsCreateCustomerInfoData = new SiteoneWsCreateCustomerInfoData();
		siteoneWsCreateCustomerInfoData.setDivisionId(source.getDivisionId());
		siteoneWsCreateCustomerInfoData.setCustTreeNodeID(source.getCustTreeNodeId());
		target.setCustomerInfo(siteoneWsCreateCustomerInfoData);
		
		SiteoneWsCreateContactInfoData siteoneWsCreateContactInfoData = new SiteoneWsCreateContactInfoData();
		siteoneWsCreateContactInfoData.setContactID(source.getContactId());
		siteoneWsCreateContactInfoData.setEmail(source.getEmail());
		target.setContactInfo(siteoneWsCreateContactInfoData);
		
		SiteoneWsCreateLoyaltyProgramInfoData siteoneWsCreateLoyaltyProgramInfoData = new SiteoneWsCreateLoyaltyProgramInfoData();
		siteoneWsCreateLoyaltyProgramInfoData.setAction(SiteoneintegrationConstants.ENROLL_CUSTOMER);
		target.setLoyaltyProgramInfo(siteoneWsCreateLoyaltyProgramInfoData);
	}

}
