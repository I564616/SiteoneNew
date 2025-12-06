/**
 *
 */
package com.siteone.facades.buyitagain.search.populator;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;


/**
 * @author BS
 *
 */

public class BuyItAgainTextPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_SORT_TYPE> implements
		Populator<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest<FACET_SEARCH_CONFIG_TYPE, IndexedType, IndexedProperty, SearchQuery, INDEXED_TYPE_SORT_TYPE>>
{

	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FACET_SEARCH_CONFIG_TYPE, IndexedType, IndexedProperty, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
			throws ConversionException
	{
		if (null != source.getSearchQueryData().getFreeTextSearch() && !source.getSearchQueryData().getFreeTextSearch().isEmpty())
		{
			final String text = source.getSearchQueryData().getFreeTextSearch();
			target.getSearchQuery().addRawQuery("((soProductCode_string:(" + text + " OR *" + text
					+ "*)) OR (soProductDescription_string:(" + text + " OR *" + text + "*)))");
		}

	}
}
