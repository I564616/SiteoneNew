/**
 *
 */
package com.siteone.core.order.dao;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;

import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneKountDataModel;
import com.siteone.core.model.SiteoneOrderEmailStatusModel;


/**
 * @author 1099417
 *
 */
public interface SiteOneOrderDao
{
	public List<OrderModel> getOrdersToSendEmail();

	public List<OrderModel> getOrdersToSendReadyForPickUpRemainderEmail();

	public List<OrderModel> getOrdersToSendNotification();

	public B2BUnitModel getOrderingAccountforOrder(String orderId);

	public OrderModel getPurchasedDateforOrder(String orderId);

	public List<OrderModel> getLastTwoHybrisOrdersForCustomer(final UserModel userModel);

	/**
	 * @param consignmentId
	 * @return
	 */
	List<SiteoneOrderEmailStatusModel> getEntryFromSiteoneOrderEmailStatus(String consignmentId);

	OrderModel getOrderForCode(String orderNumber);

	List<SiteoneOrderEmailStatusModel> getEntryFromSiteoneQuoteToOrderEmailStatus(String consignmentId);

	/**
	 * @param code
	 * @return
	 */
	public SiteoneOrderEmailStatusModel getSiteoneOrderEmailStatus(String code);

	public SiteoneOrderEmailStatusModel getSiteoneConsignmentEmailStatus(final String orderNumber);

	public SiteoneCCPaymentAuditLogModel getCCAuditDetails(final String orderNumber, final String cartID);

	public List<OrderModel> getQuoteToOrderSendEmail();
	
	public SiteoneKountDataModel getKountInquiryCallDetails(final String orderNumber);
}