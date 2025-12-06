/**
 *
 */
package com.siteone.facades.buyitagain.search.populator;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.facade.BuyItAgainSearchPageData;


/**
 * @author snavamani
 *
 */
public class BuyItAgainSearchResponseFreeTextSearchPopulator<STATE, ITEM>
		implements Populator<SolrSearchResponse, BuyItAgainSearchPageData<STATE, ITEM>>
{

	@Override
	public void populate(final SolrSearchResponse source, final BuyItAgainSearchPageData<STATE, ITEM> target)
			throws ConversionException
	{
		target.setFreeTextSearch(source.getRequest().getSearchText());

	}

}
