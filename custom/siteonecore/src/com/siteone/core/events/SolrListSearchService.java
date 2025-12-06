/**
 *
 */
package com.siteone.core.events;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import com.siteone.facade.ListSearchPageData;


/**
 * @author SM04392
 *
 */
public interface SolrListSearchService<STATE, ITEM, RESULT extends ListSearchPageData<STATE, ITEM>>
{
	RESULT searchAgain(STATE searchQueryData, PageableData pageableData);
}
