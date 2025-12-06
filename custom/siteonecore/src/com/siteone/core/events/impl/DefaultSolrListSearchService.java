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

import com.siteone.core.events.SolrListSearchService;
import com.siteone.facade.ListSearchPageData;


/**
 * @author SM04392
 *
 */
public class DefaultSolrListSearchService<ITEM>
		implements SolrListSearchService<SolrSearchQueryData, ITEM, ListSearchPageData<SolrSearchQueryData, ITEM>>
{
	private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> listSearchQueryPageableConverter;
	private Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;
	private Converter<SolrSearchResponse, ListSearchPageData<SolrSearchQueryData, ITEM>> listSearchResponseConverter;

	@Override
	public ListSearchPageData<SolrSearchQueryData, ITEM> searchAgain(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		return doSearch(searchQueryData, pageableData);
	}

	protected ListSearchPageData<SolrSearchQueryData, ITEM> doSearch(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");

		// Create the SearchQueryPageableData that contains our parameters
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(searchQueryData,
				pageableData);
		// Build up the search request
		final SolrSearchRequest solrSearchRequest = getListSearchQueryPageableConverter().convert(searchQueryPageableData);
		// Execute the search
		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);
		// Convert the response
		return getListSearchResponseConverter().convert(solrSearchResponse);
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
	 * @return the listSearchQueryPageableConverter
	 */
	public Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> getListSearchQueryPageableConverter()
	{
		return listSearchQueryPageableConverter;
	}

	/**
	 * @param listSearchQueryPageableConverter
	 *           the listSearchQueryPageableConverter to set
	 */
	public void setListSearchQueryPageableConverter(
			final Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> listSearchQueryPageableConverter)
	{
		this.listSearchQueryPageableConverter = listSearchQueryPageableConverter;
	}

	/**
	 * @return the listSearchResponseConverter
	 */
	public Converter<SolrSearchResponse, ListSearchPageData<SolrSearchQueryData, ITEM>> getListSearchResponseConverter()
	{
		return listSearchResponseConverter;
	}

	/**
	 * @param listSearchResponseConverter
	 *           the listSearchResponseConverter to set
	 */
	public void setListSearchResponseConverter(
			final Converter<SolrSearchResponse, ListSearchPageData<SolrSearchQueryData, ITEM>> listSearchResponseConverter)
	{
		this.listSearchResponseConverter = listSearchResponseConverter;
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

}
