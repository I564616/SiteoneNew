package com.siteone.core.listeners;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchContext;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchListener;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;


public class SiteoneStoreSearchListener implements FacetSearchListener {

    @Resource(name = "sessionService")
    private SessionService sessionService;

    public SiteoneStoreSearchListener() {
        //empty
    }

    public void beforeSearch(final FacetSearchContext facetSearchContext) throws FacetSearchException {
        final PointOfServiceData pointOfService = sessionService.getAttribute("sessionStore");


        if (null != pointOfService && pointOfService.getGeoPoint() != null) {
            final String geoPoint = pointOfService.getGeoPoint().getLatitude() + "," + pointOfService.getGeoPoint().getLongitude();
            final Integer nearbyStoreSearchDistance = pointOfService.getNearbyStoreSearchRadius();
            int globalNearbyStoreSearchDist;
            if (nearbyStoreSearchDistance != null && nearbyStoreSearchDistance >= 0) {
                globalNearbyStoreSearchDist = nearbyStoreSearchDistance;
            } else {
                globalNearbyStoreSearchDist = Config.getInt("solr.nearbystore.range.miles", 20); //default 20 miles value for stores missing value.
            }

            final double globalRangeInKilometers = globalNearbyStoreSearchDist * 1.609;
            final String globalNearbyStoreSearchDistString = Double.toString(globalRangeInKilometers);

            final SearchQuery searchQuery = facetSearchContext.getSearchQuery();
            searchQuery.addRawQuery(
                    "fq={!geofilt sfield=soLatLong_string pt=" + geoPoint + " d=" + globalNearbyStoreSearchDistString + "} ");
        }
    }

    public void afterSearch(final FacetSearchContext facetSearchContext) throws FacetSearchException {
        //No Action

    }

    public void afterSearchError(final FacetSearchContext facetSearchContext) throws FacetSearchException {
        //No Action
    }

    /**
     * @return the sessionService
     */
    public SessionService getSessionService() {
        return sessionService;
    }

    /**
     * @param sessionService the sessionService to set
     */
    public void setSessionService(final SessionService sessionService) {
        this.sessionService = sessionService;
    }
}