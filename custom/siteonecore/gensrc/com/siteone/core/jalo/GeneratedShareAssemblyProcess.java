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
 * Generated class for type {@link com.siteone.core.jalo.ShareAssemblyProcess ShareAssemblyProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedShareAssemblyProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>ShareAssemblyProcess.assemblyCode</code> attribute **/
	public static final String ASSEMBLYCODE = "assemblyCode";
	/** Qualifier of the <code>ShareAssemblyProcess.emailAddress</code> attribute **/
	public static final String EMAILADDRESS = "emailAddress";
	/** Qualifier of the <code>ShareAssemblyProcess.username</code> attribute **/
	public static final String USERNAME = "username";
	/** Qualifier of the <code>ShareAssemblyProcess.assemblyName</code> attribute **/
	public static final String ASSEMBLYNAME = "assemblyName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ASSEMBLYCODE, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESS, AttributeMode.INITIAL);
		tmp.put(USERNAME, AttributeMode.INITIAL);
		tmp.put(ASSEMBLYNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.assemblyCode</code> attribute.
	 * @return the assemblyCode
	 */
	public String getAssemblyCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASSEMBLYCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.assemblyCode</code> attribute.
	 * @return the assemblyCode
	 */
	public String getAssemblyCode()
	{
		return getAssemblyCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.assemblyCode</code> attribute. 
	 * @param value the assemblyCode
	 */
	public void setAssemblyCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASSEMBLYCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.assemblyCode</code> attribute. 
	 * @param value the assemblyCode
	 */
	public void setAssemblyCode(final String value)
	{
		setAssemblyCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.assemblyName</code> attribute.
	 * @return the assemblyName
	 */
	public String getAssemblyName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASSEMBLYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.assemblyName</code> attribute.
	 * @return the assemblyName
	 */
	public String getAssemblyName()
	{
		return getAssemblyName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.assemblyName</code> attribute. 
	 * @param value the assemblyName
	 */
	public void setAssemblyName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASSEMBLYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.assemblyName</code> attribute. 
	 * @param value the assemblyName
	 */
	public void setAssemblyName(final String value)
	{
		setAssemblyName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.emailAddress</code> attribute.
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return getEmailAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.emailAddress</code> attribute. 
	 * @param value the emailAddress
	 */
	public void setEmailAddress(final String value)
	{
		setEmailAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.username</code> attribute.
	 * @return the username
	 */
	public String getUsername(final SessionContext ctx)
	{
		return (String)getProperty( ctx, USERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ShareAssemblyProcess.username</code> attribute.
	 * @return the username
	 */
	public String getUsername()
	{
		return getUsername( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.username</code> attribute. 
	 * @param value the username
	 */
	public void setUsername(final SessionContext ctx, final String value)
	{
		setProperty(ctx, USERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ShareAssemblyProcess.username</code> attribute. 
	 * @param value the username
	 */
	public void setUsername(final String value)
	{
		setUsername( getSession().getSessionContext(), value );
	}
	
}
