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
package com.siteone.storefront.interceptors.beforecontroller;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.interceptors.BeforeControllerHandler;

import java.lang.annotation.Annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.method.HandlerMethod;

import com.siteone.storefront.security.evaluator.impl.RequireHardLoginEvaluator;


/**
 */
public class RequireHardLoginBeforeControllerHandler implements BeforeControllerHandler
{
	private static final Logger LOG = Logger.getLogger(RequireHardLoginBeforeControllerHandler.class);

	public static final String SECURE_GUID_SESSION_KEY = "acceleratorSecureGUID";
	public static final String CID_CART_ABANDONMENT_QUERYSTRING = "cid=em-tr-abandoned_cart-ready_to_checkout";


	private String loginUrl;
	private String loginAndCheckoutUrl;
	private RedirectStrategy redirectStrategy;
	private RequireHardLoginEvaluator requireHardLoginEvaluator;

	protected String getLoginUrl()
	{
		return loginUrl;
	}

	public void setLoginUrl(final String loginUrl)
	{
		this.loginUrl = loginUrl;
	}

	protected RedirectStrategy getRedirectStrategy()
	{
		return redirectStrategy;
	}

	public void setRedirectStrategy(final RedirectStrategy redirectStrategy)
	{
		this.redirectStrategy = redirectStrategy;
	}

	public String getLoginAndCheckoutUrl()
	{
		return loginAndCheckoutUrl;
	}

	public void setLoginAndCheckoutUrl(final String loginAndCheckoutUrl)
	{
		this.loginAndCheckoutUrl = loginAndCheckoutUrl;
	}

	protected RequireHardLoginEvaluator getRequireHardLoginEvaluator()
	{
		return requireHardLoginEvaluator;
	}

	public void setRequireHardLoginEvaluator(final RequireHardLoginEvaluator requireHardLoginEvaluator)
	{
		this.requireHardLoginEvaluator = requireHardLoginEvaluator;
	}

	@Override
	public boolean beforeController(final HttpServletRequest request, final HttpServletResponse response,
			final HandlerMethod handler) throws Exception
	{
		// We only care if the request is secure
		if (request.isSecure())
		{
			// Check if the handler has our annotation
			final RequireHardLogIn annotation = findAnnotation(handler, RequireHardLogIn.class);
			if (annotation != null)
			{
				final boolean redirect = requireHardLoginEvaluator.evaluate(request, response);

				if (redirect)
				{
					LOG.warn("Redirection required");
					getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(request));
					return false;
				}
			}
		}

		return true;
	}

	protected String getRedirectUrl(final HttpServletRequest request)
	{
		if (request != null && request.getServletPath().contains("checkout"))
		{

			if (request.getQueryString() != null && request.getQueryString().contains(CID_CART_ABANDONMENT_QUERYSTRING))
			{
				final String loginAndCheckoutUrl = getLoginAndCheckoutUrl();
				if (!loginAndCheckoutUrl.contains(CID_CART_ABANDONMENT_QUERYSTRING))
				{
					final String modifiedLoginAndCheckoutUrl = loginAndCheckoutUrl + "?" + CID_CART_ABANDONMENT_QUERYSTRING;
					setLoginAndCheckoutUrl(modifiedLoginAndCheckoutUrl);
				}
			}
			return getLoginAndCheckoutUrl();
		}
		else
		{

			return getLoginUrl();
		}
	}

	protected <T extends Annotation> T findAnnotation(final HandlerMethod handlerMethod, final Class<T> annotationType)
	{
		// Search for method level annotation
		final T annotation = handlerMethod.getMethodAnnotation(annotationType);
		if (annotation != null)
		{
			return annotation;
		}

		// Search for class level annotation
		return AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), annotationType);
	}
}
