/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem SiteOneFeatureSwitch}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneFeatureSwitch extends GenericItem
{
	/** Qualifier of the <code>SiteOneFeatureSwitch.key</code> attribute **/
	public static final String KEY = "key";
	/** Qualifier of the <code>SiteOneFeatureSwitch.value</code> attribute **/
	public static final String VALUE = "value";
	/** Qualifier of the <code>SiteOneFeatureSwitch.longValue</code> attribute **/
	public static final String LONGVALUE = "longValue";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(KEY, AttributeMode.INITIAL);
		tmp.put(VALUE, AttributeMode.INITIAL);
		tmp.put(LONGVALUE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeatureSwitch.key</code> attribute.
	 * @return the key
	 */
	public String getKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, KEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeatureSwitch.key</code> attribute.
	 * @return the key
	 */
	public String getKey()
	{
		return getKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeatureSwitch.key</code> attribute. 
	 * @param value the key
	 */
	public void setKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, KEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeatureSwitch.key</code> attribute. 
	 * @param value the key
	 */
	public void setKey(final String value)
	{
		setKey( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeatureSwitch.longValue</code> attribute.
	 * @return the longValue
	 */
	public String getLongValue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LONGVALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeatureSwitch.longValue</code> attribute.
	 * @return the longValue
	 */
	public String getLongValue()
	{
		return getLongValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeatureSwitch.longValue</code> attribute. 
	 * @param value the longValue
	 */
	public void setLongValue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LONGVALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeatureSwitch.longValue</code> attribute. 
	 * @param value the longValue
	 */
	public void setLongValue(final String value)
	{
		setLongValue( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeatureSwitch.value</code> attribute.
	 * @return the value
	 */
	public String getValue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeatureSwitch.value</code> attribute.
	 * @return the value
	 */
	public String getValue()
	{
		return getValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeatureSwitch.value</code> attribute. 
	 * @param value the value
	 */
	public void setValue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeatureSwitch.value</code> attribute. 
	 * @param value the value
	 */
	public void setValue(final String value)
	{
		setValue( getSession().getSessionContext(), value );
	}
	
}
