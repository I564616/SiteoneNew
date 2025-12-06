/**
 *
 */
package com.siteone.core.jobs.customer.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;

import java.util.List;


/**
 * @author ASaha
 *
 */
public interface CustomerUpdateCronJobDao
{
	public List<B2BCustomerModel> getCustomerForEmailUpdate();
}
