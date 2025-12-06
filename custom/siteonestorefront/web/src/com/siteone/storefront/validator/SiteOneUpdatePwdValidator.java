/**
 *
 */
package com.siteone.storefront.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siteone.storefront.forms.SiteOneUpdatePwdForm;


/**
 * @author 1099417
 *
 */
@Component("siteOneUpdatePwdFormValidator")
public class SiteOneUpdatePwdValidator implements Validator
{
	@Override
	public boolean supports(final Class<?> aClass)
	{
		return SiteOneUpdatePwdForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final SiteOneUpdatePwdForm updatePasswordForm = (SiteOneUpdatePwdForm) object;
		final String newPassword = updatePasswordForm.getPwd();
		final String checkPassword = updatePasswordForm.getCheckPwd();

		if (StringUtils.isNotEmpty(newPassword) && StringUtils.isNotEmpty(checkPassword)
				&& !StringUtils.equals(newPassword, checkPassword))
		{
			errors.rejectValue("checkPwd", "validation.checkPwd.equals");
		}
		else
		{
			if (StringUtils.isEmpty(checkPassword))
			{
				errors.rejectValue("checkPwd", "updatePwd.checkPwd.invalid");
			}

			if (StringUtils.isEmpty(newPassword))
			{
				errors.rejectValue("pwd", "updatePwd.newPassword.invalid");
			}
		}

	}
}
