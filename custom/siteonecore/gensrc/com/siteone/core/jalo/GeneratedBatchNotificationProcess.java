/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.impex.jalo.cronjob.ImpExImportCronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.BatchNotificationProcess BatchNotificationProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedBatchNotificationProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>BatchNotificationProcess.fileName</code> attribute **/
	public static final String FILENAME = "fileName";
	/** Qualifier of the <code>BatchNotificationProcess.emailReceiver</code> attribute **/
	public static final String EMAILRECEIVER = "emailReceiver";
	/** Qualifier of the <code>BatchNotificationProcess.importCronjob</code> attribute **/
	public static final String IMPORTCRONJOB = "importCronjob";
	/** Qualifier of the <code>BatchNotificationProcess.impexTransformerLog</code> attribute **/
	public static final String IMPEXTRANSFORMERLOG = "impexTransformerLog";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FILENAME, AttributeMode.INITIAL);
		tmp.put(EMAILRECEIVER, AttributeMode.INITIAL);
		tmp.put(IMPORTCRONJOB, AttributeMode.INITIAL);
		tmp.put(IMPEXTRANSFORMERLOG, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.emailReceiver</code> attribute.
	 * @return the emailReceiver
	 */
	public String getEmailReceiver(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILRECEIVER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.emailReceiver</code> attribute.
	 * @return the emailReceiver
	 */
	public String getEmailReceiver()
	{
		return getEmailReceiver( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.emailReceiver</code> attribute. 
	 * @param value the emailReceiver
	 */
	public void setEmailReceiver(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILRECEIVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.emailReceiver</code> attribute. 
	 * @param value the emailReceiver
	 */
	public void setEmailReceiver(final String value)
	{
		setEmailReceiver( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.fileName</code> attribute.
	 * @return the fileName
	 */
	public String getFileName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.fileName</code> attribute.
	 * @return the fileName
	 */
	public String getFileName()
	{
		return getFileName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.fileName</code> attribute. 
	 * @param value the fileName
	 */
	public void setFileName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.fileName</code> attribute. 
	 * @param value the fileName
	 */
	public void setFileName(final String value)
	{
		setFileName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.impexTransformerLog</code> attribute.
	 * @return the impexTransformerLog
	 */
	public String getImpexTransformerLog(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IMPEXTRANSFORMERLOG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.impexTransformerLog</code> attribute.
	 * @return the impexTransformerLog
	 */
	public String getImpexTransformerLog()
	{
		return getImpexTransformerLog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.impexTransformerLog</code> attribute. 
	 * @param value the impexTransformerLog
	 */
	public void setImpexTransformerLog(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IMPEXTRANSFORMERLOG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.impexTransformerLog</code> attribute. 
	 * @param value the impexTransformerLog
	 */
	public void setImpexTransformerLog(final String value)
	{
		setImpexTransformerLog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.importCronjob</code> attribute.
	 * @return the importCronjob
	 */
	public ImpExImportCronJob getImportCronjob(final SessionContext ctx)
	{
		return (ImpExImportCronJob)getProperty( ctx, IMPORTCRONJOB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BatchNotificationProcess.importCronjob</code> attribute.
	 * @return the importCronjob
	 */
	public ImpExImportCronJob getImportCronjob()
	{
		return getImportCronjob( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.importCronjob</code> attribute. 
	 * @param value the importCronjob
	 */
	public void setImportCronjob(final SessionContext ctx, final ImpExImportCronJob value)
	{
		setProperty(ctx, IMPORTCRONJOB,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BatchNotificationProcess.importCronjob</code> attribute. 
	 * @param value the importCronjob
	 */
	public void setImportCronjob(final ImpExImportCronJob value)
	{
		setImportCronjob( getSession().getSessionContext(), value );
	}
	
}
