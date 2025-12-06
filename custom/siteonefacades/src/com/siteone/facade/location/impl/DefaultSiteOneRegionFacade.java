/**
 *
 */
package com.siteone.facade.location.impl;

import de.hybris.platform.commercefacades.user.converters.populator.RegionPopulator;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.core.model.c2l.RegionModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.siteone.core.location.service.SiteOneRegionService;
import com.siteone.facade.location.SiteOneRegionFacade;


/**
 * @author 1129929
 *
 */
public class DefaultSiteOneRegionFacade implements SiteOneRegionFacade
{

	private SiteOneRegionService siteOneRegionService;

	private RegionPopulator regionPopulator;


	@Override
	public List<RegionData> getRegionsForCountryCode(final String countryCode)
	{


		final List<RegionModel> regionModels = siteOneRegionService.getRegionsForCountryCode(countryCode);

		if (regionModels.isEmpty())
		{
			return Collections.emptyList();
		}
		else
		{
			final List<RegionData> regionFacadeData = new ArrayList<RegionData>();
			for (final RegionModel regionmodel : regionModels)
			{
				final RegionData regionData = new RegionData();
				this.regionPopulator.populate(regionmodel, regionData);

				regionFacadeData.add(regionData);
			}
			return regionFacadeData;
		}

	}



	/**
	 * @param siteOneRegionService
	 *           the siteOneRegionService to set
	 */
	public void setSiteOneRegionService(final SiteOneRegionService siteOneRegionService)
	{
		this.siteOneRegionService = siteOneRegionService;
	}

	/**
	 * @param regionPopulator
	 *           the regionPopulator to set
	 */
	public void setRegionPopulator(final RegionPopulator regionPopulator)
	{
		this.regionPopulator = regionPopulator;
	}


}
