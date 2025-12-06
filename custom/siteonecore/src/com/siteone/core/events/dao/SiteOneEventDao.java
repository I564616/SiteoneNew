/**
 *
 */
package com.siteone.core.events.dao;

import java.util.List;

import com.siteone.core.model.SiteOneEventModel;


/**
 * @author 965504
 *
 */
public interface SiteOneEventDao
{
	public List<SiteOneEventModel> findEventByCode(final String code);

}
