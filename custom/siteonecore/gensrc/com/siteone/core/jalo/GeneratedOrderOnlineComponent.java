/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.OrderOnlineComponent OrderOnlineComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedOrderOnlineComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>OrderOnlineComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>OrderOnlineComponent.orderOnlineText</code> attribute **/
	public static final String ORDERONLINETEXT = "orderOnlineText";
	/** Qualifier of the <code>OrderOnlineComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>OrderOnlineComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(ORDERONLINETEXT, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		tmp.put(BUTTONURL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.orderOnlineText</code> attribute.
	 * @return the orderOnlineText
	 */
	public String getOrderOnlineText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERONLINETEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.orderOnlineText</code> attribute.
	 * @return the orderOnlineText
	 */
	public String getOrderOnlineText()
	{
		return getOrderOnlineText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.orderOnlineText</code> attribute. 
	 * @param value the orderOnlineText
	 */
	public void setOrderOnlineText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERONLINETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.orderOnlineText</code> attribute. 
	 * @param value the orderOnlineText
	 */
	public void setOrderOnlineText(final String value)
	{
		setOrderOnlineText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderOnlineComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderOnlineComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
}
