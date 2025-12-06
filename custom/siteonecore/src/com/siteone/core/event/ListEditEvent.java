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
public class ListEditEvent extends AbstractEvent
{
	private String listName;
	private String listCode;
	private String updateListName;
	private String emailAddress;
	private String storeAddress;
	private String contactNumber;
	private String storeId;
	private String customerName;
	private LanguageModel language;



	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName
	 *           the customerName to set
	 */
	public void setCustomerName(final String customerName)
	{
		this.customerName = customerName;
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
	 * @return the storeAddress
	 */
	public String getStoreAddress()
	{
		return storeAddress;
	}

	/**
	 * @param storeAddress
	 *           the storeAddress to set
	 */
	public void setStoreAddress(final String storeAddress)
	{
		this.storeAddress = storeAddress;
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
	 * @return the updateListName
	 */
	public String getUpdateListName()
	{
		return updateListName;
	}

	/**
	 * @param updateListName
	 *           the updateListName to set
	 */
	public void setUpdateListName(final String updateListName)
	{
		this.updateListName = updateListName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *           the emailAddress to set
	 */
	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	private BaseSiteModel site;

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
