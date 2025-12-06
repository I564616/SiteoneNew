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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneCADeliveryFees SiteoneCADeliveryFees}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneCADeliveryFees extends GenericItem
{
	/** Qualifier of the <code>SiteoneCADeliveryFees.branchId</code> attribute **/
	public static final String BRANCHID = "branchId";
	/** Qualifier of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute **/
	public static final String MINQTYTHRESHOLD = "minQtyThreshold";
	/** Qualifier of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute **/
	public static final String MAXQTYTHRESHOLD = "maxQtyThreshold";
	/** Qualifier of the <code>SiteoneCADeliveryFees.uom</code> attribute **/
	public static final String UOM = "uom";
	/** Qualifier of the <code>SiteoneCADeliveryFees.fee</code> attribute **/
	public static final String FEE = "fee";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(BRANCHID, AttributeMode.INITIAL);
		tmp.put(MINQTYTHRESHOLD, AttributeMode.INITIAL);
		tmp.put(MAXQTYTHRESHOLD, AttributeMode.INITIAL);
		tmp.put(UOM, AttributeMode.INITIAL);
		tmp.put(FEE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId()
	{
		return getBranchId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final String value)
	{
		setBranchId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.fee</code> attribute.
	 * @return the fee
	 */
	public Double getFee(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, FEE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.fee</code> attribute.
	 * @return the fee
	 */
	public Double getFee()
	{
		return getFee( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.fee</code> attribute. 
	 * @return the fee
	 */
	public double getFeeAsPrimitive(final SessionContext ctx)
	{
		Double value = getFee( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.fee</code> attribute. 
	 * @return the fee
	 */
	public double getFeeAsPrimitive()
	{
		return getFeeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.fee</code> attribute. 
	 * @param value the fee
	 */
	public void setFee(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, FEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.fee</code> attribute. 
	 * @param value the fee
	 */
	public void setFee(final Double value)
	{
		setFee( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.fee</code> attribute. 
	 * @param value the fee
	 */
	public void setFee(final SessionContext ctx, final double value)
	{
		setFee( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.fee</code> attribute. 
	 * @param value the fee
	 */
	public void setFee(final double value)
	{
		setFee( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute.
	 * @return the maxQtyThreshold
	 */
	public Long getMaxQtyThreshold(final SessionContext ctx)
	{
		return (Long)getProperty( ctx, MAXQTYTHRESHOLD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute.
	 * @return the maxQtyThreshold
	 */
	public Long getMaxQtyThreshold()
	{
		return getMaxQtyThreshold( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute. 
	 * @return the maxQtyThreshold
	 */
	public long getMaxQtyThresholdAsPrimitive(final SessionContext ctx)
	{
		Long value = getMaxQtyThreshold( ctx );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute. 
	 * @return the maxQtyThreshold
	 */
	public long getMaxQtyThresholdAsPrimitive()
	{
		return getMaxQtyThresholdAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute. 
	 * @param value the maxQtyThreshold
	 */
	public void setMaxQtyThreshold(final SessionContext ctx, final Long value)
	{
		setProperty(ctx, MAXQTYTHRESHOLD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute. 
	 * @param value the maxQtyThreshold
	 */
	public void setMaxQtyThreshold(final Long value)
	{
		setMaxQtyThreshold( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute. 
	 * @param value the maxQtyThreshold
	 */
	public void setMaxQtyThreshold(final SessionContext ctx, final long value)
	{
		setMaxQtyThreshold( ctx,Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.maxQtyThreshold</code> attribute. 
	 * @param value the maxQtyThreshold
	 */
	public void setMaxQtyThreshold(final long value)
	{
		setMaxQtyThreshold( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute.
	 * @return the minQtyThreshold
	 */
	public Long getMinQtyThreshold(final SessionContext ctx)
	{
		return (Long)getProperty( ctx, MINQTYTHRESHOLD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute.
	 * @return the minQtyThreshold
	 */
	public Long getMinQtyThreshold()
	{
		return getMinQtyThreshold( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute. 
	 * @return the minQtyThreshold
	 */
	public long getMinQtyThresholdAsPrimitive(final SessionContext ctx)
	{
		Long value = getMinQtyThreshold( ctx );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute. 
	 * @return the minQtyThreshold
	 */
	public long getMinQtyThresholdAsPrimitive()
	{
		return getMinQtyThresholdAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute. 
	 * @param value the minQtyThreshold
	 */
	public void setMinQtyThreshold(final SessionContext ctx, final Long value)
	{
		setProperty(ctx, MINQTYTHRESHOLD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute. 
	 * @param value the minQtyThreshold
	 */
	public void setMinQtyThreshold(final Long value)
	{
		setMinQtyThreshold( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute. 
	 * @param value the minQtyThreshold
	 */
	public void setMinQtyThreshold(final SessionContext ctx, final long value)
	{
		setMinQtyThreshold( ctx,Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.minQtyThreshold</code> attribute. 
	 * @param value the minQtyThreshold
	 */
	public void setMinQtyThreshold(final long value)
	{
		setMinQtyThreshold( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.uom</code> attribute.
	 * @return the uom
	 */
	public String getUom(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCADeliveryFees.uom</code> attribute.
	 * @return the uom
	 */
	public String getUom()
	{
		return getUom( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.uom</code> attribute. 
	 * @param value the uom
	 */
	public void setUom(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCADeliveryFees.uom</code> attribute. 
	 * @param value the uom
	 */
	public void setUom(final String value)
	{
		setUom( getSession().getSessionContext(), value );
	}
	
}
