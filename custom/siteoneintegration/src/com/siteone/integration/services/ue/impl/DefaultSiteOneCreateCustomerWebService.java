package com.siteone.integration.services.ue.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerRequestData;
import com.siteone.integration.customer.createCustomer.data.SiteOneWsCreateCustomerResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneCreateCustomerWebService;

import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

public class DefaultSiteOneCreateCustomerWebService implements SiteOneCreateCustomerWebService{

	private SiteOneRestClient<SiteOneWsCreateCustomerRequestData, SiteOneWsCreateCustomerResponseData> siteOneRestClient;
	private Converter<SiteoneRequestAccountModel, SiteOneWsCreateCustomerRequestData> siteOneWsCreateCustomerConverter;

	@Override
	public SiteOneWsCreateCustomerResponseData createCustomer(SiteoneRequestAccountModel siteOneRequestAccount, boolean isNewBoomiEnv)
			throws ResourceAccessException {

		SiteOneWsCreateCustomerRequestData siteOneWsCreateCustomerRequestData = getSiteOneWsCreateCustomerConverter()
				.convert(siteOneRequestAccount);
		
		if(isNewBoomiEnv)
		{
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.CREATE_CUSTOMER_NEW_URL_KEY , StringUtils.EMPTY), HttpMethod.POST,
					siteOneWsCreateCustomerRequestData, SiteOneWsCreateCustomerResponseData.class,
					siteOneWsCreateCustomerRequestData.getCorrelationID(),
					SiteoneintegrationConstants.CREATE_CUSTOMER_SERVICE_NAME, httpHeaders);
		}
		else
		{		  
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.CREATE_CUSTOMER_URL_KEY, StringUtils.EMPTY), HttpMethod.POST,
					siteOneWsCreateCustomerRequestData, SiteOneWsCreateCustomerResponseData.class,
					siteOneWsCreateCustomerRequestData.getCorrelationID(),
					SiteoneintegrationConstants.CREATE_CUSTOMER_SERVICE_NAME, null);
		}


	}

	public Converter<SiteoneRequestAccountModel, SiteOneWsCreateCustomerRequestData> getSiteOneWsCreateCustomerConverter() {
		return siteOneWsCreateCustomerConverter;
	}

	public void setSiteOneWsCreateCustomerConverter(
			Converter<SiteoneRequestAccountModel, SiteOneWsCreateCustomerRequestData> siteOneWsCreateCustomerConverter) {
		this.siteOneWsCreateCustomerConverter = siteOneWsCreateCustomerConverter;
	}

	public SiteOneRestClient<SiteOneWsCreateCustomerRequestData, SiteOneWsCreateCustomerResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsCreateCustomerRequestData, SiteOneWsCreateCustomerResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

}