/**
 *
 */
package com.siteone.core.order.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commerceservices.customer.dao.impl.DefaultCustomerAccountDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.processengine.enums.ProcessState;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.model.EmailSubscriptionsModel;
import com.siteone.core.model.PurchProductAndOrdersModel;
import com.siteone.core.model.SiteOneInvoiceModel;
import com.siteone.core.order.dao.SiteOneCustomerAccountDao;


/**
 * @author 1129929
 *
 */
public class DefaultSiteOneCustomerAccountDao extends DefaultCustomerAccountDao implements SiteOneCustomerAccountDao
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCustomerAccountDao.class);
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	private PagedFlexibleSearchService pagedFlexibleSearchService;

	private static final String FIND_SHIPTOS_QUERY = "SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.ACTIVE + "} = 1 AND {" + B2BUnitModel.REPORTINGORGANIZATION + "} = (?parent) OR {"
			+ B2BUnitModel.REPORTINGORGANIZATION + "}  IN " + " ({{SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.REPORTINGORGANIZATION + "}  = (?parent) AND {" + B2BUnitModel.ACTIVE + "} = 1}})";
	
	private static final String FIND_SHIPTOS_QUERY_1 = "SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.REPORTINGORGANIZATION + "} = (?parent) OR {"
			+ B2BUnitModel.REPORTINGORGANIZATION + "}  IN " + " ({{SELECT {" + B2BUnitModel.PK + "} FROM {" + B2BUnitModel._TYPECODE
			+ "} WHERE {" + B2BUnitModel.REPORTINGORGANIZATION + "}  = (?parent) }})";

	private static final String FIND_RECENT_ORDERS_FOR_ACCOUNT_QUERY = "SELECT TOP 1 {" + OrderModel.PK + "} FROM {"
			+ OrderModel._TYPECODE + "} WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.ORDERINGACCOUNT
			+ "} IN (?shipTos)";
	private static final String FIND_ORDERS_FOR_SHIPTOS_QUERY = "select {" + OrderModel.PK + "},{"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE
			+ " } WHERE  {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store" + " AND {"
			+ OrderModel.STATUS + "} IN (?statusList)" + "AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
			+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
			+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))";
	
	private static final String FIND_PTYPE_ORDERS_FOR_SHIPTOS_QUERY = "select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE
			+ " JOIN SiteoneCreditCodePaymentInfo as p on {"+OrderModel.PAYMENTINFOLIST+"} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} WHERE {p.paymenttype} IN (?paymentType) AND {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store" + " AND {"
			+ OrderModel.STATUS + "} IN (?statusList)" + "AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
			+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
			+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))";
	
	private static final String FIND_POA_ORDERS_FOR_SHIPTOS_QUERY = "select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE
			+ " JOIN SiteonePOAPaymentInfo as p on {"+OrderModel.CODE+"} = {p.code} } WHERE {p.paymenttype} IN (?paymentType) AND {" + OrderModel.VERSIONID + "} IS NULL AND {"+OrderModel.POAPAYMENTINFOLIST+"} IS NOT NULL AND {" + OrderModel.STORE + "} = ?store" + " AND {"
			+ OrderModel.STATUS + "} IN (?statusList)" + "AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
			+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
			+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))";

	private static final String FIND_CUSTOMER_BY_PK = "SELECT {" + B2BCustomerModel.PK + "} FROM {" + B2BCustomerModel._TYPECODE
			+ "} WHERE {" + B2BCustomerModel.PK + "} = ?pk";

	private static final String ORDER_BY_CREATION_TIME = "ORDER BY  {" + OrderModel.CREATIONTIME + "} DESC";

	private static final String CUSTOMER_QUERY = "AND {" + OrderModel.USER + "} = (?customer)";

	private static final String FIND_ORDER_HISTORY_STATUS = " AND {STATUS} NOT IN ({{ select {PK} from {OrderStatus} where {code} IN ('SUBMITTED','PROCESSING','SCHEDULED_FOR_DELIVERY','READY_FOR_PICKUP','CHECKED_VALID','CHECKED_INVALID','CREATED','ORDER_SPLIT') }})";

	private static final String FIND_OPEN_ORDERS_STATUS = " AND {STATUS} IN ({{ select {PK} from {OrderStatus} where {code} IN ('SUBMITTED','PROCESSING','SCHEDULED_FOR_DELIVERY','READY_FOR_PICKUP','CHECKED_VALID','CHECKED_INVALID','ORDER_SPLIT') }})";

	private static final String FIND_ORDERS_BY_UNIT_QUERY = "SELECT {" + OrderModel.PK + "}, {" + OrderModel.CREATIONTIME + "}, {"
			+ OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {"
			+ OrderModel.STORE + "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + FIND_ORDER_HISTORY_STATUS;

	private static final String FIND_ORDER_DATE_FROMTODATE = " AND {" + OrderModel.CREATIONTIME
			+ "} BETWEEN (?orderDateFrom) AND (?orderDateTo) ";

	private static final String FIND_ORDER_DATE_FROMDATE = " AND {" + OrderModel.CREATIONTIME
			+ "} BETWEEN (?orderDateFrom) AND NOW() ";

	private String FIND_ORDER_DATE_TODATE = " AND {" + OrderModel.CREATIONTIME
			+ "} BETWEEN ADD_DAYS(NOW(), -90) AND (?orderDateTo) ";

	private String FIND_ORDER_DATE = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN ADD_DAYS(NOW(), -90) AND NOW() ";

	private static final String SORT_ORDERS_BY_INVOICE_NUMBER = " ORDER BY {" + OrderModel.INVOICENUMBER + "} DESC";

	private static final String SORT_ORDERS_BY_ORDER_NUMBER = " ORDER BY {" + OrderModel.CODE + "} DESC";

	private static final String SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER = " ORDER BY {" + OrderModel.PURCHASEORDERNUMBER + "} DESC";

	private static final String SORT_ORDERS_BY_TOTAL_AMOUNT = " ORDER BY {" + OrderModel.TOTALPRICE + "} DESC";

	private static final String SORT_ORDERS_BY_DATE = " ORDER BY {" + OrderModel.CREATIONTIME + "} DESC";

	private static final String FIND_INVOICES_BY_CODE_QUERY = "SELECT {" + SiteOneInvoiceModel.PK + "}, {"
			+ SiteOneInvoiceModel.CREATIONTIME + "}, {" + SiteOneInvoiceModel.INVOICENUMBER + "} FROM {"
			+ SiteOneInvoiceModel._TYPECODE + "} WHERE {" + SiteOneInvoiceModel.INVOICENUMBER + "} = ?code";

	private static final String FIND_EMAIL_SUBSCRIPTION_BY_EMAIL = "SELECT {" + EmailSubscriptionsModel.PK + "} FROM {"
			+ EmailSubscriptionsModel._TYPECODE + "} WHERE {" + EmailSubscriptionsModel.EMAILID + "} = ?email";

	private static final String ENV_PROPERTIES = "local.environment";

	private static final String FIND_ORDERS_BY_CODE_STORE_QUERY = "SELECT {" + OrderModel.PK + "}, {" + OrderModel.CREATIONTIME
			+ "}, {" + OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.CODE + "} = ?code " + "OR {"
			+ OrderModel.HYBRISORDERNUMBER + "} = ?code AND {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
			+ "} = ?store";

	private static final String FIND_ORDERS_IN_TRANSIT = "SELECT {" + OrderModel.PK + "}, {" + OrderModel.CREATIONTIME + "}, {"
			+ OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.STATUS + "}"
			+ "IN (?orderstatus) AND {" + OrderModel.ORDERTYPE + "} NOT IN (?orderTypes) ";
	
	private static final String FIND_ORDERS_FOR_SAME_HYBRIS_ORDER = "SELECT {" + OrderModel.PK + "}, {" + OrderModel.CREATIONTIME + "}, {"
			+ OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.HYBRISORDERNUMBER + "}"
			+ "IN (?hybrisOrderNumber)";
	private static final String FIND_ORDERS_FOR_SAME_APPROVE_ORDER = "SELECT {" + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE
			+ "} WHERE {" + OrderModel.CODE + "}" + "= ?code";
	private List<OrderStatus> filterOrderStatusList;

	/**
	 * @return the filterOrderStatusList
	 */
	@Override
	public List<OrderStatus> getFilterOrderStatusList()
	{
		return filterOrderStatusList;
	}


	/**
	 * @param filterOrderStatusList
	 *           the filterOrderStatusList to set
	 */
	@Override
	public void setFilterOrderStatusList(final List<OrderStatus> filterOrderStatusList)
	{
		this.filterOrderStatusList = filterOrderStatusList;
	}


	@Override
	public OrderModel findRecentOrderForAccount(final Collection<B2BUnitModel> shipTos, final B2BCustomerModel customer)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("shipTos", shipTos);

		final String filterClause = StringUtils.EMPTY;

		if (null != customer)
		{
			queryParams.put("customer", customer);
			final SearchResult<OrderModel> result = getFlexibleSearchService().search(new FlexibleSearchQuery(
					createQuery(FIND_RECENT_ORDERS_FOR_ACCOUNT_QUERY + CUSTOMER_QUERY, filterClause, ORDER_BY_CREATION_TIME),
					queryParams));
			return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
		}
		else
		{
			final SearchResult<OrderModel> result = getFlexibleSearchService()
					.search(new FlexibleSearchQuery(FIND_RECENT_ORDERS_FOR_ACCOUNT_QUERY + ORDER_BY_CREATION_TIME, queryParams));
			return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
		}

	}
	
	@Override
	public List<OrderModel> findHeldOrdersForCustomer(Collection<B2BUnitModel> shipTos)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(
				"Select {o.pk} From { Order as o join Consignment as c on {c.order} = {o.pk} join ConsignmentProcess as cp on {cp.consignment} = {c.pk}}");
		sb.append(" where {o.orderingaccount} in (?shipTos) and {cp.state} = ?state order by {o.creationtime} desc");

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb.toString());
		fQuery.addQueryParameter("shipTos", shipTos);
		fQuery.addQueryParameter("state", ProcessState.RUNNING);
		
		LOG.error("Orders Held Query is " + fQuery);
		final SearchResult result = this.getFlexibleSearchService().search(fQuery);
		LOG.error("Orders Result for Held Query is " + result.getCount());
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult() : null;
	}


	@Override
	public SearchPageData<PurchProductAndOrdersModel> findPagedPurchasedProducts(final Collection<B2BUnitModel> shipTos,
			final PageableData pageableData)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("shipTos", shipTos);

		final List sortQueries = Arrays.asList(new SortQueryData[]
		{ this.createSortQueryData("byNewestDate",
				"select MAX({p.pk}) as pk from {PurchProductAndOrders as p join AbstractOrder AS o on {p.orderId}={o.code}} where {o.orderingAccount} in (?shipTos) GROUP BY {p.productcode} ORDER BY MAX({p.pk}) DESC"),
				this.createSortQueryData("byOldestDate",
						"select MAX({p.pk}) as pk from {PurchProductAndOrders as p join AbstractOrder AS o on {p.orderId}={o.code}} where {o.orderingAccount} in (?shipTos) GROUP BY {p.productcode} ORDER BY MAX({p.pk})"),
				this.createSortQueryData("byAscendingName",
						"select {pk} from {PurchProductAndOrders} where {pk} in ({{select MAX({p.pk}) as pk from {PurchProductAndOrders as p join AbstractOrder AS o on {p.orderId}={o.code}} where {o.orderingAccount} in (?shipTos) GROUP BY {p.productcode} ORDER BY MAX({p.pk}) DESC}}) order by {productname}"),
				this.createSortQueryData("byDescendingName",
						"select {pk} from {PurchProductAndOrders} where {pk} in ({{select MAX({p.pk}) as pk from {PurchProductAndOrders as p join AbstractOrder AS o on {p.orderId}={o.code}} where {o.orderingAccount} in (?shipTos) GROUP BY {p.productcode} ORDER BY MAX({p.pk}) DESC}}) order by {productname} desc") });


		return getPagedFlexibleSearchService().search(sortQueries, "byNewestDate", queryParams, pageableData);
	}

	@Override
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	@Override
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	@Override
	public PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	@Override
	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
	}


	@Override
	public SearchPageData<OrderModel> findOrdersByUnit(final BaseStoreModel store, final OrderStatus[] status,
			final PageableData pageableData, final List<B2BUnitModel> shipTos, final String pageId, final String trimmedSearchParam,
			final String dateSort, String paymentType)
	{
		
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("store", store);
		queryParams.put("shipTos", shipTos);
		queryParams.put("poNumber", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("orderNumber", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("invoiceNumber", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		

		if (Config.getBoolean(ENV_PROPERTIES, false))
		{
			FIND_ORDER_DATE_TODATE = " AND {" + OrderModel.CREATIONTIME
					+ "} BETWEEN DATE_SUB(NOW(), INTERVAL 90 DAY) AND (?orderDateTo) ";
			FIND_ORDER_DATE = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 90 DAY) AND NOW() ";
		}

		/*
		 * final String pattern = "YYYY-MM-dd HH:mm:ss.SSS"; final SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat(pattern); String fromDate = null; String toDate = null; if (null != dateFrom) { fromDate =
		 * simpleDateFormat.format(dateFrom); } if (null != dateTo) { toDate = simpleDateFormat.format(dateTo); }
		 *
		 * String filterClauseDate = StringUtils.EMPTY; if (null != fromDate && null != toDate) {
		 * queryParams.put("orderDateFrom", fromDate); queryParams.put("orderDateTo", toDate); filterClauseDate =
		 * FIND_ORDER_DATE_FROMTODATE; } else if (null != fromDate && null == toDate) { queryParams.put("orderDateFrom",
		 * fromDate); filterClauseDate = FIND_ORDER_DATE_FROMDATE; } else if (null == fromDate && null != toDate) {
		 * queryParams.put("orderDateTo", toDate); filterClauseDate = FIND_ORDER_DATE_TODATE; } else if (null == fromDate
		 * && null == toDate) { filterClauseDate = FIND_ORDER_DATE; }
		 *
		 */
		List<SortQueryData> sortQueries = null;

		/*String DAYS_30_QUERY = " AND DAYS_BETWEEN (TO_TIMESTAMP ({ " + OrderModel.CREATIONTIME
				+ " }), TO_TIMESTAMP (NOW())) <= 30 ";
		String DAYS_60_QUERY = " AND DAYS_BETWEEN (TO_TIMESTAMP ({ " + OrderModel.CREATIONTIME
				+ " }), TO_TIMESTAMP (NOW())) <= 60 ";
		String DAYS_90_QUERY = " AND DAYS_BETWEEN (TO_TIMESTAMP ({ " + OrderModel.CREATIONTIME
				+ " }), TO_TIMESTAMP (NOW())) <= 90 ";
		String DAYS_184_QUERY = " AND DAYS_BETWEEN (TO_TIMESTAMP ({ " + OrderModel.CREATIONTIME
				+ " }), TO_TIMESTAMP (NOW())) <= 184 ";
		String DAYS_365_QUERY = " AND DAYS_BETWEEN (TO_TIMESTAMP ({ " + OrderModel.CREATIONTIME
				+ " }), TO_TIMESTAMP (NOW())) <= 365 ";
		String DAYS_730_QUERY = " AND DAYS_BETWEEN (TO_TIMESTAMP ({ " + OrderModel.CREATIONTIME
				+ " }), TO_TIMESTAMP (NOW())) <= 730 ";*/

		//Migration | Query change
		String DAYS_30_QUERY = " AND DATEDIFF (DAY,CONVERT (datetime,{ " + OrderModel.CREATIONTIME
				+ " }), SYSDATETIME()) <= 30 ";
		String DAYS_60_QUERY = " AND DATEDIFF (DAY,CONVERT (datetime,{ " + OrderModel.CREATIONTIME
				+ " }), SYSDATETIME()) <= 60 ";
		String DAYS_90_QUERY = " AND DATEDIFF (DAY,CONVERT (datetime,{ " + OrderModel.CREATIONTIME
				+ " }), SYSDATETIME()) <= 90 ";
		String DAYS_184_QUERY = " AND DATEDIFF (DAY,CONVERT (datetime,{ " + OrderModel.CREATIONTIME
				+ " }), SYSDATETIME()) <= 184 ";
		String DAYS_365_QUERY = " AND DATEDIFF (YEAR,CONVERT (datetime,{ " + OrderModel.CREATIONTIME
				+ " }), SYSDATETIME()) <= 1 ";
		String DAYS_730_QUERY = " AND DATEDIFF (YEAR,CONVERT (datetime,{ " + OrderModel.CREATIONTIME
				+ " }), SYSDATETIME()) <= 2 ";

		if (Config.getBoolean(ENV_PROPERTIES, false))
		{
			/*DAYS_30_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() ";
			DAYS_60_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 60 DAY) AND NOW() ";
			DAYS_90_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 90 DAY) AND NOW() ";
			DAYS_184_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 184 DAY) AND NOW() ";
			DAYS_365_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 365 DAY) AND NOW() ";
			DAYS_730_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATE_SUB(NOW(), INTERVAL 730 DAY) AND NOW() ";*/

			//Migration | Query change
			DAYS_30_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATEADD(DAY,-30, SYSDATETIME()) AND SYSDATETIME() ";
			DAYS_60_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATEADD(DAY,-60, SYSDATETIME()) AND SYSDATETIME() ";
			DAYS_90_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATEADD(DAY,-90, SYSDATETIME()) AND SYSDATETIME() ";
			DAYS_184_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATEADD(DAY,-184, SYSDATETIME()) AND SYSDATETIME() ";
			DAYS_365_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATEADD(DAY,-365, SYSDATETIME()) AND SYSDATETIME() ";
			DAYS_730_QUERY = " AND {" + OrderModel.CREATIONTIME + "} BETWEEN DATEADD(DAY,-730, SYSDATETIME()) AND SYSDATETIME() ";
		}
		String filterClauseDate = StringUtils.EMPTY;
		if (dateSort != null)
		{
			if (dateSort.contains("by30days"))
			{
				filterClauseDate = DAYS_30_QUERY;
			}
			else if (dateSort.contains("by60days"))
			{
				filterClauseDate = DAYS_60_QUERY;
			}
			else if (dateSort.contains("by90days"))
			{
				filterClauseDate = DAYS_90_QUERY;
			}
			else if (dateSort.contains("by184days"))
			{
				filterClauseDate = DAYS_184_QUERY;
			}
			else if (dateSort.contains("by365days"))
			{
				filterClauseDate = DAYS_365_QUERY;
			}
			else if (dateSort.contains("by730days"))
			{
				filterClauseDate = DAYS_730_QUERY;
			}
		}
		if (pageId.equals("orderhistorypage"))
		{
			List<String> paymentTypeList = new ArrayList<>();
			if (ArrayUtils.isNotEmpty(status))
			{
				queryParams.put("statusList", Arrays.asList(status));
				
				if(paymentType != null && paymentType != ""){
					if(paymentType.equalsIgnoreCase("ALL")){
						paymentTypeList.add("1");
						paymentTypeList.add("2");
						paymentTypeList.add("3");
						
						queryParams.put("paymentType", paymentTypeList);
					}else{
						queryParams.put("paymentType", paymentType);
					}
					if(paymentType.equalsIgnoreCase("1")){
						sortQueries = Arrays.asList(
								createSortQueryData("byDate",
										createQuery(FIND_POA_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_DATE)),
								createSortQueryData("byInvoiceNumber",
										createQuery(FIND_POA_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
								createSortQueryData("byOrderNumber",
										createQuery(FIND_POA_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
								createSortQueryData("byPONumber",
										createQuery(FIND_POA_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
								createSortQueryData("byAmount",
										createQuery(FIND_POA_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
					}else if(paymentType.equalsIgnoreCase("ALL")){
						sortQueries = Arrays.asList(
	   						createSortQueryData("byDate",
	   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_DATE)),
	   						createSortQueryData("byInvoiceNumber",
	   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
	   						createSortQueryData("byOrderNumber",
	   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
	   						createSortQueryData("byPONumber",
	   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
	   						createSortQueryData("byAmount",
	   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
					}else{
   					sortQueries = Arrays.asList(
   							createSortQueryData("byDate",
   									createQuery(FIND_PTYPE_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_DATE)),
   							createSortQueryData("byInvoiceNumber",
   									createQuery(FIND_PTYPE_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
   							createSortQueryData("byOrderNumber",
   									createQuery(FIND_PTYPE_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
   							createSortQueryData("byPONumber",
   									createQuery(FIND_PTYPE_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
   							createSortQueryData("byAmount",
   									createQuery(FIND_PTYPE_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
					}
				}else{
   				sortQueries = Arrays.asList(
   						createSortQueryData("byDate",
   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_DATE)),
   						createSortQueryData("byInvoiceNumber",
   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
   						createSortQueryData("byOrderNumber",
   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
   						createSortQueryData("byPONumber",
   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
   						createSortQueryData("byAmount",
   								createQuery(FIND_ORDERS_FOR_SHIPTOS_QUERY, filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
				}

				/*
				 * sortQueries = Arrays.asList(createSortQueryData("byLast30Days", createQuery("select {" + OrderModel.PK +
				 * "} from {" + OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" +
				 * OrderModel.STORE + "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_30_QUERY + SORT_ORDERS_BY_DATE)),
				 *
				 * createSortQueryData("byLast60Days", createQuery("select {" + OrderModel.PK + "} from {" +
				 * OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE +
				 * "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_60_QUERY + SORT_ORDERS_BY_DATE)),
				 * createSortQueryData("byLast90Days", createQuery("select {" + OrderModel.PK + "} from {" +
				 * OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE +
				 * "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_90_QUERY + SORT_ORDERS_BY_DATE)),
				 * createSortQueryData("byLast365Days", createQuery("select {" + OrderModel.PK + "} from {" +
				 * OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE +
				 * "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_365_QUERY + SORT_ORDERS_BY_DATE)),
				 * createSortQueryData("byAllOrders", createQuery("select {" + OrderModel.PK + "} from {" +
				 * OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE +
				 * "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + SORT_ORDERS_BY_DATE)));
				 */

			}
			else
			{
				if(paymentType != null && paymentType != ""){
					if(paymentType.equalsIgnoreCase("ALL")){
						paymentTypeList.add("1");
						paymentTypeList.add("2");
						paymentTypeList.add("3");
						
						queryParams.put("paymentType", paymentTypeList);
					}else{
						queryParams.put("paymentType", paymentType);
					}
					if(paymentType.equalsIgnoreCase("1")){
						sortQueries = Arrays.asList(
	   						createSortQueryData("byDate",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteonePOAPaymentInfo as p on {"+OrderModel.CODE+"} = {p.code} } WHERE {p.paymenttype} IN (?paymentType) AND {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {"+OrderModel.POAPAYMENTINFOLIST+"} IS NOT NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_DATE)),
	   						createSortQueryData("byInvoiceNumber",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteonePOAPaymentInfo as p on {"+OrderModel.CODE+"} = {p.code} } WHERE {p.paymenttype} IN (?paymentType) AND {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {"+OrderModel.POAPAYMENTINFOLIST+"} IS NOT NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
	   						createSortQueryData("byOrderNumber",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteonePOAPaymentInfo as p on {"+OrderModel.CODE+"} = {p.code} } WHERE {p.paymenttype} IN (?paymentType) AND {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {"+OrderModel.POAPAYMENTINFOLIST+"} IS NOT NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
	   						createSortQueryData("byPONumber",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteonePOAPaymentInfo as p on {"+OrderModel.CODE+"} = {p.code} } WHERE {p.paymenttype} IN (?paymentType) AND {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {"+OrderModel.POAPAYMENTINFOLIST+"} IS NOT NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
	   						createSortQueryData("byAmount",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteonePOAPaymentInfo as p on {"+OrderModel.CODE+"} = {p.code} } WHERE {p.paymenttype} IN (?paymentType) AND {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {"+OrderModel.POAPAYMENTINFOLIST+"} IS NOT NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
					}else if(paymentType.equalsIgnoreCase("ALL")){
						sortQueries = Arrays.asList(
	   						createSortQueryData("byDate",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + "} WHERE {"+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_DATE)),
	   						createSortQueryData("byInvoiceNumber",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + "} WHERE {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
	   						createSortQueryData("byOrderNumber",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " } WHERE {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
	   						createSortQueryData("byPONumber",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " } WHERE {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
	   						createSortQueryData("byAmount",
	   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " } WHERE {"
	   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
	   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
	   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
	   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
	   										filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
					}else{
   					sortQueries = Arrays.asList(
      						createSortQueryData("byDate",
      								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteoneCreditCardPaymentInfo as p on {"+OrderModel.PAYMENTINFOLIST+"} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} WHERE {p.paymenttype} IN (?paymentType) AND {"
      										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
      										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
      										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
      										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
      										filterClauseDate, SORT_ORDERS_BY_DATE)),
      						createSortQueryData("byInvoiceNumber",
      								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteoneCreditCardPaymentInfo as p on {"+OrderModel.PAYMENTINFOLIST+"} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} WHERE {p.paymenttype} IN (?paymentType) AND {"
      										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
      										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
      										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
      										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
      										filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
      						createSortQueryData("byOrderNumber",
      								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteoneCreditCardPaymentInfo as p on {"+OrderModel.PAYMENTINFOLIST+"} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} WHERE {p.paymenttype} IN (?paymentType) AND {"
      										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
      										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
      										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
      										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
      										filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
      						createSortQueryData("byPONumber",
      								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteoneCreditCardPaymentInfo as p on {"+OrderModel.PAYMENTINFOLIST+"} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} WHERE {p.paymenttype} IN (?paymentType) AND {"
      										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
      										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
      										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
      										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
      										filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
      						createSortQueryData("byAmount",
      								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " JOIN SiteoneCreditCardPaymentInfo as p on {"+OrderModel.PAYMENTINFOLIST+"} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} WHERE {p.paymenttype} IN (?paymentType) AND {"
      										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
      										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
      										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
      										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
      										filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
					}
					
				}else{
   				sortQueries = Arrays.asList(
   						createSortQueryData("byDate",
   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + "} WHERE {"+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
   										filterClauseDate, SORT_ORDERS_BY_DATE)),
   						createSortQueryData("byInvoiceNumber",
   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + "} WHERE {"
   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
   										filterClauseDate, SORT_ORDERS_BY_INVOICE_NUMBER)),
   						createSortQueryData("byOrderNumber",
   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " } WHERE {"
   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
   										filterClauseDate, SORT_ORDERS_BY_ORDER_NUMBER)),
   						createSortQueryData("byPONumber",
   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " } WHERE {"
   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
   										filterClauseDate, SORT_ORDERS_BY_PURCHASE_ORDER_NUMBER)),
   						createSortQueryData("byAmount",
   								createQuery("select {" + OrderModel.PK + "}, {"+OrderModel.PAYMENTINFOLIST+"}, {"+OrderModel.POAPAYMENTINFOLIST+"} from {" + OrderModel._TYPECODE + " } WHERE {"
   										+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
   										+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
   										+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNumber) OR upper({"
   										+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS,
   										filterClauseDate, SORT_ORDERS_BY_TOTAL_AMOUNT)));
				}
				/*
				 * sortQueries = Arrays.asList( createSortQueryData("byLast30Days", createQuery("select {" + OrderModel.PK +
				 * "} from {" + OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" +
				 * OrderModel.STORE + "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
				 * + OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE +
				 * "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" +
				 * FIND_ORDER_HISTORY_STATUS + DAYS_30_QUERY + SORT_ORDERS_BY_DATE)), createSortQueryData("byLast60Days",
				 * createQuery("select {" + OrderModel.PK + "} from {" + OrderModel._TYPECODE + "}" + "WHERE {" +
				 * OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS + DAYS_60_QUERY +
				 * SORT_ORDERS_BY_DATE)), createSortQueryData("byLast90Days", createQuery("select {" + OrderModel.PK +
				 * "} from {" + OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" +
				 * OrderModel.STORE + "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
				 * + OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE +
				 * "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" +
				 * FIND_ORDER_HISTORY_STATUS + DAYS_90_QUERY + SORT_ORDERS_BY_DATE)), createSortQueryData("byLast365Days",
				 * createQuery("select {" + OrderModel.PK + "} from {" + OrderModel._TYPECODE + "}" + "WHERE {" +
				 * OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { " +
				 * OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER +
				 * "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({" +
				 * OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + FIND_ORDER_HISTORY_STATUS + DAYS_365_QUERY
				 * + SORT_ORDERS_BY_DATE)), createSortQueryData("byAllOrders", createQuery("select {" + OrderModel.PK +
				 * "} from {" + OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" +
				 * OrderModel.STORE + "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
				 * + OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE +
				 * "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" +
				 * FIND_ORDER_HISTORY_STATUS + SORT_ORDERS_BY_DATE)));
				 */

			}
		}
		else
		{
			if (pageId.equals("openorderspage"))
			{
				if (ArrayUtils.isNotEmpty(status))
				{
					queryParams.put("statusList", Arrays.asList(status));

					sortQueries = Arrays.asList(
							createSortQueryData("byLast30Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { "
									+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
									+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
									+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_30_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byLast60Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { "
									+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
									+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
									+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_60_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byLast90Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { "
									+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
									+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
									+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_90_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byLast365Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store" + " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { "
									+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
									+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
									+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + DAYS_365_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byAllOrders", createQuery("SELECT {" + OrderModel.PK + "} from {" + OrderModel._TYPECODE
									+ "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store"
									+ " AND {" + OrderModel.STATUS + "} IN (?statusList)" + "AND { " + OrderModel.ORDERINGACCOUNT
									+ " } IN (?shipTos) " + "and (upper({" + OrderModel.PURCHASEORDERNUMBER
									+ "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE + "}) LIKE upper(?orderNUmber) OR upper({"
									+ OrderModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber))" + SORT_ORDERS_BY_DATE)));

				}
				else
				{
					sortQueries = Arrays.asList(
							createSortQueryData("byLast30Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
									+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
									+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER
									+ "}) LIKE upper(?invoiceNumber))" + FIND_OPEN_ORDERS_STATUS + DAYS_30_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byLast60Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
									+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
									+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER
									+ "}) LIKE upper(?invoiceNumber))" + FIND_OPEN_ORDERS_STATUS + DAYS_60_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byLast90Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
									+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
									+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER
									+ "}) LIKE upper(?invoiceNumber))" + FIND_OPEN_ORDERS_STATUS + DAYS_90_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byLast365Days", createQuery("select {" + OrderModel.PK + "} from {"
									+ OrderModel._TYPECODE + "}" + "WHERE {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE
									+ "} = ?store AND { " + OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
									+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
									+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER
									+ "}) LIKE upper(?invoiceNumber))" + FIND_OPEN_ORDERS_STATUS + DAYS_365_QUERY + SORT_ORDERS_BY_DATE)),
							createSortQueryData("byAllOrders",
									createQuery("select {" + OrderModel.PK + "} from {" + OrderModel._TYPECODE + "}" + "WHERE {"
											+ OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store AND { "
											+ OrderModel.ORDERINGACCOUNT + " } IN (?shipTos)" + "and (upper({"
											+ OrderModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + OrderModel.CODE
											+ "}) LIKE upper(?orderNUmber) OR upper({" + OrderModel.INVOICENUMBER
											+ "}) LIKE upper(?invoiceNumber))" + FIND_OPEN_ORDERS_STATUS + SORT_ORDERS_BY_DATE)));

				}
			}
		}
		if (pageId.equals("orderhistorypage"))
		{
			LOG.info("Sort Queries " + sortQueries.get(0).getQuery() + " query Params " + queryParams + " pagebel data "
					+ pageableData.getCurrentPage());
			return getPagedFlexibleSearchService().search(sortQueries, "byDate", queryParams, pageableData);
		}
		return getPagedFlexibleSearchService().search(sortQueries, "byAllOrders", queryParams, pageableData);
	}

	@Override
	public SiteOneInvoiceModel findInvoiceDetailsByCode(final String invoiceNumber)
	{
		validateParameterNotNull(invoiceNumber, "invoiceNumber must not be null");

		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("code", invoiceNumber);

		final SiteOneInvoiceModel result = getFlexibleSearchService()
				.searchUnique(new FlexibleSearchQuery(FIND_INVOICES_BY_CODE_QUERY, queryParams));
		return result;
	}

	@Override
	public boolean isEmailOpted(final String email)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("email", email);
		final SearchResult<List> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FIND_EMAIL_SUBSCRIPTION_BY_EMAIL, queryParams));
		if (CollectionUtils.isNotEmpty(result.getResult()))
		//if (null != result)
		{
			return true;
		}
		return false;
	}

	@Override
	public OrderModel findOrderByCodeAndStore(final String code, final BaseStoreModel store)
	{
		validateParameterNotNull(code, "Code must not be null");
		validateParameterNotNull(store, "Store must not be null");
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("code", code);
		queryParams.put("store", store);
		final OrderModel result = getFlexibleSearchService()
				.searchUnique(new FlexibleSearchQuery(FIND_ORDERS_BY_CODE_STORE_QUERY, queryParams));
		return result;
	}


	@Override
	public List<B2BUnitModel> getAllAccountIDs(final B2BUnitModel parent)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("parent", parent);
		final SearchResult<B2BUnitModel> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FIND_SHIPTOS_QUERY, queryParams));
		if (CollectionUtils.isNotEmpty(result.getResult()))
		{
			return (result.getResult());
		}
		return null;

	}
	
	@Override
	public List<B2BUnitModel> getAllAccountIDsInclundingInactive(final B2BUnitModel parent)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("parent", parent);
		final SearchResult<B2BUnitModel> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FIND_SHIPTOS_QUERY_1, queryParams));
		return (null != result ? result.getResult() : new ArrayList<>());
	}

	@Override
	public B2BCustomerModel getCustomerByPK(final String pk)
	{
		validateParameterNotNull(pk, "B2BCustomer must not be null");

		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("pk", pk);

		final B2BCustomerModel result = getFlexibleSearchService()
				.searchUnique(new FlexibleSearchQuery(FIND_CUSTOMER_BY_PK, queryParams));
		return result;
	}

	@Override
	public List<OrderModel> findOrdersInTransit(final B2BCustomerModel customer)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		if (null != customer)
		{
			queryParams.put("customer", customer);
			final Collection<OrderStatus> orderstatus = new ArrayList();
			orderstatus.add(OrderStatus.SUBMITTED);
			queryParams.put("orderstatus", orderstatus);

			// exclude pick up orders
			final Collection<OrderTypeEnum> orderTypeStatus = new ArrayList();
			orderTypeStatus.add(OrderTypeEnum.PICKUP);
			orderTypeStatus.add(OrderTypeEnum.FUTURE_PICKUP);
			queryParams.put("orderTypes", orderTypeStatus);


			final SearchResult<OrderModel> result = getFlexibleSearchService()
					.search(new FlexibleSearchQuery(FIND_ORDERS_IN_TRANSIT + CUSTOMER_QUERY + ORDER_BY_CREATION_TIME, queryParams));
			return result.getResult();
		}
		return new ArrayList<>();

	}


	@Override
	public String getGuestCustomerUID(final String orderNumber)
	{

		final StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT {c.uid} FROM {Order  as o} ,{Customer as c}");
		sb.append(" WHERE {c.pk} = {o.user} AND {o.code} = ?orderNumber");

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(sb.toString());
		fQuery.setResultClassList(
				Arrays.asList(String.class));
		fQuery.addQueryParameter("orderNumber", orderNumber);
		final SearchResult result = this.getFlexibleSearchService().search(fQuery);
		String guestUid=null;
		if (null != result.getResult() && CollectionUtils.isNotEmpty(result.getResult()))
		{
			guestUid = result.getResult().get(0).toString();
		}
		return guestUid;
	}
	@Override
	public List<OrderModel> getRecentOrders(final List<B2BUnitModel> shipTos, Integer numberOfOrders)
	{
		final String FIND_RECENT_ORDERS_QUERY = "SELECT {" + OrderModel.PK + "} FROM {" + OrderModel._TYPECODE
				+ "} WHERE {" + OrderModel.ORDERINGACCOUNT + "} IN (?shipToList) ORDER BY {creationTime} desc ";
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("shipToList", shipTos);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_RECENT_ORDERS_QUERY,queryParams); 
		query.setCount(numberOfOrders); 
		final SearchResult<OrderModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}
	
	@Override
	public List<OrderModel> getOrdersWithSameHybrisOrderNumber(final String hybrisOrderNumber)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(hybrisOrderNumber))
		{
			queryParams.put("hybrisOrderNumber", hybrisOrderNumber);
			final SearchResult<OrderModel> result = getFlexibleSearchService()
					.search(new FlexibleSearchQuery(FIND_ORDERS_FOR_SAME_HYBRIS_ORDER, queryParams));
			return result.getResult();
		}
		
		return new ArrayList<>();
	}
	
	@Override
	public List<OrderModel> getHeldOrderWithOrderNumber(final String orderNumber)
	{
		final String queryString = "Select {o.pk} from {Order as o} where {o.code} = ?orderNo";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("orderNo", orderNumber);
		final SearchResult<OrderModel> result = getFlexibleSearchService()
				.search(query);
		return result.getResult();
	}
	
	@Override
	public boolean isBuyAgainProductExists(final String unitId)
	{
		final String queryString = "select * from {OrderEntry as entry join Order AS o on {entry.order}={o.pk} "
				+ "join B2BUnit as u on {o.orderingAccount} = {u.pk}}  "
				+ "where DATEDIFF (DAY,CONVERT (datetime,{o.creationtime}), SYSDATETIME()) <= 365 "
				+ "AND {o.code} not like '%_bak'  AND {u.uid} like CONCAT( ?unitId , '%' ) "
				+ "AND {o.STATUS} NOT IN ({{ select {PK} from {OrderStatus} "
				+ "where {code} IN ('PROCESSING','CHECKED_VALID','CHECKED_INVALID','CREATED','ORDER_SPLIT') }})";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("unitId", unitId);
		final int count = getFlexibleSearchService().search(query).getCount();
		
		return count>=1;
	}
	@Override
	public List<OrderModel> getDuplicateApprovalOrders(final String code)
	{

		final Map<String, Object> queryParams = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(code))
		{
			queryParams.put("code", code);
			final SearchResult<OrderModel> result = getFlexibleSearchService()
					.search(new FlexibleSearchQuery(FIND_ORDERS_FOR_SAME_APPROVE_ORDER, queryParams));
			return result.getResult();
		}

		return new ArrayList<>();
	}
}
