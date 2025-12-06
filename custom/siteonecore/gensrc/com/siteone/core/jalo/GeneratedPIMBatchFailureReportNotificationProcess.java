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
 * Generated class for type {@link com.siteone.core.jalo.PIMBatchFailureReportNotificationProcess PIMBatchFailureReportNotificationProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPIMBatchFailureReportNotificationProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>PIMBatchFailureReportNotificationProcess.emailReceiver</code> attribute **/
	public static final String EMAILRECEIVER = "emailReceiver";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(EMAILRECEIVER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMBatchFailureReportNotificationProcess.emailReceiver</code> attribute.
	 * @return the emailReceiver
	 */
	public String getEmailReceiver(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILRECEIVER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PIMBatchFailureReportNotificationProcess.emailReceiver</code> attribute.
	 * @return the emailReceiver
	 */
	public String getEmailReceiver()
	{
		return getEmailReceiver( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMBatchFailureReportNotificationProcess.emailReceiver</code> attribute. 
	 * @param value the emailReceiver
	 */
	public void setEmailReceiver(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILRECEIVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PIMBatchFailureReportNotificationProcess.emailReceiver</code> attribute. 
	 * @param value the emailReceiver
	 */
	public void setEmailReceiver(final String value)
	{
		setEmailReceiver( getSession().getSessionContext(), value );
	}
	
}
