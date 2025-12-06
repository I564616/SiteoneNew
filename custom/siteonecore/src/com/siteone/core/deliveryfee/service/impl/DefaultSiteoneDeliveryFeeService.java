/**
 * 
 */
package com.siteone.core.deliveryfee.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.siteone.core.deliveryfee.dao.SiteoneDeliveryFeeDao;
import com.siteone.core.deliveryfee.service.SiteoneDeliveryFeeService;
import com.siteone.core.model.SiteoneDeliveryFeesModel;
import com.siteone.core.model.SiteoneShippingFeesModel;

/**
 * @author SMondal
 *
 */
public class DefaultSiteoneDeliveryFeeService implements SiteoneDeliveryFeeService
{
	private SiteoneDeliveryFeeDao siteoneDeliveryFeeDao;
	@Override
	public String getDeliveryFee(String branchId, boolean isProUser, Set<String> lobList)
	{
		if(!CollectionUtils.isEmpty(lobList))
		{
			List<SiteoneDeliveryFeesModel> deliveryFeeList = new ArrayList<>();
			deliveryFeeList.addAll(getSiteoneDeliveryFeeDao().getDeliveryFee(branchId, isProUser, lobList));
			if(!CollectionUtils.isEmpty(deliveryFeeList))
			{
   			if(isProUser)
   			{
   				deliveryFeeList.sort(Comparator.comparing(SiteoneDeliveryFeesModel::getProFee).reversed());
   				return String.valueOf(deliveryFeeList.get(0).getProFee());
   			}
   			else
   			{
   				deliveryFeeList.sort(Comparator.comparing(SiteoneDeliveryFeesModel::getRetailFee).reversed());
   				return String.valueOf(deliveryFeeList.get(0).getRetailFee());
   			}
			}
		}
		return null;
	}
	
	@Override
	public String getShippingFee(String branchId, Set<String> productSku)
	{
		if(!CollectionUtils.isEmpty(productSku) && !StringUtils.isEmpty(branchId))
		{
			List<SiteoneShippingFeesModel> shippingFeeList = new ArrayList();
			shippingFeeList.addAll(getSiteoneDeliveryFeeDao().getShippingFee(branchId, productSku));
			if(!CollectionUtils.isEmpty(shippingFeeList))
			{
				shippingFeeList.sort(Comparator.comparing(SiteoneShippingFeesModel::getShippingFee).reversed());
				double shippingFee = shippingFeeList.get(0).getShippingFee();
				DecimalFormat df = new DecimalFormat("0.00");
				return df.format(shippingFee);
			}	
		}
		return null;
	}
	
	@Override
	public boolean getifItemLevelShippingFeeApplicable(final String branchId, final Set<String> productSku)
	{
		if(!CollectionUtils.isEmpty(productSku) && !StringUtils.isEmpty(branchId))
		{
			List<SiteoneShippingFeesModel> shippingFeeList = new ArrayList();
			shippingFeeList.addAll(getSiteoneDeliveryFeeDao().getShippingFee(branchId, productSku));
			if(!CollectionUtils.isEmpty(shippingFeeList) && shippingFeeList.size() == productSku.size())
			{
				return true;
			}	
		}
		return false;
	}
	
	public SiteoneDeliveryFeeDao getSiteoneDeliveryFeeDao()
	{
		return siteoneDeliveryFeeDao;
	}
	public void setSiteoneDeliveryFeeDao(SiteoneDeliveryFeeDao siteoneDeliveryFeeDao)
	{
		this.siteoneDeliveryFeeDao = siteoneDeliveryFeeDao;
	}
	

	
	
}
