/**
 *
 */
package com.siteone.facades.neareststore;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * @author 965504
 *
 */
public interface SiteOneStoreFinderFacade
{


	/**
	 * @param geoPoint
	 * @return
	 */
	PointOfServiceData nearestStorePositionSearch(GeoPoint geoPoint);

	PointOfServiceData getStoreForId(String storeId);

	public List<PointOfServiceData> getPointOfServiceForCityAndState(final String city, final String state);

	public Map<String, TreeMap<String, Integer>> getStoreCitiesForState(final String state);

	Map<RegionData, Boolean> getStoreRegions(String countryIsoCode);

	StoreFinderSearchPageData<PointOfServiceData> positionSearch(GeoPoint geoPoint, PageableData pageableData,
			double locationQueryMiles);


	/**
	 * @param stateIsoCode
	 * @return
	 */
	String getStateNameForIsoCode(String stateIsoCode);

	Set<String> getListofStatesFromSpecialty(final String Specialty);

	TreeMap<String, List<PointOfServiceData>> getListofStoresFromSpecialty(final String state, final String specialty);

	/**
	 * @param storeId
	 * @return
	 */
	PointOfServiceData getHubStoreBranch(String storeId);
	
	List<PointOfServiceData> getStoresForZipcode(final String zipcode,
			final double maxRadiusKm);
	
	StoreFinderSearchPageData<PointOfServiceData> locationSearchWithStoreSpecialities(String locationText, PageableData pageableData, double maxRadius,
			List<String> storespecilaities);

	StoreFinderSearchPageData<PointOfServiceData> positionSearchWithStoreSpecialities(GeoPoint geoPoint, PageableData pageableData,
			double locationQueryMiles, List<String> storeSpecialities);

}
