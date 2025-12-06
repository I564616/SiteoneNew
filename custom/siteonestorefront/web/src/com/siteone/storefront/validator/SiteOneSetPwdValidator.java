/**
 *
 */
package com.siteone.storefront.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.siteone.storefront.forms.SiteOneSetPwdForm;


/**
 * @author 1099417
 *
 */
@Component("siteOneSetPwdFormValidator")
public class SiteOneSetPwdValidator implements Validator
{
	@Override
	public boolean supports(final Class<?> aClass)
	{
		return SiteOneSetPwdForm.class.equals(aClass);
	}


	@Override
	public void validate(final Object object, final Errors errors)
	{
		final SiteOneSetPwdForm setPasswordForm = (SiteOneSetPwdForm) object;
		final String newPassword = setPasswordForm.getPwd();
		final String checkPassword = setPasswordForm.getCheckPwd();

		if (StringUtils.isNotEmpty(newPassword) && StringUtils.isNotEmpty(checkPassword)
				&& !StringUtils.equals(newPassword, checkPassword))
		{
			errors.rejectValue("checkPwd", "validation.checkPwd.equals");
		}
		else
		{
			if (StringUtils.isEmpty(checkPassword))
			{
				errors.rejectValue("checkPwd", "setPwd.checkPwd.invalid");
			}
			if (StringUtils.isEmpty(newPassword))
			{
				errors.rejectValue("pwd", "setPwd.newPassword.invalid");
			}
		}
	}
}


