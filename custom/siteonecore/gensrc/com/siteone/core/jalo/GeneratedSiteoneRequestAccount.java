/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem SiteoneRequestAccount}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneRequestAccount extends GenericItem
{
	/** Qualifier of the <code>SiteoneRequestAccount.companyName</code> attribute **/
	public static final String COMPANYNAME = "companyName";
	/** Qualifier of the <code>SiteoneRequestAccount.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>SiteoneRequestAccount.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>SiteoneRequestAccount.lastName</code> attribute **/
	public static final String LASTNAME = "lastName";
	/** Qualifier of the <code>SiteoneRequestAccount.addressLine1</code> attribute **/
	public static final String ADDRESSLINE1 = "addressLine1";
	/** Qualifier of the <code>SiteoneRequestAccount.addressLine2</code> attribute **/
	public static final String ADDRESSLINE2 = "addressLine2";
	/** Qualifier of the <code>SiteoneRequestAccount.city</code> attribute **/
	public static final String CITY = "city";
	/** Qualifier of the <code>SiteoneRequestAccount.state</code> attribute **/
	public static final String STATE = "state";
	/** Qualifier of the <code>SiteoneRequestAccount.zipcode</code> attribute **/
	public static final String ZIPCODE = "zipcode";
	/** Qualifier of the <code>SiteoneRequestAccount.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	/** Qualifier of the <code>SiteoneRequestAccount.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>SiteoneRequestAccount.typeOfCustomer</code> attribute **/
	public static final String TYPEOFCUSTOMER = "typeOfCustomer";
	/** Qualifier of the <code>SiteoneRequestAccount.contrYearsInBusiness</code> attribute **/
	public static final String CONTRYEARSINBUSINESS = "contrYearsInBusiness";
	/** Qualifier of the <code>SiteoneRequestAccount.contrEmpCount</code> attribute **/
	public static final String CONTREMPCOUNT = "contrEmpCount";
	/** Qualifier of the <code>SiteoneRequestAccount.contrPrimaryBusiness</code> attribute **/
	public static final String CONTRPRIMARYBUSINESS = "contrPrimaryBusiness";
	/** Qualifier of the <code>SiteoneRequestAccount.contrPrimaryChildBusiness</code> attribute **/
	public static final String CONTRPRIMARYCHILDBUSINESS = "contrPrimaryChildBusiness";
	/** Qualifier of the <code>SiteoneRequestAccount.languagePreference</code> attribute **/
	public static final String LANGUAGEPREFERENCE = "languagePreference";
	/** Qualifier of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute **/
	public static final String HASACCOUNTNUMBER = "hasAccountNumber";
	/** Qualifier of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute **/
	public static final String ISACCOUNTOWNER = "isAccountOwner";
	/** Qualifier of the <code>SiteoneRequestAccount.uuid</code> attribute **/
	public static final String UUID = "uuid";
	/** Qualifier of the <code>SiteoneRequestAccount.storeNumber</code> attribute **/
	public static final String STORENUMBER = "storeNumber";
	/** Qualifier of the <code>SiteoneRequestAccount.uuidType</code> attribute **/
	public static final String UUIDTYPE = "uuidType";
	/** Qualifier of the <code>SiteoneRequestAccount.tradeClassName</code> attribute **/
	public static final String TRADECLASSNAME = "tradeClassName";
	/** Qualifier of the <code>SiteoneRequestAccount.subTradeClassName</code> attribute **/
	public static final String SUBTRADECLASSNAME = "subTradeClassName";
	/** Qualifier of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute **/
	public static final String ENROLLINPARTNERSPROGRAM = "enrollInPartnersProgram";
	/** Qualifier of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute **/
	public static final String LANDSCAPINGINDUSTRY = "landscapingIndustry";
	/** Qualifier of the <code>SiteoneRequestAccount.tradeClass</code> attribute **/
	public static final String TRADECLASS = "tradeClass";
	/** Qualifier of the <code>SiteoneRequestAccount.subTradeClass</code> attribute **/
	public static final String SUBTRADECLASS = "subTradeClass";
	/** Qualifier of the <code>SiteoneRequestAccount.creditCode</code> attribute **/
	public static final String CREDITCODE = "creditCode";
	/** Qualifier of the <code>SiteoneRequestAccount.creditTerms</code> attribute **/
	public static final String CREDITTERMS = "creditTerms";
	/** Qualifier of the <code>SiteoneRequestAccount.acctGroupCode</code> attribute **/
	public static final String ACCTGROUPCODE = "acctGroupCode";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(COMPANYNAME, AttributeMode.INITIAL);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(LASTNAME, AttributeMode.INITIAL);
		tmp.put(ADDRESSLINE1, AttributeMode.INITIAL);
		tmp.put(ADDRESSLINE2, AttributeMode.INITIAL);
		tmp.put(CITY, AttributeMode.INITIAL);
		tmp.put(STATE, AttributeMode.INITIAL);
		tmp.put(ZIPCODE, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(TYPEOFCUSTOMER, AttributeMode.INITIAL);
		tmp.put(CONTRYEARSINBUSINESS, AttributeMode.INITIAL);
		tmp.put(CONTREMPCOUNT, AttributeMode.INITIAL);
		tmp.put(CONTRPRIMARYBUSINESS, AttributeMode.INITIAL);
		tmp.put(CONTRPRIMARYCHILDBUSINESS, AttributeMode.INITIAL);
		tmp.put(LANGUAGEPREFERENCE, AttributeMode.INITIAL);
		tmp.put(HASACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(ISACCOUNTOWNER, AttributeMode.INITIAL);
		tmp.put(UUID, AttributeMode.INITIAL);
		tmp.put(STORENUMBER, AttributeMode.INITIAL);
		tmp.put(UUIDTYPE, AttributeMode.INITIAL);
		tmp.put(TRADECLASSNAME, AttributeMode.INITIAL);
		tmp.put(SUBTRADECLASSNAME, AttributeMode.INITIAL);
		tmp.put(ENROLLINPARTNERSPROGRAM, AttributeMode.INITIAL);
		tmp.put(LANDSCAPINGINDUSTRY, AttributeMode.INITIAL);
		tmp.put(TRADECLASS, AttributeMode.INITIAL);
		tmp.put(SUBTRADECLASS, AttributeMode.INITIAL);
		tmp.put(CREDITCODE, AttributeMode.INITIAL);
		tmp.put(CREDITTERMS, AttributeMode.INITIAL);
		tmp.put(ACCTGROUPCODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.acctGroupCode</code> attribute.
	 * @return the acctGroupCode
	 */
	public String getAcctGroupCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCTGROUPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.acctGroupCode</code> attribute.
	 * @return the acctGroupCode
	 */
	public String getAcctGroupCode()
	{
		return getAcctGroupCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.acctGroupCode</code> attribute. 
	 * @param value the acctGroupCode
	 */
	public void setAcctGroupCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCTGROUPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.acctGroupCode</code> attribute. 
	 * @param value the acctGroupCode
	 */
	public void setAcctGroupCode(final String value)
	{
		setAcctGroupCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.addressLine1</code> attribute.
	 * @return the addressLine1
	 */
	public String getAddressLine1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESSLINE1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.addressLine1</code> attribute.
	 * @return the addressLine1
	 */
	public String getAddressLine1()
	{
		return getAddressLine1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.addressLine1</code> attribute. 
	 * @param value the addressLine1
	 */
	public void setAddressLine1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESSLINE1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.addressLine1</code> attribute. 
	 * @param value the addressLine1
	 */
	public void setAddressLine1(final String value)
	{
		setAddressLine1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.addressLine2</code> attribute.
	 * @return the addressLine2
	 */
	public String getAddressLine2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESSLINE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.addressLine2</code> attribute.
	 * @return the addressLine2
	 */
	public String getAddressLine2()
	{
		return getAddressLine2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.addressLine2</code> attribute. 
	 * @param value the addressLine2
	 */
	public void setAddressLine2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESSLINE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.addressLine2</code> attribute. 
	 * @param value the addressLine2
	 */
	public void setAddressLine2(final String value)
	{
		setAddressLine2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.city</code> attribute.
	 * @return the city
	 */
	public String getCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.city</code> attribute.
	 * @return the city
	 */
	public String getCity()
	{
		return getCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.city</code> attribute. 
	 * @param value the city
	 */
	public void setCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.city</code> attribute. 
	 * @param value the city
	 */
	public void setCity(final String value)
	{
		setCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COMPANYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return getCompanyName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COMPANYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final String value)
	{
		setCompanyName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrEmpCount</code> attribute.
	 * @return the contrEmpCount
	 */
	public String getContrEmpCount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTREMPCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrEmpCount</code> attribute.
	 * @return the contrEmpCount
	 */
	public String getContrEmpCount()
	{
		return getContrEmpCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrEmpCount</code> attribute. 
	 * @param value the contrEmpCount
	 */
	public void setContrEmpCount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTREMPCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrEmpCount</code> attribute. 
	 * @param value the contrEmpCount
	 */
	public void setContrEmpCount(final String value)
	{
		setContrEmpCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrPrimaryBusiness</code> attribute.
	 * @return the contrPrimaryBusiness
	 */
	public String getContrPrimaryBusiness(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTRPRIMARYBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrPrimaryBusiness</code> attribute.
	 * @return the contrPrimaryBusiness
	 */
	public String getContrPrimaryBusiness()
	{
		return getContrPrimaryBusiness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrPrimaryBusiness</code> attribute. 
	 * @param value the contrPrimaryBusiness
	 */
	public void setContrPrimaryBusiness(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTRPRIMARYBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrPrimaryBusiness</code> attribute. 
	 * @param value the contrPrimaryBusiness
	 */
	public void setContrPrimaryBusiness(final String value)
	{
		setContrPrimaryBusiness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrPrimaryChildBusiness</code> attribute.
	 * @return the contrPrimaryChildBusiness
	 */
	public String getContrPrimaryChildBusiness(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTRPRIMARYCHILDBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrPrimaryChildBusiness</code> attribute.
	 * @return the contrPrimaryChildBusiness
	 */
	public String getContrPrimaryChildBusiness()
	{
		return getContrPrimaryChildBusiness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrPrimaryChildBusiness</code> attribute. 
	 * @param value the contrPrimaryChildBusiness
	 */
	public void setContrPrimaryChildBusiness(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTRPRIMARYCHILDBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrPrimaryChildBusiness</code> attribute. 
	 * @param value the contrPrimaryChildBusiness
	 */
	public void setContrPrimaryChildBusiness(final String value)
	{
		setContrPrimaryChildBusiness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrYearsInBusiness</code> attribute.
	 * @return the contrYearsInBusiness
	 */
	public String getContrYearsInBusiness(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTRYEARSINBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.contrYearsInBusiness</code> attribute.
	 * @return the contrYearsInBusiness
	 */
	public String getContrYearsInBusiness()
	{
		return getContrYearsInBusiness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrYearsInBusiness</code> attribute. 
	 * @param value the contrYearsInBusiness
	 */
	public void setContrYearsInBusiness(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTRYEARSINBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.contrYearsInBusiness</code> attribute. 
	 * @param value the contrYearsInBusiness
	 */
	public void setContrYearsInBusiness(final String value)
	{
		setContrYearsInBusiness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.creditCode</code> attribute.
	 * @return the creditCode
	 */
	public String getCreditCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.creditCode</code> attribute.
	 * @return the creditCode
	 */
	public String getCreditCode()
	{
		return getCreditCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.creditCode</code> attribute. 
	 * @param value the creditCode
	 */
	public void setCreditCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.creditCode</code> attribute. 
	 * @param value the creditCode
	 */
	public void setCreditCode(final String value)
	{
		setCreditCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.creditTerms</code> attribute.
	 * @return the creditTerms
	 */
	public String getCreditTerms(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITTERMS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.creditTerms</code> attribute.
	 * @return the creditTerms
	 */
	public String getCreditTerms()
	{
		return getCreditTerms( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.creditTerms</code> attribute. 
	 * @param value the creditTerms
	 */
	public void setCreditTerms(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITTERMS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.creditTerms</code> attribute. 
	 * @param value the creditTerms
	 */
	public void setCreditTerms(final String value)
	{
		setCreditTerms( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute.
	 * @return the enrollInPartnersProgram
	 */
	public Boolean isEnrollInPartnersProgram(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ENROLLINPARTNERSPROGRAM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute.
	 * @return the enrollInPartnersProgram
	 */
	public Boolean isEnrollInPartnersProgram()
	{
		return isEnrollInPartnersProgram( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute. 
	 * @return the enrollInPartnersProgram
	 */
	public boolean isEnrollInPartnersProgramAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isEnrollInPartnersProgram( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute. 
	 * @return the enrollInPartnersProgram
	 */
	public boolean isEnrollInPartnersProgramAsPrimitive()
	{
		return isEnrollInPartnersProgramAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ENROLLINPARTNERSPROGRAM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final Boolean value)
	{
		setEnrollInPartnersProgram( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final SessionContext ctx, final boolean value)
	{
		setEnrollInPartnersProgram( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final boolean value)
	{
		setEnrollInPartnersProgram( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute.
	 * @return the hasAccountNumber
	 */
	public Boolean isHasAccountNumber(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute.
	 * @return the hasAccountNumber
	 */
	public Boolean isHasAccountNumber()
	{
		return isHasAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute. 
	 * @return the hasAccountNumber
	 */
	public boolean isHasAccountNumberAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasAccountNumber( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute. 
	 * @return the hasAccountNumber
	 */
	public boolean isHasAccountNumberAsPrimitive()
	{
		return isHasAccountNumberAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final Boolean value)
	{
		setHasAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final SessionContext ctx, final boolean value)
	{
		setHasAccountNumber( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final boolean value)
	{
		setHasAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute.
	 * @return the isAccountOwner
	 */
	public Boolean isIsAccountOwner(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISACCOUNTOWNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute.
	 * @return the isAccountOwner
	 */
	public Boolean isIsAccountOwner()
	{
		return isIsAccountOwner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute. 
	 * @return the isAccountOwner
	 */
	public boolean isIsAccountOwnerAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsAccountOwner( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute. 
	 * @return the isAccountOwner
	 */
	public boolean isIsAccountOwnerAsPrimitive()
	{
		return isIsAccountOwnerAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISACCOUNTOWNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final Boolean value)
	{
		setIsAccountOwner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final SessionContext ctx, final boolean value)
	{
		setIsAccountOwner( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final boolean value)
	{
		setIsAccountOwner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute.
	 * @return the landscapingIndustry
	 */
	public Boolean isLandscapingIndustry(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, LANDSCAPINGINDUSTRY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute.
	 * @return the landscapingIndustry
	 */
	public Boolean isLandscapingIndustry()
	{
		return isLandscapingIndustry( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute. 
	 * @return the landscapingIndustry
	 */
	public boolean isLandscapingIndustryAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isLandscapingIndustry( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute. 
	 * @return the landscapingIndustry
	 */
	public boolean isLandscapingIndustryAsPrimitive()
	{
		return isLandscapingIndustryAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute. 
	 * @param value the landscapingIndustry
	 */
	public void setLandscapingIndustry(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, LANDSCAPINGINDUSTRY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute. 
	 * @param value the landscapingIndustry
	 */
	public void setLandscapingIndustry(final Boolean value)
	{
		setLandscapingIndustry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute. 
	 * @param value the landscapingIndustry
	 */
	public void setLandscapingIndustry(final SessionContext ctx, final boolean value)
	{
		setLandscapingIndustry( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.landscapingIndustry</code> attribute. 
	 * @param value the landscapingIndustry
	 */
	public void setLandscapingIndustry(final boolean value)
	{
		setLandscapingIndustry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.languagePreference</code> attribute.
	 * @return the languagePreference
	 */
	public String getLanguagePreference(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LANGUAGEPREFERENCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.languagePreference</code> attribute.
	 * @return the languagePreference
	 */
	public String getLanguagePreference()
	{
		return getLanguagePreference( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.languagePreference</code> attribute. 
	 * @param value the languagePreference
	 */
	public void setLanguagePreference(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LANGUAGEPREFERENCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.languagePreference</code> attribute. 
	 * @param value the languagePreference
	 */
	public void setLanguagePreference(final String value)
	{
		setLanguagePreference( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final String value)
	{
		setLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.state</code> attribute.
	 * @return the state
	 */
	public String getState(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.state</code> attribute.
	 * @return the state
	 */
	public String getState()
	{
		return getState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final String value)
	{
		setState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.storeNumber</code> attribute.
	 * @return the storeNumber
	 */
	public String getStoreNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STORENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.storeNumber</code> attribute.
	 * @return the storeNumber
	 */
	public String getStoreNumber()
	{
		return getStoreNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.storeNumber</code> attribute. 
	 * @param value the storeNumber
	 */
	public void setStoreNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STORENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.storeNumber</code> attribute. 
	 * @param value the storeNumber
	 */
	public void setStoreNumber(final String value)
	{
		setStoreNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.subTradeClass</code> attribute.
	 * @return the subTradeClass
	 */
	public String getSubTradeClass(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SUBTRADECLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.subTradeClass</code> attribute.
	 * @return the subTradeClass
	 */
	public String getSubTradeClass()
	{
		return getSubTradeClass( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.subTradeClass</code> attribute. 
	 * @param value the subTradeClass
	 */
	public void setSubTradeClass(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SUBTRADECLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.subTradeClass</code> attribute. 
	 * @param value the subTradeClass
	 */
	public void setSubTradeClass(final String value)
	{
		setSubTradeClass( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.subTradeClassName</code> attribute.
	 * @return the subTradeClassName - customer Sub Trade Class name
	 */
	public String getSubTradeClassName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SUBTRADECLASSNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.subTradeClassName</code> attribute.
	 * @return the subTradeClassName - customer Sub Trade Class name
	 */
	public String getSubTradeClassName()
	{
		return getSubTradeClassName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.subTradeClassName</code> attribute. 
	 * @param value the subTradeClassName - customer Sub Trade Class name
	 */
	public void setSubTradeClassName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SUBTRADECLASSNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.subTradeClassName</code> attribute. 
	 * @param value the subTradeClassName - customer Sub Trade Class name
	 */
	public void setSubTradeClassName(final String value)
	{
		setSubTradeClassName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.tradeClass</code> attribute.
	 * @return the tradeClass
	 */
	public String getTradeClass(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TRADECLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.tradeClass</code> attribute.
	 * @return the tradeClass
	 */
	public String getTradeClass()
	{
		return getTradeClass( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.tradeClass</code> attribute. 
	 * @param value the tradeClass
	 */
	public void setTradeClass(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TRADECLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.tradeClass</code> attribute. 
	 * @param value the tradeClass
	 */
	public void setTradeClass(final String value)
	{
		setTradeClass( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.tradeClassName</code> attribute.
	 * @return the tradeClassName - customer Trade Class name
	 */
	public String getTradeClassName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TRADECLASSNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.tradeClassName</code> attribute.
	 * @return the tradeClassName - customer Trade Class name
	 */
	public String getTradeClassName()
	{
		return getTradeClassName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.tradeClassName</code> attribute. 
	 * @param value the tradeClassName - customer Trade Class name
	 */
	public void setTradeClassName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TRADECLASSNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.tradeClassName</code> attribute. 
	 * @param value the tradeClassName - customer Trade Class name
	 */
	public void setTradeClassName(final String value)
	{
		setTradeClassName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.typeOfCustomer</code> attribute.
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TYPEOFCUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.typeOfCustomer</code> attribute.
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer()
	{
		return getTypeOfCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.typeOfCustomer</code> attribute. 
	 * @param value the typeOfCustomer
	 */
	public void setTypeOfCustomer(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TYPEOFCUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.typeOfCustomer</code> attribute. 
	 * @param value the typeOfCustomer
	 */
	public void setTypeOfCustomer(final String value)
	{
		setTypeOfCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.uuid</code> attribute.
	 * @return the uuid
	 */
	public String getUuid(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UUID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.uuid</code> attribute.
	 * @return the uuid
	 */
	public String getUuid()
	{
		return getUuid( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.uuid</code> attribute. 
	 * @param value the uuid
	 */
	public void setUuid(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UUID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.uuid</code> attribute. 
	 * @param value the uuid
	 */
	public void setUuid(final String value)
	{
		setUuid( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.uuidType</code> attribute.
	 * @return the uuidType
	 */
	public String getUuidType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, UUIDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.uuidType</code> attribute.
	 * @return the uuidType
	 */
	public String getUuidType()
	{
		return getUuidType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.uuidType</code> attribute. 
	 * @param value the uuidType
	 */
	public void setUuidType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, UUIDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.uuidType</code> attribute. 
	 * @param value the uuidType
	 */
	public void setUuidType(final String value)
	{
		setUuidType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneRequestAccount.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode()
	{
		return getZipcode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneRequestAccount.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final String value)
	{
		setZipcode( getSession().getSessionContext(), value );
	}
	
}
