/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.basecommerce.jalo.site.BaseSite;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.LinkToPayCayanResponse LinkToPayCayanResponse}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedLinkToPayCayanResponse extends GenericItem
{
	/** Qualifier of the <code>LinkToPayCayanResponse.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	/** Qualifier of the <code>LinkToPayCayanResponse.site</code> attribute **/
	public static final String SITE = "site";
	/** Qualifier of the <code>LinkToPayCayanResponse.language</code> attribute **/
	public static final String LANGUAGE = "language";
	/** Qualifier of the <code>LinkToPayCayanResponse.transactionStatus</code> attribute **/
	public static final String TRANSACTIONSTATUS = "transactionStatus";
	/** Qualifier of the <code>LinkToPayCayanResponse.creditCardType</code> attribute **/
	public static final String CREDITCARDTYPE = "creditCardType";
	/** Qualifier of the <code>LinkToPayCayanResponse.expDate</code> attribute **/
	public static final String EXPDATE = "expDate";
	/** Qualifier of the <code>LinkToPayCayanResponse.entryMode</code> attribute **/
	public static final String ENTRYMODE = "entryMode";
	/** Qualifier of the <code>LinkToPayCayanResponse.transactionID</code> attribute **/
	public static final String TRANSACTIONID = "transactionID";
	/** Qualifier of the <code>LinkToPayCayanResponse.cardholder</code> attribute **/
	public static final String CARDHOLDER = "cardholder";
	/** Qualifier of the <code>LinkToPayCayanResponse.authCode</code> attribute **/
	public static final String AUTHCODE = "authCode";
	/** Qualifier of the <code>LinkToPayCayanResponse.address</code> attribute **/
	public static final String ADDRESS = "address";
	/** Qualifier of the <code>LinkToPayCayanResponse.last4Digits</code> attribute **/
	public static final String LAST4DIGITS = "last4Digits";
	/** Qualifier of the <code>LinkToPayCayanResponse.creditCardZip</code> attribute **/
	public static final String CREDITCARDZIP = "creditCardZip";
	/** Qualifier of the <code>LinkToPayCayanResponse.errorMessage</code> attribute **/
	public static final String ERRORMESSAGE = "errorMessage";
	/** Qualifier of the <code>LinkToPayCayanResponse.AVS</code> attribute **/
	public static final String AVS = "AVS";
	/** Qualifier of the <code>LinkToPayCayanResponse.CVV</code> attribute **/
	public static final String CVV = "CVV";
	/** Qualifier of the <code>LinkToPayCayanResponse.token</code> attribute **/
	public static final String TOKEN = "token";
	/** Qualifier of the <code>LinkToPayCayanResponse.aid</code> attribute **/
	public static final String AID = "aid";
	/** Qualifier of the <code>LinkToPayCayanResponse.applicationLabel</code> attribute **/
	public static final String APPLICATIONLABEL = "applicationLabel";
	/** Qualifier of the <code>LinkToPayCayanResponse.pinStatement</code> attribute **/
	public static final String PINSTATEMENT = "pinStatement";
	/** Qualifier of the <code>LinkToPayCayanResponse.transactionReferenceNumber</code> attribute **/
	public static final String TRANSACTIONREFERENCENUMBER = "transactionReferenceNumber";
	/** Qualifier of the <code>LinkToPayCayanResponse.declineCode</code> attribute **/
	public static final String DECLINECODE = "declineCode";
	/** Qualifier of the <code>LinkToPayCayanResponse.correlationID</code> attribute **/
	public static final String CORRELATIONID = "correlationID";
	/** Qualifier of the <code>LinkToPayCayanResponse.externalSystemId</code> attribute **/
	public static final String EXTERNALSYSTEMID = "externalSystemId";
	/** Qualifier of the <code>LinkToPayCayanResponse.amountCharged</code> attribute **/
	public static final String AMOUNTCHARGED = "amountCharged";
	/** Qualifier of the <code>LinkToPayCayanResponse.validationKey</code> attribute **/
	public static final String VALIDATIONKEY = "validationKey";
	/** Qualifier of the <code>LinkToPayCayanResponse.orderNumber</code> attribute **/
	public static final String ORDERNUMBER = "orderNumber";
	/** Qualifier of the <code>LinkToPayCayanResponse.orderAmount</code> attribute **/
	public static final String ORDERAMOUNT = "orderAmount";
	/** Qualifier of the <code>LinkToPayCayanResponse.date</code> attribute **/
	public static final String DATE = "date";
	/** Qualifier of the <code>LinkToPayCayanResponse.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	/** Qualifier of the <code>LinkToPayCayanResponse.poNumber</code> attribute **/
	public static final String PONUMBER = "poNumber";
	/** Qualifier of the <code>LinkToPayCayanResponse.email</code> attribute **/
	public static final String EMAIL = "email";
	/** Qualifier of the <code>LinkToPayCayanResponse.cartID</code> attribute **/
	public static final String CARTID = "cartID";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		tmp.put(SITE, AttributeMode.INITIAL);
		tmp.put(LANGUAGE, AttributeMode.INITIAL);
		tmp.put(TRANSACTIONSTATUS, AttributeMode.INITIAL);
		tmp.put(CREDITCARDTYPE, AttributeMode.INITIAL);
		tmp.put(EXPDATE, AttributeMode.INITIAL);
		tmp.put(ENTRYMODE, AttributeMode.INITIAL);
		tmp.put(TRANSACTIONID, AttributeMode.INITIAL);
		tmp.put(CARDHOLDER, AttributeMode.INITIAL);
		tmp.put(AUTHCODE, AttributeMode.INITIAL);
		tmp.put(ADDRESS, AttributeMode.INITIAL);
		tmp.put(LAST4DIGITS, AttributeMode.INITIAL);
		tmp.put(CREDITCARDZIP, AttributeMode.INITIAL);
		tmp.put(ERRORMESSAGE, AttributeMode.INITIAL);
		tmp.put(AVS, AttributeMode.INITIAL);
		tmp.put(CVV, AttributeMode.INITIAL);
		tmp.put(TOKEN, AttributeMode.INITIAL);
		tmp.put(AID, AttributeMode.INITIAL);
		tmp.put(APPLICATIONLABEL, AttributeMode.INITIAL);
		tmp.put(PINSTATEMENT, AttributeMode.INITIAL);
		tmp.put(TRANSACTIONREFERENCENUMBER, AttributeMode.INITIAL);
		tmp.put(DECLINECODE, AttributeMode.INITIAL);
		tmp.put(CORRELATIONID, AttributeMode.INITIAL);
		tmp.put(EXTERNALSYSTEMID, AttributeMode.INITIAL);
		tmp.put(AMOUNTCHARGED, AttributeMode.INITIAL);
		tmp.put(VALIDATIONKEY, AttributeMode.INITIAL);
		tmp.put(ORDERNUMBER, AttributeMode.INITIAL);
		tmp.put(ORDERAMOUNT, AttributeMode.INITIAL);
		tmp.put(DATE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		tmp.put(PONUMBER, AttributeMode.INITIAL);
		tmp.put(EMAIL, AttributeMode.INITIAL);
		tmp.put(CARTID, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.address</code> attribute.
	 * @return the address
	 */
	public String getAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.address</code> attribute.
	 * @return the address
	 */
	public String getAddress()
	{
		return getAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final String value)
	{
		setAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.aid</code> attribute.
	 * @return the aid
	 */
	public String getAid(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.aid</code> attribute.
	 * @return the aid
	 */
	public String getAid()
	{
		return getAid( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.aid</code> attribute. 
	 * @param value the aid
	 */
	public void setAid(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.aid</code> attribute. 
	 * @param value the aid
	 */
	public void setAid(final String value)
	{
		setAid( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.amountCharged</code> attribute.
	 * @return the amountCharged
	 */
	public String getAmountCharged(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMOUNTCHARGED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.amountCharged</code> attribute.
	 * @return the amountCharged
	 */
	public String getAmountCharged()
	{
		return getAmountCharged( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.amountCharged</code> attribute. 
	 * @param value the amountCharged
	 */
	public void setAmountCharged(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMOUNTCHARGED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.amountCharged</code> attribute. 
	 * @param value the amountCharged
	 */
	public void setAmountCharged(final String value)
	{
		setAmountCharged( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.applicationLabel</code> attribute.
	 * @return the applicationLabel
	 */
	public String getApplicationLabel(final SessionContext ctx)
	{
		return (String)getProperty( ctx, APPLICATIONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.applicationLabel</code> attribute.
	 * @return the applicationLabel
	 */
	public String getApplicationLabel()
	{
		return getApplicationLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.applicationLabel</code> attribute. 
	 * @param value the applicationLabel
	 */
	public void setApplicationLabel(final SessionContext ctx, final String value)
	{
		setProperty(ctx, APPLICATIONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.applicationLabel</code> attribute. 
	 * @param value the applicationLabel
	 */
	public void setApplicationLabel(final String value)
	{
		setApplicationLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.authCode</code> attribute.
	 * @return the authCode
	 */
	public String getAuthCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AUTHCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.authCode</code> attribute.
	 * @return the authCode
	 */
	public String getAuthCode()
	{
		return getAuthCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.authCode</code> attribute. 
	 * @param value the authCode
	 */
	public void setAuthCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AUTHCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.authCode</code> attribute. 
	 * @param value the authCode
	 */
	public void setAuthCode(final String value)
	{
		setAuthCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.AVS</code> attribute.
	 * @return the AVS
	 */
	public String getAVS(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AVS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.AVS</code> attribute.
	 * @return the AVS
	 */
	public String getAVS()
	{
		return getAVS( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.AVS</code> attribute. 
	 * @param value the AVS
	 */
	public void setAVS(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AVS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.AVS</code> attribute. 
	 * @param value the AVS
	 */
	public void setAVS(final String value)
	{
		setAVS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.cardholder</code> attribute.
	 * @return the cardholder
	 */
	public String getCardholder(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDHOLDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.cardholder</code> attribute.
	 * @return the cardholder
	 */
	public String getCardholder()
	{
		return getCardholder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.cardholder</code> attribute. 
	 * @param value the cardholder
	 */
	public void setCardholder(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDHOLDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.cardholder</code> attribute. 
	 * @param value the cardholder
	 */
	public void setCardholder(final String value)
	{
		setCardholder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.cartID</code> attribute.
	 * @return the cartID
	 */
	public String getCartID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.cartID</code> attribute.
	 * @return the cartID
	 */
	public String getCartID()
	{
		return getCartID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.cartID</code> attribute. 
	 * @param value the cartID
	 */
	public void setCartID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.cartID</code> attribute. 
	 * @param value the cartID
	 */
	public void setCartID(final String value)
	{
		setCartID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.correlationID</code> attribute.
	 * @return the correlationID
	 */
	public String getCorrelationID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CORRELATIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.correlationID</code> attribute.
	 * @return the correlationID
	 */
	public String getCorrelationID()
	{
		return getCorrelationID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.correlationID</code> attribute. 
	 * @param value the correlationID
	 */
	public void setCorrelationID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CORRELATIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.correlationID</code> attribute. 
	 * @param value the correlationID
	 */
	public void setCorrelationID(final String value)
	{
		setCorrelationID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.creditCardType</code> attribute.
	 * @return the creditCardType
	 */
	public String getCreditCardType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.creditCardType</code> attribute.
	 * @return the creditCardType
	 */
	public String getCreditCardType()
	{
		return getCreditCardType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.creditCardType</code> attribute. 
	 * @param value the creditCardType
	 */
	public void setCreditCardType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.creditCardType</code> attribute. 
	 * @param value the creditCardType
	 */
	public void setCreditCardType(final String value)
	{
		setCreditCardType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.creditCardZip</code> attribute.
	 * @return the creditCardZip
	 */
	public String getCreditCardZip(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDZIP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.creditCardZip</code> attribute.
	 * @return the creditCardZip
	 */
	public String getCreditCardZip()
	{
		return getCreditCardZip( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.creditCardZip</code> attribute. 
	 * @param value the creditCardZip
	 */
	public void setCreditCardZip(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDZIP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.creditCardZip</code> attribute. 
	 * @param value the creditCardZip
	 */
	public void setCreditCardZip(final String value)
	{
		setCreditCardZip( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.customerName</code> attribute.
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.customerName</code> attribute. 
	 * @param value the customerName
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.CVV</code> attribute.
	 * @return the CVV
	 */
	public String getCVV(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CVV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.CVV</code> attribute.
	 * @return the CVV
	 */
	public String getCVV()
	{
		return getCVV( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.CVV</code> attribute. 
	 * @param value the CVV
	 */
	public void setCVV(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CVV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.CVV</code> attribute. 
	 * @param value the CVV
	 */
	public void setCVV(final String value)
	{
		setCVV( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.date</code> attribute.
	 * @return the date
	 */
	public String getDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.date</code> attribute.
	 * @return the date
	 */
	public String getDate()
	{
		return getDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.date</code> attribute. 
	 * @param value the date
	 */
	public void setDate(final String value)
	{
		setDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.declineCode</code> attribute.
	 * @return the declineCode
	 */
	public String getDeclineCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DECLINECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.declineCode</code> attribute.
	 * @return the declineCode
	 */
	public String getDeclineCode()
	{
		return getDeclineCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.declineCode</code> attribute. 
	 * @param value the declineCode
	 */
	public void setDeclineCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DECLINECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.declineCode</code> attribute. 
	 * @param value the declineCode
	 */
	public void setDeclineCode(final String value)
	{
		setDeclineCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.email</code> attribute.
	 * @return the email
	 */
	public String getEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.email</code> attribute.
	 * @return the email
	 */
	public String getEmail()
	{
		return getEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final String value)
	{
		setEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.entryMode</code> attribute.
	 * @return the entryMode
	 */
	public String getEntryMode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ENTRYMODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.entryMode</code> attribute.
	 * @return the entryMode
	 */
	public String getEntryMode()
	{
		return getEntryMode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.entryMode</code> attribute. 
	 * @param value the entryMode
	 */
	public void setEntryMode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ENTRYMODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.entryMode</code> attribute. 
	 * @param value the entryMode
	 */
	public void setEntryMode(final String value)
	{
		setEntryMode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.errorMessage</code> attribute.
	 * @return the errorMessage
	 */
	public String getErrorMessage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ERRORMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.errorMessage</code> attribute.
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return getErrorMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.errorMessage</code> attribute. 
	 * @param value the errorMessage
	 */
	public void setErrorMessage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ERRORMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.errorMessage</code> attribute. 
	 * @param value the errorMessage
	 */
	public void setErrorMessage(final String value)
	{
		setErrorMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return getExpDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final String value)
	{
		setExpDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.externalSystemId</code> attribute.
	 * @return the externalSystemId
	 */
	public String getExternalSystemId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXTERNALSYSTEMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.externalSystemId</code> attribute.
	 * @return the externalSystemId
	 */
	public String getExternalSystemId()
	{
		return getExternalSystemId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.externalSystemId</code> attribute. 
	 * @param value the externalSystemId
	 */
	public void setExternalSystemId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXTERNALSYSTEMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.externalSystemId</code> attribute. 
	 * @param value the externalSystemId
	 */
	public void setExternalSystemId(final String value)
	{
		setExternalSystemId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.language</code> attribute.
	 * @return the language
	 */
	public Language getLanguage(final SessionContext ctx)
	{
		return (Language)getProperty( ctx, LANGUAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.language</code> attribute.
	 * @return the language
	 */
	public Language getLanguage()
	{
		return getLanguage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.language</code> attribute. 
	 * @param value the language
	 */
	public void setLanguage(final SessionContext ctx, final Language value)
	{
		setProperty(ctx, LANGUAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.language</code> attribute. 
	 * @param value the language
	 */
	public void setLanguage(final Language value)
	{
		setLanguage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.last4Digits</code> attribute.
	 * @return the last4Digits
	 */
	public String getLast4Digits(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LAST4DIGITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.last4Digits</code> attribute.
	 * @return the last4Digits
	 */
	public String getLast4Digits()
	{
		return getLast4Digits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.last4Digits</code> attribute. 
	 * @param value the last4Digits
	 */
	public void setLast4Digits(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LAST4DIGITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.last4Digits</code> attribute. 
	 * @param value the last4Digits
	 */
	public void setLast4Digits(final String value)
	{
		setLast4Digits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.orderAmount</code> attribute.
	 * @return the orderAmount
	 */
	public String getOrderAmount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.orderAmount</code> attribute.
	 * @return the orderAmount
	 */
	public String getOrderAmount()
	{
		return getOrderAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.orderAmount</code> attribute. 
	 * @param value the orderAmount
	 */
	public void setOrderAmount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.orderAmount</code> attribute. 
	 * @param value the orderAmount
	 */
	public void setOrderAmount(final String value)
	{
		setOrderAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber()
	{
		return getOrderNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final String value)
	{
		setOrderNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.pinStatement</code> attribute.
	 * @return the pinStatement
	 */
	public String getPinStatement(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PINSTATEMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.pinStatement</code> attribute.
	 * @return the pinStatement
	 */
	public String getPinStatement()
	{
		return getPinStatement( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.pinStatement</code> attribute. 
	 * @param value the pinStatement
	 */
	public void setPinStatement(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PINSTATEMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.pinStatement</code> attribute. 
	 * @param value the pinStatement
	 */
	public void setPinStatement(final String value)
	{
		setPinStatement( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PONUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return getPoNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PONUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final String value)
	{
		setPoNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.site</code> attribute.
	 * @return the site
	 */
	public BaseSite getSite(final SessionContext ctx)
	{
		return (BaseSite)getProperty( ctx, SITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.site</code> attribute.
	 * @return the site
	 */
	public BaseSite getSite()
	{
		return getSite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.site</code> attribute. 
	 * @param value the site
	 */
	public void setSite(final SessionContext ctx, final BaseSite value)
	{
		setProperty(ctx, SITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.site</code> attribute. 
	 * @param value the site
	 */
	public void setSite(final BaseSite value)
	{
		setSite( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.token</code> attribute.
	 * @return the token
	 */
	public String getToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.token</code> attribute.
	 * @return the token
	 */
	public String getToken()
	{
		return getToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.token</code> attribute. 
	 * @param value the token
	 */
	public void setToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.token</code> attribute. 
	 * @param value the token
	 */
	public void setToken(final String value)
	{
		setToken( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.transactionID</code> attribute.
	 * @return the transactionID
	 */
	public String getTransactionID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TRANSACTIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.transactionID</code> attribute.
	 * @return the transactionID
	 */
	public String getTransactionID()
	{
		return getTransactionID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.transactionID</code> attribute. 
	 * @param value the transactionID
	 */
	public void setTransactionID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TRANSACTIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.transactionID</code> attribute. 
	 * @param value the transactionID
	 */
	public void setTransactionID(final String value)
	{
		setTransactionID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.transactionReferenceNumber</code> attribute.
	 * @return the transactionReferenceNumber
	 */
	public String getTransactionReferenceNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TRANSACTIONREFERENCENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.transactionReferenceNumber</code> attribute.
	 * @return the transactionReferenceNumber
	 */
	public String getTransactionReferenceNumber()
	{
		return getTransactionReferenceNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.transactionReferenceNumber</code> attribute. 
	 * @param value the transactionReferenceNumber
	 */
	public void setTransactionReferenceNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TRANSACTIONREFERENCENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.transactionReferenceNumber</code> attribute. 
	 * @param value the transactionReferenceNumber
	 */
	public void setTransactionReferenceNumber(final String value)
	{
		setTransactionReferenceNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.transactionStatus</code> attribute.
	 * @return the transactionStatus
	 */
	public String getTransactionStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TRANSACTIONSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.transactionStatus</code> attribute.
	 * @return the transactionStatus
	 */
	public String getTransactionStatus()
	{
		return getTransactionStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.transactionStatus</code> attribute. 
	 * @param value the transactionStatus
	 */
	public void setTransactionStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TRANSACTIONSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.transactionStatus</code> attribute. 
	 * @param value the transactionStatus
	 */
	public void setTransactionStatus(final String value)
	{
		setTransactionStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.validationKey</code> attribute.
	 * @return the validationKey
	 */
	public String getValidationKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VALIDATIONKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayCayanResponse.validationKey</code> attribute.
	 * @return the validationKey
	 */
	public String getValidationKey()
	{
		return getValidationKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.validationKey</code> attribute. 
	 * @param value the validationKey
	 */
	public void setValidationKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VALIDATIONKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayCayanResponse.validationKey</code> attribute. 
	 * @param value the validationKey
	 */
	public void setValidationKey(final String value)
	{
		setValidationKey( getSession().getSessionContext(), value );
	}
	
}
