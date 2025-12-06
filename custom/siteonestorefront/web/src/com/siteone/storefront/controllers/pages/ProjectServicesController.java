/**
 *
 */
package com.siteone.storefront.controllers.pages;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.servicelayer.session.SessionService;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.facade.order.SiteOneProjectServicesFacade;
import com.siteone.facades.project.services.data.PsDashboardData;
import com.siteone.integration.project.services.data.PsBiddingResponseData;
import com.siteone.integration.project.services.data.PsHiddenResponseData;
import com.siteone.integration.project.services.request.PSDashboardRequest;
import com.siteone.storefront.forms.SiteOnePSDashboardForm;


/**
 * @author AA04994
 *
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/projectservices")
public class ProjectServicesController extends AbstractSearchPageController
{
	private static final String PROJECT_SERVICES_CMS_PAGE = "projectservices";
	private static final String PROJECT_SERVICES_DASHBOARD_PAGE = "projectDashboard";
	private static final String PROJECT_SERVICES_INFORMATION_PAGE = "projectInfo";
	private static final String BEARER_TOKEN = "bearerToken";
	protected static final String PROJECT_DETAILS = "/projectservices/dashboard";

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "siteOneProjectServicesFacade")
	private SiteOneProjectServicesFacade siteOneProjectServicesFacade;

	@Resource(name = "sessionService")
	private SessionService sessionService;

    @Resource(name = "siteOneFeatureSwitchService")
    private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

    @Resource(name = "storeSessionFacade")
    private SiteOneStoreSessionFacade storeSessionFacade;

	//	public String projectServices(final Model model) throws CMSItemNotFoundException
	//	{
	//		final ContentPageModel page = getContentPageForLabelOrId(PROJECT_SERVICES_CMS_PAGE);
	//		storeCmsPageInModel(model, page);
	//		setUpMetaDataForContentPage(model, page);
	//
	//		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
	//		breadcrumbs.add(new Breadcrumb("/projectservices",
	//				getMessageSource().getMessage("text.account.projectServices.breadcrumb", null, getI18nService().getCurrentLocale()),
	//				null));
	//
	//		model.addAttribute("breadcrumbs", breadcrumbs);
	//		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
	//
	//		return getViewForPage(model);
	//	}

	@GetMapping("/dashboard")
	@RequireHardLogIn
	public String dashboardPageInfo(final Model model)
			throws CMSItemNotFoundException
	{
        B2BUnitData bUnit = storeSessionFacade.getSessionShipTo();
        if (Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch("isProjectServicesEnabled")) &&
                siteOneFeatureSwitchCacheService.isB2BUnitPresentUnderFeatureSwitch("EnabledB2BUnitsForProjectServices", bUnit.getUid())) {

            final ContentPageModel page = getContentPageForLabelOrId(PROJECT_SERVICES_DASHBOARD_PAGE);
            storeCmsPageInModel(model, page);
            setUpMetaDataForContentPage(model, page);

            final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
            breadcrumbs.add(new Breadcrumb("/projectservices", getMessageSource()
      				.getMessage("text.account.projectServices.breadcrumb", null, getI18nService().getCurrentLocale()), null));
            breadcrumbs.add(new Breadcrumb("/projectservices/dashboard", getMessageSource()
                    .getMessage("text.account.projectServices.dashboard.breadcrumb", null, getI18nService().getCurrentLocale()), null));

            model.addAttribute("breadcrumbs", breadcrumbs);
            model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
            return getViewForPage(model);
        }
        else {
            return REDIRECT_PREFIX + "/";
        }
	}

	@PostMapping("/project-dashboard")
	@RequireHardLogIn
	public @ResponseBody PsDashboardData projectDashboard(final SiteOnePSDashboardForm siteOnePSDashboardForm, final Model model)
			throws CMSItemNotFoundException
	{
		String bToken = sessionService.getAttribute(BEARER_TOKEN);
		if (bToken == null)
		{
			final String sessionToken = (String) sessionService.getAttribute(SiteoneCoreConstants.OKTA_SESSION_TOKEN);
			bToken = siteOneProjectServicesFacade.getBearerToken(sessionToken);
			bToken = bToken.replace("\"", "");
			sessionService.setAttribute(BEARER_TOKEN, bToken);
		}

		PsDashboardData dashboardData = new PsDashboardData();
		if (bToken != null)
		{
			dashboardData = siteOneProjectServicesFacade.getDashboardInfo(bToken, getRequest(siteOnePSDashboardForm));
		}
		return dashboardData;
	}

	//	@RequestMapping(value = "/project-dashboard/{projectId}", method = RequestMethod.GET)
	//	@RequireHardLogIn
	//	public String projectInformation(@PathVariable("projectId")
	//	final String projectId, final Model model) throws CMSItemNotFoundException
	//	{
	//		final String bToken = sessionService.getAttribute(BEARER_TOKEN);
	//		if (bToken == null)
	//		{
	//			return null;
	//		}
	//
	//		final PsInformationData informationData = siteOneProjectServicesFacade.getProjectInfo(bToken, projectId);
	//		model.addAttribute("projectInformation", informationData);
	//
	//		final ContentPageModel page = getContentPageForLabelOrId(PROJECT_SERVICES_INFORMATION_PAGE);
	//		storeCmsPageInModel(model, page);
	//		setUpMetaDataForContentPage(model, page);
	//
	//		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
	//		breadcrumbs.add(new Breadcrumb("/projectservices/dashboard", getMessageSource()
	//				.getMessage("text.account.projectServices.dashboard.breadcrumb", null, getI18nService().getCurrentLocale()), null));
	//		breadcrumbs.add(new Breadcrumb(PROJECT_DETAILS + "/" + urlEncode(projectId),
	//				getMessageSource().getMessage("breadcrumb.project.service.view", new Object[]
	//				{ projectId }, "{0}", getI18nService().getCurrentLocale()), null));
	//
	//		model.addAttribute("breadcrumbs", breadcrumbs);
	//		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
	//		return getViewForPage(model);
	//	}

	@GetMapping("/project-bidding")
	@RequireHardLogIn
	public @ResponseBody String projectBidding(@RequestParam(value = "projectId", required = true)
	final String projectId, @RequestParam(value = "isBidding", required = true)
	final Boolean isBidding, final Model model)
	{
		final String bToken = sessionService.getAttribute(BEARER_TOKEN);
		if (bToken != null)
		{
			final PsBiddingResponseData response = siteOneProjectServicesFacade.updateBiddingInfo(bToken, projectId, isBidding);

			if (response != null && BooleanUtils.isTrue(response.getSuccess()))
			{
				return "success";
			}
			else
			{
				return "failure";
			}
		}
		else
		{
			return "failure";
		}
	}

	@GetMapping("/project-hidden")
	@RequireHardLogIn
	public @ResponseBody String projectHiding(@RequestParam(value = "projectId", required = true)
	final String projectId, @RequestParam(value = "isHidden", required = true)
	final Boolean isHidden, final Model model)
	{
		final String bToken = sessionService.getAttribute(BEARER_TOKEN);

		if (bToken != null)
		{
			final PsHiddenResponseData response = siteOneProjectServicesFacade.updateHiddenInfo(bToken, projectId, isHidden);

			if (response != null && BooleanUtils.isTrue(response.getSuccess()))
			{
				return "success";
			}
			else
			{
				return "failure";
			}
		}
		else
		{
			return "failure";
		}
	}

	@GetMapping("/project-favorite")
	@RequireHardLogIn
	public @ResponseBody String projectFav(@RequestParam(value = "projectId", required = true)
	final String projectId, @RequestParam(value = "isFav", required = true)
	final Boolean isFav, final Model model)
	{
		final String bToken = sessionService.getAttribute(BEARER_TOKEN);
		if (bToken != null)
		{
			final PsHiddenResponseData response = siteOneProjectServicesFacade.updateFavoriteInfo(bToken, projectId, isFav);

			if (response != null && BooleanUtils.isTrue(response.getSuccess()))
			{
				return "success";
			}
			else
			{
				return "failure";
			}
		}
		else
		{
			return "failure";
		}
	}

	public PSDashboardRequest getRequest(final SiteOnePSDashboardForm siteOnePSDashboardForm)
	{
		final PSDashboardRequest request = new PSDashboardRequest();
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getBidding()))
		{
			request.setBidding(siteOnePSDashboardForm.getBidding());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getFavorite()))
		{
			request.setFavorite(siteOnePSDashboardForm.getFavorite());
		}
		if (StringUtils.isNotBlank(request.getFromDate()))
		{
			request.setFromDate(request.getFromDate());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getIsAsc()))
		{
			request.setIsAsc(siteOnePSDashboardForm.getIsAsc());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getIsHidden()))
		{
			request.setIsHidden(siteOnePSDashboardForm.getIsHidden());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getPage()))
		{
			request.setPage(siteOnePSDashboardForm.getPage());
		}
		else
		{
			request.setPage("1");
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getPageSize()))
		{
			request.setPageSize(siteOnePSDashboardForm.getPageSize());
		}
		else
		{
			request.setPageSize("50");
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getSortBy()))
		{
			request.setSortBy(siteOnePSDashboardForm.getSortBy());
		}
		else
		{
			request.setSortBy("pr.ProjectNumber");
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getPrevailingWage()))
		{
			request.setPrevailingWage(siteOnePSDashboardForm.getPrevailingWage());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getProjectUnderRevision()))
		{
			request.setProjectUnderRevision(siteOnePSDashboardForm.getProjectUnderRevision());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getSearchText()))
		{
			request.setSearchText(siteOnePSDashboardForm.getSearchText());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getSortBy()))
		{
			request.setSortBy(siteOnePSDashboardForm.getSortBy());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getStates()))
		{
			request.setStates(siteOnePSDashboardForm.getStates());
		}
		if (StringUtils.isNotBlank(siteOnePSDashboardForm.getType()))
		{
			request.setType(siteOnePSDashboardForm.getType());
		}
		return request;
	}

}
