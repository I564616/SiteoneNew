/**
 *
 */
package com.siteone.fulfilmentprocess.actions.facade;

import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.util.List;


/**
 * @author BR06618
 *
 */
public interface SiteOneCheckNearbyActionFacade
{
	List<WeekdayOpeningDayModel> getWeekdayOpeningDayModel(String pk);
}
