/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo.process;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.process.PimBatchFailureEmailProcess PimBatchFailureEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedPimBatchFailureEmailProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>PimBatchFailureEmailProcess.failedBatch</code> attribute **/
	public static final String FAILEDBATCH = "failedBatch";
	/** Qualifier of the <code>PimBatchFailureEmailProcess.toEmails</code> attribute **/
	public static final String TOEMAILS = "toEmails";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FAILEDBATCH, AttributeMode.INITIAL);
		tmp.put(TOEMAILS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PimBatchFailureEmailProcess.failedBatch</code> attribute.
	 * @return the failedBatch
	 */
	public String getFailedBatch(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FAILEDBATCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PimBatchFailureEmailProcess.failedBatch</code> attribute.
	 * @return the failedBatch
	 */
	public String getFailedBatch()
	{
		return getFailedBatch( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PimBatchFailureEmailProcess.failedBatch</code> attribute. 
	 * @param value the failedBatch
	 */
	public void setFailedBatch(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FAILEDBATCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PimBatchFailureEmailProcess.failedBatch</code> attribute. 
	 * @param value the failedBatch
	 */
	public void setFailedBatch(final String value)
	{
		setFailedBatch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PimBatchFailureEmailProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PimBatchFailureEmailProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails()
	{
		return getToEmails( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PimBatchFailureEmailProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PimBatchFailureEmailProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final String value)
	{
		setToEmails( getSession().getSessionContext(), value );
	}
	
}
