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
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.flow.CheckoutFlowFacade;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.security.BruteForceAttackCounter;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import java.util.Collections;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.customer.SiteOneOktaFacade;
import com.siteone.integration.okta.data.SiteOneOktaSessionData;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * Checkout Login Controller. Handles login and register for the checkout flow.
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/login/checkout")
public class CheckoutLoginController extends AbstractSiteOneLoginPageController
{
	public static final String REDIRECT_PREFIX = "redirect:";
	private static final Logger LOG = Logger.getLogger(CheckoutLoginController.class);
	@Resource(name = "siteOneOktaFacade")
	SiteOneOktaFacade siteOneOktaFacade;
	@Resource(name = "customerFacade")
	SiteOneCustomerFacade siteOneCustomerFacade;
	@Resource(name = "checkoutFlowFacade")
	private CheckoutFlowFacade checkoutFlowFacade;
	private HttpSessionRequestCache httpSessionRequestCache;

	@Resource(name = "bruteForceAttackCounter")
	private BruteForceAttackCounter bruteForceAttackCounter;

	@Resource(name = "httpSessionRequestCache")
	public void setHttpSessionRequestCache(final HttpSessionRequestCache accHttpSessionRequestCache)
	{
		this.httpSessionRequestCache = accHttpSessionRequestCache;
	}

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Override
	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("checkout-login");
	}

	@GetMapping
	public String doCheckoutLogin(@RequestParam(value = "error", defaultValue = "false") final boolean loginError,
			@RequestParam(value = "isUserLocked", defaultValue = "false") final boolean isUserLocked, final HttpSession session,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException //NOSONAR
	{
		model.addAttribute("expressCheckoutAllowed", Boolean.valueOf(checkoutFlowFacade.isExpressCheckoutEnabledForStore()));
		return getDefaultLoginPage(loginError, session, model, isUserLocked);
	}

	@GetMapping("/register")
	public String checkoutRegister(@RequestParam(value = "error", defaultValue = "false") final boolean loginError,
			@RequestParam(value = "isUserLocked", defaultValue = "false") final boolean isUserLocked, final HttpSession session,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		return doCheckoutLogin(loginError, isUserLocked, session, model, request);
	}

	@GetMapping("/guest")
	public String doAnonymousCheckout(@RequestParam(value = "error", defaultValue = "false") final boolean loginError,
			@RequestParam(value = "isUserLocked", defaultValue = "false") final boolean isUserLocked, final HttpSession session,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		return doCheckoutLogin(loginError, isUserLocked, session, model, request);
	}

	@PostMapping("/guest")
	public String doAnonymousCheckout() throws CMSItemNotFoundException
	{
		return REDIRECT_PREFIX + "/checkout/multi/siteOne-checkout/contactDetails";
	}

	@Override
	protected String getView()
	{
		return ControllerConstants.Views.Pages.Checkout.CheckoutLoginPage;
	}

	@Override
	protected String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (hasItemsInCart())
		{
			return getCheckoutUrl();
		}
		// Redirect to the main checkout controller to handle checkout.
		return "/checkout";
	}

	/**
	 * Checks if there are any items in the cart.
	 *
	 * @return returns true if items found in cart.
	 */
	protected boolean hasItemsInCart()
	{
		final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();

		return cartData.getEntries() != null && !cartData.getEntries().isEmpty();
	}

	protected String getCheckoutUrl()
	{
		// Default to the multi-step checkout
		return "/checkout/multi";
	}

	protected CheckoutFlowFacade getCheckoutFlowFacade()
	{
		return checkoutFlowFacade;
	}

	@PostMapping("/auth")
	public String doOktaLogin(@RequestHeader(value = "referer", required = false) final String referer, final LoginForm form,
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final RedirectAttributes redirectModel, final HttpSession session)
			throws CMSItemNotFoundException
	{
		//TODO do we need null check ?
		final String userid = form.getJ_username();
		final String passwd = form.getJ_password();
		//check whether user is available in hybris first. If not, redirect user to login page and
		// display a message.
		final boolean isUserAvailable = siteOneCustomerFacade.isUserAvailable(userid.toLowerCase());
		if (isUserAvailable)
		{
			siteOneCustomerFacade.getClientInformation(request);
			final SiteOneOktaSessionData sessionData = siteOneOktaFacade.doAuth(userid, passwd);

			if (sessionData != null)
			{
				request.getSession().setAttribute("user_okta_id", sessionData.getOktaId());
			}

			if (sessionData.getIsSuccess())
			{

				//Reset the failure count
				bruteForceAttackCounter.resetUserCounter(userid.toLowerCase());

				storeTokenFromOkta(response, sessionData.getUid());
				final String successRedirect = getSuccessRedirect(request, response);
				//TODO change it to debug level
				LOG.info("success redirect " + successRedirect);
				if (!"".equals(successRedirect))
				{
					return REDIRECT_PREFIX + successRedirect;
					// return "";//REDIRECT_PREFIX + "/checkout/multi/order-type/choose";
				}
				else
				{
					return REDIRECT_PREFIX + "/cart"; //TODO check whether redirect to checkout ??
				}
			}
			else
			{
				//exception occurred while posting to okta,check for HTTP status
				//if 401 unauthorized
				if ("401".equals(sessionData.getHttpStatusCode()))
				{
					//Set Failure count
					bruteForceAttackCounter.registerLoginFailure(userid.toLowerCase());
					request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", userid.toLowerCase());

					return REDIRECT_PREFIX + "/login/checkout?error=true";
				}

				//TODO User might not be present in OKTA, but may present in hybris

				if (SiteoneCoreConstants.OKTA_USER_STATUS_LOCKED_OUT.equalsIgnoreCase(sessionData.getMessage()))
				{
					return REDIRECT_PREFIX + "/login/checkout?error=true&isUserLocked=true";
				}

				if ("404".equalsIgnoreCase(sessionData.getHttpStatusCode()))
				{
					GlobalMessages.addErrorMessage(model, "okta.login.unavailable");
					return getDefaultLoginPage(false, session, model, false);
				}


			}
		}
		else
		{
			LOG.error("User " + userid + " not present in hybris. Not invoking call to OKTA.");
			//TODO chk whether any specific message needs to be shown
			return REDIRECT_PREFIX + "/login/checkout?error=true";
		}

		return REDIRECT_PREFIX + "/login/checkout";
	}

	protected String getDefaultLoginPage(final boolean loginError, final HttpSession session, final Model model,
			final boolean isUserLocked) throws CMSItemNotFoundException
	{
		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);
		model.addAttribute(new RegisterForm());
		model.addAttribute(new GuestForm());

		final String username = (String) session.getAttribute(SPRING_SECURITY_LAST_USERNAME);
		if (username != null)
		{
			session.removeAttribute(SPRING_SECURITY_LAST_USERNAME);
		}

		loginForm.setJ_username(username);
		storeCmsPageInModel(model, getCmsPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getCmsPage());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_NOFOLLOW);

		final Breadcrumb loginBreadcrumbEntry = new Breadcrumb("#",
				getMessageSource().getMessage("header.link.login", null, "header.link.login", getI18nService().getCurrentLocale()),
				null);
		model.addAttribute("breadcrumbs", Collections.singletonList(loginBreadcrumbEntry));

		if (null != username)
		{

			final int failureCount = bruteForceAttackCounter.getUserFailedLogins(username.toLowerCase());

			if (failureCount > 1 && failureCount < 5)
			{

				final int maxLockoutAttempt = Config.getInt("okta.lockout.maxattempt", 5);
				final int remainingAttempts = maxLockoutAttempt - failureCount;

				String remainingAttemptsInWords = "";

				if (remainingAttempts == 3)
				{
					remainingAttemptsInWords = getMessageSource().getMessage("text.login.error.count3", null,
							getI18nService().getCurrentLocale());
				}
				else if (remainingAttempts == 2)
				{
					remainingAttemptsInWords = getMessageSource().getMessage("text.login.error.count2", null,
							getI18nService().getCurrentLocale());
				}
				else if (remainingAttempts == 1)
				{
					remainingAttemptsInWords = getMessageSource().getMessage("text.login.error.count1", null,
							getI18nService().getCurrentLocale());
				}

				final Object[] attributes =
				{ remainingAttemptsInWords };

				if (remainingAttempts == 1)
				{
					model.addAttribute("loginError", Boolean.valueOf(loginError));
					GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER,
							getMessageSource().getMessage("lockout.warning.message1", null, getI18nService().getCurrentLocale()),
							attributes);
				}
				else
				{
					model.addAttribute("loginError", Boolean.valueOf(loginError));
					GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER,
							getMessageSource().getMessage("lockout.warning.message", null, getI18nService().getCurrentLocale()),
							attributes);
				}

			}
			else if (isUserLocked)
			{
				model.addAttribute("loginError", Boolean.valueOf(loginError));
				GlobalMessages.addErrorMessage(model,
						getMessageSource().getMessage("account.user.locked", null, getI18nService().getCurrentLocale()));
			}
			else if (loginError)
			{
				model.addAttribute("loginError", Boolean.valueOf(loginError));
				GlobalMessages.addErrorMessage(model, getMessageSource().getMessage("login.error.account.not.found.title", null,
						getI18nService().getCurrentLocale()));
			}
		}
		return getView();
	}

}
