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

import com.siteone.core.model.OrderReadyForPickUpRemainderEmailCronJobModel;
import com.siteone.core.order.services.SiteOneOrderService;


/**
 * @author 1099417
 *
 */
public class OrderReadyForPickUpRemainderEmailCronJob extends AbstractJobPerformable<OrderReadyForPickUpRemainderEmailCronJobModel>
{
	private SiteOneOrderService siteOneOrderService;

	private static final Logger LOG = Logger.getLogger(OrderReadyForPickUpRemainderEmailCronJob.class);

	@Override
	public PerformResult perform(final OrderReadyForPickUpRemainderEmailCronJobModel orderReadyForPickUpRemainderEmailCronJobModel)
	{
		try
		{
			getSiteOneOrderService().sendOrderReadyForPickUpRemainderEmail(orderReadyForPickUpRemainderEmailCronJobModel);
		}
		catch (final IOException exception)
		{
			LOG.error("Exception occured in order ready for pick up remainder email cron job ", exception);
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
