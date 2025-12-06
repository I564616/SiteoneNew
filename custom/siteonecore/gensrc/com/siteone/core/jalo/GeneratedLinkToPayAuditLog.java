/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.LinkToPayPayment;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.LinkToPayAuditLog LinkToPayAuditLog}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedLinkToPayAuditLog extends GenericItem
{
	/** Qualifier of the <code>LinkToPayAuditLog.timestamp</code> attribute **/
	public static final String TIMESTAMP = "timestamp";
	/** Qualifier of the <code>LinkToPayAuditLog.tokenReceived</code> attribute **/
	public static final String TOKENRECEIVED = "tokenReceived";
	/** Qualifier of the <code>LinkToPayAuditLog.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>LinkToPayAuditLog.orderTotal</code> attribute **/
	public static final String ORDERTOTAL = "orderTotal";
	/** Qualifier of the <code>LinkToPayAuditLog.paymentInformation</code> attribute **/
	public static final String PAYMENTINFORMATION = "paymentInformation";
	/** Qualifier of the <code>LinkToPayAuditLog.kountStatus</code> attribute **/
	public static final String KOUNTSTATUS = "kountStatus";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(TIMESTAMP, AttributeMode.INITIAL);
		tmp.put(TOKENRECEIVED, AttributeMode.INITIAL);
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(ORDERTOTAL, AttributeMode.INITIAL);
		tmp.put(PAYMENTINFORMATION, AttributeMode.INITIAL);
		tmp.put(KOUNTSTATUS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.kountStatus</code> attribute.
	 * @return the kountStatus
	 */
	public String getKountStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, KOUNTSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.kountStatus</code> attribute.
	 * @return the kountStatus
	 */
	public String getKountStatus()
	{
		return getKountStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.kountStatus</code> attribute. 
	 * @param value the kountStatus
	 */
	public void setKountStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, KOUNTSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.kountStatus</code> attribute. 
	 * @param value the kountStatus
	 */
	public void setKountStatus(final String value)
	{
		setKountStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.orderTotal</code> attribute.
	 * @return the orderTotal
	 */
	public String getOrderTotal(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.orderTotal</code> attribute.
	 * @return the orderTotal
	 */
	public String getOrderTotal()
	{
		return getOrderTotal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.orderTotal</code> attribute. 
	 * @param value the orderTotal
	 */
	public void setOrderTotal(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.orderTotal</code> attribute. 
	 * @param value the orderTotal
	 */
	public void setOrderTotal(final String value)
	{
		setOrderTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.paymentInformation</code> attribute.
	 * @return the paymentInformation
	 */
	public LinkToPayPayment getPaymentInformation(final SessionContext ctx)
	{
		return (LinkToPayPayment)getProperty( ctx, PAYMENTINFORMATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.paymentInformation</code> attribute.
	 * @return the paymentInformation
	 */
	public LinkToPayPayment getPaymentInformation()
	{
		return getPaymentInformation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.paymentInformation</code> attribute. 
	 * @param value the paymentInformation
	 */
	public void setPaymentInformation(final SessionContext ctx, final LinkToPayPayment value)
	{
		setProperty(ctx, PAYMENTINFORMATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.paymentInformation</code> attribute. 
	 * @param value the paymentInformation
	 */
	public void setPaymentInformation(final LinkToPayPayment value)
	{
		setPaymentInformation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.timestamp</code> attribute.
	 * @return the timestamp
	 */
	public String getTimestamp(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TIMESTAMP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.timestamp</code> attribute.
	 * @return the timestamp
	 */
	public String getTimestamp()
	{
		return getTimestamp( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.timestamp</code> attribute. 
	 * @param value the timestamp
	 */
	public void setTimestamp(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TIMESTAMP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.timestamp</code> attribute. 
	 * @param value the timestamp
	 */
	public void setTimestamp(final String value)
	{
		setTimestamp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.tokenReceived</code> attribute.
	 * @return the tokenReceived
	 */
	public String getTokenReceived(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOKENRECEIVED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayAuditLog.tokenReceived</code> attribute.
	 * @return the tokenReceived
	 */
	public String getTokenReceived()
	{
		return getTokenReceived( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.tokenReceived</code> attribute. 
	 * @param value the tokenReceived
	 */
	public void setTokenReceived(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOKENRECEIVED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayAuditLog.tokenReceived</code> attribute. 
	 * @param value the tokenReceived
	 */
	public void setTokenReceived(final String value)
	{
		setTokenReceived( getSession().getSessionContext(), value );
	}
	
}
