/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.Invoice;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.InvoiceEntry SiteOneInvoiceEntry}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedInvoiceEntry extends GenericItem
{
	/** Qualifier of the <code>SiteOneInvoiceEntry.quantity</code> attribute **/
	public static final String QUANTITY = "quantity";
	/** Qualifier of the <code>SiteOneInvoiceEntry.unit</code> attribute **/
	public static final String UNIT = "unit";
	/** Qualifier of the <code>SiteOneInvoiceEntry.basePrice</code> attribute **/
	public static final String BASEPRICE = "basePrice";
	/** Qualifier of the <code>SiteOneInvoiceEntry.productItemNumber</code> attribute **/
	public static final String PRODUCTITEMNUMBER = "productItemNumber";
	/** Qualifier of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute **/
	public static final String QTYSHIPPED = "qtyShipped";
	/** Qualifier of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute **/
	public static final String QTYOPEN = "qtyOpen";
	/** Qualifier of the <code>SiteOneInvoiceEntry.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>SiteOneInvoiceEntry.netprice</code> attribute **/
	public static final String NETPRICE = "netprice";
	/** Qualifier of the <code>SiteOneInvoiceEntry.extprice</code> attribute **/
	public static final String EXTPRICE = "extprice";
	/** Qualifier of the <code>SiteOneInvoiceEntry.entryNumber</code> attribute **/
	public static final String ENTRYNUMBER = "entryNumber";
	/** Qualifier of the <code>SiteOneInvoiceEntry.quantityText</code> attribute **/
	public static final String QUANTITYTEXT = "quantityText";
	/** Qualifier of the <code>SiteOneInvoiceEntry.basePriceText</code> attribute **/
	public static final String BASEPRICETEXT = "basePriceText";
	/** Qualifier of the <code>SiteOneInvoiceEntry.extpriceText</code> attribute **/
	public static final String EXTPRICETEXT = "extpriceText";
	/** Qualifier of the <code>SiteOneInvoiceEntry.invoiceEntryId</code> attribute **/
	public static final String INVOICEENTRYID = "invoiceEntryId";
	/** Qualifier of the <code>SiteOneInvoiceEntry.invoice</code> attribute **/
	public static final String INVOICE = "invoice";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n INVOICE's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedInvoiceEntry> INVOICEHANDLER = new BidirectionalOneToManyHandler<GeneratedInvoiceEntry>(
	SiteoneCoreConstants.TC.SITEONEINVOICEENTRY,
	false,
	"invoice",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(QUANTITY, AttributeMode.INITIAL);
		tmp.put(UNIT, AttributeMode.INITIAL);
		tmp.put(BASEPRICE, AttributeMode.INITIAL);
		tmp.put(PRODUCTITEMNUMBER, AttributeMode.INITIAL);
		tmp.put(QTYSHIPPED, AttributeMode.INITIAL);
		tmp.put(QTYOPEN, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(NETPRICE, AttributeMode.INITIAL);
		tmp.put(EXTPRICE, AttributeMode.INITIAL);
		tmp.put(ENTRYNUMBER, AttributeMode.INITIAL);
		tmp.put(QUANTITYTEXT, AttributeMode.INITIAL);
		tmp.put(BASEPRICETEXT, AttributeMode.INITIAL);
		tmp.put(EXTPRICETEXT, AttributeMode.INITIAL);
		tmp.put(INVOICEENTRYID, AttributeMode.INITIAL);
		tmp.put(INVOICE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute.
	 * @return the basePrice
	 */
	public Double getBasePrice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, BASEPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute.
	 * @return the basePrice
	 */
	public Double getBasePrice()
	{
		return getBasePrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute. 
	 * @return the basePrice
	 */
	public double getBasePriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getBasePrice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute. 
	 * @return the basePrice
	 */
	public double getBasePriceAsPrimitive()
	{
		return getBasePriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute. 
	 * @param value the basePrice
	 */
	public void setBasePrice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, BASEPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute. 
	 * @param value the basePrice
	 */
	public void setBasePrice(final Double value)
	{
		setBasePrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute. 
	 * @param value the basePrice
	 */
	public void setBasePrice(final SessionContext ctx, final double value)
	{
		setBasePrice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.basePrice</code> attribute. 
	 * @param value the basePrice
	 */
	public void setBasePrice(final double value)
	{
		setBasePrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.basePriceText</code> attribute.
	 * @return the basePriceText
	 */
	public String getBasePriceText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BASEPRICETEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.basePriceText</code> attribute.
	 * @return the basePriceText
	 */
	public String getBasePriceText()
	{
		return getBasePriceText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.basePriceText</code> attribute. 
	 * @param value the basePriceText
	 */
	public void setBasePriceText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BASEPRICETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.basePriceText</code> attribute. 
	 * @param value the basePriceText
	 */
	public void setBasePriceText(final String value)
	{
		setBasePriceText( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		INVOICEHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.entryNumber</code> attribute.
	 * @return the entryNumber
	 */
	public String getEntryNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ENTRYNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.entryNumber</code> attribute.
	 * @return the entryNumber
	 */
	public String getEntryNumber()
	{
		return getEntryNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.entryNumber</code> attribute. 
	 * @param value the entryNumber
	 */
	public void setEntryNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ENTRYNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.entryNumber</code> attribute. 
	 * @param value the entryNumber
	 */
	public void setEntryNumber(final String value)
	{
		setEntryNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.extprice</code> attribute.
	 * @return the extprice
	 */
	public Double getExtprice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, EXTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.extprice</code> attribute.
	 * @return the extprice
	 */
	public Double getExtprice()
	{
		return getExtprice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.extprice</code> attribute. 
	 * @return the extprice
	 */
	public double getExtpriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getExtprice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.extprice</code> attribute. 
	 * @return the extprice
	 */
	public double getExtpriceAsPrimitive()
	{
		return getExtpriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.extprice</code> attribute. 
	 * @param value the extprice
	 */
	public void setExtprice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, EXTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.extprice</code> attribute. 
	 * @param value the extprice
	 */
	public void setExtprice(final Double value)
	{
		setExtprice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.extprice</code> attribute. 
	 * @param value the extprice
	 */
	public void setExtprice(final SessionContext ctx, final double value)
	{
		setExtprice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.extprice</code> attribute. 
	 * @param value the extprice
	 */
	public void setExtprice(final double value)
	{
		setExtprice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.extpriceText</code> attribute.
	 * @return the extpriceText
	 */
	public String getExtpriceText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXTPRICETEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.extpriceText</code> attribute.
	 * @return the extpriceText
	 */
	public String getExtpriceText()
	{
		return getExtpriceText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.extpriceText</code> attribute. 
	 * @param value the extpriceText
	 */
	public void setExtpriceText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXTPRICETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.extpriceText</code> attribute. 
	 * @param value the extpriceText
	 */
	public void setExtpriceText(final String value)
	{
		setExtpriceText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.invoice</code> attribute.
	 * @return the invoice
	 */
	public Invoice getInvoice(final SessionContext ctx)
	{
		return (Invoice)getProperty( ctx, INVOICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.invoice</code> attribute.
	 * @return the invoice
	 */
	public Invoice getInvoice()
	{
		return getInvoice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.invoice</code> attribute. 
	 * @param value the invoice
	 */
	public void setInvoice(final SessionContext ctx, final Invoice value)
	{
		INVOICEHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.invoice</code> attribute. 
	 * @param value the invoice
	 */
	public void setInvoice(final Invoice value)
	{
		setInvoice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.invoiceEntryId</code> attribute.
	 * @return the invoiceEntryId
	 */
	public String getInvoiceEntryId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICEENTRYID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.invoiceEntryId</code> attribute.
	 * @return the invoiceEntryId
	 */
	public String getInvoiceEntryId()
	{
		return getInvoiceEntryId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.invoiceEntryId</code> attribute. 
	 * @param value the invoiceEntryId
	 */
	public void setInvoiceEntryId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICEENTRYID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.invoiceEntryId</code> attribute. 
	 * @param value the invoiceEntryId
	 */
	public void setInvoiceEntryId(final String value)
	{
		setInvoiceEntryId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.netprice</code> attribute.
	 * @return the netprice
	 */
	public Double getNetprice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, NETPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.netprice</code> attribute.
	 * @return the netprice
	 */
	public Double getNetprice()
	{
		return getNetprice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.netprice</code> attribute. 
	 * @return the netprice
	 */
	public double getNetpriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getNetprice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.netprice</code> attribute. 
	 * @return the netprice
	 */
	public double getNetpriceAsPrimitive()
	{
		return getNetpriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.netprice</code> attribute. 
	 * @param value the netprice
	 */
	public void setNetprice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, NETPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.netprice</code> attribute. 
	 * @param value the netprice
	 */
	public void setNetprice(final Double value)
	{
		setNetprice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.netprice</code> attribute. 
	 * @param value the netprice
	 */
	public void setNetprice(final SessionContext ctx, final double value)
	{
		setNetprice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.netprice</code> attribute. 
	 * @param value the netprice
	 */
	public void setNetprice(final double value)
	{
		setNetprice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.productItemNumber</code> attribute.
	 * @return the productItemNumber
	 */
	public String getProductItemNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.productItemNumber</code> attribute.
	 * @return the productItemNumber
	 */
	public String getProductItemNumber()
	{
		return getProductItemNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.productItemNumber</code> attribute. 
	 * @param value the productItemNumber
	 */
	public void setProductItemNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.productItemNumber</code> attribute. 
	 * @param value the productItemNumber
	 */
	public void setProductItemNumber(final String value)
	{
		setProductItemNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute.
	 * @return the qtyOpen
	 */
	public Integer getQtyOpen(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, QTYOPEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute.
	 * @return the qtyOpen
	 */
	public Integer getQtyOpen()
	{
		return getQtyOpen( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute. 
	 * @return the qtyOpen
	 */
	public int getQtyOpenAsPrimitive(final SessionContext ctx)
	{
		Integer value = getQtyOpen( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute. 
	 * @return the qtyOpen
	 */
	public int getQtyOpenAsPrimitive()
	{
		return getQtyOpenAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute. 
	 * @param value the qtyOpen
	 */
	public void setQtyOpen(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, QTYOPEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute. 
	 * @param value the qtyOpen
	 */
	public void setQtyOpen(final Integer value)
	{
		setQtyOpen( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute. 
	 * @param value the qtyOpen
	 */
	public void setQtyOpen(final SessionContext ctx, final int value)
	{
		setQtyOpen( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyOpen</code> attribute. 
	 * @param value the qtyOpen
	 */
	public void setQtyOpen(final int value)
	{
		setQtyOpen( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute.
	 * @return the qtyShipped
	 */
	public Integer getQtyShipped(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, QTYSHIPPED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute.
	 * @return the qtyShipped
	 */
	public Integer getQtyShipped()
	{
		return getQtyShipped( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute. 
	 * @return the qtyShipped
	 */
	public int getQtyShippedAsPrimitive(final SessionContext ctx)
	{
		Integer value = getQtyShipped( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute. 
	 * @return the qtyShipped
	 */
	public int getQtyShippedAsPrimitive()
	{
		return getQtyShippedAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute. 
	 * @param value the qtyShipped
	 */
	public void setQtyShipped(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, QTYSHIPPED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute. 
	 * @param value the qtyShipped
	 */
	public void setQtyShipped(final Integer value)
	{
		setQtyShipped( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute. 
	 * @param value the qtyShipped
	 */
	public void setQtyShipped(final SessionContext ctx, final int value)
	{
		setQtyShipped( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.qtyShipped</code> attribute. 
	 * @param value the qtyShipped
	 */
	public void setQtyShipped(final int value)
	{
		setQtyShipped( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.quantity</code> attribute.
	 * @return the quantity
	 */
	public Integer getQuantity(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, QUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.quantity</code> attribute.
	 * @return the quantity
	 */
	public Integer getQuantity()
	{
		return getQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.quantity</code> attribute. 
	 * @return the quantity
	 */
	public int getQuantityAsPrimitive(final SessionContext ctx)
	{
		Integer value = getQuantity( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.quantity</code> attribute. 
	 * @return the quantity
	 */
	public int getQuantityAsPrimitive()
	{
		return getQuantityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, QUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final Integer value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final int value)
	{
		setQuantity( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final int value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.quantityText</code> attribute.
	 * @return the quantityText
	 */
	public String getQuantityText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUANTITYTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.quantityText</code> attribute.
	 * @return the quantityText
	 */
	public String getQuantityText()
	{
		return getQuantityText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.quantityText</code> attribute. 
	 * @param value the quantityText
	 */
	public void setQuantityText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUANTITYTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.quantityText</code> attribute. 
	 * @param value the quantityText
	 */
	public void setQuantityText(final String value)
	{
		setQuantityText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.unit</code> attribute.
	 * @return the unit
	 */
	public String getUnit(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoiceEntry.unit</code> attribute.
	 * @return the unit
	 */
	public String getUnit()
	{
		return getUnit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoiceEntry.unit</code> attribute. 
	 * @param value the unit
	 */
	public void setUnit(final String value)
	{
		setUnit( getSession().getSessionContext(), value );
	}
	
}
