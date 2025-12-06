package com.siteone.integration.services.ue;

import com.siteone.integration.addressverification.data.SiteOneWsAddressVerificationResponseData;

import de.hybris.platform.commercefacades.user.data.AddressData;

public interface SiteOneAddressVerificationWebService {

	public SiteOneWsAddressVerificationResponseData validateAddress(AddressData addressData);
}
