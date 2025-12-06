/**
 *
 */
package com.siteone.core.deliveryfee.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;
import java.util.Set;

import com.siteone.core.deliveryfee.dao.SiteoneDeliveryFeeDao;
import com.siteone.core.model.SiteoneDeliveryFeesModel;
import com.siteone.core.model.SiteoneShippingFeesModel;


/**
 * @author SMondal
 *
 */
public class DefaultSiteoneDeliveryFeeDao extends AbstractItemDao implements SiteoneDeliveryFeeDao
{
	private static final String DELIVERYFEE_QUERY = "Select {PK} From {Siteonedeliveryfees} where {branchid} = ?branchId and {lineofbusiness} in (?lobList)";	
	
	private static final String SHIPPINGFEE_QUERY = "Select {PK} From {SiteoneShippingFees} where {branchid} = ?branchId and {productSku} in (?productSku)";

	@Override
	public  List<SiteoneDeliveryFeesModel> getDeliveryFee(final String branchId, final boolean isProUser, final Set<String> lobList)
	{
		FlexibleSearchQuery query = null;
		query = new FlexibleSearchQuery(DELIVERYFEE_QUERY);		
		query.addQueryParameter("branchId", branchId);
		query.addQueryParameter("lobList", lobList);
		final SearchResult<SiteoneDeliveryFeesModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<SiteoneShippingFeesModel> getShippingFee(final String branchId, final Set<String> productSku)
	{
		FlexibleSearchQuery query = null;
		query = new FlexibleSearchQuery(SHIPPINGFEE_QUERY);		
		query.addQueryParameter("branchId", branchId);
		query.addQueryParameter("productSku", productSku);
		final SearchResult<SiteoneShippingFeesModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}

}
