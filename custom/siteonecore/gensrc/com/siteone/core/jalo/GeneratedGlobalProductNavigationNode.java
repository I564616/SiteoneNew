/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.cms2.jalo.navigation.CMSNavigationNode;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.GlobalProductNavigationNode GlobalProductNavigationNode}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedGlobalProductNavigationNode extends CMSNavigationNode
{
	/** Qualifier of the <code>GlobalProductNavigationNode.category</code> attribute **/
	public static final String CATEGORY = "category";
	/** Qualifier of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute **/
	public static final String SEQUENCENUMBER = "sequenceNumber";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CMSNavigationNode.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CATEGORY, AttributeMode.INITIAL);
		tmp.put(SEQUENCENUMBER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalProductNavigationNode.category</code> attribute.
	 * @return the category
	 */
	public Category getCategory(final SessionContext ctx)
	{
		return (Category)getProperty( ctx, CATEGORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalProductNavigationNode.category</code> attribute.
	 * @return the category
	 */
	public Category getCategory()
	{
		return getCategory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalProductNavigationNode.category</code> attribute. 
	 * @param value the category
	 */
	public void setCategory(final SessionContext ctx, final Category value)
	{
		setProperty(ctx, CATEGORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalProductNavigationNode.category</code> attribute. 
	 * @param value the category
	 */
	public void setCategory(final Category value)
	{
		setCategory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute.
	 * @return the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public Integer getSequenceNumber(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, SEQUENCENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute.
	 * @return the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public Integer getSequenceNumber()
	{
		return getSequenceNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute. 
	 * @return the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public int getSequenceNumberAsPrimitive(final SessionContext ctx)
	{
		Integer value = getSequenceNumber( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute. 
	 * @return the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public int getSequenceNumberAsPrimitive()
	{
		return getSequenceNumberAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute. 
	 * @param value the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public void setSequenceNumber(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, SEQUENCENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute. 
	 * @param value the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public void setSequenceNumber(final Integer value)
	{
		setSequenceNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute. 
	 * @param value the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public void setSequenceNumber(final SessionContext ctx, final int value)
	{
		setSequenceNumber( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalProductNavigationNode.sequenceNumber</code> attribute. 
	 * @param value the sequenceNumber - Sequence number to display category in alphabetical order
	 */
	public void setSequenceNumber(final int value)
	{
		setSequenceNumber( getSession().getSessionContext(), value );
	}
	
}
