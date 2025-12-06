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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.BagInfo BagInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedBagInfo extends GenericItem
{
	/** Qualifier of the <code>BagInfo.unitPrice</code> attribute **/
	public static final String UNITPRICE = "unitPrice";
	/** Qualifier of the <code>BagInfo.numberOfBags</code> attribute **/
	public static final String NUMBEROFBAGS = "numberOfBags";
	/** Qualifier of the <code>BagInfo.totalPrice</code> attribute **/
	public static final String TOTALPRICE = "totalPrice";
	/** Qualifier of the <code>BagInfo.UOM</code> attribute **/
	public static final String UOM = "UOM";
	/** Qualifier of the <code>BagInfo.isChecked</code> attribute **/
	public static final String ISCHECKED = "isChecked";
	/** Qualifier of the <code>BagInfo.localTotal</code> attribute **/
	public static final String LOCALTOTAL = "localTotal";
	/** Qualifier of the <code>BagInfo.skuId</code> attribute **/
	public static final String SKUID = "skuId";
	/** Qualifier of the <code>BagInfo.quantity</code> attribute **/
	public static final String QUANTITY = "quantity";
	/** Qualifier of the <code>BagInfo.listPrice</code> attribute **/
	public static final String LISTPRICE = "listPrice";
	/** Qualifier of the <code>BagInfo.inventoryUOMId</code> attribute **/
	public static final String INVENTORYUOMID = "inventoryUOMId";
	/** Qualifier of the <code>BagInfo.selectedUom</code> attribute **/
	public static final String SELECTEDUOM = "selectedUom";
	/** Qualifier of the <code>BagInfo.uomMultiplier</code> attribute **/
	public static final String UOMMULTIPLIER = "uomMultiplier";
	/** Qualifier of the <code>BagInfo.baseUOM</code> attribute **/
	public static final String BASEUOM = "baseUOM";
	/** Qualifier of the <code>BagInfo.discountAmount</code> attribute **/
	public static final String DISCOUNTAMOUNT = "discountAmount";
	/** Qualifier of the <code>BagInfo.discountedUnitPrice</code> attribute **/
	public static final String DISCOUNTEDUNITPRICE = "discountedUnitPrice";
	/** Qualifier of the <code>BagInfo.baseDiscountAmount</code> attribute **/
	public static final String BASEDISCOUNTAMOUNT = "baseDiscountAmount";
	/** Qualifier of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute **/
	public static final String BASEDISCOUNTEDUNITPRICE = "baseDiscountedUnitPrice";
	/** Qualifier of the <code>BagInfo.baseListPrice</code> attribute **/
	public static final String BASELISTPRICE = "baseListPrice";
	/** Qualifier of the <code>BagInfo.couponId</code> attribute **/
	public static final String COUPONID = "couponId";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(UNITPRICE, AttributeMode.INITIAL);
		tmp.put(NUMBEROFBAGS, AttributeMode.INITIAL);
		tmp.put(TOTALPRICE, AttributeMode.INITIAL);
		tmp.put(UOM, AttributeMode.INITIAL);
		tmp.put(ISCHECKED, AttributeMode.INITIAL);
		tmp.put(LOCALTOTAL, AttributeMode.INITIAL);
		tmp.put(SKUID, AttributeMode.INITIAL);
		tmp.put(QUANTITY, AttributeMode.INITIAL);
		tmp.put(LISTPRICE, AttributeMode.INITIAL);
		tmp.put(INVENTORYUOMID, AttributeMode.INITIAL);
		tmp.put(SELECTEDUOM, AttributeMode.INITIAL);
		tmp.put(UOMMULTIPLIER, AttributeMode.INITIAL);
		tmp.put(BASEUOM, AttributeMode.INITIAL);
		tmp.put(DISCOUNTAMOUNT, AttributeMode.INITIAL);
		tmp.put(DISCOUNTEDUNITPRICE, AttributeMode.INITIAL);
		tmp.put(BASEDISCOUNTAMOUNT, AttributeMode.INITIAL);
		tmp.put(BASEDISCOUNTEDUNITPRICE, AttributeMode.INITIAL);
		tmp.put(BASELISTPRICE, AttributeMode.INITIAL);
		tmp.put(COUPONID, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountAmount</code> attribute.
	 * @return the baseDiscountAmount
	 */
	public Double getBaseDiscountAmount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BASEDISCOUNTAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountAmount</code> attribute.
	 * @return the baseDiscountAmount
	 */
	public Double getBaseDiscountAmount()
	{
		return getBaseDiscountAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountAmount</code> attribute. 
	 * @return the baseDiscountAmount
	 */
	public double getBaseDiscountAmountAsPrimitive(final SessionContext ctx)
	{
		Double value = getBaseDiscountAmount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountAmount</code> attribute. 
	 * @return the baseDiscountAmount
	 */
	public double getBaseDiscountAmountAsPrimitive()
	{
		return getBaseDiscountAmountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountAmount</code> attribute. 
	 * @param value the baseDiscountAmount
	 */
	public void setBaseDiscountAmount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BASEDISCOUNTAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountAmount</code> attribute. 
	 * @param value the baseDiscountAmount
	 */
	public void setBaseDiscountAmount(final Double value)
	{
		setBaseDiscountAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountAmount</code> attribute. 
	 * @param value the baseDiscountAmount
	 */
	public void setBaseDiscountAmount(final SessionContext ctx, final double value)
	{
		setBaseDiscountAmount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountAmount</code> attribute. 
	 * @param value the baseDiscountAmount
	 */
	public void setBaseDiscountAmount(final double value)
	{
		setBaseDiscountAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute.
	 * @return the baseDiscountedUnitPrice
	 */
	public Double getBaseDiscountedUnitPrice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BASEDISCOUNTEDUNITPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute.
	 * @return the baseDiscountedUnitPrice
	 */
	public Double getBaseDiscountedUnitPrice()
	{
		return getBaseDiscountedUnitPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute. 
	 * @return the baseDiscountedUnitPrice
	 */
	public double getBaseDiscountedUnitPriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getBaseDiscountedUnitPrice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute. 
	 * @return the baseDiscountedUnitPrice
	 */
	public double getBaseDiscountedUnitPriceAsPrimitive()
	{
		return getBaseDiscountedUnitPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute. 
	 * @param value the baseDiscountedUnitPrice
	 */
	public void setBaseDiscountedUnitPrice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BASEDISCOUNTEDUNITPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute. 
	 * @param value the baseDiscountedUnitPrice
	 */
	public void setBaseDiscountedUnitPrice(final Double value)
	{
		setBaseDiscountedUnitPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute. 
	 * @param value the baseDiscountedUnitPrice
	 */
	public void setBaseDiscountedUnitPrice(final SessionContext ctx, final double value)
	{
		setBaseDiscountedUnitPrice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseDiscountedUnitPrice</code> attribute. 
	 * @param value the baseDiscountedUnitPrice
	 */
	public void setBaseDiscountedUnitPrice(final double value)
	{
		setBaseDiscountedUnitPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseListPrice</code> attribute.
	 * @return the baseListPrice
	 */
	public Double getBaseListPrice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BASELISTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseListPrice</code> attribute.
	 * @return the baseListPrice
	 */
	public Double getBaseListPrice()
	{
		return getBaseListPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseListPrice</code> attribute. 
	 * @return the baseListPrice
	 */
	public double getBaseListPriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getBaseListPrice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseListPrice</code> attribute. 
	 * @return the baseListPrice
	 */
	public double getBaseListPriceAsPrimitive()
	{
		return getBaseListPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseListPrice</code> attribute. 
	 * @param value the baseListPrice
	 */
	public void setBaseListPrice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BASELISTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseListPrice</code> attribute. 
	 * @param value the baseListPrice
	 */
	public void setBaseListPrice(final Double value)
	{
		setBaseListPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseListPrice</code> attribute. 
	 * @param value the baseListPrice
	 */
	public void setBaseListPrice(final SessionContext ctx, final double value)
	{
		setBaseListPrice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseListPrice</code> attribute. 
	 * @param value the baseListPrice
	 */
	public void setBaseListPrice(final double value)
	{
		setBaseListPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseUOM</code> attribute.
	 * @return the baseUOM
	 */
	public String getBaseUOM(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BASEUOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.baseUOM</code> attribute.
	 * @return the baseUOM
	 */
	public String getBaseUOM()
	{
		return getBaseUOM( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseUOM</code> attribute. 
	 * @param value the baseUOM
	 */
	public void setBaseUOM(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BASEUOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.baseUOM</code> attribute. 
	 * @param value the baseUOM
	 */
	public void setBaseUOM(final String value)
	{
		setBaseUOM( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.couponId</code> attribute.
	 * @return the couponId
	 */
	public String getCouponId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COUPONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.couponId</code> attribute.
	 * @return the couponId
	 */
	public String getCouponId()
	{
		return getCouponId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.couponId</code> attribute. 
	 * @param value the couponId
	 */
	public void setCouponId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COUPONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.couponId</code> attribute. 
	 * @param value the couponId
	 */
	public void setCouponId(final String value)
	{
		setCouponId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.discountAmount</code> attribute.
	 * @return the discountAmount
	 */
	public String getDiscountAmount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DISCOUNTAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.discountAmount</code> attribute.
	 * @return the discountAmount
	 */
	public String getDiscountAmount()
	{
		return getDiscountAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.discountAmount</code> attribute. 
	 * @param value the discountAmount
	 */
	public void setDiscountAmount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DISCOUNTAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.discountAmount</code> attribute. 
	 * @param value the discountAmount
	 */
	public void setDiscountAmount(final String value)
	{
		setDiscountAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.discountedUnitPrice</code> attribute.
	 * @return the discountedUnitPrice
	 */
	public String getDiscountedUnitPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DISCOUNTEDUNITPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.discountedUnitPrice</code> attribute.
	 * @return the discountedUnitPrice
	 */
	public String getDiscountedUnitPrice()
	{
		return getDiscountedUnitPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.discountedUnitPrice</code> attribute. 
	 * @param value the discountedUnitPrice
	 */
	public void setDiscountedUnitPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DISCOUNTEDUNITPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.discountedUnitPrice</code> attribute. 
	 * @param value the discountedUnitPrice
	 */
	public void setDiscountedUnitPrice(final String value)
	{
		setDiscountedUnitPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.inventoryUOMId</code> attribute.
	 * @return the inventoryUOMId
	 */
	public Integer getInventoryUOMId(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, INVENTORYUOMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.inventoryUOMId</code> attribute.
	 * @return the inventoryUOMId
	 */
	public Integer getInventoryUOMId()
	{
		return getInventoryUOMId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.inventoryUOMId</code> attribute. 
	 * @return the inventoryUOMId
	 */
	public int getInventoryUOMIdAsPrimitive(final SessionContext ctx)
	{
		Integer value = getInventoryUOMId( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.inventoryUOMId</code> attribute. 
	 * @return the inventoryUOMId
	 */
	public int getInventoryUOMIdAsPrimitive()
	{
		return getInventoryUOMIdAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.inventoryUOMId</code> attribute. 
	 * @param value the inventoryUOMId
	 */
	public void setInventoryUOMId(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, INVENTORYUOMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.inventoryUOMId</code> attribute. 
	 * @param value the inventoryUOMId
	 */
	public void setInventoryUOMId(final Integer value)
	{
		setInventoryUOMId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.inventoryUOMId</code> attribute. 
	 * @param value the inventoryUOMId
	 */
	public void setInventoryUOMId(final SessionContext ctx, final int value)
	{
		setInventoryUOMId( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.inventoryUOMId</code> attribute. 
	 * @param value the inventoryUOMId
	 */
	public void setInventoryUOMId(final int value)
	{
		setInventoryUOMId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.isChecked</code> attribute.
	 * @return the isChecked
	 */
	public Boolean isIsChecked(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISCHECKED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.isChecked</code> attribute.
	 * @return the isChecked
	 */
	public Boolean isIsChecked()
	{
		return isIsChecked( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.isChecked</code> attribute. 
	 * @return the isChecked
	 */
	public boolean isIsCheckedAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsChecked( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.isChecked</code> attribute. 
	 * @return the isChecked
	 */
	public boolean isIsCheckedAsPrimitive()
	{
		return isIsCheckedAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.isChecked</code> attribute. 
	 * @param value the isChecked
	 */
	public void setIsChecked(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISCHECKED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.isChecked</code> attribute. 
	 * @param value the isChecked
	 */
	public void setIsChecked(final Boolean value)
	{
		setIsChecked( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.isChecked</code> attribute. 
	 * @param value the isChecked
	 */
	public void setIsChecked(final SessionContext ctx, final boolean value)
	{
		setIsChecked( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.isChecked</code> attribute. 
	 * @param value the isChecked
	 */
	public void setIsChecked(final boolean value)
	{
		setIsChecked( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.listPrice</code> attribute.
	 * @return the listPrice
	 */
	public Double getListPrice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, LISTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.listPrice</code> attribute.
	 * @return the listPrice
	 */
	public Double getListPrice()
	{
		return getListPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.listPrice</code> attribute. 
	 * @return the listPrice
	 */
	public double getListPriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getListPrice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.listPrice</code> attribute. 
	 * @return the listPrice
	 */
	public double getListPriceAsPrimitive()
	{
		return getListPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, LISTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final Double value)
	{
		setListPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final SessionContext ctx, final double value)
	{
		setListPrice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final double value)
	{
		setListPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.localTotal</code> attribute.
	 * @return the localTotal
	 */
	public BigDecimal getLocalTotal(final SessionContext ctx)
	{
		return (BigDecimal)getProperty( ctx, LOCALTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.localTotal</code> attribute.
	 * @return the localTotal
	 */
	public BigDecimal getLocalTotal()
	{
		return getLocalTotal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.localTotal</code> attribute. 
	 * @param value the localTotal
	 */
	public void setLocalTotal(final SessionContext ctx, final BigDecimal value)
	{
		setProperty(ctx, LOCALTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.localTotal</code> attribute. 
	 * @param value the localTotal
	 */
	public void setLocalTotal(final BigDecimal value)
	{
		setLocalTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.numberOfBags</code> attribute.
	 * @return the numberOfBags
	 */
	public Integer getNumberOfBags(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, NUMBEROFBAGS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.numberOfBags</code> attribute.
	 * @return the numberOfBags
	 */
	public Integer getNumberOfBags()
	{
		return getNumberOfBags( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.numberOfBags</code> attribute. 
	 * @return the numberOfBags
	 */
	public int getNumberOfBagsAsPrimitive(final SessionContext ctx)
	{
		Integer value = getNumberOfBags( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.numberOfBags</code> attribute. 
	 * @return the numberOfBags
	 */
	public int getNumberOfBagsAsPrimitive()
	{
		return getNumberOfBagsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.numberOfBags</code> attribute. 
	 * @param value the numberOfBags
	 */
	public void setNumberOfBags(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, NUMBEROFBAGS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.numberOfBags</code> attribute. 
	 * @param value the numberOfBags
	 */
	public void setNumberOfBags(final Integer value)
	{
		setNumberOfBags( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.numberOfBags</code> attribute. 
	 * @param value the numberOfBags
	 */
	public void setNumberOfBags(final SessionContext ctx, final int value)
	{
		setNumberOfBags( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.numberOfBags</code> attribute. 
	 * @param value the numberOfBags
	 */
	public void setNumberOfBags(final int value)
	{
		setNumberOfBags( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.quantity</code> attribute.
	 * @return the quantity
	 */
	public String getQuantity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.quantity</code> attribute.
	 * @return the quantity
	 */
	public String getQuantity()
	{
		return getQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final String value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.selectedUom</code> attribute.
	 * @return the selectedUom
	 */
	public String getSelectedUom(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SELECTEDUOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.selectedUom</code> attribute.
	 * @return the selectedUom
	 */
	public String getSelectedUom()
	{
		return getSelectedUom( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.selectedUom</code> attribute. 
	 * @param value the selectedUom
	 */
	public void setSelectedUom(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SELECTEDUOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.selectedUom</code> attribute. 
	 * @param value the selectedUom
	 */
	public void setSelectedUom(final String value)
	{
		setSelectedUom( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.skuId</code> attribute.
	 * @return the skuId
	 */
	public String getSkuId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SKUID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.skuId</code> attribute.
	 * @return the skuId
	 */
	public String getSkuId()
	{
		return getSkuId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.skuId</code> attribute. 
	 * @param value the skuId
	 */
	public void setSkuId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SKUID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.skuId</code> attribute. 
	 * @param value the skuId
	 */
	public void setSkuId(final String value)
	{
		setSkuId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.totalPrice</code> attribute.
	 * @return the totalPrice
	 */
	public String getTotalPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOTALPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.totalPrice</code> attribute.
	 * @return the totalPrice
	 */
	public String getTotalPrice()
	{
		return getTotalPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.totalPrice</code> attribute. 
	 * @param value the totalPrice
	 */
	public void setTotalPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOTALPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.totalPrice</code> attribute. 
	 * @param value the totalPrice
	 */
	public void setTotalPrice(final String value)
	{
		setTotalPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.unitPrice</code> attribute.
	 * @return the unitPrice
	 */
	public String getUnitPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UNITPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.unitPrice</code> attribute.
	 * @return the unitPrice
	 */
	public String getUnitPrice()
	{
		return getUnitPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.unitPrice</code> attribute. 
	 * @param value the unitPrice
	 */
	public void setUnitPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UNITPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.unitPrice</code> attribute. 
	 * @param value the unitPrice
	 */
	public void setUnitPrice(final String value)
	{
		setUnitPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.UOM</code> attribute.
	 * @return the UOM
	 */
	public String getUOM(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.UOM</code> attribute.
	 * @return the UOM
	 */
	public String getUOM()
	{
		return getUOM( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.UOM</code> attribute. 
	 * @param value the UOM
	 */
	public void setUOM(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.UOM</code> attribute. 
	 * @param value the UOM
	 */
	public void setUOM(final String value)
	{
		setUOM( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.uomMultiplier</code> attribute.
	 * @return the uomMultiplier
	 */
	public String getUomMultiplier(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOMMULTIPLIER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BagInfo.uomMultiplier</code> attribute.
	 * @return the uomMultiplier
	 */
	public String getUomMultiplier()
	{
		return getUomMultiplier( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.uomMultiplier</code> attribute. 
	 * @param value the uomMultiplier
	 */
	public void setUomMultiplier(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOMMULTIPLIER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BagInfo.uomMultiplier</code> attribute. 
	 * @param value the uomMultiplier
	 */
	public void setUomMultiplier(final String value)
	{
		setUomMultiplier( getSession().getSessionContext(), value );
	}
	
}
