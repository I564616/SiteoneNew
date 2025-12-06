/**
 * 
 */
package com.siteone.core.deliveryfee.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import org.apache.commons.collections4.CollectionUtils;
import com.siteone.core.deliveryfee.dao.SiteoneCADeliveryFeeDao;
import com.siteone.core.deliveryfee.service.SiteoneCADeliveryFeeService;
import com.siteone.core.model.SiteoneCADeliveryFeesModel;


/**
 * 
 */
public class DefaultSiteoneCADeliveryFeeService implements SiteoneCADeliveryFeeService
{

	private SiteoneCADeliveryFeeDao siteoneCADeliveryFeeDao;
	@Override
	public double getDeliveryFee(final String branchId, final String uom, final long qty)
	{
		List<SiteoneCADeliveryFeesModel> deliveryFeeList = new ArrayList<>();
		deliveryFeeList.addAll(getSiteoneCADeliveryFeeDao().getDeliveryFee(branchId, uom, qty));
		if(!CollectionUtils.isEmpty(deliveryFeeList))
		{
			deliveryFeeList.sort(Comparator.comparing(SiteoneCADeliveryFeesModel::getFee).reversed());
			return deliveryFeeList.get(0).getFee();
		}
		return 0.0d;
	}

	public SiteoneCADeliveryFeeDao getSiteoneCADeliveryFeeDao()
	{
		return siteoneCADeliveryFeeDao;
	}
	public void setSiteoneCADeliveryFeeDao(SiteoneCADeliveryFeeDao siteoneCADeliveryFeeDao)
	{
		this.siteoneCADeliveryFeeDao = siteoneCADeliveryFeeDao;
	}
}
