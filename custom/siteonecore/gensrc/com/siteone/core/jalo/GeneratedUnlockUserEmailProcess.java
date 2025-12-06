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
 * Generated class for type {@link com.siteone.core.jalo.UnlockUserEmailProcess UnlockUserEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedUnlockUserEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>UnlockUserEmailProcess.token</code> attribute **/
	public static final String TOKEN = "token";
	/** Qualifier of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute **/
	public static final String EXPIRATIONTIMEINSECONDS = "expirationTimeInSeconds";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TOKEN, AttributeMode.INITIAL);
		tmp.put(EXPIRATIONTIMEINSECONDS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute.
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public Long getExpirationTimeInSeconds(final SessionContext ctx)
	{
		return (Long)getProperty( ctx, EXPIRATIONTIMEINSECONDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute.
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public Long getExpirationTimeInSeconds()
	{
		return getExpirationTimeInSeconds( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public long getExpirationTimeInSecondsAsPrimitive(final SessionContext ctx)
	{
		Long value = getExpirationTimeInSeconds( ctx );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public long getExpirationTimeInSecondsAsPrimitive()
	{
		return getExpirationTimeInSecondsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final SessionContext ctx, final Long value)
	{
		setProperty(ctx, EXPIRATIONTIMEINSECONDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final Long value)
	{
		setExpirationTimeInSeconds( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final SessionContext ctx, final long value)
	{
		setExpirationTimeInSeconds( ctx,Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UnlockUserEmailProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final long value)
	{
		setExpirationTimeInSeconds( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UnlockUserEmailProcess.token</code> attribute.
	 * @return the token - Attribute contains token that is used in this process.
	 */
	public String getToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UnlockUserEmailProcess.token</code> attribute.
	 * @return the token - Attribute contains token that is used in this process.
	 */
	public String getToken()
	{
		return getToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UnlockUserEmailProcess.token</code> attribute. 
	 * @param value the token - Attribute contains token that is used in this process.
	 */
	public void setToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UnlockUserEmailProcess.token</code> attribute. 
	 * @param value the token - Attribute contains token that is used in this process.
	 */
	public void setToken(final String value)
	{
		setToken( getSession().getSessionContext(), value );
	}
	
}
