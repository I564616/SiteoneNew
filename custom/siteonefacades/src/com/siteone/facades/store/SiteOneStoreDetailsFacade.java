/**
 *
 */
package com.siteone.facades.store;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;

import java.util.List;


/**
 * @author 532681
 *
 */
public interface SiteOneStoreDetailsFacade
{
	PointOfServiceData fetchSiteOnePointOfService(final String storeCode);

	List<PointOfServiceData> fetchListofPointOfService(final List<String> storeCodes);

	/**
	 * @return
	 */
	PointOfServiceData searchStoreDetailForPopup(final String storeId, final String productCode);
	
    boolean getIsNurseryCenter(PointOfServiceData pointOfServiceData);
}