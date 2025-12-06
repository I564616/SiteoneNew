package com.siteone.core.jobs.customer.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;

import java.util.List;


public interface FirstTimeUserCronJobDao
{

	public List<B2BCustomerModel> getFirstTimeUsers();
}
