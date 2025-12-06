/**
 *
 */
package com.siteone.core.location.service.impl;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.i18n.daos.CountryDao;
import de.hybris.platform.servicelayer.i18n.daos.RegionDao;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.siteone.core.location.service.SiteOneRegionService;


/**
 * @author 1129929
 *
 */
public class DefaultSiteOneRegionService implements SiteOneRegionService
{

	private static final Logger LOG = Logger.getLogger(DefaultSiteOneRegionService.class);

	private RegionDao regionDao;
	private CountryDao countryDao;



	@Override
	public List<RegionModel> getRegionsForCountryCode(final String countryCode)
	{
		final List<CountryModel> countries = countryDao.findCountriesByCode(countryCode);

		if (countries.isEmpty())
		{
			return Collections.emptyList();
		}
		else
		{
			final CountryModel countryModel = countries.get(0);
			final List<RegionModel> regions = this.regionDao.findRegionsByCountry(countryModel);

			if (regions == null || regions.size() == 0)
			{
				LOG.info("Returning null. No regions found for countrycode=" + countryCode);
				return null;
			}
			else
			{
				return regions;
			}
		}
	}

	public void setRegionDao(final RegionDao regionDao)
	{
		this.regionDao = regionDao;
	}

	public void setCountryDao(final CountryDao countryDao)
	{
		this.countryDao = countryDao;
	}
}
