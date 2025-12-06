/**
 *
 */
package com.siteone.facades.events.populators;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.facade.EventSearchPageData;


/**
 * @author 1124932
 *
 */
public class EventSearchResponseFreeTextSearchPopulator<STATE, ITEM>
		implements Populator<SolrSearchResponse, EventSearchPageData<STATE, ITEM>>
{

	@Override
	public void populate(final SolrSearchResponse source, final EventSearchPageData<STATE, ITEM> target) throws ConversionException
	{
		target.setFreeTextSearch(source.getRequest().getSearchText());

	}

}
