package com.siteone.integration.services.invoice;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.invoice.order.data.SiteoneInvoiceDetailsResponseData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceRequestData;
import com.siteone.integration.invoice.order.data.SiteoneInvoiceResponseData;

public interface SiteOneInvoiceWebService {

	 byte[] getInvoiceByOrderShipmentActualId(String orderShipmentActualId);

	SiteoneInvoiceResponseData getInvoicesData(SiteoneInvoiceRequestData invoiceRequest, String customerNumber, Integer divisionId, boolean isNewBoomiEnv)
			throws ResourceAccessException;
	SiteoneInvoiceDetailsResponseData getInvoiceDetailsData(String customerNumber, String invoiceNumber, boolean isNewBoomiEnv)
			throws ResourceAccessException;
}
