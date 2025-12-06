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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem SiteoneOrderEmailStatus}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneOrderEmailStatus extends GenericItem
{
	/** Qualifier of the <code>SiteoneOrderEmailStatus.orderId</code> attribute **/
	public static final String ORDERID = "orderId";
	/** Qualifier of the <code>SiteoneOrderEmailStatus.consignmentId</code> attribute **/
	public static final String CONSIGNMENTID = "consignmentId";
	/** Qualifier of the <code>SiteoneOrderEmailStatus.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute **/
	public static final String ISEMAILSENT = "isEmailsent";
	/** Qualifier of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute **/
	public static final String ISREMINDEREMAILSENT = "isReminderEmailsent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ORDERID, AttributeMode.INITIAL);
		tmp.put(CONSIGNMENTID, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(ISEMAILSENT, AttributeMode.INITIAL);
		tmp.put(ISREMINDEREMAILSENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.consignmentId</code> attribute.
	 * @return the consignmentId
	 */
	public String getConsignmentId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONSIGNMENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.consignmentId</code> attribute.
	 * @return the consignmentId
	 */
	public String getConsignmentId()
	{
		return getConsignmentId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.consignmentId</code> attribute. 
	 * @param value the consignmentId
	 */
	public void setConsignmentId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONSIGNMENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.consignmentId</code> attribute. 
	 * @param value the consignmentId
	 */
	public void setConsignmentId(final String value)
	{
		setConsignmentId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute.
	 * @return the isEmailsent
	 */
	public Boolean isIsEmailsent(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISEMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute.
	 * @return the isEmailsent
	 */
	public Boolean isIsEmailsent()
	{
		return isIsEmailsent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute. 
	 * @return the isEmailsent
	 */
	public boolean isIsEmailsentAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsEmailsent( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute. 
	 * @return the isEmailsent
	 */
	public boolean isIsEmailsentAsPrimitive()
	{
		return isIsEmailsentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute. 
	 * @param value the isEmailsent
	 */
	public void setIsEmailsent(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISEMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute. 
	 * @param value the isEmailsent
	 */
	public void setIsEmailsent(final Boolean value)
	{
		setIsEmailsent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute. 
	 * @param value the isEmailsent
	 */
	public void setIsEmailsent(final SessionContext ctx, final boolean value)
	{
		setIsEmailsent( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isEmailsent</code> attribute. 
	 * @param value the isEmailsent
	 */
	public void setIsEmailsent(final boolean value)
	{
		setIsEmailsent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute.
	 * @return the isReminderEmailsent
	 */
	public Boolean isIsReminderEmailsent(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISREMINDEREMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute.
	 * @return the isReminderEmailsent
	 */
	public Boolean isIsReminderEmailsent()
	{
		return isIsReminderEmailsent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute. 
	 * @return the isReminderEmailsent
	 */
	public boolean isIsReminderEmailsentAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsReminderEmailsent( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute. 
	 * @return the isReminderEmailsent
	 */
	public boolean isIsReminderEmailsentAsPrimitive()
	{
		return isIsReminderEmailsentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute. 
	 * @param value the isReminderEmailsent
	 */
	public void setIsReminderEmailsent(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISREMINDEREMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute. 
	 * @param value the isReminderEmailsent
	 */
	public void setIsReminderEmailsent(final Boolean value)
	{
		setIsReminderEmailsent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute. 
	 * @param value the isReminderEmailsent
	 */
	public void setIsReminderEmailsent(final SessionContext ctx, final boolean value)
	{
		setIsReminderEmailsent( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.isReminderEmailsent</code> attribute. 
	 * @param value the isReminderEmailsent
	 */
	public void setIsReminderEmailsent(final boolean value)
	{
		setIsReminderEmailsent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.orderId</code> attribute.
	 * @return the orderId
	 */
	public String getOrderId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.orderId</code> attribute.
	 * @return the orderId
	 */
	public String getOrderId()
	{
		return getOrderId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.orderId</code> attribute. 
	 * @param value the orderId
	 */
	public void setOrderId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.orderId</code> attribute. 
	 * @param value the orderId
	 */
	public void setOrderId(final String value)
	{
		setOrderId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.status</code> attribute.
	 * @return the status
	 */
	public String getStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneOrderEmailStatus.status</code> attribute.
	 * @return the status
	 */
	public String getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneOrderEmailStatus.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final String value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
}
