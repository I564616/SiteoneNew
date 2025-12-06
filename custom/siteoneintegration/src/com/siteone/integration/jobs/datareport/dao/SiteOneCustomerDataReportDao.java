package com.siteone.integration.jobs.datareport.dao;

import java.util.List;

import de.hybris.platform.b2b.model.B2BCustomerModel;

public interface SiteOneCustomerDataReportDao {
	
	List<B2BCustomerModel> getAllB2BCustomers();
}
