package com.siteone.integration.services.ue;

import com.siteone.integration.TalonOne.info.LoyaltyProgramStatusInfo;

public interface SiteOneTalonOneLoyaltyEnrollmentStatusWebService {

	LoyaltyProgramStatusInfo getLoyaltyProgramInfoStatus(String unitId, boolean isNewBoomiEnv);
	
}
