/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.model.RegulatoryStatesCronJobModel;
import com.siteone.core.services.RegulatoryStatesCronJobService;


/**
 * @author 1124932
 *
 */
public class RegulatoryStatesCronJob extends AbstractJobPerformable<RegulatoryStatesCronJobModel>
{
	private static final Logger LOG = Logger.getLogger(RegulatoryStatesCronJob.class);
	private RegulatoryStatesCronJobService regulatoryStatesCronJobService;

	@Override
	public PerformResult perform(final RegulatoryStatesCronJobModel model)
	{
		try
		{
			getRegulatoryStatesCronJobService().importProductRegulatoryStates(model);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in Regulatory States CronJob ", exception);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the regulatoryStatesCronJobService
	 */
	public RegulatoryStatesCronJobService getRegulatoryStatesCronJobService()
	{
		return regulatoryStatesCronJobService;
	}

	/**
	 * @param regulatoryStatesCronJobService
	 *           the regulatoryStatesCronJobService to set
	 */
	public void setRegulatoryStatesCronJobService(final RegulatoryStatesCronJobService regulatoryStatesCronJobService)
	{
		this.regulatoryStatesCronJobService = regulatoryStatesCronJobService;
	}

}

