/**
 *
 */
package com.siteone.fulfilmentprocess.actions.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;

import java.util.List;

import com.siteone.fulfilmentprocess.actions.dao.SiteOneCheckNearbyActionDao;


/**
 * @author BR06618
 *
 */
public class DefaultSiteOneCheckNearbyActionDao extends AbstractItemDao implements SiteOneCheckNearbyActionDao
{
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public List<WeekdayOpeningDayModel> getWeekdayOpeningDayModel(final String pk)
	{
		final String query = "SELECT {pk} From {WeekdayOpeningDay} where {OpeningSchedule} = ?pk";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameter("pk", pk);
		return getFlexibleSearchService().<WeekdayOpeningDayModel> search(fQuery).getResult();
	}


	@Override
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	@Override
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}
}
