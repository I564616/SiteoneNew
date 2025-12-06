/**
 *
 */
package com.siteone.core.jobs.cleanup.service.impl;

import de.hybris.platform.impex.model.ImpExMediaModel;

import java.util.List;

import com.siteone.core.jobs.cleanup.dao.SiteoneCleanUpImpexMediaCronJobDao;
import com.siteone.core.jobs.cleanup.service.SiteoneCleanUpImpexMediaCronJobService;


/**
 * @author SMondal
 *
 */
public class DefaultSiteoneCleanUpImpexMediaCronJobService implements SiteoneCleanUpImpexMediaCronJobService
{
	private SiteoneCleanUpImpexMediaCronJobDao siteoneCleanUpImpexMediaCronJobDao;

	@Override
	public List<ImpExMediaModel> getImpexMedia(final String deleteTillDate)
	{
		return getSiteoneCleanUpImpexMediaCronJobDao().getImpexMedia(deleteTillDate);
	}

	/**
	 * @return the siteoneCleanUpImpexMediaCronJobDao
	 */
	public SiteoneCleanUpImpexMediaCronJobDao getSiteoneCleanUpImpexMediaCronJobDao()
	{
		return siteoneCleanUpImpexMediaCronJobDao;
	}

	/**
	 * @param siteoneCleanUpImpexMediaCronJobDao
	 *           the siteoneCleanUpImpexMediaCronJobDao to set
	 */
	public void setSiteoneCleanUpImpexMediaCronJobDao(final SiteoneCleanUpImpexMediaCronJobDao siteoneCleanUpImpexMediaCronJobDao)
	{
		this.siteoneCleanUpImpexMediaCronJobDao = siteoneCleanUpImpexMediaCronJobDao;
	}



}
