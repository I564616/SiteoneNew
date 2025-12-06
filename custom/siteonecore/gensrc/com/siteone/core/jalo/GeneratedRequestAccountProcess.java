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
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.RequestAccountProcess RequestAccountProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedRequestAccountProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>RequestAccountProcess.accountNumber</code> attribute **/
	public static final String ACCOUNTNUMBER = "accountNumber";
	/** Qualifier of the <code>RequestAccountProcess.branchManagerEmail</code> attribute **/
	public static final String BRANCHMANAGEREMAIL = "branchManagerEmail";
	/** Qualifier of the <code>RequestAccountProcess.serviceTypes</code> attribute **/
	public static final String SERVICETYPES = "serviceTypes";
	/** Qualifier of the <code>RequestAccountProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>RequestAccountProcess.lastName</code> attribute **/
	public static final String LASTNAME = "lastName";
	/** Qualifier of the <code>RequestAccountProcess.addressLine1</code> attribute **/
	public static final String ADDRESSLINE1 = "addressLine1";
	/** Qualifier of the <code>RequestAccountProcess.addressLine2</code> attribute **/
	public static final String ADDRESSLINE2 = "addressLine2";
	/** Qualifier of the <code>RequestAccountProcess.city</code> attribute **/
	public static final String CITY = "city";
	/** Qualifier of the <code>RequestAccountProcess.province</code> attribute **/
	public static final String PROVINCE = "province";
	/** Qualifier of the <code>RequestAccountProcess.zipcode</code> attribute **/
	public static final String ZIPCODE = "zipcode";
	/** Qualifier of the <code>RequestAccountProcess.phoneNumber</code> attribute **/
	public static final String PHONENUMBER = "phoneNumber";
	/** Qualifier of the <code>RequestAccountProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>RequestAccountProcess.hasAccountNumber</code> attribute **/
	public static final String HASACCOUNTNUMBER = "hasAccountNumber";
	/** Qualifier of the <code>RequestAccountProcess.companyName</code> attribute **/
	public static final String COMPANYNAME = "companyName";
	/** Qualifier of the <code>RequestAccountProcess.typeOfCustomer</code> attribute **/
	public static final String TYPEOFCUSTOMER = "typeOfCustomer";
	/** Qualifier of the <code>RequestAccountProcess.contrYearsInBusiness</code> attribute **/
	public static final String CONTRYEARSINBUSINESS = "contrYearsInBusiness";
	/** Qualifier of the <code>RequestAccountProcess.contrEmpCount</code> attribute **/
	public static final String CONTREMPCOUNT = "contrEmpCount";
	/** Qualifier of the <code>RequestAccountProcess.contrPrimaryBusiness</code> attribute **/
	public static final String CONTRPRIMARYBUSINESS = "contrPrimaryBusiness";
	/** Qualifier of the <code>RequestAccountProcess.languagePreference</code> attribute **/
	public static final String LANGUAGEPREFERENCE = "languagePreference";
	/** Qualifier of the <code>RequestAccountProcess.isAccountOwner</code> attribute **/
	public static final String ISACCOUNTOWNER = "isAccountOwner";
	/** Qualifier of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute **/
	public static final String ISEMAILUNIQUEINHYBRIS = "isEmailUniqueInHybris";
	/** Qualifier of the <code>RequestAccountProcess.branchNotification</code> attribute **/
	public static final String BRANCHNOTIFICATION = "branchNotification";
	/** Qualifier of the <code>RequestAccountProcess.dunsResponse</code> attribute **/
	public static final String DUNSRESPONSE = "dunsResponse";
	/** Qualifier of the <code>RequestAccountProcess.dunsMatchCandidates</code> attribute **/
	public static final String DUNSMATCHCANDIDATES = "dunsMatchCandidates";
	/** Qualifier of the <code>RequestAccountProcess.storeNumber</code> attribute **/
	public static final String STORENUMBER = "storeNumber";
	/** Qualifier of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute **/
	public static final String ENROLLINPARTNERSPROGRAM = "enrollInPartnersProgram";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(BRANCHMANAGEREMAIL, AttributeMode.INITIAL);
		tmp.put(SERVICETYPES, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(LASTNAME, AttributeMode.INITIAL);
		tmp.put(ADDRESSLINE1, AttributeMode.INITIAL);
		tmp.put(ADDRESSLINE2, AttributeMode.INITIAL);
		tmp.put(CITY, AttributeMode.INITIAL);
		tmp.put(PROVINCE, AttributeMode.INITIAL);
		tmp.put(ZIPCODE, AttributeMode.INITIAL);
		tmp.put(PHONENUMBER, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(HASACCOUNTNUMBER, AttributeMode.INITIAL);
		tmp.put(COMPANYNAME, AttributeMode.INITIAL);
		tmp.put(TYPEOFCUSTOMER, AttributeMode.INITIAL);
		tmp.put(CONTRYEARSINBUSINESS, AttributeMode.INITIAL);
		tmp.put(CONTREMPCOUNT, AttributeMode.INITIAL);
		tmp.put(CONTRPRIMARYBUSINESS, AttributeMode.INITIAL);
		tmp.put(LANGUAGEPREFERENCE, AttributeMode.INITIAL);
		tmp.put(ISACCOUNTOWNER, AttributeMode.INITIAL);
		tmp.put(ISEMAILUNIQUEINHYBRIS, AttributeMode.INITIAL);
		tmp.put(BRANCHNOTIFICATION, AttributeMode.INITIAL);
		tmp.put(DUNSRESPONSE, AttributeMode.INITIAL);
		tmp.put(DUNSMATCHCANDIDATES, AttributeMode.INITIAL);
		tmp.put(STORENUMBER, AttributeMode.INITIAL);
		tmp.put(ENROLLINPARTNERSPROGRAM, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.accountNumber</code> attribute.
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return getAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.accountNumber</code> attribute. 
	 * @param value the accountNumber
	 */
	public void setAccountNumber(final String value)
	{
		setAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.addressLine1</code> attribute.
	 * @return the addressLine1
	 */
	public String getAddressLine1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESSLINE1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.addressLine1</code> attribute.
	 * @return the addressLine1
	 */
	public String getAddressLine1()
	{
		return getAddressLine1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.addressLine1</code> attribute. 
	 * @param value the addressLine1
	 */
	public void setAddressLine1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESSLINE1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.addressLine1</code> attribute. 
	 * @param value the addressLine1
	 */
	public void setAddressLine1(final String value)
	{
		setAddressLine1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.addressLine2</code> attribute.
	 * @return the addressLine2
	 */
	public String getAddressLine2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESSLINE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.addressLine2</code> attribute.
	 * @return the addressLine2
	 */
	public String getAddressLine2()
	{
		return getAddressLine2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.addressLine2</code> attribute. 
	 * @param value the addressLine2
	 */
	public void setAddressLine2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESSLINE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.addressLine2</code> attribute. 
	 * @param value the addressLine2
	 */
	public void setAddressLine2(final String value)
	{
		setAddressLine2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANCHMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return getBranchManagerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANCHMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final String value)
	{
		setBranchManagerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.branchNotification</code> attribute.
	 * @return the branchNotification
	 */
	public Boolean isBranchNotification(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, BRANCHNOTIFICATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.branchNotification</code> attribute.
	 * @return the branchNotification
	 */
	public Boolean isBranchNotification()
	{
		return isBranchNotification( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.branchNotification</code> attribute. 
	 * @return the branchNotification
	 */
	public boolean isBranchNotificationAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isBranchNotification( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.branchNotification</code> attribute. 
	 * @return the branchNotification
	 */
	public boolean isBranchNotificationAsPrimitive()
	{
		return isBranchNotificationAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.branchNotification</code> attribute. 
	 * @param value the branchNotification
	 */
	public void setBranchNotification(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, BRANCHNOTIFICATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.branchNotification</code> attribute. 
	 * @param value the branchNotification
	 */
	public void setBranchNotification(final Boolean value)
	{
		setBranchNotification( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.branchNotification</code> attribute. 
	 * @param value the branchNotification
	 */
	public void setBranchNotification(final SessionContext ctx, final boolean value)
	{
		setBranchNotification( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.branchNotification</code> attribute. 
	 * @param value the branchNotification
	 */
	public void setBranchNotification(final boolean value)
	{
		setBranchNotification( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.city</code> attribute.
	 * @return the city
	 */
	public String getCity(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.city</code> attribute.
	 * @return the city
	 */
	public String getCity()
	{
		return getCity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.city</code> attribute. 
	 * @param value the city
	 */
	public void setCity(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.city</code> attribute. 
	 * @param value the city
	 */
	public void setCity(final String value)
	{
		setCity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, COMPANYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.companyName</code> attribute.
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return getCompanyName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, COMPANYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.companyName</code> attribute. 
	 * @param value the companyName
	 */
	public void setCompanyName(final String value)
	{
		setCompanyName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.contrEmpCount</code> attribute.
	 * @return the contrEmpCount
	 */
	public String getContrEmpCount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTREMPCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.contrEmpCount</code> attribute.
	 * @return the contrEmpCount
	 */
	public String getContrEmpCount()
	{
		return getContrEmpCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.contrEmpCount</code> attribute. 
	 * @param value the contrEmpCount
	 */
	public void setContrEmpCount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTREMPCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.contrEmpCount</code> attribute. 
	 * @param value the contrEmpCount
	 */
	public void setContrEmpCount(final String value)
	{
		setContrEmpCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.contrPrimaryBusiness</code> attribute.
	 * @return the contrPrimaryBusiness
	 */
	public String getContrPrimaryBusiness(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTRPRIMARYBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.contrPrimaryBusiness</code> attribute.
	 * @return the contrPrimaryBusiness
	 */
	public String getContrPrimaryBusiness()
	{
		return getContrPrimaryBusiness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.contrPrimaryBusiness</code> attribute. 
	 * @param value the contrPrimaryBusiness
	 */
	public void setContrPrimaryBusiness(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTRPRIMARYBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.contrPrimaryBusiness</code> attribute. 
	 * @param value the contrPrimaryBusiness
	 */
	public void setContrPrimaryBusiness(final String value)
	{
		setContrPrimaryBusiness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.contrYearsInBusiness</code> attribute.
	 * @return the contrYearsInBusiness
	 */
	public String getContrYearsInBusiness(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTRYEARSINBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.contrYearsInBusiness</code> attribute.
	 * @return the contrYearsInBusiness
	 */
	public String getContrYearsInBusiness()
	{
		return getContrYearsInBusiness( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.contrYearsInBusiness</code> attribute. 
	 * @param value the contrYearsInBusiness
	 */
	public void setContrYearsInBusiness(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTRYEARSINBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.contrYearsInBusiness</code> attribute. 
	 * @param value the contrYearsInBusiness
	 */
	public void setContrYearsInBusiness(final String value)
	{
		setContrYearsInBusiness( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.dunsMatchCandidates</code> attribute.
	 * @return the dunsMatchCandidates
	 */
	public Map<Integer,List<String>> getAllDunsMatchCandidates(final SessionContext ctx)
	{
		Map<Integer,List<String>> map = (Map<Integer,List<String>>)getProperty( ctx, DUNSMATCHCANDIDATES);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.dunsMatchCandidates</code> attribute.
	 * @return the dunsMatchCandidates
	 */
	public Map<Integer,List<String>> getAllDunsMatchCandidates()
	{
		return getAllDunsMatchCandidates( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.dunsMatchCandidates</code> attribute. 
	 * @param value the dunsMatchCandidates
	 */
	public void setAllDunsMatchCandidates(final SessionContext ctx, final Map<Integer,List<String>> value)
	{
		setProperty(ctx, DUNSMATCHCANDIDATES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.dunsMatchCandidates</code> attribute. 
	 * @param value the dunsMatchCandidates
	 */
	public void setAllDunsMatchCandidates(final Map<Integer,List<String>> value)
	{
		setAllDunsMatchCandidates( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.dunsResponse</code> attribute.
	 * @return the dunsResponse
	 */
	public Integer getDunsResponse(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, DUNSRESPONSE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.dunsResponse</code> attribute.
	 * @return the dunsResponse
	 */
	public Integer getDunsResponse()
	{
		return getDunsResponse( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.dunsResponse</code> attribute. 
	 * @return the dunsResponse
	 */
	public int getDunsResponseAsPrimitive(final SessionContext ctx)
	{
		Integer value = getDunsResponse( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.dunsResponse</code> attribute. 
	 * @return the dunsResponse
	 */
	public int getDunsResponseAsPrimitive()
	{
		return getDunsResponseAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.dunsResponse</code> attribute. 
	 * @param value the dunsResponse
	 */
	public void setDunsResponse(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, DUNSRESPONSE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.dunsResponse</code> attribute. 
	 * @param value the dunsResponse
	 */
	public void setDunsResponse(final Integer value)
	{
		setDunsResponse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.dunsResponse</code> attribute. 
	 * @param value the dunsResponse
	 */
	public void setDunsResponse(final SessionContext ctx, final int value)
	{
		setDunsResponse( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.dunsResponse</code> attribute. 
	 * @param value the dunsResponse
	 */
	public void setDunsResponse(final int value)
	{
		setDunsResponse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute.
	 * @return the enrollInPartnersProgram
	 */
	public Boolean isEnrollInPartnersProgram(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ENROLLINPARTNERSPROGRAM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute.
	 * @return the enrollInPartnersProgram
	 */
	public Boolean isEnrollInPartnersProgram()
	{
		return isEnrollInPartnersProgram( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute. 
	 * @return the enrollInPartnersProgram
	 */
	public boolean isEnrollInPartnersProgramAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isEnrollInPartnersProgram( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute. 
	 * @return the enrollInPartnersProgram
	 */
	public boolean isEnrollInPartnersProgramAsPrimitive()
	{
		return isEnrollInPartnersProgramAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ENROLLINPARTNERSPROGRAM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final Boolean value)
	{
		setEnrollInPartnersProgram( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final SessionContext ctx, final boolean value)
	{
		setEnrollInPartnersProgram( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.enrollInPartnersProgram</code> attribute. 
	 * @param value the enrollInPartnersProgram
	 */
	public void setEnrollInPartnersProgram(final boolean value)
	{
		setEnrollInPartnersProgram( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute.
	 * @return the hasAccountNumber
	 */
	public Boolean isHasAccountNumber(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HASACCOUNTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute.
	 * @return the hasAccountNumber
	 */
	public Boolean isHasAccountNumber()
	{
		return isHasAccountNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute. 
	 * @return the hasAccountNumber
	 */
	public boolean isHasAccountNumberAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHasAccountNumber( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute. 
	 * @return the hasAccountNumber
	 */
	public boolean isHasAccountNumberAsPrimitive()
	{
		return isHasAccountNumberAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HASACCOUNTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final Boolean value)
	{
		setHasAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final SessionContext ctx, final boolean value)
	{
		setHasAccountNumber( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.hasAccountNumber</code> attribute. 
	 * @param value the hasAccountNumber
	 */
	public void setHasAccountNumber(final boolean value)
	{
		setHasAccountNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isAccountOwner</code> attribute.
	 * @return the isAccountOwner
	 */
	public Boolean isIsAccountOwner(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISACCOUNTOWNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isAccountOwner</code> attribute.
	 * @return the isAccountOwner
	 */
	public Boolean isIsAccountOwner()
	{
		return isIsAccountOwner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isAccountOwner</code> attribute. 
	 * @return the isAccountOwner
	 */
	public boolean isIsAccountOwnerAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsAccountOwner( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isAccountOwner</code> attribute. 
	 * @return the isAccountOwner
	 */
	public boolean isIsAccountOwnerAsPrimitive()
	{
		return isIsAccountOwnerAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISACCOUNTOWNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final Boolean value)
	{
		setIsAccountOwner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final SessionContext ctx, final boolean value)
	{
		setIsAccountOwner( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner
	 */
	public void setIsAccountOwner(final boolean value)
	{
		setIsAccountOwner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute.
	 * @return the isEmailUniqueInHybris
	 */
	public Boolean isIsEmailUniqueInHybris(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISEMAILUNIQUEINHYBRIS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute.
	 * @return the isEmailUniqueInHybris
	 */
	public Boolean isIsEmailUniqueInHybris()
	{
		return isIsEmailUniqueInHybris( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute. 
	 * @return the isEmailUniqueInHybris
	 */
	public boolean isIsEmailUniqueInHybrisAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsEmailUniqueInHybris( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute. 
	 * @return the isEmailUniqueInHybris
	 */
	public boolean isIsEmailUniqueInHybrisAsPrimitive()
	{
		return isIsEmailUniqueInHybrisAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute. 
	 * @param value the isEmailUniqueInHybris
	 */
	public void setIsEmailUniqueInHybris(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISEMAILUNIQUEINHYBRIS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute. 
	 * @param value the isEmailUniqueInHybris
	 */
	public void setIsEmailUniqueInHybris(final Boolean value)
	{
		setIsEmailUniqueInHybris( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute. 
	 * @param value the isEmailUniqueInHybris
	 */
	public void setIsEmailUniqueInHybris(final SessionContext ctx, final boolean value)
	{
		setIsEmailUniqueInHybris( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.isEmailUniqueInHybris</code> attribute. 
	 * @param value the isEmailUniqueInHybris
	 */
	public void setIsEmailUniqueInHybris(final boolean value)
	{
		setIsEmailUniqueInHybris( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.languagePreference</code> attribute.
	 * @return the languagePreference
	 */
	public String getLanguagePreference(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LANGUAGEPREFERENCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.languagePreference</code> attribute.
	 * @return the languagePreference
	 */
	public String getLanguagePreference()
	{
		return getLanguagePreference( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.languagePreference</code> attribute. 
	 * @param value the languagePreference
	 */
	public void setLanguagePreference(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LANGUAGEPREFERENCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.languagePreference</code> attribute. 
	 * @param value the languagePreference
	 */
	public void setLanguagePreference(final String value)
	{
		setLanguagePreference( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getLastName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final String value)
	{
		setLastName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.phoneNumber</code> attribute.
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return getPhoneNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.phoneNumber</code> attribute. 
	 * @param value the phoneNumber
	 */
	public void setPhoneNumber(final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.province</code> attribute.
	 * @return the province
	 */
	public String getProvince(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PROVINCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.province</code> attribute.
	 * @return the province
	 */
	public String getProvince()
	{
		return getProvince( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.province</code> attribute. 
	 * @param value the province
	 */
	public void setProvince(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PROVINCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.province</code> attribute. 
	 * @param value the province
	 */
	public void setProvince(final String value)
	{
		setProvince( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.serviceTypes</code> attribute.
	 * @return the serviceTypes
	 */
	public String getServiceTypes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERVICETYPES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.serviceTypes</code> attribute.
	 * @return the serviceTypes
	 */
	public String getServiceTypes()
	{
		return getServiceTypes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.serviceTypes</code> attribute. 
	 * @param value the serviceTypes
	 */
	public void setServiceTypes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERVICETYPES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.serviceTypes</code> attribute. 
	 * @param value the serviceTypes
	 */
	public void setServiceTypes(final String value)
	{
		setServiceTypes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.storeNumber</code> attribute.
	 * @return the storeNumber
	 */
	public String getStoreNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STORENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.storeNumber</code> attribute.
	 * @return the storeNumber
	 */
	public String getStoreNumber()
	{
		return getStoreNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.storeNumber</code> attribute. 
	 * @param value the storeNumber
	 */
	public void setStoreNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STORENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.storeNumber</code> attribute. 
	 * @param value the storeNumber
	 */
	public void setStoreNumber(final String value)
	{
		setStoreNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.typeOfCustomer</code> attribute.
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TYPEOFCUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.typeOfCustomer</code> attribute.
	 * @return the typeOfCustomer
	 */
	public String getTypeOfCustomer()
	{
		return getTypeOfCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.typeOfCustomer</code> attribute. 
	 * @param value the typeOfCustomer
	 */
	public void setTypeOfCustomer(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TYPEOFCUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.typeOfCustomer</code> attribute. 
	 * @param value the typeOfCustomer
	 */
	public void setTypeOfCustomer(final String value)
	{
		setTypeOfCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ZIPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>RequestAccountProcess.zipcode</code> attribute.
	 * @return the zipcode
	 */
	public String getZipcode()
	{
		return getZipcode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ZIPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>RequestAccountProcess.zipcode</code> attribute. 
	 * @param value the zipcode
	 */
	public void setZipcode(final String value)
	{
		setZipcode( getSession().getSessionContext(), value );
	}
	
}
