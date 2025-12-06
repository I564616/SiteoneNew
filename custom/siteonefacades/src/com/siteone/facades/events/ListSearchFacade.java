/**
 *
 */
package com.siteone.facades.events;

import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import com.siteone.facade.ListSearchPageData;
import com.siteone.facades.savedList.data.SavedListEntryData;


/**
 * @author SM04392
 *
 */
public interface ListSearchFacade<ITEM extends SavedListEntryData>
{
	ListSearchPageData<SearchStateData, ITEM> listSearch(SearchStateData searchState, PageableData pageableData);
}
