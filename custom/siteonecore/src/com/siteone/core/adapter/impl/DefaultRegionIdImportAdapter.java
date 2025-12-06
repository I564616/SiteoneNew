/**
 *
 */
package com.siteone.core.adapter.impl;

import com.siteone.core.adapter.RegionIdImportAdapter;
import com.siteone.core.store.dao.SiteOneStoreUtilityDao;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;


/**
 * @author 1099417
 *
 */
public class DefaultRegionIdImportAdapter implements RegionIdImportAdapter
{
	private static final Logger LOGGER = Logger.getLogger(DefaultRegionIdImportAdapter.class);

	@Resource(name = "siteOneStoreUtilityDao")
	private SiteOneStoreUtilityDao siteOneStoreUtilityDao;

	@Override
	public String getRegionId(final String parentId)
	{
		String regiondId = "";
		PointOfServiceModel pointOfServiceModel = null;

		try
		{
			pointOfServiceModel = siteOneStoreUtilityDao.getActiveNonActiveStoreForSupplyChainNode(parentId);
		}
		catch (final Exception exception)
		{
			LOGGER.error(exception);
		}

		if (null != pointOfServiceModel && "region".equalsIgnoreCase(pointOfServiceModel.getStoreType()))
		{
			regiondId = pointOfServiceModel.getStoreId();
		}
		else if (null != pointOfServiceModel && "area".equalsIgnoreCase(pointOfServiceModel.getStoreType()))
		{
			String nextParentId = pointOfServiceModel.getParentId();

			try
			{
				pointOfServiceModel = siteOneStoreUtilityDao.getActiveNonActiveStoreForSupplyChainNode(nextParentId);
			}
			catch (final Exception exception)
			{
				LOGGER.error(exception);
			}

			if (null != pointOfServiceModel && "region".equalsIgnoreCase(pointOfServiceModel.getStoreType()))
			{
				regiondId = pointOfServiceModel.getStoreId();
			}
		}

		return regiondId;
	}

}
