/**
 *
 */
package com.siteone.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.siteone.core.facade.PimPayloadsDeleteFacade;
import com.siteone.core.model.cronjob.PimPayloadsDeleteCronJobModel;


/**
 * @author SM04392
 *
 */
public class PimPayloadsDeleteJob extends AbstractJobPerformable<PimPayloadsDeleteCronJobModel>
{

	private static final Logger LOG = LoggerFactory.getLogger(PimPayloadsDeleteJob.class);
	private PimPayloadsDeleteFacade pimPayloadsDeleteFacade;

	@Override
	public PerformResult perform(final PimPayloadsDeleteCronJobModel arg0)
	{
		// YTODO Auto-generated method stub
		try
		{
			pimPayloadsDeleteFacade.deletePayloadsOlderThanTwoWeeks();
		}
		catch (final ParseException e)
		{
			// YTODO Auto-generated catch block
			LOG.info("context", e);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);

	}

	public PimPayloadsDeleteFacade getPimPayloadsDeleteFacade()
	{
		return pimPayloadsDeleteFacade;
	}

	public void setPimPayloadsDeleteFacade(final PimPayloadsDeleteFacade pimPayloadsDeleteFacade)
	{
		this.pimPayloadsDeleteFacade = pimPayloadsDeleteFacade;
	}


}