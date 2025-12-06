/**
 *
 */
package com.siteone.core.jobs.cleanup.service;

import de.hybris.platform.impex.model.ImpExMediaModel;

import java.util.List;


/**
 * @author SMondal
 *
 */
public interface SiteoneCleanUpImpexMediaCronJobService
{
	List<ImpExMediaModel> getImpexMedia(final String deleteTillDate);
}
