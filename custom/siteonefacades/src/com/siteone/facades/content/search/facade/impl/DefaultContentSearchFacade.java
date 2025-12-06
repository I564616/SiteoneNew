/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 SAP SE or an SAP affiliate company.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 *
 *
 */
package com.siteone.facades.content.search.facade.impl;

import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.ProductSearchAutocompleteService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.AutocompleteSuggestion;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.siteone.contentsearch.ContentCategoryData;
import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.core.cms.service.SiteOneCMSPageService;
import com.siteone.core.content.search.service.SolrContentSearchService;
import com.siteone.facades.content.search.facade.ContentSearchFacade;


/**
 * @author 965504
 */
public class DefaultContentSearchFacade<ITEM extends ContentData> implements ContentSearchFacade<ITEM>
{
	private SolrContentSearchService<SolrSearchQueryData, SearchResultValueData, ContentSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrContentSearchService;
	private Converter<ContentSearchPageData<SolrSearchQueryData, SearchResultValueData>, ContentSearchPageData<SearchStateData, ITEM>> contentSearchPageConverter;
	private Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder;
	private Converter<AutocompleteSuggestion, AutocompleteSuggestionData> autocompleteSuggestionConverter;
	private ProductSearchAutocompleteService<AutocompleteSuggestion> autocompleteService;
	private ThreadContextService threadContextService;
	private SiteOneCMSPageService siteOneCMSPageService;


	protected Converter<SearchQueryData, SolrSearchQueryData> getSearchQueryDecoder()
	{
		return searchQueryDecoder;
	}

	public void setSearchQueryDecoder(final Converter<SearchQueryData, SolrSearchQueryData> searchQueryDecoder)
	{
		this.searchQueryDecoder = searchQueryDecoder;
	}

	protected Converter<AutocompleteSuggestion, AutocompleteSuggestionData> getAutocompleteSuggestionConverter()
	{
		return autocompleteSuggestionConverter;
	}

	public void setAutocompleteSuggestionConverter(
			final Converter<AutocompleteSuggestion, AutocompleteSuggestionData> autocompleteSuggestionConverter)
	{
		this.autocompleteSuggestionConverter = autocompleteSuggestionConverter;
	}

	protected ProductSearchAutocompleteService<AutocompleteSuggestion> getAutocompleteService()
	{
		return autocompleteService;
	}

	public void setAutocompleteService(final ProductSearchAutocompleteService<AutocompleteSuggestion> autocompleteService)
	{
		this.autocompleteService = autocompleteService;
	}

	protected ThreadContextService getThreadContextService()
	{
		return threadContextService;
	}

	public void setThreadContextService(final ThreadContextService threadContextService)
	{
		this.threadContextService = threadContextService;
	}


	@Override
	public ContentSearchPageData<SearchStateData, ITEM> textSearch(final String text)
	{
		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ContentSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ContentSearchPageData<SearchStateData, ITEM> execute()
					{
						return getContentSearchPageConverter().convert(getSolrContentSearchService().textSearch(text, null));
					}
				});
	}

	@Override
	public ContentSearchPageData<SearchStateData, ITEM> textSearch(final SearchStateData searchState,
			final PageableData pageableData)
	{
		Assert.notNull(searchState, "SearchStateData must not be null.");

		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ContentSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ContentSearchPageData<SearchStateData, ITEM> execute()
					{
						return getContentSearchPageConverter()
								.convert(getSolrContentSearchService().searchAgain(decodeState(searchState, null), pageableData));
					}
				});
	}

@Override
	public ContentSearchPageData<SearchStateData, ITEM> textSearch(final String text, final SearchQueryContext searchQueryContext)
	{
		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ContentSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ContentSearchPageData<SearchStateData, ITEM> execute()
					{
						return getContentSearchPageConverter()
								.convert(getSolrContentSearchService().textSearch(text, searchQueryContext, null));
					}
				});
	}

	@Override
	public ContentSearchPageData<SearchStateData, ITEM> articleContentSearch(final SearchStateData searchState,
			final PageableData pageableData)
	{
		Assert.notNull(searchState, "SearchStateData must not be null.");
		final ContentSearchPageData<SearchStateData, ITEM> searchPageData = getThreadContextService()
				.executeInContext(
				new ThreadContextService.Executor<ContentSearchPageData<SearchStateData, ITEM>, ThreadContextService.Nothing>()
				{
					@Override
					public ContentSearchPageData<SearchStateData, ITEM> execute()
					{
						return getContentSearchPageConverter()
								.convert(getSolrContentSearchService().searchAgain(decodeState(searchState, null), pageableData));
					}
				});

		if (searchPageData != null)
		{
			updateCategoryContentData(searchPageData);
		}

		return searchPageData;
	}
	@Override
	public List<AutocompleteSuggestionData> getAutocompleteSuggestions(final String input)
	{
		return getThreadContextService()
				.executeInContext(new ThreadContextService.Executor<List<AutocompleteSuggestionData>, ThreadContextService.Nothing>()
				{
					@Override
					public List<AutocompleteSuggestionData> execute()
					{
						return Converters.convertAll(getAutocompleteService().getAutocompleteSuggestions(input),
								getAutocompleteSuggestionConverter());
					}
				});

	}


	protected SolrSearchQueryData decodeState(final SearchStateData searchState, final String categoryCode)
	{
		final SolrSearchQueryData searchQueryData = getSearchQueryDecoder().convert(searchState.getQuery());
		if (categoryCode != null)
		{
			searchQueryData.setCategoryCode(categoryCode);
		}
		return searchQueryData;
	}


	private void updateCategoryContentData(final ContentSearchPageData<SearchStateData, ITEM> searchPageData)
	{
		if (CollectionUtils.isNotEmpty(searchPageData.getResults()))
		{
			final List<ContentCategoryData> contentCategoryDataList = new ArrayList<>();
			final Map<String, List<ContentData>> categoryMap = searchPageData.getResults().stream()
					.sorted(Comparator.comparing(ContentData::getModifiedTime).reversed())
					.filter(data -> StringUtils.isNotEmpty(data.getCategory()))
					.collect(Collectors.groupingBy(ContentData::getCategory));

			if (categoryMap != null && !categoryMap.isEmpty())
			{
				categoryMap.forEach((key, value) -> {
					final ContentCategoryData contentCategoryData = new ContentCategoryData();
					contentCategoryData.setCategory(key);
					if (CollectionUtils.isNotEmpty(value))
					{
						contentCategoryData.setName(value.stream().findFirst().get().getCategoryName());
					}
					contentCategoryData.setContentDataList(value);
					contentCategoryData.setProductCount(contentCategoryData.getContentDataList().size());
					contentCategoryDataList.add(contentCategoryData);
				});
			}
			searchPageData.setContentCategoryData(contentCategoryDataList);
		}
	}


	/**
	 * @return the contentSearchPageConverter
	 */
	public Converter<ContentSearchPageData<SolrSearchQueryData, SearchResultValueData>, ContentSearchPageData<SearchStateData, ITEM>> getContentSearchPageConverter()
	{
		return contentSearchPageConverter;
	}

	/**
	 * @param contentSearchPageConverter
	 *           the contentSearchPageConverter to set
	 */
	public void setContentSearchPageConverter(
			final Converter<ContentSearchPageData<SolrSearchQueryData, SearchResultValueData>, ContentSearchPageData<SearchStateData, ITEM>> contentSearchPageConverter)
	{
		this.contentSearchPageConverter = contentSearchPageConverter;
	}

	/**
	 * @return the solrContentSearchService
	 */
	public SolrContentSearchService<SolrSearchQueryData, SearchResultValueData, ContentSearchPageData<SolrSearchQueryData, SearchResultValueData>> getSolrContentSearchService()
	{
		return solrContentSearchService;
	}

	/**
	 * @param solrContentSearchService
	 *           the solrContentSearchService to set
	 */
	public void setSolrContentSearchService(
			final SolrContentSearchService<SolrSearchQueryData, SearchResultValueData, ContentSearchPageData<SolrSearchQueryData, SearchResultValueData>> solrContentSearchService)
	{
		this.solrContentSearchService = solrContentSearchService;
	}

}
