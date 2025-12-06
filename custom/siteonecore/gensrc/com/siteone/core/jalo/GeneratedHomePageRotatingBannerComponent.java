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
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.HomePageRotatingBannerComponent HomePageRotatingBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHomePageRotatingBannerComponent extends RotatingImagesComponent
{
	/** Qualifier of the <code>HomePageRotatingBannerComponent.promotionalText</code> attribute **/
	public static final String PROMOTIONALTEXT = "promotionalText";
	/** Qualifier of the <code>HomePageRotatingBannerComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>HomePageRotatingBannerComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	/** Qualifier of the <code>HomePageRotatingBannerComponent.rotatingBanner</code> attribute **/
	public static final String ROTATINGBANNER = "rotatingBanner";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(RotatingImagesComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PROMOTIONALTEXT, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		tmp.put(BUTTONURL, AttributeMode.INITIAL);
		tmp.put(ROTATINGBANNER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PROMOTIONALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText()
	{
		return getPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final String value)
	{
		setPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.rotatingBanner</code> attribute.
	 * @return the rotatingBanner
	 */
	public List<SiteOneStoreDetailPromoComponent> getRotatingBanner(final SessionContext ctx)
	{
		List<SiteOneStoreDetailPromoComponent> coll = (List<SiteOneStoreDetailPromoComponent>)getProperty( ctx, ROTATINGBANNER);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageRotatingBannerComponent.rotatingBanner</code> attribute.
	 * @return the rotatingBanner
	 */
	public List<SiteOneStoreDetailPromoComponent> getRotatingBanner()
	{
		return getRotatingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.rotatingBanner</code> attribute. 
	 * @param value the rotatingBanner
	 */
	public void setRotatingBanner(final SessionContext ctx, final List<SiteOneStoreDetailPromoComponent> value)
	{
		setProperty(ctx, ROTATINGBANNER,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageRotatingBannerComponent.rotatingBanner</code> attribute. 
	 * @param value the rotatingBanner
	 */
	public void setRotatingBanner(final List<SiteOneStoreDetailPromoComponent> value)
	{
		setRotatingBanner( getSession().getSessionContext(), value );
	}
	
}
