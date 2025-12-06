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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem SiteonePricingTrack}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteonePricingTrack extends GenericItem
{
	/** Qualifier of the <code>SiteonePricingTrack.customerUnit</code> attribute **/
	public static final String CUSTOMERUNIT = "customerUnit";
	/** Qualifier of the <code>SiteonePricingTrack.branchId</code> attribute **/
	public static final String BRANCHID = "branchId";
	/** Qualifier of the <code>SiteonePricingTrack.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	/** Qualifier of the <code>SiteonePricingTrack.retailPrice</code> attribute **/
	public static final String RETAILPRICE = "retailPrice";
	/** Qualifier of the <code>SiteonePricingTrack.cspPrice</code> attribute **/
	public static final String CSPPRICE = "cspPrice";
	/** Qualifier of the <code>SiteonePricingTrack.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>SiteonePricingTrack.timeStamp</code> attribute **/
	public static final String TIMESTAMP = "timeStamp";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CUSTOMERUNIT, AttributeMode.INITIAL);
		tmp.put(BRANCHID, AttributeMode.INITIAL);
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		tmp.put(RETAILPRICE, AttributeMode.INITIAL);
		tmp.put(CSPPRICE, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(TIMESTAMP, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.branchId</code> attribute.
	 * @return the branchId
	 */
	public String getBranchId()
	{
		return getBranchId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.branchId</code> attribute. 
	 * @param value the branchId
	 */
	public void setBranchId(final String value)
	{
		setBranchId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.cspPrice</code> attribute.
	 * @return the cspPrice
	 */
	public String getCspPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CSPPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.cspPrice</code> attribute.
	 * @return the cspPrice
	 */
	public String getCspPrice()
	{
		return getCspPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.cspPrice</code> attribute. 
	 * @param value the cspPrice
	 */
	public void setCspPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CSPPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.cspPrice</code> attribute. 
	 * @param value the cspPrice
	 */
	public void setCspPrice(final String value)
	{
		setCspPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.customerUnit</code> attribute.
	 * @return the customerUnit
	 */
	public String getCustomerUnit(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERUNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.customerUnit</code> attribute.
	 * @return the customerUnit
	 */
	public String getCustomerUnit()
	{
		return getCustomerUnit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.customerUnit</code> attribute. 
	 * @param value the customerUnit
	 */
	public void setCustomerUnit(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERUNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.customerUnit</code> attribute. 
	 * @param value the customerUnit
	 */
	public void setCustomerUnit(final String value)
	{
		setCustomerUnit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return getProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final String value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.retailPrice</code> attribute.
	 * @return the retailPrice
	 */
	public String getRetailPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RETAILPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.retailPrice</code> attribute.
	 * @return the retailPrice
	 */
	public String getRetailPrice()
	{
		return getRetailPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.retailPrice</code> attribute. 
	 * @param value the retailPrice
	 */
	public void setRetailPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RETAILPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.retailPrice</code> attribute. 
	 * @param value the retailPrice
	 */
	public void setRetailPrice(final String value)
	{
		setRetailPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.timeStamp</code> attribute.
	 * @return the timeStamp
	 */
	public Date getTimeStamp(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, TIMESTAMP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePricingTrack.timeStamp</code> attribute.
	 * @return the timeStamp
	 */
	public Date getTimeStamp()
	{
		return getTimeStamp( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.timeStamp</code> attribute. 
	 * @param value the timeStamp
	 */
	public void setTimeStamp(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, TIMESTAMP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePricingTrack.timeStamp</code> attribute. 
	 * @param value the timeStamp
	 */
	public void setTimeStamp(final Date value)
	{
		setTimeStamp( getSession().getSessionContext(), value );
	}
	
}
