/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.OrderReadyForPickUpRemainderEmailCronJob OrderReadyForPickUpRemainderEmailCronJob}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedOrderReadyForPickUpRemainderEmailCronJob extends CronJob
{
	/** Qualifier of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute **/
	public static final String FIVEDAYSOLD = "fiveDaysOld";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CronJob.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FIVEDAYSOLD, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute.
	 * @return the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public Integer getFiveDaysOld(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, FIVEDAYSOLD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute.
	 * @return the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public Integer getFiveDaysOld()
	{
		return getFiveDaysOld( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute. 
	 * @return the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public int getFiveDaysOldAsPrimitive(final SessionContext ctx)
	{
		Integer value = getFiveDaysOld( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute. 
	 * @return the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public int getFiveDaysOldAsPrimitive()
	{
		return getFiveDaysOldAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute. 
	 * @param value the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public void setFiveDaysOld(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, FIVEDAYSOLD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute. 
	 * @param value the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public void setFiveDaysOld(final Integer value)
	{
		setFiveDaysOld( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute. 
	 * @param value the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public void setFiveDaysOld(final SessionContext ctx, final int value)
	{
		setFiveDaysOld( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderReadyForPickUpRemainderEmailCronJob.fiveDaysOld</code> attribute. 
	 * @param value the fiveDaysOld - All Pick Up Orders which are Five Days Older
	 */
	public void setFiveDaysOld(final int value)
	{
		setFiveDaysOld( getSession().getSessionContext(), value );
	}
	
}
