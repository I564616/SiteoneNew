/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.order.CartModel;


/**
 * @author 1219341
 *
 */
public class CartAbandonmentEvent extends AbstractCommerceUserEvent
{
	/**
	 * @return the cart
	 */
	public CartModel getCart()
	{
		return cart;
	}

	/**
	 * @param cart
	 *           the cart to set
	 */
	public void setCart(final CartModel cart)
	{
		this.cart = cart;
	}

	private CartModel cart;

}
