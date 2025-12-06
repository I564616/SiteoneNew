/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.CMSParagraphComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneArticleFeatureComponent SiteoneArticleFeatureComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneArticleFeatureComponent extends CMSParagraphComponent
{
	/** Qualifier of the <code>SiteoneArticleFeatureComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteoneArticleFeatureComponent.media</code> attribute **/
	public static final String MEDIA = "media";
	/** Qualifier of the <code>SiteoneArticleFeatureComponent.urlLink</code> attribute **/
	public static final String URLLINK = "urlLink";
	/** Qualifier of the <code>SiteoneArticleFeatureComponent.external</code> attribute **/
	public static final String EXTERNAL = "external";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CMSParagraphComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(MEDIA, AttributeMode.INITIAL);
		tmp.put(URLLINK, AttributeMode.INITIAL);
		tmp.put(EXTERNAL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.external</code> attribute.
	 * @return the external
	 */
	public Boolean isExternal(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, EXTERNAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.external</code> attribute.
	 * @return the external
	 */
	public Boolean isExternal()
	{
		return isExternal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.external</code> attribute. 
	 * @return the external
	 */
	public boolean isExternalAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isExternal( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.external</code> attribute. 
	 * @return the external
	 */
	public boolean isExternalAsPrimitive()
	{
		return isExternalAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.external</code> attribute. 
	 * @param value the external
	 */
	public void setExternal(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, EXTERNAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.external</code> attribute. 
	 * @param value the external
	 */
	public void setExternal(final Boolean value)
	{
		setExternal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.external</code> attribute. 
	 * @param value the external
	 */
	public void setExternal(final SessionContext ctx, final boolean value)
	{
		setExternal( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.external</code> attribute. 
	 * @param value the external
	 */
	public void setExternal(final boolean value)
	{
		setExternal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleFeatureComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleFeatureComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.media</code> attribute.
	 * @return the media
	 */
	public Media getMedia(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleFeatureComponent.getMedia requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, MEDIA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.media</code> attribute.
	 * @return the media
	 */
	public Media getMedia()
	{
		return getMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.media</code> attribute. 
	 * @return the localized media
	 */
	public Map<Language,Media> getAllMedia(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,MEDIA,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.media</code> attribute. 
	 * @return the localized media
	 */
	public Map<Language,Media> getAllMedia()
	{
		return getAllMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setMedia(final SessionContext ctx, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleFeatureComponent.setMedia requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setMedia(final Media value)
	{
		setMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setAllMedia(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,MEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setAllMedia(final Map<Language,Media> value)
	{
		setAllMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.urlLink</code> attribute.
	 * @return the urlLink
	 */
	public String getUrlLink(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URLLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleFeatureComponent.urlLink</code> attribute.
	 * @return the urlLink
	 */
	public String getUrlLink()
	{
		return getUrlLink( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.urlLink</code> attribute. 
	 * @param value the urlLink
	 */
	public void setUrlLink(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URLLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleFeatureComponent.urlLink</code> attribute. 
	 * @param value the urlLink
	 */
	public void setUrlLink(final String value)
	{
		setUrlLink( getSession().getSessionContext(), value );
	}
	
}
