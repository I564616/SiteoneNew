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
package de.hybris.platform.siteonepunchoutaddon.services.impl;

import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.punchout.Organization;
import de.hybris.platform.b2b.punchout.PunchOutException;
import de.hybris.platform.b2b.punchout.PunchOutResponseCode;
import de.hybris.platform.b2b.punchout.PunchOutSession;
import de.hybris.platform.b2b.punchout.populators.impl.DefaultPunchOutSessionPopulator;
import de.hybris.platform.b2b.punchout.services.PunchOutConfigurationService;
import de.hybris.platform.b2b.punchout.services.PunchOutSessionService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.site.BaseSiteService;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cxml.Credential;
import org.cxml.Identity;


/**
 * Default implementation of {@link PunchOutConfigurationService}.
 */
public class DefaultPunchOutConfigurationService implements PunchOutConfigurationService
{
	private static final Logger LOG = Logger.getLogger(DefaultPunchOutConfigurationService.class);
	private BaseSiteService baseSiteService;

	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;

	private ConfigurationService configurationService;

	private PunchOutSessionService punchoutSessionService;

	private String punchOutSessionUrlPath;

	@Override
	public String getPunchOutLoginUrl()
	{
		final String sessionId = getPunchOutSessionService().getCurrentPunchOutSessionId();
		if (StringUtils.isNotBlank(sessionId))
		{
			return getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSiteService().getCurrentBaseSite(), "", true,
					getPunchOutSessionUrlPath(), "sid=" + sessionId);
		}
		return null;
	}

	@Override
	public String getDefaultCostCenter()
	{
		return getConfigurationService().getConfiguration().getString("siteonepunchoutaddon.checkout.costcenter.default");
	}

	
	@Override
	public boolean isOrderVerificationEnabled() 
	{
		return getConfigurationService().getConfiguration().getBoolean("siteonepunchoutaddon.order.verification.enabled");
	}
	
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}
	
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	public SiteBaseUrlResolutionService getSiteBaseUrlResolutionService()
	{
		return siteBaseUrlResolutionService;
	}

	public void setSiteBaseUrlResolutionService(final SiteBaseUrlResolutionService siteBaseUrlResolutionService)
	{
		this.siteBaseUrlResolutionService = siteBaseUrlResolutionService;
	}

	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	public PunchOutSessionService getPunchOutSessionService()
	{
		return punchoutSessionService;
	}

	public void setPunchOutSessionService(final PunchOutSessionService punchoutSessionService)
	{
		this.punchoutSessionService = punchoutSessionService;
	}

	public String getPunchOutSessionUrlPath()
	{
		return punchOutSessionUrlPath;
	}

	public void setPunchOutSessionUrlPath(final String punchOutSessionUrlPath)
	{
		this.punchOutSessionUrlPath = punchOutSessionUrlPath;
	}

}
