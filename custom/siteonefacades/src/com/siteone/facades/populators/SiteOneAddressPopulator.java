/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.commercefacades.user.converters.populator.AddressPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;


/**
 * @author 1129929
 *
 */
public class SiteOneAddressPopulator extends AddressPopulator
{

	@Override
	public void populate(final AddressModel source, final AddressData target)
	{
		super.populate(source, target);
		target.setProjectName(source.getProjectName());
		//target.setDeliveryInstructions(source.getDeliveryInstructions());
		target.setGeoCode(source.getGeoCode());
		
		if (null != source.getOwner() && source.getOwner() instanceof B2BUnitModel)
		{
			target.setUnitId(((B2BUnitModel) source.getOwner()).getUid());
		}
	}

}
