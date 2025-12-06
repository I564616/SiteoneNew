/**
 *
 */
package com.siteone.core.events.service;

import com.siteone.core.model.SiteOneEventModel;


/**
 * @author 965504
 *
 */
public interface SiteOneEventService
{

	public SiteOneEventModel getEventByCode(final String eventCode);
}
