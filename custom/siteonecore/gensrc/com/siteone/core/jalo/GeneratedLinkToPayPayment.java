/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.LinkToPayPayment LinkToPayPayment}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedLinkToPayPayment extends GenericItem
{
	/** Qualifier of the <code>LinkToPayPayment.authcode</code> attribute **/
	public static final String AUTHCODE = "authcode";
	/** Qualifier of the <code>LinkToPayPayment.lastFourDigits</code> attribute **/
	public static final String LASTFOURDIGITS = "lastFourDigits";
	/** Qualifier of the <code>LinkToPayPayment.cardName</code> attribute **/
	public static final String CARDNAME = "cardName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(AUTHCODE, AttributeMode.INITIAL);
		tmp.put(LASTFOURDIGITS, AttributeMode.INITIAL);
		tmp.put(CARDNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPayment.authcode</code> attribute.
	 * @return the authcode
	 */
	public String getAuthcode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AUTHCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPayment.authcode</code> attribute.
	 * @return the authcode
	 */
	public String getAuthcode()
	{
		return getAuthcode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPayment.authcode</code> attribute. 
	 * @param value the authcode
	 */
	public void setAuthcode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AUTHCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPayment.authcode</code> attribute. 
	 * @param value the authcode
	 */
	public void setAuthcode(final String value)
	{
		setAuthcode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPayment.cardName</code> attribute.
	 * @return the cardName
	 */
	public String getCardName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPayment.cardName</code> attribute.
	 * @return the cardName
	 */
	public String getCardName()
	{
		return getCardName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPayment.cardName</code> attribute. 
	 * @param value the cardName
	 */
	public void setCardName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPayment.cardName</code> attribute. 
	 * @param value the cardName
	 */
	public void setCardName(final String value)
	{
		setCardName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPayment.lastFourDigits</code> attribute.
	 * @return the lastFourDigits
	 */
	public String getLastFourDigits(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTFOURDIGITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPayment.lastFourDigits</code> attribute.
	 * @return the lastFourDigits
	 */
	public String getLastFourDigits()
	{
		return getLastFourDigits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPayment.lastFourDigits</code> attribute. 
	 * @param value the lastFourDigits
	 */
	public void setLastFourDigits(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTFOURDIGITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPayment.lastFourDigits</code> attribute. 
	 * @param value the lastFourDigits
	 */
	public void setLastFourDigits(final String value)
	{
		setLastFourDigits( getSession().getSessionContext(), value );
	}
	
}
