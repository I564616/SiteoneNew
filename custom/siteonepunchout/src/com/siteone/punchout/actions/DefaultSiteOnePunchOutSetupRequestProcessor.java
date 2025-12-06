/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.punchout.actions;

import de.hybris.platform.b2b.punchout.PunchOutException;
import de.hybris.platform.b2b.punchout.PunchOutResponseMessage;
import de.hybris.platform.b2b.punchout.actions.inbound.DefaultPunchOutAuthenticationVerifier;
import de.hybris.platform.b2b.punchout.actions.inbound.DefaultPunchOutSetupRequestProcessor;
import de.hybris.platform.b2b.punchout.actions.inbound.PopulateSetupResponsePunchOutProcessing;
import de.hybris.platform.b2b.punchout.actions.inbound.PunchOutSetupRequestCartProcessing;
import de.hybris.platform.b2b.punchout.services.CXMLBuilder;
import de.hybris.platform.b2b.punchout.services.PunchOutService;
import de.hybris.platform.b2b.punchout.services.PunchOutSessionService;
import org.cxml.CXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Default implementation of {@link PunchOutService}.
 */
public class DefaultSiteOnePunchOutSetupRequestProcessor extends DefaultPunchOutSetupRequestProcessor
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultSiteOnePunchOutSetupRequestProcessor.class);

	private DefaultPunchOutAuthenticationVerifier punchOutAuthenticationVerifier;
	private PunchOutSetupRequestCartProcessing punchOutSetupRequestCartProcessing;
	private PopulateSetupResponsePunchOutProcessing populateSetupResponsePunchOutProcessing;
	private PunchOutSessionService punchoutSessionService;

	@Override
	public CXML generatecXML(final CXML request)
	{
		final CXML response = CXMLBuilder.newInstance().withResponseCode(HttpStatus.OK)
				.withResponseMessage(PunchOutResponseMessage.SUCCESS).create();

		try
		{
			//Step through the process of setting up a request. This handles operations, create, edit.
			getPunchOutAuthenticationVerifier().verify(request);
			getPunchOutSessionService().initAndActivatePunchOutSession(request);
			getPunchOutSetupRequestCartProcessing().processCartData(request);
			getPunchOutSessionService().saveCurrentPunchoutSession();
			getPopulateSetupResponsePunchOutProcessing().populateResponse(response);
		}
		catch (final PunchOutException error)
		{
			final String message = String.format(
					"The request processing is canceled due to the following exception, the cxml error code is %s. This was written to the response.",
					error.getErrorCode());
			LOG.error(message, error);

			return CXMLBuilder.newInstance().withResponseCode(error.getErrorCode()).withResponseMessage(error.getMessage())
						.create();
		}
		return response;
	}
	
	

	public DefaultPunchOutAuthenticationVerifier getPunchOutAuthenticationVerifier() {
		return punchOutAuthenticationVerifier;
	}



	public void setPunchOutAuthenticationVerifier(DefaultPunchOutAuthenticationVerifier punchOutAuthenticationVerifier) {
		this.punchOutAuthenticationVerifier = punchOutAuthenticationVerifier;
	}

	protected PunchOutSessionService getPunchOutSessionService()
	{
		return punchoutSessionService;
	}

	public void setPunchOutSessionService(final PunchOutSessionService punchoutSessionService)
	{
		this.punchoutSessionService = punchoutSessionService;
	}

	/**
	 * @return the punchOutSetupRequestCartProcessing
	 */
	protected PunchOutSetupRequestCartProcessing getPunchOutSetupRequestCartProcessing()
	{
		return punchOutSetupRequestCartProcessing;
	}

	/**
	 * @param punchOutSetupRequestCartProcessing
	 *           the punchOutSetupRequestCartProcessing to set
	 */
	public void setPunchOutSetupRequestCartProcessing(final PunchOutSetupRequestCartProcessing punchOutSetupRequestCartProcessing)
	{
		this.punchOutSetupRequestCartProcessing = punchOutSetupRequestCartProcessing;
	}

	/**
	 * @return the populateSetupResponsePunchOutProcessing
	 */
	protected PopulateSetupResponsePunchOutProcessing getPopulateSetupResponsePunchOutProcessing()
	{
		return populateSetupResponsePunchOutProcessing;
	}

	/**
	 * @param populateSetupResponsePunchOutProcessing
	 *           the populateSetupResponsePunchOutProcessing to set
	 */
	public void setPopulateSetupResponsePunchOutProcessing(
			final PopulateSetupResponsePunchOutProcessing populateSetupResponsePunchOutProcessing)
	{
		this.populateSetupResponsePunchOutProcessing = populateSetupResponsePunchOutProcessing;
	}
}
