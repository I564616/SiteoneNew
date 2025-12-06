package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.address.data.SiteOneWsAddressResponseData;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.user.AddressModel;

public interface SiteOneAddressWebService
{

	SiteOneWsAddressResponseData createOrUpdateAddress(AddressModel addressModel, B2BUnitModel unitModel,String operationType, boolean isNewBoomiEnv) throws ResourceAccessException;

	SiteOneWsAddressResponseData deleteAddress(String addressGuid,String unitGuid, boolean isNewBoomiEnv) throws ResourceAccessException;


}
