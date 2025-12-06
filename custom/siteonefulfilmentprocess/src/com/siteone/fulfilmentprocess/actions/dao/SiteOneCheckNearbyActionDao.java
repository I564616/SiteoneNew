/**
 *
 */
package com.siteone.fulfilmentprocess.actions.dao;

import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.util.List;


/**
 * @author BR06618
 *
 */
public interface SiteOneCheckNearbyActionDao
{
	List<WeekdayOpeningDayModel> getWeekdayOpeningDayModel(String pk);
}
