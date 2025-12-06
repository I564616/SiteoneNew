/**
 *
 */
package com.siteone.core.b2bunit.service;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.model.OrgUnitModel;

import java.io.InputStream;
import java.util.List;
import java.util.Set;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public interface SiteOneB2BUnitService
{

	List<B2BUnitModel> getShipTosForCurrentCustomer();

	/**
	 * @return
	 */
	B2BUnitModel getParentUnitForCustomer();

	/**
	 * @param unitId
	 * @return
	 */
	B2BUnitModel getParentForUnit(String unitId);

	B2BUnitModel getMainAccountForUnit(String unitId);

	/**
	 * @return
	 */
	B2BUnitModel getDefaultUnitForCurrentCustomer();

	/**
	 * @param uid
	 * @return
	 */
	List<B2BUnitModel> getShipTosForCustomer(String uid);

	/**
	 * @param unitModel
	 * @return
	 */
	Set<B2BCustomerModel> getAdminUserForUnit(B2BUnitModel unitModel);

	/**
	 * @param customer
	 * @return
	 */
	boolean isAdminUser();

	boolean isOrderingAccount(String unitId);

	public Set<B2BCustomerModel> getB2BCustomers(final B2BUnitModel unit, String searchTerm, String searchType);

	OrgUnitModel getDivisionForUnit(String unitId);

	void uploadImage(String[] filename, InputStream inputStream);
	
	public boolean deleteImage();
}
