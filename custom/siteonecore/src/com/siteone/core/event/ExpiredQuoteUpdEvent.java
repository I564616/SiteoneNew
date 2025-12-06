/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 *
 */
public class ExpiredQuoteUpdEvent extends AbstractCommerceUserEvent
{
	private String quoteNumber;
	private String notes;
	private String customerName;
	private String customerEmailAddress;
	private String accountName;
	private String accountId;
	private String phoneNumber;
	private String accountManagerEmail;
	private String branchManagerEmail;
	private String writerEmail;
	private String pricerEmail;
	private String InsideSalesRepEmail;
	private String quoteTotal;
	private String jobName;
	private String expDate;


	/**
	 * @return the quoteTotal
	 */
	public String getQuoteTotal()
	{
		return quoteTotal;
	}

	/**
	 * @param quoteTotal
	 *           the quoteTotal to set
	 */
	public void setQuoteTotal(final String quoteTotal)
	{
		this.quoteTotal = quoteTotal;
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
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return branchManagerEmail;
	}

	/**
	 * @param branchManagerEmail
	 *           the branchManagerEmail to set
	 */
	public void setBranchManagerEmail(final String branchManagerEmail)
	{
		this.branchManagerEmail = branchManagerEmail;
	}

	/**
	 * @return the writerEmail
	 */
	public String getWriterEmail()
	{
		return writerEmail;
	}

	/**
	 * @param writerEmail
	 *           the writerEmail to set
	 */
	public void setWriterEmail(final String writerEmail)
	{
		this.writerEmail = writerEmail;
	}

	/**
	 * @return the pricerEmail
	 */
	public String getPricerEmail()
	{
		return pricerEmail;
	}

	/**
	 * @param pricerEmail
	 *           the pricerEmail to set
	 */
	public void setPricerEmail(final String pricerEmail)
	{
		this.pricerEmail = pricerEmail;
	}

	/**
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return InsideSalesRepEmail;
	}

	/**
	 * @param insideSalesRepEmail
	 *           the insideSalesRepEmail to set
	 */
	public void setInsideSalesRepEmail(final String insideSalesRepEmail)
	{
		InsideSalesRepEmail = insideSalesRepEmail;
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
	 * @return the notes
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * @param notes
	 *           the notes to set
	 */
	public void setNotes(final String notes)
	{
		this.notes = notes;
	}

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
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress()
	{
		return customerEmailAddress;
	}

	/**
	 * @param customerEmailAddress
	 *           the customerEmailAddress to set
	 */
	public void setCustomerEmailAddress(final String customerEmailAddress)
	{
		this.customerEmailAddress = customerEmailAddress;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * @param accountName
	 *           the accountName to set
	 */
	public void setAccountName(final String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId()
	{
		return accountId;
	}

	/**
	 * @param accountId
	 *           the accountId to set
	 */
	public void setAccountId(final String accountId)
	{
		this.accountId = accountId;
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
}
