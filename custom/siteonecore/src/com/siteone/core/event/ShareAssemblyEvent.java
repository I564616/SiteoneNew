/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author 1003567
 *
 */
public class ShareAssemblyEvent extends AbstractCommerceUserEvent
{
	/**
	 *
	 */
	public ShareAssemblyEvent()
	{
		super();
	}

	private String assemblyName;
	private String assemblyCode;
	private String userName;
	private String email;


	/**
	 * @return the assemblyName
	 */
	public String getAssemblyName()
	{
		return assemblyName;
	}

	/**
	 * @param assemblyName
	 *           the assemblyName to set
	 */
	public void setAssemblyName(final String assemblyName)
	{
		this.assemblyName = assemblyName;
	}

	/**
	 * @return the assemblyCode
	 */
	public String getAssemblyCode()
	{
		return assemblyCode;
	}

	/**
	 * @param assemblyCode
	 *           the assemblyCode to set
	 */
	public void setAssemblyCode(final String assemblyCode)
	{
		this.assemblyCode = assemblyCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName
	 *           the userName to set
	 */
	public void setUserName(final String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *           the email to set
	 */
	public void setEmail(final String email)
	{
		this.email = email;
	}


}
