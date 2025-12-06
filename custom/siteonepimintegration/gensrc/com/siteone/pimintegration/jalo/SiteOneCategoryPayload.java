/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import com.siteone.pimintegration.jalo.SiteOneCategoryAttributes;
import com.siteone.pimintegration.jalo.SiteOneCategoryLevel;
import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.util.PartOfHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type SiteOneCategoryPayload.
 */
@SLDSafe
@SuppressWarnings({"unused","cast"})
public class SiteOneCategoryPayload extends GenericItem
{
	/** Qualifier of the <code>SiteOneCategoryPayload.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>SiteOneCategoryPayload.catalogVersion</code> attribute **/
	public static final String CATALOGVERSION = "catalogVersion";
	/** Qualifier of the <code>SiteOneCategoryPayload.categoryLevel</code> attribute **/
	public static final String CATEGORYLEVEL = "categoryLevel";
	/** Qualifier of the <code>SiteOneCategoryPayload.attributeValues</code> attribute **/
	public static final String ATTRIBUTEVALUES = "attributeValues";
	/** Qualifier of the <code>SiteOneCategoryPayload.newCategory</code> attribute **/
	public static final String NEWCATEGORY = "newCategory";
	/** Qualifier of the <code>SiteOneCategoryPayload.newCatalog</code> attribute **/
	public static final String NEWCATALOG = "newCatalog";
	/** Qualifier of the <code>SiteOneCategoryPayload.newCategoryCA</code> attribute **/
	public static final String NEWCATEGORYCA = "newCategoryCA";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(CATALOGVERSION, AttributeMode.INITIAL);
		tmp.put(CATEGORYLEVEL, AttributeMode.INITIAL);
		tmp.put(ATTRIBUTEVALUES, AttributeMode.INITIAL);
		tmp.put(NEWCATEGORY, AttributeMode.INITIAL);
		tmp.put(NEWCATALOG, AttributeMode.INITIAL);
		tmp.put(NEWCATEGORYCA, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.attributeValues</code> attribute.
	 * @return the attributeValues
	 */
	public List<SiteOneCategoryAttributes> getAttributeValues(final SessionContext ctx)
	{
		List<SiteOneCategoryAttributes> coll = (List<SiteOneCategoryAttributes>)getProperty( ctx, "attributeValues".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.attributeValues</code> attribute.
	 * @return the attributeValues
	 */
	public List<SiteOneCategoryAttributes> getAttributeValues()
	{
		return getAttributeValues( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.attributeValues</code> attribute. 
	 * @param value the attributeValues
	 */
	public void setAttributeValues(final SessionContext ctx, final List<SiteOneCategoryAttributes> value)
	{
		new PartOfHandler<List<SiteOneCategoryAttributes>>()
		{
			@Override
			protected List<SiteOneCategoryAttributes> doGetValue(final SessionContext ctx)
			{
				return getAttributeValues( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneCategoryAttributes> _value)
			{
				final List<SiteOneCategoryAttributes> value = _value;
				setProperty(ctx, "attributeValues".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.attributeValues</code> attribute. 
	 * @param value the attributeValues
	 */
	public void setAttributeValues(final List<SiteOneCategoryAttributes> value)
	{
		setAttributeValues( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.catalogVersion</code> attribute.
	 * @return the catalogVersion
	 */
	public CatalogVersion getCatalogVersion(final SessionContext ctx)
	{
		return (CatalogVersion)getProperty( ctx, "catalogVersion".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.catalogVersion</code> attribute.
	 * @return the catalogVersion
	 */
	public CatalogVersion getCatalogVersion()
	{
		return getCatalogVersion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.catalogVersion</code> attribute. 
	 * @param value the catalogVersion
	 */
	public void setCatalogVersion(final SessionContext ctx, final CatalogVersion value)
	{
		setProperty(ctx, "catalogVersion".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.catalogVersion</code> attribute. 
	 * @param value the catalogVersion
	 */
	public void setCatalogVersion(final CatalogVersion value)
	{
		setCatalogVersion( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.categoryLevel</code> attribute.
	 * @return the categoryLevel
	 */
	public List<SiteOneCategoryLevel> getCategoryLevel(final SessionContext ctx)
	{
		List<SiteOneCategoryLevel> coll = (List<SiteOneCategoryLevel>)getProperty( ctx, "categoryLevel".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.categoryLevel</code> attribute.
	 * @return the categoryLevel
	 */
	public List<SiteOneCategoryLevel> getCategoryLevel()
	{
		return getCategoryLevel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.categoryLevel</code> attribute. 
	 * @param value the categoryLevel
	 */
	public void setCategoryLevel(final SessionContext ctx, final List<SiteOneCategoryLevel> value)
	{
		new PartOfHandler<List<SiteOneCategoryLevel>>()
		{
			@Override
			protected List<SiteOneCategoryLevel> doGetValue(final SessionContext ctx)
			{
				return getCategoryLevel( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneCategoryLevel> _value)
			{
				final List<SiteOneCategoryLevel> value = _value;
				setProperty(ctx, "categoryLevel".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.categoryLevel</code> attribute. 
	 * @param value the categoryLevel
	 */
	public void setCategoryLevel(final List<SiteOneCategoryLevel> value)
	{
		setCategoryLevel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "code".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "code".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.newCatalog</code> attribute.
	 * @return the newCatalog
	 */
	public Catalog getNewCatalog(final SessionContext ctx)
	{
		return (Catalog)getProperty( ctx, "newCatalog".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.newCatalog</code> attribute.
	 * @return the newCatalog
	 */
	public Catalog getNewCatalog()
	{
		return getNewCatalog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.newCatalog</code> attribute. 
	 * @param value the newCatalog
	 */
	public void setNewCatalog(final SessionContext ctx, final Catalog value)
	{
		new PartOfHandler<Catalog>()
		{
			@Override
			protected Catalog doGetValue(final SessionContext ctx)
			{
				return getNewCatalog( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final Catalog _value)
			{
				final Catalog value = _value;
				setProperty(ctx, "newCatalog".intern(),value);
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.newCatalog</code> attribute. 
	 * @param value the newCatalog
	 */
	public void setNewCatalog(final Catalog value)
	{
		setNewCatalog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.newCategory</code> attribute.
	 * @return the newCategory
	 */
	public Category getNewCategory(final SessionContext ctx)
	{
		return (Category)getProperty( ctx, "newCategory".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.newCategory</code> attribute.
	 * @return the newCategory
	 */
	public Category getNewCategory()
	{
		return getNewCategory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.newCategory</code> attribute. 
	 * @param value the newCategory
	 */
	public void setNewCategory(final SessionContext ctx, final Category value)
	{
		new PartOfHandler<Category>()
		{
			@Override
			protected Category doGetValue(final SessionContext ctx)
			{
				return getNewCategory( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final Category _value)
			{
				final Category value = _value;
				setProperty(ctx, "newCategory".intern(),value);
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.newCategory</code> attribute. 
	 * @param value the newCategory
	 */
	public void setNewCategory(final Category value)
	{
		setNewCategory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.newCategoryCA</code> attribute.
	 * @return the newCategoryCA
	 */
	public Category getNewCategoryCA(final SessionContext ctx)
	{
		return (Category)getProperty( ctx, "newCategoryCA".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryPayload.newCategoryCA</code> attribute.
	 * @return the newCategoryCA
	 */
	public Category getNewCategoryCA()
	{
		return getNewCategoryCA( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.newCategoryCA</code> attribute. 
	 * @param value the newCategoryCA
	 */
	public void setNewCategoryCA(final SessionContext ctx, final Category value)
	{
		new PartOfHandler<Category>()
		{
			@Override
			protected Category doGetValue(final SessionContext ctx)
			{
				return getNewCategoryCA( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final Category _value)
			{
				final Category value = _value;
				setProperty(ctx, "newCategoryCA".intern(),value);
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryPayload.newCategoryCA</code> attribute. 
	 * @param value the newCategoryCA
	 */
	public void setNewCategoryCA(final Category value)
	{
		setNewCategoryCA( getSession().getSessionContext(), value );
	}
	
}
