package com.siteone.facades.store.populators;

import com.siteone.facade.NearbyStoreSearchPageData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;



/**
 * @author nmangal
 *
 */
public class NearbyStoreSearchResponseFreeTextSearchPopulator<STATE, ITEM>
        implements Populator<SolrSearchResponse, NearbyStoreSearchPageData<STATE, ITEM>>
{

    @Override
    public void populate(final SolrSearchResponse source, final NearbyStoreSearchPageData<STATE, ITEM> target) throws ConversionException
    {
        target.setFreeTextSearch(source.getRequest().getSearchText());

    }

}
