/**
 *
 */
package com.siteone.core.jobs.customer.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import com.siteone.core.jobs.customer.dao.CustomPushNotificationDao;
import com.siteone.core.model.CustomPushNotificationModel;


/**
 * @author LR03818
 *
 */
public class DefaultCustomPushNotificationDao extends AbstractItemDao implements CustomPushNotificationDao
{

	@Override
	public CustomPushNotificationModel getCustomPushNotification()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"Select {PK} from {CustomPushNotification} order by {modifiedTime} desc");
		query.setCount(1);
		final SearchResult<CustomPushNotificationModel> result = getFlexibleSearchService().search(query);
		return result.getCount() > 0 ? (CustomPushNotificationModel) result.getResult().iterator().next() : null;
	}

}
