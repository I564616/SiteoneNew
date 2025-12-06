package com.siteone.integration.jobs.datareport.dao.impl;

import java.util.List;

import com.siteone.integration.jobs.datareport.dao.SiteOneCustomerDataReportDao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;

public class DefaultSiteOneCustomerDataReportDao extends AbstractItemDao implements SiteOneCustomerDataReportDao {

	@Override
	public List<B2BCustomerModel> getAllB2BCustomers() {
		String query = "SELECT {" + Item.PK + "}  FROM   {" + "B2BCustomer" + "} WHERE {B2BCustomer:uid} NOT LIKE '%storecontact%' ";
		return getFlexibleSearchService().<B2BCustomerModel>search(query).getResult();
	}
}
