package com.siteone.core.inspire.dao.impl;

import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.inspire.dao.SiteOneInspirationDao;
import com.siteone.core.model.InspirationModel;



public class DefaultSiteOneInspirationDao implements SiteOneInspirationDao
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	private final String DESC = "date-desc";

	@Override
	public List<InspirationModel> findInspirationByCode(final String code)
	{

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {" + InspirationModel.PK + "} " + "FROM {"
				+ InspirationModel._TYPECODE + "} " + "WHERE {" + InspirationModel.INSPIRATIONCODE + "}=?code ");
		query.addQueryParameter("code", code);

		final SearchResult<InspirationModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public SearchPageData<InspirationModel> findInspiration(final PageableData pageableData)
	{
		FlexibleSearchQuery query = null;

		if (pageableData.getSort().equalsIgnoreCase(DESC))
		{
			query = new FlexibleSearchQuery("SELECT {" + InspirationModel.PK + "} " + "FROM {" + InspirationModel._TYPECODE
					+ "} order by {title} desc");
		}
		else
		{
			query = new FlexibleSearchQuery("SELECT {" + InspirationModel.PK + "} " + "FROM {" + InspirationModel._TYPECODE
					+ "} order by {title} asc");
		}

		query.setCount(pageableData.getPageSize());
		query.setStart(pageableData.getCurrentPage() * pageableData.getPageSize());
		query.setNeedTotal(true);
		final SearchPageData<InspirationModel> result = getPagedFlexibleSearchService().search(query, pageableData);
		return result;
	}



	/**
	 * @return the pagedFlexibleSearchService
	 */
	public PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	/**
	 * @param pagedFlexibleSearchService
	 *           the pagedFlexibleSearchService to set
	 */
	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
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
