package com.siteone.core.jobs.customer.dao.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;

import java.util.List;

import com.siteone.core.jobs.customer.dao.FirstTimeUserCronJobDao;


public class DefaultFirstTimeUserCronJobDao extends AbstractItemDao implements FirstTimeUserCronJobDao
{

	static final String QUERY = " SELECT {c:" + B2BCustomerModel.PK + "} " + "FROM {" + B2BCustomerModel._TYPECODE + " AS c "
			+ "JOIN " + B2BUnitModel._TYPECODE + " AS u ON {c:" + B2BCustomerModel.DEFAULTB2BUNIT + "} = {u:" + B2BUnitModel.PK
			+ "}} " + " WHERE {c:" + B2BCustomerModel.ISFIRSTTIMEUSER + "} = 1 AND {c:" + B2BCustomerModel.ACTIVE + "} = 1"
			+ " AND {u:" + B2BUnitModel.ACTIVE + "} = 1" + " AND ({u:" + B2BUnitModel.ISPUNCHOUTACCOUNT + "} = 0 OR {u:"
			+ B2BUnitModel.ISPUNCHOUTACCOUNT + "} is null)";

	@Override
	public List<B2BCustomerModel> getFirstTimeUsers()
	{
		return getFlexibleSearchService().<B2BCustomerModel> search(QUERY).getResult();
	}

}
