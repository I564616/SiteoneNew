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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneShippingFees SiteoneShippingFees}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneShippingFees extends GenericItem
{
	/** Qualifier of the <code>SiteoneShippingFees.branchId</code> attribute **/
	public static final String BRANCHID = "branchId";
	/** Qualifier of the <code>SiteoneShippingFees.productSku</code> attribute **/
	public static final String PRODUCTSKU = "productSku";
	/** Qualifier of the <code>SiteoneShippingFees.shippingFee</code> attribute **/
	public static final String SHIPPINGFEE = "shippingFee";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(BRANCHID, AttributeMode.INITIAL);
		tmp.put(PRODUCTSKU, AttributeMode.INITIAL);
		tmp.put(SHIPPINGFEE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId()
	{
		return getBranchId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final String value)
	{
		setBranchId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.productSku</code> attribute.
	 * @return the productSku
	 */
	public String getProductSku(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTSKU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.productSku</code> attribute.
	 * @return the productSku
	 */
	public String getProductSku()
	{
		return getProductSku( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.productSku</code> attribute. 
	 * @param value the productSku
	 */
	public void setProductSku(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTSKU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.productSku</code> attribute. 
	 * @param value the productSku
	 */
	public void setProductSku(final String value)
	{
		setProductSku( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.shippingFee</code> attribute.
	 * @return the shippingFee
	 */
	public Double getShippingFee(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, SHIPPINGFEE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.shippingFee</code> attribute.
	 * @return the shippingFee
	 */
	public Double getShippingFee()
	{
		return getShippingFee( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.shippingFee</code> attribute. 
	 * @return the shippingFee
	 */
	public double getShippingFeeAsPrimitive(final SessionContext ctx)
	{
		Double value = getShippingFee( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneShippingFees.shippingFee</code> attribute. 
	 * @return the shippingFee
	 */
	public double getShippingFeeAsPrimitive()
	{
		return getShippingFeeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.shippingFee</code> attribute. 
	 * @param value the shippingFee
	 */
	public void setShippingFee(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, SHIPPINGFEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.shippingFee</code> attribute. 
	 * @param value the shippingFee
	 */
	public void setShippingFee(final Double value)
	{
		setShippingFee( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.shippingFee</code> attribute. 
	 * @param value the shippingFee
	 */
	public void setShippingFee(final SessionContext ctx, final double value)
	{
		setShippingFee( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneShippingFees.shippingFee</code> attribute. 
	 * @param value the shippingFee
	 */
	public void setShippingFee(final double value)
	{
		setShippingFee( getSession().getSessionContext(), value );
	}
	
}
