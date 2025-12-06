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
package com.siteone.facades.content.search.facade;

import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;

import java.util.List;

import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;



/**
 * @author 965504
 *
 *         Content search facade interface. Used to retrieve contents of type {@link ContentData} (or subclasses of).
 *
 * @param <ITEM>
 *           The type of the content result items
 */
public interface ContentSearchFacade<ITEM extends ContentData>
{
	/**
	 * Initiate a new search using simple free text query.
	 *
	 * @param text
	 *           the search text
	 * @return the search results
	 */
	ContentSearchPageData<SearchStateData, ITEM> textSearch(String text);

	/**
	 * Refine an exiting search. The query object allows more complex queries using facet selection. The SearchStateData
	 * must have been obtained from the results of a call to {@link #textSearch(String)}.
	 *
	 * @param searchState
	 *           the search query object
	 * @param pageableData
	 *           the page to return
	 * @return the search results
	 */
	ContentSearchPageData<SearchStateData, ITEM> textSearch(SearchStateData searchState, PageableData pageableData);
	
	ContentSearchPageData<SearchStateData, ITEM> textSearch(String text, SearchQueryContext searchQueryContext);

	/**
	 * Get the auto complete suggestions for the provided input.
	 *
	 * @param input
	 *           the user's input
	 * @return a list of suggested search terms
	 */
	List<AutocompleteSuggestionData> getAutocompleteSuggestions(String input);

	/**
	 * Refine an exiting search. The query object allows more complex queries using facet selection. The SearchStateData
	 * must have been obtained from the results of a call to {@link #textSearch(String)}.
	 *
	 * @param searchState
	 *           the search query object
	 * @param pageableData
	 *           the page to return
	 * @return the search results
	 */
	ContentSearchPageData<SearchStateData, ITEM> articleContentSearch(SearchStateData searchState,
			PageableData pageableData);

}
