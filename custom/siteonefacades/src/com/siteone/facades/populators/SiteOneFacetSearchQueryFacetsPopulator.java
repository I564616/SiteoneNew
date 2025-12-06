package com.siteone.facades.populators;

import de.hybris.platform.solrfacetsearch.search.impl.SearchQueryConverterData;
import de.hybris.platform.solrfacetsearch.search.impl.populators.FacetSearchQueryFacetsPopulator;

import org.apache.solr.client.solrj.SolrQuery;

/**
 *	Custom implementation of FacetSearchQueryFacetsPopulator to set facet limit from properties file
 * 	This bean can be removed once SAP fix bug ECP-3211 (https://github.com/SiteOneLandscapeSupply/hybris/pull/4153)
 */
public class SiteOneFacetSearchQueryFacetsPopulator extends FacetSearchQueryFacetsPopulator {

    private Integer defaultLimit = 5;

	 private String defaultFacetSort;

    public SiteOneFacetSearchQueryFacetsPopulator() {
    }

    @Override
    public void populate(final SearchQueryConverterData source, final SolrQuery target) {
        super.populate(source, target);

        target.setFacetLimit(defaultLimit);
    }

    public Integer getDefaultLimit() {
        return this.defaultLimit;
    }

    public void setDefaultLimit(final Integer defaultLimit) {
        if (defaultLimit == null) {
            this.defaultLimit = 5;
        } else {
            this.defaultLimit = defaultLimit;
        }

    }

	 public String getDefaultFacetSort()
	 {
		 return defaultFacetSort;
	 }

	 public void setDefaultFacetSort(final String defaultFacetSort)
	 {
		 this.defaultFacetSort = defaultFacetSort;
	 }
}
