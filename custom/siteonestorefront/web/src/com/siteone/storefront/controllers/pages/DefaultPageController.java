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
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.impl.ContentPageBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.enums.CmsRobotTag;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.util.Config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UrlPathHelper;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.storefront.controllers.ControllerConstants;


/**
 * Error handler to show a CMS managed error page. This is the catch-all controller that handles all GET requests that
 * are not handled by other controllers.
 */
@Controller
@ControllerAdvice
// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
@RequestMapping("/**")
public class DefaultPageController extends AbstractPageController
{
	private static final String ERROR_CMS_PAGE = "notFound";
	private static final String SEO_INDEX_ENV = "storefront.seo.index.env";
	private static final Logger LOG = Logger.getLogger(DefaultPageController.class);
	private final UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder resourceBreadcrumbBuilder;

	@Resource(name = "contentPageBreadcrumbBuilder")
	private ContentPageBreadcrumbBuilder contentPageBreadcrumbBuilder;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;

	@GetMapping
	public String get(final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws CMSItemNotFoundException
	{
		final String seoIndexEnv = Config.getString(SEO_INDEX_ENV, "");
		if (StringUtils.isNotEmpty(seoIndexEnv) && "prod".equalsIgnoreCase(seoIndexEnv))
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.INDEX_FOLLOW);
		}
		else
		{
			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		}
		// Check for CMS Page where label or id is like /page
		final ContentPageModel pageForRequest = getContentPageForRequest(request);
		if (pageForRequest != null)
		{
			if (pageForRequest.getRobotTag() != null)
			{
				model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS,
						convertRobotTagToSeoRobots(pageForRequest.getRobotTag()));
			}
			storeCmsPageInModel(model, pageForRequest);
			setUpMetaDataForContentPage(model, pageForRequest);
			model.addAttribute(WebConstants.BREADCRUMBS_KEY, contentPageBreadcrumbBuilder.getBreadcrumbs(pageForRequest));
			LOG.info("cms page for request >>> " + pageForRequest);
			return getViewForPage(pageForRequest);
		}

		// No page found - display the notFound page with error from controller
		storeCmsPageInModel(model, getContentPageForLabelOrId(ERROR_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ERROR_CMS_PAGE));

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("breadcrumb.not.found"));


		response.setStatus(HttpServletResponse.SC_NOT_FOUND);

		return ControllerConstants.Views.Pages.Error.ErrorNotFoundPage;
	}

	/**
	 * Lookup the CMS Content Page for this request.
	 *
	 * @param request
	 *           The request
	 * @return the CMS content page
	 */
	protected ContentPageModel getContentPageForRequest(final HttpServletRequest request)
	{
		// Get the path for this request.
		// Note that the path begins with a '/'
		final String lookupPathForRequest = urlPathHelper.getLookupPathForRequest(request);

		try
		{
			CatalogVersionModel catalogVersionModel = null;
			try
			{
				// Lookup the CMS Content Page by label and catalog version. Note that the label value must begin with a '/'.
				if (getBaseSiteService().getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-ca"))
				{
					return getCmsPageService().getDefaultPageForLabel(lookupPathForRequest, catalogVersionService
							.getCatalogVersion(SiteoneCoreConstants.CONTENT_CATALOG_CA, SiteoneCoreConstants.CATALOG_VERSION));
				}
				if (getBaseSiteService().getCurrentBaseSite().getUid().equalsIgnoreCase("siteone-us"))
				{
					return getCmsPageService().getDefaultPageForLabel(lookupPathForRequest, catalogVersionService
							.getCatalogVersion(SiteoneCoreConstants.CONTENT_CATALOG_US, SiteoneCoreConstants.CATALOG_VERSION));
				}
			}
			catch (final CMSItemNotFoundException ignore)
			{
				LOG.error(ignore.getMessage());
			}
			catalogVersionModel = catalogVersionService.getCatalogVersion(SiteoneCoreConstants.PARENT_CONTENT_CATALOG,
					SiteoneCoreConstants.CATALOG_VERSION);
			return getCmsPageService().getDefaultPageForLabel(lookupPathForRequest, catalogVersionModel);
		}
		catch (final CMSItemNotFoundException ignore)
		{
			LOG.error(ignore.getMessage());
		}
		return null;
	}

	/**
	 * Redirect to error page based on language either english or spanish
	 */
	@ExceptionHandler(Exception.class)
	public String defaultErrorHandler(final HttpServletResponse response, final Exception e) throws Exception
	{
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
		{
			throw e;
		}
		LOG.error(e.getMessage(), e);
		return redirectBasedOnLocale(response);
	}

	// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
	@RequestMapping("/**/error")
	public String customError(final HttpServletRequest request, final HttpServletResponse response, final Model model)
			throws CMSItemNotFoundException
	{
		return redirectBasedOnLocale(response);
	}

	public String redirectBasedOnLocale(final HttpServletResponse response)
	{
		final String lang = storeSessionFacade.getCurrentLanguage().getIsocode();
		if (lang.equalsIgnoreCase("es"))
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return ControllerConstants.Views.Pages.Error.ServerErrorSpanPage;

		}
		else
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return ControllerConstants.Views.Pages.Error.ServerErrorEngPage;
		}
	}

	private String convertRobotTagToSeoRobots(final CmsRobotTag robotTag)
	{
		if (CmsRobotTag.INDEX_FOLLOW.equals(robotTag))
		{
			return ThirdPartyConstants.SeoRobots.INDEX_FOLLOW;
		}
		else if (CmsRobotTag.INDEX_NOFOLLOW.equals(robotTag))
		{
			return ThirdPartyConstants.SeoRobots.INDEX_NOFOLLOW;
		}
		else if (CmsRobotTag.NOINDEX_FOLLOW.equals(robotTag))
		{
			return ThirdPartyConstants.SeoRobots.NOINDEX_FOLLOW;
		}
		else
		{
			return ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW;
		}
	}
}