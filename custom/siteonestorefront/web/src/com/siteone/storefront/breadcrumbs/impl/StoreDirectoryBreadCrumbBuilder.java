/**
 *
 */
package com.siteone.storefront.breadcrumbs.impl;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.servicelayer.i18n.I18NService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;

import com.siteone.core.store.services.SiteOneStoreFinderService;


/**
 * @author 1099417
 *
 */
public class StoreDirectoryBreadCrumbBuilder
{
	private static final String STORE_FINDER_URL = "/store-finder";
	private SiteOneStoreFinderService siteOneStoreFinderService;
	private MessageSource messageSource;
	private I18NService i18nService;

	protected I18NService getI18nService()
	{
		return i18nService;
	}

	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	protected MessageSource getMessageSource()
	{
		return messageSource;
	}

	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}


	public List<Breadcrumb> getBreadcrumbsForStatePage()
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final String storeFinderLinkName = getMessageSource().getMessage("storeFinder.find.a.store", null,
				getI18nService().getCurrentLocale());

		breadcrumbs.add(new Breadcrumb(STORE_FINDER_URL, storeFinderLinkName, null));

		return breadcrumbs;
	}

	public List<Breadcrumb> getBreadcrumbsForCityPage()
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final String storeFinderLinkName = getMessageSource().getMessage("storeFinder.find.a.store", null,
				getI18nService().getCurrentLocale());
		final String storeDirectoryLabel = getMessageSource().getMessage("store.directory.name.label", null,
				getI18nService().getCurrentLocale());

		breadcrumbs.add(new Breadcrumb(STORE_FINDER_URL, storeFinderLinkName, null));

		breadcrumbs.add(new Breadcrumb("/store-directory", storeDirectoryLabel, null));

		return breadcrumbs;
	}

	public List<Breadcrumb> getBreadcrumbsForStoreListPage(final PointOfServiceData pointOfServiceData)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();

		final String storeFinderLinkName = getMessageSource().getMessage("storeFinder.find.a.store", null,
				getI18nService().getCurrentLocale());
		final String storeDirectoryLabel = getMessageSource().getMessage("store.directory.name.label", null,
				getI18nService().getCurrentLocale());

		breadcrumbs.add(new Breadcrumb(STORE_FINDER_URL, storeFinderLinkName, null));

		breadcrumbs.add(new Breadcrumb("/store-directory", storeDirectoryLabel, null));

		breadcrumbs.add(new Breadcrumb("/store-directory/" + pointOfServiceData.getAddress().getRegion().getIsocode(),
				pointOfServiceData.getAddress().getRegion().getName(), null));

		return breadcrumbs;
	}

	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}

	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}

}


