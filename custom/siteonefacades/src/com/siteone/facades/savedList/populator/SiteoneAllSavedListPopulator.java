/**
 *
 */
package com.siteone.facades.savedList.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

import com.siteone.facades.savedList.data.SavedListData;


/**
 * @author 1003567
 *
 */
public class SiteoneAllSavedListPopulator implements Populator<Wishlist2Model, SavedListData>
{
	private static final String PRIVATE = "private.savedList";
	private static final String SHARED = "shared.savedList";


	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	private MessageSource messageSource;

	@Override
	public void populate(final Wishlist2Model source, final SavedListData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setName(source.getName());
		target.setDescription(source.getDescription());
		target.setModifiedTime(source.getModifiedtime());
		if (source.getEntries() != null)
		{
			target.setTotalEntries(source.getEntries().size());
		}

		target.setCreatedBy(source.getCreatedBy());

		if (source.getUser() != null)
		{
			target.setUser(source.getUser().getName());
		}

		if (userService.getCurrentUser().getUid().equals(source.getCreatedBy()))
		{
			target.setIsModified(Boolean.TRUE);
		}
		if (source.getIsShared())
		{
			target.setIsShared(getMessageSource().getMessage(SHARED, null, getI18nService().getCurrentLocale()));
		}
		else
		{
			target.setIsShared(getMessageSource().getMessage(PRIVATE, null, getI18nService().getCurrentLocale()));
		}
		target.setCode(source.getPk().toString());

	}

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
}