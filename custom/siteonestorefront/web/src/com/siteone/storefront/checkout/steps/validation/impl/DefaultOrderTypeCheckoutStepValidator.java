/**
 *
 */
package com.siteone.storefront.checkout.steps.validation.impl;


import com.siteone.facades.checkout.impl.DefaultSiteOneB2BCheckoutFacade;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.AbstractCheckoutStepValidator;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.ValidationResults;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.servicelayer.user.UserService;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteone.storefront.checkout.steps.validation.B2BUserGroupProvider;
import com.siteone.storefront.util.SiteOneCheckoutUtils;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultOrderTypeCheckoutStepValidator extends AbstractCheckoutStepValidator
{
	private static final Logger LOG = Logger.getLogger(DefaultOrderTypeCheckoutStepValidator.class);

	private B2BUserGroupProvider b2bUserGroupProvider;

	@Resource(name = "siteOneCheckoutUtils")
	private SiteOneCheckoutUtils siteOneCheckoutUtils;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "b2bCheckoutFacade")
	private DefaultSiteOneB2BCheckoutFacade b2bCheckoutFacade;

	@Override
	public ValidationResults validateOnEnter(final RedirectAttributes redirectAttributes)
	{

		if (!getCheckoutFlowFacade().hasValidCart())
		{
			LOG.info("Missing, empty or unsupported cart");
			return ValidationResults.REDIRECT_TO_CART;
		}

		final CartData checkoutCart = getCheckoutFacade().getCheckoutCart();

		if (!b2bCheckoutFacade.isCartValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"cart.checkout.disable.message");
			LOG.info("User is not able to checkout beacause of recently changed store");
			return ValidationResults.REDIRECT_TO_CART;
		}

		if (! b2bCheckoutFacade.isPosValidForCheckout(checkoutCart))
		{
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER, "checkout.multi.pos.invalid");
			LOG.info("User is not able to checkout beacause of recently changed Store");
			return ValidationResults.REDIRECT_TO_CART;
		}

		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (!b2bCheckoutFacade.isAccountValidForCheckout(checkoutCart))
			{
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER,
						"checkout.error.invalid.shipto");
				LOG.info("User is not able to checkout beacause of recently changed Account");
				return ValidationResults.REDIRECT_TO_CART;
			}
		}

		return ValidationResults.SUCCESS;
	}


	/**
	 * @return the b2bUserGroupProvider
	 */
	public B2BUserGroupProvider getB2bUserGroupProvider()
	{
		return b2bUserGroupProvider;
	}

	/**
	 * @param b2bUserGroupProvider
	 *           the b2bUserGroupProvider to set
	 */
	public void setB2bUserGroupProvider(final B2BUserGroupProvider b2bUserGroupProvider)
	{
		this.b2bUserGroupProvider = b2bUserGroupProvider;
	}


}
