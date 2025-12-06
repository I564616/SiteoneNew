/**
 *
 */
package com.siteone.facades.lists.populators;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.facade.ListSearchPageData;


/**
 * @author AA04994
 *
 */
public class ListSearchResponseFreeTextSearchPopulator<STATE, ITEM>
		implements Populator<SolrSearchResponse, ListSearchPageData<STATE, ITEM>>
{

	@Override
	public void populate(final SolrSearchResponse source, final ListSearchPageData<STATE, ITEM> target) throws ConversionException
	{
		target.setFreeTextSearch(source.getRequest().getSearchText());

	}

}
