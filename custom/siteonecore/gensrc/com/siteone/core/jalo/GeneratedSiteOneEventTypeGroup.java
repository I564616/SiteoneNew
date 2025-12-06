/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneEventType;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneEventTypeGroup SiteOneEventTypeGroup}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneEventTypeGroup extends GenericItem
{
	/** Qualifier of the <code>SiteOneEventTypeGroup.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>SiteOneEventTypeGroup.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>SiteOneEventTypeGroup.types</code> attribute **/
	public static final String TYPES = "types";
	/**
	* {@link OneToManyHandler} for handling 1:n TYPES's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<SiteOneEventType> TYPESHANDLER = new OneToManyHandler<SiteOneEventType>(
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
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventTypeGroup.description</code> attribute.
	 * @return the description - Indicates the event group description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventTypeGroup.description</code> attribute.
	 * @return the description - Indicates the event group description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventTypeGroup.description</code> attribute. 
	 * @param value the description - Indicates the event group description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventTypeGroup.description</code> attribute. 
	 * @param value the description - Indicates the event group description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventTypeGroup.name</code> attribute.
	 * @return the name - Indicates the event group name
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventTypeGroup.name</code> attribute.
	 * @return the name - Indicates the event group name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventTypeGroup.name</code> attribute. 
	 * @param value the name - Indicates the event group name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventTypeGroup.name</code> attribute. 
	 * @param value the name - Indicates the event group name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventTypeGroup.types</code> attribute.
	 * @return the types
	 */
	public Collection<SiteOneEventType> getTypes(final SessionContext ctx)
	{
		return TYPESHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEventTypeGroup.types</code> attribute.
	 * @return the types
	 */
	public Collection<SiteOneEventType> getTypes()
	{
		return getTypes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventTypeGroup.types</code> attribute. 
	 * @param value the types
	 */
	public void setTypes(final SessionContext ctx, final Collection<SiteOneEventType> value)
	{
		TYPESHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEventTypeGroup.types</code> attribute. 
	 * @param value the types
	 */
	public void setTypes(final Collection<SiteOneEventType> value)
	{
		setTypes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to types. 
	 * @param value the item to add to types
	 */
	public void addToTypes(final SessionContext ctx, final SiteOneEventType value)
	{
		TYPESHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to types. 
	 * @param value the item to add to types
	 */
	public void addToTypes(final SiteOneEventType value)
	{
		addToTypes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from types. 
	 * @param value the item to remove from types
	 */
	public void removeFromTypes(final SessionContext ctx, final SiteOneEventType value)
	{
		TYPESHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from types. 
	 * @param value the item to remove from types
	 */
	public void removeFromTypes(final SiteOneEventType value)
	{
		removeFromTypes( getSession().getSessionContext(), value );
	}
	
}
