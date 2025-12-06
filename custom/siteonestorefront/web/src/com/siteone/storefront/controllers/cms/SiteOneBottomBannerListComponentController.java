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
package com.siteone.storefront.controllers.cms;

import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOneBottomBannerListComponentModel;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 *
 */
@Controller("SiteOneBottomBannerListComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneBottomBannerListComponent)
public class SiteOneBottomBannerListComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneBottomBannerListComponentModel>
{

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model,
			final SiteOneBottomBannerListComponentModel component)
	{

		if (component.getBannersList().stream().allMatch(r -> (r.getImagePosition() != null && !r.getImagePosition().isEmpty())))
		{
			model.addAttribute("sortedBannerList", component.getBannersList().stream().sorted(
					(s1, s2) -> Integer.compare(Integer.parseInt(s1.getImagePosition()), Integer.parseInt(s2.getImagePosition())))
					.collect(Collectors.toList()));
		}
		else
		{
			model.addAttribute("sortedBannerList", component.getBannersList());
		}

	}
}
