/**
 *
 */
package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.joda.time.DateTime;

import com.siteone.core.cronjob.dao.PIMBatchFailureReportCronJobDao;
import com.siteone.core.featureswitch.dao.impl.DefaultSiteOneFeatureSwitchDao;
import com.siteone.core.model.process.PimBatchFailureEmailProcessModel;


/**
 * @author SR02012
 *
 */
public class DefaultPIMBatchFailureReportCronJobDao extends AbstractItemDao implements PIMBatchFailureReportCronJobDao
{
	public static final String FAILED_BATCH_YESTERDAY_QUERY = "SELECT {" + PimBatchFailureEmailProcessModel.PK + "} FROM {"
			+ PimBatchFailureEmailProcessModel._TYPECODE + "} WHERE {" + PimBatchFailureEmailProcessModel.FAILEDBATCH
			+ "} IS NOT NULL "	+ "AND {" + PimBatchFailureEmailProcessModel.CREATIONTIME + "} > ?timestamp ";

	@Resource(name = "defaultSiteOneFeatureSwitchDao")
	DefaultSiteOneFeatureSwitchDao defaultSiteOneFeatureSwitchDao;
	
	@Override
	public List<PimBatchFailureEmailProcessModel> getFailedBatchForDay()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FAILED_BATCH_YESTERDAY_QUERY);
		int hours = Integer.parseInt(defaultSiteOneFeatureSwitchDao.getSwitchLongValue("PIMReportTimestamp"))/3600;
      query.addQueryParameter("timestamp", new DateTime().minusHours(hours).toDate());
		final SearchResult<PimBatchFailureEmailProcessModel> result = getFlexibleSearchService().search(query);
		if(null != result && result.getCount()!=0)
		{
		return result.getResult();
		}
		return Collections.emptyList();
	}

}
