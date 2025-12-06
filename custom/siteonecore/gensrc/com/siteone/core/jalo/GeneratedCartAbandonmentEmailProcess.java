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
import de.hybris.platform.jalo.order.Cart;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.CartAbandonmentEmailProcess CartAbandonmentEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCartAbandonmentEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>CartAbandonmentEmailProcess.cart</code> attribute **/
	public static final String CART = "cart";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CART, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartAbandonmentEmailProcess.cart</code> attribute.
	 * @return the cart - Attribute contains cart information that is used in this process.
	 */
	public Cart getCart(final SessionContext ctx)
	{
		return (Cart)getProperty( ctx, CART);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartAbandonmentEmailProcess.cart</code> attribute.
	 * @return the cart - Attribute contains cart information that is used in this process.
	 */
	public Cart getCart()
	{
		return getCart( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartAbandonmentEmailProcess.cart</code> attribute. 
	 * @param value the cart - Attribute contains cart information that is used in this process.
	 */
	public void setCart(final SessionContext ctx, final Cart value)
	{
		setProperty(ctx, CART,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartAbandonmentEmailProcess.cart</code> attribute. 
	 * @param value the cart - Attribute contains cart information that is used in this process.
	 */
	public void setCart(final Cart value)
	{
		setCart( getSession().getSessionContext(), value );
	}
	
}
