/**
 *
 */
package com.siteone.core.recentscan.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.siteone.core.model.RecentScanProductsModel;
import com.siteone.core.recentscan.dao.SiteOneRecentScanDao;


/**
 * @author LR03818
 *
 */
public class DefaultSiteOneRecentScanDao extends AbstractItemDao implements SiteOneRecentScanDao
{

	@Override
	public List<String> getRecentScanProductsByUser(final String accountNumber)
	{

		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"Select {productCode} from {RecentScanProducts} where {accountNumber}=?accountNumber order by {scanDate} desc");
		query.addQueryParameter("accountNumber", accountNumber);
		query.setResultClassList(Collections.singletonList(String.class));
		final SearchResult<String> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public RecentScanProductsModel getExistingRecentScanProduct(final String accountNumber, final String productCode)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"Select {PK} from {RecentScanProducts} where {accountNumber}=?accountNumber and {productCode}=?productCode");
		final Map<String, Object> params = new HashMap<>();
		params.put("accountNumber", accountNumber);
		params.put("productCode", productCode);
		query.addQueryParameters(params);
		final SearchResult<RecentScanProductsModel> result = getFlexibleSearchService().search(query);
		return result.getCount() > 0 ? (RecentScanProductsModel) result.getResult().iterator().next() : null;
	}

}
