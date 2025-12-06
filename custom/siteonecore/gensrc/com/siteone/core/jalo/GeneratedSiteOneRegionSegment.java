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
 * Generated class for type {@link com.siteone.core.jalo.SiteOneRegionSegment SiteOneRegionSegment}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneRegionSegment extends CxSegment
{
	/** Qualifier of the <code>SiteOneRegionSegment.region</code> attribute **/
	public static final String REGION = "region";
	/** Qualifier of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute **/
	public static final String ISANONYMOUSCUSTOMER = "isAnonymousCustomer";
	/** Qualifier of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute **/
	public static final String CHECKONLYREGION = "checkOnlyRegion";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CxSegment.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(REGION, AttributeMode.INITIAL);
		tmp.put(ISANONYMOUSCUSTOMER, AttributeMode.INITIAL);
		tmp.put(CHECKONLYREGION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute.
	 * @return the checkOnlyRegion
	 */
	public Boolean isCheckOnlyRegion(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, CHECKONLYREGION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute.
	 * @return the checkOnlyRegion
	 */
	public Boolean isCheckOnlyRegion()
	{
		return isCheckOnlyRegion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute. 
	 * @return the checkOnlyRegion
	 */
	public boolean isCheckOnlyRegionAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isCheckOnlyRegion( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute. 
	 * @return the checkOnlyRegion
	 */
	public boolean isCheckOnlyRegionAsPrimitive()
	{
		return isCheckOnlyRegionAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute. 
	 * @param value the checkOnlyRegion
	 */
	public void setCheckOnlyRegion(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, CHECKONLYREGION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute. 
	 * @param value the checkOnlyRegion
	 */
	public void setCheckOnlyRegion(final Boolean value)
	{
		setCheckOnlyRegion( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute. 
	 * @param value the checkOnlyRegion
	 */
	public void setCheckOnlyRegion(final SessionContext ctx, final boolean value)
	{
		setCheckOnlyRegion( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.checkOnlyRegion</code> attribute. 
	 * @param value the checkOnlyRegion
	 */
	public void setCheckOnlyRegion(final boolean value)
	{
		setCheckOnlyRegion( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute.
	 * @return the isAnonymousCustomer
	 */
	public Boolean isIsAnonymousCustomer(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISANONYMOUSCUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute.
	 * @return the isAnonymousCustomer
	 */
	public Boolean isIsAnonymousCustomer()
	{
		return isIsAnonymousCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute. 
	 * @return the isAnonymousCustomer
	 */
	public boolean isIsAnonymousCustomerAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsAnonymousCustomer( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute. 
	 * @return the isAnonymousCustomer
	 */
	public boolean isIsAnonymousCustomerAsPrimitive()
	{
		return isIsAnonymousCustomerAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute. 
	 * @param value the isAnonymousCustomer
	 */
	public void setIsAnonymousCustomer(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISANONYMOUSCUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute. 
	 * @param value the isAnonymousCustomer
	 */
	public void setIsAnonymousCustomer(final Boolean value)
	{
		setIsAnonymousCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute. 
	 * @param value the isAnonymousCustomer
	 */
	public void setIsAnonymousCustomer(final SessionContext ctx, final boolean value)
	{
		setIsAnonymousCustomer( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.isAnonymousCustomer</code> attribute. 
	 * @param value the isAnonymousCustomer
	 */
	public void setIsAnonymousCustomer(final boolean value)
	{
		setIsAnonymousCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.region</code> attribute.
	 * @return the region
	 */
	public String getRegion(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REGION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneRegionSegment.region</code> attribute.
	 * @return the region
	 */
	public String getRegion()
	{
		return getRegion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.region</code> attribute. 
	 * @param value the region
	 */
	public void setRegion(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REGION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneRegionSegment.region</code> attribute. 
	 * @param value the region
	 */
	public void setRegion(final String value)
	{
		setRegion( getSession().getSessionContext(), value );
	}
	
}
