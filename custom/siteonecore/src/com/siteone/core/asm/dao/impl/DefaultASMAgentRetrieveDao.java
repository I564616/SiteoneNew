/**
 *
 */
package com.siteone.core.asm.dao.impl;

import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;


public class DefaultASMAgentRetrieveDao extends AbstractItemDao implements com.siteone.core.asm.dao.ASMAgentRetrieveDao
{

	public static final String FETCH_ASM_AGENT_PK_QUERY = "Select {agent} From {Sessionevent} where p_customer = ?unitId order by {creationtime} desc";

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<EmployeeModel> getAgentPk(final String customerPk)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FETCH_ASM_AGENT_PK_QUERY);
		query.addQueryParameter("unitId", customerPk);
		final SearchResult<EmployeeModel> result = getFlexibleSearchService().search(query);
		if (result.getResult() != null)
		{
			return result.getResult();
		}
		return null;
	}

}
