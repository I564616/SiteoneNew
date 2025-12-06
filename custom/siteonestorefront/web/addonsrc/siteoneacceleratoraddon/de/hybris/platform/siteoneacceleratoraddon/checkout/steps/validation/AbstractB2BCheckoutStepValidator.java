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
package de.hybris.platform.siteoneacceleratoraddon.checkout.steps.validation;

//import com.siteone.storefront.util.SiteOneCheckoutUtils;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.AbstractCheckoutStepValidator;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.ValidationResults;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.siteoneacceleratoraddon.security.B2BUserGroupProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;


/**
 * Abstract checkout step validator for the B2B accelerator.
 */
public abstract class AbstractB2BCheckoutStepValidator extends AbstractCheckoutStepValidator
{
	private B2BUserGroupProvider b2bUserGroupProvider;
	
//	@Resource(name = "siteOneCheckoutUtils")
//	private SiteOneCheckoutUtils siteOneCheckoutUtils;
	
	@Resource(name = "userService")
	private UserService userService;
	
	private static final Logger LOG = Logger.getLogger(AbstractB2BCheckoutStepValidator.class);

	protected AbstractB2BCheckoutStepValidator() {
	}


	@Override
	public ValidationResults validateOnEnter(final RedirectAttributes redirectAttributes)
	{

		if (!getCheckoutFlowFacade().hasValidCart())
		{
			LOG.info("Missing, empty or unsupported cart");
			return ValidationResults.REDIRECT_TO_CART;
		}
		
		final CartData checkoutCart = getCheckoutFacade().getCheckoutCart();
		
//		if(!siteOneCheckoutUtils.isCartValidForCheckout(checkoutCart))
//		{
//			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.ERROR_MESSAGES_HOLDER,
//					"cart.checkout.disable.message");
//			LOG.info("User is not able to checkout beacause of recently changed store");
//			return ValidationResults.REDIRECT_TO_CART;
//		}
//
//		if (!siteOneCheckoutUtils.isPosValidForCheckout(checkoutCart))
//		{
//			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER, "checkout.multi.pos.invalid");
//			LOG.info("User is not able to checkout beacause of recently changed Store");
//			return ValidationResults.REDIRECT_TO_CART;
//		}
		
//		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
//		{
//		if (!siteOneCheckoutUtils.isAccountValidForCheckout(checkoutCart))
//		{
//			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.INFO_MESSAGES_HOLDER, "checkout.error.invalid.shipto");
//			LOG.info("User is not able to checkout beacause of recently changed Account");
//			return ValidationResults.REDIRECT_TO_CART;
//		}
//		}
		return doValidateOnEnter(redirectAttributes);
	}
	
	/**
	 * Performs implementation specific validation on entering a checkout step after the common validation has been
	 * performed in the abstract implementation.
	 *
	 * @param redirectAttributes
	 * @return {@link ValidationResults}
	 */
	protected abstract ValidationResults doValidateOnEnter(final RedirectAttributes redirectAttributes);

	protected B2BUserGroupProvider getB2bUserGroupProvider()
	{
		return b2bUserGroupProvider;
	}

	@Required
	public void setB2bUserGroupProvider(final B2BUserGroupProvider b2bUserGroupProvider)
	{
		this.b2bUserGroupProvider = b2bUserGroupProvider;
	}
}
