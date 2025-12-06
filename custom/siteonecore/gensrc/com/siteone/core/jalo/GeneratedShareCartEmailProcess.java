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
 * Generated class for type {@link com.siteone.core.jalo.ShareCartEmailProcess ShareCartEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedShareCartEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ShareCartEmailProcess.cart</code> attribute **/
	public static final String CART = "cart";
	/** Qualifier of the <code>ShareCartEmailProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>ShareCartEmailProcess.accountName</code> attribute **/
	public static final String ACCOUNTNAME = "accountName";
	/** Qualifier of the <code>ShareCartEmailProcess.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>ShareCartEmailProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>ShareCartEmailProcess.customerEmail</code> attribute **/
	public static final String CUSTOMEREMAIL = "customerEmail";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CART, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAIL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return getAccountName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final String value)
	{
		setAccountName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.cart</code> attribute.
	 * @return the cart
	 */
	public Cart getCart(final SessionContext ctx)
	{
		return (Cart)getProperty( ctx, CART);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.cart</code> attribute.
	 * @return the cart
	 */
	public Cart getCart()
	{
		return getCart( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.cart</code> attribute. 
	 * @param value the cart
	 */
	public void setCart(final SessionContext ctx, final Cart value)
	{
		setProperty(ctx, CART,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.cart</code> attribute. 
	 * @param value the cart
	 */
	public void setCart(final Cart value)
	{
		setCart( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.customerEmail</code> attribute.
	 * @return the customerEmail
	 */
	public String getCustomerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.customerEmail</code> attribute.
	 * @return the customerEmail
	 */
	public String getCustomerEmail()
	{
		return getCustomerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.customerEmail</code> attribute. 
	 * @param value the customerEmail
	 */
	public void setCustomerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.customerEmail</code> attribute. 
	 * @param value the customerEmail
	 */
	public void setCustomerEmail(final String value)
	{
		setCustomerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareCartEmailProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareCartEmailProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
}
