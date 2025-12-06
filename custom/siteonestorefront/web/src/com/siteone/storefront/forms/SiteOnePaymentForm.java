/**
 *
 */
package com.siteone.storefront.forms;

/**
 * @author KArasan
 *
 */
public class SiteOnePaymentForm
{

	private String status;
	private String refID;
	private String token;
	private String authCode;
	private String cardType;
	private String cardNumber;
	private String expDate;
	private String AVS;
	private String CVV;
	private String entryMode;
	private String validationKey;
	private String address;
	private String zipCode;
	private String cardholder;

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}

	/**
	 * @return the refID
	 */
	public String getRefID()
	{
		return refID;
	}

	/**
	 * @param refID
	 *           the refID to set
	 */
	public void setRefID(final String refID)
	{
		this.refID = refID;
	}

	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * @param token
	 *           the token to set
	 */
	public void setToken(final String token)
	{
		this.token = token;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode()
	{
		return authCode;
	}

	/**
	 * @param authCode
	 *           the authCode to set
	 */
	public void setAuthCode(final String authCode)
	{
		this.authCode = authCode;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType()
	{
		return cardType;
	}

	/**
	 * @param cardType
	 *           the cardType to set
	 */
	public void setCardType(final String cardType)
	{
		this.cardType = cardType;
	}
	/**
	 * @return the cardNumber
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *           the cardNumber to set
	 */
	public void setCardNumber(final String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return expDate;
	}

	/**
	 * @param expDate
	 *           the expDate to set
	 */
	public void setExpDate(final String expDate)
	{
		this.expDate = expDate;
	}

	/**
	 * @return the aVS
	 */
	public String getAVS()
	{
		return AVS;
	}

	/**
	 * @param aVS
	 *           the aVS to set
	 */
	public void setAVS(final String aVS)
	{
		AVS = aVS;
	}

	/**
	 * @return the cVV
	 */
	public String getCVV()
	{
		return CVV;
	}

	/**
	 * @param cVV
	 *           the cVV to set
	 */
	public void setCVV(final String cVV)
	{
		CVV = cVV;
	}

	/**
	 * @return the entryMode
	 */
	public String getEntryMode()
	{
		return entryMode;
	}

	/**
	 * @param entryMode
	 *           the entryMode to set
	 */
	public void setEntryMode(final String entryMode)
	{
		this.entryMode = entryMode;
	}

	/**
	 * @return the validationKey
	 */
	public String getValidationKey()
	{
		return validationKey;
	}

	/**
	 * @param validationKey
	 *           the validationKey to set
	 */
	public void setValidationKey(final String validationKey)
	{
		this.validationKey = validationKey;
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
	 * @return the zipCode
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param zipCode
	 *           the zipCode to set
	 */
	public void setZipCode(final String zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * @return the cardholder
	 */
	public String getCardholder()
	{
		return cardholder;
	}

	/**
	 * @param cardholder
	 *           the cardholder to set
	 */
	public void setCardholder(final String cardholder)
	{
		this.cardholder = cardholder;
	}

}
