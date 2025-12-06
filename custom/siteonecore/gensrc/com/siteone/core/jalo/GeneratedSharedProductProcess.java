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
import de.hybris.platform.jalo.product.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SharedProductProcess SharedProductProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSharedProductProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>SharedProductProcess.product</code> attribute **/
	public static final String PRODUCT = "product";
	/** Qualifier of the <code>SharedProductProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>SharedProductProcess.username</code> attribute **/
	public static final String USERNAME = "username";
	/** Qualifier of the <code>SharedProductProcess.stockavailabilitymessage</code> attribute **/
	public static final String STOCKAVAILABILITYMESSAGE = "stockavailabilitymessage";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCT, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(USERNAME, AttributeMode.INITIAL);
		tmp.put(STOCKAVAILABILITYMESSAGE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.product</code> attribute.
	 * @return the product
	 */
	public Product getProduct(final SessionContext ctx)
	{
		return (Product)getProperty( ctx, PRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.product</code> attribute.
	 * @return the product
	 */
	public Product getProduct()
	{
		return getProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.product</code> attribute. 
	 * @param value the product
	 */
	public void setProduct(final SessionContext ctx, final Product value)
	{
		setProperty(ctx, PRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.product</code> attribute. 
	 * @param value the product
	 */
	public void setProduct(final Product value)
	{
		setProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.stockavailabilitymessage</code> attribute.
	 * @return the stockavailabilitymessage
	 */
	public String getStockavailabilitymessage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOCKAVAILABILITYMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.stockavailabilitymessage</code> attribute.
	 * @return the stockavailabilitymessage
	 */
	public String getStockavailabilitymessage()
	{
		return getStockavailabilitymessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.stockavailabilitymessage</code> attribute. 
	 * @param value the stockavailabilitymessage
	 */
	public void setStockavailabilitymessage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STOCKAVAILABILITYMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.stockavailabilitymessage</code> attribute. 
	 * @param value the stockavailabilitymessage
	 */
	public void setStockavailabilitymessage(final String value)
	{
		setStockavailabilitymessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.username</code> attribute.
	 * @return the username
	 */
	public String getUsername(final SessionContext ctx)
	{
		return (String)getProperty( ctx, USERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SharedProductProcess.username</code> attribute.
	 * @return the username
	 */
	public String getUsername()
	{
		return getUsername( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.username</code> attribute. 
	 * @param value the username
	 */
	public void setUsername(final SessionContext ctx, final String value)
	{
		setProperty(ctx, USERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SharedProductProcess.username</code> attribute. 
	 * @param value the username
	 */
	public void setUsername(final String value)
	{
		setUsername( getSession().getSessionContext(), value );
	}
	
}
