/**
 *
 */
package com.siteone.core.location.service;

import de.hybris.platform.core.model.c2l.RegionModel;

import java.util.List;


/**
 * @author 1129929
 *
 */
public interface SiteOneRegionService
{
	List<RegionModel> getRegionsForCountryCode(final String countryCode);
}
