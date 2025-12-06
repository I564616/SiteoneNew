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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneEmbedVideoComponent SiteoneEmbedVideoComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneEmbedVideoComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneEmbedVideoComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute **/
	public static final String EXTERNALURL = "externalurl";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(EXTERNALURL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute.
	 * @return the externalurl
	 */
	public String getExternalurl(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEmbedVideoComponent.getExternalurl requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, EXTERNALURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute.
	 * @return the externalurl
	 */
	public String getExternalurl()
	{
		return getExternalurl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute. 
	 * @return the localized externalurl
	 */
	public Map<Language,String> getAllExternalurl(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,EXTERNALURL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute. 
	 * @return the localized externalurl
	 */
	public Map<Language,String> getAllExternalurl()
	{
		return getAllExternalurl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute. 
	 * @param value the externalurl
	 */
	public void setExternalurl(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEmbedVideoComponent.setExternalurl requires a session language", 0 );
		}
		setLocalizedProperty(ctx, EXTERNALURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute. 
	 * @param value the externalurl
	 */
	public void setExternalurl(final String value)
	{
		setExternalurl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute. 
	 * @param value the externalurl
	 */
	public void setAllExternalurl(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,EXTERNALURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.externalurl</code> attribute. 
	 * @param value the externalurl
	 */
	public void setAllExternalurl(final Map<Language,String> value)
	{
		setAllExternalurl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEmbedVideoComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneEmbedVideoComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEmbedVideoComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
}
