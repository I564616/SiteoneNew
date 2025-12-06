package com.siteone.facades.populators;


import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.siteone.facades.address.data.SiteOneAddressVerificationData;
import com.siteone.facades.address.data.SiteOneAddressVerificationStatusData;
import com.siteone.integration.addressverification.data.SiteOneWsAddressVerificationResponseData;


public class SiteOneWsAddressVerificationReversePopulator
		implements Populator<SiteOneWsAddressVerificationResponseData, SiteOneAddressVerificationData>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final SiteOneWsAddressVerificationResponseData source, final SiteOneAddressVerificationData target)
			throws ConversionException
	{

		target.setIsAddressValid(source.getIsAddressValid());
		target.setIsAddressCorrected(source.getIsAddressCorrected());
		target.setStreet(source.getStreet());
		target.setStreet2(source.getStreet2());
		target.setCity(source.getCity());
		target.setState(source.getState());
		target.setZipcode(source.getZipcode());
		target.setCounty(source.getCounty());

		if (null != source.getStatus())
		{
			final SiteOneAddressVerificationStatusData addressVerificationStatusData = new SiteOneAddressVerificationStatusData();
			addressVerificationStatusData.setCode(source.getStatus().getCode());
			addressVerificationStatusData.setDescription(source.getStatus().getDescription());
			addressVerificationStatusData.setInternalCode(source.getStatus().getInternalCode());
			target.setStatus(addressVerificationStatusData);
		}

	}
}