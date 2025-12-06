/**
 *
 */
package com.siteone.core.buyitagain.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import com.siteone.facade.BuyItAgainSearchPageData;


/**
 * @author SMondal
 *
 */
public interface SolrBuyItAgainSearchService<STATE, ITEM, RESULT extends BuyItAgainSearchPageData<STATE, ITEM>>
{
	RESULT searchAgain(STATE searchQueryData, PageableData pageableData);
}
