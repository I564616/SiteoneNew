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
import de.hybris.platform.jalo.product.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.PromotionProductCategory PromotionProductCategory}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPromotionProductCategory extends GenericItem
{
	/** Qualifier of the <code>PromotionProductCategory.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>PromotionProductCategory.productsList</code> attribute **/
	public static final String PRODUCTSLIST = "productsList";
	/** Qualifier of the <code>PromotionProductCategory.productsCode</code> attribute **/
	public static final String PRODUCTSCODE = "productsCode";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(PRODUCTSLIST, AttributeMode.INITIAL);
		tmp.put(PRODUCTSCODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionProductCategory.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionProductCategory.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionProductCategory.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionProductCategory.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionProductCategory.productsCode</code> attribute.
	 * @return the productsCode
	 */
	public List<String> getProductsCode(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, PRODUCTSCODE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionProductCategory.productsCode</code> attribute.
	 * @return the productsCode
	 */
	public List<String> getProductsCode()
	{
		return getProductsCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionProductCategory.productsCode</code> attribute. 
	 * @param value the productsCode
	 */
	public void setProductsCode(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, PRODUCTSCODE,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionProductCategory.productsCode</code> attribute. 
	 * @param value the productsCode
	 */
	public void setProductsCode(final List<String> value)
	{
		setProductsCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionProductCategory.productsList</code> attribute.
	 * @return the productsList
	 */
	public List<Product> getProductsList(final SessionContext ctx)
	{
		List<Product> coll = (List<Product>)getProperty( ctx, PRODUCTSLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionProductCategory.productsList</code> attribute.
	 * @return the productsList
	 */
	public List<Product> getProductsList()
	{
		return getProductsList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionProductCategory.productsList</code> attribute. 
	 * @param value the productsList
	 */
	public void setProductsList(final SessionContext ctx, final List<Product> value)
	{
		setProperty(ctx, PRODUCTSLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionProductCategory.productsList</code> attribute. 
	 * @param value the productsList
	 */
	public void setProductsList(final List<Product> value)
	{
		setProductsList( getSession().getSessionContext(), value );
	}
	
}
