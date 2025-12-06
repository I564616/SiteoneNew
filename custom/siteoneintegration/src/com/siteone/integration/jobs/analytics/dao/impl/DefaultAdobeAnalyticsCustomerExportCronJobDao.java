package com.siteone.integration.jobs.analytics.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.siteone.integration.customer.data.SiteoneAnalyticsCustomerMappingData;
import com.siteone.integration.customer.data.SiteoneAnalyticsRealtimeCustomerMappingData;
import com.siteone.integration.jobs.analytics.dao.AdobeAnalyticsCustomerExportCronJobDao;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultAdobeAnalyticsCustomerExportCronJobDao extends AbstractItemDao
		implements AdobeAnalyticsCustomerExportCronJobDao {
	private static String FIND_CUSTOMERS = "select {rqac:uuid} as uuid , {rqac:emailAddress} as email, {b2bunit:uid} as UEAccountnumber,{rqac:creationtime} as requesttime, {b2bcustomer:creationtime} as creationtime,{rqac:typeOfCustomer} as AccountType, {rqac:contrPrimaryBusiness} as Primarybusiness,  {rqac:languagePreference} as preferredLanguage FROM { B2BCustomer AS b2bcustomer JOIN SiteoneRequestAccount as rqac ON {b2bcustomer:uid} = {rqac:emailAddress} join B2BUnit as b2bunit on {b2bunit:pk} = {b2bcustomer:defaultb2bunit}}  where {b2bcustomer:creationtime} between (?previousDay) AND (?today)";
	private static String FIND_REAL_TIME_CUSTOMERS = "select {b2bcustomer:uuid} as uuid , {b2bcustomer:uid} as email,{b2bcustomer:creationtime} as requesttime, {b2bcustomer:creationtime} as creationtime,{b2bunit:tradeClassName} as AccountType, {b2bunit:tradeclass} as Primarybusiness, {b2bunit:uid} as UEAccountnumber, {lang:isocode} as preferredLanguage , {b2bcustomer:ueType} as ueType FROM { B2BCustomer AS b2bcustomer JOIN Language as lang ON {b2bcustomer:languagePreference} = {lang:pk}  JOIN SiteoneRequestAccount as rqac ON {b2bcustomer:uuid} = {rqac:uuid} join B2BUnit as b2bunit on {b2bunit:pk} = {b2bcustomer:defaultb2bunit}}  where {b2bcustomer:isRealtimeAccount} = '1' and {b2bcustomer:uuid} != '' and {b2bcustomer:creationtime} between (?previousDay) AND (?today)";
	@Override
	public List<SiteoneAnalyticsCustomerMappingData> getNewlyCreatedCustomersList() {
		List<SiteoneAnalyticsCustomerMappingData> siteoneAnalyticsCustomerMappingList = new ArrayList<>();
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_CUSTOMERS);
		query.setResultClassList(Arrays.asList(String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class));
		DateTime today = new DateTime().withTimeAtStartOfDay();
		DateTime previousDay = today.minusDays(7).withTimeAtStartOfDay();
		query.addQueryParameter("previousDay", previousDay.toDate());
		query.addQueryParameter("today", today.toDate());
		final SearchResult<List<Object>> result = getFlexibleSearchService().search(query);
		for (final List<Object> row : result.getResult())
		{
			SiteoneAnalyticsCustomerMappingData data = new SiteoneAnalyticsCustomerMappingData();
			data.setUuid((String) row.get(0));
			data.setEmail((String) row .get(1));
			data.setUeAccountNumber((String) row.get(2));
			data.setRequestedTime((String) row.get(3));
			data.setCreationTime((String) row.get(4));
			data.setAccountType((String) row.get(5));
			data.setPrimarybusiness((String) row.get(6));
			data.setPreferredLanguage((String) row.get(7));
			siteoneAnalyticsCustomerMappingList.add(data);
		}
		return siteoneAnalyticsCustomerMappingList;
	}
	
	

    @Override
    public List<SiteoneAnalyticsRealtimeCustomerMappingData> getNewlyCreatedRealtimeCustomersList() {
    List<SiteoneAnalyticsRealtimeCustomerMappingData> siteoneAnalyticsRealtimeCustomerMappingList = new ArrayList<>();
    final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_REAL_TIME_CUSTOMERS); 
    query.setResultClassList(Arrays.asList(String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,String.class));
    DateTime today = new DateTime().withTimeAtStartOfDay();
	DateTime previousDay = today.minusDays(7).withTimeAtStartOfDay();
	query.addQueryParameter("previousDay", previousDay.toDate());
	query.addQueryParameter("today", today.toDate());
    final SearchResult<List<Object>> result = getFlexibleSearchService().search(query);
    for (final List<Object> row : result.getResult())
    {
	    SiteoneAnalyticsRealtimeCustomerMappingData data = new SiteoneAnalyticsRealtimeCustomerMappingData();
	    data.setUuid((String) row.get(0));
	    data.setEmail((String) row .get(1));
	    data.setRequestedTime((String) row.get(2));
	    data.setCreationTime((String) row.get(3));
	    data.setAccountType((String) row.get(4));
	    data.setPrimarybusiness((String) row.get(5));
	    data.setUeAccount((String) row.get(6));
	    data.setPreferredLanguage((String) row.get(7));
	    data.setUuidType((String) row.get(8));
	    if(data.getUeAccount()!=null) {
	    data.setAccountNumber("Exists");
	    }
	    else {
	    	data.setAccountNumber("Not Exists");
	    }
	    siteoneAnalyticsRealtimeCustomerMappingList.add(data);
    }
    return siteoneAnalyticsRealtimeCustomerMappingList;
  }
}

