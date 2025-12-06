/**
 *
 */
package com.siteone.core.customer.dao.impl;

import de.hybris.platform.b2b.dao.impl.DefaultPagedB2BUserGroupDao;
import de.hybris.platform.b2b.model.B2BUserGroupModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOnePagedB2BUserGroupDao extends DefaultPagedB2BUserGroupDao
{

	private static final String DEFAULT_SORT_CODE = Config.getString("b2bcommerce.defaultSortCode", "byName");

	public DefaultSiteOnePagedB2BUserGroupDao(final String typeCode)
	{
		super(typeCode);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.b2b.dao.impl.DefaultPagedB2BUserGroupDao#find(de.hybris.platform.commerceservices.search.
	 * pagedata.PageableData)
	 */
	@Override
	public SearchPageData<B2BUserGroupModel> find(final PageableData pageableData)
	{
		final List sortQueries = Arrays.asList(new SortQueryData[]
		{ this.createSortQueryData("byUnitName",
				"SELECT {ug:pk} FROM { B2BUserGroup as ug JOIN B2BUnit as u ON {ug:unit} = {u:pk} } ORDER BY {u:name} "),
				this.createSortQueryData("byGroupID",
						"SELECT {ug:pk} FROM { B2BUserGroup as ug JOIN B2BUnit as u ON {ug:unit} = {u:pk} } ORDER BY {ug:uid} "),
				this.createSortQueryData("byDate",
						"SELECT {ug:pk} FROM { B2BUserGroup as ug JOIN B2BUnit as u ON {ug:unit} = {u:pk} } ORDER BY {ug:creationtime} "),
				this.createSortQueryData("byName",
						"SELECT {ug:pk} FROM { B2BUserGroup as ug JOIN B2BUnit as u ON {ug:unit} = {u:pk} } ORDER BY {ug:name} ") });
		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, new HashMap(), pageableData);
	}



}
