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

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSPreviewService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.site.BaseSiteService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteone.core.model.SiteOneArticleTagsComponentModel;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 *
 */
@Controller("SiteOneArticleTagsComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SiteOneArticleTagsComponent)
public class SiteOneArticleTagsComponentController
		extends AbstractAcceleratorCMSComponentController<SiteOneArticleTagsComponentModel>
{
	private static final String ARTICLE_PAGE = "ARTICLE_PAGE";

	private static final String ARTICLE_CA_PAGE = "ARTICLE_CA_PAGE";

	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;

	@Resource(name = "cmsPreviewService")
	private CMSPreviewService cmsPreviewService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;
	
	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;



	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final SiteOneArticleTagsComponentModel component)
	{
		final Collection<AbstractPageModel> cmsPage = cmsPageService.getPagesForComponent(component);
		
		if (CollectionUtils.isNotEmpty(cmsPage))
		{
			final Collection<ContentPageModel> contentPageModel = new HashSet<ContentPageModel>();
			for (final AbstractPageModel c : cmsPage)
			{
				contentPageModel.add((ContentPageModel) c);
			}
			final BaseSiteModel basesite = baseSiteService.getCurrentBaseSite();
			ContentPageModel c  = null;
			if (basesite.getUid().equalsIgnoreCase("siteone-us"))
			{
			c = contentPageModel.stream()
					.filter(r -> Objects.nonNull(r.getContentType()) && r.getContentType().toString().equalsIgnoreCase(ARTICLE_PAGE))
					.findFirst().get();
			}
			else {
				c = contentPageModel.stream()
						.filter(r -> Objects.nonNull(r.getContentType()) && r.getContentType().toString().equalsIgnoreCase(ARTICLE_CA_PAGE))
						.findFirst().get();
				}
			model.addAttribute("contentTags", c.getContentTags());

		}
	}


	/**
	 * @return the cmsPageService
	 */
	public CMSPageService getCmsPageService()
	{
		return cmsPageService;
	}

	/**
	 * @param cmsPageService
	 *           the cmsPageService to set
	 */
	public void setCmsPageService(final CMSPageService cmsPageService)
	{
		this.cmsPageService = cmsPageService;
	}

	/**
	 * @return the cmsPreviewService
	 */
	public CMSPreviewService getCmsPreviewService()
	{
		return cmsPreviewService;
	}

	/**
	 * @param cmsPreviewService
	 *           the cmsPreviewService to set
	 */
	public void setCmsPreviewService(final CMSPreviewService cmsPreviewService)
	{
		this.cmsPreviewService = cmsPreviewService;
	}
}
