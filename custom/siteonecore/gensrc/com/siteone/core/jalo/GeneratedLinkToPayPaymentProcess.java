/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.LinkToPayCayanResponse;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.processengine.jalo.BusinessProcess;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.processengine.jalo.BusinessProcess LinkToPayPaymentProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedLinkToPayPaymentProcess extends BusinessProcess
{
	/** Qualifier of the <code>LinkToPayPaymentProcess.logMessage</code> attribute **/
	public static final String LOGMESSAGE = "logMessage";
	/** Qualifier of the <code>LinkToPayPaymentProcess.cayanResponseForm</code> attribute **/
	public static final String CAYANRESPONSEFORM = "cayanResponseForm";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(BusinessProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LOGMESSAGE, AttributeMode.INITIAL);
		tmp.put(CAYANRESPONSEFORM, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPaymentProcess.cayanResponseForm</code> attribute.
	 * @return the cayanResponseForm
	 */
	public LinkToPayCayanResponse getCayanResponseForm(final SessionContext ctx)
	{
		return (LinkToPayCayanResponse)getProperty( ctx, CAYANRESPONSEFORM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPaymentProcess.cayanResponseForm</code> attribute.
	 * @return the cayanResponseForm
	 */
	public LinkToPayCayanResponse getCayanResponseForm()
	{
		return getCayanResponseForm( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPaymentProcess.cayanResponseForm</code> attribute. 
	 * @param value the cayanResponseForm
	 */
	public void setCayanResponseForm(final SessionContext ctx, final LinkToPayCayanResponse value)
	{
		setProperty(ctx, CAYANRESPONSEFORM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPaymentProcess.cayanResponseForm</code> attribute. 
	 * @param value the cayanResponseForm
	 */
	public void setCayanResponseForm(final LinkToPayCayanResponse value)
	{
		setCayanResponseForm( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPaymentProcess.logMessage</code> attribute.
	 * @return the logMessage
	 */
	public String getLogMessage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOGMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkToPayPaymentProcess.logMessage</code> attribute.
	 * @return the logMessage
	 */
	public String getLogMessage()
	{
		return getLogMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPaymentProcess.logMessage</code> attribute. 
	 * @param value the logMessage
	 */
	public void setLogMessage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOGMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkToPayPaymentProcess.logMessage</code> attribute. 
	 * @param value the logMessage
	 */
	public void setLogMessage(final String value)
	{
		setLogMessage( getSession().getSessionContext(), value );
	}
	
}
