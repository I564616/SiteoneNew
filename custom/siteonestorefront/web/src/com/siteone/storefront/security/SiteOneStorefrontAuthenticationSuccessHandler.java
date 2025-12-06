/**
 *
 */
package com.siteone.storefront.security;


import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.security.StorefrontAuthenticationSuccessHandler;
import de.hybris.platform.acceleratorstorefrontcommons.strategy.impl.MergingCartRestorationStrategy;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.core.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AccessControlException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.util.WebUtils;

import com.sap.security.core.server.csi.util.URLDecoder;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.customer.impl.DefaultSiteOneCustomerFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1219341
 *
 */
public class SiteOneStorefrontAuthenticationSuccessHandler extends StorefrontAuthenticationSuccessHandler
{
	private MergingCartRestorationStrategy mergingCartRestorationStrategy;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	private static final Logger LOG = Logger.getLogger(StorefrontAuthenticationSuccessHandler.class);

	public void onAuthenticationSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response,
			String targetUrl, final String newContextPath, final Cookie cookie, final SavedRequest savedRequest)
			throws IOException, AccessControlException
	{
		final String loginTobuy = request.getParameter("loginToBuy");
		final Cookie newCookie = new Cookie("loginToBuyCk", null);
		if (loginTobuy != null && loginTobuy.equalsIgnoreCase("true"))
		{
			newCookie.setValue(loginTobuy);
			newCookie.setSecure(true);
			newCookie.setPath("/");
			response.addCookie(newCookie);
		}



		ESAPI.initialize("org.owasp.esapi.reference.DefaultSecurityConfiguration");
		ESAPI.httpUtilities().setCurrentHTTP(request, response);
		if(!getCustomerFacade().getCurrentCustomer().getUnit().isActive()) {
			response.sendRedirect(newContextPath + "/");
		}
		else if (savedRequest != null)
		{
			targetUrl = StringUtils.substringAfter(savedRequest.getRedirectUrl(), request.getContextPath());
			ESAPI.httpUtilities().sendRedirect(newContextPath + targetUrl);
		}
		else if (cookie != null && cookie.getValue() != null && !cookie.getValue().isEmpty())
		{
			String defaultB2BUnit = null;
			targetUrl = StringUtils.substringAfter(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.displayName()),
					request.getContextPath());
			if (storeSessionFacade.getSessionShipTo() != null
					&& StringUtils.isNotEmpty(storeSessionFacade.getSessionShipTo().getUid()))
			{
				defaultB2BUnit = storeSessionFacade.getSessionShipTo().getUid();
			}
			cookie.setValue(null);
			cookie.setPath("/");
			ESAPI.httpUtilities().addCookie(response, cookie);
			if (targetUrl.contains("cart"))
			{
				ESAPI.httpUtilities().sendRedirect(newContextPath + targetUrl);
			}
			else if (targetUrl.equals(""))
			{
				response.sendRedirect(newContextPath + "/");
			}
			else
			{
				if ((targetUrl.contains("buy-again")) && null != defaultB2BUnit)
				{
					ESAPI.httpUtilities().sendRedirect(newContextPath + targetUrl + defaultB2BUnit
							+ "?q=%3Alastpurchaseddate-desc%3AsoLastPurchasedDateFilter%3APast 60 Days");
				}
				else if ((targetUrl.contains("orders")) && null != defaultB2BUnit)
                                {
                                       ESAPI.httpUtilities().sendRedirect(newContextPath + targetUrl + defaultB2BUnit);
                                }
				else
				{
					ESAPI.httpUtilities().sendRedirect(newContextPath + targetUrl);
				}
			}
		}
		else if (targetUrl.contains(request.getContextPath()))
		{
			if (targetUrl.contains("/login/checkout"))
			{
				targetUrl = newContextPath + "/cart";
			}
			else if (targetUrl.contains("/login"))
			{

				targetUrl = newContextPath + "/";
			}
			response.sendRedirect(targetUrl.replace(request.getContextPath(), newContextPath));
		}
		else
		{
			response.sendRedirect(newContextPath + "/");
		}
	}


	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException
	{
		//if redirected from some specific url, need to remove the cachedRequest to force use defaultTargetUrl
		final RequestCache requestCache = new HttpSessionRequestCache();
		final SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null)
		{
			for (final String redirectUrlForceDefaultTarget : getListRedirectUrlsForceDefaultTarget())
			{
				if (savedRequest.getRedirectUrl().contains(redirectUrlForceDefaultTarget))
				{
					requestCache.removeRequest(request, response);
					break;
				}
			}
		}
		if (getCustomerFacade().getCurrentCustomer().getUnit().isActive()) {
			getCustomerFacade().loginSuccess();
		}
		request.setAttribute(WebConstants.CART_MERGED, Boolean.FALSE);
		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		final String targetUrl = request.getHeader("Referer");
		final Cookie cookie = WebUtils.getCookie(request, "urllogin");

		//get the newContextPath based on languagePreference and current location
		final String newContextPath = ((DefaultSiteOneCustomerFacade) customerFacade)
				.getContextPathBasedOnLanguagePreference(username);
		// Check if the user is in role admingroup
		if (!isAdminAuthority(authentication))
		{
			((SiteOneCartFacade) cartFacade).restoreCartAndMergeForLogin(request);
			getBruteForceAttackCounter().resetUserCounter(getCustomerFacade().getCurrentCustomerUid());
			//calculate the redirect Url and send redirect
			try
			{
				onAuthenticationSuccessRedirect(request, response, targetUrl, newContextPath, cookie, savedRequest);
			}
			catch (final AccessControlException e)
			{
				LOG.error(e.getMessage());
			}
			if (savedRequest == null && getCustomerFacade().getCurrentCustomer().getUnit().isActive()) {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		}
		else
		{
			LOG.warn("Invalidating session for user in the " + Constants.USER.ADMIN_USERGROUP + " group");
			invalidateSession(request, response);
		}
	}

	/**
	 * @return the mergingCartRestorationStrategy
	 */
	public MergingCartRestorationStrategy getMergingCartRestorationStrategy()
	{
		return mergingCartRestorationStrategy;
	}

	/**
	 * @param mergingCartRestorationStrategy
	 *           the mergingCartRestorationStrategy to set
	 */
	public void setMergingCartRestorationStrategy(final MergingCartRestorationStrategy mergingCartRestorationStrategy)
	{
		this.mergingCartRestorationStrategy = mergingCartRestorationStrategy;
	}
}
