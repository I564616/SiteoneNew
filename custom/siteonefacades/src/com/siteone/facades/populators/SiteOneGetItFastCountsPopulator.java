/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.facades.populators;

import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchResponsePaginationPopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.solrfacetsearch.config.IndexedTypeSort;
import de.hybris.platform.solrfacetsearch.search.SearchResult;

import java.util.Map;


/**
 */
public class SiteOneGetItFastCountsPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SEARCH_QUERY_TYPE, ITEM>
		implements
		Populator<SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SEARCH_QUERY_TYPE, IndexedTypeSort, SearchResult>, SearchPageData<ITEM>>
{
	@Override
	public void populate(
			final SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SEARCH_QUERY_TYPE, IndexedTypeSort, SearchResult> source,
			final SearchPageData<ITEM> target)
	{
		if (source.getSearchResult()!= null && source.getSearchResult().getSolrObject().getFacetQuery() != null)
		{
		Map<String,Integer> facetQueries = source.getSearchResult().getSolrObject().getFacetQuery();
		for (String key : facetQueries.keySet()) {

			if (null != target.getPagination()) {
				if("InStock".equalsIgnoreCase(key)) {
					target.getPagination().setInStockCount(facetQueries.get(key));
				}
				if("IsShippable".equalsIgnoreCase(key)) {
					target.getPagination().setShippableCount(facetQueries.get(key));
				}
			}
		}
		}
	}
}