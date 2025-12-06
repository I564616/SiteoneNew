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
import de.hybris.platform.jalo.c2l.Region;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.RegulatoryStates RegulatoryStates}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedRegulatoryStates extends GenericItem
{
	/** Qualifier of the <code>RegulatoryStates.sku</code> attribute **/
	public static final String SKU = "sku";
	/** Qualifier of the <code>RegulatoryStates.state</code> attribute **/
	public static final String STATE = "state";
	/** Qualifier of the <code>RegulatoryStates.expirationDate</code> attribute **/
	public static final String EXPIRATIONDATE = "expirationDate";
	/** Qualifier of the <code>RegulatoryStates.rup</code> attribute **/
	public static final String RUP = "rup";
	/** Qualifier of the <code>RegulatoryStates.isRup</code> attribute **/
	public static final String ISRUP = "isRup";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(SKU, AttributeMode.INITIAL);
		tmp.put(STATE, AttributeMode.INITIAL);
		tmp.put(EXPIRATIONDATE, AttributeMode.INITIAL);
		tmp.put(RUP, AttributeMode.INITIAL);
		tmp.put(ISRUP, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.expirationDate</code> attribute.
	 * @return the expirationDate
	 */
	public Date getExpirationDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, EXPIRATIONDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.expirationDate</code> attribute.
	 * @return the expirationDate
	 */
	public Date getExpirationDate()
	{
		return getExpirationDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.expirationDate</code> attribute. 
	 * @param value the expirationDate
	 */
	public void setExpirationDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, EXPIRATIONDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.expirationDate</code> attribute. 
	 * @param value the expirationDate
	 */
	public void setExpirationDate(final Date value)
	{
		setExpirationDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.isRup</code> attribute.
	 * @return the isRup
	 */
	public Boolean isIsRup(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISRUP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.isRup</code> attribute.
	 * @return the isRup
	 */
	public Boolean isIsRup()
	{
		return isIsRup( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.isRup</code> attribute. 
	 * @return the isRup
	 */
	public boolean isIsRupAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsRup( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.isRup</code> attribute. 
	 * @return the isRup
	 */
	public boolean isIsRupAsPrimitive()
	{
		return isIsRupAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.isRup</code> attribute. 
	 * @param value the isRup
	 */
	public void setIsRup(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISRUP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.isRup</code> attribute. 
	 * @param value the isRup
	 */
	public void setIsRup(final Boolean value)
	{
		setIsRup( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.isRup</code> attribute. 
	 * @param value the isRup
	 */
	public void setIsRup(final SessionContext ctx, final boolean value)
	{
		setIsRup( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.isRup</code> attribute. 
	 * @param value the isRup
	 */
	public void setIsRup(final boolean value)
	{
		setIsRup( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.rup</code> attribute.
	 * @return the rup
	 */
	public String getRup(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RUP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.rup</code> attribute.
	 * @return the rup
	 */
	public String getRup()
	{
		return getRup( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.rup</code> attribute. 
	 * @param value the rup
	 */
	public void setRup(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RUP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.rup</code> attribute. 
	 * @param value the rup
	 */
	public void setRup(final String value)
	{
		setRup( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.sku</code> attribute.
	 * @return the sku
	 */
	public String getSku(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SKU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.sku</code> attribute.
	 * @return the sku
	 */
	public String getSku()
	{
		return getSku( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.sku</code> attribute. 
	 * @param value the sku
	 */
	public void setSku(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SKU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.sku</code> attribute. 
	 * @param value the sku
	 */
	public void setSku(final String value)
	{
		setSku( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.state</code> attribute.
	 * @return the state
	 */
	public Region getState(final SessionContext ctx)
	{
		return (Region)getProperty( ctx, STATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RegulatoryStates.state</code> attribute.
	 * @return the state
	 */
	public Region getState()
	{
		return getState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final SessionContext ctx, final Region value)
	{
		setProperty(ctx, STATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RegulatoryStates.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final Region value)
	{
		setState( getSession().getSessionContext(), value );
	}
	
}
