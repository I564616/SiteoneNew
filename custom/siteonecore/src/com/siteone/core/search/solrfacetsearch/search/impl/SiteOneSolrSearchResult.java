package com.siteone.core.search.solrfacetsearch.search.impl;

import de.hybris.platform.solrfacetsearch.search.impl.SolrSearchResult;
import org.apache.solr.client.solrj.response.SpellCheckResponse;

import java.util.Comparator;
import java.util.List;

public class SiteOneSolrSearchResult extends SolrSearchResult {
    public String getSpellingSuggestion() {
        String suggestion = null;
        if(this.getQueryResponse() != null && this.getQueryResponse().getSpellCheckResponse() != null) {
            if (this.getQueryResponse().getSpellCheckResponse().getCollatedResults() != null && this.getQueryResponse().getSpellCheckResponse().getCollatedResults().size() >= 1) {
                List<SpellCheckResponse.Collation> collations = this.getQueryResponse().getSpellCheckResponse().getCollatedResults();
                collations.sort((o1, o2) -> {
                    if (o1.getNumberOfHits() > o2.getNumberOfHits()) {
                        return -1;
                    } else if (o1.getNumberOfHits() < o2.getNumberOfHits()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });

                suggestion = collations.get(0).getCollationQueryString();
            }
        }

        return suggestion;

    }

}

