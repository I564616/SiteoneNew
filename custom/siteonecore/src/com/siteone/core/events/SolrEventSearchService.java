/**
 *
 */
package com.siteone.core.events;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import com.siteone.facade.EventSearchPageData;


/**
 * @author 1124932
 *
 */
public interface SolrEventSearchService<STATE, ITEM, RESULT extends EventSearchPageData<STATE, ITEM>>
{
	RESULT searchAgain(STATE searchQueryData, PageableData pageableData);
}
