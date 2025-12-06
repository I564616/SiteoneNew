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
 * Generated class for type {@link com.siteone.core.jalo.CCPAProcess CCPAProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCCPAProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>CCPAProcess.companyName</code> attribute **/
	public static final String COMPANYNAME = "companyName";
	/** Qualifier of the <code>CCPAProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>CCPAProcess.lastName</code> attribute **/
	public static final String LASTNAME = "lastName";
	/** Qualifier of the <code>CCPAProcess.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>CCPAProcess.customerState</code> attribute **/
	public static final String CUSTOMERSTATE = "customerState";
	/** Qualifier of the <code>CCPAProcess.addressLine1</code> attribute **/
	public static final String ADDRESSLINE1 = "addressLine1";
	/** Qualifier of the <code>CCPAProcess.addressLine2</code> attribute **/
	public static final String ADDRESSLINE2 = "addressLine2";
	/** Qualifier of the <code>CCPAProcess.city</code> attribute **/
	public static final String CITY = "city";
	/** Qualifier of the <code>CCPAProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>CCPAProcess.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	/** Qualifier of the <code>CCPAProcess.privacyRequestType</code> attribute **/
	public static final String PRIVACYREQUESTTYPE = "privacyRequestType";
	/** Qualifier of the <code>CCPAProcess.zipcode</code> attribute **/
	public static final String ZIPCODE = "zipcode";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(COMPANYNAME, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(LASTNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMERSTATE, AttributeMode.INITIAL);
		tmp.put(ADDRESSLINE1, AttributeMode.INITIAL);
		tmp.put(ADDRESSLINE2, AttributeMode.INITIAL);
		tmp.put(CITY, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		tmp.put(PRIVACYREQUESTTYPE, AttributeMode.INITIAL);
		tmp.put(ZIPCODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.addressLine1</code> attribute.
	 * @return the addressLine1
	 */
	public String getAddressLine1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESSLINE1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.addressLine1</code> attribute.
	 * @return the addressLine1
	 */
	public String getAddressLine1()
	{
		return getAddressLine1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.addressLine1</code> attribute. 
	 * @param value the addressLine1
	 */
	public void setAddressLine1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESSLINE1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.addressLine1</code> attribute. 
	 * @param value the addressLine1
	 */
	public void setAddressLine1(final String value)
	{
		setAddressLine1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.addressLine2</code> attribute.
	 * @return the addressLine2
	 */
	public String getAddressLine2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESSLINE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.addressLine2</code> attribute.
	 * @return the addressLine2
	 */
	public String getAddressLine2()
	{
		return getAddressLine2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.addressLine2</code> attribute. 
	 * @param value the addressLine2
	 */
	public void setAddressLine2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESSLINE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.addressLine2</code> attribute. 
	 * @param value the addressLine2
	 */
	public void setAddressLine2(final String value)
	{
		setAddressLine2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.city</code> attribute.
	 * @return the city
	 */
	public String getCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.city</code> attribute.
	 * @return the city
	 */
	public String getCity()
	{
		return getCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.city</code> attribute. 
	 * @param value the city
	 */
	public void setCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.city</code> attribute. 
	 * @param value the city
	 */
	public void setCity(final String value)
	{
		setCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COMPANYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return getCompanyName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COMPANYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final String value)
	{
		setCompanyName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERSTATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState()
	{
		return getCustomerState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERSTATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final String value)
	{
		setCustomerState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final String value)
	{
		setLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.privacyRequestType</code> attribute.
	 * @return the privacyRequestType
	 */
	public String getPrivacyRequestType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRIVACYREQUESTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.privacyRequestType</code> attribute.
	 * @return the privacyRequestType
	 */
	public String getPrivacyRequestType()
	{
		return getPrivacyRequestType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.privacyRequestType</code> attribute. 
	 * @param value the privacyRequestType
	 */
	public void setPrivacyRequestType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRIVACYREQUESTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.privacyRequestType</code> attribute. 
	 * @param value the privacyRequestType
	 */
	public void setPrivacyRequestType(final String value)
	{
		setPrivacyRequestType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CCPAProcess.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode()
	{
		return getZipcode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CCPAProcess.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final String value)
	{
		setZipcode( getSession().getSessionContext(), value );
	}
	
}
