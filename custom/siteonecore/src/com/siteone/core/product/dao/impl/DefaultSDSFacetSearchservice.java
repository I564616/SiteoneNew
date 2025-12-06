/**
 *
 */
package com.siteone.core.product.dao.impl;

import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.FacetSearchStrategy;
import de.hybris.platform.solrfacetsearch.search.FacetSearchStrategyFactory;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;

import java.util.Collections;
import java.util.Map;


/**
 * @author 1229803
 *
 */
public class DefaultSDSFacetSearchservice
{
	private FacetSearchStrategyFactory facetSearchStrategyFactory;

	public SearchResult sdsSearch(final SearchQuery query) throws FacetSearchException
	{
		return this.search(query, Collections.emptyMap());
	}


	public SearchResult search(final SearchQuery query, final Map<String, String> searchHints) throws FacetSearchException
	{
		final FacetSearchConfig facetSearchConfig = query.getFacetSearchConfig();
		final IndexedType indexedType = query.getIndexedType();

		final FacetSearchStrategy facetSearchStrategy = this.getFacetSearchStrategy(facetSearchConfig, indexedType);

		return facetSearchStrategy.search(query, searchHints);
	}

	protected FacetSearchStrategy getFacetSearchStrategy(final FacetSearchConfig facetSearchConfig, final IndexedType indexedType)
	{
		return this.facetSearchStrategyFactory.createStrategy(facetSearchConfig, indexedType);
	}

	@Deprecated
	public String convertSearchQueryToString(final SearchQuery query) throws FacetSearchException
	{
		return "";
	}


	/**
	 * @return the facetSearchStrategyFactory
	 */
	public FacetSearchStrategyFactory getFacetSearchStrategyFactory()
	{
		return facetSearchStrategyFactory;
	}


	/**
	 * @param facetSearchStrategyFactory
	 *           the facetSearchStrategyFactory to set
	 */
	public void setFacetSearchStrategyFactory(final FacetSearchStrategyFactory facetSearchStrategyFactory)
	{
		this.facetSearchStrategyFactory = facetSearchStrategyFactory;
	}
}
