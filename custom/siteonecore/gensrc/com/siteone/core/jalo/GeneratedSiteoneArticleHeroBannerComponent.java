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
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneArticleHeroBannerComponent SiteoneArticleHeroBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneArticleHeroBannerComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute **/
	public static final String HEROIMAGE = "heroImage";
	/** Qualifier of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute **/
	public static final String HEROIMAGETEXT = "heroImageText";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(HEROIMAGE, AttributeMode.INITIAL);
		tmp.put(HEROIMAGETEXT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute.
	 * @return the heroImage
	 */
	public Media getHeroImage(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleHeroBannerComponent.getHeroImage requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, HEROIMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute.
	 * @return the heroImage
	 */
	public Media getHeroImage()
	{
		return getHeroImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute. 
	 * @return the localized heroImage
	 */
	public Map<Language,Media> getAllHeroImage(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,HEROIMAGE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute. 
	 * @return the localized heroImage
	 */
	public Map<Language,Media> getAllHeroImage()
	{
		return getAllHeroImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute. 
	 * @param value the heroImage
	 */
	public void setHeroImage(final SessionContext ctx, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleHeroBannerComponent.setHeroImage requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEROIMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute. 
	 * @param value the heroImage
	 */
	public void setHeroImage(final Media value)
	{
		setHeroImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute. 
	 * @param value the heroImage
	 */
	public void setAllHeroImage(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,HEROIMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImage</code> attribute. 
	 * @param value the heroImage
	 */
	public void setAllHeroImage(final Map<Language,Media> value)
	{
		setAllHeroImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute.
	 * @return the heroImageText
	 */
	public String getHeroImageText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleHeroBannerComponent.getHeroImageText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEROIMAGETEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute.
	 * @return the heroImageText
	 */
	public String getHeroImageText()
	{
		return getHeroImageText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute. 
	 * @return the localized heroImageText
	 */
	public Map<Language,String> getAllHeroImageText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEROIMAGETEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute. 
	 * @return the localized heroImageText
	 */
	public Map<Language,String> getAllHeroImageText()
	{
		return getAllHeroImageText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute. 
	 * @param value the heroImageText
	 */
	public void setHeroImageText(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneArticleHeroBannerComponent.setHeroImageText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEROIMAGETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute. 
	 * @param value the heroImageText
	 */
	public void setHeroImageText(final String value)
	{
		setHeroImageText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute. 
	 * @param value the heroImageText
	 */
	public void setAllHeroImageText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEROIMAGETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneArticleHeroBannerComponent.heroImageText</code> attribute. 
	 * @param value the heroImageText
	 */
	public void setAllHeroImageText(final Map<Language,String> value)
	{
		setAllHeroImageText( getSession().getSessionContext(), value );
	}
	
}
