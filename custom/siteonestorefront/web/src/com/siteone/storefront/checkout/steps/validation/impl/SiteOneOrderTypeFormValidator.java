/**
 *
 */
package com.siteone.storefront.checkout.steps.validation.impl;

import com.siteone.facades.checkout.SiteOneB2BCheckoutFacade;
import com.siteone.facades.checkout.form.SiteOneOrderTypeFacadeForm;
import de.hybris.platform.b2bacceleratorfacades.api.cart.CheckoutFacade;
import de.hybris.platform.servicelayer.user.UserService;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siteone.storefront.forms.SiteOneOrderTypeForm;
import com.siteone.storefront.util.SiteOneCheckoutUtils;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;


/**
 * @author 1190626
 *
 */
@Component("orderTypeFormValidator")
public class SiteOneOrderTypeFormValidator implements Validator
{

	@Resource(name = "siteOneCheckoutUtils")
	private SiteOneCheckoutUtils siteOneCheckoutUtils;

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "b2bCheckoutFacade")
	private CheckoutFacade b2bCheckoutFacade;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(final Class<?> aClass)
	{
		return SiteOneOrderTypeForm.class.equals(aClass);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(final Object object, final Errors errors)
	{
		final SiteOneOrderTypeForm orderTypeForm = (SiteOneOrderTypeForm) object;

		if (StringUtils.isBlank(orderTypeForm.getOrderType()))
		{
			errors.rejectValue("orderType", "OrderType Should be Selected");
		}

		if (StringUtils.isBlank(orderTypeForm.getAddressId()))
		{
			errors.rejectValue("addressId", "Select the Address");
		}
		else
		{
			if (orderTypeForm.getOrderType().equalsIgnoreCase("DELIVERY")
					&& !((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).validateAddressForDelivery(getFacadeForm(orderTypeForm)))
			{
				orderTypeForm.setAddressId(null);
				errors.rejectValue("addressId", "Select the Address");
			}
		}
		if (StringUtils.isBlank(orderTypeForm.getContactId()))
		{
			errors.rejectValue("contactId", "Select the contact");
		}
		if (null == orderTypeForm.getRequestedDate())
		{
			errors.rejectValue("requestedDate", "Date Should not be null");
		}

		if (userService.getCurrentUser() != null && !userService.isAnonymousUser(userService.getCurrentUser()))
		{
			if (((SiteOneB2BCheckoutFacade) b2bCheckoutFacade).isPONumberRequired() && StringUtils.isBlank(orderTypeForm.getPurchaseOrderNumber()))
			{
				errors.rejectValue("PurchaseOrderNumber", "Purchased Number should not be null");
			}
		}
		if (StringUtils.isBlank(orderTypeForm.getRequestedMeridian()))
		{
			errors.rejectValue("requestedMeridian", "Requested meridian  should not be null");
		}

		if (!orderTypeForm.isTermsAndConditions())
		{
			errors.rejectValue("termsAndConditions", "Please accept terms and conditions");
		}

	}

	protected SiteOneOrderTypeFacadeForm getFacadeForm(SiteOneOrderTypeForm siteOneOrderTypeForm) {
		SiteOneOrderTypeFacadeForm siteOneOrderTypeFormToFacade =new SiteOneOrderTypeFacadeForm();
		final BeanWrapper wrappedSource = new BeanWrapperImpl(siteOneOrderTypeForm);
		String[] nullProps= Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
				.toArray(String[]::new);
		BeanUtils.copyProperties(siteOneOrderTypeForm,siteOneOrderTypeFormToFacade,nullProps);
		return siteOneOrderTypeFormToFacade;
	}
}
