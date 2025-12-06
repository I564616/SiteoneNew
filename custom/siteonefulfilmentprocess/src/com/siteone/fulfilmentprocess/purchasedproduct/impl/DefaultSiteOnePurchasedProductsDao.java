package com.siteone.fulfilmentprocess.purchasedproduct.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.model.PurchasedProductModel;
import com.siteone.fulfilmentprocess.purchasedproduct.SiteOnePurchasedProductsDao;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOnePurchasedProductsDao extends AbstractItemDao implements SiteOnePurchasedProductsDao
{
	@Override
	public PurchasedProductModel getPurchasedProductForSku(final String code)
	{
		final String query = "SELECT {PK} FROM {PurchasedProduct} WHERE {productCode} = ?code";
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query);
		fQuery.addQueryParameter("code", code);
		final SearchResult<PurchasedProductModel> result = getFlexibleSearchService().<PurchasedProductModel> search(fQuery);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

}
