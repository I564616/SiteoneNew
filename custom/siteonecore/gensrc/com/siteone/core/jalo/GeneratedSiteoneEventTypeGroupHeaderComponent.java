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
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneEventTypeGroupHeaderComponent SiteoneEventTypeGroupHeaderComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneEventTypeGroupHeaderComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute **/
	public static final String TYPECONTENT = "typeContent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
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
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupHeaderComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupHeaderComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute.
	 * @return the typeContent
	 */
	public String getTypeContent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupHeaderComponent.getTypeContent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TYPECONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute.
	 * @return the typeContent
	 */
	public String getTypeContent()
	{
		return getTypeContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute. 
	 * @return the localized typeContent
	 */
	public Map<Language,String> getAllTypeContent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TYPECONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute. 
	 * @return the localized typeContent
	 */
	public Map<Language,String> getAllTypeContent()
	{
		return getAllTypeContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteoneEventTypeGroupHeaderComponent.setTypeContent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TYPECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setTypeContent(final String value)
	{
		setTypeContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setAllTypeContent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TYPECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEventTypeGroupHeaderComponent.typeContent</code> attribute. 
	 * @param value the typeContent
	 */
	public void setAllTypeContent(final Map<Language,String> value)
	{
		setAllTypeContent( getSession().getSessionContext(), value );
	}
	
}
