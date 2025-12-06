/**
 *
 */
package com.siteone.storefront.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


/**
 * @author 1003567
 *
 */
public class SiteOneContactUsForm
{
	private String reasonForContact;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String customerNumber;
	private String typeOfCustomer;
	private String message;
	private String customerCity;
	private String customerState;
	private String projectZipcode;
	private String projectStartDate;
	private String inPhaseOf;
	private String myProject;
	private String myBudget;
	private String identity;
	private String identityName;
	private String streetAddress;


	/**
	 * @return the reasonForContact
	 */
	public String getReasonForContact()
	{
		return reasonForContact;
	}

	/**
	 * @param reasonForContact
	 *           the reasonForContact to set
	 */
	public void setReasonForContact(final String reasonForContact)
	{
		this.reasonForContact = reasonForContact;
	}

	/**
	 * @return the firstName
	 */
	@NotNull(message = "{request.firstName.invalid}")
	@Size(min = 1, max = 255, message = "{profile.firstName.invalid}")
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@NotNull(message = "{request.lastName.invalid}")
	@Size(min = 1, max = 255, message = "{profile.lastName.invalid}")
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *           the lastName to set
	 */
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	@NotNull(message = "{request.emailAddress.invalid}")
	@Size(min = 1, max = 255, message = "{request.emailAddress.invalid}")
	@Email(message = "{request.emailAddress.invalid}")
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
	 * @return the phoneNumber
	 */
	@NotNull(message = "{request.phonenumber.invalid}")
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *           the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the customerNumber
	 */
	@NotNull(message = "{contact.customerNumber.invalid}")
	@Size(min = 1, max = 50, message = "{contact.customerNumber.invalid}")
	public String getCustomerNumber()
	{
		return customerNumber;
	}

	/**
	 * @param customerNumber
	 *           the customerNumber to set
	 */
	public void setCustomerNumber(final String customerNumber)
	{
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer()
	{
		return typeOfCustomer;
	}

	/**
	 * @param typeOfCustomer
	 *           the typeOfCustomer to set
	 */
	public void setTypeOfCustomer(final String typeOfCustomer)
	{
		this.typeOfCustomer = typeOfCustomer;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message
	 *           the message to set
	 */
	public void setMessage(final String message)
	{
		this.message = message;
	}

	/**
	 * @return the customerCity
	 */
	@NotNull(message = "{request.customerCity.invalid}")
	@Size(min = 1, max = 255, message = "{profile.customerCity.invalid}")
	public String getCustomerCity()
	{
		return customerCity;
	}

	/**
	 * @param customerCity
	 *           the customerCity to set
	 */
	public void setCustomerCity(final String customerCity)
	{
		this.customerCity = customerCity;
	}

	/**
	 * @return the customerState
	 */
	@NotNull(message = "{request.customerState.invalid}")
	@Size(min = 1, max = 255, message = "{profile.customerState.invalid}")
	public String getCustomerState()
	{
		return customerState;
	}

	/**
	 * @param customerState
	 *           the customerState to set
	 */
	public void setCustomerState(final String customerState)
	{
		this.customerState = customerState;
	}

	/**
	 * @return the projectZipcode
	 */
	@NotNull(message = "{request.projectZipcode.invalid}")
	@Size(min = 1, max = 255, message = "{profile.projectZipcode.invalid}")
	public String getProjectZipcode()
	{
		return projectZipcode;
	}

	/**
	 * @param projectZipcode
	 *           the projectZipcode to set
	 */
	public void setProjectZipcode(final String projectZipcode)
	{
		this.projectZipcode = projectZipcode;
	}

	/**
	 * @return the projectStartDate
	 */
	public String getProjectStartDate()
	{
		return projectStartDate;
	}

	/**
	 * @param projectStartDate
	 *           the projectStartDate to set
	 */
	public void setProjectStartDate(final String projectStartDate)
	{
		this.projectStartDate = projectStartDate;
	}

	/**
	 * @return the inPhaseOf
	 */
	public String getInPhaseOf()
	{
		return inPhaseOf;
	}



	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress()
	{
		return streetAddress;
	}

	/**
	 * @param streetAddress
	 *           the streetAddress to set
	 */
	public void setStreetAddress(final String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	/**
	 * @param inPhaseOf
	 *           the inPhaseOf to set
	 */
	public void setInPhaseOf(final String inPhaseOf)
	{
		this.inPhaseOf = inPhaseOf;
	}

	/**
	 * @return the myProject
	 */
	public String getMyProject()
	{
		return myProject;
	}

	/**
	 * @param myProject
	 *           the myProject to set
	 */
	public void setMyProject(final String myProject)
	{
		this.myProject = myProject;
	}

	/**
	 * @return the myBudget
	 */
	public String getMyBudget()
	{
		return myBudget;
	}

	/**
	 * @param myBudget
	 *           the myBudget to set
	 */
	public void setMyBudget(final String myBudget)
	{
		this.myBudget = myBudget;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity()
	{
		return identity;
	}

	/**
	 * @param identity
	 *           the identity to set
	 */
	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	/**
	 * @return the identityName
	 */
	public String getIdentityName()
	{
		return identityName;
	}

	/**
	 * @param identityName
	 *           the identityName to set
	 */
	public void setIdentityName(final String identityName)
	{
		this.identityName = identityName;
	}


}
