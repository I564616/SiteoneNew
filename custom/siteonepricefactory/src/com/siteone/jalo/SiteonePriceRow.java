/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2017 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.jalo;

import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.storelocator.jalo.PointOfService;



/**
 *
 */
public class SiteonePriceRow extends PriceRow
{
	public static final String POS = "pointOfService";

	public PointOfService getPos(final SessionContext ctx)
	{
		return (PointOfService) this.getProperty(ctx, POS);
	}

	public PointOfService getPos()
	{
		return this.getPos(this.getSession().getSessionContext());
	}

	public void setPos(final SessionContext ctx, final PointOfService value)
	{
		this.setProperty(ctx, POS, value);
	}

	public void setPos(final PointOfService value)
	{
		this.setPos(this.getSession().getSessionContext(), value);
	}
}
