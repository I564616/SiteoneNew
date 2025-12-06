/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneStoreDetailPromoComponent;
import de.hybris.platform.cms2lib.components.RotatingImagesComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneGalleryHeaderBannerComponent SiteOneGalleryHeaderBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneGalleryHeaderBannerComponent extends RotatingImagesComponent
{
	/** Qualifier of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute **/
	public static final String PROMOTIONALTEXT = "promotionalText";
	/** Qualifier of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteOneGalleryHeaderBannerComponent.rotatingBanner</code> attribute **/
	public static final String ROTATINGBANNER = "rotatingBanner";
	/** Qualifier of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute **/
	public static final String CONTENT = "content";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(RotatingImagesComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PROMOTIONALTEXT, AttributeMode.INITIAL);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(ROTATINGBANNER, AttributeMode.INITIAL);
		tmp.put(CONTENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute.
	 * @return the content
	 */
	public String getContent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderBannerComponent.getContent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute.
	 * @return the content
	 */
	public String getContent()
	{
		return getContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute. 
	 * @return the localized content
	 */
	public Map<Language,String> getAllContent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute. 
	 * @return the localized content
	 */
	public Map<Language,String> getAllContent()
	{
		return getAllContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderBannerComponent.setContent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final String value)
	{
		setContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setAllContent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setAllContent(final Map<Language,String> value)
	{
		setAllContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderBannerComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderBannerComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderBannerComponent.getPromotionalText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PROMOTIONALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText()
	{
		return getPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PROMOTIONALTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText()
	{
		return getAllPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderBannerComponent.setPromotionalText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final String value)
	{
		setPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final Map<Language,String> value)
	{
		setAllPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.rotatingBanner</code> attribute.
	 * @return the rotatingBanner
	 */
	public List<SiteOneStoreDetailPromoComponent> getRotatingBanner(final SessionContext ctx)
	{
		List<SiteOneStoreDetailPromoComponent> coll = (List<SiteOneStoreDetailPromoComponent>)getProperty( ctx, ROTATINGBANNER);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderBannerComponent.rotatingBanner</code> attribute.
	 * @return the rotatingBanner
	 */
	public List<SiteOneStoreDetailPromoComponent> getRotatingBanner()
	{
		return getRotatingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.rotatingBanner</code> attribute. 
	 * @param value the rotatingBanner
	 */
	public void setRotatingBanner(final SessionContext ctx, final List<SiteOneStoreDetailPromoComponent> value)
	{
		setProperty(ctx, ROTATINGBANNER,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderBannerComponent.rotatingBanner</code> attribute. 
	 * @param value the rotatingBanner
	 */
	public void setRotatingBanner(final List<SiteOneStoreDetailPromoComponent> value)
	{
		setRotatingBanner( getSession().getSessionContext(), value );
	}
	
}
