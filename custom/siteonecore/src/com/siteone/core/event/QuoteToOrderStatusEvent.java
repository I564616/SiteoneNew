/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import java.util.Date;


/**
 *
 */
public class QuoteToOrderStatusEvent extends AbstractCommerceUserEvent
{
	String toMail;
	String poNumber;
	String jobName;
	String quoteNumber;
	String quoteWriter;
	String accountManager;
	String orderDate;	
	String accountManagerPhone;
	String accountManagerEmail;
	String accountAdminEmail;
	String orderNumber;
	String accountNumber;
	String firstName;
	String orderId;
	String consignmentId;
	String status;
	Boolean quoteToOrderEmailsent;
	
	/**
	 * @return the orderId
	 */
	public String getOrderId()
	{
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/**
	 * @return the consignmentId
	 */
	public String getConsignmentId()
	{
		return consignmentId;
	}

	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(String consignmentId)
	{
		this.consignmentId = consignmentId;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	/**
	 * @return the orderDate
	 */
	public String getOrderDate()
	{
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	/**
	 * @return the quoteToOrderEmailsent
	 */
	public Boolean getQuoteToOrderEmailsent()
	{
		return quoteToOrderEmailsent;
	}

	/**
	 * @param quoteToOrderEmailsent the quoteToOrderEmailsent to set
	 */
	public void setQuoteToOrderEmailsent(Boolean quoteToOrderEmailsent)
	{
		this.quoteToOrderEmailsent = quoteToOrderEmailsent;
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
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *           the accountNumber to set
	 */
	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the toMail
	 */
	public String getToMail()
	{
		return toMail;
	}

	/**
	 * @param toMail
	 *           the toMail to set
	 */
	public void setToMail(final String toMail)
	{
		this.toMail = toMail;
	}

	

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *           the orderNumber to set
	 */
	public void setOrderNumber(final String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return poNumber;
	}

	/**
	 * @param poNumber
	 *           the poNumber to set
	 */
	public void setPoNumber(final String poNumber)
	{
		this.poNumber = poNumber;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName()
	{
		return jobName;
	}

	/**
	 * @param jobName
	 *           the jobName to set
	 */
	public void setJobName(final String jobName)
	{
		this.jobName = jobName;
	}

	/**
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return quoteNumber;
	}

	/**
	 * @param quoteNumber
	 *           the quoteNumber to set
	 */
	public void setQuoteNumber(final String quoteNumber)
	{
		this.quoteNumber = quoteNumber;
	}

	/**
	 * @return the quoteWriter
	 */
	public String getQuoteWriter()
	{
		return quoteWriter;
	}

	/**
	 * @param quoteWriter
	 *           the quoteWriter to set
	 */
	public void setQuoteWriter(final String quoteWriter)
	{
		this.quoteWriter = quoteWriter;
	}

	/**
	 * @return the accountManager
	 */
	public String getAccountManager()
	{
		return accountManager;
	}

	/**
	 * @param accountManager
	 *           the accountManager to set
	 */
	public void setAccountManager(final String accountManager)
	{
		this.accountManager = accountManager;
	}

	/**
	 * @return the accountManagerPhone
	 */
	public String getAccountManagerPhone()
	{
		return accountManagerPhone;
	}

	/**
	 * @param accountManagerPhone
	 *           the accountManagerPhone to set
	 */
	public void setAccountManagerPhone(final String accountManagerPhone)
	{
		this.accountManagerPhone = accountManagerPhone;
	}

	/**
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return accountManagerEmail;
	}

	/**
	 * @param accountManagerEmail
	 *           the accountManagerEmail to set
	 */
	public void setAccountManagerEmail(final String accountManagerEmail)
	{
		this.accountManagerEmail = accountManagerEmail;
	}

	/**
	 * @return the accountAdminEmail
	 */
	public String getAccountAdminEmail()
	{
		return accountAdminEmail;
	}

	/**
	 * @param accountAdminEmail
	 *           the accountAdminEmail to set
	 */
	public void setAccountAdminEmail(final String accountAdminEmail)
	{
		this.accountAdminEmail = accountAdminEmail;
	}

}
