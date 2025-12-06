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
 * Generated class for type {@link com.siteone.core.jalo.ListEditEmailProcess ListEditEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedListEditEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ListEditEmailProcess.listName</code> attribute **/
	public static final String LISTNAME = "listName";
	/** Qualifier of the <code>ListEditEmailProcess.listCode</code> attribute **/
	public static final String LISTCODE = "listCode";
	/** Qualifier of the <code>ListEditEmailProcess.updatelistName</code> attribute **/
	public static final String UPDATELISTNAME = "updatelistName";
	/** Qualifier of the <code>ListEditEmailProcess.storeCity</code> attribute **/
	public static final String STORECITY = "storeCity";
	/** Qualifier of the <code>ListEditEmailProcess.storeId</code> attribute **/
	public static final String STOREID = "storeId";
	/** Qualifier of the <code>ListEditEmailProcess.contactNumber</code> attribute **/
	public static final String CONTACTNUMBER = "contactNumber";
	/** Qualifier of the <code>ListEditEmailProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>ListEditEmailProcess.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LISTNAME, AttributeMode.INITIAL);
		tmp.put(LISTCODE, AttributeMode.INITIAL);
		tmp.put(UPDATELISTNAME, AttributeMode.INITIAL);
		tmp.put(STORECITY, AttributeMode.INITIAL);
		tmp.put(STOREID, AttributeMode.INITIAL);
		tmp.put(CONTACTNUMBER, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.contactNumber</code> attribute.
	 * @return the contactNumber
	 */
	public String getContactNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.contactNumber</code> attribute.
	 * @return the contactNumber
	 */
	public String getContactNumber()
	{
		return getContactNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.contactNumber</code> attribute. 
	 * @param value the contactNumber
	 */
	public void setContactNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.contactNumber</code> attribute. 
	 * @param value the contactNumber
	 */
	public void setContactNumber(final String value)
	{
		setContactNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.listCode</code> attribute.
	 * @return the listCode
	 */
	public String getListCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LISTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.listCode</code> attribute.
	 * @return the listCode
	 */
	public String getListCode()
	{
		return getListCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.listCode</code> attribute. 
	 * @param value the listCode
	 */
	public void setListCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LISTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.listCode</code> attribute. 
	 * @param value the listCode
	 */
	public void setListCode(final String value)
	{
		setListCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.listName</code> attribute.
	 * @return the listName
	 */
	public String getListName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LISTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.listName</code> attribute.
	 * @return the listName
	 */
	public String getListName()
	{
		return getListName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.listName</code> attribute. 
	 * @param value the listName
	 */
	public void setListName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LISTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.listName</code> attribute. 
	 * @param value the listName
	 */
	public void setListName(final String value)
	{
		setListName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.storeCity</code> attribute.
	 * @return the storeCity
	 */
	public String getStoreCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STORECITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.storeCity</code> attribute.
	 * @return the storeCity
	 */
	public String getStoreCity()
	{
		return getStoreCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.storeCity</code> attribute. 
	 * @param value the storeCity
	 */
	public void setStoreCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STORECITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.storeCity</code> attribute. 
	 * @param value the storeCity
	 */
	public void setStoreCity(final String value)
	{
		setStoreCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId()
	{
		return getStoreId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final String value)
	{
		setStoreId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.updatelistName</code> attribute.
	 * @return the updatelistName
	 */
	public String getUpdatelistName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UPDATELISTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ListEditEmailProcess.updatelistName</code> attribute.
	 * @return the updatelistName
	 */
	public String getUpdatelistName()
	{
		return getUpdatelistName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.updatelistName</code> attribute. 
	 * @param value the updatelistName
	 */
	public void setUpdatelistName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UPDATELISTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ListEditEmailProcess.updatelistName</code> attribute. 
	 * @param value the updatelistName
	 */
	public void setUpdatelistName(final String value)
	{
		setUpdatelistName( getSession().getSessionContext(), value );
	}
	
}
