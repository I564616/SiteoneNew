/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;


public class HomeOwnerEvent extends AbstractEvent
{
	private String firstName;
	private String lastName;
	private String emailAddr;
	private String phone;
	private String address;
	private String customerCity;
	private String customerState;
	private String customerZipCode;

	private String bestTimeToCall;
	private String serviceType;
	private String referalsNo;
	private String lookingFor;
	private String lookingForOthers;

	private BaseSiteModel site;
	private LanguageModel language;

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
	 * @return the emailAddr
	 */
	public String getEmailAddr()
	{
		return emailAddr;
	}

	/**
	 * @param emailAddr
	 *           the emailAddr to set
	 */
	public void setEmailAddr(final String emailAddr)
	{
		this.emailAddr = emailAddr;
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
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *           the address to set
	 */
	public void setAddress(final String address)
	{
		this.address = address;
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
	 * @return the customerZipCode
	 */
	public String getCustomerZipCode()
	{
		return customerZipCode;
	}

	/**
	 * @param customerZipCode
	 *           the customerZipCode to set
	 */
	public void setCustomerZipCode(final String customerZipCode)
	{
		this.customerZipCode = customerZipCode;
	}

	/**
	 * @return the bestTimeToCall
	 */
	public String getBestTimeToCall()
	{
		return bestTimeToCall;
	}

	/**
	 * @param bestTimeToCall
	 *           the bestTimeToCall to set
	 */
	public void setBestTimeToCall(final String bestTimeToCall)
	{
		this.bestTimeToCall = bestTimeToCall;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType()
	{
		return serviceType;
	}

	/**
	 * @param serviceType
	 *           the serviceType to set
	 */
	public void setServiceType(final String serviceType)
	{
		this.serviceType = serviceType;
	}

	/**
	 * @return the referalsNo
	 */
	public String getReferalsNo()
	{
		return referalsNo;
	}

	/**
	 * @param referalsNo
	 *           the referalsNo to set
	 */
	public void setReferalsNo(final String referalsNo)
	{
		this.referalsNo = referalsNo;
	}

	/**
	 * @return the lookingFor
	 */
	public String getLookingFor()
	{
		return lookingFor;
	}

	/**
	 * @param lookingFor
	 *           the lookingFor to set
	 */
	public void setLookingFor(final String lookingFor)
	{
		this.lookingFor = lookingFor;
	}

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
	 * @return the lookingForOthers
	 */
	public String getLookingForOthers()
	{
		return lookingForOthers;
	}

	/**
	 * @param lookingForOthers
	 *           the lookingForOthers to set
	 */
	public void setLookingForOthers(final String lookingForOthers)
	{
		this.lookingForOthers = lookingForOthers;
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


}
