package com.siteone.v2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.WebUtils;

import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;

import de.hybris.platform.commercefacades.order.CartFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.siteone.commerceservice.dto.store.StoreFinderSearchPageDTO;
import com.siteone.commerceservice.dto.store.StoreSearchPageDTO;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facades.customer.SiteOneCustomerFacade;
import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.facades.store.SiteOneStoreDetailsFacade;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.store.data.PointOfServiceListData;
import com.siteone.utils.XSSFilterUtil;
import de.hybris.platform.servicelayer.user.UserService;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.acceleratorservices.store.data.UserLocationData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;

import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceWsDTO;
import de.hybris.platform.util.Config;
import org.springframework.security.access.annotation.Secured;
import org.apache.log4j.Logger;

@Controller
@RequestMapping(value = "/{baseSiteId}/store-finder")
@Tag(name = "SiteOne Store Locator")
public class SiteOneStoreLocatorController  extends BaseController
{
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@Resource(name = "siteOnestoreDetailsFacade")
	private SiteOneStoreDetailsFacade siteOnestoreDetailsFacade;
	
	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;
	
	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	
	@Resource(name = "userService")
	private UserService userService;
	
	private static final Logger LOG = Logger.getLogger(SiteOneStoreLocatorController.class);
	private static final String GEO_LOCATED_STORE_COOKIE = "gls";
	private static final String SUCCESS = "Success";
	private static final String SPECIALITY_HARDSCAPES = Config.getString("hardscapes.specialities", "Hardscape");
	private static final String EXCEPTION = "Exception occurred while calling the method";
	
	@PutMapping("/make-my-store/{storeId}")
	@ApiBaseSiteIdParam
	@ResponseStatus(HttpStatus.OK)
	@Operation(operationId = "make-my-store", summary = "make it the preferred store", description = "make it the preferred store for the loggedin customer and add it to the list of stores of customer.")
	public void makeMyStore(@Parameter(description = "Store identifier (currently store id)", required = true) @PathVariable final String storeId)
	{
		((SiteOneCustomerFacade) customerFacade).makeMyStore(storeId);
	}

	@GetMapping("/store")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "getStoreDetail", summary = "Fetch the store details", description = "Fetch the store details")
	public PointOfServiceWsDTO storeDetailForCode(@RequestParam("storeId") final String storeId, @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		try {
		final PointOfServiceData pointOfServiceData = siteOnestoreDetailsFacade.fetchSiteOnePointOfService(storeId);
		PointOfServiceWsDTO pointOfServiceWsDTO = getDataMapper().map(pointOfServiceData, PointOfServiceWsDTO.class, fields);
		boolean isGuestCheckoutEnabled = ((SiteOneCartFacade) cartFacade).isGuestCheckoutEnabled(storeId);
		if(pointOfServiceWsDTO != null)
		{
		   pointOfServiceWsDTO.setIsGuestCheckoutEnabled(isGuestCheckoutEnabled);
		   pointOfServiceWsDTO.setEnableOnlineFulfillment(pointOfServiceData.getEnableOnlineFulfillment());
		   return ((SiteOneCustomerFacade) customerFacade).getCustomerStoreData(pointOfServiceWsDTO, storeId);
		}
		   return pointOfServiceWsDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" storeDetailForCode");
		}
	}
	
	@GetMapping( params = "q")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "findStores", summary = "Fetch the List of stores", description = "Fetch the List of stores based on search")
	public StoreFinderSearchPageDTO findStores(@RequestParam(value = "page", defaultValue = "0") final int page, // NOSONAR
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "q") final String locationQuery,
			@RequestParam(value = "latitude", required =  false) final Double latitude,
			@RequestParam(value = "longitude", required = false) final Double longitude,
			@RequestParam(value = "miles", required = false) final String locationQueryMiles,
			@RequestParam(value = "storeSpecialities", required = false) final List<String> storeSpecialities)
	{
		try {
		final String sanitizedSearchQuery = XSSFilterUtil.filter(locationQuery);
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		StoreFinderSearchPageDTO searchPageResult = new StoreFinderSearchPageDTO();
		if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			geoPoint.setLongitude(longitude.doubleValue());

			searchResult = setUpSearchResultsForPositionWithMiles(geoPoint, createPageableData(page, getStoreLocatorPageSize(), 
					sortCode),Double.parseDouble(locationQueryMiles), storeSpecialities);
			
		}
		else if (StringUtils.isNotBlank(sanitizedSearchQuery))
		{

			searchResult = setUpSearchResultsForLocationQueryMiles(sanitizedSearchQuery,
					createPageableData(page, getStoreLocatorPageSize(), sortCode), Double.parseDouble(locationQueryMiles),
					storeSpecialities);

		}
		if(searchResult == null || CollectionUtils.isEmpty(searchResult.getResults()))
		{
			return searchPageResult;
		}
		
		searchPageResult.setStores(searchResult.getResults());
		searchPageResult.setBoundNorthLatitude(searchResult.getBoundNorthLatitude());
		searchPageResult.setBoundEastLongitude(searchResult.getBoundEastLongitude());
		searchPageResult.setBoundSouthLatitude(searchResult.getBoundSouthLatitude());
		searchPageResult.setBoundWestLongitude(searchResult.getBoundWestLongitude());
		searchPageResult.setLocationText(searchResult.getLocationText());
		searchPageResult.setPagination(searchResult.getPagination());
		searchPageResult.setSourceLatitude(searchResult.getSourceLatitude());
		searchPageResult.setSourceLongitude(searchResult.getSourceLongitude());
		return searchPageResult;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" findStores");
		}
	}
	
	@GetMapping("/remove-my-store/{storeId}")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "removeMyStores", summary = "Remove my default store", description = "Remove my default store")
	public String removeFromMyStore(@RequestHeader(value = "referer", required = false) final String referer,
			@PathVariable("storeId") final String storeId,final HttpServletRequest request,
			final HttpServletResponse response)
	{
		try {
		final Cookie geoLocatedCookie = this.getGeoLocatedCookie(request);

		String geoLocatedStoreId = null;

		if (null != geoLocatedCookie)
		{
			geoLocatedStoreId = geoLocatedCookie.getValue();
		}

		((SiteOneCustomerFacade) customerFacade).removeFromMyStore(storeId, geoLocatedStoreId);

		Gson gson = new Gson();
		return gson.toJson(SUCCESS);
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" removeFromMyStore");
		}
	}
	
	/**
	 * Get the Nearest GeoLocated Store
	 *
	 * @return
	 *
	 * @return the PointOfServiceModel
	 */
	@PostMapping("/nearest-store")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "neareststore", summary = "Nearest GeoLocated Store", description = "Nearest GeoLocated Store")
	public PointOfServiceWsDTO findNearestStore(
			@RequestParam(value = "latitude", required = false) final Double latitude,
			@RequestParam(value = "longitude", required = false) final Double longitude,
			@RequestParam(value = "userLocationConsent") final boolean userLocationConsent, final HttpServletRequest request,
			final HttpServletResponse response, @ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		try {
		PointOfServiceData posData = null;
		PointOfServiceWsDTO pointOfServiceWsDTO = null;
		if(null != latitude && null != longitude)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			geoPoint.setLongitude(longitude.doubleValue());
			posData = ((SiteOneStoreFinderFacade) storeFinderFacade).nearestStorePositionSearch(geoPoint);
			if (null != posData)
			{
				final Cookie cookie = WebUtils.getCookie(request, GEO_LOCATED_STORE_COOKIE);
				if (null != cookie)
				{
					cookie.setValue(StringUtils.normalizeSpace(posData.getStoreId()));
					cookie.setMaxAge(10 * 24 * 60 * 60);
					cookie.setSecure(true);
					response.addCookie(cookie);
				}
				else
				{
					final Cookie newCookie = new Cookie(GEO_LOCATED_STORE_COOKIE, StringUtils.normalizeSpace(posData.getStoreId()));
					newCookie.setMaxAge(10 * 24 * 60 * 60);
					newCookie.setPath("/");
					newCookie.setSecure(true);
					response.addCookie(newCookie);
				}
				boolean isGuestCheckoutEnabled = ((SiteOneCartFacade) cartFacade).isGuestCheckoutEnabled(posData.getStoreId());
				pointOfServiceWsDTO = getDataMapper().map(posData, PointOfServiceWsDTO.class, fields);
				if(pointOfServiceWsDTO != null)
				{				 
				   pointOfServiceWsDTO.setIsGuestCheckoutEnabled(isGuestCheckoutEnabled);
				   pointOfServiceWsDTO.setEnableOnlineFulfillment(posData.getEnableOnlineFulfillment());
				   ((SiteOneCustomerFacade) customerFacade).getCustomerStoreData( pointOfServiceWsDTO , posData.getStoreId());
				}
			}
		}
		
		return pointOfServiceWsDTO;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" findNearestStore");
		}
		
	}
	
	@GetMapping("/my-stores")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "myStores", summary = "Fetch the my store list", description = "Fetch the my store list")
	public PointOfServiceListWsDTO getMyStores()
	{
		try {
		final PointOfServiceListData pointsOfService = new PointOfServiceListData();
		List<PointOfServiceData> pointsOfServiceList = new ArrayList<>(((SiteOneCustomerFacade) customerFacade).getMyStores());
		pointsOfService.setPointOfServices(pointsOfServiceList);
		final PointOfServiceListWsDTO list = getDataMapper().map(pointsOfService, PointOfServiceListWsDTO.class, "FULL");
		if(!userFacade.isAnonymousUser() && list != null && list.getPointOfServices() != null && !list.getPointOfServices().isEmpty())
		{
			for(PointOfServiceWsDTO pos : list.getPointOfServices())
			{	
				pos.setIsSplitMixedCartEnabledBranch(
						siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",pos.getStoreId()));
			}
		}
		return list;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getMyStores");
		}
	}
	
	@PostMapping("/hardscapes/state")
	@ApiBaseSiteIdParam
	@Operation(operationId = "state", summary = "Fetch the store list by state", description = "Fetch the store list by state")
	public @ResponseBody TreeMap<String, List<PointOfServiceData>> findGeoStores(@RequestParam(value = "state")
			final String state)

	{
		try {
		updateLocalUserPreferences(null, state);
		TreeMap<String, List<PointOfServiceData>> pos = ((SiteOneStoreFinderFacade) storeFinderFacade).getListofStoresFromSpecialty(state, SPECIALITY_HARDSCAPES);
		return pos;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" findGeoStores");
		}
	}

	@GetMapping(value = "/getStoreData")
	@ApiBaseSiteIdParam
	@Operation(operationId = "getStoreData", summary = "Fetch the list of storeData based on zipcode", description = "Fetch the list of storeData based on zipcode and distance")
	public @ResponseBody PointOfServiceListWsDTO getStoreData(@RequestParam(value = "zipcode")
	final String zipcode,  @RequestParam(value = "miles", required = false)
	final String miles)
	{
		try {
		final  PointOfServiceListWsDTO pointsOfService = new  PointOfServiceListWsDTO();
		List<PointOfServiceWsDTO> storeList = new ArrayList<>();
		for(PointOfServiceData pointOfServiceData :  ((SiteOneStoreFinderFacade) storeFinderFacade).getStoresForZipcode(zipcode, Double.parseDouble(miles)) ) {
			PointOfServiceWsDTO pointOfServiceWsDTO = getDataMapper().map(pointOfServiceData, PointOfServiceWsDTO.class);
			pointOfServiceWsDTO.setIsMixedCartEnabled(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",pointOfServiceWsDTO.getStoreId()));
			if(!userFacade.isAnonymousUser())
			{
				pointOfServiceWsDTO.setIsSplitMixedCartEnabledBranch(
						siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",pointOfServiceWsDTO.getStoreId()));
			}
			storeList.add(pointOfServiceWsDTO);
		}
		pointsOfService.setPointOfServices(storeList);
		return pointsOfService;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" getStoreData");
		}
	}
	
	@Secured({ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@PutMapping(value = "/save-store/{storeId}")
	@ApiBaseSiteIdParam
	@ResponseStatus(HttpStatus.OK)
	@Operation(operationId = "save-store", summary = "add it to the list of stores", description = "add it to the list of stores of customer.")
	public void saveStore(@Parameter(description = "Store identifier (currently store id)", required = true) @PathVariable final String storeId)
	{
		((SiteOneCustomerFacade) customerFacade).saveStore(storeId);
	}
	
	protected PageableData createPageableData(final int pageNumber, final int pageSize, final String sortCode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);
		pageableData.setPageSize(pageSize);
		return pageableData;
	}
	protected int getStoreLocatorPageSize()
	{
		return siteConfigService.getInt("storefront.storelocator.pageSize", 0);
	}

	protected int getStorePageSize()
	{
		return siteConfigService.getInt("storefront.store.pageSize", 0);
	}
	
	@GetMapping(value = "/stores")
	@ApiBaseSiteIdParam
	@ResponseBody
	@Operation(operationId = "stores", summary = "Fetch the list of stores for popup", description = "Fetch the list of stores for popup")
	public StoreSearchPageDTO findStoresForPopUp(@RequestParam(value = "latitude", required = true)
	final Double latitude, @RequestParam(value = "longitude", required = true)
	final Double longitude, @RequestParam(value = "miles", defaultValue = "0", required = false)
	final String locationQueryMiles, @RequestParam(value = "productCode", required = false)
	final String productCode, @RequestParam(value = "storeid" , required = false)
	final String storeId,@RequestParam(value = "isNurseryCategory", required = false)
	final Boolean isNurseryCategory, @RequestParam(value = "homeStoreId" , required = false)
	final String homeStoreId)
	{
		try {
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		StoreFinderSearchPageDTO searchPageResult = new StoreFinderSearchPageDTO();
		StoreSearchPageDTO storeSearchResult = new StoreSearchPageDTO();
		StoreSearchPageDTO rawSearchResult = new StoreSearchPageDTO();
		if(homeStoreId != null)
		{
			final PointOfServiceData pointOfServiceData = ((SiteOneStoreFinderFacade) storeFinderFacade).getStoreForId(homeStoreId);
			storeSessionFacade.setSessionStore(pointOfServiceData);	
		}	
		PointOfServiceData pos=null;
		if(storeId!=null) {
			pos = siteOnestoreDetailsFacade.searchStoreDetailForPopup(storeId,productCode);
			rawSearchResult.setSearchedStore(pos);
			return rawSearchResult;
		}

		if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			geoPoint.setLongitude(longitude.doubleValue());

			searchResult = setUpSearchResultsForPositionWithMilesForPopup(geoPoint, createPageableData(0, getStorePageSize(), null),
					 Double.parseDouble(locationQueryMiles), productCode, isNurseryCategory);
		}
		
		if(searchResult == null || CollectionUtils.isEmpty(searchResult.getResults()))
		{
			return storeSearchResult;
		}
		
		searchPageResult.setStores(searchResult.getResults());
		searchPageResult.setBoundNorthLatitude(searchResult.getBoundNorthLatitude());
		searchPageResult.setBoundEastLongitude(searchResult.getBoundEastLongitude());
		searchPageResult.setBoundSouthLatitude(searchResult.getBoundSouthLatitude());
		searchPageResult.setBoundWestLongitude(searchResult.getBoundWestLongitude());
		searchPageResult.setLocationText(searchResult.getLocationText());
		searchPageResult.setPagination(searchResult.getPagination());
		searchPageResult.setSourceLatitude(searchResult.getSourceLatitude());
		searchPageResult.setSourceLongitude(searchResult.getSourceLongitude());
		
		storeSearchResult.setNearByStores(searchPageResult);
		if (!userService.isAnonymousUser(userService.getCurrentUser()))
		{
			
			List<PointOfServiceData> savedStores = new ArrayList<>(((SiteOneCustomerFacade) customerFacade).getMyStores());
			if(!StringUtils.isEmpty(productCode)) {
				savedStores.forEach(branch -> {
   				final String stockDetail = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode,branch);
   				branch.setStockDetail(stockDetail);
   			});
			} else {
				savedStores.forEach(branch -> 
   				branch.setStockDetail("")
   			);
			}
			storeSearchResult.setSaveStores(savedStores);
		}
			
		return storeSearchResult;
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(EXCEPTION +" findStoresForPopUp");
		}

	}
	
	private StoreFinderSearchPageData<PointOfServiceData> setUpSearchResultsForPositionWithMilesForPopup(final GeoPoint geoPoint, final PageableData pageableData,
			final double locationQueryMiles, final String productCode, final Boolean isNurseryCategory)
	{
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		final PointOfServiceData data = storeSessionFacade.getSessionStore();
		List<PointOfServiceData> pointofservices = new ArrayList<>();
		searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData, locationQueryMiles);
		if(BooleanUtils.isTrue(isNurseryCategory) && storeSessionFacade.getNurseryNearbyBranchesFromSession() != null)
		{
			pointofservices = storeSessionFacade.getNurseryNearbyBranchesFromSession().stream()
					.filter(pos -> !pos.getStoreId().equalsIgnoreCase(data.getStoreId())).collect(Collectors.toList());
		}
		else if(searchResult != null && !CollectionUtils.isEmpty(searchResult.getResults()))
		{
			searchResult.getResults().remove(0);
			pointofservices.addAll(searchResult.getResults());
		}
		if(searchResult != null && searchResult.getResults() != null)
		{
			searchResult.getResults().clear();
			searchResult.setResults(pointofservices);
		}
		if(searchResult != null && !CollectionUtils.isEmpty(searchResult.getResults())) {
			if(!StringUtils.isEmpty(productCode)) {		
		   		for(PointOfServiceData pos: searchResult.getResults()) {
		   			final String stockDetail = siteOneProductFacade.populateStoreLevelStockInfoDataForPopup(productCode,pos);
		   			pos.setStockDetail(stockDetail);
		   		}
			} else {
				for(PointOfServiceData pos: searchResult.getResults()) {
					pos.setStockDetail("");
				}
			}		
		
			final GeoPoint newGeoPoint = new GeoPoint();
			newGeoPoint.setLatitude(searchResult.getSourceLatitude());
			newGeoPoint.setLongitude(searchResult.getSourceLongitude());
	
			updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		}
		return searchResult;
	}
	
	/**
	 * @param geoPoint
	 * @param createPageableData
	 * @param model
	 * @param locationQueryKms
	 */
	private StoreFinderSearchPageData<PointOfServiceData> setUpSearchResultsForPositionWithMiles(final GeoPoint geoPoint, final PageableData pageableData,
			 final double locationQueryMiles, final List<String> storeSpecialities)
	{
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		if(CollectionUtils.isEmpty(storeSpecialities)){
			searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData, locationQueryMiles);
		}
		else{
			searchResult = ((SiteOneStoreFinderFacade) storeFinderFacade).positionSearchWithStoreSpecialities(geoPoint, pageableData,
					locationQueryMiles,storeSpecialities);
		}

		final GeoPoint newGeoPoint = new GeoPoint();
		newGeoPoint.setLatitude(searchResult.getSourceLatitude());
		newGeoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		return searchResult;
	}
	
	protected StoreFinderSearchPageData<PointOfServiceData> setUpSearchResultsForLocationQueryMiles(final String locationQuery, final PageableData pageableData,
			 final double locationQueryMiles, final List<String> storeSpecialities)
	{
		
		StoreFinderSearchPageData<PointOfServiceData> searchResult = null;
		if(CollectionUtils.isEmpty(storeSpecialities)){
			searchResult = storeFinderFacade.locationSearch(locationQuery,
					pageableData, locationQueryMiles);
		}
		else{
			searchResult = ((SiteOneStoreFinderFacade) storeFinderFacade).locationSearchWithStoreSpecialities(locationQuery,
					pageableData, locationQueryMiles,storeSpecialities);
		}

		final GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(searchResult.getSourceLatitude());
		geoPoint.setLongitude(searchResult.getSourceLongitude());

		updateLocalUserPreferences(geoPoint, searchResult.getLocationText());
		return searchResult;
	}
	
	
	protected void updateLocalUserPreferences(final GeoPoint geoPoint, final String location)
	{
		final UserLocationData userLocationData = new UserLocationData();
		userLocationData.setSearchTerm(location);
		userLocationData.setPoint(geoPoint);
		customerLocationService.setUserLocation(userLocationData);
	}
	
	/**
	 * @param request
	 */
	protected Cookie getGeoLocatedCookie(final HttpServletRequest request)
	{
		return WebUtils.getCookie(request, GEO_LOCATED_STORE_COOKIE);
	}
	
}