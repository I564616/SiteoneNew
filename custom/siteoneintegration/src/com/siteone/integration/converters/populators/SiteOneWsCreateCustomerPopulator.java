package com.siteone.integration.converters.populators;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerRequestData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsCreateContactInfoData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsCreateCustomerAddressData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsCreateCustomerInfoData;
import com.siteone.integration.customer.createCustomer.data.SiteoneWsCreateLoyaltyProgramInfoData;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.site.BaseSiteService;


public class SiteOneWsCreateCustomerPopulator implements Populator<SiteoneRequestAccountModel, SiteOneWsCreateCustomerRequestData>{
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	@Override
	public void populate(SiteoneRequestAccountModel source, SiteOneWsCreateCustomerRequestData target)
			throws ConversionException {
		if(source.getUuid()!=null)
		{
		target.setCorrelationID(source.getUuid());
		}
		else
		{
		target.setCorrelationID(UUID.randomUUID().toString());
		}
		target.setExternalSystemID("2");
		SiteoneWsCreateCustomerInfoData siteonewsCreateCustomerInfoData = new SiteoneWsCreateCustomerInfoData();
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		if (basesite.getUid().equalsIgnoreCase("siteone-us"))
		{
		siteonewsCreateCustomerInfoData.setDivisionId("1");
		}
		else
		{
			siteonewsCreateCustomerInfoData.setDivisionId("2");	
		}
		siteonewsCreateCustomerInfoData.setAccountNumber(source.getAccountNumber());
		siteonewsCreateCustomerInfoData.setIsLandscapingIndustry(BooleanUtils.isTrue(source.getLandscapingIndustry()));
		if(source.getTypeOfCustomer().equalsIgnoreCase(SiteoneintegrationConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR)){
			siteonewsCreateCustomerInfoData.setTypeofCustomer(SiteoneintegrationConstants.CREATE_CUSTOMER_CONTRACTOR);
			siteonewsCreateCustomerInfoData.setCompanyName(source.getCompanyName());
			siteonewsCreateCustomerInfoData.setNumberOfEmployees(source.getContrEmpCount());
			siteonewsCreateCustomerInfoData.setTradeClassCode(source.getContrPrimaryBusiness());
			siteonewsCreateCustomerInfoData.setSubTradeClassCode(source.getContrPrimaryChildBusiness());
			siteonewsCreateCustomerInfoData.setYearsInBusiness(source.getContrYearsInBusiness());
		}else{
			siteonewsCreateCustomerInfoData.setTypeofCustomer(SiteoneintegrationConstants.CREATE_CUSTOMER_RETAIL);
		}
		List<SiteoneWsCreateCustomerAddressData> addressData = new ArrayList<>();
		SiteoneWsCreateCustomerAddressData addressInfo = new SiteoneWsCreateCustomerAddressData();
		addressInfo.setAddressLine1(source.getAddressLine1());
		if(source.getAddressLine2() != null){
			addressInfo.setAddressLine2(source.getAddressLine2());
		}else{
			addressInfo.setAddressLine2("");
		}
		addressInfo.setCity(source.getCity());
		final String[] region = source.getState().split("-");
		addressInfo.setState(region[1].trim());
		addressInfo.setZip(source.getZipcode());
			
		addressData.add(addressInfo);
		
		siteonewsCreateCustomerInfoData.setAddresses(addressData);
			

		SiteoneWsCreateContactInfoData siteoneWsCreateContactInfoData = new SiteoneWsCreateContactInfoData();

		siteoneWsCreateContactInfoData.setEmail(source.getEmailAddress());
		siteoneWsCreateContactInfoData.setFirstName(source.getFirstName());
		siteoneWsCreateContactInfoData.setLastName(source.getLastName());
		siteoneWsCreateContactInfoData.setPhone(source.getPhoneNumber());
		siteoneWsCreateContactInfoData.setPreferredLanguage(source.getLanguagePreference());
		siteoneWsCreateContactInfoData.setStoreNumber(source.getStoreNumber());

		target.setCustomerInfo(siteonewsCreateCustomerInfoData);

		target.setContactInfo(siteoneWsCreateContactInfoData);		
		
		SiteoneWsCreateLoyaltyProgramInfoData siteoneWsCreateLoyaltyProgramInfoData = new SiteoneWsCreateLoyaltyProgramInfoData();
		siteoneWsCreateLoyaltyProgramInfoData.setAction(BooleanUtils.isTrue(source.getEnrollInPartnersProgram()) ? SiteoneintegrationConstants.ENROLL_CUSTOMER : "null");
		target.setLoyaltyProgramInfo(siteoneWsCreateLoyaltyProgramInfoData);
		
	}

}
