/**
 *
 */
package com.siteone.core.cms.dao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import java.util.Collection;


/**
 * @author 1190626
 *
 */
public interface SiteOneCMSPageDao
{

	/**
	 * @param category
	 * @param catalogVersions
	 * @return
	 */
	Collection<ContentPageModel> findCategoryLandingPages(CategoryModel category, Collection<CatalogVersionModel> catalogVersions);

}
