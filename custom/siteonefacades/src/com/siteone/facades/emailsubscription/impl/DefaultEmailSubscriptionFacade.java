/**
 *
 */
package com.siteone.facades.emailsubscription.impl;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.IOException;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.model.EmailSubscriptionsModel;
import com.siteone.core.services.EmailSubscriptionsService;
import com.siteone.facades.emailsubscription.EmailSubscriptionFacade;
import com.siteone.facades.emailsubscriptions.data.EmailSubscriptionsData;
import com.siteone.integration.watson.EmailSignUpService;
import org.springframework.web.client.RestClientException;


/**
 * @author 1091124
 *
 */
public class DefaultEmailSubscriptionFacade implements EmailSubscriptionFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultEmailSubscriptionFacade.class);

	private EmailSubscriptionsService emailSubscriptionsService;
	private Populator<EmailSubscriptionsData, EmailSubscriptionsModel> emailSubscriptionReversePopulator;
	private ModelService modelService;

	@Resource(name = "emailSignUpService")
	private EmailSignUpService emailSignUpService;

	@Override
	public void subscribeEmail(final EmailSubscriptionsData emailSubscriptionsData) throws ResourceAccessException, IOException, RestClientException
	{
		try
		{
			emailSignUpService.emailSignUp(emailSubscriptionsData);
			final EmailSubscriptionsModel emailSubscriptionsModel = getModelService().create(EmailSubscriptionsModel.class);
			getEmailSubscriptionReversePopulator().populate(emailSubscriptionsData, emailSubscriptionsModel);
			getEmailSubscriptionsService().setSubscribedEmail(emailSubscriptionsModel);
		}
		catch (final ResourceAccessException | IOException e)
		{
			LOG.error(e.getMessage(), e);
			throw e;
		}
		catch (final RestClientException e)
		{
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * @return the emailSubscriptionsService
	 */
	public EmailSubscriptionsService getEmailSubscriptionsService()
	{
		return emailSubscriptionsService;
	}

	/**
	 * @param emailSubscriptionsService
	 *           the emailSubscriptionsService to set
	 */
	public void setEmailSubscriptionsService(final EmailSubscriptionsService emailSubscriptionsService)
	{
		this.emailSubscriptionsService = emailSubscriptionsService;
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.emailsubscription.EmailSubscription#SubscribedEmailId(java.lang.String)
	 */

	/**
	 * @return the emailSubscriptionReversePopulator
	 */
	public Populator<EmailSubscriptionsData, EmailSubscriptionsModel> getEmailSubscriptionReversePopulator()
	{
		return emailSubscriptionReversePopulator;
	}

	/**
	 * @param emailSubscriptionReversePopulator
	 *           the emailSubscriptionReversePopulator to set
	 */
	public void setEmailSubscriptionReversePopulator(
			final Populator<EmailSubscriptionsData, EmailSubscriptionsModel> emailSubscriptionReversePopulator)
	{
		this.emailSubscriptionReversePopulator = emailSubscriptionReversePopulator;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
