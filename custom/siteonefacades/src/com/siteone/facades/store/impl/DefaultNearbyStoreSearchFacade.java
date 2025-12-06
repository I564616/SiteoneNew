package com.siteone.facades.store.impl;


import com.siteone.core.store.services.SolrNearbyStoreSearchService;
import com.siteone.facade.NearbyStoreSearchPageData;
import com.siteone.facades.store.NearbyStoreSearchFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;

import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.impl.GeometryUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;


/**
 * @author nmangal
 *
 */
public class DefaultNearbyStoreSearchFacade<ITEM extends PointOfServiceData> implements NearbyStoreSearchFacade<ITEM>
{

    private ThreadContextService threadContextService;
    private Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder;
    private Converter<NearbyStoreSearchPageData<SolrSearchQueryData, SearchResultValueData>, NearbyStoreSearchPageData<SearchStateData, ITEM>> nearbyStoreSearchPageConverter;
    private SolrNearbyStoreSearchService<SolrSearchQueryData, SearchResultValueData, NearbyStoreSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrNearbyStoreSearchService;

    @Resource(name = "sessionService")
    private SessionService sessionService;
    
    @Resource(name = "baseSiteService")
    private BaseSiteService baseSiteService;

    private StoreSessionFacade storeSessionFacade;
    private final Logger LOG = Logger.getLogger(DefaultNearbyStoreSearchFacade.class);
    public static final double IMPERIAL_DISTANCE_RATIO = 0.62137;
	public static final String NEARBY = "NEARBY";
	public static final String EXTENDED_NEARBY = "EXTENDED_NEARBY";

    public void setThreadContextService(final ThreadContextService threadContextService)
    {
        this.threadContextService = threadContextService;
    }
    
    public HashMap<String, ArrayList<PointOfServiceData>> getNearbyStores()
    {
        final PageableData pageableData = new PageableData();
        pageableData.setPageSize(Config.getInt("solr.nearbystore.pageSize", 100));
        final SearchStateData searchState = new SearchStateData();
        final SearchQueryData searchQueryData = new SearchQueryData();
        searchQueryData.setValue("");
        searchState.setQuery(searchQueryData);
 		 
 		final NearbyStoreSearchPageData<SearchStateData, ITEM> searchPageData = nearbyStoresSearch(searchState,pageableData);

 		LOG.info(searchPageData.getResults());
	   final ArrayList<PointOfServiceData> nearbyStores = new ArrayList<>();
		final ArrayList<PointOfServiceData> extendedNearbyStores = new ArrayList<>();
		final PointOfServiceData pointOfService = sessionService.getAttribute("sessionStore");
		final HashMap<String, ArrayList<PointOfServiceData>> hmExtendedNearByStores = new HashMap();
		Integer nearbyStoreSearchDistance = null;
		if (null != pointOfService && pointOfService.getGeoPoint() != null)
		{
			nearbyStoreSearchDistance = pointOfService.getNearbyStoreSearchRadius();
			final String nearbyStoreSearchDistString = null;
			final int globalNearbyStoreSearchDist;
			final double rangeInKilometers;

			if (nearbyStoreSearchDistance == null || nearbyStoreSearchDistance < 0)
			{
				nearbyStoreSearchDistance = Config.getInt("solr.nearbystore.range.miles", 20); //default 20 miles value for stores missing value.
			}


		}
		if (CollectionUtils.isNotEmpty(searchPageData.getResults()) && nearbyStoreSearchDistance != null)
		{
			final List<PointOfServiceData> searchResults = (ArrayList<PointOfServiceData>) searchPageData.getResults();
			calculateDistance(searchResults);
			Collections.sort(searchResults, Comparator.comparing(PointOfServiceData::getDistance));
			final int maxNearbyStoresAllowed = Config.getInt("solr.max.nearbystores.allowed", 5);
			int maxNearbyStoresCounter = 0;

			for (final PointOfServiceData pos : searchPageData.getResults())
			{
				if (pos != null)
				{
					if (maxNearbyStoresCounter <= maxNearbyStoresAllowed && pos.getDistance() <= nearbyStoreSearchDistance)
					{
						nearbyStores.add(maxNearbyStoresCounter, pos);
						extendedNearbyStores.add(pos);
						maxNearbyStoresCounter++;
					}
					else
					{
						extendedNearbyStores.add(pos);
					}
				}
			}
		}
		else
		{
			nearbyStores.add(((SiteOneStoreSessionFacade) storeSessionFacade).getSessionStore());
		}
		hmExtendedNearByStores.put(NEARBY, nearbyStores);
		hmExtendedNearByStores.put(EXTENDED_NEARBY, extendedNearbyStores);
		return hmExtendedNearByStores;
	}

    @Override
    public NearbyStoreSearchPageData<SearchStateData, ITEM> nearbyStoresSearch(final SearchStateData searchState,
                                                                                  final PageableData pageableData)
    {
        return getThreadContextService().executeInContext(
                new ThreadContextService.Executor<NearbyStoreSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
                {
                    @Override
                    public NearbyStoreSearchPageData<SearchStateData, ITEM> execute()
                    {
                        return getNearbyStoreSearchPageConverter()
                                .convert(getSolrNearbyStoreSearchService().searchAgain(decodeState(searchState), pageableData));
                    }
                });
    }

    private void calculateDistance(final List<PointOfServiceData> listOfStores)
    {
        final PointOfServiceData homeStore = sessionService.getAttribute("sessionStore");

        if(null != homeStore) {
            for (final PointOfServiceData storeData : listOfStores) {
                final GPS homeStoreGPS = new DefaultGPS(homeStore.getGeoPoint().getLatitude(), homeStore.getGeoPoint().getLongitude());
                final GPS currentStoreGPS = new DefaultGPS(storeData.getGeoPoint().getLatitude(), storeData.getGeoPoint().getLongitude());
                final double distanceInKm = GeometryUtils.getElipticalDistanceKM(homeStoreGPS, currentStoreGPS);
                storeData.setDistanceKm(distanceInKm);

                final double distanceInMiles = Double.valueOf(distanceInKm * IMPERIAL_DISTANCE_RATIO);
                if(baseSiteService.getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
               	 storeData.setDistance(distanceInMiles);
               	 storeData.setFormattedDistance((new DecimalFormat("###,##0.0")).format(distanceInMiles));
                }
                else {
               	 storeData.setDistance(distanceInKm);
                   storeData.setFormattedDistance((new DecimalFormat("###,##0.0")).format(distanceInKm));
                }
            }
        }
    }

    protected SolrSearchQueryData decodeState(final SearchStateData searchState)
    {
        return getSearchQueryDecoder().convert(searchState.getQuery());
    }

    public ThreadContextService getThreadContextService() {
        return threadContextService;
    }

    public Converter<SearchQueryData, SolrSearchQueryData> getSearchQueryDecoder() {
        return searchQueryDecoder;
    }

    public void setSearchQueryDecoder(final Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder) {
        this.searchQueryDecoder = searchQueryDecoder;
    }

    public Converter<NearbyStoreSearchPageData<SolrSearchQueryData, SearchResultValueData>, NearbyStoreSearchPageData<SearchStateData, ITEM>> getNearbyStoreSearchPageConverter() {
        return nearbyStoreSearchPageConverter;
    }

    public void setNearbyStoreSearchPageConverter(final Converter<NearbyStoreSearchPageData<SolrSearchQueryData, SearchResultValueData>, NearbyStoreSearchPageData<SearchStateData, ITEM>> nearbyStoreSearchPageConverter) {
        this.nearbyStoreSearchPageConverter = nearbyStoreSearchPageConverter;
    }

    public SolrNearbyStoreSearchService<SolrSearchQueryData, SearchResultValueData, NearbyStoreSearchPageData<SolrSearchQueryData, SearchResultValueData>> getSolrNearbyStoreSearchService() {
        return solrNearbyStoreSearchService;
    }

    public void setSolrNearbyStoreSearchService(final SolrNearbyStoreSearchService<SolrSearchQueryData, SearchResultValueData, NearbyStoreSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrNearbyStoreSearchService) {
        this.solrNearbyStoreSearchService = solrNearbyStoreSearchService;
    }
    
    /**
 	 * @return the storeSessionFacade
 	 */
 	public StoreSessionFacade getStoreSessionFacade()
 	{
 		return storeSessionFacade;
 	}

 	/**
 	 * @param storeSessionFacade
 	 *           the storeSessionFacade to set
 	 */
 	public void setStoreSessionFacade(final StoreSessionFacade storeSessionFacade)
 	{
 		this.storeSessionFacade = storeSessionFacade;
 	}

}

