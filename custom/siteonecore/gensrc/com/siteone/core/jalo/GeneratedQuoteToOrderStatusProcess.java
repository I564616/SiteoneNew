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
 * Generated class for type {@link com.siteone.core.jalo.QuoteToOrderStatusProcess QuoteToOrderStatusProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedQuoteToOrderStatusProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>QuoteToOrderStatusProcess.toMails</code> attribute **/
	public static final String TOMAILS = "toMails";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.poNumber</code> attribute **/
	public static final String PONUMBER = "poNumber";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.jobName</code> attribute **/
	public static final String JOBNAME = "jobName";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.quoteNumber</code> attribute **/
	public static final String QUOTENUMBER = "quoteNumber";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.quoteWriter</code> attribute **/
	public static final String QUOTEWRITER = "quoteWriter";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.orderDate</code> attribute **/
	public static final String ORDERDATE = "orderDate";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.accountManager</code> attribute **/
	public static final String ACCOUNTMANAGER = "accountManager";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.accountManagerEmail</code> attribute **/
	public static final String ACCOUNTMANAGEREMAIL = "accountManagerEmail";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.accountManagerPhone</code> attribute **/
	public static final String ACCOUNTMANAGERPHONE = "accountManagerPhone";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.accountAdminEmail</code> attribute **/
	public static final String ACCOUNTADMINEMAIL = "accountAdminEmail";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.orderId</code> attribute **/
	public static final String ORDERID = "orderId";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.consignmentId</code> attribute **/
	public static final String CONSIGNMENTID = "consignmentId";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute **/
	public static final String ISQUOTETOORDEREMAILSENT = "isQuoteToOrderEmailsent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TOMAILS, AttributeMode.INITIAL);
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(PONUMBER, AttributeMode.INITIAL);
		tmp.put(JOBNAME, AttributeMode.INITIAL);
		tmp.put(QUOTENUMBER, AttributeMode.INITIAL);
		tmp.put(QUOTEWRITER, AttributeMode.INITIAL);
		tmp.put(ORDERDATE, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGER, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGERPHONE, AttributeMode.INITIAL);
		tmp.put(ACCOUNTADMINEMAIL, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(ORDERID, AttributeMode.INITIAL);
		tmp.put(CONSIGNMENTID, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(ISQUOTETOORDEREMAILSENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountAdminEmail</code> attribute.
	 * @return the accountAdminEmail
	 */
	public String getAccountAdminEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTADMINEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountAdminEmail</code> attribute.
	 * @return the accountAdminEmail
	 */
	public String getAccountAdminEmail()
	{
		return getAccountAdminEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountAdminEmail</code> attribute. 
	 * @param value the accountAdminEmail
	 */
	public void setAccountAdminEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTADMINEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountAdminEmail</code> attribute. 
	 * @param value the accountAdminEmail
	 */
	public void setAccountAdminEmail(final String value)
	{
		setAccountAdminEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountManager</code> attribute.
	 * @return the accountManager
	 */
	public String getAccountManager(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountManager</code> attribute.
	 * @return the accountManager
	 */
	public String getAccountManager()
	{
		return getAccountManager( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountManager</code> attribute. 
	 * @param value the accountManager
	 */
	public void setAccountManager(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountManager</code> attribute. 
	 * @param value the accountManager
	 */
	public void setAccountManager(final String value)
	{
		setAccountManager( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return getAccountManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final String value)
	{
		setAccountManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountManagerPhone</code> attribute.
	 * @return the accountManagerPhone
	 */
	public String getAccountManagerPhone(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGERPHONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountManagerPhone</code> attribute.
	 * @return the accountManagerPhone
	 */
	public String getAccountManagerPhone()
	{
		return getAccountManagerPhone( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountManagerPhone</code> attribute. 
	 * @param value the accountManagerPhone
	 */
	public void setAccountManagerPhone(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGERPHONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountManagerPhone</code> attribute. 
	 * @param value the accountManagerPhone
	 */
	public void setAccountManagerPhone(final String value)
	{
		setAccountManagerPhone( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.consignmentId</code> attribute.
	 * @return the consignmentId
	 */
	public String getConsignmentId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONSIGNMENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.consignmentId</code> attribute.
	 * @return the consignmentId
	 */
	public String getConsignmentId()
	{
		return getConsignmentId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.consignmentId</code> attribute. 
	 * @param value the consignmentId
	 */
	public void setConsignmentId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONSIGNMENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.consignmentId</code> attribute. 
	 * @param value the consignmentId
	 */
	public void setConsignmentId(final String value)
	{
		setConsignmentId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute.
	 * @return the isQuoteToOrderEmailsent
	 */
	public Boolean isIsQuoteToOrderEmailsent(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISQUOTETOORDEREMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute.
	 * @return the isQuoteToOrderEmailsent
	 */
	public Boolean isIsQuoteToOrderEmailsent()
	{
		return isIsQuoteToOrderEmailsent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute. 
	 * @return the isQuoteToOrderEmailsent
	 */
	public boolean isIsQuoteToOrderEmailsentAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsQuoteToOrderEmailsent( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute. 
	 * @return the isQuoteToOrderEmailsent
	 */
	public boolean isIsQuoteToOrderEmailsentAsPrimitive()
	{
		return isIsQuoteToOrderEmailsentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute. 
	 * @param value the isQuoteToOrderEmailsent
	 */
	public void setIsQuoteToOrderEmailsent(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISQUOTETOORDEREMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute. 
	 * @param value the isQuoteToOrderEmailsent
	 */
	public void setIsQuoteToOrderEmailsent(final Boolean value)
	{
		setIsQuoteToOrderEmailsent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute. 
	 * @param value the isQuoteToOrderEmailsent
	 */
	public void setIsQuoteToOrderEmailsent(final SessionContext ctx, final boolean value)
	{
		setIsQuoteToOrderEmailsent( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.isQuoteToOrderEmailsent</code> attribute. 
	 * @param value the isQuoteToOrderEmailsent
	 */
	public void setIsQuoteToOrderEmailsent(final boolean value)
	{
		setIsQuoteToOrderEmailsent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName()
	{
		return getJobName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final String value)
	{
		setJobName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.orderDate</code> attribute.
	 * @return the orderDate
	 */
	public String getOrderDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.orderDate</code> attribute.
	 * @return the orderDate
	 */
	public String getOrderDate()
	{
		return getOrderDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.orderDate</code> attribute. 
	 * @param value the orderDate
	 */
	public void setOrderDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.orderDate</code> attribute. 
	 * @param value the orderDate
	 */
	public void setOrderDate(final String value)
	{
		setOrderDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.orderId</code> attribute.
	 * @return the orderId
	 */
	public String getOrderId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.orderId</code> attribute.
	 * @return the orderId
	 */
	public String getOrderId()
	{
		return getOrderId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.orderId</code> attribute. 
	 * @param value the orderId
	 */
	public void setOrderId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.orderId</code> attribute. 
	 * @param value the orderId
	 */
	public void setOrderId(final String value)
	{
		setOrderId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PONUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return getPoNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PONUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final String value)
	{
		setPoNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return getQuoteNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final String value)
	{
		setQuoteNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.quoteWriter</code> attribute.
	 * @return the quoteWriter
	 */
	public String getQuoteWriter(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTEWRITER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.quoteWriter</code> attribute.
	 * @return the quoteWriter
	 */
	public String getQuoteWriter()
	{
		return getQuoteWriter( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.quoteWriter</code> attribute. 
	 * @param value the quoteWriter
	 */
	public void setQuoteWriter(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTEWRITER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.quoteWriter</code> attribute. 
	 * @param value the quoteWriter
	 */
	public void setQuoteWriter(final String value)
	{
		setQuoteWriter( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.status</code> attribute.
	 * @return the status
	 */
	public String getStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.status</code> attribute.
	 * @return the status
	 */
	public String getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final String value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.toMails</code> attribute.
	 * @return the toMails
	 */
	public String getToMails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteToOrderStatusProcess.toMails</code> attribute.
	 * @return the toMails
	 */
	public String getToMails()
	{
		return getToMails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.toMails</code> attribute. 
	 * @param value the toMails
	 */
	public void setToMails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteToOrderStatusProcess.toMails</code> attribute. 
	 * @param value the toMails
	 */
	public void setToMails(final String value)
	{
		setToMails( getSession().getSessionContext(), value );
	}
	
}
