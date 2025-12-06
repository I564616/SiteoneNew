/**
 *
 */
package com.siteone.core.adapter.impl;

import de.hybris.platform.storelocator.model.PointOfServiceModel;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.adapter.PointOfServiceAdaptor;
import com.siteone.core.store.dao.SiteOneStoreUtilityDao;


/**
 * @author 1099417
 *
 */
public class DefaultPointOfServiceAdaptor implements PointOfServiceAdaptor
{
	private static final Logger LOGGER = Logger.getLogger(DefaultPointOfServiceAdaptor.class);

	@Resource(name = "siteOneStoreUtilityDao")
	private SiteOneStoreUtilityDao siteOneStoreUtilityDao;

	@Override
	public PointOfServiceModel getStoreDetailByPK(final String storePK)
	{
		PointOfServiceModel pos = null;

		try
		{
			pos = getSiteOneStoreUtilityDao().getStoreForPK(storePK);
		}
		catch (final Exception exception)
		{
			LOGGER.error(exception);
		}

		return pos;
	}

	/**
	 * @return the siteOneStoreUtilityDao
	 */
	public SiteOneStoreUtilityDao getSiteOneStoreUtilityDao()
	{
		return siteOneStoreUtilityDao;
	}

	/**
	 * @param siteOneStoreUtilityDao
	 *           the siteOneStoreUtilityDao to set
	 */
	public void setSiteOneStoreUtilityDao(final SiteOneStoreUtilityDao siteOneStoreUtilityDao)
	{
		this.siteOneStoreUtilityDao = siteOneStoreUtilityDao;
	}



}
