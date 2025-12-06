/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.b2b.services.B2BUnitService;

import java.util.Arrays;

import org.apache.commons.lang3.BooleanUtils;

import com.siteone.core.util.SiteOneB2BUnitUtil;


/**
 * @author 1188173
 *
 */
public class SiteOneB2BUnitBasicPopulator implements Populator<B2BUnitModel, B2BUnitData>
{
	private Populator<MediaModel, ImageData> imagePopulator;
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2BUnitService;
	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final B2BUnitModel source, final B2BUnitData target) throws ConversionException
	{
		target.setName(source.getLocName());
		target.setUid(source.getUid());
		target.setDisplayId(SiteOneB2BUnitUtil.unitIdWithoutDivision(source.getUid()));
		target.setActive(source.getActive());
		target.setIsOrderingAccount(source.getIsOrderingAccount());
		target.setPayBillOnline(source.getPayBillOnline());
		target.setIsPONumberRequired(source.getIsPONumberRequired());
		target.setPoRegex(source.getPoRegex());
		target.setTradeClass(source.getTradeClass());
		
		if (null != source.getReportingOrganization())
		{
			final B2BUnitData reportOrg = new B2BUnitData();
			reportOrg.setUid(source.getReportingOrganization().getUid());
			reportOrg.setDisplayId(SiteOneB2BUnitUtil.unitIdWithoutDivision(source.getReportingOrganization().getUid()));
			target.setReportingOrganization(reportOrg);
		}


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
		if (null != source.getCustomerSegment())
		{
			target.setCustomerSegment(source.getCustomerSegment());
		}
		if(null != source.getCustomerLogo())
		{
			final ImageData customerLogo = new ImageData();
			getImagePopulator().populate(source.getCustomerLogo(), customerLogo);
			target.setCustomerLogo(customerLogo);
		}
		target.setExemptDeliveryFee(BooleanUtils.isTrue((getB2BUnitService().getRootUnit(source)).isExemptDeliveryFee()));
		
	}
	/**
	 * @return the imagePopulator
	 */
	public Populator<MediaModel, ImageData> getImagePopulator()
	{
		return imagePopulator;
	}
	/**
	 * @param imagePopulator the imagePopulator to set
	 */
	public void setImagePopulator(Populator<MediaModel, ImageData> imagePopulator)
	{
		this.imagePopulator = imagePopulator;
	}
	public B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2BUnitService()
	{
		return b2BUnitService;
	}
	public void setB2BUnitService(B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService)
	{
		b2BUnitService = b2bUnitService;
	}

}
