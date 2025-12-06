/**
 *
 */
package com.siteone.core.cms.service;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;


/**
 * @author 1190626
 *
 */
public interface SiteOneCMSPageService
{
	ContentPageModel getCategoryLandingPage(CategoryModel Category) throws CMSItemNotFoundException;
}
