/*
 * [y] hybris Platform
 * 
 * Copyright (c) 2000-2019 SAP SE
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of SAP 
 * Hybris ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.punchout.populators.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.punchout.PunchOutSession;
import de.hybris.platform.b2b.punchout.populators.impl.DefaultPunchOutSessionPopulator;
import de.hybris.platform.b2b.punchout.services.CXMLElementBrowser;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.cxml.CXML;
import org.cxml.Header;
import org.cxml.PunchOutSetupRequest;

import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.company.SiteOneB2BUserFacade;

import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.user.UserService;

/**
 *
 */
public class DefaultSiteOnePunchOutSessionPopulator extends DefaultPunchOutSessionPopulator
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePunchOutSessionPopulator.class);
	
	private static final String PUNCHOUT_CUSTOMERGROUP = "b2bcustomergroup";
	private static final String PUNCHOUT_DOMAIN_B2BUNIT = "PunchoutDomainB2BUnit";
	
	@Resource
	private UserService userService;
	
	@Resource(name = "i18nService")
	private I18NService i18nService;
	
	@Resource(name = "b2bUserFacade")
	private B2BUserFacade b2bUserFacade;
	
	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;
	
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Override
	public void populate(final CXML source, final PunchOutSession target) throws ConversionException
	{

		final CXMLElementBrowser cXmlBrowser = new CXMLElementBrowser(source);

		final PunchOutSetupRequest request = cXmlBrowser.findRequestByType(PunchOutSetupRequest.class);
		target.setOperation(request.getOperation());
		populateBuyerCookie(target, request);
		//populateShippingInfo(target, request);
		target.setBrowserFormPostUrl(request.getBrowserFormPost().getURL().getvalue());
		populateUserEmail(target, cXmlBrowser);

		populateOrganizationInfo(cXmlBrowser.findHeader(), target);

	}
	
	protected void populateUserEmail(final PunchOutSession target, final CXMLElementBrowser cXmlBrowser)
	{
		final List<Object> list = cXmlBrowser.findRequest()
				.getProfileRequestOrOrderRequestOrMasterAgreementRequestOrPurchaseRequisitionRequestOrPunchOutSetupRequestOrProviderSetupRequestOrStatusUpdateRequestOrGetPendingRequestOrSubscriptionListRequestOrSubscriptionContentRequestOrSupplierListRequestOrSupplierDataRequestOrSubscriptionStatusUpdateRequestOrCopyRequestOrCatalogUploadRequestOrAuthRequestOrDataRequestOrOrganizationDataRequestOrApprovalRequestOrQualityNotificationRequestOrQualityInspectionRequestOrQualityInspectionResultRequestOrQualityInspectionDecisionRequest();
		
		if (list.size() > 0 && list.get(0) instanceof PunchOutSetupRequest)
		{
			final PunchOutSetupRequest punchOutSetupRequest = (PunchOutSetupRequest) list.get(0);
			if (punchOutSetupRequest.getContact() != null && punchOutSetupRequest.getContact().size() > 0
					&& punchOutSetupRequest.getContact().get(0) != null && punchOutSetupRequest.getContact().get(0).getEmail() != null
					&& punchOutSetupRequest.getContact().get(0).getEmail().size() > 0
					&& punchOutSetupRequest.getContact().get(0).getEmail().get(0) != null)
			{
				target.setUserEmail(punchOutSetupRequest.getContact().get(0).getEmail().get(0).getvalue());
				LOG.error("UserEmail==" + target.getUserEmail());
				
				if(!userService.isUserExisting(target.getUserEmail()))
				{
					LOG.error("Cannot find user with uid \'" + target.getUserEmail() + "\'");
					CustomerData b2bCustomerData =
							prepareCreateCustomerRequestData(target,punchOutSetupRequest,cXmlBrowser.findHeader());
					((SiteOneB2BUserFacade)b2bUserFacade).updateCustomer(b2bCustomerData);
				}
			}
		}
	}

	protected CustomerData prepareCreateCustomerRequestData(final PunchOutSession target,
			final PunchOutSetupRequest punchOutSetupRequest,final Header header) {
		final CustomerData b2bCustomerData = new CustomerData();
		b2bCustomerData.setTitleCode("2");
		String userName=punchOutSetupRequest.getContact().get(0).getName().getvalue();
		String[] names = userName.split("\\s+",2);
		b2bCustomerData.setFirstName(names[0]);
		b2bCustomerData.setLastName(names[1]);
		b2bCustomerData.setEmail(target.getUserEmail().trim().toLowerCase());
		b2bCustomerData.setDisplayUid(target.getUserEmail().trim().toLowerCase());
		String b2bUnit = setPunchoutAccountAndUsergroup(header);
		b2bCustomerData.setRoles(Collections.singletonList(PUNCHOUT_CUSTOMERGROUP));
		b2bCustomerData.setUnit(b2bUnitFacade.getUnitForUid(b2bUnit));
		b2bCustomerData.setInvoicePermissions(Boolean.FALSE);
		b2bCustomerData.setPartnerProgramPermissions(Boolean.FALSE);
		b2bCustomerData.setAccountOverviewForParent(Boolean.TRUE);
		b2bCustomerData.setAccountOverviewForShipTos(Boolean.TRUE);
		b2bCustomerData.setPayBillOnline(Boolean.FALSE);
		b2bCustomerData.setPlaceOrder(Boolean.TRUE);
		if(punchOutSetupRequest.getShipTo().getAddress().getPhone()!=null 
				&& punchOutSetupRequest.getShipTo().getAddress().getPhone().getTelephoneNumber()!=null) {
			b2bCustomerData.setContactNumber(punchOutSetupRequest.getShipTo().getAddress().getPhone().
					getTelephoneNumber().getNumber());
		}
		b2bCustomerData.setAssignedShipTos(Arrays.asList(b2bUnit));
		b2bCustomerData.setEnableAddModifyDeliveryAddress(Boolean.TRUE);
		b2bCustomerData.setHomeBranch(getHomeBranch(punchOutSetupRequest.getShipTo().getAddress().
				getPostalAddress().getPostalCode()));
		b2bCustomerData.setIsPunchoutFlow(Boolean.TRUE);
		b2bCustomerData.setLangPreference(i18nService.getLanguage("en"));
		return b2bCustomerData;
	}
	
	protected String setPunchoutAccountAndUsergroup(final Header header) {
		String domain = header.getFrom().getCredential().get(0).getDomain().toLowerCase();
		Map<String, String> punchoutB2BUnitMapping = siteOneFeatureSwitchCacheService.getPunchoutB2BUnitMapping(PUNCHOUT_DOMAIN_B2BUNIT);
		return punchoutB2BUnitMapping.get(domain);
	}
	
	protected String getHomeBranch(String zipcode) {
		List<PointOfServiceData> storesList = ((SiteOneStoreFinderFacade) storeFinderFacade)
				.getStoresForZipcode(zipcode, Double.parseDouble("100"));
		if(!storesList.isEmpty()) {
			return storesList.get(0).getStoreId();
		}
		return null;
	}
	
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public B2BUserFacade getB2bUserFacade() {
		return b2bUserFacade;
	}

	public void setB2bUserFacade(B2BUserFacade b2bUserFacade) {
		this.b2bUserFacade = b2bUserFacade;
	}

	public B2BUnitFacade getB2bUnitFacade() {
		return b2bUnitFacade;
	}

	public void setB2bUnitFacade(B2BUnitFacade b2bUnitFacade) {
		this.b2bUnitFacade = b2bUnitFacade;
	}

}
