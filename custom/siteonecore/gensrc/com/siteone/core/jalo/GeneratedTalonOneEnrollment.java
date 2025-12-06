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
 * Generated class for type {@link com.siteone.core.jalo.TalonOneEnrollment TalonOneEnrollment}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedTalonOneEnrollment extends GenericItem
{
	/** Qualifier of the <code>TalonOneEnrollment.divisionId</code> attribute **/
	public static final String DIVISIONID = "divisionId";
	/** Qualifier of the <code>TalonOneEnrollment.custTreeNodeId</code> attribute **/
	public static final String CUSTTREENODEID = "custTreeNodeId";
	/** Qualifier of the <code>TalonOneEnrollment.contactId</code> attribute **/
	public static final String CONTACTID = "contactId";
	/** Qualifier of the <code>TalonOneEnrollment.email</code> attribute **/
	public static final String EMAIL = "email";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(DIVISIONID, AttributeMode.INITIAL);
		tmp.put(CUSTTREENODEID, AttributeMode.INITIAL);
		tmp.put(CONTACTID, AttributeMode.INITIAL);
		tmp.put(EMAIL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.contactId</code> attribute.
	 * @return the contactId
	 */
	public String getContactId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTACTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.contactId</code> attribute.
	 * @return the contactId
	 */
	public String getContactId()
	{
		return getContactId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.contactId</code> attribute. 
	 * @param value the contactId
	 */
	public void setContactId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTACTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.contactId</code> attribute. 
	 * @param value the contactId
	 */
	public void setContactId(final String value)
	{
		setContactId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.custTreeNodeId</code> attribute.
	 * @return the custTreeNodeId
	 */
	public String getCustTreeNodeId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTTREENODEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.custTreeNodeId</code> attribute.
	 * @return the custTreeNodeId
	 */
	public String getCustTreeNodeId()
	{
		return getCustTreeNodeId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.custTreeNodeId</code> attribute. 
	 * @param value the custTreeNodeId
	 */
	public void setCustTreeNodeId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTTREENODEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.custTreeNodeId</code> attribute. 
	 * @param value the custTreeNodeId
	 */
	public void setCustTreeNodeId(final String value)
	{
		setCustTreeNodeId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.divisionId</code> attribute.
	 * @return the divisionId
	 */
	public String getDivisionId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DIVISIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.divisionId</code> attribute.
	 * @return the divisionId
	 */
	public String getDivisionId()
	{
		return getDivisionId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.divisionId</code> attribute. 
	 * @param value the divisionId
	 */
	public void setDivisionId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DIVISIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.divisionId</code> attribute. 
	 * @param value the divisionId
	 */
	public void setDivisionId(final String value)
	{
		setDivisionId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.email</code> attribute.
	 * @return the email
	 */
	public String getEmail(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TalonOneEnrollment.email</code> attribute.
	 * @return the email
	 */
	public String getEmail()
	{
		return getEmail( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TalonOneEnrollment.email</code> attribute. 
	 * @param value the email
	 */
	public void setEmail(final String value)
	{
		setEmail( getSession().getSessionContext(), value );
	}
	
}
