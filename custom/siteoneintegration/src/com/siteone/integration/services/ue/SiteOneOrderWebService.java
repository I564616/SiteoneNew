package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.submitOrder.data.SiteOneWsSubmitOrderResponseData;

import de.hybris.platform.ordersplitting.model.ConsignmentModel;

public interface SiteOneOrderWebService {
		
	SiteOneWsSubmitOrderResponseData submitOrder(ConsignmentModel consignmentModel, boolean isNewBoomiEnv) throws ResourceAccessException;
	
}
