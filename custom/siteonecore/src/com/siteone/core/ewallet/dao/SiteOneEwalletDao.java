/**
 *
 */
package com.siteone.core.ewallet.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import com.siteone.core.model.SiteoneEwalletCreditCardModel;


/**
 * @author RSUBATHR
 *
 */
public interface SiteOneEwalletDao
{
	public SiteoneEwalletCreditCardModel getCreditCardDetails(String vaultToken);

	/**
	 * @param pageableData
	 * @param userUnitIds
	 * @param trimmedSearchParam
	 * @param sortCode
	 * @return
	 */
	SearchPageData<SiteoneEwalletCreditCardModel> getEWalletCardDetails(PageableData pageableData, String userUnitId,
			String trimmedSearchParam, String sortCode, final Boolean shipToFlag);

	public List<SiteoneEwalletCreditCardModel> getEWalletCardDetailsForCheckout(String userUnitId);

	public List<Object> getEwalletOrderStatus(String vaultToken);

	public List<B2BCustomerModel> getAdminUsers(List<String> unitIds);

	/**
	 * @param pageableData
	 * @param userUnitId
	 * @param sortCode
	 * @param action
	 * @param vaultToken
	 * @return
	 */
	SearchPageData<B2BCustomerModel> getPagedAssignRevokeUsers(PageableData pageableData, String userUnitId, String sortCode,
			String action, String vaultToken);
}
