/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.List;

import jakarta.annotation.Resource;



/**
 * @author 1190626
 *
 */
public class SiteOneGuestUserPopulator implements Populator<CustomerModel, CustomerData>
{

	@Resource(name = "defaultSiteOneAddressPopulator")
	private SiteOneAddressPopulator siteoneAddressPopulator;

	@Override
	public void populate(final CustomerModel source, final CustomerData target) throws ConversionException
	{
		ServicesUtil.validateParameterNotNull(source, "Parameter source cannot be null.");
		ServicesUtil.validateParameterNotNull(target, "Parameter target cannot be null.");

		target.setUid(source.getUid());
		target.setName(source.getName());

		if (source.getOriginalUid() != null)
		{
			target.setDisplayUid(source.getOriginalUid());
		}

		target.setEmail(getOriginalEmail(source.getUid()));
		final List<AddressModel> addressList = (List<AddressModel>) source.getAddresses();
		target.setContactNumber(addressList.get(0).getPhone1());
		final AddressData addressData = new AddressData();
		siteoneAddressPopulator.populate(addressList.get(0), addressData);
		target.setDefaultAddress(addressData);

	}

	/**
	 * @param displayUid
	 * @return
	 */
	public String getOriginalEmail(final String displayUid)
	{
		if (displayUid != null)
		{
			final String[] SplitUID = displayUid.split("\\|");
			final String originalEmailID = SplitUID[1];
			return originalEmailID;
		}
		else
		{
			return null;
		}
	}

}
