package com.siteone.integration.services.ue;

import org.springframework.web.client.ResourceAccessException;

import com.siteone.core.model.TalonOneEnrollmentModel;
import com.siteone.integration.customer.talonOneLoyaltyEnrollment.data.SiteOneWsTalonOneLoyaltyEnrollmentResponseData;

public interface SiteOneTalonOneLoyaltyEnrollmentWebService {
	SiteOneWsTalonOneLoyaltyEnrollmentResponseData enrollCustomerInTalonOne(
			TalonOneEnrollmentModel talonOneEnrollmentModel, boolean isNewBoomiEnv)
			throws ResourceAccessException;
}
