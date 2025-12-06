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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneDeliveryFees SiteoneDeliveryFees}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneDeliveryFees extends GenericItem
{
	/** Qualifier of the <code>SiteoneDeliveryFees.branchId</code> attribute **/
	public static final String BRANCHID = "branchId";
	/** Qualifier of the <code>SiteoneDeliveryFees.lineOfBusiness</code> attribute **/
	public static final String LINEOFBUSINESS = "lineOfBusiness";
	/** Qualifier of the <code>SiteoneDeliveryFees.retailFee</code> attribute **/
	public static final String RETAILFEE = "retailFee";
	/** Qualifier of the <code>SiteoneDeliveryFees.proFee</code> attribute **/
	public static final String PROFEE = "proFee";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(BRANCHID, AttributeMode.INITIAL);
		tmp.put(LINEOFBUSINESS, AttributeMode.INITIAL);
		tmp.put(RETAILFEE, AttributeMode.INITIAL);
		tmp.put(PROFEE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId()
	{
		return getBranchId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final String value)
	{
		setBranchId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.lineOfBusiness</code> attribute.
	 * @return the lineOfBusiness
	 */
	public String getLineOfBusiness(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LINEOFBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.lineOfBusiness</code> attribute.
	 * @return the lineOfBusiness
	 */
	public String getLineOfBusiness()
	{
		return getLineOfBusiness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.lineOfBusiness</code> attribute. 
	 * @param value the lineOfBusiness
	 */
	public void setLineOfBusiness(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LINEOFBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.lineOfBusiness</code> attribute. 
	 * @param value the lineOfBusiness
	 */
	public void setLineOfBusiness(final String value)
	{
		setLineOfBusiness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.proFee</code> attribute.
	 * @return the proFee
	 */
	public Double getProFee(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PROFEE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.proFee</code> attribute.
	 * @return the proFee
	 */
	public Double getProFee()
	{
		return getProFee( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.proFee</code> attribute. 
	 * @return the proFee
	 */
	public double getProFeeAsPrimitive(final SessionContext ctx)
	{
		Double value = getProFee( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.proFee</code> attribute. 
	 * @return the proFee
	 */
	public double getProFeeAsPrimitive()
	{
		return getProFeeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.proFee</code> attribute. 
	 * @param value the proFee
	 */
	public void setProFee(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PROFEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.proFee</code> attribute. 
	 * @param value the proFee
	 */
	public void setProFee(final Double value)
	{
		setProFee( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.proFee</code> attribute. 
	 * @param value the proFee
	 */
	public void setProFee(final SessionContext ctx, final double value)
	{
		setProFee( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.proFee</code> attribute. 
	 * @param value the proFee
	 */
	public void setProFee(final double value)
	{
		setProFee( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.retailFee</code> attribute.
	 * @return the retailFee
	 */
	public Double getRetailFee(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, RETAILFEE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.retailFee</code> attribute.
	 * @return the retailFee
	 */
	public Double getRetailFee()
	{
		return getRetailFee( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.retailFee</code> attribute. 
	 * @return the retailFee
	 */
	public double getRetailFeeAsPrimitive(final SessionContext ctx)
	{
		Double value = getRetailFee( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneDeliveryFees.retailFee</code> attribute. 
	 * @return the retailFee
	 */
	public double getRetailFeeAsPrimitive()
	{
		return getRetailFeeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.retailFee</code> attribute. 
	 * @param value the retailFee
	 */
	public void setRetailFee(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, RETAILFEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.retailFee</code> attribute. 
	 * @param value the retailFee
	 */
	public void setRetailFee(final Double value)
	{
		setRetailFee( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.retailFee</code> attribute. 
	 * @param value the retailFee
	 */
	public void setRetailFee(final SessionContext ctx, final double value)
	{
		setRetailFee( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneDeliveryFees.retailFee</code> attribute. 
	 * @param value the retailFee
	 */
	public void setRetailFee(final double value)
	{
		setRetailFee( getSession().getSessionContext(), value );
	}
	
}
