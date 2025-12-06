/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 1003567
 *
 */
public class ShareListEvent extends AbstractCommerceUserEvent
{
	private String listName;
	private String listCode;
	private String userName;
	private String email;
	private Boolean showCustPrice;
	private Boolean showRetailPrice;
	private Map<String,String> retailPriceList;
	private Map<String,String> custPriceList;
	private String storeId;
	private String accountNumber;
	private String customerName;
	private String senderEmail;
	/**
	 * @return the senderEmail
	 */
	public String getSenderEmail()
	{
		return senderEmail;
	}

	/**
	 * @param senderEmail the senderEmail to set
	 */
	public void setSenderEmail(String senderEmail)
	{
		this.senderEmail = senderEmail;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
				

	public Map<String, String> getCustPriceList() {
		return custPriceList;
	}

	public void setCustPriceList(Map<String, String> custPriceList) {
		this.custPriceList = custPriceList;
	}

	public Map<String, String> getRetailPriceList() {
		return retailPriceList;
	}

	public void setRetailPriceList(Map<String, String> retailPriceList) {
		this.retailPriceList = retailPriceList;
	}




	public String getStoreId()
	{
		return storeId;
	}

	public void setStoreId(final String storeId)
	{
		this.storeId = storeId;
	}

	public void setShowCustPrice(final Boolean showCustPrice) {
		this.showCustPrice = showCustPrice;
	}

	public void setShowRetailPrice(Boolean showRetailPrice) {
		this.showRetailPrice = showRetailPrice;
	}

	public Boolean getShowCustPrice() {
		return showCustPrice;
	}

	public Boolean getShowRetailPrice() {
		return showRetailPrice;
	}



	public ShareListEvent()
	{
		super();
	}

	/**
	 * @return the listName
	 */
	public String getListName()
	{
		return listName;
	}

	/**
	 * @param listName
	 *           the listName to set
	 */
	public void setListName(final String listName)
	{
		this.listName = listName;
	}

	/**
	 * @return the listCode
	 */
	public String getListCode()
	{
		return listCode;
	}

	/**
	 * @param listCode
	 *           the listCode to set
	 */
	public void setListCode(final String listCode)
	{
		this.listCode = listCode;
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
