/**
 *
 */
package com.siteone.storefront.validator;

import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.AddressValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.siteone.storefront.forms.SiteOneAddressForm;


/**
 * @author 1129929
 *
 */
@Component("siteoneAddressValidator")
public class SiteOneAddressValidator extends AddressValidator
{
	private static final int MAX_FIELD_LENGTH = 255;

	private final Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{3}?[-]\\d{3}?[-]\\d{4}");

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return SiteOneAddressForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final SiteOneAddressForm addressForm = (SiteOneAddressForm) object;

		if (StringUtils.length(addressForm.getPhone()) >= 1)
		{
			validatePhoneNumber(errors, addressForm.getPhone());
		}

		validateCompanyName(addressForm.getCompanyName(), MAX_FIELD_LENGTH, errors);
		validateParentunit(addressForm.getUnitId(), MAX_FIELD_LENGTH, errors);
		validateCountry(addressForm.getCountryIso(), MAX_FIELD_LENGTH, errors);
		validateAddressLine1(addressForm.getLine1(), MAX_FIELD_LENGTH, errors);
		validateTown(addressForm.getTownCity(), MAX_FIELD_LENGTH, errors);
		validatePostCode(addressForm.getPostcode(), MAX_FIELD_LENGTH, errors);

	}

	protected void validatePhoneNumber(final Errors errors, final String phoneNumber)
	{
		final Matcher matcher = PHONE_NUMBER_REGEX.matcher(phoneNumber);
		final String number = phoneNumber.replaceAll("[()\\s-]+", "");
		if (!(matcher.matches()) || number.length() != 10)
		{
			errors.rejectValue("phone", "address.phone.invalid");
		}
	}

	protected void validateCompanyName(final String addressField, final int maxFieldLength, final Errors errors)
	{
		if (StringUtils.isBlank(addressField))
		{
			errors.rejectValue("companyName", "address.companyName.invalid");
		}
		else if (StringUtils.length(addressField) > maxFieldLength)
		{
			errors.rejectValue("companyName", "address.companyName.invalid");
		}
	}

	protected void validateParentunit(final String addressField, final int maxFieldLength, final Errors errors)
	{
		if (StringUtils.isBlank(addressField))
		{
			errors.rejectValue("unitId", "address.unitId.invalid");
		}
		else if (StringUtils.length(addressField) > maxFieldLength)
		{
			errors.rejectValue("unitId", "address.unitId.invalid");
		}
	}

	protected void validateCountry(final String addressField, final int maxFieldLength, final Errors errors)
	{
		if (StringUtils.isBlank(addressField))
		{
			errors.rejectValue("countryIso", "address.country.invalid");
		}
		else if (StringUtils.length(addressField) > maxFieldLength)
		{
			errors.rejectValue("countryIso", "address.country.invalid");
		}
	}

	protected void validateAddressLine1(final String addressField, final int maxFieldLength, final Errors errors)
	{
		if (StringUtils.isBlank(addressField))
		{
			errors.rejectValue("line1", "address.line1.invalid");
		}
		else if (StringUtils.length(addressField) > maxFieldLength)
		{
			errors.rejectValue("line1", "address.line1.invalid");
		}
	}

	protected void validateTown(final String addressField, final int maxFieldLength, final Errors errors)
	{
		if (StringUtils.isBlank(addressField))
		{
			errors.rejectValue("townCity", "address.townCity.invalid");
		}
		else if (StringUtils.length(addressField) > maxFieldLength)
		{
			errors.rejectValue("townCity", "address.townCity.invalid");
		}
	}

	protected void validatePostCode(final String addressField, final int maxFieldLength, final Errors errors)
	{
		if (StringUtils.isBlank(addressField))
		{
			errors.rejectValue("postcode", "address.postcode.invalid");
		}
		else if (StringUtils.length(addressField) > maxFieldLength)
		{
			errors.rejectValue("postcode", "address.postcode.invalid");
		}
	}
}
