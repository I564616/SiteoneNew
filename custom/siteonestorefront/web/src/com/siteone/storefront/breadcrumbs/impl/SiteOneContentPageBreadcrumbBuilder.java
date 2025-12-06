/**
 *
 */
package com.siteone.storefront.breadcrumbs.impl;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.ContentPageBreadcrumbBuilder;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author 1091124
 *
 */
public class SiteOneContentPageBreadcrumbBuilder extends ContentPageBreadcrumbBuilder
{
	private static final String LAST_LINK_CLASS = "active";

	/**
	 * @param page
	 *           - page to build up breadcrumb for
	 * @return breadcrumb for given page
	 */
	@Override
	public List<Breadcrumb> getBreadcrumbs(final ContentPageModel page)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		String title = page.getTitle();
		if (title == null)
		{
			title = page.getName();
		}

		breadcrumbs.add(new Breadcrumb("#", title, LAST_LINK_CLASS));

		final List<CMSNavigationNodeModel> navigationNodes = page.getNavigationNodeList();
		if (CollectionUtils.isNotEmpty(navigationNodes))
		{
			final String navigationNodeTitle = navigationNodes.get(0).getTitle();
			breadcrumbs.add(new Breadcrumb("#", navigationNodeTitle, LAST_LINK_CLASS));
		}

		Collections.reverse(breadcrumbs);

		return breadcrumbs;
	}
}


