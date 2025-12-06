/**
 *
 */
package com.siteone.storefront.forms;

/**
 * @author 205210
 *
 */
public class SiteOneUpdateProfileForm
{
	private String password;

	private String name;
	private String email;

	private String contactNumber;

	private String isEmailOpt;

	private String isSMSOpt;
	private Boolean isAdmin;


	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin()
	{
		return isAdmin;
	}

	/**
	 * @param isAdmin
	 *           the isAdmin to set
	 */
	public void setIsAdmin(final Boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}


	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *           the password to set
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *           the name to set
	 */
	public void setName(final String name)
	{
		this.name = name;
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

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber()
	{
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *           the contactNumber to set
	 */
	public void setContactNumber(final String contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the isEmailOpt
	 */
	public String getIsEmailOpt()
	{
		return isEmailOpt;
	}

	/**
	 * @param isEmailOpt
	 *           the isEmailOpt to set
	 */
	public void setIsEmailOpt(final String isEmailOpt)
	{
		this.isEmailOpt = isEmailOpt;
	}

	/**
	 * @return the isSMSOpt
	 */
	public String getIsSMSOpt()
	{
		return isSMSOpt;
	}

	/**
	 * @param isSMSOpt
	 *           the isSMSOpt to set
	 */
	public void setIsSMSOpt(final String isSMSOpt)
	{
		this.isSMSOpt = isSMSOpt;
	}


}