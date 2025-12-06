package com.siteone.integration.jobs.analytics.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.siteone.integration.jobs.analytics.dao.AdobeAnalyticsOrderExportCronJobDao;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultAdobeAnalyticsOrderExportCronJobDao extends AbstractItemDao
		implements AdobeAnalyticsOrderExportCronJobDao {

	public static final String ORDERS_PLACED_YESTERDAY_QUERY = "SELECT {" + OrderModel.PK + "} FROM {"
			+ OrderModel._TYPECODE + "} WHERE {" + OrderModel.HYBRISORDERNUMBER + "} IS NOT NULL AND {"
			+ OrderModel.CODE + "} LIKE 'M%' AND {" + OrderModel.EXTERNALSYSTEMID + "} IN (?externalSystemIDs) AND {"
			+ OrderModel.CREATIONTIME + "} BETWEEN (?previousDay) AND (?today)";

	public List<OrderModel> getListOfOnlineOrdersPlacedYesterday() {
		final FlexibleSearchQuery query = new FlexibleSearchQuery(ORDERS_PLACED_YESTERDAY_QUERY);
		String[] externalSystemIDs = new String[] { "2", "7", "13" };
		query.addQueryParameter("externalSystemIDs", Arrays.asList(externalSystemIDs));
		DateTime today = new DateTime().withTimeAtStartOfDay();
		DateTime previousDay = today.minusDays(7).withTimeAtStartOfDay();
		query.addQueryParameter("previousDay", previousDay.toDate());
		query.addQueryParameter("today", today.toDate());
		final SearchResult<OrderModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

}
