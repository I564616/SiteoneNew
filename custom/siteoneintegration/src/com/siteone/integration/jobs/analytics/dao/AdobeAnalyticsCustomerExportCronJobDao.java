package com.siteone.integration.jobs.analytics.dao;

import java.util.List;

import com.siteone.integration.customer.data.SiteoneAnalyticsCustomerMappingData;
import com.siteone.integration.customer.data.SiteoneAnalyticsRealtimeCustomerMappingData;

public interface AdobeAnalyticsCustomerExportCronJobDao {

	List<SiteoneAnalyticsCustomerMappingData> getNewlyCreatedCustomersList();

	List<SiteoneAnalyticsRealtimeCustomerMappingData> getNewlyCreatedRealtimeCustomersList();

}
