/**
 *
 */
package com.siteone.core.events.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.events.dao.SiteOneEventDao;
import com.siteone.core.events.service.SiteOneEventService;
import com.siteone.core.model.SiteOneEventModel;


/**
 * @author 965504
 *
 */
public class DefaultSiteOneEventService implements SiteOneEventService
{

	@Resource(name = "siteOneEventDao")
	private SiteOneEventDao siteOneEventDao;

	@Override
	public SiteOneEventModel getEventByCode(final String eventCode)
	{

		final List<SiteOneEventModel> events = siteOneEventDao.findEventByCode(eventCode);
		if (events != null && !events.isEmpty())
		{
			return events.get(0);
		}
		else
		{
			return null;
		}
	}

}
