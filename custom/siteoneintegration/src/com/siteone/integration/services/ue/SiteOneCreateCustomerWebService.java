package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;

public interface SiteOneCreateCustomerWebService {
	SiteOneWsCreateCustomerResponseData createCustomer(SiteoneRequestAccountModel siteOneRequestAccount, boolean isNewBoomiEnv) throws ResourceAccessException;
}