/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.user.converters.populator.AddressReversePopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author 1129929
 *
 */
public class SiteOneAddressReversePopulator extends AddressReversePopulator
{
	@Override
	public void populate(final AddressData addressData, final AddressModel addressModel) throws ConversionException
	{
		super.populate(addressData, addressModel);
		addressModel.setProjectName(addressData.getProjectName());
		//addressModel.setDeliveryInstructions(addressData.getDeliveryInstructions());
		addressModel.setGeoCode(addressData.getGeoCode());
		addressModel.setDistrict(addressData.getDistrict());
		//		//Generating guid for new address.
		//		if (null == addressModel.getPk() || StringUtils.isEmpty(addressModel.getPk().toString()))
		//		{
		//			addressModel.setGuid(UUID.randomUUID().toString());
		//		}



	}
}
