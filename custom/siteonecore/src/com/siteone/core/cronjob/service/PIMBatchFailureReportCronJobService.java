/**
 *
 */
package com.siteone.core.cronjob.service;

import com.siteone.core.model.PIMBatchFailureReportCronJobModel;


/**
 * @author SR02012
 *
 */
public interface PIMBatchFailureReportCronJobService
{
	public void getFailedBatchForDay(final PIMBatchFailureReportCronJobModel model);
}
