/**
 *
 */
package com.siteone.core.store.dao;

import de.hybris.platform.commerceservices.model.storelocator.StoreLocatorFeatureModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Collection;
import java.util.List;


/**
 * @author 965504
 *
 */
public interface SiteOnePointOfServiceDao
{
	PointOfServiceModel getStoreForId(String storeId);

	List<PointOfServiceModel> getPointOfServiceForCityAndState(final String city, final String state);

	public List<String> getStoreCitiesForState(final String state);

	List<String> findStoreRegions();

	Collection<PointOfServiceModel> getAllActivePos();

	List<PointOfServiceModel> getPointOfServiceForState(final String state, final List<String> storeSpecialities);

	List<PointOfServiceModel> getStoreForMetroStatArea(final String metroStatLocation);

	/**
	 * @param storeIds
	 * @return
	 */
	List<PointOfServiceModel> getListOfPointOfService(final List<String> storeIds);
	
	List<StoreLocatorFeatureModel> getStoreSpecialtyDetails();

	Collection<PointOfServiceModel> getAllGeocodedPOSWithStoreSpecialities(GPS center, double radius, BaseStoreModel baseStore,
			List<String> storeSpecialities);
	
	PointOfServiceModel getStoreDetailForId(String storeId);
	
	List<PointOfServiceModel> getNurseryNearbyBranches(String nbGroup);
}
