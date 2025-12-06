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
 * Generated class for type {@link com.siteone.core.jalo.LinkToPayEmailPaymentProcess LinkToPayEmailPaymentProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedLinkToPayEmailPaymentProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.amountCharged</code> attribute **/
	public static final String AMOUNTCHARGED = "amountCharged";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.last4Digits</code> attribute **/
	public static final String LAST4DIGITS = "last4Digits";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.date</code> attribute **/
	public static final String DATE = "date";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.poNumber</code> attribute **/
	public static final String PONUMBER = "poNumber";
	/** Qualifier of the <code>LinkToPayEmailPaymentProcess.associateEmail</code> attribute **/
	public static final String ASSOCIATEEMAIL = "associateEmail";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(AMOUNTCHARGED, AttributeMode.INITIAL);
		tmp.put(LAST4DIGITS, AttributeMode.INITIAL);
		tmp.put(DATE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(PONUMBER, AttributeMode.INITIAL);
		tmp.put(ASSOCIATEEMAIL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.amountCharged</code> attribute.
	 * @return the amountCharged
	 */
	public String getAmountCharged(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMOUNTCHARGED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.amountCharged</code> attribute.
	 * @return the amountCharged
	 */
	public String getAmountCharged()
	{
		return getAmountCharged( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.amountCharged</code> attribute. 
	 * @param value the amountCharged
	 */
	public void setAmountCharged(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMOUNTCHARGED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.amountCharged</code> attribute. 
	 * @param value the amountCharged
	 */
	public void setAmountCharged(final String value)
	{
		setAmountCharged( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.associateEmail</code> attribute.
	 * @return the associateEmail
	 */
	public String getAssociateEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASSOCIATEEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.associateEmail</code> attribute.
	 * @return the associateEmail
	 */
	public String getAssociateEmail()
	{
		return getAssociateEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.associateEmail</code> attribute. 
	 * @param value the associateEmail
	 */
	public void setAssociateEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASSOCIATEEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.associateEmail</code> attribute. 
	 * @param value the associateEmail
	 */
	public void setAssociateEmail(final String value)
	{
		setAssociateEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.date</code> attribute.
	 * @return the date
	 */
	public String getDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.date</code> attribute.
	 * @return the date
	 */
	public String getDate()
	{
		return getDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final String value)
	{
		setDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.last4Digits</code> attribute.
	 * @return the last4Digits
	 */
	public String getLast4Digits(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LAST4DIGITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.last4Digits</code> attribute.
	 * @return the last4Digits
	 */
	public String getLast4Digits()
	{
		return getLast4Digits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.last4Digits</code> attribute. 
	 * @param value the last4Digits
	 */
	public void setLast4Digits(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LAST4DIGITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.last4Digits</code> attribute. 
	 * @param value the last4Digits
	 */
	public void setLast4Digits(final String value)
	{
		setLast4Digits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PONUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return getPoNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PONUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final String value)
	{
		setPoNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayEmailPaymentProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayEmailPaymentProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
}
