/**
 *
 */
package com.siteone.facade.order;

import com.siteone.facades.project.services.data.PsDashboardData;
import com.siteone.integration.project.services.data.PsBiddingResponseData;
import com.siteone.integration.project.services.data.PsHiddenResponseData;
import com.siteone.integration.project.services.request.PSDashboardRequest;


/**
 *
 */
public interface SiteOneProjectServicesFacade
{

	/**
	 * @param sessionToken
	 * @return
	 */
	String getBearerToken(String sessionToken);

	/**
	 * @param bToken
	 * @param psDashboardRequest
	 * @return
	 */
	PsDashboardData getDashboardInfo(String bToken, PSDashboardRequest request);

	/**
	 * @param bToken
	 * @param projectId
	 * @param isBidding
	 * @return
	 */
	PsBiddingResponseData updateBiddingInfo(String bToken, String projectId, Boolean isBidding);

	/**
	 * @param bToken
	 * @param projectId
	 * @param isHidden
	 * @return
	 */
	PsHiddenResponseData updateHiddenInfo(String bToken, String projectId, Boolean isHidden);

	/**
	 * @param bToken
	 * @param projectId
	 * @param isFav
	 * @return
	 */
	PsHiddenResponseData updateFavoriteInfo(String bToken, String projectId, Boolean isFav);

}
