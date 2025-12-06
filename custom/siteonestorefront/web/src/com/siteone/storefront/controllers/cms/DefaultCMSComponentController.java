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

import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;
import de.hybris.platform.servicelayer.exceptions.AttributeNotSupportedException;
import de.hybris.platform.servicelayer.model.ModelService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.storefront.controllers.ControllerConstants;


/**
 * Default Controller for CMS Component. This controller is used for all CMS components that don't have a specific
 * controller to handle them.
 */
@Controller("DefaultCMSComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.DefaultCMSComponent)
public class DefaultCMSComponentController extends AbstractAcceleratorCMSComponentController<AbstractCMSComponentModel>
{
	private static final Logger LOGGER = Logger.getLogger(DefaultCMSComponentController.class);
	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final AbstractCMSComponentModel component)
	{
		// See documentation for CMSComponentService.getEditorProperties, but this will return all frontend
		// properties which we just inject into the model.
		for (final String property : getCmsComponentService().getEditorProperties(component))
		{
			try
			{
				final Object value = modelService.getAttributeValue(component, property);
				model.addAttribute(property, value);
			}
			catch (final AttributeNotSupportedException ignore)
			{
				if (component.getTypeCode().equalsIgnoreCase("ListAddToCartAction")
						|| component.getTypeCode().equalsIgnoreCase("ListOrderFormAction")
						|| component.getTypeCode().equalsIgnoreCase("ListPickUpInStoreAction")
						|| component.getTypeCode().equalsIgnoreCase("AddToCartAction")
						|| component.getTypeCode().equalsIgnoreCase("PickUpInStoreAction")
						|| component.getTypeCode().equalsIgnoreCase("SiteoneParagaraphComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteoneGreenTechButtonComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteonePageTitleBannerComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteoneContactUsComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteOneStoreDetailsPromoComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteoneArticleFeatureComponent")
						|| component.getTypeCode().equalsIgnoreCase("HomePagePromoBannerComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteOneCategoryPromoComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteOneGalleryHeaderBannerComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteOneGalleryHeaderComponent")
						|| component.getTypeCode().equalsIgnoreCase("SiteoneEmbedVideoComponent"))

				{
					//ignore
				}
				else
				{
					LOGGER.error(ignore.getMessage(), ignore);
				}
			}
		}
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
