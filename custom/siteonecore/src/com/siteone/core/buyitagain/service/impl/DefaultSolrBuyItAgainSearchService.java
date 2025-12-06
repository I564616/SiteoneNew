/**
 *
 */
package com.siteone.core.buyitagain.service.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;

import org.springframework.core.convert.converter.Converter;

import com.siteone.core.buyitagain.service.SolrBuyItAgainSearchService;
import com.siteone.facade.BuyItAgainSearchPageData;


/**
 * @author SMondal
 *
 */
public class DefaultSolrBuyItAgainSearchService<ITEM>
		implements SolrBuyItAgainSearchService<SolrSearchQueryData, ITEM, BuyItAgainSearchPageData<SolrSearchQueryData, ITEM>>
{
	private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> buyItAgainSearchQueryPageableConverter;
	private Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;
	private Converter<SolrSearchResponse, BuyItAgainSearchPageData<SolrSearchQueryData, ITEM>> buyItAgainSearchResponseConverter;



	@Override
	public BuyItAgainSearchPageData<SolrSearchQueryData, ITEM> searchAgain(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		return doSearch(searchQueryData, pageableData);
	}

	protected BuyItAgainSearchPageData<SolrSearchQueryData, ITEM> doSearch(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");
		// Create the SearchQueryPageableData that contains our parameters
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(searchQueryData,
				pageableData);
		// Build up the search request
		final SolrSearchRequest solrSearchRequest = getBuyItAgainSearchQueryPageableConverter().convert(searchQueryPageableData);
		// Execute the search
		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);
		// Convert the response
		return getBuyItAgainSearchResponseConverter().convert(solrSearchResponse);
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
	 * @return the buyItAgainSearchQueryPageableConverter
	 */
	public Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> getBuyItAgainSearchQueryPageableConverter()
	{
		return buyItAgainSearchQueryPageableConverter;
	}



	/**
	 * @param buyItAgainSearchQueryPageableConverter
	 *           the buyItAgainSearchQueryPageableConverter to set
	 */
	public void setBuyItAgainSearchQueryPageableConverter(
			final Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> buyItAgainSearchQueryPageableConverter)
	{
		this.buyItAgainSearchQueryPageableConverter = buyItAgainSearchQueryPageableConverter;
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
	 * @return the buyItAgainSearchResponseConverter
	 */
	public Converter<SolrSearchResponse, BuyItAgainSearchPageData<SolrSearchQueryData, ITEM>> getBuyItAgainSearchResponseConverter()
	{
		return buyItAgainSearchResponseConverter;
	}



	/**
	 * @param buyItAgainSearchResponseConverter
	 *           the buyItAgainSearchResponseConverter to set
	 */
	public void setBuyItAgainSearchResponseConverter(
			final Converter<SolrSearchResponse, BuyItAgainSearchPageData<SolrSearchQueryData, ITEM>> buyItAgainSearchResponseConverter)
	{
		this.buyItAgainSearchResponseConverter = buyItAgainSearchResponseConverter;
	}


}
