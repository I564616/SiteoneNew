/**
 *
 */
package com.siteone.facades.events;

import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import com.siteone.facade.EventData;
import com.siteone.facade.EventSearchPageData;


/**
 * @author 1124932
 *
 */
public interface EventSearchFacade<ITEM extends EventData>
{
	EventSearchPageData<SearchStateData, ITEM> eventSearch(SearchStateData searchState, PageableData pageableData);
}
