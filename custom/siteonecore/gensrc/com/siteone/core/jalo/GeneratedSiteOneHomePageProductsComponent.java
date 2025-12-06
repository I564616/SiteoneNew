/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneHomePageProductsComponent SiteOneHomePageProductsComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneHomePageProductsComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneHomePageProductsComponent.products</code> attribute **/
	public static final String PRODUCTS = "products";
	/** Qualifier of the <code>SiteOneHomePageProductsComponent.title</code> attribute **/
	public static final String TITLE = "title";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCTS, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageProductsComponent.products</code> attribute.
	 * @return the products - Feature Products at HomePage
	 */
	public Set<Product> getProducts(final SessionContext ctx)
	{
		Set<Product> coll = (Set<Product>)getProperty( ctx, PRODUCTS);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageProductsComponent.products</code> attribute.
	 * @return the products - Feature Products at HomePage
	 */
	public Set<Product> getProducts()
	{
		return getProducts( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageProductsComponent.products</code> attribute. 
	 * @param value the products - Feature Products at HomePage
	 */
	public void setProducts(final SessionContext ctx, final Set<Product> value)
	{
		setProperty(ctx, PRODUCTS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageProductsComponent.products</code> attribute. 
	 * @param value the products - Feature Products at HomePage
	 */
	public void setProducts(final Set<Product> value)
	{
		setProducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageProductsComponent.title</code> attribute.
	 * @return the title - Section Heading
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageProductsComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageProductsComponent.title</code> attribute.
	 * @return the title - Section Heading
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageProductsComponent.title</code> attribute. 
	 * @return the localized title - Section Heading
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneHomePageProductsComponent.title</code> attribute. 
	 * @return the localized title - Section Heading
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageProductsComponent.title</code> attribute. 
	 * @param value the title - Section Heading
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneHomePageProductsComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageProductsComponent.title</code> attribute. 
	 * @param value the title - Section Heading
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageProductsComponent.title</code> attribute. 
	 * @param value the title - Section Heading
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneHomePageProductsComponent.title</code> attribute. 
	 * @param value the title - Section Heading
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
