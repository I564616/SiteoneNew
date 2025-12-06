package com.siteone.integration.jobs.analytics.dao;

import java.util.List;

import de.hybris.platform.core.model.order.OrderModel;

public interface AdobeAnalyticsOrderExportCronJobDao {

	List<OrderModel> getListOfOnlineOrdersPlacedYesterday();

}
