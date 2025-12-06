/**
 *
 */
package com.siteone.storefront.breadcrumbs.impl;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.servicelayer.i18n.I18NService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;


/**
 * @author 1099417
 *
 */
public class SiteOneChangePwdBreadcrumbBuilder
{
	private static final String LAST_LINK_CLASS = "active";

	private MessageSource messageSource;
	private I18NService i18nService;

	public List<Breadcrumb> getBreadcrumbsForChangePwd()
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final String myAccountLinkName = getMessageSource().getMessage("header.link.account", null,
				getI18nService().getCurrentLocale());
		final String personalDetailsLabel = getMessageSource().getMessage("text.account.profile.updatePersonalDetails", null,
				getI18nService().getCurrentLocale());


		final String myAccountUrl = "/my-account/account-dashboard";
		final String personalDetailsUrl = "/my-account/update-siteoneprofile";

		breadcrumbs.add(new Breadcrumb(myAccountUrl, myAccountLinkName, null));

		breadcrumbs.add(new Breadcrumb(personalDetailsUrl, personalDetailsLabel, null));

		if (!breadcrumbs.isEmpty())
		{
			breadcrumbs.get(breadcrumbs.size() - 1).setLinkClass(LAST_LINK_CLASS);
		}

		return breadcrumbs;
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

}
