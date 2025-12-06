package com.siteone.core.email.service.impl;

import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.media.MediaFolderModel;
import de.hybris.platform.core.model.media.MediaModel;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author Abdul Rahman Sheikh M on 2/15/2018
 */
public class DefaultSiteOneEmailService extends DefaultEmailService
{
	private static final Logger LOG = Logger.getLogger(DefaultEmailService.class);

	@Override
	public EmailMessageModel createEmailMessage(final List<EmailAddressModel> toAddresses, // NOSONAR
			final List<EmailAddressModel> ccAddresses, final List<EmailAddressModel> bccAddresses,
			final EmailAddressModel fromAddress, final String replyToAddress, final String subject, final String body,
			final List<EmailAttachmentModel> attachments)
	{
		validateAddresses(toAddresses, fromAddress);
		validateContent(subject, body);
		validateEmailAddress(replyToAddress, "replyToAddress");

		final EmailMessageModel emailMessageModel = getModelService().create(EmailMessageModel.class);
		emailMessageModel.setToAddresses(toAddresses);
		emailMessageModel.setCcAddresses(ccAddresses);
		emailMessageModel.setBccAddresses(bccAddresses);
		emailMessageModel.setFromAddress(fromAddress);
		emailMessageModel.setReplyToAddress(
				replyToAddress != null && !replyToAddress.isEmpty() ? replyToAddress : fromAddress.getEmailAddress());
		emailMessageModel.setSubject(subject);
		emailMessageModel.setAttachments(attachments);
		getModelService().save(emailMessageModel);

		final MediaModel bodyMedia = createBodyMedia("bodyMedia-" + emailMessageModel.getPk(), body);
		emailMessageModel.setBodyMedia(bodyMedia);
		getModelService().save(emailMessageModel);

		//Use mailMediaUrl in velocity templates to get media url for body.
		final String newBody = super.getBody(emailMessageModel);
		if (null != newBody && StringUtils.contains(newBody, "mailMediaUrl"))
		{
			final MediaModel newMedia = this.createMediaBody(newBody,
					"MailBody-" + emailMessageModel.getPk().toString() + UUID.randomUUID());
			updateBodyMedia("bodyMedia-" + emailMessageModel.getPk(), bodyMedia,
					newBody.replaceAll("mailMediaUrl", "/mailmedia/" + newMedia.getCode()));
		}

		return emailMessageModel;
	}


	/**
	 * Method creates MediaModel object for storing email body
	 *
	 * @param bodyMediaName
	 *           - Name of the media
	 * @param bodyMedia
	 *           - Media Model
	 * @param body
	 *           - content of email body
	 * @return created MediaModel object
	 */
	protected MediaModel updateBodyMedia(final String bodyMediaName, final MediaModel bodyMedia, final String body)
	{
		final MediaFolderModel mediaFolderModel = getEmailBodyMediaFolder();
		InputStream dataStream = null;
		try
		{
			try
			{ // NOSONAR
				dataStream = new ByteArrayInputStream(body.getBytes(EMAIL_BODY_ENCODING));
			}
			catch (final UnsupportedEncodingException e)
			{
				dataStream = new ByteArrayInputStream(body.getBytes());
				LOG.warn("emailBody - UnsupportedEncodingException", e);
			}
			getMediaService().setStreamForMedia(bodyMedia, dataStream, bodyMediaName, EMAIL_BODY_MIME_TYPE, mediaFolderModel);
		}
		finally
		{
			try
			{ // NOSONAR
				if (dataStream != null) // NOSONAR
				{
					dataStream.close();
				}
			}
			catch (final IOException e)
			{
				logDebugException(e);
			}
		}
		return bodyMedia;
	}

	protected MediaModel createMediaBody(final String body, final String code)
	{
		final MediaModel bodyMedia = getModelService().create(MediaModel.class);
		bodyMedia.setCatalogVersion(getCatalogVersion());
		bodyMedia.setCode(code);
		bodyMedia.setMime(EMAIL_BODY_MIME_TYPE);
		bodyMedia.setRealFileName(code);
		getModelService().save(bodyMedia);

		InputStream dataStream = null;
		try
		{
			try
			{ // NOSONAR
				dataStream = new ByteArrayInputStream(body.getBytes(EMAIL_BODY_ENCODING));
			}
			catch (final UnsupportedEncodingException e)
			{
				dataStream = new ByteArrayInputStream(body.getBytes());
				LOG.warn("emailBody - UnsupportedEncodingException", e);
			}
			getMediaService().setStreamForMedia(bodyMedia, dataStream);
		}
		finally
		{
			try
			{ // NOSONAR
				if (dataStream != null) // NOSONAR
				{
					dataStream.close();
				}
			}
			catch (final IOException e)
			{
				logDebugException(e);
			}
		}

		return bodyMedia;
	}
	
	@Override
	public EmailAttachmentModel createEmailAttachment(final DataInputStream masterDataStream, final String filename,
			final String mimeType)
	{
		final EmailAttachmentModel attachment = getModelService().create(EmailAttachmentModel.class);
		attachment.setCode(filename);
		attachment.setMime(mimeType);
		attachment.setRealFileName(filename);
		attachment.setCatalogVersion(getCatalogVersionForAttachment());
		getModelService().save(attachment);
		getMediaService().setStreamForMedia(attachment, masterDataStream, filename, mimeType, getEmailAttachmentsMediaFolder());
		return attachment;
	}
	
	protected CatalogVersionModel getCatalogVersionForAttachment()
	{
		 return  getCatalogVersionService().getCatalogVersion("siteoneContentCatalog", "Online");
	}
	

}
