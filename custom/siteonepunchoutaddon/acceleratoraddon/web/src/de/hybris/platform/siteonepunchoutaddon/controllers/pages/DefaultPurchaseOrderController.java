/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.siteonepunchoutaddon.controllers.pages;

import de.hybris.platform.b2b.punchout.PunchOutException;
import de.hybris.platform.b2b.punchout.PunchOutResponseCode;
import de.hybris.platform.b2b.punchout.PunchOutSession;
import de.hybris.platform.b2b.punchout.services.CXMLBuilder;
import de.hybris.platform.b2b.punchout.services.PunchOutService;
import de.hybris.platform.b2b.punchout.services.PunchOutSessionService;
import de.hybris.platform.siteonepunchoutaddon.services.PunchOutUserAuthenticationService;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.CartService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.cxml.CXML;
import org.cxml.OrderRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import de.hybris.platform.b2b.punchout.services.CXMLElementBrowser;

import java.util.Date;
import java.util.List;
import de.hybris.platform.b2b.punchout.PunchOutUtils;

import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;


import de.hybris.platform.b2b.punchout.enums.PunchOutOrderOperationAllowed;
import com.siteone.punchout.services.SiteOnePunchOutAuditLogService;
import com.siteone.punchout.services.impl.DefaultSiteOnePunchOutSessionService;


/**
 * Purchase order controller default implementation.
 */
@Component
public class DefaultPurchaseOrderController implements PunchOutController
{
	private static final Logger LOG = Logger.getLogger(DefaultPurchaseOrderController.class);

	public static final String NOT_FOUND = "404";

	@Resource
	private PunchOutService punchOutService;

	@Resource
	private CartService cartService;

	@Resource
	private PunchOutUserAuthenticationService orderPunchOutUserAuthenticationService;

	@Resource(name = "punchOutSessionService")
	private DefaultSiteOnePunchOutSessionService punchoutSessionService;

	@Resource
	private SiteOnePunchOutAuditLogService siteOnePunchOutAuditLogService;

	/**
	 * Handles a Order Request from the Punch Out Provider.
	 * 
	 * @param requestBody
	 *           The cXML containing the order to be processed.
	 * @param request
	 *           The servlet request.
	 * @param response
	 *           The servlet response.
	 * @return A cXML with the Order Response, containing the status of the processing of the order.
	 */
	@PostMapping("/punchout/cxml/order")
	@ResponseBody
	public CXML handlePunchOutPurchaseOrderRequest(@RequestBody final CXML requestBody, final HttpServletRequest request,
			final HttpServletResponse response)
	{
		LOG.info("Within handlePunchOutPurchaseOrderRequest() entering");
		LOG.info("PurchaseOrder Request=="+PunchOutUtils.marshallFromBeanTree(requestBody));
		CXML cxml = null;
		String identity = null;

		try
		{
			identity = punchOutService.retrieveIdentity(requestBody);
		}
		catch(UnknownIdentifierException uie)
		{
			LOG.error(uie.getMessage());
			siteOnePunchOutAuditLogService.saveAuditLog(requestBody, cxml, identity,"ORDER_FAILURE", punchoutSessionService.getCurrentPunchOutSessionId(), new Date());
            cxml = CXMLBuilder.newInstance().withResponseCode(NOT_FOUND)
					.withResponseMessage(uie.getMessage()).create();
			return cxml;
		}

		LOG.info("identity=="+identity);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Order Identity:" + identity);
		}
	
		// if user exists
		try
		{
		if (identity != null)
		{

			orderPunchOutUserAuthenticationService.authenticate(identity, false, punchoutSessionService.getCurrentPunchOutSession(),
					request, response);

			//final CartModel cartModel = cartService.getSessionCart();

			cxml = punchOutService.processPurchaseOrderRequest(requestBody);
		}
		else
		{
			final String message = "Unable to find user " + identity;
			LOG.error(message);
			cxml = CXMLBuilder.newInstance().withResponseCode(PunchOutResponseCode.ERROR_CODE_AUTH_FAILED)
					.withResponseMessage(message).create();
		}
		}
		catch (Exception ex)
		{
		 
			siteOnePunchOutAuditLogService.saveAuditLog(requestBody, cxml, identity,"ORDER_FAILURE", punchoutSessionService.getCurrentPunchOutSessionId(), new Date());

		}

		LOG.info("PurchaseOrder Response=="+PunchOutUtils.marshallFromBeanTree(cxml));

		siteOnePunchOutAuditLogService.saveAuditLog(requestBody, cxml, identity,"ORDER", punchoutSessionService.getCurrentPunchOutSessionId(), new Date());

		return cxml;
	}

	@ExceptionHandler
	@ResponseBody
	public CXML handleException(final Exception exc)
	{
		LOG.error("Could not process PunchOut Order Request", exc);

		CXML response = null;

		if (exc instanceof PunchOutException)
		{
			final PunchOutException punchoutException = (PunchOutException) exc;
			response = CXMLBuilder.newInstance().withResponseCode(punchoutException.getErrorCode())
					.withResponseMessage(exc.getMessage()).create();
		}
		else
		{
			response = CXMLBuilder.newInstance().withResponseCode(PunchOutResponseCode.INTERNAL_SERVER_ERROR)
					.withResponseMessage(exc.getMessage()).create();
		}

		return response;
	}
}