/**
 *
 */
package com.siteone.facade.email.impl;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;
import com.siteone.integration.services.briteVerify.SiteOneBriteVerifyWebService;
import com.siteone.facade.email.BriteVerifyFacade;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import org.springframework.web.client.RestClientException;



/**
 * @author VenkatB
 *
 */
public class DefaultBriteVerifyFacade implements BriteVerifyFacade
{
	@Resource(name = "siteOneBriteVerifyWebService")
	private SiteOneBriteVerifyWebService siteOneBriteVerifyWebService;

	private static final Logger LOG = Logger.getLogger(DefaultBriteVerifyFacade.class);

	@Override
	public String validateEmailId(final String email)
	{
		try
		{
			String briteVerifyResponse = siteOneBriteVerifyWebService.validateEmailId(email);
			return briteVerifyResponse;
		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to establish connection BriteVerify API", resourceAccessException);
			throw new ServiceUnavailableException("404");
		}
		catch (final RestClientException restClientException)
		{
			LOG.error("Not able to establish connection BriteVerify API", restClientException);
			throw new ServiceUnavailableException("404");
		}

	}
}
