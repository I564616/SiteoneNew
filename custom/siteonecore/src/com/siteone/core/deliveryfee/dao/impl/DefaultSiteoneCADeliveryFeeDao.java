/**
 * 
 */
package com.siteone.core.deliveryfee.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import com.siteone.core.deliveryfee.dao.SiteoneCADeliveryFeeDao;
import com.siteone.core.model.SiteoneCADeliveryFeesModel;

/**
 * 
 */
public class DefaultSiteoneCADeliveryFeeDao extends AbstractItemDao implements SiteoneCADeliveryFeeDao
{
	private static final String DELIVERYFEE_QUERY = "Select {PK}  From {Siteonecadeliveryfees} where {branchid} = ?branchId and {uom} = (?uom) "
			+ "and {minQtyThreshold} <= (?qty) and {maxQtyThreshold} >= (?qty)";	

	@Override
	public  List<SiteoneCADeliveryFeesModel> getDeliveryFee(final String branchId, final String uom, final long qty)
	{
		FlexibleSearchQuery query = null;
		query = new FlexibleSearchQuery(DELIVERYFEE_QUERY);		
		query.addQueryParameter("branchId", branchId);
		query.addQueryParameter("uom", uom);
		query.addQueryParameter("qty", qty);
		final SearchResult<SiteoneCADeliveryFeesModel> result = getFlexibleSearchService().search(query);
		return result.getResult();
	}
}
