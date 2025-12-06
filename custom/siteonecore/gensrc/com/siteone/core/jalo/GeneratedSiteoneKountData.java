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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneKountData SiteoneKountData}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneKountData extends GenericItem
{
	/** Qualifier of the <code>SiteoneKountData.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>SiteoneKountData.customerEmailId</code> attribute **/
	public static final String CUSTOMEREMAILID = "customerEmailId";
	/** Qualifier of the <code>SiteoneKountData.kountStatus</code> attribute **/
	public static final String KOUNTSTATUS = "kountStatus";
	/** Qualifier of the <code>SiteoneKountData.isDeclineEmailSent</code> attribute **/
	public static final String ISDECLINEEMAILSENT = "isDeclineEmailSent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAILID, AttributeMode.INITIAL);
		tmp.put(KOUNTSTATUS, AttributeMode.INITIAL);
		tmp.put(ISDECLINEEMAILSENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.customerEmailId</code> attribute.
	 * @return the customerEmailId
	 */
	public String getCustomerEmailId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAILID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.customerEmailId</code> attribute.
	 * @return the customerEmailId
	 */
	public String getCustomerEmailId()
	{
		return getCustomerEmailId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.customerEmailId</code> attribute. 
	 * @param value the customerEmailId
	 */
	public void setCustomerEmailId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAILID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.customerEmailId</code> attribute. 
	 * @param value the customerEmailId
	 */
	public void setCustomerEmailId(final String value)
	{
		setCustomerEmailId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.isDeclineEmailSent</code> attribute.
	 * @return the isDeclineEmailSent
	 */
	public String getIsDeclineEmailSent(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ISDECLINEEMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.isDeclineEmailSent</code> attribute.
	 * @return the isDeclineEmailSent
	 */
	public String getIsDeclineEmailSent()
	{
		return getIsDeclineEmailSent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.isDeclineEmailSent</code> attribute. 
	 * @param value the isDeclineEmailSent
	 */
	public void setIsDeclineEmailSent(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ISDECLINEEMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.isDeclineEmailSent</code> attribute. 
	 * @param value the isDeclineEmailSent
	 */
	public void setIsDeclineEmailSent(final String value)
	{
		setIsDeclineEmailSent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.kountStatus</code> attribute.
	 * @return the kountStatus
	 */
	public String getKountStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, KOUNTSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.kountStatus</code> attribute.
	 * @return the kountStatus
	 */
	public String getKountStatus()
	{
		return getKountStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.kountStatus</code> attribute. 
	 * @param value the kountStatus
	 */
	public void setKountStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, KOUNTSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.kountStatus</code> attribute. 
	 * @param value the kountStatus
	 */
	public void setKountStatus(final String value)
	{
		setKountStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneKountData.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneKountData.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
}
