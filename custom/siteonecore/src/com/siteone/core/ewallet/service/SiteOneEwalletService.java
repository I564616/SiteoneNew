/**
 *
 */
package com.siteone.core.ewallet.service;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import com.siteone.core.model.SiteoneEwalletCreditCardModel;
import com.siteone.core.exceptions.EwalletNotFoundException;


/**
 * @author RSUBATHR
 *
 */
public interface SiteOneEwalletService
{
	public SiteoneEwalletCreditCardModel getCreditCardDetails(String vaultToken) throws EwalletNotFoundException;

	public List<Object> getEwalletOrderStatus(String vaultToken);

	public SearchPageData<SiteoneEwalletCreditCardModel> getEWalletCardDetails(final PageableData pageableData,
			final String userUnitId, final String trimmedSearchParam, final String sortCode, final Boolean shipToFlag);

	public List<SiteoneEwalletCreditCardModel> getEWalletCardDetailsForCheckout(String userUnitId);

	/**
	 * @param switchName
	 * @return
	 */
	public String getValueForSwitch(String switchKey);

	public SearchPageData<B2BCustomerModel> getPagedAssignRevokeUsers(final PageableData pageableData, final String userUnitId,
			final String sortCode, final String action, final String vaultToken);

}
