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
 * Generated class for type {@link com.siteone.core.jalo.QuoteItemDetails QuoteItemDetails}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedQuoteItemDetails extends GenericItem
{
	/** Qualifier of the <code>QuoteItemDetails.itemNumber</code> attribute **/
	public static final String ITEMNUMBER = "itemNumber";
	/** Qualifier of the <code>QuoteItemDetails.itemDescription</code> attribute **/
	public static final String ITEMDESCRIPTION = "itemDescription";
	/** Qualifier of the <code>QuoteItemDetails.quantity</code> attribute **/
	public static final String QUANTITY = "quantity";
	/** Qualifier of the <code>QuoteItemDetails.unitPrice</code> attribute **/
	public static final String UNITPRICE = "unitPrice";
	/** Qualifier of the <code>QuoteItemDetails.UOM</code> attribute **/
	public static final String UOM = "UOM";
	/** Qualifier of the <code>QuoteItemDetails.totalPrice</code> attribute **/
	public static final String TOTALPRICE = "totalPrice";
	/** Qualifier of the <code>QuoteItemDetails.extPrice</code> attribute **/
	public static final String EXTPRICE = "extPrice";
	/** Qualifier of the <code>QuoteItemDetails.lineNumber</code> attribute **/
	public static final String LINENUMBER = "lineNumber";
	/** Qualifier of the <code>QuoteItemDetails.approvedQty</code> attribute **/
	public static final String APPROVEDQTY = "approvedQty";
	/** Qualifier of the <code>QuoteItemDetails.approvalCheck</code> attribute **/
	public static final String APPROVALCHECK = "approvalCheck";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ITEMNUMBER, AttributeMode.INITIAL);
		tmp.put(ITEMDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(QUANTITY, AttributeMode.INITIAL);
		tmp.put(UNITPRICE, AttributeMode.INITIAL);
		tmp.put(UOM, AttributeMode.INITIAL);
		tmp.put(TOTALPRICE, AttributeMode.INITIAL);
		tmp.put(EXTPRICE, AttributeMode.INITIAL);
		tmp.put(LINENUMBER, AttributeMode.INITIAL);
		tmp.put(APPROVEDQTY, AttributeMode.INITIAL);
		tmp.put(APPROVALCHECK, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.approvalCheck</code> attribute.
	 * @return the approvalCheck
	 */
	public String getApprovalCheck(final SessionContext ctx)
	{
		return (String)getProperty( ctx, APPROVALCHECK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.approvalCheck</code> attribute.
	 * @return the approvalCheck
	 */
	public String getApprovalCheck()
	{
		return getApprovalCheck( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.approvalCheck</code> attribute. 
	 * @param value the approvalCheck
	 */
	public void setApprovalCheck(final SessionContext ctx, final String value)
	{
		setProperty(ctx, APPROVALCHECK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.approvalCheck</code> attribute. 
	 * @param value the approvalCheck
	 */
	public void setApprovalCheck(final String value)
	{
		setApprovalCheck( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.approvedQty</code> attribute.
	 * @return the approvedQty
	 */
	public Integer getApprovedQty(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, APPROVEDQTY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.approvedQty</code> attribute.
	 * @return the approvedQty
	 */
	public Integer getApprovedQty()
	{
		return getApprovedQty( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.approvedQty</code> attribute. 
	 * @return the approvedQty
	 */
	public int getApprovedQtyAsPrimitive(final SessionContext ctx)
	{
		Integer value = getApprovedQty( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.approvedQty</code> attribute. 
	 * @return the approvedQty
	 */
	public int getApprovedQtyAsPrimitive()
	{
		return getApprovedQtyAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.approvedQty</code> attribute. 
	 * @param value the approvedQty
	 */
	public void setApprovedQty(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, APPROVEDQTY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.approvedQty</code> attribute. 
	 * @param value the approvedQty
	 */
	public void setApprovedQty(final Integer value)
	{
		setApprovedQty( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.approvedQty</code> attribute. 
	 * @param value the approvedQty
	 */
	public void setApprovedQty(final SessionContext ctx, final int value)
	{
		setApprovedQty( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.approvedQty</code> attribute. 
	 * @param value the approvedQty
	 */
	public void setApprovedQty(final int value)
	{
		setApprovedQty( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.extPrice</code> attribute.
	 * @return the extPrice
	 */
	public String getExtPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.extPrice</code> attribute.
	 * @return the extPrice
	 */
	public String getExtPrice()
	{
		return getExtPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.extPrice</code> attribute. 
	 * @param value the extPrice
	 */
	public void setExtPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.extPrice</code> attribute. 
	 * @param value the extPrice
	 */
	public void setExtPrice(final String value)
	{
		setExtPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.itemDescription</code> attribute.
	 * @return the itemDescription
	 */
	public String getItemDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.itemDescription</code> attribute.
	 * @return the itemDescription
	 */
	public String getItemDescription()
	{
		return getItemDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.itemDescription</code> attribute. 
	 * @param value the itemDescription
	 */
	public void setItemDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.itemDescription</code> attribute. 
	 * @param value the itemDescription
	 */
	public void setItemDescription(final String value)
	{
		setItemDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber()
	{
		return getItemNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final String value)
	{
		setItemNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.lineNumber</code> attribute.
	 * @return the lineNumber
	 */
	public Integer getLineNumber(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, LINENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.lineNumber</code> attribute.
	 * @return the lineNumber
	 */
	public Integer getLineNumber()
	{
		return getLineNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.lineNumber</code> attribute. 
	 * @return the lineNumber
	 */
	public int getLineNumberAsPrimitive(final SessionContext ctx)
	{
		Integer value = getLineNumber( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.lineNumber</code> attribute. 
	 * @return the lineNumber
	 */
	public int getLineNumberAsPrimitive()
	{
		return getLineNumberAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.lineNumber</code> attribute. 
	 * @param value the lineNumber
	 */
	public void setLineNumber(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, LINENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.lineNumber</code> attribute. 
	 * @param value the lineNumber
	 */
	public void setLineNumber(final Integer value)
	{
		setLineNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.lineNumber</code> attribute. 
	 * @param value the lineNumber
	 */
	public void setLineNumber(final SessionContext ctx, final int value)
	{
		setLineNumber( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.lineNumber</code> attribute. 
	 * @param value the lineNumber
	 */
	public void setLineNumber(final int value)
	{
		setLineNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.quantity</code> attribute.
	 * @return the quantity
	 */
	public String getQuantity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.quantity</code> attribute.
	 * @return the quantity
	 */
	public String getQuantity()
	{
		return getQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final String value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.totalPrice</code> attribute.
	 * @return the totalPrice
	 */
	public String getTotalPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOTALPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.totalPrice</code> attribute.
	 * @return the totalPrice
	 */
	public String getTotalPrice()
	{
		return getTotalPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.totalPrice</code> attribute. 
	 * @param value the totalPrice
	 */
	public void setTotalPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOTALPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.totalPrice</code> attribute. 
	 * @param value the totalPrice
	 */
	public void setTotalPrice(final String value)
	{
		setTotalPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.unitPrice</code> attribute.
	 * @return the unitPrice
	 */
	public String getUnitPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UNITPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.unitPrice</code> attribute.
	 * @return the unitPrice
	 */
	public String getUnitPrice()
	{
		return getUnitPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.unitPrice</code> attribute. 
	 * @param value the unitPrice
	 */
	public void setUnitPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UNITPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.unitPrice</code> attribute. 
	 * @param value the unitPrice
	 */
	public void setUnitPrice(final String value)
	{
		setUnitPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.UOM</code> attribute.
	 * @return the UOM
	 */
	public String getUOM(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteItemDetails.UOM</code> attribute.
	 * @return the UOM
	 */
	public String getUOM()
	{
		return getUOM( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.UOM</code> attribute. 
	 * @param value the UOM
	 */
	public void setUOM(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteItemDetails.UOM</code> attribute. 
	 * @param value the UOM
	 */
	public void setUOM(final String value)
	{
		setUOM( getSession().getSessionContext(), value );
	}
	
}
