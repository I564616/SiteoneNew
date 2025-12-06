/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneBottomBannerComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneBottomBannerListComponent SiteOneBottomBannerListComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneBottomBannerListComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneBottomBannerListComponent.bannersList</code> attribute **/
	public static final String BANNERSLIST = "bannersList";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(BANNERSLIST, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneBottomBannerListComponent.bannersList</code> attribute.
	 * @return the bannersList
	 */
	public Set<SiteOneBottomBannerComponent> getBannersList(final SessionContext ctx)
	{
		Set<SiteOneBottomBannerComponent> coll = (Set<SiteOneBottomBannerComponent>)getProperty( ctx, BANNERSLIST);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneBottomBannerListComponent.bannersList</code> attribute.
	 * @return the bannersList
	 */
	public Set<SiteOneBottomBannerComponent> getBannersList()
	{
		return getBannersList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneBottomBannerListComponent.bannersList</code> attribute. 
	 * @param value the bannersList
	 */
	public void setBannersList(final SessionContext ctx, final Set<SiteOneBottomBannerComponent> value)
	{
		setProperty(ctx, BANNERSLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneBottomBannerListComponent.bannersList</code> attribute. 
	 * @param value the bannersList
	 */
	public void setBannersList(final Set<SiteOneBottomBannerComponent> value)
	{
		setBannersList( getSession().getSessionContext(), value );
	}
	
}
