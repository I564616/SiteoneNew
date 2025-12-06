/**
 *
 */
package com.siteone.storefront.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siteone.storefront.forms.CCPAForm;


/**
 * @author 1129929
 *
 */
@Component("siteoneCCPAValidator")
public class SiteOneCCPAValidator implements Validator
{
	private static final int MAX_FIELD_LENGTH = 255;

	private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{3}?[-]\\d{3}?[-]\\d{4}");

	@Override
	public boolean supports(final Class<?> clazz)
	{
		return CCPAForm.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final CCPAForm accountForm = (CCPAForm) target;

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

		if (StringUtils.isBlank(accountForm.getState()))
		{
			errors.rejectValue("state", "request.state.invalid");
		}



	}

}

