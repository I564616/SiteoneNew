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

import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.storelocator.jalo.PointOfService;


/**
 *
 */
public class SiteonePointOfService extends PointOfService
{
	public static final String STOREID = "storeId";
	public static final String NAME = "name";

	public String getStoreId(final SessionContext ctx)
	{
		return (String) this.getProperty(ctx, STOREID);
	}

	public String getStoreId()
	{
		return this.getStoreId(this.getSession().getSessionContext());
	}

	public void setStoreId(final SessionContext ctx, final String value)
	{
		this.setProperty(ctx, STOREID, value);
	}

	public void setStoreId(final String value)
	{
		this.setStoreId(this.getSession().getSessionContext(), value);
	}
	
	
	
	
	public String getName(final SessionContext ctx)
	{
		return (String) this.getProperty(ctx, NAME);
	}

	public String getName()
	{
		return this.getName(this.getSession().getSessionContext());
	}

	public void setName(final SessionContext ctx, final String value)
	{
		this.setProperty(ctx, NAME, value);
	}

	public void setName(final String value)
	{
		this.setName(this.getSession().getSessionContext(), value);
	}
}
