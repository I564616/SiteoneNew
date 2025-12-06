/**
 *
 */
package com.siteone.core.order.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.customer.dao.CustomerAccountDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.store.BaseStoreModel;

import java.util.Collection;
import java.util.List;

import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.model.SiteOneInvoiceModel;


/**
 * @author 1129929
 *
 */
public interface SiteOneCustomerAccountDao extends CustomerAccountDao
{
	/**
	 * @param invoiceNumber
	 * @return
	 */
	SiteOneInvoiceModel findInvoiceDetailsByCode(String invoiceNumber);

	/**
	 * @param customerModel
	 * @param store
	 * @param status
	 * @param pageableData
	 * @param shipTos
	 * @return
	 */
	SearchPageData<OrderModel> findOrdersByUnit(BaseStoreModel store, OrderStatus[] status, PageableData pageableData,
			List<B2BUnitModel> shipTos, String pageId, String trimmedSearchParam, String dateSort, String paymentType);


	/**
	 * @param shipTos
	 * @param pageableData
	 * @param customer
	 * @return
	 */
	SearchPageData<PurchProductAndOrdersModel> findPagedPurchasedProducts(Collection<B2BUnitModel> shipTos,
			PageableData pageableData);

	/**
	 * @param singletonList
	 * @param customerModel
	 * @return
	 */
	OrderModel findRecentOrderForAccount(Collection<B2BUnitModel> shipTos, B2BCustomerModel customerModel);

	List<OrderModel> findHeldOrdersForCustomer(Collection<B2BUnitModel> shipTos);
	
	boolean isEmailOpted(String email);

	public List<B2BUnitModel> getAllAccountIDs(final B2BUnitModel parent);

	public B2BCustomerModel getCustomerByPK(final String pk);

	/**
	 * @param customerModel
	 * @return
	 */
	public List<OrderModel> findOrdersInTransit(B2BCustomerModel customerModel);

	public String getGuestCustomerUID(final String orderNumber);

	public List<OrderModel> getRecentOrders(final List<B2BUnitModel> shipTos, Integer numberOfOrders);

	public List<OrderModel> getOrdersWithSameHybrisOrderNumber(final String hybrisOrderNumber);

	boolean isBuyAgainProductExists(String unitId);

	List<B2BUnitModel> getAllAccountIDsInclundingInactive(B2BUnitModel parent);

	/**
	 * @param hybrisOrderNumber
	 * @return
	 */
	List<OrderModel> getDuplicateApprovalOrders(String hybrisOrderNumber);

	List<OrderModel> getHeldOrderWithOrderNumber(String orderNumber);
}
