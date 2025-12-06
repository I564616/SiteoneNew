/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.v2.controller;


import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.access.annotation.Secured;

import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import com.siteone.auth.data.LogoutResponse;

import org.apache.log4j.Logger;


@Controller
@RequestMapping(value = "/{baseSiteId}/customers")
@Tag(name = "Siteone Logout")
public class LogoutController extends BaseCommerceController
{

	@Autowired
	private TokenStore tokenStore;
	private static final Logger LOG = Logger.getLogger(LogoutController.class);

	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PostMapping("/current/logout")
	@ResponseBody
	@Operation(operationId = "logout", summary = "invalidate the token of logged-in customer", description = "invalidate the token of logged-in customer")
	@ApiBaseSiteIdParam
	public LogoutResponse logout(final HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		final LogoutResponse logoutResponse = new LogoutResponse();
        if (authHeader != null) {
        	try {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
            tokenStore.removeRefreshToken(refreshToken);
            logoutResponse.setSuccess(true);
        	} catch (Exception e) {
        		LOG.error(e);
        		logoutResponse.setSuccess(false);
             }  
        }

        return logoutResponse;
	}

}