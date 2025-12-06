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
 * Generated class for type {@link com.siteone.core.jalo.NotifyQuoteStatusProcess NotifyQuoteStatusProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedNotifyQuoteStatusProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>NotifyQuoteStatusProcess.toMails</code> attribute **/
	public static final String TOMAILS = "toMails";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.quoteNumber</code> attribute **/
	public static final String QUOTENUMBER = "quoteNumber";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.jobName</code> attribute **/
	public static final String JOBNAME = "jobName";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.dateSubmitted</code> attribute **/
	public static final String DATESUBMITTED = "dateSubmitted";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.expDate</code> attribute **/
	public static final String EXPDATE = "expDate";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.mongoId</code> attribute **/
	public static final String MONGOID = "mongoId";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.accountAdminEmail</code> attribute **/
	public static final String ACCOUNTADMINEMAIL = "accountAdminEmail";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.customMessage</code> attribute **/
	public static final String CUSTOMMESSAGE = "customMessage";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.accountManagerMail</code> attribute **/
	public static final String ACCOUNTMANAGERMAIL = "accountManagerMail";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.accountManagerMobile</code> attribute **/
	public static final String ACCOUNTMANAGERMOBILE = "accountManagerMobile";
	/** Qualifier of the <code>NotifyQuoteStatusProcess.accountManagerName</code> attribute **/
	public static final String ACCOUNTMANAGERNAME = "accountManagerName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TOMAILS, AttributeMode.INITIAL);
		tmp.put(QUOTENUMBER, AttributeMode.INITIAL);
		tmp.put(JOBNAME, AttributeMode.INITIAL);
		tmp.put(DATESUBMITTED, AttributeMode.INITIAL);
		tmp.put(EXPDATE, AttributeMode.INITIAL);
		tmp.put(MONGOID, AttributeMode.INITIAL);
		tmp.put(ACCOUNTADMINEMAIL, AttributeMode.INITIAL);
		tmp.put(CUSTOMMESSAGE, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGERMAIL, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGERMOBILE, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGERNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountAdminEmail</code> attribute.
	 * @return the accountAdminEmail
	 */
	public String getAccountAdminEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTADMINEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountAdminEmail</code> attribute.
	 * @return the accountAdminEmail
	 */
	public String getAccountAdminEmail()
	{
		return getAccountAdminEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountAdminEmail</code> attribute. 
	 * @param value the accountAdminEmail
	 */
	public void setAccountAdminEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTADMINEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountAdminEmail</code> attribute. 
	 * @param value the accountAdminEmail
	 */
	public void setAccountAdminEmail(final String value)
	{
		setAccountAdminEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountManagerMail</code> attribute.
	 * @return the accountManagerMail
	 */
	public String getAccountManagerMail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGERMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountManagerMail</code> attribute.
	 * @return the accountManagerMail
	 */
	public String getAccountManagerMail()
	{
		return getAccountManagerMail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountManagerMail</code> attribute. 
	 * @param value the accountManagerMail
	 */
	public void setAccountManagerMail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGERMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountManagerMail</code> attribute. 
	 * @param value the accountManagerMail
	 */
	public void setAccountManagerMail(final String value)
	{
		setAccountManagerMail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountManagerMobile</code> attribute.
	 * @return the accountManagerMobile
	 */
	public String getAccountManagerMobile(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGERMOBILE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountManagerMobile</code> attribute.
	 * @return the accountManagerMobile
	 */
	public String getAccountManagerMobile()
	{
		return getAccountManagerMobile( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountManagerMobile</code> attribute. 
	 * @param value the accountManagerMobile
	 */
	public void setAccountManagerMobile(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGERMOBILE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountManagerMobile</code> attribute. 
	 * @param value the accountManagerMobile
	 */
	public void setAccountManagerMobile(final String value)
	{
		setAccountManagerMobile( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountManagerName</code> attribute.
	 * @return the accountManagerName
	 */
	public String getAccountManagerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.accountManagerName</code> attribute.
	 * @return the accountManagerName
	 */
	public String getAccountManagerName()
	{
		return getAccountManagerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountManagerName</code> attribute. 
	 * @param value the accountManagerName
	 */
	public void setAccountManagerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.accountManagerName</code> attribute. 
	 * @param value the accountManagerName
	 */
	public void setAccountManagerName(final String value)
	{
		setAccountManagerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.customMessage</code> attribute.
	 * @return the customMessage
	 */
	public String getCustomMessage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.customMessage</code> attribute.
	 * @return the customMessage
	 */
	public String getCustomMessage()
	{
		return getCustomMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.customMessage</code> attribute. 
	 * @param value the customMessage
	 */
	public void setCustomMessage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.customMessage</code> attribute. 
	 * @param value the customMessage
	 */
	public void setCustomMessage(final String value)
	{
		setCustomMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.dateSubmitted</code> attribute.
	 * @return the dateSubmitted
	 */
	public String getDateSubmitted(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATESUBMITTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.dateSubmitted</code> attribute.
	 * @return the dateSubmitted
	 */
	public String getDateSubmitted()
	{
		return getDateSubmitted( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.dateSubmitted</code> attribute. 
	 * @param value the dateSubmitted
	 */
	public void setDateSubmitted(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATESUBMITTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.dateSubmitted</code> attribute. 
	 * @param value the dateSubmitted
	 */
	public void setDateSubmitted(final String value)
	{
		setDateSubmitted( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return getExpDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final String value)
	{
		setExpDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName()
	{
		return getJobName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final String value)
	{
		setJobName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.mongoId</code> attribute.
	 * @return the mongoId
	 */
	public String getMongoId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MONGOID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.mongoId</code> attribute.
	 * @return the mongoId
	 */
	public String getMongoId()
	{
		return getMongoId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.mongoId</code> attribute. 
	 * @param value the mongoId
	 */
	public void setMongoId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MONGOID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.mongoId</code> attribute. 
	 * @param value the mongoId
	 */
	public void setMongoId(final String value)
	{
		setMongoId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return getQuoteNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final String value)
	{
		setQuoteNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.toMails</code> attribute.
	 * @return the toMails
	 */
	public String getToMails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NotifyQuoteStatusProcess.toMails</code> attribute.
	 * @return the toMails
	 */
	public String getToMails()
	{
		return getToMails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.toMails</code> attribute. 
	 * @param value the toMails
	 */
	public void setToMails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NotifyQuoteStatusProcess.toMails</code> attribute. 
	 * @param value the toMails
	 */
	public void setToMails(final String value)
	{
		setToMails( getSession().getSessionContext(), value );
	}
	
}
