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
public class SiteOneLeadGenerationForm
{
	private String firstname;
	private String lastname;
	private String company;
	private String Email;
	private String phone;
	private String postalcode;
	private String accountNo;
	private Boolean hasOpted;
	private String promotionId;

	/**
	 * @return the firstname
	 */
	@NotNull(message = "{leadForm.firstName.invalid}")
	@Size(min = 1, max = 255, message = "{leadForm.firstName.invalid}")
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * @param firstname
	 *           the firstname to set
	 */
	public void setFirstname(final String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	@NotNull(message = "{leadForm.lastName.invalid}")
	@Size(min = 1, max = 255, message = "{leadForm.lastName.invalid}")
	public String getLastname()
	{
		return lastname;
	}

	/**
	 * @param lastname
	 *           the lastname to set
	 */
	public void setLastname(final String lastname)
	{
		this.lastname = lastname;
	}

	/**
	 * @return the company
	 */
	@NotNull(message = "{leadForm.companyName.invalid}")
	@Size(min = 1, max = 255, message = "{leadForm.companyName.invalid}")
	public String getCompany()
	{
		return company;
	}

	/**
	 * @param company
	 *           the company to set
	 */
	public void setCompany(final String company)
	{
		this.company = company;
	}

	/**
	 * @return the email
	 */
	@NotNull(message = "{leadForm.email.invalid}")
	@Size(min = 1, max = 255, message = "{leadForm.email.invalid}")
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
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *           the phone to set
	 */
	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the postalcode
	 */
	@NotNull(message = "{leadForm.zip.invalid}")
	@Size(min = 1, max = 255, message = "{leadForm.zip.invalid}")
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
	 * @return the accountNo
	 */
	public String getAccountNo()
	{
		return accountNo;
	}

	/**
	 * @param accountNo
	 *           the accountNo to set
	 */
	public void setAccountNo(final String accountNo)
	{
		this.accountNo = accountNo;
	}

	/**
	 * @return the hasOpted
	 */
	public Boolean getHasOpted()
	{
		return hasOpted;
	}

	/**
	 * @param hasOpted
	 *           the hasOpted to set
	 */
	public void setHasOpted(final Boolean hasOpted)
	{
		this.hasOpted = hasOpted;
	}

	/**
	 * @return the promotionId
	 */
	public String getPromotionId()
	{
		return promotionId;
	}

	/**
	 * @param promotionId
	 *           the promotionId to set
	 */
	public void setPromotionId(final String promotionId)
	{
		this.promotionId = promotionId;
	}







}
