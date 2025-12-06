/**
 *
 */
package com.siteone.core.article.dao.impl;

import de.hybris.platform.commerceservices.model.OrgUnitModel;
/**
 * @author SMondal
 *
 */
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.article.dao.SiteOneB2BUnitPrincipalGroupMembersDao;


public class DefaultSiteOneB2BUnitPrincipalGroupMembersDao implements SiteOneB2BUnitPrincipalGroupMembersDao
{
	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	protected static final Logger LOG = Logger.getLogger(DefaultSiteOneB2BUnitPrincipalGroupMembersDao.class);
	private ModelService modelService;
	private FlexibleSearchService flexibleSearchService;



	@Override
	public <T extends PrincipalModel> List<T> findMembersByType(final UserGroupModel parent, final Class<T> memberType,
			final String searchTerm, final String searchType)
	{
		final StringBuilder builder = new StringBuilder();
		//Migration | Query Change
		builder.append(" SELECT {m.pk} ");
		builder.append(" FROM { ");
		builder.append("  PrincipalGroupRelation as pg  ");
		builder.append("  JOIN PrincipalGroup as p ");
		builder.append("    ON {pg.target} = {p.pk} ");
		builder.append("  JOIN ").append(this.getModelService().getModelType(memberType)).append(" as m ");
		builder.append("    ON {pg.source} = {m.pk} ");
		builder.append(" } ");
		builder.append(" WHERE  {p.pk} = ?parent and {m.active} = 1 and ");
		if (searchType != null && searchType.equals("Email"))
		{
			builder.append(" lower({m.uid}) like lower(?searchTerm) ");
		}
		else
		{
			builder.append(" lower({m.name}) like lower(?searchTerm) ");
		}
		builder.append("	Order by {m.pk} desc");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.setCount(-1);
		query.setStart(0);
		query.getQueryParameters().put("parent", parent);
		query.getQueryParameters().put("searchTerm", "%" + searchTerm + "%");
		final SearchResult<T> result = this.getFlexibleSearchService().search(query);
		return result.getResult();
	}

	public ModelService getModelService()
	{
		return this.modelService;
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return this.flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	public OrgUnitModel getDivisionForUnit(final String unitId)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {pk} FROM {OrgUnit} where {uid} =?uid");
		query.addQueryParameter("uid", unitId);
		final SearchResult<OrgUnitModel> result = getFlexibleSearchService().search(query);
		return result.getResult().get(0);
	}

}