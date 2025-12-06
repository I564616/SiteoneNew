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
 * Generated class for type {@link com.siteone.core.jalo.CustomPushNotification CustomPushNotification}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCustomPushNotification extends GenericItem
{
	/** Qualifier of the <code>CustomPushNotification.pageId</code> attribute **/
	public static final String PAGEID = "pageId";
	/** Qualifier of the <code>CustomPushNotification.identifier</code> attribute **/
	public static final String IDENTIFIER = "identifier";
	/** Qualifier of the <code>CustomPushNotification.message</code> attribute **/
	public static final String MESSAGE = "message";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PAGEID, AttributeMode.INITIAL);
		tmp.put(IDENTIFIER, AttributeMode.INITIAL);
		tmp.put(MESSAGE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomPushNotification.identifier</code> attribute.
	 * @return the identifier
	 */
	public String getIdentifier(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IDENTIFIER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomPushNotification.identifier</code> attribute.
	 * @return the identifier
	 */
	public String getIdentifier()
	{
		return getIdentifier( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomPushNotification.identifier</code> attribute. 
	 * @param value the identifier
	 */
	public void setIdentifier(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IDENTIFIER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomPushNotification.identifier</code> attribute. 
	 * @param value the identifier
	 */
	public void setIdentifier(final String value)
	{
		setIdentifier( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomPushNotification.message</code> attribute.
	 * @return the message
	 */
	public String getMessage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomPushNotification.message</code> attribute.
	 * @return the message
	 */
	public String getMessage()
	{
		return getMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomPushNotification.message</code> attribute. 
	 * @param value the message
	 */
	public void setMessage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomPushNotification.message</code> attribute. 
	 * @param value the message
	 */
	public void setMessage(final String value)
	{
		setMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomPushNotification.pageId</code> attribute.
	 * @return the pageId
	 */
	public String getPageId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAGEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomPushNotification.pageId</code> attribute.
	 * @return the pageId
	 */
	public String getPageId()
	{
		return getPageId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomPushNotification.pageId</code> attribute. 
	 * @param value the pageId
	 */
	public void setPageId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAGEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomPushNotification.pageId</code> attribute. 
	 * @param value the pageId
	 */
	public void setPageId(final String value)
	{
		setPageId( getSession().getSessionContext(), value );
	}
	
}
