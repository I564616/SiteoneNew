/**
 *
 */
package com.siteone.fulfilmentprocess.actions.order;

import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.model.ProcessTaskModel;
import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.util.Config;

import java.util.Objects;

import jakarta.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.integration.services.ue.SiteOneOrderWebService;

/**
 * @author 1085284
 *
 */
public class SubmitOrderUEAction extends AbstractSimpleDecisionAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(SubmitOrderUEAction.class);

	private int maxRetryCount;
	private long retryDelay;
	private SiteOneOrderWebService siteOneOrderWebService;
	
	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;


	@Override
	public Transition executeAction(final ConsignmentProcessModel consignmentProcessModel)
	{

		ProcessTaskModel thisTask = null;
		for (final ProcessTaskModel task : consignmentProcessModel.getCurrentTasks())
		{
			final String action = task.getAction();
			if (action.equals("submitOrderToUE"))
			{
				thisTask = task;
			}
		}
		try
		{
			siteOneOrderWebService.submitOrder(consignmentProcessModel.getConsignment(), Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			LOG.info("Order " + consignmentProcessModel.getConsignment().getCode() + " placed successfully" + "for fulfillment type:"+consignmentProcessModel.getConsignment().getDeliveryMode().getCode());
			return Transition.OK;

		}
		catch (final ResourceAccessException resourceAccessException)
		{
			LOG.error("Not able to connect to ESB while placing order. Retrying.. ");
			Objects.requireNonNull(thisTask, "Current task cannot be null");
			maxRetryCount = Config.getInt("hybris.ordersubmit.maxRetryCount", 3);
			retryDelay = Config.getLong("hybris.ordersubmit.retryDelay", 5000);
			int retryCount = 0;
			if (null != thisTask.getRetry())// NO PMD
			{
				retryCount = thisTask.getRetry().intValue();
			}
			if (retryCount < maxRetryCount)
			{
				final RetryLaterException retryLaterException = new RetryLaterException("Failed to submit order to UE, retrying..",
						resourceAccessException.getCause());
				retryLaterException.setRollBack(false);
				retryLaterException.setDelay(retryDelay);
				throw retryLaterException;
			}

			return Transition.NOK;
		}
		catch (final RestClientException restClientException)
		{
			LOG.error("Exception while executing SubmitOrderUEAction ", restClientException);
			return Transition.NOK;

		}catch (Exception e){
			LOG.error("Exception while executing SubmitOrderUEAction ", e);
			return Transition.NOK;
		}


	}

	public SiteOneOrderWebService getSiteOneOrderWebService()
	{
		return siteOneOrderWebService;
	}

	public void setSiteOneOrderWebService(final SiteOneOrderWebService siteOneOrderWebService)
	{
		this.siteOneOrderWebService = siteOneOrderWebService;
	}

}
