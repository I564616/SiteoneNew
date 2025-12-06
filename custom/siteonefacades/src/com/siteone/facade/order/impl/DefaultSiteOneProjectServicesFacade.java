/**
 *
 */
package com.siteone.facade.order.impl;


import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.order.SiteOneProjectServicesFacade;
import com.siteone.facades.project.services.data.PsDashboardData;
import com.siteone.facades.project.services.data.PsDashboardInfoData;
import com.siteone.integration.project.services.data.PsBiddingRequestData;
import com.siteone.integration.project.services.data.PsBiddingResponseData;
import com.siteone.integration.project.services.data.PsDashboardItemInfoResponseData;
import com.siteone.integration.project.services.data.PsDashboardResponseData;
import com.siteone.integration.project.services.data.PsHiddenResponseData;
import com.siteone.integration.project.services.request.PSDashboardRequest;
import com.siteone.integration.services.ue.SiteOneProjectServicesWebService;


/**
 * @author AA04994
 *
 */
public class DefaultSiteOneProjectServicesFacade implements SiteOneProjectServicesFacade
{

	@Resource(name = "siteOneProjectServicesWebService")
	private SiteOneProjectServicesWebService siteOneProjectServicesWebService;

	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";

	@Override
	public String getBearerToken(final String sessionToken)
	{
		final String bToken = siteOneProjectServicesWebService.getBearerToken(
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), sessionToken);
		return bToken;
	}

	@Override
	public PsDashboardData getDashboardInfo(final String bToken, final PSDashboardRequest request)
	{
		final PsDashboardResponseData dashboardResponse = siteOneProjectServicesWebService.getDashboardInfo(
				Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)), bToken, request);

		return populateDashboardData(dashboardResponse);
	}

	/**
	 * @param dashboardResponse
	 * @return
	 */
	private PsDashboardData populateDashboardData(final PsDashboardResponseData dashboardResponse)
	{
		final PsDashboardData dashboardData = new PsDashboardData();

		if (dashboardResponse != null)
		{
			if (dashboardResponse.getItemTotal() != null)
			{
				dashboardData.setItemTotal(dashboardResponse.getItemTotal());
			}

			final List<PsDashboardInfoData> dashboardListInfoData = new ArrayList<>();
			if (dashboardResponse.getItems() != null && !dashboardResponse.getItems().isEmpty())
			{
				for (final PsDashboardItemInfoResponseData psInfo : dashboardResponse.getItems())
				{
					if (psInfo == null)
					{
						continue;
					}

					final PsDashboardInfoData dashboardInfoData = new PsDashboardInfoData();
					if (StringUtils.isNoneBlank(psInfo.getProjectName()))
					{
						dashboardInfoData.setProjectName(psInfo.getProjectName());
					}
					if (StringUtils.isNoneBlank(psInfo.getPlanName()))
					{
						dashboardInfoData.setPlanName(psInfo.getPlanName());
					}
					if (psInfo.getProjectID() != null)
					{
						dashboardInfoData.setProjectID(psInfo.getProjectID());
					}
					if (psInfo.getProjectNumber() != null)
					{
						dashboardInfoData.setProjectNumber(psInfo.getProjectNumber());
					}
					if (psInfo.getIsHiddenFromDashboard() != null)
					{
						dashboardInfoData.setIsHiddenFromDashboard(psInfo.getIsHiddenFromDashboard());
					}
					if (psInfo.getIsFavorite() != null)
					{
						dashboardInfoData.setIsFavorite(psInfo.getIsFavorite());
					}
					if (psInfo.getIsBidding() != null)
					{
						dashboardInfoData.setIsBidding(psInfo.getIsBidding());
					}
                    if (psInfo.getCityStateZip() != null)
                    {
                        dashboardInfoData.setCityStateZip(psInfo.getCityStateZip());
                    }
                    if (psInfo.getBidDate() != null)
                    {
                        dashboardInfoData.setBidDate(psInfo.getBidDate());
                    }
                    if (psInfo.getProjectDueDate() != null)
                    {
                        dashboardInfoData.setProjectDueDate(psInfo.getProjectDueDate());
                    }
                    if (psInfo.getCreatedOn() != null)
                    {
                        dashboardInfoData.setCreatedOn(psInfo.getCreatedOn());
                    }
                    if (psInfo.getProjectStatus() != null)
                    {
                        dashboardInfoData.setProjectStatus(psInfo.getProjectStatus());
                    }
                    if (psInfo.getProjectStatusStr() != null)
                    {
                        dashboardInfoData.setProjectStatusStr(psInfo.getProjectStatusStr());
                    }
					dashboardListInfoData.add(dashboardInfoData);
				}
			}
			dashboardData.setInfo(dashboardListInfoData);
		}

		return dashboardData;
	}

	@Override
	public PsBiddingResponseData updateBiddingInfo(final String bToken, final String projectId, final Boolean isBidding)
	{
		PsBiddingResponseData response = null;
		final PsBiddingRequestData request = new PsBiddingRequestData();
		request.setProjectID(projectId);
		request.setIsProjectList(isBidding);

		if (BooleanUtils.isTrue(isBidding))
		{
			response = siteOneProjectServicesWebService.addBiddingInfo(request, bToken);
		}
		else
		{
			response = siteOneProjectServicesWebService.removeBiddingInfo(request, bToken);
		}

		return response;
	}

	@Override
	public PsHiddenResponseData updateHiddenInfo(final String bToken, final String projectId, final Boolean isHidden)
	{
		final PsHiddenResponseData response = siteOneProjectServicesWebService.updateHiddenInfo(bToken, projectId, isHidden);
		return response;
	}

	@Override
	public PsHiddenResponseData updateFavoriteInfo(final String bToken, final String projectId, final Boolean isFav)
	{
		final PsHiddenResponseData response = siteOneProjectServicesWebService.updateFavoriteInfo(bToken, projectId, isFav);
		return response;
	}

}
