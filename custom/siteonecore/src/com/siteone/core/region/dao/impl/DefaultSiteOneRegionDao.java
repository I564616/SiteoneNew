/**
 *
 */
package com.siteone.core.region.dao.impl;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.i18n.daos.impl.DefaultRegionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.siteone.core.region.dao.SiteoneRegionDao;


/**
 * @author 1124932
 *
 */
public class DefaultSiteOneRegionDao extends DefaultRegionDao implements SiteoneRegionDao
{

	@Override
	public List<RegionModel> findRegionsByCountryAndTerritory(final CountryModel country)
	{
		final Map params = new HashMap();
		params.put("country", country);
		params.put("isTerritory", Boolean.FALSE);
		return this.find(params);

	}



}
