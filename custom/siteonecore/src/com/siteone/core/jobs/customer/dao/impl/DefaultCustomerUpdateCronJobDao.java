/**
 *
 */
package com.siteone.core.jobs.customer.dao.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

import java.util.List;

import com.siteone.core.enums.UpdateEmailStatusEnum;
import com.siteone.core.jobs.customer.dao.CustomerUpdateCronJobDao;


/**
 * @author ASaha
 *
 */
public class DefaultCustomerUpdateCronJobDao extends AbstractItemDao implements CustomerUpdateCronJobDao
{
	@Override
	public List<B2BCustomerModel> getCustomerForEmailUpdate()
	{
		final String query = "SELECT {" + B2BCustomerModel.PK + "} " + "FROM {" + B2BCustomerModel._TYPECODE + "} "
				+ "WHERE {updateEmailFlag}=?updateEmailFlag AND {" + B2BCustomerModel.ACTIVE + "} = 1";

		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameter("updateEmailFlag", UpdateEmailStatusEnum.UPDATE_OKTA);

		return getFlexibleSearchService().<B2BCustomerModel> search(fQuery).getResult();
	}
}
