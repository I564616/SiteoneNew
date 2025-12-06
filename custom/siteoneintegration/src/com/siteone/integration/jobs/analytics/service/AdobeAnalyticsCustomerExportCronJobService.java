package com.siteone.integration.jobs.analytics.service;

import java.io.IOException;

import com.siteone.core.model.AdobeAnalyticsCustomerExportCronJobModel;
import com.siteone.core.model.AdobeAnalyticsRealtimeCustomerExportCronJobModel;
import com.siteone.integration.jobs.analytics.AdobeAnalyticsRealtimeCustomerExportCronJob;

public interface AdobeAnalyticsCustomerExportCronJobService {

	void exportCustomerMappingReport(AdobeAnalyticsCustomerExportCronJobModel cronjobModel) throws IOException;
	
	void exportRealtimeCustomerMappingReport(AdobeAnalyticsRealtimeCustomerExportCronJobModel cronjobModel) throws IOException;
	
	String convertToHex(String text);
	
}
