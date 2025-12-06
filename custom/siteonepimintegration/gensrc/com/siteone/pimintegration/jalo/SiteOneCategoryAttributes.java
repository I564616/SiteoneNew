/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type SiteOneCategoryAttributes.
 */
@SLDSafe
@SuppressWarnings({"unused","cast"})
public class SiteOneCategoryAttributes extends GenericItem
{
	/** Qualifier of the <code>SiteOneCategoryAttributes.id</code> attribute **/
	public static final String ID = "id";
	/** Qualifier of the <code>SiteOneCategoryAttributes.attributeCode</code> attribute **/
	public static final String ATTRIBUTECODE = "attributeCode";
	/** Qualifier of the <code>SiteOneCategoryAttributes.attributeName</code> attribute **/
	public static final String ATTRIBUTENAME = "attributeName";
	/** Qualifier of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute **/
	public static final String BASEVARIANTATTRIBUTE = "baseVariantAttribute";
	/** Qualifier of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute **/
	public static final String FACETATTRIBUTE = "facetAttribute";
	/** Qualifier of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute **/
	public static final String HIGHLIGHTATTRIBUTE = "highlightAttribute";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ID, AttributeMode.INITIAL);
		tmp.put(ATTRIBUTECODE, AttributeMode.INITIAL);
		tmp.put(ATTRIBUTENAME, AttributeMode.INITIAL);
		tmp.put(BASEVARIANTATTRIBUTE, AttributeMode.INITIAL);
		tmp.put(FACETATTRIBUTE, AttributeMode.INITIAL);
		tmp.put(HIGHLIGHTATTRIBUTE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.attributeCode</code> attribute.
	 * @return the attributeCode
	 */
	public String getAttributeCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "attributeCode".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.attributeCode</code> attribute.
	 * @return the attributeCode
	 */
	public String getAttributeCode()
	{
		return getAttributeCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.attributeCode</code> attribute. 
	 * @param value the attributeCode
	 */
	public void setAttributeCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "attributeCode".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.attributeCode</code> attribute. 
	 * @param value the attributeCode
	 */
	public void setAttributeCode(final String value)
	{
		setAttributeCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.attributeName</code> attribute.
	 * @return the attributeName
	 */
	public String getAttributeName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "attributeName".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.attributeName</code> attribute.
	 * @return the attributeName
	 */
	public String getAttributeName()
	{
		return getAttributeName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.attributeName</code> attribute. 
	 * @param value the attributeName
	 */
	public void setAttributeName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "attributeName".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.attributeName</code> attribute. 
	 * @param value the attributeName
	 */
	public void setAttributeName(final String value)
	{
		setAttributeName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute.
	 * @return the baseVariantAttribute
	 */
	public Boolean isBaseVariantAttribute(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, "baseVariantAttribute".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute.
	 * @return the baseVariantAttribute
	 */
	public Boolean isBaseVariantAttribute()
	{
		return isBaseVariantAttribute( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute. 
	 * @return the baseVariantAttribute
	 */
	public boolean isBaseVariantAttributeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isBaseVariantAttribute( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute. 
	 * @return the baseVariantAttribute
	 */
	public boolean isBaseVariantAttributeAsPrimitive()
	{
		return isBaseVariantAttributeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute. 
	 * @param value the baseVariantAttribute
	 */
	public void setBaseVariantAttribute(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, "baseVariantAttribute".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute. 
	 * @param value the baseVariantAttribute
	 */
	public void setBaseVariantAttribute(final Boolean value)
	{
		setBaseVariantAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute. 
	 * @param value the baseVariantAttribute
	 */
	public void setBaseVariantAttribute(final SessionContext ctx, final boolean value)
	{
		setBaseVariantAttribute( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.baseVariantAttribute</code> attribute. 
	 * @param value the baseVariantAttribute
	 */
	public void setBaseVariantAttribute(final boolean value)
	{
		setBaseVariantAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute.
	 * @return the facetAttribute
	 */
	public Boolean isFacetAttribute(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, "facetAttribute".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute.
	 * @return the facetAttribute
	 */
	public Boolean isFacetAttribute()
	{
		return isFacetAttribute( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute. 
	 * @return the facetAttribute
	 */
	public boolean isFacetAttributeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isFacetAttribute( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute. 
	 * @return the facetAttribute
	 */
	public boolean isFacetAttributeAsPrimitive()
	{
		return isFacetAttributeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute. 
	 * @param value the facetAttribute
	 */
	public void setFacetAttribute(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, "facetAttribute".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute. 
	 * @param value the facetAttribute
	 */
	public void setFacetAttribute(final Boolean value)
	{
		setFacetAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute. 
	 * @param value the facetAttribute
	 */
	public void setFacetAttribute(final SessionContext ctx, final boolean value)
	{
		setFacetAttribute( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.facetAttribute</code> attribute. 
	 * @param value the facetAttribute
	 */
	public void setFacetAttribute(final boolean value)
	{
		setFacetAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute.
	 * @return the highlightAttribute
	 */
	public Boolean isHighlightAttribute(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, "highlightAttribute".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute.
	 * @return the highlightAttribute
	 */
	public Boolean isHighlightAttribute()
	{
		return isHighlightAttribute( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute. 
	 * @return the highlightAttribute
	 */
	public boolean isHighlightAttributeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHighlightAttribute( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute. 
	 * @return the highlightAttribute
	 */
	public boolean isHighlightAttributeAsPrimitive()
	{
		return isHighlightAttributeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute. 
	 * @param value the highlightAttribute
	 */
	public void setHighlightAttribute(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, "highlightAttribute".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute. 
	 * @param value the highlightAttribute
	 */
	public void setHighlightAttribute(final Boolean value)
	{
		setHighlightAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute. 
	 * @param value the highlightAttribute
	 */
	public void setHighlightAttribute(final SessionContext ctx, final boolean value)
	{
		setHighlightAttribute( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.highlightAttribute</code> attribute. 
	 * @param value the highlightAttribute
	 */
	public void setHighlightAttribute(final boolean value)
	{
		setHighlightAttribute( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.id</code> attribute.
	 * @return the id
	 */
	public String getId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "id".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCategoryAttributes.id</code> attribute.
	 * @return the id
	 */
	public String getId()
	{
		return getId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "id".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCategoryAttributes.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final String value)
	{
		setId( getSession().getSessionContext(), value );
	}
	
}
