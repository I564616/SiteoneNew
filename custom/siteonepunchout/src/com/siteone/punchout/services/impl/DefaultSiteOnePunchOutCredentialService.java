/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2019 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.siteone.punchout.services.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.punchout.model.B2BCustomerPunchOutCredentialMappingModel;
import de.hybris.platform.b2b.punchout.model.PunchOutCredentialModel;
import de.hybris.platform.b2b.punchout.services.impl.DefaultPunchOutCredentialService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.persistence.security.PasswordEncoderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cxml.Credential;


/**
 *
 */
public class DefaultSiteOnePunchOutCredentialService extends DefaultPunchOutCredentialService
{
	private static final Logger LOG = Logger.getLogger(DefaultSiteOnePunchOutCredentialService.class);

	@Override
	protected B2BCustomerModel getCustomerForCredential(final Credential credential, final boolean verifySharedSecret)
	{
		B2BCustomerModel customer = null;
		final String domain = credential.getDomain();
		final String identity = extractIdentity(credential);

		final PunchOutCredentialModel credentialModel = getPunchOutCredential(domain, identity);
		if (credentialModel != null)
		{
			final B2BCustomerPunchOutCredentialMappingModel mappingModel = credentialModel.getB2BCustomerPunchOutCredentialMapping();
			boolean authenticated = true;

			if (verifySharedSecret)
			{
				final String sharedSecret = extractSharedSecret(credential);
				final PasswordEncoderFactory factory = Registry.getCurrentTenant().getJaloConnection().getPasswordEncoderFactory();
				final String encodedSharedSecret = factory.getEncoder("md5").encode(identity, sharedSecret);
				//LOG.info("sharedSecret==" + sharedSecret + " ,encodedSharedSecret==" + encodedSharedSecret);

				authenticated = StringUtils.equals(encodedSharedSecret, credentialModel.getSharedsecret());
				LOG.info("authenticated==" + authenticated);
			}

			if (mappingModel != null && authenticated)
			{
				customer = mappingModel.getB2bCustomer();
			}
		}

		return customer;
	}

}
