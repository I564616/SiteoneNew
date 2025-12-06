/**
 *
 */
package com.siteone.core.unit.dao.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.siteone.core.unit.dao.SiteOnePagedB2BUnitDao;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOnePagedB2BUnitDao extends DefaultPagedGenericDao<B2BUnitModel>
		implements SiteOnePagedB2BUnitDao<B2BUnitModel>
{
	private static final String FIND_SHIPTOS_QUERY = "SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.ACTIVE + "} = 1 AND {" + B2BUnitModel.REPORTINGORGANIZATION + "} = (?parent) AND {"
			+ B2BUnitModel.UID + "} != (?parentId) OR {" + B2BUnitModel.REPORTINGORGANIZATION + "}  IN " + " ({{SELECT {"
			+ B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE + "} WHERE {" + B2BUnitModel.REPORTINGORGANIZATION
			+ "}  = (?parent) AND {" + B2BUnitModel.UID + "} != (?parentId) AND {" + B2BUnitModel.ACTIVE + "} = 1}})";



	private static final String FIND_SHIPTOS_QUERY_POPUP = "SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.PK + "} IN " + "({{SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.ACTIVE + "} = 1 AND {" + B2BUnitModel.REPORTINGORGANIZATION + "} = (?parent) AND {"
			+ B2BUnitModel.UID + "} != (?parentId) OR {" + B2BUnitModel.REPORTINGORGANIZATION + "}  IN " + " ({{SELECT {"
			+ B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE + "} WHERE {" + B2BUnitModel.REPORTINGORGANIZATION
			+ "}  = (?parent) AND {" + B2BUnitModel.UID + "} != (?parentId) AND {" + B2BUnitModel.ACTIVE
			+ "} = 1}})}})  and (upper({uid}) LIKE upper(?uid) OR upper({name}) LIKE upper(?name))";


	private static final String SORT_BY_DATE = " ORDER BY {" + B2BUnitModel.MODIFIEDTIME + "} DESC";

	private static final String SORT_BY_ID = " ORDER BY {" + B2BUnitModel.UID + "} ASC";

	/**
	 * @param typeCode
	 */
	public DefaultSiteOnePagedB2BUnitDao(final String typeCode)
	{
		super(typeCode);
		// YTODO Auto-generated constructor stub
	}

	private static final String DEFAULT_SORT_CODE = Config.getString("b2bcommerce.defaultSortCode", "byDate");

	@Override
	public SearchPageData<B2BUnitModel> findPagedUnits(final String sortCode, final PageableData pageableData,
			final B2BUnitModel parent, final String searchParam)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("parent", parent);
		queryParams.put("parentId", parent.getUid());

		List sortQueries = new ArrayList();
		if (null != searchParam)
		{
			sortQueries = Arrays.asList(new SortQueryData[]
			{ createSortQueryData("byDate", createQuery(FIND_SHIPTOS_QUERY_POPUP, SORT_BY_DATE)),
					createSortQueryData("byUnitId", createQuery(FIND_SHIPTOS_QUERY_POPUP, SORT_BY_ID)) });
			queryParams.put("uid", "%" + searchParam + "%");
			queryParams.put("name", "%" + searchParam + "%");
		}
		else
		{
			sortQueries = Arrays.asList(new SortQueryData[]
			{ createSortQueryData("byDate", createQuery(FIND_SHIPTOS_QUERY, SORT_BY_DATE)),
					createSortQueryData("byUnitId", createQuery(FIND_SHIPTOS_QUERY, SORT_BY_ID)) });

		}

		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData);
	}

	@Override
	protected SortQueryData createSortQueryData(final String sortCode, final String query)
	{
		final SortQueryData result = new SortQueryData();
		result.setSortCode(sortCode);
		result.setQuery(query);
		return result;
	}

	protected String createQuery(final String... queryClauses)
	{
		final StringBuilder queryBuilder = new StringBuilder();

		for (final String queryClause : queryClauses)
		{
			queryBuilder.append(queryClause);
		}

		return queryBuilder.toString();
	}




}