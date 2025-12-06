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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneParagraphComponent SiteoneParagraphComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneParagraphComponent extends CMSParagraphComponent
{
	/** Qualifier of the <code>SiteoneParagraphComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteoneParagraphComponent.media</code> attribute **/
	public static final String MEDIA = "media";
	/** Qualifier of the <code>SiteoneParagraphComponent.urlForLink</code> attribute **/
	public static final String URLFORLINK = "urlForLink";
	/** Qualifier of the <code>SiteoneParagraphComponent.externalLink</code> attribute **/
	public static final String EXTERNALLINK = "externalLink";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CMSParagraphComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(MEDIA, AttributeMode.INITIAL);
		tmp.put(URLFORLINK, AttributeMode.INITIAL);
		tmp.put(EXTERNALLINK, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.externalLink</code> attribute.
	 * @return the externalLink
	 */
	public Boolean isExternalLink(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, EXTERNALLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.externalLink</code> attribute.
	 * @return the externalLink
	 */
	public Boolean isExternalLink()
	{
		return isExternalLink( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.externalLink</code> attribute. 
	 * @return the externalLink
	 */
	public boolean isExternalLinkAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isExternalLink( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.externalLink</code> attribute. 
	 * @return the externalLink
	 */
	public boolean isExternalLinkAsPrimitive()
	{
		return isExternalLinkAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.externalLink</code> attribute. 
	 * @param value the externalLink
	 */
	public void setExternalLink(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, EXTERNALLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.externalLink</code> attribute. 
	 * @param value the externalLink
	 */
	public void setExternalLink(final Boolean value)
	{
		setExternalLink( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.externalLink</code> attribute. 
	 * @param value the externalLink
	 */
	public void setExternalLink(final SessionContext ctx, final boolean value)
	{
		setExternalLink( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.externalLink</code> attribute. 
	 * @param value the externalLink
	 */
	public void setExternalLink(final boolean value)
	{
		setExternalLink( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneParagraphComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.headline</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteoneParagraphComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.media</code> attribute.
	 * @return the media
	 */
	public Media getMedia(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneParagraphComponent.getMedia requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, MEDIA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.media</code> attribute.
	 * @return the media
	 */
	public Media getMedia()
	{
		return getMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.media</code> attribute. 
	 * @return the localized media
	 */
	public Map<Language,Media> getAllMedia(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,MEDIA,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.media</code> attribute. 
	 * @return the localized media
	 */
	public Map<Language,Media> getAllMedia()
	{
		return getAllMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.media</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteoneParagraphComponent.setMedia requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setMedia(final Media value)
	{
		setMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setAllMedia(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,MEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.media</code> attribute. 
	 * @param value the media
	 */
	public void setAllMedia(final Map<Language,Media> value)
	{
		setAllMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.urlForLink</code> attribute.
	 * @return the urlForLink
	 */
	public String getUrlForLink(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URLFORLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneParagraphComponent.urlForLink</code> attribute.
	 * @return the urlForLink
	 */
	public String getUrlForLink()
	{
		return getUrlForLink( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.urlForLink</code> attribute. 
	 * @param value the urlForLink
	 */
	public void setUrlForLink(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URLFORLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneParagraphComponent.urlForLink</code> attribute. 
	 * @param value the urlForLink
	 */
	public void setUrlForLink(final String value)
	{
		setUrlForLink( getSession().getSessionContext(), value );
	}
	
}
