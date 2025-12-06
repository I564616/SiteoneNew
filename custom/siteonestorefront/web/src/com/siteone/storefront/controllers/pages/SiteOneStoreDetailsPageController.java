package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;

import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.title.SiteOnePageTitleResolver;


/**
 * @author 532681
 *
 */
@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/store")
public class SiteOneStoreDetailsPageController extends AbstractSearchPageController
{

	private static final Logger LOG = Logger.getLogger(SiteOneStoreDetailsPageController.class);

	private static final String STORE_ID_PATH_VARIABLE_PATTERN = "/{storeId:.*}";

	private static final String STORE_ID_TIME_VARIABLE_PATTERN = "/{storeCode}";


	private static final String STORE_DETAILS_CMS_PAGE_LABEL = "storeDetails";
	
	public static final String REDIRECT_PREFIXURL = "redirect:";

	private static final String REDIRECT_STORE_FINDER = REDIRECT_PREFIX + "/store-finder";
	
	private static final String STORE_DETAILS_FULLPAGEPATH = "analytics.fullpath.store.details";
	/**
	 * @return the storeFinderService
	 */


	@Resource(name = "pageTitleResolver")
	private SiteOnePageTitleResolver siteOnePageTitleResolver;

	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;


	@GetMapping(STORE_ID_PATH_VARIABLE_PATTERN)
	public String storeDetailForCode(@PathVariable("storeId")
	final String storeId, @RequestParam(value = "searchtext", required = false)
	final String searchtext, @RequestParam(value = "miles", required = false)
	final String miles, final Model model, final RedirectAttributes redirectModel, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		final PointOfServiceData pointOfServiceData = siteOnestoreDetailsFacade.fetchSiteOnePointOfService(storeId);
		String requestURI = request.getRequestURI();
		if(requestURI.endsWith("/")) {
			requestURI = requestURI.replaceFirst("/(en|es)/", "/"); 
			requestURI = requestURI.replaceAll("[\\W]$", "");
			return REDIRECT_PREFIX + requestURI;
		}
		try
		{

			LOG.debug("Inside SiteOneStoreDetailsPageController");

			getMyStoresIdList();


			final double latitude = pointOfServiceData.getGeoPoint().getLatitude();

			final double longitude = pointOfServiceData.getGeoPoint().getLongitude();

			if (StringUtils.isNotEmpty(searchtext) && StringUtils.isNotEmpty(miles))
			{
				model.addAttribute("searchtext", searchtext);
				model.addAttribute("miles", miles);
			}
			model.addAttribute("isNurseryStore", siteOnestoreDetailsFacade.getIsNurseryCenter(pointOfServiceData));
			model.addAttribute("store", pointOfServiceData);
			model.addAttribute("latitude", Double.valueOf(latitude));
			model.addAttribute("longitude", Double.valueOf(longitude));
			model.addAttribute("storeNotes", pointOfServiceData.getStoreNotes());
			model.addAttribute("storeId", storeId);
			int page=0;
			StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.positionSearch(pointOfServiceData.getGeoPoint(), 
					createPageableData(page, getNearByStorePageSize(), null, ShowMode.Page), getMaxDistanceInMiles());
			searchResult.getResults().remove(0);
			model.addAttribute("nearByStores", searchResult.getResults());
		}
		catch (final Exception excep)
		{
			LOG.debug("Exception::::" + excep);
			return handleStoreNotFoundCase(redirectModel, request);

		}
		String storeName = pointOfServiceData.getName();
		final int index = storeName.indexOf("#");
		if (index != -1)
		{
			storeName = storeName.substring(0, index - 1);
		}
		getContentPageForLabelOrId(STORE_DETAILS_CMS_PAGE_LABEL)
				.setFullPagePath(getMessageSource().getMessage(STORE_DETAILS_FULLPAGEPATH, null, getI18nService().getCurrentLocale())
						+ ": " + storeName.toLowerCase() + "_" + storeId);
		storeCmsPageInModel(model, getStoreFinderPage());
		updateLocationPagesTitle(pointOfServiceData, model);
		return ControllerConstants.Views.Pages.StoreFinder.SiteOneStoreFinderDetailsPage;
	}

	/**
	 * Get the default search radius distance in miles.
	 *
	 * @return radius distance in miles, <tt>50</tt> (fifty) indicated 'default' distance should be used
	 */
	protected int getMaxDistanceInMiles()
	{
		return getSiteConfigService().getInt("storefront.nearbystore.range.miles", 50);
	}

	/**
	 * Get the default search page size.
	 *
	 * @return the number of results per page, <tt>4</tt> (four) indicated 'default' size should be used
	 */
	protected int getNearByStorePageSize()
	{
		return getSiteConfigService().getInt("storefront.nearbystore.pageSize", 4);
	}

	@GetMapping("storeDetails/" + STORE_ID_TIME_VARIABLE_PATTERN)
	@ResponseBody
	public String storeDetail(@PathVariable("storeCode")
	final String storeCode)
	{
		final PointOfServiceData pointOfServiceData = siteOnestoreDetailsFacade.fetchSiteOnePointOfService(storeCode);
		return pointOfServiceData.getStoreStatus();
	}


	protected AbstractPageModel getStoreFinderPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId(STORE_DETAILS_CMS_PAGE_LABEL);
	}

	protected String handleStoreNotFoundCase(final RedirectAttributes redirectModel, final HttpServletRequest request)
	{
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "storelocator.error.no.results.title");
		request.setAttribute("org.springframework.web.servlet.View.responseStatus", HttpStatus.MOVED_PERMANENTLY);
		return REDIRECT_STORE_FINDER;
	}


	@ModelAttribute("myStoresIdList")
	public List<String> getMyStoresIdList()
	{
		return (((SiteOneCustomerFacade) customerFacade).getMyStoresIdList());
	}

	protected void updateLocationPagesTitle(final PointOfServiceData pointOfServiceData, final Model model)
	{
		storeContentPageTitleInModel(model, getSiteOnePageTitleResolver().resolveLocationPagesTitle(pointOfServiceData, model));
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
