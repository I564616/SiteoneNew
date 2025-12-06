/**
 *
 */
package com.siteone.facade.order;



import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;

import java.util.List;

import org.springframework.ui.Model;

import com.siteone.facade.MasterHybrisOrderData;





/**
 * @author 1190626
 *
 */

public interface SiteOneOrderFacade extends OrderFacade
{

	OrderData getRecentOrderForAccount(String unitId);

	SearchPageData<ProductData> getPagedPurchasedProduct(PageableData pageableData, String unitId);

	SearchPageData<OrderHistoryData> getPagedOrderHistoryForStatuses(PageableData pageableData, int page, String unitId, 
			String trimmedSearchParam, String oSearchParam, String iSearchParam, String pnSearchParam, String dateSort, String paymentType,String sortOrder, String sortCode, OrderStatus... statuses);
	
	SearchPageData<OrderHistoryData> getOpenOrdersLandingPage(PageableData pageableData, String unitId, 
			String trimmedSearchParam, String oSearchParam, String iSearchParam, String pnSearchParam, String dateSort, String paymentType, String sortOrder,String sortCode, OrderStatus... statuses);

	/**
	 * @return
	 */
	List<OrderData> ordersInTransit();

	List<OrderHistoryData> getRecentOrders(String unitId, Integer numberOfOrders);

	MasterHybrisOrderData getOrdersWithSameHybrisOrderNumber(final String hybrisOrderNumber);

	void getListDetailsForUser(final Model model);

	void updateFulfillmentAndPaymentInfoForApprovalOrders(List<B2BOrderApprovalData> orderApprovalList);

	void updateFulfillmentAndPaymentInfoForApprovalOrder(B2BOrderApprovalData b2bOrderApprovalData);

	int getPendingApprovalCount(final PageableData pageableData);

	B2BOrderApprovalData setOrderApprovalDecision(B2BOrderApprovalData b2bOrderApprovalData);

	/**
	 * @param orderNumber
	 * @param PO
	 * @return
	 */
	boolean updatePurchaseOrderNumber(String orderNumber, String PO, String status);

	/**
	 * @param code
	 * @param uid
	 * @return
	 */
	OrderData getOrderDetailsPage(String code, String uid, String branchNo, Integer shipmentCount, Boolean fromEmail);

	/**
	 * @param code
	 * @param uid
	 * @return
	 */
	ConsignmentData getMultipleShipmentPage(String code, String uid);

	/**
	 * @param unitId
	 * @return
	 */
	List<OrderData> fetchRecentOrdersFromAPI(String bUnit);
	
	OrderData getOrderDetailsPage(String code, String unitId);
}
