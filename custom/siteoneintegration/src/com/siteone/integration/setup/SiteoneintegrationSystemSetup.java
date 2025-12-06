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
package com.siteone.integration.setup;

import de.hybris.platform.core.initialization.SystemSetup;

import com.siteone.integration.constants.SiteoneintegrationConstants;


@SystemSetup(extension = SiteoneintegrationConstants.EXTENSIONNAME)
public class SiteoneintegrationSystemSetup
{
	public void setup()
	{
		//initial setup
	}
}
