/**
 * Controller for Store Directory
 */
package com.siteone.storefront.controllers.pages;



import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.regioncache.region.impl.DefaultCacheRegion;
import de.hybris.platform.site.BaseSiteService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.storedirectory.cache.SiteOneCityCacheValueLoader;
import com.siteone.facades.storedirectory.cache.SiteOneStateCacheValueLoader;
import com.siteone.facades.storedirectory.cache.SiteOneStoreDirectoryCacheKey;
import com.siteone.storefront.breadcrumbs.impl.StoreDirectoryBreadCrumbBuilder;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.title.SiteOnePageTitleResolver;


/**
 * @author 1230514
 *
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/store-directory")
public class StoreDirectoryPageController extends AbstractPageController
{
	@Resource(name = "siteOneStoreDirectoryCacheRegion")
	private DefaultCacheRegion siteOneStoreDirectoryCacheRegion;

	@Resource(name = "siteOneCityCacheValueLoader")
	private SiteOneCityCacheValueLoader siteOneCityCacheValueLoader;

	@Resource(name = "siteOneStateCacheValueLoader")
	private SiteOneStateCacheValueLoader siteOneStateCacheValueLoader;

	@Resource(name = "storeFinderFacade")
	private SiteOneStoreFinderFacade storeFinderFacade;

	@Resource(name = "storeDirectoryBreadCrumbBuilder")
	private StoreDirectoryBreadCrumbBuilder storeDirectoryBreadCrumbBuilder;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "pageTitleResolver")
	private SiteOnePageTitleResolver siteOnePageTitleResolver;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;
	
	@GetMapping("/{state}/{city}")
	public String storeDirectoryList(@PathVariable("state") final String state, @PathVariable("city") final String city,
			final Model model) throws CMSItemNotFoundException
	{
		final List<PointOfServiceData> pointOfServiceData = storeFinderFacade.getPointOfServiceForCityAndState(city, state);


		if (null != pointOfServiceData && pointOfServiceData.size() == 1)
		{
			//Redirect to Store details page.
			return REDIRECT_PREFIX + "/store/" + pointOfServiceData.get(0).getStoreId();
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId("storeDirectoryListPage"));
		model.addAttribute("siteOnePOS", pointOfServiceData);
		model.addAttribute("cityName", city);
		if (null != pointOfServiceData)
		{
			model.addAttribute(WebConstants.BREADCRUMBS_KEY,
					storeDirectoryBreadCrumbBuilder.getBreadcrumbsForStoreListPage(pointOfServiceData.get(0)));
		}
		getMyStoresIdList();
		updateCityPageTitle(city, model);
		return ControllerConstants.Views.Pages.StoreDirectory.StoreDirectoryListPage;

	}


	@GetMapping("/{state}")
	public String cityStoreDirectory(@PathVariable("state") final String stateIsoCode, final Model model)
			throws CMSItemNotFoundException
	{
		final SiteOneStoreDirectoryCacheKey cacheKey = new SiteOneStoreDirectoryCacheKey(stateIsoCode);
		final Map<String, TreeMap<String, Integer>> storeCitiesGroups = (Map<String, TreeMap<String, Integer>>) getSiteOneStoreDirectoryCacheRegion()
				.getWithLoader(cacheKey, getSiteOneCityCacheValueLoader());
		storeCmsPageInModel(model, getContentPageForLabelOrId("storeDirectoryCityPage"));
		final String stateName = storeFinderFacade.getStateNameForIsoCode(stateIsoCode);
		model.addAttribute("storeCitiesGroups", storeCitiesGroups);
		model.addAttribute("state", stateIsoCode);
		model.addAttribute("stateName", stateName);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storeDirectoryBreadCrumbBuilder.getBreadcrumbsForCityPage());
		updateStatePageTitle(stateName, model);
		return ControllerConstants.Views.Pages.StoreDirectory.StoreDirectoryCityPage;

	}

	@GetMapping
	public String storeDirectoryStateList(final Model model) throws CMSItemNotFoundException
	{
		final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
		SiteOneStoreDirectoryCacheKey cacheKey = null;
		if (basesite.getUid().equals("siteone-us")) {
			cacheKey = new SiteOneStoreDirectoryCacheKey(SiteoneCoreConstants.US_ISO_CODE);
		}
		if (basesite.getUid().equals("siteone-ca")) {
			cacheKey = new SiteOneStoreDirectoryCacheKey(SiteoneCoreConstants.CA_ISO_CODE);
		}
		final Map<RegionData, Boolean> regionData = (Map<RegionData, Boolean>) getSiteOneStoreDirectoryCacheRegion()
				.getWithLoader(cacheKey, getSiteOneStateCacheValueLoader());
		storeCmsPageInModel(model, getContentPageForLabelOrId("storeDirectoryStateListPage"));
		model.addAttribute("regions", regionData);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storeDirectoryBreadCrumbBuilder.getBreadcrumbsForStatePage());
		return ControllerConstants.Views.Pages.StoreDirectory.StoreDirectoryStateListPage;
	}

	@ModelAttribute("myStoresIdList")
	public List<String> getMyStoresIdList()
	{
		return (((SiteOneCustomerFacade) customerFacade).getMyStoresIdList());
	}

	protected void updateCityPageTitle(final String cityName, final Model model)
	{
		storeContentPageTitleInModel(model, getSiteOnePageTitleResolver().resolveStoreDirectoryCityPageTitle(cityName));
	}

	protected void updateStatePageTitle(final String stateName, final Model model)
	{
		storeContentPageTitleInModel(model, getSiteOnePageTitleResolver().resolveStoreDirectoryStatePageTitle(stateName));
	}


	public SiteOneCityCacheValueLoader getSiteOneCityCacheValueLoader()
	{
		return siteOneCityCacheValueLoader;
	}

	public void setSiteOneCityCacheValueLoader(final SiteOneCityCacheValueLoader siteOneCityCacheValueLoader)
	{
		this.siteOneCityCacheValueLoader = siteOneCityCacheValueLoader;
	}

	public SiteOneStateCacheValueLoader getSiteOneStateCacheValueLoader()
	{
		return siteOneStateCacheValueLoader;
	}

	public void setSiteOneStateCacheValueLoader(final SiteOneStateCacheValueLoader siteOneStateCacheValueLoader)
	{
		this.siteOneStateCacheValueLoader = siteOneStateCacheValueLoader;
	}

	public DefaultCacheRegion getSiteOneStoreDirectoryCacheRegion()
	{
		return siteOneStoreDirectoryCacheRegion;
	}

	public void setSiteOneStoreDirectoryCacheRegion(final DefaultCacheRegion siteOneStoreDirectoryCacheRegion)
	{
		this.siteOneStoreDirectoryCacheRegion = siteOneStoreDirectoryCacheRegion;
	}


	/**
	 * @return the siteOnePageTitleResolver
	 */
	public SiteOnePageTitleResolver getSiteOnePageTitleResolver()
	{
		return siteOnePageTitleResolver;
	}


	/**
	 * @param siteOnePageTitleResolver
	 *           the siteOnePageTitleResolver to set
	 */
	public void setSiteOnePageTitleResolver(final SiteOnePageTitleResolver siteOnePageTitleResolver)
	{
		this.siteOnePageTitleResolver = siteOnePageTitleResolver;
	}


}
