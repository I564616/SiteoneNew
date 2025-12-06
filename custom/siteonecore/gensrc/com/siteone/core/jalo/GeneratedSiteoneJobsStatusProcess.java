/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneJobsStatusProcess SiteoneJobsStatusProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneJobsStatusProcess extends StoreFrontCustomerProcess
{
	/** Qualifier of the <code>SiteoneJobsStatusProcess.siteoneJobsStatusList</code> attribute **/
	public static final String SITEONEJOBSSTATUSLIST = "siteoneJobsStatusList";
	/** Qualifier of the <code>SiteoneJobsStatusProcess.emailReceiver</code> attribute **/
	public static final String EMAILRECEIVER = "emailReceiver";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(StoreFrontCustomerProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(SITEONEJOBSSTATUSLIST, AttributeMode.INITIAL);
		tmp.put(EMAILRECEIVER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneJobsStatusProcess.emailReceiver</code> attribute.
	 * @return the emailReceiver
	 */
	public String getEmailReceiver(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILRECEIVER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneJobsStatusProcess.emailReceiver</code> attribute.
	 * @return the emailReceiver
	 */
	public String getEmailReceiver()
	{
		return getEmailReceiver( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneJobsStatusProcess.emailReceiver</code> attribute. 
	 * @param value the emailReceiver
	 */
	public void setEmailReceiver(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILRECEIVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneJobsStatusProcess.emailReceiver</code> attribute. 
	 * @param value the emailReceiver
	 */
	public void setEmailReceiver(final String value)
	{
		setEmailReceiver( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneJobsStatusProcess.siteoneJobsStatusList</code> attribute.
	 * @return the siteoneJobsStatusList - list of siteone cronjobs status
	 */
	public List<CronJob> getSiteoneJobsStatusList(final SessionContext ctx)
	{
		List<CronJob> coll = (List<CronJob>)getProperty( ctx, SITEONEJOBSSTATUSLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneJobsStatusProcess.siteoneJobsStatusList</code> attribute.
	 * @return the siteoneJobsStatusList - list of siteone cronjobs status
	 */
	public List<CronJob> getSiteoneJobsStatusList()
	{
		return getSiteoneJobsStatusList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneJobsStatusProcess.siteoneJobsStatusList</code> attribute. 
	 * @param value the siteoneJobsStatusList - list of siteone cronjobs status
	 */
	public void setSiteoneJobsStatusList(final SessionContext ctx, final List<CronJob> value)
	{
		setProperty(ctx, SITEONEJOBSSTATUSLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneJobsStatusProcess.siteoneJobsStatusList</code> attribute. 
	 * @param value the siteoneJobsStatusList - list of siteone cronjobs status
	 */
	public void setSiteoneJobsStatusList(final List<CronJob> value)
	{
		setSiteoneJobsStatusList( getSession().getSessionContext(), value );
	}
	
}
