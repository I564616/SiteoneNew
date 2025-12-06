/**
 *
 */
package com.siteone.core.ewallet.dao.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.commerceservices.customer.dao.impl.DefaultCustomerAccountDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.ewallet.dao.SiteOneEwalletDao;
import com.siteone.core.model.SiteoneEwalletCreditCardModel;


/**
 * @author pelango
 *
 */
public class DefaultSiteOneEwalletDao extends DefaultCustomerAccountDao implements SiteOneEwalletDao
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneEwalletDao.class);

	public static final String ASSIGN = "Assign";
	public static final String REVOKE = "Revoke";

	private static final String FIND_EWALLET_DETAILS_QUERY = "SELECT {PK} FROM {SiteOneEwalletCreditCard} WHERE {VAULTTOKEN} = ?vaultToken";

	private static String FIND_EWALLET_CARD_DETAILS = "SELECT {ewallet:PK}, {ewallet:nameOnCard} as nameOnCard, "
			+ "{ewallet:nickName} as nickName, {ewallet:creditCardType} as creditCardType, {ewallet:last4Digits} as last4Digits ,{ewallet:expDate} as expDate"
			+ "FROM {SiteOneEwalletCreditCard as ewallet "
			+ "JOIN ShipTo2WalletRelation AS b2bunitrelation ON {b2bunitrelation:target} = {ewallet:pk} "
			+ "JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:source} } "
			+ "WHERE {b2bunit:uid} IN (?unitId) AND {ewallet:active} = ?active"
			+ " AND (UPPER({ewallet:nameOnCard}) LIKE UPPER(?nameOnCard) OR UPPER({ewallet:nickName}) LIKE UPPER(?nickName) "
			+ "OR UPPER({ewallet:creditCardType}) LIKE UPPER(?creditCardType) OR UPPER({ewallet:last4Digits}) LIKE UPPER(?last4Digits))";

	private static final String FETCH_ADMIN_USERS_QUERY = "select temptable.pk,CustomerName,UnitName, Email, UnitId "
			+ "from ({{SELECT {b2bcustomer:pk}, {b2bcustomer:name} as CustomerName, {b2bcustomer:email} as Email, "
			+ "{b2bunit:name} AS UnitName, {b2bunit:uid} AS UnitId FROM { B2BCustomer AS b2bcustomer "
			+ "JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk} "
			+ "JOIN B2BUnit AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}} "
			+ "WHERE {b2bunit:path} like (?path1) OR {b2bunit:path} like (?path2) AND {b2bunit:active} = '1' }}) as temptable ,"
			+ "{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and {PrincipalGroupRelation:target} in "
			+ "({{select {pk} from {usergroup} where {uid}='b2badmingroup'}})";

	private static final String ORDERBY_NAME = " ORDER BY nameOnCard";
	private static final String ORDERBY_NICK_NAME = " ORDER BY nickName";
	private static final String ORDERBY_CARD_TYPE = " ORDER BY creditCardType";
	private static final String ORDERBY_EXP_DATE = " ORDER BY expDate";

	private static final String DEFAULT_SORT_CODE = "byName";
	private static String FIND_EWALLET_ORDER_STATUS = null;


	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	private PagedFlexibleSearchService pagedFlexibleSearchService;


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.ewallet.dao.SiteOneEwalletDao#getCreditCardDetails(java.lang.String)
	 */
	@Override
	public SiteoneEwalletCreditCardModel getCreditCardDetails(final String vaultToken)
	{
		final HashMap queryParams = new HashMap(2);
		queryParams.put("vaultToken", vaultToken);
		final SiteoneEwalletCreditCardModel result = flexibleSearchService
				.searchUnique(new FlexibleSearchQuery(FIND_EWALLET_DETAILS_QUERY, queryParams));
		return result;

	}

	@Override
	public SearchPageData<SiteoneEwalletCreditCardModel> getEWalletCardDetails(final PageableData pageableData,
			final String userUnitId, final String trimmedSearchParam, final String sortCode, final Boolean shipToFlag)
	{

		final HashMap queryParams = new HashMap(2);
		queryParams.put("nickName", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("nameOnCard", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("last4Digits", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");
		queryParams.put("creditCardType", null != trimmedSearchParam ? "%" + trimmedSearchParam + "%" : "%%");

		if (shipToFlag)
		{
			queryParams.put("path", "%" + userUnitId);
		}
		else
		{
			queryParams.put("path", "%" + userUnitId + "%");
		}

		queryParams.put("active", Boolean.TRUE);

		final int year = getCurrentYear();
		final Integer centuries = Integer.valueOf(String.valueOf(year).substring(2, 4));
		FIND_EWALLET_CARD_DETAILS = "SELECT {ewallet:PK}, {ewallet:nameOnCard} as nameOnCard, "
				//Migration | Query Change
				//+ "{ewallet:nickName} as nickName, {ewallet:creditCardType} as creditCardType," CAST('" + centuries
				//+ "'||substring({ewallet:expDate},3,2)||'-'|| substring({ewallet:expDate},1,2)||'-'|| '01' AS DATE) as expDate "
				+ "{ewallet:nickName} as nickName, {ewallet:creditCardType} as creditCardType,"
				+ "CAST(CONCAT('"+centuries+"',SUBSTRING({ewallet:expDate}, 3, 2),'-',SUBSTRING({ewallet:expDate},1,2),'-','01') AS DATE) as expDate "
				+ "FROM {SiteOneEwalletCreditCard as ewallet "
				+ "JOIN ShipTo2WalletRelation AS b2bunitrelation ON {b2bunitrelation:target} = {ewallet:pk} "
				+ "JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:source} } "
				+ "WHERE {b2bunit:path} like (?path) AND {ewallet:active} = ?active"
				+ " AND (UPPER({ewallet:nameOnCard}) LIKE UPPER(?nameOnCard) OR UPPER({ewallet:nickName}) LIKE UPPER(?nickName) "
				+ "OR UPPER({ewallet:creditCardType}) LIKE UPPER(?creditCardType) OR UPPER({ewallet:last4Digits}) LIKE UPPER(?last4Digits))";

		List sortQueries = null;
		sortQueries = Arrays.asList(new SortQueryData[]
		{ this.createSortQueryData("byName", createQuery(FIND_EWALLET_CARD_DETAILS, ORDERBY_NAME)),
				this.createSortQueryData("byExpDate", createQuery(FIND_EWALLET_CARD_DETAILS, ORDERBY_EXP_DATE)),
				this.createSortQueryData("byCardType", createQuery(FIND_EWALLET_CARD_DETAILS, ORDERBY_CARD_TYPE)),
				this.createSortQueryData("byNickName", createQuery(FIND_EWALLET_CARD_DETAILS, ORDERBY_NICK_NAME)) });

		LOG.info("sortQueries===" + sortQueries);

		return this.getPagedFlexibleSearchService().search(sortQueries, DEFAULT_SORT_CODE, queryParams, pageableData);

	}

	@Override
	public List<SiteoneEwalletCreditCardModel> getEWalletCardDetailsForCheckout(final String userUnitId)
	{
		final HashMap queryParams = new HashMap(2);
		queryParams.put("path", "%" + userUnitId + "%");
		queryParams.put("active", Boolean.TRUE);

		final int year = getCurrentYear();
		//final Integer decade = Integer.valueOf(year.substring(0,2));
		final Integer centuries = Integer.valueOf(String.valueOf(year).substring(2, 4));
		final Integer preCenturies = centuries - 1;
		final Integer expYear = year + 10;

		//Migration| Query change
		/*FIND_EWALLET_CARD_DETAILS = "SELECT {ewallet:PK}, {ewallet:nameOnCard} as nameOnCard, "
				+ "{ewallet:nickName} as nickName, {ewallet:creditCardType} as creditCardType,{ewallet:last4Digits} as last4Digits, "
				+ "CASE WHEN (CAST('" + centuries + "'||substring({expDate},3,2) AS INT)<=" + expYear + ") " + "THEN CAST('"
				+ centuries + "'||substring({ewallet:expDate},3,2)||'-'|| substring({ewallet:expDate},1,2)||'-'|| '01' AS DATE) "
				+ "WHEN (CAST('" + centuries + "'||substring({expDate},3,2) AS INT)>" + expYear + ") " + "THEN CAST('" + preCenturies
				+ "'||substring({ewallet:expDate},3,2)||'-'|| substring({ewallet:expDate},1,2)||'-'|| '01' AS DATE) "
				+ "END as expDate " + "FROM {SiteOneEwalletCreditCard as ewallet "
				+ "JOIN ShipTo2WalletRelation AS b2bunitrelation ON {b2bunitrelation:target} = {ewallet:pk} "
				+ "JOIN B2BUnit  AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:source} } "
				+ "WHERE {b2bunit:path} like (?path) AND {ewallet:active} = ?active";*/

		FIND_EWALLET_CARD_DETAILS = "SELECT {ewallet:PK}, {ewallet:nameOnCard} as nameOnCard, "
				+ "{ewallet:nickName} as nickName, {ewallet:creditCardType} as creditCardType,{ewallet:last4Digits} as last4Digits, "
				+ "CASE WHEN (CAST(CONCAT('" + centuries + "',substring({expDate},3,2)) AS INT)<=" + expYear + ") " + "THEN CAST(CONCAT('"
				+ centuries + "',substring({ewallet:expDate},3,2),'-',substring({ewallet:expDate},1,2),'-','01') AS DATE) "
				+ "WHEN (CAST(CONCAT('" + centuries + "',substring({expDate},3,2)) AS INT)>" + expYear + ") " + "THEN CAST(CONCAT('" + preCenturies
				+ "',substring({ewallet:expDate},3,2),'-',substring({ewallet:expDate},1,2),'-','01') AS DATE) "
				+ "END as expDate " + "FROM {SiteOneEwalletCreditCard as ewallet "
				+ "JOIN ShipTo2WalletRelation AS b2bunitrelation ON {b2bunitrelation:target} = {ewallet:pk} "
				+ "JOIN B2BUnit AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:source} } "
				+ "WHERE {b2bunit:path} like (?path) AND {ewallet:active} = ?active";

		final SearchResult<SiteoneEwalletCreditCardModel> result = flexibleSearchService
				.search(new FlexibleSearchQuery(FIND_EWALLET_CARD_DETAILS, queryParams));
		return result.getResult();
	}

	@Override
	public List<Object> getEwalletOrderStatus(final String vaultToken)
	{
		final HashMap queryParams = new HashMap(2);
		queryParams.put("vaulttoken", vaultToken);

		FIND_EWALLET_ORDER_STATUS = "select {orderstatus.pk} as pk,{orderstatus.code} as Status From {OrderStatus as orderstatus JOIN Order as order on {order.status} = {orderstatus.pk} JOIN SiteoneCreditCardPaymentInfo as p on {order.paymentInfoList} LIKE CONCAT( '%', CONCAT( {p.PK} , '%' ) )} where {p.VAULTTOKEN}= ?vaulttoken AND {orderstatus.code} NOT IN ('CANCELLED', 'CLOSED')";

		return flexibleSearchService.search(new FlexibleSearchQuery(FIND_EWALLET_ORDER_STATUS, queryParams)).getResult();
	}

	@Override
	public List<B2BCustomerModel> getAdminUsers(final List<String> unitIds)
	{
		final HashMap queryParams = new HashMap(2);
		queryParams.put("path1", "%" + unitIds.get(0));
		if (unitIds.size() > 1)
		{
			queryParams.put("path2", "%" + unitIds.get(1));
		}
		else
		{
			queryParams.put("path2", "");
		}
		final SearchResult<B2BCustomerModel> result = getFlexibleSearchService()
				.search(new FlexibleSearchQuery(FETCH_ADMIN_USERS_QUERY, queryParams));
		return result.getResult();

	}

	public int getCurrentYear()
	{
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		return year;
	}

	@Override
	protected String createQuery(final String... queryClauses)
	{
		final StringBuilder queryBuilder = new StringBuilder();

		for (final String queryClause : queryClauses)
		{
			queryBuilder.append(queryClause);
		}

		return queryBuilder.toString();
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

	@Override
	public SearchPageData<B2BCustomerModel> getPagedAssignRevokeUsers(final PageableData pageableData, final String userUnitId,
			final String sortCode, final String action, final String vaultToken)
	{
		String USER_DETAILS = null;
		final HashMap queryParams = new HashMap(2);

		queryParams.put("path", "%" + userUnitId + "%");
		queryParams.put("active", Boolean.TRUE);
		queryParams.put("vaultToken", vaultToken);
		if (action.equalsIgnoreCase(REVOKE))
		{
			USER_DETAILS = "select temptable.pk, name, UnitName from "
					+ "({{SELECT {b2bcustomer:pk},{b2bcustomer:name} as name, {b2bunit:name} AS UnitName FROM { B2BCustomer AS b2bcustomer "
					+ "JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk} "
					+ "JOIN B2BUnit AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}} "
					+ "WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' }}) as temptable ,"
					+ "{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and "
					+ "{PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} "
					+ "where {uid}='b2bcustomergroup'}}) and temptable.pk IN "
					+ "({{ select {cust:pk} as pk from {b2bcustomer as cust "
					+ "JOIN b2bunit as unit ON {unit:pk} = {cust:defaultb2bunit} "
					+ "JOIN Customer2WalletRelation as relation ON {relation:source}={cust:pk} "
					+ "JOIN SiteOneEwalletCreditCard as ewallet ON {relation:target} = {ewallet:pk}} "
					+ "where {unit:uid} like (?path) and {ewallet:vaulttoken} = ?vaultToken }}) order by name";
		}
		else if (action.equalsIgnoreCase(ASSIGN))
		{
			USER_DETAILS = "select temptable.pk, name, UnitName from "
					+ "({{SELECT {b2bcustomer:pk},{b2bcustomer:name} as name, {b2bunit:name} AS UnitName FROM { B2BCustomer AS b2bcustomer "
					+ "JOIN PrincipalGroupRelation AS b2bunitrelation ON {b2bunitrelation:source} = {b2bcustomer:pk} "
					+ "JOIN B2BUnit AS b2bunit ON {b2bunit:pk} = {b2bunitrelation:target}} "
					+ "WHERE {b2bunit:path} like (?path) AND {b2bunit:active} = '1' }}) as temptable ,"
					+ "{PrincipalGroupRelation} where {PrincipalGroupRelation:source}=temptable.pk and "
					+ "{PrincipalGroupRelation:target} in ({{select {pk} from {usergroup} "
					+ "where {uid}='b2bcustomergroup'}}) and temptable.pk NOT IN "
					+ "({{ select {cust:pk} as pk from {b2bcustomer as cust "
					+ "JOIN b2bunit as unit ON {unit:pk} = {cust:defaultb2bunit} "
					+ "JOIN Customer2WalletRelation as relation ON {relation:source}={cust:pk} "
					+ "JOIN SiteOneEwalletCreditCard as ewallet ON {relation:target} = {ewallet:pk}} "
					+ "where {unit:uid} like (?path) and {ewallet:vaulttoken} = ?vaultToken }}) order by name";
		}

		LOG.info("sortQueries===" + USER_DETAILS);

		return this.getPagedFlexibleSearchService().search(USER_DETAILS, queryParams, pageableData);

	}

}
