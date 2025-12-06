/**
 *
 */
package com.siteone.core.customer.dao.impl;

import de.hybris.platform.b2b.dao.impl.DefaultPagedB2BCustomerDao;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.customer.dao.SiteOnePagedB2BCustomerDao;
import com.siteone.core.enums.InvoiceStatus;
import com.siteone.core.model.SiteOneInvoiceModel;


/**
 * @author 1190626
 *
 */
public class DefaultSiteOnePagedB2BCustomerDao extends DefaultPagedB2BCustomerDao implements SiteOnePagedB2BCustomerDao
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePagedB2BCustomerDao.class);

	private static final String ENV_PROPERTIES = "local.environment";

	private static final String DEFAULT_SORT_CODE = Config.getString("b2bcommerce.defaultSortCode", "byName");

	private static final String FILTER_INVOICE_STATUS = " AND {" + SiteOneInvoiceModel.STATUS + "} NOT IN (?filterStatusList)";

	private static final String FIND_INVOICES_FOR_SHIPTOS_QUERY = "SELECT {" + SiteOneInvoiceModel.PK + "}, {"
			+ SiteOneInvoiceModel.CREATIONTIME + "}, {" + SiteOneInvoiceModel.INVOICENUMBER + "} FROM {"
			+ SiteOneInvoiceModel._TYPECODE + "} WHERE  {" + SiteOneInvoiceModel.ACCOUNTNUMBER + "} IN (?shipTos)" + "and (upper({"
			+ SiteOneInvoiceModel.PURCHASEORDERNUMBER + "}) LIKE upper(?poNumber) OR upper({" + SiteOneInvoiceModel.ORDERNUMBER
			+ "}) LIKE upper(?orderNUmber) OR upper({" + SiteOneInvoiceModel.INVOICENUMBER + "}) LIKE upper(?invoiceNumber)) ";

	private static final String FIND_INVOICE_DATE_FROMTODATE = " AND {" + SiteOneInvoiceModel.INVOICEDDATE
			+ "} BETWEEN (?invoiceDateFrom) AND (?invoiceDateTo) ";

	private static final String FIND_INVOICE_DATE_FROMDATE = " AND {" + SiteOneInvoiceModel.INVOICEDDATE
			+ "} BETWEEN (?invoiceDateFrom) AND SYSDATETIME() ";

	private String FIND_INVOICE_DATE_TODATE = " AND {" + SiteOneInvoiceModel.INVOICEDDATE
			+ "} BETWEEN DATEADD(DAY,-90,SYSDATETIME()) AND (?invoiceDateTo) ";

	private String FIND_INVOICE_DATE = " AND {" + SiteOneInvoiceModel.INVOICEDDATE + "} BETWEEN DATEADD(DAY,-90,SYSDATETIME()) AND SYSDATETIME() ";

	private static final String FIND_INVOICES_BY_SHIPTOS_AND_STATUS = FIND_INVOICES_FOR_SHIPTOS_QUERY + " AND {"
			+ SiteOneInvoiceModel.STATUS + "} IN (?statusList)";


	private static final String SORT_INVOICES_BY_DATE = " ORDER BY {" + SiteOneInvoiceModel.INVOICEDDATE + "} DESC";

	private static final String SORT_INVOICES_BY_INVOICE_NUMBER = " ORDER BY {" + SiteOneInvoiceModel.INVOICENUMBER + "} DESC";

	private static final String SORT_INVOICES_BY_ORDER_NUMBER = " ORDER BY {" + SiteOneInvoiceModel.ORDERNUMBER + "} DESC";

	private static final String SORT_INVOICES_BY_PURCHASE_ORDER_NUMBER = " ORDER BY {" + SiteOneInvoiceModel.PURCHASEORDERNUMBER
			+ "} DESC";

	private static final String SORT_INVOICES_BY_ORDER_TOTAL_AMOUNT = " ORDER BY {" + SiteOneInvoiceModel.ORDERTOTALPRICE
			+ "} DESC";
	private static String FIND_USERS = "SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target} }  WHERE {b2bunit:uid} IN (?unitId) AND (UPPER({b2bcustomer:name}) LIKE UPPER(?customerName))";
	private static final String ORDERBY_DATE = " ORDER BY  {creationtime}";
	private static final String ORDERBY_NAME = " ORDER BY CustomerName";
	private static final String ORDERBY_UNIT = " ORDER BY UnitName";
	private static final String FIND_ALL_USERS = "SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName FROM { B2BCustomer AS b2bcustomer JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk} JOIN B2BUnit AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}} WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' AND (UPPER({b2bcustomer:name}) LIKE UPPER(?customerName))";

	//private static final String SORT_INVOICES_BY_STATUS = " ORDER BY {" + SiteOneInvoiceModel.STATUS + "} ASC";

	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	private List<InvoiceStatus> filterInvoiceStatusList;

	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	public DefaultSiteOnePagedB2BCustomerDao(final String typeCode)
	{
		super(typeCode);

	}

	@Override
	public SearchPageData<B2BCustomerModel> find(final PageableData pageableData)
	{
		final List sortQueries = Arrays.asList(new SortQueryData[]

		{ this.createSortQueryData("byName", new HashMap(), SortParameters.singletonAscending("name"), pageableData),
				this.createSortQueryData("byDate", new HashMap(), SortParameters.singletonAscending("creationtime"), pageableData),
				this.createSortQueryData("byUnit",
						"SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target} }  ORDER BY UnitName ") });// 67 68
		LOG.info("sortQueries");
		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, new HashMap(), pageableData);
	}


	@Override
	public SearchPageData<B2BCustomerModel> findPagedCustomersForUnitSearch(final PageableData pageableData,
			final List<String> userUnitIds, final String Uid, final String trimmedSearchParam, final String sortCode,
			final Boolean isAdmin)
	{
		final HashMap queryParams = new HashMap(2);

		queryParams.put("unitId", userUnitIds);
		queryParams.put("Uid", Uid);
		queryParams.put("customerName", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");

		List sortQueries = null;

		if (isAdmin != null && isAdmin)
		{

			setQuerybyRole(sortCode);
			sortQueries = Arrays.asList(new SortQueryData[]
			{ this.createSortQueryData("byDate", createQuery(FIND_USERS)),
					this.createSortQueryData("byName", createQuery(FIND_USERS)),
					this.createSortQueryData("byUnit", createQuery(FIND_USERS)) });
		}
		else
		{

			FIND_USERS = "SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target} }  WHERE {b2bunit:uid} IN (?unitId) AND (UPPER({b2bcustomer:name}) LIKE UPPER(?customerName))";

			sortQueries = Arrays.asList(new SortQueryData[]
			{ this.createSortQueryData("byDate", createQuery(FIND_USERS, ORDERBY_DATE)),
					this.createSortQueryData("byName", createQuery(FIND_USERS, ORDERBY_NAME)),
					this.createSortQueryData("byUnit", createQuery(FIND_USERS, ORDERBY_UNIT)) });
		}
		LOG.info("sortQueries===" + sortQueries);
		LOG.info(this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData));

		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData);
	}

	@Override
	public SearchPageData<B2BCustomerModel> findPagedCustomersForUnitSearch(final PageableData pageableData,
			final String userUnitId, final String trimmedSearchParam, final String sortCode, final Boolean isAdmin,
			final Boolean shipToUnitFlag)
	{
		final HashMap queryParams = new HashMap(2);

		if (shipToUnitFlag)
		{
			queryParams.put("path", "%" + userUnitId);
		}
		else
		{
			queryParams.put("path", "%" + userUnitId + "%");
		}
		queryParams.put("customerName", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");

		List sortQueries = null;

		if (isAdmin != null && isAdmin)
		{

			final String findAllAdminUsers = setQuerybyRoleForAllUsers(sortCode);
			sortQueries = Arrays.asList(new SortQueryData[]
			{ this.createSortQueryData("byDate", createQuery(findAllAdminUsers)),
					this.createSortQueryData("byName", createQuery(findAllAdminUsers)),
					this.createSortQueryData("byUnit", createQuery(findAllAdminUsers)) });
		}
		else
		{
			sortQueries = Arrays.asList(new SortQueryData[]
			{ this.createSortQueryData("byDate", createQuery(FIND_ALL_USERS, ORDERBY_DATE)),
					this.createSortQueryData("byName", createQuery(FIND_ALL_USERS, ORDERBY_NAME)),
					this.createSortQueryData("byUnit", createQuery(FIND_ALL_USERS, ORDERBY_UNIT)) });
		}
		LOG.info("sortQueries===" + sortQueries);
		LOG.info(this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData));

		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData);
	}

	/**
	 * @param sortCode
	 */
	private void setQuerybyRole(final String sortCode)
	{
		String roleGroup = null;


		roleGroup = "b2badmingroup";

		if (sortCode.equalsIgnoreCase("byName"))
		{
			FIND_USERS = "select temptable.pk,CustomerName,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName, {b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}}  WHERE {b2bunit:uid} IN (?unitId) AND UPPER({b2bcustomer:name}) LIKE UPPER(?customerName)}}) as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
					+ roleGroup + "'}}) order by CustomerName";

		}
		else if (sortCode.equalsIgnoreCase("byDate"))
		{
			FIND_USERS = "select temptable.pk,CustomerName,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName, {b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}}  WHERE {b2bunit:uid} IN  (?unitId) AND UPPER({b2bcustomer:name}) LIKE UPPER(?customerName)}}) as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
					+ roleGroup + "'}}) order by CreationTime";

		}
		else if (sortCode.equalsIgnoreCase("byUnit"))
		{
			FIND_USERS = "select temptable.pk,CustomerName,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName, {b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}}  WHERE {b2bunit:uid} IN  (?unitId) AND UPPER({b2bcustomer:name}) LIKE UPPER(?customerName)}}) as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
					+ roleGroup + "'}}) order by UnitName";

		}


	}

	private String setQuerybyRoleForAllUsers(final String sortCode)
	{
		String roleGroup = null;

		String findAllAdminUsers = "";
		roleGroup = "b2badmingroup";

		if (sortCode.equalsIgnoreCase("byName"))
		{
			findAllAdminUsers = "select temptable.pk,CustomerName,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName,{b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}}  WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' AND UPPER({b2bcustomer:name}) LIKE UPPER(?customerName)}})  as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
					+ roleGroup + "'}}) order by CustomerName";
		}
		else if (sortCode.equalsIgnoreCase("byDate"))
		{
			findAllAdminUsers = "select temptable.pk,CustomerName,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName,{b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}}  WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' AND UPPER({b2bcustomer:name}) LIKE UPPER(?customerName)}})  as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
					+ roleGroup + "'}}) order by CreationTime";
		}
		else if (sortCode.equalsIgnoreCase("byUnit"))
		{
			findAllAdminUsers = "select temptable.pk,CustomerName,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bunit:name} AS UnitName,{b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer  JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk}  JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}}  WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' AND UPPER({b2bcustomer:name}) LIKE UPPER(?customerName)}})  as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
					+ roleGroup + "'}}) order by UnitName";
		}
		return findAllAdminUsers;
	}


	@Override
	public SearchPageData<SiteOneInvoiceModel> findInvoicesBasedOnShipToS(final List<String> shipTos, final InvoiceStatus[] status,
			final PageableData pageableData, final String trimmedSearchParam, final Date dateFrom, final Date dateTo)
	{
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		LOG.info("shipTos=====" + shipTos);
		LOG.info("shipTos size=====" + shipTos.size());
		queryParams.put("shipTos", shipTos);
		queryParams.put("poNumber", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("orderNumber", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("invoiceNumber", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");


		if (Config.getBoolean(ENV_PROPERTIES, false))
		{
			FIND_INVOICE_DATE_TODATE = " AND {" + SiteOneInvoiceModel.INVOICEDDATE
					+ "} BETWEEN DATE_SUB(NOW(), INTERVAL 90 DAY) AND (?invoiceDateTo) ";
			FIND_INVOICE_DATE = " AND {" + SiteOneInvoiceModel.INVOICEDDATE
					+ "} BETWEEN DATE_SUB(NOW(), INTERVAL 90 DAY) AND NOW() ";
		}

		String filterClauseDate = StringUtils.EMPTY;

		if (null != dateFrom && null != dateTo)
		{
			queryParams.put("invoiceDateFrom", dateFrom);
			queryParams.put("invoiceDateTo", dateTo);
			filterClauseDate = FIND_INVOICE_DATE_FROMTODATE;
		}
		else if (null != dateFrom && null == dateTo)
		{
			queryParams.put("invoiceDateFrom", dateFrom);
			filterClauseDate = FIND_INVOICE_DATE_FROMDATE;
		}
		else if (null == dateFrom && null != dateTo)
		{
			queryParams.put("invoiceDateTo", dateTo);
			filterClauseDate = FIND_INVOICE_DATE_TODATE;
		}
		else if (null == dateFrom && null == dateTo)
		{
			filterClauseDate = FIND_INVOICE_DATE;
		}

		String filterClause = StringUtils.EMPTY;
		if (CollectionUtils.isNotEmpty(getFilterInvoiceStatusList()))
		{
			queryParams.put("filterStatusList", getFilterInvoiceStatusList());
			filterClause = FILTER_INVOICE_STATUS;
		}

		final List<SortQueryData> sortQueries;
		if (ArrayUtils.isNotEmpty(status))
		{
			queryParams.put("statusList", Arrays.asList(status));

			sortQueries = Arrays
					.asList(
							createSortQueryData("byDate",
									createQuery(FIND_INVOICES_BY_SHIPTOS_AND_STATUS, filterClause, filterClauseDate,
											SORT_INVOICES_BY_DATE)),
							createSortQueryData("byInvoiceNumber",
									createQuery(FIND_INVOICES_BY_SHIPTOS_AND_STATUS, filterClause, filterClauseDate,
											SORT_INVOICES_BY_INVOICE_NUMBER)),
							createSortQueryData("byOrderNumber",
									createQuery(FIND_INVOICES_BY_SHIPTOS_AND_STATUS, filterClause, filterClauseDate,
											SORT_INVOICES_BY_ORDER_NUMBER)),
							createSortQueryData("byPONumber",
									createQuery(FIND_INVOICES_BY_SHIPTOS_AND_STATUS, filterClause, filterClauseDate,
											SORT_INVOICES_BY_PURCHASE_ORDER_NUMBER)),
							createSortQueryData("byAmount", createQuery(FIND_INVOICES_BY_SHIPTOS_AND_STATUS, filterClause,
									filterClauseDate, SORT_INVOICES_BY_ORDER_TOTAL_AMOUNT)));
		}
		else
		{

			sortQueries = Arrays.asList(
					createSortQueryData("byDate",
							createQuery(FIND_INVOICES_FOR_SHIPTOS_QUERY, filterClause, filterClauseDate, SORT_INVOICES_BY_DATE)),
					createSortQueryData("byInvoiceNumber",
							createQuery(FIND_INVOICES_FOR_SHIPTOS_QUERY, filterClause, filterClauseDate,
									SORT_INVOICES_BY_INVOICE_NUMBER)),
					createSortQueryData("byOrderNumber",
							createQuery(FIND_INVOICES_FOR_SHIPTOS_QUERY, filterClause, filterClauseDate, SORT_INVOICES_BY_ORDER_NUMBER)),
					createSortQueryData("byPONumber",
							createQuery(FIND_INVOICES_FOR_SHIPTOS_QUERY, filterClause, filterClauseDate,
									SORT_INVOICES_BY_PURCHASE_ORDER_NUMBER)),
					createSortQueryData("byAmount", createQuery(FIND_INVOICES_FOR_SHIPTOS_QUERY, filterClause, filterClauseDate,
							SORT_INVOICES_BY_ORDER_TOTAL_AMOUNT)));
		}
		LOG.info("sortQueries=====" + sortQueries);
		LOG.info("queryParams=====" + queryParams);
		LOG.info(sortQueries);
		LOG.info(queryParams);
		return getPagedFlexibleSearchService().search(sortQueries, "byDate", queryParams, pageableData);
	}

	protected String createQuery(final String... queryClauses)
	{
		final StringBuilder queryBuilder = new StringBuilder();

		for (final String queryClause : queryClauses)
		{
			queryBuilder.append(queryClause);
		}

		return queryBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.dao.SiteOnePagedB2BCustomerDao#findCustomers(java.util.List)
	 */
	@Override
	public List<B2BCustomerModel> findCustomers(final List<String> userIds)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {B2BCustomer} where {uid} in (?userIds) and {active}='1'");
		query.addQueryParameter("userIds", userIds);
		final SearchResult<B2BCustomerModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.dao.SiteOnePagedB2BCustomerDao#findPagedCustomersForUnitSearch(de.hybris.platform.
	 * commerceservices.search.pagedata.PageableData, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SearchPageData<B2BCustomerModel> findPagedCustomersForUnitSearch(final PageableData pageableData, final String unitId,
			final String trimmedSearchParam, final String sortCode)
	{

		final HashMap queryParams = new HashMap(2);
		queryParams.put("path", "%" + unitId + "%");
		queryParams.put("customerName", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");

		List sortQueries = null;


		sortQueries = Arrays.asList(new SortQueryData[]
		{ this.createSortQueryData("byDate", createQuery(FIND_ALL_USERS, ORDERBY_DATE)),
				this.createSortQueryData("byName", createQuery(FIND_ALL_USERS, ORDERBY_NAME)),
				this.createSortQueryData("byUnit", createQuery(FIND_ALL_USERS, ORDERBY_UNIT)) });
		LOG.info("sortQueries===" + sortQueries);
		LOG.info(this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData));

		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData);

	}

	@Override
	public List<B2BCustomerModel> findCustomersForUnit(final String unitId)
	{
		final String roleGroup = "b2bcustomergroup";
		final HashMap queryParams = new HashMap(2);
		queryParams.put("path", "%" + unitId + "%");

		final String FIND_ALL_USERS = "select temptable.pk,UnitName, CreationTime from ({{SELECT {b2bcustomer:pk}, {b2bunit:name} AS UnitName,{b2bcustomer:creationtime} as CreationTime FROM { B2BCustomer AS b2bcustomer JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk} JOIN B2BUnit AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}} WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' }}) as temptable ,{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} where {uid}='"
				+ roleGroup + "'}})";

		LOG.info(this.getFlexibleSearchService().search(FIND_ALL_USERS, queryParams));
		final SearchResult<B2BCustomerModel> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FIND_ALL_USERS, queryParams));

		return result.getResult();

	}

	/**
	 * @return the b2bUnitService
	 */
	public B2BUnitService getB2bUnitService()
	{
		return b2bUnitService;
	}

	/**
	 * @param b2bUnitService
	 *           the b2bUnitService to set
	 */
	public void setB2bUnitService(final B2BUnitService b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

	/**
	 * @return the filterInvoiceStatusList
	 */
	public List<InvoiceStatus> getFilterInvoiceStatusList()
	{
		return filterInvoiceStatusList;
	}

	/**
	 * @param filterInvoiceStatusList
	 *           the filterInvoiceStatusList to set
	 */
	public void setFilterInvoiceStatusList(final List<InvoiceStatus> filterInvoiceStatusList)
	{
		this.filterInvoiceStatusList = filterInvoiceStatusList;
	}

	/**
	 * @return the pagedFlexibleSearchService
	 */
	@Override
	public PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	/**
	 * @param pagedFlexibleSearchService
	 *           the pagedFlexibleSearchService to set
	 */
	@Override
	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
	}

	@Override
	protected SortQueryData createSortQueryData(final String sortCode, final String query)
	{
		final SortQueryData result = new SortQueryData();
		result.setSortCode(sortCode);
		result.setQuery(query);
		return result;
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.customer.dao.SiteOnePagedB2BCustomerDao#findInvoicesBasedOnUnit(java.lang.Object,
	 * java.util.List, com.siteone.core.enums.InvoiceStatus[],
	 * de.hybris.platform.commerceservices.search.pagedata.PageableData)
	 */

}