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
 * Generated class for type {@link com.siteone.core.jalo.SiteOneCuratedPLPComponent SiteOneCuratedPLPComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneCuratedPLPComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneCuratedPLPComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteOneCuratedPLPComponent.productCodes</code> attribute **/
	public static final String PRODUCTCODES = "productCodes";
	/** Qualifier of the <code>SiteOneCuratedPLPComponent.productsList</code> attribute **/
	public static final String PRODUCTSLIST = "productsList";
	/** Qualifier of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute **/
	public static final String MARKETINGBANNER = "marketingBanner";
	/** Qualifier of the <code>SiteOneCuratedPLPComponent.marketingBannerLink</code> attribute **/
	public static final String MARKETINGBANNERLINK = "marketingBannerLink";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(PRODUCTCODES, AttributeMode.INITIAL);
		tmp.put(PRODUCTSLIST, AttributeMode.INITIAL);
		tmp.put(MARKETINGBANNER, AttributeMode.INITIAL);
		tmp.put(MARKETINGBANNERLINK, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute.
	 * @return the marketingBanner - Marketing Banner
	 */
	public Media getMarketingBanner(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPComponent.getMarketingBanner requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, MARKETINGBANNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute.
	 * @return the marketingBanner - Marketing Banner
	 */
	public Media getMarketingBanner()
	{
		return getMarketingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute. 
	 * @return the localized marketingBanner - Marketing Banner
	 */
	public Map<Language,Media> getAllMarketingBanner(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,MARKETINGBANNER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute. 
	 * @return the localized marketingBanner - Marketing Banner
	 */
	public Map<Language,Media> getAllMarketingBanner()
	{
		return getAllMarketingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setMarketingBanner(final SessionContext ctx, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPComponent.setMarketingBanner requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MARKETINGBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setMarketingBanner(final Media value)
	{
		setMarketingBanner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setAllMarketingBanner(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,MARKETINGBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setAllMarketingBanner(final Map<Language,Media> value)
	{
		setAllMarketingBanner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.marketingBannerLink</code> attribute.
	 * @return the marketingBannerLink - Marketing Banner Link
	 */
	public String getMarketingBannerLink(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MARKETINGBANNERLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.marketingBannerLink</code> attribute.
	 * @return the marketingBannerLink - Marketing Banner Link
	 */
	public String getMarketingBannerLink()
	{
		return getMarketingBannerLink( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.marketingBannerLink</code> attribute. 
	 * @param value the marketingBannerLink - Marketing Banner Link
	 */
	public void setMarketingBannerLink(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MARKETINGBANNERLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.marketingBannerLink</code> attribute. 
	 * @param value the marketingBannerLink - Marketing Banner Link
	 */
	public void setMarketingBannerLink(final String value)
	{
		setMarketingBannerLink( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.productCodes</code> attribute.
	 * @return the productCodes
	 */
	public String getProductCodes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.productCodes</code> attribute.
	 * @return the productCodes
	 */
	public String getProductCodes()
	{
		return getProductCodes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.productCodes</code> attribute. 
	 * @param value the productCodes
	 */
	public void setProductCodes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.productCodes</code> attribute. 
	 * @param value the productCodes
	 */
	public void setProductCodes(final String value)
	{
		setProductCodes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.productsList</code> attribute.
	 * @return the productsList
	 */
	public String getProductsList(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTSLIST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.productsList</code> attribute.
	 * @return the productsList
	 */
	public String getProductsList()
	{
		return getProductsList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.productsList</code> attribute. 
	 * @param value the productsList
	 */
	public void setProductsList(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTSLIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.productsList</code> attribute. 
	 * @param value the productsList
	 */
	public void setProductsList(final String value)
	{
		setProductsList( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPComponent.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPComponent.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
}
