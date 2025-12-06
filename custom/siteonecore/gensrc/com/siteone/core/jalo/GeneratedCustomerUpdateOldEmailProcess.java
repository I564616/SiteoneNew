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
 * Generated class for type {@link com.siteone.core.jalo.CustomerUpdateOldEmailProcess CustomerUpdateOldEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedCustomerUpdateOldEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>CustomerUpdateOldEmailProcess.oldEmail</code> attribute **/
	public static final String OLDEMAIL = "oldEmail";
	/** Qualifier of the <code>CustomerUpdateOldEmailProcess.newEmail</code> attribute **/
	public static final String NEWEMAIL = "newEmail";
	/** Qualifier of the <code>CustomerUpdateOldEmailProcess.firstName</code> attribute **/
	public static final String FIRSTNAME = "firstName";
	/** Qualifier of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute **/
	public static final String EXPIRATIONTIMEINSECONDS = "expirationTimeInSeconds";
	/** Qualifier of the <code>CustomerUpdateOldEmailProcess.token</code> attribute **/
	public static final String TOKEN = "token";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(OLDEMAIL, AttributeMode.INITIAL);
		tmp.put(NEWEMAIL, AttributeMode.INITIAL);
		tmp.put(FIRSTNAME, AttributeMode.INITIAL);
		tmp.put(EXPIRATIONTIMEINSECONDS, AttributeMode.INITIAL);
		tmp.put(TOKEN, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute.
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public Long getExpirationTimeInSeconds(final SessionContext ctx)
	{
		return (Long)getProperty( ctx, EXPIRATIONTIMEINSECONDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute.
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public Long getExpirationTimeInSeconds()
	{
		return getExpirationTimeInSeconds( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public long getExpirationTimeInSecondsAsPrimitive(final SessionContext ctx)
	{
		Long value = getExpirationTimeInSeconds( ctx );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public long getExpirationTimeInSecondsAsPrimitive()
	{
		return getExpirationTimeInSecondsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final SessionContext ctx, final Long value)
	{
		setProperty(ctx, EXPIRATIONTIMEINSECONDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final Long value)
	{
		setExpirationTimeInSeconds( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final SessionContext ctx, final long value)
	{
		setExpirationTimeInSeconds( ctx,Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final long value)
	{
		setExpirationTimeInSeconds( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getFirstName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final String value)
	{
		setFirstName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.newEmail</code> attribute.
	 * @return the newEmail - Attribute contains new email id that is used in this process.
	 */
	public String getNewEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NEWEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.newEmail</code> attribute.
	 * @return the newEmail - Attribute contains new email id that is used in this process.
	 */
	public String getNewEmail()
	{
		return getNewEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.newEmail</code> attribute. 
	 * @param value the newEmail - Attribute contains new email id that is used in this process.
	 */
	public void setNewEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NEWEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.newEmail</code> attribute. 
	 * @param value the newEmail - Attribute contains new email id that is used in this process.
	 */
	public void setNewEmail(final String value)
	{
		setNewEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.oldEmail</code> attribute.
	 * @return the oldEmail - Attribute contains old email id that is used in this process.
	 */
	public String getOldEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, OLDEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.oldEmail</code> attribute.
	 * @return the oldEmail - Attribute contains old email id that is used in this process.
	 */
	public String getOldEmail()
	{
		return getOldEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.oldEmail</code> attribute. 
	 * @param value the oldEmail - Attribute contains old email id that is used in this process.
	 */
	public void setOldEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, OLDEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.oldEmail</code> attribute. 
	 * @param value the oldEmail - Attribute contains old email id that is used in this process.
	 */
	public void setOldEmail(final String value)
	{
		setOldEmail( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.token</code> attribute.
	 * @return the token - Attribute token that is used in this process.
	 */
	public String getToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerUpdateOldEmailProcess.token</code> attribute.
	 * @return the token - Attribute token that is used in this process.
	 */
	public String getToken()
	{
		return getToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.token</code> attribute. 
	 * @param value the token - Attribute token that is used in this process.
	 */
	public void setToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerUpdateOldEmailProcess.token</code> attribute. 
	 * @param value the token - Attribute token that is used in this process.
	 */
	public void setToken(final String value)
	{
		setToken( getSession().getSessionContext(), value );
	}
	
}
