/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.QuoteItemDetails;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.RequestQuoteProcess RequestQuoteProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedRequestQuoteProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>RequestQuoteProcess.jobName</code> attribute **/
	public static final String JOBNAME = "jobName";
	/** Qualifier of the <code>RequestQuoteProcess.jobStartDate</code> attribute **/
	public static final String JOBSTARTDATE = "jobStartDate";
	/** Qualifier of the <code>RequestQuoteProcess.branch</code> attribute **/
	public static final String BRANCH = "branch";
	/** Qualifier of the <code>RequestQuoteProcess.jobDescription</code> attribute **/
	public static final String JOBDESCRIPTION = "jobDescription";
	/** Qualifier of the <code>RequestQuoteProcess.accountName</code> attribute **/
	public static final String ACCOUNTNAME = "accountName";
	/** Qualifier of the <code>RequestQuoteProcess.accountId</code> attribute **/
	public static final String ACCOUNTID = "accountId";
	/** Qualifier of the <code>RequestQuoteProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>RequestQuoteProcess.customerEmailAddress</code> attribute **/
	public static final String CUSTOMEREMAILADDRESS = "customerEmailAddress";
	/** Qualifier of the <code>RequestQuoteProcess.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	/** Qualifier of the <code>RequestQuoteProcess.quoteHeaderID</code> attribute **/
	public static final String QUOTEHEADERID = "quoteHeaderID";
	/** Qualifier of the <code>RequestQuoteProcess.comment</code> attribute **/
	public static final String COMMENT = "comment";
	/** Qualifier of the <code>RequestQuoteProcess.accountManagerEmail</code> attribute **/
	public static final String ACCOUNTMANAGEREMAIL = "accountManagerEmail";
	/** Qualifier of the <code>RequestQuoteProcess.insideSalesRepEmail</code> attribute **/
	public static final String INSIDESALESREPEMAIL = "insideSalesRepEmail";
	/** Qualifier of the <code>RequestQuoteProcess.branchManagerEmail</code> attribute **/
	public static final String BRANCHMANAGEREMAIL = "branchManagerEmail";
	/** Qualifier of the <code>RequestQuoteProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	/** Qualifier of the <code>RequestQuoteProcess.itemDetails</code> attribute **/
	public static final String ITEMDETAILS = "itemDetails";
	/** Qualifier of the <code>RequestQuoteProcess.customItemDetails</code> attribute **/
	public static final String CUSTOMITEMDETAILS = "customItemDetails";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(JOBNAME, AttributeMode.INITIAL);
		tmp.put(JOBSTARTDATE, AttributeMode.INITIAL);
		tmp.put(BRANCH, AttributeMode.INITIAL);
		tmp.put(JOBDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTID, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		tmp.put(QUOTEHEADERID, AttributeMode.INITIAL);
		tmp.put(COMMENT, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(INSIDESALESREPEMAIL, AttributeMode.INITIAL);
		tmp.put(BRANCHMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		tmp.put(ITEMDETAILS, AttributeMode.INITIAL);
		tmp.put(CUSTOMITEMDETAILS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId()
	{
		return getAccountId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final String value)
	{
		setAccountId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return getAccountManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final String value)
	{
		setAccountManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return getAccountName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final String value)
	{
		setAccountName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.branch</code> attribute.
	 * @return the branch
	 */
	public String getBranch(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.branch</code> attribute.
	 * @return the branch
	 */
	public String getBranch()
	{
		return getBranch( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.branch</code> attribute. 
	 * @param value the branch
	 */
	public void setBranch(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.branch</code> attribute. 
	 * @param value the branch
	 */
	public void setBranch(final String value)
	{
		setBranch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return getBranchManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final String value)
	{
		setBranchManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.comment</code> attribute.
	 * @return the comment
	 */
	public String getComment(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COMMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.comment</code> attribute.
	 * @return the comment
	 */
	public String getComment()
	{
		return getComment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.comment</code> attribute. 
	 * @param value the comment
	 */
	public void setComment(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COMMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.comment</code> attribute. 
	 * @param value the comment
	 */
	public void setComment(final String value)
	{
		setComment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress()
	{
		return getCustomerEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final String value)
	{
		setCustomerEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.customItemDetails</code> attribute.
	 * @return the customItemDetails
	 */
	public List<QuoteItemDetails> getCustomItemDetails(final SessionContext ctx)
	{
		List<QuoteItemDetails> coll = (List<QuoteItemDetails>)getProperty( ctx, CUSTOMITEMDETAILS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.customItemDetails</code> attribute.
	 * @return the customItemDetails
	 */
	public List<QuoteItemDetails> getCustomItemDetails()
	{
		return getCustomItemDetails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.customItemDetails</code> attribute. 
	 * @param value the customItemDetails
	 */
	public void setCustomItemDetails(final SessionContext ctx, final List<QuoteItemDetails> value)
	{
		setProperty(ctx, CUSTOMITEMDETAILS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.customItemDetails</code> attribute. 
	 * @param value the customItemDetails
	 */
	public void setCustomItemDetails(final List<QuoteItemDetails> value)
	{
		setCustomItemDetails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSIDESALESREPEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return getInsideSalesRepEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSIDESALESREPEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final String value)
	{
		setInsideSalesRepEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.itemDetails</code> attribute.
	 * @return the itemDetails
	 */
	public List<QuoteItemDetails> getItemDetails(final SessionContext ctx)
	{
		List<QuoteItemDetails> coll = (List<QuoteItemDetails>)getProperty( ctx, ITEMDETAILS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.itemDetails</code> attribute.
	 * @return the itemDetails
	 */
	public List<QuoteItemDetails> getItemDetails()
	{
		return getItemDetails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.itemDetails</code> attribute. 
	 * @param value the itemDetails
	 */
	public void setItemDetails(final SessionContext ctx, final List<QuoteItemDetails> value)
	{
		setProperty(ctx, ITEMDETAILS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.itemDetails</code> attribute. 
	 * @param value the itemDetails
	 */
	public void setItemDetails(final List<QuoteItemDetails> value)
	{
		setItemDetails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.jobDescription</code> attribute.
	 * @return the jobDescription
	 */
	public String getJobDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.jobDescription</code> attribute.
	 * @return the jobDescription
	 */
	public String getJobDescription()
	{
		return getJobDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.jobDescription</code> attribute. 
	 * @param value the jobDescription
	 */
	public void setJobDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.jobDescription</code> attribute. 
	 * @param value the jobDescription
	 */
	public void setJobDescription(final String value)
	{
		setJobDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName()
	{
		return getJobName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final String value)
	{
		setJobName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.jobStartDate</code> attribute.
	 * @return the jobStartDate
	 */
	public String getJobStartDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBSTARTDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.jobStartDate</code> attribute.
	 * @return the jobStartDate
	 */
	public String getJobStartDate()
	{
		return getJobStartDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.jobStartDate</code> attribute. 
	 * @param value the jobStartDate
	 */
	public void setJobStartDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBSTARTDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.jobStartDate</code> attribute. 
	 * @param value the jobStartDate
	 */
	public void setJobStartDate(final String value)
	{
		setJobStartDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.quoteHeaderID</code> attribute.
	 * @return the quoteHeaderID
	 */
	public String getQuoteHeaderID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTEHEADERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.quoteHeaderID</code> attribute.
	 * @return the quoteHeaderID
	 */
	public String getQuoteHeaderID()
	{
		return getQuoteHeaderID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.quoteHeaderID</code> attribute. 
	 * @param value the quoteHeaderID
	 */
	public void setQuoteHeaderID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTEHEADERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.quoteHeaderID</code> attribute. 
	 * @param value the quoteHeaderID
	 */
	public void setQuoteHeaderID(final String value)
	{
		setQuoteHeaderID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestQuoteProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestQuoteProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
}
