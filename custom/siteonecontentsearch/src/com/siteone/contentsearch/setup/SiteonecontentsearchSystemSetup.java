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
package com.siteone.contentsearch.setup;

import static com.siteone.contentsearch.constants.SiteonecontentsearchConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.siteone.contentsearch.constants.SiteonecontentsearchConstants;
import com.siteone.contentsearch.service.SiteonecontentsearchService;


@SystemSetup(extension = SiteonecontentsearchConstants.EXTENSIONNAME)
public class SiteonecontentsearchSystemSetup
{
	private final SiteonecontentsearchService siteonecontentsearchService;

	public SiteonecontentsearchSystemSetup(final SiteonecontentsearchService siteonecontentsearchService)
	{
		this.siteonecontentsearchService = siteonecontentsearchService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		siteonecontentsearchService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SiteonecontentsearchSystemSetup.class.getResourceAsStream("/siteonecontentsearch/sap-hybris-platform.png");
	}
}
