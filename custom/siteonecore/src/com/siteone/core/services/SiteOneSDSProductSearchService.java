/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;


/**
 * @author 1229803
 *
 */
public interface SiteOneSDSProductSearchService<STATE, ITEM, RESULT extends ProductSearchPageData<STATE, ITEM>>
{
	RESULT sdsSearchAgain(STATE searchQueryData, PageableData pageableData);
}
