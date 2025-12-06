/**
 *
 */
package com.siteone.storefront.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * @author 1190626
 *
 */
public class SiteOneEmailSignUpForm
{
	private String Email;
	private String postalcode;
	private String userType;

	/**
	 * @return the email
	 */
	@NotNull(message = "{signUpForm.email.invalid }")
	@Size(min = 1, max = 255, message = "{signUpForm.email.invalid }")
	public String getEmail()
	{
		return Email;
	}

	/**
	 * @param email
	 *           the email to set
	 */
	public void setEmail(final String email)
	{
		Email = email;
	}

	/**
	 * @return the postalcode
	 */
	@NotNull(message = "{signUpForm.zip.invalid}")
	@Size(min = 1, max = 255, message = "{signUpForm.zip.invalid }")
	public String getPostalcode()
	{
		return postalcode;
	}

	/**
	 * @param postalcode
	 *           the postalcode to set
	 */
	public void setPostalcode(final String postalcode)
	{
		this.postalcode = postalcode;
	}

	/**
	 * @return the userType
	 */
	public String getUserType()
	{
		return userType;
	}

	/**
	 * @param userType
	 *           the userType to set
	 */
	public void setUserType(final String userType)
	{
		this.userType = userType;
	}


}
