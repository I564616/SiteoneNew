/**
 *
 */
package com.siteone.core.requestaccount.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneContrPrimaryBusinessModel;
import com.siteone.core.requestaccount.dao.SiteoneContrPrimaryBusinessMapDao;


/**
 * @author SNavamani
 *
 */
public class DefaultSiteoneContrPrimaryBusinessMapDao implements SiteoneContrPrimaryBusinessMapDao
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<SiteOneContrPrimaryBusinessModel> getPrimaryBusinessMap()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {pk} FROM {SiteOneContrPrimaryBusiness} where {parentId} = 'null' AND {active}=?active");
		query.addQueryParameter("active", Boolean.TRUE);
		final SearchResult<SiteOneContrPrimaryBusinessModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<SiteOneContrPrimaryBusinessModel> getChildPrimaryBusinessMap(final String code)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {pk} FROM {SiteOneContrPrimaryBusiness} where {parentId}=?code AND {active}=?active");
		query.addQueryParameter("code", code);
		query.addQueryParameter("active", Boolean.TRUE);
		final SearchResult<SiteOneContrPrimaryBusinessModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
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
