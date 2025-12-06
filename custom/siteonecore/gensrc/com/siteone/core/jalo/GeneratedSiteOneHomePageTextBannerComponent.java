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
 * Generated class for type {@link com.siteone.core.jalo.SiteOneHomePageTextBannerComponent SiteOneHomePageTextBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneHomePageTextBannerComponent extends SimpleBannerComponent
{
	/** Qualifier of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>SiteOneHomePageTextBannerComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	/** Qualifier of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		tmp.put(BUTTONURL, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageTextBannerComponent.getButtonLabel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel()
	{
		return getAllButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageTextBannerComponent.setButtonLabel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final Map<Language,String> value)
	{
		setAllButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageTextBannerComponent.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageTextBannerComponent.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageTextBannerComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute. 
	 * @return the localized title
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageTextBannerComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageTextBannerComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
