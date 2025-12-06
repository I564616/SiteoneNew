/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.InvoiceEntry;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.payment.PaymentInfo;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.Invoice SiteOneInvoice}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedInvoice extends GenericItem
{
	/** Qualifier of the <code>SiteOneInvoice.invoiceNumber</code> attribute **/
	public static final String INVOICENUMBER = "invoiceNumber";
	/** Qualifier of the <code>SiteOneInvoice.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>SiteOneInvoice.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>SiteOneInvoice.invoicedDate</code> attribute **/
	public static final String INVOICEDDATE = "invoicedDate";
	/** Qualifier of the <code>SiteOneInvoice.purchaseOrderNumber</code> attribute **/
	public static final String PURCHASEORDERNUMBER = "purchaseOrderNumber";
	/** Qualifier of the <code>SiteOneInvoice.user</code> attribute **/
	public static final String USER = "user";
	/** Qualifier of the <code>SiteOneInvoice.currency</code> attribute **/
	public static final String CURRENCY = "currency";
	/** Qualifier of the <code>SiteOneInvoice.billingTerms</code> attribute **/
	public static final String BILLINGTERMS = "billingTerms";
	/** Qualifier of the <code>SiteOneInvoice.totalTax</code> attribute **/
	public static final String TOTALTAX = "totalTax";
	/** Qualifier of the <code>SiteOneInvoice.subTotal</code> attribute **/
	public static final String SUBTOTAL = "subTotal";
	/** Qualifier of the <code>SiteOneInvoice.orderTotalPrice</code> attribute **/
	public static final String ORDERTOTALPRICE = "orderTotalPrice";
	/** Qualifier of the <code>SiteOneInvoice.storeId</code> attribute **/
	public static final String STOREID = "storeId";
	/** Qualifier of the <code>SiteOneInvoice.storeName</code> attribute **/
	public static final String STORENAME = "storeName";
	/** Qualifier of the <code>SiteOneInvoice.deliveryMode</code> attribute **/
	public static final String DELIVERYMODE = "deliveryMode";
	/** Qualifier of the <code>SiteOneInvoice.address</code> attribute **/
	public static final String ADDRESS = "address";
	/** Qualifier of the <code>SiteOneInvoice.contact_firstName</code> attribute **/
	public static final String CONTACT_FIRSTNAME = "contact_firstName";
	/** Qualifier of the <code>SiteOneInvoice.contact_lastName</code> attribute **/
	public static final String CONTACT_LASTNAME = "contact_lastName";
	/** Qualifier of the <code>SiteOneInvoice.contact_phone</code> attribute **/
	public static final String CONTACT_PHONE = "contact_phone";
	/** Qualifier of the <code>SiteOneInvoice.contact_emailid</code> attribute **/
	public static final String CONTACT_EMAILID = "contact_emailid";
	/** Qualifier of the <code>SiteOneInvoice.pickupOrDeliveryTime</code> attribute **/
	public static final String PICKUPORDELIVERYTIME = "pickupOrDeliveryTime";
	/** Qualifier of the <code>SiteOneInvoice.instructions</code> attribute **/
	public static final String INSTRUCTIONS = "instructions";
	/** Qualifier of the <code>SiteOneInvoice.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>SiteOneInvoice.freight</code> attribute **/
	public static final String FREIGHT = "freight";
	/** Qualifier of the <code>SiteOneInvoice.totalPayment</code> attribute **/
	public static final String TOTALPAYMENT = "totalPayment";
	/** Qualifier of the <code>SiteOneInvoice.amountDue</code> attribute **/
	public static final String AMOUNTDUE = "amountDue";
	/** Qualifier of the <code>SiteOneInvoice.salesAssociate</code> attribute **/
	public static final String SALESASSOCIATE = "salesAssociate";
	/** Qualifier of the <code>SiteOneInvoice.terms</code> attribute **/
	public static final String TERMS = "terms";
	/** Qualifier of the <code>SiteOneInvoice.remitaddress</code> attribute **/
	public static final String REMITADDRESS = "remitaddress";
	/** Qualifier of the <code>SiteOneInvoice.creditCardType</code> attribute **/
	public static final String CREDITCARDTYPE = "creditCardType";
	/** Qualifier of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute **/
	public static final String CCNUMBERLASTFOUR = "ccNumberLastFour";
	/** Qualifier of the <code>SiteOneInvoice.paymentType</code> attribute **/
	public static final String PAYMENTTYPE = "paymentType";
	/** Qualifier of the <code>SiteOneInvoice.billingAccNumber</code> attribute **/
	public static final String BILLINGACCNUMBER = "billingAccNumber";
	/** Qualifier of the <code>SiteOneInvoice.orderedDate</code> attribute **/
	public static final String ORDEREDDATE = "orderedDate";
	/** Qualifier of the <code>SiteOneInvoice.billingaddress</code> attribute **/
	public static final String BILLINGADDRESS = "billingaddress";
	/** Qualifier of the <code>SiteOneInvoice.requestedFor</code> attribute **/
	public static final String REQUESTEDFOR = "requestedFor";
	/** Qualifier of the <code>SiteOneInvoice.shipVia</code> attribute **/
	public static final String SHIPVIA = "shipVia";
	/** Qualifier of the <code>SiteOneInvoice.authNumber</code> attribute **/
	public static final String AUTHNUMBER = "authNumber";
	/** Qualifier of the <code>SiteOneInvoice.branchNumber</code> attribute **/
	public static final String BRANCHNUMBER = "branchNumber";
	/** Qualifier of the <code>SiteOneInvoice.branchAddress</code> attribute **/
	public static final String BRANCHADDRESS = "branchAddress";
	/** Qualifier of the <code>SiteOneInvoice.deliveryBranchNumber</code> attribute **/
	public static final String DELIVERYBRANCHNUMBER = "deliveryBranchNumber";
	/** Qualifier of the <code>SiteOneInvoice.customerObsessedContactName</code> attribute **/
	public static final String CUSTOMEROBSESSEDCONTACTNAME = "customerObsessedContactName";
	/** Qualifier of the <code>SiteOneInvoice.customerObsessedPhoneNumber</code> attribute **/
	public static final String CUSTOMEROBSESSEDPHONENUMBER = "customerObsessedPhoneNumber";
	/** Qualifier of the <code>SiteOneInvoice.customerObsessedEmail</code> attribute **/
	public static final String CUSTOMEROBSESSEDEMAIL = "customerObsessedEmail";
	/** Qualifier of the <code>SiteOneInvoice.customerObsessedContactTitle</code> attribute **/
	public static final String CUSTOMEROBSESSEDCONTACTTITLE = "customerObsessedContactTitle";
	/** Qualifier of the <code>SiteOneInvoice.pickupOrDeliveryDateTime</code> attribute **/
	public static final String PICKUPORDELIVERYDATETIME = "pickupOrDeliveryDateTime";
	/** Qualifier of the <code>SiteOneInvoice.orderShipmentActualId</code> attribute **/
	public static final String ORDERSHIPMENTACTUALID = "orderShipmentActualId";
	/** Qualifier of the <code>SiteOneInvoice.instructionsText</code> attribute **/
	public static final String INSTRUCTIONSTEXT = "instructionsText";
	/** Qualifier of the <code>SiteOneInvoice.subTotalText</code> attribute **/
	public static final String SUBTOTALTEXT = "subTotalText";
	/** Qualifier of the <code>SiteOneInvoice.orderTotalPriceText</code> attribute **/
	public static final String ORDERTOTALPRICETEXT = "orderTotalPriceText";
	/** Qualifier of the <code>SiteOneInvoice.totalTaxText</code> attribute **/
	public static final String TOTALTAXTEXT = "totalTaxText";
	/** Qualifier of the <code>SiteOneInvoice.accountDisplay</code> attribute **/
	public static final String ACCOUNTDISPLAY = "accountDisplay";
	/** Qualifier of the <code>SiteOneInvoice.paymentInfoList</code> attribute **/
	public static final String PAYMENTINFOLIST = "paymentInfoList";
	/** Qualifier of the <code>SiteOneInvoice.poaPaymentInfoList</code> attribute **/
	public static final String POAPAYMENTINFOLIST = "poaPaymentInfoList";
	/** Qualifier of the <code>SiteOneInvoice.paymentStatus</code> attribute **/
	public static final String PAYMENTSTATUS = "paymentStatus";
	/** Qualifier of the <code>SiteOneInvoice.dueDate</code> attribute **/
	public static final String DUEDATE = "dueDate";
	/** Qualifier of the <code>SiteOneInvoice.homeBranchNumber</code> attribute **/
	public static final String HOMEBRANCHNUMBER = "homeBranchNumber";
	/** Qualifier of the <code>SiteOneInvoice.entries</code> attribute **/
	public static final String ENTRIES = "entries";
	/**
	* {@link OneToManyHandler} for handling 1:n ENTRIES's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<InvoiceEntry> ENTRIESHANDLER = new OneToManyHandler<InvoiceEntry>(
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
		tmp.put(INVOICENUMBER, AttributeMode.INITIAL);
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(INVOICEDDATE, AttributeMode.INITIAL);
		tmp.put(PURCHASEORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(USER, AttributeMode.INITIAL);
		tmp.put(CURRENCY, AttributeMode.INITIAL);
		tmp.put(BILLINGTERMS, AttributeMode.INITIAL);
		tmp.put(TOTALTAX, AttributeMode.INITIAL);
		tmp.put(SUBTOTAL, AttributeMode.INITIAL);
		tmp.put(ORDERTOTALPRICE, AttributeMode.INITIAL);
		tmp.put(STOREID, AttributeMode.INITIAL);
		tmp.put(STORENAME, AttributeMode.INITIAL);
		tmp.put(DELIVERYMODE, AttributeMode.INITIAL);
		tmp.put(ADDRESS, AttributeMode.INITIAL);
		tmp.put(CONTACT_FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(CONTACT_LASTNAME, AttributeMode.INITIAL);
		tmp.put(CONTACT_PHONE, AttributeMode.INITIAL);
		tmp.put(CONTACT_EMAILID, AttributeMode.INITIAL);
		tmp.put(PICKUPORDELIVERYTIME, AttributeMode.INITIAL);
		tmp.put(INSTRUCTIONS, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(FREIGHT, AttributeMode.INITIAL);
		tmp.put(TOTALPAYMENT, AttributeMode.INITIAL);
		tmp.put(AMOUNTDUE, AttributeMode.INITIAL);
		tmp.put(SALESASSOCIATE, AttributeMode.INITIAL);
		tmp.put(TERMS, AttributeMode.INITIAL);
		tmp.put(REMITADDRESS, AttributeMode.INITIAL);
		tmp.put(CREDITCARDTYPE, AttributeMode.INITIAL);
		tmp.put(CCNUMBERLASTFOUR, AttributeMode.INITIAL);
		tmp.put(PAYMENTTYPE, AttributeMode.INITIAL);
		tmp.put(BILLINGACCNUMBER, AttributeMode.INITIAL);
		tmp.put(ORDEREDDATE, AttributeMode.INITIAL);
		tmp.put(BILLINGADDRESS, AttributeMode.INITIAL);
		tmp.put(REQUESTEDFOR, AttributeMode.INITIAL);
		tmp.put(SHIPVIA, AttributeMode.INITIAL);
		tmp.put(AUTHNUMBER, AttributeMode.INITIAL);
		tmp.put(BRANCHNUMBER, AttributeMode.INITIAL);
		tmp.put(BRANCHADDRESS, AttributeMode.INITIAL);
		tmp.put(DELIVERYBRANCHNUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMEROBSESSEDCONTACTNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEROBSESSEDPHONENUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMEROBSESSEDEMAIL, AttributeMode.INITIAL);
		tmp.put(CUSTOMEROBSESSEDCONTACTTITLE, AttributeMode.INITIAL);
		tmp.put(PICKUPORDELIVERYDATETIME, AttributeMode.INITIAL);
		tmp.put(ORDERSHIPMENTACTUALID, AttributeMode.INITIAL);
		tmp.put(INSTRUCTIONSTEXT, AttributeMode.INITIAL);
		tmp.put(SUBTOTALTEXT, AttributeMode.INITIAL);
		tmp.put(ORDERTOTALPRICETEXT, AttributeMode.INITIAL);
		tmp.put(TOTALTAXTEXT, AttributeMode.INITIAL);
		tmp.put(ACCOUNTDISPLAY, AttributeMode.INITIAL);
		tmp.put(PAYMENTINFOLIST, AttributeMode.INITIAL);
		tmp.put(POAPAYMENTINFOLIST, AttributeMode.INITIAL);
		tmp.put(PAYMENTSTATUS, AttributeMode.INITIAL);
		tmp.put(DUEDATE, AttributeMode.INITIAL);
		tmp.put(HOMEBRANCHNUMBER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.accountDisplay</code> attribute.
	 * @return the accountDisplay
	 */
	public String getAccountDisplay(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTDISPLAY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.accountDisplay</code> attribute.
	 * @return the accountDisplay
	 */
	public String getAccountDisplay()
	{
		return getAccountDisplay( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.accountDisplay</code> attribute. 
	 * @param value the accountDisplay
	 */
	public void setAccountDisplay(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTDISPLAY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.accountDisplay</code> attribute. 
	 * @param value the accountDisplay
	 */
	public void setAccountDisplay(final String value)
	{
		setAccountDisplay( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.address</code> attribute.
	 * @return the address - pick up or delivery address
	 */
	public Address getAddress(final SessionContext ctx)
	{
		return (Address)getProperty( ctx, ADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.address</code> attribute.
	 * @return the address - pick up or delivery address
	 */
	public Address getAddress()
	{
		return getAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.address</code> attribute. 
	 * @param value the address - pick up or delivery address
	 */
	public void setAddress(final SessionContext ctx, final Address value)
	{
		setProperty(ctx, ADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.address</code> attribute. 
	 * @param value the address - pick up or delivery address
	 */
	public void setAddress(final Address value)
	{
		setAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.amountDue</code> attribute.
	 * @return the amountDue
	 */
	public String getAmountDue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMOUNTDUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.amountDue</code> attribute.
	 * @return the amountDue
	 */
	public String getAmountDue()
	{
		return getAmountDue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.amountDue</code> attribute. 
	 * @param value the amountDue
	 */
	public void setAmountDue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMOUNTDUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.amountDue</code> attribute. 
	 * @param value the amountDue
	 */
	public void setAmountDue(final String value)
	{
		setAmountDue( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.authNumber</code> attribute.
	 * @return the authNumber
	 */
	public String getAuthNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AUTHNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.authNumber</code> attribute.
	 * @return the authNumber
	 */
	public String getAuthNumber()
	{
		return getAuthNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.authNumber</code> attribute. 
	 * @param value the authNumber
	 */
	public void setAuthNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AUTHNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.authNumber</code> attribute. 
	 * @param value the authNumber
	 */
	public void setAuthNumber(final String value)
	{
		setAuthNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.billingAccNumber</code> attribute.
	 * @return the billingAccNumber
	 */
	public String getBillingAccNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BILLINGACCNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.billingAccNumber</code> attribute.
	 * @return the billingAccNumber
	 */
	public String getBillingAccNumber()
	{
		return getBillingAccNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.billingAccNumber</code> attribute. 
	 * @param value the billingAccNumber
	 */
	public void setBillingAccNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BILLINGACCNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.billingAccNumber</code> attribute. 
	 * @param value the billingAccNumber
	 */
	public void setBillingAccNumber(final String value)
	{
		setBillingAccNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.billingaddress</code> attribute.
	 * @return the billingaddress
	 */
	public Address getBillingaddress(final SessionContext ctx)
	{
		return (Address)getProperty( ctx, BILLINGADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.billingaddress</code> attribute.
	 * @return the billingaddress
	 */
	public Address getBillingaddress()
	{
		return getBillingaddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.billingaddress</code> attribute. 
	 * @param value the billingaddress
	 */
	public void setBillingaddress(final SessionContext ctx, final Address value)
	{
		setProperty(ctx, BILLINGADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.billingaddress</code> attribute. 
	 * @param value the billingaddress
	 */
	public void setBillingaddress(final Address value)
	{
		setBillingaddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.billingTerms</code> attribute.
	 * @return the billingTerms
	 */
	public String getBillingTerms(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BILLINGTERMS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.billingTerms</code> attribute.
	 * @return the billingTerms
	 */
	public String getBillingTerms()
	{
		return getBillingTerms( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.billingTerms</code> attribute. 
	 * @param value the billingTerms
	 */
	public void setBillingTerms(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BILLINGTERMS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.billingTerms</code> attribute. 
	 * @param value the billingTerms
	 */
	public void setBillingTerms(final String value)
	{
		setBillingTerms( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.branchAddress</code> attribute.
	 * @return the branchAddress
	 */
	public Address getBranchAddress(final SessionContext ctx)
	{
		return (Address)getProperty( ctx, BRANCHADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.branchAddress</code> attribute.
	 * @return the branchAddress
	 */
	public Address getBranchAddress()
	{
		return getBranchAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.branchAddress</code> attribute. 
	 * @param value the branchAddress
	 */
	public void setBranchAddress(final SessionContext ctx, final Address value)
	{
		setProperty(ctx, BRANCHADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.branchAddress</code> attribute. 
	 * @param value the branchAddress
	 */
	public void setBranchAddress(final Address value)
	{
		setBranchAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.branchNumber</code> attribute.
	 * @return the branchNumber
	 */
	public String getBranchNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.branchNumber</code> attribute.
	 * @return the branchNumber
	 */
	public String getBranchNumber()
	{
		return getBranchNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.branchNumber</code> attribute. 
	 * @param value the branchNumber
	 */
	public void setBranchNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.branchNumber</code> attribute. 
	 * @param value the branchNumber
	 */
	public void setBranchNumber(final String value)
	{
		setBranchNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute.
	 * @return the ccNumberLastFour
	 */
	public Integer getCcNumberLastFour(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, CCNUMBERLASTFOUR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute.
	 * @return the ccNumberLastFour
	 */
	public Integer getCcNumberLastFour()
	{
		return getCcNumberLastFour( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute. 
	 * @return the ccNumberLastFour
	 */
	public int getCcNumberLastFourAsPrimitive(final SessionContext ctx)
	{
		Integer value = getCcNumberLastFour( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute. 
	 * @return the ccNumberLastFour
	 */
	public int getCcNumberLastFourAsPrimitive()
	{
		return getCcNumberLastFourAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute. 
	 * @param value the ccNumberLastFour
	 */
	public void setCcNumberLastFour(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, CCNUMBERLASTFOUR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute. 
	 * @param value the ccNumberLastFour
	 */
	public void setCcNumberLastFour(final Integer value)
	{
		setCcNumberLastFour( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute. 
	 * @param value the ccNumberLastFour
	 */
	public void setCcNumberLastFour(final SessionContext ctx, final int value)
	{
		setCcNumberLastFour( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.ccNumberLastFour</code> attribute. 
	 * @param value the ccNumberLastFour
	 */
	public void setCcNumberLastFour(final int value)
	{
		setCcNumberLastFour( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_emailid</code> attribute.
	 * @return the contact_emailid
	 */
	public String getContact_emailid(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACT_EMAILID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_emailid</code> attribute.
	 * @return the contact_emailid
	 */
	public String getContact_emailid()
	{
		return getContact_emailid( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_emailid</code> attribute. 
	 * @param value the contact_emailid
	 */
	public void setContact_emailid(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACT_EMAILID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_emailid</code> attribute. 
	 * @param value the contact_emailid
	 */
	public void setContact_emailid(final String value)
	{
		setContact_emailid( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_firstName</code> attribute.
	 * @return the contact_firstName
	 */
	public String getContact_firstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACT_FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_firstName</code> attribute.
	 * @return the contact_firstName
	 */
	public String getContact_firstName()
	{
		return getContact_firstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_firstName</code> attribute. 
	 * @param value the contact_firstName
	 */
	public void setContact_firstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACT_FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_firstName</code> attribute. 
	 * @param value the contact_firstName
	 */
	public void setContact_firstName(final String value)
	{
		setContact_firstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_lastName</code> attribute.
	 * @return the contact_lastName
	 */
	public String getContact_lastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACT_LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_lastName</code> attribute.
	 * @return the contact_lastName
	 */
	public String getContact_lastName()
	{
		return getContact_lastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_lastName</code> attribute. 
	 * @param value the contact_lastName
	 */
	public void setContact_lastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACT_LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_lastName</code> attribute. 
	 * @param value the contact_lastName
	 */
	public void setContact_lastName(final String value)
	{
		setContact_lastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_phone</code> attribute.
	 * @return the contact_phone
	 */
	public String getContact_phone(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACT_PHONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.contact_phone</code> attribute.
	 * @return the contact_phone
	 */
	public String getContact_phone()
	{
		return getContact_phone( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_phone</code> attribute. 
	 * @param value the contact_phone
	 */
	public void setContact_phone(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACT_PHONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.contact_phone</code> attribute. 
	 * @param value the contact_phone
	 */
	public void setContact_phone(final String value)
	{
		setContact_phone( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.creditCardType</code> attribute.
	 * @return the creditCardType
	 */
	public String getCreditCardType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.creditCardType</code> attribute.
	 * @return the creditCardType
	 */
	public String getCreditCardType()
	{
		return getCreditCardType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.creditCardType</code> attribute. 
	 * @param value the creditCardType
	 */
	public void setCreditCardType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.creditCardType</code> attribute. 
	 * @param value the creditCardType
	 */
	public void setCreditCardType(final String value)
	{
		setCreditCardType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.currency</code> attribute.
	 * @return the currency
	 */
	public String getCurrency(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CURRENCY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.currency</code> attribute.
	 * @return the currency
	 */
	public String getCurrency()
	{
		return getCurrency( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CURRENCY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.currency</code> attribute. 
	 * @param value the currency
	 */
	public void setCurrency(final String value)
	{
		setCurrency( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedContactName</code> attribute.
	 * @return the customerObsessedContactName
	 */
	public String getCustomerObsessedContactName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEROBSESSEDCONTACTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedContactName</code> attribute.
	 * @return the customerObsessedContactName
	 */
	public String getCustomerObsessedContactName()
	{
		return getCustomerObsessedContactName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedContactName</code> attribute. 
	 * @param value the customerObsessedContactName
	 */
	public void setCustomerObsessedContactName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEROBSESSEDCONTACTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedContactName</code> attribute. 
	 * @param value the customerObsessedContactName
	 */
	public void setCustomerObsessedContactName(final String value)
	{
		setCustomerObsessedContactName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedContactTitle</code> attribute.
	 * @return the customerObsessedContactTitle
	 */
	public String getCustomerObsessedContactTitle(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEROBSESSEDCONTACTTITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedContactTitle</code> attribute.
	 * @return the customerObsessedContactTitle
	 */
	public String getCustomerObsessedContactTitle()
	{
		return getCustomerObsessedContactTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedContactTitle</code> attribute. 
	 * @param value the customerObsessedContactTitle
	 */
	public void setCustomerObsessedContactTitle(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEROBSESSEDCONTACTTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedContactTitle</code> attribute. 
	 * @param value the customerObsessedContactTitle
	 */
	public void setCustomerObsessedContactTitle(final String value)
	{
		setCustomerObsessedContactTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedEmail</code> attribute.
	 * @return the customerObsessedEmail
	 */
	public String getCustomerObsessedEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEROBSESSEDEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedEmail</code> attribute.
	 * @return the customerObsessedEmail
	 */
	public String getCustomerObsessedEmail()
	{
		return getCustomerObsessedEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedEmail</code> attribute. 
	 * @param value the customerObsessedEmail
	 */
	public void setCustomerObsessedEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEROBSESSEDEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedEmail</code> attribute. 
	 * @param value the customerObsessedEmail
	 */
	public void setCustomerObsessedEmail(final String value)
	{
		setCustomerObsessedEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedPhoneNumber</code> attribute.
	 * @return the customerObsessedPhoneNumber
	 */
	public String getCustomerObsessedPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEROBSESSEDPHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.customerObsessedPhoneNumber</code> attribute.
	 * @return the customerObsessedPhoneNumber
	 */
	public String getCustomerObsessedPhoneNumber()
	{
		return getCustomerObsessedPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedPhoneNumber</code> attribute. 
	 * @param value the customerObsessedPhoneNumber
	 */
	public void setCustomerObsessedPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEROBSESSEDPHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.customerObsessedPhoneNumber</code> attribute. 
	 * @param value the customerObsessedPhoneNumber
	 */
	public void setCustomerObsessedPhoneNumber(final String value)
	{
		setCustomerObsessedPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.deliveryBranchNumber</code> attribute.
	 * @return the deliveryBranchNumber
	 */
	public String getDeliveryBranchNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DELIVERYBRANCHNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.deliveryBranchNumber</code> attribute.
	 * @return the deliveryBranchNumber
	 */
	public String getDeliveryBranchNumber()
	{
		return getDeliveryBranchNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.deliveryBranchNumber</code> attribute. 
	 * @param value the deliveryBranchNumber
	 */
	public void setDeliveryBranchNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DELIVERYBRANCHNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.deliveryBranchNumber</code> attribute. 
	 * @param value the deliveryBranchNumber
	 */
	public void setDeliveryBranchNumber(final String value)
	{
		setDeliveryBranchNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.deliveryMode</code> attribute.
	 * @return the deliveryMode
	 */
	public String getDeliveryMode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DELIVERYMODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.deliveryMode</code> attribute.
	 * @return the deliveryMode
	 */
	public String getDeliveryMode()
	{
		return getDeliveryMode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.deliveryMode</code> attribute. 
	 * @param value the deliveryMode
	 */
	public void setDeliveryMode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DELIVERYMODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.deliveryMode</code> attribute. 
	 * @param value the deliveryMode
	 */
	public void setDeliveryMode(final String value)
	{
		setDeliveryMode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.dueDate</code> attribute.
	 * @return the dueDate
	 */
	public Date getDueDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, DUEDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.dueDate</code> attribute.
	 * @return the dueDate
	 */
	public Date getDueDate()
	{
		return getDueDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.dueDate</code> attribute. 
	 * @param value the dueDate
	 */
	public void setDueDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, DUEDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.dueDate</code> attribute. 
	 * @param value the dueDate
	 */
	public void setDueDate(final Date value)
	{
		setDueDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.entries</code> attribute.
	 * @return the entries
	 */
	public Collection<InvoiceEntry> getEntries(final SessionContext ctx)
	{
		return ENTRIESHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.entries</code> attribute.
	 * @return the entries
	 */
	public Collection<InvoiceEntry> getEntries()
	{
		return getEntries( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.entries</code> attribute. 
	 * @param value the entries
	 */
	public void setEntries(final SessionContext ctx, final Collection<InvoiceEntry> value)
	{
		ENTRIESHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.entries</code> attribute. 
	 * @param value the entries
	 */
	public void setEntries(final Collection<InvoiceEntry> value)
	{
		setEntries( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to entries. 
	 * @param value the item to add to entries
	 */
	public void addToEntries(final SessionContext ctx, final InvoiceEntry value)
	{
		ENTRIESHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to entries. 
	 * @param value the item to add to entries
	 */
	public void addToEntries(final InvoiceEntry value)
	{
		addToEntries( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from entries. 
	 * @param value the item to remove from entries
	 */
	public void removeFromEntries(final SessionContext ctx, final InvoiceEntry value)
	{
		ENTRIESHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from entries. 
	 * @param value the item to remove from entries
	 */
	public void removeFromEntries(final InvoiceEntry value)
	{
		removeFromEntries( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.freight</code> attribute.
	 * @return the freight
	 */
	public String getFreight(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FREIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.freight</code> attribute.
	 * @return the freight
	 */
	public String getFreight()
	{
		return getFreight( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.freight</code> attribute. 
	 * @param value the freight
	 */
	public void setFreight(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FREIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.freight</code> attribute. 
	 * @param value the freight
	 */
	public void setFreight(final String value)
	{
		setFreight( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.homeBranchNumber</code> attribute.
	 * @return the homeBranchNumber - Guest customer Home Branch
	 */
	public String getHomeBranchNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, HOMEBRANCHNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.homeBranchNumber</code> attribute.
	 * @return the homeBranchNumber - Guest customer Home Branch
	 */
	public String getHomeBranchNumber()
	{
		return getHomeBranchNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.homeBranchNumber</code> attribute. 
	 * @param value the homeBranchNumber - Guest customer Home Branch
	 */
	public void setHomeBranchNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, HOMEBRANCHNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.homeBranchNumber</code> attribute. 
	 * @param value the homeBranchNumber - Guest customer Home Branch
	 */
	public void setHomeBranchNumber(final String value)
	{
		setHomeBranchNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.instructions</code> attribute.
	 * @return the instructions - Deprecated. check instructionsText
	 */
	public String getInstructions(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSTRUCTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.instructions</code> attribute.
	 * @return the instructions - Deprecated. check instructionsText
	 */
	public String getInstructions()
	{
		return getInstructions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.instructions</code> attribute. 
	 * @param value the instructions - Deprecated. check instructionsText
	 */
	public void setInstructions(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSTRUCTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.instructions</code> attribute. 
	 * @param value the instructions - Deprecated. check instructionsText
	 */
	public void setInstructions(final String value)
	{
		setInstructions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.instructionsText</code> attribute.
	 * @return the instructionsText
	 */
	public String getInstructionsText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSTRUCTIONSTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.instructionsText</code> attribute.
	 * @return the instructionsText
	 */
	public String getInstructionsText()
	{
		return getInstructionsText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.instructionsText</code> attribute. 
	 * @param value the instructionsText
	 */
	public void setInstructionsText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSTRUCTIONSTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.instructionsText</code> attribute. 
	 * @param value the instructionsText
	 */
	public void setInstructionsText(final String value)
	{
		setInstructionsText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.invoicedDate</code> attribute.
	 * @return the invoicedDate
	 */
	public Date getInvoicedDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, INVOICEDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.invoicedDate</code> attribute.
	 * @return the invoicedDate
	 */
	public Date getInvoicedDate()
	{
		return getInvoicedDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.invoicedDate</code> attribute. 
	 * @param value the invoicedDate
	 */
	public void setInvoicedDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, INVOICEDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.invoicedDate</code> attribute. 
	 * @param value the invoicedDate
	 */
	public void setInvoicedDate(final Date value)
	{
		setInvoicedDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.invoiceNumber</code> attribute.
	 * @return the invoiceNumber - Attribute contains unique invoice number
	 */
	public String getInvoiceNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.invoiceNumber</code> attribute.
	 * @return the invoiceNumber - Attribute contains unique invoice number
	 */
	public String getInvoiceNumber()
	{
		return getInvoiceNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber - Attribute contains unique invoice number
	 */
	public void setInvoiceNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber - Attribute contains unique invoice number
	 */
	public void setInvoiceNumber(final String value)
	{
		setInvoiceNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderedDate</code> attribute.
	 * @return the orderedDate
	 */
	public Date getOrderedDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, ORDEREDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderedDate</code> attribute.
	 * @return the orderedDate
	 */
	public Date getOrderedDate()
	{
		return getOrderedDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderedDate</code> attribute. 
	 * @param value the orderedDate
	 */
	public void setOrderedDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, ORDEREDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderedDate</code> attribute. 
	 * @param value the orderedDate
	 */
	public void setOrderedDate(final Date value)
	{
		setOrderedDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderNumber</code> attribute.
	 * @return the orderNumber - Attribute contains unique order number
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderNumber</code> attribute.
	 * @return the orderNumber - Attribute contains unique order number
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderNumber</code> attribute. 
	 * @param value the orderNumber - Attribute contains unique order number
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderNumber</code> attribute. 
	 * @param value the orderNumber - Attribute contains unique order number
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderShipmentActualId</code> attribute.
	 * @return the orderShipmentActualId
	 */
	public String getOrderShipmentActualId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERSHIPMENTACTUALID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderShipmentActualId</code> attribute.
	 * @return the orderShipmentActualId
	 */
	public String getOrderShipmentActualId()
	{
		return getOrderShipmentActualId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderShipmentActualId</code> attribute. 
	 * @param value the orderShipmentActualId
	 */
	public void setOrderShipmentActualId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERSHIPMENTACTUALID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderShipmentActualId</code> attribute. 
	 * @param value the orderShipmentActualId
	 */
	public void setOrderShipmentActualId(final String value)
	{
		setOrderShipmentActualId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute.
	 * @return the orderTotalPrice
	 */
	public Double getOrderTotalPrice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, ORDERTOTALPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute.
	 * @return the orderTotalPrice
	 */
	public Double getOrderTotalPrice()
	{
		return getOrderTotalPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute. 
	 * @return the orderTotalPrice
	 */
	public double getOrderTotalPriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getOrderTotalPrice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute. 
	 * @return the orderTotalPrice
	 */
	public double getOrderTotalPriceAsPrimitive()
	{
		return getOrderTotalPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute. 
	 * @param value the orderTotalPrice
	 */
	public void setOrderTotalPrice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, ORDERTOTALPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute. 
	 * @param value the orderTotalPrice
	 */
	public void setOrderTotalPrice(final Double value)
	{
		setOrderTotalPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute. 
	 * @param value the orderTotalPrice
	 */
	public void setOrderTotalPrice(final SessionContext ctx, final double value)
	{
		setOrderTotalPrice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderTotalPrice</code> attribute. 
	 * @param value the orderTotalPrice
	 */
	public void setOrderTotalPrice(final double value)
	{
		setOrderTotalPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderTotalPriceText</code> attribute.
	 * @return the orderTotalPriceText
	 */
	public String getOrderTotalPriceText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERTOTALPRICETEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.orderTotalPriceText</code> attribute.
	 * @return the orderTotalPriceText
	 */
	public String getOrderTotalPriceText()
	{
		return getOrderTotalPriceText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderTotalPriceText</code> attribute. 
	 * @param value the orderTotalPriceText
	 */
	public void setOrderTotalPriceText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERTOTALPRICETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.orderTotalPriceText</code> attribute. 
	 * @param value the orderTotalPriceText
	 */
	public void setOrderTotalPriceText(final String value)
	{
		setOrderTotalPriceText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.paymentInfoList</code> attribute.
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList(final SessionContext ctx)
	{
		List<PaymentInfo> coll = (List<PaymentInfo>)getProperty( ctx, PAYMENTINFOLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.paymentInfoList</code> attribute.
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList()
	{
		return getPaymentInfoList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.paymentInfoList</code> attribute. 
	 * @param value the paymentInfoList
	 */
	public void setPaymentInfoList(final SessionContext ctx, final List<PaymentInfo> value)
	{
		setProperty(ctx, PAYMENTINFOLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.paymentInfoList</code> attribute. 
	 * @param value the paymentInfoList
	 */
	public void setPaymentInfoList(final List<PaymentInfo> value)
	{
		setPaymentInfoList( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.paymentStatus</code> attribute.
	 * @return the paymentStatus
	 */
	public String getPaymentStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.paymentStatus</code> attribute.
	 * @return the paymentStatus
	 */
	public String getPaymentStatus()
	{
		return getPaymentStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.paymentStatus</code> attribute. 
	 * @param value the paymentStatus
	 */
	public void setPaymentStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.paymentStatus</code> attribute. 
	 * @param value the paymentStatus
	 */
	public void setPaymentStatus(final String value)
	{
		setPaymentStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.paymentType</code> attribute.
	 * @return the paymentType
	 */
	public String getPaymentType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAYMENTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.paymentType</code> attribute.
	 * @return the paymentType
	 */
	public String getPaymentType()
	{
		return getPaymentType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.paymentType</code> attribute. 
	 * @param value the paymentType
	 */
	public void setPaymentType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAYMENTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.paymentType</code> attribute. 
	 * @param value the paymentType
	 */
	public void setPaymentType(final String value)
	{
		setPaymentType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.pickupOrDeliveryDateTime</code> attribute.
	 * @return the pickupOrDeliveryDateTime
	 */
	public Date getPickupOrDeliveryDateTime(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, PICKUPORDELIVERYDATETIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.pickupOrDeliveryDateTime</code> attribute.
	 * @return the pickupOrDeliveryDateTime
	 */
	public Date getPickupOrDeliveryDateTime()
	{
		return getPickupOrDeliveryDateTime( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.pickupOrDeliveryDateTime</code> attribute. 
	 * @param value the pickupOrDeliveryDateTime
	 */
	public void setPickupOrDeliveryDateTime(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, PICKUPORDELIVERYDATETIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.pickupOrDeliveryDateTime</code> attribute. 
	 * @param value the pickupOrDeliveryDateTime
	 */
	public void setPickupOrDeliveryDateTime(final Date value)
	{
		setPickupOrDeliveryDateTime( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.pickupOrDeliveryTime</code> attribute.
	 * @return the pickupOrDeliveryTime
	 */
	public String getPickupOrDeliveryTime(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PICKUPORDELIVERYTIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.pickupOrDeliveryTime</code> attribute.
	 * @return the pickupOrDeliveryTime
	 */
	public String getPickupOrDeliveryTime()
	{
		return getPickupOrDeliveryTime( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.pickupOrDeliveryTime</code> attribute. 
	 * @param value the pickupOrDeliveryTime
	 */
	public void setPickupOrDeliveryTime(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PICKUPORDELIVERYTIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.pickupOrDeliveryTime</code> attribute. 
	 * @param value the pickupOrDeliveryTime
	 */
	public void setPickupOrDeliveryTime(final String value)
	{
		setPickupOrDeliveryTime( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.poaPaymentInfoList</code> attribute.
	 * @return the poaPaymentInfoList
	 */
	public List<PaymentInfo> getPoaPaymentInfoList(final SessionContext ctx)
	{
		List<PaymentInfo> coll = (List<PaymentInfo>)getProperty( ctx, POAPAYMENTINFOLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.poaPaymentInfoList</code> attribute.
	 * @return the poaPaymentInfoList
	 */
	public List<PaymentInfo> getPoaPaymentInfoList()
	{
		return getPoaPaymentInfoList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.poaPaymentInfoList</code> attribute. 
	 * @param value the poaPaymentInfoList
	 */
	public void setPoaPaymentInfoList(final SessionContext ctx, final List<PaymentInfo> value)
	{
		setProperty(ctx, POAPAYMENTINFOLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.poaPaymentInfoList</code> attribute. 
	 * @param value the poaPaymentInfoList
	 */
	public void setPoaPaymentInfoList(final List<PaymentInfo> value)
	{
		setPoaPaymentInfoList( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.purchaseOrderNumber</code> attribute.
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PURCHASEORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.purchaseOrderNumber</code> attribute.
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber()
	{
		return getPurchaseOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.purchaseOrderNumber</code> attribute. 
	 * @param value the purchaseOrderNumber
	 */
	public void setPurchaseOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PURCHASEORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.purchaseOrderNumber</code> attribute. 
	 * @param value the purchaseOrderNumber
	 */
	public void setPurchaseOrderNumber(final String value)
	{
		setPurchaseOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.remitaddress</code> attribute.
	 * @return the remitaddress
	 */
	public Address getRemitaddress(final SessionContext ctx)
	{
		return (Address)getProperty( ctx, REMITADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.remitaddress</code> attribute.
	 * @return the remitaddress
	 */
	public Address getRemitaddress()
	{
		return getRemitaddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.remitaddress</code> attribute. 
	 * @param value the remitaddress
	 */
	public void setRemitaddress(final SessionContext ctx, final Address value)
	{
		setProperty(ctx, REMITADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.remitaddress</code> attribute. 
	 * @param value the remitaddress
	 */
	public void setRemitaddress(final Address value)
	{
		setRemitaddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.requestedFor</code> attribute.
	 * @return the requestedFor
	 */
	public String getRequestedFor(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REQUESTEDFOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.requestedFor</code> attribute.
	 * @return the requestedFor
	 */
	public String getRequestedFor()
	{
		return getRequestedFor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.requestedFor</code> attribute. 
	 * @param value the requestedFor
	 */
	public void setRequestedFor(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REQUESTEDFOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.requestedFor</code> attribute. 
	 * @param value the requestedFor
	 */
	public void setRequestedFor(final String value)
	{
		setRequestedFor( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.salesAssociate</code> attribute.
	 * @return the salesAssociate
	 */
	public String getSalesAssociate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SALESASSOCIATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.salesAssociate</code> attribute.
	 * @return the salesAssociate
	 */
	public String getSalesAssociate()
	{
		return getSalesAssociate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.salesAssociate</code> attribute. 
	 * @param value the salesAssociate
	 */
	public void setSalesAssociate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SALESASSOCIATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.salesAssociate</code> attribute. 
	 * @param value the salesAssociate
	 */
	public void setSalesAssociate(final String value)
	{
		setSalesAssociate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.shipVia</code> attribute.
	 * @return the shipVia
	 */
	public String getShipVia(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHIPVIA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.shipVia</code> attribute.
	 * @return the shipVia
	 */
	public String getShipVia()
	{
		return getShipVia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.shipVia</code> attribute. 
	 * @param value the shipVia
	 */
	public void setShipVia(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHIPVIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.shipVia</code> attribute. 
	 * @param value the shipVia
	 */
	public void setShipVia(final String value)
	{
		setShipVia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final EnumerationValue value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return getStoreId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final String value)
	{
		setStoreId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.storeName</code> attribute.
	 * @return the storeName
	 */
	public String getStoreName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STORENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.storeName</code> attribute.
	 * @return the storeName
	 */
	public String getStoreName()
	{
		return getStoreName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.storeName</code> attribute. 
	 * @param value the storeName
	 */
	public void setStoreName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STORENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.storeName</code> attribute. 
	 * @param value the storeName
	 */
	public void setStoreName(final String value)
	{
		setStoreName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.subTotal</code> attribute.
	 * @return the subTotal
	 */
	public Double getSubTotal(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, SUBTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.subTotal</code> attribute.
	 * @return the subTotal
	 */
	public Double getSubTotal()
	{
		return getSubTotal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.subTotal</code> attribute. 
	 * @return the subTotal
	 */
	public double getSubTotalAsPrimitive(final SessionContext ctx)
	{
		Double value = getSubTotal( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.subTotal</code> attribute. 
	 * @return the subTotal
	 */
	public double getSubTotalAsPrimitive()
	{
		return getSubTotalAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.subTotal</code> attribute. 
	 * @param value the subTotal
	 */
	public void setSubTotal(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, SUBTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.subTotal</code> attribute. 
	 * @param value the subTotal
	 */
	public void setSubTotal(final Double value)
	{
		setSubTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.subTotal</code> attribute. 
	 * @param value the subTotal
	 */
	public void setSubTotal(final SessionContext ctx, final double value)
	{
		setSubTotal( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.subTotal</code> attribute. 
	 * @param value the subTotal
	 */
	public void setSubTotal(final double value)
	{
		setSubTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.subTotalText</code> attribute.
	 * @return the subTotalText
	 */
	public String getSubTotalText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SUBTOTALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.subTotalText</code> attribute.
	 * @return the subTotalText
	 */
	public String getSubTotalText()
	{
		return getSubTotalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.subTotalText</code> attribute. 
	 * @param value the subTotalText
	 */
	public void setSubTotalText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SUBTOTALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.subTotalText</code> attribute. 
	 * @param value the subTotalText
	 */
	public void setSubTotalText(final String value)
	{
		setSubTotalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.terms</code> attribute.
	 * @return the terms - Terms for on account payment
	 */
	public String getTerms(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TERMS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.terms</code> attribute.
	 * @return the terms - Terms for on account payment
	 */
	public String getTerms()
	{
		return getTerms( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.terms</code> attribute. 
	 * @param value the terms - Terms for on account payment
	 */
	public void setTerms(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TERMS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.terms</code> attribute. 
	 * @param value the terms - Terms for on account payment
	 */
	public void setTerms(final String value)
	{
		setTerms( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalPayment</code> attribute.
	 * @return the totalPayment
	 */
	public String getTotalPayment(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOTALPAYMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalPayment</code> attribute.
	 * @return the totalPayment
	 */
	public String getTotalPayment()
	{
		return getTotalPayment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalPayment</code> attribute. 
	 * @param value the totalPayment
	 */
	public void setTotalPayment(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOTALPAYMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalPayment</code> attribute. 
	 * @param value the totalPayment
	 */
	public void setTotalPayment(final String value)
	{
		setTotalPayment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalTax</code> attribute.
	 * @return the totalTax
	 */
	public Double getTotalTax(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, TOTALTAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalTax</code> attribute.
	 * @return the totalTax
	 */
	public Double getTotalTax()
	{
		return getTotalTax( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalTax</code> attribute. 
	 * @return the totalTax
	 */
	public double getTotalTaxAsPrimitive(final SessionContext ctx)
	{
		Double value = getTotalTax( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalTax</code> attribute. 
	 * @return the totalTax
	 */
	public double getTotalTaxAsPrimitive()
	{
		return getTotalTaxAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalTax</code> attribute. 
	 * @param value the totalTax
	 */
	public void setTotalTax(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, TOTALTAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalTax</code> attribute. 
	 * @param value the totalTax
	 */
	public void setTotalTax(final Double value)
	{
		setTotalTax( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalTax</code> attribute. 
	 * @param value the totalTax
	 */
	public void setTotalTax(final SessionContext ctx, final double value)
	{
		setTotalTax( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalTax</code> attribute. 
	 * @param value the totalTax
	 */
	public void setTotalTax(final double value)
	{
		setTotalTax( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalTaxText</code> attribute.
	 * @return the totalTaxText
	 */
	public String getTotalTaxText(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOTALTAXTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.totalTaxText</code> attribute.
	 * @return the totalTaxText
	 */
	public String getTotalTaxText()
	{
		return getTotalTaxText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalTaxText</code> attribute. 
	 * @param value the totalTaxText
	 */
	public void setTotalTaxText(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOTALTAXTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.totalTaxText</code> attribute. 
	 * @param value the totalTaxText
	 */
	public void setTotalTaxText(final String value)
	{
		setTotalTaxText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.user</code> attribute.
	 * @return the user - OrderedBy user id
	 */
	public String getUser(final SessionContext ctx)
	{
		return (String)getProperty( ctx, USER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInvoice.user</code> attribute.
	 * @return the user - OrderedBy user id
	 */
	public String getUser()
	{
		return getUser( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.user</code> attribute. 
	 * @param value the user - OrderedBy user id
	 */
	public void setUser(final SessionContext ctx, final String value)
	{
		setProperty(ctx, USER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInvoice.user</code> attribute. 
	 * @param value the user - OrderedBy user id
	 */
	public void setUser(final String value)
	{
		setUser( getSession().getSessionContext(), value );
	}
	
}
