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

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.contents.ContentCatalogModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.SearchQueryTemplateNameResolver;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.SolrFacetSearchConfigSelectionStrategy;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.exceptions.NoValidSolrConfigException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfigService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.exceptions.FacetConfigServiceException;
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel;
import de.hybris.platform.solrfacetsearch.search.FacetSearchService;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.Operator;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 *
 * @author 965504
 *
 */
public class ContentSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE> implements
		Populator<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE>>
{
	private static final Logger LOG = Logger.getLogger(ContentSearchSolrQueryPopulator.class);

	private CommonI18NService commonI18NService;
	private BaseSiteService baseSiteService;
	private BaseStoreService baseStoreService;
	private CatalogVersionService catalogVersionService;
	private FacetSearchService facetSearchService;
	private FacetSearchConfigService facetSearchConfigService;
	private SolrFacetSearchConfigSelectionStrategy solrFacetSearchConfigSelectionStrategy;
	private SearchQueryTemplateNameResolver searchQueryTemplateNameResolver;
	private StoreSessionFacade storeSessionFacade;
	private static final String ARTICLE_PAGE = "ARTICLE_PAGE";
	private static final String ARTICLE_CA_PAGE = "ARTICLE_CA_PAGE";
	private static final String PROMOTION_PAGE = "PROMOTION_PAGE";
	private static final String SEO_ARTICLE_PAGE_TYPE_INDEX = "soContentType";

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

		searchQuery.setCatalogVersions(target.getCatalogVersions());
		searchQuery.setCurrency(getCommonI18NService().getCurrentCurrency().getIsocode());
		searchQuery.setLanguage(getCommonI18NService().getCurrentLanguage().getIsocode());
		searchQuery.setDefaultOperator(Operator.OR);
		// enable spell checker
		searchQuery.setEnableSpellcheck(true);
		target.setSearchQuery(searchQuery);
		final Set<String> input = new HashSet<>();
		input.add(ARTICLE_CA_PAGE);
		input.add(ARTICLE_PAGE);
		input.add(PROMOTION_PAGE);
		target.getSearchQuery().addFilterQuery(SEO_ARTICLE_PAGE_TYPE_INDEX, Operator.OR, input);
		final String pageSize = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionPageSize();
		if (null != pageSize)
		{
			target.getSearchQuery().setPageSize(Integer.parseInt(pageSize));
		}
	}

	protected SearchQuery createSearchQueryForLegacyMode(final FacetSearchConfig facetSearchConfig, final IndexedType indexedType,
			final SearchQueryContext searchQueryContext, final String freeTextSearch)
	{
		final SearchQuery searchQuery = new SearchQuery(facetSearchConfig, indexedType);
		searchQuery.setDefaultOperator(Operator.OR);
		searchQuery.setUserQuery(freeTextSearch);
		return searchQuery;
	}

	protected SearchQuery createSearchQuery(final FacetSearchConfig facetSearchConfig, final IndexedType indexedType,
			final SearchQueryContext searchQueryContext, final String freeTextSearch)
	{
		final String queryTemplateName = getSearchQueryTemplateNameResolver().resolveTemplateName(facetSearchConfig, indexedType,
				searchQueryContext);
		final SearchQuery searchQuery = facetSearchService.createFreeTextSearchQueryFromTemplate(facetSearchConfig, indexedType,
				queryTemplateName, freeTextSearch);
		return searchQuery;
	}

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
	protected FacetSearchConfig getFacetSearchConfig() throws NoValidSolrConfigException, FacetConfigServiceException
	{
		/*
		 * final SolrFacetSearchConfigModel solrFacetSearchConfigModel = solrFacetSearchConfigSelectionStrategy
		 * .getCurrentSolrFacetSearchConfig();
		 */
		return getFacetSearchConfigService().getConfiguration("contentSearchIndex");
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	/**
	 * @return the facetSearchService
	 */
	public FacetSearchService getFacetSearchService()
	{
		return facetSearchService;
	}

	/**
	 * @param facetSearchService
	 *           the facetSearchService to set
	 */
	public void setFacetSearchService(final FacetSearchService facetSearchService)
	{
		this.facetSearchService = facetSearchService;
	}

	/**
	 * @return the facetSearchConfigService
	 */
	public FacetSearchConfigService getFacetSearchConfigService()
	{
		return facetSearchConfigService;
	}

	/**
	 * @param facetSearchConfigService
	 *           the facetSearchConfigService to set
	 */
	public void setFacetSearchConfigService(final FacetSearchConfigService facetSearchConfigService)
	{
		this.facetSearchConfigService = facetSearchConfigService;
	}

	/**
	 * @return the solrFacetSearchConfigSelectionStrategy
	 */
	public SolrFacetSearchConfigSelectionStrategy getSolrFacetSearchConfigSelectionStrategy()
	{
		return solrFacetSearchConfigSelectionStrategy;
	}

	/**
	 * @param solrFacetSearchConfigSelectionStrategy
	 *           the solrFacetSearchConfigSelectionStrategy to set
	 */
	public void setSolrFacetSearchConfigSelectionStrategy(
			final SolrFacetSearchConfigSelectionStrategy solrFacetSearchConfigSelectionStrategy)
	{
		this.solrFacetSearchConfigSelectionStrategy = solrFacetSearchConfigSelectionStrategy;
	}

	/**
	 * @return the searchQueryTemplateNameResolver
	 */
	public SearchQueryTemplateNameResolver getSearchQueryTemplateNameResolver()
	{
		return searchQueryTemplateNameResolver;
	}

	/**
	 * @param searchQueryTemplateNameResolver
	 *           the searchQueryTemplateNameResolver to set
	 */
	public void setSearchQueryTemplateNameResolver(final SearchQueryTemplateNameResolver searchQueryTemplateNameResolver)
	{
		this.searchQueryTemplateNameResolver = searchQueryTemplateNameResolver;
	}

	/**
	 * @return the storeSessionFacade
	 */
	public StoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final StoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}


}
