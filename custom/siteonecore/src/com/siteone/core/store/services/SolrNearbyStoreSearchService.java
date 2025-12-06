package com.siteone.core.store.services;


import com.siteone.facade.NearbyStoreSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

/**
 * @author nmangal
 *
 */
public interface SolrNearbyStoreSearchService<STATE, ITEM, RESULT extends NearbyStoreSearchPageData<STATE, ITEM>>
{
    RESULT searchAgain(STATE searchQueryData, PageableData pageableData);
}
