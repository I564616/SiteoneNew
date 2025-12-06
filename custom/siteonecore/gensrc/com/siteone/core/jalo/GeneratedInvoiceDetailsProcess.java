/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.Invoice;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.InvoiceDetailsProcess InvoiceDetailsProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedInvoiceDetailsProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>InvoiceDetailsProcess.invoiceNumber</code> attribute **/
	public static final String INVOICENUMBER = "invoiceNumber";
	/** Qualifier of the <code>InvoiceDetailsProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>InvoiceDetailsProcess.uid</code> attribute **/
	public static final String UID = "uid";
	/** Qualifier of the <code>InvoiceDetailsProcess.invoicNum</code> attribute **/
	public static final String INVOICNUM = "invoicNum";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(INVOICENUMBER, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(UID, AttributeMode.INITIAL);
		tmp.put(INVOICNUM, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public Invoice getInvoiceNumber(final SessionContext ctx)
	{
		return (Invoice)getProperty( ctx, INVOICENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public Invoice getInvoiceNumber()
	{
		return getInvoiceNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final SessionContext ctx, final Invoice value)
	{
		setProperty(ctx, INVOICENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final Invoice value)
	{
		setInvoiceNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.invoicNum</code> attribute.
	 * @return the invoicNum
	 */
	public String getInvoicNum(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICNUM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.invoicNum</code> attribute.
	 * @return the invoicNum
	 */
	public String getInvoicNum()
	{
		return getInvoicNum( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.invoicNum</code> attribute. 
	 * @param value the invoicNum
	 */
	public void setInvoicNum(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICNUM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.invoicNum</code> attribute. 
	 * @param value the invoicNum
	 */
	public void setInvoicNum(final String value)
	{
		setInvoicNum( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.uid</code> attribute.
	 * @return the uid
	 */
	public String getUid(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InvoiceDetailsProcess.uid</code> attribute.
	 * @return the uid
	 */
	public String getUid()
	{
		return getUid( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.uid</code> attribute. 
	 * @param value the uid
	 */
	public void setUid(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InvoiceDetailsProcess.uid</code> attribute. 
	 * @param value the uid
	 */
	public void setUid(final String value)
	{
		setUid( getSession().getSessionContext(), value );
	}
	
}
