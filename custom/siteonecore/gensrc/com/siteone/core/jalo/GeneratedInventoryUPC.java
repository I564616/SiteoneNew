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
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.InventoryUPC InventoryUPC}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedInventoryUPC extends GenericItem
{
	/** Qualifier of the <code>InventoryUPC.inventoryUPCID</code> attribute **/
	public static final String INVENTORYUPCID = "inventoryUPCID";
	/** Qualifier of the <code>InventoryUPC.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>InventoryUPC.inventoryMultiplier</code> attribute **/
	public static final String INVENTORYMULTIPLIER = "inventoryMultiplier";
	/** Qualifier of the <code>InventoryUPC.inventoryUPCDesc</code> attribute **/
	public static final String INVENTORYUPCDESC = "inventoryUPCDesc";
	/** Qualifier of the <code>InventoryUPC.hideUPCOnline</code> attribute **/
	public static final String HIDEUPCONLINE = "hideUPCOnline";
	/** Qualifier of the <code>InventoryUPC.vendorEDINumber</code> attribute **/
	public static final String VENDOREDINUMBER = "vendorEDINumber";
	/** Qualifier of the <code>InventoryUPC.vendorPartNumber</code> attribute **/
	public static final String VENDORPARTNUMBER = "vendorPartNumber";
	/** Qualifier of the <code>InventoryUPC.weight</code> attribute **/
	public static final String WEIGHT = "weight";
	/** Qualifier of the <code>InventoryUPC.length</code> attribute **/
	public static final String LENGTH = "length";
	/** Qualifier of the <code>InventoryUPC.height</code> attribute **/
	public static final String HEIGHT = "height";
	/** Qualifier of the <code>InventoryUPC.baseUPC</code> attribute **/
	public static final String BASEUPC = "baseUPC";
	/** Qualifier of the <code>InventoryUPC.fromPayload</code> attribute **/
	public static final String FROMPAYLOAD = "fromPayload";
	/** Qualifier of the <code>InventoryUPC.upcPimId</code> attribute **/
	public static final String UPCPIMID = "upcPimId";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(INVENTORYUPCID, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(INVENTORYMULTIPLIER, AttributeMode.INITIAL);
		tmp.put(INVENTORYUPCDESC, AttributeMode.INITIAL);
		tmp.put(HIDEUPCONLINE, AttributeMode.INITIAL);
		tmp.put(VENDOREDINUMBER, AttributeMode.INITIAL);
		tmp.put(VENDORPARTNUMBER, AttributeMode.INITIAL);
		tmp.put(WEIGHT, AttributeMode.INITIAL);
		tmp.put(LENGTH, AttributeMode.INITIAL);
		tmp.put(HEIGHT, AttributeMode.INITIAL);
		tmp.put(BASEUPC, AttributeMode.INITIAL);
		tmp.put(FROMPAYLOAD, AttributeMode.INITIAL);
		tmp.put(UPCPIMID, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.baseUPC</code> attribute.
	 * @return the baseUPC
	 */
	public Boolean isBaseUPC(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, BASEUPC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.baseUPC</code> attribute.
	 * @return the baseUPC
	 */
	public Boolean isBaseUPC()
	{
		return isBaseUPC( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.baseUPC</code> attribute. 
	 * @return the baseUPC
	 */
	public boolean isBaseUPCAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isBaseUPC( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.baseUPC</code> attribute. 
	 * @return the baseUPC
	 */
	public boolean isBaseUPCAsPrimitive()
	{
		return isBaseUPCAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.baseUPC</code> attribute. 
	 * @param value the baseUPC
	 */
	public void setBaseUPC(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, BASEUPC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.baseUPC</code> attribute. 
	 * @param value the baseUPC
	 */
	public void setBaseUPC(final Boolean value)
	{
		setBaseUPC( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.baseUPC</code> attribute. 
	 * @param value the baseUPC
	 */
	public void setBaseUPC(final SessionContext ctx, final boolean value)
	{
		setBaseUPC( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.baseUPC</code> attribute. 
	 * @param value the baseUPC
	 */
	public void setBaseUPC(final boolean value)
	{
		setBaseUPC( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.fromPayload</code> attribute.
	 * @return the fromPayload
	 */
	public Boolean isFromPayload(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, FROMPAYLOAD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.fromPayload</code> attribute.
	 * @return the fromPayload
	 */
	public Boolean isFromPayload()
	{
		return isFromPayload( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.fromPayload</code> attribute. 
	 * @return the fromPayload
	 */
	public boolean isFromPayloadAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isFromPayload( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.fromPayload</code> attribute. 
	 * @return the fromPayload
	 */
	public boolean isFromPayloadAsPrimitive()
	{
		return isFromPayloadAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.fromPayload</code> attribute. 
	 * @param value the fromPayload
	 */
	public void setFromPayload(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, FROMPAYLOAD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.fromPayload</code> attribute. 
	 * @param value the fromPayload
	 */
	public void setFromPayload(final Boolean value)
	{
		setFromPayload( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.fromPayload</code> attribute. 
	 * @param value the fromPayload
	 */
	public void setFromPayload(final SessionContext ctx, final boolean value)
	{
		setFromPayload( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.fromPayload</code> attribute. 
	 * @param value the fromPayload
	 */
	public void setFromPayload(final boolean value)
	{
		setFromPayload( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.height</code> attribute.
	 * @return the height
	 */
	public String getHeight(final SessionContext ctx)
	{
		return (String)getProperty( ctx, HEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.height</code> attribute.
	 * @return the height
	 */
	public String getHeight()
	{
		return getHeight( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.height</code> attribute. 
	 * @param value the height
	 */
	public void setHeight(final SessionContext ctx, final String value)
	{
		setProperty(ctx, HEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.height</code> attribute. 
	 * @param value the height
	 */
	public void setHeight(final String value)
	{
		setHeight( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.hideUPCOnline</code> attribute.
	 * @return the hideUPCOnline
	 */
	public Boolean isHideUPCOnline(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HIDEUPCONLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.hideUPCOnline</code> attribute.
	 * @return the hideUPCOnline
	 */
	public Boolean isHideUPCOnline()
	{
		return isHideUPCOnline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.hideUPCOnline</code> attribute. 
	 * @return the hideUPCOnline
	 */
	public boolean isHideUPCOnlineAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHideUPCOnline( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.hideUPCOnline</code> attribute. 
	 * @return the hideUPCOnline
	 */
	public boolean isHideUPCOnlineAsPrimitive()
	{
		return isHideUPCOnlineAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.hideUPCOnline</code> attribute. 
	 * @param value the hideUPCOnline
	 */
	public void setHideUPCOnline(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HIDEUPCONLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.hideUPCOnline</code> attribute. 
	 * @param value the hideUPCOnline
	 */
	public void setHideUPCOnline(final Boolean value)
	{
		setHideUPCOnline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.hideUPCOnline</code> attribute. 
	 * @param value the hideUPCOnline
	 */
	public void setHideUPCOnline(final SessionContext ctx, final boolean value)
	{
		setHideUPCOnline( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.hideUPCOnline</code> attribute. 
	 * @param value the hideUPCOnline
	 */
	public void setHideUPCOnline(final boolean value)
	{
		setHideUPCOnline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryMultiplier</code> attribute.
	 * @return the inventoryMultiplier
	 */
	public Float getInventoryMultiplier(final SessionContext ctx)
	{
		return (Float)getProperty( ctx, INVENTORYMULTIPLIER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryMultiplier</code> attribute.
	 * @return the inventoryMultiplier
	 */
	public Float getInventoryMultiplier()
	{
		return getInventoryMultiplier( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryMultiplier</code> attribute. 
	 * @return the inventoryMultiplier
	 */
	public float getInventoryMultiplierAsPrimitive(final SessionContext ctx)
	{
		Float value = getInventoryMultiplier( ctx );
		return value != null ? value.floatValue() : 0.0f;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryMultiplier</code> attribute. 
	 * @return the inventoryMultiplier
	 */
	public float getInventoryMultiplierAsPrimitive()
	{
		return getInventoryMultiplierAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final SessionContext ctx, final Float value)
	{
		setProperty(ctx, INVENTORYMULTIPLIER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final Float value)
	{
		setInventoryMultiplier( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final SessionContext ctx, final float value)
	{
		setInventoryMultiplier( ctx,Float.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryMultiplier</code> attribute. 
	 * @param value the inventoryMultiplier
	 */
	public void setInventoryMultiplier(final float value)
	{
		setInventoryMultiplier( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryUPCDesc</code> attribute.
	 * @return the inventoryUPCDesc
	 */
	public String getInventoryUPCDesc(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVENTORYUPCDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryUPCDesc</code> attribute.
	 * @return the inventoryUPCDesc
	 */
	public String getInventoryUPCDesc()
	{
		return getInventoryUPCDesc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryUPCDesc</code> attribute. 
	 * @param value the inventoryUPCDesc
	 */
	public void setInventoryUPCDesc(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVENTORYUPCDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryUPCDesc</code> attribute. 
	 * @param value the inventoryUPCDesc
	 */
	public void setInventoryUPCDesc(final String value)
	{
		setInventoryUPCDesc( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryUPCID</code> attribute.
	 * @return the inventoryUPCID
	 */
	public String getInventoryUPCID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVENTORYUPCID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.inventoryUPCID</code> attribute.
	 * @return the inventoryUPCID
	 */
	public String getInventoryUPCID()
	{
		return getInventoryUPCID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryUPCID</code> attribute. 
	 * @param value the inventoryUPCID
	 */
	public void setInventoryUPCID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVENTORYUPCID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.inventoryUPCID</code> attribute. 
	 * @param value the inventoryUPCID
	 */
	public void setInventoryUPCID(final String value)
	{
		setInventoryUPCID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.length</code> attribute.
	 * @return the length
	 */
	public String getLength(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LENGTH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.length</code> attribute.
	 * @return the length
	 */
	public String getLength()
	{
		return getLength( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.length</code> attribute. 
	 * @param value the length
	 */
	public void setLength(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LENGTH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.length</code> attribute. 
	 * @param value the length
	 */
	public void setLength(final String value)
	{
		setLength( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.upcPimId</code> attribute.
	 * @return the upcPimId
	 */
	public String getUpcPimId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UPCPIMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.upcPimId</code> attribute.
	 * @return the upcPimId
	 */
	public String getUpcPimId()
	{
		return getUpcPimId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.upcPimId</code> attribute. 
	 * @param value the upcPimId
	 */
	public void setUpcPimId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UPCPIMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.upcPimId</code> attribute. 
	 * @param value the upcPimId
	 */
	public void setUpcPimId(final String value)
	{
		setUpcPimId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.vendorEDINumber</code> attribute.
	 * @return the vendorEDINumber
	 */
	public String getVendorEDINumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VENDOREDINUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.vendorEDINumber</code> attribute.
	 * @return the vendorEDINumber
	 */
	public String getVendorEDINumber()
	{
		return getVendorEDINumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.vendorEDINumber</code> attribute. 
	 * @param value the vendorEDINumber
	 */
	public void setVendorEDINumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VENDOREDINUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.vendorEDINumber</code> attribute. 
	 * @param value the vendorEDINumber
	 */
	public void setVendorEDINumber(final String value)
	{
		setVendorEDINumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.vendorPartNumber</code> attribute.
	 * @return the vendorPartNumber
	 */
	public String getVendorPartNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VENDORPARTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.vendorPartNumber</code> attribute.
	 * @return the vendorPartNumber
	 */
	public String getVendorPartNumber()
	{
		return getVendorPartNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.vendorPartNumber</code> attribute. 
	 * @param value the vendorPartNumber
	 */
	public void setVendorPartNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VENDORPARTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.vendorPartNumber</code> attribute. 
	 * @param value the vendorPartNumber
	 */
	public void setVendorPartNumber(final String value)
	{
		setVendorPartNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.weight</code> attribute.
	 * @return the weight
	 */
	public String getWeight(final SessionContext ctx)
	{
		return (String)getProperty( ctx, WEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InventoryUPC.weight</code> attribute.
	 * @return the weight
	 */
	public String getWeight()
	{
		return getWeight( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final SessionContext ctx, final String value)
	{
		setProperty(ctx, WEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InventoryUPC.weight</code> attribute. 
	 * @param value the weight
	 */
	public void setWeight(final String value)
	{
		setWeight( getSession().getSessionContext(), value );
	}
	
}
