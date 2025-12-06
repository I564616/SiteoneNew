/**
 *
 */
package com.siteone.storefront.breadcrumbs.impl;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.servicelayer.i18n.I18NService;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.context.MessageSource;

import com.siteone.facades.customer.SiteOneB2BUnitFacade;


/**
 * @author 1129929
 *
 */
public class SiteOneAccountBreadcrumbBuilder
{

	private MessageSource messageSource;
	private I18NService i18nService;

	@Resource(name = "b2bUnitFacade")
	private B2BUnitFacade b2bUnitFacade;

	private static final String MY_ACCOUNT_DASHBOARD_URL = "/my-account/account-dashboard";
	private static final String TEXT_ACCOUNT_DASHBOARD = "text.account.addressBook.dashboard";
	private static final String MY_ACCOUNT_SHIPTO_URL = "/my-account/ship-to/";

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

	public List<Breadcrumb> getBreadcrumbsForAccountDashboardPage(final String unitId)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		if (unitId.equalsIgnoreCase(((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer().getUid()))
		{
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_DASHBOARD_URL,
					getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		}
		else
		{
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_SHIPTO_URL + unitId,
					getMessageSource().getMessage(TEXT_ACCOUNT_DASHBOARD, null, getI18nService().getCurrentLocale()), null));
		}
		return breadcrumbs;
	}

}
