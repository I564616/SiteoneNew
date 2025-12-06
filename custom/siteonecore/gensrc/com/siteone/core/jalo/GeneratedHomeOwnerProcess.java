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
 * Generated class for type {@link com.siteone.core.jalo.HomeOwnerProcess HomeOwnerProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHomeOwnerProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>HomeOwnerProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>HomeOwnerProcess.lastName</code> attribute **/
	public static final String LASTNAME = "lastName";
	/** Qualifier of the <code>HomeOwnerProcess.emailAddr</code> attribute **/
	public static final String EMAILADDR = "emailAddr";
	/** Qualifier of the <code>HomeOwnerProcess.phone</code> attribute **/
	public static final String PHONE = "phone";
	/** Qualifier of the <code>HomeOwnerProcess.address</code> attribute **/
	public static final String ADDRESS = "address";
	/** Qualifier of the <code>HomeOwnerProcess.customerCity</code> attribute **/
	public static final String CUSTOMERCITY = "customerCity";
	/** Qualifier of the <code>HomeOwnerProcess.customerState</code> attribute **/
	public static final String CUSTOMERSTATE = "customerState";
	/** Qualifier of the <code>HomeOwnerProcess.customerZipCode</code> attribute **/
	public static final String CUSTOMERZIPCODE = "customerZipCode";
	/** Qualifier of the <code>HomeOwnerProcess.bestTimeToCall</code> attribute **/
	public static final String BESTTIMETOCALL = "bestTimeToCall";
	/** Qualifier of the <code>HomeOwnerProcess.serviceType</code> attribute **/
	public static final String SERVICETYPE = "serviceType";
	/** Qualifier of the <code>HomeOwnerProcess.referalsNo</code> attribute **/
	public static final String REFERALSNO = "referalsNo";
	/** Qualifier of the <code>HomeOwnerProcess.lookingFor</code> attribute **/
	public static final String LOOKINGFOR = "lookingFor";
	/** Qualifier of the <code>HomeOwnerProcess.lookingForOthers</code> attribute **/
	public static final String LOOKINGFOROTHERS = "lookingForOthers";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(LASTNAME, AttributeMode.INITIAL);
		tmp.put(EMAILADDR, AttributeMode.INITIAL);
		tmp.put(PHONE, AttributeMode.INITIAL);
		tmp.put(ADDRESS, AttributeMode.INITIAL);
		tmp.put(CUSTOMERCITY, AttributeMode.INITIAL);
		tmp.put(CUSTOMERSTATE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERZIPCODE, AttributeMode.INITIAL);
		tmp.put(BESTTIMETOCALL, AttributeMode.INITIAL);
		tmp.put(SERVICETYPE, AttributeMode.INITIAL);
		tmp.put(REFERALSNO, AttributeMode.INITIAL);
		tmp.put(LOOKINGFOR, AttributeMode.INITIAL);
		tmp.put(LOOKINGFOROTHERS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.address</code> attribute.
	 * @return the address
	 */
	public String getAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.address</code> attribute.
	 * @return the address
	 */
	public String getAddress()
	{
		return getAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final String value)
	{
		setAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.bestTimeToCall</code> attribute.
	 * @return the bestTimeToCall
	 */
	public String getBestTimeToCall(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BESTTIMETOCALL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.bestTimeToCall</code> attribute.
	 * @return the bestTimeToCall
	 */
	public String getBestTimeToCall()
	{
		return getBestTimeToCall( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.bestTimeToCall</code> attribute. 
	 * @param value the bestTimeToCall
	 */
	public void setBestTimeToCall(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BESTTIMETOCALL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.bestTimeToCall</code> attribute. 
	 * @param value the bestTimeToCall
	 */
	public void setBestTimeToCall(final String value)
	{
		setBestTimeToCall( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.customerCity</code> attribute.
	 * @return the customerCity
	 */
	public String getCustomerCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERCITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.customerCity</code> attribute.
	 * @return the customerCity
	 */
	public String getCustomerCity()
	{
		return getCustomerCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.customerCity</code> attribute. 
	 * @param value the customerCity
	 */
	public void setCustomerCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERCITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.customerCity</code> attribute. 
	 * @param value the customerCity
	 */
	public void setCustomerCity(final String value)
	{
		setCustomerCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERSTATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState()
	{
		return getCustomerState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERSTATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final String value)
	{
		setCustomerState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.customerZipCode</code> attribute.
	 * @return the customerZipCode
	 */
	public String getCustomerZipCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.customerZipCode</code> attribute.
	 * @return the customerZipCode
	 */
	public String getCustomerZipCode()
	{
		return getCustomerZipCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.customerZipCode</code> attribute. 
	 * @param value the customerZipCode
	 */
	public void setCustomerZipCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.customerZipCode</code> attribute. 
	 * @param value the customerZipCode
	 */
	public void setCustomerZipCode(final String value)
	{
		setCustomerZipCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.emailAddr</code> attribute.
	 * @return the emailAddr
	 */
	public String getEmailAddr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.emailAddr</code> attribute.
	 * @return the emailAddr
	 */
	public String getEmailAddr()
	{
		return getEmailAddr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.emailAddr</code> attribute. 
	 * @param value the emailAddr
	 */
	public void setEmailAddr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.emailAddr</code> attribute. 
	 * @param value the emailAddr
	 */
	public void setEmailAddr(final String value)
	{
		setEmailAddr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final String value)
	{
		setLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.lookingFor</code> attribute.
	 * @return the lookingFor
	 */
	public String getLookingFor(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOOKINGFOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.lookingFor</code> attribute.
	 * @return the lookingFor
	 */
	public String getLookingFor()
	{
		return getLookingFor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.lookingFor</code> attribute. 
	 * @param value the lookingFor
	 */
	public void setLookingFor(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOOKINGFOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.lookingFor</code> attribute. 
	 * @param value the lookingFor
	 */
	public void setLookingFor(final String value)
	{
		setLookingFor( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.lookingForOthers</code> attribute.
	 * @return the lookingForOthers
	 */
	public String getLookingForOthers(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOOKINGFOROTHERS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.lookingForOthers</code> attribute.
	 * @return the lookingForOthers
	 */
	public String getLookingForOthers()
	{
		return getLookingForOthers( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.lookingForOthers</code> attribute. 
	 * @param value the lookingForOthers
	 */
	public void setLookingForOthers(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOOKINGFOROTHERS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.lookingForOthers</code> attribute. 
	 * @param value the lookingForOthers
	 */
	public void setLookingForOthers(final String value)
	{
		setLookingForOthers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.phone</code> attribute.
	 * @return the phone
	 */
	public String getPhone(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.phone</code> attribute.
	 * @return the phone
	 */
	public String getPhone()
	{
		return getPhone( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.phone</code> attribute. 
	 * @param value the phone
	 */
	public void setPhone(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.phone</code> attribute. 
	 * @param value the phone
	 */
	public void setPhone(final String value)
	{
		setPhone( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.referalsNo</code> attribute.
	 * @return the referalsNo
	 */
	public String getReferalsNo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REFERALSNO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.referalsNo</code> attribute.
	 * @return the referalsNo
	 */
	public String getReferalsNo()
	{
		return getReferalsNo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.referalsNo</code> attribute. 
	 * @param value the referalsNo
	 */
	public void setReferalsNo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REFERALSNO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.referalsNo</code> attribute. 
	 * @param value the referalsNo
	 */
	public void setReferalsNo(final String value)
	{
		setReferalsNo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.serviceType</code> attribute.
	 * @return the serviceType
	 */
	public String getServiceType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERVICETYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerProcess.serviceType</code> attribute.
	 * @return the serviceType
	 */
	public String getServiceType()
	{
		return getServiceType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.serviceType</code> attribute. 
	 * @param value the serviceType
	 */
	public void setServiceType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERVICETYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerProcess.serviceType</code> attribute. 
	 * @param value the serviceType
	 */
	public void setServiceType(final String value)
	{
		setServiceType( getSession().getSessionContext(), value );
	}
	
}
