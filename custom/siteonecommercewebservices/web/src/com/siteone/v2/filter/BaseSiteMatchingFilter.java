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
package com.siteone.v2.filter;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.webservicescommons.util.YSanitizer;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.exceptions.InvalidResourceException;
import com.siteone.exceptions.UpgradeRequiredException;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.Float;
import java.lang.Boolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;



/**
 * Filter that resolves base site id from the requested url and activates it.
 */
public class BaseSiteMatchingFilter extends AbstractUrlMatchingFilter
{
	private static final Logger LOG = Logger.getLogger(BaseSiteMatchingFilter.class);
	private static final String APP_ANDROID_VERSION_NUM = "MobileAppMinSuppAndroidVersion";
	private static final String APP_IOS_VERSION_NUM = "MobileAppMinSuppIOSVersion";
	private static final String APP_FORCEUPGRADE= "MobileAppForceUpgrade";
	
	private static final String ANDROID = "(ANDROID)";
	private static final String IOS = "(IOS)";
	
	private String regexp;
	private BaseSiteService baseSiteService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "cachingSiteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException, UpgradeRequiredException
	{
		checkForAppForceUpgrade(request);
		getSessionService().setAttribute("currentBaseStore",baseStoreService.getBaseStoreForUid("siteone").getPk());
		 String baseSiteID = getBaseSiteValue(request, regexp);

		if (baseSiteID != null)
		{
			if(baseSiteID.equalsIgnoreCase("siteone"))
			{
				baseSiteID="siteone-us";
			}
			final BaseSiteModel requestedBaseSite = getBaseSiteService().getBaseSiteForUID(baseSiteID);
			if (requestedBaseSite != null)
			{
				final BaseSiteModel currentBaseSite = getBaseSiteService().getCurrentBaseSite();

				if (!requestedBaseSite.equals(currentBaseSite))
				{
					getBaseSiteService().setCurrentBaseSite(requestedBaseSite, true);
				}
			}
			else
			{
				final InvalidResourceException ex = new InvalidResourceException(YSanitizer.sanitize(baseSiteID));
				LOG.debug(ex.getMessage());
				throw ex;
			}
		}

		filterChain.doFilter(request, response);
	}

	protected void checkForAppForceUpgrade(final HttpServletRequest request) throws UpgradeRequiredException
	{
		boolean enableAppForceUpgrade = Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(APP_FORCEUPGRADE));
		if (enableAppForceUpgrade && (null != request && null != request.getHeader("User-Agent")
				&& StringUtils.containsIgnoreCase(request.getHeader("User-Agent"), "SiteOneEcomApp"))) {
			String userAgent = request.getHeader("User-Agent");
			String strVersion = (userAgent.split(" ")[0]).split("/")[1];
			if (NumberUtils.isCreatable(strVersion)) {
				float version = Float.parseFloat(strVersion);
				if (userAgent.toUpperCase().endsWith(ANDROID)) {
					float appAndroidSuppVerNum =  Float.parseFloat(siteOneFeatureSwitchCacheService.getValueForSwitch(APP_ANDROID_VERSION_NUM));
					if(version < appAndroidSuppVerNum) {
						throw new UpgradeRequiredException();
					}
					
				} else if (userAgent.toUpperCase().endsWith(IOS)) {
					float appIosSuppVerNum =  Float.parseFloat(siteOneFeatureSwitchCacheService.getValueForSwitch(APP_IOS_VERSION_NUM));
					if (version < appIosSuppVerNum) {
						throw new UpgradeRequiredException();
					}
				}
			}
		
		}
	}
	protected String getRegexp()
	{
		return regexp;
	}

	public void setRegexp(final String regexp)
	{
		this.regexp = regexp;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}
	protected SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
	protected BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	public void setBaseStoreService(final BaseStoreService service)
	{
		this.baseStoreService = service;
	}

}
