/**
 *
 */
package com.siteone.storefront.validator;

import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateProfileForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ProfileValidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


/**
 * @author 1190626
 *
 */
@Component("siteOneProfileValidator")
public class SiteOneProfileValidator extends ProfileValidator
{
	@Override
	public void validate(final Object object, final Errors errors)
	{
		final UpdateProfileForm profileForm = (UpdateProfileForm) object;
		final String firstName = profileForm.getFirstName();
		final String lastName = profileForm.getLastName();

		if (StringUtils.isBlank(firstName))
		{
			errors.rejectValue("firstName", "profile.firstName.invalid");
		}
		else if (StringUtils.length(firstName) > 255)
		{
			errors.rejectValue("firstName", "profile.firstName.invalid");
		}

		if (StringUtils.isBlank(lastName))
		{
			errors.rejectValue("lastName", "profile.lastName.invalid");
		}
		else if (StringUtils.length(lastName) > 255)
		{
			errors.rejectValue("lastName", "profile.lastName.invalid");
		}
	}

}


