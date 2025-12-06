/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author AA04994
 *
 */
public class ContactSellerEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private String quoteNumber;
	private String customerName;
	private String quoteComments;
	private String quoteId;
	private String approverName;
	private String customerEmailAddress;
	private String accountManagerEmail;
	private String branchManagerEmail;
	private String insideSalesRepEmail;
	private String writerEmail;
	private String pricerEmail;
	private String accountName;
	private String accountId;
	private String phoneNumber;
	
	/**
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return insideSalesRepEmail;
	}

	/**
	 * @param insideSalesRepEmail the insideSalesRepEmail to set
	 */
	public void setInsideSalesRepEmail(String insideSalesRepEmail)
	{
		this.insideSalesRepEmail = insideSalesRepEmail;
	}
	
	/**
	 * @return the writerEmail
	 */
	public String getWriterEmail()
	{
		return writerEmail;
	}

	/**
	 * @param writerEmail the writerEmail to set
	 */
	public void setWriterEmail(String writerEmail)
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
	 * @param pricerEmail the pricerEmail to set
	 */
	public void setPricerEmail(String pricerEmail)
	{
		this.pricerEmail = pricerEmail;
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
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName
	 *           the accountName to set
	 */
	public void setCustomerName(final String customerName)
	{
		this.customerName = customerName;
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
	 * @return the comments
	 */
	public String getQuoteComments()
	{
		return quoteComments;
	}

	/**
	 * @param quoteComments
	 *           the comments to set
	 */
	public void setQuoteComments(final String quoteComments)
	{
		this.quoteComments = quoteComments;
	}

	/**
	 * @return the quoteId
	 */
	public String getQuoteId()
	{
		return quoteId;
	}

	/**
	 * @param quoteId
	 *           the quoteId to set
	 */
	public void setQuoteId(final String quoteId)
	{
		this.quoteId = quoteId;
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

	/**
	 * @return the approverName
	 */
	public String getApproverName()
	{
		return approverName;
	}

	/**
	 * @param approverName
	 *           the approverName to set
	 */
	public void setApproverName(final String approverName)
	{
		this.approverName = approverName;
	}
}
