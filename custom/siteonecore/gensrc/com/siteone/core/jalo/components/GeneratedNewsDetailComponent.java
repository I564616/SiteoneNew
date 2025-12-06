/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo.components;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneNews;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.components.NewsDetailComponent NewsDetailComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedNewsDetailComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>NewsDetailComponent.news</code> attribute **/
	public static final String NEWS = "news";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(NEWS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NewsDetailComponent.news</code> attribute.
	 * @return the news - Localized news Content/Brief of the  News component.
	 */
	public SiteOneNews getNews(final SessionContext ctx)
	{
		return (SiteOneNews)getProperty( ctx, NEWS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NewsDetailComponent.news</code> attribute.
	 * @return the news - Localized news Content/Brief of the  News component.
	 */
	public SiteOneNews getNews()
	{
		return getNews( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NewsDetailComponent.news</code> attribute. 
	 * @param value the news - Localized news Content/Brief of the  News component.
	 */
	public void setNews(final SessionContext ctx, final SiteOneNews value)
	{
		setProperty(ctx, NEWS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NewsDetailComponent.news</code> attribute. 
	 * @param value the news - Localized news Content/Brief of the  News component.
	 */
	public void setNews(final SiteOneNews value)
	{
		setNews( getSession().getSessionContext(), value );
	}
	
}
