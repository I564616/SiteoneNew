/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.QuoteItemDetails;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.QuoteApprovalProcess QuoteApprovalProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedQuoteApprovalProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>QuoteApprovalProcess.branchInfo</code> attribute **/
	public static final String BRANCHINFO = "branchInfo";
	/** Qualifier of the <code>QuoteApprovalProcess.accountInfo</code> attribute **/
	public static final String ACCOUNTINFO = "accountInfo";
	/** Qualifier of the <code>QuoteApprovalProcess.shipToInfo</code> attribute **/
	public static final String SHIPTOINFO = "shipToInfo";
	/** Qualifier of the <code>QuoteApprovalProcess.quoteNumber</code> attribute **/
	public static final String QUOTENUMBER = "quoteNumber";
	/** Qualifier of the <code>QuoteApprovalProcess.jobName</code> attribute **/
	public static final String JOBNAME = "jobName";
	/** Qualifier of the <code>QuoteApprovalProcess.dateSubmitted</code> attribute **/
	public static final String DATESUBMITTED = "dateSubmitted";
	/** Qualifier of the <code>QuoteApprovalProcess.expDate</code> attribute **/
	public static final String EXPDATE = "expDate";
	/** Qualifier of the <code>QuoteApprovalProcess.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>QuoteApprovalProcess.bidTotal</code> attribute **/
	public static final String BIDTOTAL = "bidTotal";
	/** Qualifier of the <code>QuoteApprovalProcess.itemDetails</code> attribute **/
	public static final String ITEMDETAILS = "itemDetails";
	/** Qualifier of the <code>QuoteApprovalProcess.modifiedItemDetails</code> attribute **/
	public static final String MODIFIEDITEMDETAILS = "modifiedItemDetails";
	/** Qualifier of the <code>QuoteApprovalProcess.itemNumber</code> attribute **/
	public static final String ITEMNUMBER = "itemNumber";
	/** Qualifier of the <code>QuoteApprovalProcess.itemDescription</code> attribute **/
	public static final String ITEMDESCRIPTION = "itemDescription";
	/** Qualifier of the <code>QuoteApprovalProcess.quantity</code> attribute **/
	public static final String QUANTITY = "quantity";
	/** Qualifier of the <code>QuoteApprovalProcess.unitPrice</code> attribute **/
	public static final String UNITPRICE = "unitPrice";
	/** Qualifier of the <code>QuoteApprovalProcess.UOM</code> attribute **/
	public static final String UOM = "UOM";
	/** Qualifier of the <code>QuoteApprovalProcess.extPrice</code> attribute **/
	public static final String EXTPRICE = "extPrice";
	/** Qualifier of the <code>QuoteApprovalProcess.notes</code> attribute **/
	public static final String NOTES = "notes";
	/** Qualifier of the <code>QuoteApprovalProcess.accountName</code> attribute **/
	public static final String ACCOUNTNAME = "accountName";
	/** Qualifier of the <code>QuoteApprovalProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>QuoteApprovalProcess.customerEmailAddress</code> attribute **/
	public static final String CUSTOMEREMAILADDRESS = "customerEmailAddress";
	/** Qualifier of the <code>QuoteApprovalProcess.approverName</code> attribute **/
	public static final String APPROVERNAME = "approverName";
	/** Qualifier of the <code>QuoteApprovalProcess.approverEmailAddress</code> attribute **/
	public static final String APPROVEREMAILADDRESS = "approverEmailAddress";
	/** Qualifier of the <code>QuoteApprovalProcess.accountManagerEmail</code> attribute **/
	public static final String ACCOUNTMANAGEREMAIL = "accountManagerEmail";
	/** Qualifier of the <code>QuoteApprovalProcess.insideSalesRepEmail</code> attribute **/
	public static final String INSIDESALESREPEMAIL = "insideSalesRepEmail";
	/** Qualifier of the <code>QuoteApprovalProcess.branchManagerEmail</code> attribute **/
	public static final String BRANCHMANAGEREMAIL = "branchManagerEmail";
	/** Qualifier of the <code>QuoteApprovalProcess.writerEmail</code> attribute **/
	public static final String WRITEREMAIL = "writerEmail";
	/** Qualifier of the <code>QuoteApprovalProcess.pricerEmail</code> attribute **/
	public static final String PRICEREMAIL = "pricerEmail";
	/** Qualifier of the <code>QuoteApprovalProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	/** Qualifier of the <code>QuoteApprovalProcess.itemCount</code> attribute **/
	public static final String ITEMCOUNT = "itemCount";
	/** Qualifier of the <code>QuoteApprovalProcess.quoteId</code> attribute **/
	public static final String QUOTEID = "quoteId";
	/** Qualifier of the <code>QuoteApprovalProcess.accountId</code> attribute **/
	public static final String ACCOUNTID = "accountId";
	/** Qualifier of the <code>QuoteApprovalProcess.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	/** Qualifier of the <code>QuoteApprovalProcess.poNumber</code> attribute **/
	public static final String PONUMBER = "poNumber";
	/** Qualifier of the <code>QuoteApprovalProcess.isFullQuote</code> attribute **/
	public static final String ISFULLQUOTE = "isFullQuote";
	/** Qualifier of the <code>QuoteApprovalProcess.optionalNotes</code> attribute **/
	public static final String OPTIONALNOTES = "optionalNotes";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(BRANCHINFO, AttributeMode.INITIAL);
		tmp.put(ACCOUNTINFO, AttributeMode.INITIAL);
		tmp.put(SHIPTOINFO, AttributeMode.INITIAL);
		tmp.put(QUOTENUMBER, AttributeMode.INITIAL);
		tmp.put(JOBNAME, AttributeMode.INITIAL);
		tmp.put(DATESUBMITTED, AttributeMode.INITIAL);
		tmp.put(EXPDATE, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(BIDTOTAL, AttributeMode.INITIAL);
		tmp.put(ITEMDETAILS, AttributeMode.INITIAL);
		tmp.put(MODIFIEDITEMDETAILS, AttributeMode.INITIAL);
		tmp.put(ITEMNUMBER, AttributeMode.INITIAL);
		tmp.put(ITEMDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(QUANTITY, AttributeMode.INITIAL);
		tmp.put(UNITPRICE, AttributeMode.INITIAL);
		tmp.put(UOM, AttributeMode.INITIAL);
		tmp.put(EXTPRICE, AttributeMode.INITIAL);
		tmp.put(NOTES, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(APPROVERNAME, AttributeMode.INITIAL);
		tmp.put(APPROVEREMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(INSIDESALESREPEMAIL, AttributeMode.INITIAL);
		tmp.put(BRANCHMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(WRITEREMAIL, AttributeMode.INITIAL);
		tmp.put(PRICEREMAIL, AttributeMode.INITIAL);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		tmp.put(ITEMCOUNT, AttributeMode.INITIAL);
		tmp.put(QUOTEID, AttributeMode.INITIAL);
		tmp.put(ACCOUNTID, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		tmp.put(PONUMBER, AttributeMode.INITIAL);
		tmp.put(ISFULLQUOTE, AttributeMode.INITIAL);
		tmp.put(OPTIONALNOTES, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId()
	{
		return getAccountId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final String value)
	{
		setAccountId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountInfo</code> attribute.
	 * @return the accountInfo
	 */
	public String getAccountInfo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountInfo</code> attribute.
	 * @return the accountInfo
	 */
	public String getAccountInfo()
	{
		return getAccountInfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountInfo</code> attribute. 
	 * @param value the accountInfo
	 */
	public void setAccountInfo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountInfo</code> attribute. 
	 * @param value the accountInfo
	 */
	public void setAccountInfo(final String value)
	{
		setAccountInfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return getAccountManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final String value)
	{
		setAccountManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return getAccountName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final String value)
	{
		setAccountName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.approverEmailAddress</code> attribute.
	 * @return the approverEmailAddress
	 */
	public String getApproverEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, APPROVEREMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.approverEmailAddress</code> attribute.
	 * @return the approverEmailAddress
	 */
	public String getApproverEmailAddress()
	{
		return getApproverEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.approverEmailAddress</code> attribute. 
	 * @param value the approverEmailAddress
	 */
	public void setApproverEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, APPROVEREMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.approverEmailAddress</code> attribute. 
	 * @param value the approverEmailAddress
	 */
	public void setApproverEmailAddress(final String value)
	{
		setApproverEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.approverName</code> attribute.
	 * @return the approverName
	 */
	public String getApproverName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, APPROVERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.approverName</code> attribute.
	 * @return the approverName
	 */
	public String getApproverName()
	{
		return getApproverName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.approverName</code> attribute. 
	 * @param value the approverName
	 */
	public void setApproverName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, APPROVERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.approverName</code> attribute. 
	 * @param value the approverName
	 */
	public void setApproverName(final String value)
	{
		setApproverName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.bidTotal</code> attribute.
	 * @return the bidTotal
	 */
	public String getBidTotal(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BIDTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.bidTotal</code> attribute.
	 * @return the bidTotal
	 */
	public String getBidTotal()
	{
		return getBidTotal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.bidTotal</code> attribute. 
	 * @param value the bidTotal
	 */
	public void setBidTotal(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BIDTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.bidTotal</code> attribute. 
	 * @param value the bidTotal
	 */
	public void setBidTotal(final String value)
	{
		setBidTotal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.branchInfo</code> attribute.
	 * @return the branchInfo
	 */
	public String getBranchInfo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.branchInfo</code> attribute.
	 * @return the branchInfo
	 */
	public String getBranchInfo()
	{
		return getBranchInfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.branchInfo</code> attribute. 
	 * @param value the branchInfo
	 */
	public void setBranchInfo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.branchInfo</code> attribute. 
	 * @param value the branchInfo
	 */
	public void setBranchInfo(final String value)
	{
		setBranchInfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return getBranchManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final String value)
	{
		setBranchManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress()
	{
		return getCustomerEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final String value)
	{
		setCustomerEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.dateSubmitted</code> attribute.
	 * @return the dateSubmitted
	 */
	public String getDateSubmitted(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATESUBMITTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.dateSubmitted</code> attribute.
	 * @return the dateSubmitted
	 */
	public String getDateSubmitted()
	{
		return getDateSubmitted( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.dateSubmitted</code> attribute. 
	 * @param value the dateSubmitted
	 */
	public void setDateSubmitted(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATESUBMITTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.dateSubmitted</code> attribute. 
	 * @param value the dateSubmitted
	 */
	public void setDateSubmitted(final String value)
	{
		setDateSubmitted( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return getExpDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final String value)
	{
		setExpDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.extPrice</code> attribute.
	 * @return the extPrice
	 */
	public String getExtPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.extPrice</code> attribute.
	 * @return the extPrice
	 */
	public String getExtPrice()
	{
		return getExtPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.extPrice</code> attribute. 
	 * @param value the extPrice
	 */
	public void setExtPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.extPrice</code> attribute. 
	 * @param value the extPrice
	 */
	public void setExtPrice(final String value)
	{
		setExtPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSIDESALESREPEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return getInsideSalesRepEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSIDESALESREPEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final String value)
	{
		setInsideSalesRepEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute.
	 * @return the isFullQuote
	 */
	public Boolean isIsFullQuote(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISFULLQUOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute.
	 * @return the isFullQuote
	 */
	public Boolean isIsFullQuote()
	{
		return isIsFullQuote( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute. 
	 * @return the isFullQuote
	 */
	public boolean isIsFullQuoteAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsFullQuote( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute. 
	 * @return the isFullQuote
	 */
	public boolean isIsFullQuoteAsPrimitive()
	{
		return isIsFullQuoteAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute. 
	 * @param value the isFullQuote
	 */
	public void setIsFullQuote(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISFULLQUOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute. 
	 * @param value the isFullQuote
	 */
	public void setIsFullQuote(final Boolean value)
	{
		setIsFullQuote( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute. 
	 * @param value the isFullQuote
	 */
	public void setIsFullQuote(final SessionContext ctx, final boolean value)
	{
		setIsFullQuote( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.isFullQuote</code> attribute. 
	 * @param value the isFullQuote
	 */
	public void setIsFullQuote(final boolean value)
	{
		setIsFullQuote( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemCount</code> attribute.
	 * @return the itemCount
	 */
	public String getItemCount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemCount</code> attribute.
	 * @return the itemCount
	 */
	public String getItemCount()
	{
		return getItemCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemCount</code> attribute. 
	 * @param value the itemCount
	 */
	public void setItemCount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemCount</code> attribute. 
	 * @param value the itemCount
	 */
	public void setItemCount(final String value)
	{
		setItemCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemDescription</code> attribute.
	 * @return the itemDescription
	 */
	public String getItemDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemDescription</code> attribute.
	 * @return the itemDescription
	 */
	public String getItemDescription()
	{
		return getItemDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemDescription</code> attribute. 
	 * @param value the itemDescription
	 */
	public void setItemDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemDescription</code> attribute. 
	 * @param value the itemDescription
	 */
	public void setItemDescription(final String value)
	{
		setItemDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemDetails</code> attribute.
	 * @return the itemDetails
	 */
	public List<QuoteItemDetails> getItemDetails(final SessionContext ctx)
	{
		List<QuoteItemDetails> coll = (List<QuoteItemDetails>)getProperty( ctx, ITEMDETAILS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemDetails</code> attribute.
	 * @return the itemDetails
	 */
	public List<QuoteItemDetails> getItemDetails()
	{
		return getItemDetails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemDetails</code> attribute. 
	 * @param value the itemDetails
	 */
	public void setItemDetails(final SessionContext ctx, final List<QuoteItemDetails> value)
	{
		setProperty(ctx, ITEMDETAILS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemDetails</code> attribute. 
	 * @param value the itemDetails
	 */
	public void setItemDetails(final List<QuoteItemDetails> value)
	{
		setItemDetails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.itemNumber</code> attribute.
	 * @return the itemNumber
	 */
	public String getItemNumber()
	{
		return getItemNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.itemNumber</code> attribute. 
	 * @param value the itemNumber
	 */
	public void setItemNumber(final String value)
	{
		setItemNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JOBNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.jobName</code> attribute.
	 * @return the jobName
	 */
	public String getJobName()
	{
		return getJobName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JOBNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.jobName</code> attribute. 
	 * @param value the jobName
	 */
	public void setJobName(final String value)
	{
		setJobName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.modifiedItemDetails</code> attribute.
	 * @return the modifiedItemDetails
	 */
	public List<QuoteItemDetails> getModifiedItemDetails(final SessionContext ctx)
	{
		List<QuoteItemDetails> coll = (List<QuoteItemDetails>)getProperty( ctx, MODIFIEDITEMDETAILS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.modifiedItemDetails</code> attribute.
	 * @return the modifiedItemDetails
	 */
	public List<QuoteItemDetails> getModifiedItemDetails()
	{
		return getModifiedItemDetails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.modifiedItemDetails</code> attribute. 
	 * @param value the modifiedItemDetails
	 */
	public void setModifiedItemDetails(final SessionContext ctx, final List<QuoteItemDetails> value)
	{
		setProperty(ctx, MODIFIEDITEMDETAILS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.modifiedItemDetails</code> attribute. 
	 * @param value the modifiedItemDetails
	 */
	public void setModifiedItemDetails(final List<QuoteItemDetails> value)
	{
		setModifiedItemDetails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.notes</code> attribute.
	 * @return the notes
	 */
	public String getNotes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.notes</code> attribute.
	 * @return the notes
	 */
	public String getNotes()
	{
		return getNotes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.notes</code> attribute. 
	 * @param value the notes
	 */
	public void setNotes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.notes</code> attribute. 
	 * @param value the notes
	 */
	public void setNotes(final String value)
	{
		setNotes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.optionalNotes</code> attribute.
	 * @return the optionalNotes
	 */
	public String getOptionalNotes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, OPTIONALNOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.optionalNotes</code> attribute.
	 * @return the optionalNotes
	 */
	public String getOptionalNotes()
	{
		return getOptionalNotes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.optionalNotes</code> attribute. 
	 * @param value the optionalNotes
	 */
	public void setOptionalNotes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, OPTIONALNOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.optionalNotes</code> attribute. 
	 * @param value the optionalNotes
	 */
	public void setOptionalNotes(final String value)
	{
		setOptionalNotes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PONUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return getPoNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PONUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final String value)
	{
		setPoNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.pricerEmail</code> attribute.
	 * @return the pricerEmail
	 */
	public String getPricerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRICEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.pricerEmail</code> attribute.
	 * @return the pricerEmail
	 */
	public String getPricerEmail()
	{
		return getPricerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.pricerEmail</code> attribute. 
	 * @param value the pricerEmail
	 */
	public void setPricerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRICEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.pricerEmail</code> attribute. 
	 * @param value the pricerEmail
	 */
	public void setPricerEmail(final String value)
	{
		setPricerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.quantity</code> attribute.
	 * @return the quantity
	 */
	public String getQuantity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.quantity</code> attribute.
	 * @return the quantity
	 */
	public String getQuantity()
	{
		return getQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.quantity</code> attribute. 
	 * @param value the quantity
	 */
	public void setQuantity(final String value)
	{
		setQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.quoteId</code> attribute.
	 * @return the quoteId
	 */
	public String getQuoteId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.quoteId</code> attribute.
	 * @return the quoteId
	 */
	public String getQuoteId()
	{
		return getQuoteId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.quoteId</code> attribute. 
	 * @param value the quoteId
	 */
	public void setQuoteId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.quoteId</code> attribute. 
	 * @param value the quoteId
	 */
	public void setQuoteId(final String value)
	{
		setQuoteId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return getQuoteNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final String value)
	{
		setQuoteNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.shipToInfo</code> attribute.
	 * @return the shipToInfo
	 */
	public String getShipToInfo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SHIPTOINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.shipToInfo</code> attribute.
	 * @return the shipToInfo
	 */
	public String getShipToInfo()
	{
		return getShipToInfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.shipToInfo</code> attribute. 
	 * @param value the shipToInfo
	 */
	public void setShipToInfo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SHIPTOINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.shipToInfo</code> attribute. 
	 * @param value the shipToInfo
	 */
	public void setShipToInfo(final String value)
	{
		setShipToInfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.status</code> attribute.
	 * @return the status
	 */
	public String getStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.status</code> attribute.
	 * @return the status
	 */
	public String getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final String value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.unitPrice</code> attribute.
	 * @return the unitPrice
	 */
	public String getUnitPrice(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UNITPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.unitPrice</code> attribute.
	 * @return the unitPrice
	 */
	public String getUnitPrice()
	{
		return getUnitPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.unitPrice</code> attribute. 
	 * @param value the unitPrice
	 */
	public void setUnitPrice(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UNITPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.unitPrice</code> attribute. 
	 * @param value the unitPrice
	 */
	public void setUnitPrice(final String value)
	{
		setUnitPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.UOM</code> attribute.
	 * @return the UOM
	 */
	public String getUOM(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.UOM</code> attribute.
	 * @return the UOM
	 */
	public String getUOM()
	{
		return getUOM( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.UOM</code> attribute. 
	 * @param value the UOM
	 */
	public void setUOM(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.UOM</code> attribute. 
	 * @param value the UOM
	 */
	public void setUOM(final String value)
	{
		setUOM( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.writerEmail</code> attribute.
	 * @return the writerEmail
	 */
	public String getWriterEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, WRITEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>QuoteApprovalProcess.writerEmail</code> attribute.
	 * @return the writerEmail
	 */
	public String getWriterEmail()
	{
		return getWriterEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.writerEmail</code> attribute. 
	 * @param value the writerEmail
	 */
	public void setWriterEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, WRITEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>QuoteApprovalProcess.writerEmail</code> attribute. 
	 * @param value the writerEmail
	 */
	public void setWriterEmail(final String value)
	{
		setWriterEmail( getSession().getSessionContext(), value );
	}
	
}
