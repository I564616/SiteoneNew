/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneCategoryPageTopBannerComponent;
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
 * Generated class for type {@link com.siteone.core.jalo.CategoryLandingPageRotatingBannerComponent CategoryLandingPageRotatingBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCategoryLandingPageRotatingBannerComponent extends RotatingImagesComponent
{
	/** Qualifier of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute **/
	public static final String PROMOTIONALTEXT = "promotionalText";
	/** Qualifier of the <code>CategoryLandingPageRotatingBannerComponent.rotatingBanner</code> attribute **/
	public static final String ROTATINGBANNER = "rotatingBanner";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(RotatingImagesComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PROMOTIONALTEXT, AttributeMode.INITIAL);
		tmp.put(ROTATINGBANNER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategoryLandingPageRotatingBannerComponent.getPromotionalText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PROMOTIONALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText()
	{
		return getPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PROMOTIONALTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText()
	{
		return getAllPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedCategoryLandingPageRotatingBannerComponent.setPromotionalText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final String value)
	{
		setPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryLandingPageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final Map<Language,String> value)
	{
		setAllPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryLandingPageRotatingBannerComponent.rotatingBanner</code> attribute.
	 * @return the rotatingBanner
	 */
	public List<SiteOneCategoryPageTopBannerComponent> getRotatingBanner(final SessionContext ctx)
	{
		List<SiteOneCategoryPageTopBannerComponent> coll = (List<SiteOneCategoryPageTopBannerComponent>)getProperty( ctx, ROTATINGBANNER);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryLandingPageRotatingBannerComponent.rotatingBanner</code> attribute.
	 * @return the rotatingBanner
	 */
	public List<SiteOneCategoryPageTopBannerComponent> getRotatingBanner()
	{
		return getRotatingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryLandingPageRotatingBannerComponent.rotatingBanner</code> attribute. 
	 * @param value the rotatingBanner
	 */
	public void setRotatingBanner(final SessionContext ctx, final List<SiteOneCategoryPageTopBannerComponent> value)
	{
		setProperty(ctx, ROTATINGBANNER,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryLandingPageRotatingBannerComponent.rotatingBanner</code> attribute. 
	 * @param value the rotatingBanner
	 */
	public void setRotatingBanner(final List<SiteOneCategoryPageTopBannerComponent> value)
	{
		setRotatingBanner( getSession().getSessionContext(), value );
	}
	
}
