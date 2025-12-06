/**
 *
 */
package com.siteone.facades.company;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;
import java.util.Map;

import com.siteone.facade.SiteoneRequestAccountData;
import com.siteone.facades.exceptions.ContactAlreadyPresentInUEException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInOktaException;
import com.siteone.facades.exceptions.ContactNotCreatedOrUpdatedInUEException;
import com.siteone.facades.exceptions.ContactNotEnabledOrDisabledInUEException;
import com.siteone.facades.exceptions.DuplicateEmailException;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;


/**
 * @author 1197861
 *
 */
public interface SiteOneB2BUserFacade
{
	public SearchPageData<CustomerData> getPagedUserDataForUnit(final PageableData pageableData, final List<String> userUnitIds,
			final String trimmedSearchParam, final String sortCode, final Boolean isAdmin);


	public Map<String, String> getListOfShipTos();

	void updateCustomer(final CustomerData customerData)
			throws ServiceUnavailableException, ServiceUnavailableException, ContactNotCreatedOrUpdatedInUEException,
			ContactNotCreatedOrUpdatedInOktaException, ContactAlreadyPresentInUEException, DuplicateEmailException;

	void disableCustomer(final String customerUid) throws ServiceUnavailableException, ContactNotEnabledOrDisabledInUEException;

	void enableCustomer(final String customerUid) throws ServiceUnavailableException, ContactNotEnabledOrDisabledInUEException;

	boolean isPunchOutCustomer(final String uid);


	/**
	 * @param pageableData
	 * @param userUnitIds
	 * @param trimmedSearchParam
	 * @param sortCode
	 * @param isAdmin
	 * @return
	 */
	public SearchPageData<CustomerData> getPagedUserDataForUnit(PageableData pageableData, String userUnitId,
			String trimmedSearchParam, String sortCode, Boolean isAdmin, Boolean shipToUnitFlag);


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
	public SearchPageData<CustomerData> getPagedUserDataForUnit(PageableData pageableData, String unitId,
			String trimmedSearchParam, String sortCode);


	/**
	 * @param unitId
	 * @return
	 */
	public List<B2BCustomerModel> getUserDataForUnit(String unitId);

	B2BCustomerModel createCustomer(final SiteoneRequestAccountData siteoneRequestAccountData,
			final SiteOneWsCreateCustomerResponseData response, B2BUnitModel b2bUnitModel) throws DuplicateEmailException;


}
