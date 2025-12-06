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
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneEventListComponent SiteOneEventListComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneEventListComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneEventListComponent.eventList</code> attribute **/
	public static final String EVENTLIST = "eventList";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(EVENTLIST, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventListComponent.eventList</code> attribute.
	 * @return the eventList
	 */
	public List<SiteOneEvent> getEventList(final SessionContext ctx)
	{
		List<SiteOneEvent> coll = (List<SiteOneEvent>)getProperty( ctx, EVENTLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventListComponent.eventList</code> attribute.
	 * @return the eventList
	 */
	public List<SiteOneEvent> getEventList()
	{
		return getEventList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventListComponent.eventList</code> attribute. 
	 * @param value the eventList
	 */
	public void setEventList(final SessionContext ctx, final List<SiteOneEvent> value)
	{
		setProperty(ctx, EVENTLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventListComponent.eventList</code> attribute. 
	 * @param value the eventList
	 */
	public void setEventList(final List<SiteOneEvent> value)
	{
		setEventList( getSession().getSessionContext(), value );
	}
	
}
