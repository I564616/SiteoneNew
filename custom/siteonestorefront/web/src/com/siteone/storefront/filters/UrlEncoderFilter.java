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
package com.siteone.storefront.filters;

import de.hybris.platform.acceleratorfacades.urlencoder.UrlEncoderFacade;
import de.hybris.platform.acceleratorfacades.urlencoder.data.UrlEncoderData;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import com.siteone.storefront.web.wrappers.UrlEncodeHttpRequestWrapper;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * This filter inspects the url and inject the url attributes if any for that CMSSite. Calls facades to fetch the list
 * of attributes and encode them in the URL.
 */
public class UrlEncoderFilter extends OncePerRequestFilter
{
	private static final Logger LOG = Logger.getLogger(UrlEncoderFilter.class.getName());
	private static final String MATCH_ISOCODE = "/(en|es)/.*";
	private static final String EN_ISOCODE_URI = "/en";
	private static final String ES_ISOCODE_URI = "/es";
	private static final String FORWARD_SLASH = "/";
	private static final String ROBOTS_URL = "/robots.txt";
	private static final String SITEMAP_URL = "/sitemap.xml";
	private static final String SITEMAP_MATCH = "/sitemap/";

	private UrlEncoderFacade urlEncoderFacade;
	private SessionService sessionService;

	@Resource(name = "i18nService")
	private I18NService i18nService;
	
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug(" The incoming URL : [" + request.getRequestURL().toString() + "]");
		}
		if(!(request.getRequestURI()).matches(MATCH_ISOCODE) && !(request.getRequestURI()).equalsIgnoreCase(ROBOTS_URL) 
				&& (!(request.getRequestURI()).equalsIgnoreCase(SITEMAP_URL) || !(request.getRequestURI()).startsWith(SITEMAP_MATCH))) {
			String encodedRedirectUrl = null;
			if((request.getRequestURI().equalsIgnoreCase(EN_ISOCODE_URI)) || (request.getRequestURI().equalsIgnoreCase(ES_ISOCODE_URI))) {
				encodedRedirectUrl = response.encodeRedirectURL((request.getRequestURL().toString()).replace(request.getRequestURI(),"")
						+FORWARD_SLASH + getI18nService().getCurrentLocale() + FORWARD_SLASH);
			}
			else if (request.getRequestURI().equalsIgnoreCase(FORWARD_SLASH)){
				encodedRedirectUrl = response.encodeRedirectURL((request.getRequestURL().toString())
						+ getI18nService().getCurrentLocale() + FORWARD_SLASH);
			}
			else {
				encodedRedirectUrl = response.encodeRedirectURL((request.getRequestURL().toString()).replace(request.getRequestURI(),"")
						+FORWARD_SLASH + getI18nService().getCurrentLocale() + request.getRequestURI());
			}
			if(request.getQueryString() != null && !(request.getQueryString().isEmpty())) {
				response.sendRedirect(encodedRedirectUrl + "?" + request.getQueryString());
			}
			else {
				response.sendRedirect(encodedRedirectUrl);
			}
			
		}
		
		final List<UrlEncoderData> currentUrlEncoderDatas = getUrlEncoderFacade().getCurrentUrlEncodingData();
		if (currentUrlEncoderDatas != null && !currentUrlEncoderDatas.isEmpty())
		{
			final String currentPattern = getSessionService().getAttribute(WebConstants.URL_ENCODING_ATTRIBUTES);
			final String newPattern = getUrlEncoderFacade().calculateAndUpdateUrlEncodingData(request.getRequestURI().toString(),
					request.getContextPath());
			final String newPatternWithSlash = "/" + newPattern;
			if (!StringUtils.equalsIgnoreCase(currentPattern, newPatternWithSlash))
			{
				getUrlEncoderFacade().updateSiteFromUrlEncodingData();
				getSessionService().setAttribute(WebConstants.URL_ENCODING_ATTRIBUTES, newPatternWithSlash);
			}

			final UrlEncodeHttpRequestWrapper wrappedRequest = new UrlEncodeHttpRequestWrapper(request, newPattern);
			wrappedRequest.setAttribute(WebConstants.URL_ENCODING_ATTRIBUTES, newPatternWithSlash);
			wrappedRequest.setAttribute("originalContextPath",
					StringUtils.isBlank(request.getContextPath()) ? "/" : request.getContextPath());
			if (LOG.isDebugEnabled())
			{
				LOG.debug("ContextPath=[" + wrappedRequest.getContextPath() + "]" + " Servlet Path= ["
						+ wrappedRequest.getServletPath() + "]" + " Request Url= [" + wrappedRequest.getRequestURL() + "]");
			}
			filterChain.doFilter(wrappedRequest, response);
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(" No URL attributes defined");
			}
			request.setAttribute(WebConstants.URL_ENCODING_ATTRIBUTES, "");
			filterChain.doFilter(request, response);
		}
	}

	protected UrlEncoderFacade getUrlEncoderFacade()
	{
		return urlEncoderFacade;
	}

	public void setUrlEncoderFacade(final UrlEncoderFacade urlEncoderFacade)
	{
		this.urlEncoderFacade = urlEncoderFacade;
	}

	protected SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
	
	protected I18NService getI18nService()
	{
		return i18nService;
	}
}