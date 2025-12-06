/**
 *
 */
package com.siteone.core.company.impl;

import de.hybris.platform.b2b.company.impl.DefaultB2BCommerceUserService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.company.SiteOneB2BCommerceUserService;
import com.siteone.core.customer.dao.SiteOnePagedB2BCustomerDao;


/**
 * @author 1197861
 *
 */
public class DefaultSiteOneB2BCommerceUserService extends DefaultB2BCommerceUserService implements SiteOneB2BCommerceUserService
{

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Override
	public SearchPageData<B2BCustomerModel> getPagedUsersForUnit(final PageableData pageableData, final List<String> userUnitIds,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin)
	{
		final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		return ((SiteOnePagedB2BCustomerDao) getPagedB2BCustomerDao()).findPagedCustomersForUnitSearch(pageableData, userUnitIds,
				customerModel.getUid(), trimmedSearchParam, sortCode, isAdmin);
	}

	public SearchPageData<B2BCustomerModel> getPagedUsersForUnit(final PageableData pageableData, final String userUnitId,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin, final Boolean shipToUnitFlag)
	{
		return ((SiteOnePagedB2BCustomerDao) getPagedB2BCustomerDao()).findPagedCustomersForUnitSearch(pageableData, userUnitId,
				trimmedSearchParam, sortCode, isAdmin, shipToUnitFlag);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.company.SiteOneB2BCommerceUserService#getCustomers(java.util.List)
	 */
	@Override
	public List<B2BCustomerModel> getCustomers(final List<String> custUIDs)
	{
		return ((SiteOnePagedB2BCustomerDao) getPagedB2BCustomerDao()).findCustomers(custUIDs);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.core.company.SiteOneB2BCommerceUserService#getPagedUsersForUnit(de.hybris.platform.commerceservices.
	 * search.pagedata.PageableData, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SearchPageData<B2BCustomerModel> getPagedUsersForUnit(final PageableData pageableData, final String unitId,
			final String trimmedSearchParam, final String sortCode)
	{
		return ((SiteOnePagedB2BCustomerDao) getPagedB2BCustomerDao()).findPagedCustomersForUnitSearch(pageableData, unitId,
				trimmedSearchParam, sortCode);
	}

	@Override
	public List<B2BCustomerModel> getUsersForUnit(final String unitId)
	{
		return ((SiteOnePagedB2BCustomerDao) getPagedB2BCustomerDao()).findCustomersForUnit(unitId);
	}

}