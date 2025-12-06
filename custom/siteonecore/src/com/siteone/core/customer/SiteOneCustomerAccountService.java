/**
 *
 */
package com.siteone.core.customer;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.siteone.core.enums.InvoiceStatus;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageResponseData;
import com.siteone.integration.open.order.data.OpenOrdersLandingPageRequestData;
import com.siteone.integration.open.order.data.SiteoneOrderDetailsResponseData;


/**
 * @author 965504
 *
 */
public interface SiteOneCustomerAccountService extends CustomerAccountService
{
	public PointOfServiceModel getCustomerPreferredStore();

	public Boolean isPreferredStore(PointOfServiceModel pointOfServiceModel);

	public Set<PointOfServiceModel> getCustomerStoreList();

	PointOfServiceModel makeMyStore(String storeId);

	public Boolean isInMyStore(PointOfServiceModel pointOfServiceModel);

	public void removeFromMyStore();

	public void removeFromMyStoreList(String storeId);

	Collection<AddressModel> getAddressBookForUnit(B2BUnitModel b2bUnitModel);

	AddressModel getBillingAddressForUnit(B2BUnitModel b2bUnitModel);

	AddressModel getShippingAddressForUnit(B2BUnitModel b2bUnitModel);

	Collection<AddressModel> getBillingAddressesForUnit(B2BUnitModel b2bUnitModel);

	Collection<AddressModel> getShippingAddressesForUnit(B2BUnitModel b2bUnitModel);

	AddressModel getAddressForCode(B2BUnitModel b2bUnitModel, String code);

	void setDefaultAddressEntry(B2BUnitModel b2bUnitModel, AddressModel addressModel);

	void clearDefaultAddressEntry(final B2BUnitModel b2bUnitModel);

	void saveAddressEntry(final B2BUnitModel b2bUnitModel, final AddressModel addressModel);

	void deleteAddressEntry(final B2BUnitModel b2bUnitModel, final AddressModel addressModel);

	Collection<AddressModel> getAddressesForChildUnits(final B2BUnitModel b2bUnitModel);

	/**
	 * @param pageableData
	 * @param unitId
	 * @return
	 */
	SearchPageData<B2BUnitModel> getPagedB2BUnits(PageableData pageableData, String unitId, String SearchParam);


	void forgottenPassword(CustomerModel customerModel);


	CustomerModel getCustomerFromToken(final String token, long tokenValiditySeconds)
			throws TokenInvalidatedException, IllegalArgumentException;

	void createPassword(CustomerModel customerModel);

	void createPassword(String uid);

	OrderModel getRecentOrderForAccount(String unitId);
	
	List<OrderModel> getHeldOrdersForUnit(String unitId);

	B2BCustomerModel getCustomerByPK(String pk);

	/**
	 * @param customer
	 * @return
	 */
	boolean isAdmin(B2BCustomerModel customer);

	/**
	 * @param code
	 * @return
	 */
	SiteOneInvoiceModel getInvoiceForCode(String code);

	/**
	 * @param store
	 * @param status
	 * @param pageableData
	 * @param unitId
	 * @return
	 */
	SearchPageData<OrderModel> getOrderList(BaseStoreModel store, OrderStatus[] status, PageableData pageableData, String unitId,
			String pageId, String trimmedSearchParam, String dateSort, String paymentType);

	SearchPageData<OrderModel> getOrderListForAll(BaseStoreModel store, OrderStatus[] status, PageableData pageableData,
			String unitId, String pageId, String trimmedSearchParam, String dateSort, String paymentType);


	/**
	 * @param pageableData
	 * @param unitId
	 * @return
	 */
	SearchPageData<PurchProductAndOrdersModel> getPagedPurchasedOrder(PageableData pageableData, String unitId);

	/**
	 * @param b2bCustomerModel
	 */
	public void unlockUserRequest(B2BCustomerModel b2bCustomerModel);

	public boolean isEmailOpted(String email);

	/**
	 * @param shipToId
	 * @param status
	 * @param pageableData
	 * @param trimmedSearchParam
	 * @param dateFromFinal
	 * @param dateToFinal
	 * @return
	 */
	SearchPageData<SiteOneInvoiceModel> getInvoiceList(String shipToId, InvoiceStatus[] status, PageableData pageableData,
			String trimmedSearchParam, Date dateFromFinal, Date dateToFinal);

	/**
	 * @param shipToId
	 * @param status
	 * @param pageableData
	 * @param trimmedSearchParam
	 * @param dateFromFinal
	 * @param dateToFinal
	 * @return
	 */
	SearchPageData<SiteOneInvoiceModel> getInvoiceListForAll(String shipToId, InvoiceStatus[] status, PageableData pageableData,
			String trimmedSearchParam, Date dateFromFinal, Date dateToFinal);

	SearchPageData<B2BUnitModel> getPagedB2BDefaultUnits(PageableData pageableData, String SearchParam);

	/**
	 * @return
	 */
	List<OrderModel> getOrdersInTransit();

	public String getGuestCustomerUID(final String orderNumber);

	List<OrderModel> getRecentOrders(String UnitId, Integer numberOfOrders);

	List<OrderModel> getOrdersWithSameHybrisOrderNumber(String hybrisOrderNumber);
	
	List<OrderModel> getHeldOrderWithOrderNumber(String orderNumber);

	void getDuplicateApprovalOrdersToRemove(OrderModel hybrisOrderNumber);

	boolean isBuyAgainProductExists(String unitId);

	/**
	 * @return
	 */
	PointOfServiceModel getCustomerHomeBranch();

	public void saveLinkToPayCayanResponseModel(LinkToPayCayanResponseModel linkToPayCayanResponseModel);

	PointOfServiceModel saveStore(String storeId);

	/**
	 * @param openOrderRequest
	 * @return
	 */
	OpenOrdersLandingPageResponseData getOpenOrderList(OpenOrdersLandingPageRequestData openOrderRequest, String customerNumber, Integer divisionId, boolean isNewBoomiEnv);

	/**
	 * @param replace
	 * @param code
	 * @param parseBoolean
	 * @return
	 */
	public SiteoneOrderDetailsResponseData getOrderDetailsData(String customerNumber, String invoiceNumber, boolean isNewBoomiEnv);


}
