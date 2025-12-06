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
package com.siteone.storefront.controllers.misc;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.siteone.core.services.SiteMapPageService;
import com.siteone.storefront.controllers.ControllerConstants;


@Controller
public class SiteMapController extends AbstractController
{
	private static final Logger LOG = Logger.getLogger(SiteMapController.class);

	@Resource(name = "cmsSiteService")
	private CMSSiteService cmsSiteService;

	@Resource(name = "siteMapPageService")
	private SiteMapPageService siteMapPageService;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@GetMapping(value = "/sitemap.xml", produces = "application/xml")
	public String getSitemapXml(final Model model, final HttpServletRequest request)
	{
		final CMSSiteModel currentSite = cmsSiteService.getCurrentSite();

		final List<String> siteMapUrls = new ArrayList<>();
		final List<Date> dateList = new ArrayList<>();

		final Collection<MediaModel> siteMaps = currentSite.getSiteMaps();
		int count=0;
		for (final MediaModel siteMap : siteMaps)
		{
			final String realFileName = siteMap.getCode();
			if (StringUtils.isNotBlank(realFileName))
			{
				String baseUrl = null;
				if (cmsSiteService.getCurrentSite().getUid().equalsIgnoreCase("siteone-us"))
				{
					baseUrl = configurationService.getConfiguration().getString("website.siteone-us.https") + request.getContextPath();
				}
				if (cmsSiteService.getCurrentSite().getUid().equalsIgnoreCase("siteone-ca"))
				{
					baseUrl = configurationService.getConfiguration().getString("website.siteone-ca.https");
				}
				String siteMapFile  = realFileName.split("-")[0].toLowerCase();
				if(siteMapFile.equalsIgnoreCase("product")) {
					String[] siteMapFileArray = realFileName.split("-");
					if(siteMapFileArray.length == 5) {
						siteMapFile=siteMapFile+"-"+count;
						count++;
					}
				}
				siteMapUrls.add(baseUrl + "/sitemap/" + siteMapFile + ".xml");
				dateList.add(siteMap.getModifiedtime());
				LOG.error("currentDate: " + siteMap.getModifiedtime());
			}
		}
		final SimpleDateFormat lastmodFormat = new SimpleDateFormat("yyyy-MM-dd");

		final List<String> currentDate = new ArrayList<>();
		for (final Date date : dateList)
		{
			final String lastmodString = lastmodFormat.format(date);
			currentDate.add(lastmodString);
		}
		final  HashMap<String, String> siteMapData = new LinkedHashMap<>();
		for(int i=0;i<siteMapUrls.size();i++) {
			siteMapData.put(siteMapUrls.get(i),currentDate.get(i));
		}
		model.addAttribute("siteMapData", siteMapData);

		return ControllerConstants.Views.Pages.Misc.MiscSiteMapPage;
	}

	// FRAMEWORK_UPDATE - TODO - AntPathMatcher was replaced with PathPatternParser as the new default path parser in Spring 6. Adjust this path to the new matching rules or re-enable deprecated AntPathMatcher. Consult "Adapting to PathPatternParser new default URL Matcher" JDK21 Upgrade Step in SAP Help documentation.
	@GetMapping("/**/sitemap/{sitemapPageType:.*}")
	public void getSiteMapPageXml(final HttpServletResponse response,
			@PathVariable("sitemapPageType") String sitemapPageType)
	{
		try
		{

			if(StringUtils.isNotBlank(sitemapPageType)) {
				String[] sitemapPageArray = sitemapPageType.split(".xml")[0].split("-");
				if (cmsSiteService.getCurrentSite().getUid().equalsIgnoreCase("siteone-us")) {				
				if(sitemapPageArray.length>1) {
					sitemapPageType = sitemapPageArray[0]+"-en-USD-"+sitemapPageArray[1];
				}else {
					sitemapPageType = sitemapPageArray[0]+"-en-USD";
				}
				}
				else if (cmsSiteService.getCurrentSite().getUid().equalsIgnoreCase("siteone-ca")) {
					if(sitemapPageArray.length>1) {
						sitemapPageType = sitemapPageArray[0]+"-en-CAD-"+sitemapPageArray[1];
					}else {
						sitemapPageType = sitemapPageArray[0]+"-en-CAD";
					}
				}
			}
			final MediaModel media = siteMapPageService.getSiteMapPage(sitemapPageType);

			final byte[] xml = mediaService.getDataFromMedia(media);

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");

			response.setContentType("text/xml");
			response.setContentLength(xml.length);
			response.getOutputStream().write(xml);
			response.getOutputStream().flush();
		}
		catch (final Exception e)
		{
			LOG.error("Site Map failed");
			LOG.error(e);
		}

	}

}

