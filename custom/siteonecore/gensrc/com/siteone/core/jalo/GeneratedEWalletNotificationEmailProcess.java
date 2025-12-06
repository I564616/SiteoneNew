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
 * Generated class for type {@link com.siteone.core.jalo.EWalletNotificationEmailProcess EWalletNotificationEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedEWalletNotificationEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>EWalletNotificationEmailProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>EWalletNotificationEmailProcess.cardNumber</code> attribute **/
	public static final String CARDNUMBER = "cardNumber";
	/** Qualifier of the <code>EWalletNotificationEmailProcess.cardType</code> attribute **/
	public static final String CARDTYPE = "cardType";
	/** Qualifier of the <code>EWalletNotificationEmailProcess.nickName</code> attribute **/
	public static final String NICKNAME = "nickName";
	/** Qualifier of the <code>EWalletNotificationEmailProcess.operationType</code> attribute **/
	public static final String OPERATIONTYPE = "operationType";
	/** Qualifier of the <code>EWalletNotificationEmailProcess.customerEmail</code> attribute **/
	public static final String CUSTOMEREMAIL = "customerEmail";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(CARDNUMBER, AttributeMode.INITIAL);
		tmp.put(CARDTYPE, AttributeMode.INITIAL);
		tmp.put(NICKNAME, AttributeMode.INITIAL);
		tmp.put(OPERATIONTYPE, AttributeMode.INITIAL);
		tmp.put(CUSTOMEREMAIL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.cardNumber</code> attribute.
	 * @return the cardNumber
	 */
	public String getCardNumber(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.cardNumber</code> attribute.
	 * @return the cardNumber
	 */
	public String getCardNumber()
	{
		return getCardNumber( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.cardNumber</code> attribute. 
	 * @param value the cardNumber
	 */
	public void setCardNumber(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.cardNumber</code> attribute. 
	 * @param value the cardNumber
	 */
	public void setCardNumber(final String value)
	{
		setCardNumber( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.cardType</code> attribute.
	 * @return the cardType
	 */
	public String getCardType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CARDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.cardType</code> attribute.
	 * @return the cardType
	 */
	public String getCardType()
	{
		return getCardType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.cardType</code> attribute. 
	 * @param value the cardType
	 */
	public void setCardType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CARDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.cardType</code> attribute. 
	 * @param value the cardType
	 */
	public void setCardType(final String value)
	{
		setCardType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.customerEmail</code> attribute.
	 * @return the customerEmail
	 */
	public String getCustomerEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTOMEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.customerEmail</code> attribute.
	 * @return the customerEmail
	 */
	public String getCustomerEmail()
	{
		return getCustomerEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.customerEmail</code> attribute. 
	 * @param value the customerEmail
	 */
	public void setCustomerEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTOMEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.customerEmail</code> attribute. 
	 * @param value the customerEmail
	 */
	public void setCustomerEmail(final String value)
	{
		setCustomerEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.nickName</code> attribute.
	 * @return the nickName
	 */
	public String getNickName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NICKNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.nickName</code> attribute.
	 * @return the nickName
	 */
	public String getNickName()
	{
		return getNickName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.nickName</code> attribute. 
	 * @param value the nickName
	 */
	public void setNickName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NICKNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.nickName</code> attribute. 
	 * @param value the nickName
	 */
	public void setNickName(final String value)
	{
		setNickName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.operationType</code> attribute.
	 * @return the operationType
	 */
	public String getOperationType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, OPERATIONTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EWalletNotificationEmailProcess.operationType</code> attribute.
	 * @return the operationType
	 */
	public String getOperationType()
	{
		return getOperationType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.operationType</code> attribute. 
	 * @param value the operationType
	 */
	public void setOperationType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, OPERATIONTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EWalletNotificationEmailProcess.operationType</code> attribute. 
	 * @param value the operationType
	 */
	public void setOperationType(final String value)
	{
		setOperationType( getSession().getSessionContext(), value );
	}
	
}
