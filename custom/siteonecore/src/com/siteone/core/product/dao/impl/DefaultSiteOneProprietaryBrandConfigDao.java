/**
 *
 */
package com.siteone.core.product.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import jakarta.annotation.Resource;

import com.siteone.core.model.ProprietaryBrandConfigModel;
import com.siteone.core.product.dao.SiteOneProprietaryBrandConfigDao;


/**
 * @author 1091124
 *
 */
public class DefaultSiteOneProprietaryBrandConfigDao implements SiteOneProprietaryBrandConfigDao
{
	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<ProprietaryBrandConfigModel> findProprietaryBrandConfigByIndex(final String indexName)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"SELECT {PK} FROM {ProprietaryBrandConfig} " + "WHERE {indexName} = ?indexName " + "ORDER BY {seqNo}");
		query.addQueryParameter("indexName", indexName);
		final SearchResult<ProprietaryBrandConfigModel> result = getFlexibleSearchService().search(query);
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
