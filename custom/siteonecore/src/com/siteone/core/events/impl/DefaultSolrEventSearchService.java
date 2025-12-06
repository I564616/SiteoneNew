/**
 *
 */
package com.siteone.core.events.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;

import org.springframework.core.convert.converter.Converter;

import com.siteone.core.events.SolrEventSearchService;
import com.siteone.facade.EventSearchPageData;


/**
 * @author 1124932
 *
 */
public class DefaultSolrEventSearchService<ITEM>
		implements SolrEventSearchService<SolrSearchQueryData, ITEM, EventSearchPageData<SolrSearchQueryData, ITEM>>
{
	private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> eventSearchQueryPageableConverter;
	private Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;
	private Converter<SolrSearchResponse, EventSearchPageData<SolrSearchQueryData, ITEM>> eventSearchResponseConverter;

	@Override
	public EventSearchPageData<SolrSearchQueryData, ITEM> searchAgain(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		return doSearch(searchQueryData, pageableData);
	}

	protected EventSearchPageData<SolrSearchQueryData, ITEM> doSearch(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");
		// Create the SearchQueryPageableData that contains our parameters
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(searchQueryData,
				pageableData);
		// Build up the search request
		final SolrSearchRequest solrSearchRequest = getEventSearchQueryPageableConverter().convert(searchQueryPageableData);
		// Execute the search
		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);
		// Convert the response
		return getEventSearchResponseConverter().convert(solrSearchResponse);
	}

	protected SearchQueryPageableData<SolrSearchQueryData> buildSearchQueryPageableData(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = createSearchQueryPageableData();
		searchQueryPageableData.setSearchQueryData(searchQueryData);
		searchQueryPageableData.setPageableData(pageableData);
		return searchQueryPageableData;
	}

	protected SearchQueryPageableData<SolrSearchQueryData> createSearchQueryPageableData()
	{
		return new SearchQueryPageableData<SolrSearchQueryData>();
	}

	protected SolrSearchQueryData createSearchQueryData()
	{
		return new SolrSearchQueryData();
	}

	/**
	 * @return the eventSearchQueryPageableConverter
	 */
	public Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> getEventSearchQueryPageableConverter()
	{
		return eventSearchQueryPageableConverter;
	}

	/**
	 * @param eventSearchQueryPageableConverter
	 *           the eventSearchQueryPageableConverter to set
	 */
	public void setEventSearchQueryPageableConverter(
			final Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> eventSearchQueryPageableConverter)
	{
		this.eventSearchQueryPageableConverter = eventSearchQueryPageableConverter;
	}

	/**
	 * @return the searchRequestConverter
	 */
	public Converter<SolrSearchRequest, SolrSearchResponse> getSearchRequestConverter()
	{
		return searchRequestConverter;
	}

	/**
	 * @param searchRequestConverter
	 *           the searchRequestConverter to set
	 */
	public void setSearchRequestConverter(final Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter)
	{
		this.searchRequestConverter = searchRequestConverter;
	}

	/**
	 * @return the eventSearchResponseConverter
	 */
	public Converter<SolrSearchResponse, EventSearchPageData<SolrSearchQueryData, ITEM>> getEventSearchResponseConverter()
	{
		return eventSearchResponseConverter;
	}

	/**
	 * @param eventSearchResponseConverter
	 *           the eventSearchResponseConverter to set
	 */
	public void setEventSearchResponseConverter(
			final Converter<SolrSearchResponse, EventSearchPageData<SolrSearchQueryData, ITEM>> eventSearchResponseConverter)
	{
		this.eventSearchResponseConverter = eventSearchResponseConverter;
	}

}
