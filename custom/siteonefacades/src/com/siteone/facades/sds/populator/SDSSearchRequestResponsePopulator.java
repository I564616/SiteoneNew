/**
 *
 */
package com.siteone.facades.sds.populator;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SolrSearchRequestResponsePopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.FacetSearchService;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;

import org.apache.log4j.Logger;
import org.apache.solr.common.SolrException;


/**
 * @author 1229803
 *
 */
public class SDSSearchRequestResponsePopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		implements
		Populator<SolrSearchRequest<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE>, SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE, SearchResult>>
{
	private static final Logger LOG = Logger.getLogger(SolrSearchRequestResponsePopulator.class);

	private FacetSearchService solrFacetSearchService;

	@Override
	public void populate(
			final SolrSearchRequest<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> source,
			final SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE, SearchResult> target)
	{
		try
		{
			target.setRequest(source);
			final SearchResult searchResult = getSolrFacetSearchService().search(source.getSearchQuery());
			target.setSearchResult(searchResult);
		}
		catch (final FacetSearchException | SolrException | NullPointerException ex)
		{
			LOG.error("Exception while executing SOLR search", ex);
		}
	}


	/**
	 * @return the solrFacetSearchService
	 */
	public FacetSearchService getSolrFacetSearchService()
	{
		return solrFacetSearchService;
	}

	/**
	 * @param solrFacetSearchService
	 *           the solrFacetSearchService to set
	 */
	public void setSolrFacetSearchService(final FacetSearchService solrFacetSearchService)
	{
		this.solrFacetSearchService = solrFacetSearchService;
	}
}
