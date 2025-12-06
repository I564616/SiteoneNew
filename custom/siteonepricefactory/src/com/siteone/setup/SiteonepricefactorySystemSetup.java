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
package com.siteone.setup;

import static com.siteone.constants.SiteonepricefactoryConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.siteone.constants.SiteonepricefactoryConstants;
import com.siteone.service.SiteonepricefactoryService;


@SystemSetup(extension = SiteonepricefactoryConstants.EXTENSIONNAME)
public class SiteonepricefactorySystemSetup
{
	private final SiteonepricefactoryService siteonepricefactoryService;

	public SiteonepricefactorySystemSetup(final SiteonepricefactoryService siteonepricefactoryService)
	{
		this.siteonepricefactoryService = siteonepricefactoryService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		siteonepricefactoryService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SiteonepricefactorySystemSetup.class.getResourceAsStream("/siteonepricefactory/sap-hybris-platform.png");
	}
}
