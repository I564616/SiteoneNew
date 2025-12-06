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
 * Generated class for type {@link com.siteone.core.jalo.SiteoneCCPaymentAuditLog SiteoneCCPaymentAuditLog}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneCCPaymentAuditLog extends GenericItem
{
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.cartID</code> attribute **/
	public static final String CARTID = "cartID";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.orderTotal</code> attribute **/
	public static final String ORDERTOTAL = "orderTotal";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.customerEmailId</code> attribute **/
	public static final String CUSTOMEREMAILID = "customerEmailId";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.lastFourDigits</code> attribute **/
	public static final String LASTFOURDIGITS = "lastFourDigits";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.zipcode</code> attribute **/
	public static final String ZIPCODE = "zipcode";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute **/
	public static final String DECLINECOUNT = "declineCount";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.branchMgrEmailId</code> attribute **/
	public static final String BRANCHMGREMAILID = "branchMgrEmailId";
	/** Qualifier of the <code>SiteoneCCPaymentAuditLog.accountMgrEmailId</code> attribute **/
	public static final String ACCOUNTMGREMAILID = "accountMgrEmailId";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(CARTID, AttributeMode.INITIAL);
		tmp.put(ORDERTOTAL, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAILID, AttributeMode.INITIAL);
		tmp.put(LASTFOURDIGITS, AttributeMode.INITIAL);
		tmp.put(ZIPCODE, AttributeMode.INITIAL);
		tmp.put(DECLINECOUNT, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(BRANCHMGREMAILID, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMGREMAILID, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.accountMgrEmailId</code> attribute.
	 * @return the accountMgrEmailId
	 */
	public String getAccountMgrEmailId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMGREMAILID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.accountMgrEmailId</code> attribute.
	 * @return the accountMgrEmailId
	 */
	public String getAccountMgrEmailId()
	{
		return getAccountMgrEmailId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.accountMgrEmailId</code> attribute. 
	 * @param value the accountMgrEmailId
	 */
	public void setAccountMgrEmailId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMGREMAILID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.accountMgrEmailId</code> attribute. 
	 * @param value the accountMgrEmailId
	 */
	public void setAccountMgrEmailId(final String value)
	{
		setAccountMgrEmailId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.branchMgrEmailId</code> attribute.
	 * @return the branchMgrEmailId
	 */
	public String getBranchMgrEmailId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHMGREMAILID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.branchMgrEmailId</code> attribute.
	 * @return the branchMgrEmailId
	 */
	public String getBranchMgrEmailId()
	{
		return getBranchMgrEmailId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.branchMgrEmailId</code> attribute. 
	 * @param value the branchMgrEmailId
	 */
	public void setBranchMgrEmailId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHMGREMAILID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.branchMgrEmailId</code> attribute. 
	 * @param value the branchMgrEmailId
	 */
	public void setBranchMgrEmailId(final String value)
	{
		setBranchMgrEmailId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.cartID</code> attribute.
	 * @return the cartID
	 */
	public String getCartID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.cartID</code> attribute.
	 * @return the cartID
	 */
	public String getCartID()
	{
		return getCartID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.cartID</code> attribute. 
	 * @param value the cartID
	 */
	public void setCartID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.cartID</code> attribute. 
	 * @param value the cartID
	 */
	public void setCartID(final String value)
	{
		setCartID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.customerEmailId</code> attribute.
	 * @return the customerEmailId
	 */
	public String getCustomerEmailId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAILID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.customerEmailId</code> attribute.
	 * @return the customerEmailId
	 */
	public String getCustomerEmailId()
	{
		return getCustomerEmailId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.customerEmailId</code> attribute. 
	 * @param value the customerEmailId
	 */
	public void setCustomerEmailId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAILID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.customerEmailId</code> attribute. 
	 * @param value the customerEmailId
	 */
	public void setCustomerEmailId(final String value)
	{
		setCustomerEmailId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute.
	 * @return the declineCount
	 */
	public Integer getDeclineCount(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, DECLINECOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute.
	 * @return the declineCount
	 */
	public Integer getDeclineCount()
	{
		return getDeclineCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute. 
	 * @return the declineCount
	 */
	public int getDeclineCountAsPrimitive(final SessionContext ctx)
	{
		Integer value = getDeclineCount( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute. 
	 * @return the declineCount
	 */
	public int getDeclineCountAsPrimitive()
	{
		return getDeclineCountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute. 
	 * @param value the declineCount
	 */
	public void setDeclineCount(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, DECLINECOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute. 
	 * @param value the declineCount
	 */
	public void setDeclineCount(final Integer value)
	{
		setDeclineCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute. 
	 * @param value the declineCount
	 */
	public void setDeclineCount(final SessionContext ctx, final int value)
	{
		setDeclineCount( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.declineCount</code> attribute. 
	 * @param value the declineCount
	 */
	public void setDeclineCount(final int value)
	{
		setDeclineCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.lastFourDigits</code> attribute.
	 * @return the lastFourDigits
	 */
	public String getLastFourDigits(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTFOURDIGITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.lastFourDigits</code> attribute.
	 * @return the lastFourDigits
	 */
	public String getLastFourDigits()
	{
		return getLastFourDigits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.lastFourDigits</code> attribute. 
	 * @param value the lastFourDigits
	 */
	public void setLastFourDigits(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTFOURDIGITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.lastFourDigits</code> attribute. 
	 * @param value the lastFourDigits
	 */
	public void setLastFourDigits(final String value)
	{
		setLastFourDigits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.orderTotal</code> attribute.
	 * @return the orderTotal
	 */
	public String getOrderTotal(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.orderTotal</code> attribute.
	 * @return the orderTotal
	 */
	public String getOrderTotal()
	{
		return getOrderTotal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.orderTotal</code> attribute. 
	 * @param value the orderTotal
	 */
	public void setOrderTotal(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.orderTotal</code> attribute. 
	 * @param value the orderTotal
	 */
	public void setOrderTotal(final String value)
	{
		setOrderTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneCCPaymentAuditLog.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode()
	{
		return getZipcode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneCCPaymentAuditLog.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final String value)
	{
		setZipcode( getSession().getSessionContext(), value );
	}
	
}
