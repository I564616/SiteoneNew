/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.constants.CoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneDeal SiteOneDeal}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneDeal extends GenericItem
{
	/** Qualifier of the <code>SiteOneDeal.dealCode</code> attribute **/
	public static final String DEALCODE = "dealCode";
	/** Qualifier of the <code>SiteOneDeal.dealName</code> attribute **/
	public static final String DEALNAME = "dealName";
	/** Qualifier of the <code>SiteOneDeal.dealproducts</code> attribute **/
	public static final String DEALPRODUCTS = "dealproducts";
	/**
	* {@link OneToManyHandler} for handling 1:n DEALPRODUCTS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<Product> DEALPRODUCTSHANDLER = new OneToManyHandler<Product>(
	CoreConstants.TC.PRODUCT,
	false,
	"deal",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(DEALCODE, AttributeMode.INITIAL);
		tmp.put(DEALNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDeal.dealCode</code> attribute.
	 * @return the dealCode
	 */
	public String getDealCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDeal.dealCode</code> attribute.
	 * @return the dealCode
	 */
	public String getDealCode()
	{
		return getDealCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDeal.dealCode</code> attribute. 
	 * @param value the dealCode
	 */
	public void setDealCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDeal.dealCode</code> attribute. 
	 * @param value the dealCode
	 */
	public void setDealCode(final String value)
	{
		setDealCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDeal.dealName</code> attribute.
	 * @return the dealName
	 */
	public String getDealName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDeal.dealName</code> attribute.
	 * @return the dealName
	 */
	public String getDealName()
	{
		return getDealName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDeal.dealName</code> attribute. 
	 * @param value the dealName
	 */
	public void setDealName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDeal.dealName</code> attribute. 
	 * @param value the dealName
	 */
	public void setDealName(final String value)
	{
		setDealName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDeal.dealproducts</code> attribute.
	 * @return the dealproducts
	 */
	public Collection<Product> getDealproducts(final SessionContext ctx)
	{
		return DEALPRODUCTSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDeal.dealproducts</code> attribute.
	 * @return the dealproducts
	 */
	public Collection<Product> getDealproducts()
	{
		return getDealproducts( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDeal.dealproducts</code> attribute. 
	 * @param value the dealproducts
	 */
	public void setDealproducts(final SessionContext ctx, final Collection<Product> value)
	{
		DEALPRODUCTSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDeal.dealproducts</code> attribute. 
	 * @param value the dealproducts
	 */
	public void setDealproducts(final Collection<Product> value)
	{
		setDealproducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to dealproducts. 
	 * @param value the item to add to dealproducts
	 */
	public void addToDealproducts(final SessionContext ctx, final Product value)
	{
		DEALPRODUCTSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to dealproducts. 
	 * @param value the item to add to dealproducts
	 */
	public void addToDealproducts(final Product value)
	{
		addToDealproducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from dealproducts. 
	 * @param value the item to remove from dealproducts
	 */
	public void removeFromDealproducts(final SessionContext ctx, final Product value)
	{
		DEALPRODUCTSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from dealproducts. 
	 * @param value the item to remove from dealproducts
	 */
	public void removeFromDealproducts(final Product value)
	{
		removeFromDealproducts( getSession().getSessionContext(), value );
	}
	
}
