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
package de.hybris.platform.siteoneacceleratoraddon.checkout.steps;

import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.b2b.enums.CheckoutPaymentType;
import de.hybris.platform.commercefacades.order.CheckoutFacade;

import de.hybris.platform.commercefacades.order.data.CartData;


public class B2BPaymentMethodCheckoutStep extends CheckoutStep
{
	private CheckoutFacade checkoutFacade;

	@Override
	public boolean isEnabled()
	{
		final CartData checkoutCart = getCheckoutFacade().getCheckoutCart();
		if (checkoutCart == null || checkoutCart.getPaymentType() == null)
		{
			return false;
		}
		return CheckoutPaymentType.CARD.getCode().equals(checkoutCart.getPaymentType().getCode());
	}

	protected CheckoutFacade getCheckoutFacade()
	{
		return checkoutFacade;
	}

	public void setCheckoutFacade(final CheckoutFacade checkoutFacade)
	{
		this.checkoutFacade = checkoutFacade;
	}

}
