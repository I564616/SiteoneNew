/**
 *
 */
package com.siteone.facades.checkout.form;

import java.util.Date;


/**
 * @author bsaha
 *
 */
public class SiteOneOrderTypeFacadeForm
{
	private String orderType;
	private String storeId;
	private String addressId;
	private String deliveryAddressId;
	private String shippingAddressId;
	private String deliveryContactId;
	private String shippingContactId;
	private String contactId;
	private String date;
	private Date requestedDate;
	private String time;
	private String specialInstruction;
	private String PurchaseOrderNumber;
	boolean termsAndConditions;
	private String requestedMeridian;
	private String kountSessionId;
	private String division;
	boolean expediteDelivery;
	boolean chooseLift;
	boolean isHomeOwner;
	boolean isGuestUser;
	boolean isExpedited;
	private String pickupInstruction;
	private String deliveryInstruction;
	private String shippingInstruction;
	String isSameAsContactInfo;
	private String isSameAsContactInfoDelivery;
	private String isSameAsContactInfoShipping;

	/**
	 * @return the requestedMeridian
	 */
	public String getRequestedMeridian()
	{
		return requestedMeridian;
	}

	/**
	 * @param requestedMeridian
	 *           the requestedMeridian to set
	 */
	public void setRequestedMeridian(final String requestedMeridian)
	{
		this.requestedMeridian = requestedMeridian;
	}

	/**
	 * @return the termsAndConditions
	 */
	public boolean isTermsAndConditions()
	{
		return termsAndConditions;
	}

	/**
	 * @param termsAndConditions
	 *           the termsAndConditions to set
	 */
	public void setTermsAndConditions(final boolean termsAndConditions)
	{
		this.termsAndConditions = termsAndConditions;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return storeId;
	}

	/**
	 * @param storeId
	 *           the storeId to set
	 */
	public void setStoreId(final String storeId)
	{
		this.storeId = storeId;
	}

	/**
	 * @return the contactId
	 */
	public String getContactId()
	{
		return contactId;
	}

	/**
	 * @param contactId
	 *           the contactId to set
	 */
	public void setContactId(final String contactId)
	{
		this.contactId = contactId;
	}

	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time
	 *           the time to set
	 */
	public void setTime(final String time)
	{
		this.time = time;
	}

	/**
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction()
	{
		return specialInstruction;
	}

	/**
	 * @param specialInstruction
	 *           the specialInstruction to set
	 */
	public void setSpecialInstruction(final String specialInstruction)
	{
		this.specialInstruction = specialInstruction;
	}

	/**
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber()
	{
		return PurchaseOrderNumber;
	}

	/**
	 * @param purchaseOrderNumber
	 *           the purchaseOrderNumber to set
	 */
	public void setPurchaseOrderNumber(final String purchaseOrderNumber)
	{
		PurchaseOrderNumber = purchaseOrderNumber;
	}

	/**
	 * @return the addressId
	 */
	public String getAddressId()
	{
		return addressId;
	}

	/**
	 * @param addressId
	 *           the addressId to set
	 */
	public void setAddressId(final String addressId)
	{
		this.addressId = addressId;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType()
	{
		return orderType;
	}

	/**
	 * @param orderType
	 *           the orderType to set
	 */
	public void setOrderType(final String orderType)
	{
		this.orderType = orderType;
	}

	/**
	 * @return the requestedDate
	 */
	public Date getRequestedDate()
	{
		return requestedDate;
	}

	/**
	 * @param requestedDate
	 *           the requestedDate to set
	 */
	public void setRequestedDate(final Date requestedDate)
	{
		this.requestedDate = requestedDate;
	}

	/**
	 * @return the date
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * @param date
	 *           the date to set
	 */
	public void setDate(final String date)
	{
		this.date = date;
	}

	/**
	 * @return the kountSessionId
	 */
	public String getKountSessionId()
	{
		return kountSessionId;
	}

	/**
	 * @param kountSessionId
	 *           the kountSessionId to set
	 */
	public void setKountSessionId(final String kountSessionId)
	{
		this.kountSessionId = kountSessionId;
	}

	public String getDivision()
	{
		return division;
	}

	public void setDivision(final String division)
	{
		this.division = division;
	}

	public boolean isExpediteDelivery()
	{
		return expediteDelivery;
	}

	public void setExpediteDelivery(final boolean expediteDelivery)
	{
		this.expediteDelivery = expediteDelivery;
	}

	public boolean isChooseLift()
	{
		return chooseLift;
	}

	public void setChooseLift(final boolean chooseLift)
	{
		this.chooseLift = chooseLift;
	}

	public boolean isHomeOwner()
	{
		return isHomeOwner;
	}

	public void setHomeOwner(final boolean isHomeOwner)
	{
		this.isHomeOwner = isHomeOwner;
	}

	public boolean isGuestUser()
	{
		return isGuestUser;
	}

	public void setGuestUser(final boolean isGuestUser)
	{
		this.isGuestUser = isGuestUser;
	}

	public boolean isExpedited()
	{
		return isExpedited;
	}

	public void setExpedited(final boolean isExpedited)
	{
		this.isExpedited = isExpedited;
	}

	/**
	 * @return the isSameAsContactInfo
	 */
	public String getIsSameAsContactInfo()
	{
		return isSameAsContactInfo;
	}

	/**
	 * @param isSameAsContactInfo
	 *           the isSameAsContactInfo to set
	 */
	public void setIsSameAsContactInfo(final String isSameAsContactInfo)
	{
		this.isSameAsContactInfo = isSameAsContactInfo;
	}

	/**
	 * @return the pickupInstruction
	 */
	public String getPickupInstruction()
	{
		return pickupInstruction;
	}

	/**
	 * @param pickupInstruction
	 *           the pickupInstruction to set
	 */
	public void setPickupInstruction(final String pickupInstruction)
	{
		this.pickupInstruction = pickupInstruction;
	}

	/**
	 * @return the deliveryInstruction
	 */
	public String getDeliveryInstruction()
	{
		return deliveryInstruction;
	}

	/**
	 * @param deliveryInstruction
	 *           the deliveryInstruction to set
	 */
	public void setDeliveryInstruction(final String deliveryInstruction)
	{
		this.deliveryInstruction = deliveryInstruction;
	}

	/**
	 * @return the shippingInstruction
	 */
	public String getShippingInstruction()
	{
		return shippingInstruction;
	}

	/**
	 * @param shippingInstruction
	 *           the shippingInstruction to set
	 */
	public void setShippingInstruction(final String shippingInstruction)
	{
		this.shippingInstruction = shippingInstruction;
	}

	/**
	 * @return the deliveryAddressId
	 */
	public String getDeliveryAddressId()
	{
		return deliveryAddressId;
	}

	/**
	 * @param deliveryAddressId
	 *           the deliveryAddressId to set
	 */
	public void setDeliveryAddressId(final String deliveryAddressId)
	{
		this.deliveryAddressId = deliveryAddressId;
	}

	/**
	 * @return the shippingAddressId
	 */
	public String getShippingAddressId()
	{
		return shippingAddressId;
	}

	/**
	 * @param shippingAddressId
	 *           the shippingAddressId to set
	 */
	public void setShippingAddressId(final String shippingAddressId)
	{
		this.shippingAddressId = shippingAddressId;
	}

	/**
	 * @return the deliveryContactId
	 */
	public String getDeliveryContactId()
	{
		return deliveryContactId;
	}

	/**
	 * @param deliveryContactId
	 *           the deliveryContactId to set
	 */
	public void setDeliveryContactId(final String deliveryContactId)
	{
		this.deliveryContactId = deliveryContactId;
	}

	/**
	 * @return the shippingContactId
	 */
	public String getShippingContactId()
	{
		return shippingContactId;
	}

	/**
	 * @param shippingContactId
	 *           the shippingContactId to set
	 */
	public void setShippingContactId(final String shippingContactId)
	{
		this.shippingContactId = shippingContactId;
	}

	/**
	 * @return the isSameAsContactInfoDelivery
	 */
	public String getIsSameAsContactInfoDelivery()
	{
		return isSameAsContactInfoDelivery;
	}

	/**
	 * @param isSameAsContactInfoDelivery
	 *           the isSameAsContactInfoDelivery to set
	 */
	public void setIsSameAsContactInfoDelivery(final String isSameAsContactInfoDelivery)
	{
		this.isSameAsContactInfoDelivery = isSameAsContactInfoDelivery;
	}

	/**
	 * @return the isSameAsContactInfoShipping
	 */
	public String getIsSameAsContactInfoShipping()
	{
		return isSameAsContactInfoShipping;
	}

	/**
	 * @param isSameAsContactInfoShipping
	 *           the isSameAsContactInfoShipping to set
	 */
	public void setIsSameAsContactInfoShipping(final String isSameAsContactInfoShipping)
	{
		this.isSameAsContactInfoShipping = isSameAsContactInfoShipping;
	}



}