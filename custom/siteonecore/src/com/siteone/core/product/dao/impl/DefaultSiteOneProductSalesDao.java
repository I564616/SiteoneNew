/**
 *
 */
package com.siteone.core.product.dao.impl;

import com.siteone.core.model.ProductSalesInfoModel;
import com.siteone.core.product.dao.SiteOneProductSalesDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import jakarta.annotation.Resource;
import java.util.List;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductSalesDao extends AbstractItemDao implements SiteOneProductSalesDao
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	public List<ProductSalesInfoModel> findSalesByProductCode(final String code)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {ProductSalesInfo} WHERE {ProductCode} = ?code");
		query.addQueryParameter("code", code);
		final SearchResult<ProductSalesInfoModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

}
