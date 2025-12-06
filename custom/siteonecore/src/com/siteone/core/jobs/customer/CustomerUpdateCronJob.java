/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import com.siteone.core.jobs.customer.service.CustomerUpdateCronJobService;
import com.siteone.core.model.CustomerUpdateCronJobModel;


/**
 * @author ASaha
 *
 */
public class CustomerUpdateCronJob extends AbstractJobPerformable<CustomerUpdateCronJobModel>
{
	CustomerUpdateCronJobService customerEmailUpdateCronJobService;

	@Override
	public PerformResult perform(final CustomerUpdateCronJobModel oktaCustomerEmailUpdateCronJobModel)
	{
		getCustomerEmailUpdateCronJobService().getCustomerForEmailUpdate(oktaCustomerEmailUpdateCronJobModel);

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the customerEmailUpdateCronJobService
	 */
	public CustomerUpdateCronJobService getCustomerEmailUpdateCronJobService()
	{
		return customerEmailUpdateCronJobService;
	}

	/**
	 * @param customerEmailUpdateCronJobService
	 *           the customerEmailUpdateCronJobService to set
	 */
	public void setCustomerEmailUpdateCronJobService(
			final CustomerUpdateCronJobService customerEmailUpdateCronJobService)
	{
		this.customerEmailUpdateCronJobService = customerEmailUpdateCronJobService;
	}



}
