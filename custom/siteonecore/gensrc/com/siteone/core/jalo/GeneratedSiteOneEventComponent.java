/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneEvent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneEventComponent SiteOneEventComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneEventComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneEventComponent.event</code> attribute **/
	public static final String EVENT = "event";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(EVENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventComponent.event</code> attribute.
	 * @return the event
	 */
	public SiteOneEvent getEvent(final SessionContext ctx)
	{
		return (SiteOneEvent)getProperty( ctx, EVENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventComponent.event</code> attribute.
	 * @return the event
	 */
	public SiteOneEvent getEvent()
	{
		return getEvent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventComponent.event</code> attribute. 
	 * @param value the event
	 */
	public void setEvent(final SessionContext ctx, final SiteOneEvent value)
	{
		setProperty(ctx, EVENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventComponent.event</code> attribute. 
	 * @param value the event
	 */
	public void setEvent(final SiteOneEvent value)
	{
		setEvent( getSession().getSessionContext(), value );
	}
	
}
