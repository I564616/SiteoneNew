/**
 *
 */
package com.siteone.core.adapter.impl;

import com.siteone.core.adapter.AreaImportAdapter;
import com.siteone.core.store.dao.SiteOneStoreUtilityDao;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;


/**
 * @author 1099417
 *
 */
public class DefaultAreaImportAdapter implements AreaImportAdapter
{
	private static final Logger LOGGER = Logger.getLogger(DefaultAreaImportAdapter.class);

	@Resource(name = "siteOneStoreUtilityDao")
	private SiteOneStoreUtilityDao siteOneStoreUtilityDao;

	@Override
	public String getArea(final String parentId)
	{
		String area = "";
		PointOfServiceModel areaPosModel = null;

		try
		{
			areaPosModel = siteOneStoreUtilityDao.getActiveNonActiveStoreForSupplyChainNode(parentId);
		}
		catch (final Exception exception)
		{
			LOGGER.error(exception);
		}

		if (null != areaPosModel && "area".equalsIgnoreCase(areaPosModel.getStoreType()))
		{
			area = areaPosModel.getStoreId();
		}

		return area;
	}

}
