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

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.ForgottenPwdForm;
import de.hybris.platform.acceleratorstorefrontcommons.security.BruteForceAttackCounter;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.user.exceptions.PasswordPolicyViolationException;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.exceptions.OktaInvalidUserStatusException;
import com.siteone.core.exceptions.OktaRecoveryTokenNotFoundException;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.exceptions.RecentlyUsedPasswordException;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneSetPwdForm;
import com.siteone.storefront.forms.SiteOneUnlockUserForm;
import com.siteone.storefront.forms.SiteOneUpdatePwdForm;
import com.siteone.storefront.validator.SiteOneSetPwdValidator;
import com.siteone.storefront.validator.SiteOneUpdatePwdValidator;


/**
 * Controller for the forgotten password pages. Supports requesting a password reset email as well as changing the
 * password once you have got the token that was sent via email.
 */
@Controller
@RequestMapping(value = "/login/pw")
public class PasswordResetPageController extends AbstractPageController
{
	private static final String FORGOTTEN_PWD_TITLE = "forgottenPwd.title";

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(PasswordResetPageController.class);

	private static final String REDIRECT_PWD_REQ_CONF = "redirect:/login/pw/request/external/conf";
	private static final String REDIRECT_LOGIN = "redirect:/login";
	private static final String REDIRECT_HOME = "redirect:/";
	private static final String UPDATE_PWD_CMS_PAGE = "updatePassword";
	private static final String SET_PWD_CMS_PAGE = "setPasswordPage";
	private static final String IS_NEW_CUSTOMR = "isNewCustomer";

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder resourceBreadcrumbBuilder;

	@Resource(name = "siteOneUpdatePwdFormValidator")
	private SiteOneUpdatePwdValidator siteOneUpdatePwdValidator;

	@Resource(name = "siteOneSetPwdFormValidator")
	private SiteOneSetPwdValidator siteOneSetPwdFormValidator;

	@Resource(name = "bruteForceAttackCounter")
	private BruteForceAttackCounter bruteForceAttackCounter;

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;
	

	@GetMapping("/request")
	public String getPasswordRequest(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(new ForgottenPwdForm());
		return ControllerConstants.Views.Fragments.Password.PasswordResetRequestPopup;
	}

	@PostMapping("/request")
	public String passwordRequest(@Valid final ForgottenPwdForm form, final BindingResult bindingResult, final Model model)
			throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute("isInvalidEmail", true);
			return ControllerConstants.Views.Fragments.Password.PasswordResetRequestPopup;
		}
		else
		{
			try
			{
				customerFacade.forgottenPassword(form.getEmail());
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.warn("Email: " + form.getEmail() + " does not exist in the database.");
				LOG.error(unknownIdentifierException.getMessage(), unknownIdentifierException);
				//				model.addAttribute("isValidCustomer", false);
				//
				//				bindingResult.rejectValue("email", "account.forgotpassword.invalid.customer");
				return ControllerConstants.Views.Fragments.Password.ForgotPasswordValidationMessage;
			}
			catch (final OktaInvalidUserStatusException | OktaRecoveryTokenNotFoundException exception)
			{
				LOG.error(exception.getMessage(), exception);
				model.addAttribute("isLinkCreated", false);
				return ControllerConstants.Views.Fragments.Password.PasswordResetRequestPopup;
			}
			catch (final ModelSavingException e)
			{
				LOG.error(e.getMessage(), e);
				if (e.getCause() instanceof InterceptorException)
				{
					model.addAttribute("isLinkCreated", false);
					return ControllerConstants.Views.Fragments.Password.PasswordResetRequestPopup;
				}
			}
			return ControllerConstants.Views.Fragments.Password.ForgotPasswordValidationMessage;
		}
	}



	@GetMapping("/unlock-request")
	public String getUnlockUserRequest(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(new SiteOneUnlockUserForm());
		return ControllerConstants.Views.Fragments.Password.UnlockUserRequestPopup;
	}

	@PostMapping("/unlock-request")
	public String unlockUserRequest(@Valid final SiteOneUnlockUserForm form, final BindingResult bindingResult, final Model model)
			throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute("isInvalidEmail", true);
			return ControllerConstants.Views.Fragments.Password.UnlockUserRequestPopup;
		}
		else
		{
			try
			{
				((SiteOneCustomerFacade) customerFacade).unlockUserRequest(form.getEmail());
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.warn("Email: " + form.getEmail() + " does not exist in the database.");
				LOG.error(unknownIdentifierException.getMessage(), unknownIdentifierException);
				model.addAttribute("isValidCustomer", false);

				bindingResult.rejectValue("email", "account.forgotpassword.invalid.customer");
				return ControllerConstants.Views.Fragments.Password.UnlockUserRequestPopup;
			}
			return ControllerConstants.Views.Fragments.Password.UnlockUserValidationMessage;
		}
	}

	@GetMapping("/unlock")
	public String unlockUser(@RequestParam(required = false) final String token, final Model model,
			final RedirectAttributes redirectModel, HttpServletRequest request) throws CMSItemNotFoundException
	{


		if (StringUtils.isBlank(token))
		{
			return REDIRECT_HOME;
		}

		try
		{
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			final String userId = ((SiteOneCustomerFacade) customerFacade).unlockUser(token);

			if (null != userId)
			{
				//Reset the failure count
				bruteForceAttackCounter.resetUserCounter(userId.toLowerCase());

				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.user.unlocked");
			}
		}
		catch (final TokenInvalidatedException tokenInvalidatedException)
		{
			LOG.error(tokenInvalidatedException.getMessage(), tokenInvalidatedException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"system.error.link.expired.unlock.title");
			return REDIRECT_LOGIN;
		}
		catch (final IllegalArgumentException illegalArgumentException)
		{
			LOG.error(illegalArgumentException.getMessage(), illegalArgumentException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
					"system.error.link.expired.unlock.title");
			return REDIRECT_LOGIN;
		}


		return REDIRECT_LOGIN;


	}

	@GetMapping("/request/external")
	public String getExternalPasswordRequest(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute(new ForgottenPwdForm());
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs(FORGOTTEN_PWD_TITLE));
		return ControllerConstants.Views.Pages.Password.PasswordResetRequest;
	}

	@GetMapping("/request/external/conf")
	public String getExternalPasswordRequestConf(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs(FORGOTTEN_PWD_TITLE));
		return ControllerConstants.Views.Pages.Password.PasswordResetRequestConfirmation;
	}

	@PostMapping("/request/external")
	public String externalPasswordRequest(@Valid final ForgottenPwdForm form, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs(FORGOTTEN_PWD_TITLE));

		if (bindingResult.hasErrors())
		{
			return ControllerConstants.Views.Pages.Password.PasswordResetRequest;
		}
		else
		{
			try
			{
				customerFacade.forgottenPassword(form.getEmail());
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
						"account.confirmation.forgotten.password.link.sent");
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.warn("Email: " + form.getEmail() + " does not exist in the database.");
				LOG.error(unknownIdentifierException.getMessage(), unknownIdentifierException);
			}
			return REDIRECT_PWD_REQ_CONF;
		}
	}

	@GetMapping("/change")
	public String getChangePassword(@RequestParam(required = false) final String token, final Model model,
			final RedirectAttributes redirectModel, HttpServletRequest request) throws CMSItemNotFoundException
	{


		if (StringUtils.isBlank(token))
		{
			return REDIRECT_HOME;
		}
		//Verify RecoveryToken and get stateToken if fails redirects to login page
		try
		{
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			((SiteOneCustomerFacade) customerFacade).verifyRecoveryToken(token);
		}
		catch (final InvalidTokenException invalidTokenException)
		{
			LOG.error(invalidTokenException.getMessage() + "  token="+token, invalidTokenException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "system.error.link.expired.title");
			return REDIRECT_LOGIN;
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error(resourceAccessException.getMessage() + "  token="+token, resourceAccessException);
			GlobalMessages.addErrorMessage(model, "form.sytem.down.reset.password.error");
			return REDIRECT_LOGIN;
		}
		catch (final IllegalArgumentException illegalArgumentException)
		{
			LOG.error(illegalArgumentException.getMessage() + "  token="+token, illegalArgumentException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.link.expired.title");
			return REDIRECT_LOGIN;
		}
		catch (final TokenInvalidatedException tokenInvalidatedException)
		{
			LOG.error(tokenInvalidatedException.getMessage() + "  token="+token, tokenInvalidatedException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.link.expired.title");
			return REDIRECT_LOGIN;
		}


		final SiteOneUpdatePwdForm form = new SiteOneUpdatePwdForm();

		form.setToken(token);
		//set state Token
		//form.setStateToken(stateToken);
		model.addAttribute(form);
		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PWD_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PWD_CMS_PAGE));
		//model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("resetPwd.title"));
		return ControllerConstants.Views.Pages.Password.PasswordResetChangePage;
	}

	@PostMapping("/change")
	public String changePassword(@Valid final SiteOneUpdatePwdForm form,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectModel, HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		getSiteOneUpdatePwdValidator().validate(form, bindingResult);
		if (bindingResult.hasErrors())
		{
			prepareErrorMessage(model, UPDATE_PWD_CMS_PAGE);
			return ControllerConstants.Views.Pages.Password.PasswordResetChangePage;
		}

		if (!StringUtils.isBlank(form.getToken()))
		{
			try
			{
				//Call to reset Password with stateToken
				((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
				final String status = ((SiteOneCustomerFacade) customerFacade).resetPassword(form.getToken(), form.getPwd());
				if (status.equalsIgnoreCase("SUCCESS"))
				{
					GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
							"account.confirmation.password.updated");
				}
			}
			catch (final InvalidTokenException invalidTokenException)
			{
				LOG.error(invalidTokenException.getMessage(), invalidTokenException);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
						"system.error.link.expired.title");
			}
			catch (final ResourceAccessException resourceAccessException)
			{
				return REDIRECT_LOGIN;
			}
			catch (final PasswordPolicyViolationException passwordPolicyViolationException)
			{
				model.addAttribute(form);
				storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PWD_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PWD_CMS_PAGE));
				GlobalMessages.addErrorMessage(model, "account.password.reset.invalid.password");
				return ControllerConstants.Views.Pages.Password.PasswordResetChangePage;
			}
			catch (final RecentlyUsedPasswordException oktaRecentlyUsedPasswordException)
			{
				LOG.error(oktaRecentlyUsedPasswordException);
				model.addAttribute(form);
				storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PWD_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PWD_CMS_PAGE));
				GlobalMessages.addErrorMessage(model, "form.global.error.recent.password");
				return ControllerConstants.Views.Pages.Password.PasswordResetChangePage;
			}
			catch (final TokenInvalidatedException tokenInvalidatedException)
			{
				LOG.error(tokenInvalidatedException.getMessage(), tokenInvalidatedException);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.invalid.token");
			}

		}
		return REDIRECT_LOGIN;
	}

	//Set Password
	@GetMapping("/setPassword")
	public String getSetPassword(@RequestParam(required = false) final String token, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (StringUtils.isBlank(token))
		{
			return REDIRECT_LOGIN;
		}

		final long tokenValiditySeconds = Config.getLong(SiteoneintegrationConstants.TOKEN_VALIDITY_CREATE_PASSWORD, 1800);

		boolean isValidToken = false;

		try
		{
			isValidToken = ((SiteOneCustomerFacade) customerFacade).verifySetPasswordToken(token, tokenValiditySeconds);
		}
		catch (final IllegalArgumentException illegalArgumentException)
		{
			LOG.error(illegalArgumentException.getMessage(), illegalArgumentException);

			if ("user for token not found".equalsIgnoreCase(illegalArgumentException.getMessage())
					|| "Invalid token".equalsIgnoreCase(illegalArgumentException.getMessage()))
			{
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.invalid.token");
			}
			else
			{
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
						"system.error.link.expired.title");
			}

			return REDIRECT_LOGIN;
		}
		catch (final TokenInvalidatedException tokenInvalidatedException)
		{
			LOG.error(tokenInvalidatedException.getMessage(), tokenInvalidatedException);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.invalid.token");
			return REDIRECT_LOGIN;
		}

		if (!isValidToken)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.invalid.token");
			return REDIRECT_LOGIN;
		}

		final SiteOneSetPwdForm form = new SiteOneSetPwdForm();

		form.setToken(token);
		model.addAttribute(form);
		storeCmsPageInModel(model, getContentPageForLabelOrId(SET_PWD_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SET_PWD_CMS_PAGE));
		//model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("setPwd.title"));
		return ControllerConstants.Views.Pages.Password.SetPasswordPage;

	}


	@PostMapping("/setPassword")
	public String setPassword(@Valid final SiteOneSetPwdForm form,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectModel,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		getSiteOneSetPwdFormValidator().validate(form, bindingResult);
		if (bindingResult.hasErrors())
		{
			prepareErrorMessage(model, SET_PWD_CMS_PAGE);
			return ControllerConstants.Views.Pages.Password.SetPasswordPage;
		}
		if (!StringUtils.isBlank(form.getToken()))
		{
			try
			{
				((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
				((SiteOneCustomerFacade) customerFacade).activateUser(form.getToken());
				final String status = ((SiteOneCustomerFacade) customerFacade).setPasswordForUser(form.getToken(), form.getPwd());

				if (null != status && status.equalsIgnoreCase(SiteoneCoreConstants.OKTA_USER_STATUS_ACTIVE))
				{

					request.getSession().setAttribute(IS_NEW_CUSTOMR, "New");

					GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
							"account.confirmation.password.updated");

				}

			}
			catch (final ResourceAccessException resourceAccessException)
			{
				LOG.error(resourceAccessException.getMessage(), resourceAccessException);
				return REDIRECT_LOGIN;
			}
			catch (final PasswordPolicyViolationException passwordPolicyViolationException)
			{
				LOG.error(passwordPolicyViolationException.getMessage(), passwordPolicyViolationException);
				model.addAttribute(form);
				storeCmsPageInModel(model, getContentPageForLabelOrId(SET_PWD_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SET_PWD_CMS_PAGE));
				//model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("setPwd.title"));
				GlobalMessages.addErrorMessage(model, "account.password.reset.invalid.password");
				return ControllerConstants.Views.Pages.Password.SetPasswordPage;
			}
			catch (final IllegalArgumentException illegalArgumentException)
			{
				LOG.error(illegalArgumentException.getMessage(), illegalArgumentException);

				if ("user for token not found".equalsIgnoreCase(illegalArgumentException.getMessage())
						|| "Invalid token".equalsIgnoreCase(illegalArgumentException.getMessage()))
				{
					GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.invalid.token");
				}
				else
				{
					GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
							"system.error.link.expired.title");
				}

			}
			catch (final TokenInvalidatedException tokenInvalidatedException)
			{
				LOG.error(tokenInvalidatedException.getMessage(), tokenInvalidatedException);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.invalid.token");
			}

		}
		return REDIRECT_LOGIN;
	}


	/**
	 * @return the siteOneUpdatePwdValidator
	 */
	public SiteOneUpdatePwdValidator getSiteOneUpdatePwdValidator()
	{
		return siteOneUpdatePwdValidator;
	}

	/**
	 * @param siteOneUpdatePwdValidator
	 *           the siteOneUpdatePwdValidator to set
	 */
	public void setSiteOneUpdatePwdValidator(final SiteOneUpdatePwdValidator siteOneUpdatePwdValidator)
	{
		this.siteOneUpdatePwdValidator = siteOneUpdatePwdValidator;
	}

	/**
	 * @return the siteOneSetPwdFormValidator
	 */
	public SiteOneSetPwdValidator getSiteOneSetPwdFormValidator()
	{
		return siteOneSetPwdFormValidator;
	}

	/**
	 * @param siteOneSetPwdFormValidator
	 *           the siteOneSetPwdFormValidator to set
	 */
	public void setSiteOneSetPwdFormValidator(final SiteOneSetPwdValidator siteOneSetPwdFormValidator)
	{
		this.siteOneSetPwdFormValidator = siteOneSetPwdFormValidator;
	}

	/**
	 * Prepares the view to display an error message
	 *
	 * @throws CMSItemNotFoundException
	 */
	protected void prepareErrorMessage(final Model model, final String page) throws CMSItemNotFoundException
	{
		GlobalMessages.addErrorMessage(model, "form.global.error");
		storeCmsPageInModel(model, getContentPageForLabelOrId(page));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(page));
	}

}
