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
package com.siteone.contentsearch.controller;

import static com.siteone.contentsearch.constants.SiteonecontentsearchConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.siteone.contentsearch.service.SiteonecontentsearchService;


@Controller
public class SiteonecontentsearchHelloController
{
	@Autowired
	private SiteonecontentsearchService siteonecontentsearchService;

	@GetMapping("/")
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", siteonecontentsearchService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
