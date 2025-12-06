/**
 *
 */
package com.siteone.facades.neareststore.impl;

import de.hybris.platform.commercefacades.storefinder.impl.DefaultStoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.converters.populator.RegionPopulator;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;



/**
 * @author 965504
 *
 */
public class DefaultSiteOneStoreFinderFacade extends DefaultStoreFinderFacade implements SiteOneStoreFinderFacade
{

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	private Converter<PointOfServiceModel, PointOfServiceData> siteOnePointOfServiceConverter;

	private RegionPopulator regionPopulator;
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Override
	public PointOfServiceData nearestStorePositionSearch(final GeoPoint geoPoint)
	{
		PointOfServiceData posData = null;

		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();

		final PointOfServiceModel nearByStore = ((SiteOneStoreFinderService) getStoreFinderService()).doSearch(currentBaseStore,
				geoPoint);

		if (null != nearByStore)
		{
			posData = getSiteOnePointOfServiceConverter().convert(nearByStore);
		}

		return posData;
	}

	/**
	 * @return the storeSessionFacade
	 */
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	@Override
	public PointOfServiceData getStoreForId(final String storeId)
	{
		// YTODO Auto-generated method stub
		final PointOfServiceModel pointOfServiceModel = ((SiteOneStoreFinderService) getStoreFinderService())
				.getStoreForId(storeId);
		if(pointOfServiceModel!=null){
			return getSiteOnePointOfServiceConverter().convert(pointOfServiceModel);
		}else{
			return null;
		}
		
	}


	@Override
	public List<PointOfServiceData> getPointOfServiceForCityAndState(final String city, final String state)
	{


		final List<PointOfServiceModel> pointOfServiceModel = ((SiteOneStoreFinderService) getStoreFinderService())
				.getPointOfServiceForCityAndState(city, state);
		final List<PointOfServiceData> pointOfServiceData = Converters.convertAll(pointOfServiceModel,
				getSiteOnePointOfServiceConverter());

		return pointOfServiceData;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.neareststore.SiteOneStoreFinderFacade#getStoreCitiesForState(java.lang.String)
	 */
	@Override
	public Map<String, TreeMap<String, Integer>> getStoreCitiesForState(final String state)
	{
		// YTODO Auto-generated method stub
		final List<String> storeCities = ((SiteOneStoreFinderService) getStoreFinderService()).getStoreCitiesForState(state);


		final Map<String, Integer> sortedStoreCities = new TreeMap<String, Integer>();
		storeCities.forEach(storeCity -> {
			if (!sortedStoreCities.containsKey(storeCity))
			{
				sortedStoreCities.put(storeCity, Integer.valueOf(Collections.frequency(storeCities, storeCity)));
			}
		});

		final Map<String, TreeMap<String, Integer>> storeCitiesGroups = new TreeMap<String, TreeMap<String, Integer>>();

		sortedStoreCities.forEach((city, storeCount) -> {

			final String alphabet = city.trim().substring(0, 1);

			if (!storeCitiesGroups.containsKey(alphabet))
			{
				final TreeMap<String, Integer> storeCitiesGroup = new TreeMap<String, Integer>();
				storeCitiesGroup.put(city, storeCount);

				storeCitiesGroups.put(alphabet, storeCitiesGroup);
			}
			else
			{

				storeCitiesGroups.get(alphabet).put(city, storeCount);

			}



		});

		return storeCitiesGroups;


	}

	/**
	 * Region API
	 */
	@Override
	public Map<RegionData, Boolean> getStoreRegions(final String countryIsoCode)
	{
		final Map<RegionData, Boolean> regions = new LinkedHashMap<RegionData, Boolean>();

		final List<String> storeRegions = ((SiteOneStoreFinderService) getStoreFinderService()).getStoreRegions();

		final List<RegionModel> usRegions = ((SiteOneStoreFinderService) getStoreFinderService())
				.getRegionsByCountryAndTerritory(countryIsoCode);

		if (usRegions.isEmpty())
		{
			return Collections.emptyMap();
		}
		else
		{
			final List<RegionData> regionFacadeData = new ArrayList<RegionData>();
			for (final RegionModel regionmodel : usRegions)
			{
				final RegionData regionData = new RegionData();
				this.regionPopulator.populate(regionmodel, regionData);

				regionFacadeData.add(regionData);
			}

			//Sort the list in alphabeticalOrder
			final List<RegionData> sortedRegionData = regionFacadeData.stream()
					.sorted((region1, region2) -> region1.getName().compareTo(region2.getName())).collect(Collectors.toList());

			sortedRegionData.forEach(usRegionData -> {

				final Integer storeCount = Integer.valueOf(Collections.frequency(storeRegions, usRegionData.getIsocode()));
				usRegionData.setStoreCount(storeCount);
				regions.put(usRegionData, Boolean.valueOf(storeRegions.contains(usRegionData.getIsocode())));
			});


			return regions;

		}
	}

	@Override
	public StoreFinderSearchPageData<PointOfServiceData> positionSearch(final GeoPoint geoPoint, final PageableData pageableData,
			final double locationQueryMiles)
	{
		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		final StoreFinderSearchPageData<PointOfServiceDistanceData> searchPageData = getStoreFinderService()
				.positionSearch(currentBaseStore, geoPoint, pageableData, locationQueryMiles);
		return getSearchPagePointOfServiceDistanceConverter().convert(searchPageData);
	}



	@Override
	public String getStateNameForIsoCode(final String stateIsoCode)
	{
		return ((SiteOneStoreFinderService) getStoreFinderService()).getStateNameForIsoCode(StringUtils.substringBefore(stateIsoCode,SiteoneCoreConstants.DASH),
				stateIsoCode);
	}

	@Override
	public Set<String> getListofStatesFromSpecialty(final String Specialty)
	{
		final Collection<PointOfServiceModel> pos = siteOneStoreFinderService.getStores();
		final TreeSet<String> regionCodes = new TreeSet();
		if (CollectionUtils.isNotEmpty(pos))
		{
			for (final PointOfServiceModel posModel : pos)
			{
				for (final String speciality : posModel.getStoreSpecialities())
				{
					if (speciality.contains(Specialty))
					{
						regionCodes.add(posModel.getAddress().getRegion().getName());
					}
				}
			}
		}
		return regionCodes;
	}

	@Override
	public TreeMap<String, List<PointOfServiceData>> getListofStoresFromSpecialty(final String state, final String specialty)
	{
		List<String> lsSpecialty = null;
		final TreeMap<String, Set> tmPosModels = new TreeMap();
		final TreeMap<String, List<PointOfServiceData>> tmPosDatas = new TreeMap();
		if (specialty != null)
		{
			final String[] arrSpecialty = specialty.split(",");
			lsSpecialty = Arrays.asList(arrSpecialty);
		}

		final Collection<PointOfServiceModel> pos = siteOneStoreFinderService.getStores();
		Set<PointOfServiceModel> posModels;
		if (pos.isEmpty() || CollectionUtils.isEmpty(lsSpecialty))
		{
			return new TreeMap();
		}
		else
		{
			for (final String lsSpecialtyEntry : lsSpecialty)
			{
				tmPosModels.put(lsSpecialtyEntry, new HashSet());

			}
			for (final PointOfServiceModel posModel : pos)
			{
				for (final String speciality : posModel.getStoreSpecialities())
				{
					if (lsSpecialty.contains(speciality) && (posModel.getAddress().getRegion().getName().contains(state)))
					{
						(tmPosModels.get(speciality)).add(posModel);
					}
				}
			}
			for (final String speciality : tmPosModels.keySet())
			{
				posModels = tmPosModels.get(speciality);
				final List<PointOfServiceData> posDatas = Converters.convertAll(posModels, getSiteOnePointOfServiceConverter());
				Collections.sort(posDatas, StoreSpecialityResultDataComparator.INSTANCE);
				tmPosDatas.put(speciality, posDatas);
			}
			return tmPosDatas;
		}
	}

	public static class StoreSpecialityResultDataComparator extends AbstractComparator<PointOfServiceData>
	{
		public static final StoreSpecialityResultDataComparator INSTANCE = new StoreSpecialityResultDataComparator();

		@Override
		protected int compareInstances(final PointOfServiceData result1, final PointOfServiceData result2)
		{
			int result = compareValues(result1.getDistanceKm().doubleValue(), result2.getDistanceKm().doubleValue());
			if (EQUAL == result)
			{
				result = compareValues(result1.getName(), result2.getName(), false);
			}
			return result;
		}
	}


	@Override
	public PointOfServiceData getHubStoreBranch(final String storeId)
	{
		if (storeId != null)
		{
			List<PointOfServiceData> posHubStoresDataList = new ArrayList<>();
			final PointOfServiceModel pos = getSiteOneStoreFinderService().getStoreForId(storeId);

			if (CollectionUtils.isNotEmpty(pos.getShippingHubBranches()))
			{
				posHubStoresDataList = getSiteOnePointOfServiceConverter().convertAll(pos.getShippingHubBranches());

				if (CollectionUtils.isNotEmpty(posHubStoresDataList))
				{
					final PointOfServiceData hubPos = posHubStoresDataList.get(0);
					return hubPos != null ? hubPos : null;
				}

			}
		}

		return null;
	}
	
	@Override
	public List<PointOfServiceData> getStoresForZipcode(final String zipcode,
			final double maxRadiusKm)
	{
		List<PointOfServiceData> stores = new ArrayList<>();
		List<PointOfServiceDistanceData> storesModelList = getSiteOneStoreFinderService().getStoresForZipcode(
				getBaseStoreService().getCurrentBaseStore(), zipcode, Double.valueOf(maxRadiusKm));
		if(!CollectionUtils.isEmpty(storesModelList))
		{
			for(PointOfServiceDistanceData pos : storesModelList)
			{
				PointOfServiceData store = getSiteOnePointOfServiceConverter().convert(pos.getPointOfService());
				getPointOfServiceDistanceConverter().convert(pos, store);
				stores.add(store);
			}
		}
		return stores;
	}

	@Override
	public StoreFinderSearchPageData<PointOfServiceData> locationSearchWithStoreSpecialities(final String locationText, final PageableData pageableData,
			final double maxRadius, final List<String> storespecilaities)
	{

		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		final StoreFinderSearchPageData<PointOfServiceDistanceData> searchPageData = ((SiteOneStoreFinderService) getStoreFinderService())
				.locationSearchWithStoreSpecialities(currentBaseStore, locationText, pageableData, maxRadius, storespecilaities);
		return getSearchPagePointOfServiceDistanceConverter().convert(searchPageData);
	}


	@Override
	public StoreFinderSearchPageData<PointOfServiceData> positionSearchWithStoreSpecialities(final GeoPoint geoPoint,
			final PageableData pageableData, final double locationQueryMiles, final List<String> storeSpecialities)
	{
		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		final StoreFinderSearchPageData<PointOfServiceDistanceData> searchPageData = ((SiteOneStoreFinderService) getStoreFinderService())
				.positionSearchWithStoreSpecialities(currentBaseStore, geoPoint, pageableData, locationQueryMiles, storeSpecialities);
		return getSearchPagePointOfServiceDistanceConverter().convert(searchPageData);
	}
	
	/**
	 * @return the siteOnePointOfServiceConverter
	 */
	public Converter<PointOfServiceModel, PointOfServiceData> getSiteOnePointOfServiceConverter()
	{
		return siteOnePointOfServiceConverter;
	}

	/**
	 * @param siteOnePointOfServiceConverter
	 *           the siteOnePointOfServiceConverter to set
	 */
	public void setSiteOnePointOfServiceConverter(
			final Converter<PointOfServiceModel, PointOfServiceData> siteOnePointOfServiceConverter)
	{
		this.siteOnePointOfServiceConverter = siteOnePointOfServiceConverter;
	}

	/**
	 * @return the regionPopulator
	 */
	public RegionPopulator getRegionPopulator()
	{
		return regionPopulator;
	}

	/**
	 * @param regionPopulator
	 *           the regionPopulator to set
	 */
	public void setRegionPopulator(final RegionPopulator regionPopulator)
	{
		this.regionPopulator = regionPopulator;
	}

	/**
	 * @return the siteOneStoreFinderService
	 */
	public SiteOneStoreFinderService getSiteOneStoreFinderService()
	{
		return siteOneStoreFinderService;
	}

	/**
	 * @param siteOneStoreFinderService
	 *           the siteOneStoreFinderService to set
	 */
	public void setSiteOneStoreFinderService(final SiteOneStoreFinderService siteOneStoreFinderService)
	{
		this.siteOneStoreFinderService = siteOneStoreFinderService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.siteone.facades.neareststore.SiteOneStoreFinderFacade#positionSearch(java.lang.String,
	 * de.hybris.platform.commerceservices.search.pagedata.PageableData, java.lang.String)
	 */




}
