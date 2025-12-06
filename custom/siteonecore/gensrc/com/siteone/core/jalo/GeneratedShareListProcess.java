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
 * Generated class for type {@link com.siteone.core.jalo.ShareListProcess ShareListProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedShareListProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ShareListProcess.listCode</code> attribute **/
	public static final String LISTCODE = "listCode";
	/** Qualifier of the <code>ShareListProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>ShareListProcess.username</code> attribute **/
	public static final String USERNAME = "username";
	/** Qualifier of the <code>ShareListProcess.listName</code> attribute **/
	public static final String LISTNAME = "listName";
	/** Qualifier of the <code>ShareListProcess.showCustPrice</code> attribute **/
	public static final String SHOWCUSTPRICE = "showCustPrice";
	/** Qualifier of the <code>ShareListProcess.showRetailPrice</code> attribute **/
	public static final String SHOWRETAILPRICE = "showRetailPrice";
	/** Qualifier of the <code>ShareListProcess.retailPriceList</code> attribute **/
	public static final String RETAILPRICELIST = "retailPriceList";
	/** Qualifier of the <code>ShareListProcess.custPriceList</code> attribute **/
	public static final String CUSTPRICELIST = "custPriceList";
	/** Qualifier of the <code>ShareListProcess.storeId</code> attribute **/
	public static final String STOREID = "storeId";
	/** Qualifier of the <code>ShareListProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>ShareListProcess.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>ShareListProcess.senderEmail</code> attribute **/
	public static final String SENDEREMAIL = "senderEmail";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LISTCODE, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(USERNAME, AttributeMode.INITIAL);
		tmp.put(LISTNAME, AttributeMode.INITIAL);
		tmp.put(SHOWCUSTPRICE, AttributeMode.INITIAL);
		tmp.put(SHOWRETAILPRICE, AttributeMode.INITIAL);
		tmp.put(RETAILPRICELIST, AttributeMode.INITIAL);
		tmp.put(CUSTPRICELIST, AttributeMode.INITIAL);
		tmp.put(STOREID, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(SENDEREMAIL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.custPriceList</code> attribute.
	 * @return the custPriceList
	 */
	public Map<String,String> getAllCustPriceList(final SessionContext ctx)
	{
		Map<String,String> map = (Map<String,String>)getProperty( ctx, CUSTPRICELIST);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.custPriceList</code> attribute.
	 * @return the custPriceList
	 */
	public Map<String,String> getAllCustPriceList()
	{
		return getAllCustPriceList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.custPriceList</code> attribute. 
	 * @param value the custPriceList
	 */
	public void setAllCustPriceList(final SessionContext ctx, final Map<String,String> value)
	{
		setProperty(ctx, CUSTPRICELIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.custPriceList</code> attribute. 
	 * @param value the custPriceList
	 */
	public void setAllCustPriceList(final Map<String,String> value)
	{
		setAllCustPriceList( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.listCode</code> attribute.
	 * @return the listCode
	 */
	public String getListCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LISTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.listCode</code> attribute.
	 * @return the listCode
	 */
	public String getListCode()
	{
		return getListCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.listCode</code> attribute. 
	 * @param value the listCode
	 */
	public void setListCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LISTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.listCode</code> attribute. 
	 * @param value the listCode
	 */
	public void setListCode(final String value)
	{
		setListCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.listName</code> attribute.
	 * @return the listName
	 */
	public String getListName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LISTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.listName</code> attribute.
	 * @return the listName
	 */
	public String getListName()
	{
		return getListName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.listName</code> attribute. 
	 * @param value the listName
	 */
	public void setListName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LISTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.listName</code> attribute. 
	 * @param value the listName
	 */
	public void setListName(final String value)
	{
		setListName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.retailPriceList</code> attribute.
	 * @return the retailPriceList
	 */
	public Map<String,String> getAllRetailPriceList(final SessionContext ctx)
	{
		Map<String,String> map = (Map<String,String>)getProperty( ctx, RETAILPRICELIST);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.retailPriceList</code> attribute.
	 * @return the retailPriceList
	 */
	public Map<String,String> getAllRetailPriceList()
	{
		return getAllRetailPriceList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.retailPriceList</code> attribute. 
	 * @param value the retailPriceList
	 */
	public void setAllRetailPriceList(final SessionContext ctx, final Map<String,String> value)
	{
		setProperty(ctx, RETAILPRICELIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.retailPriceList</code> attribute. 
	 * @param value the retailPriceList
	 */
	public void setAllRetailPriceList(final Map<String,String> value)
	{
		setAllRetailPriceList( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.senderEmail</code> attribute.
	 * @return the senderEmail
	 */
	public String getSenderEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SENDEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.senderEmail</code> attribute.
	 * @return the senderEmail
	 */
	public String getSenderEmail()
	{
		return getSenderEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.senderEmail</code> attribute. 
	 * @param value the senderEmail
	 */
	public void setSenderEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SENDEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.senderEmail</code> attribute. 
	 * @param value the senderEmail
	 */
	public void setSenderEmail(final String value)
	{
		setSenderEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showCustPrice</code> attribute.
	 * @return the showCustPrice
	 */
	public Boolean isShowCustPrice(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOWCUSTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showCustPrice</code> attribute.
	 * @return the showCustPrice
	 */
	public Boolean isShowCustPrice()
	{
		return isShowCustPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showCustPrice</code> attribute. 
	 * @return the showCustPrice
	 */
	public boolean isShowCustPriceAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShowCustPrice( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showCustPrice</code> attribute. 
	 * @return the showCustPrice
	 */
	public boolean isShowCustPriceAsPrimitive()
	{
		return isShowCustPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showCustPrice</code> attribute. 
	 * @param value the showCustPrice
	 */
	public void setShowCustPrice(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOWCUSTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showCustPrice</code> attribute. 
	 * @param value the showCustPrice
	 */
	public void setShowCustPrice(final Boolean value)
	{
		setShowCustPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showCustPrice</code> attribute. 
	 * @param value the showCustPrice
	 */
	public void setShowCustPrice(final SessionContext ctx, final boolean value)
	{
		setShowCustPrice( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showCustPrice</code> attribute. 
	 * @param value the showCustPrice
	 */
	public void setShowCustPrice(final boolean value)
	{
		setShowCustPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showRetailPrice</code> attribute.
	 * @return the showRetailPrice
	 */
	public Boolean isShowRetailPrice(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOWRETAILPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showRetailPrice</code> attribute.
	 * @return the showRetailPrice
	 */
	public Boolean isShowRetailPrice()
	{
		return isShowRetailPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showRetailPrice</code> attribute. 
	 * @return the showRetailPrice
	 */
	public boolean isShowRetailPriceAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShowRetailPrice( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.showRetailPrice</code> attribute. 
	 * @return the showRetailPrice
	 */
	public boolean isShowRetailPriceAsPrimitive()
	{
		return isShowRetailPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showRetailPrice</code> attribute. 
	 * @param value the showRetailPrice
	 */
	public void setShowRetailPrice(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOWRETAILPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showRetailPrice</code> attribute. 
	 * @param value the showRetailPrice
	 */
	public void setShowRetailPrice(final Boolean value)
	{
		setShowRetailPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showRetailPrice</code> attribute. 
	 * @param value the showRetailPrice
	 */
	public void setShowRetailPrice(final SessionContext ctx, final boolean value)
	{
		setShowRetailPrice( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.showRetailPrice</code> attribute. 
	 * @param value the showRetailPrice
	 */
	public void setShowRetailPrice(final boolean value)
	{
		setShowRetailPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return getStoreId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final String value)
	{
		setStoreId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.username</code> attribute.
	 * @return the username
	 */
	public String getUsername(final SessionContext ctx)
	{
		return (String)getProperty( ctx, USERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareListProcess.username</code> attribute.
	 * @return the username
	 */
	public String getUsername()
	{
		return getUsername( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.username</code> attribute. 
	 * @param value the username
	 */
	public void setUsername(final SessionContext ctx, final String value)
	{
		setProperty(ctx, USERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareListProcess.username</code> attribute. 
	 * @param value the username
	 */
	public void setUsername(final String value)
	{
		setUsername( getSession().getSessionContext(), value );
	}
	
}
