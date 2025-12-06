/**
 *
 */
package com.siteone.core.customer.dao.impl;




import de.hybris.platform.b2b.model.B2BPermissionModel;
import de.hybris.platform.b2bacceleratorservices.dao.impl.DefaultPagedB2BPermissionDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOnePagedB2BPermissionDao extends DefaultPagedB2BPermissionDao
{
	public DefaultSiteOnePagedB2BPermissionDao(final String typeCode)
	{
		super(typeCode);
	}

	private static final String DEFAULT_SORT_CODE = Config.getString("b2bcommerce.defaultSortCode", "byName");

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.b2b.dao.impl.DefaultPagedB2BPermissionDao#find(de.hybris.platform.commerceservices.search.
	 * pagedata.PageableData)
	 */
	@Override
	public SearchPageData<B2BPermissionModel> find(final PageableData pageableData)
	{
		final List sortQueries = Arrays.asList(new SortQueryData[]
		{ this.createSortQueryData("byUnitName",
				"SELECT {B2BPermission:pk} FROM { B2BPermission as B2BPermission JOIN B2BUnit as B2BUnit ON {B2BPermission:unit} = {B2BUnit:pk} }ORDER BY {B2BUnit:name}"),
				this.createSortQueryData("byName", new HashMap(), SortParameters.singletonAscending("code"), pageableData),
				this.createSortQueryData("byDate", new HashMap(), SortParameters.singletonAscending("creationtime"), pageableData) });
		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, new HashMap(), pageableData);
	}



}
