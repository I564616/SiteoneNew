/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;

import com.siteone.core.model.QuoteToOrderStatusEmailCronJobModel;
import com.siteone.core.order.services.SiteOneOrderService;


/**
 *
 */
public class QuoteToOrderStatusEmailCronJob extends AbstractJobPerformable<QuoteToOrderStatusEmailCronJobModel>
{
	private SiteOneOrderService siteOneOrderService;

	private static final Logger LOG = Logger.getLogger(QuoteToOrderStatusEmailCronJob.class);

	@Override
	public PerformResult perform(final QuoteToOrderStatusEmailCronJobModel quoteToOrderStatusEmailCronJobModel)
	{
		try
		{
			getSiteOneOrderService().sendQuoteToOrderStatusEmail(quoteToOrderStatusEmailCronJobModel);
		}
		catch (final Exception exception)
		{
			LOG.error("Exception occured in order status email cron job ", exception);
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the siteOneOrderService
	 */
	public SiteOneOrderService getSiteOneOrderService()
	{
		return siteOneOrderService;
	}

	/**
	 * @param siteOneOrderService
	 *           the siteOneOrderService to set
	 */
	public void setSiteOneOrderService(final SiteOneOrderService siteOneOrderService)
	{
		this.siteOneOrderService = siteOneOrderService;
	}

}
