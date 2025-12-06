/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;

import org.apache.log4j.Logger;

import com.siteone.core.order.services.SiteOneOrderService;


/**
 * @author SNavamani
 *
 */
public class OrderingUnitCellDecorator implements CSVCellDecorator
{
	private SiteOneOrderService siteOneOrderService;

	private static final Logger LOGGER = Logger.getLogger(OrderingUnitCellDecorator.class);

	@Override
	public String decorate(final int position, final Map srcLine)
	{
		siteOneOrderService = (SiteOneOrderService) Registry.getApplicationContext().getBean("defaultSiteOneOrderService");
		final String orderId = (String) srcLine.get(Integer.valueOf(position));
		String orderingAccountUid = null;
		if (orderId == null || orderId.length() == 0)
		{
			return orderId;
		}
		else
		{
			try
			{
				orderingAccountUid = getSiteOneOrderService().getOrderingAccountforOrder(orderId);
			}
			catch (final ModelNotFoundException e)
			{
				LOGGER.error(e);
			}
		}
		return orderingAccountUid;

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