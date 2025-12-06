package com.siteone.integration.jobs.analytics.service;

import java.io.IOException;

import com.siteone.core.model.AdobeAnalyticsOrderExportCronJobModel;

public interface AdobeAnalyticsOrderExportCronJobService {

	void exportOrderMappingReport(AdobeAnalyticsOrderExportCronJobModel cronjobModel) throws IOException;
}
