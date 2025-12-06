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
 * Generated class for type {@link com.siteone.core.jalo.OrderReadyToPickUpEmailProcess OrderReadyToPickUpEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedOrderReadyToPickUpEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>OrderReadyToPickUpEmailProcess.order</code> attribute **/
	public static final String ORDER = "order";
	/** Qualifier of the <code>OrderReadyToPickUpEmailProcess.consignment</code> attribute **/
	public static final String CONSIGNMENT = "consignment";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ORDER, AttributeMode.INITIAL);
		tmp.put(CONSIGNMENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyToPickUpEmailProcess.consignment</code> attribute.
	 * @return the consignment
	 */
	public Consignment getConsignment(final SessionContext ctx)
	{
		return (Consignment)getProperty( ctx, CONSIGNMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyToPickUpEmailProcess.consignment</code> attribute.
	 * @return the consignment
	 */
	public Consignment getConsignment()
	{
		return getConsignment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyToPickUpEmailProcess.consignment</code> attribute. 
	 * @param value the consignment
	 */
	public void setConsignment(final SessionContext ctx, final Consignment value)
	{
		setProperty(ctx, CONSIGNMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyToPickUpEmailProcess.consignment</code> attribute. 
	 * @param value the consignment
	 */
	public void setConsignment(final Consignment value)
	{
		setConsignment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyToPickUpEmailProcess.order</code> attribute.
	 * @return the order
	 */
	public Order getOrder(final SessionContext ctx)
	{
		return (Order)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyToPickUpEmailProcess.order</code> attribute.
	 * @return the order
	 */
	public Order getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyToPickUpEmailProcess.order</code> attribute. 
	 * @param value the order
	 */
	public void setOrder(final SessionContext ctx, final Order value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyToPickUpEmailProcess.order</code> attribute. 
	 * @param value the order
	 */
	public void setOrder(final Order value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
}
