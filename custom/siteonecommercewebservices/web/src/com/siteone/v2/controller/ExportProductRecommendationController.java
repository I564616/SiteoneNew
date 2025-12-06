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

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.siteone.facades.product.SiteOneProductFacade;
import com.siteone.integration.product.data.SiteOneWsProductResponseData;
import com.siteone.integration.product.data.SiteOneWsCategoryResponseData;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.RequestParameterException;
import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.MediaType;

/**
 * Web Services Controller to expose the functionality of the
 * {@link de.hybris.platform.commercefacades.product.ProductFacade} and SearchFacade.
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/export/recommendation/products")
@Tag(name = "Export")
@Hidden
public class ExportProductRecommendationController extends BaseController
{

	@Resource(name = "siteOneProductFacade")
	private SiteOneProductFacade siteOneProductFacade;
	
	@GetMapping(value = "/getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiBaseSiteIdParam
	public List<SiteOneWsProductResponseData> getExportedProducts()
	{
		return siteOneProductFacade.getAllProducts();
	}

	
	@GetMapping(value = "/getExportProducts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiBaseSiteIdParam
	public List<SiteOneWsProductResponseData> getProducts(@RequestParam(required = true) final String productCodes)
	{
		if(!ObjectUtils.isEmpty(productCodes)) {
		return siteOneProductFacade.getAllRecommProducts(productCodes);
		}else {
			throw new RequestParameterException("Product code is null or blank");
		}
	}
	
	@GetMapping(value = "/getExportCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiBaseSiteIdParam
	public List<SiteOneWsCategoryResponseData> getCategories(@RequestParam(required = true) final String categoriesCode)
	{
		if(!ObjectUtils.isEmpty(categoriesCode)) {
		return siteOneProductFacade.getAllRecommCategories(categoriesCode);
		}else {
			throw new RequestParameterException("Category code is null or blank");
		}
	}
}