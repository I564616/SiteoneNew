/**
 *
 */
package com.siteone.utils;

/**
 * @author Karasan
 *
 */
public class SiteOneGCContactForm
{
	private String firstName;
	private String lastName;
	private String companyName;
	private String phone;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private Boolean isSameaddressforParcelShip;
	private String deliveryMode;
	private Boolean isSameaddressforDelivery;

	/**
	 * @return the deliveryMode
	 */
	public String getDeliveryMode()
	{
		return deliveryMode;
	}

	/**
	 * @param deliveryMode
	 *           the deliveryMode to set
	 */
	public void setDeliveryMode(final String deliveryMode)
	{
		this.deliveryMode = deliveryMode;
	}
	
	
	public Boolean getIsSameaddressforDelivery()
	{
		return isSameaddressforDelivery;
	}

	/**
	 * @param isSameaddressforDelivery
	 *           the isSameaddressforDelivery to set
	 */
	public void setIsSameaddressforDelivery(final Boolean isSameaddressforDelivery)
	{
		this.isSameaddressforDelivery = isSameaddressforDelivery;
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
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return companyName;
	}

	/**
	 * @param companyName
	 *           the companyName to set
	 */
	public void setCompanyName(final String companyName)
	{
		this.companyName = companyName;
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
	 * @return the addressLine1
	 */
	public String getAddressLine1()
	{
		return addressLine1;
	}

	/**
	 * @param addressLine1
	 *           the addressLine1 to set
	 */
	public void setAddressLine1(final String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2()
	{
		return addressLine2;
	}

	/**
	 * @param addressLine2
	 *           the addressLine2 to set
	 */
	public void setAddressLine2(final String addressLine2)
	{
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *           the city to set
	 */
	public void setCity(final String city)
	{
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *           the state to set
	 */
	public void setState(final String state)
	{
		this.state = state;
	}

	/**
	 * @return the zip
	 */
	public String getZip()
	{
		return zip;
	}

	/**
	 * @param zip
	 *           the zip to set
	 */
	public void setZip(final String zip)
	{
		this.zip = zip;
	}

	/**
	 * @return the isSameaddressforParcelShip
	 */
	public Boolean getIsSameaddressforParcelShip()
	{
		return isSameaddressforParcelShip;
	}

	/**
	 * @param isSameaddressforParcelShip
	 *           the isSameaddressforParcelShip to set
	 */
	public void setIsSameaddressforParcelShip(final Boolean isSameaddressforParcelShip)
	{
		this.isSameaddressforParcelShip = isSameaddressforParcelShip;
	}

}
