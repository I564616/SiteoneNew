/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneEventTypeGroup;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneEventType SiteOneEventType}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneEventType extends GenericItem
{
	/** Qualifier of the <code>SiteOneEventType.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>SiteOneEventType.group</code> attribute **/
	public static final String GROUP = "group";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n GROUP's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedSiteOneEventType> GROUPHANDLER = new BidirectionalOneToManyHandler<GeneratedSiteOneEventType>(
	SiteoneCoreConstants.TC.SITEONEEVENTTYPE,
	false,
	"group",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(NAME, AttributeMode.INITIAL);
		tmp.put(GROUP, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		GROUPHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventType.group</code> attribute.
	 * @return the group
	 */
	public SiteOneEventTypeGroup getGroup(final SessionContext ctx)
	{
		return (SiteOneEventTypeGroup)getProperty( ctx, GROUP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventType.group</code> attribute.
	 * @return the group
	 */
	public SiteOneEventTypeGroup getGroup()
	{
		return getGroup( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventType.group</code> attribute. 
	 * @param value the group
	 */
	public void setGroup(final SessionContext ctx, final SiteOneEventTypeGroup value)
	{
		GROUPHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventType.group</code> attribute. 
	 * @param value the group
	 */
	public void setGroup(final SiteOneEventTypeGroup value)
	{
		setGroup( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventType.name</code> attribute.
	 * @return the name - Indicates the event type
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventType.name</code> attribute.
	 * @return the name - Indicates the event type
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventType.name</code> attribute. 
	 * @param value the name - Indicates the event type
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventType.name</code> attribute. 
	 * @param value the name - Indicates the event type
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
}
