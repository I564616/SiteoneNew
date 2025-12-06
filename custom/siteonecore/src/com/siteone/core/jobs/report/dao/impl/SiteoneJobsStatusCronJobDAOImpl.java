package com.siteone.core.jobs.report.dao.impl;
/**
 * 
 */


import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.siteone.core.jobs.report.dao.SiteoneJobsStatusCronJobDAO;
import com.siteone.core.model.RegulatoryStatesModel;
import de.hybris.platform.util.Config;


/**
 * @author NMangal
 *
 */
public class SiteoneJobsStatusCronJobDAOImpl extends AbstractItemDao implements SiteoneJobsStatusCronJobDAO
{
	  public List<CronJobModel> getSiteoneCronJobs(){
		  
		  final FlexibleSearchQuery query = new FlexibleSearchQuery(
					"select {pk} from {cronjob } where {code} in (?cronJobsList)");

		  final String[] cronJobsList = Config.getString("cronjob.status.report.jobs", null).split(",");
		  
		  final List<String> cronJobsListAsArray = new ArrayList<String>(Arrays.asList(cronJobsList));
		  query.addQueryParameter("cronJobsList", cronJobsListAsArray);
		  final SearchResult<CronJobModel> result = getFlexibleSearchService().search(query);
		  final List<CronJobModel> cronjobs = result.getResult();
		  final List<CronJobModel> cronJobsListResult = new ArrayList<CronJobModel>();
		  cronJobsListResult.addAll(cronjobs);
		  
		  final FlexibleSearchQuery syncProductJobQuery = new FlexibleSearchQuery("select top 1 {c:pk} from { cronjob as c join job as j on {c:job} = {j:pk} } where  {j:code} like 'siteone_SyncProducts_StageToOnline'  order by {c:creationtime} desc" );
		  final SearchResult<CronJobModel> productSyncQueryresult = getFlexibleSearchService().search(syncProductJobQuery);
		  final List<CronJobModel> syncProductCronJob = productSyncQueryresult.getResult(); 
		  if(syncProductCronJob!=null && syncProductCronJob.get(0)!=null){
			  syncProductCronJob.get(0).setCode("siteone_SyncProducts_StageToOnline");
		  }
		  cronJobsListResult.addAll(syncProductCronJob);
		  
		  final FlexibleSearchQuery syncCAProductJobQuery = new FlexibleSearchQuery("select top 1 {c:pk} from { cronjob as c join job as j on {c:job} = {j:pk} } where  {j:code} like 'siteoneca_SyncProducts_StageToOnline'  order by {c:creationtime} desc" );
		  final SearchResult<CronJobModel> productCASyncQueryresult = getFlexibleSearchService().search(syncCAProductJobQuery);
		  final List<CronJobModel> syncCAProductCronJob = productCASyncQueryresult.getResult(); 
		  if(syncCAProductCronJob!=null && syncCAProductCronJob.get(0)!=null){
			  syncCAProductCronJob.get(0).setCode("siteoneca_SyncProducts_StageToOnline");
		  }
		  cronJobsListResult.addAll(syncCAProductCronJob);
		  
		  final FlexibleSearchQuery syncCategoryJobQuery = new FlexibleSearchQuery("select top 1 {c:pk} from { cronjob as c join job as j on {c:job} = {j:pk} } where  {j:code} like 'siteone_Category_Sync_StageToOnline'  order by {c:creationtime} desc" );
		  final SearchResult<CronJobModel> categorySyncQueryresult = getFlexibleSearchService().search(syncCategoryJobQuery);
		  final List<CronJobModel> syncCategoryJob = categorySyncQueryresult.getResult(); 
		  if(syncCategoryJob!=null && syncCategoryJob.get(0)!=null){
			  syncCategoryJob.get(0).setCode("siteone_Category_Sync_StageToOnline");
		  }
		  cronJobsListResult.addAll(syncCategoryJob);
		  
		  final FlexibleSearchQuery syncCACategoryJobQuery = new FlexibleSearchQuery("select top 1 {c:pk} from { cronjob as c join job as j on {c:job} = {j:pk} } where  {j:code} like 'siteoneca_Category_Sync_StageToOnline'  order by {c:creationtime} desc" );
		  final SearchResult<CronJobModel> categoryCASyncQueryresult = getFlexibleSearchService().search(syncCACategoryJobQuery);
		  final List<CronJobModel> syncCACategoryJob = categoryCASyncQueryresult.getResult(); 
		  if(syncCACategoryJob!=null && syncCACategoryJob.get(0)!=null){
			  syncCACategoryJob.get(0).setCode("siteoneca_Category_Sync_StageToOnline");
		  }
		  cronJobsListResult.addAll(syncCACategoryJob);
		  
		  return cronJobsListResult;
	  }
}
