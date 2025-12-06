/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;


/**
 * @author 1003567
 *
 */
public class ContactUsEvent extends AbstractEvent
{
	private String reasonForContact;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String customerNumber;
	private String typeOfCustomer;
	private String message;
	private BaseSiteModel site;
	private LanguageModel language;
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
	 * @return the site
	 */
	public BaseSiteModel getSite()
	{
		return site;
	}

	/**
	 * @param site
	 *           the site to set
	 */
	public void setSite(final BaseSiteModel site)
	{
		this.site = site;
	}

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
	 * @return the language
	 */
	public LanguageModel getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *           the language to set
	 */
	public void setLanguage(final LanguageModel language)
	{
		this.language = language;
	}

	/**
	 * @return the customerCity
	 */
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


}
