/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.converters.populators.B2BUnitPopulator;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.converters.Converters;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.util.SiteOneB2BUnitUtil;




/**
 * @author 1190626
 *
 */
public class SiteOneB2BUnitPopulator extends B2BUnitPopulator
{
	@Override
	public void populate(final B2BUnitModel source, final B2BUnitData target)
	{
		target.setName(source.getLocName());
		target.setUid(source.getUid());
		target.setModifiedTime(source.getModifiedtime());
		if (null != source.getBillingAddress())
		{
			target.setDefaultBillingAddress(getAddressConverter().convert(source.getBillingAddress()));
		}
		if (null != source.getShippingAddress())
		{
			target.setDefaultShippingAddress(getAddressConverter().convert(source.getShippingAddress()));
		}
		if (CollectionUtils.isNotEmpty(source.getAddresses()))
		{
			target.setAddresses(Converters.convertAll(source.getAddresses(), getAddressConverter()));
		}
		target.setIsPONumberRequired(source.getIsPONumberRequired());
		target.setPoRegex(source.getPoRegex());
		target.setIsBillingAccount(source.getIsBillingAccount());
		target.setIsOrderingAccount(source.getIsOrderingAccount());
		target.setDisplayId(SiteOneB2BUnitUtil.unitIdWithoutDivision(source.getUid()));
		target.setIsPunchOutAccount(source.getIsPunchOutAccount());
		target.setPayBillOnline(source.getPayBillOnline());
		target.setTradeClass(source.getTradeClass());
		
		if (null != source.getIsBillingAccount() && null != source.getIsOrderingAccount())
		{
			if (Boolean.TRUE.equals(source.getIsBillingAccount()))
			{
				target.setOrderType(Arrays.asList("Billing"));
			}
			if (Boolean.TRUE.equals(source.getIsOrderingAccount()))
			{
				target.setOrderType(Arrays.asList("Ordering"));
			}
			if (Boolean.TRUE.equals(source.getIsBillingAccount()) && Boolean.TRUE.equals(source.getIsOrderingAccount()))
			{
				target.setOrderType(Arrays.asList("Billing", "Ordering"));
			}
		}
		if (null != source.getPhoneNumber())
		{
			target.setPhoneNumber(source.getPhoneNumber());
		}
		if (null != source.getPhoneId())
		{
			target.setPhoneId(source.getPhoneId());
		}

		if (null != source.getReportingOrganization())
		{
			final B2BUnitData reportOrg = new B2BUnitData();
			reportOrg.setUid(source.getReportingOrganization().getUid());
			reportOrg.setDisplayId(SiteOneB2BUnitUtil.unitIdWithoutDivision(source.getReportingOrganization().getUid()));
			target.setReportingOrganization(reportOrg);			
		}
		if (null != source.getCustomerSegment())
		{
			target.setCustomerSegment(source.getCustomerSegment());
		}
		target.setExemptDeliveryFee(BooleanUtils.isTrue((getB2BUnitService().getRootUnit(source)).isExemptDeliveryFee()));
		target.setHomeBranch(source.getHomeBranch());
		if(!source.getShippingThresholdAndFee().isEmpty())
		{
			Map<String,String> thresholdAndFeeMap = source.getShippingThresholdAndFee();
			if(thresholdAndFeeMap.containsKey("shippingThreshold"))
			{
				target.setShippingThreshold(thresholdAndFeeMap.get("shippingThreshold"));
			}
			if(thresholdAndFeeMap.containsKey("shippingFee"))
			{
				target.setShippingFee(thresholdAndFeeMap.get("shippingFee"));
			}
		}
	}

}

