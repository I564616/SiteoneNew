/**
 *
 */
package com.siteone.core.jobs.cleanup.dao;

import de.hybris.platform.impex.model.ImpExMediaModel;

import java.util.List;


/**
 * @author SMondal
 *
 */
public interface SiteoneCleanUpImpexMediaCronJobDao
{
	List<ImpExMediaModel> getImpexMedia(final String deleteTillDate);
}
