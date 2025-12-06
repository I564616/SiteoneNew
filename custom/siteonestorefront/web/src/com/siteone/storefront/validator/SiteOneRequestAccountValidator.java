/**
 *
 */
package com.siteone.storefront.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.storefront.forms.SiteOneRequestAccountForm;


/**
 * @author 1129929
 *
 */
@Component("siteOneRequestAccountValidator")
public class SiteOneRequestAccountValidator implements Validator
{
	private static final int MAX_FIELD_LENGTH = 255;

	private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{3}?[-]\\d{3}?[-]\\d{4}");

	private static final Pattern YEARS_IN_BUSINESS_REGEX = Pattern.compile("\\d{1,3}(\\.\\d{1,2})?|\\.\\d{1,2}");


	@Override
	public boolean supports(final Class<?> clazz)
	{
		return SiteOneRequestAccountForm.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final SiteOneRequestAccountForm accountForm = (SiteOneRequestAccountForm) target;

		if (accountForm.getHasAccountNumber())
		{
			if (StringUtils.isBlank(accountForm.getAccountNumber()))
			{
				errors.rejectValue("accountNumber", "request.accountnumber.invalid");
			}
			else if (StringUtils.length(accountForm.getAccountNumber()) > MAX_FIELD_LENGTH)
			{
				errors.rejectValue("accountNumber", "request.accountnumber.invalid");
			}
		}

		if (accountForm.getTypeOfCustomer().equalsIgnoreCase(SiteoneCoreConstants.REQUEST_ACCOUNT_FORM_CONTRACTOR))
		{
			if (StringUtils.isBlank(accountForm.getCompanyName()))
			{
				errors.rejectValue("companyName", "request.companyname.invalid");
			}
			else if (StringUtils.length(accountForm.getCompanyName()) > MAX_FIELD_LENGTH)
			{
				errors.rejectValue("companyName", "request.companyname.invalid");
			}
			if (StringUtils.isBlank(accountForm.getContrEmpCount()))
			{
				errors.rejectValue("contrEmpCount", "request.contrempcount.invalid");
			}
			if (StringUtils.isBlank(accountForm.getContrYearsInBusiness()))
			{
				errors.rejectValue("contrYearsInBusiness", "request.contryearsinbusiness.empty");
			}
			else if (StringUtils.length(accountForm.getContrYearsInBusiness()) >= 1)
			{
				validateYearsInBusiness(errors, accountForm.getContrYearsInBusiness());
			}
		}

		if (StringUtils.isBlank(accountForm.getFirstName()))
		{
			errors.rejectValue("firstName", "request.firstName.invalid");
		}
		else if (StringUtils.length(accountForm.getFirstName()) > MAX_FIELD_LENGTH)
		{
			errors.rejectValue("firstName", "request.firstName.invalid");
		}

		if (StringUtils.isBlank(accountForm.getLastName()))
		{
			errors.rejectValue("lastName", "request.lastName.invalid");
		}
		else if (StringUtils.length(accountForm.getLastName()) > MAX_FIELD_LENGTH)
		{
			errors.rejectValue("lastName", "request.lastName.invalid");
		}

		if (StringUtils.isBlank(accountForm.getAddressLine1()))
		{
			errors.rejectValue("addressLine1", "request.address1.invalid");
		}
		else if (StringUtils.length(accountForm.getAddressLine1()) > MAX_FIELD_LENGTH)
		{
			errors.rejectValue("addressLine1", "request.address1.invalid");
		}

		if (StringUtils.isBlank(accountForm.getCity()))
		{
			errors.rejectValue("city", "request.city.invalid");
		}
		else if (StringUtils.length(accountForm.getCity()) > MAX_FIELD_LENGTH)
		{
			errors.rejectValue("city", "request.city.invalid");
		}

		if (StringUtils.isBlank(accountForm.getState()))
		{
			errors.rejectValue("state", "request.state.invalid");
		}

		if (StringUtils.isBlank(accountForm.getZipcode()))
		{
			errors.rejectValue("zipcode", "request.zipcode.invalid");
		}
		else if (StringUtils.length(accountForm.getZipcode()) > MAX_FIELD_LENGTH)
		{
			errors.rejectValue("zipcode", "request.zipcode.invalid");
		}

		if (StringUtils.length(accountForm.getPhoneNumber()) >= 1)
		{
			validatePhoneNumber(errors, accountForm.getPhoneNumber());
		}

	}

	protected void validatePhoneNumber(final Errors errors, final String phoneNumber)
	{
		final Matcher matcher = PHONE_NUMBER_REGEX.matcher(phoneNumber);
		final String number = phoneNumber.replaceAll("[()\\s-]+", "");
		if (!(matcher.matches()) || number.length() != 10)
		{
			errors.rejectValue("phoneNumber", "address.phone.invalid");
		}
	}

	protected void validateYearsInBusiness(final Errors errors, final String years)
	{
		final Matcher matcher = YEARS_IN_BUSINESS_REGEX.matcher(years);
		if (!(matcher.matches()))
		{
			errors.rejectValue("contrYearsInBusiness", "request.contryearsinbusiness.invalid");
		}
	}

	public void validatePreferredLanguage(final String languagePreference, final Errors errors)
	{


		if (null == languagePreference || languagePreference.isEmpty())
		{
			errors.rejectValue("languagePreference", "Wrong Selection");
		}
	}

}

