/**
 *
 */
package com.siteone.storefront.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


/**
 * @author 1197861
 *
 */
public class SiteOneRequestAccountOnlineAccessForm
{
	private String accountNumber;
	private String emailAddress;
	private String code;

	/**
	 * @return the accountNumber
	 */
	@NotNull(message = "{request.accountnumber.invalid}")
	@Size(min = 1, max = 255, message = "{request.accountnumber.invalid}")
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *           the accountNumber to set
	 */
	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the emailAddress
	 */
	@NotNull(message = "{request.emailAddress.invalid}")
	@Size(min = 1, max = 255, message = "{request.emailAddress.invalid}")
	@Email(message = "{request.emailAddress.invalid}")
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *           the emailAddress to set
	 */
	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}


}
