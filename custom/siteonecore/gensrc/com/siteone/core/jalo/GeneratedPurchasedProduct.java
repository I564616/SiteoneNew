/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Utilities;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generated class for type {@link com.siteone.core.jalo.PurchasedProduct PurchasedProduct}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPurchasedProduct extends GenericItem
{
	/** Qualifier of the <code>PurchasedProduct.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	/** Qualifier of the <code>PurchasedProduct.productName</code> attribute **/
	public static final String PRODUCTNAME = "productName";
	/** Qualifier of the <code>PurchasedProduct.itemNumber</code> attribute **/
	public static final String ITEMNUMBER = "itemNumber";
	/** Qualifier of the <code>PurchasedProduct.isStoreProduct</code> attribute **/
	public static final String ISSTOREPRODUCT = "isStoreProduct";
	/** Qualifier of the <code>PurchasedProduct.orders</code> attribute **/
	public static final String ORDERS = "orders";
	/** Relation ordering override parameter constants for PurchasedProduct2OrderRelation from ((siteonecore))*/
	protected static String PURCHASEDPRODUCT2ORDERRELATION_SRC_ORDERED = "relation.PurchasedProduct2OrderRelation.source.ordered";
	protected static String PURCHASEDPRODUCT2ORDERRELATION_TGT_ORDERED = "relation.PurchasedProduct2OrderRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for PurchasedProduct2OrderRelation from ((siteonecore))*/
	protected static String PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED = "relation.PurchasedProduct2OrderRelation.markmodified";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		tmp.put(PRODUCTNAME, AttributeMode.INITIAL);
		tmp.put(ITEMNUMBER, AttributeMode.INITIAL);
		tmp.put(ISSTOREPRODUCT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * @deprecated since 2011, use {@link Utilities#getMarkModifiedOverride(de.hybris.platform.jalo.type.RelationType)
	 */
	@Override
	@Deprecated(since = "2105", forRemoval = true)
	public boolean isMarkModifiedDisabled(final Item referencedItem)
	{
		ComposedType relationSecondEnd0 = TypeManager.getInstance().getComposedType("AbstractOrder");
		if(relationSecondEnd0.isAssignableFrom(referencedItem.getComposedType()))
		{
			return Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED);
		}
		return true;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.isStoreProduct</code> attribute.
	 * @return the isStoreProduct - isStoreProduct
	 */
	public Boolean isIsStoreProduct(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISSTOREPRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.isStoreProduct</code> attribute.
	 * @return the isStoreProduct - isStoreProduct
	 */
	public Boolean isIsStoreProduct()
	{
		return isIsStoreProduct( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.isStoreProduct</code> attribute. 
	 * @return the isStoreProduct - isStoreProduct
	 */
	public boolean isIsStoreProductAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsStoreProduct( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.isStoreProduct</code> attribute. 
	 * @return the isStoreProduct - isStoreProduct
	 */
	public boolean isIsStoreProductAsPrimitive()
	{
		return isIsStoreProductAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISSTOREPRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final Boolean value)
	{
		setIsStoreProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final SessionContext ctx, final boolean value)
	{
		setIsStoreProduct( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.isStoreProduct</code> attribute. 
	 * @param value the isStoreProduct - isStoreProduct
	 */
	public void setIsStoreProduct(final boolean value)
	{
		setIsStoreProduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber()
	{
		return getItemNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final String value)
	{
		setItemNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.orders</code> attribute.
	 * @return the orders
	 */
	public Set<AbstractOrder> getOrders(final SessionContext ctx)
	{
		final List<AbstractOrder> items = getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			"AbstractOrder",
			null,
			false,
			false
		);
		return new LinkedHashSet<AbstractOrder>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.orders</code> attribute.
	 * @return the orders
	 */
	public Set<AbstractOrder> getOrders()
	{
		return getOrders( getSession().getSessionContext() );
	}
	
	public long getOrdersCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			"AbstractOrder",
			null
		);
	}
	
	public long getOrdersCount()
	{
		return getOrdersCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.orders</code> attribute. 
	 * @param value the orders
	 */
	public void setOrders(final SessionContext ctx, final Set<AbstractOrder> value)
	{
		setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.orders</code> attribute. 
	 * @param value the orders
	 */
	public void setOrders(final Set<AbstractOrder> value)
	{
		setOrders( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to orders. 
	 * @param value the item to add to orders
	 */
	public void addToOrders(final SessionContext ctx, final AbstractOrder value)
	{
		addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to orders. 
	 * @param value the item to add to orders
	 */
	public void addToOrders(final AbstractOrder value)
	{
		addToOrders( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from orders. 
	 * @param value the item to remove from orders
	 */
	public void removeFromOrders(final SessionContext ctx, final AbstractOrder value)
	{
		removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from orders. 
	 * @param value the item to remove from orders
	 */
	public void removeFromOrders(final AbstractOrder value)
	{
		removeFromOrders( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return getProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final String value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.productName</code> attribute.
	 * @return the productName
	 */
	public String getProductName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PurchasedProduct.productName</code> attribute.
	 * @return the productName
	 */
	public String getProductName()
	{
		return getProductName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.productName</code> attribute. 
	 * @param value the productName
	 */
	public void setProductName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PurchasedProduct.productName</code> attribute. 
	 * @param value the productName
	 */
	public void setProductName(final String value)
	{
		setProductName( getSession().getSessionContext(), value );
	}
	
}
