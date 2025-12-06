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
package com.siteone.core.content.search.service.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryTermData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import com.siteone.contentsearch.ContentSearchPageData;
import com.siteone.core.content.search.service.SolrContentSearchService;


/**
 *
 * @author 965504
 *
 *         Default implementation of the ContentSearchService
 */
public class DefaultSolrContentSearchService<ITEM>
		implements SolrContentSearchService<SolrSearchQueryData, ITEM, ContentSearchPageData<SolrSearchQueryData, ITEM>>
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(DefaultSolrContentSearchService.class);

	private Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> contentSearchQueryPageableConverter;
	private Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;
	private Converter<SolrSearchResponse, ContentSearchPageData<SolrSearchQueryData, ITEM>> contentSearchResponseConverter;

	protected Converter<SolrSearchRequest, SolrSearchResponse> getSearchRequestConverter()
	{
		return searchRequestConverter;
	}

	public void setSearchRequestConverter(final Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter)
	{
		this.searchRequestConverter = searchRequestConverter;
	}

	// End spring inject methods

	@Override
	public ContentSearchPageData<SolrSearchQueryData, ITEM> textSearch(final String text, final PageableData pageableData)
	{
		final SolrSearchQueryData searchQueryData = createSearchQueryData();
		searchQueryData.setFreeTextSearch(text);
		searchQueryData.setFilterTerms(Collections.<SolrSearchQueryTermData> emptyList());

		return doSearch(searchQueryData, pageableData);
	}

	@Override
	public ContentSearchPageData<SolrSearchQueryData, ITEM> textSearch(final String text,
			final SearchQueryContext searchQueryContext, final PageableData pageableData)
	{
		final SolrSearchQueryData searchQueryData = createSearchQueryData();
		searchQueryData.setFreeTextSearch(text);
		searchQueryData.setFilterTerms(Collections.<SolrSearchQueryTermData> emptyList());
		searchQueryData.setSearchQueryContext(searchQueryContext);

		return doSearch(searchQueryData, pageableData);
	}


	@Override
	public ContentSearchPageData<SolrSearchQueryData, ITEM> searchAgain(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		return doSearch(searchQueryData, pageableData);
	}

	protected ContentSearchPageData<SolrSearchQueryData, ITEM> doSearch(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		validateParameterNotNull(searchQueryData, "SearchQueryData cannot be null");
		// Create the SearchQueryPageableData that contains our parameters
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(searchQueryData,
				pageableData);
		// Build up the search request
		final SolrSearchRequest solrSearchRequest = getContentSearchQueryPageableConverter().convert(searchQueryPageableData);
		// Execute the search
		final SolrSearchResponse solrSearchResponse = getSearchRequestConverter().convert(solrSearchRequest);
		// Convert the response
		return getContentSearchResponseConverter().convert(solrSearchResponse);
	}

	protected SearchQueryPageableData<SolrSearchQueryData> buildSearchQueryPageableData(final SolrSearchQueryData searchQueryData,
			final PageableData pageableData)
	{
		final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = createSearchQueryPageableData();
		searchQueryPageableData.setSearchQueryData(searchQueryData);
		searchQueryPageableData.setPageableData(pageableData);
		return searchQueryPageableData;
	}

	// Create methods for data object - can be overridden in spring config

	protected SearchQueryPageableData<SolrSearchQueryData> createSearchQueryPageableData()
	{
		return new SearchQueryPageableData<SolrSearchQueryData>();
	}

	protected SolrSearchQueryData createSearchQueryData()
	{
		return new SolrSearchQueryData();
	}

	/**
	 * @return the contentSearchQueryPageableConverter
	 */
	public Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> getContentSearchQueryPageableConverter()
	{
		return contentSearchQueryPageableConverter;
	}

	/**
	 * @param contentSearchQueryPageableConverter
	 *           the contentSearchQueryPageableConverter to set
	 */
	public void setContentSearchQueryPageableConverter(
			final Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> contentSearchQueryPageableConverter)
	{
		this.contentSearchQueryPageableConverter = contentSearchQueryPageableConverter;
	}

	/**
	 * @return the contentSearchResponseConverter
	 */
	public Converter<SolrSearchResponse, ContentSearchPageData<SolrSearchQueryData, ITEM>> getContentSearchResponseConverter()
	{
		return contentSearchResponseConverter;
	}

	/**
	 * @param contentSearchResponseConverter
	 *           the contentSearchResponseConverter to set
	 */
	public void setContentSearchResponseConverter(
			final Converter<SolrSearchResponse, ContentSearchPageData<SolrSearchQueryData, ITEM>> contentSearchResponseConverter)
	{
		this.contentSearchResponseConverter = contentSearchResponseConverter;
	}
}
