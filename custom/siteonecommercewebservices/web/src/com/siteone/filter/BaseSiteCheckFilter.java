/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.filter;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.site.BaseSiteService;
import com.siteone.exceptions.BaseSiteMismatchException;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


/**
 * BaseSiteCheckFilter is responsible for checking if base site set in current session cart is the same as one set in
 * baseSiteService It prevents mixing requests for multiple sites in one session
 */
public class BaseSiteCheckFilter extends OncePerRequestFilter
{
	private CartService cartService;

	private BaseSiteService baseSiteService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{
		checkBaseSite();
		filterChain.doFilter(request, response);
	}

	protected void checkBaseSite() throws BaseSiteMismatchException
	{
		if (getCartService().hasSessionCart())
		{
			final CartModel cart = getCartService().getSessionCart();
			if (cart != null)
			{
				final BaseSiteModel baseSiteFromCart = cart.getSite();
				final BaseSiteModel baseSiteFromService = getBaseSiteService().getCurrentBaseSite();

				if (baseSiteFromCart != null && baseSiteFromService != null && !baseSiteFromCart.equals(baseSiteFromService))
				{
					throw new BaseSiteMismatchException(baseSiteFromService.getUid(), baseSiteFromCart.getUid());
				}
			}
		}
	}

	/**
	 * @return the cartService
	 */
	public CartService getCartService()
	{
		return cartService;
	}

	/**
	 * @param cartService
	 *           the cartService to set
	 */
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}
}
