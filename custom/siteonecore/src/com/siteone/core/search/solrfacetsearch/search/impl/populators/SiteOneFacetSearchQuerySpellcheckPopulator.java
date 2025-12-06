package com.siteone.core.search.solrfacetsearch.search.impl.populators;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.impl.SearchQueryConverterData;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SiteOneFacetSearchQuerySpellcheckPopulator implements Populator<SearchQueryConverterData, SolrQuery> {
    public static final String SPELLCHECK_PARAM = "spellcheck";
    public static final String SPELLCHECK_QUERY_PARAM = "spellcheck.q";
    public static final String SPELLCHECK_DICTIONARY_PARAM = "spellcheck.dictionary";
    public static final String SPELLCHECK_COLLATE_PARAM = "spellcheck.collate";

    private List<String> additionalSpellDictionaries;

    public void populate(SearchQueryConverterData source, SolrQuery target) {
        SearchQuery searchQuery = source.getSearchQuery();
        if (searchQuery.isEnableSpellcheck() && StringUtils.isNotBlank(searchQuery.getUserQuery())) {
            target.add(SPELLCHECK_PARAM, "true");
            target.add(SPELLCHECK_DICTIONARY_PARAM, searchQuery.getLanguage());
            String appendedLanguage = "";
            if(searchQuery.getLanguage() != null && !searchQuery.getLanguage().equalsIgnoreCase("en")){

                appendedLanguage = "_"+searchQuery.getLanguage().toLowerCase();
            }
            if(this.additionalSpellDictionaries != null && !(this.additionalSpellDictionaries.isEmpty())) {
                for( String dictionary: this.additionalSpellDictionaries ) {
                    target.add(SPELLCHECK_DICTIONARY_PARAM, dictionary+appendedLanguage);
                }

            }
            target.add(SPELLCHECK_COLLATE_PARAM, Boolean.TRUE.toString());
        }

    }

    public List<String> getAdditionalSpellDictionaries() {
        return additionalSpellDictionaries;
    }

    public void setAdditionalSpellDictionaries(List<String> additionalSpellDictionaries) {
        this.additionalSpellDictionaries = additionalSpellDictionaries;
    }

}