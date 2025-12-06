/**
 *
 */
package com.siteone.facade.location;

import de.hybris.platform.commercefacades.user.data.RegionData;

import java.util.List;


/**
 * @author 1129929
 *
 */
public interface SiteOneRegionFacade
{

	List<RegionData> getRegionsForCountryCode(final String countryCode);

}
