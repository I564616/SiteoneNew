/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.v2.controller;


import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.store.StoreCountListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.store.StoreFinderSearchPageWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.RequestParameterException;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import com.siteone.store.data.PointOfServiceListData;
import com.siteone.store.data.StoreCountListData;
import com.siteone.v2.helper.StoresHelper;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Hidden;

@Controller
@CacheControl(directive = CacheControlDirective.PUBLIC, maxAge = 1800)
@RequestMapping(value = "/{baseSiteId}/stores")
@Tag(name = "Stores")
@Hidden
public class StoresController extends BaseController
{
	private static final String DEFAULT_SEARCH_RADIUS_METRES = "100000.0";
	private static final String DEFAULT_ACCURACY = "0.0";

	@Resource(name = "storesHelper")
	private StoresHelper storesHelper;
	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@GetMapping
	@ResponseBody
	@Operation(operationId = "getStoreLocations", summary = "Get a list of store locations", description = "Lists all store locations that are near the location specified in a query or based on latitude and longitude.")
	@ApiBaseSiteIdParam
	public StoreFinderSearchPageWsDTO getStoreLocations(
			@Parameter(description = "Location in natural language i.e. city or country.") @RequestParam(required = false) final String query,
			@Parameter(description = "Coordinate that specifies the north-south position of a point on the Earth's surface.") @RequestParam(required = false) final Double latitude,
			@Parameter(description = "Coordinate that specifies the east-west position of a point on the Earth's surface.") @RequestParam(required = false) final Double longitude,
			@Parameter(description = "The current result page requested.") @RequestParam(defaultValue = DEFAULT_CURRENT_PAGE) final int currentPage,
			@Parameter(description = "The number of results returned per page.") @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pageSize,
			@Parameter(description = "Sorting method applied to the return results.") @RequestParam(defaultValue = "asc") final String sort,
			@Parameter(description = "Radius in meters. Max value: 40075000.0 (Earth's perimeter).") @RequestParam(defaultValue = DEFAULT_SEARCH_RADIUS_METRES) final double radius,
			@Parameter(description = "Accuracy in meters.") @RequestParam(defaultValue = DEFAULT_ACCURACY) final double accuracy,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields, final HttpServletResponse response)
			throws RequestParameterException //NOSONAR
	{
		final StoreFinderSearchPageWsDTO result = storesHelper
				.locationSearch(query, latitude, longitude, currentPage, pageSize, sort, radius, accuracy,
						addPaginationField(fields));

		// X-Total-Count header
		setTotalCountHeader(response, result.getPagination());

		return result;
	}

	@GetMapping({ "/country/{countryIso}" })
	@ResponseBody
	@Operation(operationId = "getStoresByCountry", summary = "Get a list of store locations for a given country", description = "Lists all store locations that are in the specified country.")
	@ApiBaseSiteIdParam
	public PointOfServiceListWsDTO getStoresByCountry(
			@Parameter(description = "Country ISO code", required = true) @PathVariable final String countryIso,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final PointOfServiceListData pointsOfService = new PointOfServiceListData();
		pointsOfService.setPointOfServices(storeFinderFacade.getPointsOfServiceForCountry(countryIso));

		return getDataMapper().map(pointsOfService, PointOfServiceListWsDTO.class, fields);
	}

	@GetMapping({ "/country/{countryIso}/region/{regionIso}" })
	@ResponseBody
	@Operation(operationId = "getStoresByCountryAndRegion", summary = "Get a list of store locations for a given country and region", description = "Lists all store locations that are in the specified country and region.")
	@ApiBaseSiteIdParam
	public PointOfServiceListWsDTO getStoresByCountryAndRegion(
			@Parameter(description = "Country ISO code", required = true) @PathVariable final String countryIso,
			@Parameter(description = "Region ISO code", required = true) @PathVariable final String regionIso,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final PointOfServiceListData pointsOfService = new PointOfServiceListData();
		pointsOfService.setPointOfServices(storeFinderFacade.getPointsOfServiceForRegion(countryIso, regionIso));

		return getDataMapper().map(pointsOfService, PointOfServiceListWsDTO.class, fields);
	}

	@RequestMapping(method = RequestMethod.HEAD)
	@Operation(operationId = "countStoreLocations", summary = "Get a header with the number of store locations.", description =
			"In the response header, the \"x-total-count\" indicates the number of "
					+ "all store locations that are near the location specified in a query, or based on latitude and longitude.")
	@ApiBaseSiteIdParam
	public void countStoreLocations(
			@Parameter(description = "Location in natural language i.e. city or country.") @RequestParam(required = false) final String query,
			@Parameter(description = "Coordinate that specifies the north-south position of a point on the Earth's surface.") @RequestParam(required = false) final Double latitude,
			@Parameter(description = "Coordinate that specifies the east-west position of a point on the Earth's surface.") @RequestParam(required = false) final Double longitude,
			@Parameter(description = "Radius in meters. Max value: 40075000.0 (Earth's perimeter).") @RequestParam(defaultValue = DEFAULT_SEARCH_RADIUS_METRES) final double radius,
			@Parameter(description = "Accuracy in meters.") @RequestParam(defaultValue = DEFAULT_ACCURACY) final double accuracy,
			final HttpServletResponse response) throws RequestParameterException //NOSONAR
	{
		final StoreFinderSearchPageData<PointOfServiceData> result = storesHelper
				.locationSearch(query, latitude, longitude, 0, 1, "asc", radius, accuracy);

		// X-Total-Count header
		setTotalCountHeader(response, result.getPagination());
	}


	@GetMapping("/{storeId}")
	@Operation(operationId = "getStoreLocation", summary = "Get a store location", description = "Returns store location based on its unique name.")
	@ApiBaseSiteIdParam
	@ResponseBody
	public PointOfServiceWsDTO getStoreLocation(
			@Parameter(description = "Store identifier (currently store name)", required = true) @PathVariable final String storeId,
			@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		return storesHelper.locationDetails(storeId, fields);
	}

	@GetMapping("/storescounts")
	@Operation(operationId = "getLocationCounts", summary = "Gets a store location count per country and regions", description = "Returns store counts in countries and regions")
	@ApiBaseSiteIdParam
	@ResponseBody
	public StoreCountListWsDTO getLocationCounts()
	{
		final StoreCountListData storeCountListData = new StoreCountListData();
		storeCountListData.setCountriesAndRegionsStoreCount(storeFinderFacade.getStoreCounts());
		return getDataMapper().map(storeCountListData, StoreCountListWsDTO.class);
	}
}
