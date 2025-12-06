/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.HomePageToolsComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneToolsListComponent SiteOneToolsListComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneToolsListComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneToolsListComponent.toolsList</code> attribute **/
	public static final String TOOLSLIST = "toolsList";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TOOLSLIST, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneToolsListComponent.toolsList</code> attribute.
	 * @return the toolsList
	 */
	public List<HomePageToolsComponent> getToolsList(final SessionContext ctx)
	{
		List<HomePageToolsComponent> coll = (List<HomePageToolsComponent>)getProperty( ctx, TOOLSLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneToolsListComponent.toolsList</code> attribute.
	 * @return the toolsList
	 */
	public List<HomePageToolsComponent> getToolsList()
	{
		return getToolsList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneToolsListComponent.toolsList</code> attribute. 
	 * @param value the toolsList
	 */
	public void setToolsList(final SessionContext ctx, final List<HomePageToolsComponent> value)
	{
		setProperty(ctx, TOOLSLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneToolsListComponent.toolsList</code> attribute. 
	 * @param value the toolsList
	 */
	public void setToolsList(final List<HomePageToolsComponent> value)
	{
		setToolsList( getSession().getSessionContext(), value );
	}
	
}
