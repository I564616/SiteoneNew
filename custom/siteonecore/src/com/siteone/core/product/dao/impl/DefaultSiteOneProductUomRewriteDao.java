/**
 *
 */
package com.siteone.core.product.dao.impl;

import com.siteone.core.model.UomRewriteConfigModel;
import com.siteone.core.product.dao.SiteOneProductDao;
import com.siteone.core.product.dao.SiteOneProductUomRewriteDao;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import jakarta.annotation.Resource;
import java.util.List;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProductUomRewriteDao implements SiteOneProductUomRewriteDao
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<UomRewriteConfigModel> findUomConfigByIndex(final String indexName)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {UomRewriteConfig} " +
				                                                  "WHERE {indexName} = ?indexName " +
				                                                  "ORDER BY {seqNo}" );
		query.addQueryParameter("indexName", indexName);
		final SearchResult<UomRewriteConfigModel> result = getFlexibleSearchService().search(query);
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
