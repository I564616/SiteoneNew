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
 * Generated class for type {@link com.siteone.core.jalo.SiteOneMarketingBannerComponent SiteOneMarketingBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneMarketingBannerComponent extends SimpleBannerComponent
{
	/** Qualifier of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute **/
	public static final String MARKETINGBANNER = "marketingBanner";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(MARKETINGBANNER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute.
	 * @return the marketingBanner - Marketing Banner
	 */
	public Media getMarketingBanner(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneMarketingBannerComponent.getMarketingBanner requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, MARKETINGBANNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute.
	 * @return the marketingBanner - Marketing Banner
	 */
	public Media getMarketingBanner()
	{
		return getMarketingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute. 
	 * @return the localized marketingBanner - Marketing Banner
	 */
	public Map<Language,Media> getAllMarketingBanner(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,MARKETINGBANNER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute. 
	 * @return the localized marketingBanner - Marketing Banner
	 */
	public Map<Language,Media> getAllMarketingBanner()
	{
		return getAllMarketingBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneMarketingBannerComponent.setMarketingBanner requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MARKETINGBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setMarketingBanner(final Media value)
	{
		setMarketingBanner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setAllMarketingBanner(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,MARKETINGBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneMarketingBannerComponent.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setAllMarketingBanner(final Map<Language,Media> value)
	{
		setAllMarketingBanner( getSession().getSessionContext(), value );
	}
	
}
