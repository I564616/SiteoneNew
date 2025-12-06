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
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.PIMReportMessage PIMReportMessage}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPIMReportMessage extends GenericItem
{
	/** Qualifier of the <code>PIMReportMessage.generatedErrorMessage</code> attribute **/
	public static final String GENERATEDERRORMESSAGE = "generatedErrorMessage";
	/** Qualifier of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute **/
	public static final String USERFRIENDLYERRORMESSAGE = "userFriendlyErrorMessage";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(GENERATEDERRORMESSAGE, AttributeMode.INITIAL);
		tmp.put(USERFRIENDLYERRORMESSAGE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute.
	 * @return the generatedErrorMessage
	 */
	public String getGeneratedErrorMessage(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPIMReportMessage.getGeneratedErrorMessage requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, GENERATEDERRORMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute.
	 * @return the generatedErrorMessage
	 */
	public String getGeneratedErrorMessage()
	{
		return getGeneratedErrorMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute. 
	 * @return the localized generatedErrorMessage
	 */
	public Map<Language,String> getAllGeneratedErrorMessage(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,GENERATEDERRORMESSAGE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute. 
	 * @return the localized generatedErrorMessage
	 */
	public Map<Language,String> getAllGeneratedErrorMessage()
	{
		return getAllGeneratedErrorMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute. 
	 * @param value the generatedErrorMessage
	 */
	public void setGeneratedErrorMessage(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPIMReportMessage.setGeneratedErrorMessage requires a session language", 0 );
		}
		setLocalizedProperty(ctx, GENERATEDERRORMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute. 
	 * @param value the generatedErrorMessage
	 */
	public void setGeneratedErrorMessage(final String value)
	{
		setGeneratedErrorMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute. 
	 * @param value the generatedErrorMessage
	 */
	public void setAllGeneratedErrorMessage(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,GENERATEDERRORMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.generatedErrorMessage</code> attribute. 
	 * @param value the generatedErrorMessage
	 */
	public void setAllGeneratedErrorMessage(final Map<Language,String> value)
	{
		setAllGeneratedErrorMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute.
	 * @return the userFriendlyErrorMessage
	 */
	public String getUserFriendlyErrorMessage(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPIMReportMessage.getUserFriendlyErrorMessage requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, USERFRIENDLYERRORMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute.
	 * @return the userFriendlyErrorMessage
	 */
	public String getUserFriendlyErrorMessage()
	{
		return getUserFriendlyErrorMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute. 
	 * @return the localized userFriendlyErrorMessage
	 */
	public Map<Language,String> getAllUserFriendlyErrorMessage(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,USERFRIENDLYERRORMESSAGE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute. 
	 * @return the localized userFriendlyErrorMessage
	 */
	public Map<Language,String> getAllUserFriendlyErrorMessage()
	{
		return getAllUserFriendlyErrorMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute. 
	 * @param value the userFriendlyErrorMessage
	 */
	public void setUserFriendlyErrorMessage(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPIMReportMessage.setUserFriendlyErrorMessage requires a session language", 0 );
		}
		setLocalizedProperty(ctx, USERFRIENDLYERRORMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute. 
	 * @param value the userFriendlyErrorMessage
	 */
	public void setUserFriendlyErrorMessage(final String value)
	{
		setUserFriendlyErrorMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute. 
	 * @param value the userFriendlyErrorMessage
	 */
	public void setAllUserFriendlyErrorMessage(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,USERFRIENDLYERRORMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMReportMessage.userFriendlyErrorMessage</code> attribute. 
	 * @param value the userFriendlyErrorMessage
	 */
	public void setAllUserFriendlyErrorMessage(final Map<Language,String> value)
	{
		setAllUserFriendlyErrorMessage( getSession().getSessionContext(), value );
	}
	
}
