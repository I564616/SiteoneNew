/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import com.siteone.core.services.SiteMapPageService;


/**
 * @author 1124932
 *
 */
public class DefaultSiteMapPageService implements SiteMapPageService
{

	private FlexibleSearchService flexibleSearchService;

	@Override
	public MediaModel getSiteMapPage(final String sitemapPageType)
	{
		final String siteMapQuery = "select {pk} from {media} where LOWER({code}) like LOWER('" + sitemapPageType
				+ "%') order by {creationtime} desc";
		final StringBuilder builder = new StringBuilder(siteMapQuery);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<MediaModel> result = getFlexibleSearchService().search(query);

		return result.getResult().isEmpty() ? null : result.getResult().get(0);
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

}
