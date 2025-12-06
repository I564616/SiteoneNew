/**
 * 
 */
package com.siteone.core.jobs.report.service.impl;

import com.siteone.core.jobs.report.dao.SiteoneJobsStatusCronJobDAO;
import com.siteone.core.jobs.report.service.SiteoneJobsStatusCronJobService;
import com.siteone.facade.customer.info.SiteOneCustInfoData;
import com.siteone.facade.reports.SiteOneJobsStatusData;
import com.siteone.integration.customer.info.SiteOneCustInfoResponeData;

import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;


/**
 * @author NMangal
 * 
 */
public class SiteoneJobsStatusCronJobServiceImpl implements SiteoneJobsStatusCronJobService
{


	private SiteoneJobsStatusCronJobDAO siteoneJobsStatusCronJobDAO;
	
	@Resource(name = "siteOneCustInfoDataConverter")
	private Converter<CronJobModel, SiteOneJobsStatusData> siteOneCustInfoDataConverter;


	public  List<CronJobModel> sendSiteoneCronJobStatusReport()
	{
		final List<CronJobModel>cronJobList = getSiteoneJobsStatusCronJobDAO().getSiteoneCronJobs();
		/*final List<SiteOneJobsStatusData>jobsStausList = new ArrayList<SiteOneJobsStatusData>();
		
		for(final CronJobModel cronjob: cronJobList){
			jobsStausList.add(siteOneCustInfoDataConverter.convert(cronjob));
		}*/
		
		return cronJobList;

	}

	public SiteoneJobsStatusCronJobDAO getSiteoneJobsStatusCronJobDAO()
	{
		return siteoneJobsStatusCronJobDAO;
	}

	public void setSiteoneJobsStatusCronJobDAO(final SiteoneJobsStatusCronJobDAO siteoneJobsStatusCronJobDAO)
	{
		this.siteoneJobsStatusCronJobDAO = siteoneJobsStatusCronJobDAO;
	}


}
