/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.acceleratorcms.jalo.components.SimpleBannerComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.HomePageToolsComponent HomePageToolsComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHomePageToolsComponent extends SimpleBannerComponent
{
	/** Qualifier of the <code>HomePageToolsComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>HomePageToolsComponent.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>HomePageToolsComponent.buttonName</code> attribute **/
	public static final String BUTTONNAME = "buttonName";
	/** Qualifier of the <code>HomePageToolsComponent.videoId</code> attribute **/
	public static final String VIDEOID = "videoId";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(BUTTONNAME, AttributeMode.INITIAL);
		tmp.put(VIDEOID, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.buttonName</code> attribute.
	 * @return the buttonName
	 */
	public String getButtonName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageToolsComponent.getButtonName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.buttonName</code> attribute.
	 * @return the buttonName
	 */
	public String getButtonName()
	{
		return getButtonName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.buttonName</code> attribute. 
	 * @return the localized buttonName
	 */
	public Map<Language,String> getAllButtonName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.buttonName</code> attribute. 
	 * @return the localized buttonName
	 */
	public Map<Language,String> getAllButtonName()
	{
		return getAllButtonName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setButtonName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageToolsComponent.setButtonName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setButtonName(final String value)
	{
		setButtonName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setAllButtonName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setAllButtonName(final Map<Language,String> value)
	{
		setAllButtonName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageToolsComponent.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageToolsComponent.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageToolsComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.title</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedHomePageToolsComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.videoId</code> attribute.
	 * @return the videoId
	 */
	public String getVideoId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VIDEOID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageToolsComponent.videoId</code> attribute.
	 * @return the videoId
	 */
	public String getVideoId()
	{
		return getVideoId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.videoId</code> attribute. 
	 * @param value the videoId
	 */
	public void setVideoId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VIDEOID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageToolsComponent.videoId</code> attribute. 
	 * @param value the videoId
	 */
	public void setVideoId(final String value)
	{
		setVideoId( getSession().getSessionContext(), value );
	}
	
}
