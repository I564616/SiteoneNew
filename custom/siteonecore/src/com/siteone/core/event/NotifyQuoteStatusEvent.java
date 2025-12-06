/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author PElango
 *
 */
public class NotifyQuoteStatusEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{
	String toMail;
	String quoteNumber;
	String accountManagerName;
	String jobName;
	String dateSubmitted;
	String expDate;
	String mongoId;
	String accountManagerMobile;
	String accountManagerMail;
	String accountAdminEmail;
	String customMessage;

	/**
	 * @return the accountAdminEmail
	 */
	public String getAccountAdminEmail()
	{
		return accountAdminEmail;
	}

	/**
	 * @param accountAdminEmail the accountAdminEmail to set
	 */
	public void setAccountAdminEmail(String accountAdminEmail)
	{
		this.accountAdminEmail = accountAdminEmail;
	}

	/**
	 * @return the customMessage
	 */
	public String getCustomMessage()
	{
		return customMessage;
	}

	/**
	 * @param customMessage the customMessage to set
	 */
	public void setCustomMessage(String customMessage)
	{
		this.customMessage = customMessage;
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
	 * @return the dateSubmitted
	 */
	public String getDateSubmitted()
	{
		return dateSubmitted;
	}

	/**
	 * @param dateSubmitted
	 *           the dateSubmitted to set
	 */
	public void setDateSubmitted(final String dateSubmitted)
	{
		this.dateSubmitted = dateSubmitted;
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
	 * @return the mongoId
	 */
	public String getMongoId()
	{
		return mongoId;
	}

	/**
	 * @param mongoId
	 *           the mongoId to set
	 */
	public void setMongoId(final String mongoId)
	{
		this.mongoId = mongoId;
	}

	/**
	 * @return the accountManagerName
	 */
	public String getAccountManagerName()
	{
		return accountManagerName;
	}

	/**
	 * @param accountManagerName
	 *           the accountManagerName to set
	 */
	public void setAccountManagerName(final String accountManagerName)
	{
		this.accountManagerName = accountManagerName;
	}

	/**
	 * @return the accountManagerMobile
	 */
	public String getAccountManagerMobile()
	{
		return accountManagerMobile;
	}

	/**
	 * @param accountManagerMobile
	 *           the accountManagerMobile to set
	 */
	public void setAccountManagerMobile(final String accountManagerMobile)
	{
		this.accountManagerMobile = accountManagerMobile;
	}

	/**
	 * @return the accountManagerMail
	 */
	public String getAccountManagerMail()
	{
		return accountManagerMail;
	}

	/**
	 * @param accountManagerMail
	 *           the accountManagerMail to set
	 */
	public void setAccountManagerMail(final String accountManagerMail)
	{
		this.accountManagerMail = accountManagerMail;
	}

}
