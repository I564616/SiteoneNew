/**
 *
 */
package com.siteone.core.deliveryfee.service;

import java.util.Set;


/**
 * @author SMondal
 *
 */
public interface SiteoneDeliveryFeeService
{
	public String getDeliveryFee(final String branchId, final boolean isProUser, final Set<String> lobList);
	
	public String getShippingFee(final String branchId, final Set<String> productSku);
	
	public boolean getifItemLevelShippingFeeApplicable(final String branchId, final Set<String> productSku);
}
