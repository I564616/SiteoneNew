/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.platform.cmsfacades.setup;

import static com.siteone.platform.cmsfacades.constants.SiteonecmsfacadesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.siteone.platform.cmsfacades.constants.SiteonecmsfacadesConstants;
import com.siteone.platform.cmsfacades.service.SiteonecmsfacadesService;


@SystemSetup(extension = SiteonecmsfacadesConstants.EXTENSIONNAME)
public class SiteonecmsfacadesSystemSetup
{
	private final SiteonecmsfacadesService siteonecmsfacadesService;

	public SiteonecmsfacadesSystemSetup(final SiteonecmsfacadesService siteonecmsfacadesService)
	{
		this.siteonecmsfacadesService = siteonecmsfacadesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		siteonecmsfacadesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SiteonecmsfacadesSystemSetup.class.getResourceAsStream("/siteonecmsfacades/sap-hybris-platform.png");
	}
}
