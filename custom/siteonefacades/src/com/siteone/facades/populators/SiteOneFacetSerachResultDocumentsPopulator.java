/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.solrfacetsearch.search.impl.SearchResultConverterData;
import de.hybris.platform.solrfacetsearch.search.impl.SolrSearchResult;
import de.hybris.platform.solrfacetsearch.search.impl.populators.FacetSearchResultDocumentsPopulator;

import org.apache.solr.client.solrj.response.QueryResponse;


/**
 * @author 1124932
 *
 */
public class SiteOneFacetSerachResultDocumentsPopulator extends FacetSearchResultDocumentsPopulator
{
	@Override
	public void populate(final SearchResultConverterData source, final SolrSearchResult target)
	{
		super.populate(source, target);
		long numberOfResults = 0L;
		final QueryResponse queryResponse = source.getQueryResponse();
		numberOfResults = queryResponse.getResults().getNumFound();
		target.setNumberOfResults(numberOfResults);
	}
}

