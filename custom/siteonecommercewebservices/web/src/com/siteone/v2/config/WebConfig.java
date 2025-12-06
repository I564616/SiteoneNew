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
package com.siteone.v2.config;


import de.hybris.platform.servicelayer.config.ConfigurationService;
import com.siteone.request.mapping.handler.CommerceHandlerMapping;

import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import com.google.common.collect.ImmutableSet;


/**
 * Spring configuration which replace <mvc:annotation-driven> tag. It allows override default
 * RequestMappingHandlerMapping with our own mapping handler
 */

@Configuration
@ImportResource({ "WEB-INF/config/v2/springmvc-v2-servlet.xml" })
public class WebConfig extends DelegatingWebMvcConfiguration
{
	private static final String PASSWORD_AUTHORIZATION_SCOPE = "siteonecommercewebservices.oauth2.password.scope";
	private static final String CLIENT_CREDENTIAL_AUTHORIZATION_SCOPE = "siteonecommercewebservices.oauth2.clientCredentials.scope";
	private static final String AUTHORIZATION_URL = "siteonecommercewebservices.oauth2.tokenUrl";

	private static final String DESC = "siteonecommercewebservices.v2.description";
	private static final String TITLE = "siteonecommercewebservices.v2.title";
	private static final String VERSION = "siteonecommercewebservices.v2.version";
	private static final String LICENSE = "siteonecommercewebservices.v2.license";
	private static final String LICENSE_URL = "siteonecommercewebservices.v2.license.url";

	private static final String PASSWORD_AUTHORIZATION_NAME = "oauth2_Password";
	private static final String CLIENT_CREDENTIAL_AUTHORIZATION_NAME = "oauth2_client_credentials";

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "messageConvertersV2")
	private List<HttpMessageConverter<?>> messageConvertersV2;

	@Resource
	private List<HandlerExceptionResolver> exceptionResolversV2;

	private ApplicationContext applicationContext;

	@SuppressWarnings({ "deprecation", "squid:CallToDeprecatedMethod" })
	@Override
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping(final ContentNegotiationManager mvcContentNegotiationManager,
			final FormattingConversionService mvcConversionService, final ResourceUrlProvider mvcResourceUrlProvider)
	{
		final CommerceHandlerMapping handlerMapping = new CommerceHandlerMapping("v2");
		handlerMapping.setOrder(0);
		handlerMapping.setDetectHandlerMethodsInAncestorContexts(true);
		handlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
		handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager);
		/*
		 * For more details about deprecation see: https://github.com/spring-projects/spring-framework/issues/24179
		 */
		handlerMapping.setUseRegisteredSuffixPatternMatch(true);
		return handlerMapping;
	}

	@Override
	protected void configureMessageConverters(final List<HttpMessageConverter<?>> converters)
	{
		converters.addAll(messageConvertersV2);
		super.addDefaultHttpMessageConverters(converters);
	}

	@Override
	protected void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> exceptionResolvers)
	{
		final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
		exceptionHandlerExceptionResolver.setApplicationContext(applicationContext);
		exceptionHandlerExceptionResolver.setContentNegotiationManager(mvcContentNegotiationManager());
		exceptionHandlerExceptionResolver.setMessageConverters(getMessageConverters());
		exceptionHandlerExceptionResolver.afterPropertiesSet();

		exceptionResolvers.add(exceptionHandlerExceptionResolver);
		exceptionResolvers.addAll(exceptionResolversV2);
		exceptionResolvers.add(new ResponseStatusExceptionResolver());
		exceptionResolvers.add(new DefaultHandlerExceptionResolver());
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException //NOSONAR
	{
		super.setApplicationContext(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer)
	{
		configurer.favorPathExtension(false).favorParameter(true);
	}

}
