/**
 *
 */
package com.siteone.core.company;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;


/**
 * @author 1197861
 *
 */
public interface SiteOneB2BCommerceUserService
{
	public SearchPageData<B2BCustomerModel> getPagedUsersForUnit(final PageableData pageableData, final List<String> userUnitIds,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin);

	public SearchPageData<B2BCustomerModel> getPagedUsersForUnit(final PageableData pageableData, final String userUnitId,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin, final Boolean shipToUnitFlag);

	/**
	 * @param custUIDs
	 * @return
	 */
	public List<B2BCustomerModel> getCustomers(List<String> custUIDs);

	/**
	 * @param pageableData
	 * @param unitId
	 * @param trimmedSearchParam
	 * @param sortCode
	 * @return
	 */
	public SearchPageData<B2BCustomerModel> getPagedUsersForUnit(PageableData pageableData, String unitId,
			String trimmedSearchParam, String sortCode);

	/**
	 * @param unitId
	 * @return
	 */
	public List<B2BCustomerModel> getUsersForUnit(String unitId);

}
