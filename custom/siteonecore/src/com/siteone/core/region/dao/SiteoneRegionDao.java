/**
 *
 */
package com.siteone.core.region.dao;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;

import java.util.List;


/**
 * @author 1124932
 *
 */
public interface SiteoneRegionDao
{
	List<RegionModel> findRegionsByCountryAndTerritory(CountryModel arg0);
}
