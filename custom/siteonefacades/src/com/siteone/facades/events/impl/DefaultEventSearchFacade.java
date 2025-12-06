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

import com.siteone.core.events.SolrEventSearchService;
import com.siteone.facade.EventData;
import com.siteone.facade.EventSearchPageData;
import com.siteone.facades.events.EventSearchFacade;


/**
 * @author 1124932
 *
 */
public class DefaultEventSearchFacade<ITEM extends EventData> implements EventSearchFacade<ITEM>
{

	private ThreadContextService threadContextService;
	private Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder;
	private Converter<EventSearchPageData<SolrSearchQueryData, SearchResultValueData>, EventSearchPageData<SearchStateData, ITEM>> eventSearchPageConverter;
	private SolrEventSearchService<SolrSearchQueryData, SearchResultValueData, EventSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrEventSearchService;


	public void setThreadContextService(final ThreadContextService threadContextService)
	{
		this.threadContextService = threadContextService;
	}

	@Override
	public EventSearchPageData<SearchStateData, ITEM> eventSearch(final SearchStateData searchState,
			final PageableData pageableData)
	{
		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<EventSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public EventSearchPageData<SearchStateData, ITEM> execute()
					{
						return getEventSearchPageConverter()
								.convert(getSolrEventSearchService().searchAgain(decodeState(searchState), pageableData));
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
	 * @return the eventSearchPageConverter
	 */
	public Converter<EventSearchPageData<SolrSearchQueryData, SearchResultValueData>, EventSearchPageData<SearchStateData, ITEM>> getEventSearchPageConverter()
	{
		return eventSearchPageConverter;
	}

	/**
	 * @param eventSearchPageConverter
	 *           the eventSearchPageConverter to set
	 */
	public void setEventSearchPageConverter(
			final Converter<EventSearchPageData<SolrSearchQueryData, SearchResultValueData>, EventSearchPageData<SearchStateData, ITEM>> eventSearchPageConverter)
	{
		this.eventSearchPageConverter = eventSearchPageConverter;
	}

	/**
	 * @return the solrEventSearchService
	 */
	public SolrEventSearchService<SolrSearchQueryData, SearchResultValueData, EventSearchPageData<SolrSearchQueryData, SearchResultValueData>> getSolrEventSearchService()
	{
		return solrEventSearchService;
	}

	/**
	 * @param solrEventSearchService
	 *           the solrEventSearchService to set
	 */
	public void setSolrEventSearchService(
			final SolrEventSearchService<SolrSearchQueryData, SearchResultValueData, EventSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrEventSearchService)
	{
		this.solrEventSearchService = solrEventSearchService;
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
