/**
 *
 */
package com.siteone.core.jobs.customer;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.siteone.core.model.OrderStatusNotificationCronJobModel;
import com.siteone.core.order.services.SiteOneOrderService;


/**
 * @author snavamani
 *
 */
public class OrderStatusNotificationCronJob extends AbstractJobPerformable<OrderStatusNotificationCronJobModel>
{
	private SiteOneOrderService siteOneOrderService;

	private static final Logger LOG = Logger.getLogger(OrderStatusNotificationCronJob.class);

	@Override
	public PerformResult perform(final OrderStatusNotificationCronJobModel orderStatusNotificationCronJobModel)
	{
		try
		{
			getSiteOneOrderService().sendOrderStatusNotification(orderStatusNotificationCronJobModel);
		}
		catch (final IOException exception)
		{
			LOG.error("Exception occured in order status notification cron job ", exception);
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