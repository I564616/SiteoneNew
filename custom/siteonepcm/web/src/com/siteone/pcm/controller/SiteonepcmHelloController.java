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
package com.siteone.pcm.controller;

import static com.siteone.pcm.constants.SiteonepcmConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.siteone.pcm.service.SiteonepcmService;


@Controller
public class SiteonepcmHelloController
{
	@Autowired
	private SiteonepcmService siteonepcmService;

	@GetMapping("/")
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", siteonepcmService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
