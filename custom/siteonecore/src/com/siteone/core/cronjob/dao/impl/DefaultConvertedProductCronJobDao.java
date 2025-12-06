package com.siteone.core.cronjob.dao.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.util.Config;

import java.util.List;

import com.siteone.core.cronjob.dao.ConvertedProductCronJobDao;


/**
 * @author SD02010
 */
public class DefaultConvertedProductCronJobDao implements ConvertedProductCronJobDao
{

	private FlexibleSearchService flexibleSearchService;


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


	@Override
	public List<ProductModel> getConvertedProducts()
	{
		final String queryString = Config.getString("import.converted.product.query", null);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		return getFlexibleSearchService().<ProductModel> search(query).getResult();
	}

}
