/**
 *
 */
package com.siteone.core.news.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneNewsModel;
import com.siteone.core.news.dao.SiteOneNewsDao;


/**
 * @author 191179
 *
 */
public class DefaultSiteOneNewsDao implements SiteOneNewsDao
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<SiteOneNewsModel> findNewsByCode(final String code)
	{

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {" + SiteOneNewsModel.PK + "} " + "FROM {"
				+ SiteOneNewsModel._TYPECODE + "} " + "WHERE {" + SiteOneNewsModel.NEWSCODE + "}=?code ");
		query.addQueryParameter("code", code);

		final SearchResult<SiteOneNewsModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

}
