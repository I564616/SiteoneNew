package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.acceleratorservices.store.data.UserLocationData;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;


@Controller
@RequestMapping(value =
{ "/hardscapes", "/stonecenter" })
public class SiteOneHardscapesController extends AbstractPageController
{
	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	private static final String HARDSCAPES_PAGE = "hardscapes";
	private static final String STONECENTER_PAGE = "stonecenter";
	private static final String HARDSCAPES = "Hardscape";
	private static final String SPECIALITY_HARDSCAPES = Config.getString("hardscapes.specialities", "Hardscape");
	private static final String SEO_INDEX_ENV = "storefront.seo.index.env";

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SiteOneHardscapesController.class);

	@GetMapping
	public String stateHardscapesDirectory(final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final Set<String> states = storeFinderFacade.getListofStatesFromSpecialty(HARDSCAPES);
		model.addAttribute("statesList", states);
		String path = request.getServletPath();
		if (null != path && path.startsWith("/"))
		{
			path = path.substring(1, path.length());
		}
		else
		{
			path = HARDSCAPES_PAGE;
		}
		setUpHardscapeBreadcrumb(model, path);
		storeCmsPageInModel(model, getContentPageForLabelOrId(path));

		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(path));
		final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
		if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		}
		else
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		}
		return getViewForPage(model);


	}


	@PostMapping("/state")
	public @ResponseBody TreeMap<String, List<PointOfServiceData>> findGeoStores(@RequestParam(value = "state")
			final String state, final Model model, final HttpServletRequest request, final HttpServletResponse response)

	{
		updateLocalUserPreferences(null, state);
		final TreeMap<String, List<PointOfServiceData>> pos = storeFinderFacade.getListofStoresFromSpecialty(state, SPECIALITY_HARDSCAPES);
		return pos;

	}

	@ModelAttribute("myStoresIdList")
	public List<String> getMyStoresIdList()
	{
		return (((SiteOneCustomerFacade) customerFacade).getMyStoresIdList());
	}

	protected void updateLocalUserPreferences(final GeoPoint geoPoint, final String location)
	{
		final UserLocationData userLocationData = new UserLocationData();
		userLocationData.setSearchTerm(location);
		userLocationData.setPoint(geoPoint);
		customerLocationService.setUserLocation(userLocationData);
	}


	private void setUpHardscapeBreadcrumb(final Model model, final String path)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<>();
		if (path != null && path.equalsIgnoreCase(STONECENTER_PAGE))
		{
			final Breadcrumb breadcrumb = new Breadcrumb("#",
					getMessageSource().getMessage("breadcrumb.stonecenter", null, getI18nService().getCurrentLocale()), null);
			final Breadcrumb breadcrumbHardscapeLanding = new Breadcrumb("/" + HARDSCAPES_PAGE,
					getMessageSource().getMessage("breadcrumb.hardscape", null, getI18nService().getCurrentLocale()), null);
			breadcrumbs.add(breadcrumbHardscapeLanding);
			breadcrumbs.add(breadcrumb);
		}
		else
		{
			final Breadcrumb breadcrumb = new Breadcrumb("#",
					getMessageSource().getMessage("breadcrumb.hardscape", null, getI18nService().getCurrentLocale()), null);
			breadcrumbs.add(breadcrumb);
		}

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

	}


}

