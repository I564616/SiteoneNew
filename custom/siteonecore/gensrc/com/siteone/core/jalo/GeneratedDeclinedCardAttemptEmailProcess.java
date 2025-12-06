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
 * Generated class for type {@link com.siteone.core.jalo.DeclinedCardAttemptEmailProcess DeclinedCardAttemptEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedDeclinedCardAttemptEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>DeclinedCardAttemptEmailProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	/** Qualifier of the <code>DeclinedCardAttemptEmailProcess.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>DeclinedCardAttemptEmailProcess.orderAmount</code> attribute **/
	public static final String ORDERAMOUNT = "orderAmount";
	/** Qualifier of the <code>DeclinedCardAttemptEmailProcess.date</code> attribute **/
	public static final String DATE = "date";
	/** Qualifier of the <code>DeclinedCardAttemptEmailProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(ORDERAMOUNT, AttributeMode.INITIAL);
		tmp.put(DATE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.date</code> attribute.
	 * @return the date
	 */
	public String getDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.date</code> attribute.
	 * @return the date
	 */
	public String getDate()
	{
		return getDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final String value)
	{
		setDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.orderAmount</code> attribute.
	 * @return the orderAmount
	 */
	public String getOrderAmount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.orderAmount</code> attribute.
	 * @return the orderAmount
	 */
	public String getOrderAmount()
	{
		return getOrderAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.orderAmount</code> attribute. 
	 * @param value the orderAmount
	 */
	public void setOrderAmount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.orderAmount</code> attribute. 
	 * @param value the orderAmount
	 */
	public void setOrderAmount(final String value)
	{
		setOrderAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>DeclinedCardAttemptEmailProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>DeclinedCardAttemptEmailProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
}
