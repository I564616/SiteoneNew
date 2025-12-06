/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pcm.jalo;

import com.siteone.pcm.constants.SiteonepcmConstants;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.media.AbstractMedia;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.util.PartOfHandler;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type <code>SiteonepcmManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteonepcmManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("itemNumber", AttributeMode.INITIAL);
		tmp.put("productType", AttributeMode.INITIAL);
		tmp.put("case", AttributeMode.INITIAL);
		tmp.put("purchPkg", AttributeMode.INITIAL);
		tmp.put("weight", AttributeMode.INITIAL);
		tmp.put("uom", AttributeMode.INITIAL);
		tmp.put("form", AttributeMode.INITIAL);
		tmp.put("isLescoBranded", AttributeMode.INITIAL);
		tmp.put("vendorEDINumber", AttributeMode.INITIAL);
		tmp.put("productBrandName", AttributeMode.INITIAL);
		tmp.put("subBrand", AttributeMode.INITIAL);
		tmp.put("vendorPartNumber", AttributeMode.INITIAL);
		tmp.put("upcCode", AttributeMode.INITIAL);
		tmp.put("productShortDesc", AttributeMode.INITIAL);
		tmp.put("productLongDesc", AttributeMode.INITIAL);
		tmp.put("isSdsRequired", AttributeMode.INITIAL);
		tmp.put("isRegulatedItem", AttributeMode.INITIAL);
		tmp.put("isProductDiscontinued", AttributeMode.INITIAL);
		tmp.put("productState", AttributeMode.INITIAL);
		tmp.put("sds", AttributeMode.INITIAL);
		tmp.put("savedListProductComment", AttributeMode.INITIAL);
		tmp.put("masterPckQtyinUnits", AttributeMode.INITIAL);
		tmp.put("singleCaseQtyinUnits", AttributeMode.INITIAL);
		tmp.put("isProductSoldbyUom", AttributeMode.INITIAL);
		tmp.put("isDirectShippedProduct", AttributeMode.INITIAL);
		tmp.put("featureBullets", AttributeMode.INITIAL);
		tmp.put("applications", AttributeMode.INITIAL);
		tmp.put("standardsApprovals", AttributeMode.INITIAL);
		tmp.put("categoryCode", AttributeMode.INITIAL);
		tmp.put("inventoryUOMID", AttributeMode.INITIAL);
		tmp.put("internalVariantAttribute", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.product.Product", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("SalesCategoryMapping", AttributeMode.INITIAL);
		tmp.put("urlLink", AttributeMode.INITIAL);
		tmp.put("productCount", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.category.jalo.Category", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("lastExecutionTime", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.cronjob.jalo.CronJob", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("sds", AttributeMode.INITIAL);
		tmp.put("label", AttributeMode.INITIAL);
		tmp.put("galleryImages", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.media.Media", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.applications</code> attribute.
	 * @return the applications
	 */
	public String getApplications(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.APPLICATIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.applications</code> attribute.
	 * @return the applications
	 */
	public String getApplications(final Product item)
	{
		return getApplications( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.applications</code> attribute. 
	 * @param value the applications
	 */
	public void setApplications(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.APPLICATIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.applications</code> attribute. 
	 * @param value the applications
	 */
	public void setApplications(final Product item, final String value)
	{
		setApplications( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.case</code> attribute.
	 * @return the case
	 */
	public Float getCase(final SessionContext ctx, final Product item)
	{
		return (Float)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.CASE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.case</code> attribute.
	 * @return the case
	 */
	public Float getCase(final Product item)
	{
		return getCase( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.case</code> attribute. 
	 * @return the case
	 */
	public float getCaseAsPrimitive(final SessionContext ctx, final Product item)
	{
		Float value = getCase( ctx,item );
		return value != null ? value.floatValue() : 0.0f;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.case</code> attribute. 
	 * @return the case
	 */
	public float getCaseAsPrimitive(final Product item)
	{
		return getCaseAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.case</code> attribute. 
	 * @param value the case
	 */
	public void setCase(final SessionContext ctx, final Product item, final Float value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.CASE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.case</code> attribute. 
	 * @param value the case
	 */
	public void setCase(final Product item, final Float value)
	{
		setCase( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.case</code> attribute. 
	 * @param value the case
	 */
	public void setCase(final SessionContext ctx, final Product item, final float value)
	{
		setCase( ctx, item, Float.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.case</code> attribute. 
	 * @param value the case
	 */
	public void setCase(final Product item, final float value)
	{
		setCase( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.categoryCode</code> attribute.
	 * @return the categoryCode
	 */
	public String getCategoryCode(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.CATEGORYCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.categoryCode</code> attribute.
	 * @return the categoryCode
	 */
	public String getCategoryCode(final Product item)
	{
		return getCategoryCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.categoryCode</code> attribute. 
	 * @param value the categoryCode
	 */
	public void setCategoryCode(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.CATEGORYCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.categoryCode</code> attribute. 
	 * @param value the categoryCode
	 */
	public void setCategoryCode(final Product item, final String value)
	{
		setCategoryCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.featureBullets</code> attribute.
	 * @return the featureBullets
	 */
	public String getFeatureBullets(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.FEATUREBULLETS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.featureBullets</code> attribute.
	 * @return the featureBullets
	 */
	public String getFeatureBullets(final Product item)
	{
		return getFeatureBullets( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.featureBullets</code> attribute. 
	 * @param value the featureBullets
	 */
	public void setFeatureBullets(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.FEATUREBULLETS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.featureBullets</code> attribute. 
	 * @param value the featureBullets
	 */
	public void setFeatureBullets(final Product item, final String value)
	{
		setFeatureBullets( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.form</code> attribute.
	 * @return the form
	 */
	public String getForm(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.FORM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.form</code> attribute.
	 * @return the form
	 */
	public String getForm(final Product item)
	{
		return getForm( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.form</code> attribute. 
	 * @param value the form
	 */
	public void setForm(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.FORM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.form</code> attribute. 
	 * @param value the form
	 */
	public void setForm(final Product item, final String value)
	{
		setForm( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.galleryImages</code> attribute.
	 * @return the galleryImages
	 */
	public List<Media> getGalleryImages(final SessionContext ctx, final Media item)
	{
		List<Media> coll = (List<Media>)item.getProperty( ctx, SiteonepcmConstants.Attributes.Media.GALLERYIMAGES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.galleryImages</code> attribute.
	 * @return the galleryImages
	 */
	public List<Media> getGalleryImages(final Media item)
	{
		return getGalleryImages( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.galleryImages</code> attribute. 
	 * @param value the galleryImages
	 */
	public void setGalleryImages(final SessionContext ctx, final Media item, final List<Media> value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Media.GALLERYIMAGES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.galleryImages</code> attribute. 
	 * @param value the galleryImages
	 */
	public void setGalleryImages(final Media item, final List<Media> value)
	{
		setGalleryImages( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return SiteonepcmConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.internalVariantAttribute</code> attribute.
	 * @return the internalVariantAttribute
	 */
	public String getInternalVariantAttribute(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.INTERNALVARIANTATTRIBUTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.internalVariantAttribute</code> attribute.
	 * @return the internalVariantAttribute
	 */
	public String getInternalVariantAttribute(final Product item)
	{
		return getInternalVariantAttribute( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.internalVariantAttribute</code> attribute. 
	 * @param value the internalVariantAttribute
	 */
	public void setInternalVariantAttribute(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.INTERNALVARIANTATTRIBUTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.internalVariantAttribute</code> attribute. 
	 * @param value the internalVariantAttribute
	 */
	public void setInternalVariantAttribute(final Product item, final String value)
	{
		setInternalVariantAttribute( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryUOMID</code> attribute.
	 * @return the inventoryUOMID
	 */
	public Integer getInventoryUOMID(final SessionContext ctx, final Product item)
	{
		return (Integer)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.INVENTORYUOMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryUOMID</code> attribute.
	 * @return the inventoryUOMID
	 */
	public Integer getInventoryUOMID(final Product item)
	{
		return getInventoryUOMID( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryUOMID</code> attribute. 
	 * @return the inventoryUOMID
	 */
	public int getInventoryUOMIDAsPrimitive(final SessionContext ctx, final Product item)
	{
		Integer value = getInventoryUOMID( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryUOMID</code> attribute. 
	 * @return the inventoryUOMID
	 */
	public int getInventoryUOMIDAsPrimitive(final Product item)
	{
		return getInventoryUOMIDAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final SessionContext ctx, final Product item, final Integer value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.INVENTORYUOMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final Product item, final Integer value)
	{
		setInventoryUOMID( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final SessionContext ctx, final Product item, final int value)
	{
		setInventoryUOMID( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final Product item, final int value)
	{
		setInventoryUOMID( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDirectShippedProduct</code> attribute.
	 * @return the isDirectShippedProduct
	 */
	public Boolean isIsDirectShippedProduct(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ISDIRECTSHIPPEDPRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDirectShippedProduct</code> attribute.
	 * @return the isDirectShippedProduct
	 */
	public Boolean isIsDirectShippedProduct(final Product item)
	{
		return isIsDirectShippedProduct( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDirectShippedProduct</code> attribute. 
	 * @return the isDirectShippedProduct
	 */
	public boolean isIsDirectShippedProductAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsDirectShippedProduct( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDirectShippedProduct</code> attribute. 
	 * @return the isDirectShippedProduct
	 */
	public boolean isIsDirectShippedProductAsPrimitive(final Product item)
	{
		return isIsDirectShippedProductAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDirectShippedProduct</code> attribute. 
	 * @param value the isDirectShippedProduct
	 */
	public void setIsDirectShippedProduct(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ISDIRECTSHIPPEDPRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDirectShippedProduct</code> attribute. 
	 * @param value the isDirectShippedProduct
	 */
	public void setIsDirectShippedProduct(final Product item, final Boolean value)
	{
		setIsDirectShippedProduct( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDirectShippedProduct</code> attribute. 
	 * @param value the isDirectShippedProduct
	 */
	public void setIsDirectShippedProduct(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsDirectShippedProduct( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDirectShippedProduct</code> attribute. 
	 * @param value the isDirectShippedProduct
	 */
	public void setIsDirectShippedProduct(final Product item, final boolean value)
	{
		setIsDirectShippedProduct( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isLescoBranded</code> attribute.
	 * @return the isLescoBranded
	 */
	public Boolean isIsLescoBranded(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ISLESCOBRANDED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isLescoBranded</code> attribute.
	 * @return the isLescoBranded
	 */
	public Boolean isIsLescoBranded(final Product item)
	{
		return isIsLescoBranded( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isLescoBranded</code> attribute. 
	 * @return the isLescoBranded
	 */
	public boolean isIsLescoBrandedAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsLescoBranded( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isLescoBranded</code> attribute. 
	 * @return the isLescoBranded
	 */
	public boolean isIsLescoBrandedAsPrimitive(final Product item)
	{
		return isIsLescoBrandedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isLescoBranded</code> attribute. 
	 * @param value the isLescoBranded
	 */
	public void setIsLescoBranded(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ISLESCOBRANDED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isLescoBranded</code> attribute. 
	 * @param value the isLescoBranded
	 */
	public void setIsLescoBranded(final Product item, final Boolean value)
	{
		setIsLescoBranded( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isLescoBranded</code> attribute. 
	 * @param value the isLescoBranded
	 */
	public void setIsLescoBranded(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsLescoBranded( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isLescoBranded</code> attribute. 
	 * @param value the isLescoBranded
	 */
	public void setIsLescoBranded(final Product item, final boolean value)
	{
		setIsLescoBranded( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductDiscontinued</code> attribute.
	 * @return the isProductDiscontinued
	 */
	public Boolean isIsProductDiscontinued(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ISPRODUCTDISCONTINUED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductDiscontinued</code> attribute.
	 * @return the isProductDiscontinued
	 */
	public Boolean isIsProductDiscontinued(final Product item)
	{
		return isIsProductDiscontinued( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductDiscontinued</code> attribute. 
	 * @return the isProductDiscontinued
	 */
	public boolean isIsProductDiscontinuedAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsProductDiscontinued( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductDiscontinued</code> attribute. 
	 * @return the isProductDiscontinued
	 */
	public boolean isIsProductDiscontinuedAsPrimitive(final Product item)
	{
		return isIsProductDiscontinuedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductDiscontinued</code> attribute. 
	 * @param value the isProductDiscontinued
	 */
	public void setIsProductDiscontinued(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ISPRODUCTDISCONTINUED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductDiscontinued</code> attribute. 
	 * @param value the isProductDiscontinued
	 */
	public void setIsProductDiscontinued(final Product item, final Boolean value)
	{
		setIsProductDiscontinued( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductDiscontinued</code> attribute. 
	 * @param value the isProductDiscontinued
	 */
	public void setIsProductDiscontinued(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsProductDiscontinued( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductDiscontinued</code> attribute. 
	 * @param value the isProductDiscontinued
	 */
	public void setIsProductDiscontinued(final Product item, final boolean value)
	{
		setIsProductDiscontinued( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductSoldbyUom</code> attribute.
	 * @return the isProductSoldbyUom
	 */
	public Boolean isIsProductSoldbyUom(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ISPRODUCTSOLDBYUOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductSoldbyUom</code> attribute.
	 * @return the isProductSoldbyUom
	 */
	public Boolean isIsProductSoldbyUom(final Product item)
	{
		return isIsProductSoldbyUom( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductSoldbyUom</code> attribute. 
	 * @return the isProductSoldbyUom
	 */
	public boolean isIsProductSoldbyUomAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsProductSoldbyUom( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductSoldbyUom</code> attribute. 
	 * @return the isProductSoldbyUom
	 */
	public boolean isIsProductSoldbyUomAsPrimitive(final Product item)
	{
		return isIsProductSoldbyUomAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductSoldbyUom</code> attribute. 
	 * @param value the isProductSoldbyUom
	 */
	public void setIsProductSoldbyUom(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ISPRODUCTSOLDBYUOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductSoldbyUom</code> attribute. 
	 * @param value the isProductSoldbyUom
	 */
	public void setIsProductSoldbyUom(final Product item, final Boolean value)
	{
		setIsProductSoldbyUom( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductSoldbyUom</code> attribute. 
	 * @param value the isProductSoldbyUom
	 */
	public void setIsProductSoldbyUom(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsProductSoldbyUom( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductSoldbyUom</code> attribute. 
	 * @param value the isProductSoldbyUom
	 */
	public void setIsProductSoldbyUom(final Product item, final boolean value)
	{
		setIsProductSoldbyUom( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isRegulatedItem</code> attribute.
	 * @return the isRegulatedItem
	 */
	public Boolean isIsRegulatedItem(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ISREGULATEDITEM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isRegulatedItem</code> attribute.
	 * @return the isRegulatedItem
	 */
	public Boolean isIsRegulatedItem(final Product item)
	{
		return isIsRegulatedItem( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isRegulatedItem</code> attribute. 
	 * @return the isRegulatedItem
	 */
	public boolean isIsRegulatedItemAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsRegulatedItem( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isRegulatedItem</code> attribute. 
	 * @return the isRegulatedItem
	 */
	public boolean isIsRegulatedItemAsPrimitive(final Product item)
	{
		return isIsRegulatedItemAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isRegulatedItem</code> attribute. 
	 * @param value the isRegulatedItem
	 */
	public void setIsRegulatedItem(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ISREGULATEDITEM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isRegulatedItem</code> attribute. 
	 * @param value the isRegulatedItem
	 */
	public void setIsRegulatedItem(final Product item, final Boolean value)
	{
		setIsRegulatedItem( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isRegulatedItem</code> attribute. 
	 * @param value the isRegulatedItem
	 */
	public void setIsRegulatedItem(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsRegulatedItem( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isRegulatedItem</code> attribute. 
	 * @param value the isRegulatedItem
	 */
	public void setIsRegulatedItem(final Product item, final boolean value)
	{
		setIsRegulatedItem( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isSdsRequired</code> attribute.
	 * @return the isSdsRequired
	 */
	public Boolean isIsSdsRequired(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ISSDSREQUIRED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isSdsRequired</code> attribute.
	 * @return the isSdsRequired
	 */
	public Boolean isIsSdsRequired(final Product item)
	{
		return isIsSdsRequired( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isSdsRequired</code> attribute. 
	 * @return the isSdsRequired
	 */
	public boolean isIsSdsRequiredAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsSdsRequired( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isSdsRequired</code> attribute. 
	 * @return the isSdsRequired
	 */
	public boolean isIsSdsRequiredAsPrimitive(final Product item)
	{
		return isIsSdsRequiredAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isSdsRequired</code> attribute. 
	 * @param value the isSdsRequired
	 */
	public void setIsSdsRequired(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ISSDSREQUIRED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isSdsRequired</code> attribute. 
	 * @param value the isSdsRequired
	 */
	public void setIsSdsRequired(final Product item, final Boolean value)
	{
		setIsSdsRequired( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isSdsRequired</code> attribute. 
	 * @param value the isSdsRequired
	 */
	public void setIsSdsRequired(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsSdsRequired( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isSdsRequired</code> attribute. 
	 * @param value the isSdsRequired
	 */
	public void setIsSdsRequired(final Product item, final boolean value)
	{
		setIsSdsRequired( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.ITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber(final Product item)
	{
		return getItemNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.ITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final Product item, final String value)
	{
		setItemNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.label</code> attribute.
	 * @return the label
	 */
	public String getLabel(final SessionContext ctx, final Media item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedMedia.getLabel requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteonepcmConstants.Attributes.Media.LABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.label</code> attribute.
	 * @return the label
	 */
	public String getLabel(final Media item)
	{
		return getLabel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.label</code> attribute. 
	 * @return the localized label
	 */
	public Map<Language,String> getAllLabel(final SessionContext ctx, final Media item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteonepcmConstants.Attributes.Media.LABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.label</code> attribute. 
	 * @return the localized label
	 */
	public Map<Language,String> getAllLabel(final Media item)
	{
		return getAllLabel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.label</code> attribute. 
	 * @param value the label
	 */
	public void setLabel(final SessionContext ctx, final Media item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedMedia.setLabel requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteonepcmConstants.Attributes.Media.LABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.label</code> attribute. 
	 * @param value the label
	 */
	public void setLabel(final Media item, final String value)
	{
		setLabel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.label</code> attribute. 
	 * @param value the label
	 */
	public void setAllLabel(final SessionContext ctx, final Media item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteonepcmConstants.Attributes.Media.LABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.label</code> attribute. 
	 * @param value the label
	 */
	public void setAllLabel(final Media item, final Map<Language,String> value)
	{
		setAllLabel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CronJob.lastExecutionTime</code> attribute.
	 * @return the lastExecutionTime - This is the date when job was executed last.
	 */
	public Date getLastExecutionTime(final SessionContext ctx, final CronJob item)
	{
		return (Date)item.getProperty( ctx, SiteonepcmConstants.Attributes.CronJob.LASTEXECUTIONTIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CronJob.lastExecutionTime</code> attribute.
	 * @return the lastExecutionTime - This is the date when job was executed last.
	 */
	public Date getLastExecutionTime(final CronJob item)
	{
		return getLastExecutionTime( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CronJob.lastExecutionTime</code> attribute. 
	 * @param value the lastExecutionTime - This is the date when job was executed last.
	 */
	public void setLastExecutionTime(final SessionContext ctx, final CronJob item, final Date value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.CronJob.LASTEXECUTIONTIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CronJob.lastExecutionTime</code> attribute. 
	 * @param value the lastExecutionTime - This is the date when job was executed last.
	 */
	public void setLastExecutionTime(final CronJob item, final Date value)
	{
		setLastExecutionTime( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.masterPckQtyinUnits</code> attribute.
	 * @return the masterPckQtyinUnits
	 */
	public String getMasterPckQtyinUnits(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.MASTERPCKQTYINUNITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.masterPckQtyinUnits</code> attribute.
	 * @return the masterPckQtyinUnits
	 */
	public String getMasterPckQtyinUnits(final Product item)
	{
		return getMasterPckQtyinUnits( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.masterPckQtyinUnits</code> attribute. 
	 * @param value the masterPckQtyinUnits
	 */
	public void setMasterPckQtyinUnits(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.MASTERPCKQTYINUNITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.masterPckQtyinUnits</code> attribute. 
	 * @param value the masterPckQtyinUnits
	 */
	public void setMasterPckQtyinUnits(final Product item, final String value)
	{
		setMasterPckQtyinUnits( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productBrandName</code> attribute.
	 * @return the productBrandName
	 */
	public EnumerationValue getProductBrandName(final SessionContext ctx, final Product item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.PRODUCTBRANDNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productBrandName</code> attribute.
	 * @return the productBrandName
	 */
	public EnumerationValue getProductBrandName(final Product item)
	{
		return getProductBrandName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productBrandName</code> attribute. 
	 * @param value the productBrandName
	 */
	public void setProductBrandName(final SessionContext ctx, final Product item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.PRODUCTBRANDNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productBrandName</code> attribute. 
	 * @param value the productBrandName
	 */
	public void setProductBrandName(final Product item, final EnumerationValue value)
	{
		setProductBrandName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.productCount</code> attribute.
	 * @return the productCount
	 */
	public Integer getProductCount(final SessionContext ctx, final Category item)
	{
		return (Integer)item.getProperty( ctx, SiteonepcmConstants.Attributes.Category.PRODUCTCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.productCount</code> attribute.
	 * @return the productCount
	 */
	public Integer getProductCount(final Category item)
	{
		return getProductCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.productCount</code> attribute. 
	 * @return the productCount
	 */
	public int getProductCountAsPrimitive(final SessionContext ctx, final Category item)
	{
		Integer value = getProductCount( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.productCount</code> attribute. 
	 * @return the productCount
	 */
	public int getProductCountAsPrimitive(final Category item)
	{
		return getProductCountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.productCount</code> attribute. 
	 * @param value the productCount
	 */
	public void setProductCount(final SessionContext ctx, final Category item, final Integer value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Category.PRODUCTCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.productCount</code> attribute. 
	 * @param value the productCount
	 */
	public void setProductCount(final Category item, final Integer value)
	{
		setProductCount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.productCount</code> attribute. 
	 * @param value the productCount
	 */
	public void setProductCount(final SessionContext ctx, final Category item, final int value)
	{
		setProductCount( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.productCount</code> attribute. 
	 * @param value the productCount
	 */
	public void setProductCount(final Category item, final int value)
	{
		setProductCount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productLongDesc</code> attribute.
	 * @return the productLongDesc
	 */
	public String getProductLongDesc(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.PRODUCTLONGDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productLongDesc</code> attribute.
	 * @return the productLongDesc
	 */
	public String getProductLongDesc(final Product item)
	{
		return getProductLongDesc( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productLongDesc</code> attribute. 
	 * @param value the productLongDesc
	 */
	public void setProductLongDesc(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.PRODUCTLONGDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productLongDesc</code> attribute. 
	 * @param value the productLongDesc
	 */
	public void setProductLongDesc(final Product item, final String value)
	{
		setProductLongDesc( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productShortDesc</code> attribute.
	 * @return the productShortDesc
	 */
	public String getProductShortDesc(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.PRODUCTSHORTDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productShortDesc</code> attribute.
	 * @return the productShortDesc
	 */
	public String getProductShortDesc(final Product item)
	{
		return getProductShortDesc( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productShortDesc</code> attribute. 
	 * @param value the productShortDesc
	 */
	public void setProductShortDesc(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.PRODUCTSHORTDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productShortDesc</code> attribute. 
	 * @param value the productShortDesc
	 */
	public void setProductShortDesc(final Product item, final String value)
	{
		setProductShortDesc( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productState</code> attribute.
	 * @return the productState
	 */
	public String getProductState(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.PRODUCTSTATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productState</code> attribute.
	 * @return the productState
	 */
	public String getProductState(final Product item)
	{
		return getProductState( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productState</code> attribute. 
	 * @param value the productState
	 */
	public void setProductState(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.PRODUCTSTATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productState</code> attribute. 
	 * @param value the productState
	 */
	public void setProductState(final Product item, final String value)
	{
		setProductState( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productType</code> attribute.
	 * @return the productType
	 */
	public String getProductType(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.PRODUCTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productType</code> attribute.
	 * @return the productType
	 */
	public String getProductType(final Product item)
	{
		return getProductType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productType</code> attribute. 
	 * @param value the productType
	 */
	public void setProductType(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.PRODUCTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productType</code> attribute. 
	 * @param value the productType
	 */
	public void setProductType(final Product item, final String value)
	{
		setProductType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.purchPkg</code> attribute.
	 * @return the purchPkg
	 */
	public Float getPurchPkg(final SessionContext ctx, final Product item)
	{
		return (Float)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.PURCHPKG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.purchPkg</code> attribute.
	 * @return the purchPkg
	 */
	public Float getPurchPkg(final Product item)
	{
		return getPurchPkg( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.purchPkg</code> attribute. 
	 * @return the purchPkg
	 */
	public float getPurchPkgAsPrimitive(final SessionContext ctx, final Product item)
	{
		Float value = getPurchPkg( ctx,item );
		return value != null ? value.floatValue() : 0.0f;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.purchPkg</code> attribute. 
	 * @return the purchPkg
	 */
	public float getPurchPkgAsPrimitive(final Product item)
	{
		return getPurchPkgAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.purchPkg</code> attribute. 
	 * @param value the purchPkg
	 */
	public void setPurchPkg(final SessionContext ctx, final Product item, final Float value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.PURCHPKG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.purchPkg</code> attribute. 
	 * @param value the purchPkg
	 */
	public void setPurchPkg(final Product item, final Float value)
	{
		setPurchPkg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.purchPkg</code> attribute. 
	 * @param value the purchPkg
	 */
	public void setPurchPkg(final SessionContext ctx, final Product item, final float value)
	{
		setPurchPkg( ctx, item, Float.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.purchPkg</code> attribute. 
	 * @param value the purchPkg
	 */
	public void setPurchPkg(final Product item, final float value)
	{
		setPurchPkg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.SalesCategoryMapping</code> attribute.
	 * @return the SalesCategoryMapping
	 */
	public String getSalesCategoryMapping(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Category.SALESCATEGORYMAPPING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.SalesCategoryMapping</code> attribute.
	 * @return the SalesCategoryMapping
	 */
	public String getSalesCategoryMapping(final Category item)
	{
		return getSalesCategoryMapping( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.SalesCategoryMapping</code> attribute. 
	 * @param value the SalesCategoryMapping
	 */
	public void setSalesCategoryMapping(final SessionContext ctx, final Category item, final String value)
	{
		new PartOfHandler<String>()
		{
			@Override
			protected String doGetValue(final SessionContext ctx)
			{
				return getSalesCategoryMapping( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final String _value)
			{
				final String value = _value;
				item.setProperty(ctx, SiteonepcmConstants.Attributes.Category.SALESCATEGORYMAPPING,value);
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.SalesCategoryMapping</code> attribute. 
	 * @param value the SalesCategoryMapping
	 */
	public void setSalesCategoryMapping(final Category item, final String value)
	{
		setSalesCategoryMapping( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.savedListProductComment</code> attribute.
	 * @return the savedListProductComment
	 */
	public String getSavedListProductComment(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.SAVEDLISTPRODUCTCOMMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.savedListProductComment</code> attribute.
	 * @return the savedListProductComment
	 */
	public String getSavedListProductComment(final Product item)
	{
		return getSavedListProductComment( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.savedListProductComment</code> attribute. 
	 * @param value the savedListProductComment
	 */
	public void setSavedListProductComment(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.SAVEDLISTPRODUCTCOMMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.savedListProductComment</code> attribute. 
	 * @param value the savedListProductComment
	 */
	public void setSavedListProductComment(final Product item, final String value)
	{
		setSavedListProductComment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.sds</code> attribute.
	 * @return the sds
	 */
	public String getSds(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.SDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.sds</code> attribute.
	 * @return the sds
	 */
	public String getSds(final Product item)
	{
		return getSds( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.sds</code> attribute. 
	 * @param value the sds
	 */
	public void setSds(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.SDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.sds</code> attribute. 
	 * @param value the sds
	 */
	public void setSds(final Product item, final String value)
	{
		setSds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.sds</code> attribute.
	 * @return the sds
	 */
	public String getSds(final SessionContext ctx, final Media item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Media.SDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.sds</code> attribute.
	 * @return the sds
	 */
	public String getSds(final Media item)
	{
		return getSds( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.sds</code> attribute. 
	 * @param value the sds
	 */
	public void setSds(final SessionContext ctx, final Media item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Media.SDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.sds</code> attribute. 
	 * @param value the sds
	 */
	public void setSds(final Media item, final String value)
	{
		setSds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.singleCaseQtyinUnits</code> attribute.
	 * @return the singleCaseQtyinUnits
	 */
	public String getSingleCaseQtyinUnits(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.SINGLECASEQTYINUNITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.singleCaseQtyinUnits</code> attribute.
	 * @return the singleCaseQtyinUnits
	 */
	public String getSingleCaseQtyinUnits(final Product item)
	{
		return getSingleCaseQtyinUnits( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.singleCaseQtyinUnits</code> attribute. 
	 * @param value the singleCaseQtyinUnits
	 */
	public void setSingleCaseQtyinUnits(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.SINGLECASEQTYINUNITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.singleCaseQtyinUnits</code> attribute. 
	 * @param value the singleCaseQtyinUnits
	 */
	public void setSingleCaseQtyinUnits(final Product item, final String value)
	{
		setSingleCaseQtyinUnits( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.standardsApprovals</code> attribute.
	 * @return the standardsApprovals
	 */
	public String getStandardsApprovals(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.STANDARDSAPPROVALS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.standardsApprovals</code> attribute.
	 * @return the standardsApprovals
	 */
	public String getStandardsApprovals(final Product item)
	{
		return getStandardsApprovals( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.standardsApprovals</code> attribute. 
	 * @param value the standardsApprovals
	 */
	public void setStandardsApprovals(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.STANDARDSAPPROVALS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.standardsApprovals</code> attribute. 
	 * @param value the standardsApprovals
	 */
	public void setStandardsApprovals(final Product item, final String value)
	{
		setStandardsApprovals( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.subBrand</code> attribute.
	 * @return the subBrand
	 */
	public String getSubBrand(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.SUBBRAND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.subBrand</code> attribute.
	 * @return the subBrand
	 */
	public String getSubBrand(final Product item)
	{
		return getSubBrand( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.subBrand</code> attribute. 
	 * @param value the subBrand
	 */
	public void setSubBrand(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.SUBBRAND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.subBrand</code> attribute. 
	 * @param value the subBrand
	 */
	public void setSubBrand(final Product item, final String value)
	{
		setSubBrand( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.uom</code> attribute.
	 * @return the uom
	 */
	public String getUom(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.UOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.uom</code> attribute.
	 * @return the uom
	 */
	public String getUom(final Product item)
	{
		return getUom( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.uom</code> attribute. 
	 * @param value the uom
	 */
	public void setUom(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.UOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.uom</code> attribute. 
	 * @param value the uom
	 */
	public void setUom(final Product item, final String value)
	{
		setUom( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.upcCode</code> attribute.
	 * @return the upcCode
	 */
	public Integer getUpcCode(final SessionContext ctx, final Product item)
	{
		return (Integer)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.UPCCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.upcCode</code> attribute.
	 * @return the upcCode
	 */
	public Integer getUpcCode(final Product item)
	{
		return getUpcCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.upcCode</code> attribute. 
	 * @return the upcCode
	 */
	public int getUpcCodeAsPrimitive(final SessionContext ctx, final Product item)
	{
		Integer value = getUpcCode( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.upcCode</code> attribute. 
	 * @return the upcCode
	 */
	public int getUpcCodeAsPrimitive(final Product item)
	{
		return getUpcCodeAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.upcCode</code> attribute. 
	 * @param value the upcCode
	 */
	public void setUpcCode(final SessionContext ctx, final Product item, final Integer value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.UPCCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.upcCode</code> attribute. 
	 * @param value the upcCode
	 */
	public void setUpcCode(final Product item, final Integer value)
	{
		setUpcCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.upcCode</code> attribute. 
	 * @param value the upcCode
	 */
	public void setUpcCode(final SessionContext ctx, final Product item, final int value)
	{
		setUpcCode( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.upcCode</code> attribute. 
	 * @param value the upcCode
	 */
	public void setUpcCode(final Product item, final int value)
	{
		setUpcCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.urlLink</code> attribute.
	 * @return the urlLink
	 */
	public String getUrlLink(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Category.URLLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.urlLink</code> attribute.
	 * @return the urlLink
	 */
	public String getUrlLink(final Category item)
	{
		return getUrlLink( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.urlLink</code> attribute. 
	 * @param value the urlLink
	 */
	public void setUrlLink(final SessionContext ctx, final Category item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Category.URLLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.urlLink</code> attribute. 
	 * @param value the urlLink
	 */
	public void setUrlLink(final Category item, final String value)
	{
		setUrlLink( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.vendorEDINumber</code> attribute.
	 * @return the vendorEDINumber
	 */
	public String getVendorEDINumber(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.VENDOREDINUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.vendorEDINumber</code> attribute.
	 * @return the vendorEDINumber
	 */
	public String getVendorEDINumber(final Product item)
	{
		return getVendorEDINumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.vendorEDINumber</code> attribute. 
	 * @param value the vendorEDINumber
	 */
	public void setVendorEDINumber(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.VENDOREDINUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.vendorEDINumber</code> attribute. 
	 * @param value the vendorEDINumber
	 */
	public void setVendorEDINumber(final Product item, final String value)
	{
		setVendorEDINumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.vendorPartNumber</code> attribute.
	 * @return the vendorPartNumber
	 */
	public String getVendorPartNumber(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.VENDORPARTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.vendorPartNumber</code> attribute.
	 * @return the vendorPartNumber
	 */
	public String getVendorPartNumber(final Product item)
	{
		return getVendorPartNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.vendorPartNumber</code> attribute. 
	 * @param value the vendorPartNumber
	 */
	public void setVendorPartNumber(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.VENDORPARTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.vendorPartNumber</code> attribute. 
	 * @param value the vendorPartNumber
	 */
	public void setVendorPartNumber(final Product item, final String value)
	{
		setVendorPartNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weight</code> attribute.
	 * @return the weight
	 */
	public Float getWeight(final SessionContext ctx, final Product item)
	{
		return (Float)item.getProperty( ctx, SiteonepcmConstants.Attributes.Product.WEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weight</code> attribute.
	 * @return the weight
	 */
	public Float getWeight(final Product item)
	{
		return getWeight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weight</code> attribute. 
	 * @return the weight
	 */
	public float getWeightAsPrimitive(final SessionContext ctx, final Product item)
	{
		Float value = getWeight( ctx,item );
		return value != null ? value.floatValue() : 0.0f;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weight</code> attribute. 
	 * @return the weight
	 */
	public float getWeightAsPrimitive(final Product item)
	{
		return getWeightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final SessionContext ctx, final Product item, final Float value)
	{
		item.setProperty(ctx, SiteonepcmConstants.Attributes.Product.WEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final Product item, final Float value)
	{
		setWeight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final SessionContext ctx, final Product item, final float value)
	{
		setWeight( ctx, item, Float.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final Product item, final float value)
	{
		setWeight( getSession().getSessionContext(), item, value );
	}
	
}
