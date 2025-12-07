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
package com.siteone.core.email.service.impl;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailGenerationService;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageTemplateModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.commons.model.renderer.RendererTemplateModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.siteone.core.model.ContactSellerProcessModel;
import com.siteone.core.model.ExpiredQuoteUpdProcessModel;
import com.siteone.core.model.KountDeclineProcessModel;
import com.siteone.core.model.NotifyQuoteStatusProcessModel;
import com.siteone.core.model.PIMBatchFailureReportNotificationProcessModel;
import com.siteone.core.model.QuoteApprovalProcessModel;
import com.siteone.core.model.QuoteToOrderStatusProcessModel;
import com.siteone.core.model.RequestQuoteProcessModel;


/**
 * Service to render email message.
 */
public class DefaultSiteoneEmailGenerationService extends DefaultEmailGenerationService
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteoneEmailGenerationService.class);
	private EmailService emailService;

	@Resource(name = "modelService")
	private ModelService modelService;

	/**
	 * @return the emailService
	 */
	@Override
	public EmailService getEmailService()
	{
		return emailService;
	}

	/**
	 * @param emailService
	 *           the emailService to set
	 */
	@Override
	public void setEmailService(final EmailService emailService)
	{
		this.emailService = emailService;
	}

	@Override
	public EmailMessageModel generate(final BusinessProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		ServicesUtil.validateParameterNotNull(emailPageModel, "EmailPageModel cannot be null");
		Assert.isInstanceOf(EmailPageTemplateModel.class, emailPageModel.getMasterTemplate(),
				"MasterTemplate associated with EmailPageModel should be EmailPageTemplate");

		final EmailPageTemplateModel emailPageTemplateModel = (EmailPageTemplateModel) emailPageModel.getMasterTemplate();
		final RendererTemplateModel bodyRenderTemplate = emailPageTemplateModel.getHtmlTemplate();
		Assert.notNull(bodyRenderTemplate, "HtmlTemplate associated with MasterTemplate of EmailPageModel cannot be null");
		final RendererTemplateModel subjectRenderTemplate = emailPageTemplateModel.getSubject();
		Assert.notNull(subjectRenderTemplate, "Subject associated with MasterTemplate of EmailPageModel cannot be null");

		final EmailMessageModel emailMessageModel;
		//This call creates the context to be used for rendering of subject and body templates.
		final AbstractEmailContext<BusinessProcessModel> emailContext = getEmailContextFactory().create(businessProcessModel,
				emailPageModel, bodyRenderTemplate);

		if (emailContext == null)
		{
			LOG.error("Failed to create email context for businessProcess [" + businessProcessModel + "]");
			throw new IllegalStateException("Failed to create email context for businessProcess [" + businessProcessModel + "]");
		}
		else
		{
			if (!validate(emailContext))
			{
				LOG.error("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
						+ ReflectionToStringBuilder.toString(emailContext));
				throw new IllegalStateException("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
						+ ReflectionToStringBuilder.toString(emailContext));
			}

			final StringWriter subject = new StringWriter();
			getRendererService().render(subjectRenderTemplate, emailContext, subject);

			final StringWriter body = new StringWriter();
			getRendererService().render(bodyRenderTemplate, emailContext, body);

			emailMessageModel = this.createEmailMessage(subject.toString(), body.toString(), emailContext, businessProcessModel);

			if (LOG.isDebugEnabled())
			{
				LOG.debug("Email Subject: " + emailMessageModel.getSubject());
				LOG.debug("Email Body: " + emailMessageModel.getBody());
			}

		}
		if (!businessProcessModel.getAttachments().isEmpty())
		{
			final List<EmailAttachmentModel> attachments = businessProcessModel.getAttachments();
			emailMessageModel.setAttachments(attachments);
			modelService.saveAll(emailMessageModel);
		}
		return emailMessageModel;
	}

	protected EmailMessageModel createEmailMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext, final BusinessProcessModel businessProcessModel)
	{
		final List<EmailAddressModel> toEmails = new ArrayList<>();
		final Set<EmailAddressModel> uniqueToEmails = new HashSet<>();
		final List<EmailAddressModel> ccEmails = new ArrayList<>();
		final List<EmailAddressModel> bccEmails = new ArrayList<>();

		//Added to override emails for testing purpose. This flag should be disabled(false) in production env.
		final boolean isEmailOverride = Config.getBoolean("email.override", false);

		if (isEmailOverride)
		{
			final String toRecipients = Config.getString("email.override.recipients.to", "");
			final String[] recipients = toRecipients.split(";");
			LOG.warn("Email overridden to recipients " + recipients);
			for (final String rec : recipients)
			{
				uniqueToEmails.add(getEmailService().getOrCreateEmailAddressForEmail(rec, emailContext.getToDisplayName()));
			}
			if (isDomainWhiteListed(emailContext.getToEmail()))
			{
				uniqueToEmails.add(
						getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(), emailContext.getToDisplayName()));
			}
		}
		else
		{
			if (null != businessProcessModel
					&& ((businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("orderConfirmationEmailProcess"))
							|| (businessProcessModel.getProcessDefinitionName()
									.equalsIgnoreCase("pimBatchFailureReportNotificationEmailProcess")))
					|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("kountDeclineProcess")
							|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("orderPendingApprovalEmailProcess"))
							|| businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("quoteApprovalEmailProcess")
							|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("contactSellerEmailProcess"))
							|| businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("requestQuoteEmailProcess")
							|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("notifyQuoteStatusEmailProcess"))
							|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("expiredQuoteUpdEmailProcess"))
							|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("quoteToOrderStatusEmailProcess"))))
			{
				String[] toEmailsList;
				if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("orderConfirmationEmailProcess")
						|| (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("orderPendingApprovalEmailProcess")))
				{
					toEmailsList = ((OrderProcessModel) businessProcessModel).getToEmails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("kountDeclineProcess"))
				{
					toEmailsList = ((KountDeclineProcessModel) businessProcessModel).getToEmails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("quoteApprovalEmailProcess"))
				{
					toEmailsList = ((QuoteApprovalProcessModel) businessProcessModel).getToEmails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("contactSellerEmailProcess"))
				{
					toEmailsList = ((ContactSellerProcessModel) businessProcessModel).getToEmails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("requestQuoteEmailProcess"))
				{
					toEmailsList = ((RequestQuoteProcessModel) businessProcessModel).getToEmails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("notifyQuoteStatusEmailProcess"))
				{
					toEmailsList = ((NotifyQuoteStatusProcessModel) businessProcessModel).getToMails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("expiredQuoteUpdEmailProcess"))
				{
					toEmailsList = ((ExpiredQuoteUpdProcessModel) businessProcessModel).getToEmails().split(";");
				}
				else if (businessProcessModel.getProcessDefinitionName().equalsIgnoreCase("quoteToOrderStatusEmailProcess"))
				{
					toEmailsList = ((QuoteToOrderStatusProcessModel) businessProcessModel).getToMails().split(";");
				}
				else
				{
					toEmailsList = ((PIMBatchFailureReportNotificationProcessModel) businessProcessModel).getEmailReceiver()
							.split(";");
				}
				for (final String rec : toEmailsList)
				{
					uniqueToEmails.add(getEmailService().getOrCreateEmailAddressForEmail(rec, emailContext.getToDisplayName()));
				}
			}
			final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
					emailContext.getToDisplayName());
			uniqueToEmails.add(toAddress);
		}
		if (!CollectionUtils.isEmpty(uniqueToEmails))
		{
			toEmails.addAll(uniqueToEmails);
		}
		if (null != businessProcessModel.getCcEmails())
		{
			//Added to override emails for testing purpose. This flag should be disabled(false) in production env.
			if (isEmailOverride)
			{
				final String ccRecipients = Config.getString("email.override.recipients.cc", "");
				final String[] recipients = ccRecipients.split(";");
				LOG.warn("Email overridden cc recipients " + recipients);
				for (final String ss : recipients)
				{
					ccEmails.add(getEmailService().getOrCreateEmailAddressForEmail(ss, emailContext.getToDisplayName()));
				}
				for (final Entry<String, String> entry : businessProcessModel.getCcEmails().entrySet())
				{
					if (isDomainWhiteListed(entry.getKey().toString()))
					{
						final EmailAddressModel ccAddresses = getEmailService()
								.getOrCreateEmailAddressForEmail(entry.getKey().toString(), entry.getValue());
						ccEmails.add(ccAddresses);
					}
				}

			}
			else
			{
				for (final Entry<String, String> entry : businessProcessModel.getCcEmails().entrySet())
				{
					final EmailAddressModel ccAddresses = getEmailService().getOrCreateEmailAddressForEmail(entry.getKey().toString(),
							entry.getValue());
					ccEmails.add(ccAddresses);
				}
			}
		}

		if (null != businessProcessModel.getBccEmails())
		{
			//Added to override emails for testing purpose. This flag should be disabled(false) in production env.
			if (isEmailOverride)
			{
				final String ccRecipients = Config.getString("email.override.recipients.cc", "");
				final String[] recipients = ccRecipients.split(";");
				LOG.warn("Email overridden cc recipients " + recipients);
				for (final String ss : recipients)
				{
					bccEmails.add(getEmailService().getOrCreateEmailAddressForEmail(ss, emailContext.getToDisplayName()));
				}
				for (final Entry<String, String> entry : businessProcessModel.getCcEmails().entrySet())
				{
					if (isDomainWhiteListed(entry.getKey().toString()))
					{
						final EmailAddressModel ccAddresses = getEmailService()
								.getOrCreateEmailAddressForEmail(entry.getKey().toString(), entry.getValue());
						bccEmails.add(ccAddresses);
					}
				}

			}
			else
			{
				for (final Entry<String, String> entry : businessProcessModel.getBccEmails().entrySet())
				{
					final EmailAddressModel ccAddresses = getEmailService().getOrCreateEmailAddressForEmail(entry.getKey().toString(),
							entry.getValue());
					bccEmails.add(ccAddresses);
				}
			}
		}

		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
				emailContext.getFromDisplayName());

		return getEmailService().createEmailMessage(toEmails, ccEmails, bccEmails, fromAddress, emailContext.getFromEmail(),
				emailSubject, emailBody, null);
	}

	private boolean isDomainWhiteListed(final String recipient)
	{
		final String whiteListedDomains = Config.getString("email.override.whitelisted.domains", "");

		final String recipientDomain = recipient.substring(recipient.indexOf('@') + 1);

		return whiteListedDomains.contains(recipientDomain);
	}

}
