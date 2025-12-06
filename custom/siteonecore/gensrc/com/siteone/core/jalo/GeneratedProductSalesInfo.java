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
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Utilities;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.ProductSalesInfo ProductSalesInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedProductSalesInfo extends GenericItem
{
	/** Qualifier of the <code>ProductSalesInfo.region</code> attribute **/
	public static final String REGION = "region";
	/** Qualifier of the <code>ProductSalesInfo.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	/** Qualifier of the <code>ProductSalesInfo.ytdSales</code> attribute **/
	public static final String YTDSALES = "ytdSales";
	/** Qualifier of the <code>ProductSalesInfo.lastYtdSales</code> attribute **/
	public static final String LASTYTDSALES = "lastYtdSales";
	/** Qualifier of the <code>ProductSalesInfo.products</code> attribute **/
	public static final String PRODUCTS = "products";
	/** Relation ordering override parameter constants for SalesProductRelation from ((siteonecore))*/
	protected static String SALESPRODUCTRELATION_SRC_ORDERED = "relation.SalesProductRelation.source.ordered";
	protected static String SALESPRODUCTRELATION_TGT_ORDERED = "relation.SalesProductRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for SalesProductRelation from ((siteonecore))*/
	protected static String SALESPRODUCTRELATION_MARKMODIFIED = "relation.SalesProductRelation.markmodified";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(REGION, AttributeMode.INITIAL);
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		tmp.put(YTDSALES, AttributeMode.INITIAL);
		tmp.put(LASTYTDSALES, AttributeMode.INITIAL);
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
		ComposedType relationSecondEnd0 = TypeManager.getInstance().getComposedType("Product");
		if(relationSecondEnd0.isAssignableFrom(referencedItem.getComposedType()))
		{
			return Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED);
		}
		return true;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.lastYtdSales</code> attribute.
	 * @return the lastYtdSales
	 */
	public Double getLastYtdSales(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LASTYTDSALES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.lastYtdSales</code> attribute.
	 * @return the lastYtdSales
	 */
	public Double getLastYtdSales()
	{
		return getLastYtdSales( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.lastYtdSales</code> attribute. 
	 * @return the lastYtdSales
	 */
	public double getLastYtdSalesAsPrimitive(final SessionContext ctx)
	{
		Double value = getLastYtdSales( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.lastYtdSales</code> attribute. 
	 * @return the lastYtdSales
	 */
	public double getLastYtdSalesAsPrimitive()
	{
		return getLastYtdSalesAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.lastYtdSales</code> attribute. 
	 * @param value the lastYtdSales
	 */
	public void setLastYtdSales(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LASTYTDSALES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.lastYtdSales</code> attribute. 
	 * @param value the lastYtdSales
	 */
	public void setLastYtdSales(final Double value)
	{
		setLastYtdSales( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.lastYtdSales</code> attribute. 
	 * @param value the lastYtdSales
	 */
	public void setLastYtdSales(final SessionContext ctx, final double value)
	{
		setLastYtdSales( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.lastYtdSales</code> attribute. 
	 * @param value the lastYtdSales
	 */
	public void setLastYtdSales(final double value)
	{
		setLastYtdSales( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return getProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final String value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.products</code> attribute.
	 * @return the products
	 */
	public Collection<Product> getProducts(final SessionContext ctx)
	{
		final List<Product> items = getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			"Product",
			null,
			false,
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.products</code> attribute.
	 * @return the products
	 */
	public Collection<Product> getProducts()
	{
		return getProducts( getSession().getSessionContext() );
	}
	
	public long getProductsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			"Product",
			null
		);
	}
	
	public long getProductsCount()
	{
		return getProductsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.products</code> attribute. 
	 * @param value the products
	 */
	public void setProducts(final SessionContext ctx, final Collection<Product> value)
	{
		setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.products</code> attribute. 
	 * @param value the products
	 */
	public void setProducts(final Collection<Product> value)
	{
		setProducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products. 
	 * @param value the item to add to products
	 */
	public void addToProducts(final SessionContext ctx, final Product value)
	{
		addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products. 
	 * @param value the item to add to products
	 */
	public void addToProducts(final Product value)
	{
		addToProducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products. 
	 * @param value the item to remove from products
	 */
	public void removeFromProducts(final SessionContext ctx, final Product value)
	{
		removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products. 
	 * @param value the item to remove from products
	 */
	public void removeFromProducts(final Product value)
	{
		removeFromProducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.region</code> attribute.
	 * @return the region
	 */
	public String getRegion(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REGION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.region</code> attribute.
	 * @return the region
	 */
	public String getRegion()
	{
		return getRegion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.region</code> attribute. 
	 * @param value the region
	 */
	public void setRegion(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REGION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.region</code> attribute. 
	 * @param value the region
	 */
	public void setRegion(final String value)
	{
		setRegion( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.ytdSales</code> attribute.
	 * @return the ytdSales
	 */
	public Double getYtdSales(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, YTDSALES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.ytdSales</code> attribute.
	 * @return the ytdSales
	 */
	public Double getYtdSales()
	{
		return getYtdSales( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.ytdSales</code> attribute. 
	 * @return the ytdSales
	 */
	public double getYtdSalesAsPrimitive(final SessionContext ctx)
	{
		Double value = getYtdSales( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductSalesInfo.ytdSales</code> attribute. 
	 * @return the ytdSales
	 */
	public double getYtdSalesAsPrimitive()
	{
		return getYtdSalesAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.ytdSales</code> attribute. 
	 * @param value the ytdSales
	 */
	public void setYtdSales(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, YTDSALES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.ytdSales</code> attribute. 
	 * @param value the ytdSales
	 */
	public void setYtdSales(final Double value)
	{
		setYtdSales( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.ytdSales</code> attribute. 
	 * @param value the ytdSales
	 */
	public void setYtdSales(final SessionContext ctx, final double value)
	{
		setYtdSales( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductSalesInfo.ytdSales</code> attribute. 
	 * @param value the ytdSales
	 */
	public void setYtdSales(final double value)
	{
		setYtdSales( getSession().getSessionContext(), value );
	}
	
}
