/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneEventTypeGroup;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneEventTypeGroupComponent SiteoneEventTypeGroupComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneEventTypeGroupComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneEventTypeGroupComponent.eventType</code> attribute **/
	public static final String EVENTTYPE = "eventType";
	/** Qualifier of the <code>SiteoneEventTypeGroupComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute **/
	public static final String TYPECONTENT = "typeContent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(EVENTTYPE, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(TYPECONTENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.eventType</code> attribute.
	 * @return the eventType
	 */
	public SiteOneEventTypeGroup getEventType(final SessionContext ctx)
	{
		return (SiteOneEventTypeGroup)getProperty( ctx, EVENTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.eventType</code> attribute.
	 * @return the eventType
	 */
	public SiteOneEventTypeGroup getEventType()
	{
		return getEventType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.eventType</code> attribute. 
	 * @param value the eventType
	 */
	public void setEventType(final SessionContext ctx, final SiteOneEventTypeGroup value)
	{
		setProperty(ctx, EVENTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.eventType</code> attribute. 
	 * @param value the eventType
	 */
	public void setEventType(final SiteOneEventTypeGroup value)
	{
		setEventType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute.
	 * @return the typeContent
	 */
	public String getTypeContent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupComponent.getTypeContent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TYPECONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute.
	 * @return the typeContent
	 */
	public String getTypeContent()
	{
		return getTypeContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute. 
	 * @return the localized typeContent
	 */
	public Map<Language,String> getAllTypeContent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TYPECONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute. 
	 * @return the localized typeContent
	 */
	public Map<Language,String> getAllTypeContent()
	{
		return getAllTypeContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setTypeContent(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupComponent.setTypeContent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TYPECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setTypeContent(final String value)
	{
		setTypeContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setAllTypeContent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TYPECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setAllTypeContent(final Map<Language,String> value)
	{
		setAllTypeContent( getSession().getSessionContext(), value );
	}
	
}
