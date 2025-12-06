/**
 *
 */
package com.siteone.facades.events;

import com.siteone.facade.EventData;

/**
 * @author 965504
 *
 */
public interface SiteOneEventFacade
{

	public EventData getEventByCode(final String eventCode);

}
