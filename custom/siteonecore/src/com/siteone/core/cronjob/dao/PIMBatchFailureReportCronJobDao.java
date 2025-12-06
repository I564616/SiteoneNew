/**
 *
 */
package com.siteone.core.cronjob.dao;

import java.util.List;

import com.siteone.core.model.process.PimBatchFailureEmailProcessModel;


/**
 * @author SR02012
 *
 */
public interface PIMBatchFailureReportCronJobDao
{
	public List<PimBatchFailureEmailProcessModel> getFailedBatchForDay();
}
