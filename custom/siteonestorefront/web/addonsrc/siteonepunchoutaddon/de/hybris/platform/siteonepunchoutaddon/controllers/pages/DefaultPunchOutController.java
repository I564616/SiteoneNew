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

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.punchout.PunchOutException;
import de.hybris.platform.b2b.punchout.PunchOutSession;
import de.hybris.platform.b2b.punchout.PunchOutUtils;
import de.hybris.platform.b2b.punchout.constants.PunchOutSetupOperation;
import de.hybris.platform.b2b.punchout.enums.PunchOutLevel;
import de.hybris.platform.b2b.punchout.services.PunchOutService;
import de.hybris.platform.b2b.punchout.services.PunchOutSessionService;
import de.hybris.platform.siteonepunchoutaddon.constants.SiteonepunchoutaddonConstants;
import de.hybris.platform.siteonepunchoutaddon.services.PunchOutUserAuthenticationService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.cxml.CXML;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.hybris.platform.b2b.punchout.PunchOutUtils;

import com.siteone.punchout.services.SiteOnePunchOutAuditLogService;
import com.siteone.punchout.services.impl.DefaultSiteOnePunchOutSessionService;
import java.util.Arrays;
import java.util.Date;


@Component
public class DefaultPunchOutController extends AbstractPageController implements PunchOutController
{
	private static final Logger LOG = Logger.getLogger(DefaultPunchOutController.class);

	private static final String CART_CMS_PAGE = "cartPage";
	public static final String REDIRECT_PREFIX = "redirect:";
	public static final String ADDON_PREFIX = "addon:";
	public static final String BASE_ADDON_PAGE_PATH = "/siteonepunchoutaddon/pages";

	@Resource(name = "punchOutSessionService")
	private DefaultSiteOnePunchOutSessionService punchoutSessionService;

	@Resource
	private PunchOutService punchOutService;

	@Resource
	private PunchOutUserAuthenticationService punchOutUserAuthenticationService;

	@Resource
	private SiteOnePunchOutAuditLogService siteOnePunchOutAuditLogService;


	/**
	 * Used to create a new punch out session by authenticating a punch out user.
	 *
	 * @param key
	 *           Secured key that have been exchanged with the punch out provider (or security hub).
	 * @param sessionId
	 *           the hybris session ID
	 */
	@GetMapping("/punchout/cxml/session")
	public void handlePunchOutSession(@RequestParam("sid") final String sessionId,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		final PunchOutSession punchOutSession = punchoutSessionService.loadPunchOutSession(sessionId);
		LOG.info("SiteOne.punchOutSession=="+punchOutSession);
		LOG.info("SiteOne.PunchOut session: " + printSessionInfo(punchOutSession));

		final String userId = punchOutSession.getUserEmail();
		LOG.info("SiteOne.PunchOut userId: " + userId);
		final HttpSession session = request.getSession();
		session.removeAttribute(SiteonepunchoutaddonConstants.PUNCHOUT_USER);

		getPunchOutUserAuthenticationService().authenticate(userId, true, punchOutSession, request, response);

		// if user was authenticated (no exception was thrown), set the current cart with the one from the setup request session
		punchoutSessionService.setCurrentCartFromPunchOutSetup(userId);

		session.setAttribute(SiteonepunchoutaddonConstants.PUNCHOUT_USER, userId);
	}

	/**
	 * Cancels a requisition (POST) to the punch out provider sending a cancel message.
	 *
	 * @return Redirect to cart page.
	 * @throws InvalidCartException
	 * @throws CMSItemNotFoundException
	 */
	@GetMapping("/punchout/cxml/cancel")
	public String cancelRequisition(final Model model, final HttpServletRequest request) throws InvalidCartException,
			CMSItemNotFoundException
	{
		final CXML cXML = punchOutService.processCancelPunchOutOrderMessage();
		processRequisitionMessage(cXML, model, request);

		return ADDON_PREFIX + BASE_ADDON_PAGE_PATH + "/punchout/punchoutSendOrderPage";
	}


	protected String printSessionInfo(final PunchOutSession punchoutSession)
	{

		return new ToStringBuilder(punchoutSession, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("operation", punchoutSession.getOperation())
				.append("browserFormPostUrl", punchoutSession.getBrowserFormPostUrl())
				.append("buyerCookie", punchoutSession.getBuyerCookie())
				.append("time", punchoutSession.getTime())
				.append("initiatedBy",
						ToStringBuilder.reflectionToString(punchoutSession.getInitiatedBy(), ToStringStyle.SHORT_PREFIX_STYLE))
				.append("targetedTo",
						ToStringBuilder.reflectionToString(punchoutSession.getTargetedTo(), ToStringStyle.SHORT_PREFIX_STYLE))
				.append("sentBy", ToStringBuilder.reflectionToString(punchoutSession.getSentBy(), ToStringStyle.SHORT_PREFIX_STYLE))
				.toString();
	}

	/**
	 * Places a requisition (POST) to the punchout provider sending the information of the cart.
	 *
	 * @return Redirect to cart page.
	 * @throws InvalidCartException
	 * @throws CMSItemNotFoundException
	 */
	@GetMapping("/punchout/cxml/requisition")
	public String placeRequisition(final Model model, final HttpServletRequest request) throws InvalidCartException,
			CMSItemNotFoundException
	{
		final CXML cXML = punchOutService.processPunchOutOrderMessage();
		processRequisitionMessage(cXML, model, request);

		LOG.info("ReturnRequisition Response=="+PunchOutUtils.marshallFromBeanTree(cXML));
		String uid = ((CustomerData) model.asMap().get("user")).getUid();
		siteOnePunchOutAuditLogService.saveAuditLog(null, cXML, uid,"REQUISITION", punchoutSessionService.getCurrentPunchOutSessionId(), new Date());

		return ADDON_PREFIX + BASE_ADDON_PAGE_PATH + "/punchout/punchoutSendOrderPage";
	}


	protected void processRequisitionMessage(final CXML cXML, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{

		final String cXMLContents = PunchOutUtils.transformCXMLToBase64(cXML);
		model.addAttribute("orderAsCXML", cXMLContents);
		model.addAttribute("browseFormPostUrl", punchoutSessionService.getCurrentPunchOutSession().getBrowserFormPostUrl());

		GlobalMessages.addConfMessage(model, "punchout.message.redirecting");

		storeCmsPageInModel(model, getContentPageForLabelOrId(CART_CMS_PAGE));

		getPunchOutUserAuthenticationService().logout();
	}

	@ExceptionHandler(Exception.class)
	public String handleException(final Exception e, final HttpServletRequest request)
	{
		LOG.error(e.getMessage(), e);
		request.setAttribute("error", e);
		request.setAttribute("message", PunchOutException.PUNCHOUT_EXCEPTION_MESSAGE);

		return ADDON_PREFIX + BASE_ADDON_PAGE_PATH + "/error/punchoutError";
	}

	public void setPunchOutService(final PunchOutService punchoutService)
	{
		this.punchOutService = punchoutService;
	}

	public PunchOutUserAuthenticationService getPunchOutUserAuthenticationService()
	{
		return punchOutUserAuthenticationService;
	}

	public void setPunchOutUserAuthenticationService(final PunchOutUserAuthenticationService punchOutUserAuthenticationService)
	{
		this.punchOutUserAuthenticationService = punchOutUserAuthenticationService;
	}
}