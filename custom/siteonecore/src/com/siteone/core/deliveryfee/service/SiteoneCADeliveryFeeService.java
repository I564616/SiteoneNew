/**
 *
 */
package com.siteone.core.deliveryfee.service;


/**
 *
 */
public interface SiteoneCADeliveryFeeService
{
	public double getDeliveryFee(final String branchId, final String uom, final long qty);
}
