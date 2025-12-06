/**
 *
 */
package com.siteone.core.deliveryfee.dao;

import java.util.List;


import com.siteone.core.model.SiteoneCADeliveryFeesModel;


/**
 *
 */
public interface SiteoneCADeliveryFeeDao
{
	public List<SiteoneCADeliveryFeesModel> getDeliveryFee(final String branchId, final String uom,
			final long qty);
}
