/**
 *
 */
package com.siteone.core.article.dao.impl;

import de.hybris.platform.b2b.dao.impl.DefaultPrincipalGroupMembersDao;
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


/**
 * @author 212064
 *
 */
public class DefaultBUnitPrincipalGroupMembersDao extends DefaultPrincipalGroupMembersDao
{
	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	protected static final Logger LOG = Logger.getLogger(DefaultBUnitPrincipalGroupMembersDao.class);
	private ModelService modelService;
	private FlexibleSearchService flexibleSearchService;

	@Override
	public <T extends PrincipalModel> List<T> findAllMembersByType(final UserGroupModel parent, final Class<T> memberType)
	{
		return this.findMembersByType(parent, memberType, -1, 1);
	}

	@Override
	public <T extends PrincipalModel> List<T> findMembersByType(final UserGroupModel parent, final Class<T> memberType,
			final int count, final int start)
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
		builder.append(" WHERE  {p.pk} = ?parent and {m.active} = 1 Order by {m.pk} desc");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.setCount(-1);
		query.setStart(0);
		query.getQueryParameters().put("parent", parent);
		final SearchResult<T> result = this.getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public ModelService getModelService()
	{
		return this.modelService;
	}

	@Override
	public FlexibleSearchService getFlexibleSearchService()
	{
		return this.flexibleSearchService;
	}

	@Override
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}