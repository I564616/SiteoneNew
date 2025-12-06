/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type SiteOneProductTransformation.
 */
@SLDSafe
@SuppressWarnings({"unused","cast"})
public class SiteOneProductTransformation extends GenericItem
{
	/** Qualifier of the <code>SiteOneProductTransformation.url</code> attribute **/
	public static final String URL = "url";
	/** Qualifier of the <code>SiteOneProductTransformation.format</code> attribute **/
	public static final String FORMAT = "format";
	/** Qualifier of the <code>SiteOneProductTransformation.code</code> attribute **/
	public static final String CODE = "code";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(URL, AttributeMode.INITIAL);
		tmp.put(FORMAT, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductTransformation.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "code".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductTransformation.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductTransformation.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "code".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductTransformation.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductTransformation.format</code> attribute.
	 * @return the format
	 */
	public String getFormat(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "format".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductTransformation.format</code> attribute.
	 * @return the format
	 */
	public String getFormat()
	{
		return getFormat( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductTransformation.format</code> attribute. 
	 * @param value the format
	 */
	public void setFormat(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "format".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductTransformation.format</code> attribute. 
	 * @param value the format
	 */
	public void setFormat(final String value)
	{
		setFormat( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductTransformation.url</code> attribute.
	 * @return the url
	 */
	public String getUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "url".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductTransformation.url</code> attribute.
	 * @return the url
	 */
	public String getUrl()
	{
		return getUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductTransformation.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "url".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductTransformation.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final String value)
	{
		setUrl( getSession().getSessionContext(), value );
	}
	
}
