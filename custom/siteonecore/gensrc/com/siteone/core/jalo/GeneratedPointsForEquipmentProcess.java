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
 * Generated class for type {@link com.siteone.core.jalo.PointsForEquipmentProcess PointsForEquipmentProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPointsForEquipmentProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerContactName</code> attribute **/
	public static final String DEALERCONTACTNAME = "dealerContactName";
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerName</code> attribute **/
	public static final String DEALERNAME = "dealerName";
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerAddressLine1</code> attribute **/
	public static final String DEALERADDRESSLINE1 = "dealerAddressLine1";
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerAddressLine2</code> attribute **/
	public static final String DEALERADDRESSLINE2 = "dealerAddressLine2";
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerCity</code> attribute **/
	public static final String DEALERCITY = "dealerCity";
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerState</code> attribute **/
	public static final String DEALERSTATE = "dealerState";
	/** Qualifier of the <code>PointsForEquipmentProcess.dealerZipCode</code> attribute **/
	public static final String DEALERZIPCODE = "dealerZipCode";
	/** Qualifier of the <code>PointsForEquipmentProcess.customerContactName</code> attribute **/
	public static final String CUSTOMERCONTACTNAME = "customerContactName";
	/** Qualifier of the <code>PointsForEquipmentProcess.companyName</code> attribute **/
	public static final String COMPANYNAME = "companyName";
	/** Qualifier of the <code>PointsForEquipmentProcess.jdlAccountNumber</code> attribute **/
	public static final String JDLACCOUNTNUMBER = "jdlAccountNumber";
	/** Qualifier of the <code>PointsForEquipmentProcess.customerAddressLine1</code> attribute **/
	public static final String CUSTOMERADDRESSLINE1 = "customerAddressLine1";
	/** Qualifier of the <code>PointsForEquipmentProcess.customerAddressLine2</code> attribute **/
	public static final String CUSTOMERADDRESSLINE2 = "customerAddressLine2";
	/** Qualifier of the <code>PointsForEquipmentProcess.customerCity</code> attribute **/
	public static final String CUSTOMERCITY = "customerCity";
	/** Qualifier of the <code>PointsForEquipmentProcess.customerState</code> attribute **/
	public static final String CUSTOMERSTATE = "customerState";
	/** Qualifier of the <code>PointsForEquipmentProcess.customerZipCode</code> attribute **/
	public static final String CUSTOMERZIPCODE = "customerZipCode";
	/** Qualifier of the <code>PointsForEquipmentProcess.custEmailAddress</code> attribute **/
	public static final String CUSTEMAILADDRESS = "custEmailAddress";
	/** Qualifier of the <code>PointsForEquipmentProcess.phoneNum</code> attribute **/
	public static final String PHONENUM = "phoneNum";
	/** Qualifier of the <code>PointsForEquipmentProcess.faxNum</code> attribute **/
	public static final String FAXNUM = "faxNum";
	/** Qualifier of the <code>PointsForEquipmentProcess.dateOfPurProduct1</code> attribute **/
	public static final String DATEOFPURPRODUCT1 = "dateOfPurProduct1";
	/** Qualifier of the <code>PointsForEquipmentProcess.dateOfPurProduct2</code> attribute **/
	public static final String DATEOFPURPRODUCT2 = "dateOfPurProduct2";
	/** Qualifier of the <code>PointsForEquipmentProcess.dateOfPurProduct3</code> attribute **/
	public static final String DATEOFPURPRODUCT3 = "dateOfPurProduct3";
	/** Qualifier of the <code>PointsForEquipmentProcess.dateOfPurProduct4</code> attribute **/
	public static final String DATEOFPURPRODUCT4 = "dateOfPurProduct4";
	/** Qualifier of the <code>PointsForEquipmentProcess.dateOfPurProduct5</code> attribute **/
	public static final String DATEOFPURPRODUCT5 = "dateOfPurProduct5";
	/** Qualifier of the <code>PointsForEquipmentProcess.itemDescProduct1</code> attribute **/
	public static final String ITEMDESCPRODUCT1 = "itemDescProduct1";
	/** Qualifier of the <code>PointsForEquipmentProcess.itemDescProduct2</code> attribute **/
	public static final String ITEMDESCPRODUCT2 = "itemDescProduct2";
	/** Qualifier of the <code>PointsForEquipmentProcess.itemDescProduct3</code> attribute **/
	public static final String ITEMDESCPRODUCT3 = "itemDescProduct3";
	/** Qualifier of the <code>PointsForEquipmentProcess.itemDescProduct4</code> attribute **/
	public static final String ITEMDESCPRODUCT4 = "itemDescProduct4";
	/** Qualifier of the <code>PointsForEquipmentProcess.itemDescProduct5</code> attribute **/
	public static final String ITEMDESCPRODUCT5 = "itemDescProduct5";
	/** Qualifier of the <code>PointsForEquipmentProcess.serialNumberProduct1</code> attribute **/
	public static final String SERIALNUMBERPRODUCT1 = "serialNumberProduct1";
	/** Qualifier of the <code>PointsForEquipmentProcess.serialNumberProduct2</code> attribute **/
	public static final String SERIALNUMBERPRODUCT2 = "serialNumberProduct2";
	/** Qualifier of the <code>PointsForEquipmentProcess.serialNumberProduct3</code> attribute **/
	public static final String SERIALNUMBERPRODUCT3 = "serialNumberProduct3";
	/** Qualifier of the <code>PointsForEquipmentProcess.serialNumberProduct4</code> attribute **/
	public static final String SERIALNUMBERPRODUCT4 = "serialNumberProduct4";
	/** Qualifier of the <code>PointsForEquipmentProcess.serialNumberProduct5</code> attribute **/
	public static final String SERIALNUMBERPRODUCT5 = "serialNumberProduct5";
	/** Qualifier of the <code>PointsForEquipmentProcess.invoiceCostProduct1</code> attribute **/
	public static final String INVOICECOSTPRODUCT1 = "invoiceCostProduct1";
	/** Qualifier of the <code>PointsForEquipmentProcess.invoiceCostProduct2</code> attribute **/
	public static final String INVOICECOSTPRODUCT2 = "invoiceCostProduct2";
	/** Qualifier of the <code>PointsForEquipmentProcess.invoiceCostProduct3</code> attribute **/
	public static final String INVOICECOSTPRODUCT3 = "invoiceCostProduct3";
	/** Qualifier of the <code>PointsForEquipmentProcess.invoiceCostProduct4</code> attribute **/
	public static final String INVOICECOSTPRODUCT4 = "invoiceCostProduct4";
	/** Qualifier of the <code>PointsForEquipmentProcess.invoiceCostProduct5</code> attribute **/
	public static final String INVOICECOSTPRODUCT5 = "invoiceCostProduct5";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(DEALERCONTACTNAME, AttributeMode.INITIAL);
		tmp.put(DEALERNAME, AttributeMode.INITIAL);
		tmp.put(DEALERADDRESSLINE1, AttributeMode.INITIAL);
		tmp.put(DEALERADDRESSLINE2, AttributeMode.INITIAL);
		tmp.put(DEALERCITY, AttributeMode.INITIAL);
		tmp.put(DEALERSTATE, AttributeMode.INITIAL);
		tmp.put(DEALERZIPCODE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERCONTACTNAME, AttributeMode.INITIAL);
		tmp.put(COMPANYNAME, AttributeMode.INITIAL);
		tmp.put(JDLACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMERADDRESSLINE1, AttributeMode.INITIAL);
		tmp.put(CUSTOMERADDRESSLINE2, AttributeMode.INITIAL);
		tmp.put(CUSTOMERCITY, AttributeMode.INITIAL);
		tmp.put(CUSTOMERSTATE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERZIPCODE, AttributeMode.INITIAL);
		tmp.put(CUSTEMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(PHONENUM, AttributeMode.INITIAL);
		tmp.put(FAXNUM, AttributeMode.INITIAL);
		tmp.put(DATEOFPURPRODUCT1, AttributeMode.INITIAL);
		tmp.put(DATEOFPURPRODUCT2, AttributeMode.INITIAL);
		tmp.put(DATEOFPURPRODUCT3, AttributeMode.INITIAL);
		tmp.put(DATEOFPURPRODUCT4, AttributeMode.INITIAL);
		tmp.put(DATEOFPURPRODUCT5, AttributeMode.INITIAL);
		tmp.put(ITEMDESCPRODUCT1, AttributeMode.INITIAL);
		tmp.put(ITEMDESCPRODUCT2, AttributeMode.INITIAL);
		tmp.put(ITEMDESCPRODUCT3, AttributeMode.INITIAL);
		tmp.put(ITEMDESCPRODUCT4, AttributeMode.INITIAL);
		tmp.put(ITEMDESCPRODUCT5, AttributeMode.INITIAL);
		tmp.put(SERIALNUMBERPRODUCT1, AttributeMode.INITIAL);
		tmp.put(SERIALNUMBERPRODUCT2, AttributeMode.INITIAL);
		tmp.put(SERIALNUMBERPRODUCT3, AttributeMode.INITIAL);
		tmp.put(SERIALNUMBERPRODUCT4, AttributeMode.INITIAL);
		tmp.put(SERIALNUMBERPRODUCT5, AttributeMode.INITIAL);
		tmp.put(INVOICECOSTPRODUCT1, AttributeMode.INITIAL);
		tmp.put(INVOICECOSTPRODUCT2, AttributeMode.INITIAL);
		tmp.put(INVOICECOSTPRODUCT3, AttributeMode.INITIAL);
		tmp.put(INVOICECOSTPRODUCT4, AttributeMode.INITIAL);
		tmp.put(INVOICECOSTPRODUCT5, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COMPANYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return getCompanyName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COMPANYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final String value)
	{
		setCompanyName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.custEmailAddress</code> attribute.
	 * @return the custEmailAddress
	 */
	public String getCustEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTEMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.custEmailAddress</code> attribute.
	 * @return the custEmailAddress
	 */
	public String getCustEmailAddress()
	{
		return getCustEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.custEmailAddress</code> attribute. 
	 * @param value the custEmailAddress
	 */
	public void setCustEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTEMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.custEmailAddress</code> attribute. 
	 * @param value the custEmailAddress
	 */
	public void setCustEmailAddress(final String value)
	{
		setCustEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerAddressLine1</code> attribute.
	 * @return the customerAddressLine1
	 */
	public String getCustomerAddressLine1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERADDRESSLINE1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerAddressLine1</code> attribute.
	 * @return the customerAddressLine1
	 */
	public String getCustomerAddressLine1()
	{
		return getCustomerAddressLine1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerAddressLine1</code> attribute. 
	 * @param value the customerAddressLine1
	 */
	public void setCustomerAddressLine1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERADDRESSLINE1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerAddressLine1</code> attribute. 
	 * @param value the customerAddressLine1
	 */
	public void setCustomerAddressLine1(final String value)
	{
		setCustomerAddressLine1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerAddressLine2</code> attribute.
	 * @return the customerAddressLine2
	 */
	public String getCustomerAddressLine2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERADDRESSLINE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerAddressLine2</code> attribute.
	 * @return the customerAddressLine2
	 */
	public String getCustomerAddressLine2()
	{
		return getCustomerAddressLine2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerAddressLine2</code> attribute. 
	 * @param value the customerAddressLine2
	 */
	public void setCustomerAddressLine2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERADDRESSLINE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerAddressLine2</code> attribute. 
	 * @param value the customerAddressLine2
	 */
	public void setCustomerAddressLine2(final String value)
	{
		setCustomerAddressLine2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerCity</code> attribute.
	 * @return the customerCity
	 */
	public String getCustomerCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERCITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerCity</code> attribute.
	 * @return the customerCity
	 */
	public String getCustomerCity()
	{
		return getCustomerCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerCity</code> attribute. 
	 * @param value the customerCity
	 */
	public void setCustomerCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERCITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerCity</code> attribute. 
	 * @param value the customerCity
	 */
	public void setCustomerCity(final String value)
	{
		setCustomerCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerContactName</code> attribute.
	 * @return the customerContactName
	 */
	public String getCustomerContactName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERCONTACTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerContactName</code> attribute.
	 * @return the customerContactName
	 */
	public String getCustomerContactName()
	{
		return getCustomerContactName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerContactName</code> attribute. 
	 * @param value the customerContactName
	 */
	public void setCustomerContactName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERCONTACTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerContactName</code> attribute. 
	 * @param value the customerContactName
	 */
	public void setCustomerContactName(final String value)
	{
		setCustomerContactName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERSTATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState()
	{
		return getCustomerState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERSTATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final String value)
	{
		setCustomerState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerZipCode</code> attribute.
	 * @return the customerZipCode
	 */
	public String getCustomerZipCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.customerZipCode</code> attribute.
	 * @return the customerZipCode
	 */
	public String getCustomerZipCode()
	{
		return getCustomerZipCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerZipCode</code> attribute. 
	 * @param value the customerZipCode
	 */
	public void setCustomerZipCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.customerZipCode</code> attribute. 
	 * @param value the customerZipCode
	 */
	public void setCustomerZipCode(final String value)
	{
		setCustomerZipCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct1</code> attribute.
	 * @return the dateOfPurProduct1
	 */
	public String getDateOfPurProduct1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATEOFPURPRODUCT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct1</code> attribute.
	 * @return the dateOfPurProduct1
	 */
	public String getDateOfPurProduct1()
	{
		return getDateOfPurProduct1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct1</code> attribute. 
	 * @param value the dateOfPurProduct1
	 */
	public void setDateOfPurProduct1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATEOFPURPRODUCT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct1</code> attribute. 
	 * @param value the dateOfPurProduct1
	 */
	public void setDateOfPurProduct1(final String value)
	{
		setDateOfPurProduct1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct2</code> attribute.
	 * @return the dateOfPurProduct2
	 */
	public String getDateOfPurProduct2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATEOFPURPRODUCT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct2</code> attribute.
	 * @return the dateOfPurProduct2
	 */
	public String getDateOfPurProduct2()
	{
		return getDateOfPurProduct2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct2</code> attribute. 
	 * @param value the dateOfPurProduct2
	 */
	public void setDateOfPurProduct2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATEOFPURPRODUCT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct2</code> attribute. 
	 * @param value the dateOfPurProduct2
	 */
	public void setDateOfPurProduct2(final String value)
	{
		setDateOfPurProduct2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct3</code> attribute.
	 * @return the dateOfPurProduct3
	 */
	public String getDateOfPurProduct3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATEOFPURPRODUCT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct3</code> attribute.
	 * @return the dateOfPurProduct3
	 */
	public String getDateOfPurProduct3()
	{
		return getDateOfPurProduct3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct3</code> attribute. 
	 * @param value the dateOfPurProduct3
	 */
	public void setDateOfPurProduct3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATEOFPURPRODUCT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct3</code> attribute. 
	 * @param value the dateOfPurProduct3
	 */
	public void setDateOfPurProduct3(final String value)
	{
		setDateOfPurProduct3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct4</code> attribute.
	 * @return the dateOfPurProduct4
	 */
	public String getDateOfPurProduct4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATEOFPURPRODUCT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct4</code> attribute.
	 * @return the dateOfPurProduct4
	 */
	public String getDateOfPurProduct4()
	{
		return getDateOfPurProduct4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct4</code> attribute. 
	 * @param value the dateOfPurProduct4
	 */
	public void setDateOfPurProduct4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATEOFPURPRODUCT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct4</code> attribute. 
	 * @param value the dateOfPurProduct4
	 */
	public void setDateOfPurProduct4(final String value)
	{
		setDateOfPurProduct4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct5</code> attribute.
	 * @return the dateOfPurProduct5
	 */
	public String getDateOfPurProduct5(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATEOFPURPRODUCT5);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dateOfPurProduct5</code> attribute.
	 * @return the dateOfPurProduct5
	 */
	public String getDateOfPurProduct5()
	{
		return getDateOfPurProduct5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct5</code> attribute. 
	 * @param value the dateOfPurProduct5
	 */
	public void setDateOfPurProduct5(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATEOFPURPRODUCT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dateOfPurProduct5</code> attribute. 
	 * @param value the dateOfPurProduct5
	 */
	public void setDateOfPurProduct5(final String value)
	{
		setDateOfPurProduct5( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerAddressLine1</code> attribute.
	 * @return the dealerAddressLine1
	 */
	public String getDealerAddressLine1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERADDRESSLINE1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerAddressLine1</code> attribute.
	 * @return the dealerAddressLine1
	 */
	public String getDealerAddressLine1()
	{
		return getDealerAddressLine1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerAddressLine1</code> attribute. 
	 * @param value the dealerAddressLine1
	 */
	public void setDealerAddressLine1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERADDRESSLINE1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerAddressLine1</code> attribute. 
	 * @param value the dealerAddressLine1
	 */
	public void setDealerAddressLine1(final String value)
	{
		setDealerAddressLine1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerAddressLine2</code> attribute.
	 * @return the dealerAddressLine2
	 */
	public String getDealerAddressLine2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERADDRESSLINE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerAddressLine2</code> attribute.
	 * @return the dealerAddressLine2
	 */
	public String getDealerAddressLine2()
	{
		return getDealerAddressLine2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerAddressLine2</code> attribute. 
	 * @param value the dealerAddressLine2
	 */
	public void setDealerAddressLine2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERADDRESSLINE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerAddressLine2</code> attribute. 
	 * @param value the dealerAddressLine2
	 */
	public void setDealerAddressLine2(final String value)
	{
		setDealerAddressLine2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerCity</code> attribute.
	 * @return the dealerCity
	 */
	public String getDealerCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERCITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerCity</code> attribute.
	 * @return the dealerCity
	 */
	public String getDealerCity()
	{
		return getDealerCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerCity</code> attribute. 
	 * @param value the dealerCity
	 */
	public void setDealerCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERCITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerCity</code> attribute. 
	 * @param value the dealerCity
	 */
	public void setDealerCity(final String value)
	{
		setDealerCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerContactName</code> attribute.
	 * @return the dealerContactName
	 */
	public String getDealerContactName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERCONTACTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerContactName</code> attribute.
	 * @return the dealerContactName
	 */
	public String getDealerContactName()
	{
		return getDealerContactName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerContactName</code> attribute. 
	 * @param value the dealerContactName
	 */
	public void setDealerContactName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERCONTACTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerContactName</code> attribute. 
	 * @param value the dealerContactName
	 */
	public void setDealerContactName(final String value)
	{
		setDealerContactName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerName</code> attribute.
	 * @return the dealerName
	 */
	public String getDealerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerName</code> attribute.
	 * @return the dealerName
	 */
	public String getDealerName()
	{
		return getDealerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerName</code> attribute. 
	 * @param value the dealerName
	 */
	public void setDealerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerName</code> attribute. 
	 * @param value the dealerName
	 */
	public void setDealerName(final String value)
	{
		setDealerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerState</code> attribute.
	 * @return the dealerState
	 */
	public String getDealerState(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERSTATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerState</code> attribute.
	 * @return the dealerState
	 */
	public String getDealerState()
	{
		return getDealerState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerState</code> attribute. 
	 * @param value the dealerState
	 */
	public void setDealerState(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERSTATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerState</code> attribute. 
	 * @param value the dealerState
	 */
	public void setDealerState(final String value)
	{
		setDealerState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerZipCode</code> attribute.
	 * @return the dealerZipCode
	 */
	public String getDealerZipCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DEALERZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.dealerZipCode</code> attribute.
	 * @return the dealerZipCode
	 */
	public String getDealerZipCode()
	{
		return getDealerZipCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerZipCode</code> attribute. 
	 * @param value the dealerZipCode
	 */
	public void setDealerZipCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DEALERZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.dealerZipCode</code> attribute. 
	 * @param value the dealerZipCode
	 */
	public void setDealerZipCode(final String value)
	{
		setDealerZipCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.faxNum</code> attribute.
	 * @return the faxNum
	 */
	public String getFaxNum(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FAXNUM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.faxNum</code> attribute.
	 * @return the faxNum
	 */
	public String getFaxNum()
	{
		return getFaxNum( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.faxNum</code> attribute. 
	 * @param value the faxNum
	 */
	public void setFaxNum(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FAXNUM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.faxNum</code> attribute. 
	 * @param value the faxNum
	 */
	public void setFaxNum(final String value)
	{
		setFaxNum( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct1</code> attribute.
	 * @return the invoiceCostProduct1
	 */
	public String getInvoiceCostProduct1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICECOSTPRODUCT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct1</code> attribute.
	 * @return the invoiceCostProduct1
	 */
	public String getInvoiceCostProduct1()
	{
		return getInvoiceCostProduct1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct1</code> attribute. 
	 * @param value the invoiceCostProduct1
	 */
	public void setInvoiceCostProduct1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICECOSTPRODUCT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct1</code> attribute. 
	 * @param value the invoiceCostProduct1
	 */
	public void setInvoiceCostProduct1(final String value)
	{
		setInvoiceCostProduct1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct2</code> attribute.
	 * @return the invoiceCostProduct2
	 */
	public String getInvoiceCostProduct2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICECOSTPRODUCT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct2</code> attribute.
	 * @return the invoiceCostProduct2
	 */
	public String getInvoiceCostProduct2()
	{
		return getInvoiceCostProduct2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct2</code> attribute. 
	 * @param value the invoiceCostProduct2
	 */
	public void setInvoiceCostProduct2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICECOSTPRODUCT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct2</code> attribute. 
	 * @param value the invoiceCostProduct2
	 */
	public void setInvoiceCostProduct2(final String value)
	{
		setInvoiceCostProduct2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct3</code> attribute.
	 * @return the invoiceCostProduct3
	 */
	public String getInvoiceCostProduct3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICECOSTPRODUCT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct3</code> attribute.
	 * @return the invoiceCostProduct3
	 */
	public String getInvoiceCostProduct3()
	{
		return getInvoiceCostProduct3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct3</code> attribute. 
	 * @param value the invoiceCostProduct3
	 */
	public void setInvoiceCostProduct3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICECOSTPRODUCT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct3</code> attribute. 
	 * @param value the invoiceCostProduct3
	 */
	public void setInvoiceCostProduct3(final String value)
	{
		setInvoiceCostProduct3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct4</code> attribute.
	 * @return the invoiceCostProduct4
	 */
	public String getInvoiceCostProduct4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICECOSTPRODUCT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct4</code> attribute.
	 * @return the invoiceCostProduct4
	 */
	public String getInvoiceCostProduct4()
	{
		return getInvoiceCostProduct4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct4</code> attribute. 
	 * @param value the invoiceCostProduct4
	 */
	public void setInvoiceCostProduct4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICECOSTPRODUCT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct4</code> attribute. 
	 * @param value the invoiceCostProduct4
	 */
	public void setInvoiceCostProduct4(final String value)
	{
		setInvoiceCostProduct4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct5</code> attribute.
	 * @return the invoiceCostProduct5
	 */
	public String getInvoiceCostProduct5(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INVOICECOSTPRODUCT5);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.invoiceCostProduct5</code> attribute.
	 * @return the invoiceCostProduct5
	 */
	public String getInvoiceCostProduct5()
	{
		return getInvoiceCostProduct5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct5</code> attribute. 
	 * @param value the invoiceCostProduct5
	 */
	public void setInvoiceCostProduct5(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INVOICECOSTPRODUCT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.invoiceCostProduct5</code> attribute. 
	 * @param value the invoiceCostProduct5
	 */
	public void setInvoiceCostProduct5(final String value)
	{
		setInvoiceCostProduct5( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct1</code> attribute.
	 * @return the itemDescProduct1
	 */
	public String getItemDescProduct1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCPRODUCT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct1</code> attribute.
	 * @return the itemDescProduct1
	 */
	public String getItemDescProduct1()
	{
		return getItemDescProduct1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct1</code> attribute. 
	 * @param value the itemDescProduct1
	 */
	public void setItemDescProduct1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCPRODUCT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct1</code> attribute. 
	 * @param value the itemDescProduct1
	 */
	public void setItemDescProduct1(final String value)
	{
		setItemDescProduct1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct2</code> attribute.
	 * @return the itemDescProduct2
	 */
	public String getItemDescProduct2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCPRODUCT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct2</code> attribute.
	 * @return the itemDescProduct2
	 */
	public String getItemDescProduct2()
	{
		return getItemDescProduct2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct2</code> attribute. 
	 * @param value the itemDescProduct2
	 */
	public void setItemDescProduct2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCPRODUCT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct2</code> attribute. 
	 * @param value the itemDescProduct2
	 */
	public void setItemDescProduct2(final String value)
	{
		setItemDescProduct2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct3</code> attribute.
	 * @return the itemDescProduct3
	 */
	public String getItemDescProduct3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCPRODUCT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct3</code> attribute.
	 * @return the itemDescProduct3
	 */
	public String getItemDescProduct3()
	{
		return getItemDescProduct3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct3</code> attribute. 
	 * @param value the itemDescProduct3
	 */
	public void setItemDescProduct3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCPRODUCT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct3</code> attribute. 
	 * @param value the itemDescProduct3
	 */
	public void setItemDescProduct3(final String value)
	{
		setItemDescProduct3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct4</code> attribute.
	 * @return the itemDescProduct4
	 */
	public String getItemDescProduct4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCPRODUCT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct4</code> attribute.
	 * @return the itemDescProduct4
	 */
	public String getItemDescProduct4()
	{
		return getItemDescProduct4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct4</code> attribute. 
	 * @param value the itemDescProduct4
	 */
	public void setItemDescProduct4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCPRODUCT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct4</code> attribute. 
	 * @param value the itemDescProduct4
	 */
	public void setItemDescProduct4(final String value)
	{
		setItemDescProduct4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct5</code> attribute.
	 * @return the itemDescProduct5
	 */
	public String getItemDescProduct5(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMDESCPRODUCT5);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.itemDescProduct5</code> attribute.
	 * @return the itemDescProduct5
	 */
	public String getItemDescProduct5()
	{
		return getItemDescProduct5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct5</code> attribute. 
	 * @param value the itemDescProduct5
	 */
	public void setItemDescProduct5(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMDESCPRODUCT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.itemDescProduct5</code> attribute. 
	 * @param value the itemDescProduct5
	 */
	public void setItemDescProduct5(final String value)
	{
		setItemDescProduct5( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.jdlAccountNumber</code> attribute.
	 * @return the jdlAccountNumber
	 */
	public String getJdlAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, JDLACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.jdlAccountNumber</code> attribute.
	 * @return the jdlAccountNumber
	 */
	public String getJdlAccountNumber()
	{
		return getJdlAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.jdlAccountNumber</code> attribute. 
	 * @param value the jdlAccountNumber
	 */
	public void setJdlAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, JDLACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.jdlAccountNumber</code> attribute. 
	 * @param value the jdlAccountNumber
	 */
	public void setJdlAccountNumber(final String value)
	{
		setJdlAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.phoneNum</code> attribute.
	 * @return the phoneNum
	 */
	public String getPhoneNum(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.phoneNum</code> attribute.
	 * @return the phoneNum
	 */
	public String getPhoneNum()
	{
		return getPhoneNum( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.phoneNum</code> attribute. 
	 * @param value the phoneNum
	 */
	public void setPhoneNum(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.phoneNum</code> attribute. 
	 * @param value the phoneNum
	 */
	public void setPhoneNum(final String value)
	{
		setPhoneNum( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct1</code> attribute.
	 * @return the serialNumberProduct1
	 */
	public String getSerialNumberProduct1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERIALNUMBERPRODUCT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct1</code> attribute.
	 * @return the serialNumberProduct1
	 */
	public String getSerialNumberProduct1()
	{
		return getSerialNumberProduct1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct1</code> attribute. 
	 * @param value the serialNumberProduct1
	 */
	public void setSerialNumberProduct1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERIALNUMBERPRODUCT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct1</code> attribute. 
	 * @param value the serialNumberProduct1
	 */
	public void setSerialNumberProduct1(final String value)
	{
		setSerialNumberProduct1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct2</code> attribute.
	 * @return the serialNumberProduct2
	 */
	public String getSerialNumberProduct2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERIALNUMBERPRODUCT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct2</code> attribute.
	 * @return the serialNumberProduct2
	 */
	public String getSerialNumberProduct2()
	{
		return getSerialNumberProduct2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct2</code> attribute. 
	 * @param value the serialNumberProduct2
	 */
	public void setSerialNumberProduct2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERIALNUMBERPRODUCT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct2</code> attribute. 
	 * @param value the serialNumberProduct2
	 */
	public void setSerialNumberProduct2(final String value)
	{
		setSerialNumberProduct2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct3</code> attribute.
	 * @return the serialNumberProduct3
	 */
	public String getSerialNumberProduct3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERIALNUMBERPRODUCT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct3</code> attribute.
	 * @return the serialNumberProduct3
	 */
	public String getSerialNumberProduct3()
	{
		return getSerialNumberProduct3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct3</code> attribute. 
	 * @param value the serialNumberProduct3
	 */
	public void setSerialNumberProduct3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERIALNUMBERPRODUCT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct3</code> attribute. 
	 * @param value the serialNumberProduct3
	 */
	public void setSerialNumberProduct3(final String value)
	{
		setSerialNumberProduct3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct4</code> attribute.
	 * @return the serialNumberProduct4
	 */
	public String getSerialNumberProduct4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERIALNUMBERPRODUCT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct4</code> attribute.
	 * @return the serialNumberProduct4
	 */
	public String getSerialNumberProduct4()
	{
		return getSerialNumberProduct4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct4</code> attribute. 
	 * @param value the serialNumberProduct4
	 */
	public void setSerialNumberProduct4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERIALNUMBERPRODUCT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct4</code> attribute. 
	 * @param value the serialNumberProduct4
	 */
	public void setSerialNumberProduct4(final String value)
	{
		setSerialNumberProduct4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct5</code> attribute.
	 * @return the serialNumberProduct5
	 */
	public String getSerialNumberProduct5(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERIALNUMBERPRODUCT5);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointsForEquipmentProcess.serialNumberProduct5</code> attribute.
	 * @return the serialNumberProduct5
	 */
	public String getSerialNumberProduct5()
	{
		return getSerialNumberProduct5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct5</code> attribute. 
	 * @param value the serialNumberProduct5
	 */
	public void setSerialNumberProduct5(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERIALNUMBERPRODUCT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointsForEquipmentProcess.serialNumberProduct5</code> attribute. 
	 * @param value the serialNumberProduct5
	 */
	public void setSerialNumberProduct5(final String value)
	{
		setSerialNumberProduct5( getSession().getSessionContext(), value );
	}
	
}
