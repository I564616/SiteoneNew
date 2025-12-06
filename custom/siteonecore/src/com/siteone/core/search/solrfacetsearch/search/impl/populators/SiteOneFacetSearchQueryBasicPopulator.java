/**
 *
 */
package com.siteone.core.search.solrfacetsearch.search.impl.populators;

import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.FreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.search.Keyword;
import de.hybris.platform.solrfacetsearch.search.RawQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.impl.SearchQueryConverterData;
import de.hybris.platform.solrfacetsearch.search.impl.populators.FacetSearchQueryBasicPopulator;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.*;


/**
 * @author i849388
 *
 */
public class SiteOneFacetSearchQueryBasicPopulator extends FacetSearchQueryBasicPopulator
{

    private ConfigurationService configurationService;

    private static final String DEFAULT_DISMAX_FREE_TEXT_QUERY_BUILDER = "disMaxFreeTextQueryBuilder";
    private static final String SITEONE_DISMAX_FREE_TEXT_QUERY_BUILDER = "siteone.search.query.dismax.builder";

    @Override
    public void populate(final SearchQueryConverterData source, final SolrQuery target)
    {
        final SearchQuery searchQuery = source.getSearchQuery();

        final String parserName = this.configurationService.getConfiguration().getString(SITEONE_DISMAX_FREE_TEXT_QUERY_BUILDER,
                DEFAULT_DISMAX_FREE_TEXT_QUERY_BUILDER);

        IndexedType indexedType = searchQuery.getIndexedType();

        if (!parserName.equals(searchQuery.getFreeTextQueryBuilder()) && !"siteoneProductType".equalsIgnoreCase(indexedType.getIdentifier())
      		  && !"siteoneCAProductType".equalsIgnoreCase(indexedType.getIdentifier()))
        {
            super.populate(source, target);
        }
        else
        {
            if (searchQuery.getFreeTextQueryBuilder() == null)
                searchQuery.setFreeTextQueryBuilder("disMaxFreeTextQueryBuilder");

            if (!StringUtils.isNotEmpty(searchQuery.getUserQuery())) {
                searchQuery.setUserQuery("*");
                Keyword keyword = new Keyword("*");
                List<Keyword> keywordList = new ArrayList<>();
                keywordList.add(keyword);
                searchQuery.setKeywords(keywordList);
            }

            final FreeTextQueryBuilder freeTextQueryBuilder = getFreeTextQueryBuilderFactory().createQueryBuilder(searchQuery);
            final String freeTextQuery = freeTextQueryBuilder.buildQuery(searchQuery);


            final String[] arr = freeTextQuery.split("&");
            target.setQuery(arr[0]);

            for (int i = 1; i < arr.length; i++)
            {
                final String[] fields = arr[i].split("=");
                target.set(fields[0], fields[1]);
            }

            applyRawQueries(searchQuery, target);

            //target.setQuery(freeTextQuery);
            //target.setQuery(escape(freeTextQuery));

        }

    }

    private void applyRawQueries(final SearchQuery searchQuery, final SolrQuery target)
    {
        Set<String> rawQuerySet = new HashSet<>();
        for (RawQuery rawQuery : searchQuery.getRawQueries())
        {
            rawQuerySet.add(rawQuery.getQuery());
        }

        for (String rawQuery : rawQuerySet)
        {
            target.addFacetQuery(rawQuery);
        }
    }

    /**
     * @return the configurationService
     */
    public ConfigurationService getConfigurationService()
    {
        return configurationService;
    }

    /**
     * @param configurationService
     *           the configurationService to set
     */
    public void setConfigurationService(final ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }

}