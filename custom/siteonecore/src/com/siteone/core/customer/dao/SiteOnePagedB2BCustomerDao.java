/**
 *
 */
package com.siteone.core.customer.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.Date;
import java.util.List;

import com.siteone.core.enums.InvoiceStatus;
import com.siteone.core.model.SiteOneInvoiceModel;


/**
 * @author 1197861
 *
 */
public interface SiteOnePagedB2BCustomerDao
{

	/**
	 * @param shipTos
	 * @param status
	 * @param pageableData
	 * @return
	 */
	public SearchPageData<SiteOneInvoiceModel> findInvoicesBasedOnShipToS(List<String> shipTos, InvoiceStatus[] status,
			PageableData pageableData, String trimmedSearchParam, Date dateFromFinal, Date dateToFinal);

	/**
	 * @param pageableData
	 * @param unit
	 * @param Uid
	 * @param trimmedSearchParam
	 * @return
	 */
	public SearchPageData<B2BCustomerModel> findPagedCustomersForUnitSearch(PageableData pageableData, List<String> userUnitIds,
			String Uid, String trimmedSearchParam, String sortCode, Boolean isAdmin);

	/**
	 * @param pageableData
	 * @param userUnitId
	 * @param trimmedSearchParam
	 * @param sortCode
	 * @param isAdmin
	 * @return
	 */
	public SearchPageData<B2BCustomerModel> findPagedCustomersForUnitSearch(PageableData pageableData, String userUnitId,
			String trimmedSearchParam, String sortCode, Boolean isAdmin, Boolean shipToUnitFlag);

	/**
	 * @param custUIDs
	 * @return
	 */
	public List<B2BCustomerModel> findCustomers(List<String> custUIDs);

	/**
	 * @param pageableData
	 * @param unitId
	 * @param trimmedSearchParam
	 * @param sortCode
	 * @return
	 */
	public SearchPageData<B2BCustomerModel> findPagedCustomersForUnitSearch(PageableData pageableData, String unitId,
			String trimmedSearchParam, String sortCode);

	/**
	 * @param unitId
	 * @return
	 */
	public List<B2BCustomerModel> findCustomersForUnit(String unitId);
}