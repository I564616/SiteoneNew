/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 *  
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.b2b.punchout.jalo;

import de.hybris.platform.b2b.punchout.constants.SiteonepunchoutConstants;
import de.hybris.platform.b2b.punchout.jalo.StoredPunchOutSession;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>SiteonepunchoutManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteonepunchoutManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("key", AttributeMode.INITIAL);
		tmp.put("salt", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.b2b.punchout.jalo.StoredPunchOutSession", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	@Override
	public String getName()
	{
		return SiteonepunchoutConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoredPunchOutSession.key</code> attribute.
	 * @return the key
	 */
	public String getKey(final SessionContext ctx, final GenericItem item)
	{
		return (String)item.getProperty( ctx, SiteonepunchoutConstants.Attributes.StoredPunchOutSession.KEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoredPunchOutSession.key</code> attribute.
	 * @return the key
	 */
	public String getKey(final StoredPunchOutSession item)
	{
		return getKey( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoredPunchOutSession.key</code> attribute. 
	 * @param value the key
	 */
	public void setKey(final SessionContext ctx, final GenericItem item, final String value)
	{
		item.setProperty(ctx, SiteonepunchoutConstants.Attributes.StoredPunchOutSession.KEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoredPunchOutSession.key</code> attribute. 
	 * @param value the key
	 */
	public void setKey(final StoredPunchOutSession item, final String value)
	{
		setKey( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoredPunchOutSession.salt</code> attribute.
	 * @return the salt
	 */
	public String getSalt(final SessionContext ctx, final GenericItem item)
	{
		return (String)item.getProperty( ctx, SiteonepunchoutConstants.Attributes.StoredPunchOutSession.SALT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoredPunchOutSession.salt</code> attribute.
	 * @return the salt
	 */
	public String getSalt(final StoredPunchOutSession item)
	{
		return getSalt( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoredPunchOutSession.salt</code> attribute. 
	 * @param value the salt
	 */
	public void setSalt(final SessionContext ctx, final GenericItem item, final String value)
	{
		item.setProperty(ctx, SiteonepunchoutConstants.Attributes.StoredPunchOutSession.SALT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoredPunchOutSession.salt</code> attribute. 
	 * @param value the salt
	 */
	public void setSalt(final StoredPunchOutSession item, final String value)
	{
		setSalt( getSession().getSessionContext(), item, value );
	}
	
}
