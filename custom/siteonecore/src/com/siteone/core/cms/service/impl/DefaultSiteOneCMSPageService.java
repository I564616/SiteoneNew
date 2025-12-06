/**
 *
 */
package com.siteone.core.cms.service.impl;


import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.data.RestrictionData;
import de.hybris.platform.cms2.servicelayer.services.impl.DefaultCMSPageService;
import de.hybris.platform.site.BaseSiteService;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.annotation.Resource;

import com.siteone.core.cms.dao.SiteOneCMSPageDao;
import com.siteone.core.cms.service.SiteOneCMSPageService;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOneCMSPageService extends DefaultCMSPageService implements SiteOneCMSPageService
{
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	@Override
	public ContentPageModel getCategoryLandingPage(final CategoryModel category) throws CMSItemNotFoundException
	{
		Collection<CatalogVersionModel> catalogVersions = new ArrayList<>();
		if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
			catalogVersions.add(super.getCatalogVersionService().getCatalogVersion("siteoneUSContentCatalog", "Online"));
		}
		if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca")) {
			catalogVersions.add(super.getCatalogVersionService().getCatalogVersion("siteoneCAContentCatalog", "Online"));
		}
		Collection pages = ((SiteOneCMSPageDao) getCmsPageDao()).findCategoryLandingPages(category, catalogVersions);
		if(pages.isEmpty()) {
			catalogVersions = new ArrayList<>();
			catalogVersions.add(super.getCatalogVersionService().getCatalogVersion("siteoneContentCatalog", "Online"));
			pages = ((SiteOneCMSPageDao) getCmsPageDao()).findCategoryLandingPages(category, catalogVersions);
		}
		final Collection result = this.getCmsRestrictionService().evaluatePages(pages, (RestrictionData) null);
		if (result.isEmpty())
		{
			throw new CMSItemNotFoundException("No page for category [" + category.getCode() + "] found.");// 558
		}
		else
		{
			if (result.size() > 1)
			{
				LOG.warn("More than one page found for the category [" + category.getCode() + "]. Returning default.");// 562
			}

			return (ContentPageModel) result.iterator().next();// 564
		}
	}


}
