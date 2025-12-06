/**
 *
 */
package com.siteone.core.store.services;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Collection;
import java.util.List;


/**
 * @author 965504
 *
 */
public interface SiteOneStoreFinderService<ITEM extends PointOfServiceDistanceData, RESULT extends StoreFinderSearchPageData<ITEM>>
{

	/**
	 * @param baseStore
	 * @param centerPoint
	 * @return
	 */
	PointOfServiceModel doSearch(BaseStoreModel baseStore, GeoPoint centerPoint);

	PointOfServiceModel getStoreForId(String storeId);

	List<PointOfServiceModel> getPointOfServiceForCityAndState(final String city, String state);

	String getTimeZoneResponse(final Double latitude, final Double longitude, final Double timestamp) throws Exception;



	/**
	 * @param state
	 * @return
	 */
	public List<String> getStoreCitiesForState(String state);

	List<String> getStoreRegions();

	List<RegionModel> getRegionsByCountry(String countryCode);

	List<RegionModel> getRegionsByCountryAndTerritory(String countryCode);

	/**
	 * @param baseStore
	 * @param geoPoint
	 * @param pageableData
	 * @param locationQueryMiles
	 * @return
	 */
	StoreFinderSearchPageData<ITEM> positionSearch(BaseStoreModel baseStore, GeoPoint geoPoint, PageableData pageableData,
			double locationQueryMiles);

	/**
	 * @param usIsoCode
	 * @param stateIsoCode
	 * @return
	 */
	String getStateNameForIsoCode(String usIsoCode, String stateIsoCode);

	/**
	 * @param metroStatArea
	 * @return
	 */
	List<PointOfServiceModel> getStoreForMetroStatArea(String metroStatArea);

	Collection<PointOfServiceModel> getStores();

	/**
	 * @param storeIds
	 * @return
	 */
	List<PointOfServiceModel> getListofStores(List<String> storeIds);
	
	List<PointOfServiceModel> getNurseryNearbyBranches(String nbGroup);
	
	List<PointOfServiceDistanceData> getStoresForZipcode(final BaseStoreModel baseStore, final String zipcode,
			final Double maxRadiusKm);
	
	StoreFinderSearchPageData<ITEM> locationSearchWithStoreSpecialities(BaseStoreModel baseStore, String locationText,PageableData pageableData, double maxRadiusKm, List<String> storeSpecialities);

	StoreFinderSearchPageData<ITEM> positionSearchWithStoreSpecialities(BaseStoreModel baseStore, GeoPoint geoPoint, PageableData pageableData,
			double locationQueryMiles, List<String> storeSpecialities);
	
	PointOfServiceModel getStoreDetailForId(String storeId);
}
