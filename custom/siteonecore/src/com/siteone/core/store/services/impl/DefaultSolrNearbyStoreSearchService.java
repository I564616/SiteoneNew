package com.siteone.core.store.services.impl;


import com.siteone.core.store.services.SolrNearbyStoreSearchService;
import com.siteone.facade.NearbyStoreSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import org.springframework.core.convert.converter.Converter;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

public class DefaultSolrNearbyStoreSearchService<ITEM> implements SolrNearbyStoreSearchService<SolrSearchQueryData, ITEM, NearbyStoreSearchPageData<SolrSearchQueryData, ITEM>> {

    private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> nearbyStoreSearchQueryPageableConverter;
    private Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;
    private Converter<SolrSearchResponse, NearbyStoreSearchPageData<SolrSearchQueryData, ITEM>> nearbyStoreSearchResponseConverter;


    @Override
    public NearbyStoreSearchPageData<SolrSearchQueryData, ITEM> searchAgain(final SolrSearchQueryData searchQueryData,
                                                                      final PageableData pageableData)
    {
        return doSearch(searchQueryData, pageableData);
    }

    protected NearbyStoreSearchPageData<SolrSearchQueryData, ITEM> doSearch(final SolrSearchQueryData searchQueryData,
                                                                      final PageableData pageableData)
    {
        validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");
        // Create the SearchQueryPageableData that contains our parameters
        final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(searchQueryData,
                pageableData);
        // Build up the search request
        final SolrSearchRequest solrSearchRequest = getNearbyStoreSearchQueryPageableConverter().convert(searchQueryPageableData);
        // Execute the search
        final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);
        // Con7vert the response
        return getNearbyStoreSearchResponseConverter().convert(solrSearchResponse);
    }

    protected SearchQueryPageableData<SolrSearchQueryData> createSearchQueryPageableData()
    {
        return new SearchQueryPageableData<SolrSearchQueryData>();
    }


    protected SearchQueryPageableData<SolrSearchQueryData> buildSearchQueryPageableData(final SolrSearchQueryData searchQueryData,
                                                                                        final PageableData pageableData)
    {
        final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = createSearchQueryPageableData();
        searchQueryPageableData.setSearchQueryData(searchQueryData);
        searchQueryPageableData.setPageableData(pageableData);
        return searchQueryPageableData;
    }


    public Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> getNearbyStoreSearchQueryPageableConverter() {
        return nearbyStoreSearchQueryPageableConverter;
    }

    public void setNearbyStoreSearchQueryPageableConverter(Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> nearbyStoreSearchQueryPageableConverter) {
        this.nearbyStoreSearchQueryPageableConverter = nearbyStoreSearchQueryPageableConverter;
    }

    public Converter<SolrSearchRequest, SolrSearchResponse> getSearchRequestConverter() {
        return searchRequestConverter;
    }

    public void setSearchRequestConverter(Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter) {
        this.searchRequestConverter = searchRequestConverter;
    }

    public Converter<SolrSearchResponse, NearbyStoreSearchPageData<SolrSearchQueryData, ITEM>> getNearbyStoreSearchResponseConverter() {
        return nearbyStoreSearchResponseConverter;
    }

    public void setNearbyStoreSearchResponseConverter(Converter<SolrSearchResponse, NearbyStoreSearchPageData<SolrSearchQueryData, ITEM>> nearbyStoreSearchResponseConverter) {
        this.nearbyStoreSearchResponseConverter = nearbyStoreSearchResponseConverter;
    }
}