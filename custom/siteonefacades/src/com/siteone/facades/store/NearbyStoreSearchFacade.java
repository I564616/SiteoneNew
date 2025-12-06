package com.siteone.facades.store;

import com.siteone.facade.NearbyStoreSearchPageData;

import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author nmangal
 *
 */
public interface NearbyStoreSearchFacade<ITEM extends PointOfServiceData>
{
    NearbyStoreSearchPageData<SearchStateData, ITEM> nearbyStoresSearch(SearchStateData searchState, PageableData pageableData);
    
    HashMap<String, ArrayList<PointOfServiceData>> getNearbyStores();
}

