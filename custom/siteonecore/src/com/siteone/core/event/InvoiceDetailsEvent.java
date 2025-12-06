/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import com.siteone.core.model.SiteOneInvoiceModel;


/**
 * @author 1129929
 *
 */
public class InvoiceDetailsEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{

	private String invoiceNumber;

	private String emailAddress;
	
	private String uid;

	/**
	 * @return the uid
	 */
	public String getUid()
	{
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
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
	
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber()
	{
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber;
	}

}
