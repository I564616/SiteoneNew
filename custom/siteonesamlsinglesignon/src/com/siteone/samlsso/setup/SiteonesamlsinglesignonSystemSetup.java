/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.samlsso.setup;

import static com.siteone.samlsso.constants.SiteonesamlsinglesignonConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.siteone.samlsso.constants.SiteonesamlsinglesignonConstants;
import com.siteone.samlsso.service.SiteonesamlsinglesignonService;


@SystemSetup(extension = SiteonesamlsinglesignonConstants.EXTENSIONNAME)
public class SiteonesamlsinglesignonSystemSetup
{
	private final SiteonesamlsinglesignonService siteonesamlsinglesignonService;

	public SiteonesamlsinglesignonSystemSetup(final SiteonesamlsinglesignonService siteonesamlsinglesignonService)
	{
		this.siteonesamlsinglesignonService = siteonesamlsinglesignonService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		siteonesamlsinglesignonService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SiteonesamlsinglesignonSystemSetup.class.getResourceAsStream("/siteonesamlsinglesignon/sap-hybris-platform.png");
	}
}
