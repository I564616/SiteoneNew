package com.siteone.v2.controller;

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siteone.event.SiteOnePasswordResetEvent;
import com.siteone.facades.customer.SiteOneCustomerFacade;


/**
 * Controller for the forgotten password pages. Supports requesting a password reset email as well as changing the
 * password once you have got the token that was sent via email.
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/login/pw")
@Tag(name = "SiteOne Password Management")
public class SiteOnePasswordResetController {

	private static final Logger LOG = Logger.getLogger(SiteOnePasswordResetController.class);

	private static final String SUCCESS = "Success";
	private static final String FAILURE = "Failure";
	private static final String NOTOKEN = "Token is missing.";
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "eventService")
	private EventService eventService;
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	
	@PostMapping("/forgetPasswordRequest")
	@ResponseBody
	@Operation(operationId = "resetPassword", summary = "Send reset password mail", description = "Send reset password mail")
	@ApiBaseSiteIdParam
	public String passwordRequest(@RequestParam("email") final String email)
	{
		try
		{
			eventService.publishEvent(initializeEvent(new SiteOnePasswordResetEvent(email)));
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method passwordRequest ");
	    }
	}
	
	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event)
	{
		event.setSite(baseSiteService.getCurrentBaseSite());
		event.setLanguage(commonI18NService.getCurrentLanguage());
		event.setCurrency(commonI18NService.getCurrentCurrency());
		return event;
	}
	
	@PostMapping("/unlock-request")
	@Operation(operationId = "unlock-request", summary = "Send unlock request mail", description = "Send unlock request mail")
	@ApiBaseSiteIdParam
	public @ResponseBody String unlockUserRequest(@RequestParam("email") final String email)
	{
		try
		{
			((SiteOneCustomerFacade) customerFacade).unlockUserRequest(email);
			Gson gson = new Gson();
			return gson.toJson(SUCCESS);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method unlockUserRequest ");
	    }
	}

	@GetMapping(value = "/unlock")
	@Operation(operationId = "unlock-account", summary = "unlock account by token", description = "unlock account by token")
	@ApiBaseSiteIdParam
	public @ResponseBody String unlockUser(@RequestParam("token") final String token, HttpServletRequest request)
	{

		Gson gson = new Gson();
		if (StringUtils.isBlank(token))
		{
			return gson.toJson(NOTOKEN);
		}

		try
		{
			((SiteOneCustomerFacade) customerFacade).getClientInformation(request);
			((SiteOneCustomerFacade) customerFacade).unlockUser(token);
			return gson.toJson(SUCCESS);
		}
		catch (final TokenInvalidatedException tokenInvalidatedException)
		{
			LOG.error(tokenInvalidatedException.getMessage(), tokenInvalidatedException);
			return gson.toJson(FAILURE);
		}
		catch (final IllegalArgumentException illegalArgumentException)
		{
			LOG.error(illegalArgumentException.getMessage(), illegalArgumentException);
			return gson.toJson(FAILURE);
		}
		catch(Exception ex)
	    {
	        LOG.error(ex);
	        throw new RuntimeException("Exception occurred while calling through method unlockUser ");
	    }
	}

}
