/**
 *
 */
package com.siteone.storefront.validator;

import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdatePasswordForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author 1099417
 *
 */
@Component("siteOneChangePwdValidator")
public class SiteOneChangePwdValidator implements Validator
{
	private static final String UPDATE_PWD_INVALID = "updatePwd.pwd.invalid";

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return UpdatePasswordForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final UpdatePasswordForm passwordForm = (UpdatePasswordForm) object;
		final String currPasswd = passwordForm.getCurrentPassword();
		final String newPasswd = passwordForm.getNewPassword();
		final String checkPasswd = passwordForm.getCheckNewPassword();

		if (StringUtils.isEmpty(currPasswd))
		{
			errors.rejectValue("currentPassword", "profile.currentPassword.invalid");
		}

		if (StringUtils.isEmpty(newPasswd))
		{
			errors.rejectValue("newPassword", "updatePwd.newPassword.invalid");
		}
		else if (StringUtils.length(newPasswd) < 8 || StringUtils.length(newPasswd) > 255)
		{
			errors.rejectValue("newPassword", UPDATE_PWD_INVALID);
		}

		if (StringUtils.isEmpty(checkPasswd))
		{
			errors.rejectValue("checkNewPassword", "updatePwd.checkPassword.invalid");
		}
		else if (StringUtils.length(checkPasswd) < 8 || StringUtils.length(checkPasswd) > 255)
		{
			errors.rejectValue("checkNewPassword", UPDATE_PWD_INVALID);
		}
	}


}
