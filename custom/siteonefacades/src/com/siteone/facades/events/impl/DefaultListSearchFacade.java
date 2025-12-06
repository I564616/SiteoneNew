/**
 *
 */
package com.siteone.facades.events.impl;

import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.siteone.core.events.SolrListSearchService;
import com.siteone.facade.ListSearchPageData;
import com.siteone.facades.events.ListSearchFacade;
import com.siteone.facades.savedList.data.SavedListEntryData;


/**
 * @author SM04392
 *
 */
public class DefaultListSearchFacade<ITEM extends SavedListEntryData> implements ListSearchFacade<ITEM>
{

	private ThreadContextService threadContextService;
	private Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder;
	private Converter<ListSearchPageData<SolrSearchQueryData, SearchResultValueData>, ListSearchPageData<SearchStateData, ITEM>> listSearchPageConverter;
	private SolrListSearchService<SolrSearchQueryData, SearchResultValueData, ListSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrListSearchService;


	public void setThreadContextService(final ThreadContextService threadContextService)
	{
		this.threadContextService = threadContextService;
	}

	@Override
	public ListSearchPageData<SearchStateData, ITEM> listSearch(final SearchStateData searchState, final PageableData pageableData)
	{
		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ListSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ListSearchPageData<SearchStateData, ITEM> execute()
					{
						return getListSearchPageConverter()
								.convert(getSolrListSearchService().searchAgain(decodeState(searchState), pageableData));
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
	 * @return the listSearchPageConverter
	 */
	public Converter<ListSearchPageData<SolrSearchQueryData, SearchResultValueData>, ListSearchPageData<SearchStateData, ITEM>> getListSearchPageConverter()
	{
		return listSearchPageConverter;
	}

	/**
	 * @param listSearchPageConverter
	 *           the listSearchPageConverter to set
	 */
	public void setListSearchPageConverter(
			final Converter<ListSearchPageData<SolrSearchQueryData, SearchResultValueData>, ListSearchPageData<SearchStateData, ITEM>> listSearchPageConverter)
	{
		this.listSearchPageConverter = listSearchPageConverter;
	}

	/**
	 * @return the solrListSearchService
	 */
	public SolrListSearchService<SolrSearchQueryData, SearchResultValueData, ListSearchPageData<SolrSearchQueryData, SearchResultValueData>> getSolrListSearchService()
	{
		return solrListSearchService;
	}

	/**
	 * @param solrListSearchService
	 *           the solrListSearchService to set
	 */
	public void setSolrListSearchService(
			final SolrListSearchService<SolrSearchQueryData, SearchResultValueData, ListSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrListSearchService)
	{
		this.solrListSearchService = solrListSearchService;
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
	 * @param threadContextService
	 *           the threadContextService to set
	 */

}
