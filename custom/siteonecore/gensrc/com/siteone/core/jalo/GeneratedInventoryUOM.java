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
 * Generated class for type {@link com.siteone.core.jalo.InventoryUOM InventoryUOM}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedInventoryUOM extends GenericItem
{
	/** Qualifier of the <code>InventoryUOM.inventoryUOMID</code> attribute **/
	public static final String INVENTORYUOMID = "inventoryUOMID";
	/** Qualifier of the <code>InventoryUOM.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>InventoryUOM.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>InventoryUOM.inventoryMultiplier</code> attribute **/
	public static final String INVENTORYMULTIPLIER = "inventoryMultiplier";
	/** Qualifier of the <code>InventoryUOM.parentInventoryUOMID</code> attribute **/
	public static final String PARENTINVENTORYUOMID = "parentInventoryUOMID";
	/** Qualifier of the <code>InventoryUOM.inventoryUOMDesc</code> attribute **/
	public static final String INVENTORYUOMDESC = "inventoryUOMDesc";
	/** Qualifier of the <code>InventoryUOM.measure</code> attribute **/
	public static final String MEASURE = "measure";
	/** Qualifier of the <code>InventoryUOM.hideUOMOnline</code> attribute **/
	public static final String HIDEUOMONLINE = "hideUOMOnline";
	/** Qualifier of the <code>InventoryUOM.productSource</code> attribute **/
	public static final String PRODUCTSOURCE = "productSource";
	/**
	* {@link OneToManyHandler} for handling 1:n PRODUCTSOURCE's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<Product> PRODUCTSOURCEHANDLER = new OneToManyHandler<Product>(
	CoreConstants.TC.PRODUCT,
	false,
	"InventoryUOM",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(INVENTORYUOMID, AttributeMode.INITIAL);
		tmp.put(NAME, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(INVENTORYMULTIPLIER, AttributeMode.INITIAL);
		tmp.put(PARENTINVENTORYUOMID, AttributeMode.INITIAL);
		tmp.put(INVENTORYUOMDESC, AttributeMode.INITIAL);
		tmp.put(MEASURE, AttributeMode.INITIAL);
		tmp.put(HIDEUOMONLINE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.hideUOMOnline</code> attribute.
	 * @return the hideUOMOnline
	 */
	public Boolean isHideUOMOnline(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HIDEUOMONLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.hideUOMOnline</code> attribute.
	 * @return the hideUOMOnline
	 */
	public Boolean isHideUOMOnline()
	{
		return isHideUOMOnline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.hideUOMOnline</code> attribute. 
	 * @return the hideUOMOnline
	 */
	public boolean isHideUOMOnlineAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHideUOMOnline( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.hideUOMOnline</code> attribute. 
	 * @return the hideUOMOnline
	 */
	public boolean isHideUOMOnlineAsPrimitive()
	{
		return isHideUOMOnlineAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.hideUOMOnline</code> attribute. 
	 * @param value the hideUOMOnline
	 */
	public void setHideUOMOnline(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HIDEUOMONLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.hideUOMOnline</code> attribute. 
	 * @param value the hideUOMOnline
	 */
	public void setHideUOMOnline(final Boolean value)
	{
		setHideUOMOnline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.hideUOMOnline</code> attribute. 
	 * @param value the hideUOMOnline
	 */
	public void setHideUOMOnline(final SessionContext ctx, final boolean value)
	{
		setHideUOMOnline( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.hideUOMOnline</code> attribute. 
	 * @param value the hideUOMOnline
	 */
	public void setHideUOMOnline(final boolean value)
	{
		setHideUOMOnline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryMultiplier</code> attribute.
	 * @return the inventoryMultiplier
	 */
	public Float getInventoryMultiplier(final SessionContext ctx)
	{
		return (Float)getProperty( ctx, INVENTORYMULTIPLIER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryMultiplier</code> attribute.
	 * @return the inventoryMultiplier
	 */
	public Float getInventoryMultiplier()
	{
		return getInventoryMultiplier( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryMultiplier</code> attribute. 
	 * @return the inventoryMultiplier
	 */
	public float getInventoryMultiplierAsPrimitive(final SessionContext ctx)
	{
		Float value = getInventoryMultiplier( ctx );
		return value != null ? value.floatValue() : 0.0f;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryMultiplier</code> attribute. 
	 * @return the inventoryMultiplier
	 */
	public float getInventoryMultiplierAsPrimitive()
	{
		return getInventoryMultiplierAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final SessionContext ctx, final Float value)
	{
		setProperty(ctx, INVENTORYMULTIPLIER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final Float value)
	{
		setInventoryMultiplier( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final SessionContext ctx, final float value)
	{
		setInventoryMultiplier( ctx,Float.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final float value)
	{
		setInventoryMultiplier( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryUOMDesc</code> attribute.
	 * @return the inventoryUOMDesc
	 */
	public String getInventoryUOMDesc(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVENTORYUOMDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryUOMDesc</code> attribute.
	 * @return the inventoryUOMDesc
	 */
	public String getInventoryUOMDesc()
	{
		return getInventoryUOMDesc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryUOMDesc</code> attribute. 
	 * @param value the inventoryUOMDesc
	 */
	public void setInventoryUOMDesc(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVENTORYUOMDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryUOMDesc</code> attribute. 
	 * @param value the inventoryUOMDesc
	 */
	public void setInventoryUOMDesc(final String value)
	{
		setInventoryUOMDesc( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryUOMID</code> attribute.
	 * @return the inventoryUOMID
	 */
	public Integer getInventoryUOMID(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, INVENTORYUOMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryUOMID</code> attribute.
	 * @return the inventoryUOMID
	 */
	public Integer getInventoryUOMID()
	{
		return getInventoryUOMID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryUOMID</code> attribute. 
	 * @return the inventoryUOMID
	 */
	public int getInventoryUOMIDAsPrimitive(final SessionContext ctx)
	{
		Integer value = getInventoryUOMID( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.inventoryUOMID</code> attribute. 
	 * @return the inventoryUOMID
	 */
	public int getInventoryUOMIDAsPrimitive()
	{
		return getInventoryUOMIDAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, INVENTORYUOMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final Integer value)
	{
		setInventoryUOMID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final SessionContext ctx, final int value)
	{
		setInventoryUOMID( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.inventoryUOMID</code> attribute. 
	 * @param value the inventoryUOMID
	 */
	public void setInventoryUOMID(final int value)
	{
		setInventoryUOMID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.measure</code> attribute.
	 * @return the measure
	 */
	public String getMeasure(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MEASURE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.measure</code> attribute.
	 * @return the measure
	 */
	public String getMeasure()
	{
		return getMeasure( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.measure</code> attribute. 
	 * @param value the measure
	 */
	public void setMeasure(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MEASURE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.measure</code> attribute. 
	 * @param value the measure
	 */
	public void setMeasure(final String value)
	{
		setMeasure( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute.
	 * @return the parentInventoryUOMID
	 */
	public Integer getParentInventoryUOMID(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, PARENTINVENTORYUOMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute.
	 * @return the parentInventoryUOMID
	 */
	public Integer getParentInventoryUOMID()
	{
		return getParentInventoryUOMID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute. 
	 * @return the parentInventoryUOMID
	 */
	public int getParentInventoryUOMIDAsPrimitive(final SessionContext ctx)
	{
		Integer value = getParentInventoryUOMID( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute. 
	 * @return the parentInventoryUOMID
	 */
	public int getParentInventoryUOMIDAsPrimitive()
	{
		return getParentInventoryUOMIDAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute. 
	 * @param value the parentInventoryUOMID
	 */
	public void setParentInventoryUOMID(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, PARENTINVENTORYUOMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute. 
	 * @param value the parentInventoryUOMID
	 */
	public void setParentInventoryUOMID(final Integer value)
	{
		setParentInventoryUOMID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute. 
	 * @param value the parentInventoryUOMID
	 */
	public void setParentInventoryUOMID(final SessionContext ctx, final int value)
	{
		setParentInventoryUOMID( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.parentInventoryUOMID</code> attribute. 
	 * @param value the parentInventoryUOMID
	 */
	public void setParentInventoryUOMID(final int value)
	{
		setParentInventoryUOMID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.productSource</code> attribute.
	 * @return the productSource
	 */
	public Collection<Product> getProductSource(final SessionContext ctx)
	{
		return PRODUCTSOURCEHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUOM.productSource</code> attribute.
	 * @return the productSource
	 */
	public Collection<Product> getProductSource()
	{
		return getProductSource( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.productSource</code> attribute. 
	 * @param value the productSource
	 */
	public void setProductSource(final SessionContext ctx, final Collection<Product> value)
	{
		PRODUCTSOURCEHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUOM.productSource</code> attribute. 
	 * @param value the productSource
	 */
	public void setProductSource(final Collection<Product> value)
	{
		setProductSource( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to productSource. 
	 * @param value the item to add to productSource
	 */
	public void addToProductSource(final SessionContext ctx, final Product value)
	{
		PRODUCTSOURCEHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to productSource. 
	 * @param value the item to add to productSource
	 */
	public void addToProductSource(final Product value)
	{
		addToProductSource( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from productSource. 
	 * @param value the item to remove from productSource
	 */
	public void removeFromProductSource(final SessionContext ctx, final Product value)
	{
		PRODUCTSOURCEHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from productSource. 
	 * @param value the item to remove from productSource
	 */
	public void removeFromProductSource(final Product value)
	{
		removeFromProductSource( getSession().getSessionContext(), value );
	}
	
}
