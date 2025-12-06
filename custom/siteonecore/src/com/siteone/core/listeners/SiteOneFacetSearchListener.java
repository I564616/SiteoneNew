package com.siteone.core.listeners;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.search.BoostField;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.Keyword;
import de.hybris.platform.solrfacetsearch.search.RawQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchContext;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchListener;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.model.AutoPhraseConfigModel;
import com.siteone.core.model.UomRewriteConfigModel;
import com.siteone.core.services.SiteOneAutoPhraseConfigService;
import com.siteone.core.services.SiteOneUomRewriteConfigService;


public class SiteOneFacetSearchListener implements FacetSearchListener
{
	private static final Logger LOG = Logger.getLogger(SiteOneFacetSearchListener.class);

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneUomRewriteConfigService")
	private SiteOneUomRewriteConfigService siteOneUomRewriteConfigService;

	@Resource(name = "siteOneAutoPhraseConfigService")
	private SiteOneAutoPhraseConfigService siteOneAutoPhraseConfigService;

	public SiteOneFacetSearchListener()
	{
	}

	public void beforeSearch(final FacetSearchContext facetSearchContext) throws FacetSearchException
	{
		final PointOfServiceData pointOfService = sessionService.getAttribute("sessionStore");
		if (null != getSessionService().getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES))
		{
			final List<PointOfServiceData> stores = getSessionService().getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
			final SearchQuery searchQuery = facetSearchContext.getSearchQuery();
			final Boolean inStockFilterStringValue = getSessionService().getAttribute(SiteoneCoreConstants.INSTOCK_FILTER_SELECTED);

			final List<UomRewriteConfigModel> uomRewriteConfigModels = siteOneUomRewriteConfigService
					.getUomRewriteConfigByIndex("siteoneIndex");
			final List<AutoPhraseConfigModel> autoPhraseConfigModels = siteOneAutoPhraseConfigService
					.getAutoPhraseConfigByIndex("siteoneIndex");
			String updatedUserQuery = searchQuery.getUserQuery();

			//Rewrite user query with correct UOM
			if (StringUtils.isNotEmpty(searchQuery.getUserQuery()))
			{
				//Rewrite UOMs in the query
				for (final UomRewriteConfigModel model : uomRewriteConfigModels)
				{
					final Pattern pattern = Pattern.compile(model.getRegex());
					final Matcher matcher = pattern.matcher(updatedUserQuery);
					if (matcher.find())
					{
						updatedUserQuery = updatedUserQuery.replaceAll(model.getRegex(), "\"$1 " + model.getReplacement() + "\"");
						break;
					}
				}

				//Rewrite search query with auto-phrasing
				for (final AutoPhraseConfigModel model : autoPhraseConfigModels)
				{
					updatedUserQuery = updatedUserQuery.replaceAll(model.getMatchPhrase(), "\"" + model.getMatchPhrase() + "\"");
				}

				/* Start */
				final Pattern pattern = Pattern.compile("[^a-z0-9 \\\"'-]", Pattern.CASE_INSENSITIVE);
				updatedUserQuery = pattern.matcher(updatedUserQuery).replaceAll(" ");
				/* End */

				searchQuery.setUserQuery(updatedUserQuery);

				final List<Keyword> keywordList = new ArrayList<>();

				final String words[] = updatedUserQuery.split(" ");
				for (final String token : words)
				{
					keywordList.add(new Keyword(token));
				}
				searchQuery.setKeywords(keywordList);
			}

			//Rewrite user query using automatic phrasing

			final List<PointOfServiceData> selectedNearbyStores = stores.stream()
					.filter(store -> store.getIsNearbyStoreSelected() != null && store.getIsNearbyStoreSelected())
					.collect(Collectors.toList());
			final List<String> hubStoresList = pointOfService.getHubStores();
			final String hubStore = CollectionUtils.isNotEmpty(hubStoresList) ? hubStoresList.get(0) : null;

			if (inStockFilterStringValue != null && inStockFilterStringValue)
			{

				if (null != pointOfService && pointOfService.getAddress() != null && pointOfService.getAddress().getRegion() != null)
				{
					final StringBuilder sb = new StringBuilder();
					final ListIterator<PointOfServiceData> listIterator = selectedNearbyStores.listIterator();

					while (listIterator.hasNext())
					{
						sb.append("soIsStockAvailable_" + listIterator.next().getStoreId() + "_boolean:true");
						if (listIterator.hasNext())
						{
							sb.append(" OR ");
						}
					}

					if (null != hubStore)
					{
						sb.append(" OR soIsStockAvailable_").append(hubStore).append("_boolean:true");
					}

					searchQuery.addFilterRawQuery(sb.toString());
				}

			}

			Boolean variantQuery = Boolean.FALSE;
			final List<RawQuery> rawQueries = searchQuery.getFilterRawQueries();
			for (final RawQuery rawQuery : rawQueries)
			{
				if (rawQuery.getQuery().contains("socode_string : ("))
				{
					variantQuery = Boolean.TRUE;
					break;
				}
			}

			if (!variantQuery)
			{
				final String displayAll = sessionService.getAttribute("displayAll");
				if (displayAll == null || !"on".equalsIgnoreCase(displayAll))
				{
					final StringBuilder query = new StringBuilder();
					final ListIterator<PointOfServiceData> listIterator = selectedNearbyStores.listIterator();
					final StringBuilder storeQuery = getQuery(listIterator, null);
					query.append(
							"soregionallyAssorted_boolean:false OR (soregionallyAssorted_boolean:true AND soavailableInStores_string_mv: (");
					query.append(storeQuery.toString().isEmpty() ? "\"\"" : storeQuery);
					query.append("))");
					if (hubStore != null)
					{
						query.append(" OR (soisShippable_boolean:true");
						query.append(" AND soIsStockAvailable_").append(hubStore).append("_boolean:true");
						query.append(")");
					}
					searchQuery.addFilterRawQuery(query.toString());
				}

				searchQuery.addFilterRawQuery("!sosecretSku_boolean:true");
			}

			final String isExpressShipping = sessionService.getAttribute("expressShipping");
			if (isExpressShipping != null && isExpressShipping == "true")
			{
				final StringBuilder sb = new StringBuilder();
				sb.append("soisShippable_boolean:true");

				if (hubStore != null)
				{
					sb.append(" AND soIsStockAvailable_").append(hubStore).append("_boolean:true");
				}

				searchQuery.addFilterRawQuery(sb.toString());

				sessionService.removeAttribute("expressShipping");
			}
			applyfacetquery(searchQuery, stores, hubStore);
			applyInventoryBoosts(searchQuery, stores);

			searchQuery.addFilterRawQuery("socode_string:*");

		}
	}

	private void applyInventoryBoosts(final SearchQuery searchQuery, final List<PointOfServiceData> stores)
	{
		final int limit = Math.min(5, stores.size());

		for (int i = 0; i < stores.size(); i++)
		{
			final String isStockAvailableBoost;
			//final String isSellableBoost;

			if (i < limit)
			{
				//apply boosts to home store (nearby 0) through nearby 5.
				isStockAvailableBoost = Config.getString("siteone.solr.boost.soIsStockAvailable.boost.nearby" + i, "2.75");
				//isSellableBoost = Config.getString("siteone.solr.boost.isSellable.boost.nearby" + i, "2.45");
			}
			else
			{
				//apply default boost to nearby store 6 and above.
				isStockAvailableBoost = Config.getString("siteone.solr.boost.soIsStockAvailable.boost.nearby.default", "2.65");
				//isSellableBoost = Config.getString("siteone.solr.boost.isSellable.boost.nearby.default", "2.35");
			}

			if (StringUtils.isNotEmpty(isStockAvailableBoost))
			{
				searchQuery.addBoost("soIsStockAvailable_" + stores.get(i).getStoreId() + "_boolean",
						SearchQuery.QueryOperator.EQUAL_TO, true, Float.valueOf(isStockAvailableBoost), BoostField.BoostType.ADDITIVE);
			}

			/*
			 * if (StringUtils.isNotEmpty(isSellableBoost)) { searchQuery.addBoost("isSellable_" +
			 * stores.get(i).getStoreId() + "_boolean", SearchQuery.QueryOperator.EQUAL_TO, true,
			 * Float.valueOf(isSellableBoost), BoostField.BoostType.ADDITIVE); }
			 */
		}
	}

	private void applyfacetquery(final SearchQuery searchQuery, final List<PointOfServiceData> stores, final String hubStore)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("{!tag=is1 key=InStock}soIsStockAvailableInStores_string_mv:(");

		final ListIterator<PointOfServiceData> listIterator = stores.listIterator();
		sb.append(getQuery(listIterator, hubStore));
		sb.append(")");
		searchQuery.addRawQuery(sb.toString());

		if (hubStore != null)
		{
			searchQuery.addRawQuery(
					"{!tag=is1 key=IsShippable}soisShippable_boolean:true AND soIsStockAvailableInStores_string_mv:" + hubStore);
		}
		LOG.info("SiteOne CA applyfacetquery value " + sb.toString());
	}

	public StringBuilder getQuery(final ListIterator<PointOfServiceData> listIterator, final String hubStore)
	{
		final StringBuilder sb = new StringBuilder();
		while (listIterator.hasNext())
		{
			sb.append(listIterator.next().getStoreId());
			if (listIterator.hasNext())
			{
				sb.append(" OR ");
			}
		}

		if (hubStore != null)
		{
			sb.append(" OR ").append(hubStore);
		}
		return sb;
	}

	public void afterSearch(final FacetSearchContext facetSearchContext) throws FacetSearchException
	{
	}

	public void afterSearchError(final FacetSearchContext facetSearchContext) throws FacetSearchException
	{
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
	 * @return the siteOneUomRewriteConfigService
	 */
	public SiteOneUomRewriteConfigService getSiteOneUomRewriteConfigService()
	{
		return siteOneUomRewriteConfigService;
	}

	/**
	 * @param siteOneUomRewriteConfigService
	 *           the siteOneUomRewriteConfigService to set
	 */
	public void setSiteOneUomRewriteConfigService(final SiteOneUomRewriteConfigService siteOneUomRewriteConfigService)
	{
		this.siteOneUomRewriteConfigService = siteOneUomRewriteConfigService;
	}
}
