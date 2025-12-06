/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.PointOfServicePopulator;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.customer.SiteOneCustomerAccountService;


/**
 * @author 965504
 *
 *
 */
public class SiteOnePointOfServicePopulator extends PointOfServicePopulator
{

	private CustomerAccountService customerAccountService;

	@Override
	public void populate(final PointOfServiceModel source, final PointOfServiceData target)
	{
		final Date currentDate = new Date();
		super.populate(source, target);
		target.setStoreId(source.getStoreId());
		target.setIsActive(source.getIsActive());
		target.setPickupfullfillment(source.getPickupfullfillment());
		target.setDeliveryfullfillment(source.getDeliveryfullfillment());
		target.setShippingfullfillment(source.getShippingfullfillment());
		target.setRegionId(source.getRegionId());
		target.setIsPreferredStore(((SiteOneCustomerAccountService) getCustomerAccountService()).isPreferredStore(source));
		target.setIsInMyStore(((SiteOneCustomerAccountService) getCustomerAccountService()).isInMyStore(source));
		target.setTitle(source.getTitle());
		target.setSupportProductScanner(source.getSupportProductScanner());
		target.setExcludeBranches(source.getExcludeBranches());
		target.setCustomerRetailId(source.getCustomerRetailId());
		target.setEnableOnlineFulfillment(source.getEnableOnlineFulfillment() != null ? source.getEnableOnlineFulfillment() : Boolean.TRUE);
		if(source.getStoreSpecialities()!=null && !CollectionUtils.isEmpty(source.getStoreSpecialities()))
		{
			target.setStoreSpecialities(source.getStoreSpecialities());
		}
		if(source.getDeliveryOrShippingThreshold() != null && !source.getDeliveryOrShippingThreshold().isEmpty())
		{
			Map<String,String> thresholdMap = source.getDeliveryOrShippingThreshold();
			String deliveryThreshold = "";
			if(thresholdMap.containsKey("deliveryThresholdForHomeownerOrGuest"))
			{
				deliveryThreshold = thresholdMap.get("deliveryThresholdForHomeownerOrGuest");
				target.setDeliveryThresholdForHomeownerOrGuest(deliveryThreshold);
			}
			if(thresholdMap.containsKey("deliveryThresholdForContractor"))
			{
				deliveryThreshold = thresholdMap.get("deliveryThresholdForContractor");
				target.setDeliveryThresholdForContractor(deliveryThreshold);
			}
			if(thresholdMap.containsKey("shippingThresholdForHomeownerOrGuest"))
			{
				target.setShippingThresholdForHomeownerOrGuest(thresholdMap.get("shippingThresholdForHomeownerOrGuest"));
			}
			if(thresholdMap.containsKey("shippingThresholdForContractor"))
			{
				target.setShippingThresholdForContractor(thresholdMap.get("shippingThresholdForContractor"));
		 	}
			if(thresholdMap.containsKey("shippingFeeForHomeownerOrGuest"))
			{
				target.setShippingFeeForHomeownerOrGuest(thresholdMap.get("shippingFeeForHomeownerOrGuest"));
			}
			if(thresholdMap.containsKey("shippingFeeForContractor"))
			{
				target.setShippingFeeForContractor(thresholdMap.get("shippingFeeForContractor"));
         }
		}
		if (null != source.getDivision())
		{
			target.setStoreDivision(source.getDivision().getUid());
		}
		if (null != source.getNearbyStoreSearchRadius())
		{
			target.setNearbyStoreSearchRadius(source.getNearbyStoreSearchRadius());
		}
		if(source.getNurseryBuyingGroup() != null && target.getStoreSpecialities() != null && !target.getStoreSpecialities().stream().filter(speciality -> 
		speciality.equalsIgnoreCase("Nursery Center")).collect(Collectors.toList()).isEmpty())
		{
			target.setNurseryBuyingGroup(source.getNurseryBuyingGroup());
		}
		if (null != source.getLicenseEndDate())
		{
			if (currentDate.before(source.getLicenseEndDate()))
			{
				target.setIsLicensed(Boolean.TRUE);
			}
			else
			{
				target.setIsLicensed(Boolean.FALSE);
			}
		}
		else
		{
			target.setIsLicensed(Boolean.FALSE);
		}
		if (CollectionUtils.isNotEmpty(source.getShippingHubBranches()))
		{
			final List<String> hubStores = source.getShippingHubBranches().stream().map(hubStore -> hubStore.getStoreId())
					.collect(Collectors.toList());
			target.setHubStores(CollectionUtils.isNotEmpty(hubStores) ? hubStores : Collections.EMPTY_LIST);
		}
		if (CollectionUtils.isNotEmpty(source.getDistributedBranches()))
		{
			target.setDistributedBranches(source.getDistributedBranches().stream().map(dBranch -> dBranch.getStoreId())
					.collect(Collectors.toList()));
		}
		if (null != source.getSupplyChainNodeId())
		{
			target.setSupplyChainNodeId(source.getSupplyChainNodeId());
		}
		target.setAcquisitionOrCoBrandedBranch(source.getAcquisitionOrCoBrandedBranch());
		if (null != source.getBigBag()) {
			target.setBigBag(source.getBigBag());
		}
		if(null!=source.getArea())
		{
			target.setAreaId(source.getArea());
		}
	}

	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}


	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}

}
