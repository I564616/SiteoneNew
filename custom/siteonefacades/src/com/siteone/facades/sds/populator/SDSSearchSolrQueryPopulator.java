/**
 *
 */
package com.siteone.facades.sds.populator;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchSolrQueryPopulator;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.exceptions.NoValidSolrConfigException;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.exceptions.FacetConfigServiceException;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author 1229803
 *
 */
public class SDSSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		extends SearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
{
	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
	{
		// Setup the SolrSearchRequest
		target.setSearchQueryData(source.getSearchQueryData());
		target.setPageableData(source.getPageableData());

		final Collection<CatalogVersionModel> catalogVersions = getSessionProductCatalogVersions();
		if (catalogVersions == null || catalogVersions.isEmpty())
		{
			throw new ConversionException("Missing solr facet search indexed catalog versions");
		}

		target.setCatalogVersions(new ArrayList<CatalogVersionModel>(catalogVersions));

		try
		{
			target.setFacetSearchConfig(getFacetSearchConfig());
		}
		catch (final NoValidSolrConfigException e)
		{
			throw new ConversionException("No valid solrFacetSearchConfig found for the current context", e);
		}
		catch (final FacetConfigServiceException e)
		{
			throw new ConversionException(e.getMessage(), e);
		}

		// We can only search one core so select the indexed type
		target.setIndexedType(getIndexedType(target.getFacetSearchConfig()));

		// Create the solr search query for the config and type (this sets-up the default page size and sort order)
		SearchQuery searchQuery;

		if (target.getFacetSearchConfig().getSearchConfig().isLegacyMode())
		{
			searchQuery = createSearchQueryForLegacyMode(target.getFacetSearchConfig(), target.getIndexedType(),
					source.getSearchQueryData().getSearchQueryContext(), source.getSearchQueryData().getFreeTextSearch());
		}
		else
		{
			searchQuery = createSearchQuery(target.getFacetSearchConfig(), target.getIndexedType(),
					source.getSearchQueryData().getSearchQueryContext(), source.getSearchQueryData().getFreeTextSearch());
		}

		searchQuery.setCatalogVersions(target.getCatalogVersions());

		target.setSearchQuery(searchQuery);
	}
}


