/**
 *
 */
package com.siteone.core.deliveryfee.dao;

import java.util.List;
import java.util.Set;

import com.siteone.core.model.SiteoneDeliveryFeesModel;
import com.siteone.core.model.SiteoneShippingFeesModel;


/**
 * @author SMondal
 *
 */
public interface SiteoneDeliveryFeeDao
{
	public List<SiteoneDeliveryFeesModel> getDeliveryFee(final String branchId, final boolean isProUser, final Set<String> lobList);
	
	public List<SiteoneShippingFeesModel> getShippingFee(final String branchId, final Set<String> productSku);
	
}
