package com.siteone.integration.jobs.datareport;

import com.siteone.core.model.CustomerDataReportCronJobModel;
import com.siteone.integration.jobs.datareport.service.SiteOneCustomerDataReportService;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class CustomerDataReportCronJob extends AbstractJobPerformable<CustomerDataReportCronJobModel> {

	private SiteOneCustomerDataReportService siteOneCustomerDataReportService;

	@Override
	public PerformResult perform(CustomerDataReportCronJobModel customerDataReportCronJobModel) {

		getSiteOneCustomerDataReportService().exportCustomerData(customerDataReportCronJobModel);

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public SiteOneCustomerDataReportService getSiteOneCustomerDataReportService() {
		return siteOneCustomerDataReportService;
	}

	public void setSiteOneCustomerDataReportService(SiteOneCustomerDataReportService siteOneCustomerDataReportService) {
		this.siteOneCustomerDataReportService = siteOneCustomerDataReportService;
	}

}
