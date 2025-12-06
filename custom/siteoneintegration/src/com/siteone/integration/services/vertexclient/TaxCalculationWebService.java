package com.siteone.integration.services.vertexclient;

import com.siteone.integration.webserviceclient.vertexclient.VertexEnvelope;

import de.hybris.platform.core.model.order.AbstractOrderModel;

public interface TaxCalculationWebService {

	VertexEnvelope calculateTax(AbstractOrderModel abstractOrder);
}
