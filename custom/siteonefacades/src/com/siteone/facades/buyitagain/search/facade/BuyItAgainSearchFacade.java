/**
 *
 */
package com.siteone.facades.buyitagain.search.facade;

import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import com.siteone.facade.BuyItAgainData;
import com.siteone.facade.BuyItAgainSearchPageData;


/**
 * @author SMondal
 *
 */
public interface BuyItAgainSearchFacade<ITEM extends BuyItAgainData>
{
	public BuyItAgainSearchPageData<SearchStateData, ITEM> getBuyItAgainProducts(PageableData pageableData);

	BuyItAgainSearchPageData<SearchStateData, ITEM> buyItAgainSearch(SearchStateData searchState, PageableData pageableData);
}
