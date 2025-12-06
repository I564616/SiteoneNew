package com.siteone.integration.services.ue.impl;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.address.data.SiteOneWsAddressRequestData;
import com.siteone.integration.address.data.SiteOneWsAddressResponseData;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneAddressWebService;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

public class DefaultSiteOneAddressWebService implements SiteOneAddressWebService {

	private SiteOneRestClient<SiteOneWsAddressRequestData, SiteOneWsAddressResponseData> siteOneRestClient;
	private Converter<AddressModel, SiteOneWsAddressRequestData> siteOneWsAddressConverter;

	@Override
	public SiteOneWsAddressResponseData createOrUpdateAddress(final AddressModel addressModel, final B2BUnitModel unitModel,String operationType, boolean isNewBoomiEnv) throws ResourceAccessException 
	{			
		final SiteOneWsAddressResponseData siteOneWsAddressResponseData = execute(addressModel,operationType,unitModel.getGuid(), isNewBoomiEnv);
		return siteOneWsAddressResponseData;
	}


	@Override
	public SiteOneWsAddressResponseData deleteAddress(String addressGuid,String unitGuid, boolean isNewBoomiEnv) throws ResourceAccessException
	{				
		final SiteOneWsAddressRequestData siteOneWsAddressRequestData = new SiteOneWsAddressRequestData();		
		siteOneWsAddressRequestData.setOperationType(SiteoneintegrationConstants.OPERATION_TYPE_DELETE);
		siteOneWsAddressRequestData.setAccountNumber(unitGuid);
		siteOneWsAddressRequestData.setAddressId(addressGuid);
		siteOneWsAddressRequestData.setCorrelationId(UUID.randomUUID().toString());
		return getResponse(siteOneWsAddressRequestData, isNewBoomiEnv);
	}

	public SiteOneWsAddressResponseData execute(AddressModel addressModel, String operationType, String b2bUnitGuid, boolean isNewBoomiEnv)  throws ResourceAccessException
	{
		final SiteOneWsAddressRequestData siteOneWsAddressRequestData = getSiteOneWsAddressConverter().convert(addressModel);
		siteOneWsAddressRequestData.setOperationType(operationType);
		siteOneWsAddressRequestData.setAccountNumber(b2bUnitGuid);
		return getResponse(siteOneWsAddressRequestData, isNewBoomiEnv);
	}
	
	protected SiteOneWsAddressResponseData getResponse(SiteOneWsAddressRequestData siteOneWsAddressRequestData, boolean isNewBoomiEnv)
	{
		if(isNewBoomiEnv)
		{
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.ADDRESS_SERVICE_NEW_URL_KEY , null), HttpMethod.POST,
					siteOneWsAddressRequestData, SiteOneWsAddressResponseData.class,siteOneWsAddressRequestData.getCorrelationId(),SiteoneintegrationConstants.ADDRESS_SERVICE_NAME,httpHeaders);
		}
		else
		{
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.ADDRESS_SERVICE_URL_KEY, null), HttpMethod.POST,
					siteOneWsAddressRequestData, SiteOneWsAddressResponseData.class,siteOneWsAddressRequestData.getCorrelationId(),SiteoneintegrationConstants.ADDRESS_SERVICE_NAME,null);
		}
	}

	/**
	 * @return the siteOneRestClient
	 */
	public SiteOneRestClient<SiteOneWsAddressRequestData, SiteOneWsAddressResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	/**
	 * @param siteOneRestClient
	 *            the siteOneRestClient to set
	 */
	public void setSiteOneRestClient(
			final SiteOneRestClient<SiteOneWsAddressRequestData, SiteOneWsAddressResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

	/**
	 * @return the siteOneWsAddressConverter
	 */
	public Converter<AddressModel, SiteOneWsAddressRequestData> getSiteOneWsAddressConverter() {
		return siteOneWsAddressConverter;
	}

	/**
	 * @param siteOneWsAddressConverter
	 *            the siteOneWsAddressConverter to set
	 */
	public void setSiteOneWsAddressConverter(
			Converter<AddressModel, SiteOneWsAddressRequestData> siteOneWsAddressConverter) {
		this.siteOneWsAddressConverter = siteOneWsAddressConverter;
	}
}
