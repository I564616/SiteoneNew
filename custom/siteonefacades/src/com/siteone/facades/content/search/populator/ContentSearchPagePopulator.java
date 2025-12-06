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
package com.siteone.facades.content.search.populator;

import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.SpellingSuggestionData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.siteone.contentsearch.ContentData;
import com.siteone.contentsearch.ContentSearchPageData;



/**
 *
 * @author 965504
 */
public class ContentSearchPagePopulator<QUERY, STATE, RESULT, ITEM extends ContentData, SCAT, CATEGORY>
		implements Populator<ContentSearchPageData<QUERY, RESULT>, ContentSearchPageData<STATE, ITEM>>
{
	private Converter<QUERY, STATE> searchStateConverter;
	private Converter<BreadcrumbData<QUERY>, BreadcrumbData<STATE>> breadcrumbConverter;
	private Converter<FacetData<QUERY>, FacetData<STATE>> facetConverter;
	private Converter<SpellingSuggestionData<QUERY>, SpellingSuggestionData<STATE>> spellingSuggestionConverter;
	private Converter<RESULT, ITEM> searchResultContentConverter;
	private Converter<SCAT, CATEGORY> categoryConverter;


	protected Converter<QUERY, STATE> getSearchStateConverter()
	{
		return searchStateConverter;
	}

	public void setSearchStateConverter(final Converter<QUERY, STATE> searchStateConverter)
	{
		this.searchStateConverter = searchStateConverter;
	}

	protected Converter<BreadcrumbData<QUERY>, BreadcrumbData<STATE>> getBreadcrumbConverter()
	{
		return breadcrumbConverter;
	}

	public void setBreadcrumbConverter(final Converter<BreadcrumbData<QUERY>, BreadcrumbData<STATE>> breadcrumbConverter)
	{
		this.breadcrumbConverter = breadcrumbConverter;
	}

	protected Converter<FacetData<QUERY>, FacetData<STATE>> getFacetConverter()
	{
		return facetConverter;
	}

	public void setFacetConverter(final Converter<FacetData<QUERY>, FacetData<STATE>> facetConverter)
	{
		this.facetConverter = facetConverter;
	}

	protected Converter<SCAT, CATEGORY> getCategoryConverter()
	{
		return categoryConverter;
	}

	public void setCategoryConverter(final Converter<SCAT, CATEGORY> categoryConverter)
	{
		this.categoryConverter = categoryConverter;
	}

	protected Converter<SpellingSuggestionData<QUERY>, SpellingSuggestionData<STATE>> getSpellingSuggestionConverter()
	{
		return spellingSuggestionConverter;
	}

	public void setSpellingSuggestionConverter(
			final Converter<SpellingSuggestionData<QUERY>, SpellingSuggestionData<STATE>> spellingSuggestionConverter)
	{
		this.spellingSuggestionConverter = spellingSuggestionConverter;
	}

	@Override
	public void populate(final ContentSearchPageData<QUERY, RESULT> source, final ContentSearchPageData<STATE, ITEM> target)
	{
		target.setFreeTextSearch(source.getFreeTextSearch());
		target.setCategoryCode(source.getCategoryCode());

		if (source.getBreadcrumbs() != null)
		{
			target.setBreadcrumbs(Converters.convertAll(source.getBreadcrumbs(), getBreadcrumbConverter()));
		}

		target.setCurrentQuery(getSearchStateConverter().convert(source.getCurrentQuery()));

		if (source.getFacets() != null)
		{
			target.setFacets(Converters.convertAll(source.getFacets(), getFacetConverter()));
		}

		target.setPagination(source.getPagination());

		if (source.getResults() != null)
		{
			target.setResults(Converters.convertAll(source.getResults(), getSearchResultContentConverter()));
		}

		target.setSorts(source.getSorts());

		if (source.getSpellingSuggestion() != null)
		{
			target.setSpellingSuggestion(getSpellingSuggestionConverter().convert(source.getSpellingSuggestion()));
		}

		target.setKeywordRedirectUrl(source.getKeywordRedirectUrl());
	}

	/**
	 * @return the searchResultContentConverter
	 */
	public Converter<RESULT, ITEM> getSearchResultContentConverter()
	{
		return searchResultContentConverter;
	}

	/**
	 * @param searchResultContentConverter
	 *           the searchResultContentConverter to set
	 */
	public void setSearchResultContentConverter(final Converter<RESULT, ITEM> searchResultContentConverter)
	{
		this.searchResultContentConverter = searchResultContentConverter;
	}
}
