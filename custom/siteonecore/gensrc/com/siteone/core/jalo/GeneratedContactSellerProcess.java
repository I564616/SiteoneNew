/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.ContactSellerProcess ContactSellerProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedContactSellerProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ContactSellerProcess.quoteNumber</code> attribute **/
	public static final String QUOTENUMBER = "quoteNumber";
	/** Qualifier of the <code>ContactSellerProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>ContactSellerProcess.quoteComments</code> attribute **/
	public static final String QUOTECOMMENTS = "quoteComments";
	/** Qualifier of the <code>ContactSellerProcess.quoteId</code> attribute **/
	public static final String QUOTEID = "quoteId";
	/** Qualifier of the <code>ContactSellerProcess.approverName</code> attribute **/
	public static final String APPROVERNAME = "approverName";
	/** Qualifier of the <code>ContactSellerProcess.customerEmailAddress</code> attribute **/
	public static final String CUSTOMEREMAILADDRESS = "customerEmailAddress";
	/** Qualifier of the <code>ContactSellerProcess.accountName</code> attribute **/
	public static final String ACCOUNTNAME = "accountName";
	/** Qualifier of the <code>ContactSellerProcess.accountManagerEmail</code> attribute **/
	public static final String ACCOUNTMANAGEREMAIL = "accountManagerEmail";
	/** Qualifier of the <code>ContactSellerProcess.insideSalesRepEmail</code> attribute **/
	public static final String INSIDESALESREPEMAIL = "insideSalesRepEmail";
	/** Qualifier of the <code>ContactSellerProcess.branchManagerEmail</code> attribute **/
	public static final String BRANCHMANAGEREMAIL = "branchManagerEmail";
	/** Qualifier of the <code>ContactSellerProcess.writerEmail</code> attribute **/
	public static final String WRITEREMAIL = "writerEmail";
	/** Qualifier of the <code>ContactSellerProcess.pricerEmail</code> attribute **/
	public static final String PRICEREMAIL = "pricerEmail";
	/** Qualifier of the <code>ContactSellerProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	/** Qualifier of the <code>ContactSellerProcess.accountId</code> attribute **/
	public static final String ACCOUNTID = "accountId";
	/** Qualifier of the <code>ContactSellerProcess.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(QUOTENUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(QUOTECOMMENTS, AttributeMode.INITIAL);
		tmp.put(QUOTEID, AttributeMode.INITIAL);
		tmp.put(APPROVERNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(INSIDESALESREPEMAIL, AttributeMode.INITIAL);
		tmp.put(BRANCHMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(WRITEREMAIL, AttributeMode.INITIAL);
		tmp.put(PRICEREMAIL, AttributeMode.INITIAL);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		tmp.put(ACCOUNTID, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.accountId</code> attribute.
	 * @return the accountId
	 */
	public String getAccountId()
	{
		return getAccountId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.accountId</code> attribute. 
	 * @param value the accountId
	 */
	public void setAccountId(final String value)
	{
		setAccountId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return getAccountManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final String value)
	{
		setAccountManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return getAccountName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final String value)
	{
		setAccountName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.approverName</code> attribute.
	 * @return the approverName
	 */
	public String getApproverName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, APPROVERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.approverName</code> attribute.
	 * @return the approverName
	 */
	public String getApproverName()
	{
		return getApproverName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.approverName</code> attribute. 
	 * @param value the approverName
	 */
	public void setApproverName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, APPROVERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.approverName</code> attribute. 
	 * @param value the approverName
	 */
	public void setApproverName(final String value)
	{
		setApproverName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return getBranchManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final String value)
	{
		setBranchManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.customerEmailAddress</code> attribute.
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress()
	{
		return getCustomerEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.customerEmailAddress</code> attribute. 
	 * @param value the customerEmailAddress
	 */
	public void setCustomerEmailAddress(final String value)
	{
		setCustomerEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSIDESALESREPEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return getInsideSalesRepEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSIDESALESREPEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final String value)
	{
		setInsideSalesRepEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.pricerEmail</code> attribute.
	 * @return the pricerEmail
	 */
	public String getPricerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRICEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.pricerEmail</code> attribute.
	 * @return the pricerEmail
	 */
	public String getPricerEmail()
	{
		return getPricerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.pricerEmail</code> attribute. 
	 * @param value the pricerEmail
	 */
	public void setPricerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRICEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.pricerEmail</code> attribute. 
	 * @param value the pricerEmail
	 */
	public void setPricerEmail(final String value)
	{
		setPricerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.quoteComments</code> attribute.
	 * @return the quoteComments
	 */
	public String getQuoteComments(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTECOMMENTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.quoteComments</code> attribute.
	 * @return the quoteComments
	 */
	public String getQuoteComments()
	{
		return getQuoteComments( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.quoteComments</code> attribute. 
	 * @param value the quoteComments
	 */
	public void setQuoteComments(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTECOMMENTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.quoteComments</code> attribute. 
	 * @param value the quoteComments
	 */
	public void setQuoteComments(final String value)
	{
		setQuoteComments( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.quoteId</code> attribute.
	 * @return the quoteId
	 */
	public String getQuoteId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.quoteId</code> attribute.
	 * @return the quoteId
	 */
	public String getQuoteId()
	{
		return getQuoteId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.quoteId</code> attribute. 
	 * @param value the quoteId
	 */
	public void setQuoteId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.quoteId</code> attribute. 
	 * @param value the quoteId
	 */
	public void setQuoteId(final String value)
	{
		setQuoteId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QUOTENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.quoteNumber</code> attribute.
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return getQuoteNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QUOTENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.quoteNumber</code> attribute. 
	 * @param value the quoteNumber
	 */
	public void setQuoteNumber(final String value)
	{
		setQuoteNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.writerEmail</code> attribute.
	 * @return the writerEmail
	 */
	public String getWriterEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, WRITEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactSellerProcess.writerEmail</code> attribute.
	 * @return the writerEmail
	 */
	public String getWriterEmail()
	{
		return getWriterEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.writerEmail</code> attribute. 
	 * @param value the writerEmail
	 */
	public void setWriterEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, WRITEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactSellerProcess.writerEmail</code> attribute. 
	 * @param value the writerEmail
	 */
	public void setWriterEmail(final String value)
	{
		setWriterEmail( getSession().getSessionContext(), value );
	}
	
}
