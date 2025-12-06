/**
 *
 */
package com.siteone.facades.events.populator;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;

import com.siteone.core.model.SiteOneEventModel;
import com.siteone.facade.EventData;


/**
 * @author 965504
 *
 */
public class SiteOneEventPopulator implements Populator<SiteOneEventModel, EventData>
{
	private Populator<MediaModel, ImageData> imagePopulator;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;



	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	@Override
	public void populate(final SiteOneEventModel source, final EventData target) throws ConversionException
	{

		final ImageData imageData = new ImageData();
		final ImageData documentData = new ImageData();
		final Date currentDate = new Date();
		final SimpleDateFormat eventDateformat = new SimpleDateFormat("EEEE, MMMM dd");
		final SimpleDateFormat registrationTimeFormat = new SimpleDateFormat("hh:mma");
		final SimpleDateFormat registrationDateFormat = new SimpleDateFormat("EEEE, MMMM dd, YYYY");

		target.setName(source.getName());
		target.setEventStartDate(eventDateformat.format(source.getEventStartDate()));
		target.setEventEndDate(eventDateformat.format(source.getEventEndDate()));
		target.setTime(source.getTime());
		target.setLocation(source.getLocation());
		target.setVenue(source.getVenue());
		target.setCode(source.getCode());
		target.setLongDescription(source.getLongDescription());
		target.setShortDescription(source.getShortDescription());
		target.setIsPartnerProgramEvent(source.getIsPartnerProgramEvent());
		target.setType(source.getType().getName());
		target.setLearnMoreUrl(source.getLearnMoreUrl());
		if (null != source.getEventRegistrationUrl())
		{
			target.setEventRegistrationUrl(source.getEventRegistrationUrl());
		}
		if (currentDate.after(source.getEventEndDate()))
		{
			target.setEventExpiryMessage(
					getMessageSource().getMessage("event.expiry.message", null, getI18nService().getCurrentLocale()));
		}
		if (null != source.getType().getGroup())
		{
			target.setDescription((source.getType().getGroup().getDescription()));
			target.setTypeGroupName(source.getType().getGroup().getName());
		}
		if (null != source.getRegistrationStartDate() && null != source.getRegistrationEndDate())
		{
			if (currentDate.before(source.getRegistrationStartDate()))
			{
				target.setRegistrationMessage(
						"Registration opens at " + registrationTimeFormat.format(source.getRegistrationStartDate()).toLowerCase()
								+ " ET on " + registrationDateFormat.format(source.getRegistrationStartDate()));
			}
			else if (currentDate.after(source.getRegistrationEndDate()))
			{
				target.setRegistrationMessage("Registration has closed.");
			}
		}

		if (null != source.getImage())
		{
			getImagePopulator().populate(source.getImage(), imageData);
			target.setImage(imageData);
		}
		if (null != source.getDocument())
		{
			getImagePopulator().populate(source.getDocument(), documentData);
			target.setDocument(documentData);
		}
		if (null != source.getRegistrationStartDate() && null != source.getRegistrationEndDate())
		{
			if (currentDate.after(source.getRegistrationStartDate()) && currentDate.before(source.getRegistrationEndDate()))
			{
				target.setIsRegistrationOpen(Boolean.TRUE);
			}
			else
			{
				target.setIsRegistrationOpen(Boolean.FALSE);
			}
		}

	}

	/**
	 * @return the imagePopulator
	 */
	public Populator<MediaModel, ImageData> getImagePopulator()
	{
		return imagePopulator;
	}

	/**
	 * @param imagePopulator
	 *           the imagePopulator to set
	 */
	public void setImagePopulator(final Populator<MediaModel, ImageData> imagePopulator)
	{
		this.imagePopulator = imagePopulator;
	}


}
