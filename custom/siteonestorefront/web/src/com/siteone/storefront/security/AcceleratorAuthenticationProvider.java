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
package com.siteone.storefront.security;

import de.hybris.platform.core.Constants;
import de.hybris.platform.spring.security.CoreAuthenticationProvider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.siteone.facades.customer.SiteOneCustomerFacade;

import com.siteone.facades.company.SiteOneB2BUserFacade;


/**
 * Derived authentication provider supporting additional authentication checks. See
 * {@link de.hybris.platform.spring.security.RejectUserPreAuthenticationChecks}.
 *
 * <ul>
 * <li>prevent login without password for users created via CSCockpit</li>
 * <li>prevent login as user in group admingroup</li>
 * </ul>
 *
 * any login as admin disables SearchRestrictions and therefore no page can be viewed correctly
 */
public class AcceleratorAuthenticationProvider extends CoreAuthenticationProvider
{
	private static final String ROLE_ADMIN_GROUP = "ROLE_" + Constants.USER.ADMIN_USERGROUP.toUpperCase();

	private GrantedAuthority adminAuthority = new SimpleGrantedAuthority(ROLE_ADMIN_GROUP);

	private SiteOneCustomerFacade siteOneCustomerFacade;
	
	private SiteOneB2BUserFacade siteOneB2BUserFacade;



	/**
	 * @see de.hybris.platform.acceleratorstorefrontcommons.security.AbstractAcceleratorAuthenticationProvider#additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails,
	 *      org.springframework.security.authentication.AbstractAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(final UserDetails details, final AbstractAuthenticationToken authentication)
			throws AuthenticationException
	{
		super.additionalAuthenticationChecks(details, authentication);

		// Check if user has supplied no password
		if (StringUtils.isEmpty((String) authentication.getCredentials()))
		{
			throw new BadCredentialsException("Login without password");
		}

		// Check if the user is in role admingroup
		if (getAdminAuthority() != null && details.getAuthorities().contains(getAdminAuthority()))
		{
			throw new LockedException("Login attempt as " + Constants.USER.ADMIN_USERGROUP + " is rejected");
		}
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException
	{

		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		final boolean isUserAvailable = siteOneCustomerFacade.isUserAvailable(StringUtils.lowerCase(username));

		if (!isUserAvailable || siteOneB2BUserFacade.isPunchOutCustomer(username))
		{
			throw new BadCredentialsException(messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
		}


		//		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		//		if (getBruteForceAttackCounter().isAttack(username))
		//		{
		//			try
		//			{
		//				final UserModel userModel = getUserService().getUserForUID(StringUtils.lowerCase(username));
		//				userModel.setLoginDisabled(true);
		//				getModelService().save(userModel);
		//				bruteForceAttackCounter.resetUserCounter(userModel.getUid());
		//			}
		//			catch (final UnknownIdentifierException e)
		//			{
		//				LOG.warn("Brute force attack attempt for non existing user name " + username);
		//			}

		//			throw new BadCredentialsException(messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
		//
		//		}

		return super.authenticate(authentication);

	}

	public void setAdminGroup(final String adminGroup)
	{
		if (StringUtils.isBlank(adminGroup))
		{
			adminAuthority = null;
		}
		else
		{
			adminAuthority = new SimpleGrantedAuthority(adminGroup);
		}
	}

	protected GrantedAuthority getAdminAuthority()
	{
		return adminAuthority;
	}

	/**
	 * @return the siteOneCustomerFacade
	 */
	public SiteOneCustomerFacade getSiteOneCustomerFacade()
	{
		return siteOneCustomerFacade;
	}

	/**
	 * @param siteOneCustomerFacade
	 *           the siteOneCustomerFacade to set
	 */
	public void setSiteOneCustomerFacade(final SiteOneCustomerFacade siteOneCustomerFacade)
	{
		this.siteOneCustomerFacade = siteOneCustomerFacade;
	}

	/**
	 * @return the siteOneB2BUserFacade
	 */
	public SiteOneB2BUserFacade getSiteOneB2BUserFacade()
	{
		return siteOneB2BUserFacade;
	}

	/**
	 * @param siteOneB2BUserFacade the siteOneB2BUserFacade to set
	 */
	public void setSiteOneB2BUserFacade(SiteOneB2BUserFacade siteOneB2BUserFacade)
	{
		this.siteOneB2BUserFacade = siteOneB2BUserFacade;
	}

}
