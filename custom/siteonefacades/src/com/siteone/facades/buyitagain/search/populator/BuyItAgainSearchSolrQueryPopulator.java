/**
 *
 */
package com.siteone.facades.buyitagain.search.populator;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
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
import de.hybris.platform.servicelayer.session.SessionService;
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

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.facades.customer.SiteOneB2BUnitFacade;


/**
 * @author SMondal
 *
 */
public class BuyItAgainSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		extends SearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
{
	private static final Logger LOG = Logger.getLogger(BuyItAgainSearchSolrQueryPopulator.class);
	private static final String SOLR_ORDERING_ACCOUNT_PARAM = "soOrderingAccount_string";
	private static final String SOLR_ISONLINEPRODUCT_PARAM = "soIsOnlineProduct_boolean";
	private static final String SOLR_PRODUCT_CODE_PARAM = "soProductCode_string";
	private static final String SOLR_CATEGORY_NAME_PARAM = "soCategoryName_string";
	private static final String SOLR_GROUPING = "group";
	private static final String SOLR_GROUPING_FIELD = "group.field";
	private static final String SOLR_GROUPING_LIMIT = "group.limit";
	private static final String SOLR_GROUPING_FACET = "group.facet";
	private static final String SOLR_GROUPING_MAIN = "group.main";
	private static final String SOLR_GROUPING_COUNT = "group.ngroups";


	protected B2BUnitFacade b2bUnitFacade;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
	{
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

		target.setIndexedType(getIndexedType(target.getFacetSearchConfig()));

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
		searchQuery.setLanguage(getCommonI18NService().getCurrentLanguage().getIsocode());
		searchQuery.setDefaultOperator(Operator.OR);
		final StringBuilder sb = new StringBuilder();
		sb.append(SOLR_ISONLINEPRODUCT_PARAM).append(":true");
		final B2BUnitData defaultB2BUnit = ((SiteOneB2BUnitFacade) b2bUnitFacade).getDefaultUnitForCurrentCustomer();
		final B2BUnitModel parent = ((SiteOneB2BUnitService) b2bUnitService).getParentUnitForCustomer();
		if (defaultB2BUnit != null)
		{
			if (defaultB2BUnit.getUid().contains("-"))
			{
				sb.append(" AND ").append(SOLR_ORDERING_ACCOUNT_PARAM).append(":\\/").append(parent.getUid()).append("\\/")
						.append(defaultB2BUnit.getUid()).append("*");
			}
			else
			{
				sb.append(" AND ").append(SOLR_ORDERING_ACCOUNT_PARAM).append(":\\/").append(defaultB2BUnit.getUid()).append("*");
			}
		}
		else
		{
			sb.append(" AND ").append(SOLR_ORDERING_ACCOUNT_PARAM).append(":\\/").append("NO_UNIT");
		}

		if (sessionService.getAttribute("RecommendedDetailsPage") != null
				&& sessionService.getAttribute("RecommendedDetailsPage") != "")
		{
			final String categoryName = sessionService.getAttribute("RecommendedDetailsPage");
			sb.append(" AND ").append(SOLR_CATEGORY_NAME_PARAM).append(":").append("\"").append(categoryName).append("\"");
			sessionService.setAttribute("RecommendedDetailsPage", "");
		}
		searchQuery.addFilterRawQuery(sb.toString());
		searchQuery.addRawParam(SOLR_GROUPING, "true");
		if (sessionService.getAttribute("RecommendedList") != null
				&& sessionService.getAttribute("RecommendedList") == "LandingPage")
		{
			searchQuery.addRawParam(SOLR_GROUPING_FIELD, SOLR_CATEGORY_NAME_PARAM);
			searchQuery.addRawParam(SOLR_GROUPING_COUNT, "true");
		}
		else
		{
			searchQuery.addRawParam(SOLR_GROUPING_FIELD, SOLR_PRODUCT_CODE_PARAM);
		}
		searchQuery.addRawParam(SOLR_GROUPING_LIMIT, "1");
		searchQuery.addRawParam(SOLR_GROUPING_FACET, "false");
		searchQuery.addRawParam(SOLR_GROUPING_MAIN, "true");
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
		return getFacetSearchConfigService().getConfiguration("buyItAgainSearchIndex");
	}

	/**
	 * @return the b2bUnitFacade
	 */
	public B2BUnitFacade getB2bUnitFacade()
	{
		return b2bUnitFacade;
	}

	/**
	 * @param b2bUnitFacade
	 *           the b2bUnitFacade to set
	 */
	public void setB2bUnitFacade(final B2BUnitFacade b2bUnitFacade)
	{
		this.b2bUnitFacade = b2bUnitFacade;
	}


}
