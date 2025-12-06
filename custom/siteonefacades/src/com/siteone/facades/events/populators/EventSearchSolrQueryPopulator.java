/**
 *
 */
package com.siteone.facades.events.populators;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.contents.ContentCatalogModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchSolrQueryPopulator;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.exceptions.NoValidSolrConfigException;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.exceptions.FacetConfigServiceException;
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.Operator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author 1124932
 *
 */
public class EventSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		extends SearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
{
	private static final Logger LOG = Logger.getLogger(EventSearchSolrQueryPopulator.class);

	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
	{
		// Setup the SolrSearchRequest
		target.setSearchQueryData(source.getSearchQueryData());
		target.setPageableData(source.getPageableData());

		final Collection<CatalogVersionModel> catalogVersions = getContentCatalogVersions();
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
			LOG.error("No valid solrFacetSearchConfig found for the current context", e);
			throw new ConversionException("No valid solrFacetSearchConfig found for the current context", e);
		}
		catch (final FacetConfigServiceException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ConversionException(e.getMessage(), e);
		}

		// We can only search one core so select the indexed type
		target.setIndexedType(getIndexedType(target.getFacetSearchConfig()));

		// Create the solr search query for the config and type (this sets-up the default page size and sort order)
		/*
		 * final SearchQuery searchQuery = createSearchQuery(target.getFacetSearchConfig(), target.getIndexedType(),
		 * source.getSearchQueryData().getSearchQueryContext(), source.getSearchQueryData().getFreeTextSearch());
		 */
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
		String filterquery = "sodivision_string";
		if (getBaseSiteService().getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us")) {
			searchQuery.addFilterQuery(filterquery, SearchQuery.Operator.AND, "US");
		}
		if (getBaseSiteService().getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca")) {
			searchQuery.addFilterQuery(filterquery, SearchQuery.Operator.AND, "CA");
		}
		searchQuery.setCatalogVersions(target.getCatalogVersions());
		searchQuery.setLanguage(getCommonI18NService().getCurrentLanguage().getIsocode());
		searchQuery.setDefaultOperator(Operator.OR);
		target.setSearchQuery(searchQuery);

	}

	@Override
	protected SearchQuery createSearchQueryForLegacyMode(final FacetSearchConfig facetSearchConfig, final IndexedType indexedType,
			final SearchQueryContext searchQueryContext, final String freeTextSearch)
	{
		final SearchQuery searchQuery = new SearchQuery(facetSearchConfig, indexedType);
		searchQuery.setDefaultOperator(Operator.OR);
		searchQuery.setUserQuery(freeTextSearch);
		return searchQuery;
	}

	@Override
	protected SearchQuery createSearchQuery(final FacetSearchConfig facetSearchConfig, final IndexedType indexedType,
			final SearchQueryContext searchQueryContext, final String freeTextSearch)
	{
		final String queryTemplateName = getSearchQueryTemplateNameResolver().resolveTemplateName(facetSearchConfig, indexedType,
				searchQueryContext);
		final SearchQuery searchQuery = getFacetSearchService().createFreeTextSearchQueryFromTemplate(facetSearchConfig,
				indexedType, queryTemplateName, freeTextSearch);
		return searchQuery;
	}

	@Override
	protected IndexedType getIndexedType(final FacetSearchConfig config)
	{
		final IndexConfig indexConfig = config.getIndexConfig();
		// Strategy for working out which of the available indexed types to use
		final Collection<IndexedType> indexedTypes = indexConfig.getIndexedTypes().values();
		if (indexedTypes != null && !indexedTypes.isEmpty())
		{
			// When there are multiple - select the first
			return indexedTypes.iterator().next();
		}
		// No indexed types
		return null;
	}

	/**
	 * Get all the session catalog versions that belong to product catalogs of the current site.
	 *
	 * @return the list of session catalog versions
	 */
	protected Collection<CatalogVersionModel> getContentCatalogVersions()
	{
		final CMSSiteModel currentSite = (CMSSiteModel) getBaseSiteService().getCurrentBaseSite();

		final List<ContentCatalogModel> contentCatalogs = currentSite.getContentCatalogs();

		final Collection<CatalogVersionModel> sessionCatalogVersions = getCatalogVersionService().getSessionCatalogVersions();

		final Collection<CatalogVersionModel> result = new ArrayList<CatalogVersionModel>();

		for (final CatalogVersionModel sessionCatalogVersion : sessionCatalogVersions)
		{
			if (contentCatalogs.contains(sessionCatalogVersion.getCatalog()))
			{
				result.add(sessionCatalogVersion);
			}
		}
		return result;
	}

	/**
	 * Resolves suitable {@link FacetSearchConfig} for the query based on the configured strategy bean.<br>
	 *
	 * @return {@link FacetSearchConfig} that is converted from {@link SolrFacetSearchConfigModel}
	 * @throws NoValidSolrConfigException
	 *            , FacetConfigServiceException
	 */
	@Override
	protected FacetSearchConfig getFacetSearchConfig() throws NoValidSolrConfigException, FacetConfigServiceException
	{
		return getFacetSearchConfigService().getConfiguration("eventSearchIndex");
	}



}
