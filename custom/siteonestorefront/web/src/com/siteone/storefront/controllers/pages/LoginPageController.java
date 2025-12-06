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

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.security.BruteForceAttackCounter;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import java.util.Collections;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.UrlUtils;
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
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginPageController extends AbstractSiteOneLoginPageController
{

	private static final Logger LOG = Logger.getLogger(LoginPageController.class);

	@Resource(name = "siteOneOktaFacade")

	private SiteOneOktaFacade siteOneOktaFacade;

	@Resource(name = "customerFacade")
	private SiteOneCustomerFacade siteOneCustomerFacade;

	private HttpSessionRequestCache httpSessionRequestCache;

	@Resource(name = "bruteForceAttackCounter")
	private BruteForceAttackCounter bruteForceAttackCounter;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	private static final String LOGIN_ATTEMPT_ERROR1 = "lockout.warning.message1";
	private static final String LOGIN_ATTEMPT_ERROR = "lockout.warning.message";


	@Override
	protected String getView()
	{
		return ControllerConstants.Views.Pages.Account.AccountLoginPage;
	}

	@Override
	protected String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (httpSessionRequestCache.getRequest(request, response) != null)
		{
			return httpSessionRequestCache.getRequest(request, response).getRedirectUrl();
		}
		return "/";
	}

	@Override
	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("login");
	}


	@Resource(name = "httpSessionRequestCache")
	public void setHttpSessionRequestCache(final HttpSessionRequestCache accHttpSessionRequestCache)
	{
		this.httpSessionRequestCache = accHttpSessionRequestCache;
	}

	@GetMapping
	public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError,
			@RequestParam(value = "isUserLocked", defaultValue = "false") final boolean isUserLocked, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
			throws CMSItemNotFoundException
	{
		if (!loginError)
		{
			storeReferer(referer, request, response);
		}
		return getDefaultLoginPage(loginError, session, model, isUserLocked, request);
	}

	protected void storeReferer(final String referer, final HttpServletRequest request, final HttpServletResponse response)
	{
		//we need to skip referer saving in case when user is coming from non language
		// url like siteone.com as opposed to siteone.com/en
		StringBuilder baseUrl = new StringBuilder(configurationService.getConfiguration().getString("website.siteone.https"));
		baseUrl.append("/");

		if (StringUtils.isNotBlank(referer) && !StringUtils.endsWith(referer, "/login")
				&& !StringUtils.contains(referer, "/login/pw/change") && !StringUtils.contains(referer, "/login/pw/setPassword")
				&& StringUtils.contains(referer, request.getServerName()) && !StringUtils.endsWith(referer, "login?error=true")
		        && !baseUrl.toString().equals(referer) )
		{
			httpSessionRequestCache.saveRequest(request, response);
		}
	}

	@PostMapping("/register")
	public String doRegister(@RequestHeader(value = "referer", required = false) final String referer, final RegisterForm form,
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		getRegistrationValidator().validate(form, bindingResult);
		return processRegisterUserRequest(referer, form, bindingResult, model, request, response, redirectModel);
	}

	/**
	 * Performs okta authentication for the given username/password. 1. Verify user is available in hybris, if not return
	 * and display error message 2.If user is present in hybris, call OKTA auth API with username and password 3. If
	 * authentication is successful, redirect to success redirect, if not redirect to login error page. 4. If the user
	 * present in hybris, but not in OKTA, 401 unauthorized message will be received from OKTA and user will be redirect
	 * to login error page.
	 *
	 * @param referer
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectModel
	 * @return
	 * @throws CMSItemNotFoundException
	 */
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
		if (null != userid)
		{
			model.addAttribute("isCreditCodeValid", siteOneCustomerFacade.isCreditCodeValid(userid.toLowerCase()));
		}
		siteOneCustomerFacade.getClientInformation(request);
		if (isUserAvailable)
		{
			
			final SiteOneOktaSessionData sessionData = siteOneOktaFacade.doAuth(userid, passwd);

			if (sessionData != null)
			{
				request.getSession().setAttribute("user_okta_id", sessionData.getOktaId());
				
			}

			if (sessionData.getIsSuccess())
			{
				//Reset the failure count
				bruteForceAttackCounter.resetUserCounter(userid.toLowerCase());
				siteOneCustomerFacade.customerLastLogin(userid.toLowerCase());
				//if authentication is successful, store the encrypted user details in a cookie


				storeTokenFromOkta(response, sessionData.getUid());
				String successRedirect = getSuccessRedirect(request, response);

				if (UrlUtils.isAbsoluteUrl(successRedirect))
				{
					successRedirect = calculateRelativeRedirectUrl(request.getContextPath(), successRedirect);
				}

				if (successRedirect.contains("my-account") || successRedirect.contains("my-company"))
				{
					successRedirect = "/";
				}
				LOG.info("success redirect " + successRedirect);
				final String redirectToRoot = REDIRECT_PREFIX + "/";
				if (!"".equals(successRedirect))
				{
					if ("/".equals(successRedirect))
					{
						return redirectToRoot;
					}
					else
					{
						return redirectToRoot + successRedirect;
					}
				}
				else
				{
					return redirectToRoot;
				}
			}
			else
			{
				//Check for HTTP status
				//if 401 unauthorized. Also handles, if user present in hybris
				//but not in okta.
				if ("401".equals(sessionData.getHttpStatusCode()))
				{

					//Set Failure count
					bruteForceAttackCounter.registerLoginFailure(userid.toLowerCase());
					request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", userid.toLowerCase());

					return REDIRECT_PREFIX + "/login?error=true";
				}

				if (SiteoneCoreConstants.OKTA_USER_STATUS_LOCKED_OUT.equalsIgnoreCase(sessionData.getMessage()))
				{
					return REDIRECT_PREFIX + "/login?error=true&isUserLocked=true";
				}

				if ("404".equalsIgnoreCase(sessionData.getHttpStatusCode()))
				{
					GlobalMessages.addErrorMessage(model, "okta.login.unavailable");
					return getDefaultLoginPage(false, session, model, false, request);
				}
			}
		}
		else
		{
			LOG.error("User " + userid + " not present in hybris. Not invoking call to OKTA.");
			//TODO chk whether any specific message needs to be shown
			return REDIRECT_PREFIX + "/login?error=true";
		}

		return REDIRECT_PREFIX + "/login";
	}



	protected String getDefaultLoginPage(final boolean loginError, final HttpSession session, final Model model,
			final boolean isUserLocked, HttpServletRequest request) throws CMSItemNotFoundException
	{
		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);
		model.addAttribute(new RegisterForm());
		model.addAttribute(new GuestForm());
		String errorMessage = null;
		final String username = (String) session.getAttribute(SPRING_SECURITY_LAST_USERNAME);
		if (username != null)
		{
			session.removeAttribute(SPRING_SECURITY_LAST_USERNAME);
		}

		if("true".equals(session.getAttribute("csrfTokenExpiredDuringLogin"))){
			GlobalMessages.addErrorMessage(model, "user.login.csrf.expired");
			session.removeAttribute("csrfTokenExpiredDuringLogin");
		}
		loginForm.setJ_username(username);
		storeCmsPageInModel(model, getCmsPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getCmsPage());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_NOFOLLOW);

		final Breadcrumb loginBreadcrumbEntry = new Breadcrumb("#",
				getMessageSource().getMessage("header.link.login", null, "header.link.login", getI18nService().getCurrentLocale()),
				null);
		
		model.addAttribute("breadcrumbs", Collections.singletonList(loginBreadcrumbEntry));
		siteOneCustomerFacade.getClientInformation(request);
		if (null != username)
		{

			final int failureCount = bruteForceAttackCounter.getUserFailedLogins(username.toLowerCase());

			if (SiteoneCoreConstants.OKTAUSER_LOCKED_OUT
					.equals(sessionService.getAttribute(SiteoneCoreConstants.OKTAUSERSTATUS_KEY + username.toLowerCase())))
			{
				model.addAttribute("loginError", Boolean.valueOf(loginError));
				GlobalMessages.addErrorMessage(model,
						getMessageSource().getMessage("account.user.locked", null, getI18nService().getCurrentLocale()));
				errorMessage = getMessageSource().getMessage("account.user.locked", null, getI18nService().getCurrentLocale());
			}
			else if (failureCount > 1 && failureCount < 5)
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
					errorMessage = getMessageSource().getMessage("lockout.warning.message1", null,
							getI18nService().getCurrentLocale());
				}
				else
				{
					model.addAttribute("loginError", Boolean.valueOf(loginError));
					model.addAttribute("remainingAttempts", remainingAttemptsInWords);
					GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER,
							getMessageSource().getMessage("lockout.warning.message", null, getI18nService().getCurrentLocale()),
							attributes);
					errorMessage = getMessageSource().getMessage("lockout.warning.message", null, getI18nService().getCurrentLocale());
				}
			}
			else if (loginError)
			{
				model.addAttribute("loginError", Boolean.valueOf(loginError));
				if (username.endsWith("@siteone.com"))
				{
					GlobalMessages.addErrorMessage(model, "login.error.account.not.found.title1");
					errorMessage = getMessageSource().getMessage("login.error.account.not.found.title1", null,
							getI18nService().getCurrentLocale());
				}
				else
				{
					GlobalMessages.addErrorMessage(model, "login.error.account.not.found.title");
					errorMessage = getMessageSource().getMessage("login.error.account.not.found.title", null,
							getI18nService().getCurrentLocale());
				}
			}

		}
		model.addAttribute("errorMessage", errorMessage);

		return getView();
	}

	protected String calculateRelativeRedirectUrl(final String contextPath, final String url)
	{


		String relUrl = url.substring(url.indexOf("://") + 3);
		String modifiedContextPath = StringUtils.isNotEmpty(contextPath) ? contextPath : "/";
		final String urlEncodingAttributes = getSessionService().getAttribute(WebConstants.URL_ENCODING_ATTRIBUTES);
		if (urlEncodingAttributes != null && !url.contains(urlEncodingAttributes)
				&& modifiedContextPath.contains(urlEncodingAttributes))
		{
			modifiedContextPath = StringUtils.remove(modifiedContextPath, urlEncodingAttributes);
		}

		relUrl = relUrl.substring(relUrl.indexOf(modifiedContextPath) + modifiedContextPath.length());

		return StringUtils.isEmpty(relUrl) ? "/" : relUrl;


	}


}
