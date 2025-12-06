/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.ExpiredQuoteUpdProcess ExpiredQuoteUpdProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedExpiredQuoteUpdProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.quoteNumber</code> attribute **/
	public static final String QUOTENUMBER = "quoteNumber";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.notes</code> attribute **/
	public static final String NOTES = "notes";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.customerEmailAddress</code> attribute **/
	public static final String CUSTOMEREMAILADDRESS = "customerEmailAddress";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.accountName</code> attribute **/
	public static final String ACCOUNTNAME = "accountName";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.accountId</code> attribute **/
	public static final String ACCOUNTID = "accountId";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.jobName</code> attribute **/
	public static final String JOBNAME = "jobName";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.expDate</code> attribute **/
	public static final String EXPDATE = "expDate";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.quoteTotal</code> attribute **/
	public static final String QUOTETOTAL = "quoteTotal";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.accountManagerEmail</code> attribute **/
	public static final String ACCOUNTMANAGEREMAIL = "accountManagerEmail";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.branchManagerEmail</code> attribute **/
	public static final String BRANCHMANAGEREMAIL = "branchManagerEmail";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.writerEmail</code> attribute **/
	public static final String WRITEREMAIL = "writerEmail";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.pricerEmail</code> attribute **/
	public static final String PRICEREMAIL = "pricerEmail";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.InsideSalesRepEmail</code> attribute **/
	public static final String INSIDESALESREPEMAIL = "InsideSalesRepEmail";
	/** Qualifier of the <code>ExpiredQuoteUpdProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(QUOTENUMBER, AttributeMode.INITIAL);
		tmp.put(NOTES, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTID, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		tmp.put(JOBNAME, AttributeMode.INITIAL);
		tmp.put(EXPDATE, AttributeMode.INITIAL);
		tmp.put(QUOTETOTAL, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(BRANCHMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(WRITEREMAIL, AttributeMode.INITIAL);
		tmp.put(PRICEREMAIL, AttributeMode.INITIAL);
		tmp.put(INSIDESALESREPEMAIL, AttributeMode.INITIAL);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId()
	{
		return getAccountId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final String value)
	{
		setAccountId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return getAccountManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final String value)
	{
		setAccountManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return getAccountName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final String value)
	{
		setAccountName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return getBranchManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final String value)
	{
		setBranchManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress()
	{
		return getCustomerEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final String value)
	{
		setCustomerEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return getExpDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final String value)
	{
		setExpDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.InsideSalesRepEmail</code> attribute.
	 * @return the InsideSalesRepEmail
	 */
	public String getInsideSalesRepEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSIDESALESREPEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.InsideSalesRepEmail</code> attribute.
	 * @return the InsideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return getInsideSalesRepEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.InsideSalesRepEmail</code> attribute. 
	 * @param value the InsideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSIDESALESREPEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.InsideSalesRepEmail</code> attribute. 
	 * @param value the InsideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final String value)
	{
		setInsideSalesRepEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName()
	{
		return getJobName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final String value)
	{
		setJobName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.notes</code> attribute.
	 * @return the notes
	 */
	public String getNotes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.notes</code> attribute.
	 * @return the notes
	 */
	public String getNotes()
	{
		return getNotes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.notes</code> attribute. 
	 * @param value the notes
	 */
	public void setNotes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.notes</code> attribute. 
	 * @param value the notes
	 */
	public void setNotes(final String value)
	{
		setNotes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.pricerEmail</code> attribute.
	 * @return the pricerEmail
	 */
	public String getPricerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRICEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.pricerEmail</code> attribute.
	 * @return the pricerEmail
	 */
	public String getPricerEmail()
	{
		return getPricerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.pricerEmail</code> attribute. 
	 * @param value the pricerEmail
	 */
	public void setPricerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRICEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.pricerEmail</code> attribute. 
	 * @param value the pricerEmail
	 */
	public void setPricerEmail(final String value)
	{
		setPricerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return getQuoteNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final String value)
	{
		setQuoteNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.quoteTotal</code> attribute.
	 * @return the quoteTotal
	 */
	public String getQuoteTotal(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTETOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.quoteTotal</code> attribute.
	 * @return the quoteTotal
	 */
	public String getQuoteTotal()
	{
		return getQuoteTotal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.quoteTotal</code> attribute. 
	 * @param value the quoteTotal
	 */
	public void setQuoteTotal(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTETOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.quoteTotal</code> attribute. 
	 * @param value the quoteTotal
	 */
	public void setQuoteTotal(final String value)
	{
		setQuoteTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.writerEmail</code> attribute.
	 * @return the writerEmail
	 */
	public String getWriterEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, WRITEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExpiredQuoteUpdProcess.writerEmail</code> attribute.
	 * @return the writerEmail
	 */
	public String getWriterEmail()
	{
		return getWriterEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.writerEmail</code> attribute. 
	 * @param value the writerEmail
	 */
	public void setWriterEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, WRITEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExpiredQuoteUpdProcess.writerEmail</code> attribute. 
	 * @param value the writerEmail
	 */
	public void setWriterEmail(final String value)
	{
		setWriterEmail( getSession().getSessionContext(), value );
	}
	
}
