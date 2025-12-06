package com.siteone.integration.services.ue;

import com.siteone.integration.project.services.data.PsBiddingRequestData;
import com.siteone.integration.project.services.data.PsBiddingResponseData;
import com.siteone.integration.project.services.data.PsDashboardResponseData;
import com.siteone.integration.project.services.data.PsHiddenResponseData;
import com.siteone.integration.project.services.request.PSDashboardRequest;

public interface SiteOneProjectServicesWebService 
{

	String getBearerToken(boolean isNewBoomiEnv, String sessionToken);

	PsDashboardResponseData getDashboardInfo(boolean isNewBoomiEnv, String bToken, PSDashboardRequest request);

	PsBiddingResponseData addBiddingInfo(PsBiddingRequestData request, String bToken);

	PsBiddingResponseData removeBiddingInfo(PsBiddingRequestData request, String bToken);

	PsHiddenResponseData updateHiddenInfo(String bToken, String projectId, Boolean isHidden);

	PsHiddenResponseData updateFavoriteInfo(String bToken, String projectId, Boolean isFav);

}
