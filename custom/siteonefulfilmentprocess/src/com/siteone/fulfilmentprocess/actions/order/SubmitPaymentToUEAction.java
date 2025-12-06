/**
 *
 */
package com.siteone.fulfilmentprocess.actions.order;

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
import com.siteone.core.model.LinkToPayPaymentProcessModel;
import com.siteone.integration.services.ue.SiteOneLinkToPayWebService;


/**
 * @author 1085284
 *
 */
public class SubmitPaymentToUEAction extends AbstractSimpleDecisionAction<LinkToPayPaymentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(SubmitPaymentToUEAction.class);
	public static final String STATUS_OK = "OK";

	private int maxRetryCount;
	private long retryDelay;
	private SiteOneLinkToPayWebService siteOneLinkToPayWebService;

	private static final String BOOMI_PLATFORM = "boomiPlatformLinux";
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;


	@Override
	public Transition executeAction(final LinkToPayPaymentProcessModel linkToPayProcess)
	{

		ProcessTaskModel thisTask = null;
		for (final ProcessTaskModel task : linkToPayProcess.getCurrentTasks())
		{
			final String action = task.getAction();
			if (action.equals("submitPaymentToUE"))
			{
				thisTask = task;
			}
		}
		try
		{
			final String responseStatus = siteOneLinkToPayWebService.sendPaymentDetails(linkToPayProcess.getCayanResponseForm(),
					Boolean.parseBoolean(siteOneFeatureSwitchCacheService.getValueForSwitch(BOOMI_PLATFORM)));
			LOG.error("Payment " + linkToPayProcess.getCayanResponseForm().getOrderNumber() + " placed successfully");
			LOG.error("LTP Payment response" + responseStatus);
			if (null != responseStatus && responseStatus.contains(STATUS_OK))
			{
				return Transition.OK;
			}
			else
			{
				return Transition.NOK;
			}

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

		}
		catch (final Exception e)
		{
			LOG.error("Exception while executing SubmitOrderUEAction ", e);
			return Transition.NOK;
		}


	}


	/**
	 * @return the siteOneLinkToPayWebService
	 */
	public SiteOneLinkToPayWebService getSiteOneLinkToPayWebService()
	{
		return siteOneLinkToPayWebService;
	}


	/**
	 * @param siteOneLinkToPayWebService
	 *           the siteOneLinkToPayWebService to set
	 */
	public void setSiteOneLinkToPayWebService(final SiteOneLinkToPayWebService siteOneLinkToPayWebService)
	{
		this.siteOneLinkToPayWebService = siteOneLinkToPayWebService;
	}


}
