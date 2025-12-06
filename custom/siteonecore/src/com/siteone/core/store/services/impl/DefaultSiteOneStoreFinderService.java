/**
 *
 */
package com.siteone.core.store.services.impl;

import de.hybris.platform.basecommerce.enums.PointOfServiceTypeEnum;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commerceservices.storefinder.impl.DefaultStoreFinderService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.daos.CountryDao;
import de.hybris.platform.servicelayer.i18n.daos.RegionDao;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.exception.GeoServiceWrapperException;
import de.hybris.platform.storelocator.exception.PointOfServiceDaoException;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.siteone.core.region.dao.SiteoneRegionDao;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;
import com.siteone.core.store.services.SiteOneStoreFinderService;


/**
 * @author 965504
 *
 */
public class DefaultSiteOneStoreFinderService<ITEM extends PointOfServiceDistanceData> extends DefaultStoreFinderService
		implements SiteOneStoreFinderService<ITEM, StoreFinderSearchPageData<ITEM>>
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneStoreFinderService.class);
	public static final double IMPERIAL_DISTANCE_RATIO = 0.62137;
	protected static final Pattern LATITUDE_PATTERN = Pattern
			.compile("^-?([0-8]?[0-9]|90)(\\.[0-9]{1,16})?$");
	protected static final Pattern LONGITUDE_PATTERN = Pattern
			.compile("^-?([0-9]{1,2}|1[0-7][0-9]|180)(\\.[0-9]{1,16})?$");
	private static final String GEOCODE_EXCEPTION = "Failed to resolve location for [";
	private static final String BRACKET = "]";

	private RegionDao regionDao;
	private CountryDao countryDao;
	private SiteoneRegionDao siteOneRegionDao;
	private RestTemplate restTemplate;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	private static final String GOOGLE_API_TIMEZONE = "googleApiKeyTimeZone";

	@Override
	public PointOfServiceModel doSearch(final BaseStoreModel baseStore, final GeoPoint centerPoint)
	{
		final Collection<PointOfServiceModel> posResults;
		PointOfServiceModel nearByStore = null;

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("baseStore", baseStore);
		paramMap.put("type", PointOfServiceTypeEnum.STORE);
		paramMap.put("isActive", Boolean.TRUE);
		paramMap.put("isDCBranch", Boolean.FALSE);
		posResults = getPointOfServiceGenericDao().find(paramMap);

		if (posResults != null)
		{
			final List<ITEM> posDistanceData = calculateDistances(centerPoint, posResults);

			if (CollectionUtils.isNotEmpty(posDistanceData))
			{
				for (final ITEM item : posDistanceData)
				{
					if (item.getPointOfService().getAcquisitionOrCoBrandedBranch() == null
							|| !item.getPointOfService().getAcquisitionOrCoBrandedBranch())
					{
						nearByStore = item.getPointOfService();
						break;
					}
				}
			}
		}

		return nearByStore;

	}

	@Override
	protected StoreFinderSearchPageData<ITEM> doSearch(final BaseStoreModel baseStore, final String locationText,
			final GeoPoint centerPoint, final PageableData pageableData, final Double maxRadiusKm)
	{
		return doSearch(baseStore, locationText, centerPoint, pageableData, maxRadiusKm, null);
	}

	@Override
	public PointOfServiceModel getStoreForId(final String storeId)
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getStoreForId(storeId);
	}
	
	@Override
	public PointOfServiceModel getStoreDetailForId(final String storeId)
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getStoreDetailForId(storeId);
	}

	@Override
	public List<PointOfServiceModel> getListofStores(final List<String> storeIds)
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getListOfPointOfService(storeIds);
	}
	
	@Override
	public List<PointOfServiceModel> getNurseryNearbyBranches(String nbGroup)
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getNurseryNearbyBranches(nbGroup);
	}

	@Override
	public List<PointOfServiceModel> getStoreForMetroStatArea(final String metroStatArea)
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getStoreForMetroStatArea(metroStatArea);
	}

	@Override
	public StoreFinderSearchPageData<ITEM> locationSearch(final BaseStoreModel baseStore, final String locationText,
			final PageableData pageableData)
	{
		return locationSearch(baseStore, locationText, pageableData, 0.0, null);
	}


	@Override
	public StoreFinderSearchPageData<ITEM> locationSearch(final BaseStoreModel baseStore, final String locationText,
			final PageableData pageableData, final double maxRadiusKm)
	{
		return locationSearch(baseStore, locationText, pageableData, maxRadiusKm, null);
	}

	/**
	 * @param locationText
	 * @param geoPoint
	 * @param results
	 * @param paginationData
	 * @return StoreFinderSearchPageData<ITEM>
	 */
	private StoreFinderSearchPageData<ITEM> createSiteOneSearchResult(final String locationText, final GeoPoint geoPoint,
			final List<ITEM> results, final PaginationData paginationData)
	{
		final StoreFinderSearchPageData<ITEM> searchPageData = createStoreFinderSearchPageData();
		if (CollectionUtils.isNotEmpty(results))
		{
			final PointOfServiceModel pointOfService = results.get(0).getPointOfService();
			final Double posLatitude = pointOfService.getLatitude();
			final Double posLongitude = pointOfService.getLongitude();
			searchPageData.setResults(results);
			searchPageData.setPagination(paginationData);

			searchPageData.setLocationText(locationText);
			searchPageData.setSourceLatitude(geoPoint.getLatitude());
			searchPageData.setSourceLongitude(geoPoint.getLongitude());


			searchPageData.setBoundNorthLatitude(posLatitude);
			searchPageData.setBoundEastLongitude(posLongitude);
			searchPageData.setBoundSouthLatitude(posLatitude);
			searchPageData.setBoundWestLongitude(posLongitude);
		}
		return searchPageData;

	}

	@Override
	public List<PointOfServiceModel> getPointOfServiceForCityAndState(final String city, final String state)
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getPointOfServiceForCityAndState(city, state);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.store.services.SiteOneStoreFinderService#getStoreCitiesForState(java.lang.String)
	 */
	@Override
	public List<String> getStoreCitiesForState(final String state)
	{
		// YTODO Auto-generated method stub
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getStoreCitiesForState(state);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.chinaacceleratorservices.core.service.RegionService#getRegionsForCountryCode(java.lang.String)
	 */
	@Override
	public List<String> getStoreRegions()
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).findStoreRegions();
	}

	@Override
	public List<RegionModel> getRegionsByCountry(final String countryCode)
	{

		final List<CountryModel> countries = countryDao.findCountriesByCode(countryCode);
		if (countries.isEmpty())
		{
			return null;
		}
		final CountryModel countryModel = countries.get(0);

		return regionDao.findRegionsByCountry(countryModel);

	}

	@Override
	public List<RegionModel> getRegionsByCountryAndTerritory(final String countryCode)
	{

		final List<CountryModel> countries = countryDao.findCountriesByCode(countryCode);
		if (countries.isEmpty())
		{
			return null;
		}
		final CountryModel countryModel = countries.get(0);

		return siteOneRegionDao.findRegionsByCountryAndTerritory(countryModel);

	}




	/**
	 * @return the regionDao
	 */
	public RegionDao getRegionDao()
	{
		return regionDao;
	}

	/**
	 * @param regionDao
	 *           the regionDao to set
	 */
	public void setRegionDao(final RegionDao regionDao)
	{
		this.regionDao = regionDao;
	}

	/**
	 * @return the countryDao
	 */
	public CountryDao getCountryDao()
	{
		return countryDao;
	}

	/**
	 * @param countryDao
	 *           the countryDao to set
	 */
	public void setCountryDao(final CountryDao countryDao)
	{
		this.countryDao = countryDao;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.siteone.core.store.services.SiteOneStoreFinderService#positionSearch(de.hybris.platform.store.BaseStoreModel,
	 * de.hybris.platform.commerceservices.store.data.GeoPoint,
	 * de.hybris.platform.commerceservices.search.pagedata.PageableData, java.lang.Double)
	 */
	@Override
	public StoreFinderSearchPageData<ITEM> positionSearch(final BaseStoreModel baseStore, final GeoPoint geoPoint,
			final PageableData pageableData, final double locationQueryMiles)
	{
		return doSearch(baseStore, null, geoPoint, pageableData, Double.valueOf(locationQueryMiles));
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.core.store.services.SiteOneStoreFinderService#getStateNameForIsoCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String getStateNameForIsoCode(final String usIsoCode, final String stateIsoCode)
	{
		final List<CountryModel> countries = countryDao.findCountriesByCode(usIsoCode);
		if (countries.isEmpty())
		{
			return null;
		}
		final CountryModel countryModel = countries.get(0);
		String stateName = "";
		final List<RegionModel> region = regionDao.findRegionsByCountryAndCode(countryModel, stateIsoCode);
		if (null != region && !region.isEmpty())
		{
			stateName = region.get(0).getName();
		}
		return stateName;
	}

	public List<PointOfServiceDistanceData> calculateDistancesForMiles(final GeoPoint centerPoint,
			final Collection<PointOfServiceModel> pointsOfService, final double radiusKm)
	{
		final List<PointOfServiceDistanceData> result = new ArrayList<>();

		for (final PointOfServiceModel pointOfService : pointsOfService)
		{
			final PointOfServiceDistanceData storeFinderResultData = createStoreFinderResultData();
			storeFinderResultData.setPointOfService(pointOfService);
			final double distanceInKm = calculateDistance(centerPoint, pointOfService);
			final double distanceInMiles = distanceInKm * IMPERIAL_DISTANCE_RATIO;
			storeFinderResultData.setDistanceKm(distanceInKm);
			if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
				if (distanceInMiles <= radiusKm)
				{
					result.add(storeFinderResultData);
				}
			}
			else {
				if (distanceInKm <= radiusKm)
				{
					result.add(storeFinderResultData);
				}
			}
		}

		Collections.sort(result, StoreFinderResultDataComparator.INSTANCE);

		return result;
	}


	@Override
	public String getTimeZoneResponse(final Double latitude, final Double longitude, final Double timestamp) throws Exception
	{
		final String key = configurationService.getConfiguration().getString(GOOGLE_API_TIMEZONE);
		final Matcher latmatcher = LATITUDE_PATTERN.matcher(latitude.toString());
		final Matcher longmatcher = LONGITUDE_PATTERN.matcher(longitude.toString());
		if (!(latmatcher.matches() && longmatcher.matches()))

		{
			throw new IllegalArgumentException("Invalid Latitude and Longitude");
		}
		final String url = "https://maps.googleapis.com/maps/api/timezone/json?location=" + latitude + "," + longitude
				+ "&timestamp=" + timestamp + "&key=" + key;
		final String response = getRestTemplate().getForObject(url, String.class);
		return response;
	}

	@Override
	public Collection<PointOfServiceModel> getStores()
	{
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getAllActivePos();
	}

	
	@Override
	public List<PointOfServiceDistanceData> getStoresForZipcode(final BaseStoreModel baseStore, final String zipcode,
			final Double maxRadiusKm)
	{
		List<PointOfServiceDistanceData> sortedPosResults = new ArrayList<>();
		if (!StringUtils.isBlank(zipcode))
		{
			try
			{
   			final GeoPoint geoPoint = new GeoPoint();
   			Collection<PointOfServiceModel> posResults = null;   			
   			final GPS resolvedPoint = getGeoWebServiceWrapper()
   					.geocodeAddress(generateGeoAddressForSearchQuery(baseStore, zipcode));
     			geoPoint.setLatitude(resolvedPoint.getDecimalLatitude());
   			geoPoint.setLongitude(resolvedPoint.getDecimalLongitude());
   			if (maxRadiusKm != null)
   			{
   				posResults = getPointsOfServiceNear(geoPoint, maxRadiusKm.doubleValue(), baseStore);
   			}
   			if (!CollectionUtils.isEmpty(posResults))
   			{
   				sortedPosResults = calculateDistancesForMiles(geoPoint, posResults,	maxRadiusKm.doubleValue());   			
   			}
			}
			catch (final GeoServiceWrapperException ex)
			{
				LOG.error(GEOCODE_EXCEPTION + zipcode + BRACKET, ex);
			}
		}
		return sortedPosResults;
	}
	
	@Override
	public StoreFinderSearchPageData<ITEM> locationSearchWithStoreSpecialities(final BaseStoreModel baseStore,
			final String locationText, final PageableData pageableData, final double maxRadiusKm,
			final List<String> storeSpecialities)
	{
		return locationSearch(baseStore, locationText, pageableData, maxRadiusKm,storeSpecialities);
	}
	
	public StoreFinderSearchPageData<ITEM> locationSearch(final BaseStoreModel baseStore,
			final String locationText, final PageableData pageableData, final double maxRadiusKm,
			final List<String> storeSpecialities)
	{
		final GeoPoint geoPoint = new GeoPoint();

		if (locationText != null && !locationText.isEmpty())
		{
			try
			{
				final PointOfServiceModel pos = ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getStoreDetailForId(locationText);
				if (pos == null)
				{

					return fetchPointOfServiceResultsByLocationText(baseStore, locationText, pageableData, maxRadiusKm,
							storeSpecialities);
				}
				final List<ITEM> result = new ArrayList<ITEM>();


				String postalCode = pos.getAddress().getPostalcode();
				if (postalCode.contains("-"))
				{
					postalCode = postalCode.split("-")[0];
				}

				final GPS resolvedPoint = getGeoWebServiceWrapper()
						.geocodeAddress(generateGeoAddressForSearchQuery(baseStore, postalCode));

				if (null != resolvedPoint)
				{
					LOG.info(
							"Resolved lat & long : " + resolvedPoint.getDecimalLatitude() + "/" + resolvedPoint.getDecimalLongitude());
				}

				geoPoint.setLatitude(resolvedPoint != null ? resolvedPoint.getDecimalLatitude() : 0);
				geoPoint.setLongitude(resolvedPoint != null ? resolvedPoint.getDecimalLongitude() : 0);

				final ITEM storeFinderResultData = (ITEM) new PointOfServiceDistanceData();
				storeFinderResultData.setPointOfService(pos);
				storeFinderResultData.setDistanceKm(calculateDistance(geoPoint, pos));
				result.add(storeFinderResultData);

				return createSiteOneSearchResult(locationText, geoPoint, result, createPagination(pageableData, 1));
			}
			catch (final GeoServiceWrapperException ex)
			{
				LOG.error(GEOCODE_EXCEPTION + locationText + BRACKET, ex);
			}
		}

		// Return no results
		return createSearchResult(locationText, geoPoint, Collections.<ITEM> emptyList(), createPaginationData());
	}


	public StoreFinderSearchPageData<ITEM> doSearchWithStoreSpecialities(final BaseStoreModel baseStore, final String locationText,
			final GeoPoint centerPoint, final PageableData pageableData, final Double maxRadiusKm,
			final List<String> storeSpecialities)
	{
		return doSearch(baseStore, locationText, centerPoint, pageableData, maxRadiusKm,
				storeSpecialities);
	}
	
	public StoreFinderSearchPageData<ITEM> doSearch(final BaseStoreModel baseStore, final String locationText,
			final GeoPoint centerPoint, final PageableData pageableData, final Double maxRadiusKm,
			final List<String> storeSpecialities)
	{
		Collection<PointOfServiceModel> posResults = null;

		final int resultRangeStart = pageableData.getCurrentPage() * pageableData.getPageSize();
		final int resultRangeEnd = (pageableData.getCurrentPage() + 1) * pageableData.getPageSize();
		boolean isStateSearch = false;

		if (locationText != null)
		{
			posResults = ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getPointOfServiceForState(locationText,
					storeSpecialities);
			if (CollectionUtils.isNotEmpty(posResults))
			{
				isStateSearch = true;
			}
		}
		if (!isStateSearch)
		{
			posResults = fetchPointOfServiceResults(centerPoint, maxRadiusKm, baseStore, storeSpecialities);
		}

		if (CollectionUtils.isNotEmpty(posResults))
		{
			List<PointOfServiceDistanceData> orderedResults = null;
			if (isStateSearch)
			{
				orderedResults = calculateDistances(centerPoint, posResults);
			}
			else
			{
				// Sort all the POS
				orderedResults = calculateDistancesForMiles(centerPoint, posResults,
						maxRadiusKm != null ? maxRadiusKm.doubleValue() : 0);
			}
			final PaginationData paginationData = createPagination(pageableData, orderedResults.size());
			// Slice the required range window out of the results
			final List<PointOfServiceDistanceData> orderedResultsWindow = orderedResults
					.subList(Math.min(orderedResults.size(), resultRangeStart), Math.min(orderedResults.size(), resultRangeEnd));
			return createSearchResult(locationText, centerPoint, orderedResultsWindow, paginationData);

		}

		// Return no results
		return createSearchResult(locationText, centerPoint, Collections.<ITEM> emptyList(), createPagination(pageableData, 0));
	}


	public Collection<PointOfServiceModel> getPointsOfServiceNearWithStoreSpecialities(final GeoPoint centerPoint, final double radiusKm,
			final BaseStoreModel baseStore, final List<String> storeSpecialities) throws PointOfServiceDaoException
	{
		final GPS referenceGps = new DefaultGPS(centerPoint.getLatitude(), centerPoint.getLongitude());
		return ((SiteOnePointOfServiceDao) getPointOfServiceDao()).getAllGeocodedPOSWithStoreSpecialities(referenceGps, radiusKm, baseStore,
				storeSpecialities);
	}

	@Override
	public StoreFinderSearchPageData<ITEM> positionSearchWithStoreSpecialities(final BaseStoreModel baseStore, final GeoPoint geoPoint,
			final PageableData pageableData, final double locationQueryMiles,final List<String> storeSpecialities)
	{
		return doSearchWithStoreSpecialities(baseStore, null, geoPoint, pageableData, Double.valueOf(locationQueryMiles),storeSpecialities);
	}
	
	public Collection<PointOfServiceModel> fetchPointOfServiceResults(final GeoPoint centerPoint, final Double maxRadiusKm,
			final BaseStoreModel baseStore, final List<String> storeSpecialities)
	{
		if (maxRadiusKm != null)
		{
			if(CollectionUtils.isNotEmpty(storeSpecialities)){
				return getPointsOfServiceNearWithStoreSpecialities(centerPoint, maxRadiusKm.doubleValue(), baseStore, storeSpecialities);
			}else{
				return getPointsOfServiceNear(centerPoint, maxRadiusKm.doubleValue(), baseStore);
			}
		}
		else
		{
			final Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("baseStore", baseStore);
			paramMap.put("type", PointOfServiceTypeEnum.STORE);
			paramMap.put("isActive", Boolean.TRUE);
			paramMap.put("isDCBranch", Boolean.FALSE);
			return getPointOfServiceGenericDao().find(paramMap);
		}
	}
	
	public StoreFinderSearchPageData<ITEM> fetchPointOfServiceResultsByLocationText(final BaseStoreModel baseStore,
			final String locationText, final PageableData pageableData, final double maxRadiusKm,
			final List<String> storeSpecialities)
	{
		final GeoPoint geoPoint = new GeoPoint();
		final GPS resolvedPoint = getGeoWebServiceWrapper()
				.geocodeAddress(generateGeoAddressForSearchQuery(baseStore, locationText));

		geoPoint.setLatitude(resolvedPoint.getDecimalLatitude());
		geoPoint.setLongitude(resolvedPoint.getDecimalLongitude());

		if(CollectionUtils.isNotEmpty(storeSpecialities)){
			return doSearchWithStoreSpecialities(baseStore, locationText, geoPoint, pageableData, Double.valueOf(maxRadiusKm),
					storeSpecialities);
		}else if(maxRadiusKm != 0.0){
			return doSearch(baseStore, locationText, geoPoint, pageableData, Double.valueOf(maxRadiusKm));
		}else{
			return doSearch(baseStore, locationText, geoPoint, pageableData, baseStore.getMaxRadiusForPoSSearch());
		}
	}
	
	
	/**
	 * @return the siteOneRegionDao
	 */
	public SiteoneRegionDao getSiteOneRegionDao()
	{
		return siteOneRegionDao;
	}

	/**
	 * @param siteOneRegionDao
	 *           the siteOneRegionDao to set
	 */
	public void setSiteOneRegionDao(final SiteoneRegionDao siteOneRegionDao)
	{
		this.siteOneRegionDao = siteOneRegionDao;
	}

	/**
	 * @return the restTemplate
	 */
	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}

	/**
	 * @param restTemplate
	 *           the restTemplate to set
	 */
	public void setRestTemplate(final RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}


}
