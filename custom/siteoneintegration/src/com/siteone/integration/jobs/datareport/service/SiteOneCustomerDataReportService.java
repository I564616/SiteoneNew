package com.siteone.integration.jobs.datareport.service;

import com.siteone.core.model.CustomerDataReportCronJobModel;

public interface SiteOneCustomerDataReportService {

	void exportCustomerData(CustomerDataReportCronJobModel customerDataReportCronJobModel);
}
