/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.personalizationservices.jalo.CxSegment;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneTradeClassSegment SiteOneTradeClassSegment}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneTradeClassSegment extends CxSegment
{
	/** Qualifier of the <code>SiteOneTradeClassSegment.subTradeClassCode</code> attribute **/
	public static final String SUBTRADECLASSCODE = "subTradeClassCode";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CxSegment.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(SUBTRADECLASSCODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTradeClassSegment.subTradeClassCode</code> attribute.
	 * @return the subTradeClassCode
	 */
	public String getSubTradeClassCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SUBTRADECLASSCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTradeClassSegment.subTradeClassCode</code> attribute.
	 * @return the subTradeClassCode
	 */
	public String getSubTradeClassCode()
	{
		return getSubTradeClassCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTradeClassSegment.subTradeClassCode</code> attribute. 
	 * @param value the subTradeClassCode
	 */
	public void setSubTradeClassCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SUBTRADECLASSCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTradeClassSegment.subTradeClassCode</code> attribute. 
	 * @param value the subTradeClassCode
	 */
	public void setSubTradeClassCode(final String value)
	{
		setSubTradeClassCode( getSession().getSessionContext(), value );
	}
	
}
