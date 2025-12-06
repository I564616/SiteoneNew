package com.siteone.integration.exception.okta;

import java.util.List;

import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.user.PasswordPolicyViolation;

public class OktaInvalidPasswordException extends SystemException{
	
	private List<PasswordPolicyViolation> PasswordPolicyViolation;
	
	public OktaInvalidPasswordException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public OktaInvalidPasswordException(final String message)
	{
		super(message);
	}

	public OktaInvalidPasswordException(final Throwable cause)
	{
		super(cause);
	}

	/**
	 * @return the passwordPolicyViolation
	 */
	public List<PasswordPolicyViolation> getPasswordPolicyViolation() {
		return PasswordPolicyViolation;
	}

	/**
	 * @param passwordPolicyViolation the passwordPolicyViolation to set
	 */
	public void setPasswordPolicyViolation(List<PasswordPolicyViolation> passwordPolicyViolation) {
		PasswordPolicyViolation = passwordPolicyViolation;
	}

}
