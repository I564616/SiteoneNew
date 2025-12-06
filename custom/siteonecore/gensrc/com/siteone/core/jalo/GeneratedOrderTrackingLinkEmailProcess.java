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
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.ordersplitting.jalo.Consignment;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.OrderTrackingLinkEmailProcess OrderTrackingLinkEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedOrderTrackingLinkEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>OrderTrackingLinkEmailProcess.order</code> attribute **/
	public static final String ORDER = "order";
	/** Qualifier of the <code>OrderTrackingLinkEmailProcess.consignment</code> attribute **/
	public static final String CONSIGNMENT = "consignment";
	/** Qualifier of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute **/
	public static final String ISSHIPPINGFEEBRANCH = "isShippingFeeBranch";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ORDER, AttributeMode.INITIAL);
		tmp.put(CONSIGNMENT, AttributeMode.INITIAL);
		tmp.put(ISSHIPPINGFEEBRANCH, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.consignment</code> attribute.
	 * @return the consignment
	 */
	public Consignment getConsignment(final SessionContext ctx)
	{
		return (Consignment)getProperty( ctx, CONSIGNMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.consignment</code> attribute.
	 * @return the consignment
	 */
	public Consignment getConsignment()
	{
		return getConsignment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.consignment</code> attribute. 
	 * @param value the consignment
	 */
	public void setConsignment(final SessionContext ctx, final Consignment value)
	{
		setProperty(ctx, CONSIGNMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.consignment</code> attribute. 
	 * @param value the consignment
	 */
	public void setConsignment(final Consignment value)
	{
		setConsignment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute.
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public Boolean isIsShippingFeeBranch(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISSHIPPINGFEEBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute.
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public Boolean isIsShippingFeeBranch()
	{
		return isIsShippingFeeBranch( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute. 
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public boolean isIsShippingFeeBranchAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsShippingFeeBranch( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute. 
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public boolean isIsShippingFeeBranchAsPrimitive()
	{
		return isIsShippingFeeBranchAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISSHIPPINGFEEBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final Boolean value)
	{
		setIsShippingFeeBranch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final SessionContext ctx, final boolean value)
	{
		setIsShippingFeeBranch( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final boolean value)
	{
		setIsShippingFeeBranch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.order</code> attribute.
	 * @return the order
	 */
	public Order getOrder(final SessionContext ctx)
	{
		return (Order)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingLinkEmailProcess.order</code> attribute.
	 * @return the order
	 */
	public Order getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.order</code> attribute. 
	 * @param value the order
	 */
	public void setOrder(final SessionContext ctx, final Order value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingLinkEmailProcess.order</code> attribute. 
	 * @param value the order
	 */
	public void setOrder(final Order value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
}
