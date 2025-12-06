package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.customer.event.PendoEventRequest;
import com.siteone.integration.customer.data.SiteOneWsB2BCustomerResponseData;

import de.hybris.platform.b2b.model.B2BCustomerModel;

/**
 * Interface of contact webservice
 */
public interface SiteOneContactWebService 
{
	SiteOneWsB2BCustomerResponseData createB2BCustomer(B2BCustomerModel b2bCustomerModel, boolean isNewBoomiEnv) throws ResourceAccessException;
    SiteOneWsB2BCustomerResponseData updateB2BCustomer(B2BCustomerModel b2bCustomerModel, boolean isNewBoomiEnv) throws ResourceAccessException;
    
    SiteOneWsB2BCustomerResponseData disableOrEnableB2BCustomer(B2BCustomerModel b2bCustomerModel, boolean isNewBoomiEnv) throws ResourceAccessException;
    String generatePendoEvent(PendoEventRequest request);
    
}
