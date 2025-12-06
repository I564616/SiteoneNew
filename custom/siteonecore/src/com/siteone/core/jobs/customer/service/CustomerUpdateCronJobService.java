/**
 *
 */
package com.siteone.core.jobs.customer.service;

import com.siteone.core.model.CustomerUpdateCronJobModel;


/**
 * @author ASaha
 *
 */
public interface CustomerUpdateCronJobService
{
	public void getCustomerForEmailUpdate(CustomerUpdateCronJobModel oktaCustomerEmailUpdateCronJobModel);
}
