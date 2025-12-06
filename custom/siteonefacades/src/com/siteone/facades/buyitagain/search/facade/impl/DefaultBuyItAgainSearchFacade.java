/**
 *
 */
package com.siteone.facades.buyitagain.search.facade.impl;

import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.siteone.core.buyitagain.service.SolrBuyItAgainSearchService;
import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;
import com.siteone.facades.buyitagain.search.facade.BuyItAgainSearchFacade;


/**
 * @author SMondal
 *
 */
public class DefaultBuyItAgainSearchFacade<ITEM extends BuyItAgainData> implements BuyItAgainSearchFacade<ITEM>
{
	private ThreadContextService threadContextService;
	private Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder;
	private Converter<BuyItAgainSearchPageData<SolrSearchQueryData, SearchResultValueData>, BuyItAgainSearchPageData<SearchStateData, ITEM>> buyItAgainSearchPageConverter;
	private SolrBuyItAgainSearchService<SolrSearchQueryData, SearchResultValueData, BuyItAgainSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrBuyItAgainSearchService;

	@Override
	public BuyItAgainSearchPageData<SearchStateData, ITEM> getBuyItAgainProducts(final PageableData pageableData)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	@Override
	public BuyItAgainSearchPageData<SearchStateData, ITEM> buyItAgainSearch(final SearchStateData searchState,
			final PageableData pageableData)
	{
		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<BuyItAgainSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public BuyItAgainSearchPageData<SearchStateData, ITEM> execute()
					{
						return getBuyItAgainSearchPageConverter()
								.convert(getSolrBuyItAgainSearchService().searchAgain(decodeState(searchState), pageableData));
					}
				});
	}

	protected SolrSearchQueryData decodeState(final SearchStateData searchState)
	{
		return getSearchQueryDecoder().convert(searchState.getQuery());
	}


	/**
	 * @return the threadContextService
	 */
	public ThreadContextService getThreadContextService()
	{
		return threadContextService;
	}

	/**
	 * @param threadContextService
	 *           the threadContextService to set
	 */
	public void setThreadContextService(final ThreadContextService threadContextService)
	{
		this.threadContextService = threadContextService;
	}

	/**
	 * @return the searchQueryDecoder
	 */
	public Converter<SearchQueryData, SolrSearchQueryData> getSearchQueryDecoder()
	{
		return searchQueryDecoder;
	}

	/**
	 * @param searchQueryDecoder
	 *           the searchQueryDecoder to set
	 */
	public void setSearchQueryDecoder(final Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder)
	{
		this.searchQueryDecoder = searchQueryDecoder;
	}

	/**
	 * @return the buyItAgainSearchPageConverter
	 */
	public Converter<BuyItAgainSearchPageData<SolrSearchQueryData, SearchResultValueData>, BuyItAgainSearchPageData<SearchStateData, ITEM>> getBuyItAgainSearchPageConverter()
	{
		return buyItAgainSearchPageConverter;
	}

	/**
	 * @param buyItAgainSearchPageConverter
	 *           the buyItAgainSearchPageConverter to set
	 */
	public void setBuyItAgainSearchPageConverter(
			final Converter<BuyItAgainSearchPageData<SolrSearchQueryData, SearchResultValueData>, BuyItAgainSearchPageData<SearchStateData, ITEM>> buyItAgainSearchPageConverter)
	{
		this.buyItAgainSearchPageConverter = buyItAgainSearchPageConverter;
	}

	/**
	 * @return the solrBuyItAgainSearchService
	 */
	public SolrBuyItAgainSearchService<SolrSearchQueryData, SearchResultValueData, BuyItAgainSearchPageData<SolrSearchQueryData, SearchResultValueData>> getSolrBuyItAgainSearchService()
	{
		return solrBuyItAgainSearchService;
	}

	/**
	 * @param solrBuyItAgainSearchService
	 *           the solrBuyItAgainSearchService to set
	 */
	public void setSolrBuyItAgainSearchService(
			final SolrBuyItAgainSearchService<SolrSearchQueryData, SearchResultValueData, BuyItAgainSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrBuyItAgainSearchService)
	{
		this.solrBuyItAgainSearchService = solrBuyItAgainSearchService;
	}



}
