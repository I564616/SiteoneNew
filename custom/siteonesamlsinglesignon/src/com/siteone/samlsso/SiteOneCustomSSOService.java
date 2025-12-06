/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.samlsso;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.samlsinglesignon.DefaultSSOService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.util.Collection;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;


/**
 * Default SSO service for getting/creating user
 */
public class SiteOneCustomSSOService extends DefaultSSOService
{
	@Resource
	private ModelService modelService;

	@Resource
	private UserService userService;


	public UserModel getOrCreateSSO;
	private static final Logger LOG = Logger.getLogger(SiteOneCustomSSOService.class);

	@Override
	public UserModel getOrCreateSSOUser(final String id, final String name, final Collection<String> roles)
	{
		LOG.info(" enters getOrCreateSSOUser method id" + id);
		if (CollectionUtils.isEmpty(roles))
		{
			roles.add("readonlygroup");
		}

		return super.getOrCreateSSOUser(id, name, roles);

	}

	@Override
	protected UserModel createNewUser(final String id, final String name, final SSOUserMapping userMapping)
	{
		final UserModel newUser = super.createNewUser(id, name, userMapping);
		newUser.setBackOfficeLoginDisabled(false);
		return newUser;
	}

	public UserService getUserService()
	{
		return (UserService) Registry.getApplicationContext().getBean("userService");
	}

	public ModelService getModelService()
	{
		return (ModelService) Registry.getApplicationContext().getBean("modelService");
	}

}