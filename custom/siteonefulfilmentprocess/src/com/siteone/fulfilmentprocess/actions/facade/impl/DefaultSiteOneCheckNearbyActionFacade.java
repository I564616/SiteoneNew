/**
 *
 */
package com.siteone.fulfilmentprocess.actions.facade.impl;

import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.util.List;

import com.siteone.fulfilmentprocess.actions.dao.SiteOneCheckNearbyActionDao;
import com.siteone.fulfilmentprocess.actions.facade.SiteOneCheckNearbyActionFacade;


/**
 * @author BR06618
 *
 */
public class DefaultSiteOneCheckNearbyActionFacade implements SiteOneCheckNearbyActionFacade
{
	private SiteOneCheckNearbyActionDao siteOneCheckNearbyActionDao;


	public SiteOneCheckNearbyActionDao getSiteOneCheckNearbyActionDao()
	{
		return siteOneCheckNearbyActionDao;
	}

	public void setSiteOneCheckNearbyActionDao(final SiteOneCheckNearbyActionDao siteOneCheckNearbyActionDao)
	{
		this.siteOneCheckNearbyActionDao = siteOneCheckNearbyActionDao;
	}

	@Override
	public List<WeekdayOpeningDayModel> getWeekdayOpeningDayModel(final String pk)
	{
		return siteOneCheckNearbyActionDao.getWeekdayOpeningDayModel(pk);
	}
}
