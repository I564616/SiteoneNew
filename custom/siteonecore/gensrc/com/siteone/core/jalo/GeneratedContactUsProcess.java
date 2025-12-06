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
 * Generated class for type {@link com.siteone.core.jalo.ContactUsProcess ContactUsProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedContactUsProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ContactUsProcess.reasonForContact</code> attribute **/
	public static final String REASONFORCONTACT = "reasonForContact";
	/** Qualifier of the <code>ContactUsProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>ContactUsProcess.lastName</code> attribute **/
	public static final String LASTNAME = "lastName";
	/** Qualifier of the <code>ContactUsProcess.customerEmail</code> attribute **/
	public static final String CUSTOMEREMAIL = "customerEmail";
	/** Qualifier of the <code>ContactUsProcess.contactNumber</code> attribute **/
	public static final String CONTACTNUMBER = "contactNumber";
	/** Qualifier of the <code>ContactUsProcess.customerNumber</code> attribute **/
	public static final String CUSTOMERNUMBER = "customerNumber";
	/** Qualifier of the <code>ContactUsProcess.typeOfCustomer</code> attribute **/
	public static final String TYPEOFCUSTOMER = "typeOfCustomer";
	/** Qualifier of the <code>ContactUsProcess.message</code> attribute **/
	public static final String MESSAGE = "message";
	/** Qualifier of the <code>ContactUsProcess.messageforContactUS</code> attribute **/
	public static final String MESSAGEFORCONTACTUS = "messageforContactUS";
	/** Qualifier of the <code>ContactUsProcess.customerCity</code> attribute **/
	public static final String CUSTOMERCITY = "customerCity";
	/** Qualifier of the <code>ContactUsProcess.customerState</code> attribute **/
	public static final String CUSTOMERSTATE = "customerState";
	/** Qualifier of the <code>ContactUsProcess.projectZipcode</code> attribute **/
	public static final String PROJECTZIPCODE = "projectZipcode";
	/** Qualifier of the <code>ContactUsProcess.projectStartDate</code> attribute **/
	public static final String PROJECTSTARTDATE = "projectStartDate";
	/** Qualifier of the <code>ContactUsProcess.inPhaseOf</code> attribute **/
	public static final String INPHASEOF = "inPhaseOf";
	/** Qualifier of the <code>ContactUsProcess.myProject</code> attribute **/
	public static final String MYPROJECT = "myProject";
	/** Qualifier of the <code>ContactUsProcess.myBudget</code> attribute **/
	public static final String MYBUDGET = "myBudget";
	/** Qualifier of the <code>ContactUsProcess.identity</code> attribute **/
	public static final String IDENTITY = "identity";
	/** Qualifier of the <code>ContactUsProcess.identityName</code> attribute **/
	public static final String IDENTITYNAME = "identityName";
	/** Qualifier of the <code>ContactUsProcess.streetAddress</code> attribute **/
	public static final String STREETADDRESS = "streetAddress";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(REASONFORCONTACT, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(LASTNAME, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAIL, AttributeMode.INITIAL);
		tmp.put(CONTACTNUMBER, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNUMBER, AttributeMode.INITIAL);
		tmp.put(TYPEOFCUSTOMER, AttributeMode.INITIAL);
		tmp.put(MESSAGE, AttributeMode.INITIAL);
		tmp.put(MESSAGEFORCONTACTUS, AttributeMode.INITIAL);
		tmp.put(CUSTOMERCITY, AttributeMode.INITIAL);
		tmp.put(CUSTOMERSTATE, AttributeMode.INITIAL);
		tmp.put(PROJECTZIPCODE, AttributeMode.INITIAL);
		tmp.put(PROJECTSTARTDATE, AttributeMode.INITIAL);
		tmp.put(INPHASEOF, AttributeMode.INITIAL);
		tmp.put(MYPROJECT, AttributeMode.INITIAL);
		tmp.put(MYBUDGET, AttributeMode.INITIAL);
		tmp.put(IDENTITY, AttributeMode.INITIAL);
		tmp.put(IDENTITYNAME, AttributeMode.INITIAL);
		tmp.put(STREETADDRESS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.contactNumber</code> attribute.
	 * @return the contactNumber
	 */
	public String getContactNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.contactNumber</code> attribute.
	 * @return the contactNumber
	 */
	public String getContactNumber()
	{
		return getContactNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.contactNumber</code> attribute. 
	 * @param value the contactNumber
	 */
	public void setContactNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.contactNumber</code> attribute. 
	 * @param value the contactNumber
	 */
	public void setContactNumber(final String value)
	{
		setContactNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerCity</code> attribute.
	 * @return the customerCity
	 */
	public String getCustomerCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERCITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerCity</code> attribute.
	 * @return the customerCity
	 */
	public String getCustomerCity()
	{
		return getCustomerCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerCity</code> attribute. 
	 * @param value the customerCity
	 */
	public void setCustomerCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERCITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerCity</code> attribute. 
	 * @param value the customerCity
	 */
	public void setCustomerCity(final String value)
	{
		setCustomerCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerEmail</code> attribute.
	 * @return the customerEmail
	 */
	public String getCustomerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerEmail</code> attribute.
	 * @return the customerEmail
	 */
	public String getCustomerEmail()
	{
		return getCustomerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerEmail</code> attribute. 
	 * @param value the customerEmail
	 */
	public void setCustomerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerEmail</code> attribute. 
	 * @param value the customerEmail
	 */
	public void setCustomerEmail(final String value)
	{
		setCustomerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerNumber</code> attribute.
	 * @return the customerNumber
	 */
	public String getCustomerNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerNumber</code> attribute.
	 * @return the customerNumber
	 */
	public String getCustomerNumber()
	{
		return getCustomerNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerNumber</code> attribute. 
	 * @param value the customerNumber
	 */
	public void setCustomerNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerNumber</code> attribute. 
	 * @param value the customerNumber
	 */
	public void setCustomerNumber(final String value)
	{
		setCustomerNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERSTATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.customerState</code> attribute.
	 * @return the customerState
	 */
	public String getCustomerState()
	{
		return getCustomerState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERSTATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.customerState</code> attribute. 
	 * @param value the customerState
	 */
	public void setCustomerState(final String value)
	{
		setCustomerState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.identity</code> attribute.
	 * @return the identity
	 */
	public String getIdentity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IDENTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.identity</code> attribute.
	 * @return the identity
	 */
	public String getIdentity()
	{
		return getIdentity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.identity</code> attribute. 
	 * @param value the identity
	 */
	public void setIdentity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IDENTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.identity</code> attribute. 
	 * @param value the identity
	 */
	public void setIdentity(final String value)
	{
		setIdentity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.identityName</code> attribute.
	 * @return the identityName
	 */
	public String getIdentityName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IDENTITYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.identityName</code> attribute.
	 * @return the identityName
	 */
	public String getIdentityName()
	{
		return getIdentityName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.identityName</code> attribute. 
	 * @param value the identityName
	 */
	public void setIdentityName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IDENTITYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.identityName</code> attribute. 
	 * @param value the identityName
	 */
	public void setIdentityName(final String value)
	{
		setIdentityName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.inPhaseOf</code> attribute.
	 * @return the inPhaseOf
	 */
	public String getInPhaseOf(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INPHASEOF);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.inPhaseOf</code> attribute.
	 * @return the inPhaseOf
	 */
	public String getInPhaseOf()
	{
		return getInPhaseOf( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.inPhaseOf</code> attribute. 
	 * @param value the inPhaseOf
	 */
	public void setInPhaseOf(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INPHASEOF,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.inPhaseOf</code> attribute. 
	 * @param value the inPhaseOf
	 */
	public void setInPhaseOf(final String value)
	{
		setInPhaseOf( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final String value)
	{
		setLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.message</code> attribute.
	 * @return the message
	 */
	public String getMessage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.message</code> attribute.
	 * @return the message
	 */
	public String getMessage()
	{
		return getMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.message</code> attribute. 
	 * @param value the message
	 */
	public void setMessage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.message</code> attribute. 
	 * @param value the message
	 */
	public void setMessage(final String value)
	{
		setMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.messageforContactUS</code> attribute.
	 * @return the messageforContactUS
	 */
	public String getMessageforContactUS(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MESSAGEFORCONTACTUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.messageforContactUS</code> attribute.
	 * @return the messageforContactUS
	 */
	public String getMessageforContactUS()
	{
		return getMessageforContactUS( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.messageforContactUS</code> attribute. 
	 * @param value the messageforContactUS
	 */
	public void setMessageforContactUS(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MESSAGEFORCONTACTUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.messageforContactUS</code> attribute. 
	 * @param value the messageforContactUS
	 */
	public void setMessageforContactUS(final String value)
	{
		setMessageforContactUS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.myBudget</code> attribute.
	 * @return the myBudget
	 */
	public String getMyBudget(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MYBUDGET);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.myBudget</code> attribute.
	 * @return the myBudget
	 */
	public String getMyBudget()
	{
		return getMyBudget( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.myBudget</code> attribute. 
	 * @param value the myBudget
	 */
	public void setMyBudget(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MYBUDGET,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.myBudget</code> attribute. 
	 * @param value the myBudget
	 */
	public void setMyBudget(final String value)
	{
		setMyBudget( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.myProject</code> attribute.
	 * @return the myProject
	 */
	public String getMyProject(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MYPROJECT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.myProject</code> attribute.
	 * @return the myProject
	 */
	public String getMyProject()
	{
		return getMyProject( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.myProject</code> attribute. 
	 * @param value the myProject
	 */
	public void setMyProject(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MYPROJECT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.myProject</code> attribute. 
	 * @param value the myProject
	 */
	public void setMyProject(final String value)
	{
		setMyProject( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.projectStartDate</code> attribute.
	 * @return the projectStartDate
	 */
	public String getProjectStartDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PROJECTSTARTDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.projectStartDate</code> attribute.
	 * @return the projectStartDate
	 */
	public String getProjectStartDate()
	{
		return getProjectStartDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.projectStartDate</code> attribute. 
	 * @param value the projectStartDate
	 */
	public void setProjectStartDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PROJECTSTARTDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.projectStartDate</code> attribute. 
	 * @param value the projectStartDate
	 */
	public void setProjectStartDate(final String value)
	{
		setProjectStartDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.projectZipcode</code> attribute.
	 * @return the projectZipcode
	 */
	public String getProjectZipcode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PROJECTZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.projectZipcode</code> attribute.
	 * @return the projectZipcode
	 */
	public String getProjectZipcode()
	{
		return getProjectZipcode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.projectZipcode</code> attribute. 
	 * @param value the projectZipcode
	 */
	public void setProjectZipcode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PROJECTZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.projectZipcode</code> attribute. 
	 * @param value the projectZipcode
	 */
	public void setProjectZipcode(final String value)
	{
		setProjectZipcode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.reasonForContact</code> attribute.
	 * @return the reasonForContact
	 */
	public String getReasonForContact(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REASONFORCONTACT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.reasonForContact</code> attribute.
	 * @return the reasonForContact
	 */
	public String getReasonForContact()
	{
		return getReasonForContact( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.reasonForContact</code> attribute. 
	 * @param value the reasonForContact
	 */
	public void setReasonForContact(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REASONFORCONTACT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.reasonForContact</code> attribute. 
	 * @param value the reasonForContact
	 */
	public void setReasonForContact(final String value)
	{
		setReasonForContact( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.streetAddress</code> attribute.
	 * @return the streetAddress
	 */
	public String getStreetAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STREETADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.streetAddress</code> attribute.
	 * @return the streetAddress
	 */
	public String getStreetAddress()
	{
		return getStreetAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.streetAddress</code> attribute. 
	 * @param value the streetAddress
	 */
	public void setStreetAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STREETADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.streetAddress</code> attribute. 
	 * @param value the streetAddress
	 */
	public void setStreetAddress(final String value)
	{
		setStreetAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.typeOfCustomer</code> attribute.
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TYPEOFCUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContactUsProcess.typeOfCustomer</code> attribute.
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer()
	{
		return getTypeOfCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.typeOfCustomer</code> attribute. 
	 * @param value the typeOfCustomer
	 */
	public void setTypeOfCustomer(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TYPEOFCUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContactUsProcess.typeOfCustomer</code> attribute. 
	 * @param value the typeOfCustomer
	 */
	public void setTypeOfCustomer(final String value)
	{
		setTypeOfCustomer( getSession().getSessionContext(), value );
	}
	
}
