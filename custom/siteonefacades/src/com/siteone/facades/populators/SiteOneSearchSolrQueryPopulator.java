/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchSolrQueryPopulator;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import java.util.Arrays;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


public class SiteOneSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		extends SearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
{
	private static final String CURATED_PLP = "CuratedPLP";
	private static final String RECOMMENDED_PRODUCT = "RecommendedProduct";
	private StoreSessionFacade storeSessionFacade;
	private static final String SOLR_BASEPRODUCT_PARAM = "sobaseProductCode_string";
	private static final String SOLR_GROUPING = "group";
	private static final String SOLR_GROUPING_FIELD = "group.field";
	private static final String SOLR_GROUPING_LIMIT = "group.limit";
	private static final String SOLR_GROUPING_FACET = "group.facet";
	private static final String SOLR_GROUPING_MAIN = "group.main";
	private static final String SOLR_GROUPING_COUNT = "group.ngroups";

	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
	{
		super.populate(source, target);
		final String pageSize = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionPageSize();
		if (null != pageSize)
		{
			target.getSearchQuery().setPageSize(Integer.parseInt(pageSize));
		}
		getProductFromFilterQuery(source, target);
	}

	private void getProductFromFilterQuery(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
	{
		final SearchQuery searchQuery1;
		boolean curated = false;
		String filterQuery = "";
		String filterRawQuery = "";
		String groupingCount = "";
		final StringBuilder filterSkuRawQuery = new StringBuilder();
		String[] products = new String[] {};
		ListIterator<String> skus;
		boolean skuId = false;
		boolean plpVariant = false;

		if (null != source.getSearchQueryData().getFreeTextSearch()
				&& source.getSearchQueryData().getFreeTextSearch().startsWith("skuId="))
		{
			skuId = true;
		}
		if (null != source.getSearchQueryData().getCategoryCode()
				&& source.getSearchQueryData().getCategoryCode().equalsIgnoreCase(CURATED_PLP))
		{
			filterQuery = "soVariantItemNumber_string";
			curated = true;

		}
		else if (null != source.getSearchQueryData().getCategoryCode()
				&& source.getSearchQueryData().getCategoryCode().equalsIgnoreCase(RECOMMENDED_PRODUCT))
		{
			filterQuery = "socode_string";
			curated = true;
		}
		if (null != source.getSearchQueryData().getCategoryCode()
				&& source.getSearchQueryData().getCategoryCode().startsWith("VariantProduct,"))
		{
			groupingCount = source.getSearchQueryData().getCategoryCode().split(",")[1];
			plpVariant = true;
			filterSkuRawQuery.append("socode_string : (");
			skus = Arrays.asList(source.getSearchQueryData().getFreeTextSearch().split(",")).listIterator();

			while (skus.hasNext())
			{
				filterSkuRawQuery.append(skus.next());
				if (skus.hasNext())
				{
					filterSkuRawQuery.append(" OR ");
				}
			}
			filterSkuRawQuery.append(")");
			source.getSearchQueryData().setFreeTextSearch(null);
			source.getSearchQueryData().setCategoryCode(null);
		}
		else
		{
			filterRawQuery = "!soisVariantProduct_boolean:true";
		}
		if (curated)
		{
			products = source.getSearchQueryData().getFreeTextSearch().split(",");
			products = Arrays.stream(products).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
			source.getSearchQueryData().setFreeTextSearch(null);
			source.getSearchQueryData().setCategoryCode(null);
		}
		if (skuId)
		{
			skus = Arrays.asList((source.getSearchQueryData().getFreeTextSearch().replace("skuId=", "")).split(",")).listIterator();
			filterSkuRawQuery.append("!soisVariantProduct_boolean:true and soproductsSku_string_mv : (");
			while (skus.hasNext())
			{
				filterSkuRawQuery.append(skus.next());
				if (skus.hasNext())
				{
					filterSkuRawQuery.append(" OR ");
				}
			}
			filterSkuRawQuery.append(")");
			source.getSearchQueryData().setFreeTextSearch(null);
			source.getSearchQueryData().setCategoryCode(null);
		}
		if (target.getFacetSearchConfig().getSearchConfig().isLegacyMode())
		{
			searchQuery1 = createSearchQueryForLegacyMode(target.getFacetSearchConfig(), target.getIndexedType(),
					source.getSearchQueryData().getSearchQueryContext(), source.getSearchQueryData().getFreeTextSearch());
		}
		else
		{
			searchQuery1 = createSearchQuery(target.getFacetSearchConfig(), target.getIndexedType(),
					source.getSearchQueryData().getSearchQueryContext(), source.getSearchQueryData().getFreeTextSearch());
		}
		if (curated && StringUtils.isNotEmpty(filterQuery))
		{
			searchQuery1.addFilterQuery(filterQuery, SearchQuery.Operator.OR, products);
		}
		searchQuery1.setCatalogVersions(target.getCatalogVersions());
		if (StringUtils.isNotEmpty(filterRawQuery) && !skuId && !plpVariant && !curated)
		{
			searchQuery1.addFilterRawQuery(filterRawQuery);
		}
		if (skuId)
		{
			searchQuery1.addFilterRawQuery(filterSkuRawQuery.toString());
		}
		if (plpVariant)
		{
			searchQuery1.addFilterRawQuery(filterSkuRawQuery.toString());
			searchQuery1.addRawParam(SOLR_GROUPING, "true");
			searchQuery1.addRawParam(SOLR_GROUPING_FIELD, SOLR_BASEPRODUCT_PARAM);
			searchQuery1.addRawParam(SOLR_GROUPING_COUNT, "true");


			searchQuery1.addRawParam(SOLR_GROUPING_LIMIT, groupingCount);
			searchQuery1.addRawParam(SOLR_GROUPING_FACET, "false");
			searchQuery1.addRawParam(SOLR_GROUPING_MAIN, "true");
		}



		target.setSearchQuery(searchQuery1);

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
