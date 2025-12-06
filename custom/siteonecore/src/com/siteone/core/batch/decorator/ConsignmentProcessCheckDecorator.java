/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.order.services.SiteOneOrderService;


/**
 *
 */
public class ConsignmentProcessCheckDecorator implements CSVCellDecorator
{
	private SiteOneOrderService siteOneOrderService;
	private SiteOneB2BUnitService siteOneB2BUnitService;
	private static final Logger LOGGER = Logger.getLogger(MainAccountCellDecorator.class);

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		siteOneB2BUnitService = (SiteOneB2BUnitService) Registry.getApplicationContext().getBean("defaultSiteOneB2BUnitService");
		siteOneOrderService = (SiteOneOrderService) Registry.getApplicationContext().getBean("defaultSiteOneOrderService");
		String orderId = (String) srcLine.get(Integer.valueOf(position));
		LOGGER.error("Consignment feed orderId : " + orderId);
		if (orderId == null || orderId.length() == 0)
		{
			return orderId;
		}
		else
		{
			try
			{
				LOGGER.error("Consignment feed orderId1 " + orderId);
				final OrderModel order = getSiteOneOrderService().getOrderForCode(orderId);
				LOGGER.error("Consignment feed ordermodel " + order);
				if (order != null && CollectionUtils.isNotEmpty(order.getConsignments()) && order.getConsignments().size() > 1)
				{
					LOGGER.error("Consignment feed consignment size " + order.getConsignments().size());
					for (final ConsignmentModel consignment : order.getConsignments())
					{
						LOGGER.error("Consignment feed consignment code " + consignment.getCode());
						for (final ConsignmentProcessModel process : consignment.getConsignmentProcesses())
						{
							LOGGER.error("Consignment feed process status " + consignment.getCode() + " " + process.getState().getCode()
									+ " " + process.getProcessState().getCode() + " " + process.getEndMessage());
							if (!process.getState().getCode().equalsIgnoreCase("SUCCEEDED")
									&& !process.getEndMessage().equalsIgnoreCase("Order placed."))
							{
								orderId = "";
								break;
							}
						}
					}
				}
			}
			catch (final ModelNotFoundException e)
			{
				LOGGER.error(e);
			}

		}
		return orderId;
	}

	/**
	 * @return the siteOneB2BUnitService
	 */
	public SiteOneB2BUnitService getSiteOneB2BUnitService()
	{
		return siteOneB2BUnitService;
	}

	/**
	 * @param siteOneB2BUnitService
	 *           the siteOneB2BUnitService to set
	 */
	public void setSiteOneB2BUnitService(final SiteOneB2BUnitService siteOneB2BUnitService)
	{
		this.siteOneB2BUnitService = siteOneB2BUnitService;
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
