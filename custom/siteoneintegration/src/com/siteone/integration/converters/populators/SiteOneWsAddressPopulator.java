
package com.siteone.integration.converters.populators;

import java.util.UUID;

import com.siteone.integration.address.data.SiteOneWsAddressRequestData;

import de.hybris.platform.converters.Populator;

import de.hybris.platform.core.model.user.AddressModel;



/**
 * Populates {@link GenderData} with name and code.
 */
public class SiteOneWsAddressPopulator implements Populator<AddressModel, SiteOneWsAddressRequestData>
{

	@Override
	public void populate(final AddressModel source , final  SiteOneWsAddressRequestData target) {
		
		// TODO Auto-generated method stub
	
		target.setStreetName(source.getStreetname());
		target.setStreetNumber(source.getStreetnumber());
		target.setCity(source.getTown());
		target.setState(source.getRegion().getIsocodeShort());
		target.setCounty(source.getDistrict());
		target.setPostalCode(source.getPostalcode());
		//Setting guid/addressId for update address
		if(null != source.getGuid()) {
			target.setAddressId(source.getGuid());
		}
		
		
		target.setCountry(source.getCountry().getIsocode());
		
		//Settng unique Id for each request.
		target.setCorrelationId(UUID.randomUUID().toString());
		
		
		

	}


}