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
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneContrPrimaryBusiness SiteOneContrPrimaryBusiness}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneContrPrimaryBusiness extends GenericItem
{
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1</code> attribute **/
	public static final String PRIMARYBUSINESSL1 = "PrimaryBusinessL1";
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2</code> attribute **/
	public static final String PRIMARYBUSINESSL2 = "primaryBusinessL2";
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1_ES</code> attribute **/
	public static final String PRIMARYBUSINESSL1_ES = "PrimaryBusinessL1_ES";
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2_ES</code> attribute **/
	public static final String PRIMARYBUSINESSL2_ES = "primaryBusinessL2_ES";
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.parentId</code> attribute **/
	public static final String PARENTID = "parentId";
	/** Qualifier of the <code>SiteOneContrPrimaryBusiness.active</code> attribute **/
	public static final String ACTIVE = "active";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PRIMARYBUSINESSL1, AttributeMode.INITIAL);
		tmp.put(PRIMARYBUSINESSL2, AttributeMode.INITIAL);
		tmp.put(PRIMARYBUSINESSL1_ES, AttributeMode.INITIAL);
		tmp.put(PRIMARYBUSINESSL2_ES, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(PARENTID, AttributeMode.INITIAL);
		tmp.put(ACTIVE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute.
	 * @return the active
	 */
	public Boolean isActive(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ACTIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute.
	 * @return the active
	 */
	public Boolean isActive()
	{
		return isActive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute. 
	 * @return the active
	 */
	public boolean isActiveAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isActive( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute. 
	 * @return the active
	 */
	public boolean isActiveAsPrimitive()
	{
		return isActiveAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ACTIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final Boolean value)
	{
		setActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final SessionContext ctx, final boolean value)
	{
		setActive( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final boolean value)
	{
		setActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.parentId</code> attribute.
	 * @return the parentId
	 */
	public String getParentId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PARENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.parentId</code> attribute.
	 * @return the parentId
	 */
	public String getParentId()
	{
		return getParentId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.parentId</code> attribute. 
	 * @param value the parentId
	 */
	public void setParentId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PARENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.parentId</code> attribute. 
	 * @param value the parentId
	 */
	public void setParentId(final String value)
	{
		setParentId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1</code> attribute.
	 * @return the PrimaryBusinessL1
	 */
	public String getPrimaryBusinessL1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRIMARYBUSINESSL1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1</code> attribute.
	 * @return the PrimaryBusinessL1
	 */
	public String getPrimaryBusinessL1()
	{
		return getPrimaryBusinessL1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1</code> attribute. 
	 * @param value the PrimaryBusinessL1
	 */
	public void setPrimaryBusinessL1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRIMARYBUSINESSL1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1</code> attribute. 
	 * @param value the PrimaryBusinessL1
	 */
	public void setPrimaryBusinessL1(final String value)
	{
		setPrimaryBusinessL1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1_ES</code> attribute.
	 * @return the PrimaryBusinessL1_ES
	 */
	public String getPrimaryBusinessL1_ES(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRIMARYBUSINESSL1_ES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1_ES</code> attribute.
	 * @return the PrimaryBusinessL1_ES
	 */
	public String getPrimaryBusinessL1_ES()
	{
		return getPrimaryBusinessL1_ES( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1_ES</code> attribute. 
	 * @param value the PrimaryBusinessL1_ES
	 */
	public void setPrimaryBusinessL1_ES(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRIMARYBUSINESSL1_ES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.PrimaryBusinessL1_ES</code> attribute. 
	 * @param value the PrimaryBusinessL1_ES
	 */
	public void setPrimaryBusinessL1_ES(final String value)
	{
		setPrimaryBusinessL1_ES( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2</code> attribute.
	 * @return the primaryBusinessL2
	 */
	public List<String> getPrimaryBusinessL2(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, PRIMARYBUSINESSL2);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2</code> attribute.
	 * @return the primaryBusinessL2
	 */
	public List<String> getPrimaryBusinessL2()
	{
		return getPrimaryBusinessL2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2</code> attribute. 
	 * @param value the primaryBusinessL2
	 */
	public void setPrimaryBusinessL2(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, PRIMARYBUSINESSL2,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2</code> attribute. 
	 * @param value the primaryBusinessL2
	 */
	public void setPrimaryBusinessL2(final List<String> value)
	{
		setPrimaryBusinessL2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2_ES</code> attribute.
	 * @return the primaryBusinessL2_ES
	 */
	public List<String> getPrimaryBusinessL2_ES(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, PRIMARYBUSINESSL2_ES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2_ES</code> attribute.
	 * @return the primaryBusinessL2_ES
	 */
	public List<String> getPrimaryBusinessL2_ES()
	{
		return getPrimaryBusinessL2_ES( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2_ES</code> attribute. 
	 * @param value the primaryBusinessL2_ES
	 */
	public void setPrimaryBusinessL2_ES(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, PRIMARYBUSINESSL2_ES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneContrPrimaryBusiness.primaryBusinessL2_ES</code> attribute. 
	 * @param value the primaryBusinessL2_ES
	 */
	public void setPrimaryBusinessL2_ES(final List<String> value)
	{
		setPrimaryBusinessL2_ES( getSession().getSessionContext(), value );
	}
	
}
