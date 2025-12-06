/**
 *
 */
package com.siteone.facade.populator;

import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchResponseSortsPopulator;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.IndexedTypeSort;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import java.util.ArrayList;
import java.util.List;

import com.siteone.facades.storesession.SiteOneStoreSessionFacade;


/**
 * @author 1124932
 *
 */
public class DefaultSiteOneSearchResponseSortsPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SEARCH_RESULT_TYPE, ITEM>
		extends
		SearchResponseSortsPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SEARCH_RESULT_TYPE, ITEM>
{
	private StoreSessionFacade storeSessionFacade;

	@Override
	protected List<SortData> buildSorts(
			final SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, IndexedTypeSort, SEARCH_RESULT_TYPE> source)
	{
		final String tabId = ((SiteOneStoreSessionFacade) storeSessionFacade).getSessionTabId();

		final List<SortData> result = new ArrayList<>();

		final IndexedType indexedType = source.getRequest().getSearchQuery().getIndexedType();

		if (null != indexedType && ((null != tabId
				&& (tabId.equalsIgnoreCase("content") && indexedType.getIdentifier().equals("siteoneContentSearchType")
						|| tabId.equalsIgnoreCase("product") && (indexedType.getIdentifier().equals("siteoneProductType")|| indexedType.getIdentifier().equals("siteoneCAProductType"))))
				|| null == tabId))
		{
			final IndexedTypeSort currentSort = source.getRequest().getCurrentSort();
			final String currentSortCode = currentSort != null ? currentSort.getCode() : null;

			final List<IndexedTypeSort> sorts = indexedType.getSorts();
			if (sorts != null && !sorts.isEmpty())
			{
				for (final IndexedTypeSort sort : sorts)
				{
					addSortData(result, currentSortCode, sort);
				}
			}
		}

		return result;
	}





	/**
	 * @return the storeSessionFacade
	 */
	public StoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final StoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}
}
