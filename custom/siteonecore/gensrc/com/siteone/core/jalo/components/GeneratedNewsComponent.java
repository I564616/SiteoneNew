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
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.components.NewsComponent NewsComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedNewsComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>NewsComponent.newsList</code> attribute **/
	public static final String NEWSLIST = "newsList";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(NEWSLIST, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NewsComponent.newsList</code> attribute.
	 * @return the newsList
	 */
	public List<SiteOneNews> getNewsList(final SessionContext ctx)
	{
		List<SiteOneNews> coll = (List<SiteOneNews>)getProperty( ctx, NEWSLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>NewsComponent.newsList</code> attribute.
	 * @return the newsList
	 */
	public List<SiteOneNews> getNewsList()
	{
		return getNewsList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NewsComponent.newsList</code> attribute. 
	 * @param value the newsList
	 */
	public void setNewsList(final SessionContext ctx, final List<SiteOneNews> value)
	{
		setProperty(ctx, NEWSLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>NewsComponent.newsList</code> attribute. 
	 * @param value the newsList
	 */
	public void setNewsList(final List<SiteOneNews> value)
	{
		setNewsList( getSession().getSessionContext(), value );
	}
	
}
