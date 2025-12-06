package com.siteone.integration.services.ue.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.model.TalonOneEnrollmentModel;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteOneWsTalonOneLoyaltyEnrollmentRequestData;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteOneWsTalonOneLoyaltyEnrollmentResponseData;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneTalonOneLoyaltyEnrollmentWebService;

import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

public class DefaultSiteOneTalonOneLoyaltyEnrollmentWebService implements SiteOneTalonOneLoyaltyEnrollmentWebService {
	
	private SiteOneRestClient<SiteOneWsTalonOneLoyaltyEnrollmentRequestData, SiteOneWsTalonOneLoyaltyEnrollmentResponseData> siteOneRestClient;
	private Converter<TalonOneEnrollmentModel, SiteOneWsTalonOneLoyaltyEnrollmentRequestData> siteOneWsTalonOneLoyaltyEnrollmentConverter;

	@Override
	public SiteOneWsTalonOneLoyaltyEnrollmentResponseData enrollCustomerInTalonOne(
			TalonOneEnrollmentModel talonOneEnrollmentModel, boolean isNewBoomiEnv)
			throws ResourceAccessException 
	{
		SiteOneWsTalonOneLoyaltyEnrollmentRequestData siteOneWsTalonOneLoyaltyEnrollmentRequestData = getSiteOneWsTalonOneLoyaltyEnrollmentConverter().convert(talonOneEnrollmentModel);
	
		if(isNewBoomiEnv)
		{
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_LOYALTY, null));
			return getSiteOneRestClient().execute(
				Config.getString(SiteoneintegrationConstants.ENROLL_IN_TALONONE_NEW_URL_KEY, StringUtils.EMPTY), HttpMethod.POST,
				siteOneWsTalonOneLoyaltyEnrollmentRequestData, SiteOneWsTalonOneLoyaltyEnrollmentResponseData.class,
				siteOneWsTalonOneLoyaltyEnrollmentRequestData.getCorrelationID(),
				SiteoneintegrationConstants.TALONONE_ENROLLMENT_SERVICE_NAME, httpHeaders);
		}
		else
		{
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.ENROLL_IN_TALONONE_URL_KEY, StringUtils.EMPTY), HttpMethod.POST,
					siteOneWsTalonOneLoyaltyEnrollmentRequestData, SiteOneWsTalonOneLoyaltyEnrollmentResponseData.class,
					siteOneWsTalonOneLoyaltyEnrollmentRequestData.getCorrelationID(),
					SiteoneintegrationConstants.TALONONE_ENROLLMENT_SERVICE_NAME, null);
		}
	}

	public SiteOneRestClient<SiteOneWsTalonOneLoyaltyEnrollmentRequestData, SiteOneWsTalonOneLoyaltyEnrollmentResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsTalonOneLoyaltyEnrollmentRequestData, SiteOneWsTalonOneLoyaltyEnrollmentResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

	public Converter<TalonOneEnrollmentModel, SiteOneWsTalonOneLoyaltyEnrollmentRequestData> getSiteOneWsTalonOneLoyaltyEnrollmentConverter() {
		return siteOneWsTalonOneLoyaltyEnrollmentConverter;
	}

	public void setSiteOneWsTalonOneLoyaltyEnrollmentConverter(
			Converter<TalonOneEnrollmentModel, SiteOneWsTalonOneLoyaltyEnrollmentRequestData> siteOneWsTalonOneLoyaltyEnrollmentConverter) {
		this.siteOneWsTalonOneLoyaltyEnrollmentConverter = siteOneWsTalonOneLoyaltyEnrollmentConverter;
	}

	

	

	
	
	

}
