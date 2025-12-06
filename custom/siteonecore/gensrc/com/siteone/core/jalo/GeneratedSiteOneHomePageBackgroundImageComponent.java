/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneHomePageTextBannerComponent;
import de.hybris.platform.acceleratorcms.jalo.components.SimpleBannerComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneHomePageBackgroundImageComponent SiteOneHomePageBackgroundImageComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneHomePageBackgroundImageComponent extends SimpleBannerComponent
{
	/** Qualifier of the <code>SiteOneHomePageBackgroundImageComponent.bannerList</code> attribute **/
	public static final String BANNERLIST = "bannerList";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(BANNERLIST, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageBackgroundImageComponent.bannerList</code> attribute.
	 * @return the bannerList
	 */
	public List<SiteOneHomePageTextBannerComponent> getBannerList(final SessionContext ctx)
	{
		List<SiteOneHomePageTextBannerComponent> coll = (List<SiteOneHomePageTextBannerComponent>)getProperty( ctx, BANNERLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageBackgroundImageComponent.bannerList</code> attribute.
	 * @return the bannerList
	 */
	public List<SiteOneHomePageTextBannerComponent> getBannerList()
	{
		return getBannerList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageBackgroundImageComponent.bannerList</code> attribute. 
	 * @param value the bannerList
	 */
	public void setBannerList(final SessionContext ctx, final List<SiteOneHomePageTextBannerComponent> value)
	{
		setProperty(ctx, BANNERLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageBackgroundImageComponent.bannerList</code> attribute. 
	 * @param value the bannerList
	 */
	public void setBannerList(final List<SiteOneHomePageTextBannerComponent> value)
	{
		setBannerList( getSession().getSessionContext(), value );
	}
	
}
