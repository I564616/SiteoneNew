/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import java.util.List;

import com.siteone.core.model.QuoteItemDetailsModel;


/**
 * @author AA04994
 *
 */
public class RequestQuoteEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	private String jobName;
	private String jobStartDate;
	private String branch;
	private String accountName;
	private String quoteHeaderID;
	private String jobDescription;
	private String comment;
	private String customerName;
	private String customerEmailAddress;
	private String accountManagerEmail;
	private String branchManagerEmail;
	private String insideSalesRepEmail;
	private String accountId;
	private String phoneNumber;
	private List<QuoteItemDetailsModel> itemDetails;
	private List<QuoteItemDetailsModel> customItemDetails;
	
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
	 * @return the quoteId
	 */
	public String getQuoteHeaderID()
	{
		return quoteHeaderID;
	}

	/**
	 * @param quoteId
	 *           the quoteId to set
	 */
	public void setQuoteHeaderID(final String quoteHeaderID)
	{
		this.quoteHeaderID = quoteHeaderID;
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
	 * @return the jobStartDate
	 */
	public String getJobStartDate()
	{
		return jobStartDate;
	}

	/**
	 * @param jobStartDate
	 *           the jobStartDate to set
	 */
	public void setJobStartDate(final String jobStartDate)
	{
		this.jobStartDate = jobStartDate;
	}

	/**
	 * @return the branch
	 */
	public String getBranch()
	{
		return branch;
	}

	/**
	 * @param branch
	 *           the branch to set
	 */
	public void setBranch(final String branch)
	{
		this.branch = branch;
	}

	/**
	 * @return the jobDescription
	 */
	public String getJobDescription()
	{
		return jobDescription;
	}

	/**
	 * @param jobDescription
	 *           the jobDescription to set
	 */
	public void setJobDescription(final String jobDescription)
	{
		this.jobDescription = jobDescription;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *           the comment to set
	 */
	public void setComment(final String comment)
	{
		this.comment = comment;
	}

	/**
	 * @return the itemDetails
	 */
	public List<QuoteItemDetailsModel> getItemDetails()
	{
		return itemDetails;
	}

	/**
	 * @param itemDetails
	 *           the itemDetails to set
	 */
	public void setItemDetails(final List<QuoteItemDetailsModel> itemDetails)
	{
		this.itemDetails = itemDetails;
	}

	/**
	 * @return the customItemDetails
	 */
	public List<QuoteItemDetailsModel> getCustomItemDetails()
	{
		return customItemDetails;
	}

	/**
	 * @param customItemDetails
	 *           the customItemDetails to set
	 */
	public void setCustomItemDetails(final List<QuoteItemDetailsModel> customItemDetails)
	{
		this.customItemDetails = customItemDetails;
	}


}