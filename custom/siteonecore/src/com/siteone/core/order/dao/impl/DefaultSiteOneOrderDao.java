/**
 *
 */
package com.siteone.core.order.dao.impl;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.model.QuoteToOrderStatusProcessModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.model.SiteoneKountDataModel;
import com.siteone.core.model.SiteoneOrderEmailStatusModel;
import com.siteone.core.order.dao.SiteOneOrderDao;


/**
 * @author 1099417
 *
 */
public class DefaultSiteOneOrderDao extends AbstractItemDao implements SiteOneOrderDao
{
	private static final String FIND_ORDERING_ACCOUNT_QUERY = "SELECT {" + OrderModel.ORDERINGACCOUNT + "} FROM {"
			+ OrderModel._TYPECODE + "} WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.CODE + "} = ?orderId";

	private static final String FIND_PURCHASED_DATE_QUERY = "SELECT {" + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE
			+ "} WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.CODE + "} = ?orderId";

	private static final String FIND_LAST_TWO_ORDERS_QUERY = "SELECT TOP 2 {" + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE
			+ "} WHERE {" + OrderModel.USER + "} =?userModel AND {" + OrderModel.ISHYBRISORDER
			+ "} = '1' ORDER BY {creationTime} desc ";

	private static final String FIND_ENTRY_IN_SITEONEORDEREMAILSTATUS_TABLE_QUERY = "SELECT {" + SiteoneOrderEmailStatusModel.PK
			+ "} FROM {" + SiteoneOrderEmailStatusModel._TYPECODE + "} WHERE {" + SiteoneOrderEmailStatusModel.CONSIGNMENTID
			+ "} =?consignmentId AND {" + SiteoneOrderEmailStatusModel.ISEMAILSENT + "} = '1'";
	
	private static final String FIND_ENTRY_IN_QUOTETOORDEREMAILSTATUS_TABLE_QUERY = "SELECT {" + QuoteToOrderStatusProcessModel.PK
			+ "} FROM {" + QuoteToOrderStatusProcessModel._TYPECODE + "} WHERE {" + QuoteToOrderStatusProcessModel.CONSIGNMENTID
			+ "} =?consignmentId AND {" + QuoteToOrderStatusProcessModel.ISQUOTETOORDEREMAILSENT + "} = '1'";

	@Override
	public List<OrderModel> getOrdersToSendEmail()
	{
		final String query = "SELECT {" + OrderModel.PK + "}" + " FROM {" + OrderModel._TYPECODE + " AS order }" + " WHERE {order:"
				+ OrderModel.STATUS + "} in ({{ select {pk} from {" + OrderStatus._TYPECODE
				+ "} where {code} ='SUBMITTED'}}) AND {order:" + OrderModel.ISORDERSTATUSEMAILSENT + "} = '0' AND ({order:"
				+ OrderModel.HYBRISORDERNUMBER + "} is not null OR {order:" + OrderModel.QUOTENUMBER + "} is not null)";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		return getFlexibleSearchService().<OrderModel> search(fQuery).getResult();
	}
	
	@Override
	public List<OrderModel> getQuoteToOrderSendEmail()
	{
		final String query = "SELECT {" + OrderModel.PK + "}" + " FROM {" + OrderModel._TYPECODE + " AS order }" + " WHERE {order:"
				+ OrderModel.STATUS + "} in ({{ select {pk} from {" + OrderStatus._TYPECODE
				+ "} where {code} ='SUBMITTED'}}) AND {order:" + OrderModel.ISQUOTETOORDERSTATUSEMAILSENT + "} = '0' AND {order:"
				+ OrderModel.QUOTENUMBER + "} is not null";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		return getFlexibleSearchService().<OrderModel> search(fQuery).getResult();
	}

	@Override
	public List<OrderModel> getOrdersToSendReadyForPickUpRemainderEmail()
	{
		final String query = "select {pk} from {order} where {code} in  ({{select {orderId} from {SiteoneOrderEmailStatus} where {status} = 'READY_FOR_PICKUP' AND {isReminderEmailsent} = '0' AND {creationtime} BETWEEN DATEADD(DAY,-5,SYSDATETIME()) AND SYSDATETIME()}})";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		return getFlexibleSearchService().<OrderModel> search(fQuery).getResult();
	}

	@Override
	public SiteoneOrderEmailStatusModel getSiteoneConsignmentEmailStatus(final String orderNumber)
	{
		final String query = "select {pk} from {SiteoneOrderEmailStatus} where {consignmentId} = (?orderNumber)";
		final Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("orderNumber", orderNumber);
		final List<SiteoneOrderEmailStatusModel> ordersList = getFlexibleSearchService()
				.<SiteoneOrderEmailStatusModel> search(new FlexibleSearchQuery(query, queryParams)).getResult();
		if (!CollectionUtils.isEmpty(ordersList))
		{
			return ordersList.get(0);
		}
		return null;
	}

	@Override
	public List<SiteoneOrderEmailStatusModel> getEntryFromSiteoneOrderEmailStatus(final String consignmentId)
	{
		final Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("consignmentId", consignmentId);
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(FIND_ENTRY_IN_SITEONEORDEREMAILSTATUS_TABLE_QUERY, queryParams);
		return getFlexibleSearchService().<SiteoneOrderEmailStatusModel> search(fQuery).getResult();
	}
	
	@Override
	public List<SiteoneOrderEmailStatusModel> getEntryFromSiteoneQuoteToOrderEmailStatus(final String consignmentId)
	{
		final Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("consignmentId", consignmentId);
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(FIND_ENTRY_IN_QUOTETOORDEREMAILSTATUS_TABLE_QUERY, queryParams);
		return getFlexibleSearchService().<SiteoneOrderEmailStatusModel> search(fQuery).getResult();
	}

	@Override
	public List<OrderModel> getOrdersToSendNotification()
	{
		final String query = "SELECT {" + OrderModel.PK + "}" + " FROM {" + OrderModel._TYPECODE + " AS order }" + " WHERE {order:"
				+ OrderModel.STATUS + "} in ({{ select {pk} from {" + OrderStatus._TYPECODE
				+ "} where {code} ='SUBMITTED'}}) AND {order:" + OrderModel.ISORDERSTATUSNOTIFICATIONSENT + "} = '0' AND {order:"
				+ OrderModel.HYBRISORDERNUMBER + "} is not null";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		return getFlexibleSearchService().<OrderModel> search(fQuery).getResult();
	}

	@Override
	public B2BUnitModel getOrderingAccountforOrder(final String orderId)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("orderId", orderId);
		final B2BUnitModel result = getFlexibleSearchService()
				.searchUnique(new FlexibleSearchQuery(FIND_ORDERING_ACCOUNT_QUERY, queryParams));
		return result;
	}

	@Override
	public OrderModel getPurchasedDateforOrder(final String orderId)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("orderId", orderId);
		final OrderModel result = getFlexibleSearchService()
				.searchUnique(new FlexibleSearchQuery(FIND_PURCHASED_DATE_QUERY, queryParams));
		return result;
	}

	@Override
	public List<OrderModel> getLastTwoHybrisOrdersForCustomer(final UserModel userModel)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("userModel", userModel);
		final List<OrderModel> ordersList = getFlexibleSearchService()
				.<OrderModel> search(new FlexibleSearchQuery(FIND_LAST_TWO_ORDERS_QUERY, queryParams)).getResult();
		return (null != ordersList ? ordersList : new ArrayList<OrderModel>());
	}

	@Override
	public OrderModel getOrderForCode(final String orderNumber)
	{
		final String query = "select {pk} from {order} where {code} = (?orderNumber)";
		final Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("orderNumber", orderNumber);
		final List<OrderModel> ordersList = getFlexibleSearchService()
				.<OrderModel> search(new FlexibleSearchQuery(query, queryParams)).getResult();
		if (!CollectionUtils.isEmpty(ordersList))
		{
			return ordersList.get(0);
		}
		return null;
	}

	@Override
	public SiteoneOrderEmailStatusModel getSiteoneOrderEmailStatus(final String orderNumber)
	{
		final String query = "select {pk} from {SiteoneOrderEmailStatus} where {orderId} = (?orderNumber)";
		final Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("orderNumber", orderNumber);
		final List<SiteoneOrderEmailStatusModel> ordersList = getFlexibleSearchService()
				.<SiteoneOrderEmailStatusModel> search(new FlexibleSearchQuery(query, queryParams)).getResult();
		if (!CollectionUtils.isEmpty(ordersList))
		{
			return ordersList.get(0);
		}
		return null;
	}

	@Override
	public SiteoneCCPaymentAuditLogModel getCCAuditDetails(final String orderNumber, final String cartID)
	{		
			final String query = "select {pk} from {SiteoneCCPaymentAuditLog} where {orderNumber} = (?orderNumber) or {cartID} = (?cartID)";
			final Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("orderNumber", orderNumber);
			queryParams.put("cartID", cartID);
			final List<SiteoneCCPaymentAuditLogModel> ordersList = getFlexibleSearchService()
					.<SiteoneCCPaymentAuditLogModel> search(new FlexibleSearchQuery(query, queryParams)).getResult();
			if (!CollectionUtils.isEmpty(ordersList))
			{
				return ordersList.get(0);
			}
			return null;		
	}

	@Override
	public SiteoneKountDataModel getKountInquiryCallDetails(String orderNumber)
	{
		final String query = "select {pk} from {SiteoneKountData} where {orderNumber} = (?orderNumber)";
		final Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("orderNumber", orderNumber);
		final List<SiteoneKountDataModel> ordersList = getFlexibleSearchService()
				.<SiteoneKountDataModel> search(new FlexibleSearchQuery(query, queryParams)).getResult();
		if (!CollectionUtils.isEmpty(ordersList))
		{
			return ordersList.get(0);
		}
		return null;
	}
}