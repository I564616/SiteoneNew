/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteoneCuratedPLPNavNode;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.CuratedPLPVerticaNavNodeComponent CuratedPLPVerticaNavNodeComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCuratedPLPVerticaNavNodeComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>CuratedPLPVerticaNavNodeComponent.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>CuratedPLPVerticaNavNodeComponent.url</code> attribute **/
	public static final String URL = "url";
	/** Qualifier of the <code>CuratedPLPVerticaNavNodeComponent.navSubCategory</code> attribute **/
	public static final String NAVSUBCATEGORY = "navSubCategory";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(URL, AttributeMode.INITIAL);
		tmp.put(NAVSUBCATEGORY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CuratedPLPVerticaNavNodeComponent.code</code> attribute.
	 * @return the code - Indicates the event code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CuratedPLPVerticaNavNodeComponent.code</code> attribute.
	 * @return the code - Indicates the event code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CuratedPLPVerticaNavNodeComponent.code</code> attribute. 
	 * @param value the code - Indicates the event code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CuratedPLPVerticaNavNodeComponent.code</code> attribute. 
	 * @param value the code - Indicates the event code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CuratedPLPVerticaNavNodeComponent.navSubCategory</code> attribute.
	 * @return the navSubCategory - Indicates the sub category list
	 */
	public List<SiteoneCuratedPLPNavNode> getNavSubCategory(final SessionContext ctx)
	{
		List<SiteoneCuratedPLPNavNode> coll = (List<SiteoneCuratedPLPNavNode>)getProperty( ctx, NAVSUBCATEGORY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CuratedPLPVerticaNavNodeComponent.navSubCategory</code> attribute.
	 * @return the navSubCategory - Indicates the sub category list
	 */
	public List<SiteoneCuratedPLPNavNode> getNavSubCategory()
	{
		return getNavSubCategory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CuratedPLPVerticaNavNodeComponent.navSubCategory</code> attribute. 
	 * @param value the navSubCategory - Indicates the sub category list
	 */
	public void setNavSubCategory(final SessionContext ctx, final List<SiteoneCuratedPLPNavNode> value)
	{
		setProperty(ctx, NAVSUBCATEGORY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CuratedPLPVerticaNavNodeComponent.navSubCategory</code> attribute. 
	 * @param value the navSubCategory - Indicates the sub category list
	 */
	public void setNavSubCategory(final List<SiteoneCuratedPLPNavNode> value)
	{
		setNavSubCategory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CuratedPLPVerticaNavNodeComponent.url</code> attribute.
	 * @return the url - Indicates the event url
	 */
	public String getUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CuratedPLPVerticaNavNodeComponent.url</code> attribute.
	 * @return the url - Indicates the event url
	 */
	public String getUrl()
	{
		return getUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CuratedPLPVerticaNavNodeComponent.url</code> attribute. 
	 * @param value the url - Indicates the event url
	 */
	public void setUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CuratedPLPVerticaNavNodeComponent.url</code> attribute. 
	 * @param value the url - Indicates the event url
	 */
	public void setUrl(final String value)
	{
		setUrl( getSession().getSessionContext(), value );
	}
	
}
