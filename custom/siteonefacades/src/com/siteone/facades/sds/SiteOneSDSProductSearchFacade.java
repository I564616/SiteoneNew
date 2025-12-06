/**
 *
 */
package com.siteone.facades.sds;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.List;

import com.siteone.core.model.SiteOneCuratedPLPHSProductComponentModel;


/**
 * @author 1229803
 *
 */
public interface SiteOneSDSProductSearchFacade<ITEM extends ProductData>
{
	ProductSearchPageData<SearchStateData, ITEM> sdsTextSearch(SearchStateData searchState, PageableData pageableData);

	ProductSearchPageData<SearchStateData, ITEM> curatedPLPSearch(SearchStateData searchState, PageableData pageableData,
			String code);
	
	List<ProductData> sortByItemNumber(final SiteOneCuratedPLPHSProductComponentModel component, 
			final ProductSearchPageData<SearchStateData, ProductData> searchResults);

}
