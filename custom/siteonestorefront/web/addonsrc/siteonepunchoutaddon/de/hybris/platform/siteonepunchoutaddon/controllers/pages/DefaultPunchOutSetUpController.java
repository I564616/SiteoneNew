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

import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.b2b.punchout.services.PunchOutSessionService;

import java.io.StringWriter;
import java.util.Date;

import jakarta.annotation.Resource;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.cxml.CXML;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import de.hybris.platform.b2b.punchout.PunchOutUtils;
import de.hybris.platform.b2b.punchout.enums.PunchOutOrderOperationAllowed;

import com.siteone.punchout.services.SiteOnePunchOutAuditLogService;
import de.hybris.platform.b2b.punchout.PunchOutSession;


/**
 * Controller that handles the Punch Out Setup request.
 */
@Component
public class DefaultPunchOutSetUpController implements PunchOutController
{

	private static final Logger LOG = Logger.getLogger(DefaultPunchOutSetUpController.class);

	@Resource
	private PunchOutService punchOutService;

	@Resource
	private SiteOnePunchOutAuditLogService siteOnePunchOutAuditLogService;

	@Resource
	private PunchOutSessionService punchOutSessionService;

	public static final String NOT_FOUND = "404";
	/**
	 * Receives a request from the punchout provider and sends it the information to access the hybris site.
	 * 
	 * @param requestBody
	 *           The cXML file with the punchout user requisition.
	 * @return A cXML file with the access information.
	 */
	@PostMapping("/punchout/cxml/setup")
	@ResponseBody
	public CXML handlePunchOutSetUpRequest(@RequestBody final CXML requestBody)
	{
		
		CXML punchoutSetUpResponse = null;
		try
		{
			
			if (LOG.isDebugEnabled())
			{
				LOG.debug("cXML: " + requestBody);
			}

			punchoutSetUpResponse = punchOutService.processPunchOutSetUpRequest(requestBody);
		}
		catch(UnknownIdentifierException ex)
		{
			LOG.error(ex.getMessage());
			final PunchOutSession currentPunchOutSession = punchOutSessionService.getCurrentPunchOutSession();
            String email = null;
            
			if (currentPunchOutSession != null)
            {
				email=currentPunchOutSession.getUserEmail();
            }
    		siteOnePunchOutAuditLogService.saveAuditLog(requestBody, punchoutSetUpResponse, email,"CREATEorEDIT_FAILURE", punchOutSessionService.getCurrentPunchOutSessionId(), new Date());
			punchoutSetUpResponse = CXMLBuilder.newInstance().withResponseCode(NOT_FOUND)
					.withResponseMessage(ex.getMessage()).create();
			return punchoutSetUpResponse;
		}
		catch(Exception ex) 
		{
			LOG.error(ex.getMessage());
			final PunchOutSession currentPunchOutSession = punchOutSessionService.getCurrentPunchOutSession();
            String email = null;
            
			if (currentPunchOutSession != null)
            {
				email=currentPunchOutSession.getUserEmail();
            }
    		siteOnePunchOutAuditLogService.saveAuditLog(requestBody, punchoutSetUpResponse, email,"CREATEorEDIT_FAILURE", punchOutSessionService.getCurrentPunchOutSessionId(), new Date());
			punchoutSetUpResponse = CXMLBuilder.newInstance().withResponseCode(NOT_FOUND)
					.withResponseMessage(ex.getMessage()).create();
			return punchoutSetUpResponse;
		}

		LOG.info("SetUp Response=="+PunchOutUtils.marshallFromBeanTree(punchoutSetUpResponse));

		final PunchOutSession currentPunchOutSession = punchOutSessionService.getCurrentPunchOutSession();

		siteOnePunchOutAuditLogService.saveAuditLog(requestBody, punchoutSetUpResponse, currentPunchOutSession.getUserEmail(),"CREATEorEDIT", punchOutSessionService.getCurrentPunchOutSessionId(), new Date());

		return punchoutSetUpResponse;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CXML handleException(final Exception exc)
	{
		LOG.error("Error occurred while processing PunchOut Setup Request", exc);

		CXML response = null;

		if (exc instanceof PunchOutException)
		{
			final PunchOutException punchoutException = (PunchOutException) exc;
			response = CXMLBuilder.newInstance().withResponseCode(punchoutException.getErrorCode())
					.withResponseMessage(PunchOutException.PUNCHOUT_EXCEPTION_MESSAGE).create();
		}
		else
		{
			response = CXMLBuilder.newInstance().withResponseCode(PunchOutResponseCode.INTERNAL_SERVER_ERROR)
					.withResponseMessage(PunchOutException.PUNCHOUT_EXCEPTION_MESSAGE).create();
		}

		return response;
	}

}