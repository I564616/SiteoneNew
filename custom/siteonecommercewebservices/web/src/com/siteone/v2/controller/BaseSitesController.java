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

import de.hybris.platform.commercefacades.basesite.data.BaseSiteData;
import de.hybris.platform.commercefacades.basesites.BaseSiteFacade;
import de.hybris.platform.commercewebservicescommons.dto.basesite.BaseSiteListWsDTO;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.swagger.ApiFieldsParam;
import com.siteone.basesite.data.BaseSiteDataList;

import jakarta.annotation.Resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Hidden;

@Controller
@RequestMapping(value = "/basesites")
@CacheControl(directive = CacheControlDirective.PUBLIC, maxAge = 360)
@Tag(name = "Base Sites")
@Hidden
public class BaseSitesController extends BaseController
{
	@Resource(name = "baseSiteFacade")
	private BaseSiteFacade baseSiteFacade;

	@GetMapping
	@ResponseBody
	@Operation(operationId = "getBaseSites", summary = "Get all base sites.", description = "Get all base sites with corresponding base stores details in FULL mode.")
	public BaseSiteListWsDTO getBaseSites(@ApiFieldsParam @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final List<BaseSiteData> allBaseSites = baseSiteFacade.getAllBaseSites();
		final BaseSiteDataList baseSiteDataList = new BaseSiteDataList();
		baseSiteDataList.setBaseSites(allBaseSites);
		return getDataMapper().map(baseSiteDataList, BaseSiteListWsDTO.class, fields);
	}
}
