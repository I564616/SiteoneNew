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
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneCategoryPageTopBannerComponent SiteOneCategoryPageTopBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneCategoryPageTopBannerComponent extends SimpleBannerComponent
{
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute **/
	public static final String PROMOTIONALTEXT = "promotionalText";
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.subheadText</code> attribute **/
	public static final String SUBHEADTEXT = "subheadText";
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute **/
	public static final String ICON = "icon";
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteOneCategoryPageTopBannerComponent.video</code> attribute **/
	public static final String VIDEO = "video";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PROMOTIONALTEXT, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		tmp.put(BUTTONURL, AttributeMode.INITIAL);
		tmp.put(SUBHEADTEXT, AttributeMode.INITIAL);
		tmp.put(ICON, AttributeMode.INITIAL);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(VIDEO, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCategoryPageTopBannerComponent.getButtonLabel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel()
	{
		return getAllButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneCategoryPageTopBannerComponent.setButtonLabel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final Map<Language,String> value)
	{
		setAllButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCategoryPageTopBannerComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneCategoryPageTopBannerComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute.
	 * @return the icon
	 */
	public Integer getIcon(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ICON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute.
	 * @return the icon
	 */
	public Integer getIcon()
	{
		return getIcon( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute. 
	 * @return the icon
	 */
	public int getIconAsPrimitive(final SessionContext ctx)
	{
		Integer value = getIcon( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute. 
	 * @return the icon
	 */
	public int getIconAsPrimitive()
	{
		return getIconAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute. 
	 * @param value the icon
	 */
	public void setIcon(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ICON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute. 
	 * @param value the icon
	 */
	public void setIcon(final Integer value)
	{
		setIcon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute. 
	 * @param value the icon
	 */
	public void setIcon(final SessionContext ctx, final int value)
	{
		setIcon( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.icon</code> attribute. 
	 * @param value the icon
	 */
	public void setIcon(final int value)
	{
		setIcon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCategoryPageTopBannerComponent.getPromotionalText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PROMOTIONALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText()
	{
		return getPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PROMOTIONALTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText()
	{
		return getAllPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneCategoryPageTopBannerComponent.setPromotionalText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final String value)
	{
		setPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final Map<Language,String> value)
	{
		setAllPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.subheadText</code> attribute.
	 * @return the subheadText
	 */
	public String getSubheadText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SUBHEADTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.subheadText</code> attribute.
	 * @return the subheadText
	 */
	public String getSubheadText()
	{
		return getSubheadText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.subheadText</code> attribute. 
	 * @param value the subheadText
	 */
	public void setSubheadText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SUBHEADTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.subheadText</code> attribute. 
	 * @param value the subheadText
	 */
	public void setSubheadText(final String value)
	{
		setSubheadText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.video</code> attribute.
	 * @return the video
	 */
	public Media getVideo(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, VIDEO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPageTopBannerComponent.video</code> attribute.
	 * @return the video
	 */
	public Media getVideo()
	{
		return getVideo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.video</code> attribute. 
	 * @param value the video
	 */
	public void setVideo(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, VIDEO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPageTopBannerComponent.video</code> attribute. 
	 * @param value the video
	 */
	public void setVideo(final Media value)
	{
		setVideo( getSession().getSessionContext(), value );
	}
	
}
