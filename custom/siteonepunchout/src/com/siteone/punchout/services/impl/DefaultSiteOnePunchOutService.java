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
package com.siteone.punchout.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.punchout.services.CXMLElementBrowser;
import de.hybris.platform.b2b.punchout.services.impl.DefaultPunchOutService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.cxml.CXML;
//import org.cxml.Credential;
import org.cxml.OrderRequest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

/**
 *
 */
public class DefaultSiteOnePunchOutService extends DefaultPunchOutService
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePunchOutService.class);
	
	@Resource
	private UserService userService;
	
	@Override
	public String retrieveIdentity(final CXML request)
	{
		String userId = super.retrieveIdentity(request);
		final CXMLElementBrowser cXmlBrowser = new CXMLElementBrowser(request);

		String emailId = null;
		if(userId != null){
			List<Object> list = cXmlBrowser.findRequest().getProfileRequestOrOrderRequestOrMasterAgreementRequestOrPurchaseRequisitionRequestOrPunchOutSetupRequestOrProviderSetupRequestOrStatusUpdateRequestOrGetPendingRequestOrSubscriptionListRequestOrSubscriptionContentRequestOrSupplierListRequestOrSupplierDataRequestOrSubscriptionStatusUpdateRequestOrCopyRequestOrCatalogUploadRequestOrAuthRequestOrDataRequestOrOrganizationDataRequestOrApprovalRequestOrQualityNotificationRequestOrQualityInspectionRequestOrQualityInspectionResultRequestOrQualityInspectionDecisionRequest();
			if (list.size() > 0 && list.get(0) instanceof OrderRequest)
			{
				final OrderRequest orderRequest = (OrderRequest) list.get(0);

				if (orderRequest.getOrderRequestHeader() != null && orderRequest.getOrderRequestHeader().getContact() != null
					&& orderRequest.getOrderRequestHeader().getContact().size()>0 
					&& orderRequest.getOrderRequestHeader().getContact().get(0) !=null
					&& orderRequest.getOrderRequestHeader().getContact().get(0).getEmail() != null 
					&& orderRequest.getOrderRequestHeader().getContact().get(0).getEmail().size()>0
					&& orderRequest.getOrderRequestHeader().getContact().get(0).getEmail().get(0) != null)
				{
					emailId = orderRequest.getOrderRequestHeader().getContact().get(0).getEmail().get(0).getvalue();
					LOG.info("emailId=="+emailId);
				}
			}
			
			if(emailId != null){
				try
				{
					final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) userService.getUserForUID(emailId);
				
					if(b2bCustomerModel == null){
						emailId = null;
					}
				}
				catch(UnknownIdentifierException ex)
				{
					throw new UnknownIdentifierException("Cannot find user with uid \'" + emailId + "\'");
				}
			}
		}

		return emailId;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}
	
	
}
