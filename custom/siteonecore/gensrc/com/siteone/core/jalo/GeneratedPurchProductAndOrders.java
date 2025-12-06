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
 * Generated class for type {@link com.siteone.core.jalo.PurchProductAndOrders PurchProductAndOrders}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPurchProductAndOrders extends GenericItem
{
	/** Qualifier of the <code>PurchProductAndOrders.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	/** Qualifier of the <code>PurchProductAndOrders.productName</code> attribute **/
	public static final String PRODUCTNAME = "productName";
	/** Qualifier of the <code>PurchProductAndOrders.itemNumber</code> attribute **/
	public static final String ITEMNUMBER = "itemNumber";
	/** Qualifier of the <code>PurchProductAndOrders.isStoreProduct</code> attribute **/
	public static final String ISSTOREPRODUCT = "isStoreProduct";
	/** Qualifier of the <code>PurchProductAndOrders.orderId</code> attribute **/
	public static final String ORDERID = "orderId";
	/** Qualifier of the <code>PurchProductAndOrders.uniqueKeyPurchasedProduct</code> attribute **/
	public static final String UNIQUEKEYPURCHASEDPRODUCT = "uniqueKeyPurchasedProduct";
	/** Qualifier of the <code>PurchProductAndOrders.orderingAccount</code> attribute **/
	public static final String ORDERINGACCOUNT = "orderingAccount";
	/** Qualifier of the <code>PurchProductAndOrders.mainAccount</code> attribute **/
	public static final String MAINACCOUNT = "mainAccount";
	/** Qualifier of the <code>PurchProductAndOrders.purchasedDate</code> attribute **/
	public static final String PURCHASEDDATE = "purchasedDate";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		tmp.put(PRODUCTNAME, AttributeMode.INITIAL);
		tmp.put(ITEMNUMBER, AttributeMode.INITIAL);
		tmp.put(ISSTOREPRODUCT, AttributeMode.INITIAL);
		tmp.put(ORDERID, AttributeMode.INITIAL);
		tmp.put(UNIQUEKEYPURCHASEDPRODUCT, AttributeMode.INITIAL);
		tmp.put(ORDERINGACCOUNT, AttributeMode.INITIAL);
		tmp.put(MAINACCOUNT, AttributeMode.INITIAL);
		tmp.put(PURCHASEDDATE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute.
	 * @return the isStoreProduct - isStoreProduct
	 */
	public Boolean isIsStoreProduct(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISSTOREPRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute.
	 * @return the isStoreProduct - isStoreProduct
	 */
	public Boolean isIsStoreProduct()
	{
		return isIsStoreProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute. 
	 * @return the isStoreProduct - isStoreProduct
	 */
	public boolean isIsStoreProductAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsStoreProduct( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute. 
	 * @return the isStoreProduct - isStoreProduct
	 */
	public boolean isIsStoreProductAsPrimitive()
	{
		return isIsStoreProductAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISSTOREPRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final Boolean value)
	{
		setIsStoreProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final SessionContext ctx, final boolean value)
	{
		setIsStoreProduct( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final boolean value)
	{
		setIsStoreProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber()
	{
		return getItemNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final String value)
	{
		setItemNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.mainAccount</code> attribute.
	 * @return the mainAccount
	 */
	public String getMainAccount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MAINACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.mainAccount</code> attribute.
	 * @return the mainAccount
	 */
	public String getMainAccount()
	{
		return getMainAccount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.mainAccount</code> attribute. 
	 * @param value the mainAccount
	 */
	public void setMainAccount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MAINACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.mainAccount</code> attribute. 
	 * @param value the mainAccount
	 */
	public void setMainAccount(final String value)
	{
		setMainAccount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.orderId</code> attribute.
	 * @return the orderId - order id
	 */
	public String getOrderId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.orderId</code> attribute.
	 * @return the orderId - order id
	 */
	public String getOrderId()
	{
		return getOrderId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.orderId</code> attribute. 
	 * @param value the orderId - order id
	 */
	public void setOrderId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.orderId</code> attribute. 
	 * @param value the orderId - order id
	 */
	public void setOrderId(final String value)
	{
		setOrderId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.orderingAccount</code> attribute.
	 * @return the orderingAccount
	 */
	public String getOrderingAccount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERINGACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.orderingAccount</code> attribute.
	 * @return the orderingAccount
	 */
	public String getOrderingAccount()
	{
		return getOrderingAccount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.orderingAccount</code> attribute. 
	 * @param value the orderingAccount
	 */
	public void setOrderingAccount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERINGACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.orderingAccount</code> attribute. 
	 * @param value the orderingAccount
	 */
	public void setOrderingAccount(final String value)
	{
		setOrderingAccount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return getProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final String value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.productName</code> attribute.
	 * @return the productName
	 */
	public String getProductName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.productName</code> attribute.
	 * @return the productName
	 */
	public String getProductName()
	{
		return getProductName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.productName</code> attribute. 
	 * @param value the productName
	 */
	public void setProductName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.productName</code> attribute. 
	 * @param value the productName
	 */
	public void setProductName(final String value)
	{
		setProductName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.purchasedDate</code> attribute.
	 * @return the purchasedDate
	 */
	public Date getPurchasedDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, PURCHASEDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.purchasedDate</code> attribute.
	 * @return the purchasedDate
	 */
	public Date getPurchasedDate()
	{
		return getPurchasedDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.purchasedDate</code> attribute. 
	 * @param value the purchasedDate
	 */
	public void setPurchasedDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, PURCHASEDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.purchasedDate</code> attribute. 
	 * @param value the purchasedDate
	 */
	public void setPurchasedDate(final Date value)
	{
		setPurchasedDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.uniqueKeyPurchasedProduct</code> attribute.
	 * @return the uniqueKeyPurchasedProduct - order id
	 */
	public String getUniqueKeyPurchasedProduct(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UNIQUEKEYPURCHASEDPRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchProductAndOrders.uniqueKeyPurchasedProduct</code> attribute.
	 * @return the uniqueKeyPurchasedProduct - order id
	 */
	public String getUniqueKeyPurchasedProduct()
	{
		return getUniqueKeyPurchasedProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.uniqueKeyPurchasedProduct</code> attribute. 
	 * @param value the uniqueKeyPurchasedProduct - order id
	 */
	public void setUniqueKeyPurchasedProduct(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UNIQUEKEYPURCHASEDPRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchProductAndOrders.uniqueKeyPurchasedProduct</code> attribute. 
	 * @param value the uniqueKeyPurchasedProduct - order id
	 */
	public void setUniqueKeyPurchasedProduct(final String value)
	{
		setUniqueKeyPurchasedProduct( getSession().getSessionContext(), value );
	}
	
}
