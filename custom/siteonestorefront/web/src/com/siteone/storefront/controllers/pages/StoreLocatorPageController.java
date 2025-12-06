/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.customerlocation.CustomerLocationFacade;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.acceleratorservices.store.data.UserLocationData;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.StorefinderBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.StorePositionForm;
import de.hybris.platform.acceleratorstorefrontcommons.util.MetaSanitizerUtil;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.personalizationservices.service.CxService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.siteone.core.store.services.SiteOneStoreFinderService;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.storefront.controllers.ControllerConstants;
import com.siteone.storefront.forms.SiteOneStoreFinderForm;


/**
 * Controller for store locator search and detail pages.
 */

@Controller
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping(value = "/**/store-finder")
public class StoreLocatorPageController extends AbstractSearchPageController
{
	private static final Logger LOG = Logger.getLogger(StoreLocatorPageController.class);

	private static final String STORE_FINDER_CMS_PAGE_LABEL = "storefinder";
	private static final String GOOGLE_API_KEY_ID = "googleApiKey";
	private static final String GOOGLE_API_VERSION = "googleApiVersion";
	private static final String GEO_LOCATED_STORE_COOKIE = "gls";
	public static final String REDIRECT_PREFIX = "redirect:";
	public static final String FORWARD_PREFIX = "forward:";
	public static final String ROOT = "/";
	private static final String STORE_ID_PATH_VARIABLE_PATTERN = "{storeId:.*}";
	private static final String POINT_OF_SERVICE_DATA_ATTR = "pointOfServiceData";
	private static final String POINT_OF_SERVICE_DATA = "pointOfServiceDataSelected";

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "storefinderBreadcrumbBuilder")
	private StorefinderBreadcrumbBuilder storefinderBreadcrumbBuilder;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;


	@Resource(name = "defaultSiteOneStoreFinderService")
	private SiteOneStoreFinderService siteOneStoreFinderService;

	@Resource(name = "customerLocationFacade")
	private CustomerLocationFacade customerLocationFacade;

	@Resource(name = "cxService")
	private CxService cxService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;

	@Resource(name = "pointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;
	
	@Resource(name = "siteOnePointOfServiceConverter")
	private Converter<PointOfServiceModel, PointOfServiceData> siteOnePointOfServiceConverter;

	@ModelAttribute("googleApiVersion")
	public String getGoogleApiVersion()
	{
		return configurationService.getConfiguration().getString(GOOGLE_API_VERSION);
	}

	@ModelAttribute("googleApiKey")
	public String getGoogleApiKey(final HttpServletRequest request)
	{
		final String googleApiKey = getHostConfigService().getProperty(GOOGLE_API_KEY_ID, request.getServerName());
		if (StringUtils.isEmpty(googleApiKey))
		{
			LOG.warn("No Google API key found for server: " + request.getServerName());
		}
		return googleApiKey;
	}

	// Method to get the empty search form
	@GetMapping
	public String getStoreFinderPage(final Model model) throws CMSItemNotFoundException
	{
		setUpPageForms(model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		return getViewForPage(model);
	}

	@GetMapping("/footer")
	public String getStoreFinderFooterPage(@RequestParam(value = "query")
	final String query, final Model model) throws CMSItemNotFoundException
	{
		setUpPageForms(model);
		model.addAttribute("query", query);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		return getViewForPage(model);
	}

	@GetMapping( params = "q")
	public String findStores(@RequestParam(value = "page", defaultValue = "0")
	final int page, // NOSONAR
			@RequestParam(value = "show", defaultValue = "Page")
			final AbstractSearchPageController.ShowMode showMode, @RequestParam(value = "sort", required = false)
			final String sortCode, @RequestParam(value = "q")
			final String locationQuery, @RequestParam(value = "latitude", required = false)
			final Double latitude, @RequestParam(value = "longitude", required = false)
			final Double longitude, @RequestParam(value = "miles", required = false)
			final String locationQueryMiles, final SiteOneStoreFinderForm siteOneStoreFinderForm, final Model model,
			@RequestParam(value = "storeSpecialities", required = false)
			final String storeSpecialities, final BindingResult bindingResult) throws CMSItemNotFoundException
	{
		List<String> storeSpecialtyList = null;
		if (storeSpecialities != null)
		{
			storeSpecialtyList = new ArrayList<>(Arrays.asList(storeSpecialities.split(",")));
		}
		if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			if (longitude.doubleValue() > 0)
			{
				geoPoint.setLongitude(longitude.doubleValue() * -1);
			}

			setUpSearchResultsForPositionWithMiles(geoPoint, createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode),
					model, Double.parseDouble(locationQueryMiles), storeSpecialtyList);
		}
		else if (StringUtils.isNotBlank(locationQuery))
		{
			final String[] branchzipcode = locationQuery.split("-");
			setUpSearchResultsForLocationQueryMiles(branchzipcode[0],
					createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode), model,
					Double.parseDouble(locationQueryMiles), storeSpecialtyList);

			setUpMetaData(locationQuery, model);
			setUpPageForms(model);
			setUpPageTitle(locationQuery, model);

		}
		else
		{
			GlobalMessages.addErrorMessage(model, "storelocator.error.no.results.subtitle");
			model.addAttribute(WebConstants.BREADCRUMBS_KEY,
					storefinderBreadcrumbBuilder.getBreadcrumbsForLocationSearch(locationQuery));
		}

		storeCmsPageInModel(model, getStoreFinderPage());

		return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
	}

	@GetMapping(value = "/stores")
	public String findStoresForPopUp(@RequestParam(value = "latitude", required = true)
	final Double latitude, @RequestParam(value = "longitude", required = true)
	final Double longitude, @RequestParam(value = "miles", defaultValue = "0", required = false)
	final String locationQueryMiles, @RequestParam(value = "productCode", required = false)
	final String productCode, @RequestParam(value = "storeId", required = false)
	final String storeId, @RequestParam(value = "isNurseryCategory", required = false)
	final Boolean isNurseryCategory, final Model model)
	{
		
		if (storeId != null)
		{
			addSearchedStoreDetail(storeId,productCode,model);
		}
		
		if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			if (longitude.doubleValue() > 0)
			{
				geoPoint.setLongitude(longitude.doubleValue() * -1);
			}

			setUpSearchResultsForPositionWithMilesForPopup(geoPoint, createPageableData(0, getStorePageSize(), null, ShowMode.Page),
					model, Double.parseDouble(locationQueryMiles), productCode, isNurseryCategory);
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "storelocator.error.no.results.subtitle");
		}

		
		if (!getUserFacade().isAnonymousUser())
		{
			
			Collection<PointOfServiceData> savedBranches = ((SiteOneCustomerFacade) customerFacade).getMyStores();
			if(!StringUtils.isEmpty(productCode)) {
   			savedBranches.forEach(branch -> {
   				final String stockDetail = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode,branch);
   				branch.setStockDetail(stockDetail);
   			});
			} else {
				savedBranches.forEach(branch -> {
   				branch.setStockDetail("");
   			});
			}
			model.addAttribute(POINT_OF_SERVICE_DATA_ATTR, savedBranches);
		}
		return ControllerConstants.Views.Pages.StoreFinder.STORE_SEARCH_PAGE;
	}

	/**
	 * @param storeId
	 * @param productCode
	 * @param model
	 */
	private void addSearchedStoreDetail(String storeId, String productCode, Model model)
	{
		final PointOfServiceModel pointOfServiceModel = siteOneStoreFinderService.getStoreDetailForId(storeId);
		if (pointOfServiceModel == null)
		{
			model.addAttribute("searchedStore", "No store found.");
		}
		else
		{
			final PointOfServiceData pointOfServiceData = siteOnePointOfServiceConverter.convert(pointOfServiceModel);
			if(pointOfServiceData!=null) {
				if(!StringUtils.isEmpty(productCode)) {	   			
	   				final String stockDetail = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode,pointOfServiceData);
	   				pointOfServiceData.setStockDetail(stockDetail);	   			
				} else {					
						pointOfServiceData.setStockDetail("");
				}				
			}
			model.addAttribute(POINT_OF_SERVICE_DATA, pointOfServiceData);
		}
	}

	/**
	 * @param geoPoint
	 * @param createPageableData
	 * @param model
	 * @param locationQueryKms
	 */
	private void setUpSearchResultsForPositionWithMiles(final GeoPoint geoPoint, final PageableData pageableData,
			final Model model, final double locationQueryMiles, final List<String> storeSpecialities)
	{
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		if (CollectionUtils.isEmpty(storeSpecialities))
		{
			searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData, locationQueryMiles);
		}
		else
		{
			searchResult = ((SiteOneStoreFinderFacade) storeFinderFacade).positionSearchWithStoreSpecialities(geoPoint, pageableData,
					locationQueryMiles, storeSpecialities);
		}
		
		final GeoPoint newGeoPoint = new GeoPoint();
		newGeoPoint.setLatitude(searchResult.getSourceLatitude());
		newGeoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, newGeoPoint);
		setUpNoResultsErrorMessage(model, searchResult);

	}
	
	private void setUpSearchResultsForPositionWithMilesForPopup(final GeoPoint geoPoint, final PageableData pageableData,
			final Model model, final double locationQueryMiles, final String productCode, final Boolean isNurseryCategory)
	{
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		List<PointOfServiceData> pointofservices = new ArrayList<>();
		final PointOfServiceData data = ((PointOfServiceData) getSessionService().getAttribute("sessionStore"));
		searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData, locationQueryMiles);
		if(BooleanUtils.isTrue(isNurseryCategory) && data.getNurseryBuyingGroup() != null && storeSessionFacade.getNurseryNearbyBranchesFromSession() != null)
		{
			pointofservices = storeSessionFacade.getNurseryNearbyBranchesFromSession().stream().filter(pos -> !pos.getStoreId().equalsIgnoreCase(data.getStoreId())).collect(Collectors.toList());
		}
		else
		{
			searchResult.getResults().remove(0);
			pointofservices.addAll(searchResult.getResults());
		}
		if(searchResult != null && searchResult.getResults() != null)
		{
			searchResult.getResults().clear();
			searchResult.setResults(pointofservices);
		}
		

		if (!StringUtils.isEmpty(productCode) && searchResult != null && !CollectionUtils.isEmpty(searchResult.getResults()))
		{
			final String pdpStock = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode, data);
			for (final PointOfServiceData pos : searchResult.getResults())
			{
				String stockDetail = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode, pos);
				if (pdpStock.equals("outOfStock"))
				{
					if (stockDetail.equals("outOfStock"))
					{
						stockDetail = "notAvailable";
					}
				}
				else
				{
					 String mil = pos.getFormattedDistance();
					 String[] miles = mil.split(" ", 0);
					 double milesd = Double.parseDouble(miles[0]);
					Integer radius = data.getNearbyStoreSearchRadius();
					if (radius==null)
					{
						radius= getSiteConfigService().getInt("storefront.nearbystore.range.miles", 50);
					}
					if ( milesd > radius
							&& stockDetail.equals("outOfStock"))
					{
						stockDetail = "notAvailable";
					}
				}
				pos.setStockDetail(stockDetail);
			}
		}
		else
		{
			for (final PointOfServiceData pos : searchResult.getResults())
			{
				pos.setStockDetail("");
			}
		}

		final GeoPoint newGeoPoint = new GeoPoint();
		newGeoPoint.setLatitude(searchResult.getSourceLatitude());
		newGeoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, newGeoPoint);
		setUpNoResultsErrorMessage(model, searchResult);

	}

	@RequestMapping(value = "/position", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String searchByCurrentPosition(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final AbstractSearchPageController.ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, final StorePositionForm storePositionForm, final Model model) throws CMSItemNotFoundException
	{
		final GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(storePositionForm.getLatitude());
		geoPoint.setLongitude(storePositionForm.getLongitude());

		setUpSearchResultsForPosition(geoPoint, createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode), model);
		setUpPageForms(model);
		storeCmsPageInModel(model, getStoreFinderPage());

		return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
	}

	/**
	 * Get the Nearest GeoLocated Store
	 *
	 * @return
	 *
	 * @return the PointOfServiceModel
	 */
	@PostMapping("/neareststore")
	public @ResponseBody PointOfServiceData findNearestStore(@RequestParam(value = "latitude", required = false)
	final Double latitude, @RequestParam(value = "longitude", required = false)
	final Double longitude, @RequestParam(value = "userLocationConsent")
	final boolean userLocationConsent, final HttpServletRequest request, final HttpServletResponse response)
	{
		final Cookie cookie = WebUtils.getCookie(request, GEO_LOCATED_STORE_COOKIE);
		PointOfServiceData posData = null;
		if (null != latitude && null != longitude)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			if (longitude.doubleValue() > 0)
			{
				geoPoint.setLongitude(longitude.doubleValue() * -1);
			}
			posData = ((SiteOneStoreFinderFacade) storeFinderFacade).nearestStorePositionSearch(geoPoint);
			if (userLocationConsent && posData != null && getSessionService().getAttribute("geoPointStore") == null)
			{
				getSessionService().setAttribute("geoPointStore", posData.getStoreId());
			}
			if (null != posData)
			{
				if (null != cookie && (cookie.getValue().isEmpty() || userLocationConsent))
				{
					storeSessionFacade.setSessionStore(posData);
					cookie.setValue(StringUtils.normalizeSpace(posData.getStoreId()));
					cookie.setMaxAge(10 * 24 * 60 * 60);
					cookie.setSecure(true);
					response.addCookie(cookie);
					return posData;
				}
				else if (cookie == null)
				{
					storeSessionFacade.setSessionStore(posData);
					final Cookie newCookie = new Cookie(GEO_LOCATED_STORE_COOKIE, StringUtils.normalizeSpace(posData.getStoreId()));
					newCookie.setMaxAge(10 * 24 * 60 * 60);
					newCookie.setPath("/");
					newCookie.setSecure(true);
					response.addCookie(newCookie);
					return posData;
				}
			}
		}

		return storeSessionFacade.getSessionStore();
	}


	@GetMapping("/make-my-store/{storeId:.*}")
	public String makeMyStore(@RequestHeader(value = "referer", required = false)
	final String referer, @PathVariable(value = "storeId")
	final String storeId, final HttpServletRequest request, final HttpServletResponse response,
			final RedirectAttributes redirectAttributes)
	{
		Cookie cookie = WebUtils.getCookie(request, "csc");

		if (getUserFacade().isAnonymousUser())
		{
			if (null != cookie)
			{
				cookie.setMaxAge(0);
				cookie.setSecure(true);
				response.addCookie(cookie);

				final Cookie newCookie = new Cookie("csc", storeId);
				newCookie.setMaxAge(10 * 24 * 60 * 60);
				newCookie.setPath("/");
				newCookie.setSecure(true);
				response.addCookie(newCookie);
			}
			else
			{
				cookie = new Cookie("csc", storeId);
				cookie.setMaxAge(10 * 24 * 60 * 60);
				cookie.setPath("/");
				cookie.setSecure(true);
				response.addCookie(cookie);
			}
		}
		((SiteOneCustomerFacade) customerFacade).makeMyStore(storeId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.preferredstore.added", null);

		if (!getUserFacade().isAnonymousUser() && null != cookie)
		{
			eraseCookie(cookie, response);
		}

		redirectAttributes.addAttribute("confirmStore", true);
		cxService.calculateAndLoadPersonalizationInSession(userService.getCurrentUser());
		final String a = getCommonI18NService().getCurrentLanguage().getIsocode().toString().toLowerCase();

		if (referer != null)
		{
		if (referer.contains("/es/") || referer.contains("/en/"))
		{
			return REDIRECT_PREFIX + referer;
		}
		else
		{
			if (referer.endsWith("/es") || referer.endsWith("/en"))
			{
				return REDIRECT_PREFIX + referer + "/";
			}
			else if (StringUtils.contains(StringUtils.substringAfter(referer, "https://"), "/"))
			{
				return REDIRECT_PREFIX + "https://"
						+ StringUtils.replaceOnce(StringUtils.substringAfter(referer, "https://"), "/", "/" + a + "/");
			}
			else if (referer.endsWith("/"))
			{
				return REDIRECT_PREFIX + referer + a + "/";
			}
			return REDIRECT_PREFIX + referer + "/" + a + "/";
		}
		}
		return null;
	}


	@PostMapping("/GeoPOS")
	public @ResponseBody PointOfServiceData findGeoStores(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final AbstractSearchPageController.ShowMode showMode, @RequestParam(value = "sort", required = false)
	final String sortCode, @RequestParam(value = "q")
	final String locationQuery, @RequestParam(value = "miles", required = false)
	final String locationQueryMiles, final Model model, final HttpServletRequest request, final HttpServletResponse response)

	{
		PointOfServiceData pos = null;

		if (StringUtils.isNotBlank(locationQuery))
		{

			final PageableData pageableData = createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode);

			final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.locationSearch(locationQuery,
					pageableData, Double.parseDouble(locationQueryMiles));
			if (searchResult.getResults().isEmpty())
			{
				return pos;
			}
			else
			{
				pos = searchResult.getResults().get(0);
			}


		}
		else
		{
			GlobalMessages.addErrorMessage(model, "storelocator.error.no.results.subtitle");
		}

		return pos;
	}


	// setup methods to populate the model
	protected void setUpMetaData(final String locationQuery, final Model model)
	{
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_FOLLOW);
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(locationQuery);
		final String metaDescription = MetaSanitizerUtil
				.sanitizeDescription(getSiteName() + " " + getMessageSource().getMessage("storeFinder.meta.description.results", null,
						"storeFinder.meta.description.results", getI18nService().getCurrentLocale()) + " " + locationQuery);
		super.setUpMetaData(model, metaKeywords, metaDescription);
	}

	protected void setUpNoResultsErrorMessage(final Model model, final StoreFinderSearchPageData<PointOfServiceData> searchResult)
	{
		if (searchResult.getResults().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "storelocator.error.no.results.subtitle");
		}
	}

	protected void setUpPageData(final Model model, final StoreFinderSearchPageData<PointOfServiceData> searchResult,
			final List<Breadcrumb> breadCrumbsList)
	{
		populateModel(model, searchResult, ShowMode.Page);
		getMyStoresIdList();
		model.addAttribute("locationQuery", StringEscapeUtils.escapeHtml4(searchResult.getLocationText()));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadCrumbsList);
	}

	protected void setUpSearchResultsForPosition(final GeoPoint geoPoint, final PageableData pageableData, final Model model)
	{
		// Run the location search & populate the model
		final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData);

		final GeoPoint newGeoPoint = new GeoPoint();
		newGeoPoint.setLatitude(searchResult.getSourceLatitude());
		newGeoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, newGeoPoint);
		setUpNoResultsErrorMessage(model, searchResult);
	}

	protected void setUpPosition(final Model model, final GeoPoint geoPoint)
	{
		model.addAttribute("geoPoint", geoPoint);
	}

	protected void setUpSearchResultsForLocationQuery(final String locationQuery, final PageableData pageableData,
			final Model model)
	{
		// Run the location search & populate the model

		final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.locationSearch(locationQuery,
				pageableData);
		final GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(searchResult.getSourceLatitude());
		geoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(geoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForLocationSearch(locationQuery));
		setUpNoResultsErrorMessage(model, searchResult);
	}

	protected void updateLocalUserPreferences(final GeoPoint geoPoint, final String location)
	{
		final UserLocationData userLocationData = new UserLocationData();
		userLocationData.setSearchTerm(location);
		userLocationData.setPoint(geoPoint);
		customerLocationService.setUserLocation(userLocationData);
	}

	protected void setUpPageForms(final Model model)
	{
		final StorePositionForm storePositionForm = new StorePositionForm();
		final SiteOneStoreFinderForm siteOneStoreFinderForm = new SiteOneStoreFinderForm();
		model.addAttribute("storePositionForm", storePositionForm);
		model.addAttribute("siteOneStoreFinderForm", siteOneStoreFinderForm);

	}

	protected void setUpPageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(model,
				getPageTitleResolver().resolveContentPageTitle(getMessageSource().getMessage("storeFinder.meta.title", null,
						"storeFinder.meta.title", getI18nService().getCurrentLocale()) + " " + searchText));
	}

	protected AbstractPageModel getStoreFinderPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId(STORE_FINDER_CMS_PAGE_LABEL);
	}

	/**
	 * Get the default search page size.
	 *
	 * @return the number of results per page, <tt>0</tt> (zero) indicated 'default' size should be used
	 */
	protected int getStoreLocatorPageSize()
	{
		return getSiteConfigService().getInt("storefront.storelocator.pageSize", 0);
	}
	
	/**
	 * Get the default search page size.
	 *
	 * @return the number of results per page, <tt>0</tt> (zero) indicated 'default' size should be used
	 */
	protected int getStorePageSize()
	{
		return getSiteConfigService().getInt("storefront.store.pageSize", 0);
	}

	protected void setUpSearchResultsForLocationQueryMiles(final String locationQuery, final PageableData pageableData,
			final Model model, final double locationQueryMiles, final List<String> storeSpecialities)
	{
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		if (CollectionUtils.isEmpty(storeSpecialities))
		{
			searchResult = storeFinderFacade.locationSearch(locationQuery, pageableData, locationQueryMiles);
		}
		else
		{
			searchResult = ((SiteOneStoreFinderFacade) storeFinderFacade).locationSearchWithStoreSpecialities(locationQuery,
					pageableData, locationQueryMiles, storeSpecialities);
		}

		final GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(searchResult.getSourceLatitude());
		geoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(geoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForLocationSearch(locationQuery));
		setUpNoResultsErrorMessage(model, searchResult);
	}

	@GetMapping("/remove-my-store/" + STORE_ID_PATH_VARIABLE_PATTERN)
	public String removeFromMyStore(@RequestHeader(value = "referer", required = false)
	final String referer, @PathVariable("storeId")
	final String storeId, final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final Cookie geoLocatedCookie = this.getGeoLocatedCookie(request);

		String geoLocatedStoreId = null;

		if (null != geoLocatedCookie)
		{
			geoLocatedStoreId = geoLocatedCookie.getValue();
		}

		((SiteOneCustomerFacade) customerFacade).removeFromMyStore(storeId, geoLocatedStoreId);

		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.storeFromList.removed", null);
		return REDIRECT_PREFIX + referer;
	}

	/**
	 * @param cookie
	 * @param response
	 */
	protected void eraseCookie(final Cookie confirmedStoreCookie, final HttpServletResponse response)
	{
		confirmedStoreCookie.setValue(null);
		confirmedStoreCookie.setMaxAge(0);
		confirmedStoreCookie.setSecure(true);
		response.addCookie(confirmedStoreCookie);
	}

	/**
	 * @param request
	 */
	protected Cookie getGeoLocatedCookie(final HttpServletRequest request)
	{
		return WebUtils.getCookie(request, GEO_LOCATED_STORE_COOKIE);
	}


	@ModelAttribute("myStoresIdList")
	public List<String> getMyStoresIdList()
	{
		return (((SiteOneCustomerFacade) customerFacade).getMyStoresIdList());
	}

}
