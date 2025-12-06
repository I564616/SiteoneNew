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

import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.commercefacades.order.CartFacade;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.facades.cart.SiteOneCartFacade;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * Controller for CMS MiniCartComponent
 */
@Controller("MiniCartComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.MiniCartComponent)
public class MiniCartComponentController extends AbstractAcceleratorCMSComponentController<MiniCartComponentModel>
{

	public static final String TOTAL_ITEMS = "totalItems";
	public static final String TOTAL_DISPLAY = "totalDisplay";

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final MiniCartComponentModel component)
	{
		model.addAttribute(TOTAL_DISPLAY, component.getTotalDisplay());
		model.addAttribute(TOTAL_ITEMS, ((SiteOneCartFacade) cartFacade).getTotalItems());
	}
}
