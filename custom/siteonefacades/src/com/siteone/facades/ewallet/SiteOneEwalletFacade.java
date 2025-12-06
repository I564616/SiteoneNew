/**
 *
 */
package com.siteone.facades.ewallet;

import de.hybris.platform.commercefacades.ewallet.data.PaymentconfigData;
import de.hybris.platform.commercefacades.ewallet.data.SiteOneEwalletData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import org.springframework.ui.Model;

import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInCayanException;
import com.siteone.facades.exceptions.EwalletNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ServiceUnavailableException;



/**
 * @author RSUBATHR
 *
 */
public interface SiteOneEwalletFacade
{
	public void updateEwalletDetails(final String vaultToken, final String nickName, final String expDate)
			throws EwalletNotCreatedOrUpdatedInCayanException, EwalletNotCreatedOrUpdatedInUEException, ServiceUnavailableException,
			Exception;

	public String addEwalletDetails(final SiteOneEwalletData ewalletData) throws Exception;

	public void deleteCardFromEwallet(final String vaultToken) throws Exception;

	public SearchPageData<SiteOneEwalletData> getPagedEWalletDataForUnit(final PageableData pageableData, final String userUnitId,
			final String trimmedSearchParam, final String sortCode, final Boolean shipToFlag);

	/**
	 * @param vaultToken
	 * @param custUIDs
	 * @param operationType
	 */
	public void updateEwalletToUser(String vaultToken, List<String> custUIDs, String operationType) throws Exception;

	/**
	 * @param vaultToken
	 * @return
	 */
	public SiteOneEwalletData ewalletDetails(String vaultToken);

	public List<SiteOneEwalletData> getEWalletDataForCheckout(String userUnitId);

	/**
	 * @param accountCmsPage
	 * @param model
	 */
	public void populatePaymentconfig(String accountCmsPage, Model model);

	public SearchPageData<CustomerData> getPagedAssignRevokeUsers(final PageableData pageableData, final String userUnitId,
			final String sortCode, final String action, final String vaultToken);

	public List<SiteOneEwalletData> filterValidEwalletData(CustomerData customerData);

	/**
	 * @return
	 */
	public PaymentconfigData populatePaymentconfig();

	public String getUsersRevokeEwalletData(final String listOfCustIds, final String vaultToken, final String operationType);

	public String editEwalletData(String vaultToken, String nickName, String expDate);

	public String deleteCardForShipTo(String vaultToken);

	/**
	 * @param token
	 */
	public SiteOnePaymentInfoData addGlobalPaymentDetails(String token);

	public void voidGlobalPaymentDetails(SiteOnePaymentInfoData siteOnePaymentInfoData);


}
