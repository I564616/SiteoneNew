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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneLeftNavigationComponent SiteoneLeftNavigationComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneLeftNavigationComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute **/
	public static final String LINKNAME = "linkName";
	/** Qualifier of the <code>SiteoneLeftNavigationComponent.linkUrl</code> attribute **/
	public static final String LINKURL = "linkUrl";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LINKNAME, AttributeMode.INITIAL);
		tmp.put(LINKURL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute.
	 * @return the linkName
	 */
	public String getLinkName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneLeftNavigationComponent.getLinkName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, LINKNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute.
	 * @return the linkName
	 */
	public String getLinkName()
	{
		return getLinkName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute. 
	 * @return the localized linkName
	 */
	public Map<Language,String> getAllLinkName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,LINKNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute. 
	 * @return the localized linkName
	 */
	public Map<Language,String> getAllLinkName()
	{
		return getAllLinkName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute. 
	 * @param value the linkName
	 */
	public void setLinkName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneLeftNavigationComponent.setLinkName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, LINKNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute. 
	 * @param value the linkName
	 */
	public void setLinkName(final String value)
	{
		setLinkName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute. 
	 * @param value the linkName
	 */
	public void setAllLinkName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,LINKNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneLeftNavigationComponent.linkName</code> attribute. 
	 * @param value the linkName
	 */
	public void setAllLinkName(final Map<Language,String> value)
	{
		setAllLinkName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneLeftNavigationComponent.linkUrl</code> attribute.
	 * @return the linkUrl
	 */
	public String getLinkUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LINKURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneLeftNavigationComponent.linkUrl</code> attribute.
	 * @return the linkUrl
	 */
	public String getLinkUrl()
	{
		return getLinkUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneLeftNavigationComponent.linkUrl</code> attribute. 
	 * @param value the linkUrl
	 */
	public void setLinkUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LINKURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneLeftNavigationComponent.linkUrl</code> attribute. 
	 * @param value the linkUrl
	 */
	public void setLinkUrl(final String value)
	{
		setLinkUrl( getSession().getSessionContext(), value );
	}
	
}
