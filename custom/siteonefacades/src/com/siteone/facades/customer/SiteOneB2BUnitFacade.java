/**
 *
 */
package com.siteone.facades.customer;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.UserGroupModel;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.facade.customer.info.MyAccountUserInfo;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facades.exceptions.ServiceUnavailableException;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;


/**
 * @author 1188173
 *
 */
public interface SiteOneB2BUnitFacade
{

	/**
	 * @param uid
	 * @return
	 */
	List<B2BUnitData> getShipTosForUnit(String uid);

	/**
	 * @param uid
	 * @return
	 */
	List<B2BUnitData> getShipTosForUnitBasedOnCustomer(String uid);

	/**
	 * @param customer
	 * @return
	 */
	B2BUnitData getParentUnitForCustomer();

	/**
	 * @return
	 */
	B2BUnitData getDefaultUnitForCurrentCustomer();

	/**
	 * @param uid
	 * @return
	 */
	List<B2BUnitData> getShipTosForCustomer(String uid);

	/**
	 * @param unitId
	 * @return
	 */
	Set<CustomerData> getAdminUserForUnit(String unitId);

	Set<CustomerData> getCustomersForUnit(String unitId);

	List<CustomerData> getUsersforCustomerUnit();

	List<CustomerData> getUsersforCustomerUnit(final String searchTerm, final String searchType);

	SiteOneCustInfoData getAccountInformation(String uid) throws ServiceUnavailableException;

	//List<ProductData> getPurchasedProducts();

	/**
	 * @param b2bUnitData
	 * @param siteOneWsCreateCustomerResponseData
	 * @return
	 */
	B2BUnitModel updateB2BUnit(SiteoneRequestAccountModel siteoneRequestAccountModel,
							   SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE);


	public void uploadImage(String[] filename, InputStream inputStream);

	public boolean deleteImage();

	MyAccountUserInfo getUserAccountInfo();


	boolean getLoyaltyProgramInfoStatus();

	/**
	 * @param uid
	 * @return
	 * @throws ServiceUnavailableException
	 */
	SiteOneCustInfoData getLoyaltyInformation(String uid) throws ServiceUnavailableException;

	public List<B2BUnitData> getModifiedShipTos(final B2BUnitData child, final Collection<String> assignedShipTos);

	public Collection<String> getModifiedAssignedShipTos();

	public B2BUnitData getDefaultUnitWithAdministratorInfo();

	public boolean checkIsUnitForApproveOrders(final String uid);

	public void assignToGroup(final B2BCustomerModel admin, final UserGroupModel userGroup);

	public void setApproverGroupsForAdmin();

	B2BUnitModel updateParentB2BUnit(SiteoneRequestAccountModel siteoneRequestAccountModel,
									 SiteOneWsCreateCustomerResponseData siteOneWsCreateCustomerResponseData, final boolean isAccountExistsInUE);

	public PointOfServiceData getHomeBranchForUnit(String unitId);

	public boolean setIsSegmentLevelShippingEnabled(String tradeClass);
	
	public boolean checkIsParentUnitEnabledForAgroAI(final String uid);
}
