/**
 *
 */
package com.siteone.core.events.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.events.dao.SiteOneEventDao;
import com.siteone.core.model.SiteOneEventModel;


/**
 * @author 965504
 *
 */
public class DefaultSiteOneEventDao implements SiteOneEventDao
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;


	@Override
	public List<SiteOneEventModel> findEventByCode(final String code)
	{

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {" + SiteOneEventModel.PK + "} " + "FROM {"
				+ SiteOneEventModel._TYPECODE + "} " + "WHERE {" + SiteOneEventModel.CODE + "}=?code ");
		query.addQueryParameter("code", code);
		final SearchResult<SiteOneEventModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

}
