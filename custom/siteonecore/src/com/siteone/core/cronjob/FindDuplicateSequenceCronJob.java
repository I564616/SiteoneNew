/**
 *
 */
package com.siteone.core.cronjob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.cronjob.service.FindDuplicateSequenceCronJobService;
import com.siteone.core.model.FindDuplicateSequenceCronJobModel;


/**
 * @author LR03818
 *
 */
public class FindDuplicateSequenceCronJob extends AbstractJobPerformable<FindDuplicateSequenceCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(FindDuplicateSequenceCronJob.class);
	private FindDuplicateSequenceCronJobService findDuplicateSequenceCronJobService;

	@Override
	public PerformResult perform(final FindDuplicateSequenceCronJobModel findDuplicateSequenceCronJobModel)
	{
		try
		{
			findDuplicateSequenceCronJobService.findAndFixDuplicateSequence(findDuplicateSequenceCronJobModel);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in Find Duplicate Sequence cron job ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the findDuplicateSequenceCronJobService
	 */
	public FindDuplicateSequenceCronJobService getFindDuplicateSequenceCronJobService()
	{
		return findDuplicateSequenceCronJobService;
	}

	/**
	 * @param findDuplicateSequenceCronJobService
	 *           the findDuplicateSequenceCronJobService to set
	 */
	public void setFindDuplicateSequenceCronJobService(
			final FindDuplicateSequenceCronJobService findDuplicateSequenceCronJobService)
	{
		this.findDuplicateSequenceCronJobService = findDuplicateSequenceCronJobService;
	}

}
