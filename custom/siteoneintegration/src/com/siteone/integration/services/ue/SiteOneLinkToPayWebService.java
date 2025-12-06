package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.integration.linktopay.order.data.SiteOneWsLinkToPayOrderResponseData;

public interface SiteOneLinkToPayWebService {
	SiteOneWsLinkToPayOrderResponseData getOrderDetails(String orderNumber,boolean isNewBoomiEnv,String correlationID);

	String sendPaymentDetails(final LinkToPayCayanResponseModel cayanResponseModel, boolean isNewBoomiEnv)
			throws ResourceAccessException, RestClientException;
}
