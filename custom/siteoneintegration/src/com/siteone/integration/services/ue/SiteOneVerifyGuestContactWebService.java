package com.siteone.integration.services.ue;

import com.siteone.integration.verifyguestcontact.data.SiteOneWsVerifyGuestContactResponseData;

public interface SiteOneVerifyGuestContactWebService {

    SiteOneWsVerifyGuestContactResponseData verifyGuestInUE(String uid, boolean isNewBoomiEnv);
}
