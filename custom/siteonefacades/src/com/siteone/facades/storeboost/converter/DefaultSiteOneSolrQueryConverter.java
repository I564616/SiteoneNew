/**
 *
 */
package com.siteone.facades.storeboost.converter;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider.FieldType;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.OrderField;
import de.hybris.platform.solrfacetsearch.search.QueryField;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.Operator;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.QueryParser;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchContext;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchListener;
import de.hybris.platform.solrfacetsearch.search.impl.DefaultSolrQueryConverter;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.siteone.facades.storesession.SiteOneStoreSessionFacade;




/**
 * @author 1229803
 *
 */
public class DefaultSiteOneSolrQueryConverter extends DefaultSolrQueryConverter
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneSolrQueryConverter.class);
	private SessionService sessionService;
	private StoreSessionFacade storeSessionFacade;

	@Override
	public SolrQuery convertSolrQuery(final SearchQuery searchQuery) throws FacetSearchException
	{
		this.checkQuery(searchQuery);

		final ArrayList queries = new ArrayList();
		final ArrayList boosts = new ArrayList();
		final ArrayList filterQueries = new ArrayList();
		List catalogVersionFilters = null;

		final Map facetInfoMap = this.getFacetInfo(searchQuery);

		if (!searchQuery.getFacetSearchConfig().getName().equals("eventSearchIndex"))
		{
			catalogVersionFilters = this.includeCatalogVersionFields(searchQuery);
		}

		this.splitQueryFields(this.prepareQueryFields(searchQuery), queries, filterQueries, facetInfoMap);

		final List boostFields = this.prepareBoostFields(searchQuery);
		final Iterator convertedCoupledQueryFields = boostFields.iterator();
		while (convertedCoupledQueryFields.hasNext())
		{
			final QueryField convertedQueryFields = (QueryField) convertedCoupledQueryFields.next();
			if (QueryParser.EDISMAX == searchQuery.getQueryParser())
			{
				boosts.add(convertedQueryFields);
			}
		}

		final String[] convertedQueryFields1 = this.convertQueryFields(queries, (Map) null);
		final String[] convertedCoupledQueryFields1 = this.convertCoupledQueryFields(searchQuery, searchQuery.getCoupledFields());
		final String[] convertedRawQueries = this.convertRawQueries(searchQuery, searchQuery.getRawQueries());
		LOG.info("raw Queries===" + searchQuery);
		LOG.info("raw Queries1===" + convertedRawQueries);
		final ArrayList combinedQueryFields = new ArrayList();
		combinedQueryFields.addAll(Arrays.asList(convertedQueryFields1));
		combinedQueryFields.addAll(Arrays.asList(convertedCoupledQueryFields1));
		combinedQueryFields.addAll(Arrays.asList(convertedRawQueries));

		searchQuery.addFilterQuery("soisProductDiscontinued", "false");
		final String query = this.buildQuery((String[]) combinedQueryFields.toArray(new String[combinedQueryFields.size()]),
				searchQuery);

		String updatedQuery = query;

		LOG.info("updatedQuery==" + updatedQuery);
		LOG.info("inside if" + ((String) getSessionService().getAttribute("allowedCategories")));
		if (null != ((String) getSessionService().getAttribute("allowedCategories")))
		{
			LOG.info("inside if");
			final String punchOutSpecificCat = ((String) getSessionService().getAttribute("allowedCategories"));

			LOG.info("punchOutSpecificCat==" + punchOutSpecificCat);

			if (query.equals("*:*"))
			{
				updatedQuery = "socategory_string_mv:(" + getPartQuery(punchOutSpecificCat) + ")";
			}
			else
			{
				updatedQuery = "(" + query + ") AND (socategory_string_mv:(" + getPartQuery(punchOutSpecificCat) + "))";
			}
		}

		LOG.info("updatedQuery==" + updatedQuery);

		final SolrQuery solrQuery = new SolrQuery(updatedQuery);

		final IndexedType indexedType = searchQuery.getIndexedType();
		if (indexedType.isGroup())
		{
			final IndexedProperty convertedQueryFilters = indexedType.getIndexedProperties().get(indexedType.getGroupFieldName());
			if (convertedQueryFilters == null)
			{
				throw new FacetSearchException("Grouping is enabled but no groupFieldName is configured in the indexed type");
			}

			final String convertedCatalogVersionFilters = this.getFieldNameTranslator().translate(searchQuery,
					convertedQueryFilters.getName(), FieldType.INDEX);

			solrQuery.add("group", new String[]
			{ "true" });
			solrQuery.add("group.field", new String[]
			{ convertedCatalogVersionFilters });
			solrQuery.add("group.limit", new String[]
			{ Integer.toString(indexedType.getGroupLimit()) });
			solrQuery.add("group.facet", new String[]
			{ Boolean.toString(indexedType.isGroupFacets()) });
			solrQuery.add("group.ngroups", new String[]
			{ "true" });
		}

		if (searchQuery.isEnableSpellcheck() && StringUtils.isNotBlank(searchQuery.getUserQuery()))
		{
			solrQuery.add("spellcheck", new String[]
			{ "true" });
			solrQuery.add("spellcheck.dictionary", new String[]
			{ searchQuery.getLanguage() });
			solrQuery.add("spellcheck.collate", new String[]
			{ Boolean.FALSE.toString() });
			solrQuery.add("spellcheck.q", new String[]
			{ searchQuery.getUserQuery() });
		}
		String[] convertedQueryFilters1;
		if (QueryParser.EDISMAX == searchQuery.getQueryParser())
		{

			convertedQueryFilters1 = this.convertQueryFields(boosts, (Map) null);
			solrQuery.add("defType", new String[]
			{ QueryParser.EDISMAX.getName() });
			solrQuery.add("bq", new String[]
			{ StringUtils.join(convertedQueryFilters1, Operator.OR.getName()) });
		}

		convertedQueryFilters1 = this.convertQueryFields(filterQueries, facetInfoMap);
		final String[] convertedCatalogVersionFilters1 = this.convertCoupledQueryFields(searchQuery, catalogVersionFilters);
		final String[] combinedFilterFields = (String[]) ArrayUtils.addAll(convertedQueryFilters1, convertedCatalogVersionFilters1);


		solrQuery.setFilterQueries(combinedFilterFields);
		final int start = searchQuery.getOffset() * searchQuery.getPageSize();
		final String tabId = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionTabId();
		if (null != tabId && tabId.equalsIgnoreCase("content")
				&& searchQuery.getFacetSearchConfig().getName().equals("siteoneIndex"))
		{
			solrQuery.setStart(Integer.valueOf(0));
			solrQuery.setRows(Integer.valueOf(0));
		}
		else if (null != tabId && tabId.equalsIgnoreCase("product")
				&& searchQuery.getFacetSearchConfig().getName().equals("contentSearchIndex"))
		{
			solrQuery.setStart(Integer.valueOf(0));
			solrQuery.setRows(Integer.valueOf(0));
		}
		else
		{
			solrQuery.setStart(Integer.valueOf(start));
			solrQuery.setRows(Integer.valueOf(searchQuery.getPageSize()));
			solrQuery.setFacet(true);
			this.addFacetFields(solrQuery, facetInfoMap);


			final Iterator arg19 = searchQuery.getSorts().iterator();
			while (arg19.hasNext())
			{
				final OrderField of = (OrderField) arg19.next();
				if ("score".equals(of.getField()))
				{
					solrQuery.addSort(of.getField(), of.isAscending() ? ORDER.asc : ORDER.desc);
				}
				else
				{
					solrQuery.addSort(this.getFieldNameTranslator().translate(searchQuery, of.getField(), FieldType.SORT),
							of.isAscending() ? ORDER.asc : ORDER.desc);
				}
			}

			solrQuery.setFacetMinCount(1);
			solrQuery.setFacetLimit(getDefaultLimit().intValue());

			solrQuery.setFacetSort(getFacetSort().getName());

			if (searchQuery.getRawParams().size() > 0)
			{

				this.addSolrParams(solrQuery, searchQuery);
			}
			solrQuery.addField("so*");
			solrQuery.addField("allCategories");

			if (null != ((PointOfServiceData) getSessionService().getAttribute("sessionStore")))
			{
				final String storeId = "*"
						+ ((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId().toLowerCase() + "*";
				solrQuery.addField(storeId);
			}
		}

		return this.applyPostProcessorsInOrder(solrQuery, searchQuery);
	}

	//@override
	protected List<QueryField> prepareBoostFields(final SearchQuery solrSearchQuery)
	{
		final String soavailableInStoreBoost = Config.getString("siteone.solr.boost.soavailableInStores.value", "");
		final String isSellableBoost = Config.getString("siteone.solr.boost.isSellable.value", "");
		final String soregulatoryStatesBoost = Config.getString("siteone.solr.boost.soregulatoryStates.value", "");
		final String soisRegulateditemBoost = Config.getString("siteone.solr.boost.soisRegulateditem.value", "");
		final List fields = solrSearchQuery.getBoostFields();
		final ArrayList convertedFields = new ArrayList();
		QueryField qf;
		String fieldName;
		String storeId = null;
		String regulatedStates = null;
		final PointOfServiceData pointOfService = ((PointOfServiceData) getSessionService().getAttribute("sessionStore"));
		if (!fields.isEmpty())
		{
			final Iterator itr = fields.iterator();
			while (itr.hasNext())
			{
				qf = (QueryField) itr.next();
				fieldName = qf.getField();
				if ("fulltext".equals(fieldName))
				{
					fieldName = fieldName + getForbiddenChar() + solrSearchQuery.getLanguage();
				}
				else
				{
					fieldName = this.getFieldNameTranslator().translate(solrSearchQuery, fieldName, FieldType.INDEX);
				}
				convertedFields.add(new QueryField(fieldName, qf.getOperator(), qf.getValues()));
			}
		}

		if (null != pointOfService)
		{
			storeId = pointOfService.getStoreId().toLowerCase();

			if (StringUtils.isNotEmpty(soavailableInStoreBoost))
			{
				convertedFields.add(new QueryField("soavailableInStores_string_mv", Operator.OR,
						new HashSet<String>(Arrays.asList(storeId + "^" + soavailableInStoreBoost))));
			}

			if (StringUtils.isNotEmpty(isSellableBoost))
			{
				convertedFields.add(new QueryField("isSellable_" + storeId + "_boolean", Operator.OR,
						new HashSet<String>(Arrays.asList("true^" + isSellableBoost))));
			}

			if (StringUtils.isNotEmpty(soregulatoryStatesBoost))
			{
				regulatedStates = pointOfService.getAddress().getRegion().getIsocodeShort();
				regulatedStates = "'" + regulatedStates + "'";
				convertedFields.add(new QueryField("soregulatoryStates_string_mv", Operator.OR,
						new HashSet<String>(Arrays.asList(regulatedStates + "^" + soregulatoryStatesBoost))));
			}

			if (StringUtils.isNotEmpty(soisRegulateditemBoost))
			{
				convertedFields.add(new QueryField("soisRegulateditem_boolean", Operator.OR,
						new HashSet<String>(Arrays.asList("false^" + soisRegulateditemBoost))));
			}
		}

		convertedFields
				.add(new QueryField("socategory_string_mv", Operator.AND, new HashSet<String>(Arrays.asList("SH1615" + "^97123"))));

		return convertedFields;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
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

	private String getPartQuery(final String categories)
	{
		final String cats[] = StringUtils.split(categories, ',');
		String partQuery = StringUtils.EMPTY;
		for (int index = 0; index < cats.length; index++)
		{
			final String punchOutSpecificCat = cats[index];

			if (index == 0)
			{
				partQuery = punchOutSpecificCat + "^100.0 OR " + punchOutSpecificCat + "*^50.0";
			}
			else
			{
				partQuery = partQuery + " OR " + punchOutSpecificCat + "^100.0 OR " + punchOutSpecificCat + "*^50.0";
			}

		}

		return partQuery;
	}

}
