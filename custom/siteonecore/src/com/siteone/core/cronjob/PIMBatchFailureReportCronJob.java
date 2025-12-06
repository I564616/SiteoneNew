/**
 *
 */
package com.siteone.core.cronjob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import com.siteone.core.cronjob.service.PIMBatchFailureReportCronJobService;
import com.siteone.core.model.PIMBatchFailureReportCronJobModel;


/**
 * @author SR02012
 *
 */
public class PIMBatchFailureReportCronJob extends AbstractJobPerformable<PIMBatchFailureReportCronJobModel>
{
	private PIMBatchFailureReportCronJobService pimBatchFailureReportCronjobService;

	@Override
	public PerformResult perform(final PIMBatchFailureReportCronJobModel model)
	{
		getPimBatchFailureReportCronjobService().getFailedBatchForDay(model);
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	public PIMBatchFailureReportCronJobService getPimBatchFailureReportCronjobService()
	{
		return pimBatchFailureReportCronjobService;
	}

	public void setPimBatchFailureReportCronjobService(
			final PIMBatchFailureReportCronJobService pimBatchFailureReportCronjobService)
	{
		this.pimBatchFailureReportCronjobService = pimBatchFailureReportCronjobService;
	}
}
